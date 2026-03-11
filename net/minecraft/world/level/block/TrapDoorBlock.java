/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockSetType;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Half;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class TrapDoorBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
/*     */   static {
/*  37 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BlockSetType.CODEC.fieldOf("block_set_type").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, TrapDoorBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<TrapDoorBlock> CODEC;
/*     */   
/*     */   public MapCodec<? extends TrapDoorBlock> codec() {
/*  44 */     return CODEC;
/*     */   }
/*     */   
/*  47 */   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
/*  48 */   public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
/*  49 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*  50 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*     */   protected static final int AABB_THICKNESS = 3;
/*  53 */   protected static final VoxelShape EAST_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
/*  54 */   protected static final VoxelShape WEST_OPEN_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  55 */   protected static final VoxelShape SOUTH_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
/*  56 */   protected static final VoxelShape NORTH_OPEN_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
/*  57 */   protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
/*  58 */   protected static final VoxelShape TOP_AABB = Block.box(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */   
/*     */   private final BlockSetType type;
/*     */   
/*     */   protected TrapDoorBlock(BlockSetType $$0, BlockBehaviour.Properties $$1) {
/*  63 */     super($$1.sound($$0.soundType()));
/*  64 */     this.type = $$0;
/*  65 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)OPEN, Boolean.valueOf(false))).setValue((Property)HALF, (Comparable)Half.BOTTOM)).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  70 */     if (!((Boolean)$$0.getValue((Property)OPEN)).booleanValue()) {
/*  71 */       return ($$0.getValue((Property)HALF) == Half.TOP) ? TOP_AABB : BOTTOM_AABB;
/*     */     }
/*     */     
/*  74 */     switch ((Direction)$$0.getValue((Property)FACING))
/*     */     
/*     */     { default:
/*  77 */         return NORTH_OPEN_AABB;
/*     */       case WATER:
/*  79 */         return SOUTH_OPEN_AABB;
/*     */       case AIR:
/*  81 */         return WEST_OPEN_AABB;
/*     */       case null:
/*  83 */         break; }  return EAST_OPEN_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/*  89 */     switch ($$3) {
/*     */       case LAND:
/*  91 */         return ((Boolean)$$0.getValue((Property)OPEN)).booleanValue();
/*     */       case WATER:
/*  93 */         return ((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue();
/*     */       case AIR:
/*  95 */         return ((Boolean)$$0.getValue((Property)OPEN)).booleanValue();
/*     */     } 
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 103 */     if (!this.type.canOpenByHand()) {
/* 104 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 107 */     toggle($$0, $$1, $$2, $$3);
/* 108 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onExplosionHit(BlockState $$0, Level $$1, BlockPos $$2, Explosion $$3, BiConsumer<ItemStack, BlockPos> $$4) {
/* 113 */     if ($$3.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK && !$$1.isClientSide() && this.type.canOpenByWindCharge() && !((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 114 */       toggle($$0, $$1, $$2, (Player)null);
/*     */     }
/* 116 */     super.onExplosionHit($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private void toggle(BlockState $$0, Level $$1, BlockPos $$2, @Nullable Player $$3) {
/* 120 */     BlockState $$4 = (BlockState)$$0.cycle((Property)OPEN);
/* 121 */     $$1.setBlock($$2, $$4, 2);
/*     */     
/* 123 */     if (((Boolean)$$4.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 124 */       $$1.scheduleTick($$2, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$1));
/*     */     }
/*     */     
/* 127 */     playSound($$3, $$1, $$2, ((Boolean)$$4.getValue((Property)OPEN)).booleanValue());
/*     */   }
/*     */   
/*     */   protected void playSound(@Nullable Player $$0, Level $$1, BlockPos $$2, boolean $$3) {
/* 131 */     $$1.playSound($$0, $$2, $$3 ? this.type.trapdoorOpen() : this.type.trapdoorClose(), SoundSource.BLOCKS, 1.0F, $$1.getRandom().nextFloat() * 0.1F + 0.9F);
/* 132 */     $$1.gameEvent((Entity)$$0, $$3 ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 137 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 141 */     boolean $$6 = $$1.hasNeighborSignal($$2);
/* 142 */     if ($$6 != ((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 143 */       if (((Boolean)$$0.getValue((Property)OPEN)).booleanValue() != $$6) {
/* 144 */         $$0 = (BlockState)$$0.setValue((Property)OPEN, Boolean.valueOf($$6));
/* 145 */         playSound((Player)null, $$1, $$2, $$6);
/*     */       } 
/* 147 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf($$6)), 2);
/*     */       
/* 149 */       if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 150 */         $$1.scheduleTick($$2, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$1));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 157 */     BlockState $$1 = defaultBlockState();
/* 158 */     FluidState $$2 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */     
/* 160 */     Direction $$3 = $$0.getClickedFace();
/* 161 */     if ($$0.replacingClickedOnBlock() || !$$3.getAxis().isHorizontal()) {
/* 162 */       $$1 = (BlockState)((BlockState)$$1.setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite())).setValue((Property)HALF, ($$3 == Direction.UP) ? (Comparable)Half.BOTTOM : (Comparable)Half.TOP);
/*     */     } else {
/* 164 */       $$1 = (BlockState)((BlockState)$$1.setValue((Property)FACING, (Comparable)$$3)).setValue((Property)HALF, (($$0.getClickLocation()).y - $$0.getClickedPos().getY() > 0.5D) ? (Comparable)Half.TOP : (Comparable)Half.BOTTOM);
/*     */     } 
/* 166 */     if ($$0.getLevel().hasNeighborSignal($$0.getClickedPos())) {
/* 167 */       $$1 = (BlockState)((BlockState)$$1.setValue((Property)OPEN, Boolean.valueOf(true))).setValue((Property)POWERED, Boolean.valueOf(true));
/*     */     }
/* 169 */     return (BlockState)$$1.setValue((Property)WATERLOGGED, Boolean.valueOf(($$2.getType() == Fluids.WATER)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 174 */     $$0.add(new Property[] { (Property)FACING, (Property)OPEN, (Property)HALF, (Property)POWERED, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 179 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 180 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 182 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 187 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 188 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/* 191 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   protected BlockSetType getType() {
/* 195 */     return this.type;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TrapDoorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */