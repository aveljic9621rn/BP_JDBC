package controller;

import gui.MainFrame;
import lombok.SneakyThrows;

import javax.swing.AbstractAction;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;

public class PrettyActionAction extends AbstractAction {

    public PrettyActionAction() {
        putValue(SHORT_DESCRIPTION,"Pretty Action");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {




            //citanje iz fajla mysqlreserved words (test je skracena verzija sa 2 reci)
            File sqlWords = new File("mysqlreservedwords.txt");
            Scanner sc = new Scanner(sqlWords);
            String[] sqlWordsArray = new String[189];

            int n = 0, i = 0;
            int sqlArratLength = 0;

            //ucitavanje rezervisanih reci u niz
            while (sc.hasNextLine()) {

                sqlWordsArray[n++] = sc.nextLine();
                sqlArratLength++;

            }

            //ucitavanje texta iz textarea u string
            String str = MainFrame.getInstance().getTextPane().getText();

            //parsiranje po recima
            String[] tokens = str.split("[\n ]");
            String[] result = new String[200];
            int ctr = 0;

            MainFrame.getInstance().getTextPane().setText("");

            for (String t : tokens) {
                for (String s : sqlWordsArray) {
                    if (t.equalsIgnoreCase(s)) {
                        result[i] = t.toUpperCase();
                        if (MainFrame.getInstance().getTextPane().getText().equalsIgnoreCase(""))
                            appendToPane(MainFrame.getInstance().getTextPane(), result[i] + " ", Color.BLUE);
                        else
                            appendToPane(MainFrame.getInstance().getTextPane(), "\n" + result[i] + " ", Color.BLUE);
                        ctr = 0;

                        break;

                    }

                    ctr++;
                    if (ctr == sqlArratLength) {
                        result[i] = t.toLowerCase();
                        appendToPane(MainFrame.getInstance().getTextPane(), result[i] + " ", Color.black);
                        ctr = 0;
                    }
                }
                i++;
            }



    }

    private void appendToPane(JTextPane textPane, String s, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

       //aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(s);
    }
}
