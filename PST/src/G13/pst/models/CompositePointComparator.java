package G13.pst.models;

import java.util.Comparator;

public class CompositePointComparator implements Comparator<CompositePoint>
{
	Comparator<CompositePoint> comparator;
	public CompositePointComparator(boolean onCnX)
	{
		comparator = onCnX? new ComparatorOnCnX() : new ComparatorOnCnY();
	}
	
	private class ComparatorOnCnX implements Comparator<CompositePoint>
	{

		@Override
		public int compare(CompositePoint o1, CompositePoint o2) {
			return o1.getCnX().compareTo(o2.getCnX());
		}
		
	}
	
	private class ComparatorOnCnY implements Comparator<CompositePoint>
	{

		@Override
		public int compare(CompositePoint o1, CompositePoint o2) {
			return o1.getCnY().compareTo(o2.getCnY());
		}
		
	}

	@Override
	public int compare(CompositePoint o1, CompositePoint o2) {
		return comparator.compare(o1, o2);
	}
}
