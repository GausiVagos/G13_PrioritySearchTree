package G13.pst.prioritySearchTree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import G13.pst.models.Point;
import G13.pst.models.PointComparator;

public class PrioritySearchTree 
{
	private PrioritySearchTree leftC;
	private PrioritySearchTree rightC;
	
	private Point value;
	private double median;
	private static boolean hasBeenSorted = false;
	private boolean horizontal;
	private boolean maximum_limited;
	
	public PrioritySearchTree(List<Point> data, boolean horizontal, boolean maximum_limited)
	{
		if(!hasBeenSorted)
		{
			data.sort(new PointComparator(!horizontal));// a PointComparator initialized with 'false' will base its comparisons on Y
			hasBeenSorted = true;
		}
		
		this.horizontal = horizontal;
		this.maximum_limited = maximum_limited;
		Stream<Point> stream = data.stream();
		value = maximum_limited ? stream.min(new PointComparator(horizontal)).get() : stream.max(new PointComparator(horizontal)).get();
		data.remove(value);
		
		int length = data.size();
		if(horizontal)
		{
			median = length%2 == 0? (data.get(length/2-1).getY() + data.get(length/2).getY())/2 : (data.get(length/2)).getY();
		}
		else
		{
			median = length%2 == 0? (data.get(length/2-1).getX() + data.get(length/2).getX())/2 : (data.get(length/2)).getX();
		}
		
		int limit = (int)Math.ceil((double)length/2); //the index of the the first element of the second sublist
		
		//TO VERIFY : the splitting operates correctly
		List<Point> sub = data.subList(limit, length);
		List<Point> data2 = new ArrayList<Point>(sub);
		sub.clear();
		
		leftC = data.size()!=0 ? new PrioritySearchTree(data,horizontal,maximum_limited) : null;
		rightC = data2.size()!=0 ? new PrioritySearchTree(data2,horizontal,maximum_limited) : null;
	}
	
	public List<Point> query(double minKey, double maxKey, double limit)
	{
		
		double priority = horizontal? value.getX() : value.getY();
		double key = horizontal? value.getY() : value.getX();
		
		List<Point> result = new ArrayList<Point>();
		if((maximum_limited && priority>limit) || (!maximum_limited && priority<limit)) // if the priority has passed its limit (over the max or below the min)
			return result; // end of the branch
		
		if(minKey<=key && key<=maxKey) // the current node lies in the query window
			result.add(value);
		if(minKey < median && leftC!=null)
			result.addAll(leftC.query(minKey,maxKey,limit)); // check the left subtree
		if(median<maxKey && rightC!=null)
			result.addAll(rightC.query(minKey,maxKey,limit)); // check the right subtree
		
		return result;
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
