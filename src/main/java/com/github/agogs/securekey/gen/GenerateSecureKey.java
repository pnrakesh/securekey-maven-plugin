package com.github.agogs.securekey.gen;

import com.github.agogs.securekey.util.Util;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.UUID;

import static java.lang.System.out;

/**
 * Create a {@link SecretKey} instance and generate the Base64 encoded string
 * The secret to create SecureKey instance is passed as a parameter in the plugin configuration
 *
 * @author agogs
 *
 */
@Mojo(name = "securekey", defaultPhase = LifecyclePhase.COMPILE )
public class GenerateSecureKey extends AbstractMojo {

    /**
     * @parameter
     */
    private Integer keySize;

    /**
     * @parameter
     */
    private String algorithm;

    /**
     * @parameter default-value="mojo-secret"
     */
    private String secret;

    /**
     * @parameter default-value="securekey.properties"
     */
    private String fileName;

    /**
     * @parameter
     */
    private String filePath;

    /**
     * @parameter default-value="secure.key.encoded"
     */
    private String propertyName;


    /**
     * This method implements the business logic of the plugin
     * @throws MojoExecutionException
     */
    public void execute() throws MojoExecutionException {

        if (algorithm == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("Mention algorithm to use to generate a key in the configuration, for example:\n");
            builder.append("<configuration>\n" +
                    "<keySize>256</keySize><!-- optional, default is 128 bits-->\n" +
                    "<algorithm>AES</algorithm>\n" +
                    "<secret>secret</secret><!-- optional, default = random string -->" +
                    "<fileName>key.properties</fileName><!-- optional, default = securekey.properties -->" +
                    "<filePath>/path/to/file</filePath><!-- optional, default is project root-->" +
                    "<propertyName>property.name</propertyName><!-- optional, default = secure.key.encoded -->" +
                    "</configuration>");
            throw new MojoExecutionException(builder.toString());
        }


        try {
            out.println("********************************************************************");

            out.println("keysize = " + keySize);
            out.println("algorithm = " + algorithm);
            //generate the key
            SecretKey key = generateKey();
            //encode the key to BASE64 string
            String encodedKey = Util.encodeAESKeyToBase64(key);
            //write the BASE64 encoded string to file
            writeKeystringToFile(encodedKey);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage());
        }
        out.println("********************************************************************");

    }

    /**
     * Generate an instance of {@link SecretKey} using the provided algorithm, secret passphrase and the key size
     * @return instance of {@link SecretKey}
     * @throws UnsupportedEncodingException
     */
    private SecretKey generateKey() throws UnsupportedEncodingException {
        try {
            out.println("************ Generating the Secretkey ***********");
            final KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            SecureRandom random = new SecureRandom((secret + UUID.randomUUID().toString()).getBytes(StandardCharsets.UTF_8.name()));
            keyGen.init(keySize, random);
            out.println("************ Successfully generated Secretkey ***********");
            return keyGen.generateKey();
        } catch (final NoSuchAlgorithmException e) {
            // AES functionality is a requirement for any Java SE runtime
            throw new IllegalStateException("AES should always be present in a Java SE runtime", e);
        }
    }

    /**
     *  Write the encoded BASE64 string to file.
     *  The file path has to be specified in the configuration otherwise the path will default ot project root directory.
     * @param keyString
     * @throws IOException
     */
    private void writeKeystringToFile(String keyString) throws IOException {
        File file = new File(filePath + File.separator + fileName);
        out.println("writing contents to file : " + file.getAbsolutePath());
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        Properties properties = new Properties();
        properties.put(propertyName, keyString);
        properties.store(writer, "");
        writer.close();
        out.println("************ write successful ***********");
    }
}
