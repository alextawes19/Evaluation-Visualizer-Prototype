import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Driver to test combining work orders.
 */
public class Driver2 {
    public static void main(String[] args) {
        ArrayList<WorkOrder> workOrders = new ArrayList<>();
        HashSet<String> rawWOs = new HashSet<>();

        try {
            File writeFile = new File("src/main/resources/Additional.txt");
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
            }

            for (WorkOrder wo : workOrders) {
                rawWOs.add(wo.getWorkOrderID() + "|" + wo.getSignDate());
            }

            ArrayList<CombinedWorkOrder> combinedWorkOrders = constructCombinedWorkOrders(workOrders);

            System.out.println("Raw wo: " + rawWOs.size());
            System.out.println("Combined: " + combinedWorkOrders.size());

//            for (CombinedWorkOrder cWO : combinedWorkOrders) {
//                System.out.println(cWO.toString());
//            }

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e);
        }
    } //main

    public static ArrayList<CombinedWorkOrder> constructCombinedWorkOrders (ArrayList<WorkOrder> workOrders) {
        Map<String, CombinedWorkOrder> combinedWorkOrderMap = new HashMap<>();

        for (WorkOrder wo : workOrders) {
            String key = wo.getWorkOrderID() + "|" + wo.getSignDate();
            CombinedWorkOrder combinedWorkOrder = combinedWorkOrderMap.getOrDefault(key,
                    new CombinedWorkOrder(
                            wo.getWorkOrderID(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            wo.getLocationName(),
                            wo.getCity(),
                            wo.getState(),
                            wo.getSignDate(),
                            wo.getJobID(),
                            wo.getJobCategoryID(),
                            wo.getProgramType(),
                            wo.getCustomerName(),
                            wo.getPrimaryTechnicianID(),
                            wo.getDistrictName(),
                            wo.getWoStatusID(),
                            wo.getGp()
                    ));

            combinedWorkOrder.getQuestions().add(wo.getEvalQuestTxt());
            combinedWorkOrder.getAnswers().add(wo.getEvalQuestValTxt());

            combinedWorkOrderMap.put(key, combinedWorkOrder);
        }

        return new ArrayList<>(combinedWorkOrderMap.values());
    } //constructCombinedWorkOrders
} //Driver2
