package cointanks;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {

	public Novcic(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		int sirina = polje.getWidth();
		int visina = polje.getHeight();
		int dim = Math.min(visina, sirina);
		int x = dim / 2;
		int y = dim / 2;
		int r = dim / 2;
		x -= r / 2;
		y -= r / 2;
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, r, r);
		g.dispose();

	}

	@Override
	public boolean pomeri(Polje p) {
		return false;
	}

}
