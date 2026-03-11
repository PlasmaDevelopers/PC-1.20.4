/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BrewingStandBlock extends BaseEntityBlock {
/*  34 */   public static final MapCodec<BrewingStandBlock> CODEC = simpleCodec(BrewingStandBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<BrewingStandBlock> codec() {
/*  38 */     return CODEC;
/*     */   }
/*     */   
/*  41 */   public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[] { BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1, BlockStateProperties.HAS_BOTTLE_2 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   protected static final VoxelShape SHAPE = Shapes.or(
/*  48 */       Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D), 
/*  49 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D));
/*     */ 
/*     */   
/*     */   public BrewingStandBlock(BlockBehaviour.Properties $$0) {
/*  53 */     super($$0);
/*  54 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HAS_BOTTLE[0], Boolean.valueOf(false))).setValue((Property)HAS_BOTTLE[1], Boolean.valueOf(false))).setValue((Property)HAS_BOTTLE[2], Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  59 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  64 */     return (BlockEntity)new BrewingStandBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/*  70 */     return $$0.isClientSide ? null : createTickerHelper($$2, BlockEntityType.BREWING_STAND, BrewingStandBlockEntity::serverTick);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  75 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  80 */     if ($$1.isClientSide) {
/*  81 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  84 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/*  85 */     if ($$6 instanceof BrewingStandBlockEntity) {
/*  86 */       $$3.openMenu((MenuProvider)$$6);
/*  87 */       $$3.awardStat(Stats.INTERACT_WITH_BREWINGSTAND);
/*     */     } 
/*     */     
/*  90 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/*  95 */     if ($$4.hasCustomHoverName()) {
/*  96 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/*  97 */       if ($$5 instanceof BrewingStandBlockEntity) {
/*  98 */         ((BrewingStandBlockEntity)$$5).setCustomName($$4.getHoverName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 105 */     double $$4 = $$2.getX() + 0.4D + $$3.nextFloat() * 0.2D;
/* 106 */     double $$5 = $$2.getY() + 0.7D + $$3.nextFloat() * 0.3D;
/* 107 */     double $$6 = $$2.getZ() + 0.4D + $$3.nextFloat() * 0.2D;
/*     */     
/* 109 */     $$1.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 114 */     Containers.dropContentsOnDestroy($$0, $$3, $$1, $$2);
/* 115 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 125 */     return AbstractContainerMenu.getRedstoneSignalFromBlockEntity($$1.getBlockEntity($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 130 */     $$0.add(new Property[] { (Property)HAS_BOTTLE[0], (Property)HAS_BOTTLE[1], (Property)HAS_BOTTLE[2] });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BrewingStandBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */