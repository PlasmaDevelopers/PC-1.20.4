/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class FarmBlock
/*     */   extends Block {
/*  32 */   public static final MapCodec<FarmBlock> CODEC = simpleCodec(FarmBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<FarmBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */   
/*  39 */   public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
/*  40 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
/*     */   
/*     */   public static final int MAX_MOISTURE = 7;
/*     */   
/*     */   protected FarmBlock(BlockBehaviour.Properties $$0) {
/*  45 */     super($$0);
/*  46 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)MOISTURE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  51 */     if ($$1 == Direction.UP && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  52 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*  54 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  59 */     BlockState $$3 = $$1.getBlockState($$2.above());
/*  60 */     return (!$$3.isSolid() || $$3.getBlock() instanceof FenceGateBlock || $$3.getBlock() instanceof net.minecraft.world.level.block.piston.MovingPistonBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  65 */     if (!defaultBlockState().canSurvive((LevelReader)$$0.getLevel(), $$0.getClickedPos())) {
/*  66 */       return Blocks.DIRT.defaultBlockState();
/*     */     }
/*  68 */     return super.getStateForPlacement($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  78 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  83 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/*  84 */       turnToDirt((Entity)null, $$0, (Level)$$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  90 */     int $$4 = ((Integer)$$0.getValue((Property)MOISTURE)).intValue();
/*  91 */     if (isNearWater((LevelReader)$$1, $$2) || $$1.isRainingAt($$2.above())) {
/*  92 */       if ($$4 < 7) {
/*  93 */         $$1.setBlock($$2, (BlockState)$$0.setValue((Property)MOISTURE, Integer.valueOf(7)), 2);
/*     */       }
/*  95 */     } else if ($$4 > 0) {
/*  96 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)MOISTURE, Integer.valueOf($$4 - 1)), 2);
/*  97 */     } else if (!shouldMaintainFarmland((BlockGetter)$$1, $$2)) {
/*  98 */       turnToDirt((Entity)null, $$0, (Level)$$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/* 104 */     if (!$$0.isClientSide && $$0.random.nextFloat() < $$4 - 0.5F && $$3 instanceof net.minecraft.world.entity.LivingEntity && (
/* 105 */       $$3 instanceof net.minecraft.world.entity.player.Player || $$0.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)))
/*     */     {
/* 107 */       if ($$3.getBbWidth() * $$3.getBbWidth() * $$3.getBbHeight() > 0.512F) {
/* 108 */         turnToDirt($$3, $$1, $$0, $$2);
/*     */       }
/*     */     }
/*     */     
/* 112 */     super.fallOn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static void turnToDirt(@Nullable Entity $$0, BlockState $$1, Level $$2, BlockPos $$3) {
/* 116 */     BlockState $$4 = pushEntitiesUp($$1, Blocks.DIRT.defaultBlockState(), (LevelAccessor)$$2, $$3);
/* 117 */     $$2.setBlockAndUpdate($$3, $$4);
/* 118 */     $$2.gameEvent(GameEvent.BLOCK_CHANGE, $$3, GameEvent.Context.of($$0, $$4));
/*     */   }
/*     */   
/*     */   private static boolean shouldMaintainFarmland(BlockGetter $$0, BlockPos $$1) {
/* 122 */     return $$0.getBlockState($$1.above()).is(BlockTags.MAINTAINS_FARMLAND);
/*     */   }
/*     */   
/*     */   private static boolean isNearWater(LevelReader $$0, BlockPos $$1) {
/* 126 */     for (BlockPos $$2 : BlockPos.betweenClosed($$1.offset(-4, 0, -4), $$1.offset(4, 1, 4))) {
/* 127 */       if ($$0.getFluidState($$2).is(FluidTags.WATER)) {
/* 128 */         return true;
/*     */       }
/*     */     } 
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 136 */     $$0.add(new Property[] { (Property)MOISTURE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 141 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FarmBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */