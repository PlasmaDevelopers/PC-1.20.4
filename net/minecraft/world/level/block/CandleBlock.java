/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.function.ToIntFunction;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
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
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class CandleBlock extends AbstractCandleBlock implements SimpleWaterloggedBlock {
/*  36 */   public static final MapCodec<CandleBlock> CODEC = simpleCodec(CandleBlock::new); public static final int MIN_CANDLES = 1;
/*     */   public static final int MAX_CANDLES = 4;
/*     */   
/*     */   public MapCodec<CandleBlock> codec() {
/*  40 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public static final IntegerProperty CANDLES = BlockStateProperties.CANDLES;
/*  47 */   public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
/*  48 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED; public static final ToIntFunction<BlockState> LIGHT_EMISSION; private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS;
/*     */   static {
/*  50 */     LIGHT_EMISSION = ($$0 -> ((Boolean)$$0.getValue((Property)LIT)).booleanValue() ? (3 * ((Integer)$$0.getValue((Property)CANDLES)).intValue()) : 0);
/*     */     
/*  52 */     PARTICLE_OFFSETS = (Int2ObjectMap<List<Vec3>>)Util.make(() -> {
/*     */           Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
/*     */           int2ObjectOpenHashMap.defaultReturnValue(ImmutableList.of());
/*     */           int2ObjectOpenHashMap.put(1, ImmutableList.of(new Vec3(0.5D, 0.5D, 0.5D)));
/*     */           int2ObjectOpenHashMap.put(2, ImmutableList.of(new Vec3(0.375D, 0.44D, 0.5D), new Vec3(0.625D, 0.5D, 0.44D)));
/*     */           int2ObjectOpenHashMap.put(3, ImmutableList.of(new Vec3(0.5D, 0.313D, 0.625D), new Vec3(0.375D, 0.44D, 0.5D), new Vec3(0.56D, 0.5D, 0.44D)));
/*     */           int2ObjectOpenHashMap.put(4, ImmutableList.of(new Vec3(0.44D, 0.313D, 0.56D), new Vec3(0.625D, 0.44D, 0.56D), new Vec3(0.375D, 0.44D, 0.375D), new Vec3(0.56D, 0.5D, 0.375D)));
/*     */           return Int2ObjectMaps.unmodifiable((Int2ObjectMap)int2ObjectOpenHashMap);
/*     */         });
/*     */   }
/*  62 */   private static final VoxelShape ONE_AABB = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 6.0D, 9.0D);
/*  63 */   private static final VoxelShape TWO_AABB = Block.box(5.0D, 0.0D, 6.0D, 11.0D, 6.0D, 9.0D);
/*  64 */   private static final VoxelShape THREE_AABB = Block.box(5.0D, 0.0D, 6.0D, 10.0D, 6.0D, 11.0D);
/*  65 */   private static final VoxelShape FOUR_AABB = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 10.0D);
/*     */   
/*     */   public CandleBlock(BlockBehaviour.Properties $$0) {
/*  68 */     super($$0);
/*  69 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)CANDLES, Integer.valueOf(1))).setValue((Property)LIT, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  74 */     if (($$3.getAbilities()).mayBuild && $$3.getItemInHand($$4).isEmpty() && ((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*  75 */       extinguish($$3, $$0, (LevelAccessor)$$1, $$2);
/*  76 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */     
/*  79 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/*  84 */     if (!$$1.isSecondaryUseActive() && $$1.getItemInHand().getItem() == asItem() && ((Integer)$$0.getValue((Property)CANDLES)).intValue() < 4) {
/*  85 */       return true;
/*     */     }
/*  87 */     return super.canBeReplaced($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  92 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos());
/*  93 */     if ($$1.is(this)) {
/*  94 */       return (BlockState)$$1.cycle((Property)CANDLES);
/*     */     }
/*     */     
/*  97 */     FluidState $$2 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*  98 */     boolean $$3 = ($$2.getType() == Fluids.WATER);
/*  99 */     return (BlockState)super.getStateForPlacement($$0).setValue((Property)WATERLOGGED, Boolean.valueOf($$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 104 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 105 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/* 108 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 113 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 114 */       return Fluids.WATER.getSource(false);
/*     */     }
/*     */     
/* 117 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 122 */     switch (((Integer)$$0.getValue((Property)CANDLES)).intValue())
/*     */     
/*     */     { default:
/* 125 */         return ONE_AABB;
/*     */       case 2:
/* 127 */         return TWO_AABB;
/*     */       case 3:
/* 129 */         return THREE_AABB;
/*     */       case 4:
/* 131 */         break; }  return FOUR_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 137 */     $$0.add(new Property[] { (Property)CANDLES, (Property)LIT, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean placeLiquid(LevelAccessor $$0, BlockPos $$1, BlockState $$2, FluidState $$3) {
/* 142 */     if (((Boolean)$$2.getValue((Property)WATERLOGGED)).booleanValue() || $$3.getType() != Fluids.WATER) {
/* 143 */       return false;
/*     */     }
/*     */     
/* 146 */     BlockState $$4 = (BlockState)$$2.setValue((Property)WATERLOGGED, Boolean.valueOf(true));
/* 147 */     if (((Boolean)$$2.getValue((Property)LIT)).booleanValue()) {
/* 148 */       extinguish((Player)null, $$4, $$0, $$1);
/*     */     } else {
/* 150 */       $$0.setBlock($$1, $$4, 3);
/*     */     } 
/*     */     
/* 153 */     $$0.scheduleTick($$1, $$3.getType(), $$3.getType().getTickDelay((LevelReader)$$0));
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean canLight(BlockState $$0) {
/* 158 */     return ($$0.is(BlockTags.CANDLES, $$0 -> ($$0.hasProperty((Property)LIT) && $$0.hasProperty((Property)WATERLOGGED))) && !((Boolean)$$0.getValue((Property)LIT)).booleanValue() && !((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Iterable<Vec3> getParticleOffsets(BlockState $$0) {
/* 164 */     return (Iterable<Vec3>)PARTICLE_OFFSETS.get(((Integer)$$0.getValue((Property)CANDLES)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canBeLit(BlockState $$0) {
/* 169 */     return (!((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue() && super.canBeLit($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 174 */     return Block.canSupportCenter($$1, $$2.below(), Direction.UP);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CandleBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */