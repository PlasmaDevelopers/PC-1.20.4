/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class FenceGateBlock extends HorizontalDirectionalBlock {
/*     */   static {
/*  34 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)WoodType.CODEC.fieldOf("wood_type").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, FenceGateBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<FenceGateBlock> CODEC;
/*     */   
/*     */   public MapCodec<FenceGateBlock> codec() {
/*  41 */     return CODEC;
/*     */   }
/*     */   
/*  44 */   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
/*  45 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*  46 */   public static final BooleanProperty IN_WALL = BlockStateProperties.IN_WALL;
/*     */   
/*  48 */   protected static final VoxelShape Z_SHAPE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
/*  49 */   protected static final VoxelShape X_SHAPE = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
/*     */   
/*  51 */   protected static final VoxelShape Z_SHAPE_LOW = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 13.0D, 10.0D);
/*  52 */   protected static final VoxelShape X_SHAPE_LOW = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 13.0D, 16.0D);
/*     */   
/*  54 */   protected static final VoxelShape Z_COLLISION_SHAPE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 24.0D, 10.0D);
/*  55 */   protected static final VoxelShape X_COLLISION_SHAPE = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 24.0D, 16.0D);
/*     */   
/*  57 */   protected static final VoxelShape Z_SUPPORT_SHAPE = Block.box(0.0D, 5.0D, 6.0D, 16.0D, 24.0D, 10.0D);
/*  58 */   protected static final VoxelShape X_SUPPORT_SHAPE = Block.box(6.0D, 5.0D, 0.0D, 10.0D, 24.0D, 16.0D);
/*     */   
/*  60 */   protected static final VoxelShape Z_OCCLUSION_SHAPE = Shapes.or(
/*  61 */       Block.box(0.0D, 5.0D, 7.0D, 2.0D, 16.0D, 9.0D), 
/*  62 */       Block.box(14.0D, 5.0D, 7.0D, 16.0D, 16.0D, 9.0D));
/*     */   
/*  64 */   protected static final VoxelShape X_OCCLUSION_SHAPE = Shapes.or(
/*  65 */       Block.box(7.0D, 5.0D, 0.0D, 9.0D, 16.0D, 2.0D), 
/*  66 */       Block.box(7.0D, 5.0D, 14.0D, 9.0D, 16.0D, 16.0D));
/*     */ 
/*     */   
/*  69 */   protected static final VoxelShape Z_OCCLUSION_SHAPE_LOW = Shapes.or(
/*  70 */       Block.box(0.0D, 2.0D, 7.0D, 2.0D, 13.0D, 9.0D), 
/*  71 */       Block.box(14.0D, 2.0D, 7.0D, 16.0D, 13.0D, 9.0D));
/*     */   
/*  73 */   protected static final VoxelShape X_OCCLUSION_SHAPE_LOW = Shapes.or(
/*  74 */       Block.box(7.0D, 2.0D, 0.0D, 9.0D, 13.0D, 2.0D), 
/*  75 */       Block.box(7.0D, 2.0D, 14.0D, 9.0D, 13.0D, 16.0D));
/*     */   
/*     */   private final WoodType type;
/*     */   
/*     */   public FenceGateBlock(WoodType $$0, BlockBehaviour.Properties $$1) {
/*  80 */     super($$1.sound($$0.soundType()));
/*  81 */     this.type = $$0;
/*     */     
/*  83 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)OPEN, Boolean.valueOf(false))).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)IN_WALL, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  88 */     if (((Boolean)$$0.getValue((Property)IN_WALL)).booleanValue()) {
/*  89 */       return (((Direction)$$0.getValue((Property)FACING)).getAxis() == Direction.Axis.X) ? X_SHAPE_LOW : Z_SHAPE_LOW;
/*     */     }
/*  91 */     return (((Direction)$$0.getValue((Property)FACING)).getAxis() == Direction.Axis.X) ? X_SHAPE : Z_SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  96 */     Direction.Axis $$6 = $$1.getAxis();
/*  97 */     if (((Direction)$$0.getValue((Property)FACING)).getClockWise().getAxis() == $$6) {
/*  98 */       boolean $$7 = (isWall($$2) || isWall($$3.getBlockState($$4.relative($$1.getOpposite()))));
/*  99 */       return (BlockState)$$0.setValue((Property)IN_WALL, Boolean.valueOf($$7));
/*     */     } 
/*     */     
/* 102 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 107 */     if (((Boolean)$$0.getValue((Property)OPEN)).booleanValue()) {
/* 108 */       return Shapes.empty();
/*     */     }
/* 110 */     return (((Direction)$$0.getValue((Property)FACING)).getAxis() == Direction.Axis.Z) ? Z_SUPPORT_SHAPE : X_SUPPORT_SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 116 */     if (((Boolean)$$0.getValue((Property)OPEN)).booleanValue()) {
/* 117 */       return Shapes.empty();
/*     */     }
/* 119 */     return (((Direction)$$0.getValue((Property)FACING)).getAxis() == Direction.Axis.Z) ? Z_COLLISION_SHAPE : X_COLLISION_SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 124 */     if (((Boolean)$$0.getValue((Property)IN_WALL)).booleanValue()) {
/* 125 */       return (((Direction)$$0.getValue((Property)FACING)).getAxis() == Direction.Axis.X) ? X_OCCLUSION_SHAPE_LOW : Z_OCCLUSION_SHAPE_LOW;
/*     */     }
/* 127 */     return (((Direction)$$0.getValue((Property)FACING)).getAxis() == Direction.Axis.X) ? X_OCCLUSION_SHAPE : Z_OCCLUSION_SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 132 */     switch ($$3) {
/*     */       case LAND:
/* 134 */         return ((Boolean)$$0.getValue((Property)OPEN)).booleanValue();
/*     */       case WATER:
/* 136 */         return false;
/*     */       case AIR:
/* 138 */         return ((Boolean)$$0.getValue((Property)OPEN)).booleanValue();
/*     */     } 
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 146 */     Level $$1 = $$0.getLevel();
/* 147 */     BlockPos $$2 = $$0.getClickedPos();
/*     */     
/* 149 */     boolean $$3 = $$1.hasNeighborSignal($$2);
/* 150 */     Direction $$4 = $$0.getHorizontalDirection();
/*     */     
/* 152 */     Direction.Axis $$5 = $$4.getAxis();
/*     */     
/* 154 */     boolean $$6 = (($$5 == Direction.Axis.Z && (isWall($$1.getBlockState($$2.west())) || isWall($$1.getBlockState($$2.east())))) || ($$5 == Direction.Axis.X && (isWall($$1.getBlockState($$2.north())) || isWall($$1.getBlockState($$2.south())))));
/* 155 */     return (BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$4)).setValue((Property)OPEN, Boolean.valueOf($$3))).setValue((Property)POWERED, Boolean.valueOf($$3))).setValue((Property)IN_WALL, Boolean.valueOf($$6));
/*     */   }
/*     */   
/*     */   private boolean isWall(BlockState $$0) {
/* 159 */     return $$0.is(BlockTags.WALLS);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 164 */     if (((Boolean)$$0.getValue((Property)OPEN)).booleanValue()) {
/* 165 */       $$0 = (BlockState)$$0.setValue((Property)OPEN, Boolean.valueOf(false));
/* 166 */       $$1.setBlock($$2, $$0, 10);
/*     */     } else {
/*     */       
/* 169 */       Direction $$6 = $$3.getDirection();
/* 170 */       if ($$0.getValue((Property)FACING) == $$6.getOpposite()) {
/* 171 */         $$0 = (BlockState)$$0.setValue((Property)FACING, (Comparable)$$6);
/*     */       }
/* 173 */       $$0 = (BlockState)$$0.setValue((Property)OPEN, Boolean.valueOf(true));
/* 174 */       $$1.setBlock($$2, $$0, 10);
/*     */     } 
/*     */     
/* 177 */     boolean $$7 = ((Boolean)$$0.getValue((Property)OPEN)).booleanValue();
/*     */     
/* 179 */     $$1.playSound($$3, $$2, $$7 ? this.type.fenceGateOpen() : this.type.fenceGateClose(), SoundSource.BLOCKS, 1.0F, $$1.getRandom().nextFloat() * 0.1F + 0.9F);
/* 180 */     $$1.gameEvent((Entity)$$3, $$7 ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, $$2);
/* 181 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onExplosionHit(BlockState $$0, Level $$1, BlockPos $$2, Explosion $$3, BiConsumer<ItemStack, BlockPos> $$4) {
/* 186 */     if ($$3.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK && !$$1.isClientSide() && !((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 187 */       boolean $$5 = ((Boolean)$$0.getValue((Property)OPEN)).booleanValue();
/* 188 */       $$1.setBlockAndUpdate($$2, (BlockState)$$0.setValue((Property)OPEN, Boolean.valueOf(!$$5)));
/*     */       
/* 190 */       $$1.playSound(null, $$2, $$5 ? this.type.fenceGateClose() : this.type.fenceGateOpen(), SoundSource.BLOCKS, 1.0F, $$1.getRandom().nextFloat() * 0.1F + 0.9F);
/* 191 */       $$1.gameEvent($$5 ? GameEvent.BLOCK_CLOSE : GameEvent.BLOCK_OPEN, $$2, GameEvent.Context.of($$0));
/*     */     } 
/* 193 */     super.onExplosionHit($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 198 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 202 */     boolean $$6 = $$1.hasNeighborSignal($$2);
/* 203 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue() != $$6) {
/* 204 */       $$1.setBlock($$2, (BlockState)((BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf($$6))).setValue((Property)OPEN, Boolean.valueOf($$6)), 2);
/* 205 */       if (((Boolean)$$0.getValue((Property)OPEN)).booleanValue() != $$6) {
/* 206 */         $$1.playSound(null, $$2, $$6 ? this.type.fenceGateOpen() : this.type.fenceGateClose(), SoundSource.BLOCKS, 1.0F, $$1.getRandom().nextFloat() * 0.1F + 0.9F);
/* 207 */         $$1.gameEvent(null, $$6 ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, $$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 214 */     $$0.add(new Property[] { (Property)FACING, (Property)OPEN, (Property)POWERED, (Property)IN_WALL });
/*     */   }
/*     */   
/*     */   public static boolean connectsToDirection(BlockState $$0, Direction $$1) {
/* 218 */     return (((Direction)$$0.getValue((Property)FACING)).getAxis() == $$1.getClockWise().getAxis());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FenceGateBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */