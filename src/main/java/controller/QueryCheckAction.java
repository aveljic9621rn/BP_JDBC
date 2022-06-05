package controller;

import gui.MainFrame;
import lombok.SneakyThrows;

import javax.swing.AbstractAction;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class QueryCheckAction extends AbstractAction {

    public QueryCheckAction() {
        putValue(SHORT_DESCRIPTION,"Query Check");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("QUERY CHECK");
        MainFrame.getInstance().getAppCore().run();

    }
}
