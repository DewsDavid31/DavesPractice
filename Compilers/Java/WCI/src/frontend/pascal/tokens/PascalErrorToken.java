package frontend.pascal.tokens;

import frontend.*;
import frontend.pascal.*;

import static frontend.pascal.PascalTokenType.*;

/**
 * <h1>PascalErrorToken</h1>
 *
 * <p>Pascal error token.</p>
 */
public class PascalErrorToken extends PascalToken{

	/**
	 * Constructor.
	 * @param source, the source from where to fetch
	 * subsequent characters.
	 * @param errorCode the error code.
	 * @param tokenText the text of erroneous token.
	 * @throws Exception if an error occurred. 
	 */
	public PascalErrorToken(Source source, PascalErrorCode errorCode,
							String tokenText)
		throws Exception
	{
		super(source);
		
		this.text = tokenText;
		this.type = ERROR;
		this.value = errorCode;
		
	}
	
	/**
	 * Do nothing.
	 * Does not consume any source characters.
	 * @throws Exception if an error occurred.
	 */
	protected void extract()
	throws Exception
	{
	}
}
