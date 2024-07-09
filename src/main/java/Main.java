import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Main extends JFrame {
    //Swing components
    private JButton buttonUpload;
    private JPanel panelMain;
    private JComboBox comboFilter;
    private JPanel panelSpacer;
    private JPanel topPanel;
    private JComboBox comboTechs;
    private JButton buttonFilter;
    private JTextArea textArea1;
    private JButton buttonExport;

    //Utility
    ArrayList<CombinedWorkOrder> combinedWorkOrders;

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
        m.setSize(900, 900);
        m.setVisible(true);
        m.setResizable(false);

        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } //main

    //EXTRA METHODS
    public void uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().endsWith(".csv")) {
                JOptionPane.showMessageDialog(this, "Please upload a CSV file.");
            } else {
                //process csv
                processCSV(selectedFile);
            }
        }
    } //uploadFile

    public void processCSV(File selectedFile) {
        ArrayList<WorkOrder> workOrders = new ArrayList<>();
        HashSet<String> rawWOs = new HashSet<>();

        try {
            FileReader fileReader = new FileReader(selectedFile);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextRecord;

            //ensure correct csv format
            nextRecord = csvReader.readNext();

            //take care of byte order mark (BOM)
            if (nextRecord[0].startsWith("\uFEFF")) {
                nextRecord[0] = nextRecord[0].substring(1);
            }

            if (!nextRecord[0].equals("WorkOrderID") || !nextRecord[1].equals("EvalQuestTxt") || !nextRecord[2].equals("EvalQuestValTxt")
                    || !nextRecord[3].equals("LocationName") || !nextRecord[4].equals("City") || !nextRecord[5].equals("State")
                    || !nextRecord[6].equals("SignDate") || !nextRecord[7].equals("JobID") || !nextRecord[8].equals("JobCategoryID")
                    || !nextRecord[9].equals("ProgramType") || !nextRecord[10].equals("CustomerName") || !nextRecord[11].equals("PrimaryTechnicianID")
                    || !nextRecord[12].equals("DistrictName") || !nextRecord[13].equals("WOStatusID") || !nextRecord[14].equals("Revenue")
                    || !nextRecord[15].equals("Expense") || !nextRecord[16].equals("GP")) {

                throw new Exception("CSV Format Error.\n\nExpected file layout:\n" +
                        "Column 1: WorkOrderID\nColumn 2: EvalQuestTxt\nColumn 3: EvalQuestValTxt\nColumn 4: LocationName\n" +
                        "Column 5: City\nColumn 6: State\nColumn 7: SignDate\nColumn 8: JobID\nColumn 9: JobCategoryID\n" +
                        "Column 10: ProgramType\nColumn 11: CustomerName\nColumn 12: PrimaryTechnicianID\nColumn 13: DistrictName\n" +
                        "Column 14: WOStatusID\n Column 15: Revenue\nColumn 16: Expense\n Column 17: GP");
            } else {
                System.out.println("Correct Format");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error occurred while processing data: " + e);
        }
    } //processCSV


} //Main
