/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.border.WorldBorder;
/*    */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class DragonEggBlock extends FallingBlock {
/* 20 */   public static final MapCodec<DragonEggBlock> CODEC = simpleCodec(DragonEggBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<DragonEggBlock> codec() {
/* 24 */     return CODEC;
/*    */   }
/*    */   
/* 27 */   protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
/*    */   
/*    */   public DragonEggBlock(BlockBehaviour.Properties $$0) {
/* 30 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 35 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 40 */     teleport($$0, $$1, $$2);
/* 41 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */   }
/*    */ 
/*    */   
/*    */   public void attack(BlockState $$0, Level $$1, BlockPos $$2, Player $$3) {
/* 46 */     teleport($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   private void teleport(BlockState $$0, Level $$1, BlockPos $$2) {
/* 50 */     WorldBorder $$3 = $$1.getWorldBorder();
/* 51 */     for (int $$4 = 0; $$4 < 1000; $$4++) {
/* 52 */       BlockPos $$5 = $$2.offset($$1.random
/* 53 */           .nextInt(16) - $$1.random.nextInt(16), $$1.random
/* 54 */           .nextInt(8) - $$1.random.nextInt(8), $$1.random
/* 55 */           .nextInt(16) - $$1.random.nextInt(16));
/*    */       
/* 57 */       if ($$1.getBlockState($$5).isAir() && $$3.isWithinBounds($$5)) {
/* 58 */         if ($$1.isClientSide) {
/* 59 */           for (int $$6 = 0; $$6 < 128; $$6++) {
/* 60 */             double $$7 = $$1.random.nextDouble();
/* 61 */             float $$8 = ($$1.random.nextFloat() - 0.5F) * 0.2F;
/* 62 */             float $$9 = ($$1.random.nextFloat() - 0.5F) * 0.2F;
/* 63 */             float $$10 = ($$1.random.nextFloat() - 0.5F) * 0.2F;
/*    */             
/* 65 */             double $$11 = Mth.lerp($$7, $$5.getX(), $$2.getX()) + $$1.random.nextDouble() - 0.5D + 0.5D;
/* 66 */             double $$12 = Mth.lerp($$7, $$5.getY(), $$2.getY()) + $$1.random.nextDouble() - 0.5D;
/* 67 */             double $$13 = Mth.lerp($$7, $$5.getZ(), $$2.getZ()) + $$1.random.nextDouble() - 0.5D + 0.5D;
/* 68 */             $$1.addParticle((ParticleOptions)ParticleTypes.PORTAL, $$11, $$12, $$13, $$8, $$9, $$10);
/*    */           } 
/*    */         } else {
/* 71 */           $$1.setBlock($$5, $$0, 2);
/* 72 */           $$1.removeBlock($$2, false);
/*    */         } 
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getDelayAfterPlace() {
/* 81 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 86 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DragonEggBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */