package testing;

/**
 * Julian Roloff, 7352137
 */

/*
 * Siehe Hinweise auf dem Aufgabenblatt. 
 */

import logic.Field;
import logic.Spieler;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import logic.Brett;

public class Testing {

	private Field[][] board;


	public Testing(Field[][] initBoard) {
		this.board = initBoard;
		
		
	}

	public boolean isStartklar() {
		int t = 6; // Annahme: getAnzahlFarben() gibt die Anzahl der Farben im Spielbrett zurück
        int rows = board.length;
        int cols = board[0].length;

        // Bedingung (1): Überprüfen, ob die Nachbarn jedes Feldes eine andere Farbe haben
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Field currentField = board[i][j];
                if (hasSameColorNeighbor(currentField, i, j)) {
                    return false; // Bedingung (1) ist nicht erfüllt, Spielbrett ist nicht startklar
                }
            }
        }

        // Bedingung (2): Überprüfen, ob es t viele Farben im Spielbrett gibt
        int uniqueColors = countUniqueColors();
        if (uniqueColors != t) {
            return false; // Bedingung (2) ist nicht erfüllt, Spielbrett ist nicht startklar
        }

        // Bedingung (3): Überprüfen, ob die Ecken unterschiedliche Farben haben
        Field bottomLeft = board[rows - 1][0];
        Field topRight = board[0][cols - 1];
        if (bottomLeft.getColor() == topRight.getColor()) {
            return false; // Bedingung (3) ist nicht erfüllt, Spielbrett ist nicht startklar
        }

        // Alle Bedingungen sind erfüllt, Spielbrett ist startklar
        return true;

		
	}

	public boolean isEndConfig() {
		int s1Color = board[board.length-1][0].getColor(); // Annahme: getS1Color() gibt die Farbe von S1 zurück
        int s2Color = board[0][board[0].length-1].getColor(); // Annahme: getS2Color() gibt die Farbe von S2 zurück

        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Field currentField = board[i][j];
                int fieldColor = currentField.getColor();
                if (!(fieldColor == s1Color) && !(fieldColor == s2Color)) {
                    return false; // Das Feld hat weder die Farbe von S1 noch von S2, Spielbrett ist nicht in einer Endkonfiguration
                }
            }
        }

        // Alle Felder haben entweder die Farbe von S1 oder von S2, Spielbrett ist in einer Endkonfiguration
        return true;
		
	}

	
	public int testStrategy01() {
		int minIncrease = Integer.MAX_VALUE;
	    int chosenColor = -1;
	    int lc = board[board.length-1][0].getColor();
	    int oc = board[0][board[0].length-1].getColor();
	    for (int i = 0; i < 6; i++) {
	    	int color = i;
            if (i == lc || i == oc) {
                continue;
            }
	        
	        int increase = getIncreaseForColor(color, board);
	        if (increase < minIncrease) {
	            minIncrease = increase;
	            chosenColor = color;
	        } else if (increase ==  minIncrease && i < chosenColor ) {
	            // In case of a tie, choose the smallest color based on RGB value
	            chosenColor = color;
	        }
	    }
	    
	    if (chosenColor == -1) {
            throw new IllegalStateException("Es wurde keine Farbe ausgewählt.");
        }

        return chosenColor;
	}

	public int testStrategy02() {
		
        int lastColor2 = board[board.length-1][0].getColor(); // Annahme: getLastColor2() gibt den Wert von lastColor2 zurück
        int colorS1 = board[0][board[0].length-1].getColor(); // Annahme: getColorS1() gibt den Wert von colorS1 zurück
        

        int maxIncrease = Integer.MIN_VALUE;
        int chosenColorIndex = -1;
        int lc = lastColor2;
        int oc = colorS1;

        for (int i = 0; i < 6; i++) {
            int color = i;
            if (i == lc || i == oc) {
                continue;
            }

            int increase = getIncreaseForColor(color, board);
            if (increase > maxIncrease) {
                maxIncrease = increase;
                chosenColorIndex = i;
            } else if (increase == maxIncrease &&  i < chosenColorIndex) {
                // Im Falle eines Gleichstands wird die kleinste Farbe basierend auf dem RGB-Wert gewählt
                chosenColorIndex = i;
            }
        }

        if (chosenColorIndex == -1) {
            throw new IllegalStateException("Es wurde keine Farbe ausgewählt.");
        }

        return chosenColorIndex;
	}

	public int testStrategy03() {
		int maxAreaIncrease = Integer.MIN_VALUE;
	    int chosenColor = -1;
	    int lc = board[board.length-1][0].getColor();
	    int oc = board[0][board[0].length-1].getColor();

	    for (int i = 0; i < 6; i++) {
	    	int color = i;
            if (i == lc || i == oc) {
                continue;
            }

	        int areaIncrease = getAreaIncreaseForColorBlocking(color, board);
	        if (areaIncrease > maxAreaIncrease) {
	            maxAreaIncrease = areaIncrease;
	            chosenColor = color;
	        } else if (areaIncrease == maxAreaIncrease && color < chosenColor) {
	            // In case of a tie, choose the smallest color based on RGB value
	            chosenColor = color;
	        }
	    }

	    return chosenColor;
	}

	
	public boolean toBoard(Field[][] anotherBoard, int moves) {
		
		return false;
	}

	public int minMoves(int row, int col) { 
	
        // Überprüfen, ob das Field-Objekt bereits eingenommen ist
        if (board[row][col].getSpieler().getNr() == 1) {
            return 0;
        }

        // Anzahl der benötigten Züge
        int moves = 0;
        
        //Farbe mit der S1 beginnt
        int farbe = board[board.length-1][0].getColor();
        
        // Schleife, um die Farben zyklisch aufsteigend zu wählen und das Field-Objekt einzunehmen
        while (board[row][col].getSpieler().getNr() != 1) {
        	if(farbe > 6) {
        		farbe = 1;
        	}
            
            // Aktualisieren des Startfarbindex für den nächsten Zug
            board = findConnectedFields(farbe, board);
            
            // Nächste Position von S1 berechnen
            updateFelderClick(board, farbe);

            // Inkrementieren der Anzahl der benötigten Züge
            moves++;
            // Aufsteigendes und zyklisches Durchgehen der Farben
            farbe++;
        }

        return moves;
	}

	public int minMovesFull() {
		// Überprüfen, ob das Field-Objekt bereits eingenommen ist
        if (checkEnd()) {
            return 0;
        }

        // Anzahl der benötigten Züge
        int moves = 0;
        
        //Farbe mit der S1 beginnt
        int farbe = board[board.length-1][0].getColor();
        
        // Schleife, um die Farben zyklisch aufsteigend zu wählen und das Field-Objekt einzunehmen
        while (!checkEnd()) {
        	if(farbe > 6) {
        		farbe = 1;
        	}
            
            // Aktualisieren des Startfarbindex für den nächsten Zug
            board = findConnectedFields(farbe, board);
            
            // Nächste Position von S1 berechnen
            updateFelderClick(board, farbe);

            // Inkrementieren der Anzahl der benötigten Züge
            moves++;
            // Aufsteigendes und zyklisches Durchgehen der Farben
            farbe++;
        }

        return moves;
		
	}

	
	
	
	/*
	 * Getter und Setter
	 */
	public Field[][] getBoard() {
		return board;
	}

	public void setBoard(Field[][] board) {
		this.board = board;
	}
	
	
	
	
	
	
	
	
	/**
	 *  Hilfsmethoden
	 */
	public int getBoardZeilen() {
		return board.length;
	}
	public int getBoardSpalten() {
		return board[0].length;
	}
	
	private boolean hasSameColorNeighbor(Field field, int row, int col) {
        int rows = board.length;
        int cols = board[0].length;
        int color = field.getColor();

        // Überprüfen der Nachbarn oben, unten, links und rechts
        if (row > 0 && board[row - 1][col].getColor() == color) {
            return true;
        }
        if (row < rows - 1 && board[row + 1][col].getColor() == color) {
            return true;
        }
        if (col > 0 && board[row][col - 1].getColor() == color) {
            return true;
        }
        if (col < cols - 1 && board[row][col + 1].getColor() == color) {
            return true;
        }

        return false;
    }
	private int countUniqueColors() {
        Set<Integer> uniqueColors = new HashSet<>();

        for (Field[] row : board) {
            for (Field field : row) {
                uniqueColors.add(field.getColor());
            }
        }

        return uniqueColors.size();
    }
	
	public int getIncreaseForColor(int color, Field [][] field ) {
		
	    int increase = 0;
	    
	    for (int i = 0; i< field.length; i++) {
	    	for ( int j = 0; j<field[0].length; j++) {
	    		if (field[i][j].getSpieler().getNr() == 2) {
	    			int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	    			for (int[] neighbor : neighbors) {
	    				int newRow = i + neighbor[0];
	    	            int newCol = j + neighbor[1];
	    	            
	    	            
	    	            if (isValidField(newRow, newCol)) {
	    	            	if (field[newRow][newCol].getColor() == color) {
	    	            		increase++;
	    	            	}
	    	            }
	    			}
	    				
	    			
	    		}
	    	}
	    }
	    return increase;
	}
	public int getAreaIncreaseForColorBlocking(int color,Field [][] field) {
		int increase = 0;
	    
	    for (int i = 0; i< field.length; i++) {
	    	for ( int j = 0; j<field[0].length; j++) {
	    		if (field[i][j].getSpieler().getNr() == 1) {
	    			int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	    			for (int[] neighbor : neighbors) {
	    				int newRow = i + neighbor[0];
	    	            int newCol = j + neighbor[1];
	    	            
	    	            
	    	            if (isValidField(newRow, newCol)) {
	    	            	if (field[newRow][newCol].getColor() == color) {
	    	            		increase++;
	    	            	}
	    	            }
	    			}
	    				
	    			
	    		}
	    	}
	    }
	    return increase;
	}
	
	
	private boolean isValidField(int row, int col) {
	    int numRows = board.length;
	    int numCols = board[0].length;
	    
	    // Überprüfen, ob die Zeile und Spalte innerhalb der Grenzen des Spielfelds liegen
	    return row >= 0 && row < numRows && col >= 0 && col < numCols;
	}
	
	
	public void updateFelderClick(Field[][] field, int newC) {
	    for (int i = 0; i < field.length; i++) {
	        for (int j = 0; j < field[0].length; j++) {
	            if (field[i][j].getSpieler().getNr() == 1) {
	                field[i][j].setColor(newC);
	                
	            }
	        }
	    }
	}
	public Field[][] findConnectedFields(int color, Field [][] field) {
	    
	    	for (int i = 0; i< field.length; i++) {
		    	for ( int j = 0; j<field[0].length; j++) {
		    		if (field[i][j].getSpieler().getNr() == 1) {
		    			int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		    			for (int[] neighbor : neighbors) {
		    				int newRow = i + neighbor[0];
		    	            int newCol = j + neighbor[1];
		    	            
		    	            
		    	            if (isValidField(newRow, newCol)) {
		    	            	if (field[newRow][newCol].getColor() == color) {
		    	            		field[newRow][newCol].setSpieler(1,0,0);
		    	            	}
		    	            }
		    			}
		    				
		    			
		    		}
		    	}
		    }
	    
	    return field;
	}
	public boolean checkEnd() {
		int color1 = board[board.length-1][0].getSpieler().getNr();
	    
	    
	    
	    
	    for (Field[] row : board) {
	        for ( Field field : row) {
	            if (field.getColor() == 0 || color1 != field.getSpieler().getNr()) {
	                return false;
	            }
	        }
	    }
	    
	    
	    
	    return true;
	    
	    
	}

}
