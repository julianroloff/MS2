package gui;

/**
 * Julian Roloff, 7352137
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

public class Menütafel extends JPanel{
	
	
	
	
	
	
	private JPanel varPanel1, varPanel2, varPanel3, varPanel4, varPanel5, varPanel6, varPanel7;
	
	
	private JLabel timerLabel;
	private Timer timer;
	private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
	
	private JLabel startingPlayer;
	private JRadioButton sOne;
	private JRadioButton sTwo;
	
	private JLabel farbenLabel;
	private JComboBox<Integer> selectFarben;
	
	private JLabel zeilenLabel;
	private JComboBox<Integer> selectZeilen;
	
	private JLabel spaltenLabel;
	private JComboBox<Integer> selectSpalten;
	
	private JLabel strategieLabel;
	
	
	
	private JComboBox<String> selectStrategie;
	private String strat;
	
	
	private JButton bedienungsanleitung;
	
	
	
	private int anzahlFarben;
	private int anzahlZeilen;
	private int anzahlSpalten;
	
	
	private boolean startMode;
	private boolean s1Clicked = true;
	

	
	public Menütafel() {				//Menütafel auf rechter Seite des Fensters
		super();
		this.setLayout(new GridLayout(7,1));	//Menütafel besteht aus 7 Komponenten
		
		
		//Create 7 Panels
		varPanel1 = new JPanel();	
		varPanel2 = new JPanel();	//Play Button & Start Button
		varPanel2.setBackground(Color.red);
		varPanel2.setLayout(new GridLayout(1,2));
		varPanel3 = new JPanel();
		varPanel3.setLayout(new GridLayout(3,1));
		varPanel3.setBackground(Color.ORANGE);
		
		varPanel4 = new JPanel();
		varPanel4.setLayout(new GridLayout(1,2));
		varPanel4.setBackground(Color.ORANGE);
		
		varPanel5 = new JPanel();
		varPanel5.setLayout(new GridLayout(2,2));
		varPanel5.setBackground(Color.ORANGE);
		
		varPanel6 = new JPanel();
		varPanel6.setLayout(new GridLayout(1,2));
		varPanel6.setBackground(Color.ORANGE);
		
		
		varPanel7 = new JPanel();	//Bedienungsanleitung Button
		bedienungsanleitung = new JButton("Bedienungsanleitung");
		JLabel b1 = new JLabel("Bedienungsanleitung:");
		JLabel b2 = new JLabel("- Ziel des Spiels ist es eine größere Farbfläche als sein Gegner zu haben");
		JLabel b3 = new JLabel("- Du kannst auf zwei verschiedene Arten eine Farbe auswählen:");
		JLabel b4 = new JLabel("1. Durch das klicken auf die gewünschte Farbe");
		JLabel b5 = new JLabel("2. Durch das klicken der entsprechenden Ziffer auf der Tastatur");
		bedienungsanleitung.addActionListener(new ActionListener() {								
            @Override																
            public void actionPerformed(ActionEvent e) {
                JFrame bed = new JFrame("Bedienungsanleitung");
                bed.setSize(500,300);
                bed.setLocationRelativeTo(null);
                bed.setLayout(new GridLayout(5,1));
                bed.add(b1);
                bed.add(b2);
                bed.add(b3);
                bed.add(b4);
                bed.add(b5);
                bed.setVisible(true);
                
            }
        });
		varPanel7.add(bedienungsanleitung);
		varPanel7.setBackground(Color.ORANGE);
		
		
		
		
		
		
		
		
		
		this.setBackground(Color.red);									//Panel einfärben nur zur Visualisierung
		
		
		
		//Panel 1
		 

	    varPanel1 = new JPanel();
	    varPanel1.setBackground(Color.red);
	    
	    timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        varPanel1.add(timerLabel);
	    this.add(varPanel1);
	    
	    
		
		
		
		
		//Panel 2
		this.add(varPanel2);
		
		
		//Panel 3
		startingPlayer = new JLabel("Wer beginnt das Spiel?");// Label für Radio Buttons: Welcher Spieler soll starten?
		
		varPanel3.add(startingPlayer);									//Panel und RadioButtons hinzufügen
		
		
		sOne = new JRadioButton("S1", true);									// Radio Buttons um startenden Spieler auszuwählen
		sTwo = new JRadioButton("S2", false);
		ActionListener radioActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton radioButton = (JRadioButton) e.getSource();
                if (radioButton.isSelected()) {
                    s1Clicked = true;
                    //System.out.print(s1Clicked);
                } 
            }
        };
        ActionListener radioActionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton radioButton = (JRadioButton) e.getSource();
                if (radioButton.isSelected()) {
                    s1Clicked = false;
                    //System.out.print(s1Clicked);
                } 
            }
        };
        
        sOne.addActionListener(radioActionListener);
		sTwo.addActionListener(radioActionListener2);
																		
		ButtonGroup group = new ButtonGroup();							//Button Group erstellen damit immer nur ein RadioButton ausgewählt ist
		group.add(sOne);
		group.add(sTwo);
		
		varPanel3.add(sOne);
		varPanel3.add(sTwo);
		
		this.add(varPanel3);
		
		//Panel 4
		farbenLabel = new JLabel("Farben:");		// JLabel für ComboBox: Anzahl Farben
													//Komponenten zu Panel 4 hinzufügen
	    varPanel4.add(farbenLabel);									//JLabel Anzahl Farben
	    
	    
	    
	    
	    Integer[] choices = {4,5,6,7,8,9};				//Choices für ComboBox: Anzahl Farben
	    selectFarben = new JComboBox<Integer>(choices);							//ComboBox füllen
	    selectFarben.setSelectedIndex(1);										//Default = 5
	    anzahlFarben = (int)selectFarben.getSelectedItem();
	    
	 // ActionListener Farben hinzufügen
        selectFarben.addActionListener(new ActionListener() {								//ActionListener hinzufüfen um eingaben aus
            @Override																//JComboBox zu bekommen
            public void actionPerformed(ActionEvent e) {
                JComboBox<Integer> source = (JComboBox<Integer>) e.getSource();
                anzahlFarben = (int) source.getSelectedItem();
            }
        });
	    
	    varPanel4.add(selectFarben);
	    
	    this.add(varPanel4);
	    
	    //Panel 5
	    zeilenLabel = new JLabel("Zeilen:");					//JLabel für ComboBox: Anzahl Zeilen
	    Integer[] zeilenChoices = { 3,4,5,6,7,8,9,10}; 								//Choices für ComboBox: Anzahl Zeilen
	    selectZeilen = new JComboBox<Integer>(zeilenChoices);					//ComboBox: Anzahl Zeilen
	    selectZeilen.setSelectedIndex(3);										//Default = 6
	    anzahlZeilen = (int)selectZeilen.getSelectedItem();
	    
	 // ActionListener Zeilen hinzufügen
        selectZeilen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Integer> source = (JComboBox<Integer>) e.getSource();
                anzahlZeilen = (int) source.getSelectedItem();
                
            }
        });
        
        spaltenLabel = new JLabel("Spalten:");					//JLabel für ComboBox: Anzahl Spalten	
        Integer[] spaltenChoices = { 3,4,5,6,7,8,9,10};								//Choices für ComboBox: Anzahl Spalten
	    selectSpalten = new JComboBox<Integer>(spaltenChoices);					//ComboBox: Anzahl Spalten
	    selectSpalten.setSelectedIndex(3);										//Default = 6
	    anzahlSpalten = (int)selectSpalten.getSelectedItem();
	    
	 // ActionListener Spalten hinzufügen
        selectSpalten.addActionListener(new ActionListener() {								//ActionListener hinzufüfen um eingaben aus
            @Override																//JComboBox zu bekommen
            public void actionPerformed(ActionEvent e) {
                JComboBox<Integer> source = (JComboBox<Integer>) e.getSource();
                anzahlSpalten = (int) source.getSelectedItem();
            }
        });
        
        
        varPanel5.add(zeilenLabel);
        varPanel5.add(selectZeilen);
        varPanel5.add(spaltenLabel);
        varPanel5.add(selectSpalten);
	    this.add(varPanel5);
	    
	    
	    //Panel 6
	    
	    strategieLabel = new JLabel("Strategie PC:");					//JLable für ComboBox: Wähle Strategie
	    
	    varPanel6.add(strategieLabel);
	    
	    String [] strategieChoices = {"Stagnation", "Greedy", "Blocking"};		//Choices für ComboBox: Wähle Strategie
	    selectStrategie = new JComboBox<String>(strategieChoices);						//ComboBox_ Wähle Strategie
	    selectStrategie.setSelectedIndex(0);											//Default = Strat01
	    strat = (String)selectStrategie.getSelectedItem();
	    
	 // ActionListener Strategie hinzufügen
        selectStrategie.addActionListener(new ActionListener() {								//ActionListener hinzufüfen um eingaben aus
            @Override																//JComboBox zu bekommen
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                strat = (String) source.getSelectedItem();
            }
        });
		varPanel6.add(selectStrategie);
	    
	    this.add(varPanel6);
	    
	    //Panel 7
	    
	    this.add(varPanel7);

	}



	
	
	/**
	 * Getter und Setter
	 */
	public int getAnzahlFarben() {
		return anzahlFarben;
	}



	public void setAnzahlFarben(int anzahlFarben) {
		this.anzahlFarben = anzahlFarben;
	}



	public int getAnzahlZeilen() {
		return anzahlZeilen;
	}



	public void setAnzahlZeilen(int anzahlZeilen) {
		this.anzahlZeilen = anzahlZeilen;
	}



	public int getAnzahlSpalten() {
		return anzahlSpalten;
	}



	public void setAnzahlSpalten(int anzahlSpalten) {
		this.anzahlSpalten = anzahlSpalten;
	}


	public String getStrategie() {
		return strat;
	}
	

	public boolean isS1Clicked() {
		return s1Clicked;
	}
	
	public void setS1Clicked(boolean s) {
		this.s1Clicked = s;
	}



	public boolean isStartMode() {
		return startMode;
	}

	public void setStartMode(boolean startMode) {
		this.startMode = startMode;
	}
	
	
	public void setVarPanel2(JButton b) {
		varPanel2.add(b);
	}
	
	
	
	
	public void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                    if (minutes == 60) {
                        minutes = 0;
                        hours++;
                    }
                }
                String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timerLabel.setText(time);
            }
        });
        timer.start();
    }
	
	public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
	public void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        hours = 0;
        minutes = 0;
        seconds = 0;
        timerLabel.setText("00:00:00");
    }

	
}
