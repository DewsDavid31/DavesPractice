package intermediate;
/**
 * <h1>ICodeNode</h1>
 * 
 * <p> Interface for a node of the intermediate code</p>
 *
 */
public interface ICodeNode {

	/**
	 * Getter of the node type.
	 * @return the node type.
	 */
	public ICodeNodeType getType();
	
	/**
	 * return the parent of this node.
	 * @return the parent of this node.
	 */
	public ICodeNodeType getParent();
	
	/**
	 * Add a child node.
	 * @param node the child node.
	 * @return the child node.
	 */
	public ICodeNode addChild(ICodeNode node);
	
	/**
	 * Set node attribute.
	 * @param key the attribute key.
	 * @param the attribute value to set.
	 * @return 
	 */
	public void setAttribute(ICodeKey key, Object value);
	
	/**
	 * Get node attribute.
	 * @param key the attribute key.
	 * @return the attribute specified.
	 */
	public Object getAttribute(ICodeKey key);
	
	/**
	 * Make a copy of this node.
	 * @return copy of this node.
	 */
	public ICodeNode copy();

}
