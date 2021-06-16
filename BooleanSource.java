
public class BooleanSource {
	private double probability;
	
	public BooleanSource(double p) {
		if ((p<0) || (1<p))
			throw new IllegalArgumentException("Illegal p: " + p);
		probability = p;
	}
	public boolean query()	{
		return (Math.random() < probability);
	}
}
