package intermediate.symtablimpl;

import intermediate.SymTabKey;

/**
 * <h1>SymTabKeyImpl</h1> 
 *
 * <p>Attribute keys for a symbol table entry.</p>
 */
public enum SymTabKeyImpl implements SymTabKey{
	// Constant.
	CONSTANT_VALUE,
	
	// Procedures and functions.
	ROUTINE_CODE, ROUTINE_SYMTAB, ROUTINE_ICODE,
	ROUTINE_PARMS, ROUTINE_ROUTINES,
	
	// Variable or record field values.
	DATA_VALUE,
}
