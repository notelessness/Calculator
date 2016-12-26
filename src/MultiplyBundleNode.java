/**
 * Calculator
 * Created by Notelessness on 2016-12-24.
 */
public class MultiplyBundleNode extends EquationNode
{
	public MultiplyBundleNode()
	{
		super();
	}

	@Override
	public double calculate(UnknownValue[] value)
	{
		double sum = 0;

		for(EquationNode node : lowNodes)
		{
			sum*=node.calculate(value);
		}

		return sum;
	}

	@Override
	public void differentiate(UnknownValue value, MultiplyBundleNode bundle)
	{
		PlusBundleNode plusBundleNode = new PlusBundleNode();

		for(EquationNode differentiatedNode : lowNodes)
		{
			MultiplyBundleNode multiplyBundleNode = new MultiplyBundleNode();
			differentiatedNode.differentiate(value, multiplyBundleNode);
			for(EquationNode node : lowNodes)
			{
				if(!node.equals(differentiatedNode))
				{
					multiplyBundleNode.connectLowNode(node.clone());
				}
			}
			plusBundleNode.connectLowNode(multiplyBundleNode);
		}

		bundle.connectLowNode(plusBundleNode);
	}

	@Override
	protected EquationNode clone()
	{
		MultiplyBundleNode clone = new MultiplyBundleNode();

		for(EquationNode node : lowNodes)
		{
			clone.connectLowNode(node.clone());
		}

		return clone;
	}
}