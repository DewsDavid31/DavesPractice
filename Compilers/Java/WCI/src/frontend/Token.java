package frontend;
import static frontend.pascal.PascalTokenType.ERROR;
/**
 * <h1>Token</h1>
 * 
 * <p>The framework that represents the token returned by scanner.</p>
 */
public class Token {
	protected TokenType type;			// Language-specific token type.
	protected String text;				// Token text.
	protected Object value;				// Contained value in token.
	protected Source source;			// Source token is from.
	protected int lineNum;				// Line token is from.
	protected int position;				// Position token is from.
	/**
	 * Constructor.
	 * @param source the source to fetch tokens from.
	 * @throws Exception if an error occurred.
	 */
	public Token(Source source)
		throws Exception
	{
		this.source = source;
		this.lineNum = source.getLineNum();
		this.position = source.getPosition();
		this.type = ERROR;
		extract();
	}
	
	/**
	 * Default method to extract only one-character tokens from source.
	 * Subclasses can override this method to construct language-specific
	 * tokens. After extracting the token, the current source line
	 * position will be one beyond the last character.
	 * @throws Exception if an error occurred.
	 */
	protected void extract()
		throws Exception
	{
		text = Character.toString(currentChar());
		value = null;
		
		nextChar();		// Consume current character.
	}
	
	/**
	 * Call the source's currentChar() method.
	 * @return the current character from the source.
	 * @throws Exception if an error occurred.
	 */
	protected char currentChar()
		throws Exception	
	{
		return source.currentChar();
	}
	
	/**
	 * Call the source's nextChar() method.
	 * @return the next character from the source.
	 * @throws Exception if an error occurred.
	 */
	protected char nextChar()
		throws Exception
	{
		return source.nextChar();
	}
	
	/**
	 * Call the source's peekChar() method.
	 * @return the next character from source without moving forward.
	 * @throws Exception if an error occurred.
	 */
	protected char peekChar()
		throws Exception
	{
		return source.peekChar();
	}

	/**
	 * Getter for line number.
	 * @return line number of token.
	 */
	public Number getLineNumber() {
		return lineNum;
	}

	/**
	 * Getter for position.
	 * @return position number.
	 */
	public Object getPosition() {
		return position;
	}

	/**
	 * Getter for text of token.
	 * @return text.
	 */
	public Object getText() {
		
		return text;
	}
	
	/**
	 * Getter for token's value.
	 * @return value of token.
	 */
	public Object getValue() {
	
		return value;
	}

	/**
	 * Getter for token's type.
	 * @return current token type.
	 */
	public TokenType getType() {
		return type;
	}
}
