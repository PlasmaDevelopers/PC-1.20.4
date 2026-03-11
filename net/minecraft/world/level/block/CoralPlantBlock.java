/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class CoralPlantBlock extends BaseCoralPlantTypeBlock {
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)CoralBlock.DEAD_CORAL_FIELD.forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, CoralPlantBlock::new));
/*    */   }
/*    */   public static final MapCodec<CoralPlantBlock> CODEC;
/*    */   private final Block deadBlock;
/*    */   protected static final float AABB_OFFSET = 6.0F;
/*    */   
/*    */   public MapCodec<CoralPlantBlock> codec() {
/* 25 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 30 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);
/*    */   
/*    */   protected CoralPlantBlock(Block $$0, BlockBehaviour.Properties $$1) {
/* 33 */     super($$1);
/* 34 */     this.deadBlock = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 39 */     tryScheduleDieTick($$0, (LevelAccessor)$$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 44 */     if (!scanForWater($$0, (BlockGetter)$$1, $$2)) {
/* 45 */       $$1.setBlock($$2, (BlockState)this.deadBlock.defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(false)), 2);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 51 */     if ($$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 52 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/*    */     
/* 55 */     tryScheduleDieTick($$0, $$3, $$4);
/*    */     
/* 57 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 58 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/*    */     
/* 61 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 66 */     return SHAPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CoralPlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */