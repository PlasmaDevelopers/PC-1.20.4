/*    */ package net.minecraft.client.multiplayer.chat;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.time.Instant;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.GuiMessageTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.PlayerChatMessage;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum ChatTrustLevel
/*    */   implements StringRepresentable {
/* 15 */   SECURE("secure"),
/* 16 */   MODIFIED("modified"),
/* 17 */   NOT_SECURE("not_secure"); public static final Codec<ChatTrustLevel> CODEC; private final String serializedName;
/*    */   
/*    */   static {
/* 20 */     CODEC = (Codec<ChatTrustLevel>)StringRepresentable.fromEnum(ChatTrustLevel::values);
/*    */   }
/*    */ 
/*    */   
/*    */   ChatTrustLevel(String $$0) {
/* 25 */     this.serializedName = $$0;
/*    */   }
/*    */   
/*    */   public static ChatTrustLevel evaluate(PlayerChatMessage $$0, Component $$1, Instant $$2) {
/* 29 */     if (!$$0.hasSignature() || $$0.hasExpiredClient($$2)) {
/* 30 */       return NOT_SECURE;
/*    */     }
/*    */     
/* 33 */     if (isModified($$0, $$1)) {
/* 34 */       return MODIFIED;
/*    */     }
/*    */     
/* 37 */     return SECURE;
/*    */   }
/*    */   
/*    */   private static boolean isModified(PlayerChatMessage $$0, Component $$1) {
/* 41 */     if (!$$1.getString().contains($$0.signedContent())) {
/* 42 */       return true;
/*    */     }
/*    */     
/* 45 */     Component $$2 = $$0.unsignedContent();
/* 46 */     if ($$2 == null) {
/* 47 */       return false;
/*    */     }
/*    */     
/* 50 */     return containsModifiedStyle($$2);
/*    */   }
/*    */   
/*    */   private static boolean containsModifiedStyle(Component $$0) {
/* 54 */     return ((Boolean)$$0.visit(($$0, $$1) -> isModifiedStyle($$0) ? Optional.of(Boolean.valueOf(true)) : Optional.empty(), Style.EMPTY)
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 59 */       .orElse(Boolean.valueOf(false))).booleanValue();
/*    */   }
/*    */   
/*    */   private static boolean isModifiedStyle(Style $$0) {
/* 63 */     return !$$0.getFont().equals(Style.DEFAULT_FONT);
/*    */   }
/*    */   
/*    */   public boolean isNotSecure() {
/* 67 */     return (this == NOT_SECURE);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public GuiMessageTag createTag(PlayerChatMessage $$0) {
/* 72 */     switch (this) { case MODIFIED: case NOT_SECURE:  }  return 
/*    */ 
/*    */       
/* 75 */       null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 81 */     return this.serializedName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\ChatTrustLevel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */