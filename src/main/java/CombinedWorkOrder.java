import java.util.ArrayList;

/**
 * Represents a work order and its responses to every question in the evaluation form.
 */
public class CombinedWorkOrder {
    private final String workOrderID;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private final String locationName;
    private final String city;
    private final String state;
    private final String signDate;
    private final String jobID;
    private final String jobCategoryID;
    private final String programType;
    private final String customerName;
    private final String primaryTechnicianID;
    private final String districtName;
    private final String woStatusID;
    private final String gp;

    /**
     * Constructs a combined work order.
     *
     * @param workOrderID work order ID
     * @param questions list of form questions
     * @param answers list of answers to form questions
     * @param locationName store name of WO
     * @param city city of WO
     * @param state state of WO
     * @param signDate sign date and time of WO
     * @param jobID job ID of WO
     * @param jobCategoryID job category of WO
     * @param programType program type of WO
     * @param customerName customer name of WO
     * @param primaryTechnicianID primary technician of WO
     * @param districtName district name of WO
     * @param woStatusID status ID of WO
     * @param gp gross profit of WO
     */
    public CombinedWorkOrder (String workOrderID, ArrayList<String> questions, ArrayList<String> answers, String locationName,
                              String city, String state, String signDate, String jobID, String jobCategoryID,
                              String programType, String customerName, String primaryTechnicianID,
                              String districtName, String woStatusID, String gp) {
        this.workOrderID = workOrderID;
        this.questions = questions;
        this.answers = answers;
        this.locationName = locationName;
        this.city = city;
        this.state = state;
        this.signDate = signDate;
        this.jobID = jobID;
        this.jobCategoryID = jobCategoryID;
        this.programType = programType;
        this.customerName = customerName;
        this.primaryTechnicianID = primaryTechnicianID;
        this.districtName = districtName;
        this.woStatusID = woStatusID;
        this.gp = gp;
    } //CombinedWorkOrder

    //GETTERS
    public String getWorkOrderID() {
        return workOrderID;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getSignDate() {
        return signDate;
    }

    public String getJobID() {
        return jobID;
    }

    public String getJobCategoryID() {
        return jobCategoryID;
    }

    public String getProgramType() {
        return programType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPrimaryTechnicianID() {
        return primaryTechnicianID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getWoStatusID() {
        return woStatusID;
    }

    public String getGp() {
        return gp;
    }

    //SETTERS
    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    //ADDITIONAL CLASS METHODS
    @Override
    public String toString() {
        return "WorkOrder { \n" +
                "\t WO Number=             " + workOrderID + '\n' +
                "\t Questions=             " + questions.toString() + '\n' +
                "\t Answers=               " + answers.toString() + '\n' +
                "\t Store=                 " + locationName + '\n' +
                "\t City=                  " + city + '\n' +
                "\t State=                 " + state + '\n' +
                "\t Sign Date=             " + signDate + '\n' +
                "\t Job ID=                " + jobID + '\n' +
                "\t Job Category ID=       " + jobCategoryID + '\n' +
                "\t Program Type=          " + programType + '\n' +
                "\t Customer Name=         " + customerName + '\n' +
                "\t Primary Tech=          " + primaryTechnicianID + '\n' +
                "\t District Name=         " + districtName + '\n' +
                "\t WO Status ID=          " + woStatusID + '\n' +
                "\t GP=                   $" + gp + '\n' +
                '}';
    } //toString
} //CombinedWorkOrder
