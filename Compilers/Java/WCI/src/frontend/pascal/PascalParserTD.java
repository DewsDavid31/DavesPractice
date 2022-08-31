package frontend.pascal;

import frontend.EofToken;
import frontend.Parser;
import frontend.Scanner;
import frontend.Token;
import frontend.TokenType;
import frontend.pascal.parsers.StatementParser;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.SymTabEntry;
import message.Message;

import static message.MessageType.PARSER_SUMMARY;
import static frontend.pascal.PascalTokenType.IDENTIFIER;
import static frontend.pascal.PascalTokenType.ERROR;
import static frontend.pascal.PascalErrorCode.IO_ERROR;
import static frontend.pascal.PascalErrorCode.UNEXPECTED_TOKEN;
import static frontend.pascal.PascalErrorCode.MISSING_PERIOD;
import static frontend.pascal.PascalTokenType.DOT;
import static frontend.pascal.PascalTokenType.BEGIN;
import static message.MessageType.TOKEN;
/**
 * <h1>PascalParserTD</h1> 
 *
 * <p>The top-down Pascal parser.</p>
 */
public class PascalParserTD extends Parser
{
	/**
	 * Constructor.
	 * @param scanner the scanner to be used with this parser.
	 */
	public PascalParserTD(Scanner scanner)
	{
		super(scanner);
	}
	
	/**
	 * Constructor for subclasses.
	 * @param parent the parent parsers.
	 */
	public PascalParserTD(PascalParserTD parent)
	{
		super(parent.getScanner());
	}
	/**
	 * Getter for scanner for subclasses.
	 * @return current scanner.
	 */
	private Scanner getScanner() {
		return scanner;
	}
	
	protected static PascalErrorHandler errorHandler = new PascalErrorHandler();
	
	/**
	 * Parse a Pascal source program and generate the symbol table
	 * and the intermediate code
	 * @throws Exception if an error occurred.
	 */
	public void parse()
		throws Exception
	{
		long startTime = System.currentTimeMillis();
		iCode = ICodeFactory.createICode();
		try {

			Token token = nextToken();
			ICodeNode rootNode = null;
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
			// Look for the BEGIN token to parse a compound statement.
			if (token.getType() == BEGIN) {
				StatementParser statementParser = new StatementParser(this);
				rootNode = statementParser.parse(token);
				token = currentToken();
			}
			else {
				errorHandler.flag(token, UNEXPECTED_TOKEN, this);
			}
			
			// Look for final period.
			if(token.getType() != DOT) {
				errorHandler.flag(token, MISSING_PERIOD, this);
			}
			token = currentToken();
			
			// Set the parse tree root node.
			if (rootNode != null) {
				iCode.setRoot(rootNode);
			}
			
			// Send the parser summary message
			float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
			sendMessage(new Message(PARSER_SUMMARY,
									new Number[] {token.getLineNumber(),
														getErrorCount(),
														elapsedTime}));
		}
		catch(java.io.IOException ex) {
			errorHandler.abortTranslation(IO_ERROR, this);
		}	
	}
	/**
	 * Return the number of syntax errors found by the parser.
	 * @return the error count.
	 */
	public int getErrorCount()
	{
		return errorHandler.getErrorCount();
	}

}
