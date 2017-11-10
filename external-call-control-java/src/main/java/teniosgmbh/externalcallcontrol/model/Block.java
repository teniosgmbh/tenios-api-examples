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

    private String hangupCause;
    
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

    public static Block newBridgeBlock(String bridgeMode, Destination... destinations) {
        Block block = new Block("BRIDGE");
        block.setBridgeMode(bridgeMode);
        block.setDestinations(Arrays.asList(destinations));
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

    /* ANNOUNCEMENT block specific: */

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
}
