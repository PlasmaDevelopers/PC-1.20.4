/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.SculkShriekerBlock;
/*    */ import net.minecraft.world.level.block.SculkSpreader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
/*    */ 
/*    */ public class SculkPatchFeature extends Feature<SculkPatchConfiguration> {
/*    */   public SculkPatchFeature(Codec<SculkPatchConfiguration> $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<SculkPatchConfiguration> $$0) {
/* 25 */     WorldGenLevel $$1 = $$0.level();
/* 26 */     BlockPos $$2 = $$0.origin();
/* 27 */     if (!canSpreadFrom((LevelAccessor)$$1, $$2)) {
/* 28 */       return false;
/*    */     }
/* 30 */     SculkPatchConfiguration $$3 = $$0.config();
/* 31 */     RandomSource $$4 = $$0.random();
/* 32 */     SculkSpreader $$5 = SculkSpreader.createWorldGenSpreader();
/* 33 */     int $$6 = $$3.spreadRounds() + $$3.growthRounds();
/* 34 */     for (int $$7 = 0; $$7 < $$6; $$7++) {
/* 35 */       for (int $$8 = 0; $$8 < $$3.chargeCount(); $$8++) {
/* 36 */         $$5.addCursors($$2, $$3.amountPerCharge());
/*    */       }
/* 38 */       boolean $$9 = ($$7 < $$3.spreadRounds());
/* 39 */       for (int $$10 = 0; $$10 < $$3.spreadAttempts(); $$10++) {
/* 40 */         $$5.updateCursors((LevelAccessor)$$1, $$2, $$4, $$9);
/*    */       }
/* 42 */       $$5.clear();
/*    */     } 
/* 44 */     BlockPos $$11 = $$2.below();
/* 45 */     if ($$4.nextFloat() <= $$3.catalystChance() && $$1.getBlockState($$11).isCollisionShapeFullBlock((BlockGetter)$$1, $$11)) {
/* 46 */       $$1.setBlock($$2, Blocks.SCULK_CATALYST.defaultBlockState(), 3);
/*    */     }
/* 48 */     int $$12 = $$3.extraRareGrowths().sample($$4);
/* 49 */     for (int $$13 = 0; $$13 < $$12; $$13++) {
/* 50 */       BlockPos $$14 = $$2.offset($$4.nextInt(5) - 2, 0, $$4.nextInt(5) - 2);
/* 51 */       if ($$1.getBlockState($$14).isAir() && $$1.getBlockState($$14.below()).isFaceSturdy((BlockGetter)$$1, $$14.below(), Direction.UP)) {
/* 52 */         $$1.setBlock($$14, (BlockState)Blocks.SCULK_SHRIEKER.defaultBlockState().setValue((Property)SculkShriekerBlock.CAN_SUMMON, Boolean.valueOf(true)), 3);
/*    */       }
/*    */     } 
/* 55 */     return true;
/*    */   }
/*    */   
/*    */   private boolean canSpreadFrom(LevelAccessor $$0, BlockPos $$1) {
/* 59 */     BlockState $$2 = $$0.getBlockState($$1);
/* 60 */     if ($$2.getBlock() instanceof net.minecraft.world.level.block.SculkBehaviour) {
/* 61 */       return true;
/*    */     }
/* 63 */     if ($$2.isAir() || ($$2.is(Blocks.WATER) && $$2.getFluidState().isSource())) {
/* 64 */       Objects.requireNonNull($$1); return Direction.stream().map($$1::relative).anyMatch($$1 -> $$0.getBlockState($$1).isCollisionShapeFullBlock((BlockGetter)$$0, $$1));
/*    */     } 
/* 66 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SculkPatchFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */