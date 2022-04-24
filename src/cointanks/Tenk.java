package cointanks;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {
	
	private Thread nit;

	public Tenk(Polje polje) {
		super(polje);
	}

	@Override
	public void run() {
		
		while(!Thread.interrupted()) {
			
			try {
				
				Thread.sleep(500);
				nadji_polje();
				
				
			}catch(InterruptedException e) {
				nit.interrupt();
			}
			
		}
	}

	private void nadji_polje() {
		
		int br = (int)(Math.random() * 4);
		switch(br) {
			case 0: pomeri(polje.udaljeno(-1, 0)); break; // gore
			case 1: pomeri(polje.udaljeno(1, 0)); break; // dole
			case 2: pomeri(polje.udaljeno(0, -1)); break; // levo
			case 3: pomeri(polje.udaljeno(0, 1)); break; // desno
		}
	}

	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		int visina = polje.getHeight();
		int sirina = polje.getWidth();
		int dim = Math.min(visina, sirina);
		g.setColor(Color.BLUE);
		g.drawLine(0, 0, dim, dim);
		g.drawLine(0, dim, dim, 0);
		g.dispose();
		
	}

	@Override
	public boolean pomeri(Polje p) {
		
		if(p == null || !p.dozvoljeno(this)) {
			nadji_polje();
			return false;
		}
		polje.repaint();
		polje = p;
		return true;
	}
	
	public void pokreni() {
		nit = new Thread(this);
		nit.start();
	}

	public void zaustavi() {
		nit.interrupt();
	}

}
