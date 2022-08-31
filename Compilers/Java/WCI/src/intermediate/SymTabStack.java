package intermediate;

import java.util.ArrayList;

/**
 * <h1> SymTabStack</h1>
 *
 *<p>The interface for the symbol table stack</p>
 */
public interface SymTabStack {

	/**
	 * Getter for current nesting level.
	 * @return current nesting level.
	 */
	public int getCurrentNestingLevel();
	
	/**
	 * Return the local symbol table which is on top of the stack.
	 * @return the local symbol table
	 */
	public SymTab getLocalSymTab();
	
	/**
	 * Create and enter a new entry into the local symbol table.
	 * @param name the name of the entry.
	 * @return the new entry.
	 */
	public SymTabEntry enterLocal(String name);
	
	/**
	 * Look up an existing entry by name in local symbol table.
	 * @param name name of the entry.
	 * @return the entry, defaults to null if not found.
	 */
	public SymTabEntry lookupLocal(String name);
	
	/**
	 * Look up existing entry throughout the symbol table stack.
	 * @param name name of the entry.
	 * @return entry specified, defaults to null if not found.
	 */
	public SymTabEntry lookup(String name);
	
}
