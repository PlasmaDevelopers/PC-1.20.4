/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.SimpleMenuProvider;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.item.FallingBlockEntity;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.AnvilMenu;
/*     */ import net.minecraft.world.inventory.ContainerLevelAccess;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class AnvilBlock extends FallingBlock {
/*  33 */   public static final MapCodec<AnvilBlock> CODEC = simpleCodec(AnvilBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<AnvilBlock> codec() {
/*  37 */     return CODEC;
/*     */   }
/*     */   
/*  40 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*     */   
/*  42 */   private static final VoxelShape BASE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
/*     */   
/*  44 */   private static final VoxelShape X_LEG1 = Block.box(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
/*  45 */   private static final VoxelShape X_LEG2 = Block.box(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
/*  46 */   private static final VoxelShape X_TOP = Block.box(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
/*     */   
/*  48 */   private static final VoxelShape Z_LEG1 = Block.box(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D);
/*  49 */   private static final VoxelShape Z_LEG2 = Block.box(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D);
/*  50 */   private static final VoxelShape Z_TOP = Block.box(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);
/*     */   
/*  52 */   private static final VoxelShape X_AXIS_AABB = Shapes.or(BASE, new VoxelShape[] { X_LEG1, X_LEG2, X_TOP });
/*  53 */   private static final VoxelShape Z_AXIS_AABB = Shapes.or(BASE, new VoxelShape[] { Z_LEG1, Z_LEG2, Z_TOP });
/*     */   
/*  55 */   private static final Component CONTAINER_TITLE = (Component)Component.translatable("container.repair");
/*     */   private static final float FALL_DAMAGE_PER_DISTANCE = 2.0F;
/*     */   private static final int FALL_DAMAGE_MAX = 40;
/*     */   
/*     */   public AnvilBlock(BlockBehaviour.Properties $$0) {
/*  60 */     super($$0);
/*  61 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  66 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getClockWise());
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  71 */     if ($$1.isClientSide) {
/*  72 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  75 */     $$3.openMenu($$0.getMenuProvider($$1, $$2));
/*  76 */     $$3.awardStat(Stats.INTERACT_WITH_ANVIL);
/*  77 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/*  83 */     return (MenuProvider)new SimpleMenuProvider(($$2, $$3, $$4) -> new AnvilMenu($$2, $$3, ContainerLevelAccess.create($$0, $$1)), CONTAINER_TITLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  88 */     Direction $$4 = (Direction)$$0.getValue((Property)FACING);
/*  89 */     if ($$4.getAxis() == Direction.Axis.X) {
/*  90 */       return X_AXIS_AABB;
/*     */     }
/*  92 */     return Z_AXIS_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void falling(FallingBlockEntity $$0) {
/*  98 */     $$0.setHurtsEntities(2.0F, 40);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLand(Level $$0, BlockPos $$1, BlockState $$2, BlockState $$3, FallingBlockEntity $$4) {
/* 103 */     if (!$$4.isSilent()) {
/* 104 */       $$0.levelEvent(1031, $$1, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBrokenAfterFall(Level $$0, BlockPos $$1, FallingBlockEntity $$2) {
/* 110 */     if (!$$2.isSilent()) {
/* 111 */       $$0.levelEvent(1029, $$1, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DamageSource getFallDamageSource(Entity $$0) {
/* 117 */     return $$0.damageSources().anvil($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockState damage(BlockState $$0) {
/* 122 */     if ($$0.is(Blocks.ANVIL)) {
/* 123 */       return (BlockState)Blocks.CHIPPED_ANVIL.defaultBlockState().setValue((Property)FACING, $$0.getValue((Property)FACING));
/*     */     }
/* 125 */     if ($$0.is(Blocks.CHIPPED_ANVIL)) {
/* 126 */       return (BlockState)Blocks.DAMAGED_ANVIL.defaultBlockState().setValue((Property)FACING, $$0.getValue((Property)FACING));
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 133 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 138 */     $$0.add(new Property[] { (Property)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDustColor(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 148 */     return ($$0.getMapColor($$1, $$2)).col;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AnvilBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */