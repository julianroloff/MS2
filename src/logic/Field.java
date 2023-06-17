package logic;

/**
 * Julian Roloff, 7352137
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * Siehe Hinweise zu Umgang mit dem Repository auf Aufgabenblatt.  
 */

public class Field {

	private int color;
	private int row;
	private int col;
	private Spieler spieler;
	
	
	public Field(int row, int col, int color) {
		
	this.row = row;
	this.col = col;
	this.color = color;
	this.spieler = new Spieler(0,0,0);
	
	}

	
	
	
	

	






	
	
	
	
	
	
	
	
	
	
	/*
	 * Getter und Setter
	 */
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	public Spieler getSpieler() {
		return spieler;
	}
	public void setSpieler(int n, int s, int color) {
		this.spieler = new Spieler(n, s, color);
	}
	public void setSpecificSpieler(Spieler s) {
		this.spieler = s;
	}
	

}
