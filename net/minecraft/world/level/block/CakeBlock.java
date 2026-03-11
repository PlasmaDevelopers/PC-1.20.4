/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
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
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class CakeBlock extends Block {
/*  31 */   public static final MapCodec<CakeBlock> CODEC = simpleCodec(CakeBlock::new);
/*     */   public static final int MAX_BITES = 6;
/*     */   
/*     */   public MapCodec<CakeBlock> codec() {
/*  35 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  39 */   public static final IntegerProperty BITES = BlockStateProperties.BITES;
/*     */   
/*  41 */   public static final int FULL_CAKE_SIGNAL = getOutputSignal(0);
/*     */   
/*     */   protected static final float AABB_OFFSET = 1.0F;
/*     */   protected static final float AABB_SIZE_PER_BITE = 2.0F;
/*  45 */   protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[] {
/*  46 */       Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), 
/*  47 */       Block.box(3.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), 
/*  48 */       Block.box(5.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), 
/*  49 */       Block.box(7.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), 
/*  50 */       Block.box(9.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), 
/*  51 */       Block.box(11.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), 
/*  52 */       Block.box(13.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)
/*     */     };
/*     */   
/*     */   protected CakeBlock(BlockBehaviour.Properties $$0) {
/*  56 */     super($$0);
/*  57 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)BITES, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  62 */     return SHAPE_BY_BITE[((Integer)$$0.getValue((Property)BITES)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  67 */     ItemStack $$6 = $$3.getItemInHand($$4);
/*  68 */     Item $$7 = $$6.getItem();
/*     */     
/*  70 */     if ($$6.is(ItemTags.CANDLES) && ((Integer)$$0.getValue((Property)BITES)).intValue() == 0) {
/*  71 */       Block $$8 = Block.byItem($$7);
/*  72 */       if ($$8 instanceof CandleBlock) {
/*  73 */         if (!$$3.isCreative()) {
/*  74 */           $$6.shrink(1);
/*     */         }
/*  76 */         $$1.playSound(null, $$2, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
/*  77 */         $$1.setBlockAndUpdate($$2, CandleCakeBlock.byCandle($$8));
/*  78 */         $$1.gameEvent((Entity)$$3, GameEvent.BLOCK_CHANGE, $$2);
/*  79 */         $$3.awardStat(Stats.ITEM_USED.get($$7));
/*  80 */         return InteractionResult.SUCCESS;
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     if ($$1.isClientSide) {
/*  85 */       if (eat((LevelAccessor)$$1, $$2, $$0, $$3).consumesAction())
/*  86 */         return InteractionResult.SUCCESS; 
/*  87 */       if ($$6.isEmpty()) {
/*  88 */         return InteractionResult.CONSUME;
/*     */       }
/*     */     } 
/*     */     
/*  92 */     return eat((LevelAccessor)$$1, $$2, $$0, $$3);
/*     */   }
/*     */   
/*     */   protected static InteractionResult eat(LevelAccessor $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/*  96 */     if (!$$3.canEat(false)) {
/*  97 */       return InteractionResult.PASS;
/*     */     }
/*  99 */     $$3.awardStat(Stats.EAT_CAKE_SLICE);
/*     */     
/* 101 */     $$3.getFoodData().eat(2, 0.1F);
/* 102 */     int $$4 = ((Integer)$$2.getValue((Property)BITES)).intValue();
/*     */     
/* 104 */     $$0.gameEvent((Entity)$$3, GameEvent.EAT, $$1);
/*     */     
/* 106 */     if ($$4 < 6) {
/* 107 */       $$0.setBlock($$1, (BlockState)$$2.setValue((Property)BITES, Integer.valueOf($$4 + 1)), 3);
/*     */     } else {
/* 109 */       $$0.removeBlock($$1, false);
/* 110 */       $$0.gameEvent((Entity)$$3, GameEvent.BLOCK_DESTROY, $$1);
/*     */     } 
/*     */     
/* 113 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 118 */     if ($$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 119 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 122 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 127 */     return $$1.getBlockState($$2.below()).isSolid();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 132 */     $$0.add(new Property[] { (Property)BITES });
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 137 */     return getOutputSignal(((Integer)$$0.getValue((Property)BITES)).intValue());
/*     */   }
/*     */   
/*     */   public static int getOutputSignal(int $$0) {
/* 141 */     return (7 - $$0) * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 151 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CakeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */