/*     */ package net.minecraft.world.level.block.piston;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Collections;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.BaseEntityBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.PistonType;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class MovingPistonBlock extends BaseEntityBlock {
/*  40 */   public static final MapCodec<MovingPistonBlock> CODEC = simpleCodec(MovingPistonBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<MovingPistonBlock> codec() {
/*  44 */     return CODEC;
/*     */   }
/*     */   
/*  47 */   public static final DirectionProperty FACING = PistonHeadBlock.FACING;
/*  48 */   public static final EnumProperty<PistonType> TYPE = PistonHeadBlock.TYPE;
/*     */   
/*     */   public MovingPistonBlock(BlockBehaviour.Properties $$0) {
/*  51 */     super($$0);
/*  52 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)TYPE, (Comparable)PistonType.DEFAULT));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockEntity newMovingBlockEntity(BlockPos $$0, BlockState $$1, BlockState $$2, Direction $$3, boolean $$4, boolean $$5) {
/*  63 */     return new PistonMovingBlockEntity($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/*  69 */     return createTickerHelper($$2, BlockEntityType.PISTON, PistonMovingBlockEntity::tick);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  74 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*  77 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/*  78 */     if ($$5 instanceof PistonMovingBlockEntity) {
/*  79 */       ((PistonMovingBlockEntity)$$5).finalTick();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {
/*  86 */     BlockPos $$3 = $$1.relative(((Direction)$$2.getValue((Property)FACING)).getOpposite());
/*  87 */     BlockState $$4 = $$0.getBlockState($$3);
/*  88 */     if ($$4.getBlock() instanceof PistonBaseBlock && ((Boolean)$$4.getValue((Property)PistonBaseBlock.EXTENDED)).booleanValue()) {
/*  89 */       $$0.removeBlock($$3, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  96 */     if (!$$1.isClientSide && $$1.getBlockEntity($$2) == null) {
/*     */       
/*  98 */       $$1.removeBlock($$2, false);
/*  99 */       return InteractionResult.CONSUME;
/*     */     } 
/* 101 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(BlockState $$0, LootParams.Builder $$1) {
/* 107 */     PistonMovingBlockEntity $$2 = getBlockEntity((BlockGetter)$$1.getLevel(), BlockPos.containing((Position)$$1.getParameter(LootContextParams.ORIGIN)));
/* 108 */     if ($$2 == null) {
/* 109 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 112 */     return $$2.getMovedState().getDrops($$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 118 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 123 */     PistonMovingBlockEntity $$4 = getBlockEntity($$1, $$2);
/* 124 */     if ($$4 != null) {
/* 125 */       return $$4.getCollisionShape($$1, $$2);
/*     */     }
/* 127 */     return Shapes.empty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private PistonMovingBlockEntity getBlockEntity(BlockGetter $$0, BlockPos $$1) {
/* 132 */     BlockEntity $$2 = $$0.getBlockEntity($$1);
/* 133 */     if ($$2 instanceof PistonMovingBlockEntity) {
/* 134 */       return (PistonMovingBlockEntity)$$2;
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 141 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 146 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 151 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 156 */     $$0.add(new Property[] { (Property)FACING, (Property)TYPE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 161 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\piston\MovingPistonBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */