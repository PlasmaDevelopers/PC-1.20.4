/*    */ package net.minecraft.util;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.security.PrivateKey;
/*    */ import java.security.Signature;
/*    */ import java.security.SignatureException;
/*    */ import java.util.Objects;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public interface Signer {
/* 10 */   public static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */ 
/*    */   
/*    */   default byte[] sign(byte[] $$0) {
/* 15 */     return sign($$1 -> $$1.update($$0));
/*    */   }
/*    */   
/*    */   static Signer from(PrivateKey $$0, String $$1) {
/* 19 */     return $$2 -> {
/*    */         try {
/*    */           Signature $$3 = Signature.getInstance($$0); $$3.initSign($$1);
/*    */           Objects.requireNonNull($$3);
/*    */           $$2.update($$3::update);
/*    */           return $$3.sign();
/* 25 */         } catch (Exception $$4) {
/*    */           throw new IllegalStateException("Failed to sign message", $$4);
/*    */         } 
/*    */       };
/*    */   }
/*    */   
/*    */   byte[] sign(SignatureUpdater paramSignatureUpdater);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\Signer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */