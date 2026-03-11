/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class BeetrootBlock extends CropBlock {
/* 19 */   public static final MapCodec<BeetrootBlock> CODEC = simpleCodec(BeetrootBlock::new);
/*    */   public static final int MAX_AGE = 3;
/*    */   
/*    */   public MapCodec<BeetrootBlock> codec() {
/* 23 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 27 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
/*    */   
/* 29 */   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
/* 30 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), 
/* 31 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), 
/* 32 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
/* 33 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D)
/*    */     };
/*    */   
/*    */   public BeetrootBlock(BlockBehaviour.Properties $$0) {
/* 37 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IntegerProperty getAgeProperty() {
/* 42 */     return AGE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxAge() {
/* 47 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemLike getBaseSeedId() {
/* 52 */     return (ItemLike)Items.BEETROOT_SEEDS;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 58 */     if ($$3.nextInt(3) != 0) {
/* 59 */       super.randomTick($$0, $$1, $$2, $$3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBonemealAgeIncrease(Level $$0) {
/* 65 */     return super.getBonemealAgeIncrease($$0) / 3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 70 */     $$0.add(new Property[] { (Property)AGE });
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 75 */     return SHAPE_BY_AGE[getAge($$0)];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BeetrootBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */