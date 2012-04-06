package jsyntaxpane.demo;

import java.awt.*;
import javax.swing.*;

import jsyntaxpane.DefaultSyntaxKit;

/**
 * This class is an XML Viewer that utilises jsyntaxpane to display XML well formatted in a frame
 */
public class XmlViewer {

  /**
   * Demo method to show and test the frame
   */
  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        //Reformat the XML from the context menu when the frame loads
        new XmlViewer("Demo XML", "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><!-- http://www.w3schools.com/XML/xml_examples.asp --><note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>");
      }
    });
  }

  public XmlViewer(String title, String xmlContent) {
    JFrame frame = new JFrame(title);
    final Container contentPane = frame.getContentPane();
    contentPane.setLayout(new BorderLayout());

    DefaultSyntaxKit.initKit();

    final JEditorPane codeEditor = new JEditorPane();
    JScrollPane scrPane = new JScrollPane(codeEditor);
    contentPane.add(scrPane, BorderLayout.CENTER);
    contentPane.doLayout();
    codeEditor.setContentType("text/xml");
    codeEditor.setText(xmlContent);

    JToolBar toolBar = new JToolBar();
    toolBar.setRollover(true);
    toolBar.setFocusable(false);
    frame.getContentPane().add(toolBar, BorderLayout.NORTH);
    toolBar.removeAll();

    DefaultSyntaxKit defaultSyntaxKit = (DefaultSyntaxKit)codeEditor.getEditorKit();
    defaultSyntaxKit.addToolBarActions(codeEditor, toolBar);
    toolBar.validate();

    frame.setSize(800, 600);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }
}
