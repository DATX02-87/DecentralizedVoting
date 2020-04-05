package se.chalmers.datx02.api.model;

public class TransactionDataToSignResponse {
    private byte[] dataToSign;

    public byte[] getDataToSign() {
        return dataToSign;
    }

    public void setDataToSign(byte[] dataToSign) {
        this.dataToSign = dataToSign;
    }
}
