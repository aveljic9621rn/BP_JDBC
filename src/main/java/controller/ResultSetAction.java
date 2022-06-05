package controller;

import gui.MainFrame;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultSetAction extends AbstractAction {
    public ResultSetAction() {
        putValue(SHORT_DESCRIPTION,"Result Set");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String[] tableNames = new String[50];

        Object treeRoot = MainFrame.getInstance().getjTree().getModel().getRoot();
        int childCount = MainFrame.getInstance().getjTree().getModel().getChildCount(treeRoot);

        for (int i = 0; i < childCount; i++){
            tableNames[i] =  MainFrame.getInstance().getjTree().getModel().getChild(treeRoot, i).toString();
        }

       for (String s: tableNames) {

           if (MainFrame.getInstance().getjTree().getLastSelectedPathComponent().toString().equalsIgnoreCase(s)){
               toFile(MainFrame.getInstance().getJTable().getModel(), s);
           }

       }
    }

    public void toFile(TableModel table, String s){
        try{
            TableModel model = table;
            String path = "", name = "";

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\Users\\aleks\\Desktop"));
            fileChooser.setAcceptAllFileFilterUsed(false);


            if (fileChooser.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                path = "" + fileChooser.getCurrentDirectory();
                name = s;
            }
            else {
                System.out.println("No Selection");
            }

            FileWriter fw = new FileWriter(path + "\\" + name + ".csv");

            for(int i = 0; i < model.getColumnCount(); i++){
                if (i == 0)
                    fw.write("(");


                if (i == model.getColumnCount() - 1)
                    fw.write(model.getColumnName(i) +")");

                else
                    fw.write(model.getColumnName(i) + ", ");
            }

            fw.write("\n\n");

            for(int i=0; i< model.getRowCount(); i++) {
                for(int j=0; j < model.getColumnCount(); j++) {

                    if ((Object)model.getValueAt(i, j) != null) {
                        if (j == model.getColumnCount() - 1)
                            fw.write(model.getValueAt(i, j).toString());
                        else
                            fw.write(model.getValueAt(i, j).toString() + ", ");
                    }else
                        fw.write("  /  , ");



                }
                fw.write("\n");
            }

            fw.close();

        }catch(IOException e){ System.out.println(e); }
    }
}
