/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ 
/*    */ public final class Configuration extends Record implements FeatureConfiguration {
/*    */   private final BlockStateProvider fluid;
/*    */   private final BlockStateProvider barrier;
/*    */   public static final Codec<Configuration> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;
/*    */   }
/*    */   
/* 19 */   public Configuration(BlockStateProvider $$0, BlockStateProvider $$1) { this.fluid = $$0; this.barrier = $$1; } public BlockStateProvider fluid() { return this.fluid; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;
/* 19 */     //   0	8	1	$$0	Ljava/lang/Object; } public BlockStateProvider barrier() { return this.barrier; } static {
/* 20 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Configuration::fluid), (App)BlockStateProvider.CODEC.fieldOf("barrier").forGetter(Configuration::barrier)).apply((Applicative)$$0, Configuration::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\LakeFeature$Configuration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */