package G13.pst.models;

import java.util.Comparator;

public class CompositePointComparator implements Comparator<CompositePoint>
{
	Comparator comparator;
	public CompositePointComparator(boolean onExt1)
	{
		comparator = onExt1? new ComparatorOnExt1() : new ComparatorOnExt2();
	}
	
	private class ComparatorOnExt1 implements Comparator<CompositePoint>
	{

		@Override
		public int compare(CompositePoint o1, CompositePoint o2) {
			return o1.getExt1().compareTo(o2.getExt1());
		}
		
	}
	
	private class ComparatorOnExt2 implements Comparator<CompositePoint>
	{

		@Override
		public int compare(CompositePoint o1, CompositePoint o2) {
			return o1.getExt2().compareTo(o2.getExt2());
		}
		
	}

	@Override
	public int compare(CompositePoint o1, CompositePoint o2) {
		return comparator.compare(o1, o2);
	}
}