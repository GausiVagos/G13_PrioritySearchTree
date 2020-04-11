package G13.pst.models;

public class Point 
{
	private double x;
	private double y;
	private Segment segment;
	
	public Point(double x, double y, Segment segment) {
		this.x = x;
		this.y = y;
		this.segment = segment;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	
	public CompositePoint toComposite()
	{
		return new CompositePoint(this);
	}
}
