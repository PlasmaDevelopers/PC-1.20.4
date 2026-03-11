/*     */ package net.minecraft.world.level.block.grower;
/*     */ import com.mojang.serialization.Codec;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.features.TreeFeatures;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ 
/*     */ public final class TreeGrower {
/*  27 */   private static final Map<String, TreeGrower> GROWERS = (Map<String, TreeGrower>)new Object2ObjectArrayMap(); static {
/*  28 */     Objects.requireNonNull(GROWERS); CODEC = ExtraCodecs.stringResolverCodec($$0 -> $$0.name, GROWERS::get);
/*     */   }
/*  30 */   public static final Codec<TreeGrower> CODEC; public static final TreeGrower OAK = new TreeGrower("oak", 0.1F, Optional.empty(), Optional.empty(), Optional.of(TreeFeatures.OAK), Optional.of(TreeFeatures.FANCY_OAK), Optional.of(TreeFeatures.OAK_BEES_005), Optional.of(TreeFeatures.FANCY_OAK_BEES_005));
/*  31 */   public static final TreeGrower SPRUCE = new TreeGrower("spruce", 0.5F, Optional.of(TreeFeatures.MEGA_SPRUCE), Optional.of(TreeFeatures.MEGA_PINE), Optional.of(TreeFeatures.SPRUCE), Optional.empty(), Optional.empty(), Optional.empty());
/*  32 */   public static final TreeGrower MANGROVE = new TreeGrower("mangrove", 0.85F, Optional.empty(), Optional.empty(), Optional.of(TreeFeatures.MANGROVE), Optional.of(TreeFeatures.TALL_MANGROVE), Optional.empty(), Optional.empty());
/*     */   
/*  34 */   public static final TreeGrower AZALEA = new TreeGrower("azalea", Optional.empty(), Optional.of(TreeFeatures.AZALEA_TREE), Optional.empty());
/*  35 */   public static final TreeGrower BIRCH = new TreeGrower("birch", Optional.empty(), Optional.of(TreeFeatures.BIRCH), Optional.of(TreeFeatures.BIRCH_BEES_005));
/*  36 */   public static final TreeGrower JUNGLE = new TreeGrower("jungle", Optional.of(TreeFeatures.MEGA_JUNGLE_TREE), Optional.of(TreeFeatures.JUNGLE_TREE_NO_VINE), Optional.empty());
/*  37 */   public static final TreeGrower ACACIA = new TreeGrower("acacia", Optional.empty(), Optional.of(TreeFeatures.ACACIA), Optional.empty());
/*  38 */   public static final TreeGrower CHERRY = new TreeGrower("cherry", Optional.empty(), Optional.of(TreeFeatures.CHERRY), Optional.of(TreeFeatures.CHERRY_BEES_005));
/*  39 */   public static final TreeGrower DARK_OAK = new TreeGrower("dark_oak", Optional.of(TreeFeatures.DARK_OAK), Optional.empty(), Optional.empty());
/*     */   
/*     */   private final String name;
/*     */   private final float secondaryChance;
/*     */   private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> megaTree;
/*     */   private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryMegaTree;
/*     */   private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> tree;
/*     */   private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryTree;
/*     */   private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> flowers;
/*     */   private final Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryFlowers;
/*     */   
/*     */   public TreeGrower(String $$0, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$1, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$2, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$3) {
/*  51 */     this($$0, 0.0F, $$1, Optional.empty(), $$2, Optional.empty(), $$3, Optional.empty());
/*     */   }
/*     */   
/*     */   public TreeGrower(String $$0, float $$1, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$2, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$3, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$4, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$5, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$6, Optional<ResourceKey<ConfiguredFeature<?, ?>>> $$7) {
/*  55 */     this.name = $$0;
/*  56 */     this.secondaryChance = $$1;
/*  57 */     this.megaTree = $$2;
/*  58 */     this.secondaryMegaTree = $$3;
/*  59 */     this.tree = $$4;
/*  60 */     this.secondaryTree = $$5;
/*  61 */     this.flowers = $$6;
/*  62 */     this.secondaryFlowers = $$7;
/*     */     
/*  64 */     GROWERS.put($$0, this);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource $$0, boolean $$1) {
/*  69 */     if ($$0.nextFloat() < this.secondaryChance) {
/*  70 */       if ($$1 && this.secondaryFlowers.isPresent()) {
/*  71 */         return this.secondaryFlowers.get();
/*     */       }
/*  73 */       if (this.secondaryTree.isPresent()) {
/*  74 */         return this.secondaryTree.get();
/*     */       }
/*     */     } 
/*  77 */     if ($$1 && this.flowers.isPresent()) {
/*  78 */       return this.flowers.get();
/*     */     }
/*  80 */     return this.tree.orElse(null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource $$0) {
/*  85 */     if (this.secondaryMegaTree.isPresent() && $$0.nextFloat() < this.secondaryChance) {
/*  86 */       return this.secondaryMegaTree.get();
/*     */     }
/*  88 */     return this.megaTree.orElse(null);
/*     */   }
/*     */   
/*     */   public boolean growTree(ServerLevel $$0, ChunkGenerator $$1, BlockPos $$2, BlockState $$3, RandomSource $$4) {
/*  92 */     ResourceKey<ConfiguredFeature<?, ?>> $$5 = getConfiguredMegaFeature($$4);
/*  93 */     if ($$5 != null) {
/*  94 */       Holder<ConfiguredFeature<?, ?>> $$6 = $$0.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder($$5).orElse(null);
/*  95 */       if ($$6 != null) {
/*  96 */         for (int $$7 = 0; $$7 >= -1; $$7--) {
/*  97 */           for (int $$8 = 0; $$8 >= -1; $$8--) {
/*  98 */             if (isTwoByTwoSapling($$3, (BlockGetter)$$0, $$2, $$7, $$8)) {
/*  99 */               ConfiguredFeature<?, ?> $$9 = (ConfiguredFeature<?, ?>)$$6.value();
/*     */               
/* 101 */               BlockState $$10 = Blocks.AIR.defaultBlockState();
/* 102 */               $$0.setBlock($$2.offset($$7, 0, $$8), $$10, 4);
/* 103 */               $$0.setBlock($$2.offset($$7 + 1, 0, $$8), $$10, 4);
/* 104 */               $$0.setBlock($$2.offset($$7, 0, $$8 + 1), $$10, 4);
/* 105 */               $$0.setBlock($$2.offset($$7 + 1, 0, $$8 + 1), $$10, 4);
/*     */               
/* 107 */               if ($$9.place((WorldGenLevel)$$0, $$1, $$4, $$2.offset($$7, 0, $$8))) {
/* 108 */                 return true;
/*     */               }
/* 110 */               $$0.setBlock($$2.offset($$7, 0, $$8), $$3, 4);
/* 111 */               $$0.setBlock($$2.offset($$7 + 1, 0, $$8), $$3, 4);
/* 112 */               $$0.setBlock($$2.offset($$7, 0, $$8 + 1), $$3, 4);
/* 113 */               $$0.setBlock($$2.offset($$7 + 1, 0, $$8 + 1), $$3, 4);
/* 114 */               return false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 121 */     ResourceKey<ConfiguredFeature<?, ?>> $$11 = getConfiguredFeature($$4, hasFlowers((LevelAccessor)$$0, $$2));
/* 122 */     if ($$11 == null) {
/* 123 */       return false;
/*     */     }
/*     */     
/* 126 */     Holder<ConfiguredFeature<?, ?>> $$12 = $$0.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder($$11).orElse(null);
/* 127 */     if ($$12 == null) {
/* 128 */       return false;
/*     */     }
/*     */     
/* 131 */     ConfiguredFeature<?, ?> $$13 = (ConfiguredFeature<?, ?>)$$12.value();
/*     */     
/* 133 */     BlockState $$14 = $$0.getFluidState($$2).createLegacyBlock();
/* 134 */     $$0.setBlock($$2, $$14, 4);
/*     */     
/* 136 */     if ($$13.place((WorldGenLevel)$$0, $$1, $$4, $$2)) {
/* 137 */       if ($$0.getBlockState($$2) == $$14) {
/* 138 */         $$0.sendBlockUpdated($$2, $$3, $$14, 2);
/*     */       }
/* 140 */       return true;
/*     */     } 
/*     */     
/* 143 */     $$0.setBlock($$2, $$3, 4);
/* 144 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isTwoByTwoSapling(BlockState $$0, BlockGetter $$1, BlockPos $$2, int $$3, int $$4) {
/* 148 */     Block $$5 = $$0.getBlock();
/* 149 */     return ($$1.getBlockState($$2.offset($$3, 0, $$4)).is($$5) && $$1
/* 150 */       .getBlockState($$2.offset($$3 + 1, 0, $$4)).is($$5) && $$1
/* 151 */       .getBlockState($$2.offset($$3, 0, $$4 + 1)).is($$5) && $$1
/* 152 */       .getBlockState($$2.offset($$3 + 1, 0, $$4 + 1)).is($$5));
/*     */   }
/*     */   
/*     */   private boolean hasFlowers(LevelAccessor $$0, BlockPos $$1) {
/* 156 */     for (BlockPos $$2 : BlockPos.MutableBlockPos.betweenClosed($$1.below().north(2).west(2), $$1.above().south(2).east(2))) {
/* 157 */       if ($$0.getBlockState($$2).is(BlockTags.FLOWERS)) {
/* 158 */         return true;
/*     */       }
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\grower\TreeGrower.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */