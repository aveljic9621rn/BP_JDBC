package controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import database.Database;
import database.DatabaseImplementation;
import database.MYSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.MainFrame;
import utils.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BulkImportAction extends AbstractAction {
    public BulkImportAction() {
        putValue(SHORT_DESCRIPTION,"Bulk Import");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Settings settings = initSettings();
        Database database = new DatabaseImplementation(new MYSQLrepository(settings));

        JFileChooser fileChooser = new JFileChooser();

        JButton tree = (JButton) e.getSource();

        String[] tableNames = new String[50];

        Object treeRoot = MainFrame.getInstance().getjTree().getModel().getRoot();
        int childCount = MainFrame.getInstance().getjTree().getModel().getChildCount(treeRoot);
        for (int i = 0; i < childCount; i++){
            tableNames[i] =  MainFrame.getInstance().getjTree().getModel().getChild(treeRoot, i).toString();
        }

        for (String s: tableNames) {
            if (MainFrame.getInstance().getjTree().getLastSelectedPathComponent().toString().equalsIgnoreCase(s)){
                if (fileChooser.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                    try {
                        CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fileChooser.getSelectedFile())));
                        reader.skip(1);
                        String fileName = fileChooser.getSelectedFile().toString();
                        System.out.println(fileName);

                        String[] columnNames = new String[MainFrame.getInstance().getJTable().getModel().getColumnCount()];
                        String columns = new String();

                        for (int i = 0; i < MainFrame.getInstance().getJTable().getModel().getColumnCount(); i++) {
                            columnNames[i] = MainFrame.getInstance().getJTable().getColumnName(i).toString();

                        }
                        for (int i = 0; i < MainFrame.getInstance().getJTable().getColumnCount(); i++) {
                            if (i == MainFrame.getInstance().getJTable().getColumnCount() - 1) {
                                columns += columnNames[i];
                                continue;
                            }
                            columns += columnNames[i] + ", ";
                        }

                        //parsiranje tabele po redovima
                        String[] row;
                        String pom = "";

                        while ((row = reader.readNext()) != null) {

                            int i=0;
                            /// insert into countries values (row0, row1, row2)
                            for (String cell : row) {
                                cell = cell.replaceAll("\\s", "");

                                    //provera da li je string zbog apostrofa
                                    boolean isNumber = (cell.startsWith("1") || cell.startsWith("2") ||
                                                            cell.startsWith("3") || cell.startsWith("4") ||
                                                                cell.startsWith("5") ||cell.startsWith("6") ||
                                                                    cell.startsWith("7") ||cell.startsWith("8") ||
                                                                        cell.startsWith("9") ||cell.startsWith("0"));

                                //ako je poslednji ne treba zarez na kraju
                                if(i == row.length - 1) {
                                    if (isNumber){
                                        pom += cell;
                                    } else {
                                        pom += "'";
                                        pom += cell;
                                        pom += "'";
                                    }
                                }else {
                                    if (isNumber)  {
                                        pom += Integer.parseInt(cell);
                                        pom += ", ";
                                    } else {
                                        pom += "'";
                                        pom += cell;
                                        pom += "', ";
                                    }
                                }
                                i++;
                            }

                            //salje se na ubacivanje
                            database.insertDataToTable(s, pom, columns);
                            pom = "";
                        }

                    } catch (CsvValidationException csvValidationException) {
                        csvValidationException.printStackTrace();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
    }


        }
}

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mysql_ip", Constants.MYSQL_IP);
        settingsImplementation.addParameter("mysql_database", Constants.MYSQL_DATABASE);
        settingsImplementation.addParameter("mysql_username", Constants.MYSQL_USERNAME);
        settingsImplementation.addParameter("mysql_password", Constants.MYSQL_PASSWORD);
        return settingsImplementation;
    }

}
