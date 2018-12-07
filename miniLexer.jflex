// Samuel Sedivy - sls195@pitt.edu - Project3a - 25 March 2018
import java_cup.runtime.*;
%%
%class miniLexer
%cup
%line
%column

%{
  
  private Symbol symbol(int symbol)
  {
    return new Symbol(symbol, yyline + 1, yycolumn + 1);
  }

  private Symbol symbol(int symbol, Object value)
  {
    return new Symbol(symbol, yyline + 1, yycolumn + 1, value);
  }

  private void error(String message) {
    System.err.println("Error at line " + (yyline + 1) + ", column" + (yycolumn + 1) + " : " + message);
  }

%}


inputChar = [^\r\n]
eof = \n|\r\n
whiteSpace = {eof} | [' '\t]
blockComment = "/*" ~"*/"
lineComment = "//" {inputChar}* {eof}?
intLiteral =  0 | [1-9][0-9]*
identifier = [A-Za-z][A-Za-z_0-9]*

%%

<YYINITIAL> {

  "&&"              { return symbol(sym.AND); }
  "boolean"         { return symbol(sym.BOOLEAN); }
  "<"               { return symbol(sym.LESSTHAN); }
  "String"          { return symbol(sym.STRING); }
  "extends"         { return symbol(sym.EXTENDS); }
  "false"           { return symbol(sym.FALSE); }
  "true"            { return symbol(sym.TRUE); }
  "="               { return symbol(sym.EQUALS); }
  "if"              { return symbol(sym.IF); }
  "public"          { return symbol(sym.PUBLIC); }
  "class"           { return symbol(sym.CLASS); }
  "static"          { return symbol(sym.STATIC); }
  "{"               { return symbol(sym.LBRACE); }
  "["               { return symbol(sym.LBRACKET); }
  "("               { return symbol(sym.LPAREN); }
  "}"               { return symbol(sym.RBRACE); }
  "]"               { return symbol(sym.RBRACKET); }
  ")"               { return symbol(sym.RPAREN); }
  ","               { return symbol(sym.COMMA); }
  "."               { return symbol(sym.DOT); }
  "length"          { return symbol(sym.LENGTH); }  
  "main"            { return symbol(sym.MAIN); }
  "void"            { return symbol(sym.VOID); }
  "-"               { return symbol(sym.MINUS); }
  "new"             { return symbol(sym.NEW); }
  "!"               { return symbol(sym.NOT); }
  "+"               { return symbol(sym.PLUS); }
  "else"            { return symbol(sym.ELSE); }
  "return"          { return symbol(sym.RETURN); }
  "int"             { return symbol(sym.INT); }
  "*"               { return symbol(sym.TIMES); }
  ";"               { return symbol(sym.SEMICOLON); }
  "System.out.println" { return symbol(sym.PRINT); }
  "this"            { return symbol(sym.THIS); }
  "while"           { return symbol(sym.WHILE); }
  {identifier}      { return symbol(sym.IDENTIFIER, yytext()); }
  {intLiteral}      { return symbol(sym.INTLITERAL, Integer.parseInt(yytext())); }
  {lineComment}     { }
  {blockComment}    { }
  {whiteSpace}      { }
}

[^]                 { error("Illegal character <" + yytext() + ">"); }