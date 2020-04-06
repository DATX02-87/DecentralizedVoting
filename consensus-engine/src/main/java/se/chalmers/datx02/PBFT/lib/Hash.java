package se.chalmers.datx02.PBFT.lib;

import se.chalmers.datx02.PBFT.lib.exceptions.SigningError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static byte[] hashSha512(byte[] bytes){
        byte[] hashedBytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(bytes);

            hashedBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashedBytes;
    }

    public static void verifySha512(byte[] content, byte[] content_hash) throws SigningError {
        byte[] computed_sha = hashSha512(content);

        if(computed_sha != content_hash)
            throw new SigningError(String.format("Hash verification failed - Content: %s Hash: %s", content, content_hash));
    }
}
