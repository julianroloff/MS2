package logic;

/**
 * Julian Roloff, 7352137
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Brett {
	private int zeilen;
	private int spalten;
	private int farben;
	private Color [][] felder;
	private Field [][] field;
	private int score1 = 1;
	private int score2 = 1;
	private int lC2;
	
	
	public Brett(int zeilen, int spalten, int farben) {
		this.zeilen = zeilen; 
		this.spalten = spalten; 
		this.farben = farben;  
		this.felder = new Color[zeilen][spalten];
		felderErstellen(felder, farben, zeilen, spalten);
		field = new Field[zeilen][spalten];
		
	}
	
	public void felderErstellen(Color[][] felder, int farben, int zeile, int spalte) {
	    Random random = new Random();

	    // Setze die Farbe für das Feld links unten
	    Color farbeUntenLinks = getZufälligeFarbe(random.nextInt(farben));
	    

	    felder[zeile - 1][0] = farbeUntenLinks;

	    // Setze die Farbe für das Feld rechts oben
	    Color farbeObenRechts;
	    do {
	        farbeObenRechts = getZufälligeFarbe(random.nextInt(farben));
	    } while (farbeObenRechts == farbeUntenLinks);

	    felder[0][spalte - 1] = farbeObenRechts;

	    // Erzeuge die restlichen Felder
	    for (int i = 0; i < zeile; i++) {
	        for (int j = 0; j < spalte; j++) {
	            if ((i == zeile - 1 && j == 0) || (i == 0 && j == spalte - 1)) {
	                continue; // Überspringe die Felder links unten und rechts oben
	            }

	            Color zufälligeFarbe;
	            do {
	                int indexZufälligeFarbe = random.nextInt(farben);
	                zufälligeFarbe = getZufälligeFarbe(indexZufälligeFarbe);
	            } while (hatGleicheNachbarFarbe(felder, i, j, zufälligeFarbe));

	            felder[i][j] = zufälligeFarbe;
	        }
	    }
	}
	
	public boolean hatGleicheNachbarFarbe(Color[][] squares, int row, int col, Color color) {
	    int numRows = squares.length;
	    int numCols = squares[0].length;

	    if (row > 0 && squares[row - 1][col] == color) { // Check top neighbor
	        return true;
	    }
	    if (row < numRows - 1 && squares[row + 1][col] == color) { // Check bottom neighbor
	        return true;
	    }
	    if (col > 0 && squares[row][col - 1] == color) { // Check left neighbor
	        return true;
	    }
	    if (col < numCols - 1 && squares[row][col + 1] == color) { // Check right neighbor
	        return true;
	    }

	    return false;
	}
	
	public Color getZufälligeFarbe(int indexZufälligeFarbe) {
		Color[] farben = {
	            Color.RED,
	            Color.GREEN,
	            Color.DARK_GRAY,
	            Color.BLUE,
	            Color.CYAN,
	            Color.MAGENTA,
	            Color.ORANGE,
	            Color.PINK,
	            Color.GRAY
	        };
		
		
        Random rand = new Random();
        int zufälligerIndex = rand.nextInt(farben.length);
        
        // Überprüfe, ob der gegebene Index innerhalb des gültigen Bereichs des Arrays liegt
        if (indexZufälligeFarbe >= 0 && indexZufälligeFarbe < farben.length) {
            zufälligerIndex = indexZufälligeFarbe;
        }

        return farben[zufälligerIndex];
    }
	
	public Color[] getFarbenListe(Color [][] felder) {
		
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
	
	
	public Color[][] getFelder() {
		return felder;
	}

	public void setFelder(Color[][] felder) {
		this.felder = felder;
	}
	public int getFarbenIndex(Color farbe, Color[] farbenListe) {
	    for (int i = 0; i < farbenListe.length; i++) {
	        if (farbenListe[i] != null && farbenListe[i].equals(farbe)) {
	            return i+1;
	        }
	    }
	    return -1; // Farbe nicht gefunden
	}
	
	public Field[][] createFieldArray(Color[][] felder) {
	    int rows = felder.length;
	    int cols = felder[0].length;

	    Field[][] fieldArray = new Field[rows][cols];

	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            Color color = felder[i][j];
	            Color [] liste = getFarbenListe(felder);
	            int index = getFarbenIndex(color, liste);
	            Field field = new Field(i, j, index);
	            fieldArray[i][j] = field;
	        }
	    }
	    
	    

	    return fieldArray;
	}
	
	public boolean hatBenachbarteFelderSpieler1(Field[][] fieldObjects, int zeile, int spalte, int wahlfarbe) {
	    int farbe = wahlfarbe;
	    int spielerNr = 1;

	    // Überprüfe ob ein benachbartes Feld die Farbe und SpielerNr hat
	    if (zeile > 0 && fieldObjects[zeile - 1][spalte].getColor() == farbe && fieldObjects[zeile - 1][spalte].getSpieler().getNr() == spielerNr) {
	        return true; // Feld darüber hat die gleiche Farbe und SpielerNr
	    }
	    if (zeile < fieldObjects.length - 1 && fieldObjects[zeile + 1][spalte].getColor() == farbe && fieldObjects[zeile + 1][spalte].getSpieler().getNr() == spielerNr) {
	        return true; // Feld darunter hat die gleiche Farbe und SpielerNr
	    }
	    if (spalte > 0 && fieldObjects[zeile][spalte - 1].getColor() == farbe && fieldObjects[zeile][spalte - 1].getSpieler().getNr() == spielerNr) {
	        return true; // Feld links hat die gleiche Farbe und SpielerNr
	    }
	    if (spalte < fieldObjects[0].length - 1 && fieldObjects[zeile][spalte + 1].getColor() == farbe && fieldObjects[zeile][spalte + 1].getSpieler().getNr() == spielerNr) {
	        return true; // Feld rechts hat die gleiche Farbe und SpielerNr
	    }

	    return false; // Keine benachbarten Felder mit gleicher Farbe und SpielerNr gefunden
	}
	
	
	public Field[][] findConnectedFields(Color color, Color [][] felder, Field [][] field, Spieler s) {
	    
	    Spieler s1 = s;
	    
	    if (s1.getNr()==1) {
	    	for (int i = 0; i< field.length; i++) {
		    	for ( int j = 0; j<field[0].length; j++) {
		    		if (field[i][j].getSpieler().getNr() == 1) {
		    			int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		    			for (int[] neighbor : neighbors) {
		    				int newRow = i + neighbor[0];
		    	            int newCol = j + neighbor[1];
		    	            
		    	            
		    	            if (isValidField(newRow, newCol)) {
		    	            	if (felder[newRow][newCol].equals(color)) {
		    	            		field[newRow][newCol].setSpecificSpieler(s1);
		    	            	}
		    	            }
		    			}
		    				
		    			
		    		}
		    	}
		    }
	    
	    
	    } else {
	    	for (int i = 0; i< field.length; i++) {
		    	for ( int j = 0; j<field[0].length; j++) {
		    		if (field[i][j].getSpieler().getNr() == 2) {
		    			int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		    			for (int[] neighbor : neighbors) {
		    				int newRow = i + neighbor[0];
		    	            int newCol = j + neighbor[1];
		    	            
		    	            
		    	            if (isValidField(newRow, newCol)) {
		    	            	if (felder[newRow][newCol].equals(color)) {
		    	            		field[newRow][newCol].setSpecificSpieler(s1);
		    	            	}
		    	            }
		    			}
		    				
		    			
		    		}
		    	}
		    }
	    	
	    }
	    
	    return field;
	}
	private boolean isValidField(int row, int col) {
	    int numRows = field.length;
	    int numCols = field[0].length;
	    
	    // Überprüfen, ob die Zeile und Spalte innerhalb der Grenzen des Spielfelds liegen
	    return row >= 0 && row < numRows && col >= 0 && col < numCols;
	}
	
	public Color getFarbeFromIndex(int index, Color[] farbenListe) {
	    if (index >= 1 && index <= farbenListe.length) {
	        return farbenListe[index - 1];
	    }
	    return null; // Index ungültig oder Farbe nicht gefunden
	}
	
	public void updateFelderClick(Field[][] field, Color[][] felder, Spieler s1, Color newC, Color [] farbenListe) {
		score1 = 0;
	    for (int i = 0; i < field.length; i++) {
	        for (int j = 0; j < field[0].length; j++) {
	            if (field[i][j].getSpieler() == s1) {
	                felder[i][j] = newC;
	                field[i][j].setColor(getFarbenIndex(newC, farbenListe));
	                score1++;
	            }
	        }
	    }
	}
	public void updateFelderClickPc(Field[][] field, Color[][] felder, Spieler s1, Color newC, Color [] farbenListe) {
		score2 = 0;
	    for (int i = 0; i < field.length; i++) {
	        for (int j = 0; j < field[0].length; j++) {
	            if (field[i][j].getSpieler() == s1) {
	                felder[i][j] = newC;
	                field[i][j].setColor(getFarbenIndex(newC, farbenListe));
	                score2++;
	            }
	        }
	    }
	}

	public int getScore1() {
		return score1;
	}
	public int getScore2() {
		return score2;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}
	
	
	public Color chooseColorStagnation(Color [][] felder, Color [] farbenListe, Field [][] field, int lastColor2, int colorS1, Spieler s) {
		int minIncrease = Integer.MAX_VALUE;
	    Color chosenColor = null;
	    Color lc = getFarbeFromIndex(lastColor2, farbenListe);
	    Color oc = getFarbeFromIndex(colorS1, farbenListe);
	    for (Color color : farbenListe) {
	        if (color.equals(lc) || color.equals(oc)) {
	            continue;
	        }
	        
	        int increase = getIncreaseForColor(felder, color, field);
	        if (increase < minIncrease) {
	            minIncrease = increase;
	            chosenColor = color;
	        } else if (increase == minIncrease && getFarbenIndex(color, farbenListe) < getFarbenIndex(chosenColor, farbenListe)) {
	            // In case of a tie, choose the smallest color based on RGB value
	            chosenColor = color;
	        }
	    }
	    
	    return chosenColor;
	}
	
	public Color chooseColorGreedy(Color[][] felder, Color[] farbenListe, Field[][] field, int lastColor2, int colorS1, Spieler s) {
	    int maxIncrease = Integer.MIN_VALUE;
	    Color chosenColor = null;
	    Color lc = getFarbeFromIndex(lastColor2, farbenListe);
	    Color oc = getFarbeFromIndex(colorS1, farbenListe);

	    for (Color color : farbenListe) {
	        if (color.equals(lc) || color.equals(oc)) {
	            continue;
	        }

	        int increase = getIncreaseForColor(felder, color, field);
	        if (increase > maxIncrease) {
	            maxIncrease = increase;
	            chosenColor = color;
	        } else if (increase == maxIncrease && getFarbenIndex(color, farbenListe) < getFarbenIndex(chosenColor, farbenListe)) {
	            // In case of a tie, choose the smallest color based on RGB value
	            chosenColor = color;
	        }
	    }

	    return chosenColor;
	}
	
	public Color chooseColorBlocking(Color[][] felder, Color[] farbenListe, Field[][] field, int lastColor2, int colorS1, Spieler s) {
	    int maxAreaIncrease = Integer.MIN_VALUE;
	    Color chosenColor = null;
	    Color lc = getFarbeFromIndex(lastColor2, farbenListe);
	    Color oc = getFarbeFromIndex(colorS1, farbenListe);

	    for (Color color : farbenListe) {
	        if (color.equals(lc) || color.equals(oc)) {
	            continue;
	        }

	        int areaIncrease = getAreaIncreaseForColorBlocking(felder, color, field);
	        if (areaIncrease > maxAreaIncrease) {
	            maxAreaIncrease = areaIncrease;
	            chosenColor = color;
	        } else if (areaIncrease == maxAreaIncrease && getFarbenIndex(color, farbenListe) < getFarbenIndex(chosenColor, farbenListe)) {
	            // In case of a tie, choose the smallest color based on RGB value
	            chosenColor = color;
	        }
	    }

	    return chosenColor;
	}
	
	public int getAreaIncreaseForColorBlocking(Color [][]felder,Color color,Field [][] field) {
		int increase = 0;
	    
	    for (int i = 0; i< field.length; i++) {
	    	for ( int j = 0; j<field[0].length; j++) {
	    		if (field[i][j].getSpieler().getNr() == 1) {
	    			int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	    			for (int[] neighbor : neighbors) {
	    				int newRow = i + neighbor[0];
	    	            int newCol = j + neighbor[1];
	    	            
	    	            
	    	            if (isValidField(newRow, newCol)) {
	    	            	if (felder[newRow][newCol].equals(color)) {
	    	            		increase++;
	    	            	}
	    	            }
	    			}
	    				
	    			
	    		}
	    	}
	    }
	    return increase;
	}
	
	public int getIncreaseForColor(Color [][] felder, Color color, Field [][] field ) {
		
	    int increase = 0;
	    
	    for (int i = 0; i< field.length; i++) {
	    	for ( int j = 0; j<field[0].length; j++) {
	    		if (field[i][j].getSpieler().getNr() == 2) {
	    			int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	    			for (int[] neighbor : neighbors) {
	    				int newRow = i + neighbor[0];
	    	            int newCol = j + neighbor[1];
	    	            
	    	            
	    	            if (isValidField(newRow, newCol)) {
	    	            	if (felder[newRow][newCol].equals(color)) {
	    	            		increase++;
	    	            	}
	    	            }
	    			}
	    				
	    			
	    		}
	    	}
	    }
	    return increase;
	}
	
	public void pcTurn(Color [][] felder, Color [] farbenListe, Field [][] field, String strat, Spieler s, int lastColor2, int colorS1) {
		Color c;
		if(strat == "Stagnation") {
			c = chooseColorStagnation(felder, farbenListe, field, lastColor2, colorS1, s);
			lC2 = getFarbenIndex(c,farbenListe);
			field = findConnectedFields(c, felder, field, s);
			updateFelderClickPc(field, felder, s, c, farbenListe);
			
			
		} else if(strat == "Greedy") {
			c = chooseColorGreedy(felder, farbenListe, field, lastColor2, colorS1, s);
			lC2 = getFarbenIndex(c, farbenListe);
			field = findConnectedFields(c, felder, field, s);
			updateFelderClickPc(field, felder, s, c, farbenListe);
		} else {
			c=chooseColorBlocking(felder, farbenListe, field, lastColor2, colorS1, s);
			lC2 = getFarbenIndex(c, farbenListe);
			field = findConnectedFields(c, felder, field, s);
			updateFelderClickPc(field, felder, s, c, farbenListe);
			
		}
		
		
	}
	
	public void playerTurn(int wahlfarbe, Color [] farbenUnique, Color [][] felder, Field [][] field, Spieler s, Color [] farbenListe) {
			Color newC = getFarbeFromIndex(wahlfarbe, farbenUnique);
		   field = findConnectedFields(newC, felder, field,s);
		   updateFelderClick(field, felder, s, newC, farbenListe);
		   
	}
	
	public int getlC2() {
		return lC2;
	}
	
	
	
	public void doEnd() {
		JLabel winner = new JLabel();
		   JFrame ende = new JFrame();
		   ende.setLayout(new BorderLayout());
		   ende.setSize(200,200);
		   ende.setLocationRelativeTo(null);
		   ende.setVisible(true);
		   if (getScore1()> getScore2()) {
			   winner = new JLabel("Du hast gewonnen." );
		   } else if (getScore2()>getScore1()) {
			   winner = new JLabel("Der Gewinner ist PC.");
		   } else {
			   winner = new JLabel("Unentschieden!");
		   }
		   
		   ende.add(winner, BorderLayout.CENTER);
	}
}
