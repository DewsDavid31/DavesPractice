package intermediate.icodeimpl;

import intermediate.ICodeNodeType;

/**
 *<h1>ICodeNodeTypeImpl</h1> 
 *
 *<p>Node types of the intermediate code parse tree.</p>
 */
public enum ICodeNodeTypeImpl implements ICodeNodeType
{
	// Program structure.
	PROGRAM, PROCEDURE, FUNCTION,
	
	// Statements.
	COMPOUND, ASSIGN, LOOP, TEST, CALL, PARAMETERS,
	IF, SELECT, SELECT_BRANCH, SELECT_CONSTANTS, NO_OP,
	
	// Relational operators. Similar to assembler.
	EQ, NE, LT, LE, GT, GE, NOT,
	
	// Additive operators.
	ADD, SUBTRACT, OR, NEGATE,
	
	// Multiplicative operators
	MULTIPLY, INTEGER_DIVIDE, FLOAT_DIVIDE, MOD, AND,
	
	// Operands
	VARIABLE, SUBSCRIPT, FIELD,
	INTEGER_CONSTANT, REAL_CONSTANT,
	STRING_CONSTANT, BOOLEAN_CONSTANT,
}
