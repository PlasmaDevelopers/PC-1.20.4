/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
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
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class CandleCakeBlock extends AbstractCandleBlock {
/*     */   static {
/*  33 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("candle").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, CandleCakeBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<CandleCakeBlock> CODEC;
/*     */   
/*     */   public MapCodec<CandleCakeBlock> codec() {
/*  40 */     return CODEC;
/*     */   }
/*     */   
/*  43 */   public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
/*     */   
/*     */   protected static final float AABB_OFFSET = 1.0F;
/*  46 */   protected static final VoxelShape CAKE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
/*  47 */   protected static final VoxelShape CANDLE_SHAPE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
/*  48 */   protected static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);
/*     */   
/*  50 */   private static final Map<Block, CandleCakeBlock> BY_CANDLE = Maps.newHashMap();
/*     */   
/*  52 */   private static final Iterable<Vec3> PARTICLE_OFFSETS = (Iterable<Vec3>)ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));
/*     */   
/*     */   private final Block candleBlock;
/*     */   
/*     */   protected CandleCakeBlock(Block $$0, BlockBehaviour.Properties $$1) {
/*  57 */     super($$1);
/*  58 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)LIT, Boolean.valueOf(false)));
/*     */     
/*  60 */     BY_CANDLE.put($$0, this);
/*  61 */     this.candleBlock = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<Vec3> getParticleOffsets(BlockState $$0) {
/*  66 */     return PARTICLE_OFFSETS;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  71 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  76 */     ItemStack $$6 = $$3.getItemInHand($$4);
/*  77 */     if ($$6.is(Items.FLINT_AND_STEEL) || $$6.is(Items.FIRE_CHARGE)) {
/*  78 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/*  81 */     if (candleHit($$5) && $$3.getItemInHand($$4).isEmpty() && ((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*  82 */       extinguish($$3, $$0, (LevelAccessor)$$1, $$2);
/*     */     } else {
/*  84 */       InteractionResult $$7 = CakeBlock.eat((LevelAccessor)$$1, $$2, Blocks.CAKE.defaultBlockState(), $$3);
/*  85 */       if ($$7.consumesAction()) {
/*  86 */         dropResources($$0, $$1, $$2);
/*     */       }
/*  88 */       return $$7;
/*     */     } 
/*     */     
/*  91 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */   
/*     */   private static boolean candleHit(BlockHitResult $$0) {
/*  95 */     return (($$0.getLocation()).y - $$0.getBlockPos().getY() > 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 100 */     $$0.add(new Property[] { (Property)LIT });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 105 */     return new ItemStack(Blocks.CAKE);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 110 */     if ($$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 111 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 114 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 119 */     return $$1.getBlockState($$2.below()).isSolid();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 124 */     return CakeBlock.FULL_CAKE_SIGNAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 134 */     return false;
/*     */   }
/*     */   
/*     */   public static BlockState byCandle(Block $$0) {
/* 138 */     return ((CandleCakeBlock)BY_CANDLE.get($$0)).defaultBlockState();
/*     */   }
/*     */   
/*     */   public static boolean canLight(BlockState $$0) {
/* 142 */     return $$0.is(BlockTags.CANDLE_CAKES, $$1 -> ($$1.hasProperty((Property)LIT) && !((Boolean)$$0.getValue((Property)LIT)).booleanValue()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CandleCakeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */