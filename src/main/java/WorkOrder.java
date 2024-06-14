/**
 * Represents a work order within the evaluation form and its response to a specific form question.
 */
public class WorkOrder {
    private final String workOrderID;
    private final String evalQuestTxt;
    private final String evalQuestValTxt;
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
     *  Constructs WorkOrder object.
     *
     * @param workOrderID work order ID
     * @param evalQuestTxt eval question text
     * @param evalQuestValTxt eval answer text
     * @param locationName store name
     * @param city city
     * @param state state
     * @param signDate date and time of signature
     * @param jobID job ID
     * @param jobCategoryID job category ID
     * @param programType program type
     * @param customerName customer name
     * @param primaryTechnicianID technician username
     * @param districtName district
     * @param woStatusID WO status ID
     * @param gp gross profit
     */
    public WorkOrder(String workOrderID, String evalQuestTxt, String evalQuestValTxt, String locationName,
                     String city, String state, String signDate, String jobID, String jobCategoryID,
                     String programType, String customerName, String primaryTechnicianID,
                     String districtName, String woStatusID, String gp) {
        this.workOrderID = workOrderID;
        this.evalQuestTxt = evalQuestTxt;
        this.evalQuestValTxt = evalQuestValTxt;
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
    } //WorkOrder



    // GETTERS
    public String getWorkOrderID() {
        return workOrderID;
    } //getWorkOrderID

    public String getEvalQuestTxt() {
        return evalQuestTxt;
    } //getEvalQuestTxt

    public String getEvalQuestValTxt() {
        return evalQuestValTxt;
    } //getEvalQuestValTxt

    public String getLocationName() {
        return locationName;
    } //getLocationName

    public String getCity() {
        return city;
    } //getCity

    public String getState() {
        return state;
    } //getState

    public String getSignDate() {
        return signDate;
    } //getSignDate

    public String getJobID() {
        return jobID;
    } //getJobID

    public String getJobCategoryID() {
        return jobCategoryID;
    } //getJobCategoryID

    public String getProgramType() {
        return programType;
    } //getProgramType

    public String getCustomerName() {
        return customerName;
    } //getCustomerName

    public String getPrimaryTechnicianID() {
        return primaryTechnicianID;
    } //getPrimaryTechnicianID

    public String getDistrictName() {
        return districtName;
    } //getDistrictName

    public String getWoStatusID() {
        return woStatusID;
    } //getWoStatusID

    public String getGp() {
        return gp;
    } //getGp

    //ADDITIONAL CLASS METHODS

    @Override
    public String toString() {
        return "WorkOrder{" +
                "workOrderID='" + workOrderID + '\'' +
                ", evalQuestTxt='" + evalQuestTxt + '\'' +
                ", evalQuestValTxt='" + evalQuestValTxt + '\'' +
                ", locationName='" + locationName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", signDate='" + signDate + '\'' +
                ", jobID='" + jobID + '\'' +
                ", jobCategoryID='" + jobCategoryID + '\'' +
                ", programType='" + programType + '\'' +
                ", customerName='" + customerName + '\'' +
                ", primaryTechnicianID='" + primaryTechnicianID + '\'' +
                ", districtName='" + districtName + '\'' +
                ", woStatusID='" + woStatusID + '\'' +
                ", gp='" + gp + '\'' +
                '}';
    } //toString

} //WorkOrder
