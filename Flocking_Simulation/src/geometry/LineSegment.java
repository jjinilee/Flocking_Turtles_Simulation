package geometry;

public class LineSegment {
	
	
	private CartesianCoordinate startPoint;
	private CartesianCoordinate endPoint;
	
	/**
	 * Line segment constructor which connects two Cartesian points into a line
	 * 
	 * @param start, start Cartesian coordinate of the line segment 
	 * @param end, end Cartesian coordinate of the line segment
	 */
	public LineSegment(CartesianCoordinate start, CartesianCoordinate end) {
		
		startPoint = start;
		endPoint = end;
	}
	
	/**
	 * Returns the start Cartesian coordinate
	 */
	public CartesianCoordinate getStartPoint() {
		return startPoint;
	}
	
	/**
	 * Returns the end Cartesian coordinate
	 */
	public CartesianCoordinate getEndPoint() {
		return endPoint;
	}
	
	/**
	 *  Calculate the length of a line segment
	 */
	public double length() {
		
		double lineLength;
		lineLength = Math.sqrt(Math.pow((endPoint.getY() - startPoint.getY()), 2) + Math.pow((endPoint.getX() - startPoint.getX()), 2));
		return lineLength;
	}

}
