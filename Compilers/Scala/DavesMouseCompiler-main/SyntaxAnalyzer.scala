/*
 * CS3210 - Principles of Programming Languages - Fall 2021
 * Instructor: Thyago Mota
 * Student(s):
 * Description: Prg 01 - SyntaxAnalyzer (an iterable syntax analyzer)
 */

/*
mouse       = { statement } ´$$´
statement   = ´?´ | ´!´ | string | identifier | ´=´ | literal | ´+´ | ´-´ | ´*´ | ´/´ | ´\´ | ´^´ | ´.´ | if | while
string      = ´"´ { character } ´"´
identifier  = letter
literal     = ´0´ | nonzero { digit }
nonzero     = ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´
digit       = ´0´ | ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´
if          = ´[´ { statement } ´]´
while       = ´(´ { statement } ´)´
letter      = ´a´ | ´b´ | ´c´ | ´d´ | ´e´ | ´f´ | ´g´ | ´h´ | ´i´ | ´j´ | ´k´ | ´l´ | ´m´ | ´n´ | ´o´ | ´p´ | ´q´ | ´r´ | ´s´ | ´t´ | ´u´ | ´v´ | ´x´ | ´y´ | ´w´ | ´z´ | ´A´ | ´B´ | ´C´ | ´D´ | ´E´ | ´F´ | ´G´ | ´H´ | ´I´ | ´J´ | ´K´ | ´L´ | ´M´ | ´N´ | ´O´ | ´P´ | ´Q´ | ´R´ | ´S´ | ´T´ | ´U´ | ´V´ | ´X´ | ´Y´ | ´W´ | ´Z´
punctuation = ´.´ | ´,´ | ´;´ | ´:´ | ´?´ | ´!´
special     = ´<´ | ´_´ | ´@´ | ´#´ | ´$´ | ´%´ | ´^´ | ´&´ | ´(´ | ´)´ | ´-´ | ´+´ | ´=´ | ´'´ | ´/´ | ´\´ | ´[´ | ´]´ | ´{´ | ´}´ | ´|´
blank       = ´ ´
character   = letter | digit | punctuation | special | blank
 */

class SyntaxAnalyzer(private var source: String) {

  private val it = new LexicalAnalyzer(source).iterator
  private var current: Lexeme = null

  // returns the current lexeme
  private def getLexeme(): Lexeme = {
    if (current == null) {
      current = it.next
    }
    //    println(current)
    current
  }

  // advances the input one lexeme
  private def nextLexeme() = {
    current = it.next
  }

  // parses the program, returning its corresponding parse tree
  def parse() = {
    parseMouse()
  }

  def parseMouse() = {
    val tree = new Tree("Mouse")
    while (getLexeme().getToken() != Token.EO_PRG) {
      tree.add(parseStatement())
      if(!it.hasNext){
        throw new Exception("Syntax Analyzer Error: expected '$$'")
      }
      nextLexeme()
    }
    tree.add(new Tree(getLexeme().getLabel()))
    tree
  }


  def parseStatement() = {
    val tree = new Tree("statement")

    if (getLexeme().getToken() == Token.COMMENT) {
      val subtree = new Tree("comment")
      subtree.setAttribute("value", getLexeme().getLabel())
      tree.add(subtree)
      tree
    }
    else if (getLexeme().getToken() == Token.BANG) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.QUESTION) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.BANG) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.EQUALS) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.PLUS) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.SLASH) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.BACKSLASH) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.CARROT) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.SPLAT) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.MINUS) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.DOT) {
      tree.add(new Tree(getLexeme().getLabel()))
      tree
    }
    else if (getLexeme().getToken() == Token.STRING) {
      val subtree = new Tree("string")
      subtree.setAttribute("value", getLexeme().getLabel())
      tree.add(subtree)
      tree
    }
    else if (getLexeme().getToken() == Token.OPEN_IF) {
      tree.add(parseIf())
      tree
    }
    else if (getLexeme().getToken() == Token.OPEN_WHILE) {
      tree.add(parseWhile())
      tree
    }
    else if (getLexeme().getToken() == Token.IDENTIFIER) {
      val subtree = new Tree("identifier")
      subtree.setAttribute("value", getLexeme().getLabel())
      tree.add(subtree)
      tree
    }
    else if (getLexeme().getToken() == Token.LITERAL) {
      val subtree = new Tree("literal")
      subtree.setAttribute("value", getLexeme().getLabel())
      tree.add(subtree)
      tree
    }
    else if (getLexeme().getToken() == Token.EO_PRG) {
      tree
    }
    else {
      throw new Exception("Syntax Analyzer Error: expected string, identifier, literal, if, while or special character")
    }
  }


  def parseIf(): Tree = {
    val tree = new Tree("if")
    tree.add(new Tree(getLexeme().getLabel()))
    nextLexeme()
    while (getLexeme().getToken() != Token.CLOSE_IF) {
      tree.add(parseStatement())
      nextLexeme()
      if(getLexeme().getToken() == Token.EO_PRG || getLexeme().getToken() == Token.EOF){
        throw new Exception("Syntax Analyzer Error: expected ']'")
      }
    }
    tree.add(new Tree(getLexeme().getLabel()))
    tree
  }


  def parseWhile(): Tree = {
    val tree = new Tree("while")
    tree.add(new Tree(getLexeme().getLabel()))
    nextLexeme()
    while (getLexeme().getToken() != Token.CLOSE_WHILE) {
      tree.add(parseStatement())
      nextLexeme()
      if(getLexeme().getToken() == Token.EO_PRG || getLexeme().getToken() == Token.EOF){
        throw new Exception("Syntax Analyzer Error: expected ')'")
      }
    }
    tree.add(new Tree(getLexeme().getLabel()))
    tree
  }
}

object SyntaxAnalyzer {
  def main(args: Array[String]): Unit = {

    // check if source file was passed through the command-line
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    val syntaxAnalyzer = new SyntaxAnalyzer(args(0))
    val parseTree = syntaxAnalyzer.parse()
    print(parseTree)
  }
}
