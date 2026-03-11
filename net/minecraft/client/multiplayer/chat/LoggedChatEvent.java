/*    */ package net.minecraft.client.multiplayer.chat;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public interface LoggedChatEvent
/*    */ {
/*  9 */   public static final Codec<LoggedChatEvent> CODEC = StringRepresentable.fromEnum(Type::values).dispatch(LoggedChatEvent::type, Type::codec);
/*    */   
/*    */   Type type();
/*    */   
/*    */   public enum Type implements StringRepresentable {
/* 14 */     PLAYER("player", () -> LoggedChatMessage.Player.CODEC),
/* 15 */     SYSTEM("system", () -> LoggedChatMessage.System.CODEC);
/*    */     
/*    */     private final String serializedName;
/*    */     
/*    */     private final Supplier<Codec<? extends LoggedChatEvent>> codec;
/*    */     
/*    */     Type(String $$0, Supplier<Codec<? extends LoggedChatEvent>> $$1) {
/* 22 */       this.serializedName = $$0;
/* 23 */       this.codec = $$1;
/*    */     }
/*    */     
/*    */     private Codec<? extends LoggedChatEvent> codec() {
/* 27 */       return this.codec.get();
/*    */     }
/*    */ 
/*    */     
/*    */     public String getSerializedName() {
/* 32 */       return this.serializedName;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\LoggedChatEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */