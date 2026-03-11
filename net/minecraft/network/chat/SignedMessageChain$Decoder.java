/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nullable;
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
/*    */ @FunctionalInterface
/*    */ public interface Decoder
/*    */ {
/*    */   static Decoder unsigned(UUID $$0, BooleanSupplier $$1) {
/* 86 */     return ($$2, $$3) -> {
/*    */         if ($$0.getAsBoolean())
/*    */           throw new SignedMessageChain.DecodeException(Component.translatable("chat.disabled.missingProfileKey"), false); 
/*    */         return PlayerChatMessage.unsigned($$1, $$3.content());
/*    */       };
/*    */   }
/*    */   
/*    */   PlayerChatMessage unpack(@Nullable MessageSignature paramMessageSignature, SignedMessageBody paramSignedMessageBody) throws SignedMessageChain.DecodeException;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SignedMessageChain$Decoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */