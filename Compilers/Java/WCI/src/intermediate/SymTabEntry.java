package intermediate;

import java.util.ArrayList;

/**
 * <h1>SymTabEntry</h1>
 *
 * <p>Interface for entries into symbol table.</p>
 */
public interface SymTabEntry {
	
	/**
	 * Getter of name of entry.
	 * @return name of entry.
	 */
	public String getName();
	
	/**
	 * Getter of symbol table.
	 * @return the symbol table that contains entry.
	 */
	public SymTab getSymTab();
	
	/**
	 * Append a source line number to the entry.
	 * @param lineNumber the line number to append.
	 */
	public void appendLineNumber(int lineNumber);
	
	/**
	 * Getter of list of line numbers.
	 * @return list of line numbers.
	 */
	public ArrayList<Integer> getLineNumbers();
	
	/**
	 * Set an attribute of the entry.
	 * @param key the attribute key.
	 * @param value the attribute value.
	 */
	public void setAttribute(SymTabKey key, Object value);
	
	/**
	 * Getter for attribute of entry.
	 * @param key the attribute key.
	 * @return the attribute value.
	 */
	public Object getAttribute(SymTabKey key);

}
