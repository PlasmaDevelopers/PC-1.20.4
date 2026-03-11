/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.grower.TreeGrower;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class SaplingBlock extends BushBlock implements BonemealableBlock {
/*    */   static {
/* 20 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)TreeGrower.CODEC.fieldOf("tree").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, SaplingBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<SaplingBlock> CODEC;
/*    */   
/*    */   public MapCodec<? extends SaplingBlock> codec() {
/* 27 */     return CODEC;
/*    */   }
/*    */   
/* 30 */   public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
/*    */   
/*    */   protected static final float AABB_OFFSET = 6.0F;
/* 33 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
/*    */   
/*    */   protected final TreeGrower treeGrower;
/*    */   
/*    */   protected SaplingBlock(TreeGrower $$0, BlockBehaviour.Properties $$1) {
/* 38 */     super($$1);
/* 39 */     this.treeGrower = $$0;
/* 40 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)STAGE, Integer.valueOf(0)));
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 45 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 50 */     if ($$1.getMaxLocalRawBrightness($$2.above()) >= 9 && 
/* 51 */       $$3.nextInt(7) == 0) {
/* 52 */       advanceTree($$1, $$2, $$0, $$3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void advanceTree(ServerLevel $$0, BlockPos $$1, BlockState $$2, RandomSource $$3) {
/* 58 */     if (((Integer)$$2.getValue((Property)STAGE)).intValue() == 0) {
/* 59 */       $$0.setBlock($$1, (BlockState)$$2.cycle((Property)STAGE), 4);
/*    */     } else {
/* 61 */       this.treeGrower.growTree($$0, $$0.getChunkSource().getGenerator(), $$1, $$2, $$3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 72 */     return ($$0.random.nextFloat() < 0.45D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 77 */     advanceTree($$0, $$2, $$3, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 82 */     $$0.add(new Property[] { (Property)STAGE });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SaplingBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */