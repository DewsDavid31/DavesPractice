package frontend.pascal.tokens;
import static frontend.pascal.PascalTokenType.STRING;
import static frontend.pascal.PascalTokenType.ERROR;
import static frontend.pascal.PascalErrorCode.UNEXPECTED_EOF;
import static frontend.Source.EOF;
import static frontend.pascal.PascalTokenType.STRING;
import frontend.Source;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalToken;

/**
 * <h1>PascalStringToken</h1> 
 *
 * <p> Token for string literals in Pascal.</p>
 */
public class PascalStringToken extends PascalToken {

	/**
	 * Constructor.
	 * @param source source object used for token.
	 * @param tokenText text contained in token.
	 * @param errorCode error code from token.
	 * @throws Exception if an error occurred.
	 */
	public PascalStringToken(Source source) 
			throws Exception {
		super(source);
		
	}
	
	/**
	 * Extract a Pascal string token from the source.
	 * @throws Exception if an error occurred.
	 */
	protected void extract()
		throws Exception
	{
		StringBuilder textBuffer = new StringBuilder();
		StringBuilder valueBuffer = new StringBuilder();
		
		char currentChar = nextChar();	// Consumes initial quote.
		textBuffer.append('\'');
		
		// Getting string characters
		do {
			// Replace any whitespace with a blank
			if (Character.isWhitespace(currentChar)) {
				currentChar = ' ';
			}
			
			if ((currentChar != '\'') && (currentChar != EOF)) {
				textBuffer.append(currentChar);
				valueBuffer.append(currentChar);
				currentChar = nextChar();	// Consume character.
			}
			
			// If a quote each pair of adjacent quotes is a single-quote
			if (currentChar == '\'') {
				while((currentChar == '\'') && (peekChar() == '\'')) {
					textBuffer.append("''");
					valueBuffer.append(currentChar);	// Append single-quote
					currentChar = nextChar();			// Consume both quotes
					currentChar = nextChar();
				}
			}
		} while ((currentChar != '\'') && (currentChar != EOF));
		
		if (currentChar == '\'') {
			nextChar(); 		// Consume final quote
			textBuffer.append('\'');
			
			type = STRING;
			value = valueBuffer.toString();
		}
		else {
			type = ERROR;
			value = UNEXPECTED_EOF;
		}
		
		text = textBuffer.toString();
	}
	

}
