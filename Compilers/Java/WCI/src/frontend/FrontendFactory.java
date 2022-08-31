package frontend;

import frontend.pascal.PascalScanner;
import frontend.pascal.PascalParserTD;

/**
 *<h1>FrontendFactory</h1> 
 *
 *<p>A factory class that generates parsers for specific source
 *languages.</p>
 */
public class FrontendFactory {
	/**
	 * Create a parser.
	 * @param language the name of source language.
	 * @param type the type of parser.
	 * @param source the source object.
	 * @return the parser.
	 * @throws Exception if an error occurred.
	 */
	public static Parser createParser(String language, String type,
													Source source)
		throws Exception
	{
		if(language.equalsIgnoreCase("Pascal") &&
				type.equalsIgnoreCase("top-down"))
		{
			Scanner scanner = new PascalScanner(source);
			return new PascalParserTD(scanner);
		}
		else if(!language.equalsIgnoreCase("Pascal")) {
			throw new Exception("Parser factory: Invalid language '" +
								language + "'");
		}
		else {
			throw new Exception("Parser factory: Invalid type '" + 
								type + "'");
		}
	}
}
