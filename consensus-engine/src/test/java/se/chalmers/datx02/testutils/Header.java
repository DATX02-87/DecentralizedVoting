package se.chalmers.datx02.testutils;

import com.google.api.client.util.Key;

import java.util.List;

public class Header {
    @Key(value = "batch_ids")
    private List<String> batchIds;

    @Key(value = "block_num")
    private long blockNum;

    @Key(value = "previous_block_id")
    private String previousBlockId;

    @Key(value = "signer_public_key")
    private String signerPublicKey;

    @Key(value = "state_root_hash")
    private String stateRootHash;

    public List<String> getBatchIds() {
        return batchIds;
    }

    public long getBlockNum() {
        return blockNum;
    }

    public String getPreviousBlockId() {
        return previousBlockId;
    }

    public String getSignerPublicKey() {
        return signerPublicKey;
    }

    public String getStateRootHash() {
        return stateRootHash;
    }
}
