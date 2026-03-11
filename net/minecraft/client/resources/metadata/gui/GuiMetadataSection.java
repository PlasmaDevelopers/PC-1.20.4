/*    */ package net.minecraft.client.resources.metadata.gui;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ 
/*    */ public final class GuiMetadataSection extends Record {
/*    */   private final GuiSpriteScaling scaling;
/*    */   
/*  8 */   public GuiMetadataSection(GuiSpriteScaling $$0) { this.scaling = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection; } public GuiSpriteScaling scaling() { return this.scaling; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final GuiMetadataSection DEFAULT = new GuiMetadataSection(GuiSpriteScaling.DEFAULT); public static final Codec<GuiMetadataSection> CODEC;
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(GuiSpriteScaling.CODEC, "scaling", GuiSpriteScaling.DEFAULT).forGetter(GuiMetadataSection::scaling)).apply((Applicative)$$0, GuiMetadataSection::new));
/*    */   }
/*    */ 
/*    */   
/* 15 */   public static final MetadataSectionType<GuiMetadataSection> TYPE = MetadataSectionType.fromCodec("gui", CODEC);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\gui\GuiMetadataSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */