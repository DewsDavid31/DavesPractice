package frontend.pascal;

import frontend.*;
/**
 * <h1>PascalToken</h1>
 *
 * <p>Base class for Pascal token classes</p>
 */
public class PascalToken extends Token
{
	/**
	 * Constructor.
	 * @param source the source where to fetch tokens.
	 * @throws Exception if an error occurred.
	 */
	protected PascalToken(Source source)
			throws Exception
	{
		super(source);
	}

}
