package G13.pst.prioritySearchTree;

import java.util.List;

import G13.pst.models.Point;

public class PrioritySearchTree 
{
	private Point value;
	private PrioritySearchTree leftC;
	private PrioritySearchTree rightC;
	
	public PrioritySearchTree(List<Point> data)
	{
		if(data.size()>0)
		{
			Point minX = minimum_in_x(data);
			value = minX;
			data.remove(minX);
			
			for(Point p : data)
			{
				//TODO
			}
		}
	}
	
	private Point minimum_in_x(List<Point> data)
	{
		//TODO
		return null;
	}
	
	private double median_in_Y(Point[] data)
	{
		//TODO : trier en fonction de y (ou avec les nombres composites?)
		//Arrays.sort(data);
		int length = data.length;

		return length%2 == 0? (data[length/2-1].getY() + data[length/2].getY())/2 : (data[length/2]).getY();
	}
}