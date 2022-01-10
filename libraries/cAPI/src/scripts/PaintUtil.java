package scripts;

import org.tribot.api2007.Interfaces;

import java.awt.*;
import java.util.ArrayList;

public class PaintUtil {

    private static int[] xCoords, yCoords;
    private String[] paintInformationArray;
    private static int borderThickness, numberOfItems, horizontalPadding, verticalPadding;

    private void reinitializeVariables() {
        this.numberOfItems = paintInformationArray.length;
        this.xCoords = new int[numberOfItems];
        this.yCoords = new int[numberOfItems];
    }

    public static void createPaint(Graphics g, ArrayList<String> words){
        int xWords = 10; // for words
        int xGraphics = 5;
        FontMetrics fontMetrics = g.getFontMetrics();
        Color backgroundColor = new Color(93, 140, 245, 160);
        Font font = new Font("Arial Nova", Font.PLAIN, 12);

        int rectangleYStart = 201;
        int multipleOfString = 20* words.size();
        // this uses the chatbox to determine the lowest the graphics go
        if (Interfaces.get(162,40) != null){
            rectangleYStart = (int) Interfaces.get(162,40).getAbsoluteBounds().getY() -
                    multipleOfString - 5;
        }


        for (int i =0 ; i < words.size(); i++) {
            int width = ((int) fontMetrics.getStringBounds(words.get(i), g).getWidth() + xWords);
            int addY = i * 20;
            int height = (int) fontMetrics.getStringBounds(words.get(i), g).getHeight() + 2;

            g.setColor(backgroundColor);
            g.fillRect(xGraphics, rectangleYStart + addY, width, height);

            g.setColor(Color.DARK_GRAY);
            g.drawRect(xGraphics, rectangleYStart + addY, width, height);

            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(words.get(i), xWords, rectangleYStart + addY + 14);
        }
    };

    public static void createPaint(Graphics g, String[] words){
        int xWords = 10; // for words
        int xGraphics = 5;
        FontMetrics fontMetrics = g.getFontMetrics();
        Color backgroundColor = new Color(93, 140, 245, 160);
        Font font = new Font("Arial Nova", Font.PLAIN, 12);

        int rectangleYStart = 201;
        int multipleOfString = 20* words.length;
        // this uses the chatbox to determine the lowest the graphics go
        if (Interfaces.get(162,40) != null){
            rectangleYStart = (int) Interfaces.get(162,40).getAbsoluteBounds().getY() -
                    multipleOfString - 5;
        }

        for (int i =0 ; i < words.length; i++) {
            int width = ((int) fontMetrics.getStringBounds(words[i], g).getWidth() + xWords);
            int addY = i * 20;
            int height = (int) fontMetrics.getStringBounds(words[i], g).getHeight() + 2;

            g.setColor(backgroundColor);
            g.fillRect(xGraphics, rectangleYStart + addY, width, height);

            g.setColor(Color.DARK_GRAY);
            g.drawRect(xGraphics, rectangleYStart + addY, width, height);

            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(words[i], xWords, rectangleYStart + addY + 14);
        }
    };


    public static void createPaintableText(Graphics graphics, String[] text, int startingY) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        numberOfItems = text.length;
        int textHeight = fontMetrics.getHeight();
        for (int i = 0; i < numberOfItems; i++) {
            xCoords[i] = 9;
            yCoords[i] = startingY + ((int) fontMetrics.getStringBounds(text[i], graphics).getHeight() + 5) * (i + 1);
        }
    }



}
