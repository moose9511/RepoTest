package repo;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class RPS implements Icon {
	public static final int SIZE = 10; // size of the square hitbox
	
	private String type; // name of the object
	private int xPos; // x coordinate
	private int yPos; // y coordinate
	private int[] direction = new int[2]; // x and y direction
	
	public RPS (String type, int x, int y, int[] direction) {
		
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
		this.direction = direction;
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
	public int[] getDeg() {
		return direction;
	}
	public void changeX() {
		direction[0] *= -1;
	}
	public void changeY() {
		direction[1] *= -1;
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
 }
