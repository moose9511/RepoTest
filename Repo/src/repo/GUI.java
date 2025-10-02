package repo;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
public class GUI {
	JFrame frame;
    JLabel numLabel, speedLabel;
	JPanel gamePane, configPane, controlPanel;
	JSlider numSlider, speedSlider;
    JButton continueBtn, pauseBtn;
    RPS[] items;
    int tps;
    boolean paused;
    Timer timer;
    // checks if a circular area at a specified point intersects an area of an item
    public boolean isEmpty(int x, int y){
        boolean res = true;
        if(isValid(x,y)){
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
		return ((x > 0 && x < gamePane.getWidth()-RPS.RADIUS) && (y > 0 && y < gamePane.getHeight()-RPS.RADIUS));
	}
    
	// checks if a circular area at a specified point intersects an area of an item, returns the intersected item if so
    public RPS itemAt(int x, int y){
        if(isValid(x,y)){
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
    	tps = 30;
        int[] dir = target.getDir();
        int[] pos = target.getPos();
        int[] targetPos = new int[] {pos[0]+(dir[0]*multi), pos[1]+(dir[1]*multi)};
        
        if(!isValid(targetPos[0], targetPos[1])) { // change direction if item hits the edge of the wall
        	target.changeDir();
        }
        if(!isEmpty(targetPos[0], targetPos[1])){ // checks if targeted position is intersecting an item
        	
        	// gets the intersected item and checks who wins
            RPS i = itemAt(targetPos[0], targetPos[1]);
            if(i != null && !i.equals(target)){
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
		// setBounds takes 4 integers, so must round and convert a double into an int
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
            	JFrame gameFrame = new JFrame("RPS Simulator");
                gameFrame.setSize((int) Math.round(650*1.618)+100, 750);
                gamePane = new JPanel();
                gamePane.setLayout(null);
                gamePane.setBounds(50, 50, (int) Math.round(650*1.618), 650);
                controlPanel = new JPanel();
                controlPanel.setLayout(null);
                controlPanel.setBackground(new Color(230, 230, 230));
                controlPanel.setBounds(0,0,frame.getWidth(), 50);
                pauseBtn = new JButton("Pause Simulation");
                pauseBtn.setBounds((int) Math.round(controlPanel.getWidth()*0.15)-75, (controlPanel.getHeight()/2)-15, 150, 30);
                pauseBtn.addActionListener(new ActionListener() {
                	@Override
                	public void actionPerformed(ActionEvent e) {
                		paused = !paused;
                		pauseBtn.setText((paused) ? "Unpause Simulation" : "Pause Simulation");
                	}
                });
                speedSlider = new JSlider();
                speedSlider.setValue(30);
                speedSlider.setBounds((int) Math.round(controlPanel.getWidth()*0.75)-100, 10, 200, 30);
                speedSlider.setMinimum(1);
                speedLabel = new JLabel("Ticks per second: 30");
                speedLabel.setBounds((int) Math.round(controlPanel.getWidth()*0.55)-100, 10, 200, 30);
                speedSlider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e){
                        tps = speedSlider.getValue();
                        speedLabel.setText("Ticks per second:  " + tps);
                        System.out.println(tps);
                        if(timer != null) {
                        	timer.cancel();
                        }
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                            	if(!paused) {
        	                        for(RPS item : items){
        	                            //System.out.print(item.getPos)
        	                            move(item, 10);
        	                            
        	                        }
        	                        gamePane.revalidate();
        	                        gamePane.repaint();
                            	}
                            }
                        }, 0, 1000/((tps == 0) ? 30 : tps));
                    }
                });
                controlPanel.add(speedSlider);
                controlPanel.add(pauseBtn);
                controlPanel.add(speedLabel);
                gameFrame.add(gamePane);
                gameFrame.add(controlPanel);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                int numEach = numSlider.getValue();
                items = new RPS[numEach*3];
                //System.out.println("a");
                for(int i = 0; i < 3; i++){
                    for(int ii = 0; ii < numEach; ii++){
                        int x = (int) Math.round(Math.random()*frame.getWidth());
                        int y = (int) Math.round(Math.random()*frame.getHeight());
                        while(!isEmpty(x,y) || !isValid(x,y)){
                            x = (int) Math.round(Math.random()*frame.getWidth());
                            y = (int) Math.round(Math.random()*frame.getHeight());
                        }
                        int[] dir = {(Math.random() > .5) ? -1:1, (Math.random() > .5) ? -1:1};
                        RPS item = new RPS((i == 0) ? "rock" : (i == 1) ? "paper" : "scissors", x, y, dir);
                        item.setBounds(x,y, 20, 20);
                        items[(i*numEach)+ii] = item;
                        gamePane.add(item);
                        System.out.println("Placed " + item.getType());
                    }
                }
                //frame.setContentPane(gamePane);
                frame.setVisible(false);
                gameFrame.setVisible(true);
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                    	if(!paused) {
	                        for(RPS item : items){
	                            //System.out.print(item.getPos)
	                            move(item, 10);
	                            
	                        }
	                        gamePane.revalidate();
	                        gamePane.repaint();
                    	}
                    }
                }, 0, 1000/30);
            }
        });
        continueBtn.setBounds((int) Math.round((650*1.618/2)-90), (int) Math.round((650/2)+45), 180, 35);
		configPane = new JPanel();
		configPane.setLayout(null);
		configPane.add(numSlider);
        configPane.add(numLabel);
        configPane.add(continueBtn);
		frame.setContentPane(configPane);
		frame.setVisible(true);
	}
}