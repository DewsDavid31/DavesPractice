package frontend.pascal.parsers;

import frontend.EofToken;
import frontend.Scanner;
import frontend.Token;
import frontend.TokenType;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;

import static frontend.pascal.PascalErrorCode.MISSING_SEMICOLON;
import static frontend.pascal.PascalErrorCode.UNEXPECTED_TOKEN;
import static frontend.pascal.PascalTokenType.SEMICOLON;
import static frontend.pascal.PascalTokenType.IDENTIFIER;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.NO_OP;
import static intermediate.icodeimpl.IcodeKeyImpl.LINE;


public class StatementParser extends PascalParserTD {

	public StatementParser(PascalParserTD parent) {
		super(parent);
	}


	/**
	 * Parse a statement.
	 * To be overridden by specialized statement parser subclass.
	 * @param token the initial token.
	 * @return root node by parse tree.
	 * @throws Exception if an error occurred.
	 */
	public ICodeNode parse(Token token)
		throws Exception
	{
		ICodeNode statementNode = null;
		
		switch ((PascalTokenType) token.getType()) {
			case BEGIN: {
				CompoundStatementParser compoundParser =
						new CompoundStatementParser(this);
				statementNode = compoundParser.parse(token);
				break;
			}
			// An assignment statement begins with a variable's identifier
			case IDENTIFIER: {
				AssignmentStatementParser assignmentParser =
						new AssignmentStatementParser(this);
				statementNode = assignmentParser.parse(token);
				break;
			}
			default: {
				statementNode = ICodeFactory.createICodeNode(NO_OP);
				break;
			}
		
		}
		// Set the current line number as an attribute.
		setLineNumber(statementNode, token);
		return statementNode;
	}


	/**
	 * Set the current line number as a statement node attribute.
	 * @param node ICodeNode
	 * @param token Token.
	 */
	protected void setLineNumber(ICodeNode node, Token token)
	{
		if (node != null)
		{
			node.setAttribute(LINE, token.getLineNumber());
		}
	}
	
	/**
	 * Parse a statement list.
	 * @param token the current token.
	 * @param parentNode the parent node of the statement list.
	 * @param terminator the token type that terminates the list.
	 * @param errorCode the errorCode if terminator is missing.
	 * @throws Exception if an error occurred.
	 */
	protected void parseList(Token token, ICodeNode parentNode,
							PascalTokenType terminator,
							PascalErrorCode errorCode)
		throws Exception
		{
		// Loop to parse each statement until the END token
		// or the end of the source file.
		while (!(token instanceof EofToken) &&
				(token.getType() != terminator)) {
			// Parse a statement. The parent node adopts the statement node.
			ICodeNode statementNode = parse(token);
			parentNode.addChild(statementNode);
			
			token = currentToken();
			TokenType tokenType = token.getType();
			
			// Look for the semicolon between statements.
			if (tokenType == SEMICOLON)
			{
				token = nextToken(); // Consume the ;.
			}
			
			// If at the start of the next assignment statement,
			// find missing a semicolon.
			else if (tokenType == IDENTIFIER)
			{
				errorHandler.flag(token, MISSING_SEMICOLON, this);
			}
			
			// Unexpected token.
			else if (tokenType != terminator)
			{
				errorHandler.flag(token, UNEXPECTED_TOKEN, this);
				token = nextToken();	// Consume the unexpected token.
			}
		}
		
		// Look for the terminator token.
		if (token.getType() == terminator)
		{
			token = nextToken();	// Consume terminator token.
		}
		else {
			errorHandler.flag(token, errorCode, this);
		}
	}

}
