/*    */ package net.minecraft.world.scores;
/*    */ public final class PlayerScoreEntry extends Record {
/*    */   private final String owner;
/*    */   private final int value;
/*    */   @Nullable
/*    */   private final Component display;
/*    */   @Nullable
/*    */   private final NumberFormat numberFormatOverride;
/*    */   
/* 10 */   public PlayerScoreEntry(String $$0, int $$1, @Nullable Component $$2, @Nullable NumberFormat $$3) { this.owner = $$0; this.value = $$1; this.display = $$2; this.numberFormatOverride = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/scores/PlayerScoreEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/world/scores/PlayerScoreEntry; } public String owner() { return this.owner; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/scores/PlayerScoreEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/scores/PlayerScoreEntry; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/scores/PlayerScoreEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/scores/PlayerScoreEntry;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } public int value() { return this.value; } @Nullable public Component display() { return this.display; } @Nullable public NumberFormat numberFormatOverride() { return this.numberFormatOverride; }
/*    */    public boolean isHidden() {
/* 12 */     return this.owner.startsWith("#");
/*    */   }
/*    */   
/*    */   public Component ownerName() {
/* 16 */     if (this.display != null) {
/* 17 */       return this.display;
/*    */     }
/* 19 */     return (Component)Component.literal(owner());
/*    */   }
/*    */   
/*    */   public MutableComponent formatValue(NumberFormat $$0) {
/* 23 */     return ((NumberFormat)Objects.<NumberFormat>requireNonNullElse(this.numberFormatOverride, $$0)).format(this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\PlayerScoreEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */