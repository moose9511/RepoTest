package repo;
import java.util.concurrent.*;
import javax.swing.*;
import java.awt.*;
public class GUI {
	JFrame frame;
	JPanel gamePane, configPane;
	JSlider numSlider;
	public GUI() {
		frame = new JFrame("Rock Paper Scissors Simulator");
		numSlider = new JSlider();
		frame.setSize((int) Math.round(650*1.618), 650);
		numSlider.setBounds((int) Math.round((650*1.618/2)-100), (int) Math.round((650/2)-40), 200, 80);
		configPane = new JPanel();
		configPane.setLayout(null);
		configPane.add(numSlider);
		frame.setContentPane(configPane);
		frame.setVisible(true);
	}
}
