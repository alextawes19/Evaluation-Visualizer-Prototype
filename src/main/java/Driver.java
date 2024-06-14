import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Main driver for the application.
 */
public class Driver {
    public static void main(String[] args) {
        //START TIMER
        long startTime = System.nanoTime();

        ArrayList<WorkOrder> workOrders = new ArrayList<>();

        double numQ6 = 0;
        String q6 = "Overall Performance Was:";

        //csv file handling
        try {
            File csvFile = new File("src/main/resources/TwoDays.csv");
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
                workOrders.add(workOrder);

                if (workOrder.getEvalQuestTxt().equals(q6)) {
                    numQ6++;
                }
            }
        } catch (Exception e) {
            System.out.println("Unexpected error has occurred.");
        } //try

        int numExcellent = 0;
        int numGood = 0;
        int numPoor = 0;

        for (WorkOrder wo : workOrders) {
            if (wo.getEvalQuestValTxt().equals("Excellent")) {
                numExcellent++;
            } else if (wo.getEvalQuestValTxt().equals("Good")) {
                numGood++;
            } else if (wo.getEvalQuestValTxt().equals("Poor")) {
                numPoor++;
            }
        }
        double size = workOrders.size();
        double percentExcellent = (numExcellent/numQ6) * 100;
        double percentGood = (numGood/numQ6) * 100;
        double percentPoor = (numPoor/numQ6) * 100;

        System.out.println(workOrders.get(0).toString());
        System.out.println("# of WOs: " + workOrders.size());
        System.out.println("-----STATISTICS-----");
        System.out.println("Excellent: " + numExcellent + "(" + percentExcellent + ")");
        System.out.println("Good: " + numGood + "(" + percentGood + ")");
        System.out.println("Poor: " + numPoor + "(" + percentPoor + ")");


        //END TIMER
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000;
        long totalTimeS = totalTime/1000;
        System.out.println("Run time: " + totalTime + "ms, " + totalTimeS + "s");
    } //main
} //Driver
