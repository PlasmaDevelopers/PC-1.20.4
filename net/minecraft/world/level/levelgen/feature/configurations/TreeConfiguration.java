/*     */ package net.minecraft.world.level.levelgen.feature.configurations;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function10;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
/*     */ 
/*     */ public class TreeConfiguration implements FeatureConfiguration {
/*     */   static {
/*  18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter(()), (App)TrunkPlacer.CODEC.fieldOf("trunk_placer").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("foliage_provider").forGetter(()), (App)FoliagePlacer.CODEC.fieldOf("foliage_placer").forGetter(()), (App)RootPlacer.CODEC.optionalFieldOf("root_placer").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("dirt_provider").forGetter(()), (App)FeatureSize.CODEC.fieldOf("minimum_size").forGetter(()), (App)TreeDecorator.CODEC.listOf().fieldOf("decorators").forGetter(()), (App)Codec.BOOL.fieldOf("ignore_vines").orElse(Boolean.valueOf(false)).forGetter(()), (App)Codec.BOOL.fieldOf("force_dirt").orElse(Boolean.valueOf(false)).forGetter(())).apply((Applicative)$$0, TreeConfiguration::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<TreeConfiguration> CODEC;
/*     */   
/*     */   public final BlockStateProvider trunkProvider;
/*     */   
/*     */   public final BlockStateProvider dirtProvider;
/*     */   
/*     */   public final TrunkPlacer trunkPlacer;
/*     */   
/*     */   public final BlockStateProvider foliageProvider;
/*     */   
/*     */   public final FoliagePlacer foliagePlacer;
/*     */   
/*     */   public final Optional<RootPlacer> rootPlacer;
/*     */   
/*     */   public final FeatureSize minimumSize;
/*     */   
/*     */   public final List<TreeDecorator> decorators;
/*     */   public final boolean ignoreVines;
/*     */   public final boolean forceDirt;
/*     */   
/*     */   protected TreeConfiguration(BlockStateProvider $$0, TrunkPlacer $$1, BlockStateProvider $$2, FoliagePlacer $$3, Optional<RootPlacer> $$4, BlockStateProvider $$5, FeatureSize $$6, List<TreeDecorator> $$7, boolean $$8, boolean $$9) {
/*  43 */     this.trunkProvider = $$0;
/*  44 */     this.trunkPlacer = $$1;
/*  45 */     this.foliageProvider = $$2;
/*  46 */     this.foliagePlacer = $$3;
/*  47 */     this.rootPlacer = $$4;
/*  48 */     this.dirtProvider = $$5;
/*  49 */     this.minimumSize = $$6;
/*  50 */     this.decorators = $$7;
/*  51 */     this.ignoreVines = $$8;
/*  52 */     this.forceDirt = $$9;
/*     */   }
/*     */   
/*     */   public static class TreeConfigurationBuilder {
/*     */     public final BlockStateProvider trunkProvider;
/*     */     private final TrunkPlacer trunkPlacer;
/*     */     public final BlockStateProvider foliageProvider;
/*     */     private final FoliagePlacer foliagePlacer;
/*     */     private final Optional<RootPlacer> rootPlacer;
/*     */     private BlockStateProvider dirtProvider;
/*     */     private final FeatureSize minimumSize;
/*  63 */     private List<TreeDecorator> decorators = (List<TreeDecorator>)ImmutableList.of();
/*     */     private boolean ignoreVines;
/*     */     private boolean forceDirt;
/*     */     
/*     */     public TreeConfigurationBuilder(BlockStateProvider $$0, TrunkPlacer $$1, BlockStateProvider $$2, FoliagePlacer $$3, Optional<RootPlacer> $$4, FeatureSize $$5) {
/*  68 */       this.trunkProvider = $$0;
/*  69 */       this.trunkPlacer = $$1;
/*  70 */       this.foliageProvider = $$2;
/*  71 */       this.dirtProvider = (BlockStateProvider)BlockStateProvider.simple(Blocks.DIRT);
/*  72 */       this.foliagePlacer = $$3;
/*  73 */       this.rootPlacer = $$4;
/*  74 */       this.minimumSize = $$5;
/*     */     }
/*     */     
/*     */     public TreeConfigurationBuilder(BlockStateProvider $$0, TrunkPlacer $$1, BlockStateProvider $$2, FoliagePlacer $$3, FeatureSize $$4) {
/*  78 */       this($$0, $$1, $$2, $$3, Optional.empty(), $$4);
/*     */     }
/*     */     
/*     */     public TreeConfigurationBuilder dirt(BlockStateProvider $$0) {
/*  82 */       this.dirtProvider = $$0;
/*  83 */       return this;
/*     */     }
/*     */     
/*     */     public TreeConfigurationBuilder decorators(List<TreeDecorator> $$0) {
/*  87 */       this.decorators = $$0;
/*  88 */       return this;
/*     */     }
/*     */     
/*     */     public TreeConfigurationBuilder ignoreVines() {
/*  92 */       this.ignoreVines = true;
/*  93 */       return this;
/*     */     }
/*     */     
/*     */     public TreeConfigurationBuilder forceDirt() {
/*  97 */       this.forceDirt = true;
/*  98 */       return this;
/*     */     }
/*     */     
/*     */     public TreeConfiguration build() {
/* 102 */       return new TreeConfiguration(this.trunkProvider, this.trunkPlacer, this.foliageProvider, this.foliagePlacer, this.rootPlacer, this.dirtProvider, this.minimumSize, this.decorators, this.ignoreVines, this.forceDirt);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\TreeConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */