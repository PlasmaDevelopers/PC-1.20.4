/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class MushroomBlock extends BushBlock implements BonemealableBlock {
/*     */   static {
/*  23 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, MushroomBlock::new));
/*     */   }
/*     */   
/*     */   public static final MapCodec<MushroomBlock> CODEC;
/*     */   protected static final float AABB_OFFSET = 3.0F;
/*     */   
/*     */   public MapCodec<MushroomBlock> codec() {
/*  30 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  34 */   protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
/*     */   private final ResourceKey<ConfiguredFeature<?, ?>> feature;
/*     */   
/*     */   public MushroomBlock(ResourceKey<ConfiguredFeature<?, ?>> $$0, BlockBehaviour.Properties $$1) {
/*  38 */     super($$1);
/*  39 */     this.feature = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  44 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  49 */     if ($$3.nextInt(25) == 0) {
/*  50 */       int $$4 = 5;
/*  51 */       int $$5 = 4;
/*  52 */       for (BlockPos $$6 : BlockPos.betweenClosed($$2.offset(-4, -1, -4), $$2.offset(4, 1, 4))) {
/*  53 */         if ($$1.getBlockState($$6).is(this) && --$$4 <= 0) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  59 */       BlockPos $$7 = $$2.offset($$3.nextInt(3) - 1, $$3.nextInt(2) - $$3.nextInt(2), $$3.nextInt(3) - 1);
/*  60 */       for (int $$8 = 0; $$8 < 4; $$8++) {
/*  61 */         if ($$1.isEmptyBlock($$7) && $$0.canSurvive((LevelReader)$$1, $$7)) {
/*  62 */           $$2 = $$7;
/*     */         }
/*  64 */         $$7 = $$2.offset($$3.nextInt(3) - 1, $$3.nextInt(2) - $$3.nextInt(2), $$3.nextInt(3) - 1);
/*     */       } 
/*     */       
/*  67 */       if ($$1.isEmptyBlock($$7) && $$0.canSurvive((LevelReader)$$1, $$7)) {
/*  68 */         $$1.setBlock($$7, $$0, 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  75 */     return $$0.isSolidRender($$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  80 */     BlockPos $$3 = $$2.below();
/*  81 */     BlockState $$4 = $$1.getBlockState($$3);
/*  82 */     if ($$4.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
/*  83 */       return true;
/*     */     }
/*     */     
/*  86 */     return ($$1.getRawBrightness($$2, 0) < 13 && mayPlaceOn($$4, (BlockGetter)$$1, $$3));
/*     */   }
/*     */   
/*     */   public boolean growMushroom(ServerLevel $$0, BlockPos $$1, BlockState $$2, RandomSource $$3) {
/*  90 */     Optional<? extends Holder<ConfiguredFeature<?, ?>>> $$4 = $$0.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.feature);
/*  91 */     if ($$4.isEmpty()) {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     $$0.removeBlock($$1, false);
/*     */     
/*  97 */     if (((ConfiguredFeature)((Holder)$$4.get()).value()).place((WorldGenLevel)$$0, $$0.getChunkSource().getGenerator(), $$3, $$1)) {
/*  98 */       return true;
/*     */     }
/*     */     
/* 101 */     $$0.setBlock($$1, $$2, 3);
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 112 */     return ($$1.nextFloat() < 0.4D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 117 */     growMushroom($$0, $$2, $$3, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MushroomBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */