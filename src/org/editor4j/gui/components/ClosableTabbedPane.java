package org.editor4j.gui.components;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class ClosableTabbedPane extends JTabbedPane {
    int currentIndex = 0;

    public void addTab(Editor editor) {

        Tab tab = new Tab(editor.file.getName(), editor);

        tab.index = currentIndex++;
        addTab(tab.title, tab.jPanel);

        setTabComponentAt(tab.index, new TabComponent(tab, new TabClosingListener() {
            @Override
            public void tabClosing(int index) {
                removeTabAt(index);
                reindex();
            }
        }, editor.languageDescriptor.iconName));

        setSelectedIndex(tab.index);


    }

    public void reindex() {
        for(currentIndex = 0; currentIndex < getTabCount(); currentIndex++){
            ((TabComponent) getTabComponentAt(currentIndex)).parentTab.index = currentIndex;
        }
    }


    class TabComponent extends JPanel {

        private Tab parentTab;
        public TabComponent(Tab parentTab, TabClosingListener tabClosingListener, String iconName) {
            this.parentTab = parentTab;

            setOpaque(false);

            FlowLayout layout = (FlowLayout) getLayout();
            layout.setVgap(0);
            layout.setHgap(0);

            if(iconName != null) {

                FlatSVGIcon flatSVGIcon = new FlatSVGIcon(new File("resources/languageicons/" + iconName + "-original.svg"));


                this.add(new JLabel(flatSVGIcon.derive(20, 20)));

            }

            this.add(new JLabel(parentTab.title));


            FlatTabbedPaneCloseIcon f = new FlatTabbedPaneCloseIcon();


            JButton close = new JButton(f);

            close.addActionListener(e -> tabClosingListener.tabClosing(this.parentTab.index));

            close.setBorderPainted(false);
            close.setContentAreaFilled(false);
            close.setFocusPainted(false);
            close.setOpaque(false);

            this.add(close);
        }
    }

    abstract class TabClosingListener {
        public abstract void tabClosing(int index);
    }
}
