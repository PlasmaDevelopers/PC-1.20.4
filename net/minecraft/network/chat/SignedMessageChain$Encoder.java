/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface Encoder {
/*    */   public static final Encoder UNSIGNED = $$0 -> null;
/*    */   
/*    */   @Nullable
/*    */   MessageSignature pack(SignedMessageBody paramSignedMessageBody);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SignedMessageChain$Encoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */