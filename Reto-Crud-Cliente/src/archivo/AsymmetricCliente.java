package archivo;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static com.google.common.io.ByteStreams.toByteArray;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author Oscar
 */
public class AsymmetricCliente {

    public byte[] cipher(String mensaje) {
        byte[] encodedMessage = null;
        try {
            InputStream input = getClass().getResourceAsStream("Public.key");
            byte fileKey[] = toByteArray(input);
            input.close();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = c.doFinal(mensaje.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedMessage;
    }

    public byte[] decrypt(byte[] mensaje) {
        byte[] decodedMessage = null;
        try {
            byte fileKey[] = fileReader("./Reto-crud-server/java/archivos/Private.key");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(fileKey);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.DECRYPT_MODE, privateKey);
            decodedMessage = c.doFinal(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedMessage;
    }

    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}