package intermediate;

import intermediate.symtablimpl.SymTabEntryImpl;
import intermediate.symtablimpl.SymTabImpl;
import intermediate.symtablimpl.SymTabStackImpl;

/**
 * <h1>SymTabFactory</h1>
 * 
 * <p>A factory that produces objects that implement symbol table</p>
 */
public class SymTabFactory {
	/**
	 * Create and return a symbol table stack implementation.
	 * @return the symbol table implementation.
	 */
	public static SymTabStack createSymTabStack()
	{
		return new SymTabStackImpl();
	}
	
	/**
	 * Create and return a symbol table implementation.
	 * @param nestingLevel the nesting level of stack.
	 * @return the symbol table implementation.
	 */
	public static SymTab createSymTab(int nestingLevel) {
		return new SymTabImpl(nestingLevel);
	}
	
	/**
	 * Create and return a symbol table implementation.
	 * @param name the identifier name.
	 * @param symTab the symbol table containing entry.
	 * @return the symbol table entry implementation.
	 */
	public static SymTabEntry createSymTabEntry(String name, SymTab symTab)
	{
		return new SymTabEntryImpl(name, symTab);
	}
}
