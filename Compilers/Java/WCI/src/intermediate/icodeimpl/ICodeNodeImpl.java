package intermediate.icodeimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import intermediate.ICodeFactory;
import intermediate.ICodeKey;
import intermediate.ICodeNode;
import intermediate.ICodeNodeType;

/**
 * <h1>ICodeNodeImpl</h1>
 * 
 *<p>An implementation of a node of the intermediate code.</p>
 */
public class ICodeNodeImpl 
	extends HashMap<ICodeKey, Object>
	implements ICodeNode
	{
	private ICodeNodeType type;				// Node type.
	private ICodeNode parent;				// Current parent.
	private ArrayList<ICodeNode> children;	// Children of this node.
	
	/**
	 * Constructor.
	 * @param type the type of node to construct.
	 */
	public ICodeNodeImpl (ICodeNodeType type)
	{
		this.type = type;
		this.parent = null;
		this.children = new ArrayList<ICodeNode> ();
	}

	/**
	 * Returns type of node.
	 * @return the node type.
	 */
	@Override
	public ICodeNodeType getType() {
		return type;
	}
	/**
	 * Returns local parent node of tree.
	 * @return local parent node.
	 */
	@Override
	public ICodeNodeType getParent() {
		return null; //FIXME: add implementation.
	}

	/**
	 * Adds child node to tree.
	 * @return the node added.
	 */
	@Override
	public ICodeNode addChild(ICodeNode node) {
		if(node != null) {
			children.add(node);
		((ICodeNodeImpl) node).parent = this;
		}
		return node;
	}

	/**
	 * Sets the attribute of a given node.
	 * @param key the key of given node.
	 * @param value the attribute to set.
	 */
	@Override
	public void setAttribute(ICodeKey key, Object value) {
		put(key, value);
	}
	
	/**
	 * Gets the attribute of a node.
	 * @param key the key specifying the node.
	 * @return the attribute.
	 */
	@Override
	public Object getAttribute(ICodeKey key) {
		return get(key);
	}

	/**
	 * Copies the given node.
	 * @return a copy of the given node.
	 */
	@Override
	public ICodeNode copy() {
		// Ensuring typing matches.
		ICodeNodeImpl copy =
				(ICodeNodeImpl) ICodeFactory.createICodeNode(type);
		Set<Entry<ICodeKey, Object>> attributes = entrySet();
		Iterator<Map.Entry<ICodeKey, Object>> it = attributes.iterator();
		
		// Copying attributes.
		while (it.hasNext()) {
			Map.Entry<ICodeKey, Object> attribute = it.next();
			copy.put(attribute.getKey(), attribute.getValue());
		}
		return copy;
	}
	

	public String toString()
	{
		return type.toString();
	}

	public ArrayList<ICodeNode> getChildren() {
		return children;
	}
}
