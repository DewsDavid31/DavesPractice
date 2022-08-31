package frontend.pascal;

import static frontend.Source.EOF;
import static frontend.pascal.PascalTokenType.END_OF_FILE;
import static frontend.pascal.PascalErrorCode.INVALID_CHARACTER;
import frontend.EofToken;
import frontend.Scanner;
import frontend.Source;
import frontend.Token;
import frontend.pascal.tokens.PascalErrorToken;
import frontend.pascal.tokens.PascalNumberToken;
import frontend.pascal.tokens.PascalSpecialSymbolToken;
import frontend.pascal.tokens.PascalStringToken;
import frontend.pascal.tokens.PascalWordToken;

/**
 * <h1>PascalScanner</h1> 
 *
 * <p>The Pascal Scanner.</p>
 */
public class PascalScanner extends Scanner{

	/**
	 * Constructor
	 * @param source the source to be used with this Scanner.
	 */
	public PascalScanner(Source source)
	{
		super(source);
	}
	
	/**
	 * Extract and return the next Pascal token from the source.
	 * @return the next Token.
	 * @throws Exception if an error occurred.
	 */
	protected Token extractToken()
		throws Exception
	{
		skipWhiteSpace();
		
		Token token;
		char currentChar = currentChar();
		
		// Construct the next token.
		// The current character determines the token type
		if (currentChar == EOF) {
			token = new EofToken(source, END_OF_FILE);
		}
		else if (Character.isLetter(currentChar)) {
			token = new PascalWordToken(source);
		}
		else if (Character.isDigit(currentChar)) {
			token = new PascalNumberToken(source);
		}
		else if (currentChar == '\'') {
			token = new PascalStringToken(source);
		}
		else if (PascalTokenType.SPECIAL_SYMBOLS
				.containsKey(Character.toString(currentChar))) {
			token = new PascalSpecialSymbolToken(source);
		}
		else {
			token = new PascalErrorToken(source, INVALID_CHARACTER,
										Character.toString(currentChar));
			nextChar();	// Consume character
		}
		
		return token;
	}

	/**
	 * Skips spaces in reading.
	 * @throws Exception if an error occurred.
	 */
	private void skipWhiteSpace()
			throws Exception {
		
		char currentChar = currentChar();
		
		while ((Character.isWhitespace(currentChar)) || (currentChar == '{')) {
			
			// Start of comment
			if (currentChar == '{') {
				do {
					currentChar = nextChar(); // Consume comment characters
				} while ((currentChar != '}') && (currentChar != EOF));
				
				// Found closing '}'?
				if (currentChar == '}') {
					currentChar = nextChar();	// Consume '}'
				}
			}
			else {
				currentChar = nextChar();		// Consume whitespace character
			}
		}
		
		
	}
	
	
}
