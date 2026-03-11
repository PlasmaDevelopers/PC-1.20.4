/*    */ package net.minecraft.world.item;public final class Default extends Record implements TooltipFlag { private final boolean advanced;
/*    */   private final boolean creative;
/*    */   
/*  4 */   public Default(boolean $$0, boolean $$1) { this.advanced = $$0; this.creative = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/TooltipFlag$Default;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #4	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  4 */     //   0	7	0	this	Lnet/minecraft/world/item/TooltipFlag$Default; } public boolean advanced() { return this.advanced; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/TooltipFlag$Default;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #4	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/item/TooltipFlag$Default; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/TooltipFlag$Default;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #4	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/item/TooltipFlag$Default;
/*  4 */     //   0	8	1	$$0	Ljava/lang/Object; } public boolean creative() { return this.creative; }
/*    */   
/*    */   public boolean isAdvanced() {
/*  7 */     return this.advanced;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCreative() {
/* 12 */     return this.creative;
/*    */   }
/*    */   
/*    */   public Default asCreative() {
/* 16 */     return new Default(this.advanced, true);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\TooltipFlag$Default.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */