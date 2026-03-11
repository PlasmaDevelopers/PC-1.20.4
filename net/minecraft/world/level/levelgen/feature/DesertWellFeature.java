/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.List;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.BrushableBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ public class DesertWellFeature
/*     */   extends Feature<NoneFeatureConfiguration> {
/*  20 */   private static final BlockStatePredicate IS_SAND = BlockStatePredicate.forBlock(Blocks.SAND);
/*     */   
/*  22 */   private final BlockState sand = Blocks.SAND.defaultBlockState();
/*  23 */   private final BlockState sandSlab = Blocks.SANDSTONE_SLAB.defaultBlockState();
/*  24 */   private final BlockState sandstone = Blocks.SANDSTONE.defaultBlockState();
/*  25 */   private final BlockState water = Blocks.WATER.defaultBlockState();
/*     */   
/*     */   public DesertWellFeature(Codec<NoneFeatureConfiguration> $$0) {
/*  28 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/*  33 */     WorldGenLevel $$1 = $$0.level();
/*  34 */     BlockPos $$2 = $$0.origin();
/*  35 */     $$2 = $$2.above();
/*     */     
/*  37 */     while ($$1.isEmptyBlock($$2) && $$2.getY() > $$1.getMinBuildHeight() + 2) {
/*  38 */       $$2 = $$2.below();
/*     */     }
/*     */     
/*  41 */     if (!IS_SAND.test($$1.getBlockState($$2))) {
/*  42 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  47 */     for (int $$3 = -2; $$3 <= 2; $$3++) {
/*  48 */       for (int $$4 = -2; $$4 <= 2; $$4++) {
/*  49 */         if ($$1.isEmptyBlock($$2.offset($$3, -1, $$4)) && $$1.isEmptyBlock($$2.offset($$3, -2, $$4))) {
/*  50 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  56 */     for (int $$5 = -2; $$5 <= 0; $$5++) {
/*  57 */       for (int $$6 = -2; $$6 <= 2; $$6++) {
/*  58 */         for (int $$7 = -2; $$7 <= 2; $$7++) {
/*  59 */           $$1.setBlock($$2.offset($$6, $$5, $$7), this.sandstone, 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  65 */     $$1.setBlock($$2, this.water, 2);
/*  66 */     for (Direction $$8 : Direction.Plane.HORIZONTAL) {
/*  67 */       $$1.setBlock($$2.relative($$8), this.water, 2);
/*     */     }
/*     */ 
/*     */     
/*  71 */     BlockPos $$9 = $$2.below();
/*  72 */     $$1.setBlock($$9, this.sand, 2);
/*  73 */     for (Direction $$10 : Direction.Plane.HORIZONTAL) {
/*  74 */       $$1.setBlock($$9.relative($$10), this.sand, 2);
/*     */     }
/*     */ 
/*     */     
/*  78 */     for (int $$11 = -2; $$11 <= 2; $$11++) {
/*  79 */       for (int $$12 = -2; $$12 <= 2; $$12++) {
/*  80 */         if ($$11 == -2 || $$11 == 2 || $$12 == -2 || $$12 == 2) {
/*  81 */           $$1.setBlock($$2.offset($$11, 1, $$12), this.sandstone, 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     $$1.setBlock($$2.offset(2, 1, 0), this.sandSlab, 2);
/*  87 */     $$1.setBlock($$2.offset(-2, 1, 0), this.sandSlab, 2);
/*  88 */     $$1.setBlock($$2.offset(0, 1, 2), this.sandSlab, 2);
/*  89 */     $$1.setBlock($$2.offset(0, 1, -2), this.sandSlab, 2);
/*     */ 
/*     */     
/*  92 */     for (int $$13 = -1; $$13 <= 1; $$13++) {
/*  93 */       for (int $$14 = -1; $$14 <= 1; $$14++) {
/*  94 */         if ($$13 == 0 && $$14 == 0) {
/*  95 */           $$1.setBlock($$2.offset($$13, 4, $$14), this.sandstone, 2);
/*     */         } else {
/*  97 */           $$1.setBlock($$2.offset($$13, 4, $$14), this.sandSlab, 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 103 */     for (int $$15 = 1; $$15 <= 3; $$15++) {
/* 104 */       $$1.setBlock($$2.offset(-1, $$15, -1), this.sandstone, 2);
/* 105 */       $$1.setBlock($$2.offset(-1, $$15, 1), this.sandstone, 2);
/* 106 */       $$1.setBlock($$2.offset(1, $$15, -1), this.sandstone, 2);
/* 107 */       $$1.setBlock($$2.offset(1, $$15, 1), this.sandstone, 2);
/*     */     } 
/*     */     
/* 110 */     BlockPos $$16 = $$2;
/* 111 */     List<BlockPos> $$17 = List.of($$16, $$16.east(), $$16.south(), $$16.west(), $$16.north());
/*     */     
/* 113 */     RandomSource $$18 = $$0.random();
/* 114 */     placeSusSand($$1, ((BlockPos)Util.getRandom($$17, $$18)).below(1));
/* 115 */     placeSusSand($$1, ((BlockPos)Util.getRandom($$17, $$18)).below(2));
/*     */     
/* 117 */     return true;
/*     */   }
/*     */   
/*     */   private static void placeSusSand(WorldGenLevel $$0, BlockPos $$1) {
/* 121 */     $$0.setBlock($$1, Blocks.SUSPICIOUS_SAND.defaultBlockState(), 3);
/* 122 */     $$0.getBlockEntity($$1, BlockEntityType.BRUSHABLE_BLOCK).ifPresent($$1 -> $$1.setLootTable(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY, $$0.asLong()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\DesertWellFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */