package teniosgmbh.externalcallcontrol.model;


import java.util.Arrays;
import java.util.List;

/**
 * We're using one single Block class here for simplicity.
 * You could also model this into subclasses, using @JsonTypeInfo and @JsonSubTypes.
 */
public class Block {

    private String blockType;
    
    private Integer blockTimeout;
    private List<Destination> destinations;

    private String bridgeMode;

    private String announcementName;
    private Boolean standardAnnouncement;

    private String voiceName;
    private Boolean useSsml;
    private String text;

    private String routingplanName;

    private String hangupCause;

    private String errorAnnouncementName;
    private Boolean standardErrorAnnouncement;
    private String variableName;
    private Integer minDigits;
    private Integer maxDigits;
    private String terminator;
    private Integer maxTries;
    private Integer timeout; /* Please don't confuse this with blockTimeout! */

    private String missingInputAnnouncementName;
    private Boolean standardMissingInputAnnouncement;

    private String language;

    public Block(String blockType) {
        this.blockType = blockType;
    }

    /* Convenience factory methods */

    public static Block newAnnouncementBlock(String announcementName, boolean standardAnnouncement) {
        Block block = new Block("ANNOUNCEMENT");
        block.setAnnouncementName(announcementName);
        block.setStandardAnnouncement(standardAnnouncement);
        return block;
    }

    public static Block newSayBlock(String voiceName, boolean useSsml, String text) {
        Block block = new Block("SAY");
        block.setVoiceName(voiceName);
        block.setUseSsml(useSsml);
        block.setText(text);
        return block;
    }

    public static Block newBridgeBlock(String bridgeMode, Destination... destinations) {
        Block block = new Block("BRIDGE");
        block.setBridgeMode(bridgeMode);
        block.setDestinations(Arrays.asList(destinations));
        return block;
    }

    public static Block newRoutingplanBlock(String routingplanName) {
        Block block = new Block("ROUTINGPLAN");
        block.setRoutingplanName(routingplanName);
        return block;
    }

    public static Block newCollectDigitsBlock(String announcementName, boolean standardAnnouncement,
                                              String errorAnnouncementName, boolean standardErrorAnnouncement,
                                              String variableName, int minDigits, int maxDigits,
                                              String terminator, int maxTries, int timeout) {
        Block block = new Block("COLLECT_DIGITS");
        block.setAnnouncementName(announcementName);
        block.setStandardAnnouncement(standardAnnouncement);
        block.setErrorAnnouncementName(errorAnnouncementName);
        block.setStandardErrorAnnouncement(standardErrorAnnouncement);
        block.setVariableName(variableName);
        block.setMinDigits(minDigits);
        block.setMaxDigits(maxDigits);
        block.setTerminator(terminator);
        block.setMaxTries(maxTries);
        block.setTimeout(timeout);
        return block;
    }

    public static Block newCollectSpeechBlock(String announcementName, boolean standardAnnouncement,
                                              String missingInputAnnouncementName, boolean standardMissingInputAnnouncement,
                                              String language, String variableName,
                                              int maxTries) {
        Block block = new Block("COLLECT_SPEECH");
        block.setAnnouncementName(announcementName);
        block.setStandardAnnouncement(standardAnnouncement);
        block.setMissingInputAnnouncementName(missingInputAnnouncementName);
        block.setStandardMissingInputAnnouncement(standardMissingInputAnnouncement);
        block.setLanguage(language);
        block.setVariableName(variableName);
        block.setMaxTries(maxTries);
        return block;
    }

    public static Block newHangupBlock(String hangupCause) {
        Block block = new Block("HANGUP");
        block.setHangupCause(hangupCause);
        return block;
    }

    public String getBlockType() {
        return blockType;
    }
    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }
    
    public Integer getBlockTimeout() {
        return blockTimeout;
    }
    public void setBlockTimeout(Integer blockTimeout) {
        this.blockTimeout = blockTimeout;
    }

    /* BRIDGE block specific: */

    public String getBridgeMode() {
        return bridgeMode;
    }
    public void setBridgeMode(String bridgeMode) {
        this.bridgeMode = bridgeMode;
    }


    public List<Destination> getDestinations() {
        return destinations;
    }
    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    /* ANNOUNCEMENT block specific (also used for COLLECT_DIGITS block): */

    public String getAnnouncementName() {
        return announcementName;
    }
    public void setAnnouncementName(String announcementName) {
        this.announcementName = announcementName;
    }

    public Boolean getStandardAnnouncement() {
        return standardAnnouncement;
    }
    public void setStandardAnnouncement(Boolean standardAnnouncement) {
        this.standardAnnouncement = standardAnnouncement;
    }

    /* SAY block specific */

    public String getVoiceName() {
        return voiceName;
    }
    public void setVoiceName(final String voiceName) {
        this.voiceName = voiceName;
    }

    public Boolean getUseSsml() {
        return useSsml;
    }
    public void setUseSsml(final Boolean useSsml) {
        this.useSsml = useSsml;
    }

    public String getText() {
        return text;
    }
    public void setText(final String text) {
        this.text = text;
    }

    /* ROUTINGPLAN block specific */

    public String getRoutingplanName() {
        return routingplanName;
    }
    public void setRoutingplanName(String routingplanName) {
        this.routingplanName = routingplanName;
    }

    /* HANGUP block specific: */

    public String getHangupCause() {
        return hangupCause;
    }
    public void setHangupCause(String hangupCause) {
        this.hangupCause = hangupCause;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Block{");
        sb.append("blockType='").append(blockType).append('\'');
        sb.append(", blockTimeout=").append(blockTimeout);
        sb.append(", destinations=").append(destinations);
        sb.append(", bridgeMode='").append(bridgeMode).append('\'');
        sb.append(", announcementName='").append(announcementName).append('\'');
        sb.append(", standardAnnouncement=").append(standardAnnouncement);
        sb.append(", hangupCause='").append(hangupCause).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /* COLLECT_DIGITS block specific: */

    public String getErrorAnnouncementName() {
        return errorAnnouncementName;
    }
    public void setErrorAnnouncementName(String errorAnnouncementName) {
        this.errorAnnouncementName = errorAnnouncementName;
    }

    public Boolean getStandardErrorAnnouncement() {
        return standardErrorAnnouncement;
    }
    public void setStandardErrorAnnouncement(Boolean standardErrorAnnouncement) {
        this.standardErrorAnnouncement = standardErrorAnnouncement;
    }

    public String getVariableName() {
        return variableName;
    }
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Integer getMinDigits() {
        return minDigits;
    }
    public void setMinDigits(Integer minDigits) {
        this.minDigits = minDigits;
    }

    public Integer getMaxDigits() {
        return maxDigits;
    }
    public void setMaxDigits(Integer maxDigits) {
        this.maxDigits = maxDigits;
    }

    public String getTerminator() {
        return terminator;
    }
    public void setTerminator(String terminator) {
        this.terminator = terminator;
    }

    public Integer getMaxTries() {
        return maxTries;
    }
    public void setMaxTries(Integer maxTries) {
        this.maxTries = maxTries;
    }

    /** Please don't confuse this with blockTimeout **/
    public Integer getTimeout() {
        return timeout;
    }
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    /* COLLECT_SPEECH block specific: */

    public String getMissingInputAnnouncementName() {
        return missingInputAnnouncementName;
    }
    public void setMissingInputAnnouncementName(String missingInputAnnouncementName) {
        this.missingInputAnnouncementName = missingInputAnnouncementName;
    }

    public Boolean getStandardMissingInputAnnouncement() {
        return standardMissingInputAnnouncement;
    }
    public void setStandardMissingInputAnnouncement(Boolean standardMissingInputAnnouncement) {
        this.standardMissingInputAnnouncement = standardMissingInputAnnouncement;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(final String language) {
        this.language = language;
    }
}
