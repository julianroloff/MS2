package gui;

/**
 * Julian Roloff, 7352137
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Spielbrett extends JPanel{
	
	private int zeilen;
	private int spalten;
	private int farben;
	private JPanel [][] felderArray;
	private JPanel feld;
	private int feldGröße = 20;
	
	public Spielbrett(int zeilen, int spalten, int farben) {		//Erstelle JPanel für das Spielfeld
										
		setPreferredSize(new Dimension(spalten * feldGröße, zeilen * feldGröße));
		setBorder(BorderFactory.createLineBorder(Color.yellow));
		
		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Zeichne das Schachbrett
        for (int row = 0; row < zeilen; row++) {
            for (int col = 0; col < spalten; col++) {
                int x = col * feldGröße;
                int y = row * feldGröße;

                if ((row + col) % 2 == 0) {
                    g.setColor(Color.yellow);
                } else {
                    g.setColor(Color.blue);
                }

                g.fillRect(x, y, feldGröße, feldGröße);
            }
        }
    }
	
	
}
