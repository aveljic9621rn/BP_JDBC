package app;

import gui.MainFrame;
import org.json.simple.parser.ParseException;
import resource.data.Row;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        AppCore appCore = new AppCore();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setAppCore(appCore);

    }

}
