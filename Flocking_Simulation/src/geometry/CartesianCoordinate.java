package geometry;

public class CartesianCoordinate {
	
	
	private double xPosition;
	private double yPosition;
	
	/**
	 * Cartesian coordinate constructor
	 * 
	 * @param x, the x-coordinate of the object
	 * @param y, the y-coordinate of the object
	 */
	public CartesianCoordinate(double x, double y) {
		xPosition = x;
		yPosition = y;
	}
	
	/**
	 *  Returns the x-coordinate of the Cartesian coordinate object
	 */
	public double getX(){
		return xPosition;
	}
	
	/**
	 * Set the x-coordinate of the Cartesian coordinate object at the specified value
	 * 
	 * @param xPosition, where we want to set the x-coordinate
	 */
	public void setX(double xPosition) {
		this.xPosition = xPosition;
	}
	
	/**
	 * Returns the y-coordinate of the Cartesian coordinate object
	 */
	public double getY() {
		return yPosition;
	}
	
	/**
	 * Set the y-coordinate of the Cartesian coordinate object at the specified value
	 * 
	 * @param yPosition, where we want to set the y-coordinate
	 */
	public void setY(double yPosition) {
		this.yPosition = yPosition;
	}
}
