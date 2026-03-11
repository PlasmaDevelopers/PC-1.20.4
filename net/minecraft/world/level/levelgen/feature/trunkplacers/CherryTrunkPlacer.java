/*     */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.util.Function7;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*     */ 
/*     */ public class CherryTrunkPlacer extends TrunkPlacer {
/*     */   static {
/*  24 */     BRANCH_START_CODEC = ExtraCodecs.validate(UniformInt.CODEC, $$0 -> ($$0.getMaxValue() - $$0.getMinValue() < 1) ? DataResult.error(()) : DataResult.success($$0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).and($$0.group((App)IntProvider.codec(1, 3).fieldOf("branch_count").forGetter(()), (App)IntProvider.codec(2, 16).fieldOf("branch_horizontal_length").forGetter(()), (App)IntProvider.codec(-16, 0, BRANCH_START_CODEC).fieldOf("branch_start_offset_from_top").forGetter(()), (App)IntProvider.codec(-16, 16).fieldOf("branch_end_offset_from_top").forGetter(()))).apply((Applicative)$$0, CherryTrunkPlacer::new));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final Codec<UniformInt> BRANCH_START_CODEC;
/*     */   
/*     */   public static final Codec<CherryTrunkPlacer> CODEC;
/*     */   
/*     */   private final IntProvider branchCount;
/*     */   private final IntProvider branchHorizontalLength;
/*     */   private final UniformInt branchStartOffsetFromTop;
/*     */   private final UniformInt secondBranchStartOffsetFromTop;
/*     */   private final IntProvider branchEndOffsetFromTop;
/*     */   
/*     */   public CherryTrunkPlacer(int $$0, int $$1, int $$2, IntProvider $$3, IntProvider $$4, UniformInt $$5, IntProvider $$6) {
/*  46 */     super($$0, $$1, $$2);
/*  47 */     this.branchCount = $$3;
/*  48 */     this.branchHorizontalLength = $$4;
/*  49 */     this.branchStartOffsetFromTop = $$5;
/*  50 */     this.secondBranchStartOffsetFromTop = UniformInt.of($$5.getMinValue(), $$5.getMaxValue() - 1);
/*  51 */     this.branchEndOffsetFromTop = $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TrunkPlacerType<?> type() {
/*  56 */     return TrunkPlacerType.CHERRY_TRUNK_PLACER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/*     */     int $$13;
/*  68 */     setDirtAt($$0, $$1, $$2, $$4.below(), $$5);
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
/*  79 */     int $$6 = Math.max(0, $$3 - 1 + this.branchStartOffsetFromTop.sample($$2));
/*  80 */     int $$7 = Math.max(0, $$3 - 1 + this.secondBranchStartOffsetFromTop.sample($$2));
/*  81 */     if ($$7 >= $$6) {
/*  82 */       $$7++;
/*     */     }
/*     */     
/*  85 */     int $$8 = this.branchCount.sample($$2);
/*  86 */     boolean $$9 = ($$8 == 3);
/*  87 */     boolean $$10 = ($$8 >= 2);
/*     */ 
/*     */     
/*  90 */     if ($$9) {
/*  91 */       int $$11 = $$3;
/*  92 */     } else if ($$10) {
/*  93 */       int $$12 = Math.max($$6, $$7) + 1;
/*     */     } else {
/*  95 */       $$13 = $$6 + 1;
/*     */     } 
/*     */     
/*  98 */     for (int $$14 = 0; $$14 < $$13; $$14++) {
/*  99 */       placeLog($$0, $$1, $$2, $$4.above($$14), $$5);
/*     */     }
/*     */     
/* 102 */     List<FoliagePlacer.FoliageAttachment> $$15 = new ArrayList<>();
/*     */     
/* 104 */     if ($$9) {
/* 105 */       $$15.add(new FoliagePlacer.FoliageAttachment($$4.above($$13), 0, false));
/*     */     }
/*     */     
/* 108 */     BlockPos.MutableBlockPos $$16 = new BlockPos.MutableBlockPos();
/* 109 */     Direction $$17 = Direction.Plane.HORIZONTAL.getRandomDirection($$2);
/* 110 */     Function<BlockState, BlockState> $$18 = $$1 -> (BlockState)$$1.trySetValue((Property)RotatedPillarBlock.AXIS, (Comparable)$$0.getAxis());
/*     */     
/* 112 */     $$15.add(generateBranch($$0, $$1, $$2, $$3, $$4, $$5, $$18, $$17, $$6, ($$6 < $$13 - 1), $$16));
/*     */     
/* 114 */     if ($$10) {
/* 115 */       $$15.add(generateBranch($$0, $$1, $$2, $$3, $$4, $$5, $$18, $$17.getOpposite(), $$7, ($$7 < $$13 - 1), $$16));
/*     */     }
/*     */     
/* 118 */     return $$15;
/*     */   }
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
/*     */   private FoliagePlacer.FoliageAttachment generateBranch(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5, Function<BlockState, BlockState> $$6, Direction $$7, int $$8, boolean $$9, BlockPos.MutableBlockPos $$10) {
/* 134 */     $$10.set((Vec3i)$$4).move(Direction.UP, $$8);
/*     */     
/* 136 */     int $$11 = $$3 - 1 + this.branchEndOffsetFromTop.sample($$2);
/*     */     
/* 138 */     boolean $$12 = ($$9 || $$11 < $$8);
/* 139 */     int $$13 = this.branchHorizontalLength.sample($$2) + ($$12 ? 1 : 0);
/*     */ 
/*     */     
/* 142 */     BlockPos $$14 = $$4.relative($$7, $$13).above($$11);
/*     */     
/* 144 */     int $$15 = $$12 ? 2 : 1;
/*     */     
/* 146 */     for (int $$16 = 0; $$16 < $$15; $$16++) {
/* 147 */       placeLog($$0, $$1, $$2, (BlockPos)$$10.move($$7), $$5, $$6);
/*     */     }
/*     */     
/* 150 */     Direction $$17 = ($$14.getY() > $$10.getY()) ? Direction.UP : Direction.DOWN;
/*     */     
/*     */     while (true) {
/* 153 */       int $$18 = $$10.distManhattan((Vec3i)$$14);
/* 154 */       if ($$18 == 0) {
/*     */         break;
/*     */       }
/*     */       
/* 158 */       float $$19 = Math.abs($$14.getY() - $$10.getY()) / $$18;
/* 159 */       boolean $$20 = ($$2.nextFloat() < $$19);
/*     */       
/* 161 */       $$10.move($$20 ? $$17 : $$7);
/* 162 */       placeLog($$0, $$1, $$2, (BlockPos)$$10, $$5, $$20 ? Function.<BlockState>identity() : $$6);
/*     */     } 
/*     */     
/* 165 */     return new FoliagePlacer.FoliageAttachment($$14.above(), 0, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\CherryTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */