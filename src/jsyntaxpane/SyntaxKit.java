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
package jsyntaxpane;

import jsyntaxpane.lexers.TALLexer;
import jsyntaxpane.lexers.JavaLexer;
import jsyntaxpane.lexers.GroovyLexer;
import jsyntaxpane.lexers.XmlLexer;
import jsyntaxpane.lexers.JavaScriptLexer;
import jsyntaxpane.lexers.PropertiesLexer;
import jsyntaxpane.lexers.SqlLexer;
import java.awt.Font;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class SyntaxKit extends DefaultEditorKit implements ViewFactory {

    private static Font DEFAULT_FONT = new Font("Courier New", Font.PLAIN, 12);
    private String lang;
    private static HashMap<String, Class<? extends Lexer>> LEXERS_MAP = null;
    private static String[] LANGS;
    
    static {
        initKit();
    }

    /**
     * Create a new Kit for the given language (text/tal, text/hex-dump, etc)
     * @param lang
     */
    public SyntaxKit(String lang) {
        super();
        this.lang = lang;
    }

    @Override
    public ViewFactory getViewFactory() {
        return this;
    }

    @Override
    public View create(Element element) {
        return new SyntaxView(element);
    }

    /**
     * Install the View on the given EditorPane.  This is called by Swing and
     * can be used to do anything you need on the JEditorPane control.  Here
     * I set some default Actions.
     * 
     * @param editorPane
     */
    @Override
    public void install(JEditorPane editorPane) {
        // clear the bindings and action so we can start fresh
        editorPane.getActionMap().clear();
        editorPane.getKeymap().removeBindings();
        // now use the defaults
        super.install(editorPane);
        editorPane.setFont(DEFAULT_FONT);

        // and now add our own actions and key bindings
        SyntaxActions.addAction(editorPane, "TAB", SyntaxActions.INDENT);
        SyntaxActions.addAction(editorPane, "shift TAB", SyntaxActions.UNINDENT);
        SyntaxActions.addAction(editorPane, "control Z", SyntaxActions.UNDO);
        SyntaxActions.addAction(editorPane, "control Y", SyntaxActions.REDO);

        if ("groovy".equals(lang) || "java".equals(lang)) {
            SyntaxActions.addAction(editorPane, '(', SyntaxActions.LPARAN);
            SyntaxActions.addAction(editorPane, '[', SyntaxActions.LSQUARE);
            SyntaxActions.addAction(editorPane, '"', SyntaxActions.DQUOTE);
            SyntaxActions.addAction(editorPane, '\'', SyntaxActions.SQUOTE);
            SyntaxActions.addAction(editorPane, "ENTER", SyntaxActions.JAVA_INDENT);
        } else if (lang.equalsIgnoreCase("xml")) {
            SyntaxActions.addAction(editorPane, "ENTER", SyntaxActions.SMART_INDENT);
        }
    }

    /**
     * This is called by Swing to create a Document for the JEditorPane document
     * This may be called before you actually get a reference to the control.
     * We use it here to create a properl lexer and pass it to the 
     * SyntaxDcument we return.
     * @return
     */
    @Override
    public Document createDefaultDocument() {
        Lexer lexer = null;
        if (LEXERS_MAP.containsKey(lang)) {
            try {
                lexer = LEXERS_MAP.get(lang).newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(SyntaxKit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SyntaxKit.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (LEXERS_MAP.containsKey("text/" + lang)) {
            try {
                lexer = LEXERS_MAP.get("text/" + lang).newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(SyntaxKit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SyntaxKit.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Logger.getLogger(SyntaxKit.class.getName()).warning("Unable to find lexer for: " + lang);
        }
        return new SyntaxDocument(lexer);
    }

    /**
     * This is called to initialize the list of lexers we have.  You can call 
     * this at initialization, or it will be called when needed.
     */
    public static void initKit() {
        LEXERS_MAP = new HashMap<String, Class<? extends Lexer>>();
        registerLexer(JavaLexer.class, JavaLexer.LANGS);
        registerLexer(GroovyLexer.class, GroovyLexer.LANGS);
        registerLexer(JavaScriptLexer.class, JavaScriptLexer.LANGS);
        registerLexer(XmlLexer.class, XmlLexer.LANGS);
        registerLexer(TALLexer.class, TALLexer.LANGS);
        registerLexer(PropertiesLexer.class, PropertiesLexer.LANGS);
        registerLexer(SqlLexer.class, SqlLexer.LANGS);
    }

    /**
     * Add the given lexer, that supports the given languages to out list
     * @param lexer Lexer to add
     * @param langs supported array of languages
     */
    public static void registerLexer(Class<? extends Lexer> lexer, String[] langs) {
        for (String lang : langs) {
            LEXERS_MAP.put(lang, lexer);
        }
        // throu away the old list, and create a new one when needed
        LANGS = null;
    }

    /**
     * Get a sorted String Array of supported languages.
     * @return
     */
    public static String[] getLangs() {
        if (LANGS == null) {
            int i = 0;
            LANGS = new String[LEXERS_MAP.size()];
            for (String l : LEXERS_MAP.keySet()) {
                LANGS[i++] = l;
            }
            Arrays.sort(LANGS);
        }
        return LANGS;
    }
}