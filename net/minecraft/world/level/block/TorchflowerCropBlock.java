/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class TorchflowerCropBlock extends CropBlock {
/* 19 */   public static final MapCodec<TorchflowerCropBlock> CODEC = simpleCodec(TorchflowerCropBlock::new);
/*    */   public static final int MAX_AGE = 2;
/*    */   
/*    */   public MapCodec<TorchflowerCropBlock> codec() {
/* 23 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 27 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
/*    */   
/*    */   private static final float AABB_OFFSET = 3.0F;
/* 30 */   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
/* 31 */       Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D), 
/* 32 */       Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)
/*    */     };
/*    */   
/*    */   private static final int BONEMEAL_INCREASE = 1;
/*    */   
/*    */   public TorchflowerCropBlock(BlockBehaviour.Properties $$0) {
/* 38 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 43 */     $$0.add(new Property[] { (Property)AGE });
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 48 */     return SHAPE_BY_AGE[getAge($$0)];
/*    */   }
/*    */ 
/*    */   
/*    */   protected IntegerProperty getAgeProperty() {
/* 53 */     return AGE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxAge() {
/* 58 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemLike getBaseSeedId() {
/* 63 */     return (ItemLike)Items.TORCHFLOWER_SEEDS;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForAge(int $$0) {
/* 68 */     if ($$0 == 2)
/*    */     {
/* 70 */       return Blocks.TORCHFLOWER.defaultBlockState();
/*    */     }
/* 72 */     return super.getStateForAge($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 77 */     if ($$3.nextInt(3) != 0) {
/* 78 */       super.randomTick($$0, $$1, $$2, $$3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBonemealAgeIncrease(Level $$0) {
/* 84 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TorchflowerCropBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */