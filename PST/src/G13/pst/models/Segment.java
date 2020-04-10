package G13.pst.models;

public class Segment 
{
	private Point ext1;
	private Point ext2;
	
	public Segment(Point ext1, Point ext2) {
		super();
		this.ext1 = ext1;
		this.ext2 = ext2;
	}

	public Point getExt1() {
		return ext1;
	}

	public void setExt1(Point ext1) {
		this.ext1 = ext1;
	}

	public Point getExt2() {
		return ext2;
	}

	public void setExt2(Point ext2) {
		this.ext2 = ext2;
	}
	
}
