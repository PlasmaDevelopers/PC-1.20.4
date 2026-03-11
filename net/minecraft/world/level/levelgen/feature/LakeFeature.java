/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*     */ 
/*     */ @Deprecated
/*     */ public class LakeFeature extends Feature<LakeFeature.Configuration> {
/*     */   public static final class Configuration extends Record implements FeatureConfiguration {
/*     */     private final BlockStateProvider fluid;
/*     */     private final BlockStateProvider barrier;
/*     */     public static final Codec<Configuration> CODEC;
/*     */     
/*  19 */     public Configuration(BlockStateProvider $$0, BlockStateProvider $$1) { this.fluid = $$0; this.barrier = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #19	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  19 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration; } public BlockStateProvider fluid() { return this.fluid; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #19	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #19	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/LakeFeature$Configuration;
/*  19 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockStateProvider barrier() { return this.barrier; } static {
/*  20 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Configuration::fluid), (App)BlockStateProvider.CODEC.fieldOf("barrier").forGetter(Configuration::barrier)).apply((Applicative)$$0, Configuration::new));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  26 */   private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();
/*     */   
/*     */   public LakeFeature(Codec<Configuration> $$0) {
/*  29 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<Configuration> $$0) {
/*  34 */     BlockPos $$1 = $$0.origin();
/*  35 */     WorldGenLevel $$2 = $$0.level();
/*  36 */     RandomSource $$3 = $$0.random();
/*  37 */     Configuration $$4 = $$0.config();
/*     */     
/*  39 */     if ($$1.getY() <= $$2.getMinBuildHeight() + 4) {
/*  40 */       return false;
/*     */     }
/*     */     
/*  43 */     $$1 = $$1.below(4);
/*     */     
/*  45 */     boolean[] $$5 = new boolean[2048];
/*     */     
/*  47 */     int $$6 = $$3.nextInt(4) + 4;
/*  48 */     for (int $$7 = 0; $$7 < $$6; $$7++) {
/*  49 */       double $$8 = $$3.nextDouble() * 6.0D + 3.0D;
/*  50 */       double $$9 = $$3.nextDouble() * 4.0D + 2.0D;
/*  51 */       double $$10 = $$3.nextDouble() * 6.0D + 3.0D;
/*     */       
/*  53 */       double $$11 = $$3.nextDouble() * (16.0D - $$8 - 2.0D) + 1.0D + $$8 / 2.0D;
/*  54 */       double $$12 = $$3.nextDouble() * (8.0D - $$9 - 4.0D) + 2.0D + $$9 / 2.0D;
/*  55 */       double $$13 = $$3.nextDouble() * (16.0D - $$10 - 2.0D) + 1.0D + $$10 / 2.0D;
/*     */       
/*  57 */       for (int $$14 = 1; $$14 < 15; $$14++) {
/*  58 */         for (int $$15 = 1; $$15 < 15; $$15++) {
/*  59 */           for (int $$16 = 1; $$16 < 7; $$16++) {
/*  60 */             double $$17 = ($$14 - $$11) / $$8 / 2.0D;
/*  61 */             double $$18 = ($$16 - $$12) / $$9 / 2.0D;
/*  62 */             double $$19 = ($$15 - $$13) / $$10 / 2.0D;
/*  63 */             double $$20 = $$17 * $$17 + $$18 * $$18 + $$19 * $$19;
/*  64 */             if ($$20 < 1.0D) {
/*  65 */               $$5[($$14 * 16 + $$15) * 8 + $$16] = true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     BlockState $$21 = $$4.fluid().getState($$3, $$1);
/*     */     
/*  74 */     for (int $$22 = 0; $$22 < 16; $$22++) {
/*  75 */       for (int $$23 = 0; $$23 < 16; $$23++) {
/*  76 */         for (int $$24 = 0; $$24 < 8; $$24++) {
/*  77 */           boolean $$25 = (!$$5[($$22 * 16 + $$23) * 8 + $$24] && (($$22 < 15 && $$5[(($$22 + 1) * 16 + $$23) * 8 + $$24]) || ($$22 > 0 && $$5[(($$22 - 1) * 16 + $$23) * 8 + $$24]) || ($$23 < 15 && $$5[($$22 * 16 + $$23 + 1) * 8 + $$24]) || ($$23 > 0 && $$5[($$22 * 16 + $$23 - 1) * 8 + $$24]) || ($$24 < 7 && $$5[($$22 * 16 + $$23) * 8 + $$24 + 1]) || ($$24 > 0 && $$5[($$22 * 16 + $$23) * 8 + $$24 - 1])));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  86 */           if ($$25) {
/*  87 */             BlockState $$26 = $$2.getBlockState($$1.offset($$22, $$24, $$23));
/*  88 */             if ($$24 >= 4 && $$26.liquid()) {
/*  89 */               return false;
/*     */             }
/*  91 */             if ($$24 < 4 && !$$26.isSolid() && $$2.getBlockState($$1.offset($$22, $$24, $$23)) != $$21) {
/*  92 */               return false;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     for (int $$27 = 0; $$27 < 16; $$27++) {
/* 100 */       for (int $$28 = 0; $$28 < 16; $$28++) {
/* 101 */         for (int $$29 = 0; $$29 < 8; $$29++) {
/* 102 */           if ($$5[($$27 * 16 + $$28) * 8 + $$29]) {
/* 103 */             BlockPos $$30 = $$1.offset($$27, $$29, $$28);
/* 104 */             if (canReplaceBlock($$2.getBlockState($$30))) {
/*     */ 
/*     */               
/* 107 */               boolean $$31 = ($$29 >= 4);
/* 108 */               $$2.setBlock($$30, $$31 ? AIR : $$21, 2);
/* 109 */               if ($$31) {
/* 110 */                 $$2.scheduleTick($$30, AIR.getBlock(), 0);
/* 111 */                 markAboveForPostProcessing($$2, $$30);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 118 */     BlockState $$32 = $$4.barrier().getState($$3, $$1);
/*     */     
/* 120 */     if (!$$32.isAir()) {
/* 121 */       for (int $$33 = 0; $$33 < 16; $$33++) {
/* 122 */         for (int $$34 = 0; $$34 < 16; $$34++) {
/* 123 */           for (int $$35 = 0; $$35 < 8; $$35++) {
/* 124 */             boolean $$36 = (!$$5[($$33 * 16 + $$34) * 8 + $$35] && (($$33 < 15 && $$5[(($$33 + 1) * 16 + $$34) * 8 + $$35]) || ($$33 > 0 && $$5[(($$33 - 1) * 16 + $$34) * 8 + $$35]) || ($$34 < 15 && $$5[($$33 * 16 + $$34 + 1) * 8 + $$35]) || ($$34 > 0 && $$5[($$33 * 16 + $$34 - 1) * 8 + $$35]) || ($$35 < 7 && $$5[($$33 * 16 + $$34) * 8 + $$35 + 1]) || ($$35 > 0 && $$5[($$33 * 16 + $$34) * 8 + $$35 - 1])));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 133 */             if ($$36 && (
/* 134 */               $$35 < 4 || $$3.nextInt(2) != 0)) {
/* 135 */               BlockState $$37 = $$2.getBlockState($$1.offset($$33, $$35, $$34));
/* 136 */               if ($$37.isSolid() && !$$37.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
/* 137 */                 BlockPos $$38 = $$1.offset($$33, $$35, $$34);
/* 138 */                 $$2.setBlock($$38, $$32, 2);
/* 139 */                 markAboveForPostProcessing($$2, $$38);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 148 */     if ($$21.getFluidState().is(FluidTags.WATER)) {
/* 149 */       for (int $$39 = 0; $$39 < 16; $$39++) {
/* 150 */         for (int $$40 = 0; $$40 < 16; $$40++) {
/* 151 */           int $$41 = 4;
/* 152 */           BlockPos $$42 = $$1.offset($$39, 4, $$40);
/* 153 */           if (((Biome)$$2.getBiome($$42).value()).shouldFreeze((LevelReader)$$2, $$42, false) && canReplaceBlock($$2.getBlockState($$42))) {
/* 154 */             $$2.setBlock($$42, Blocks.ICE.defaultBlockState(), 2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   private boolean canReplaceBlock(BlockState $$0) {
/* 164 */     return !$$0.is(BlockTags.FEATURES_CANNOT_REPLACE);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\LakeFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */