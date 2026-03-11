/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.PointedDripstoneBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.DripstoneThickness;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DripstoneUtils
/*     */ {
/*     */   protected static double getDripstoneHeight(double $$0, double $$1, double $$2, double $$3) {
/*  31 */     if ($$0 < $$3) {
/*  32 */       $$0 = $$3;
/*     */     }
/*     */ 
/*     */     
/*  36 */     double $$4 = 0.384D;
/*  37 */     double $$5 = $$0 / $$1 * 0.384D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     double $$6 = 0.75D * Math.pow($$5, 1.3333333333333333D);
/*  43 */     double $$7 = Math.pow($$5, 0.6666666666666666D);
/*  44 */     double $$8 = 0.3333333333333333D * Math.log($$5);
/*  45 */     double $$9 = $$2 * ($$6 - $$7 - $$8);
/*     */     
/*  47 */     $$9 = Math.max($$9, 0.0D);
/*  48 */     return $$9 / 0.384D * $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel $$0, BlockPos $$1, int $$2) {
/*  61 */     if (isEmptyOrWaterOrLava((LevelAccessor)$$0, $$1)) {
/*  62 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  66 */     float $$3 = 6.0F;
/*  67 */     float $$4 = 6.0F / $$2; float $$5;
/*  68 */     for ($$5 = 0.0F; $$5 < 6.2831855F; $$5 += $$4) {
/*  69 */       int $$6 = (int)(Mth.cos($$5) * $$2);
/*  70 */       int $$7 = (int)(Mth.sin($$5) * $$2);
/*  71 */       if (isEmptyOrWaterOrLava((LevelAccessor)$$0, $$1.offset($$6, 0, $$7))) {
/*  72 */         return false;
/*     */       }
/*     */     } 
/*  75 */     return true;
/*     */   }
/*     */   
/*     */   protected static boolean isEmptyOrWater(LevelAccessor $$0, BlockPos $$1) {
/*  79 */     return $$0.isStateAtPosition($$1, DripstoneUtils::isEmptyOrWater);
/*     */   }
/*     */   
/*     */   protected static boolean isEmptyOrWaterOrLava(LevelAccessor $$0, BlockPos $$1) {
/*  83 */     return $$0.isStateAtPosition($$1, DripstoneUtils::isEmptyOrWaterOrLava);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void buildBaseToTipColumn(Direction $$0, int $$1, boolean $$2, Consumer<BlockState> $$3) {
/*  92 */     if ($$1 >= 3) {
/*  93 */       $$3.accept(createPointedDripstone($$0, DripstoneThickness.BASE));
/*  94 */       for (int $$4 = 0; $$4 < $$1 - 3; $$4++) {
/*  95 */         $$3.accept(createPointedDripstone($$0, DripstoneThickness.MIDDLE));
/*     */       }
/*     */     } 
/*  98 */     if ($$1 >= 2) {
/*  99 */       $$3.accept(createPointedDripstone($$0, DripstoneThickness.FRUSTUM));
/*     */     }
/* 101 */     if ($$1 >= 1) {
/* 102 */       $$3.accept(createPointedDripstone($$0, $$2 ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void growPointedDripstone(LevelAccessor $$0, BlockPos $$1, Direction $$2, int $$3, boolean $$4) {
/* 107 */     if (!isDripstoneBase($$0.getBlockState($$1.relative($$2.getOpposite())))) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     BlockPos.MutableBlockPos $$5 = $$1.mutable();
/* 112 */     buildBaseToTipColumn($$2, $$3, $$4, $$3 -> {
/*     */           if ($$3.is(Blocks.POINTED_DRIPSTONE)) {
/*     */             $$3 = (BlockState)$$3.setValue((Property)PointedDripstoneBlock.WATERLOGGED, Boolean.valueOf($$0.isWaterAt((BlockPos)$$1)));
/*     */           }
/*     */           $$0.setBlock((BlockPos)$$1, $$3, 2);
/*     */           $$1.move($$2);
/*     */         });
/*     */   }
/*     */   
/*     */   protected static boolean placeDripstoneBlockIfPossible(LevelAccessor $$0, BlockPos $$1) {
/* 122 */     BlockState $$2 = $$0.getBlockState($$1);
/* 123 */     if ($$2.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
/* 124 */       $$0.setBlock($$1, Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
/* 125 */       return true;
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */   
/*     */   private static BlockState createPointedDripstone(Direction $$0, DripstoneThickness $$1) {
/* 131 */     return (BlockState)((BlockState)Blocks.POINTED_DRIPSTONE.defaultBlockState()
/* 132 */       .setValue((Property)PointedDripstoneBlock.TIP_DIRECTION, (Comparable)$$0))
/* 133 */       .setValue((Property)PointedDripstoneBlock.THICKNESS, (Comparable)$$1);
/*     */   }
/*     */   
/*     */   public static boolean isDripstoneBaseOrLava(BlockState $$0) {
/* 137 */     return (isDripstoneBase($$0) || $$0.is(Blocks.LAVA));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDripstoneBase(BlockState $$0) {
/* 144 */     return ($$0.is(Blocks.DRIPSTONE_BLOCK) || $$0.is(BlockTags.DRIPSTONE_REPLACEABLE));
/*     */   }
/*     */   
/*     */   public static boolean isEmptyOrWater(BlockState $$0) {
/* 148 */     return ($$0.isAir() || $$0.is(Blocks.WATER));
/*     */   }
/*     */   
/*     */   public static boolean isNeitherEmptyNorWater(BlockState $$0) {
/* 152 */     return (!$$0.isAir() && !$$0.is(Blocks.WATER));
/*     */   }
/*     */   
/*     */   public static boolean isEmptyOrWaterOrLava(BlockState $$0) {
/* 156 */     return ($$0.isAir() || $$0.is(Blocks.WATER) || $$0.is(Blocks.LAVA));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\DripstoneUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */