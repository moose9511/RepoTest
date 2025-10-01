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
    public boolean isEmpty(int x, int y){
        boolean res = true;
        if((x > 0 && x < frame.getWidth()-1) && (y > 0 && y < frame.getHeight()-1)){
            //System.out.println("c");
            for(RPS item : items){
                if(item != null){
                    int[] pos = item.getPos();
                    double distance = Math.sqrt(Math.pow(pos[0]-x, 2) + Math.pow(pos[1]-y, 2));
                    res = (Math.abs(distance) < 10) ? false : res;
                }
            }
        }
        return res;
    }

    public RPS itemAt(int x, int y){
        if((x > 0 && x < frame.getWidth()-1) && (y > 0 && y < frame.getHeight()-1)){
            //System.out.println("b");
            for(RPS item : items){
                int[] pos = item.getPos();
                double distance = Math.sqrt(Math.pow(pos[0]-x, 2) + Math.pow(pos[1]-y, 2));
                if(Math.abs(distance) < 10){
                    return item;
                }
            }
        } 
        return null;
    }
    
    public boolean isValidPos(int x, int y) {
    	return ((x > 0 && x < frame.getWidth()-20) && (y > 0 && y < frame.getHeight()-20));
    }
    
    public void move(RPS target, int multi){
        int[] dir = target.getDir();
        int[] pos = target.getPos();
        if(!isValidPos(pos[0]+(dir[0]*multi), pos[1]+(dir[1]*multi))){
        	target.setDir(new int[] {-dir[0], -dir[1]});
        }
        if(!isEmpty(pos[0]+(dir[0]*multi), pos[1]+(dir[1]*multi))){
            RPS i = itemAt(pos[0]+(dir[0]*multi), pos[1]+(dir[1]*multi));
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
            target.setLocation(pos[0]+(dir[0]*multi), pos[1]+(dir[1]*multi));
            target.setX(pos[0]+(dir[0]*multi));
            target.setY(pos[1]+(dir[1]*multi));
            target.setDir(new int[] {-dir[0], -dir[1]});
        } else {
            target.setLocation(pos[0]+(dir[0]*multi), pos[1]+(dir[1]*multi));
            target.setX(pos[0]+(dir[0]*multi));
            target.setY(pos[1]+(dir[1]*multi));
        }
    }
	public GUI() {
		frame = new JFrame("Rock Paper Scissors Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		numSlider = new JSlider();
		frame.setSize((int) Math.round(650*1.618), 650);
		numSlider.setBounds((int) Math.round((650*1.618/2)-100), (int) Math.round((650/2)-40), 200, 80);
        numSlider.setMaximum(50);
        continueBtn = new JButton("Continue to simulation");
        numLabel = new JLabel("Number of each item: " + numSlider.getValue());
        numSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e){
                numLabel.setText("Number of each item: " + numSlider.getValue());
            }
        });
        numLabel.setBounds((int) Math.round((650*1.618/2)-80), (int) Math.round((650/2)-85), 160, 70);
        continueBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                gamePane = new JPanel();
                gamePane.setLayout(null);
                int numEach = numSlider.getValue();
                items = new RPS[numEach*3];
                //System.out.println("a");
                for(int i = 0; i < 3; i++){
                    for(int ii = 0; ii < numEach; ii++){
                        int x = (int) Math.round(Math.random()*frame.getWidth());
                        int y = (int) Math.round(Math.random()*frame.getHeight());
                        while(!isEmpty(x,y)){
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
                            move(item, 10);
                            
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