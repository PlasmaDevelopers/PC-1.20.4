/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class DaylightDetectorBlock extends BaseEntityBlock {
/*  32 */   public static final MapCodec<DaylightDetectorBlock> CODEC = simpleCodec(DaylightDetectorBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<DaylightDetectorBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */   
/*  39 */   public static final IntegerProperty POWER = BlockStateProperties.POWER;
/*  40 */   public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;
/*     */   
/*  42 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
/*     */   
/*     */   public DaylightDetectorBlock(BlockBehaviour.Properties $$0) {
/*  45 */     super($$0);
/*     */     
/*  47 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)POWER, Integer.valueOf(0))).setValue((Property)INVERTED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  52 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  62 */     return ((Integer)$$0.getValue((Property)POWER)).intValue();
/*     */   }
/*     */   
/*     */   private static void updateSignalStrength(BlockState $$0, Level $$1, BlockPos $$2) {
/*  66 */     int $$3 = $$1.getBrightness(LightLayer.SKY, $$2) - $$1.getSkyDarken();
/*  67 */     float $$4 = $$1.getSunAngle(1.0F);
/*     */     
/*  69 */     boolean $$5 = ((Boolean)$$0.getValue((Property)INVERTED)).booleanValue();
/*  70 */     if ($$5) {
/*  71 */       $$3 = 15 - $$3;
/*  72 */     } else if ($$3 > 0) {
/*     */       
/*  74 */       float $$6 = ($$4 < 3.1415927F) ? 0.0F : 6.2831855F;
/*  75 */       $$4 += ($$6 - $$4) * 0.2F;
/*     */       
/*  77 */       $$3 = Math.round($$3 * Mth.cos($$4));
/*     */     } 
/*  79 */     $$3 = Mth.clamp($$3, 0, 15);
/*     */     
/*  81 */     if (((Integer)$$0.getValue((Property)POWER)).intValue() != $$3) {
/*  82 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWER, Integer.valueOf($$3)), 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  88 */     if ($$3.mayBuild()) {
/*  89 */       if ($$1.isClientSide) {
/*  90 */         return InteractionResult.SUCCESS;
/*     */       }
/*     */       
/*  93 */       BlockState $$6 = (BlockState)$$0.cycle((Property)INVERTED);
/*  94 */       $$1.setBlock($$2, $$6, 2);
/*  95 */       $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of((Entity)$$3, $$6));
/*  96 */       updateSignalStrength($$6, $$1, $$2);
/*     */       
/*  98 */       return InteractionResult.CONSUME;
/*     */     } 
/* 100 */     return super.use($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 105 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 115 */     return (BlockEntity)new DaylightDetectorBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 121 */     if (!$$0.isClientSide && $$0.dimensionType().hasSkyLight()) {
/* 122 */       return createTickerHelper($$2, BlockEntityType.DAYLIGHT_DETECTOR, DaylightDetectorBlock::tickEntity);
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   private static void tickEntity(Level $$0, BlockPos $$1, BlockState $$2, DaylightDetectorBlockEntity $$3) {
/* 128 */     if ($$0.getGameTime() % 20L == 0L) {
/* 129 */       updateSignalStrength($$2, $$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 135 */     $$0.add(new Property[] { (Property)POWER, (Property)INVERTED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DaylightDetectorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */