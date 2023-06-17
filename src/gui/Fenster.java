package gui;

/**
 * Julian Roloff, 7352137
 */


import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Timer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import logic.Brett;
import logic.Field;
import logic.Spieler;

public class Fenster extends JFrame{
	
	private Menütafel menü;
	private Anzeigetafel anzeige;
	private JPanel oben;
	private JPanel spielfeld;
	private JPanel unten;
	private Brett feldLogik;
	
	private JLabel spielstand;
	private JLabel score1;
	private int scoreOne = 1;
	private int oldScoreOne;
	private int scoreTwo = 1;
	private int oldScoreTwo;
	private int countScore = 0;
	private JLabel score2;
	private JLabel turn;
	
	
	
	private JButton start;
	private JButton play;
	
	
	private int lastColor1;
	private int lastColor2;
	
	private boolean startMode = false;
	boolean clickable = false;
	private boolean playable = false;
	private int countStart = 0;
	private int countPlay = 0;
	
	
	private Color [][] felder;
	private Color [] farbenUnique;
	
	private int wahlfarbe;
	private Field [][] field;
	
	private Spieler s1, s2;
	
	
	public Fenster() {
		super("MS2");
		this.setSize(600, 600);  						//Window size 600x600
		this.setMinimumSize(new Dimension(600,600));	//Minimum window size 600x600
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	//Stop running when window is closed
		this.setLocationRelativeTo(null);				//When opened, window is in the middle of the screen
		this.setLayout(new BorderLayout());
		
		
		//Menütafel
		//Menütafel
		//Menütafel
		//Menütafel
		//Menütafel
		//Menütafel
		menü = new Menütafel();																	//Create Menütafel		
		menü.setPreferredSize(new Dimension((int) (this.getWidth()*0.4), this.getHeight()));	//Set size to 20% of window size
		
		
	
		//Menü Panel 2
		start = new JButton("Start");									//Start/Stop Knopf erstellen
		//Create responsive Button (Start/Stop Button)
				start.addActionListener(new ActionListener() {
					
					@Override
				    public void actionPerformed(ActionEvent e) {
				        countStart++;
				        if (countStart % 2 == 0) {
				            start.setText("Start");
				            startMode = false;
				            if(countPlay%2!=0) {
				            play.doClick();
				            
				            }
				            play.setEnabled(false);
				            
				            menü.resetTimer();
				            updateSpielfeld();
				            
				        } else {
				            start.setText("Stop");
				            startMode = true;
				            playable = false;
				            feldLogik = new Brett(menü.getAnzahlZeilen(), menü.getAnzahlSpalten(), menü.getAnzahlFarben());
				    		
				    		felder = feldLogik.getFelder();
				    		farbenUnique = updateFarbenListe(felder);
				    		field = feldLogik.createFieldArray(felder);
				    		
				    		/**
				    		 * Testing
				    		 */
				    		for(int o = 0; o < field.length; o++) {
				    			
				    			System.out.print("{");
				    			
				    			for (int k = 0; k < field[0].length; k++) {
				    				System.out.print(field[o][k].getColor() + ",");
				    			
				    			}
				    			System.out.println("},");
				    		}
				    		/**
				    		 * 
				    		 */
				    		
				    		s1 = new Spieler(1, 0, field[field.length-1][0].getColor());
				    		s2 = new Spieler(2,0,field[0][field[0].length-1].getColor());
				    		
				    		field[field.length-1][0].setSpecificSpieler(s1);;
				    		field[0][field[0].length-1].setSpecificSpieler(s2);;
				    		play.setEnabled(true);
				    		
				    		if(menü.isS1Clicked()) {
				    			turn.setText("S1 ist dran.");
				    		} else {
				    			turn.setText("PC ist dran.");
				    		}
				    		
				    		//Reset variables
				    		lastColor1 = field[field.length-1][0].getColor();
				    		lastColor2 = field[0][field[0].length-1].getColor();
				    		
				    		
				    		
				    		updateSpielfeld();
				        }
				        
				        
				        updateFarbauswahl();
					}
				});
		
		
		play = new JButton("Play");										//Play/Pause Knopf erstellen
		play.setEnabled(false);
		//Create responsive Button (Start/Stop Button)
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				countPlay++;
				if(countPlay%2==0) {
					play.setText("Play");
					
					//Button Disabled
					//Stop Game Timer
					menü.stopTimer();
					
					playable = false;
					clickable = false;
				} else {
					play.setText("Pause");
					
					//Buttons Enabled
					//Start Game Timer
					menü.startTimer();

					clickable = true;
					playable = true;
					if(!(menü.isS1Clicked())) {
						spielzug();
					}
					//Tastenklick erfassen
					
					EventQueue.invokeLater(() -> {
			            Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
			                if (event instanceof KeyEvent) {
			                    KeyEvent keyEvent = (KeyEvent) event;
			                    if (keyEvent.getKeyCode() > (47) && keyEvent.getKeyCode() < (farbenUnique.length+ 1 + 48) && keyEvent.getID() == KeyEvent.KEY_PRESSED) {
			                        System.out.println(keyEvent.getKeyCode() - 48);
			                        Color auswahl = feldLogik.getFarbeFromIndex(keyEvent.getKeyCode()-48, farbenUnique);
			                        wahlfarbe = feldLogik.getFarbenIndex(auswahl, farbenUnique);
			                    }
			                    
			                    if (wahlfarbe == field[field.length-1][0].getColor() || wahlfarbe == field[0][field[0].length-1].getColor()) {
				                       return; // Exit early if wahlfarbe is equal to lastColor1 or s2.getColor()
				                   }
			                    
			                    spielzug();
			                    
			                }
			            }, AWTEvent.KEY_EVENT_MASK);
			        });
				}
			       
			}
		});
													
		menü.setVarPanel2(start);
		menü.setVarPanel2(play);
		
		//Menü Panel 3
		
		
		
		
        
		
	  //Menütafel
	  //Menütafel
	  //Menütafel
	  //Menütafel
	  //Menütafel
	  //Menütafel
	    
	    
	    
	    
	    
	    
	    
	  //Anzeigetafel
	  //Anzeigetafel
	  //Anzeigetafel
	  //Anzeigetafel
	  //Anzeigetafel
	  //Anzeigetafel
	  //Anzeigetafel
		anzeige = new Anzeigetafel();															//Create Anzeigetafel
		anzeige.setPreferredSize(new Dimension((int)(this.getWidth()*0.6), this.getHeight()));	//Set size to 80% of window size
		anzeige.setLayout(new BorderLayout());
		
		
		
			//Oberer Teil
			oben = new JPanel();
			oben.setLayout(new GridLayout(1,3));
			spielstand = new JLabel("  Spielstand:");
			score1 = new JLabel("S1:  "+ "1");
			score2 = new JLabel("S2:  " + "1");
			oben.add(spielstand);
			oben.add(score1);
			oben.add(score2);
			turn = new JLabel("");
			oben.add(turn);
			
			
			oben.setBackground(Color.white);
			oben.setPreferredSize(new Dimension(480,100));
			anzeige.add(oben, BorderLayout.NORTH);
			
			//Mittlerer Teil
			spielfeld = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					if(startMode) {
						
						paintGrid(g);
						repaint();
					}
				}    				
			};
			
			//Add mouse listener for grid:
		       spielfeld.addMouseListener(new MouseAdapter() {
		           @Override
		           public void mouseClicked(MouseEvent e) {
		        	   if(menü.isS1Clicked() && playable) {
		        	   //Spielzug Spieler1
		        		   int gridWidth = spielfeld.getWidth() - (2 * 5); // Adjust for padding
		        		   int gridHeight = spielfeld.getHeight() - (2 * 5); // Adjust for padding

		        		   int col = menü.getAnzahlSpalten();
		        		   int row = menü.getAnzahlZeilen();

		        		   int gridSize = Math.min(gridWidth / col, gridHeight / row);

		        		   int xOffset = (spielfeld.getWidth() - (gridSize * col)) / 2;
		        		   int yOffset = (spielfeld.getHeight() - (gridSize * row)) / 2;

		        		   int mouseX = e.getX();
		        		   int mouseY = e.getY();

		        		   int c = (mouseX - xOffset) / gridSize;
		        		   int r = (mouseY - yOffset) / gridSize;
		               
		               
		               
		        		   //Spielzug Spieler 1
		        		   // Perform actions based on the clicked rectangle
		        		   if (r >= 0 && r < row && c >= 0 && c < col) {
		        			   // Rectangle at row 'r' and column 'c' is clicked
		        			   wahlfarbe=feldLogik.getFarbenIndex(felder[r][c], farbenUnique);
		        			   System.out.print(wahlfarbe);
		        		   }
		        		   
		        		   if (wahlfarbe == field[field.length-1][0].getColor() || wahlfarbe == field[0][field[0].length-1].getColor()) {
		                       return; // Exit early if wahlfarbe is equal to lastColor1 or s2.getColor()
		                   }
		        			   
		        		   
		        		   spielzug();
		        		   
		        		   
		        	   	}
		        	   
		        	   
		        	   
		           }
		       });
			
			anzeige.add(spielfeld);
		
			//Unterer Teil
			unten = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					if(startMode) {
						paintFarbenWahl(g);
						repaint();
					}
					
					
				}
			};
			
			unten.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			    	if(menü.isS1Clicked() && playable) {
				        int mouseX = e.getX();
				        int mouseY = e.getY();
				        
				        // Überprüfe, ob der Klick innerhalb des Farbengrids liegt
				        int gridBreite2 = unten.getWidth() - 10;
				        int gridHöhe2 = unten.getHeight() - 10;
				        int zeilen = 1;
				        int spalten = farbenUnique.length;
				        int gridSize2 = Math.min(gridBreite2 / spalten, gridHöhe2 / zeilen);
				        int xOffset2 = (unten.getWidth() - (gridSize2 * spalten)) / 2;
				        int yOffset2 = (unten.getHeight() - (gridSize2 * zeilen)) / 2;
				        
				        if (mouseY >= yOffset2 && mouseY < yOffset2 + gridSize2) {
				            int s = (mouseX - xOffset2) / gridSize2;
				            if (s >= 0 && s < spalten) {
				                // Der Klick liegt innerhalb des Farbengrids
				                Color auswahl = farbenUnique[s];
				                wahlfarbe = feldLogik.getFarbenIndex(auswahl, farbenUnique);
				                
				                System.out.println(wahlfarbe);
				            }
				        }
				        
				        if (wahlfarbe == field[field.length-1][0].getColor() || wahlfarbe == field[0][field[0].length-1].getColor()) {
		                       return; // Exit early if wahlfarbe is equal to lastColor1 or s2.getColor()
		                }
				        spielzug();
			    	}
			    }
			});
			
			
			unten.setBackground(Color.white);
			unten.setPreferredSize(new Dimension(480,100));
			anzeige.add(unten, BorderLayout.SOUTH);
		
		//Anzeigetafel
		//Anzeigetafel
		//Anzeigetafel
		//Anzeigetafel
		//Anzeigetafel
		//Anzeigetafel
		//Anzeigetafel
		//Anzeigetafel
			
		
		
		this.add(menü, BorderLayout.EAST);				//Add Anzeigetafel
		this.add(anzeige, BorderLayout.CENTER);			//Add Menütafel
		
		
		feldLogik = new Brett(menü.getAnzahlZeilen(), menü.getAnzahlSpalten(), menü.getAnzahlFarben());
		
		felder = feldLogik.getFelder();
		farbenUnique = feldLogik.getFarbenListe(felder);
		
		
		
		
		addComponentListener(new ComponentAdapter() {			//ComponentListener to change component size adjusted to window size
			@Override
			public void componentResized(ComponentEvent e) {
				changeMenütafelSize();
			}
		});
		
		
		
		this.setVisible(true);							//Window is visible
		
		
		
	}
	
	


//Methods
//Methods
//Methods
//Methods
//Methods
//Methods
//Methods
//Methods



	//Method to help scale Components when window size is changed
	public void changeMenütafelSize() {
		menü.setPreferredSize(new Dimension((int)(this.getWidth()*0.4), this.getHeight()));
		anzeige.setPreferredSize(new Dimension((int)(this.getWidth()*0.6), this.getHeight()));
	}
	
	public void paintGrid(Graphics g) {
		int gridBreite = spielfeld.getWidth() - (10);		//Definiere Rahmen für Grid inklusive padding
		int gridHöhe = spielfeld.getHeight() - (10);

		
		int zeilen = felder.length;
		int spalten = felder[0].length;
		
		
		int gridSize = Math.min(gridBreite/spalten, gridHöhe/zeilen);
		
		//Berechene Offset und Grid Mittig zu platzieren
		int xOffset = (spielfeld.getWidth()-(gridSize*spalten))/2;
		int yOffset = (spielfeld.getHeight()-(gridSize*zeilen))/2;
		
		//Grid Zellen malen
		for (int z = 0; z<zeilen; z++) {
			for (int s = 0; s < spalten; s++) {
				
				int x = xOffset + (s * gridSize);
				int y = yOffset + (z * gridSize);
				
				Color feldFarbe = felder[z][s];
				
				g.setColor(feldFarbe);
				
				g.fillRect(x, y, gridSize, gridSize);
			}
		}
		
		//Vertikale GridLines Malen
		g.setColor(Color.black);
		for(int s = 0; s < spalten; s++) {
			int x = xOffset + (s * gridSize);
			int y1 = yOffset;
			int y2 = yOffset + (zeilen * gridSize);
			g.drawLine(x,y1, x, y2);
		}
		//Horizontale GridLines Malen
		for(int z = 0; z < zeilen; z++) {
			int y = yOffset + (z * gridSize);
			int x1 = xOffset;
			int x2 = xOffset + (spalten * gridSize);
			g.drawLine(x1,y,x2,y);
		}
	}
	
	public void paintFarbenWahl(Graphics g) {
	    int gridBreite2 = unten.getWidth() - (10);  // Definiere Rahmen für Grid inklusive padding
	    int gridHöhe2 = unten.getHeight() - (10);
	    
	    int zeilen = 1;  // Nur eine Zeile
	    int spalten = farbenUnique.length;
	    
	    int gridSize2 = Math.min(gridBreite2 / spalten, gridHöhe2 / zeilen);
	    
	    // Berechne Offset und platziere das Grid in der Mitte
	    int xOffset2 = (unten.getWidth() - (gridSize2 * spalten)) / 2;
	    int yOffset2 = (unten.getHeight() - (gridSize2 * zeilen)) / 2;
	    
	    // Grid-Zellen malen
	    for (int s = 0; s < spalten; s++) {
	        int x = xOffset2 + (s * gridSize2);
	        int y = yOffset2;
	        
	        Color feldFarbe = farbenUnique[s]; 				 // Verwende nur die erste Zeile des Arrays
	        g.setColor(feldFarbe);
	        g.fillRect(x, y, gridSize2, gridSize2);
	        
	        
	     // Zahl zeichnen
	        g.setColor(Color.black);
	        String zahl = String.valueOf(s + 1); // Zahl berechnen (1-basiert)
	        int zahlX = x + (gridSize2 / 2) - (g.getFontMetrics().stringWidth(zahl) / 2);
	        int zahlY = y + (gridSize2 / 2) + (g.getFontMetrics().getAscent() / 2);
	        g.drawString(zahl, zahlX, zahlY);
	    }
	    
	    // Vertikale Grid-Linien malen
	    g.setColor(Color.black);
	    for (int s = 0; s <= spalten; s++) {
	        int x = xOffset2 + (s * gridSize2);
	        int y1 = yOffset2;
	        int y2 = yOffset2 + gridSize2;
	        g.drawLine(x, y1, x, y2);
	    }
	    // Horizontale Grid-Linie malen
	    int y = yOffset2 + gridSize2;
	    int x1 = xOffset2;
	    int x2 = xOffset2 + (spalten * gridSize2);
	    g.drawLine(x1, y, x2, y);
	}
	
	
	public void updateSpielfeld() {
	    if (startMode) {
	        spielfeld.repaint(); // Neuzeichnen des Spielfelds
	    }
	}
	public void updateFarbauswahl() {
		if (startMode) {
		    unten.repaint(); // Ruft die paintFarbenWahl-Methode erneut auf, um das Grid neu zu zeichnen
		}
	    
	}
	public Color [] updateFarbenListe(Color [][] felder) {
		List<Color> liste = new ArrayList<Color>();
		for (Color[] row : felder) {
            for (Color color : row) {
                if (!liste.contains(color)) {
                    liste.add(color);
                }
            }
        }
		
		Color[] farbenListe = liste.toArray(new Color[liste.size()]);
		return farbenListe;
	}
	
	
	

	public void spielzug() {
		if(menü.isS1Clicked()) {
			feldLogik.playerTurn(wahlfarbe, farbenUnique, felder, field, s1, farbenUnique);
			oldScoreOne = scoreOne;
			   scoreOne = feldLogik.getScore1();
			   score1.setText("S1: "+scoreOne);
			   spielfeld.repaint();
			   menü.setS1Clicked(false);
			   s1.setColor(wahlfarbe);
			   turn.setText("PC ist dran.");
			   updateSpielfeld();
			   if(oldScoreOne == scoreOne) {
				   countScore++;
			   } else {
				   countScore = 0;
			   }
			  // System.out.println(countScore);
			   
			   if(checkEnd()) {
				   start.doClick();
	  			  feldLogik.doEnd();
	  		   }
			   
			   Thread pcThread = new Thread(() -> {
				   	// Create a timer with a delay of one second (1000 milliseconds)
			        Timer timer = new Timer(1000, new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                // Perform the PC's turn
			                feldLogik.pcTurn(felder, farbenUnique, field, menü.getStrategie(), s2, lastColor2, s1.getColor());
			                lastColor2 = feldLogik.getlC2();
			                oldScoreTwo = scoreTwo;
			                scoreTwo = feldLogik.getScore2();
			                score2.setText("S2: "+scoreTwo);
			                s2.setColor(lastColor2);
			                menü.setS1Clicked(true);
			                turn.setText("S1 ist dran.");
			                updateSpielfeld();
			                if (oldScoreTwo == scoreTwo) {
			                	countScore++;
			                } else {
			                	countScore = 0;
			                }
			           //     System.out.print(countScore);
			     		   
			     		   if(checkEnd()) {
			     			  start.doClick();
			     			  feldLogik.doEnd();
			     		   }
			            }
			        });
	
			        // Start the timer
			        timer.setRepeats(false); // Only execute the task once
			        timer.start();
		        
			   });
			   
			   pcThread.start();
		} else {
			Thread pcThread = new Thread(() -> {
			
				// Create a timer with a delay of one second (1000 milliseconds)
		        Timer timer = new Timer(1000, new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                // Perform the PC's turn
		                feldLogik.pcTurn(felder, farbenUnique, field, menü.getStrategie(), s2, lastColor2, s1.getColor());
		                lastColor2 = feldLogik.getlC2();
		                oldScoreTwo = scoreTwo;
		                scoreTwo = feldLogik.getScore2();
		                score2.setText("S2: "+scoreTwo);
		                s2.setColor(lastColor2);
		                menü.setS1Clicked(true);
		                turn.setText("S1 ist dran.");
		                updateSpielfeld();
		                if (oldScoreTwo == scoreTwo) {
		                	countScore++;
		                } else {
		                	countScore = 0;
		                }
		           //     System.out.print(countScore);
		     		   
		     		   if(checkEnd()) {
		     			  start.doClick();
		     			  feldLogik.doEnd();
		     		   }
		            }
		        });
				
		        // Start the timer
		        timer.setRepeats(false); // Only execute the task once
		        timer.start();
			});
			
			pcThread.start();
	        
		}
		
		   
		   
	   	}
	
	public boolean checkEnd() {
		int color1 = field[field.length-1][0].getSpieler().getNr();
	    int color2 = field[0][field[0].length-1].getSpieler().getNr();
	    
	    
	    
	    for (Field[] row : field) {
	        for ( Field field : row) {
	            if (field.getColor() == 0 || (color1 != field.getSpieler().getNr() && color2 != field.getSpieler().getNr()) && countScore < 4) {
	                return false;
	            }
	        }
	    }
	    
	    
	    
	    return true;
	    
	    
	}
	public int higherScore() {
		if(feldLogik.getScore1() > feldLogik.getScore2()) {
			return 1;
		} else if (feldLogik.getScore1() < feldLogik.getScore2()) {
			return 2;
		} else {
			return 0;
		}
	}
}
