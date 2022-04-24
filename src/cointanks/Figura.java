package cointanks;


public abstract class Figura {
	
	protected Polje polje;

	public Figura(Polje polje) {
		super();
		this.polje = polje;
	}
	
	public Polje getPolje() {
		return polje;
	}
	
	public abstract boolean pomeri(Polje p);	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof Figura))
			return false;
		
		return polje == ((Figura)obj).polje;
	}
	
	public abstract void iscrtaj();

}
