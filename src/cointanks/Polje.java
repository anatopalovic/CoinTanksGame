package cointanks;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cointanks.Igra.Vrsta;

public abstract class Polje extends Canvas {
	
	private Mreza mreza;
	private int vrsta;
	private int kolona;
	
	public Polje(Mreza mreza) {
		super();
		this.mreza = mreza;
		
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if(mreza.rezim() == Vrsta.IZMENA)
					mreza.izmeni_polje(pozicija());
			}
		});
		
	}
	
	public int[] pozicija() {
		int niz[] = new int[2];
		niz[0] = vrsta;
		niz[1] = kolona;
		return niz;
	}
	
	public void postavi_poz(int i, int j) {
		vrsta = i;
		kolona = j;
	}
	
	public Polje udaljeno(int i, int j) {
		return mreza.nadji_udaljeno(vrsta, kolona, i, j);
	}
	
	public Mreza getMreza() {
		return mreza;
	}
	
	public abstract boolean dozvoljeno(Figura figura);
	

}
