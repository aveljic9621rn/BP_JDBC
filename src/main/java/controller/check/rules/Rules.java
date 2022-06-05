package controller.check.rules;

import controller.check.Rule;
import gui.MainFrame;

import java.io.FileNotFoundException;

public class Rules implements Rule {

   // private Entity entity = (Entity) MainFrame.getInstance().getJTree().getLastSelectedPathComponent();

    public Rules() {
      //  this.entity = entity;
    }

    @Override
    public String check(String query) throws FileNotFoundException {
        String[] split = query.split(" ");
        String tableName = "";
        for (int i = 0; i < split.length; i++) {
            if (split[i].equalsIgnoreCase("from") || split[i].equals("into")) {
                tableName = split[i + 1];
                break;
            }
        }
        String[] tableNames = new String[50];
        Object treeRoot = MainFrame.getInstance().getjTree().getModel().getRoot();
        int childCount = MainFrame.getInstance().getjTree().getModel().getChildCount(treeRoot);
        for (int i = 0; i < childCount; i++) {
            tableNames[i] = MainFrame.getInstance().getjTree().getModel().getChild(treeRoot, i).toString();
        }

        int counter=0;
        boolean flag = false;
        for (int i=0; i<tableNames.length; i++) {

            if(tableNames[i] == null) {
                return "EXIST";
            }
            if(tableNames[i].equalsIgnoreCase(tableName)) {
                break;
            }

            counter++;
        }


        return "";
    }




    }

