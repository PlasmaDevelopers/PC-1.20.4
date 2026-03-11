/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ 
/*    */ public final class FeatureFlagsMetadataSection extends Record {
/*    */   private final FeatureFlagSet flags;
/*    */   private static final Codec<FeatureFlagsMetadataSection> CODEC;
/*    */   
/*  9 */   public FeatureFlagsMetadataSection(FeatureFlagSet $$0) { this.flags = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/FeatureFlagsMetadataSection;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/server/packs/FeatureFlagsMetadataSection; } public FeatureFlagSet flags() { return this.flags; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/FeatureFlagsMetadataSection;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/FeatureFlagsMetadataSection; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/FeatureFlagsMetadataSection;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/packs/FeatureFlagsMetadataSection;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)FeatureFlags.CODEC.fieldOf("enabled").forGetter(FeatureFlagsMetadataSection::flags)).apply((Applicative)$$0, FeatureFlagsMetadataSection::new)); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public static final MetadataSectionType<FeatureFlagsMetadataSection> TYPE = MetadataSectionType.fromCodec("features", CODEC);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\FeatureFlagsMetadataSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */