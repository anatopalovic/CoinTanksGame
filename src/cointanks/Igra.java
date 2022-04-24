package cointanks;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	
	private Mreza mreza;
	private TextField novcici;
	private CheckboxGroup vrsta_polja;
	private Label poeni;
	private MenuBar rezim;
	public enum Vrsta { IZMENA, IGRANJE };
	private Vrsta vrsta;
	
	
	public Igra() {
		super("Igra");
		setBounds(150, 50, 600, 600);
		setLayout(new BorderLayout());
		
		mreza = new Mreza(this);
		
		rezim = new MenuBar();
		setMenuBar(rezim);
		Menu meni = new Menu("Rezim");
		rezim.add(meni);
		MenuItem izmena = new MenuItem("Rezim izmena");
		izmena.addActionListener(e -> {
			
			
				mreza.zaustavi(true);
			
			vrsta = Vrsta.IZMENA;
			
		});
		
		meni.add(izmena);
		meni.addSeparator();
		
		MenuItem igranje = new MenuItem("Rezim igranje");
		igranje.addActionListener(e -> {
			
			vrsta = Vrsta.IGRANJE;
			
		});

		meni.add(igranje);
		
		
		Panel donji = new Panel(new GridLayout(1, 4));
		donji.setLayout(new FlowLayout(FlowLayout.CENTER));
		novcici = new TextField("");
		donji.add(new Label("Novcica:"));
		donji.add(novcici);
		poeni = new Label("Poena: ");
		Button pocni = new Button("Pocni");
		
		pocni.addActionListener(e -> {
			if(vrsta == Vrsta.IGRANJE && !novcici.getText().equals("")) {
				if(mreza.jel_radi() == false) {
					if(mreza.jel_gotova())
						mreza.obrisi_figure();
					mreza.inicijalizuj(Integer.parseInt(novcici.getText()));
					mreza.pokreni();
				}
				else {
					mreza.zaustavi(true);
					mreza.inicijalizuj(Integer.parseInt(novcici.getText()));
					mreza.pokreni();
				}
			}
		});
		
		donji.add(poeni);
		donji.add(pocni);
		
		add(donji, BorderLayout.SOUTH);
		
		Panel desni = new Panel(new GridLayout(1, 2));
		desni.add(new Label("Podloga"));
		
		Panel polja = new Panel(new GridLayout(2, 1));
		
		Panel trava = new Panel(new BorderLayout());
		vrsta_polja = new CheckboxGroup();
		Checkbox travnato = new Checkbox("Trava", true, vrsta_polja);
		Checkbox zidno = new Checkbox("Zid", false, vrsta_polja);
		trava.add(travnato, BorderLayout.CENTER);
		trava.setBackground(Color.GREEN);
		
		Panel zid = new Panel(new BorderLayout());
		zid.add(zidno, BorderLayout.CENTER);
		zid.setBackground(Color.LIGHT_GRAY);
		
		polja.add(trava);
		polja.add(zid);
		
		desni.add(polja);
		
		add(desni,BorderLayout.EAST);
		
		add(mreza, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mreza.zaustavi(true);
				dispose();
			}
		});
		
		setVisible(true);
		//mreza.requestFocus();
	}
	
	public Vrsta rezim_igre() {
		return vrsta;
	}
	
	public void azuriraj_poene(int p) {
		poeni.setText("Poena: " + p);
	}
	
	public String izabrano_polje() {
		return vrsta_polja.getSelectedCheckbox().getLabel();
	}

	public static void main(String[] args) {
		
		new Igra();

	}

}
