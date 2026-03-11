/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.primitives.Longs;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.spec.EncodedKeySpec;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.Base64;
/*     */ import java.util.Objects;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ 
/*     */ 
/*     */ public class Crypt
/*     */ {
/*     */   private static final String SYMMETRIC_ALGORITHM = "AES";
/*     */   private static final int SYMMETRIC_BITS = 128;
/*     */   private static final String ASYMMETRIC_ALGORITHM = "RSA";
/*     */   private static final int ASYMMETRIC_BITS = 1024;
/*     */   private static final String BYTE_ENCODING = "ISO_8859_1";
/*     */   private static final String HASH_ALGORITHM = "SHA-1";
/*     */   public static final String SIGNING_ALGORITHM = "SHA256withRSA";
/*     */   public static final int SIGNATURE_BYTES = 256;
/*     */   private static final String PEM_RSA_PRIVATE_KEY_HEADER = "-----BEGIN RSA PRIVATE KEY-----";
/*     */   private static final String PEM_RSA_PRIVATE_KEY_FOOTER = "-----END RSA PRIVATE KEY-----";
/*     */   public static final String RSA_PUBLIC_KEY_HEADER = "-----BEGIN RSA PUBLIC KEY-----";
/*     */   private static final String RSA_PUBLIC_KEY_FOOTER = "-----END RSA PUBLIC KEY-----";
/*     */   public static final String MIME_LINE_SEPARATOR = "\n";
/*  44 */   public static final Base64.Encoder MIME_ENCODER = Base64.getMimeEncoder(76, "\n".getBytes(StandardCharsets.UTF_8)); public static final Codec<PublicKey> PUBLIC_KEY_CODEC; public static final Codec<PrivateKey> PRIVATE_KEY_CODEC;
/*     */   static {
/*  46 */     PUBLIC_KEY_CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*     */           try {
/*     */             return DataResult.success(stringToRsaPublicKey($$0));
/*  49 */           } catch (CryptException $$1) {
/*     */             Objects.requireNonNull($$1);
/*     */             return DataResult.error($$1::getMessage);
/*     */           } 
/*     */         }Crypt::rsaPublicKeyToString);
/*  54 */     PRIVATE_KEY_CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*     */           try {
/*     */             return DataResult.success(stringToPemRsaPrivateKey($$0));
/*  57 */           } catch (CryptException $$1) {
/*     */             Objects.requireNonNull($$1);
/*     */             return DataResult.error($$1::getMessage);
/*     */           } 
/*     */         }Crypt::pemRsaPrivateKeyToString);
/*     */   } public static SecretKey generateSecretKey() throws CryptException {
/*     */     try {
/*  64 */       KeyGenerator $$0 = KeyGenerator.getInstance("AES");
/*  65 */       $$0.init(128);
/*  66 */       return $$0.generateKey();
/*  67 */     } catch (Exception $$1) {
/*  68 */       throw new CryptException($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static KeyPair generateKeyPair() throws CryptException {
/*     */     try {
/*  74 */       KeyPairGenerator $$0 = KeyPairGenerator.getInstance("RSA");
/*  75 */       $$0.initialize(1024);
/*     */       
/*  77 */       return $$0.generateKeyPair();
/*  78 */     } catch (Exception $$1) {
/*  79 */       throw new CryptException($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static byte[] digestData(String $$0, PublicKey $$1, SecretKey $$2) throws CryptException {
/*     */     try {
/*  85 */       return digestData(new byte[][] { $$0
/*  86 */             .getBytes("ISO_8859_1"), $$2
/*  87 */             .getEncoded(), $$1
/*  88 */             .getEncoded() });
/*     */     }
/*  90 */     catch (Exception $$3) {
/*  91 */       throw new CryptException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static byte[] digestData(byte[]... $$0) throws Exception {
/*  96 */     MessageDigest $$1 = MessageDigest.getInstance("SHA-1");
/*  97 */     for (byte[] $$2 : $$0) {
/*  98 */       $$1.update($$2);
/*     */     }
/* 100 */     return $$1.digest();
/*     */   }
/*     */   
/*     */   private static <T extends Key> T rsaStringToKey(String $$0, String $$1, String $$2, ByteArrayToKeyFunction<T> $$3) throws CryptException {
/* 104 */     int $$4 = $$0.indexOf($$1);
/* 105 */     if ($$4 != -1) {
/* 106 */       $$4 += $$1.length();
/* 107 */       int $$5 = $$0.indexOf($$2, $$4);
/*     */       
/* 109 */       $$0 = $$0.substring($$4, $$5 + 1);
/*     */     } 
/*     */     try {
/* 112 */       return $$3.apply(Base64.getMimeDecoder().decode($$0));
/* 113 */     } catch (IllegalArgumentException $$6) {
/* 114 */       throw new CryptException($$6);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static PrivateKey stringToPemRsaPrivateKey(String $$0) throws CryptException {
/* 119 */     return rsaStringToKey($$0, "-----BEGIN RSA PRIVATE KEY-----", "-----END RSA PRIVATE KEY-----", Crypt::byteToPrivateKey);
/*     */   }
/*     */   
/*     */   public static PublicKey stringToRsaPublicKey(String $$0) throws CryptException {
/* 123 */     return rsaStringToKey($$0, "-----BEGIN RSA PUBLIC KEY-----", "-----END RSA PUBLIC KEY-----", Crypt::byteToPublicKey);
/*     */   }
/*     */   
/*     */   public static String rsaPublicKeyToString(PublicKey $$0) {
/* 127 */     if (!"RSA".equals($$0.getAlgorithm())) {
/* 128 */       throw new IllegalArgumentException("Public key must be RSA");
/*     */     }
/*     */     
/* 131 */     return "-----BEGIN RSA PUBLIC KEY-----\n" + MIME_ENCODER
/* 132 */       .encodeToString($$0.getEncoded()) + "\n-----END RSA PUBLIC KEY-----\n";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String pemRsaPrivateKeyToString(PrivateKey $$0) {
/* 137 */     if (!"RSA".equals($$0.getAlgorithm())) {
/* 138 */       throw new IllegalArgumentException("Private key must be RSA");
/*     */     }
/*     */     
/* 141 */     return "-----BEGIN RSA PRIVATE KEY-----\n" + MIME_ENCODER
/* 142 */       .encodeToString($$0.getEncoded()) + "\n-----END RSA PRIVATE KEY-----\n";
/*     */   }
/*     */ 
/*     */   
/*     */   private static PrivateKey byteToPrivateKey(byte[] $$0) throws CryptException {
/*     */     try {
/* 148 */       EncodedKeySpec $$1 = new PKCS8EncodedKeySpec($$0);
/* 149 */       KeyFactory $$2 = KeyFactory.getInstance("RSA");
/* 150 */       return $$2.generatePrivate($$1);
/* 151 */     } catch (Exception $$3) {
/* 152 */       throw new CryptException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static PublicKey byteToPublicKey(byte[] $$0) throws CryptException {
/*     */     try {
/* 158 */       EncodedKeySpec $$1 = new X509EncodedKeySpec($$0);
/* 159 */       KeyFactory $$2 = KeyFactory.getInstance("RSA");
/* 160 */       return $$2.generatePublic($$1);
/* 161 */     } catch (Exception $$3) {
/* 162 */       throw new CryptException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SecretKey decryptByteToSecretKey(PrivateKey $$0, byte[] $$1) throws CryptException {
/* 167 */     byte[] $$2 = decryptUsingKey($$0, $$1);
/*     */     try {
/* 169 */       return new SecretKeySpec($$2, "AES");
/* 170 */     } catch (Exception $$3) {
/* 171 */       throw new CryptException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static byte[] encryptUsingKey(Key $$0, byte[] $$1) throws CryptException {
/* 176 */     return cipherData(1, $$0, $$1);
/*     */   }
/*     */   
/*     */   public static byte[] decryptUsingKey(Key $$0, byte[] $$1) throws CryptException {
/* 180 */     return cipherData(2, $$0, $$1);
/*     */   }
/*     */   
/*     */   private static byte[] cipherData(int $$0, Key $$1, byte[] $$2) throws CryptException {
/*     */     try {
/* 185 */       return setupCipher($$0, $$1.getAlgorithm(), $$1).doFinal($$2);
/* 186 */     } catch (Exception $$3) {
/* 187 */       throw new CryptException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Cipher setupCipher(int $$0, String $$1, Key $$2) throws Exception {
/* 192 */     Cipher $$3 = Cipher.getInstance($$1);
/* 193 */     $$3.init($$0, $$2);
/* 194 */     return $$3;
/*     */   }
/*     */   
/*     */   public static Cipher getCipher(int $$0, Key $$1) throws CryptException {
/*     */     try {
/* 199 */       Cipher $$2 = Cipher.getInstance("AES/CFB8/NoPadding");
/* 200 */       $$2.init($$0, $$1, new IvParameterSpec($$1.getEncoded()));
/* 201 */       return $$2;
/* 202 */     } catch (Exception $$3) {
/* 203 */       throw new CryptException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class SaltSupplier {
/* 208 */     private static final SecureRandom secureRandom = new SecureRandom();
/*     */     
/*     */     public static long getLong() {
/* 211 */       return secureRandom.nextLong();
/*     */     } }
/*     */   public static final class SaltSignaturePair extends Record { private final long salt; private final byte[] signature;
/*     */     
/* 215 */     public SaltSignaturePair(long $$0, byte[] $$1) { this.salt = $$0; this.signature = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/Crypt$SaltSignaturePair;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #215	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 215 */       //   0	7	0	this	Lnet/minecraft/util/Crypt$SaltSignaturePair; } public long salt() { return this.salt; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/Crypt$SaltSignaturePair;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #215	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/Crypt$SaltSignaturePair; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/Crypt$SaltSignaturePair;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #215	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/Crypt$SaltSignaturePair;
/* 215 */       //   0	8	1	$$0	Ljava/lang/Object; } public byte[] signature() { return this.signature; }
/* 216 */      public static final SaltSignaturePair EMPTY = new SaltSignaturePair(0L, ByteArrays.EMPTY_ARRAY);
/*     */     
/*     */     public SaltSignaturePair(FriendlyByteBuf $$0) {
/* 219 */       this($$0.readLong(), $$0.readByteArray());
/*     */     }
/*     */     
/*     */     public boolean isValid() {
/* 223 */       return (this.signature.length > 0);
/*     */     }
/*     */     
/*     */     public static void write(FriendlyByteBuf $$0, SaltSignaturePair $$1) {
/* 227 */       $$0.writeLong($$1.salt);
/* 228 */       $$0.writeByteArray($$1.signature);
/*     */     }
/*     */     
/*     */     public byte[] saltAsBytes() {
/* 232 */       return Longs.toByteArray(this.salt);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static interface ByteArrayToKeyFunction<T extends Key> {
/*     */     T apply(byte[] param1ArrayOfbyte) throws CryptException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\Crypt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */