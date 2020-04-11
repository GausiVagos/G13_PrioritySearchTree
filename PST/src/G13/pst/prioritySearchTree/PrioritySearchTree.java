package G13.pst.prioritySearchTree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import G13.pst.models.Point;
import G13.pst.models.PointComparator;

public class PrioritySearchTree 
{
	private Point value;
	private double median;
	private PrioritySearchTree leftC;
	private PrioritySearchTree rightC;
	
	private static boolean hasBeenSorted = false;
	static List<Point> result;
	
	public PrioritySearchTree(List<Point> data, boolean horizontal, boolean maximum_limited)
	{
		if(!hasBeenSorted)
		{
			data.sort(new PointComparator(!horizontal));// a PointComparator initialized with 'false' will base its comparisons on Y
			hasBeenSorted = true;
		}
		
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
	
	//TODO : orienter les requêtes
	/*
	public List<Point> launchSearch(double minY, double maxY, double maxX)
	{
		result = new ArrayList<Point>();
		query(minY,maxY,maxX);
		return result;
	}
	
	public void query(double minY, double maxY, double maxX)
	{
		if(value.getX()>maxX)
			return;
		else if(minY<=value.getY() && value.getY()<=maxY)
			result.add(value);
		if(minY < median && leftC!=null)
			leftC.query(minY,maxY,maxX);
		if(median<maxY && rightC!=null)
			rightC.query(minY,maxY,maxX);
	}
	*/
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
