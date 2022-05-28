package org.editor4j.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class FontBox extends JComboBox<ImageIcon>  {

    public static int RENDERED_FONT_WIDTH = 300, RENDERED_FONT_HEIGHT = 25;


    BufferedImage bufferedImage = new BufferedImage(RENDERED_FONT_WIDTH, RENDERED_FONT_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics2D = bufferedImage.createGraphics();
    RenderedFont[] renderedFonts;
    private Font selectedFont;
    private Color textColor = UIManager.getColor("ComboBox.foreground");
    private Color background = UIManager.getColor("ComboBox.background");


    public FontBox(Font[] fonts){

        renderedFonts = new RenderedFont[fonts.length];

        setPreferredSize(new Dimension(RENDERED_FONT_WIDTH, RENDERED_FONT_HEIGHT));
        //setRenderer(this);



        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                for(int i = 0; i < fonts.length; i++){
                    Font font = fonts[i];
                    renderFontToImage(font);

                    RenderedFont f = new RenderedFont(bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(), Image.SCALE_DEFAULT));

                    f.fontName = font.getFontName();
                    renderedFonts[i] = f;


                }

                System.out.println("Done rendering fonts");
                return null;
            }

            @Override
            protected void done() {
                for(RenderedFont i : renderedFonts) {
                    addItem(i);
                }

                for(RenderedFont renderedFont : renderedFonts){
                    if(renderedFont.fontName.equals(selectedFont.getFontName())){
                        setSelectedItem(renderedFont);
                    }
                }


            }
        };
        swingWorker.execute();
    }

    private void renderFontToImage(Font font) {
        graphics2D.setColor(background);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        graphics2D.setColor(textColor);
        graphics2D.setFont(font.deriveFont(20f));

        graphics2D.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString(font.getFontName(),  10, 20);
    }




    /*@Override
    public ImageIcon getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel font;

        if(index != -1) {
            RenderedFont renderedFont = renderedFonts[index];
            font = new JLabel(renderedFont);
        } else {
            int indexOfSelectedFont = getSelectedIndex();

            font = new JLabel(renderedFonts[indexOfSelectedFont]);
            
        }
        font.setHorizontalAlignment(SwingConstants.LEFT);
        font.setPreferredSize(new Dimension(RENDERED_FONT_WIDTH, RENDERED_FONT_HEIGHT));
        return font;
    }*/

    public void setFont(Font font) {
        selectedFont = font;
    }
}
