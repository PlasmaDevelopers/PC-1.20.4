/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class SporeBlossomBlock extends Block {
/* 18 */   public static final MapCodec<SporeBlossomBlock> CODEC = simpleCodec(SporeBlossomBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SporeBlossomBlock> codec() {
/* 22 */     return CODEC;
/*    */   }
/*    */   
/* 25 */   private static final VoxelShape SHAPE = Block.box(2.0D, 13.0D, 2.0D, 14.0D, 16.0D, 14.0D);
/*    */   private static final int ADD_PARTICLE_ATTEMPTS = 14;
/*    */   private static final int PARTICLE_XZ_RADIUS = 10;
/*    */   private static final int PARTICLE_Y_MAX = 10;
/*    */   
/*    */   public SporeBlossomBlock(BlockBehaviour.Properties $$0) {
/* 31 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 36 */     return (Block.canSupportCenter($$1, $$2.above(), Direction.DOWN) && !$$1.isWaterAt($$2));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 41 */     if ($$1 == Direction.UP && !canSurvive($$0, (LevelReader)$$3, $$4)) {
/* 42 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/* 44 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 49 */     int $$4 = $$2.getX();
/* 50 */     int $$5 = $$2.getY();
/* 51 */     int $$6 = $$2.getZ();
/*    */     
/* 53 */     double $$7 = $$4 + $$3.nextDouble();
/* 54 */     double $$8 = $$5 + 0.7D;
/* 55 */     double $$9 = $$6 + $$3.nextDouble();
/*    */     
/* 57 */     $$1.addParticle((ParticleOptions)ParticleTypes.FALLING_SPORE_BLOSSOM, $$7, $$8, $$9, 0.0D, 0.0D, 0.0D);
/*    */     
/* 59 */     BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
/* 60 */     for (int $$11 = 0; $$11 < 14; $$11++) {
/* 61 */       $$10.set($$4 + Mth.nextInt($$3, -10, 10), $$5 - $$3.nextInt(10), $$6 + Mth.nextInt($$3, -10, 10));
/* 62 */       BlockState $$12 = $$1.getBlockState((BlockPos)$$10);
/* 63 */       if (!$$12.isCollisionShapeFullBlock((BlockGetter)$$1, (BlockPos)$$10)) {
/* 64 */         $$1.addParticle((ParticleOptions)ParticleTypes.SPORE_BLOSSOM_AIR, $$10.getX() + $$3.nextDouble(), $$10.getY() + $$3.nextDouble(), $$10.getZ() + $$3.nextDouble(), 0.0D, 0.0D, 0.0D);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 71 */     return SHAPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SporeBlossomBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */