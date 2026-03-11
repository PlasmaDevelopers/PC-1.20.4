/*    */ package net.minecraft.client.gui.font.providers;
/*    */ 
/*    */ 
/*    */ public final class ProviderReferenceDefinition extends Record implements GlyphProviderDefinition {
/*    */   private final ResourceLocation id;
/*    */   public static final MapCodec<ProviderReferenceDefinition> CODEC;
/*    */   
/*  8 */   public ProviderReferenceDefinition(ResourceLocation $$0) { this.id = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition; } public ResourceLocation id() { return this.id; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("id").forGetter(ProviderReferenceDefinition::id)).apply((Applicative)$$0, ProviderReferenceDefinition::new)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GlyphProviderType type() {
/* 15 */     return GlyphProviderType.REFERENCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
/* 20 */     return Either.right(new GlyphProviderDefinition.Reference(this.id));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\ProviderReferenceDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */