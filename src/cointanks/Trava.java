package cointanks;

import java.awt.Color;
import java.awt.Graphics;

public class Trava extends Polje {
	
	public Trava(Mreza mreza) {
		super(mreza);
	}
	
	@Override
	public boolean dozvoljeno(Figura figura) {
		return true;
	}
	
	@Override
	public void paint(Graphics g) {
		setBackground(Color.GREEN);
	}
	
}
