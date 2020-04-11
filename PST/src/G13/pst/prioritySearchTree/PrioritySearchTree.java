package G13.pst.prioritySearchTree;

import java.util.ArrayList;
import java.util.List;

import G13.pst.models.Point;
import G13.pst.models.PointComparator;

public class PrioritySearchTree 
{
	private Point value;
	private double median;
	private PrioritySearchTree leftC;
	private PrioritySearchTree rightC;
	
	private static boolean hasBeenSorted = false;
	
	public PrioritySearchTree(List<Point> data)
	{
		if(!hasBeenSorted)
		{
			data.sort(new PointComparator(false));// a PointComparator initialized with 'false' will base its comparisons on Y
			hasBeenSorted = true;
		}
			
		Point minX = data.stream().min(new PointComparator(true)).get(); // as a PointComparator with 'true' will operate on X
		value = minX;
		data.remove(minX);
		
		int length = data.size();
		median = length%2 == 0? (data.get(length/2-1).getY() + data.get(length/2).getY())/2 : (data.get(length/2)).getY();
		int limit = (int)Math.ceil((double)length/2); //the index of the the first element of the second sublist
		
		//TO VERIFY : the splitting operates correctly
		List<Point> sub = data.subList(limit, length);
		List<Point> data2 = new ArrayList<Point>(sub);
		sub.clear();
		
		leftC = data.size()!=0 ? new PrioritySearchTree(data) : null;
		rightC = data2.size()!=0 ? new PrioritySearchTree(data2) : null;
	}

	public Point getValue() {
		return value;
	}

	public void setValue(Point value) {
		this.value = value;
	}
	
	public double getMedian() {
		return median;
	}
	
	public PrioritySearchTree getLeftC() {
		return leftC;
	}

	public void setLeftC(PrioritySearchTree leftC) {
		this.leftC = leftC;
	}

	public PrioritySearchTree getRightC() {
		return rightC;
	}

	public void setRightC(PrioritySearchTree rightC) {
		this.rightC = rightC;
	}
	
	
}
