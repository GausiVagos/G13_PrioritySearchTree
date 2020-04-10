package G13.pst.models;

public class Point 
{
	private int x;
	private int y;
	private Segment segment;
	
	public Point(int x, int y, Segment segment) {
		super();
		this.x = x;
		this.y = y;
		this.segment = segment;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
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
