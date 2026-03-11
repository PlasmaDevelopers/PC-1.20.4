/*     */ package net.minecraft.world.level.levelgen.feature.configurations;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TreeConfigurationBuilder
/*     */ {
/*     */   public final BlockStateProvider trunkProvider;
/*     */   private final TrunkPlacer trunkPlacer;
/*     */   public final BlockStateProvider foliageProvider;
/*     */   private final FoliagePlacer foliagePlacer;
/*     */   private final Optional<RootPlacer> rootPlacer;
/*     */   private BlockStateProvider dirtProvider;
/*     */   private final FeatureSize minimumSize;
/*  63 */   private List<TreeDecorator> decorators = (List<TreeDecorator>)ImmutableList.of();
/*     */   private boolean ignoreVines;
/*     */   private boolean forceDirt;
/*     */   
/*     */   public TreeConfigurationBuilder(BlockStateProvider $$0, TrunkPlacer $$1, BlockStateProvider $$2, FoliagePlacer $$3, Optional<RootPlacer> $$4, FeatureSize $$5) {
/*  68 */     this.trunkProvider = $$0;
/*  69 */     this.trunkPlacer = $$1;
/*  70 */     this.foliageProvider = $$2;
/*  71 */     this.dirtProvider = (BlockStateProvider)BlockStateProvider.simple(Blocks.DIRT);
/*  72 */     this.foliagePlacer = $$3;
/*  73 */     this.rootPlacer = $$4;
/*  74 */     this.minimumSize = $$5;
/*     */   }
/*     */   
/*     */   public TreeConfigurationBuilder(BlockStateProvider $$0, TrunkPlacer $$1, BlockStateProvider $$2, FoliagePlacer $$3, FeatureSize $$4) {
/*  78 */     this($$0, $$1, $$2, $$3, Optional.empty(), $$4);
/*     */   }
/*     */   
/*     */   public TreeConfigurationBuilder dirt(BlockStateProvider $$0) {
/*  82 */     this.dirtProvider = $$0;
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   public TreeConfigurationBuilder decorators(List<TreeDecorator> $$0) {
/*  87 */     this.decorators = $$0;
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public TreeConfigurationBuilder ignoreVines() {
/*  92 */     this.ignoreVines = true;
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public TreeConfigurationBuilder forceDirt() {
/*  97 */     this.forceDirt = true;
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public TreeConfiguration build() {
/* 102 */     return new TreeConfiguration(this.trunkProvider, this.trunkPlacer, this.foliageProvider, this.foliagePlacer, this.rootPlacer, this.dirtProvider, this.minimumSize, this.decorators, this.ignoreVines, this.forceDirt);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\TreeConfiguration$TreeConfigurationBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */