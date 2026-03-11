/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Path
/*    */   extends Record
/*    */   implements ComponentPath
/*    */ {
/*    */   private final ContainerEventHandler component;
/*    */   private final ComponentPath childPath;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/ComponentPath$Path;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #33	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/ComponentPath$Path;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/ComponentPath$Path;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #33	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/ComponentPath$Path;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/ComponentPath$Path;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #33	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/ComponentPath$Path;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Path(ContainerEventHandler $$0, ComponentPath $$1) {
/* 33 */     this.component = $$0; this.childPath = $$1; } public ContainerEventHandler component() { return this.component; } public ComponentPath childPath() { return this.childPath; }
/*    */   
/*    */   public void applyFocus(boolean $$0) {
/* 36 */     if (!$$0) {
/* 37 */       this.component.setFocused(null);
/*    */     } else {
/* 39 */       this.component.setFocused(this.childPath.component());
/*    */     } 
/* 41 */     this.childPath.applyFocus($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\ComponentPath$Path.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */