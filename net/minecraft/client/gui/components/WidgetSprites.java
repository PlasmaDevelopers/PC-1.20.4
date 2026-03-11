/*    */ package net.minecraft.client.gui.components;public final class WidgetSprites extends Record { private final ResourceLocation enabled; private final ResourceLocation disabled;
/*    */   private final ResourceLocation enabledFocused;
/*    */   private final ResourceLocation disabledFocused;
/*    */   
/*  5 */   public WidgetSprites(ResourceLocation $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) { this.enabled = $$0; this.disabled = $$1; this.enabledFocused = $$2; this.disabledFocused = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/components/WidgetSprites;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  5 */     //   0	7	0	this	Lnet/minecraft/client/gui/components/WidgetSprites; } public ResourceLocation enabled() { return this.enabled; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/components/WidgetSprites;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/components/WidgetSprites; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/components/WidgetSprites;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/components/WidgetSprites;
/*  5 */     //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation disabled() { return this.disabled; } public ResourceLocation enabledFocused() { return this.enabledFocused; } public ResourceLocation disabledFocused() { return this.disabledFocused; }
/*    */    public WidgetSprites(ResourceLocation $$0, ResourceLocation $$1) {
/*  7 */     this($$0, $$0, $$1, $$1);
/*    */   }
/*    */   
/*    */   public WidgetSprites(ResourceLocation $$0, ResourceLocation $$1, ResourceLocation $$2) {
/* 11 */     this($$0, $$1, $$2, $$1);
/*    */   }
/*    */   
/*    */   public ResourceLocation get(boolean $$0, boolean $$1) {
/* 15 */     if ($$0) {
/* 16 */       return $$1 ? this.enabledFocused : this.enabled;
/*    */     }
/* 18 */     return $$1 ? this.disabledFocused : this.disabled;
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\WidgetSprites.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */