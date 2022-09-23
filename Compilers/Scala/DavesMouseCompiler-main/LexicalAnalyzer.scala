/*
 * CS3210 - Principles of Programming Languages - Fall 2021
 * Instructor: Thyago Mota
 * Student(s):
 * Description: Prg 01 - LexicalAnalyzer (an iterable lexical analyzer)
 */

import LexicalAnalyzer.{BLANKS, DIGITS, LETTERS, NEW_LINE, PUNCTUATIONS, SPECIALS}
import scala.io.Source

class LexicalAnalyzer(private var source: String) extends Iterable[Lexeme]{

  var input = ""
  for (line <- Source.fromFile(source).getLines)
    input += line + NEW_LINE
  input = input.trim

  // checks if reached eof
  private def eof: Boolean = {
    input.length == 0
  }

  // returns the current char (requires checking for eof before call)
  private def getChar(): Char = {
    input(0)
  }

  // advances the input one character (requires checking for eof before call)
  private def nextChar() = {
    input = input.substring(1)
  }

  // checks if input has a blank character ahead
  private def hasBlank(): Boolean = {
    BLANKS.contains(getChar)
  }

  // reads the input until a non-blank character is found, updating the input
  def readBlanks: Unit = {
    var foundNonBlank = false
    while (!eof && !foundNonBlank) {
      val c = getChar
      if (hasBlank)
        nextChar
      else
        foundNonBlank = true
    }
  }

  // checks if input has a letter ahead
  private def hasLetter(): Boolean = {
    LETTERS.contains(getChar)
  }

  // checks if input has a digit ahead
  private def hasDigit(): Boolean = {
    DIGITS.contains(getChar)
  }

  // checks if input has a special character ahead
  private def hasSpecial(): Boolean = {
    SPECIALS.contains(getChar)
  }

  // checks if input has a punctuation character ahead
  private def hasPunctuation(): Boolean = {
    PUNCTUATIONS.contains(getChar)
  }

  // returns an iterator for the lexical analyzer
  override def iterator: Iterator[Lexeme] = {

    new Iterator[Lexeme] {

      // returns true/false depending whether there is a lexeme to be read from the input
      override def hasNext: Boolean = {
        readBlanks
        !eof
      }

      // returns the next lexeme (or end of line if there isn't any lexeme left to be read)
      // TODO: finish this part of the code
      override def next(): Lexeme = {
        var c = getChar()
        while(BLANKS.contains(c)) { // skip blanks on their own
          nextChar()
          c = getChar()
        }
        if (c == '*') {
          nextChar()
          return new Lexeme(c + "", Token.SPLAT)
        }
        if (PUNCTUATIONS.contains(c)) {
          if(c == '!'){
            nextChar()
            return new Lexeme(c + "", Token.BANG)
          }
          if(c == '?'){
            nextChar()
            return new Lexeme(c + "", Token.QUESTION)
          }
          if(c == '.'){
            nextChar()
            return new Lexeme(c + "", Token.DOT)
          }
          nextChar()
          return new Lexeme(c + "", Token.PUNCTUATION)
        }
        else if (LETTERS.contains(c)) {
          var str = c + ""
          nextChar()
          return new Lexeme(str, Token.IDENTIFIER)
        }
        else if (DIGITS.contains(c)) {
          var str = c + ""
          nextChar()
          c = getChar()
          while (DIGITS.contains(c)) {
            str += c
            nextChar()
            c = getChar()
          }
          return new Lexeme(str, Token.LITERAL)
        }
        else if (c == '"'){
          var str = ""
          nextChar()
          c = getChar()
          while (c != '"'){
            str += c
            nextChar()
            c = getChar()
          }
          nextChar()
          return new Lexeme(str, Token.STRING)
        }
        else if (c == '\''){
          var str = c + ""
          nextChar()
          c = getChar()
          while (c != '\n'){
            str += c
            nextChar()
            c = getChar()
          }
          str += c
          nextChar()
          return new Lexeme(str, Token.COMMENT)
        }
        else if (SPECIALS.contains(c)) {
          if (c == '^') {
            nextChar()
            return new Lexeme(c + "", Token.CARROT)
          }
          if (c == '\\') {
            nextChar()
            return new Lexeme(c + "", Token.SLASH)
          }
          if (c == '/') {
            nextChar()
            return new Lexeme(c + "", Token.BACKSLASH)
          }
          if (c == '+') {
            nextChar()
            return new Lexeme(c + "", Token.PLUS)
          }
          if (c == '-') {
            nextChar()
            return new Lexeme(c + "", Token.MINUS)
          }
          if (c == '=') {
            nextChar()
            return new Lexeme(c + "", Token.EQUALS)
          }
          if (c == '[') {
            nextChar()
            return new Lexeme(c + "", Token.OPEN_IF)
          }
          else if (c == ']') {
            nextChar()
            return new Lexeme(c + "", Token.CLOSE_IF)
          }
          else if (c == '('){
            nextChar()
            return new Lexeme(c + "", Token.OPEN_WHILE)
          }
          else if (c == ')'){
            nextChar()
            return new Lexeme(c + "", Token.CLOSE_WHILE)
          }
          else if (c == '$'){
            var str = c + ""
            nextChar()
            c = getChar()
            if (c == '$') {
              str += c
              nextChar()
              return new Lexeme(str, Token.EO_PRG)
            }
            else{
              nextChar()
              return new Lexeme(str, Token.SPECIAL)
            }
          }
          else{
            nextChar()
            return new Lexeme(c + "", Token.SPECIAL)
          }
        }
        else if (!hasNext) {
          return new Lexeme("eof", Token.EOF)
        }
        else {
          // throw an exception if an unrecognizable symbol is found
          throw new Exception("Lexical Analyzer Error: unrecognizable symbol found!")
        }
      }
    }
  }
}

object LexicalAnalyzer {
  val BLANKS       = " \n\t"
  val NEW_LINE     = '\n'
  val LETTERS      = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val DIGITS       = "0123456789"
  val PUNCTUATIONS = ".,;:?!"
  val SPECIALS     = "_@#$%^&()-+='/\\[]{}|"

  def main(args: Array[String]): Unit = {
    // checks if source file was passed through the command-line
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    // iterates over the lexical analyzer, printing the lexemes found
    val lex = new LexicalAnalyzer(args(0))
    val it = lex.iterator
    while (it.hasNext)
      println(it.next())

  } // end main method
}
