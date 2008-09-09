/*
 * Copyright 2008 Ayman Al-Sairafi ayman.alsairafi@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License
 *       at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jsyntaxpane.lexers;

import jsyntaxpane.DefaultLexer;
import jsyntaxpane.Token;
import jsyntaxpane.TokenType;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;
import jsyntaxpane.SyntaxActions;

%%

%public
%class XmlLexer
%extends DefaultLexer
%final
%unicode
%char
%type Token

%{
    /**
     * Create an empty lexer, yyrset will be called later to reset and assign
     * the reader
     */
    public XmlLexer() {
        super();
    }

    private Token token(TokenType type) {
        return new Token(type, yychar, yylength());
    }

    @Override
    public void addKeyActions(Keymap map) {
        super.addKeyActions(map);
    }

    @Override
    public String[] getLanguages() {
        return LANGS;
    }

    private static final String[] LANGS = {"xml"};
%}

/* main character classes */

/* comments */
Comment = "<!--" [^--] ~"-->" | "<!--" "-"+ "->"

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]+


LetterDigit = [a-zA-Z0-9_]

/*
TagStart = [<>] 
TagEnd = "</" | ">" | "/>"
Tag = {TagStart} | {TagEnd}
Tag = [<>/]
*/

DocType = "<?xml" [^?]* "?>"

/* Tag Delimiters */
TagStart = "<" {LetterDigit}+ (":" | "-" | {LetterDigit} )*
TagEnd = ("</" {LetterDigit}+ (":" | "-" | {LetterDigit} )* ">") | "/>" | ">"
Tag = {TagStart} | {TagEnd}

/* attribute */
Attribute = {LetterDigit}+ "="
lcase = [a-z]

/* string and character literals */
DQuoteStringChar = [^\r\n\"]
SQuoteStringChar = [^\r\n\']

%%

<YYINITIAL> {
  \"{DQuoteStringChar}+\"        { return token(TokenType.STRING); }
  \'{SQuoteStringChar}+\'        { return token(TokenType.STRING); }
  
  {Comment}                      { return token(TokenType.COMMENT); }

  {Tag}                          { return token(TokenType.OPERATOR); }

  {Attribute}                    { return token(TokenType.IDENTIFIER); }

  {DocType}                      { return token(TokenType.KEYWORD); }

  "&"  {lcase}+ ";"              { return token(TokenType.OPERATOR); }
  "&#" [:digit:]+ ";"            { return token(TokenType.OPERATOR); }

  {LetterDigit}+                 { return token(TokenType.IDENTIFIER); }

  {WhiteSpace}+                  { /* skip */ }
}


/* error fallback */
.|\n                             {  }
<<EOF>>                          { return null; }

