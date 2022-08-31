package frontend.pascal.parsers;

import static frontend.pascal.PascalErrorCode.MISSING_COLON_EQUALS;
import static frontend.pascal.PascalTokenType.COLON_EQUALS;
import static frontend.pascal.PascalTokenType.ERROR;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.ASSIGN;
import static intermediate.icodeimpl.ICodeNodeTypeImpl.VARIABLE;
import static intermediate.icodeimpl.IcodeKeyImpl.ID;
import static message.MessageType.TOKEN;

import frontend.Token;
import frontend.pascal.PascalParserTD;
import intermediate.ICodeFactory;
import intermediate.ICodeKey;
import intermediate.ICodeNode;
import intermediate.SymTabEntry;
import message.Message;
public class AssignmentStatementParser extends PascalParserTD{

	

	public AssignmentStatementParser(PascalParserTD parent) {
		super(parent);
	}
	
	/**
	 * Parse an assignment statement.
	 * @param token the initial token.
	 * @return the root node for the generated parse tree.
	 * @throws Exception if an error occurred.
	 */
	public ICodeNode parse(Token token)
		throws Exception
		{
			// Create the ASSIGN node.
			ICodeNode assignNode = ICodeFactory.createICodeNode(ASSIGN);
			
			// Look up the target identifier in the symbol table stack.
			// Enter the identifier if it is not found.
			String targetName = ((String) token.getText()).toLowerCase();
			SymTabEntry targetId = symTabStack.lookup(targetName);
			if (targetId == null) {
				targetId = symTabStack.enterLocal(targetName);
			}
			targetId.appendLineNumber((int)token.getLineNumber());
			
			token = nextToken();	// Consume identifier token.
			
			// Create the variable node and set its name attribute.
			ICodeNode variableNode = ICodeFactory.createICodeNode(VARIABLE);
			variableNode.setAttribute(ID, targetId);
			
			// The ASSIGN node adopts the variable node as its first child.
			assignNode.addChild(variableNode);
			
			// Look for the := token.
			if (token.getType() == COLON_EQUALS) {
				token = nextToken();	// Consume the :=
			}
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
			else {
				errorHandler.flag(token, MISSING_COLON_EQUALS, this);
			}
			
			// Parse the expression. The ASSIGN node adopts the expression's
			// node as its second child.
			ExpressionParser expressionParser = new ExpressionParser(this);
			assignNode.addChild(expressionParser.parse(token));
			
			return assignNode;
			
		}
 
}
