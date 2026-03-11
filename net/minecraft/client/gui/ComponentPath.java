/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ 
/*    */ public interface ComponentPath
/*    */ {
/*    */   static ComponentPath leaf(GuiEventListener $$0) {
/* 10 */     return new Leaf($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   static ComponentPath path(ContainerEventHandler $$0, @Nullable ComponentPath $$1) {
/* 15 */     if ($$1 == null) {
/* 16 */       return null;
/*    */     }
/* 18 */     return new Path($$0, $$1);
/*    */   }
/*    */   
/*    */   static ComponentPath path(GuiEventListener $$0, ContainerEventHandler... $$1) {
/* 22 */     ComponentPath $$2 = leaf($$0);
/* 23 */     for (ContainerEventHandler $$3 : $$1) {
/* 24 */       $$2 = path($$3, $$2);
/*    */     }
/* 26 */     return $$2;
/*    */   }
/*    */   GuiEventListener component();
/*    */   void applyFocus(boolean paramBoolean);
/*    */   public static final class Path extends Record implements ComponentPath { private final ContainerEventHandler component;
/*    */     private final ComponentPath childPath;
/*    */     
/* 33 */     public Path(ContainerEventHandler $$0, ComponentPath $$1) { this.component = $$0; this.childPath = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/ComponentPath$Path;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #33	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 33 */       //   0	7	0	this	Lnet/minecraft/client/gui/ComponentPath$Path; } public ContainerEventHandler component() { return this.component; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/ComponentPath$Path;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #33	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/gui/ComponentPath$Path; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/ComponentPath$Path;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #33	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/gui/ComponentPath$Path;
/* 33 */       //   0	8	1	$$0	Ljava/lang/Object; } public ComponentPath childPath() { return this.childPath; }
/*    */     
/*    */     public void applyFocus(boolean $$0) {
/* 36 */       if (!$$0) {
/* 37 */         this.component.setFocused(null);
/*    */       } else {
/* 39 */         this.component.setFocused(this.childPath.component());
/*    */       } 
/* 41 */       this.childPath.applyFocus($$0);
/*    */     } }
/*    */   public static final class Leaf extends Record implements ComponentPath { private final GuiEventListener component;
/*    */     
/* 45 */     public Leaf(GuiEventListener $$0) { this.component = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/ComponentPath$Leaf;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #45	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/gui/ComponentPath$Leaf; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/ComponentPath$Leaf;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #45	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/gui/ComponentPath$Leaf; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/ComponentPath$Leaf;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #45	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/gui/ComponentPath$Leaf;
/* 45 */       //   0	8	1	$$0	Ljava/lang/Object; } public GuiEventListener component() { return this.component; }
/*    */     
/*    */     public void applyFocus(boolean $$0) {
/* 48 */       this.component.setFocused($$0);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\ComponentPath.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */