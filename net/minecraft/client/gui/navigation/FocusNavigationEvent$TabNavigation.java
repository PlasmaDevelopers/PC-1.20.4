/*   */ package net.minecraft.client.gui.navigation;
/*   */ 
/*   */ public final class TabNavigation extends Record implements FocusNavigationEvent {
/*   */   private final boolean forward;
/*   */   
/* 6 */   public TabNavigation(boolean $$0) { this.forward = $$0; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$TabNavigation;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 6 */     //   0	7	0	this	Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$TabNavigation; } public boolean forward() { return this.forward; }
/*   */   public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$TabNavigation;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$TabNavigation; }
/*   */   public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$TabNavigation;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$TabNavigation;
/*   */     //   0	8	1	$$0	Ljava/lang/Object; } public ScreenDirection getVerticalDirectionForInitialFocus() {
/* 9 */     return this.forward ? ScreenDirection.DOWN : ScreenDirection.UP;
/*   */   }
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\navigation\FocusNavigationEvent$TabNavigation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */