package turtle;

import java.util.ArrayList;
import java.util.List;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import geometry.LineSegment;

public class RandomTurtleB extends DynamicTurtle{
	
	private int counter = 0;
	private int angularVelocity = 0;
	private final int MAX_UPDATES = 50; // Maximum number of updates that will run before getting a new angular velocity
	private final float MILLISECONDS_IN_SECOND = 1000;
	private final int MAX_ANGULAR_VEL_UPDATE = 10; // At maximum, it only turns 10 degrees per update
	private final int MAX_ANGULAR_VELOCITY_CHANGE = 4; // At maximum, it only changes the angular velocity by 4 per update
	
	
	/**
	 *  Random turtle constructor which calls DynamicTurtle constructor, draws RandomTurtle positioned at (0,0)
	 *  
	 * @param canvas, where the turtle will be drawn  
	 */
	public RandomTurtleB(Canvas canvas) {
		
		super(canvas);
	}
	
	/**
	 * This RandomTurtle constructor allows the turtle to move to the given position on the canvas
	 * 
	 * @param canvas, where the turtle will be drawn
	 * @param xPosition, the x-coordinate where to turtle to be drawn
	 * @param yPosition, the y-coordinate where to turtle to be drawn
	 */
	public RandomTurtleB(Canvas canvas, double xPosition, double yPosition) {
		
		super(canvas, xPosition, yPosition);
		setDirection(Math.random() * (2*Math.PI));
	}
	
	
	@Override
	public void update(int time, List<DynamicTurtle> turtles) { 
		
		
		/**
		 * When there are turtles on the canvas, find the distance between the turtle and the other turtles.
		 * Turtle will be added into the neighbourTurtle list only when the distance between them is lesser than the flock area which is 50. 
		 */
		
		List<DynamicTurtle> neighbourTurtles = new ArrayList<DynamicTurtle> ();
		
		for (DynamicTurtle turtle : turtles) {
			
			// Measure the distance between the turtle and the other turtles inside the DynamicTurtle list 
			double distanceBtwnTurtles = calculateConnection(turtle);
			
			if (distanceBtwnTurtles < getFlockArea() && turtle != this) { 	
				neighbourTurtles.add(turtle);
			}
		}
		
		/**
		 * Calculate the distance between the turtle and the center of circular obstacle. If the distance between them is less
		 * than 75 pixels (which is the radius of the circular obstacle), turtle will hit the obstacle so make it turn with 180 degrees.
		 * Turtles can never penetrate the obstacle.
		 */
		
		CartesianCoordinate centerOfObstacle = new CartesianCoordinate(350, 200);
		
		double distanceToObstacle = calculateDistance(centerOfObstacle);
		
		// When turtle hits the obstacle
		if (distanceToObstacle <= 75) {
			
			turn(180);
			counter = (MAX_UPDATES / 5);
		}
		
		/**
		 * If there's no turtle around or the value of all three behaviours are zero, 
		 * the turtle will move randomly without getting affected by any behaviours, no flocking behaviour. 
		 */
	
		if (neighbourTurtles.size() == 0 || getCohesionBehaviour() == 0 && getAlignmentBehaviour() == 0 && getSeparationBehaviour() == 0) {
			
			
			/** 
			 * If it's not yet time to change the angular velocity of the turtle, it will continue to move with same angular velocity and speed
			 */
			
			if (counter > 0) {
				
				float distance = (float)(getSpeed() * (time / MILLISECONDS_IN_SECOND));
				turn(angularVelocity);
				move((int) distance);
				// The counter should be decremented each time update is called
				counter--;
			
			}
			
			/** 
			 * Else, turtle's angular velocity will change. Check whether the randomly moving turtle will turn 
			 * either in the left side or the right side. If the value of the theCheck is (smaller than 0.5 / greater than 0.5), 
			 * it turns (left side, anti-clockwise direction / right side, clockwise direction).
			 */
			
			else {
			
				// theCheck will generate a random number from 0 to 1
				double theCheck = Math.random();
				
				// Limit the angular velocity to not exceed it's maximum and minimum angular velocity 
				if (theCheck < 0.5) {
					
					if (angularVelocity < MAX_ANGULAR_VEL_UPDATE) {
						angularVelocity = angularVelocity + ((int) (Math.random() * MAX_ANGULAR_VELOCITY_CHANGE));
					}
					
					// If the angular velocity is greater than the maximum, set it to the maximum
					else {
						angularVelocity = MAX_ANGULAR_VEL_UPDATE;
					}
				
				} else if (theCheck > 0.5) {
					
					if (angularVelocity > -MAX_ANGULAR_VEL_UPDATE) { 
						
						angularVelocity = angularVelocity + ((int) (Math.random() * -MAX_ANGULAR_VELOCITY_CHANGE));
					}
					// If the angular velocity is smaller than the minimum, set it to the minimum
					else {
						angularVelocity = - MAX_ANGULAR_VEL_UPDATE;
					}
				
				float distance = (float)(getSpeed() * (time / MILLISECONDS_IN_SECOND));
				move((int) distance);
				// Game loop updates everything every 20 milliseconds, which is 50 frames per second. 
				counter = (int) (Math.random() * MAX_UPDATES);
				}
			}
		} 
		
		/**
		 * When there are turtles around or behaviours to obey the movement of cohesion, separation or alignment, the turtle
		 * will start to move according to the values of behaviours. 
		 */
		else { 
		
		// The counter should be decremented each time update is called
		counter--;
		
		 // Average the angle by summing the angles obtained according to the value of behaviours and dividing it by the number of
		 // non-zero behaviours, and not the value of behaviours themselves. 
		double angleToFace = ((cohesionControl(neighbourTurtles) + separationControl(neighbourTurtles) + alignmentControl(neighbourTurtles)) / checkBehaviour(getCohesionBehaviour(), getSeparationBehaviour(), getAlignmentBehaviour()));
			
			/**
			 * Check whether the turtle will move in left or right side. If the angleToFace is positive, it will move to right side
			 * which is the clockwise direction and if the angleToFace is negative, it will move to left side which is anti-clockwise direction
			 */
		
			if (angleToFace > 0) {
				
				if (angularVelocity < MAX_ANGULAR_VEL_UPDATE) {
				angularVelocity = angularVelocity + MAX_ANGULAR_VELOCITY_CHANGE;
				}
				
				else{
					// If the angular velocity is greater than the maximum, set it to the maximum
					angularVelocity = MAX_ANGULAR_VEL_UPDATE;
				}
			
			} else if (angleToFace < 0) {
				 
				if (angularVelocity > -MAX_ANGULAR_VEL_UPDATE) {
					angularVelocity =  angularVelocity - MAX_ANGULAR_VELOCITY_CHANGE;
				}
				else {
					// If the angular velocity is smaller than the minimum, set it to the minimum
					angularVelocity = -MAX_ANGULAR_VEL_UPDATE;
				}
			}
			
			turn(angularVelocity);
			float distance = (float) getSpeed() * ((float) time / MILLISECONDS_IN_SECOND);
			move((int) distance);
			
		}
	}
		
	/**
	 * Cohesion, make turtles move towards the center of the mass of those around them 
	 * 
	 * @param neighbourTurtles, turtles within the flock area 
	 * @return cohesion angle which the turtle needs to turn due to cohesion
	 */
	public double cohesionControl(List<DynamicTurtle> neighbourTurtles) {
				
		CartesianCoordinate centerOfMass = calculateCenterOfMass(neighbourTurtles);
		CartesianCoordinate currentPosition = getCurrentPosition();
		
		double xDifference = centerOfMass.getX() - currentPosition.getX();
		double yDifference = centerOfMass.getY() - currentPosition.getY();
		
		double angleInRadians = Math.atan2(yDifference, xDifference);
		
		// Place the angleInRadians to be within the range of 0 to 360 degrees
		if (angleInRadians < 0) {
			angleInRadians = (2*Math.PI) - (-angleInRadians);
		}
		
		double cohesionAngle = getCohesionBehaviour() * (angleInRadians - getDirection());
		
		// Check the angle is in the range between 180 and -180 degrees to obtain the smallest direction needed to turn.
		cohesionAngle = checkAngle(cohesionAngle);
		
		return cohesionAngle;
	}
	
	
	/**
	 * Separation, make turtles move away from the center of mass of those around them
	 * 
	 * @param neighbourTurtles, turtles within the flock area
	 * @return separationAngle, for the turtle to rotate due to separation.
	 */
	public double separationControl(List<DynamicTurtle> neighbourTurtles) {
		
		// Get the center of mass of the turtles around them
		CartesianCoordinate centerOfMass = calculateCenterOfMass(neighbourTurtles);
		CartesianCoordinate currentPosition = getCurrentPosition();
		
		double xDifference = centerOfMass.getX() - currentPosition.getX();
		double yDifference = centerOfMass.getY() - currentPosition.getY();
		
		double angleInRadians = (Math.atan2(yDifference, xDifference));
		
		// Place the angleInRadians to be within the range of 0 to 360 degrees
		if (angleInRadians < 0) {
			angleInRadians = (2*Math.PI) - (-angleInRadians);
		}
		
		// Angle required for the turtle to rotate due to separation.
		double separationAngle = getSeparationBehaviour() * ((angleInRadians - getDirection()) - Math.PI);
		
		// Check the angle is in the range between 180 and -180 degrees to obtain the smallest direction needed to turn.
		separationAngle = checkAngle(separationAngle);
		
		return separationAngle;
	}
	
	
	/**
	 * alignment, make turtles to align it's direction with those around them 
	 * 
	 * @param neighbourTurtles, turtles within the flock area
	 * @return alignmentAngle, for the turtles to rotate due to alignment 
	 */
	public double alignmentControl(List<DynamicTurtle> neighbourTurtles) {
		
		// Calculate the average direction of neighbourTurtles within the flock area
		double averageDirection = calculateAverageDirection(neighbourTurtles);
		
		// Angle required for the turtle to rotate due to alignment
		double alignmentAngle = getAlignmentBehaviour() * (averageDirection - getDirection());
		
		// Check the angle is in the range between 180 and -180 degrees to obtain the smallest direction needed to turn.
		alignmentAngle = checkAngle(alignmentAngle);
		
		return alignmentAngle;	
	}
	
	
	/**
	 * Calculates the center of mass of all of the neighbourTurtles within the flock area 
	 * 
	 * @param neighbourTurtles, turtles within the flock area
	 */
	public CartesianCoordinate calculateCenterOfMass(List<DynamicTurtle> neighbourTurtles) {
		
		int sumOfX = 0;
		int sumOfY = 0;
		int centerX = 0;
		int centerY = 0;
		int noOfTurtlesAround = 0;
		
		// Sum all the x and y coordinates of the turtles inside the list 
		for (DynamicTurtle turtle : neighbourTurtles) {
				
			sumOfX = sumOfX + turtle.getPositionX();
			sumOfY = sumOfY + turtle.getPositionY();
			noOfTurtlesAround++;
		}
		
		// Divide the the summation of x and y coordinates by the number of the turtles in the list 
		centerX = sumOfX / noOfTurtlesAround;
		centerY = sumOfY / noOfTurtlesAround;
		
		CartesianCoordinate center = new CartesianCoordinate(centerX, centerY);
		
		return center;
	}
	
	
	/**
	 * Calculate all the average direction of all dynamic turtles within the certain radius
	 * 
	 * @param neighborTurtles, turtles within the flock radius
	 */
	public double calculateAverageDirection(List<DynamicTurtle> neighbourTurtles) {
		
		int noOfTurtlesAround = 0;
		double sumOfAngles = 0;
		double averageDirection = 0;
		
		// Sum all the average directions of the turtles facing in the flock area
		for (DynamicTurtle turtle : neighbourTurtles) {
				
			sumOfAngles = sumOfAngles + turtle.getDirection();
			noOfTurtlesAround++;
		}
		
		averageDirection = sumOfAngles / noOfTurtlesAround;
		
		return averageDirection;
	}
		
	
	/**
	 * Calculate the distance between the turtle and another turtle
	 * 
	 * @param turtles in the dynamic turtle list
	 * @return the distance between two turtles 
	 */
	public double calculateConnection(Turtle turtles) {
		   
		LineSegment connectionBtwnTurtles = new LineSegment(getCurrentPosition(), turtles.getCurrentPosition());
		return connectionBtwnTurtles.length();
	}
	
	
	/**
	 * Calculate the distance between the turtle and the center of the obstacle 
	 * 
	 * @param obstacleCenter which is the center of the obstacle, (350, 200)
	 * @return distanceToObstacle is the length between the turtle and the center of the obstacle 
	 */
	public double calculateDistance(CartesianCoordinate obstacleCenter) {
			
		LineSegment distanceToObstacle = new LineSegment(this.getCurrentPosition(), obstacleCenter);
		return distanceToObstacle.length();
	}
	
	
	/**
	 * Check and set the angles to be in the range between 180 and -180 degrees so that the angle the turtle needs 
	 * to rotate will be the smallest possible angle. This is used to check the 'angleInRadians' in cohesion and separation control method 
	 * 
	 * @param angle to be set within the range
	 */
	private double checkAngle(double angle) {
			
		if (angle > Math.PI) {
			angle = angle - (2 * Math.PI);
		}
		else if (angle < -Math.PI) {
			angle = angle + (2 * Math.PI);
		}
			
		return angle;
	}
		
	
	/** 
	 * Behaviour is the value which used to vary the amount of cohesion, separation and alignment behaviour.
	 * 
	 * @return the number of non-zero behaviours among cohesion, separation and alignment behaviours
	 */
	private int checkBehaviour(double cohesionFactor, double alignmentFactor, double separationFactor) {
			
		int nonZeroBehaviour = 0;
			
		if (cohesionFactor > 0) {
			nonZeroBehaviour++;
		}	
		if (separationFactor > 0) {
			nonZeroBehaviour++;
		}
		if (alignmentFactor > 0) {
			nonZeroBehaviour++;
		}
		return nonZeroBehaviour;
	}
		
}

