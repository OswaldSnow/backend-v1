package backend.v1.tools;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class RsaTools {
    @Value("${rsa.private-key-path}")
    private String privateKeyPath;

    @Value("${rsa.public-key-path}")
    private String publicKeyPath;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    static {
        // 注册 BouncyCastle 提供者
        Security.addProvider(new BouncyCastleProvider());
    }

    @PostConstruct
    public void init() throws Exception {
        // 检查密钥文件是否存在，不存在则生成新的密钥对
        File privateKeyFile = new File(privateKeyPath);
        File publicKeyFile = new File(publicKeyPath);

        if (!privateKeyFile.exists() || !publicKeyFile.exists()) {
            generateKeyPair();
        } else {
            loadKeys();
        }
    }

    // 生成新的密钥对并保存到文件
    private void generateKeyPair() throws Exception {
        // 创建路径
        createDirectoryIfNotExists(privateKeyPath);
        createDirectoryIfNotExists(publicKeyPath);

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        generator.initialize(2048, new SecureRandom());
        KeyPair keyPair = generator.generateKeyPair();

        // 保存私钥
        try (PemWriter pemWriter = new PemWriter(new FileWriter(privateKeyPath))) {
            pemWriter.writeObject(new PemObject("PRIVATE KEY", keyPair.getPrivate().getEncoded()));
        }

        // 保存公钥
        try (PemWriter pemWriter = new PemWriter(new FileWriter(publicKeyPath))) {
            pemWriter.writeObject(new PemObject("PUBLIC KEY", keyPair.getPublic().getEncoded()));
        }

        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // 创建目录的辅助方法
    private void createDirectoryIfNotExists(String filePath) {
        File file = new File(filePath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }
    }

    // 从文件加载密钥
    private void loadKeys() throws Exception {
        // 加载私钥
        try (PemReader pemReader = new PemReader(new FileReader(privateKeyPath))) {
            PemObject pemObject = pemReader.readPemObject();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(pemObject.getContent());
            KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
            this.privateKey = factory.generatePrivate(privateKeySpec);
        }

        // 加载公钥
        try (PemReader pemReader = new PemReader(new FileReader(publicKeyPath))) {
            PemObject pemObject = pemReader.readPemObject();
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(pemObject.getContent());
            KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
            this.publicKey = factory.generatePublic(publicKeySpec);
        }
    }

    // RSA 加密
    public byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    // RSA 解密
    public byte[] decrypt(byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encrypted);
    }

    // 获取公钥字符串（提供给前端）
    public String getPublicKeyString() {
        try (StringWriter stringWriter = new StringWriter();
             PemWriter pemWriter = new PemWriter(stringWriter)) {
            pemWriter.writeObject(new PemObject("PUBLIC KEY", publicKey.getEncoded()));
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to export public key", e);
        }
    }

}
