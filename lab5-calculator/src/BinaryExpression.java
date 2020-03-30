
public abstract class BinaryExpression implements Expression{
	private final Expression lft;
	private final Expression rht;
	private final String operator;

	public BinaryExpression(final Expression lft, final Expression rht, final String operator)
	{
		this.lft = lft;
      	this.rht = rht;
      	this.operator = operator;
	}

	public String toString()
	{
		return "(" + lft + " " + operator + " " + rht + ")";
	}
	   
	public double evaluate(Bindings bindings) {
		return _applyOperator(lft.evaluate(bindings), rht.evaluate(bindings));
	}
	
	protected abstract double _applyOperator(double l, double r);
}
