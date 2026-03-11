/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.DustParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class RedstoneWallTorchBlock extends RedstoneTorchBlock {
/*  24 */   public static final MapCodec<RedstoneWallTorchBlock> CODEC = simpleCodec(RedstoneWallTorchBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<RedstoneWallTorchBlock> codec() {
/*  28 */     return CODEC;
/*     */   }
/*     */   
/*  31 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  32 */   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
/*     */   
/*     */   protected RedstoneWallTorchBlock(BlockBehaviour.Properties $$0) {
/*  35 */     super($$0);
/*  36 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)LIT, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionId() {
/*  41 */     return asItem().getDescriptionId();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  46 */     return WallTorchBlock.getShape($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  51 */     return Blocks.WALL_TORCH.canSurvive($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  56 */     return Blocks.WALL_TORCH.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  62 */     BlockState $$1 = Blocks.WALL_TORCH.getStateForPlacement($$0);
/*  63 */     return ($$1 == null) ? null : (BlockState)defaultBlockState().setValue((Property)FACING, $$1.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/*  68 */     if (!((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     Direction $$4 = ((Direction)$$0.getValue((Property)FACING)).getOpposite();
/*  73 */     double $$5 = 0.27D;
/*  74 */     double $$6 = $$2.getX() + 0.5D + ($$3.nextDouble() - 0.5D) * 0.2D + 0.27D * $$4.getStepX();
/*  75 */     double $$7 = $$2.getY() + 0.7D + ($$3.nextDouble() - 0.5D) * 0.2D + 0.22D;
/*  76 */     double $$8 = $$2.getZ() + 0.5D + ($$3.nextDouble() - 0.5D) * 0.2D + 0.27D * $$4.getStepZ();
/*     */     
/*  78 */     $$1.addParticle((ParticleOptions)DustParticleOptions.REDSTONE, $$6, $$7, $$8, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasNeighborSignal(Level $$0, BlockPos $$1, BlockState $$2) {
/*  83 */     Direction $$3 = ((Direction)$$2.getValue((Property)FACING)).getOpposite();
/*     */     
/*  85 */     return $$0.hasSignal($$1.relative($$3), $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  90 */     if (((Boolean)$$0.getValue((Property)LIT)).booleanValue() && $$0.getValue((Property)FACING) != $$3) {
/*  91 */       return 15;
/*     */     }
/*     */     
/*  94 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  99 */     return Blocks.WALL_TORCH.rotate($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 104 */     return Blocks.WALL_TORCH.mirror($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 109 */     $$0.add(new Property[] { (Property)FACING, (Property)LIT });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RedstoneWallTorchBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */