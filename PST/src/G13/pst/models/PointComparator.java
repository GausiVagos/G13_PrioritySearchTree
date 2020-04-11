package G13.pst.models;

import java.util.Comparator;

public class PointComparator implements Comparator<Point>
{
	Comparator<Point> comparator;
	public PointComparator(boolean onCnX)
	{
		comparator = onCnX? new ComparatorOnCnX() : new ComparatorOnCnY();
	}
	
	private class ComparatorOnCnX implements Comparator<Point>
	{

		@Override
		public int compare(Point o1, Point o2) {
			return (new CompositeNumber(o1.getX(),o1.getY())).compareTo(new CompositeNumber(o2.getX(),o2.getY()));
		}
		
	}
	
	private class ComparatorOnCnY implements Comparator<Point>
	{

		@Override
		public int compare(Point o1, Point o2) {
			return (new CompositeNumber(o1.getY(),o1.getX())).compareTo(new CompositeNumber(o2.getY(),o2.getX()));
		}
		
	}

	@Override
	public int compare(Point o1, Point o2) {
		return comparator.compare(o1, o2);
	}
}
