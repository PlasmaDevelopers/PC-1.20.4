/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FlowingFluid;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class LiquidBlock extends Block implements BucketPickup {
/*     */   static {
/*  39 */     FLOWING_FLUID = BuiltInRegistries.FLUID.byNameCodec().comapFlatMap($$0 -> { FlowingFluid $$1 = (FlowingFluid)$$0; return (Function)(($$0 instanceof FlowingFluid) ? DataResult.success($$1) : DataResult.error(()));
/*     */         }$$0 -> $$0);
/*  41 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)FLOWING_FLUID.fieldOf("fluid").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, LiquidBlock::new));
/*     */   }
/*     */   
/*     */   private static final Codec<FlowingFluid> FLOWING_FLUID;
/*     */   public static final MapCodec<LiquidBlock> CODEC;
/*     */   
/*     */   public MapCodec<LiquidBlock> codec() {
/*  48 */     return CODEC;
/*     */   }
/*     */   
/*  51 */   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;
/*     */   
/*     */   protected final FlowingFluid fluid;
/*     */   
/*     */   private final List<FluidState> stateCache;
/*  56 */   public static final VoxelShape STABLE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */   
/*  58 */   public static final ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS = ImmutableList.of(Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);
/*     */   
/*     */   protected LiquidBlock(FlowingFluid $$0, BlockBehaviour.Properties $$1) {
/*  61 */     super($$1);
/*  62 */     this.fluid = $$0;
/*  63 */     this.stateCache = Lists.newArrayList();
/*  64 */     this.stateCache.add($$0.getSource(false));
/*  65 */     for (int $$2 = 1; $$2 < 8; $$2++) {
/*  66 */       this.stateCache.add($$0.getFlowing(8 - $$2, false));
/*     */     }
/*  68 */     this.stateCache.add($$0.getFlowing(8, true));
/*  69 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)LEVEL, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  75 */     if ($$3.isAbove(STABLE_SHAPE, $$2, true) && ((Integer)$$0.getValue((Property)LEVEL)).intValue() == 0 && $$3.canStandOnFluid($$1.getFluidState($$2.above()), $$0.getFluidState())) {
/*  76 */       return STABLE_SHAPE;
/*     */     }
/*  78 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/*  83 */     return $$0.getFluidState().isRandomlyTicking();
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  88 */     $$0.getFluidState().randomTick((Level)$$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/*  98 */     return !this.fluid.is(FluidTags.LAVA);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 103 */     int $$1 = ((Integer)$$0.getValue((Property)LEVEL)).intValue();
/* 104 */     return this.stateCache.get(Math.min($$1, 8));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean skipRendering(BlockState $$0, BlockState $$1, Direction $$2) {
/* 109 */     return $$1.getFluidState().getType().isSame((Fluid)this.fluid);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 114 */     return RenderShape.INVISIBLE;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(BlockState $$0, LootParams.Builder $$1) {
/* 119 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 124 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 129 */     if (shouldSpreadLiquid($$1, $$2, $$0)) {
/* 130 */       $$1.scheduleTick($$2, $$0.getFluidState().getType(), this.fluid.getTickDelay((LevelReader)$$1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 136 */     if ($$0.getFluidState().isSource() || $$2.getFluidState().isSource()) {
/* 137 */       $$3.scheduleTick($$4, $$0.getFluidState().getType(), this.fluid.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/* 140 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 145 */     if (shouldSpreadLiquid($$1, $$2, $$0)) {
/* 146 */       $$1.scheduleTick($$2, $$0.getFluidState().getType(), this.fluid.getTickDelay((LevelReader)$$1));
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean shouldSpreadLiquid(Level $$0, BlockPos $$1, BlockState $$2) {
/* 151 */     if (this.fluid.is(FluidTags.LAVA)) {
/* 152 */       boolean $$3 = $$0.getBlockState($$1.below()).is(Blocks.SOUL_SOIL);
/*     */       
/* 154 */       for (UnmodifiableIterator<Direction> unmodifiableIterator = POSSIBLE_FLOW_DIRECTIONS.iterator(); unmodifiableIterator.hasNext(); ) { Direction $$4 = unmodifiableIterator.next();
/* 155 */         BlockPos $$5 = $$1.relative($$4.getOpposite());
/*     */         
/* 157 */         if ($$0.getFluidState($$5).is(FluidTags.WATER)) {
/* 158 */           Block $$6 = $$0.getFluidState($$1).isSource() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
/* 159 */           $$0.setBlockAndUpdate($$1, $$6.defaultBlockState());
/* 160 */           fizz((LevelAccessor)$$0, $$1);
/* 161 */           return false;
/*     */         } 
/*     */         
/* 164 */         if ($$3 && $$0.getBlockState($$5).is(Blocks.BLUE_ICE)) {
/* 165 */           $$0.setBlockAndUpdate($$1, Blocks.BASALT.defaultBlockState());
/* 166 */           fizz((LevelAccessor)$$0, $$1);
/* 167 */           return false;
/*     */         }  }
/*     */     
/*     */     } 
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   private void fizz(LevelAccessor $$0, BlockPos $$1) {
/* 175 */     $$0.levelEvent(1501, $$1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 180 */     $$0.add(new Property[] { (Property)LEVEL });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack pickupBlock(@Nullable Player $$0, LevelAccessor $$1, BlockPos $$2, BlockState $$3) {
/* 185 */     if (((Integer)$$3.getValue((Property)LEVEL)).intValue() == 0) {
/* 186 */       $$1.setBlock($$2, Blocks.AIR.defaultBlockState(), 11);
/* 187 */       return new ItemStack((ItemLike)this.fluid.getBucket());
/*     */     } 
/* 189 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<SoundEvent> getPickupSound() {
/* 194 */     return this.fluid.getPickupSound();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LiquidBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */