import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Main driver for the application.
 */
public class Driver {
    /**
     * Main method.
     *
     * @param args CL arguments. Not used.
     */
    public static void main(String[] args) {
        //START TIMER
        long startTime = System.nanoTime();

        ArrayList<WorkOrder> workOrders = new ArrayList<>();
        ArrayList<WorkOrder> poorWorkOrders = new ArrayList<>();
        HashSet<String> uniqueWoCount = new HashSet<>();

        double numQ6 = 0;
        String q6 = "Overall Performance Was:";
        String q7 = "Additional Comments on Work Provided:";

        //csv file handling
        try {
            File writeFile = new File("src/main/resources/Additional.txt");
            FileWriter fileWriter = new FileWriter(writeFile);
            fileWriter.write("=====WORK ORDERS WITH ADDITIONAL COMMENTS===== \n\n");

            File csvFile = new File("src/main/resources/May.csv");
            FileReader fileReader = new FileReader(csvFile);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextRecord;

            //skip first line of csv
            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                WorkOrder workOrder = new WorkOrder (
                        nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3],
                        nextRecord[4],nextRecord[5], nextRecord[6], nextRecord[7],
                        nextRecord[8],nextRecord[9], nextRecord[10], nextRecord[11],
                        nextRecord[12],nextRecord[13], nextRecord[14]
                );

                if (workOrder.getEvalQuestValTxt().isEmpty()) {
                    workOrder.setEvalQuestValTxt("No Response");
                }

                workOrders.add(workOrder);

                if (workOrder.getEvalQuestTxt().equals(q6)) {
                    numQ6++;
                }
            }

            int numExcellent = 0;
            int numGood = 0;
            int numPoor = 0;
            int numBlank = 0;

            for (WorkOrder wo : workOrders) {
                uniqueWoCount.add(wo.getWorkOrderID());
                if (wo.getEvalQuestValTxt().equals("Excellent")) {
                    numExcellent++;
                } else if (wo.getEvalQuestValTxt().equals("Good")) {
                    numGood++;
                } else if (wo.getEvalQuestValTxt().equals("Poor")) {
                    numPoor++;
                    poorWorkOrders.add(wo);
                } else if (wo.getEvalQuestValTxt().equals("No Response")) {
                    numBlank++;
                }

                String woString = wo.toString();
                //potentially hash for optimization
                if (wo.getEvalQuestTxt().equals(q7) && !wo.getEvalQuestValTxt().equals("No Response")) {
                    fileWriter.write(woString + "\n\n");
                }
            } //for

            fileWriter.write("========================================\n");
            fileWriter.write("\n=====POOR PERFORMANCE WOs=====\n");

            for (WorkOrder wo : poorWorkOrders) {
                String woString = wo.toString();
                fileWriter.write(woString + "\n\n");
            }

            //calculate basic statistics
            double percentExcellent = Math.round(((numExcellent/numQ6) * 100.0) * 100.0) / 100.0;
            double percentGood = Math.round(((numGood/numQ6) * 100.0) * 100.0) / 100.0;
            double percentPoor = Math.round(((numPoor/numQ6) * 100.0) * 100.0) / 100.0;
            double percentBlank = Math.round(((numBlank/numQ6) * 100.0) * 100.0) / 100.0;

            fileWriter.write("========================================\n");
            fileWriter.write("\n=====OVERALL STATISTICS=====\n");
            fileWriter.write("# of unique WOs: " + uniqueWoCount.size() + "\n");
            fileWriter.write("Excellent: " + numExcellent + "(" + percentExcellent + "%)" + "\n");
            fileWriter.write("Good: " + numGood + "(" + percentGood + "%)" + "\n");
            fileWriter.write("Poor: " + numPoor + "(" + percentPoor + "%)" + "\n");
            fileWriter.write("Blank: " + numBlank + "(" + percentBlank + "%)");

            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Unexpected error has occurred: " + e);
        } //try

        //END TIMER
        long endTime = System.nanoTime();
        long totalTimeMs = (endTime - startTime)/1000000;
        long totalTimeS = totalTimeMs/1000;
        System.out.println("Run time: " + totalTimeMs + "ms, " + totalTimeS + "s");
    } //main
} //Driver
