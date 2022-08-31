package frontend;
import static frontend.pascal.PascalTokenType.ERROR;

import frontend.pascal.PascalTokenType;

/**
 *<h1>EofToken</h1> 
 *
 *<p>The generic end-of-line token.</p>
 */
public class EofToken extends Token {
	/**
	 * Constructor.
	 * @param source the source from where to fetch tokens.
	 * @param endOfFile 
	 * @throws Exception if an error occurred.
	 */
	public EofToken(Source source, PascalTokenType endOfFile)
		throws Exception
	{
		super(source);
		this.type = endOfFile;
	}
	
	/**
	 * Do nothing. Do not consume any source characters.
	 * @param source the source to fetch tokens from.
	 * @throws Exception if an error occurred.
	 */
	protected void extract(Source source)
		throws Exception
	{
	}
}
