import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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
        comboFilter.addItem("Filter by All Rankings");
        comboFilter.addItem("Filter by Excellent Rankings");
        comboFilter.addItem("Filter by Good Rankings");
        comboFilter.addItem("Filter by Poor Rankings");
        comboFilter.addItem("Filter by WOs with Additional Comments");
        comboFilter.addItem("Filter by Tech Assigned");
        comboFilter.addItem("Filter by WO Type");
        comboFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboFilter.getSelectedItem().equals("Filter by Tech Assigned")) {
                    populateTechNames();
                    comboTechs.setEnabled(true);
                    buttonFilter.setEnabled(true);
                } else if (comboFilter.getSelectedItem().equals("Filter by WO Type")) {
                    populateWOTypes();
                    comboTechs.setEnabled(true);
                    buttonFilter.setEnabled(true);
                } else {
                    comboTechs.setEnabled(false);
                    buttonFilter.setEnabled(true);
                }
            }
        });
        //comboFilter
        comboFilter.setEnabled(false);
        //comboTechs
        comboTechs.setEnabled(false);
        comboTechs.addItem("Tech Names | WO Types");
        //buttonFilter
        buttonFilter.setEnabled(false);
        //textArea
        textArea1.setEnabled(false);
        textArea1.setEditable(false);
        //buttonExport
        buttonExport.setEnabled(false);
    } //Main

    public static void main(String[] args) {
        Main m = new Main();
        m.setTitle("Eval Form Analysis");
        m.setContentPane(m.panelMain);
        m.setSize(600, 600);
        m.setVisible(true);
        m.setResizable(true);

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
                //process csv on separate thread
                new ProcessCSVWorker(selectedFile).execute();
            }
        }
    } //uploadFile

    private class ProcessCSVWorker extends SwingWorker<Void, CombinedWorkOrder> {
        private File selectedFile;
        private JDialog progressDialog;
        private JProgressBar progressBar;

        public ProcessCSVWorker(File selectedFile) {
            this.selectedFile = selectedFile;
            createProgressDialog();
        } //ProcessCSVWorker

        private void createProgressDialog() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new JDialog(Main.this, "Processing", true);
                    progressDialog.setSize(300, 75);
                    progressDialog.setLayout(new BorderLayout());
                    progressDialog.setLocationRelativeTo(Main.this);

                    progressBar = new JProgressBar(0, 100);
                    progressBar.setStringPainted(true);
                    progressDialog.add(progressBar, BorderLayout.CENTER);

                    progressDialog.setVisible(true);

                }
            });

            this.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("progress".equals(evt.getPropertyName())) {
                        progressBar.setValue((Integer) evt.getNewValue());
                    }
                }
            });
        } //createProgressDialog

        @Override
        protected Void doInBackground() throws Exception {
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
                    int totalLines = 0;
                    while(csvReader.readNext() != null) {
                        totalLines++;
                    }
                    fileReader.close();
                    csvReader.close();

                    fileReader = new FileReader(selectedFile);
                    csvReader = new CSVReader(fileReader);
                    nextRecord = csvReader.readNext();
                    int processedLines = 0;

                    while ((nextRecord = csvReader.readNext()) != null) {
                        WorkOrder workOrder = new WorkOrder (
                                nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3],
                                nextRecord[4],nextRecord[5], nextRecord[6], nextRecord[7],
                                nextRecord[8],nextRecord[9], nextRecord[10], nextRecord[11],
                                nextRecord[12],nextRecord[13], nextRecord[16]
                        );

                        if (workOrder.getEvalQuestValTxt().isEmpty()) {
                            workOrder.setEvalQuestValTxt("No Response");
                        }

                        workOrders.add(workOrder);

                        processedLines++;
                        int progress = (int) ((processedLines / totalLines) * 100);
                        setProgress(progress);
                    }

                    for (WorkOrder wo : workOrders) {
                        rawWOs.add(wo.getWorkOrderID() + "|" + wo.getSignDate());
                    }

                    combinedWorkOrders = Driver2.constructCombinedWorkOrders(workOrders);

                    for (CombinedWorkOrder cWO : combinedWorkOrders) {
                        publish(cWO);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(Main.this, "Unexpected error occurred while processing data: " + e);
            }

            return null;
        } //

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
        }

        @Override
        protected void done() {
            comboFilter.setEnabled(true);
            progressDialog.dispose();
        }
    } //ProcessCSVWorker

    /**
     * Populates comboTechs with all unique technician names.
     */
    public void populateTechNames() {
        //if somehow data has not been populated
        if (combinedWorkOrders == null) {
            JOptionPane.showMessageDialog(this, "Critical Error: data processing");
            return;
        }

        //obtain unique techIDs for filtering
        HashSet<String> techNames = new HashSet<>();

        for (CombinedWorkOrder wo : combinedWorkOrders) {
            techNames.add(wo.getPrimaryTechnicianID());
        }

        comboTechs.removeAllItems();

        List<String> orderedTechNames = new ArrayList<>(techNames);
        Collections.sort(orderedTechNames);

        //populate combobox with tech names
        for (String techName : orderedTechNames) {
            if (techName.equals("")) {
                continue;
            } else {
                comboTechs.addItem(techName);
            }

        }
    } //filterByTechs

    public void populateWOTypes() {
        //if somehow data has not been populated
        if (combinedWorkOrders == null) {
            JOptionPane.showMessageDialog(this, "Critical Error: data processing");
            return;
        }

        //obtain unique techIDs for filtering
        HashSet<String> woTypes = new HashSet<>();

        for (CombinedWorkOrder wo : combinedWorkOrders) {
            woTypes.add(wo.getProgramType());
        }

        comboTechs.removeAllItems();

        List<String> orderedWOTypes = new ArrayList<>(woTypes);
        Collections.sort(orderedWOTypes);

        //populate combobox with tech names
        for (String woType : orderedWOTypes) {
            if (woType.equals("")) {
                continue;
            } else {
                comboTechs.addItem(woType);
            }
        }
    }

} //Main
