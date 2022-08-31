package frontend.pascal.parsers;

import java.util.EnumSet;
import java.util.HashMap;

import frontend.Token;
import frontend.TokenType;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.ICodeNodeType;
import intermediate.SymTabEntry;
import intermediate.icodeimpl.ICodeNodeTypeImpl;
import message.Message;

import static intermediate.icodeimpl.ICodeNodeTypeImpl.EQ;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.GT;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.GE;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.LE;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.LT;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.NE;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.ADD;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.NEGATE;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.SUBTRACT;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.INTEGER_DIVIDE;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.INTEGER_CONSTANT;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.FLOAT_DIVIDE;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.MULTIPLY;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.REAL_CONSTANT;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.VARIABLE;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.STRING_CONSTANT;
import static frontend.pascal.PascalErrorCode.IDENTIFIER_UNDEFINED;
import static frontend.pascal.PascalErrorCode.UNEXPECTED_TOKEN;
import static frontend.pascal.PascalErrorCode.MISSING_RIGHT_PAREN;
import static intermediate.icodeimpl.IcodeKeyImpl.VALUE;
import static message.MessageType.TOKEN;
import static intermediate.icodeimpl.IcodeKeyImpl.ID;
import static frontend.pascal.PascalTokenType.LESS_THAN;
import static frontend.pascal.PascalTokenType.EQUALS;
import static frontend.pascal.PascalTokenType.ERROR;
import static frontend.pascal.PascalTokenType.NOT_EQUALS;
import static frontend.pascal.PascalTokenType.GREATER_EQUALS;
import static frontend.pascal.PascalTokenType.LESS_EQUALS;
import static frontend.pascal.PascalTokenType.GREATER_THAN;
import static frontend.pascal.PascalTokenType.PLUS;
import static frontend.pascal.PascalTokenType.MINUS;
import static frontend.pascal.PascalTokenType.DIV;
import static frontend.pascal.PascalTokenType.STAR;
import static frontend.pascal.PascalTokenType.SLASH;
import static frontend.pascal.PascalTokenType.RIGHT_PAREN;

public class ExpressionParser extends StatementParser{

	public ExpressionParser(PascalParserTD parent) {
		super(parent);
	}

	/**
	 * Parse an expression.
	 * @param token the initial token.
	 * @return the root node of the generated parse tree.
	 * @throws Exception if an error occurred.
	 */
	public ICodeNode parse(Token token) 
		throws Exception
	{
		if(token.getType() != ERROR) {
			// Format each token
			sendMessage(new Message(TOKEN,
										new Object[] {
												token.getLineNumber(),
												token.getPosition(),
												token.getType(),
												token.getText(),
												token.getValue()
										}));
		}
		return parseExpression(token);
	}
	// Set of relational operators
				private static final EnumSet<PascalTokenType> REL_OPS =
						EnumSet.of(EQUALS, NOT_EQUALS, LESS_THAN, LESS_EQUALS,
								GREATER_THAN, GREATER_EQUALS);
				
				// Map of relational operator tokens to node types.
				private static final HashMap<PascalTokenType, ICodeNodeType>
					REL_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeType>();
				static {
					REL_OPS_MAP.put(EQUALS, EQ);
					REL_OPS_MAP.put(NOT_EQUALS, NE);
					REL_OPS_MAP.put(LESS_THAN, LT);
					REL_OPS_MAP.put(LESS_EQUALS, LE);
					REL_OPS_MAP.put(GREATER_THAN, GT);
					REL_OPS_MAP.put(GREATER_EQUALS, GE);
					
				};
				
	/**
	 * Parse an expression.
	 * @param token the initial token.
	 * @return the root of the generated parse subtree.
	 * @throws Exception if an error occurred.
	 */
	private ICodeNode parseExpression(Token token)
		throws Exception
		{
			
			// Parse a simple expression and make the root of its tree
			// the root node.
			ICodeNode rootNode = parseSimpleExpression(token);
			
			token = currentToken();
			TokenType tokenType = token.getType();
			
			// Look for relational operator.
			if (REL_OPS.contains(tokenType)) {
				
				// Create a new operator node and adopt the current tree
				// as its first child.
				ICodeNodeType nodeType = REL_OPS_MAP.get(tokenType);
				ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
				opNode.addChild(rootNode);
				
				token = nextToken();	// Consume operator.
				
				// Parse the second simple expression. The operator node
				// adopts the simple expression's tree as its second
				// child.
				opNode.addChild(parseSimpleExpression(token));
				
				// The operator node becomes the new root node.
				rootNode = opNode;
				
			}
			return rootNode;
		}
	//Set of additive operators
	private static final EnumSet<PascalTokenType> ADD_OPS =
			EnumSet.of(PLUS, MINUS, PascalTokenType.OR);
	
	// Map additive operator tokens to node types.
	private static final HashMap<PascalTokenType, ICodeNodeTypeImpl>
		ADD_OPS_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeTypeImpl>();
	static {
		ADD_OPS_OPS_MAP.put(PLUS, ADD);
		ADD_OPS_OPS_MAP.put(MINUS, SUBTRACT);
		ADD_OPS_OPS_MAP.put(PascalTokenType.OR, ICodeNodeTypeImpl.OR);
		
	};
	
	/**
	 * Parse a simple expression.
	 * @param token the initial token.
	 * @return the root of the generated parse subtree.
	 * @throws Exception if an error occurred.
	 */
	private ICodeNode parseSimpleExpression(Token token)
		throws Exception
		{
			TokenType signType = null;	// Type of leading sign (if any).
			
			// Look for a leading + or - sign.
			TokenType tokenType = token.getType();
			if ((tokenType == PLUS) || (tokenType == MINUS)) {
				signType = tokenType;
				token = nextToken();	// Consume the + or -
				
			}
			
			// Parse a term and make the root of its tree the root node.
			ICodeNode rootNode = parseTerm(token);
			
			// Checks for a leading - sign.
			if (signType == MINUS) {
				
				// Create a NEGATE node and adopt the current tree
				// as its child. The NEGATE node becomes the new root node.
				ICodeNode negateNode = ICodeFactory.createICodeNode(NEGATE);
				negateNode.addChild(rootNode);
				rootNode = negateNode;
				
			}
			
			token = currentToken();
			tokenType = token.getType();
			
			// Loop over the additive operators.
			while (ADD_OPS.contains(tokenType)) {
				// Create a new operator node and adopt the current tree
				// as its first child.
				ICodeNodeType nodeType = ADD_OPS_OPS_MAP.get(tokenType);
				ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
				opNode.addChild(rootNode);
				
				token = nextToken();	// Consume the operator.
				
				// Parse another term. The operator node adopts
				// the term's tree as its second child.
				opNode.addChild(parseTerm(token));
				
				// The operator node becomes the new root node.
				rootNode = opNode;
				
				token = currentToken();
				tokenType = token.getType();
				
			}
			return rootNode;
		}
	
	// Set of multiplicative operators.
	private static final EnumSet<PascalTokenType> MULT_OPS =
			EnumSet.of(STAR, SLASH, DIV, PascalTokenType.MOD, PascalTokenType.AND);
	
	// Map multiplicative operator tokens to node types.
	private static final HashMap<PascalTokenType, ICodeNodeType>
		MULT_OPS_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeType>();
	static {
		MULT_OPS_OPS_MAP.put(STAR, MULTIPLY);
		MULT_OPS_OPS_MAP.put(SLASH, FLOAT_DIVIDE);
		MULT_OPS_OPS_MAP.put(DIV, INTEGER_DIVIDE);
		MULT_OPS_OPS_MAP.put(PascalTokenType.MOD, ICodeNodeTypeImpl.MOD);
		MULT_OPS_OPS_MAP.put(PascalTokenType.AND, ICodeNodeTypeImpl.AND);
		
	};
	
	/**
	 * Parse a term.
	 * @param token the initial token.
	 * @return the root of the generated parse subtree.
	 * @throws Exception if an error occurred.
	 */
	private ICodeNode parseTerm(Token token)
		throws Exception
	{
		// Parse a factor and make its node the root node.
		ICodeNode rootNode = parseFactor(token);
		
		token = currentToken();
		TokenType tokenType = token.getType();
		
		// Loop over multiplicative operators.
		while (MULT_OPS.contains(tokenType)) {
			
			// Create a new operator node and adopt the current tree.
			// as its first child.
			ICodeNodeType nodeType = MULT_OPS_OPS_MAP.get(tokenType);
			ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
			opNode.addChild(rootNode);
			
			token = nextToken();	// Consume the operator.
			
			// Parse another factor. The operator node adopts.
			// the term's tree as its second child.
			opNode.addChild(parseFactor(token));
			
			// The operator node becomes the new root node.
			rootNode = opNode;
			
			token = currentToken();
			tokenType = token.getType();
		}
		return rootNode;
	}
	
	/**
	 * Parse a factor.
	 * @param token the initial token.
	 * @return the root of the generated parse subtree.
	 * @throws Exception if an error occurred.
	 */
	private ICodeNode parseFactor(Token token)
		throws Exception
	{
		TokenType tokenType = token.getType();
		ICodeNode rootNode = null;
		
		switch((PascalTokenType) tokenType) {
			case IDENTIFIER : {
				// Look up the identifier in the symbol table stack.
				// Flag the identifier as undefined if it's not found.
				String name = ((String) token.getText()).toLowerCase();
				SymTabEntry id = symTabStack.lookup(name);
				if (id == null) {
					errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
					id = symTabStack.enterLocal(name);
				}
				
				rootNode = ICodeFactory.createICodeNode(VARIABLE);
				rootNode.setAttribute(ID, id);
				id.appendLineNumber((int) token.getLineNumber());
				
				token = nextToken();	// Consume the identifier.
				break;
			}
			
			case INTEGER : {
				// Create an INTEGER_CONSTANT node as the root node.
				rootNode = ICodeFactory.createICodeNode(INTEGER_CONSTANT);
				rootNode.setAttribute(VALUE, token.getValue());
				
				token = nextToken();	// Consume the number.
				break;
			}
			
			case REAL : {
				// Create an REAL_CONSTANT node as the root node.
				rootNode = ICodeFactory.createICodeNode(REAL_CONSTANT);
				rootNode.setAttribute(VALUE, token.getValue());
				
				token = nextToken();	// Consume the number.
				break;
			}
			
			case STRING : {
				String value = (String) token.getValue();
				
				// Create a STRING_CONSTANT node as the root node.
				rootNode = ICodeFactory.createICodeNode(STRING_CONSTANT);
				rootNode.setAttribute(VALUE, value);
				
				token = nextToken();	// Consume the String.
				break;
			}
			
			case NOT: {
				token = nextToken();	// Consume the NOT.
				
				// Create a NOT node as the root node.
				rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NOT);
				
				// Parse the factor. The NOT node adopts the
				// factor node as its child.
				rootNode.addChild(parseFactor(token));
				
				break;
				
			}
			
			case LEFT_PAREN: {
				token = nextToken();	// Consume the (.
				
				// Parse an expression and make its node the root node.
				rootNode = parseExpression(token);
				
				// Look for the matching ) token.
				token = currentToken();
				if (token.getType() == RIGHT_PAREN) {
					token = nextToken(); // Consume the ).
					
				}
				else {
					errorHandler.flag(token, MISSING_RIGHT_PAREN, this);
				}
			
				break;
			}
			default: {
				errorHandler.flag(token, UNEXPECTED_TOKEN, this);
				break;
			}
		}
		return rootNode;
	}
	

}
