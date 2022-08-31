package frontend;

import message.Message;
import message.MessageHandler;
import message.MessageListener;
import message.MessageProducer;
import intermediate.ICode;
import intermediate.SymTab;
import intermediate.SymTabFactory;
import intermediate.SymTabStack;

/**
 * <h1>Parser</h1>
 * <p>A language-independent framework class. This abstract
 * parser class will be implemented by language-specific subclasses.
 * </p>
 *
 */
public abstract class Parser implements MessageProducer
{
	protected static SymTab symTab; 				// Generated symbol table.
	protected static SymTabStack symTabStack;		// Generated symbol table stack.
	protected static MessageHandler messageHandler; // Message handling delegate.
	
	static {
		symTab = null;
		symTabStack = SymTabFactory.createSymTabStack();
		messageHandler = new MessageHandler();
	}
	
	protected Scanner scanner; // Scanner to be used with this parser
	protected ICode iCode;
	
	/**
	 * Constructor.
	 * @param scanner the scanner to be used with this parser
	 */
	protected Parser(Scanner scanner)
	{
		this.scanner = scanner;
		this.iCode = null;
	}
	
	/**
	 * Parse a source program and generate the intermediate code
	 * and the symbol table. To be implemented by a language-specific
	 * parser subclass.
	 * @throws Exception if an error occurred.
	 */
	public abstract void parse()
		throws Exception;
	
	/**
	 * Return the number of syntax errors found by the parser.
	 * To be implemented by a language-specific parser subclass.
	 * @throws Exception if an error occurred.
	 */
	public abstract int getErrorCount();
	
	/**
	 * Call the scanner's currentToken() method.
	 * @return the current token.
	 *
	 */
	public Token currentToken()
	{
		return scanner.currentToken();
	}
	
	/**
	 * Call the scanner's nextToken() method.
	 * @return the next token.
	 * @throws Exception if an error occurred.
	 */
	public Token nextToken()
		throws Exception
	{
		return scanner.nextToken();
	}
	
	/**
	 * Add a listener to the listener list.
	 * @param listener the listener to add.
	 */
	public void addMessageListener(MessageListener listener) {
		messageHandler.addListener(listener);
	}
	
	/**
	 * Remove a listener from the listener list.
	 * @param listener the listener to remove.
	 */
	public void removeMessageListener(MessageListener listener) {
		messageHandler.removeListener(listener);
	}
	
	/**
	 * Notify listeners after setting the message.
	 * @param message the message to set.
	 */
	public void sendMessage(Message message) {
		messageHandler.sendMessage(message);
	}
	/**
	 * Getter for intermediate code.
	 * @return current intermediate code.
	 */
	public ICode getICode() {
		return iCode;
	}
	
	/**
	 * Getter for symbol table.
	 * @return current symbol table.
	 */
	public SymTab getSymTab() {
		return symTab;
	}

	/**
	 * Getter for symbol table stack.
	 * @return symbol table stack.
	 */
	public SymTabStack getSymStack() {
		return symTabStack;
	}

}
