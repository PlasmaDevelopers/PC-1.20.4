/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class WallHangingSignBlock extends SignBlock {
/*     */   static {
/*  41 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)WoodType.CODEC.fieldOf("wood_type").forGetter(SignBlock::type), (App)propertiesCodec()).apply((Applicative)$$0, WallHangingSignBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<WallHangingSignBlock> CODEC;
/*     */   
/*     */   public MapCodec<WallHangingSignBlock> codec() {
/*  48 */     return CODEC;
/*     */   }
/*     */   
/*  51 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  52 */   public static final VoxelShape PLANK_NORTHSOUTH = Block.box(0.0D, 14.0D, 6.0D, 16.0D, 16.0D, 10.0D);
/*  53 */   public static final VoxelShape PLANK_EASTWEST = Block.box(6.0D, 14.0D, 0.0D, 10.0D, 16.0D, 16.0D);
/*     */   
/*  55 */   public static final VoxelShape SHAPE_NORTHSOUTH = Shapes.or(PLANK_NORTHSOUTH, 
/*     */       
/*  57 */       Block.box(1.0D, 0.0D, 7.0D, 15.0D, 10.0D, 9.0D));
/*     */   
/*  59 */   public static final VoxelShape SHAPE_EASTWEST = Shapes.or(PLANK_EASTWEST, 
/*     */       
/*  61 */       Block.box(7.0D, 0.0D, 1.0D, 9.0D, 10.0D, 15.0D));
/*     */   
/*  63 */   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, SHAPE_NORTHSOUTH, Direction.SOUTH, SHAPE_NORTHSOUTH, Direction.EAST, SHAPE_EASTWEST, Direction.WEST, SHAPE_EASTWEST));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WallHangingSignBlock(WoodType $$0, BlockBehaviour.Properties $$1) {
/*  71 */     super($$0, $$1.sound($$0.hangingSignSoundType()));
/*  72 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  77 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof SignBlockEntity) { SignBlockEntity $$6 = (SignBlockEntity)blockEntity;
/*  78 */       ItemStack $$7 = $$3.getItemInHand($$4);
/*     */       
/*  80 */       if (shouldTryToChainAnotherHangingSign($$0, $$3, $$5, $$6, $$7)) {
/*  81 */         return InteractionResult.PASS;
/*     */       } }
/*     */     
/*  84 */     return super.use($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private boolean shouldTryToChainAnotherHangingSign(BlockState $$0, Player $$1, BlockHitResult $$2, SignBlockEntity $$3, ItemStack $$4) {
/*  88 */     return (!$$3.canExecuteClickCommands($$3.isFacingFrontText($$1), $$1) && $$4
/*  89 */       .getItem() instanceof net.minecraft.world.item.HangingSignItem && !isHittingEditableSide($$2, $$0));
/*     */   }
/*     */   
/*     */   private boolean isHittingEditableSide(BlockHitResult $$0, BlockState $$1) {
/*  93 */     return ($$0.getDirection().getAxis() == ((Direction)$$1.getValue((Property)FACING)).getAxis());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionId() {
/*  98 */     return asItem().getDescriptionId();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 103 */     return AABBS.get($$0.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 108 */     return getShape($$0, $$1, $$2, CollisionContext.empty());
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 113 */     switch ((Direction)$$0.getValue((Property)FACING)) {
/*     */       case EAST:
/*     */       case WEST:
/* 116 */         return PLANK_EASTWEST;
/*     */     } 
/* 118 */     return PLANK_NORTHSOUTH;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlace(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 123 */     Direction $$3 = ((Direction)$$0.getValue((Property)FACING)).getClockWise();
/* 124 */     Direction $$4 = ((Direction)$$0.getValue((Property)FACING)).getCounterClockWise();
/*     */     
/* 126 */     return (canAttachTo($$1, $$0, $$2.relative($$3), $$4) || canAttachTo($$1, $$0, $$2.relative($$4), $$3));
/*     */   }
/*     */   
/*     */   public boolean canAttachTo(LevelReader $$0, BlockState $$1, BlockPos $$2, Direction $$3) {
/* 130 */     BlockState $$4 = $$0.getBlockState($$2);
/*     */ 
/*     */     
/* 133 */     if ($$4.is(BlockTags.WALL_HANGING_SIGNS)) {
/* 134 */       return ((Direction)$$4.getValue((Property)FACING)).getAxis().test((Direction)$$1.getValue((Property)FACING));
/*     */     }
/*     */     
/* 137 */     return $$4.isFaceSturdy((BlockGetter)$$0, $$2, $$3, SupportType.FULL);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 143 */     BlockState $$1 = defaultBlockState();
/* 144 */     FluidState $$2 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */     
/* 146 */     Level level = $$0.getLevel();
/* 147 */     BlockPos $$4 = $$0.getClickedPos();
/*     */     
/* 149 */     for (Direction $$5 : $$0.getNearestLookingDirections()) {
/* 150 */       if ($$5.getAxis().isHorizontal() && !$$5.getAxis().test($$0.getClickedFace())) {
/*     */ 
/*     */ 
/*     */         
/* 154 */         Direction $$6 = $$5.getOpposite();
/* 155 */         $$1 = (BlockState)$$1.setValue((Property)FACING, (Comparable)$$6);
/* 156 */         if ($$1.canSurvive((LevelReader)level, $$4) && canPlace($$1, (LevelReader)level, $$4)) {
/* 157 */           return (BlockState)$$1.setValue((Property)WATERLOGGED, Boolean.valueOf(($$2.getType() == Fluids.WATER)));
/*     */         }
/*     */       } 
/*     */     } 
/* 161 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 166 */     if ($$1.getAxis() == ((Direction)$$0.getValue((Property)FACING)).getClockWise().getAxis() && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 167 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/* 169 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYRotationDegrees(BlockState $$0) {
/* 174 */     return ((Direction)$$0.getValue((Property)FACING)).toYRot();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 179 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 184 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 189 */     $$0.add(new Property[] { (Property)FACING, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 194 */     return (BlockEntity)new HangingSignBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 205 */     return createTickerHelper($$2, BlockEntityType.HANGING_SIGN, SignBlockEntity::tick);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WallHangingSignBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */