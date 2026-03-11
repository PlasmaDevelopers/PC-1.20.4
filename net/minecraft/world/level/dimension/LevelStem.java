/*    */ package net.minecraft.world.level.dimension;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ public final class LevelStem extends Record {
/*    */   private final Holder<DimensionType> type;
/*    */   private final ChunkGenerator generator;
/*    */   public static final Codec<LevelStem> CODEC;
/*    */   
/* 11 */   public LevelStem(Holder<DimensionType> $$0, ChunkGenerator $$1) { this.type = $$0; this.generator = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/dimension/LevelStem;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/world/level/dimension/LevelStem; } public Holder<DimensionType> type() { return this.type; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/dimension/LevelStem;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/dimension/LevelStem; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/dimension/LevelStem;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/dimension/LevelStem;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public ChunkGenerator generator() { return this.generator; }
/*    */ 
/*    */   
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)DimensionType.CODEC.fieldOf("type").forGetter(LevelStem::type), (App)ChunkGenerator.CODEC.fieldOf("generator").forGetter(LevelStem::generator)).apply((Applicative)$$0, $$0.stable(LevelStem::new)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public static final ResourceKey<LevelStem> OVERWORLD = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation("overworld"));
/* 21 */   public static final ResourceKey<LevelStem> NETHER = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation("the_nether"));
/* 22 */   public static final ResourceKey<LevelStem> END = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation("the_end"));
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\LevelStem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */