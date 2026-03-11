/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.mojang.authlib.yggdrasil.ServicesKeyInfo;
/*    */ import com.mojang.authlib.yggdrasil.ServicesKeySet;
/*    */ import com.mojang.authlib.yggdrasil.ServicesKeyType;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.security.PublicKey;
/*    */ import java.security.Signature;
/*    */ import java.security.SignatureException;
/*    */ import java.util.Collection;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public interface SignatureValidator
/*    */ {
/*    */   public static final SignatureValidator NO_VALIDATION = ($$0, $$1) -> true;
/* 18 */   public static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean validate(byte[] $$0, byte[] $$1) {
/* 23 */     return validate($$1 -> $$1.update($$0), $$1);
/*    */   }
/*    */   
/*    */   private static boolean verifySignature(SignatureUpdater $$0, byte[] $$1, Signature $$2) throws SignatureException {
/* 27 */     Objects.requireNonNull($$2); $$0.update($$2::update);
/* 28 */     return $$2.verify($$1);
/*    */   }
/*    */   
/*    */   static SignatureValidator from(PublicKey $$0, String $$1) {
/* 32 */     return ($$2, $$3) -> {
/*    */         try {
/*    */           Signature $$4 = Signature.getInstance($$0);
/*    */           $$4.initVerify($$1);
/*    */           return verifySignature($$2, $$3, $$4);
/* 37 */         } catch (Exception $$5) {
/*    */           LOGGER.error("Failed to verify signature", $$5);
/*    */           return false;
/*    */         } 
/*    */       };
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   static SignatureValidator from(ServicesKeySet $$0, ServicesKeyType $$1) {
/* 46 */     Collection<ServicesKeyInfo> $$2 = $$0.keys($$1);
/* 47 */     if ($$2.isEmpty()) {
/* 48 */       return null;
/*    */     }
/* 50 */     return ($$1, $$2) -> $$0.stream().anyMatch(());
/*    */   }
/*    */   
/*    */   boolean validate(SignatureUpdater paramSignatureUpdater, byte[] paramArrayOfbyte);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SignatureValidator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */