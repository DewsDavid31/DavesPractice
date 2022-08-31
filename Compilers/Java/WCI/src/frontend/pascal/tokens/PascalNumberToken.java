package frontend.pascal.tokens;

import static frontend.pascal.PascalTokenType.ERROR;
import static frontend.pascal.PascalTokenType.REAL;
import static frontend.pascal.PascalTokenType.INTEGER;
import static frontend.pascal.PascalErrorCode.RANGE_REAL;
import static frontend.pascal.PascalErrorCode.INVALID_NUMBER;
import static frontend.pascal.PascalErrorCode.RANGE_INTEGER;

import frontend.Source;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalToken;
/**
* <h1>PascalSpecialSymbolToken</h1> 
*
* <p> Token for special symbols in Pascal.</p>
*/
public class PascalNumberToken extends PascalToken {

	private static final int MAX_EXPONENT = 99;
	
			/**
			 * Constructor.
			 * @param source source object used for token.
			 * @param tokenText text contained in token.
			 * @param errorCode error code from token.
			 * @throws Exception if an error occurred.
			 */
			public PascalNumberToken(Source source) 
				throws Exception
			{
				super(source);
				
			}
	
			/**
			 * Extracts number token calling all helper methods.
			 * @throws Exception if an error occurred.
			 */
			protected void extract()
				throws Exception
				{
					StringBuilder textBuffer = new StringBuilder();
					extractNumber(textBuffer);
				}
			/**
			 * Extract a Pascal Number token from the source.
			 * @param textBuffer the buffer to append the token's 
			 * characteristics.
			 * @throws Exception if an error occurred.
			 */
			protected void extractNumber(StringBuilder textBuffer)
				throws Exception
			{
				String wholeDigits = null;		// Digits before decimal point.
				String fractionDigits = null;	// Digits after decimal point.
				String exponentDigits = null;	// Exponential digits.
				char exponentSign = '+';		// Exponent sign either '+' or '-'.
				boolean sawDotDot = false;		// True if saw .. token.
				char currentChar;				// current character.
		
				type = INTEGER;					// Assume Integer token for now.
				
				// Extract the digits of the whole part of number.
				wholeDigits = unsignedIntegerDigits(textBuffer);
				if (type == ERROR) {
					return;
				}
				
				// Checking for a .
				// Either as decimal or .. token
				currentChar = currentChar();
				if (currentChar == '.') {
					if (peekChar() == '.') {
						sawDotDot = true;	// Verified as a '..' token.
					}
					else {
						type = REAL; // Decimal point forces a Real number.
						textBuffer.append(currentChar);
						currentChar = nextChar(); // Consume decimal point.
						
						// Collection of fractional digits.
						fractionDigits = unsignedIntegerDigits(textBuffer);
						if (type == ERROR) {
							return;
						}
					}
				}
				// Checking for exponent.
				// No exponent possible with '..'
				currentChar = currentChar();
				if (!sawDotDot && ((currentChar) == 'E') || (currentChar == 'e')) {
					type = REAL;	// Exponent verified, thus REAL
					textBuffer.append(currentChar);
					currentChar = nextChar(); // Consume 'E' or 'e'.
					
					// Finding exponent sign.
					if ((currentChar == '+') || (currentChar == '-')) {
						textBuffer.append(currentChar);
						currentChar = nextChar();	// Consume '+' or '-'.
						
					}
					
					// Extraction of exponential digits
					exponentDigits = unsignedIntegerDigits(textBuffer);
					
				}
				
				// Compute the value of an integer number token.
				if (type == INTEGER) {
					int integerValue = computeIntegerValue(wholeDigits);
					
					if (type != ERROR) {
						value = new Integer(integerValue);
					}
				}
				// Compute value of Real number token
				else if (type == REAL) {
					float floatValue = computeFloatValue(wholeDigits, fractionDigits,
														exponentDigits, exponentSign);
					
					if (type != ERROR) {
						value = new Float(floatValue);
					}
				}
			}
			
			/**
			 * Extract and return the digits of an unsigned integer.
			 * @param textBuffer the buffer used to append characters.
			 * @return the string of digits.
			 * @throws Exception if an error occurred.
			 */
			private String unsignedIntegerDigits(StringBuilder textBuffer)
				throws Exception
			{
				char currentChar = currentChar();
				
				// Must contain one digit.
				if (!Character.isDigit(currentChar)) {
					type = ERROR;
					value = INVALID_NUMBER;
					return null;
				}
				
				// Extract the digits.
				StringBuilder digits = new StringBuilder();
				while (Character.isDigit(currentChar)) {
					textBuffer.append(currentChar);
					digits.append(currentChar);
					currentChar = nextChar(); // Consume digit
				}
				
				return digits.toString();
		}
			
			/**
			 * Compute and return the float value of a real number.
			 * @param wholeDigits the string of digits before decimal point.
			 * @param fractionalDigits the string of digits after decimal point.
			 * @param exponentialDigits the string of exponential digits.
			 * @param exponentSign the exponent sign.
			 * @return the float value.
			 */
			private float computeFloatValue(String wholeDigits, String fractionDigits,
											String exponentDigits,  char exponentSign)
			{
				double floatValue = 0.0;
				int exponentValue = computeIntegerValue(exponentDigits);
				String digits = wholeDigits;	// Whole and fractionalDigits.
				
				// Negate the exponent if the exponent sign is '-'.
				if (exponentSign == '-') {
					exponentValue = -exponentValue;
				}
				
				// If there are any fractional digits, they adjust the exponent here
				if (Math.abs(exponentValue + wholeDigits.length()) > MAX_EXPONENT) {
					type = ERROR;
					value = RANGE_REAL;
					return 0.0f;
				}
				
				// Loop over the digits to compute the float value
				int index = 0;
				while (index < digits.length()) {
					floatValue = 10*floatValue +
							Character.getNumericValue(digits.charAt(index++));
				}
				
				// Adjust the float value based on exponential value.
				if (exponentValue != 0) {
					floatValue *= Math.pow(10, exponentValue);
				}
				
				return (float) floatValue;
			}
			
			/**
			 * Compute and return the integer value of a string of digits.
			 * Checks for overflow.
			 * @param digits the string of digits.
			 * @return the integer value.
			 */
			private int computeIntegerValue(String digits)
			{
				// Returns 0 if no digits
				if (digits == null) {
					return 0;
				}
				
				int integerValue = 0;	// Current calculated value.
				int prevValue = -1;		// Overflow if prevValue > integerValue.
				int index = 0;
				
				// Loop over the digits to compute the integer value.
				// as long as there is no overflow.
				while ((index < digits.length()) && (integerValue > prevValue)) {
					prevValue = integerValue;
					integerValue = 10 * integerValue +
										Character.getNumericValue(digits.charAt(index++));
					
				}
				
				// No overflow found.
				if (integerValue >= prevValue) {
					return integerValue;
				}
				
				// Overflow occurred.
				else {
					type = ERROR;
					value = RANGE_INTEGER;
					return 0;
				}
			}
}
			


