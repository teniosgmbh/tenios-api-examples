package teniosgmbh.externalcallcontrol.model;

import java.util.Arrays;
import java.util.List;

public class CallControlResponse {

    private List<Block> blocks;

    public CallControlResponse(Block... blocks) {
        this.blocks = Arrays.asList(blocks);
    }
    
    public List<Block> getBlocks() {
        return blocks;
    }
    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

}
