package teniosgmbh.externalcallcontrol.model;

import java.util.Map;
import java.util.UUID;

public class ExternalPostBlockParams {
    
    private String requestType; // should always be "EXTERNAL_POST"
    private int customerNumber;
    private String accessKey;
    private Map<String, String> variables;

    public enum RequestStatus {
        REQUESTING_BLOCKS, VALIDATION_ERRORS;
    }

    private UUID callControlUuid;
    private int loopCount;
    private RequestStatus requestStatus;
    private BlocksProcessingResult blocksProcessingResult;

    
    public String getRequestType() {
        return requestType;
    }
    public void setRequestType(final String requestType) {
        this.requestType = requestType;
    }
    
    public int getCustomerNumber() {
        return customerNumber;
    }
    public void setCustomerNumber(final int customerNumber) {
        this.customerNumber = customerNumber;
    }
    
    public String getAccessKey() {
        return accessKey;
    }
    public void setAccessKey(final String apiKey) {
        this.accessKey = apiKey;
    }
    
    public Map<String, String> getVariables() {
        return variables;
    }
    public void setVariables(final Map<String, String> variables) {
        this.variables = variables;
    }


    public UUID getCallControlUuid() {
        return callControlUuid;
    }
    public void setCallControlUuid(final UUID callControlUuid) {
        this.callControlUuid = callControlUuid;
    }

    public int getLoopCount() {
        return loopCount;
    }
    public void setLoopCount(final int loopCount) {
        this.loopCount = loopCount;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
    public void setRequestStatus(final RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public BlocksProcessingResult getBlocksProcessingResult() {
        return blocksProcessingResult;
    }
    public void setBlocksProcessingResult(final BlocksProcessingResult blocksProcessingResult) {
        this.blocksProcessingResult = blocksProcessingResult;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExternalPostBlockParams{");
        sb.append("requestType='").append(requestType).append('\'');
        sb.append(", customerNumber=").append(customerNumber);
        sb.append(", accessKey='").append(accessKey).append('\'');
        sb.append(", variables=").append(variables);
        sb.append(", callControlUuid=").append(callControlUuid);
        sb.append(", loopCount=").append(loopCount);
        sb.append(", requestStatus=").append(requestStatus);
        sb.append(", blocksProcessingResult=").append(blocksProcessingResult);
        sb.append('}');
        return sb.toString();
    }
}
