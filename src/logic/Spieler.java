package logic;

/**
 * Julian Roloff, 7352137
 */

public class Spieler {
	
	private int nr;
	private int score;
	private int color;
	
	public Spieler(int nr, int score, int color) {
		this.nr = nr;
		this.score = score;
		this.color = color;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
