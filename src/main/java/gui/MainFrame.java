

package gui;

import app.AppCore;
import controller.*;
import lombok.Data;
import observer.Notification;
import observer.Subscriber;
import tree.implementation.SelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

@Data
public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private ActionManager actionManager;
    private AppCore appCore;
    private JTable jTable;
    private JScrollPane jsp;
    private JTree jTree = new JTree();
    private JPanel left;
    private JTextPane textPane;
    private JPanel textPanel;
    private JPanel top;
    private JPanel mainTop;
    private JButton btnBulkImport, btnResultSetExport, sqlPrettyBtn, queryCheckerBtn;

    public JTextPane getTextArea() {
        return textPane;
    }

    public void setTextArea(JTextPane textPane) {
        this.textPane = textPane;
    }

    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        actionManager = new ActionManager();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mainTop = new JPanel(new BorderLayout());


        textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Input" ) );


        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(100,100));
        textPane.setText("");

        textPanel.add(textPane, BorderLayout.CENTER);
        top = new JPanel(new FlowLayout());

        btnBulkImport = new JButton("BULK IMPORT");
        btnBulkImport.addActionListener(MainFrame.getInstance().getActionManager().getBulkAction());
        top.add(btnBulkImport);


        btnResultSetExport = new JButton("RESULT SET");
        btnResultSetExport.addActionListener(MainFrame.getInstance().getActionManager().getResultSetAction());
        top.add(btnResultSetExport);

        sqlPrettyBtn = new JButton("SQL PRETTY");
        sqlPrettyBtn.addActionListener(MainFrame.getInstance().getActionManager().getPrettyAction());
        top.add(sqlPrettyBtn);


        queryCheckerBtn = new JButton("RUN");
        queryCheckerBtn.addActionListener(MainFrame.getInstance().getActionManager().getQueryCheckAction());
        top.add(queryCheckerBtn);

        mainTop.add(top, BorderLayout.NORTH);
        mainTop.add(textPanel, BorderLayout.CENTER);

        this.add(mainTop, BorderLayout.NORTH);

        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
        jTable.setFillsViewportHeight(true);
        this.add(new JScrollPane(jTable));

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
        initialiseTree();
    }

    private void initialiseTree() {
        DefaultTreeModel defaultTreeModel = appCore.loadResource();
        jTree = new JTree(defaultTreeModel);
        jTree.addTreeSelectionListener(new SelectionListener());
        jsp = new JScrollPane(jTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        left = new JPanel(new BorderLayout());
        left.add(jsp, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        pack();
    }


    @Override
    public void update(Notification notification) {


    }

    public JTree getjTree() {
        return jTree;
    }
}

