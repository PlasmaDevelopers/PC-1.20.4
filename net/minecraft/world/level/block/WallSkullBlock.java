/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class WallSkullBlock extends AbstractSkullBlock {
/*    */   static {
/* 20 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)SkullBlock.Type.CODEC.fieldOf("kind").forGetter(AbstractSkullBlock::getType), (App)propertiesCodec()).apply((Applicative)$$0, WallSkullBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<WallSkullBlock> CODEC;
/*    */   
/*    */   public MapCodec<? extends WallSkullBlock> codec() {
/* 27 */     return CODEC;
/*    */   }
/*    */   
/* 30 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*    */   
/* 32 */   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, 
/* 33 */         Block.box(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D), Direction.SOUTH, 
/* 34 */         Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D), Direction.EAST, 
/* 35 */         Block.box(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D), Direction.WEST, 
/* 36 */         Block.box(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D)));
/*    */ 
/*    */   
/*    */   protected WallSkullBlock(SkullBlock.Type $$0, BlockBehaviour.Properties $$1) {
/* 40 */     super($$0, $$1);
/* 41 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)Direction.NORTH));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescriptionId() {
/* 46 */     return asItem().getDescriptionId();
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 51 */     return AABBS.get($$0.getValue((Property)FACING));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 56 */     BlockState $$1 = super.getStateForPlacement($$0);
/*    */     
/* 58 */     Level level = $$0.getLevel();
/* 59 */     BlockPos $$3 = $$0.getClickedPos();
/*    */     
/* 61 */     Direction[] $$4 = $$0.getNearestLookingDirections();
/* 62 */     for (Direction $$5 : $$4) {
/* 63 */       if ($$5.getAxis().isHorizontal()) {
/*    */ 
/*    */ 
/*    */         
/* 67 */         Direction $$6 = $$5.getOpposite();
/*    */         
/* 69 */         $$1 = (BlockState)$$1.setValue((Property)FACING, (Comparable)$$6);
/* 70 */         if (!level.getBlockState($$3.relative($$5)).canBeReplaced($$0)) {
/* 71 */           return $$1;
/*    */         }
/*    */       } 
/*    */     } 
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 80 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 85 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 90 */     super.createBlockStateDefinition($$0);
/* 91 */     $$0.add(new Property[] { (Property)FACING });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WallSkullBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */