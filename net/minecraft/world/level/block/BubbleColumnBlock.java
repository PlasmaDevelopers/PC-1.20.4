/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BubbleColumnBlock extends Block implements BucketPickup {
/*  34 */   public static final MapCodec<BubbleColumnBlock> CODEC = simpleCodec(BubbleColumnBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<BubbleColumnBlock> codec() {
/*  38 */     return CODEC;
/*     */   }
/*     */   
/*  41 */   public static final BooleanProperty DRAG_DOWN = BlockStateProperties.DRAG;
/*     */   private static final int CHECK_PERIOD = 5;
/*     */   
/*     */   public BubbleColumnBlock(BlockBehaviour.Properties $$0) {
/*  45 */     super($$0);
/*  46 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)DRAG_DOWN, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/*  51 */     BlockState $$4 = $$1.getBlockState($$2.above());
/*  52 */     if ($$4.isAir()) {
/*  53 */       $$3.onAboveBubbleCol(((Boolean)$$0.getValue((Property)DRAG_DOWN)).booleanValue());
/*     */       
/*  55 */       if (!$$1.isClientSide) {
/*  56 */         ServerLevel $$5 = (ServerLevel)$$1;
/*  57 */         for (int $$6 = 0; $$6 < 2; $$6++) {
/*  58 */           $$5.sendParticles((ParticleOptions)ParticleTypes.SPLASH, $$2.getX() + $$1.random.nextDouble(), ($$2.getY() + 1), $$2.getZ() + $$1.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
/*  59 */           $$5.sendParticles((ParticleOptions)ParticleTypes.BUBBLE, $$2.getX() + $$1.random.nextDouble(), ($$2.getY() + 1), $$2.getZ() + $$1.random.nextDouble(), 1, 0.0D, 0.01D, 0.0D, 0.2D);
/*     */         } 
/*     */       } 
/*     */     } else {
/*  63 */       $$3.onInsideBubbleColumn(((Boolean)$$0.getValue((Property)DRAG_DOWN)).booleanValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  69 */     updateColumn((LevelAccessor)$$1, $$2, $$0, $$1.getBlockState($$2.below()));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/*  74 */     return Fluids.WATER.getSource(false);
/*     */   }
/*     */   
/*     */   public static void updateColumn(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {
/*  78 */     updateColumn($$0, $$1, $$0.getBlockState($$1), $$2);
/*     */   }
/*     */   
/*     */   public static void updateColumn(LevelAccessor $$0, BlockPos $$1, BlockState $$2, BlockState $$3) {
/*  82 */     if (!canExistIn($$2)) {
/*     */       return;
/*     */     }
/*  85 */     BlockState $$4 = getColumnState($$3);
/*  86 */     $$0.setBlock($$1, $$4, 2);
/*     */     
/*  88 */     BlockPos.MutableBlockPos $$5 = $$1.mutable().move(Direction.UP);
/*  89 */     while (canExistIn($$0.getBlockState((BlockPos)$$5))) {
/*  90 */       if (!$$0.setBlock((BlockPos)$$5, $$4, 2)) {
/*     */         return;
/*     */       }
/*  93 */       $$5.move(Direction.UP);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean canExistIn(BlockState $$0) {
/*  98 */     return ($$0.is(Blocks.BUBBLE_COLUMN) || ($$0.is(Blocks.WATER) && $$0.getFluidState().getAmount() >= 8 && $$0.getFluidState().isSource()));
/*     */   }
/*     */   
/*     */   private static BlockState getColumnState(BlockState $$0) {
/* 102 */     if ($$0.is(Blocks.BUBBLE_COLUMN)) {
/* 103 */       return $$0;
/*     */     }
/* 105 */     if ($$0.is(Blocks.SOUL_SAND)) {
/* 106 */       return (BlockState)Blocks.BUBBLE_COLUMN.defaultBlockState().setValue((Property)DRAG_DOWN, Boolean.valueOf(false));
/*     */     }
/* 108 */     if ($$0.is(Blocks.MAGMA_BLOCK)) {
/* 109 */       return (BlockState)Blocks.BUBBLE_COLUMN.defaultBlockState().setValue((Property)DRAG_DOWN, Boolean.valueOf(true));
/*     */     }
/*     */     
/* 112 */     return Blocks.WATER.defaultBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 117 */     double $$4 = $$2.getX();
/* 118 */     double $$5 = $$2.getY();
/* 119 */     double $$6 = $$2.getZ();
/*     */     
/* 121 */     if (((Boolean)$$0.getValue((Property)DRAG_DOWN)).booleanValue()) {
/* 122 */       $$1.addAlwaysVisibleParticle((ParticleOptions)ParticleTypes.CURRENT_DOWN, $$4 + 0.5D, $$5 + 0.8D, $$6, 0.0D, 0.0D, 0.0D);
/* 123 */       if ($$3.nextInt(200) == 0) {
/* 124 */         $$1.playLocalSound($$4, $$5, $$6, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 0.2F + $$3.nextFloat() * 0.2F, 0.9F + $$3.nextFloat() * 0.15F, false);
/*     */       }
/*     */     } else {
/* 127 */       $$1.addAlwaysVisibleParticle((ParticleOptions)ParticleTypes.BUBBLE_COLUMN_UP, $$4 + 0.5D, $$5, $$6 + 0.5D, 0.0D, 0.04D, 0.0D);
/* 128 */       $$1.addAlwaysVisibleParticle((ParticleOptions)ParticleTypes.BUBBLE_COLUMN_UP, $$4 + $$3.nextFloat(), $$5 + $$3.nextFloat(), $$6 + $$3.nextFloat(), 0.0D, 0.04D, 0.0D);
/* 129 */       if ($$3.nextInt(200) == 0) {
/* 130 */         $$1.playLocalSound($$4, $$5, $$6, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 0.2F + $$3.nextFloat() * 0.2F, 0.9F + $$3.nextFloat() * 0.15F, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 137 */     $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     
/* 139 */     if (!$$0.canSurvive((LevelReader)$$3, $$4) || $$1 == Direction.DOWN || ($$1 == Direction.UP && 
/*     */       
/* 141 */       !$$2.is(Blocks.BUBBLE_COLUMN) && canExistIn($$2)))
/*     */     {
/* 143 */       $$3.scheduleTick($$4, this, 5);
/*     */     }
/*     */     
/* 146 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 151 */     BlockState $$3 = $$1.getBlockState($$2.below());
/*     */     
/* 153 */     return ($$3.is(Blocks.BUBBLE_COLUMN) || $$3.is(Blocks.MAGMA_BLOCK) || $$3.is(Blocks.SOUL_SAND));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 158 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 163 */     return RenderShape.INVISIBLE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 168 */     $$0.add(new Property[] { (Property)DRAG_DOWN });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack pickupBlock(@Nullable Player $$0, LevelAccessor $$1, BlockPos $$2, BlockState $$3) {
/* 173 */     $$1.setBlock($$2, Blocks.AIR.defaultBlockState(), 11);
/* 174 */     return new ItemStack((ItemLike)Items.WATER_BUCKET);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<SoundEvent> getPickupSound() {
/* 179 */     return Fluids.WATER.getPickupSound();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BubbleColumnBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */