/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class FrostedIceBlock extends IceBlock {
/*  19 */   public static final MapCodec<FrostedIceBlock> CODEC = simpleCodec(FrostedIceBlock::new);
/*     */   public static final int MAX_AGE = 3;
/*     */   
/*     */   public MapCodec<FrostedIceBlock> codec() {
/*  23 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  27 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
/*     */   
/*     */   private static final int NEIGHBORS_TO_AGE = 4;
/*     */   private static final int NEIGHBORS_TO_MELT = 2;
/*     */   
/*     */   public FrostedIceBlock(BlockBehaviour.Properties $$0) {
/*  33 */     super($$0);
/*  34 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  40 */     tick($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  45 */     if (($$3.nextInt(3) == 0 || fewerNeigboursThan((BlockGetter)$$1, $$2, 4)) && $$1.getMaxLocalRawBrightness($$2) > 11 - ((Integer)$$0.getValue((Property)AGE)).intValue() - $$0.getLightBlock((BlockGetter)$$1, $$2) && 
/*  46 */       slightlyMelt($$0, (Level)$$1, $$2)) {
/*  47 */       BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*  48 */       for (Direction $$5 : Direction.values()) {
/*  49 */         $$4.setWithOffset((Vec3i)$$2, $$5);
/*  50 */         BlockState $$6 = $$1.getBlockState((BlockPos)$$4);
/*  51 */         if ($$6.is(this) && !slightlyMelt($$6, (Level)$$1, (BlockPos)$$4)) {
/*  52 */           $$1.scheduleTick((BlockPos)$$4, this, Mth.nextInt($$3, 20, 40));
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  58 */     $$1.scheduleTick($$2, this, Mth.nextInt($$3, 20, 40));
/*     */   }
/*     */   
/*     */   private boolean slightlyMelt(BlockState $$0, Level $$1, BlockPos $$2) {
/*  62 */     int $$3 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/*  63 */     if ($$3 < 3) {
/*  64 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)AGE, Integer.valueOf($$3 + 1)), 2);
/*  65 */       return false;
/*     */     } 
/*  67 */     melt($$0, $$1, $$2);
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  74 */     if ($$3.defaultBlockState().is(this) && 
/*  75 */       fewerNeigboursThan((BlockGetter)$$1, $$2, 2)) {
/*  76 */       melt($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*  80 */     super.neighborChanged($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private boolean fewerNeigboursThan(BlockGetter $$0, BlockPos $$1, int $$2) {
/*  84 */     int $$3 = 0;
/*  85 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*  86 */     for (Direction $$5 : Direction.values()) {
/*  87 */       $$4.setWithOffset((Vec3i)$$1, $$5);
/*     */       
/*  89 */       $$3++;
/*  90 */       if ($$0.getBlockState((BlockPos)$$4).is(this) && $$3 >= $$2) {
/*  91 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 100 */     $$0.add(new Property[] { (Property)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 105 */     return ItemStack.EMPTY;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FrostedIceBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */