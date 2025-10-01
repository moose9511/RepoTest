package repo;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RPS extends JLabel {
	public static final int RADIUS = 10; // size of the square hit box
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
    public void setType(String type){
        if(type.equals("rock") || type.equals("paper") || type.equals("scissors")){
            this.type = type;
            this.setIcon(new ImageIcon("images/" + type + ".png"));
        }
    }
	public int[] getDir() {
		return direction;
	}
    public void setDir(int[] d) {
		direction = d;
	}
	public void changeX() {
		direction[0] *= -1;
	}
	public void changeY() {
		direction[1] *= -1;
	}
    public void setX(int num){
        xPos = num;
    }
    public void setY(int num){
        yPos = num;
    }
	public int[] getCenter() {
		int[] res = new int[2];
		res[0] = xPos+RADIUS;
		res[1] = yPos+RADIUS;
		return res;
	}
	
 }