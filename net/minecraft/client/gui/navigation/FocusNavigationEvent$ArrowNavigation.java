/*    */ package net.minecraft.client.gui.navigation;
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
/*    */ public final class ArrowNavigation
/*    */   extends Record
/*    */   implements FocusNavigationEvent
/*    */ {
/*    */   private final ScreenDirection direction;
/*    */   
/*    */   public ArrowNavigation(ScreenDirection $$0) {
/* 20 */     this.direction = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 20 */     //   0	7	0	this	Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation; } public ScreenDirection direction() { return this.direction; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } public ScreenDirection getVerticalDirectionForInitialFocus() {
/* 23 */     return (this.direction.getAxis() == ScreenAxis.VERTICAL) ? this.direction : ScreenDirection.DOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\navigation\FocusNavigationEvent$ArrowNavigation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */