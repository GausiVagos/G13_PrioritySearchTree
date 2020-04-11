package G13.pst.models;

public class CompositePoint 
{
	private CompositeNumber cnX;
	private CompositeNumber cnY;
	
	public CompositePoint(CompositeNumber cnX, CompositeNumber cnY) {
		super();
		this.cnX = cnX;
		this.cnY = cnY;
	}

	public CompositePoint(Point p) 
	{
		cnX = new CompositeNumber(p.getX(),p.getY());
		cnY = new CompositeNumber(p.getY(),p.getX());
	}

	public CompositeNumber getCnX() {
		return cnX;
	}

	public void setCnX(CompositeNumber cnX) {
		this.cnX = cnX;
	}

	public CompositeNumber getCnY() {
		return cnY;
	}

	public void setCnY(CompositeNumber cnY) {
		this.cnY = cnY;
	}
	
}
