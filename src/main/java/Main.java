import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame {
    private JButton buttonUpload;
    private JPanel panelMain;
    private JComboBox comboFilter;
    private JPanel panelSpacer;
    private JPanel topPanel;
    private JComboBox comboTechs;
    private JButton buttonFilter;
    private JTextArea textArea1;
    private JButton buttonExport;

    public Main() {
        //topPanel
        topPanel.setSize(100, 300);
        //buttonUpload
        buttonUpload.setSize(50,50);
        buttonUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile();
            }
        });
        //comboFilter
        comboFilter.addItem("Filter by Ranking (Excellent, Good, Poor)");
        comboFilter.addItem("Filter by Tech Assigned");
        //comboFilter
        comboFilter.setEnabled(false);
        //comboTechs
        comboTechs.setEnabled(false);
        comboTechs.addItem("Tech Names");
        //buttonFilter
        buttonFilter.setEnabled(false);
        //textArea
        textArea1.setEnabled(false);
        //buttonExport
        buttonExport.setEnabled(false);
    } //Main

    public static void main(String[] args) {
        Main m = new Main();
        m.setTitle("Eval Form Analysis");
        m.setContentPane(m.panelMain);
        m.setSize(600, 600);
        m.setVisible(true);
        m.setResizable(false);

        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } //main

    //EXTRA METHODS
    public void uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == fileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
        }
    } //uploadFile
} //Main
