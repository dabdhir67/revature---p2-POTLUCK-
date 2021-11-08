package com.revature.utilities;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.revature.models.Chef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

public class SecurityUtil {
    private static final Logger logger = LogManager.getLogger(SecurityUtil.class);
    private static final int SALT_LENGTH = 512;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static RSAEncrypter encrypter;
    private static RSADecrypter decrypter;

    /**
     * Generates a string used to hash passwords
     * @return a base 64 encoded string
     */
    private static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SECURE_RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Uses the given salt to hash the password
     * @param password string to hash
     * @param salt string to hash the password with
     * @return the hashed string
     */
    private static String hashPassword(String password, String salt) {
        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
        Arrays.fill(chars, Character.MIN_VALUE);

        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(securePassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(e.getMessage());
        } finally {
            spec.clearPassword();
        }
        return null;
    }

    /**
     * Hashes the given password to create a passkey
     * @param password the string to hash
     * @return the hashed password string
     */
    public static String hashPassword(String password) {
        if (password == null) {
            logger.error("Cannot hash null values. Exiting method and returning null.");
            return null;
        }
        if (password.isEmpty()) {
            logger.error("Cannot hash an empty string. Exiting method and returning null.");
            return null;
        }
        String salt = generateSalt();
        String hashed = hashPassword(password, salt);
        return hashed + salt;
    }

    /**
     * Determines is the password given is the one associated with the given pass key
     * @param password the password to test for validity
     * @param passKey the passkey to check the password against
     * @return true if password is correct
     */
    public static boolean isPassword(String password, String passKey) {
        if (password == null || passKey == null) {
            logger.error("Cannot verify null password or pass key. Exiting method and returning false.");
            return false;
        }
        if (password.isEmpty() || passKey.isEmpty()) {
            logger.error("Cannot verify empty password or pass key. Exiting method and returning false.");
            return false;
        }
        if (passKey.length() < 88) return false;

        String encrypted = hashPassword(password, passKey.substring(88));
        if (encrypted == null) {
            logger.error("Unable to verify password validity. Exiting method and returning false.");
            return false;
        }
        return encrypted.equals(passKey.substring(0,88));
    }

    /**
     * Generates a unique random id
     * @return the id
     */
    public static Integer getId() {
        UUID id = UUID.randomUUID();
        int uid = (""+id).hashCode();
        String filterStr = "" + uid;
        String str = filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }

    /**
     * Generates the encryption and decryption objects used to encrypt/decrypt tokens
     */
    private static void generateEncryptionObjects() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //Initialize key size
            keyPairGenerator.initialize(2048);
            // Generate the key pair
            KeyPair keyPair = keyPairGenerator.genKeyPair();

            // Create KeyFactory and RSA Keys Specs
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);

            // Generate (and retrieve) RSA Keys from the KeyFactory using Keys Specs
            RSAPublicKey publicRsaKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
            RSAPrivateKey privateRsaKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

            // Create an RSA encrypted with the specified public RSA key
            encrypter = new RSAEncrypter(publicRsaKey);
            // Create a decrypter with the specified private RSA key
            decrypter = new RSADecrypter(privateRsaKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Generates a string encrypting the given id and additional validation information
     * @param chef the object that is going to be encrypted
     * @return the encrypted token string
     */
    public static String generateToken(Chef chef) {
        if (chef == null) {
            logger.error("Cannot generate token from null values. Exiting method and returning null.");
            return null;
        }

        if (encrypter == null || decrypter == null) {
            generateEncryptionObjects();
        }

        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
        claimsSet.issuer("potluck-system");
        claimsSet.subject("potluck-identity-token");

        claimsSet.claim("chef", chef);
        claimsSet.claim("id", chef.getC_id());
        claimsSet.claim("passkey", chef.getPasskey());

        claimsSet.expirationTime(Timestamp.valueOf(LocalDateTime.now().plusHours(4)));
        claimsSet.notBeforeTime(Timestamp.valueOf(LocalDateTime.now()));
        claimsSet.jwtID(UUID.randomUUID().toString());

        // Create the JWE header and specify:
        // RSA-OAEP-256 as the encryption algorithm
        // 128-bit AES/GCM as the encryption method
        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
        // Initialized the EncryptedJWT object
        EncryptedJWT jwt = new EncryptedJWT(header, claimsSet.build());

        try {
            jwt.encrypt(encrypter);
        } catch (JOSEException e) {
            logger.error("Unable to encrypt token" + e.getMessage());
        }

        return jwt.serialize();
    }

    /**
     * Decrypts token, checks for correct com.revature.data, and returns the id and access level within
     * @param token string of the encrypted token
     * @return id of token, null if token invalid
     */
    public static Chef validateToken(String token) {
        if (decrypter == null) {
            logger.error("Decrypter never initialized!");
            return null;
        }
        if (token == null) {
            logger.error("Cannot validate null token. Exiting method and returning null.");
            return null;
        }
        if (token.isEmpty()) {
            logger.error("Cannot validate empty token string. Exiting method and returning null.");
            return null;
        }
        try {
            EncryptedJWT jwt = EncryptedJWT.parse(token);

            try {
                jwt.decrypt(decrypter);
            } catch (JOSEException e) {
                logger.error("Unable to decrypt token " + e.getMessage());
                return null;
            }
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            if (!claims.getIssuer().equals("potluck-system")) return null;
            if (claims.getExpirationTime().before(Timestamp.valueOf(LocalDateTime.now()))) return null;
            if (Timestamp.valueOf(LocalDateTime.now()).before(claims.getNotBeforeTime())) return null;

            Map<String, Object> guts = claims.getJSONObjectClaim("chef");

            Integer id = claims.getIntegerClaim("id");
            String username = (String) guts.get("username");
            String passkey = claims.getStringClaim("passkey");
            String firstName = (String) guts.get("firstName");
            String lastName = (String) guts.get("lastName");
            String email = (String) guts.get("email");

            return new Chef(id, username, passkey, firstName, lastName, email);
        } catch (ParseException e) {
            logger.error("Unable to parse token " + e.getMessage());
        }
        return null;
    }
}
