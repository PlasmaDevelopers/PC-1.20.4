/*    */ package net.minecraft.client.gui.font;
/*    */ public final class GlyphRenderTypes extends Record {
/*    */   private final RenderType normal;
/*    */   private final RenderType seeThrough;
/*    */   private final RenderType polygonOffset;
/*    */   
/*  7 */   public GlyphRenderTypes(RenderType $$0, RenderType $$1, RenderType $$2) { this.normal = $$0; this.seeThrough = $$1; this.polygonOffset = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/GlyphRenderTypes;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/client/gui/font/GlyphRenderTypes; } public RenderType normal() { return this.normal; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/GlyphRenderTypes;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/font/GlyphRenderTypes; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/GlyphRenderTypes;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/font/GlyphRenderTypes;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public RenderType seeThrough() { return this.seeThrough; } public RenderType polygonOffset() { return this.polygonOffset; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static GlyphRenderTypes createForIntensityTexture(ResourceLocation $$0) {
/* 13 */     return new GlyphRenderTypes(
/* 14 */         RenderType.textIntensity($$0), 
/* 15 */         RenderType.textIntensitySeeThrough($$0), 
/* 16 */         RenderType.textIntensityPolygonOffset($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public static GlyphRenderTypes createForColorTexture(ResourceLocation $$0) {
/* 21 */     return new GlyphRenderTypes(
/* 22 */         RenderType.text($$0), 
/* 23 */         RenderType.textSeeThrough($$0), 
/* 24 */         RenderType.textPolygonOffset($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderType select(Font.DisplayMode $$0) {
/* 29 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case NORMAL: case SEE_THROUGH: case POLYGON_OFFSET: break; }  return 
/*    */ 
/*    */       
/* 32 */       this.polygonOffset;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\GlyphRenderTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */