package org.agogs.crypto.util;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Expose utility methods
 *
 * @author pnrakesh
 */
public class Util {
    /**
     * Encode a {@link SecretKey} object into base64 encoded string
     * @param aesKey - Secret key
     * @return - base64 encoded string
     * @throws IllegalArgumentException
     */
    public static String encodeAESKeyToBase64(final SecretKey aesKey)
            throws IllegalArgumentException {
        if (!aesKey.getAlgorithm().equalsIgnoreCase("AES")) {
            throw new IllegalArgumentException("Not an AES key");
        }

        final byte[] keyData = aesKey.getEncoded();
        final String encodedKey = Base64.getEncoder().encodeToString(keyData);
        return encodedKey;
    }
}
