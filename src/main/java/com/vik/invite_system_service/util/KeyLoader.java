package com.vik.invite_system_service.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyLoader {
    private static final String KEY_FOLDER_PATH = "D:/keys/";

    public static RSAPrivateKey loadPrivateKey(String filename) throws Exception {
        try {
            String filepath = KEY_FOLDER_PATH + filename;
            String key = new String(Files.readAllBytes(Paths.get(filepath)))
                    .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            byte[] keyBytes = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the private key file", e);
        }
    }

    public static RSAPublicKey loadPublicKey(String filename) throws Exception {
        try {
            String filepath = KEY_FOLDER_PATH + filename;
            String key = new String(Files.readAllBytes(Paths.get(filepath)))
                    .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");
            byte[] keyBytes = Base64.getDecoder().decode(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the public key file", e);
        }
    }
}
