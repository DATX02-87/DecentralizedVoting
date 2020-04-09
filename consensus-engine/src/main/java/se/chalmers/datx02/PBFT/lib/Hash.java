package se.chalmers.datx02.PBFT.lib;

import se.chalmers.datx02.PBFT.lib.exceptions.SigningError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    /**
     * Tries to has a byte with the SHA-512 algorithm
     * @param bytes specifies the bytes to be hashed
     * @return returns the hashed SHA-512 byte
     */
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

    /**
     * Tries to verify a content with a SHA-512 hashed content
     * @param content specifies the non-hashed content
     * @param content_hash specifies the hashed content
     * @throws SigningError if the hash verification fails
     */
    public static void verifySha512(byte[] content, byte[] content_hash) throws SigningError {
        byte[] computed_sha = hashSha512(content);

        if(computed_sha != content_hash)
            throw new SigningError(String.format("Hash verification failed - Content: %s Hash: %s", content, content_hash));
    }
}
