/**
 * Represents a work order within the evaluation form and its response to a specific form question.
 */
public class WorkOrder {
    private final String workOrderID;
    private final String evalQuestTxt;
    private String evalQuestValTxt;
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
    /**
     * Gets the WO ID.
     *
     * @return WO ID attached to the currently evaluated WO.
     */
    public String getWorkOrderID() {
        return workOrderID;
    } //getWorkOrderID

    /**
     * Gets the question currently being evaluated for a specific WO.
     *
     * @return the question on the evaluation form for this object
     */
    public String getEvalQuestTxt() {
        return evalQuestTxt;
    } //getEvalQuestTxt

    /**
     * Gets the response to a question on the evaluation form.
     *
     * @return the response to the specific question
     */
    public String getEvalQuestValTxt() {
        return evalQuestValTxt;
    } //getEvalQuestValTxt

    /**
     * Gets the store name for the current WO.
     *
     * @return store name work was completed
     */
    public String getLocationName() {
        return locationName;
    } //getLocationName

    /**
     * Gets the city for the current WO.
     *
     * @return city where work was completed
     */
    public String getCity() {
        return city;
    } //getCity

    /**
     * Gets the state where WO was completed.
     *
     * @return state where WO was completed
     */
    public String getState() {
        return state;
    } //getState

    /**
     * Gets the evaluation's sign date and time.
     *
     * @return date and time eval form was signed
     */
    public String getSignDate() {
        return signDate;
    } //getSignDate

    /**
     * Gets the job ID.
     *
     * @return job ID for WO
     */
    public String getJobID() {
        return jobID;
    } //getJobID

    /**
     * Gets job category WO is in.
     *
     * @return job category for WO
     */
    public String getJobCategoryID() {
        return jobCategoryID;
    } //getJobCategoryID

    /**
     * Gets program type WO is a part of.
     *
     * @return program type of WO
     */
    public String getProgramType() {
        return programType;
    } //getProgramType

    /**
     * Gets name of customer.
     *
     * @return name of customer for WO
     */
    public String getCustomerName() {
        return customerName;
    } //getCustomerName

    /**
     * Gets name of primary technician which completed the work.
     *
     * @return name of primary technician for the WO
     */
    public String getPrimaryTechnicianID() {
        return primaryTechnicianID;
    } //getPrimaryTechnicianID

    /**
     * Gets district name in which WO was completed.
     *
     * @return name of district work was completed in.
     */
    public String getDistrictName() {
        return districtName;
    } //getDistrictName

    /**
     * Gets WO status ID.
     *
     * @return status of WO
     */
    public String getWoStatusID() {
        return woStatusID;
    } //getWoStatusID

    /**
     * Gets gross profit.
     *
     * @return gross profit of WO.
     */
    public String getGp() {
        return gp;
    } //getGp

    //SETTERS

    /**
     * Sets evalQuestValTxt.
     *
     * @param evalQuestValTxt 'No Response' for blank responses
     */
    public void setEvalQuestValTxt(String evalQuestValTxt) {
        this.evalQuestValTxt = evalQuestValTxt;
    }

    //ADDITIONAL CLASS METHODS

    @Override
    public String toString() {
        return "WorkOrder { \n" +
                "\t WO Number=             " + workOrderID + '\n' +
                "\t Question=              " + evalQuestTxt + '\n' +
                "\t Answer=                " + evalQuestValTxt + '\n' +
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

} //WorkOrder
