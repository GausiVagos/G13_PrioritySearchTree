package G13.pst.models;

public class CompositeNumber implements Comparable
{
	private int nb1;
	private int nb2;
	
	public CompositeNumber(int nb1, int nb2) {
		super();
		this.nb1 = nb1;
		this.nb2 = nb2;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeNumber other = (CompositeNumber) obj;
		if (nb1 != other.nb1 || nb2 != other.nb2)
			return false;
		return true;
	}
	
	public int getNb1() {
		return nb1;
	}

	public void setNb1(int nb1) {
		this.nb1 = nb1;
	}

	public int getNb2() {
		return nb2;
	}

	public void setNb2(int nb2) {
		this.nb2 = nb2;
	}

	public boolean lesserThan(CompositeNumber other)
	{
		return nb1<other.getNb1() || (nb1==other.getNb1() && nb2<other.getNb2());
	}
	
	/*
	public boolean greaterThan(CompositeNumber other)
	{
		return nb1>other.getNb1() || (nb1==other.getNb1() && nb2>other.getNb2());
	}
	
	
	@Override
	public int compare(Object o1, Object o2) {
		CompositeNumber c1 = (CompositeNumber)o1;
		CompositeNumber c2 = (CompositeNumber)o2;
		if(c1 == null || c2 == null)
			return -2;
		
		if(c1.equals(c2))
			return 0;
		else if(c1.lesserThan(c2))
			return -1;
		else return 1;
	}
	*/ 
	
	@Override
	public int compareTo(Object o) 
	{
		CompositeNumber other = (CompositeNumber)o;
		if(other == null)
			return -2;
	
		return this.equals(other)? 0 : this.lesserThan(other)? -1 : 1;
	}
}
