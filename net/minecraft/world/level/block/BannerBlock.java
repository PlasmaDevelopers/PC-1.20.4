/*    */ package net.minecraft.world.level.block;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.block.state.properties.RotationSegment;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class BannerBlock extends AbstractBannerBlock {
/*    */   static {
/* 24 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DyeColor.CODEC.fieldOf("color").forGetter(AbstractBannerBlock::getColor), (App)propertiesCodec()).apply((Applicative)$$0, BannerBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<BannerBlock> CODEC;
/*    */   
/*    */   public MapCodec<BannerBlock> codec() {
/* 31 */     return CODEC;
/*    */   }
/*    */   
/* 34 */   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
/*    */   
/* 36 */   private static final Map<DyeColor, Block> BY_COLOR = Maps.newHashMap();
/* 37 */   private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*    */   
/*    */   public BannerBlock(DyeColor $$0, BlockBehaviour.Properties $$1) {
/* 40 */     super($$0, $$1);
/* 41 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)ROTATION, Integer.valueOf(0)));
/*    */     
/* 43 */     BY_COLOR.put($$0, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 48 */     return $$1.getBlockState($$2.below()).isSolid();
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 53 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 58 */     return (BlockState)defaultBlockState().setValue((Property)ROTATION, Integer.valueOf(RotationSegment.convertToSegment($$0.getRotation() + 180.0F)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 63 */     if ($$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 64 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/*    */     
/* 67 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 72 */     return (BlockState)$$0.setValue((Property)ROTATION, Integer.valueOf($$1.rotate(((Integer)$$0.getValue((Property)ROTATION)).intValue(), 16)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 77 */     return (BlockState)$$0.setValue((Property)ROTATION, Integer.valueOf($$1.mirror(((Integer)$$0.getValue((Property)ROTATION)).intValue(), 16)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 82 */     $$0.add(new Property[] { (Property)ROTATION });
/*    */   }
/*    */   
/*    */   public static Block byColor(DyeColor $$0) {
/* 86 */     return BY_COLOR.getOrDefault($$0, Blocks.WHITE_BANNER);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BannerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */