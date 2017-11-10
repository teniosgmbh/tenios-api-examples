package teniosgmbh.externalcallcontrol.model;

import java.util.List;

public class BlocksProcessingResult {
    private List<String> validationErrors;

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(final List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BlocksProcessingResult{");
        sb.append("validationErrors=").append(validationErrors);
        sb.append('}');
        return sb.toString();
    }
}