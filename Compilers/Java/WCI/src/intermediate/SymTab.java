package intermediate;
import java.util.ArrayList;
/**
 * <h1>SymTab</h1>
 * 
 * <p>The framework interface that represents a symbol table</p>
 */
public interface SymTab {

	/**
	 * Getter for the nesting level of entry.
	 * @return the scope nesting level of entry.
	 */
	public int getNestingLevel();
	
	/**
	 * Create and enter a new entry in the symbol table.
	 * @param name the name of the entry.
	 * @return the new entry.
	 */
	public SymTabEntry enter(String name);
	
	/**
	 * Look up an existing symbol table entry.
	 * @param name the name of the entry.
	 * @return entry specified, default to null if not found.
	 */
	public SymTabEntry lookup(String name);
	
	/**
	 * Return a list of sorted entries by name.
	 */
	public ArrayList<SymTabEntry> sortedEntries();
}
