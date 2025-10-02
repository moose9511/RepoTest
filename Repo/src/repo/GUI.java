package repo;
import java.util.concurrent.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
public class GUI {
	JFrame frame;
    JLabel numLabel;
	JPanel gamePane, configPane;
	JSlider numSlider;
    JButton continueBtn;
    RPS[] items;
    
    // checks if a circular area at a specified point intersects an area of an item
    public boolean isEmpty(int x, int y){
        boolean res = true;
        if((x > 0 && x < frame.getWidth()-1) && (y > 0 && y < frame.getHeight()-1)){
        	// checks each items distance to the point to see if the distance is close enough to have intersected
            for(RPS item : items){
                if(item != null){
                    int[] pos = item.getPos();
                    double distance = Math.hypot(pos[0]-x, pos[1]-y);
                    res = (Math.abs(distance) < RPS.RADIUS) ? false : res;
                }
            }
        }
        return res;
    }
    
    // checks if a circular area at a specified point is within the frame
	public boolean isValid(int x, int y) {
		return ((x > 0 && x < frame.getWidth()-RPS.RADIUS) && (y > 0 && y < frame.getHeight()-RPS.RADIUS));
	}
    
	// checks if a circular area at a specified point intersects an area of an item, returns the intersected item if so
    public RPS itemAt(int x, int y){
        if((x > 0 && x < frame.getWidth()-1) && (y > 0 && y < frame.getHeight()-1)){
            // checks each items distance to the point to see if the distance is close enough to have intersected
            for(RPS item : items){
                int[] pos = item.getPos();
                double distance = Math.hypot(pos[0]-x, pos[1]-y);
                if(Math.abs(distance) < RPS.RADIUS){
                    return item;
                }
            }
        } 
        return null;
    }

    // moves an item to a desired position based on it's direction and speed
    public void move(RPS target, int multi){
        int[] dir = target.getDir();
        int[] pos = target.getPos();
        int[] targetPos = new int[] {pos[0]+(dir[0]*multi), pos[1]+(dir[1]*multi)};
        
        if(!isValid(targetPos[0], targetPos[1])) { // change direction if item hits the edge of the wall
        	target.changeDir();
        }
        if(!isEmpty(targetPos[0], targetPos[1])){ // checks if targeted position is intersecting an item
        	
        	// gets the intersected item and checks who wins
            RPS i = itemAt(targetPos[0], targetPos[1]);
            if(i != null){
                if(target.getType() == "rock"){
                    if(i.getType() == "paper"){
                        target.setType("paper");
                    } else if(i.getType() == "scissors"){
                        i.setType("rock");
                    } 
                } else if(target.getType() == "paper"){
                    if(i.getType() == "scissors"){
                        target.setType("scissors");
                    } else if (i.getType() == "rock"){
                        i.setType("paper");
                    }
                } else if(target.getType() == "scissors"){
                    if(i.getType() == "rock"){
                        target.setType("rock");
                    } else if (i.getType() == "paper"){
                        i.setType("scissors");
                    }
                }
            }
            
            target.setLocation(targetPos[0], targetPos[1]);
            target.setPos(targetPos);
            target.setDir(new int[] {-dir[0], -dir[1]});
        } else {
            target.setLocation(targetPos[0], targetPos[1]);
            target.setPos(targetPos);
        }
    }
	public GUI() {
		// FRAME 
		frame = new JFrame("Rock Paper Scissors Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) Math.round(650*1.618), 650);
		
		// Number slider for amount of items
		numSlider = new JSlider();
		// what do the rounded numbers mean?
		numSlider.setBounds((int) Math.round((650*1.618/2)-100), (int) Math.round((650/2)-40), 200, 80);
        numSlider.setMaximum(50);
        
        // Button to start program
        continueBtn = new JButton("Continue to simulation");
        numLabel = new JLabel("Number of each item: " + numSlider.getValue());
        numLabel.setBounds((int) Math.round((650*1.618/2)-80), (int) Math.round((650/2)-85), 160, 70);
        
        // Updates number of items showed to user
        numSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e){
                numLabel.setText("Number of each item: " + numSlider.getValue());
            }
        });
        
        // continues to program
        continueBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	// where the program will be displayed
                gamePane = new JPanel();
                gamePane.setLayout(null);
                
                int numEach = numSlider.getValue();
                items = new RPS[numEach*3];
                
                // creates an amount of items for each type
                for(int i = 0; i < 3; i++){
                    for(int ii = 0; ii < numEach; ii++){
                        int x = (int) Math.round(Math.random()*frame.getWidth());
                        int y = (int) Math.round(Math.random()*frame.getHeight());
                        while(!isEmpty(x,y)){
                            x = (int) Math.round(Math.random()*frame.getWidth());
                            y = (int) Math.round(Math.random()*frame.getHeight());
                        }
                        
                        // picks random direction
                        int[] dir = {(Math.random() > .5) ? -1:1, (Math.random() > .5) ? -1:1};
                        RPS item = new RPS((i == 0) ? "rock" : (i == 1) ? "paper" : "scissors", x, y, dir);
                        item.setBounds(x,y, RPS.DIAMETER, RPS.DIAMETER);
                        items[(i*numEach)+ii] = item;
                        gamePane.add(item);
                    }
                }
                // sets game pane to frame
                frame.setContentPane(gamePane);
                frame.setVisible(true);
                
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        JPanel nPanel = new JPanel();
                        nPanel.setLayout(null);
                        for(RPS item : items){
                            //System.out.print(item.getPos)
                            move(item, 8);
                            
                            //item.setLocation(p[0], p[1]);
                            //nPanel.add(item);
                        }
                        gamePane.revalidate();
                        gamePane.repaint();
                        //frame.setContentPane(nPanel);
		                //frame.setVisible(true);
                    }
                }, 0, 33);
            }
        });
        continueBtn.setBounds((int) Math.round((650*1.618/2)-80), (int) Math.round((650/2)-5), 160, 70);
		configPane = new JPanel();
		configPane.setLayout(null);
		configPane.add(numSlider);
        configPane.add(numLabel);
        configPane.add(continueBtn);
		frame.setContentPane(configPane);
		frame.setVisible(true);
        
	}
}