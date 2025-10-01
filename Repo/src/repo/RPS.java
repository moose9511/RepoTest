package repo;

import java.awt.Image;

import javax.swing.ImageIcon;

public class RPS extends ImageIcon {
	public static final int RADIUS = 10; // size of the square hit box
	
	private String type; // name of the object
	private int xPos; // x coordinate in middle of object
	private int yPos; // y coordinate in middle of object
	private int[] direction = new int[2]; // x and y direction
	
	public RPS (String type, int x, int y, int[] direction) {
		
		// checks if type is a rock, paper or scissors, complains if it's invalid
		if(type.equals("rock")) {
			this.type = "rock";
			ImageIcon temp = new ImageIcon(getClass().getResource("/imgs/rock.jpg"));
			super.setImage(temp.getImage());
		} else if (type.equals("paper")) {
			this.type = "paper";
			ImageIcon temp = new ImageIcon(getClass().getResource("/imgs/paper.jpg"));
			super.setImage(temp.getImage());
		} else if (type.equals("scissors")) {
			this.type = "scissors";
			ImageIcon temp = new ImageIcon(getClass().getResource("/imgs/scissors.jpg"));
			super.setImage(temp.getImage());
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
	
 }
