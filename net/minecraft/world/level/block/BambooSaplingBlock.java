/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class BambooSaplingBlock extends Block implements BonemealableBlock {
/* 24 */   public static final MapCodec<BambooSaplingBlock> CODEC = simpleCodec(BambooSaplingBlock::new);
/*    */   protected static final float SAPLING_AABB_OFFSET = 4.0F;
/*    */   
/*    */   public MapCodec<BambooSaplingBlock> codec() {
/* 28 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 32 */   protected static final VoxelShape SAPLING_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);
/*    */   
/*    */   public BambooSaplingBlock(BlockBehaviour.Properties $$0) {
/* 35 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 40 */     Vec3 $$4 = $$0.getOffset($$1, $$2);
/* 41 */     return SAPLING_SHAPE.move($$4.x, $$4.y, $$4.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 46 */     if ($$3.nextInt(3) == 0 && $$1.isEmptyBlock($$2.above()) && $$1.getRawBrightness($$2.above(), 0) >= 9) {
/* 47 */       growBamboo((Level)$$1, $$2);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 53 */     return $$1.getBlockState($$2.below()).is(BlockTags.BAMBOO_PLANTABLE_ON);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 58 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 59 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/*    */     
/* 62 */     if ($$1 == Direction.UP && $$2.is(Blocks.BAMBOO)) {
/* 63 */       $$3.setBlock($$4, Blocks.BAMBOO.defaultBlockState(), 2);
/*    */     }
/*    */     
/* 66 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 71 */     return new ItemStack((ItemLike)Items.BAMBOO);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 76 */     return $$0.getBlockState($$1.above()).isAir();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 81 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 86 */     growBamboo((Level)$$0, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDestroyProgress(BlockState $$0, Player $$1, BlockGetter $$2, BlockPos $$3) {
/* 91 */     if ($$1.getMainHandItem().getItem() instanceof net.minecraft.world.item.SwordItem) {
/* 92 */       return 1.0F;
/*    */     }
/*    */     
/* 95 */     return super.getDestroyProgress($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   protected void growBamboo(Level $$0, BlockPos $$1) {
/* 99 */     $$0.setBlock($$1.above(), (BlockState)Blocks.BAMBOO.defaultBlockState().setValue((Property)BambooStalkBlock.LEAVES, (Comparable)BambooLeaves.SMALL), 3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BambooSaplingBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */