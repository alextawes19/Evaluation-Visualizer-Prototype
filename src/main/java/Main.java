import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
        comboFilter.addItem("Filter by All WOs with Additional Comments");
        comboFilter.addItem("Filter by Excellent Rated WOs with Additional Comments");
        comboFilter.addItem("Filter by Poor Rated WOs with Additional Comments");
        comboFilter.addItem("Filter by Tech Assigned");
        comboFilter.addItem("Filter by WO Type");
        comboFilter.addItem("Filter by Job Category (ERO, etc.)");
        comboFilter.addItem("Filter by Customer");
        comboFilter.addItem("Filter by District");
        comboFilter.addItem("Tech Performance Rankings");
        comboFilter.addItem("Customer Performance Rankings");
        comboFilter.addItem("District Performance Rankings");
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
                } else if (comboFilter.getSelectedItem().equals("Filter by Job Category (ERO, etc.)")) {
                    populateJobCategories();
                    comboTechs.setEnabled(true);
                    buttonFilter.setEnabled(true);
                } else if (comboFilter.getSelectedItem().equals("Filter by Customer")) {
                    populateCustomers();
                    comboTechs.setEnabled(true);
                    buttonFilter.setEnabled(true);
                } else if (comboFilter.getSelectedItem().equals("Filter by District")) {
                    populateDistricts();
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
        comboTechs.addItem("Tech Names | WO Types | Job Category | Customer | District");
        //buttonFilter
        buttonFilter.setEnabled(false);

        buttonFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
                if (comboFilter.getSelectedItem().equals("Filter by All Rankings")) {
                    new FilterByAllRankings().execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by Excellent Rankings")) {
                    new FilterByRankings("Excellent").execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by Good Rankings")) {
                    new FilterByRankings("Good").execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by Poor Rankings")) {
                    new FilterByRankings("Poor").execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by All WOs with Additional Comments")) {
                    new FilterByAdditionalComments().execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by Tech Assigned")) {
                    new FilterByTechAssigned((String)comboTechs.getSelectedItem()).execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by WO Type")) {
                    new FilterByWOType((String)comboTechs.getSelectedItem()).execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by Excellent Rated WOs with Additional Comments")) {
                    new FilterByExcellentWithAdditional().execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by Poor Rated WOs with Additional Comments")) {
                    new FilterByPoorWithAdditional().execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by Job Category (ERO, etc.)")) {
                    new FilterByJobCategory((String)comboTechs.getSelectedItem()).execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by Customer")) {
                    new FilterByCustomer((String)comboTechs.getSelectedItem()).execute();
                } else if (comboFilter.getSelectedItem().equals("Filter by District")) {
                    new FilterByDistrict((String)comboTechs.getSelectedItem()).execute();
                } else if (comboFilter.getSelectedItem().equals("Tech Performance Rankings")) {
                    new FilterByTechRankings().execute();
                } else if (comboFilter.getSelectedItem().equals("Customer Performance Rankings")) {
                    new FilterByCustomerRankings().execute();
                } else if (comboFilter.getSelectedItem().equals("District Performance Rankings")) {
                    new FilterByDistrictRankings().execute();
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Error processing selections");
                }
            }
        });
        //textArea
        textArea1.setEnabled(false);
        textArea1.setEditable(false);

        //buttonExport
        buttonExport.setEnabled(false);

        buttonExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    exportToPDF();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    } //Main

    /**
     * Main method. Entry point for swing.
     *
     * @param args CL arguments
     */
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
                FileReader fileReader = new FileReader(selectedFile, StandardCharsets.UTF_8);
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
            buttonExport.setEnabled(true);
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
    } //populateWOTypes

    public void populateJobCategories() {
        //if somehow data has not been populated
        if (combinedWorkOrders == null) {
            JOptionPane.showMessageDialog(this, "Critical Error: data processing");
            return;
        }

        //obtain unique job categories for filtering
        HashSet<String> jobCategories = new HashSet<>();

        for (CombinedWorkOrder wo : combinedWorkOrders) {
            jobCategories.add(wo.getJobCategoryID());
        }

        comboTechs.removeAllItems();

        List<String> orderedWOTypes = new ArrayList<>(jobCategories);
        Collections.sort(orderedWOTypes);

        //populate combobox with tech names
        for (String jobCategory : orderedWOTypes) {
            if (jobCategory.equals("")) {
                continue;
            } else {
                comboTechs.addItem(jobCategory);
            }
        }
    } //populateJobCategories

    public void populateCustomers() {
        //if somehow data has not been populated
        if (combinedWorkOrders == null) {
            JOptionPane.showMessageDialog(this, "Critical Error: data processing");
            return;
        }

        //obtain unique job categories for filtering
        HashSet<String> customers = new HashSet<>();

        for (CombinedWorkOrder wo : combinedWorkOrders) {
            customers.add(wo.getCustomerName());
        }

        comboTechs.removeAllItems();

        List<String> orderedWOTypes = new ArrayList<>(customers);
        Collections.sort(orderedWOTypes);

        //populate combobox with tech names
        for (String customer : orderedWOTypes) {
            if (customer.equals("")) {
                continue;
            } else {
                comboTechs.addItem(customer);
            }
        }
    } //populateCustomers

    public void populateDistricts() {
        //if somehow data has not been populated
        if (combinedWorkOrders == null) {
            JOptionPane.showMessageDialog(this, "Critical Error: data processing");
            return;
        }

        //obtain unique job categories for filtering
        HashSet<String> districts = new HashSet<>();

        for (CombinedWorkOrder wo : combinedWorkOrders) {
            districts.add(wo.getDistrictName());
        }

        comboTechs.removeAllItems();

        List<String> orderedWOTypes = new ArrayList<>(districts);
        Collections.sort(orderedWOTypes);

        //populate combobox with tech names
        for (String district : orderedWOTypes) {
            if (district.equals("")) {
                continue;
            } else {
                comboTechs.addItem(district);
            }
        }
    } //populateDistricts

    /**
     * Exports whatever is in textArea1 to PDF
     *
     * @throws FileNotFoundException if file not found
     */
    public void exportToPDF() throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }

            PdfWriter writer = new PdfWriter(fileToSave);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            String content = textArea1.getText();

            try {
                PdfFont font = PdfFontFactory.createFont();

                Text textElement = new Text(content)
                        .setFont(font)
                        .setFontSize(8) // Set the font size to 12
                        .setFontColor(ColorConstants.BLACK); // Set font color to black

                Paragraph paragraph = new Paragraph(textElement);

                document.add(paragraph);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            document.close();

            JOptionPane.showMessageDialog(this, "PDF exported successfully!");
        }
    } //exportToPDF

    public double calculatePercentage(int num, int listSize) {
        return Math.round((((double) num / listSize) * 100.0) * 100.0) / 100.0;
    } //calculatePercentage



    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //================================FILTERING METHODS AND THREADS=================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================

    /**
     * Filters WOs by all rankings
     */
    private class FilterByAllRankings extends SwingWorker<Void, CombinedWorkOrder> {
        private ArrayList<CombinedWorkOrder> allRankings = new ArrayList<>();
        int numExcellent = 0;
        int numGood = 0;
        int numPoor = 0;
        int numBlank = 0;

        double percentExcellent;
        double percentGood;
        double percentPoor;
        double percentBlank;

        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                allRankings.add(cWO);

                if (cWO.getQuestions().contains("Overall Performance Was:")) {
                    int index  = cWO.getQuestions().indexOf("Overall Performance Was:");

                    if (cWO.getAnswers().get(index).equals("Excellent")) {
                        numExcellent++;
                    } else if (cWO.getAnswers().get(index).equals("Good")) {
                        numGood++;
                    } else if (cWO.getAnswers().get(index).equals("Poor")) {
                        numPoor++;
                    } else if (cWO.getAnswers().get(index).equals("No Response")) {
                        numBlank++;
                    }
                }
            }

            //calculate basic statistics
            percentExcellent = Math.round((((double) numExcellent / allRankings.size()) * 100.0) * 100.0) / 100.0;
            percentGood = Math.round((((double) numGood /allRankings.size()) * 100.0) * 100.0) / 100.0;
            percentPoor = Math.round((((double) numPoor /allRankings.size()) * 100.0) * 100.0) / 100.0;
            percentBlank = Math.round((((double) numBlank /allRankings.size()) * 100.0) * 100.0) / 100.0;

            for (CombinedWorkOrder cWO : allRankings) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n======================STATISTICS SUMMARY======================\n" +
                    "# of WOs: " + allRankings.size() + "\n" +
                    "Excellent: " + numExcellent + "(" + percentExcellent + "%)\n" +
                    "Good: " + numGood + "(" + percentGood + "%)\n" +
                    "Poor: " + numPoor + "(" + percentPoor + "%)\n" +
                    "Blank/No Response: " + numBlank + "(" + percentBlank + "%)");
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByAllRankings

    /**
     * Filters WOs by determined ratings
     */
    private class FilterByRankings extends SwingWorker<Void, CombinedWorkOrder> {
        private ArrayList<CombinedWorkOrder> rankings = new ArrayList<>();
        private String rank;

        public FilterByRankings (String rank) {
            this.rank = rank;
        } //FilterByRankings

        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getQuestions().contains("Overall Performance Was:")) {
                    int index = cWO.getQuestions().indexOf("Overall Performance Was:");

                    if (rank.equals("Excellent")) {
                        if (cWO.getAnswers().get(index).equals("Excellent")) {
                            rankings.add(cWO);
                        }
                    } else if (rank.equals("Good")) {
                        if (cWO.getAnswers().get(index).equals("Good")) {
                            rankings.add(cWO);
                        }
                    } else if (rank.equals("Poor")) {
                        if (cWO.getAnswers().get(index).equals("Poor")) {
                            rankings.add(cWO);
                        }
                    }
                }
            }

            for (CombinedWorkOrder cWO : rankings) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n======================STATISTICS SUMMARY======================\n" +
                    "Count of " + rank + " WOs: " + rankings.size());
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByRankings

    /**
     * Filters WOs by additional comments
     */
    private class FilterByAdditionalComments extends SwingWorker<Void, CombinedWorkOrder> {
        private ArrayList<CombinedWorkOrder> additionalComments = new ArrayList<>();

        int numExcellent = 0;
        int numGood = 0;
        int numPoor = 0;
        int numBlank = 0;
        double percentExcellent;
        double percentGood;
        double percentPoor;
        double percentBlank;

        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getQuestions().contains("Additional Comments on Work Provided:")) {
                    int index = cWO.getQuestions().indexOf("Additional Comments on Work Provided:");

                    if (!cWO.getAnswers().get(index).equals("No Response") && !cWO.getAnswers().get(index).equals("Na")) {
                        additionalComments.add(cWO);

                        if (cWO.getQuestions().contains("Overall Performance Was:")) {
                            int index1  = cWO.getQuestions().indexOf("Overall Performance Was:");

                            if (cWO.getAnswers().get(index1).equals("Excellent")) {
                                numExcellent++;
                            } else if (cWO.getAnswers().get(index1).equals("Good")) {
                                numGood++;
                            } else if (cWO.getAnswers().get(index1).equals("Poor")) {
                                numPoor++;
                            } else if (cWO.getAnswers().get(index1).equals("No Response")) {
                                numBlank++;
                            }
                        }
                    }
                }
            }

            //calculate basic statistics
            percentExcellent = calculatePercentage(numExcellent, additionalComments.size());
            percentGood = calculatePercentage(numGood, additionalComments.size());
            percentPoor = calculatePercentage(numPoor, additionalComments.size());
            percentBlank = calculatePercentage(numBlank, additionalComments.size());

            for (CombinedWorkOrder cWO : additionalComments) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n======================STATISTICS SUMMARY======================\n" +
                    "# of WOs: " + additionalComments.size() + "\n" +
                    "Excellent: " + numExcellent + "(" + percentExcellent + "%)\n" +
                    "Good: " + numGood + "(" + percentGood + "%)\n" +
                    "Poor: " + numPoor + "(" + percentPoor + "%)\n" +
                    "Blank/No Response: " + numBlank + "(" + percentBlank + "%)");
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByAdditionalComments

    /**
     * Filters WOs by the tech assigned
     */
    private class FilterByTechAssigned extends SwingWorker<Void, CombinedWorkOrder> {
        private String tech;
        private ArrayList<CombinedWorkOrder> specificTech = new ArrayList<>();

        int numExcellent = 0;
        int numGood = 0;
        int numPoor = 0;
        int numBlank = 0;
        double percentExcellent;
        double percentGood;
        double percentPoor;
        double percentBlank;

        public FilterByTechAssigned(String tech) {
            this.tech = tech;
        } //FilterByTechAssigned
        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getPrimaryTechnicianID().equals(tech)) {
                    specificTech.add(cWO);

                    if (cWO.getQuestions().contains("Overall Performance Was:")) {
                        int index  = cWO.getQuestions().indexOf("Overall Performance Was:");

                        if (cWO.getAnswers().get(index).equals("Excellent")) {
                            numExcellent++;
                        } else if (cWO.getAnswers().get(index).equals("Good")) {
                            numGood++;
                        } else if (cWO.getAnswers().get(index).equals("Poor")) {
                            numPoor++;
                        } else if (cWO.getAnswers().get(index).equals("No Response")) {
                            numBlank++;
                        }
                    }
                }
            }

            //calculate basic statistics
            percentExcellent = calculatePercentage(numExcellent, specificTech.size());
            percentGood = calculatePercentage(numGood, specificTech.size());
            percentPoor = calculatePercentage(numPoor, specificTech.size());
            percentBlank = calculatePercentage(numBlank, specificTech.size());

            for (CombinedWorkOrder cWO : specificTech) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n======================STATISTICS SUMMARY======================\n" +
                    "# of WOs for " + tech + ": " + specificTech.size() + "\n" +
                    "Excellent: " + numExcellent + "(" + percentExcellent + "%)\n" +
                    "Good: " + numGood + "(" + percentGood + "%)\n" +
                    "Poor: " + numPoor + "(" + percentPoor + "%)\n" +
                    "Blank/No Response: " + numBlank + "(" + percentBlank + "%)");
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByTechAssigned

    /**
     * Filters WOs by WO type
     */
    private class FilterByWOType extends SwingWorker<Void, CombinedWorkOrder> {
        private String woType;
        private ArrayList<CombinedWorkOrder> specificType = new ArrayList<>();

        int numExcellent = 0;
        int numGood = 0;
        int numPoor = 0;
        int numBlank = 0;
        double percentExcellent;
        double percentGood;
        double percentPoor;
        double percentBlank;

        public FilterByWOType(String woType) {
            this.woType = woType;
        } //FilterByWOType
        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getProgramType().equals(woType)) {
                    specificType.add(cWO);

                    if (cWO.getQuestions().contains("Overall Performance Was:")) {
                        int index  = cWO.getQuestions().indexOf("Overall Performance Was:");

                        if (cWO.getAnswers().get(index).equals("Excellent")) {
                            numExcellent++;
                        } else if (cWO.getAnswers().get(index).equals("Good")) {
                            numGood++;
                        } else if (cWO.getAnswers().get(index).equals("Poor")) {
                            numPoor++;
                        } else if (cWO.getAnswers().get(index).equals("No Response")) {
                            numBlank++;
                        }
                    }
                }
            }

            //calculate basic statistics
            percentExcellent = calculatePercentage(numExcellent, specificType.size());
            percentGood = calculatePercentage(numGood, specificType.size());
            percentPoor = calculatePercentage(numPoor, specificType.size());
            percentBlank = calculatePercentage(numBlank, specificType.size());

            for (CombinedWorkOrder cWO : specificType) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n======================STATISTICS SUMMARY======================\n" +
                    "# of WOs for " + woType + ": " + specificType.size() + "\n" +
                    "Excellent: " + numExcellent + "(" + percentExcellent + "%)\n" +
                    "Good: " + numGood + "(" + percentGood + "%)\n" +
                    "Poor: " + numPoor + "(" + percentPoor + "%)\n" +
                    "Blank/No Response: " + numBlank + "(" + percentBlank + "%)");
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByWOType

    /**
     * Filters WOs by Job Category.
     */
    private class FilterByJobCategory extends SwingWorker<Void, CombinedWorkOrder> {
        private String jobCategory;
        private ArrayList<CombinedWorkOrder> specificCategory = new ArrayList<>();

        int numExcellent = 0;
        int numGood = 0;
        int numPoor = 0;
        int numBlank = 0;
        double percentExcellent;
        double percentGood;
        double percentPoor;
        double percentBlank;

        public FilterByJobCategory(String jobCategory) {
            this.jobCategory = jobCategory;
        } //FilterByWOType
        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getJobCategoryID().equals(jobCategory)) {
                    specificCategory.add(cWO);

                    if (cWO.getQuestions().contains("Overall Performance Was:")) {
                        int index  = cWO.getQuestions().indexOf("Overall Performance Was:");

                        if (cWO.getAnswers().get(index).equals("Excellent")) {
                            numExcellent++;
                        } else if (cWO.getAnswers().get(index).equals("Good")) {
                            numGood++;
                        } else if (cWO.getAnswers().get(index).equals("Poor")) {
                            numPoor++;
                        } else if (cWO.getAnswers().get(index).equals("No Response")) {
                            numBlank++;
                        }
                    }
                }
            }

            //calculate basic statistics
            percentExcellent = calculatePercentage(numExcellent, specificCategory.size());
            percentGood = calculatePercentage(numGood, specificCategory.size());
            percentPoor = calculatePercentage(numPoor, specificCategory.size());
            percentBlank = calculatePercentage(numBlank, specificCategory.size());

            for (CombinedWorkOrder cWO : specificCategory) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n======================STATISTICS SUMMARY======================\n" +
                    "# of WOs for " + jobCategory + ": " + specificCategory.size() + "\n" +
                    "Excellent: " + numExcellent + "(" + percentExcellent + "%)\n" +
                    "Good: " + numGood + "(" + percentGood + "%)\n" +
                    "Poor: " + numPoor + "(" + percentPoor + "%)\n" +
                    "Blank/No Response: " + numBlank + "(" + percentBlank + "%)");
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByJobCategory

    /**
     * Filters WOs by Job Category.
     */
    private class FilterByCustomer extends SwingWorker<Void, CombinedWorkOrder> {
        private String customer;
        private ArrayList<CombinedWorkOrder> specificCustomer = new ArrayList<>();

        int numExcellent = 0;
        int numGood = 0;
        int numPoor = 0;
        int numBlank = 0;
        double percentExcellent;
        double percentGood;
        double percentPoor;
        double percentBlank;

        public FilterByCustomer(String customer) {
            this.customer = customer;
        } //FilterByWOType
        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getCustomerName().equals(customer)) {
                    specificCustomer.add(cWO);

                    if (cWO.getQuestions().contains("Overall Performance Was:")) {
                        int index  = cWO.getQuestions().indexOf("Overall Performance Was:");

                        if (cWO.getAnswers().get(index).equals("Excellent")) {
                            numExcellent++;
                        } else if (cWO.getAnswers().get(index).equals("Good")) {
                            numGood++;
                        } else if (cWO.getAnswers().get(index).equals("Poor")) {
                            numPoor++;
                        } else if (cWO.getAnswers().get(index).equals("No Response")) {
                            numBlank++;
                        }
                    }
                }
            }

            //calculate basic statistics
            percentExcellent = calculatePercentage(numExcellent, specificCustomer.size());
            percentGood = calculatePercentage(numGood, specificCustomer.size());
            percentPoor = calculatePercentage(numPoor, specificCustomer.size());
            percentBlank = calculatePercentage(numBlank, specificCustomer.size());

            for (CombinedWorkOrder cWO : specificCustomer) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n======================STATISTICS SUMMARY======================\n" +
                    "# of WOs for " + customer + ": " + specificCustomer.size() + "\n" +
                    "Excellent: " + numExcellent + "(" + percentExcellent + "%)\n" +
                    "Good: " + numGood + "(" + percentGood + "%)\n" +
                    "Poor: " + numPoor + "(" + percentPoor + "%)\n" +
                    "Blank/No Response: " + numBlank + "(" + percentBlank + "%)");
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByCustomer

    /**
     * Filters WOs by Job Category.
     */
    private class FilterByDistrict extends SwingWorker<Void, CombinedWorkOrder> {
        private String district;
        private ArrayList<CombinedWorkOrder> specificDistrict = new ArrayList<>();

        int numExcellent = 0;
        int numGood = 0;
        int numPoor = 0;
        int numBlank = 0;
        double percentExcellent;
        double percentGood;
        double percentPoor;
        double percentBlank;

        public FilterByDistrict(String district) {
            this.district = district;
        } //FilterByWOType
        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getDistrictName().equals(district)) {
                    specificDistrict.add(cWO);

                    if (cWO.getQuestions().contains("Overall Performance Was:")) {
                        int index  = cWO.getQuestions().indexOf("Overall Performance Was:");

                        if (cWO.getAnswers().get(index).equals("Excellent")) {
                            numExcellent++;
                        } else if (cWO.getAnswers().get(index).equals("Good")) {
                            numGood++;
                        } else if (cWO.getAnswers().get(index).equals("Poor")) {
                            numPoor++;
                        } else if (cWO.getAnswers().get(index).equals("No Response")) {
                            numBlank++;
                        }
                    }
                }
            }

            //calculate basic statistics
            percentExcellent = calculatePercentage(numExcellent, specificDistrict.size());
            percentGood = calculatePercentage(numGood, specificDistrict.size());
            percentPoor = calculatePercentage(numPoor, specificDistrict.size());
            percentBlank = calculatePercentage(numBlank, specificDistrict.size());

            for (CombinedWorkOrder cWO : specificDistrict) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n======================STATISTICS SUMMARY======================\n" +
                    "# of WOs for " + district + ": " + specificDistrict.size() + "\n" +
                    "Excellent: " + numExcellent + "(" + percentExcellent + "%)\n" +
                    "Good: " + numGood + "(" + percentGood + "%)\n" +
                    "Poor: " + numPoor + "(" + percentPoor + "%)\n" +
                    "Blank/No Response: " + numBlank + "(" + percentBlank + "%)");
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByDistrict

    /**
     * Filters WOs by excellent ratings that have additional comments
     */
    private class FilterByExcellentWithAdditional extends SwingWorker<Void, CombinedWorkOrder> {
        private ArrayList<CombinedWorkOrder> excellentAdditionalWOs = new ArrayList<>();
        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getQuestions().contains("Overall Performance Was:") && cWO.getQuestions().contains("Additional Comments on Work Provided:")) {
                    int index = cWO.getQuestions().indexOf("Overall Performance Was:");
                    int index1 = cWO.getQuestions().indexOf("Additional Comments on Work Provided:");

                    if (cWO.getAnswers().get(index).equals("Excellent") &&
                            !cWO.getAnswers().get(index1).equals("No Response") &&
                            !cWO.getAnswers().get(index1).equals("Na")) {
                        excellentAdditionalWOs.add(cWO);
                    }
                }
            }

            for (CombinedWorkOrder cWO : excellentAdditionalWOs) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            textArea1.append("========EXCELLENT RATED WORK ORDERS WITH ADDITIONAL COMMENTS========\n\n\n");
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n=====================STATISTICS=====================\n" +
                    "Number of WOs: " + excellentAdditionalWOs.size());
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByExcellentWithAdditional

    /**
     * Filters WOs by poor ratings that have additional comments
     */
    private class FilterByPoorWithAdditional extends SwingWorker<Void, CombinedWorkOrder> {
        private ArrayList<CombinedWorkOrder> poorAdditionalWOs = new ArrayList<>();
        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getQuestions().contains("Overall Performance Was:") && cWO.getQuestions().contains("Additional Comments on Work Provided:")) {
                    int index = cWO.getQuestions().indexOf("Overall Performance Was:");
                    int index1 = cWO.getQuestions().indexOf("Additional Comments on Work Provided:");

                    if (cWO.getAnswers().get(index).equals("Poor") &&
                            !cWO.getAnswers().get(index1).equals("No Response") &&
                            !cWO.getAnswers().get(index1).equals("Na")) {
                        poorAdditionalWOs.add(cWO);
                    }
                }
            }

            for (CombinedWorkOrder cWO : poorAdditionalWOs) {
                publish(cWO);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<CombinedWorkOrder> chunks) {
            for (CombinedWorkOrder cWO: chunks) {
                textArea1.append(cWO.toString() + "\n");
            }
            textArea1.setEnabled(true);
            textArea1.append("\n\n=====================STATISTICS=====================\n" +
                    "Number of WOs: " + poorAdditionalWOs.size());
        } //process

        @Override
        protected void done() {
            super.done();
        } //done
    } //FilterByPoorWithAdditional

    /**
     * Gives ranking of Excellent WO counts by technician
     */
    private class FilterByTechRankings extends SwingWorker<Void, Map.Entry<String, Integer>> {
        HashMap<String, Integer> techMap = new HashMap<>();
        HashMap<String, Integer> ratioMap = new HashMap<>();

        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getQuestions().contains("Overall Performance Was:")) {
                    int index = cWO.getQuestions().indexOf("Overall Performance Was:");
                    String tech = cWO.getPrimaryTechnicianID();

                    if (cWO.getAnswers().get(index).equals("Excellent")) {
                        techMap.put(tech, techMap.getOrDefault(tech, 0) + 1);
                    }
                    if (!cWO.getAnswers().get(index).equals("No Response")) {
                        ratioMap.put(tech, ratioMap.getOrDefault(tech, 0) + 1);
                    }
                }
            }

            for (HashMap.Entry<String, Integer> entry : techMap.entrySet()) {
                publish(entry);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<Map.Entry<String, Integer>> chunks) {

        } //process

        @Override
        protected void done() {
            List<Map.Entry<String, Integer>> sortedTechs = new ArrayList<>(techMap.entrySet());
            List<Map.Entry<String, Integer>> ratioList = new ArrayList<>(ratioMap.entrySet());
            sortedTechs.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            // Clear the text area before displaying the results
            textArea1.setText("");

            double techPercent = 0;
            double averageRatio = 0;
            // Update the text area with the sorted results
            for (Map.Entry<String, Integer> entry : sortedTechs) {
                for (Map.Entry<String, Integer> ratio : ratioList) {
                    if (ratio.getKey().equals(entry.getKey())) {
                        techPercent = ((double) entry.getValue() /ratio.getValue()) * 100.00;
                        averageRatio += techPercent;
                    }
                }
                textArea1.append("========================\n");
                textArea1.append("Technician:           " + entry.getKey() + "\nExcellent Ratings: " + entry.getValue()
                        + "\nExcellence Percentage: " + Math.round(techPercent*100.0)/100.0 + "\n");
            }
            textArea1.append("\n\n=================STATISTICS SUMMARY=================\n" +
                    "Average Excellence Percentage: " + (Math.round(averageRatio / sortedTechs.size()*100.0)/100.0));
        } //done
    } //FilterByTechRankings

    /**
     * Gives ranking of Excellent WO counts by customer
     */
    private class FilterByCustomerRankings extends SwingWorker<Void, Map.Entry<String, Integer>> {
        HashMap<String, Integer> customerMap = new HashMap<>();
        HashMap<String, Integer> ratioMap = new HashMap<>();

        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getQuestions().contains("Overall Performance Was:")) {
                    int index = cWO.getQuestions().indexOf("Overall Performance Was:");
                    String customer = cWO.getCustomerName();
                    if (cWO.getAnswers().get(index).equals("Excellent")) {
                        customerMap.put(customer, customerMap.getOrDefault(customer, 0) + 1);
                    }
                    if (!cWO.getAnswers().get(index).equals("No Response")) {
                        ratioMap.put(customer, ratioMap.getOrDefault(customer, 0) + 1);
                    }
                }
            }

            for (HashMap.Entry<String, Integer> entry : customerMap.entrySet()) {
                publish(entry);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<Map.Entry<String, Integer>> chunks) {

        } //process

        @Override
        protected void done() {
            List<Map.Entry<String, Integer>> sortedCustomers = new ArrayList<>(customerMap.entrySet());
            List<Map.Entry<String, Integer>> ratioList = new ArrayList<>(ratioMap.entrySet());
            sortedCustomers.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            // Clear the text area before displaying the results
            textArea1.setText("");

            double customerPercent = 0;
            double averageRatio = 0;
            // Update the text area with the sorted results
            for (Map.Entry<String, Integer> entry : sortedCustomers) {
                for (Map.Entry<String, Integer> ratio : ratioList) {
                    if (ratio.getKey().equals(entry.getKey())) {
                        customerPercent = ((double) entry.getValue() /ratio.getValue()) * 100.00;
                        averageRatio += customerPercent;
                    }
                }
                textArea1.append("========================\n");
                textArea1.append("Customer:             " + entry.getKey() + "\nExcellent Ratings: " + entry.getValue()
                        + "\nExcellence Percentage: " + Math.round(customerPercent*100.0)/100.0 + "\n");
            }
            textArea1.append("\n\n=================STATISTICS SUMMARY=================\n" +
                    "Average Excellence Percentage: " + (Math.round(averageRatio / sortedCustomers.size()*100.0)/100.0));
        } //done
    } //FilterByCustomerRankings

    /**
     * Gives ranking of Excellent WO counts by district
     */
    private class FilterByDistrictRankings extends SwingWorker<Void, Map.Entry<String, Integer>> {
        HashMap<String, Integer> districtMap = new HashMap<>();
        HashMap<String, Integer> ratioMap = new HashMap<>();

        @Override
        protected Void doInBackground() throws Exception {
            for (CombinedWorkOrder cWO : combinedWorkOrders) {
                if (cWO.getQuestions().contains("Overall Performance Was:")) {
                    int index = cWO.getQuestions().indexOf("Overall Performance Was:");
                    String district = cWO.getDistrictName();
                    if (cWO.getAnswers().get(index).equals("Excellent")) {
                        districtMap.put(district, districtMap.getOrDefault(district, 0) + 1);
                    }

                    if (!cWO.getAnswers().get(index).equals("No Response")) {
                        ratioMap.put(district, ratioMap.getOrDefault(district, 0) + 1);
                    }
                }
            }

            for (HashMap.Entry<String, Integer> entry : districtMap.entrySet()) {
                publish(entry);
            }
            return null;
        } //doInBackground

        @Override
        protected void process(List<Map.Entry<String, Integer>> chunks) {

        } //process

        @Override
        protected void done() {
            List<Map.Entry<String, Integer>> sortedDistricts = new ArrayList<>(districtMap.entrySet());
            List<Map.Entry<String, Integer>> ratioList = new ArrayList<>(ratioMap.entrySet());
            sortedDistricts.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            // Clear the text area before displaying the results
            textArea1.setText("");

            double districtPercent = 0;
            double averageRatio = 0;
            // Update the text area with the sorted results
            for (Map.Entry<String, Integer> entry : sortedDistricts) {
                for (Map.Entry<String, Integer> ratio : ratioList) {
                    if (ratio.getKey().equals(entry.getKey())) {
                        districtPercent = ((double) entry.getValue() / ratio.getValue()) * 100.00;
                        averageRatio += districtPercent;
                    }
                }
                textArea1.append("========================\n");
                textArea1.append("District:                  " + entry.getKey() + "\nExcellent Ratings: " + entry.getValue()
                        + "\nExcellence Percentage: " + Math.round(districtPercent*100.0)/100.0 + "\n");
            }
            textArea1.append("\n\n=================STATISTICS SUMMARY=================\n" +
                    "Average Excellence Percentage: " + (Math.round(averageRatio / sortedDistricts.size()*100.0)/100.0));
        } //done
    } //FilterByDistrictRankings

} //Main
