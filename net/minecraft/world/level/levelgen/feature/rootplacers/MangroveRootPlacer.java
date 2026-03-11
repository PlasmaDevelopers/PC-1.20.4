/*     */ package net.minecraft.world.level.levelgen.feature.rootplacers;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*     */ 
/*     */ public class MangroveRootPlacer extends RootPlacer {
/*     */   public static final int ROOT_WIDTH_LIMIT = 8;
/*     */   
/*     */   static {
/*  25 */     CODEC = RecordCodecBuilder.create($$0 -> rootPlacerParts($$0).and((App)MangroveRootPlacement.CODEC.fieldOf("mangrove_root_placement").forGetter(())).apply((Applicative)$$0, MangroveRootPlacer::new));
/*     */   }
/*     */   public static final int ROOT_LENGTH_LIMIT = 15;
/*     */   public static final Codec<MangroveRootPlacer> CODEC;
/*     */   private final MangroveRootPlacement mangroveRootPlacement;
/*     */   
/*     */   public MangroveRootPlacer(IntProvider $$0, BlockStateProvider $$1, Optional<AboveRootPlacement> $$2, MangroveRootPlacement $$3) {
/*  32 */     super($$0, $$1, $$2);
/*  33 */     this.mangroveRootPlacement = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean placeRoots(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, BlockPos $$4, TreeConfiguration $$5) {
/*  38 */     List<BlockPos> $$6 = Lists.newArrayList();
/*     */     
/*  40 */     BlockPos.MutableBlockPos $$7 = $$3.mutable();
/*  41 */     while ($$7.getY() < $$4.getY()) {
/*  42 */       if (!canPlaceRoot($$0, (BlockPos)$$7)) {
/*  43 */         return false;
/*     */       }
/*  45 */       $$7.move(Direction.UP);
/*     */     } 
/*     */     
/*  48 */     $$6.add($$4.below());
/*     */ 
/*     */     
/*  51 */     for (Direction $$8 : Direction.Plane.HORIZONTAL) {
/*  52 */       BlockPos $$9 = $$4.relative($$8);
/*  53 */       List<BlockPos> $$10 = Lists.newArrayList();
/*     */       
/*  55 */       if (!simulateRoots($$0, $$2, $$9, $$8, $$4, $$10, 0)) {
/*  56 */         return false;
/*     */       }
/*     */       
/*  59 */       $$6.addAll($$10);
/*  60 */       $$6.add($$4.relative($$8));
/*     */     } 
/*     */     
/*  63 */     for (BlockPos $$11 : $$6) {
/*  64 */       placeRoot($$0, $$1, $$2, $$11, $$5);
/*     */     }
/*     */     
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean simulateRoots(LevelSimulatedReader $$0, RandomSource $$1, BlockPos $$2, Direction $$3, BlockPos $$4, List<BlockPos> $$5, int $$6) {
/*  72 */     int $$7 = this.mangroveRootPlacement.maxRootLength();
/*  73 */     if ($$6 == $$7 || $$5.size() > $$7) {
/*  74 */       return false;
/*     */     }
/*  76 */     List<BlockPos> $$8 = potentialRootPositions($$2, $$3, $$1, $$4);
/*  77 */     for (BlockPos $$9 : $$8) {
/*  78 */       if (canPlaceRoot($$0, $$9)) {
/*  79 */         $$5.add($$9);
/*  80 */         if (!simulateRoots($$0, $$1, $$9, $$3, $$4, $$5, $$6 + 1)) {
/*  81 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     return true;
/*     */   }
/*     */   
/*     */   protected List<BlockPos> potentialRootPositions(BlockPos $$0, Direction $$1, RandomSource $$2, BlockPos $$3) {
/*  90 */     BlockPos $$4 = $$0.below();
/*  91 */     BlockPos $$5 = $$0.relative($$1);
/*  92 */     int $$6 = $$0.distManhattan((Vec3i)$$3);
/*  93 */     int $$7 = this.mangroveRootPlacement.maxRootWidth();
/*  94 */     float $$8 = this.mangroveRootPlacement.randomSkewChance();
/*     */ 
/*     */     
/*  97 */     if ($$6 > $$7 - 3 && $$6 <= $$7) {
/*  98 */       return ($$2.nextFloat() < $$8) ? List.<BlockPos>of($$4, $$5.below()) : List.<BlockPos>of($$4);
/*     */     }
/*     */ 
/*     */     
/* 102 */     if ($$6 > $$7) {
/* 103 */       return List.of($$4);
/*     */     }
/*     */ 
/*     */     
/* 107 */     if ($$2.nextFloat() < $$8) {
/* 108 */       return List.of($$4);
/*     */     }
/*     */     
/* 111 */     return $$2.nextBoolean() ? List.<BlockPos>of($$5) : List.<BlockPos>of($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canPlaceRoot(LevelSimulatedReader $$0, BlockPos $$1) {
/* 116 */     return (super.canPlaceRoot($$0, $$1) || $$0.isStateAtPosition($$1, $$0 -> $$0.is(this.mangroveRootPlacement.canGrowThrough())));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void placeRoot(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, TreeConfiguration $$4) {
/* 121 */     if ($$0.isStateAtPosition($$3, $$0 -> $$0.is(this.mangroveRootPlacement.muddyRootsIn()))) {
/* 122 */       BlockState $$5 = this.mangroveRootPlacement.muddyRootsProvider().getState($$2, $$3);
/* 123 */       $$1.accept($$3, getPotentiallyWaterloggedState($$0, $$3, $$5));
/*     */     } else {
/* 125 */       super.placeRoot($$0, $$1, $$2, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected RootPlacerType<?> type() {
/* 131 */     return RootPlacerType.MANGROVE_ROOT_PLACER;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\rootplacers\MangroveRootPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */