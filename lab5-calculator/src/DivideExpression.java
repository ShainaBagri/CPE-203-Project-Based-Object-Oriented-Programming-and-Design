class DivideExpression
	extends BinaryExpression
{
	public DivideExpression(final Expression lft, final Expression rht)
	{
		super(lft, rht, "/");
	}
	protected double _applyOperator(double l, double r) {
		return l/r;
	}
}

