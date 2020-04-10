package G13.pst.models;

public class CompositePoint 
{
	private CompositeNumber ext1;
	private CompositeNumber ext2;
	
	public CompositePoint(CompositeNumber ext1, CompositeNumber ext2) {
		super();
		this.ext1 = ext1;
		this.ext2 = ext2;
	}

	public CompositePoint(Point p) 
	{
		ext1 = new CompositeNumber(p.getX(),p.getY());
		ext2 = new CompositeNumber(p.getY(),p.getX());
	}

	public CompositeNumber getExt1() {
		return ext1;
	}

	public void setExt1(CompositeNumber ext1) {
		this.ext1 = ext1;
	}

	public CompositeNumber getExt2() {
		return ext2;
	}

	public void setExt2(CompositeNumber ext2) {
		this.ext2 = ext2;
	}
	
}
