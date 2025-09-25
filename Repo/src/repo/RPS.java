package repo;

import javax.swing.Icon;
import javax.swing.JFrame;

public class RPS {
	public static final int SIZE = 10; // size of the square hitbox
	
	private String type; // name of the object
	private Icon icon;
	private int xPos; // x coordinate
	private int yPos; // y coordinate
	private int degree; // angle of the direction the object moves
	
	public RPS (String type, int x, int y, int direction) {
		
		// checks if type is a rock, paper or scissors, complains if it's invalid
		if(type.equals("rock")) {
			this.type = "rock";
		} else if (type.equals("paper")) {
			this.type = "paper";
		} else if (type.equals("scissors")) {
			this.type = "scissors";
		} else {
			System.out.println("Invalid type for RPS");
			return;
		}
		
		this.type = type;
		xPos = x;
		yPos = y;
		degree = direction;
	}
	
	// return methods
	public int getX() {
		return xPos;
	}
	public int getY() {
		return yPos;
	}
 	public int[] getPos() {
		return new int[] {xPos, yPos};
	}
	public String getType() {
		return type;
	}
	public int getDeg() {
		return degree;
	}
	public void setDeg(int addedDeg) {
		degree += addedDeg; // add degree
		degree %= 360; // makes sure degree doesn't go over 360 to stay within 360 degrees
	}
 }
