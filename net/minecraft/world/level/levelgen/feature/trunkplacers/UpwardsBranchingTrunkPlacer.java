/*     */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function7;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*     */ 
/*     */ public class UpwardsBranchingTrunkPlacer extends TrunkPlacer {
/*     */   static {
/*  23 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).and($$0.group((App)IntProvider.POSITIVE_CODEC.fieldOf("extra_branch_steps").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_per_log_probability").forGetter(()), (App)IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter(()), (App)RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_grow_through").forGetter(()))).apply((Applicative)$$0, UpwardsBranchingTrunkPlacer::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<UpwardsBranchingTrunkPlacer> CODEC;
/*     */   
/*     */   private final IntProvider extraBranchSteps;
/*     */   
/*     */   private final float placeBranchPerLogProbability;
/*     */   
/*     */   private final IntProvider extraBranchLength;
/*     */   
/*     */   private final HolderSet<Block> canGrowThrough;
/*     */   
/*     */   public UpwardsBranchingTrunkPlacer(int $$0, int $$1, int $$2, IntProvider $$3, float $$4, IntProvider $$5, HolderSet<Block> $$6) {
/*  38 */     super($$0, $$1, $$2);
/*  39 */     this.extraBranchSteps = $$3;
/*  40 */     this.placeBranchPerLogProbability = $$4;
/*  41 */     this.extraBranchLength = $$5;
/*  42 */     this.canGrowThrough = $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TrunkPlacerType<?> type() {
/*  47 */     return TrunkPlacerType.UPWARDS_BRANCHING_TRUNK_PLACER;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/*  52 */     List<FoliagePlacer.FoliageAttachment> $$6 = Lists.newArrayList();
/*     */     
/*  54 */     BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
/*  55 */     for (int $$8 = 0; $$8 < $$3; $$8++) {
/*  56 */       int $$9 = $$4.getY() + $$8;
/*  57 */       if (placeLog($$0, $$1, $$2, (BlockPos)$$7.set($$4.getX(), $$9, $$4.getZ()), $$5) && 
/*  58 */         $$8 < $$3 - 1 && $$2.nextFloat() < this.placeBranchPerLogProbability) {
/*  59 */         Direction $$10 = Direction.Plane.HORIZONTAL.getRandomDirection($$2);
/*  60 */         int $$11 = this.extraBranchLength.sample($$2);
/*  61 */         int $$12 = Math.max(0, $$11 - this.extraBranchLength.sample($$2) - 1);
/*  62 */         int $$13 = this.extraBranchSteps.sample($$2);
/*  63 */         placeBranch($$0, $$1, $$2, $$3, $$5, $$6, $$7, $$9, $$10, $$12, $$13);
/*     */       } 
/*     */ 
/*     */       
/*  67 */       if ($$8 == $$3 - 1) {
/*  68 */         $$6.add(new FoliagePlacer.FoliageAttachment((BlockPos)$$7.set($$4.getX(), $$9 + 1, $$4.getZ()), 0, false));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  73 */     return $$6;
/*     */   }
/*     */   
/*     */   private void placeBranch(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, TreeConfiguration $$4, List<FoliagePlacer.FoliageAttachment> $$5, BlockPos.MutableBlockPos $$6, int $$7, Direction $$8, int $$9, int $$10) {
/*  77 */     int $$11 = $$7 + $$9;
/*  78 */     int $$12 = $$6.getX();
/*  79 */     int $$13 = $$6.getZ();
/*  80 */     for (int $$14 = $$9; $$14 < $$3 && $$10 > 0; $$14++, $$10--) {
/*  81 */       if ($$14 >= 1) {
/*     */ 
/*     */         
/*  84 */         int $$15 = $$7 + $$14;
/*  85 */         $$12 += $$8.getStepX();
/*  86 */         $$13 += $$8.getStepZ();
/*  87 */         $$11 = $$15;
/*     */         
/*  89 */         if (placeLog($$0, $$1, $$2, (BlockPos)$$6.set($$12, $$15, $$13), $$4)) {
/*  90 */           $$11++;
/*     */         }
/*     */         
/*  93 */         $$5.add(new FoliagePlacer.FoliageAttachment($$6.immutable(), 0, false));
/*     */       } 
/*  95 */     }  if ($$11 - $$7 > 1) {
/*  96 */       BlockPos $$16 = new BlockPos($$12, $$11, $$13);
/*  97 */       $$5.add(new FoliagePlacer.FoliageAttachment($$16, 0, false));
/*  98 */       $$5.add(new FoliagePlacer.FoliageAttachment($$16.below(2), 0, false));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean validTreePos(LevelSimulatedReader $$0, BlockPos $$1) {
/* 104 */     return (super.validTreePos($$0, $$1) || $$0.isStateAtPosition($$1, $$0 -> $$0.is(this.canGrowThrough)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\UpwardsBranchingTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */