import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tools.Utils;
import turtle.DynamicTurtle;
import turtle.RandomTurtleB;
import turtle.Turtle;
import drawing.Canvas;
import geometry.CartesianCoordinate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
	
public class FlockingSimulation {
		

	private final int WINDOW_X_SIZE = 1200;
	private final int WINDOW_Y_SIZE = 600;
	// Used to divide the behaviours so that the range of behaviours will be from 0 to 1
	// because the sliders only returns integers
	private final double SLIDER_RATIO = 50; 
	private List<DynamicTurtle> turtles; 
		
	private JFrame frame;
	private Canvas canvas;
	private JPanel lowerPanel, upperPanel; 
	private JButton addTurtleButton; // A button for adding new turtles 
	private JButton removeTurtleButton; // A button for removing previously added turtles
	private JButton clearthemButton; // A button to remove all the turtles 
	private JButton addMoreButton; // A button adding 3 turtles at once 	
	
	// Slider which can vary the value of speed and flock area of the turtle, cohesion, separation and alignment behaviours
	private JSlider turtleSpeed;
	private JSlider turtleCohesion;
	private JSlider turtleSeparation;
	private JSlider turtleAlignment;
	private JSlider turtleFlockArea;
	
	// Label for speed, cohesion, separation, alignment and flock area on the canvas 
	private JLabel labelSpeed; 
	private JLabel labelCohesion;
	private JLabel labelSeparation;
	private JLabel labelAlignment;
	private JLabel labelFlockArea;
	
	private boolean continueRunning;
	
	public FlockingSimulation() {
		
		setupGUI();
		drawCircularObstacle();
		setupTurtles();
		gameLoop();
	}
		
	public static void main(String[] args) {
		System.out.println("*********** Running Turtle Program ***********");
		new FlockingSimulation();
	}
		
	private void setupGUI() {
			
		frame = new JFrame();
		frame.setTitle("Flocking Turtle Simulation_2021");
		frame.setSize(WINDOW_X_SIZE, WINDOW_Y_SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		canvas = new Canvas();
		frame.add(canvas, BorderLayout.CENTER);
		
		lowerPanel = new JPanel();
		frame.add(lowerPanel, BorderLayout.NORTH);
		
		upperPanel = new JPanel();
		frame.add(upperPanel, BorderLayout.SOUTH);
		
		/**
		 *  Add turtles button which will add randomly moving turtles at random positions when it is clicked 
		 */
		addTurtleButton = new JButton("Add turtles");
		lowerPanel.add(addTurtleButton);
		addTurtleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
				addNewTurtle();
				(turtles.get(turtles.size() - 1)).setSpeed(turtleSpeed.getValue());
				(turtles.get(turtles.size() - 1)).setCohesionBehaviour((double) turtleCohesion.getValue()/SLIDER_RATIO);
				(turtles.get(turtles.size() - 1)).setSeparationBehaviour((double) turtleSeparation.getValue()/SLIDER_RATIO);
				(turtles.get(turtles.size() - 1)).setAlignmentBehaviour((double) turtleAlignment.getValue()/SLIDER_RATIO);
			}
		});
		
		
		/** Remove Turtles button which will remove the recently added turtles when clicked
		 * 
		 */
		removeTurtleButton = new JButton("Remove Turtles");
		lowerPanel.add(removeTurtleButton);
		removeTurtleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					
				if (turtles.size() > 0) {
					// Minus one from the size of the arraylist is the index of the last added turtle
					// So, when we want to remove the turtle, we can undraw the index of the last added turtle
					(turtles.get(turtles.size() - 1)).unDraw();
					turtles.remove(turtles.size() - 1);		
				} 
			}
		});
		
		
		/**
		 *  Turtle's speed slider which can adjust the speed of the turtles 
		 */
		labelSpeed = new JLabel("  Turtle's Speed");
		lowerPanel.add(labelSpeed);
						
		turtleSpeed = new JSlider(JSlider.HORIZONTAL, 0, 500, 100);
		turtleSpeed.setMajorTickSpacing(200);
		turtleSpeed.setMinorTickSpacing(100);
		turtleSpeed.setPaintTicks(true);
		turtleSpeed.setPaintLabels(true);
		lowerPanel.add(turtleSpeed);			
		turtleSpeed.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				synchronized (turtles) {
					for (DynamicTurtle turtle : turtles) {
						// Turtle's speed will change according to the current value of the slider
						turtle.setSpeed(turtleSpeed.getValue());
					}
				}
			}
		});
		
		
		// TRIPLETS button which adds 3 turtles into the screen at once when it is clicked
		addMoreButton = new JButton("TRIPLETS!");
		lowerPanel.add(addMoreButton);
		addMoreButton.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i < 3; i++) {
					addNewTurtle();
					(turtles.get(turtles.size() - 1)).setSpeed(turtleSpeed.getValue());
					(turtles.get(turtles.size() - 1)).setCohesionBehaviour((double) turtleCohesion.getValue()/SLIDER_RATIO);
					(turtles.get(turtles.size() - 1)).setSeparationBehaviour((double) turtleSeparation.getValue()/SLIDER_RATIO);
					(turtles.get(turtles.size() - 1)).setAlignmentBehaviour((double) turtleAlignment.getValue()/SLIDER_RATIO);
				}
			}
		});		
		
				
		// GOOD-BYE button which will remove all the turtles on the canvas
		clearthemButton = new JButton("GOOD-BYE!");
		lowerPanel.add(clearthemButton);
		clearthemButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Turtle.size() indicates the number of turtles on the canvas and when it is greater than zero, 
				// it indicates that there must be at least one randomly moving turtle on the canvas so remove  
				while (turtles.size() > 0) {
					(turtles.get(turtles.size() - turtles.size())).unDraw();
					turtles.remove(turtles.size() - turtles.size());		
				}
			}
		});
		
		
		// Add labels in the slider
	    Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
	    position.put(0, new JLabel("0"));
	    position.put(50, new JLabel("Limit"));
	    
	    // Cohesion behaviour slider, which will adjust the value of cohesion behaviour of the turtles 
		labelCohesion = new JLabel("Cohesion ");
		upperPanel.add(labelCohesion);
	    
		turtleCohesion = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
		turtleCohesion.setPaintTicks(true);
		turtleCohesion.setPaintLabels(true);
		turtleCohesion.setMajorTickSpacing(10);
		turtleCohesion.setLabelTable(position);
		upperPanel.add(turtleCohesion);			
		turtleCohesion.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				synchronized (turtles) {
					for (DynamicTurtle turtle : turtles) {
						turtle.setCohesionBehaviour((double) turtleCohesion.getValue() /SLIDER_RATIO);
					}
				}
			}
		});
		
		
		// Separation behaviour slider, which will adjust the value of separation behaviour of the turtles 
		labelSeparation = new JLabel(" Separation ");
		upperPanel.add(labelSeparation);
			    
		turtleSeparation = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
		turtleSeparation.setPaintTicks(true);
		turtleSeparation.setPaintLabels(true);
		turtleSeparation.setMajorTickSpacing(10);
		turtleSeparation.setLabelTable(position);
		upperPanel.add(turtleSeparation);			
		turtleSeparation.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				synchronized (turtles) {
					for (DynamicTurtle turtle : turtles) {
						turtle.setSeparationBehaviour((double) turtleSeparation.getValue()/SLIDER_RATIO);
					}
				}
			}
		});
		
		
		// Alignment behaviour slider, which will adjust the value of alignment behaviour of the turtles  
		labelAlignment = new JLabel(" Alignment ");
		upperPanel.add(labelAlignment);
					    
		turtleAlignment = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
		turtleAlignment.setPaintTicks(true);
		turtleAlignment.setPaintLabels(true);
		turtleAlignment.setMajorTickSpacing(10);
		turtleAlignment.setLabelTable(position);
		upperPanel.add(turtleAlignment);			
		turtleAlignment.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				synchronized (turtles) {
					for (DynamicTurtle turtle : turtles) {
						turtle.setAlignmentBehaviour((double)turtleAlignment.getValue()/SLIDER_RATIO);
					}
				}
			}
		});
			
		
		// Flock area slider, which will adjust the value of flock area of the turtles 
		labelFlockArea = new JLabel(" flock Area ");
		upperPanel.add(labelFlockArea);
								    
		turtleFlockArea = new JSlider(JSlider.HORIZONTAL, 0, 300, 50);
		turtleFlockArea.setMajorTickSpacing(60);
		turtleFlockArea.setMinorTickSpacing(15);
		turtleFlockArea.setPaintTicks(true);
		turtleFlockArea.setPaintLabels(true);
		upperPanel.add(turtleFlockArea);			
		turtleFlockArea.addChangeListener(new ChangeListener() {
						
			@Override
			public void stateChanged(ChangeEvent e) {
				synchronized (turtles) {
					for (DynamicTurtle turtle : turtles) {
							turtle.setFlockArea(turtleFlockArea.getValue());									
					}
				}
			}
		});
			
		frame.revalidate();
		}
	
		
	/**
	 *  Synchronized list of dynamic turtles
	 */
	private void setupTurtles() {
		
		turtles = Collections.synchronizedList(new ArrayList<DynamicTurtle>());
	}
		
	/**
	 *  Draws an circular obstacle on the canvas which the turtles cannot penetrate
	 */
	public void drawCircularObstacle() {
			
		CartesianCoordinate startPoint = new CartesianCoordinate(350, 125);
		Turtle circularTurtle = new Turtle(canvas);
			
		circularTurtle.drawObstacle(startPoint);
	}
	
	/**
	 * Add turtles in random positions on the canvas. If the distance between the center of obstacle and 
	 * the turtle is smaller than 75 (flock area), it means the turtle will turtle will appear in the obstacle so if that's the case, 
	 * add turtles in another position. 
	 */
	private void addNewTurtle() {
			
		CartesianCoordinate centerOfObstacle = new CartesianCoordinate(350, 200);
			
		int addPointX = (int)(Math.random() * WINDOW_X_SIZE);
		int addPointY = (int)(Math.random() * WINDOW_Y_SIZE);
			
		if (Math.sqrt(Math.pow((addPointY - centerOfObstacle.getY()), 2) + Math.pow((addPointX - centerOfObstacle.getX()), 2)) <= 75) {
			addNewTurtle();
		} else  {
			turtles.add(new RandomTurtleB(canvas, addPointX, addPointY));
		}
	}
			
		
		/**
		 * Game loop
		 */
		private void gameLoop() {
			
			// Game loop updates everything in every 20 milliseconds
			int deltaTime = 20;
			continueRunning = true;
			
			while (continueRunning) {
				
				synchronized (turtles) {
					for (DynamicTurtle turtle : turtles) {
						turtle.unDraw();
					}
				}
				// update and wrap the position of the turtles if they reach the boundaries of the canvas
				synchronized (turtles) {
					for (DynamicTurtle turtle : turtles) {
						turtle.update(deltaTime, turtles);
						turtle.wrapPosition(canvas.getWidth(), canvas.getHeight());
						}
				}
				synchronized (turtles) {
					for (DynamicTurtle turtle : turtles) {
						turtle.draw();
					}
				}
				
				Utils.pause(deltaTime);
			}
		}
}
