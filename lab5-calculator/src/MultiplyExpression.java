class MultiplyExpression
	extends BinaryExpression
{
	public MultiplyExpression(final Expression lft, final Expression rht)
	{
		super(lft, rht, "*");
	}
	protected double _applyOperator(double l, double r) {
		return l*r;
	}
}

