package cointanks;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import cointanks.Igra.Vrsta;

public class Mreza extends Panel implements Runnable {
	
	private Thread nit;
	private int vrste;
	private Igra igra;
	private Polje polja[][];
	private int poeni = 0;
	private int broj_novcica;
	private boolean radi = false;
	private boolean gotova = false;
	private LinkedList<Igrac> igraci = new LinkedList<Igrac>();
	private LinkedList<Novcic> novcici = new LinkedList<Novcic>();
	private LinkedList<Tenk> tenkovi = new LinkedList<Tenk>();

	public Mreza(int vrste, Igra igra) {
		super();
		this.vrste = vrste;
		this.igra = igra;
		polja = new Polje[vrste][vrste];
		setLayout(new GridLayout(vrste, vrste));
		postavi_polja(vrste);
		
		
		addKeyListener(new KeyAdapter() {
			
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(radi == false)
					return;
				
				
				Igrac igrac = igraci.get(0);
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W: {
					igrac.pomeri(igrac.getPolje().udaljeno(-1, 0));
					break;
				}
				case KeyEvent.VK_S: {
					igrac.pomeri(igrac.getPolje().udaljeno(1, 0));
					break;
				}
				case KeyEvent.VK_A: {
					igrac.pomeri(igrac.getPolje().udaljeno(0, -1));
					break;
				}
				case KeyEvent.VK_D: {
					igrac.pomeri(igrac.getPolje().udaljeno(0, 1));
					break;
				}
				}
			}
		});
	}
	
private void postavi_polja(int vrste) {
		
		int br_trava =(int)(vrste * vrste * 0.8);
		
		int zauzeta[][] = new int[br_trava][2];
		
		int x = 0;
		while(x < br_trava) {
			
			int i = (int)(Math.random() * vrste);
			int j = (int)(Math.random() * vrste);
			boolean nema = true;
			for(int m = 0; m < x; m++) 
				if(zauzeta[m][0] == i && zauzeta[m][1] == j) {
					nema = false;
					break;
				}
				
			if(nema == true) {
				zauzeta[x][0] = i;
				zauzeta[x][1] = j;
				++x;
			}
		}
		for(int i = 0; i < vrste; i++)
			for(int j = 0; j < vrste; j++) {
				boolean ima = false;
				for(int k = 0; k < br_trava; k++)
					if(zauzeta[k][0] == i && zauzeta[k][1] == j) {
						ima = true;
						break;
					}
				if(ima == true)
					polja[i][j] = new Trava(this);
				else
					polja[i][j] = new Zid(this);
				
				add(polja[i][j]);
				polja[i][j].postavi_poz(i, j );
			}
	}

	public Mreza(Igra igra) {
		this(17, igra);
	}

	@Override
	public void run() {
		
		while(!Thread.interrupted()) {
			
			try {
			
				Thread.sleep(40);
				azuriraj();
				repaint();
			
			} catch (InterruptedException e) {
				nit.interrupt();
			}
		}
	}

	private void azuriraj() {
		
		if(radi == false)
			return;
		
		Polje igracevo = igraci.get(0).getPolje();
		
		for(Novcic n : novcici)
			if(n.getPolje() == igracevo) {
				++poeni;
				igra.azuriraj_poene(poeni);
				n.getPolje().repaint();
				novcici.remove(n);
				break;
			}
		for(Tenk t : tenkovi)
			if(t.getPolje() == igracevo) {
				igraci.remove(0);
				zaustavi(false);
				return;
			}
		
		if(poeni == broj_novcica) {
			zaustavi(false);
		}
		
	}
	
	public void pokreni() {
		nit = new Thread(this);
		nit.start();
		for(Tenk t : tenkovi)
			t.pokreni();
		radi = true;
		gotova = false;
		requestFocus();
	}

	public void zaustavi(boolean kraj) {
		
		if(radi == false)
			return;
	
		for(Tenk t : tenkovi)
			t.zaustavi();
		
		nit.interrupt();
		
		if(kraj == true) 
			obrisi_figure();
			
		radi = false;
		gotova = true;
		
	}

	public void obrisi_figure() {
		
		int n = igraci.size();
		for(int i = 0; i < n; i++)
			igraci.remove();
		
		n = novcici.size();
		for(int i = 0; i < n; i++)
			novcici.remove();

		n = tenkovi.size();
		for(int i = 0; i < n; i++)
			tenkovi.remove();
		
		for(int i = 0; i < vrste; i++)
			for(int j = 0; j < vrste; j++)
				polja[i][j].repaint();
	}

	@Override
	public void paint(Graphics g) {
		
		for(Igrac i : igraci)
			i.iscrtaj();

		for(Novcic n : novcici)
			n.iscrtaj();
		
		for(Tenk t : tenkovi)
			t.iscrtaj();
	}

public void inicijalizuj(int br_novcica) {
	
	
		broj_novcica = br_novcica;
		poeni = 0;
		igra.azuriraj_poene(poeni);
		
		int dodati = 0;
		
		Figura probni_novcic = new Novcic(null);
		
		while(dodati < br_novcica) {
			
			int i = (int)(Math.random() * vrste);
			int j = (int)(Math.random() * vrste);
			if(polja[i][j].dozvoljeno(probni_novcic) && jel_slobodno(polja[i][j]) == true) {
				novcici.add(new Novcic(polja[i][j]));
				++dodati;
			}
		}
		dodati = 0;
		int br_tenkova = br_novcica / 3;
		Figura probni_tenk = new Tenk(null);
		
		while(dodati < br_tenkova) {
			
			int i = (int)(Math.random() * vrste);
			int j = (int)(Math.random() * vrste);
			if(polja[i][j].dozvoljeno(probni_tenk) && jel_slobodno(polja[i][j]) == true) {
				tenkovi.add(new Tenk(polja[i][j]));
				++dodati;
			}	
			
		}
		
		dodati = 0;
		Figura probni_igrac = new Igrac(null);
		
		while(dodati < 1) {
			int i = (int)(Math.random() * vrste);
			int j = (int)(Math.random() * vrste);
			if(polja[i][j].dozvoljeno(probni_igrac) && jel_slobodno(polja[i][j]) == true) {
				igraci.add(new Igrac(polja[i][j]));
				++dodati;
			}
		}
		repaint();
	}

	private boolean jel_slobodno(Polje polje) {
		
		for(Igrac i : igraci)
			if(i.getPolje() == polje) {
				return false;
			}
		for(Novcic n : novcici)
			if(n.getPolje() == polje) {
				return false;
			}
		for(Tenk t : tenkovi)
			if(t.getPolje() == polje) {
				return false;
			}
		
		return true;
	}

	public Polje nadji_udaljeno(int vrsta, int kolona, int i, int j) {
		if(vrsta + i >= vrste || vrsta + i < 0 || kolona + j >= vrste || kolona + j < 0)
			return null;
		return polja[vrsta + i][kolona + j];
	}
	
	public Vrsta rezim() {
		return igra.rezim_igre();
	}

	public void izmeni_polje(int[] pozicija) {
		
		Polje novo;
		
		if(igra.izabrano_polje().equals("Trava")) 
			novo = new Trava(this);
		else
			novo = new Zid(this);
		
		int v = pozicija[0];
		int k = pozicija[1];
		
		polja[v][k] = novo;
		polja[v][k].postavi_poz(v, k);
		
		int index = v * vrste + k;
		
		remove(index);
		
		add(novo, index);
		
		revalidate();
	}
	
	public boolean jel_radi() {
		return radi;
	}
	
	public boolean jel_gotova() {
		return gotova;
	}
}
