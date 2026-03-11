/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.SignatureValidator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyBased
/*    */   implements SignedMessageValidator
/*    */ {
/*    */   private final SignatureValidator validator;
/*    */   private final BooleanSupplier expired;
/*    */   @Nullable
/*    */   private PlayerChatMessage lastMessage;
/*    */   private boolean isChainValid = true;
/*    */   
/*    */   public KeyBased(SignatureValidator $$0, BooleanSupplier $$1) {
/* 36 */     this.validator = $$0;
/* 37 */     this.expired = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean validateChain(PlayerChatMessage $$0) {
/* 42 */     if ($$0.equals(this.lastMessage)) {
/* 43 */       return true;
/*    */     }
/*    */     
/* 46 */     if (this.lastMessage != null && !$$0.link().isDescendantOf(this.lastMessage.link())) {
/* 47 */       LOGGER.error("Received out-of-order chat message from {}: expected index > {} for session {}, but was {} for session {}", new Object[] { $$0.sender(), Integer.valueOf(this.lastMessage.link().index()), this.lastMessage.link().sessionId(), Integer.valueOf($$0.link().index()), $$0.link().sessionId() });
/* 48 */       return false;
/*    */     } 
/*    */     
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   private boolean validate(PlayerChatMessage $$0) {
/* 55 */     if (this.expired.getAsBoolean()) {
/* 56 */       LOGGER.error("Received message from player with expired profile public key: {}", $$0);
/* 57 */       return false;
/*    */     } 
/* 59 */     if (!$$0.verify(this.validator)) {
/* 60 */       LOGGER.error("Received message with invalid signature from {}", $$0.sender());
/* 61 */       return false;
/*    */     } 
/* 63 */     return validateChain($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public PlayerChatMessage updateAndValidate(PlayerChatMessage $$0) {
/* 69 */     this.isChainValid = (this.isChainValid && validate($$0));
/* 70 */     if (!this.isChainValid) {
/* 71 */       return null;
/*    */     }
/* 73 */     this.lastMessage = $$0;
/* 74 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SignedMessageValidator$KeyBased.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */