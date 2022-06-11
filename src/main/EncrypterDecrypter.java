package main;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

public class EncrypterDecrypter {
  
  private static final String TRANSFORMATION = "PBEWithMD5AndTripleDES";
  private static Cipher cipher;
  private static SecretKeyFactory keyFactory;
  
  static {
    try {
      cipher = Cipher.getInstance(TRANSFORMATION);
      keyFactory = SecretKeyFactory.getInstance(TRANSFORMATION);
    } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
      e.printStackTrace();
    }
  }
  
  private SecretKey key;
  
  public void setPassword(String password) throws InvalidKeySpecException,
                                                  NoSuchAlgorithmException {
    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
    key = keyFactory.generateSecret(keySpec);
  }
  
  public void encrypt(String content,
                      FileOutputStream outFile) throws InvalidAlgorithmParameterException,
                                                       InvalidKeyException,
                                                       IOException,
                                                       IllegalBlockSizeException,
                                                       BadPaddingException {
    byte[] salt = new byte[8];
    new Random().nextBytes(salt);
    outFile.write(salt);
    
    PBEParameterSpec paramSpec = new PBEParameterSpec(salt,100);
    cipher.init(Cipher.ENCRYPT_MODE,key,paramSpec);
    
    byte[] input = content.getBytes();
    byte[] output = cipher.update(input,0,input.length);
    outFile.write(output);
    
    output = cipher.doFinal();
    if (output != null)
      outFile.write(output);
    
    outFile.flush();
  }
  
  public String decrypt(FileInputStream inFile) throws IOException,
                                                       InvalidAlgorithmParameterException,
                                                       InvalidKeyException,
                                                       IllegalBlockSizeException,
                                                       BadPaddingException {
    byte[] salt = new byte[8];
    inFile.read(salt);
  
    PBEParameterSpec paramSpec = new PBEParameterSpec(salt,100);
    cipher.init(Cipher.DECRYPT_MODE,key,paramSpec);
  
    byte[] input = inFile.readAllBytes();
    byte[] output = cipher.update(input,0,input.length);
    String msg = new String(output);
    
    output = cipher.doFinal();
    if (output != null)
      msg += new String(output);
    
    return msg;
  }
  
}
