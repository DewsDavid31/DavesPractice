package intermediate.icodeimpl;

import intermediate.ICode;
import intermediate.ICodeNode;

/**
 * <h1>ICodeImpl</h1>
 * 
 *<p>An implementation of intermediate code as a parse tree.</p>
 */
public class ICodeImpl implements ICode{

	private ICodeNode root;		// Root node.
	
	/**
	 * Set and return the root node.
	 * @param node the node to set as root.
	 * @return the root node.
	 */
	public ICodeNode setRoot(ICodeNode node)
	{
		root = node;
		return root;
	}
	/**
	 * Getter for root node.
	 * @return the current root node.
	 */
	@Override
	public ICodeNode getRoot() {
		return root;
	}
}
