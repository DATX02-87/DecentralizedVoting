package se.chalmers.datx02.PBFT.message;

public class PBFTMessage {
    private MessageExtensions msg_type;
    private int view, seq_num;
    private byte[] signer_id;





    // TODO: Impl methods with new protos

    public PBFTMessage(MessageExtensions msg_type, int view, int seq_num, byte[] signer_id){
        this.msg_type = msg_type;
        this.view = view;
        this.seq_num = seq_num;
        this.signer_id = signer_id;
    }


    // TODO: Maybe need to override?
    @Override
    public int hashCode() {
        return super.hashCode();
    }


    public byte[] getSignerId(){
        return signer_id;
    }

    public int getSeq(){
        return seq_num;
    }

    public int getView(){
        return view;
    }

    @Override
    public String toString(){
        return null;
    }
}
