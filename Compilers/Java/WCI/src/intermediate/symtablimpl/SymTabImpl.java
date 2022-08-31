package intermediate.symtablimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import intermediate.SymTab;
import intermediate.SymTabEntry;
import intermediate.SymTabFactory;

/**
 * <h1>SymTabImpl</h1> 
 *
 * <p>An implementation of the symbol table</h1>
 */
public class SymTabImpl
extends TreeMap<String, SymTabEntry>
implements SymTab{
	private int nestingLevel;
	
	public SymTabImpl(int nestingLevel)
	{
		this.nestingLevel = nestingLevel;
	}
	
	/**
	 * Create and enter a new entry into the symbol table.
	 * @param name the name of the entry.
	 * @return the new entry.
	 */
	public SymTabEntry enter(String name)
	{
		SymTabEntry entry = SymTabFactory.createSymTabEntry(name, this);
		put(name, entry);
		
		return entry;
	}
	
	/**
	 * Lookup an existing symbol table entry.
	 * @param name name of the entry.
	 * @return the entry, or null if it doesn't exist. 
	 */
	public SymTabEntry lookup(String name)
	{
		return get(name);
	}
	
	/**
	 * @return a list of symbol table entries sorted by name.
	 */
	public ArrayList<SymTabEntry> sortedEntries()
	{
		Collection<SymTabEntry> entries = values();
		Iterator<SymTabEntry> iter = entries.iterator();
		ArrayList<SymTabEntry> list = new ArrayList<SymTabEntry>(size());
		
		// Iterates over the sorted entries and appends them to a list.
		while(iter.hasNext()) {
			list.add(iter.next());
		}
		
		return list; 	// Sorted list of entries.
	}

	@Override
	/**
	 * Getter for current nesting level.
	 * @return current nesting level.
	 */
	public int getNestingLevel() {
		return nestingLevel;
	}
}
		
