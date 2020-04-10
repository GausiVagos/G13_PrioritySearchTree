package G13.pst.models;

public class CompositeNumber
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
		if (nb1 != other.nb1)
			return false;
		if (nb2 != other.nb2)
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
	
	public boolean greaterThan(CompositeNumber other)
	{
		return nb1>other.getNb1() || (nb1==other.getNb1() && nb2>other.getNb2());
	}
}
