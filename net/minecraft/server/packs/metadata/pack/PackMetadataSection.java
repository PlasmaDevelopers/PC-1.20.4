/*    */ package net.minecraft.server.packs.metadata.pack;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ 
/*    */ public final class PackMetadataSection extends Record {
/*    */   private final Component description;
/*    */   private final int packFormat;
/*    */   private final Optional<InclusiveRange<Integer>> supportedFormats;
/*    */   public static final Codec<PackMetadataSection> CODEC;
/*    */   
/* 12 */   public PackMetadataSection(Component $$0, int $$1, Optional<InclusiveRange<Integer>> $$2) { this.description = $$0; this.packFormat = $$1; this.supportedFormats = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/metadata/pack/PackMetadataSection;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/server/packs/metadata/pack/PackMetadataSection; } public Component description() { return this.description; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/metadata/pack/PackMetadataSection;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/metadata/pack/PackMetadataSection; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/metadata/pack/PackMetadataSection;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/packs/metadata/pack/PackMetadataSection;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public int packFormat() { return this.packFormat; } public Optional<InclusiveRange<Integer>> supportedFormats() { return this.supportedFormats; } static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ComponentSerialization.CODEC.fieldOf("description").forGetter(PackMetadataSection::description), (App)Codec.INT.fieldOf("pack_format").forGetter(PackMetadataSection::packFormat), (App)InclusiveRange.codec((Codec)Codec.INT).optionalFieldOf("supported_formats").forGetter(PackMetadataSection::supportedFormats)).apply((Applicative)$$0, PackMetadataSection::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 19 */   public static final MetadataSectionType<PackMetadataSection> TYPE = MetadataSectionType.fromCodec("pack", CODEC);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\metadata\pack\PackMetadataSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */