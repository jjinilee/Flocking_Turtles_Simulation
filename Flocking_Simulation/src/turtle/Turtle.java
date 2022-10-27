package turtle;

import drawing.Canvas;
import geometry.CartesianCoordinate;

public class Turtle {
	
	
	private Canvas myCanvas;
	private CartesianCoordinate currentPosition;
	private double direction;
	protected boolean penPosition;
	
	
	/** A turtle is constructed on the empty canvas which is currently positioned at the initial point (top-left corner of the canvas) 
	 * and the path isn't drawing as it's stationary. Also, current direction is not facing the next point to move since it hasn't been declared yet.
	 * 
	 * @param myCanvas, where the turtle will be drawn   
	 */
	public Turtle(Canvas myCanvas) {
	
		this.myCanvas = myCanvas;
		currentPosition = new CartesianCoordinate(0, 0);
		direction = 0;
		penPosition = false;
	}
	
	/**
	 * The turtle is moved in its current direction for the given number of pixels. 
	 * If the pen is down when the turtle moves, a line will be drawn on the floor. 
	 * 
	 * @param i, the number of pixels to move.
	 */
	public void move(int i) {
		
		// New position will be calculated by the direction (cosine/sine) of the turtle facing multiplied by 
		// the number of pixels to move plus the (x-coordinate / y-coordinates) of where the turtle is currently facing 
		double newPositionX = i * Math.cos(direction) + currentPosition.getX();
		double newPositionY = i * Math.sin(direction) + currentPosition.getY();
		
		// New position of turtle 
		CartesianCoordinate newPosition = new CartesianCoordinate(newPositionX,newPositionY);
		
		if (penPosition) {
			myCanvas.drawLineBetweenPoints(currentPosition, newPosition);
			
		} else {
			
			// Even though the pen is down, the turtle will continue to move to the next position but the line being removed. 
			myCanvas.drawLineBetweenPoints(currentPosition, newPosition);
			myCanvas.removeMostRecentLine();
		}
		currentPosition = newPosition;
	}

	/**
	 * Rotates the turtle clockwise by the specified angle in degrees.
	 * 
	 * @param i, the number of degrees to turn.
	 */
	public void turn(double i) {
		
		direction = Math.toDegrees(direction);
		
		double newDirection = direction + i;
		
		// Limit the angle not to exceed 360 degrees
		if (newDirection >= 360) {
			 
			newDirection = newDirection - 360;
		}
		direction = Math.toRadians(newDirection);
	}

	/**
	 * Moves the pen off the canvas so that the turtle's route 'isn't drawn' for any subsequent movements.
	 */
	public void putPenUp() {
		
		penPosition = false;
	}

	/**
	 * Lowers the pen onto the canvas so that the turtle's route is 'drawn'.
	 */
	public void putPenDown() {
		
		penPosition = true;
	}
	
	/** 
	 * Draws an equilibrium triangle, a nice-looking turtle 
	 */
	public void draw() {
		
		// Since the pen's current position is unknown, we will store the initial position of the pen
		boolean currentPenPosition = penPosition;
		
		penPosition = true;
		
		turn(150);
		move(10);
		turn(120);
		move(10);
		turn(120);
		move(10);
		// Make it face back to where it was originally facing
		turn(330);
		
		// Convert the current pen position to it's initial pen position 
		penPosition = currentPenPosition;
	}
	
	/** 
	 * Remove the previously drawn turtle from the canvas 
	 */
	public void unDraw() {
		
		// Remove method should be called as many as draw method and since draw method is used for 3 times, 
		// we need to call it three times
		myCanvas.removeMostRecentLine();
		myCanvas.removeMostRecentLine();
		myCanvas.removeMostRecentLine();
		myCanvas.repaint();
	}
	
	/** 
	 * Returns the current position of the turtle's x-coordinate 
	 */
	public int getPositionX() {
		return (int)currentPosition.getX();
	}
	
	/** 
	 * Returns the current position of the turtle's y-coordinate 
	 */
	public int getPositionY() {
		return (int)currentPosition.getY();
	}
	
	/**
	 * Ensures the turtle never leaves the boundary of the canvas and when it reaches the wall, it will wrap around. 
	 *  
	 * @param maxXPosition, width of the canvas
	 * @param maxYPosition, height of the canvas
	 */
	public void wrapPosition(int maxXPosition, int maxYPosition) {
		
		// If minimum X position is exceeded, the turtle's coordinates are reset to the maximum position
		if(currentPosition.getX() < 0) {
			currentPosition.setX(maxXPosition);
		} 
			// If maximum X position is exceeded, the turtle's coordinates are reset to 0
			else if (currentPosition.getX() > maxXPosition) {
			currentPosition.setX(0);
		} 
		
		// If minimum Y position is exceeded, the turtle's coordinates are reset to the maximum position
		if(currentPosition.getY() < 0) {
			currentPosition.setY(maxYPosition);	
		} 
			// If maximum Y position is exceeded, the turtle's coordinates are reset to 0
			else if (currentPosition.getY() > maxYPosition) {
			currentPosition.setY(0);
		}
	}
	
	/**
	 * Make turtle to move to a point on the canvas and draws a circular shape on the canvas. 
	 * 
	 * @param startPoint is where the turtle starts to draw the shape 
	 */
	public void drawObstacle(CartesianCoordinate startPoint) {
	
	// Since the pen's current position is undefined, we will store the initial position of the pen
	boolean currentPenPosition = penPosition;	
				
	move((int)startPoint.getX());
	turn(90);
	move((int)startPoint.getY());
	turn(-90);
	
	penPosition = true;
	 
	int i = 0;
	
		while (i <= 40) {
			move(6);
			turn(9);
			move(6);
			i++;
		}
		
	// Convert the current pen position to it's initial pen position  
	penPosition = currentPenPosition;
	}

	/**
	 *  Returns the direction where the turtle is currently facing
	 */
	public double getDirection() {
		return (double) direction;
	}
	
	/**
	 * Set the direction of the turtle at the specified value
	 * 
	 * @param direction of the turtle that we will vary
	 */
	public void setDirection(double direction) {
		this.direction = direction;
	}
	
	/** 
	 * Returns the current position of the turtle. 
	 */
	public CartesianCoordinate getCurrentPosition() {
		return currentPosition;
	}
	
}
