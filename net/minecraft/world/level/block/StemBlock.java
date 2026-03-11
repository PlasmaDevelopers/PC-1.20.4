/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class StemBlock extends BushBlock implements BonemealableBlock {
/*     */   static {
/*  30 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceKey.codec(Registries.BLOCK).fieldOf("fruit").forGetter(()), (App)ResourceKey.codec(Registries.BLOCK).fieldOf("attached_stem").forGetter(()), (App)ResourceKey.codec(Registries.ITEM).fieldOf("seed").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, StemBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<StemBlock> CODEC;
/*     */   
/*     */   public static final int MAX_AGE = 7;
/*     */   
/*     */   public MapCodec<StemBlock> codec() {
/*  39 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  43 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
/*     */   
/*     */   protected static final float AABB_OFFSET = 1.0F;
/*  46 */   protected static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
/*  47 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 2.0D, 9.0D), 
/*  48 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 4.0D, 9.0D), 
/*  49 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 6.0D, 9.0D), 
/*  50 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 8.0D, 9.0D), 
/*  51 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 10.0D, 9.0D), 
/*  52 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 12.0D, 9.0D), 
/*  53 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D), 
/*  54 */       Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D)
/*     */     };
/*     */   
/*     */   private final ResourceKey<Block> fruit;
/*     */   
/*     */   private final ResourceKey<Block> attachedStem;
/*     */   private final ResourceKey<Item> seed;
/*     */   
/*     */   protected StemBlock(ResourceKey<Block> $$0, ResourceKey<Block> $$1, ResourceKey<Item> $$2, BlockBehaviour.Properties $$3) {
/*  63 */     super($$3);
/*  64 */     this.fruit = $$0;
/*  65 */     this.attachedStem = $$1;
/*  66 */     this.seed = $$2;
/*  67 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  72 */     return SHAPE_BY_AGE[((Integer)$$0.getValue((Property)AGE)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  77 */     return $$0.is(Blocks.FARMLAND);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  82 */     if ($$1.getRawBrightness($$2, 0) < 9) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     float $$4 = CropBlock.getGrowthSpeed(this, (BlockGetter)$$1, $$2);
/*  87 */     if ($$3.nextInt((int)(25.0F / $$4) + 1) == 0) {
/*  88 */       int $$5 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/*  89 */       if ($$5 < 7) {
/*  90 */         $$0 = (BlockState)$$0.setValue((Property)AGE, Integer.valueOf($$5 + 1));
/*  91 */         $$1.setBlock($$2, $$0, 2);
/*     */       } else {
/*  93 */         Direction $$6 = Direction.Plane.HORIZONTAL.getRandomDirection($$3);
/*  94 */         BlockPos $$7 = $$2.relative($$6);
/*     */         
/*  96 */         BlockState $$8 = $$1.getBlockState($$7.below());
/*  97 */         if ($$1.getBlockState($$7).isAir() && ($$8.is(Blocks.FARMLAND) || $$8.is(BlockTags.DIRT))) {
/*  98 */           Registry<Block> $$9 = $$1.registryAccess().registryOrThrow(Registries.BLOCK);
/*  99 */           Optional<Block> $$10 = $$9.getOptional(this.fruit);
/* 100 */           Optional<Block> $$11 = $$9.getOptional(this.attachedStem);
/* 101 */           if ($$10.isPresent() && $$11.isPresent()) {
/* 102 */             $$1.setBlockAndUpdate($$7, ((Block)$$10.get()).defaultBlockState());
/* 103 */             $$1.setBlockAndUpdate($$2, (BlockState)((Block)$$11.get()).defaultBlockState().setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)$$6));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 112 */     return new ItemStack((ItemLike)DataFixUtils.orElse($$0.registryAccess().registryOrThrow(Registries.ITEM).getOptional(this.seed), this));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 117 */     return (((Integer)$$2.getValue((Property)AGE)).intValue() != 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 127 */     int $$4 = Math.min(7, ((Integer)$$3.getValue((Property)AGE)).intValue() + Mth.nextInt($$0.random, 2, 5));
/* 128 */     BlockState $$5 = (BlockState)$$3.setValue((Property)AGE, Integer.valueOf($$4));
/* 129 */     $$0.setBlock($$2, $$5, 2);
/* 130 */     if ($$4 == 7) {
/* 131 */       $$5.randomTick($$0, $$2, $$0.random);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 137 */     $$0.add(new Property[] { (Property)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\StemBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */