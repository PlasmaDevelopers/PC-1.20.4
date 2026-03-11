/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.cauldron.CauldronInteraction;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ 
/*     */ public class LayeredCauldronBlock extends AbstractCauldronBlock {
/*     */   static {
/*  20 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(()), (App)CauldronInteraction.CODEC.fieldOf("interactions").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, LayeredCauldronBlock::new));
/*     */   }
/*     */   
/*     */   public static final MapCodec<LayeredCauldronBlock> CODEC;
/*     */   public static final int MIN_FILL_LEVEL = 1;
/*     */   public static final int MAX_FILL_LEVEL = 3;
/*     */   
/*     */   public MapCodec<LayeredCauldronBlock> codec() {
/*  28 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;
/*     */   
/*     */   private static final int BASE_CONTENT_HEIGHT = 6;
/*     */   
/*     */   private static final double HEIGHT_PER_LEVEL = 3.0D;
/*     */   private final Biome.Precipitation precipitationType;
/*     */   
/*     */   public LayeredCauldronBlock(Biome.Precipitation $$0, CauldronInteraction.InteractionMap $$1, BlockBehaviour.Properties $$2) {
/*  41 */     super($$2, $$1);
/*  42 */     this.precipitationType = $$0;
/*  43 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)LEVEL, Integer.valueOf(1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFull(BlockState $$0) {
/*  48 */     return (((Integer)$$0.getValue((Property)LEVEL)).intValue() == 3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canReceiveStalactiteDrip(Fluid $$0) {
/*  53 */     return ($$0 == Fluids.WATER && this.precipitationType == Biome.Precipitation.RAIN);
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getContentHeight(BlockState $$0) {
/*  58 */     return (6.0D + ((Integer)$$0.getValue((Property)LEVEL)).intValue() * 3.0D) / 16.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/*  63 */     if (!$$1.isClientSide && $$3.isOnFire() && isEntityInsideContent($$0, $$2, $$3)) {
/*  64 */       $$3.clearFire();
/*  65 */       if ($$3.mayInteract($$1, $$2)) {
/*  66 */         handleEntityOnFireInside($$0, $$1, $$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEntityOnFireInside(BlockState $$0, Level $$1, BlockPos $$2) {
/*  72 */     if (this.precipitationType == Biome.Precipitation.SNOW) {
/*  73 */       lowerFillLevel((BlockState)Blocks.WATER_CAULDRON.defaultBlockState().setValue((Property)LEVEL, $$0.getValue((Property)LEVEL)), $$1, $$2);
/*     */     } else {
/*  75 */       lowerFillLevel($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void lowerFillLevel(BlockState $$0, Level $$1, BlockPos $$2) {
/*  80 */     int $$3 = ((Integer)$$0.getValue((Property)LEVEL)).intValue() - 1;
/*  81 */     BlockState $$4 = ($$3 == 0) ? Blocks.CAULDRON.defaultBlockState() : (BlockState)$$0.setValue((Property)LEVEL, Integer.valueOf($$3));
/*  82 */     $$1.setBlockAndUpdate($$2, $$4);
/*  83 */     $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlePrecipitation(BlockState $$0, Level $$1, BlockPos $$2, Biome.Precipitation $$3) {
/*  88 */     if (!CauldronBlock.shouldHandlePrecipitation($$1, $$3) || ((Integer)$$0.getValue((Property)LEVEL)).intValue() == 3 || $$3 != this.precipitationType) {
/*     */       return;
/*     */     }
/*     */     
/*  92 */     BlockState $$4 = (BlockState)$$0.cycle((Property)LEVEL);
/*  93 */     $$1.setBlockAndUpdate($$2, $$4);
/*  94 */     $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/*  99 */     return ((Integer)$$0.getValue((Property)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 104 */     $$0.add(new Property[] { (Property)LEVEL });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void receiveStalactiteDrip(BlockState $$0, Level $$1, BlockPos $$2, Fluid $$3) {
/* 109 */     if (isFull($$0)) {
/*     */       return;
/*     */     }
/* 112 */     BlockState $$4 = (BlockState)$$0.setValue((Property)LEVEL, Integer.valueOf(((Integer)$$0.getValue((Property)LEVEL)).intValue() + 1));
/* 113 */     $$1.setBlockAndUpdate($$2, $$4);
/* 114 */     $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$4));
/* 115 */     $$1.levelEvent(1047, $$2, 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LayeredCauldronBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */