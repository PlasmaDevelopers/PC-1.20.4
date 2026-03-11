/*    */ package net.minecraft.client.multiplayer.chat;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Type
/*    */   implements StringRepresentable
/*    */ {
/* 14 */   PLAYER("player", () -> LoggedChatMessage.Player.CODEC),
/* 15 */   SYSTEM("system", () -> LoggedChatMessage.System.CODEC);
/*    */   
/*    */   private final String serializedName;
/*    */   
/*    */   private final Supplier<Codec<? extends LoggedChatEvent>> codec;
/*    */   
/*    */   Type(String $$0, Supplier<Codec<? extends LoggedChatEvent>> $$1) {
/* 22 */     this.serializedName = $$0;
/* 23 */     this.codec = $$1;
/*    */   }
/*    */   
/*    */   private Codec<? extends LoggedChatEvent> codec() {
/* 27 */     return this.codec.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 32 */     return this.serializedName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\LoggedChatEvent$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */