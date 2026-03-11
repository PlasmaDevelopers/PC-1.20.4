/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class CoralWallFanBlock extends BaseCoralWallFanBlock {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)CoralBlock.DEAD_CORAL_FIELD.forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, CoralWallFanBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<CoralWallFanBlock> CODEC;
/*    */   private final Block deadBlock;
/*    */   
/*    */   public MapCodec<CoralWallFanBlock> codec() {
/* 22 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CoralWallFanBlock(Block $$0, BlockBehaviour.Properties $$1) {
/* 28 */     super($$1);
/* 29 */     this.deadBlock = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 34 */     tryScheduleDieTick($$0, (LevelAccessor)$$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 39 */     if (!scanForWater($$0, (BlockGetter)$$1, $$2)) {
/* 40 */       $$1.setBlock($$2, (BlockState)((BlockState)this.deadBlock.defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)FACING, $$0.getValue((Property)FACING)), 2);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 46 */     if ($$1.getOpposite() == $$0.getValue((Property)FACING) && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 47 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/*    */     
/* 50 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 51 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/* 53 */     tryScheduleDieTick($$0, $$3, $$4);
/*    */     
/* 55 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CoralWallFanBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */