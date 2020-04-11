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
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	private class ComparatorOnExt2 implements Comparator<CompositePoint>
	{

		@Override
		public int compare(CompositePoint o1, CompositePoint o2) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

	@Override
	public int compare(CompositePoint o1, CompositePoint o2) {
		return comparator.compare(o1, o2);
	}
}
