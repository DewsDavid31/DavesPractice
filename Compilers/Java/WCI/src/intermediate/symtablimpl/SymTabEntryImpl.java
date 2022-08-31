package intermediate.symtablimpl;

import java.util.ArrayList;
import java.util.HashMap;

import intermediate.SymTab;
import intermediate.SymTabEntry;
import intermediate.SymTabKey;

/**
 * <h1>SymTabEntryImpl</h1> 
 *
 * <p>An implementation of a symbol table entry.</p>
 */
public class SymTabEntryImpl
extends HashMap<SymTabKey, Object>
implements SymTabEntry
{
	private String name;					// Entry name.
	private SymTab symTab; 					// Containing symbol table.
	private ArrayList<Integer> lineNumbers;	// List of source line numbers.
	
	/**
	 * Constructor.
	 * @param name the name of the entry.
	 * @param symTab the containing symbol table.
	 */
	public SymTabEntryImpl(String name, SymTab symTab)
	{
		this.name = name;
		this.symTab = symTab;
		this.lineNumbers = new ArrayList<Integer>();
	}
	
	/**
	 * Append a source line to the entry.
	 * @param lineNumber the line number to append.
	 */
	public void appendLineNumber(int lineNumber)
	{
		lineNumbers.add(lineNumber);
	}
	
	/**
	 * Set an attribute of the entry.
	 * @param key key of the attribute.
	 * @param value value to set of attribute.
	 */
	public void setAttribute(SymTabKey key, Object value)
	{
		put(key, value);
	}
	
	/**
	 * Get the value of an attribute of the entry.
	 * @param key the key of attribute of entry.
	 * @return the value of attribute.
	 */
	public Object getAttribute(SymTabKey key)
	{
		return get(key);
	}

	/**
	 * Getter for entrie's name
	 * @return name of entry.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Getter for containing symbol table.
	 * @return current symbol table.
	 */
	@Override
	public SymTab getSymTab() {
		return symTab;
	}

	/**
	 * Getter for current list of line numbers.
	 * @return list of line numbers in ArrayList format.
	 */
	@Override
	public ArrayList<Integer> getLineNumbers() {
		return lineNumbers;
	}

}
