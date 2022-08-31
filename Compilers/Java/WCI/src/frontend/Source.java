package frontend;

import java.io.BufferedReader;
import java.io.IOException;

import message.Message;
import message.MessageListener;
import message.MessageProducer;

import static message.MessageType.SOURCE_LINE;
/**
 * <h1>Source</h1>
 * 
 * <p>The framework class that represents the source program.</p>
 */
public class Source implements MessageProducer{
	public static final char EOL = '\n';
	public static final char EOF = (char) 0;
	
	private BufferedReader reader;
	private String line;
	private int lineNum;
	private int currentPos;
	
	/**
	 * Constructor.
	 * @param reader the reader for the source program.
	 * @throws IOException if an I/O error occurred.
	 */
	public Source(BufferedReader reader)
		throws IOException
	{
		this.lineNum = 0;
		this.currentPos = -2; // Set to -2 to read first source line
		this.reader = reader;
	}
	
	/**
	 * Return the source character at the current position.
	 * @return the source character at the current position.
	 * @throws Exception if an error occurred.
	 * 
	 */
	public char currentChar()
		throws Exception
	{
		// First line reached
		if(currentPos == -2) {
			readLine();
			return nextChar();
		}
		
		// End of file reached
		else if(line == null) {
			return EOF;
		}
		
		// End of line reached
		else if((currentPos == -1) || (currentPos == line.length())) {
				return EOL;
		}
		
		// Need to read the next line
		else if (currentPos > line.length()) {
			readLine();
			return nextChar();
		}
		
		// Character can be returned at position
		else {
			return line.charAt(currentPos);
		}
	}
	
	/**
	 * Consume the current source character and return the next character.
	 * @return the next source character.
	 * @throws Exception if an error occurred.
	 */
	public char nextChar()
		throws Exception
	{
		++currentPos;
		return currentChar();
	}
	
	/**
	 * Return the source character following the current character without
	 * consuming the current character.
	 * @return the following character.
	 * @throws Exception if an error occurred.
	 */
	public char peekChar()
		throws Exception
	{
		currentChar();
		if(line == null) {
			return EOF;
		}
		
		int nextPos = currentPos + 1;
		return nextPos < line.length() ? line.charAt(nextPos) : EOL;
	}
	
	/**
	 * Read the next source line.
	 * @throws IOException if an I/O error occurred.
	 */
	private void readLine()
		throws IOException
	{
		line = reader.readLine(); // Null when at the end of the source.
		currentPos = -1;
		
		if(line != null) {
			++lineNum;
		}
		
		// Sending a source line message containing the line number
		// and the line text to all the listeners.
		if(line != null) {
			sendMessage(new Message(SOURCE_LINE,
									new Object[] {lineNum, line}));
		}
	}
	/**
	 * Close the source.
	 * @throws Exception if an error occurred.
	 */
	public void close()
		throws Exception
	{
		if(reader != null) {
			try {
				reader.close();
			}
			catch(IOException ex) {
				ex.printStackTrace();
				throw ex;
			}
		}
	}
	
	/**
	 * Gets current line number.
	 * @return 
	 * @return line number.
	 */
	public int getLineNum()
	{
		return this.lineNum;
	}
	
	/**
	 * Gets current position.
	 * @return 
	 * @return position.
	 */
	public int getPosition()
	{
		return this.currentPos;
	}

	@Override
	public void addMessageListener(MessageListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMessageListener(MessageListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub
		
	}
}
