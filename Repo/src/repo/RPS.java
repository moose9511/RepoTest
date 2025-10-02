package repo;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RPS extends JLabel {
	public static final int RADIUS = 10; // radius of the circular hit box
	public static final int DIAMETER = RADIUS*2; // diameter of the circular hit box
	private String type; // name of the object
	private int xPos; // x coordinate in middle of object
	private int yPos; // y coordinate in middle of object
	private int[] direction = new int[2]; // x and y direction
	
	public RPS (String type, int x, int y, int[] direction) {
		super(type);
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
	
	// get methods
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
	public int[] getDir() {
		return direction;
	}
	
	// set methods
    public void setType(String type){
        if(type.equals("rock") || type.equals("paper") || type.equals("scissors")){
            this.type = type;
            this.setIcon(new ImageIcon("/repo/imgs/" + type + ".jpg"));
        }
    }
    public void setDir(int[] d) {
		direction = d;
	}
    public void setX(int num){
        xPos = num;
    }
    public void setY(int num){
        yPos = num;
    }
    public void setPos(int[] pos) {
    	xPos = pos[0];
    	yPos = pos[1];
    }
    
    // reverse direction method
	public void changeX() {
		direction[0] *= -1;
	}
	public void changeY() {
		direction[1] *= -1;
	}
	public void changeDir() {
		direction[0] *= -1;
		direction[1] *= -1;
	}
    
	
 }