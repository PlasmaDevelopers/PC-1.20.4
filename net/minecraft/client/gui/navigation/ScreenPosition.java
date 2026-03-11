/*    */ package net.minecraft.client.gui.navigation;public final class ScreenPosition extends Record { private final int x; private final int y;
/*    */   
/*  3 */   public ScreenPosition(int $$0, int $$1) { this.x = $$0; this.y = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/navigation/ScreenPosition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #3	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  3 */     //   0	7	0	this	Lnet/minecraft/client/gui/navigation/ScreenPosition; } public int x() { return this.x; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/navigation/ScreenPosition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #3	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/navigation/ScreenPosition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/navigation/ScreenPosition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #3	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/navigation/ScreenPosition;
/*  3 */     //   0	8	1	$$0	Ljava/lang/Object; } public int y() { return this.y; }
/*    */    public static ScreenPosition of(ScreenAxis $$0, int $$1, int $$2) {
/*  5 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case DOWN: case UP: break; }  return 
/*    */       
/*  7 */       new ScreenPosition($$2, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScreenPosition step(ScreenDirection $$0) {
/* 12 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case DOWN: case UP: case LEFT: case RIGHT: break; }  return 
/*    */ 
/*    */ 
/*    */       
/* 16 */       new ScreenPosition(this.x + 1, this.y);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCoordinate(ScreenAxis $$0) {
/* 21 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case DOWN: case UP: break; }  return 
/*    */       
/* 23 */       this.y;
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\navigation\ScreenPosition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */