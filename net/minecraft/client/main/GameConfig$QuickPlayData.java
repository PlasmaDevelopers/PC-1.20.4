/*    */ package net.minecraft.client.main;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
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
/*    */ public final class QuickPlayData
/*    */   extends Record
/*    */ {
/*    */   @Nullable
/*    */   private final String path;
/*    */   @Nullable
/*    */   private final String singleplayer;
/*    */   @Nullable
/*    */   private final String multiplayer;
/*    */   @Nullable
/*    */   private final String realms;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/main/GameConfig$QuickPlayData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #78	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/main/GameConfig$QuickPlayData;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/main/GameConfig$QuickPlayData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #78	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/main/GameConfig$QuickPlayData;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/main/GameConfig$QuickPlayData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #78	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/main/GameConfig$QuickPlayData;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public QuickPlayData(@Nullable String $$0, @Nullable String $$1, @Nullable String $$2, @Nullable String $$3) {
/* 78 */     this.path = $$0; this.singleplayer = $$1; this.multiplayer = $$2; this.realms = $$3; } @Nullable public String path() { return this.path; } @Nullable public String singleplayer() { return this.singleplayer; } @Nullable public String multiplayer() { return this.multiplayer; } @Nullable public String realms() { return this.realms; }
/*    */    public boolean isEnabled() {
/* 80 */     return (!Util.isBlank(this.singleplayer) || !Util.isBlank(this.multiplayer) || !Util.isBlank(this.realms));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\main\GameConfig$QuickPlayData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */