package frontend.pascal.parsers;

import frontend.Token;
import frontend.pascal.PascalParserTD;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import message.Message;

import static intermediate.icodeimpl.ICodeNodeTypeImpl.COMPOUND;
import static message.MessageType.TOKEN;
import static frontend.pascal.PascalTokenType.END;
import static frontend.pascal.PascalTokenType.ERROR;
import static frontend.pascal.PascalErrorCode.MISSING_END;

public class CompoundStatementParser extends StatementParser{
	

	public CompoundStatementParser(PascalParserTD parent) {
		super(parent);
	}

	/**
	 * Parse a compound statement.
	 * @param token the initial token.
	 * @return the root node of the generated parse tree.
	 * @throws Exception if an error occurred.
	 */
	public ICodeNode parse(Token token)
		throws Exception
	{
		token = nextToken(); // Consume the BEGIN.
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
		// Create COMPOUND node.
		ICodeNode compoundNode = ICodeFactory.createICodeNode(COMPOUND);
		
		// Parse the statement list terminated by END token.
		StatementParser statementParser = new StatementParser(this);
		statementParser.parseList(token, compoundNode, END, MISSING_END);
		
		return compoundNode;
	}
}
