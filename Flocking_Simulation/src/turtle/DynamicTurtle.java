package turtle;

import java.util.List;

import drawing.Canvas;

public class DynamicTurtle extends Turtle {
	
	
	private int speed = 100; // Represents 100 pixels per second
	private double flockArea = 50; // The radius of the area where the turtles will flock
	private final float MILLISECONDS_IN_SECOND = 1000; // Time in milliseconds
	
	private double cohesionBehaviour = 1; // Value used to vary the amount of cohesion 
	private double separationBehaviour = 1; // Value used to vary the amount of separation
	private double alignmentBehaviour = 1; // Value used to vary the amount of alignment  
	
	
	/** 
	 * This turtle constructor allows the turtle to be positioned at (0,0) with no other extra functionalities.
	 * 
	 * @param canvas, where the turtle will be drawn  
	 */
	public DynamicTurtle(Canvas canvas) {
		
		super(canvas);
		draw();
	}
	
	
	/** This turtle constructor allows the turtle to move to the given position on the canvas.
	 * 
	 * @param canvas, where the turtle will be drawn 
	 * @param xPosition, the x-coordinate where to turtle to be drawn
	 * @param yPosition, the y-coordinate where to turtle to be drawn
	 */
	public DynamicTurtle(Canvas canvas, double xPosition, double yPosition) {
		
		super(canvas);
		move((int)xPosition);
		turn(90);
		move((int)yPosition);
		turn(270);
		draw();
	}
		
	/** Update the dynamic turtles' position according to it's speed, time passed and past position. 
	 * 
	 * @param time is how much time has passed
	 * @param turtles is an array list of DynamicTurtles
	 */
	public void update(int time, List<DynamicTurtle> Turtles) {
		
		// For division operation, integer variables need to be carried out as floats
		float distance = (float) speed * ((float) time / MILLISECONDS_IN_SECOND);
		
		// turtles will move this distance within each update 
		move((int) distance);
	}
	
	/** 
	 * Returns the speed of the dynamic turtle 
	 */
	public int getSpeed() {
		return speed;
	}
	
	/** 
	 * Set the speed at the specified value 
	 * 
	 * @param turtleSpeed, the speed of the turtle which we will vary
	 */
	public void setSpeed(int turtleSpeed) {
		this.speed = turtleSpeed;
	}
	
	/**
	 * Returns the cohesion behaviour of the dynamic turtle, which will vary the amount of cohesion  
	 */
	public double getCohesionBehaviour() {
		return cohesionBehaviour;
	}
	
	/**
	 * Set the cohesionBehaviour at the specified value
	 * 
	 * @param cohesionBehaviour that we will vary
	 */
	public void setCohesionBehaviour(double cohesionBehaviour) {
		this.cohesionBehaviour = cohesionBehaviour;
	}
	
	/**
	 * Returns the separation behaviour of the dynamic turtle, which will vary the amount of separation  
	 */
	public double getSeparationBehaviour() {
		return separationBehaviour;
	}
	
	/**
	 * Set the separationBehaviour at the specified value
	 * 
	 * @param separationBehaviour that we will vary
	 */
	public void setSeparationBehaviour(double separationBehaviour) {
		this.separationBehaviour = separationBehaviour;
	}
	
	/**
	 * Returns the alignment behaviour of the dynamic turtle, which will vary the amount of alignment  
	 */
	public double getAlignmentBehaviour() {
		return alignmentBehaviour;
	}
	
	/**
	 * Set the alignmentBehaviour at the specified value
	 * 
	 * @param alignmentBehaviour that we will vary
	 */
	public void setAlignmentBehaviour(double alignmentBehaviour) {
		this.alignmentBehaviour = alignmentBehaviour;
	}
	
	/**
	 * Returns the flock area where the dynamic turtles will exhibit flocking behaviour
	 */
	public double getFlockArea() {
		return flockArea;
	}
	
	/**
	 * Set the flockArea at the specified value
	 * 
	 * @param flockArea that we will vary
	 */
	public void setFlockArea(double flockArea) {
		this.flockArea = flockArea;
	}

}
