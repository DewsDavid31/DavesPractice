package frontend.pascal.tokens;
import static frontend.pascal.PascalTokenType.STRING;
import static frontend.pascal.PascalTokenType.RESERVED_WORDS;
import static frontend.pascal.PascalTokenType.IDENTIFIER;

import frontend.Source;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalToken;
import frontend.pascal.PascalTokenType;


/**
 * <h1>PascalWordToken</h1> 
 *
 * <p>Token for Pascal alpha-numerical words</p>
 */
public class PascalWordToken extends PascalToken {
	/** constructor. 
	 * @param source current source used for token.
	 */
	public PascalWordToken(Source source)
		throws Exception
{
		super(source);
		

}

	/**
	 * Extract a Pascal word from the source.
	 * @throws Exception if an error occurred.
	 */
	protected void extract()
		throws Exception
		{
			StringBuilder textBuffer = new StringBuilder();
			char currentChar = currentChar();
			
			// Get the word characters (letter or digit).
			// The scanner has already found first char is a letter.
			while (Character.isLetterOrDigit(currentChar)) {
				textBuffer.append(currentChar);
				currentChar = nextChar();	// Consume character
			}
			
			text = textBuffer.toString();
			
			// Check if reserved word or identifier
			type = (RESERVED_WORDS.contains(text.toLowerCase()))
					? PascalTokenType.valueOf(text.toUpperCase())
					: IDENTIFIER;
					// ? Reserved word : Identifier
		}
	

	
}
