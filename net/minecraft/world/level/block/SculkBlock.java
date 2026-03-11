/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class SculkBlock extends DropExperienceBlock implements SculkBehaviour {
/* 15 */   public static final MapCodec<SculkBlock> CODEC = simpleCodec(SculkBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SculkBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */   
/*    */   public SculkBlock(BlockBehaviour.Properties $$0) {
/* 23 */     super((IntProvider)ConstantInt.of(1), $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int attemptUseCharge(SculkSpreader.ChargeCursor $$0, LevelAccessor $$1, BlockPos $$2, RandomSource $$3, SculkSpreader $$4, boolean $$5) {
/* 29 */     int $$6 = $$0.getCharge();
/* 30 */     if ($$6 == 0 || $$3.nextInt($$4.chargeDecayRate()) != 0) {
/* 31 */       return $$6;
/*    */     }
/*    */     
/* 34 */     BlockPos $$7 = $$0.getPos();
/* 35 */     boolean $$8 = $$7.closerThan((Vec3i)$$2, $$4.noGrowthRadius());
/* 36 */     if ($$8 || !canPlaceGrowth($$1, $$7)) {
/* 37 */       if ($$3.nextInt($$4.additionalDecayRate()) != 0) {
/* 38 */         return $$6;
/*    */       }
/* 40 */       return $$6 - ($$8 ? 1 : getDecayPenalty($$4, $$7, $$2, $$6));
/*    */     } 
/* 42 */     int $$9 = $$4.growthSpawnCost();
/* 43 */     if ($$3.nextInt($$9) < $$6) {
/* 44 */       BlockPos $$10 = $$7.above();
/* 45 */       BlockState $$11 = getRandomGrowthState($$1, $$10, $$3, $$4.isWorldGeneration());
/* 46 */       $$1.setBlock($$10, $$11, 3);
/* 47 */       $$1.playSound(null, $$7, $$11.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
/*    */     } 
/* 49 */     return Math.max(0, $$6 - $$9);
/*    */   }
/*    */   
/*    */   private static int getDecayPenalty(SculkSpreader $$0, BlockPos $$1, BlockPos $$2, int $$3) {
/* 53 */     int $$4 = $$0.noGrowthRadius();
/* 54 */     float $$5 = Mth.square((float)Math.sqrt($$1.distSqr((Vec3i)$$2)) - $$4);
/* 55 */     int $$6 = Mth.square(24 - $$4);
/*    */ 
/*    */     
/* 58 */     float $$7 = Math.min(1.0F, $$5 / $$6);
/* 59 */     return Math.max(1, (int)($$3 * $$7 * 0.5F));
/*    */   }
/*    */   
/*    */   private BlockState getRandomGrowthState(LevelAccessor $$0, BlockPos $$1, RandomSource $$2, boolean $$3) {
/*    */     BlockState $$5;
/* 64 */     if ($$2.nextInt(11) == 0) {
/* 65 */       BlockState $$4 = (BlockState)Blocks.SCULK_SHRIEKER.defaultBlockState().setValue((Property)SculkShriekerBlock.CAN_SUMMON, Boolean.valueOf($$3));
/*    */     } else {
/* 67 */       $$5 = Blocks.SCULK_SENSOR.defaultBlockState();
/*    */     } 
/*    */     
/* 70 */     if ($$5.hasProperty((Property)BlockStateProperties.WATERLOGGED) && !$$0.getFluidState($$1).isEmpty()) {
/* 71 */       return (BlockState)$$5.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
/*    */     }
/* 73 */     return $$5;
/*    */   }
/*    */   
/*    */   private static boolean canPlaceGrowth(LevelAccessor $$0, BlockPos $$1) {
/* 77 */     BlockState $$2 = $$0.getBlockState($$1.above());
/* 78 */     if (!$$2.isAir() && (!$$2.is(Blocks.WATER) || !$$2.getFluidState().is((Fluid)Fluids.WATER))) {
/* 79 */       return false;
/*    */     }
/*    */     
/* 82 */     int $$3 = 0;
/* 83 */     for (BlockPos $$4 : BlockPos.betweenClosed($$1.offset(-4, 0, -4), $$1.offset(4, 2, 4))) {
/* 84 */       BlockState $$5 = $$0.getBlockState($$4);
/* 85 */       if ($$5.is(Blocks.SCULK_SENSOR) || $$5.is(Blocks.SCULK_SHRIEKER)) {
/* 86 */         $$3++;
/*    */       }
/* 88 */       if ($$3 > 2) {
/* 89 */         return false;
/*    */       }
/*    */     } 
/* 92 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canChangeBlockStateOnSpread() {
/* 97 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */