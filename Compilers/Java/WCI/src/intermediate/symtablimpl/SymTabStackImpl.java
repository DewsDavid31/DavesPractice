package intermediate.symtablimpl;

import java.util.ArrayList;

import intermediate.SymTab;
import intermediate.SymTabEntry;
import intermediate.SymTabFactory;
import intermediate.SymTabStack;

/**
 * <h1>SymTabStackImpl</h1>
 * 
 * <p>Implementation of symbol table stack</p>
 */
public class SymTabStackImpl extends ArrayList<SymTab>
implements SymTabStack{
	private int currentNestingLevel;	// Current scope nesting level.
	
	/**
	 * Constructor.
	 */
	public SymTabStackImpl()
	{
		this.currentNestingLevel = 0;
		add(SymTabFactory.createSymTab(currentNestingLevel));
	}
	
	/**
	 * Return the local symbol table at top of stack.
	 * @return the local symbol table.
	 */
	public SymTab getLocalSymTab()
	{
		return get(currentNestingLevel);
	}
	
	/**
	 * Create and enter a new entry into the local symbol table.
	 * @param name name of the entry.
	 * @return the new entry.
	 */
	public SymTabEntry enterLocal(String name)
	{
		return get(currentNestingLevel).enter(name);
		
	}
	
	/**
	 * Look up existing symbol table entry in the local symbol table.
	 * @param name the name of the entry.
	 * @return the specified entry, default to null if not found.
	 */
	public SymTabEntry lookupLocal(String name)
	{
		return get(currentNestingLevel).lookup(name);
	}
	
	/**
	 * Lookup existing entry in entire stack.
	 * @param name the name of the entry.
	 * @return the entry, or null if it does not exist.
	 */
	public SymTabEntry lookup(String name)
	{
		return lookupLocal(name);
	}

	/**
	 * Getter for current nesting level.
	 * @return nesting level.
	 */
	@Override
	public int getCurrentNestingLevel() {
		return currentNestingLevel;
	}

	
}
