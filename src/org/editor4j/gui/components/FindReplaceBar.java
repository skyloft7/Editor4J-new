package org.editor4j.gui.components;

import org.editor4j.gui.ComponentRegistry;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindReplaceBar extends Toolbar implements ActionListener {

    public JTextField findText = new JTextField();
    public JButton findNext = new JButton("Find Next");
    public JButton findLast = new JButton("Find Last");
    public JCheckBox matchCase = new JCheckBox();
    public JCheckBox wholeWord = new JCheckBox();


    public JTextField replaceText = new JTextField();
    public JButton replaceAll = new JButton("Replace All");

    public FindReplaceBar(){

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel findOptions = buildFindOptions();
        JPanel replaceOptions = buildReplaceOptions();

        JCheckBox jCheckBox = new JCheckBox();
        jCheckBox.addActionListener(e -> {
            if(jCheckBox.isSelected()) {
                content.add(replaceOptions);
                content.revalidate();
                content.repaint();
            }
            else {
                content.remove(replaceOptions);
                content.revalidate();
                content.repaint();
            }
        });

        findOptions.add(new Field("Replace", jCheckBox));

        content.add(findOptions);
    }

    private JPanel buildReplaceOptions() {
        FlowLayout f = new FlowLayout(FlowLayout.LEFT);
        f.setVgap(0);
        JPanel replaceOptions = new JPanel();
        replaceOptions.setLayout(f);

        replaceText.setColumns(10);
        replaceOptions.add(new Field("Replace With", replaceText));
        replaceOptions.add(replaceAll);
        replaceAll.addActionListener(this);

        return replaceOptions;
    }

    private JPanel buildFindOptions(){
        FlowLayout f = new FlowLayout();
        f.setVgap(0);
        JPanel findOptions = new JPanel();

        findOptions.setLayout(f);

        findText.setColumns(10);

        findOptions.add(new Field("Find", findText));
        findOptions.add(new Field("Match Case", matchCase));
        findOptions.add(new Field("Whole Word", wholeWord));
        findOptions.add(findNext);
        findOptions.add(findLast);


        findNext.addActionListener(this);
        findLast.addActionListener(this);

        return findOptions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SearchContext searchContext = new SearchContext();

        if(e.getSource() == findNext){
            searchContext.setSearchForward(true);
        }
        if(e.getSource() == findLast){
            searchContext.setSearchForward(false);
        }
        if(e.getSource() == replaceAll){
            searchContext.setReplaceWith(replaceText.getText());
        }

        searchContext.setSearchFor(findText.getText());
        searchContext.setMatchCase(matchCase.isSelected());
        searchContext.setWholeWord(wholeWord.isSelected());


        ClosableTabbedPane closableTabbedPane = (ClosableTabbedPane) ComponentRegistry.components.get("tabPane");
        int index = closableTabbedPane.getSelectedIndex();

        if(index != -1) {

            CodeEditor c = (CodeEditor) closableTabbedPane.getSelectedComponent();
            SearchResult s;
            if(e.getSource() != replaceAll)
                s = SearchEngine.find(c.rSyntaxTextArea, searchContext);
            else
                s = SearchEngine.replaceAll(c.rSyntaxTextArea, searchContext);

            if(!s.wasFound()){
                JOptionPane.showInternalMessageDialog(null, "No more matches for \"" + findText.getText() + "\"");
            }

        }
    }
}
