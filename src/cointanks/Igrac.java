package cointanks;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {

	public Igrac(Polje polje) {
		super(polje);
	}
	
	@Override
	public boolean pomeri(Polje p) {
		
		if(p == null)
			return false;
		if(p.dozvoljeno(this)) {
			polje.repaint();
			polje = p;
			return true;
		}
		return false;
	}

	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		int visina = polje.getHeight();
		int sirina = polje.getWidth();
		int dim = Math.min(visina, sirina);
		g.setColor(Color.RED);
		g.drawLine(dim / 2, 0, dim / 2, dim);
		g.drawLine(0, dim / 2, dim, dim / 2);
		g.dispose();
	}

}
