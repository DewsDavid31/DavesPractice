package frontend.pascal.tokens;
import static frontend.pascal.PascalTokenType.ERROR;
import static frontend.pascal.PascalTokenType.SPECIAL_SYMBOLS;
import static frontend.pascal.PascalErrorCode.INVALID_CHARACTER;

import frontend.Source;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalToken;

/**
 * <h1>PascalSpecialSymbolToken</h1> 
 *
 * <p> Token for special symbols in Pascal.</p>
 */
public class PascalSpecialSymbolToken extends PascalToken {

	
		/**
		 * Constructor.
		 * @param source source object used for token.
		 * @param tokenText text contained in token.
		 * @param errorCode error code from token.
		 * @throws Exception if an error occurred.
		 */
		public PascalSpecialSymbolToken(Source source) 
			throws Exception
		{
			super(source);

		}
		
		protected void extract()
			throws Exception
		{
			char currentChar = currentChar();
			
			text = Character.toString(currentChar);
			type = null;
			
			switch(currentChar) {
				case '+': case '-': case '*' : case ',' :
				case ';': case '\'': case '=' :
				case '(': case ')': case '^' : case '[' :
				case ']': case '{' : case '}' :{
					nextChar(); // Consume character.
					break;
				}
				case ':' : {
					currentChar = nextChar(); // Consume ':'.
					
					if (currentChar == '=') {
						text += currentChar;
						nextChar();	// Consume '='.
					}
					else if (currentChar == '>') {
						text += currentChar;
						nextChar(); // Consume '>'.
					}
					break;
				}
				
				// > or >=
				case '>' : {
					currentChar = nextChar(); // Consume '>'.
					
					if (currentChar == '=') {
						text += currentChar;
						nextChar();	// Consume '='.
					}
					break;
				}
				// . or ..
				case '.' : {
					currentChar = nextChar(); // Consume '.'.
					
					if (currentChar == '.') {
						text += currentChar;
						nextChar(); // Consume '.'.
					}
					break;
				}
				
				default: {
					nextChar(); // Consume incorrect character
					type = ERROR;
					value = INVALID_CHARACTER;
				}
			}
			
			// Set the type if there wasn't an error.
			if (type == null) {
				type = SPECIAL_SYMBOLS.get(text);
			}
		}
}
