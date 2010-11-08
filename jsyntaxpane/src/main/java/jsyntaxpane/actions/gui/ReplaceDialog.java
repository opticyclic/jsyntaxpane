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
package jsyntaxpane.actions.gui;

import jsyntaxpane.actions.*;
import jsyntaxpane.components.Markers;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import jsyntaxpane.util.SwingUtils;

/**
 * A Find and Replace Dialog.  The dialog will also act as a listener to
 * Document changes so that all highlights are updated if the document is
 * changed.
 * 
 * @author Ayman Al-Sairafi
 */
public class ReplaceDialog extends javax.swing.JDialog
	implements CaretListener, EscapeListener {

	private JTextComponent textComponent;
	private DocumentSearchData dsd;
	private static Markers.SimpleMarker SEARCH_MARKER = new Markers.SimpleMarker(Color.YELLOW);

	/**
	 * Creates new form FindDialog
	 * @param text
	 * @param dsd DocumentSerachData
	 */
	public ReplaceDialog(JTextComponent text,
		DocumentSearchData dsd) {
		super(ActionUtils.getFrameFor(text), false);
		initComponents();
		textComponent = text;
		this.dsd = dsd;
		textComponent.addCaretListener(this);
		setLocationRelativeTo(text.getRootPane());
		getRootPane().setDefaultButton(jBtnNext);
		SwingUtils.addEscapeListener(this);
		jBtnReplaceAll.setEnabled(text.isEditable() && text.isEnabled());
	}

	/**
	 * updates the highlights in the document when it is updated.
	 * This is called by the DocumentListener methods
	 */
	public void updateHighlights() {
		Markers.removeMarkers(textComponent, SEARCH_MARKER);
		if (jTglHighlight.isSelected()) {
			Markers.markAll(textComponent, dsd.getPattern(), SEARCH_MARKER);
		}
	}

	private void showRegexpError(PatternSyntaxException ex) throws HeadlessException {
		JOptionPane.showMessageDialog(this, "Regexp error: " + ex.getMessage(),
			"Regular Expression Error", JOptionPane.ERROR_MESSAGE);
		jCmbFind.requestFocus();
	}

	/**
	 * update the finder object with data from our UI
	 */
	private void updateFinder() {
		String regex = (String) jCmbFind.getSelectedItem();
		try {
			dsd.setPattern(regex,
				jChkRegex.isSelected(),
				jChkIgnoreCase.isSelected());
			ActionUtils.insertIntoCombo(jCmbFind, regex);
		} catch (PatternSyntaxException e) {
			showRegexpError(e);
		}
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblFind = new javax.swing.JLabel();
        jBtnNext = new javax.swing.JButton();
        jBtnPrev = new javax.swing.JButton();
        jBtnReplaceAll = new javax.swing.JButton();
        jChkWrap = new javax.swing.JCheckBox();
        jChkRegex = new javax.swing.JCheckBox();
        jChkIgnoreCase = new javax.swing.JCheckBox();
        jLblReplace = new javax.swing.JLabel();
        jTglHighlight = new javax.swing.JToggleButton();
        jCmbReplace = new javax.swing.JComboBox();
        jCmbFind = new javax.swing.JComboBox();
        jBtnReplace = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("jsyntaxpane/Bundle"); // NOI18N
        setTitle(bundle.getString("ReplaceDialog.title")); // NOI18N
        setName(""); // NOI18N
        setResizable(false);

        jLblFind.setDisplayedMnemonic('F');
        jLblFind.setLabelFor(jCmbFind);
        jLblFind.setText(bundle.getString("ReplaceDialog.jLblFind.text")); // NOI18N

        jBtnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/images/small-icons/go-next.png"))); // NOI18N
        jBtnNext.setMnemonic('N');
        jBtnNext.setText(bundle.getString("ReplaceDialog.jBtnNext.text")); // NOI18N
        jBtnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNextActionPerformed(evt);
            }
        });

        jBtnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/images/small-icons/go-previous.png"))); // NOI18N
        jBtnPrev.setMnemonic('N');
        jBtnPrev.setText(bundle.getString("ReplaceDialog.jBtnPrev.text")); // NOI18N
        jBtnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPrevActionPerformed(evt);
            }
        });

        jBtnReplaceAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/images/small-icons/edit-find-replace-all.png"))); // NOI18N
        jBtnReplaceAll.setMnemonic('H');
        jBtnReplaceAll.setText(bundle.getString("ReplaceDialog.jBtnReplaceAll.text")); // NOI18N
        jBtnReplaceAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReplaceAllActionPerformed(evt);
            }
        });

        jChkWrap.setMnemonic('W');
        jChkWrap.setText(bundle.getString("ReplaceDialog.jChkWrap.text")); // NOI18N
        jChkWrap.setToolTipText(bundle.getString("ReplaceDialog.jChkWrap.toolTipText")); // NOI18N

        jChkRegex.setMnemonic('R');
        jChkRegex.setText(bundle.getString("ReplaceDialog.jChkRegex.text")); // NOI18N

        jChkIgnoreCase.setMnemonic('I');
        jChkIgnoreCase.setText(bundle.getString("ReplaceDialog.jChkIgnoreCase.text")); // NOI18N

        jLblReplace.setDisplayedMnemonic('R');
        jLblReplace.setLabelFor(jCmbReplace);
        jLblReplace.setText(bundle.getString("ReplaceDialog.jLblReplace.text")); // NOI18N

        jTglHighlight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/images/small-icons/highlight.png"))); // NOI18N
        jTglHighlight.setText(bundle.getString("ReplaceDialog.jTglHighlight.text")); // NOI18N
        jTglHighlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTglHighlightActionPerformed(evt);
            }
        });

        jCmbReplace.setEditable(true);

        jCmbFind.setEditable(true);

        jBtnReplace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/images/small-icons/edit-find-replace.png"))); // NOI18N
        jBtnReplace.setText(bundle.getString("ReplaceDialog.jBtnReplace.text")); // NOI18N
        jBtnReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReplaceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblFind)
                    .addComponent(jLblReplace))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCmbFind, 0, 289, Short.MAX_VALUE)
                    .addComponent(jCmbReplace, javax.swing.GroupLayout.Alignment.TRAILING, 0, 289, Short.MAX_VALUE)
                    .addComponent(jChkRegex, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                    .addComponent(jChkWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                    .addComponent(jChkIgnoreCase, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnReplace, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jBtnNext, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jBtnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jTglHighlight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jBtnReplaceAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblFind)
                    .addComponent(jCmbFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnNext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnPrev)
                    .addComponent(jCmbReplace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblReplace))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnReplace)
                    .addComponent(jChkWrap, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jChkRegex)
                    .addComponent(jBtnReplaceAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jChkIgnoreCase)
                    .addComponent(jTglHighlight))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNextActionPerformed
		try {
			updateFinder();
			if (!dsd.doFindNext(textComponent)) {
				dsd.msgNotFound(textComponent);
			}
			textComponent.requestFocusInWindow();
		} catch (PatternSyntaxException ex) {
			showRegexpError(ex);
		}
    }//GEN-LAST:event_jBtnNextActionPerformed

    private void jBtnReplaceAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReplaceAllActionPerformed
		try {
			updateFinder();
			String replacement = (String) jCmbReplace.getSelectedItem();
			if(!jChkRegex.isSelected()) {
				replacement = Matcher.quoteReplacement(replacement);
			}
			ActionUtils.insertIntoCombo(jCmbReplace, replacement);
			jTglHighlight.setSelected(false);
			dsd.doReplaceAll(textComponent, replacement);
			textComponent.requestFocusInWindow();
		} catch (PatternSyntaxException ex) {
			showRegexpError(ex);
		}
}//GEN-LAST:event_jBtnReplaceAllActionPerformed

    private void jTglHighlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTglHighlightActionPerformed
		updateFinder();
		updateHighlights();
    }//GEN-LAST:event_jTglHighlightActionPerformed

	private void jBtnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPrevActionPerformed
		updateFinder();
		dsd.doFindPrev(textComponent);
}//GEN-LAST:event_jBtnPrevActionPerformed

	private void jBtnReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReplaceActionPerformed
		jTglHighlight.setSelected(false);
		String replacement = jCmbReplace.getSelectedItem() == null ?
			"" : jCmbReplace.getSelectedItem().toString();
		dsd.doReplace(textComponent, replacement);
	}//GEN-LAST:event_jBtnReplaceActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnNext;
    private javax.swing.JButton jBtnPrev;
    private javax.swing.JButton jBtnReplace;
    private javax.swing.JButton jBtnReplaceAll;
    private javax.swing.JCheckBox jChkIgnoreCase;
    private javax.swing.JCheckBox jChkRegex;
    private javax.swing.JCheckBox jChkWrap;
    private javax.swing.JComboBox jCmbFind;
    private javax.swing.JComboBox jCmbReplace;
    private javax.swing.JLabel jLblFind;
    private javax.swing.JLabel jLblReplace;
    private javax.swing.JToggleButton jTglHighlight;
    // End of variables declaration//GEN-END:variables

	@Override
	public void caretUpdate(CaretEvent e) {
		updateHighlights();
	}

	@Override
	public void escapePressed() {
		setVisible(false);
	}
}
