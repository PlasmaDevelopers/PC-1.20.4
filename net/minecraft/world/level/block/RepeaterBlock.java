/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.DustParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class RepeaterBlock extends DiodeBlock {
/*  24 */   public static final MapCodec<RepeaterBlock> CODEC = simpleCodec(RepeaterBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<RepeaterBlock> codec() {
/*  28 */     return CODEC;
/*     */   }
/*     */   
/*  31 */   public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
/*  32 */   public static final IntegerProperty DELAY = BlockStateProperties.DELAY;
/*     */   
/*     */   protected RepeaterBlock(BlockBehaviour.Properties $$0) {
/*  35 */     super($$0);
/*  36 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)DELAY, Integer.valueOf(1))).setValue((Property)LOCKED, Boolean.valueOf(false))).setValue((Property)POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  41 */     if (!($$3.getAbilities()).mayBuild) {
/*  42 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/*  45 */     $$1.setBlock($$2, (BlockState)$$0.cycle((Property)DELAY), 3);
/*  46 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getDelay(BlockState $$0) {
/*  51 */     return ((Integer)$$0.getValue((Property)DELAY)).intValue() * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  56 */     BlockState $$1 = super.getStateForPlacement($$0);
/*  57 */     return (BlockState)$$1.setValue((Property)LOCKED, Boolean.valueOf(isLocked((LevelReader)$$0.getLevel(), $$0.getClickedPos(), $$1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  62 */     if ($$1 == Direction.DOWN && !canSurviveOn((LevelReader)$$3, $$5, $$2)) {
/*  63 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/*  66 */     if (!$$3.isClientSide() && $$1.getAxis() != ((Direction)$$0.getValue((Property)FACING)).getAxis()) {
/*  67 */       return (BlockState)$$0.setValue((Property)LOCKED, Boolean.valueOf(isLocked((LevelReader)$$3, $$4, $$0)));
/*     */     }
/*  69 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/*  74 */     return (getAlternateSignal((SignalGetter)$$0, $$1, $$2) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean sideInputDiodesOnly() {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/*  84 */     if (!((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*     */       return;
/*     */     }
/*  87 */     Direction $$4 = (Direction)$$0.getValue((Property)FACING);
/*     */     
/*  89 */     double $$5 = $$2.getX() + 0.5D + ($$3.nextDouble() - 0.5D) * 0.2D;
/*  90 */     double $$6 = $$2.getY() + 0.4D + ($$3.nextDouble() - 0.5D) * 0.2D;
/*  91 */     double $$7 = $$2.getZ() + 0.5D + ($$3.nextDouble() - 0.5D) * 0.2D;
/*     */     
/*  93 */     float $$8 = -5.0F;
/*  94 */     if ($$3.nextBoolean()) {
/*  95 */       $$8 = (((Integer)$$0.getValue((Property)DELAY)).intValue() * 2 - 1);
/*     */     }
/*  97 */     $$8 /= 16.0F;
/*     */     
/*  99 */     double $$9 = ($$8 * $$4.getStepX());
/* 100 */     double $$10 = ($$8 * $$4.getStepZ());
/*     */     
/* 102 */     $$1.addParticle((ParticleOptions)DustParticleOptions.REDSTONE, $$5 + $$9, $$6, $$7 + $$10, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 107 */     $$0.add(new Property[] { (Property)FACING, (Property)DELAY, (Property)LOCKED, (Property)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RepeaterBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */