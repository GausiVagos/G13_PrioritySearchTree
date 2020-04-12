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
	private boolean horizontal;
	private boolean maximum_limited;
	List<Point> result;
	
	public PrioritySearchTree(List<Point> data, boolean horizontal, boolean maximum_limited, boolean has_to_be_sorted)
	{
		if(has_to_be_sorted)// only the root of the tree has to sort the point on their key
			data.sort(new PointComparator(!horizontal));// a PointComparator initialized with 'false' will base its comparisons on Y
		
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
		
		leftC = data.size()!=0 ? new PrioritySearchTree(data,horizontal,maximum_limited, false) : null; // the subtrees won't have to sort the points, it has already been done
		rightC = data2.size()!=0 ? new PrioritySearchTree(data2,horizontal,maximum_limited, false) : null;
	}
	
	public List<Point> launchQuery(double minKey, double maxKey, double limit)
	{
		result = new ArrayList<Point>();
		query(minKey,maxKey,limit,result); // when reporting a point, each subtree will add it to the same result list
		return result;
	}
	
	public void query(double minKey, double maxKey, double limit, List<Point> result)
	{
		
		double priority = horizontal? value.getX() : value.getY();
		double key = horizontal? value.getY() : value.getX();
	
		if((maximum_limited && priority>limit) || (!maximum_limited && priority<limit)) // if the priority has passed its limit (over the max or below the min)
			return; // end of the branch
		
		if(minKey<=key && key<=maxKey) // the current node lies in the query window
			result.add(value);
		if(minKey < median && leftC!=null)
			leftC.query(minKey,maxKey,limit,result); // check the left subtree
		if(median<maxKey && rightC!=null)
			rightC.query(minKey,maxKey,limit,result); // check the right subtree
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
