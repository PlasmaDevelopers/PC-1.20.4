/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.GrowingPlantHeadBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*     */ 
/*     */ public class WeepingVinesFeature extends Feature<NoneFeatureConfiguration> {
/*  17 */   private static final Direction[] DIRECTIONS = Direction.values();
/*     */   
/*     */   public WeepingVinesFeature(Codec<NoneFeatureConfiguration> $$0) {
/*  20 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/*  25 */     WorldGenLevel $$1 = $$0.level();
/*  26 */     BlockPos $$2 = $$0.origin();
/*  27 */     RandomSource $$3 = $$0.random();
/*  28 */     if (!$$1.isEmptyBlock($$2)) {
/*  29 */       return false;
/*     */     }
/*     */     
/*  32 */     BlockState $$4 = $$1.getBlockState($$2.above());
/*  33 */     if (!$$4.is(Blocks.NETHERRACK) && !$$4.is(Blocks.NETHER_WART_BLOCK)) {
/*  34 */       return false;
/*     */     }
/*     */     
/*  37 */     placeRoofNetherWart((LevelAccessor)$$1, $$3, $$2);
/*  38 */     placeRoofWeepingVines((LevelAccessor)$$1, $$3, $$2);
/*     */     
/*  40 */     return true;
/*     */   }
/*     */   
/*     */   private void placeRoofNetherWart(LevelAccessor $$0, RandomSource $$1, BlockPos $$2) {
/*  44 */     $$0.setBlock($$2, Blocks.NETHER_WART_BLOCK.defaultBlockState(), 2);
/*     */     
/*  46 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
/*  47 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*     */     
/*  49 */     for (int $$5 = 0; $$5 < 200; $$5++) {
/*  50 */       $$3.setWithOffset((Vec3i)$$2, $$1.nextInt(6) - $$1.nextInt(6), $$1.nextInt(2) - $$1.nextInt(5), $$1.nextInt(6) - $$1.nextInt(6));
/*  51 */       if ($$0.isEmptyBlock((BlockPos)$$3)) {
/*     */ 
/*     */ 
/*     */         
/*  55 */         int $$6 = 0;
/*  56 */         for (Direction $$7 : DIRECTIONS) {
/*  57 */           BlockState $$8 = $$0.getBlockState((BlockPos)$$4.setWithOffset((Vec3i)$$3, $$7));
/*  58 */           if ($$8.is(Blocks.NETHERRACK) || $$8.is(Blocks.NETHER_WART_BLOCK)) {
/*  59 */             $$6++;
/*     */           }
/*     */           
/*  62 */           if ($$6 > 1) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/*  67 */         if ($$6 == 1)
/*  68 */           $$0.setBlock((BlockPos)$$3, Blocks.NETHER_WART_BLOCK.defaultBlockState(), 2); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void placeRoofWeepingVines(LevelAccessor $$0, RandomSource $$1, BlockPos $$2) {
/*  74 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
/*     */     
/*  76 */     for (int $$4 = 0; $$4 < 100; $$4++) {
/*  77 */       $$3.setWithOffset((Vec3i)$$2, $$1.nextInt(8) - $$1.nextInt(8), $$1.nextInt(2) - $$1.nextInt(7), $$1.nextInt(8) - $$1.nextInt(8));
/*  78 */       if ($$0.isEmptyBlock((BlockPos)$$3)) {
/*     */ 
/*     */ 
/*     */         
/*  82 */         BlockState $$5 = $$0.getBlockState($$3.above());
/*  83 */         if ($$5.is(Blocks.NETHERRACK) || $$5.is(Blocks.NETHER_WART_BLOCK)) {
/*     */ 
/*     */ 
/*     */           
/*  87 */           int $$6 = Mth.nextInt($$1, 1, 8);
/*  88 */           if ($$1.nextInt(6) == 0) {
/*  89 */             $$6 *= 2;
/*     */           }
/*  91 */           if ($$1.nextInt(5) == 0) {
/*  92 */             $$6 = 1;
/*     */           }
/*     */           
/*  95 */           int $$7 = 17;
/*  96 */           int $$8 = 25;
/*  97 */           placeWeepingVinesColumn($$0, $$1, $$3, $$6, 17, 25);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public static void placeWeepingVinesColumn(LevelAccessor $$0, RandomSource $$1, BlockPos.MutableBlockPos $$2, int $$3, int $$4, int $$5) {
/* 102 */     for (int $$6 = 0; $$6 <= $$3; $$6++) {
/* 103 */       if ($$0.isEmptyBlock((BlockPos)$$2)) {
/* 104 */         if ($$6 == $$3 || !$$0.isEmptyBlock($$2.below())) {
/* 105 */           $$0.setBlock((BlockPos)$$2, (BlockState)Blocks.WEEPING_VINES.defaultBlockState().setValue((Property)GrowingPlantHeadBlock.AGE, Integer.valueOf(Mth.nextInt($$1, $$4, $$5))), 2);
/*     */           break;
/*     */         } 
/* 108 */         $$0.setBlock((BlockPos)$$2, Blocks.WEEPING_VINES_PLANT.defaultBlockState(), 2);
/*     */       } 
/*     */ 
/*     */       
/* 112 */       $$2.move(Direction.DOWN);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\WeepingVinesFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */