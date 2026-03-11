/*     */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*     */ 
/*     */ public class FancyTrunkPlacer extends TrunkPlacer {
/*     */   public static final Codec<FancyTrunkPlacer> CODEC;
/*     */   
/*     */   static {
/*  21 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).apply((Applicative)$$0, FancyTrunkPlacer::new));
/*     */   }
/*     */   private static final double TRUNK_HEIGHT_SCALE = 0.618D;
/*     */   private static final double CLUSTER_DENSITY_MAGIC = 1.382D;
/*     */   private static final double BRANCH_SLOPE = 0.381D;
/*     */   private static final double BRANCH_LENGTH_MAGIC = 0.328D;
/*     */   
/*     */   public FancyTrunkPlacer(int $$0, int $$1, int $$2) {
/*  29 */     super($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TrunkPlacerType<?> type() {
/*  34 */     return TrunkPlacerType.FANCY_TRUNK_PLACER;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/*  39 */     int $$6 = 5;
/*  40 */     int $$7 = $$3 + 2;
/*  41 */     int $$8 = Mth.floor($$7 * 0.618D);
/*     */     
/*  43 */     setDirtAt($$0, $$1, $$2, $$4.below(), $$5);
/*     */ 
/*     */     
/*  46 */     double $$9 = 1.0D;
/*  47 */     int $$10 = Math.min(1, Mth.floor(1.382D + Math.pow(1.0D * $$7 / 13.0D, 2.0D)));
/*     */     
/*  49 */     int $$11 = $$4.getY() + $$8;
/*  50 */     int $$12 = $$7 - 5;
/*     */     
/*  52 */     List<FoliageCoords> $$13 = Lists.newArrayList();
/*  53 */     $$13.add(new FoliageCoords($$4.above($$12), $$11));
/*     */     
/*  55 */     for (; $$12 >= 0; $$12--) {
/*  56 */       float $$14 = treeShape($$7, $$12);
/*  57 */       if ($$14 >= 0.0F)
/*     */       {
/*     */ 
/*     */         
/*  61 */         for (int $$15 = 0; $$15 < $$10; $$15++) {
/*  62 */           double $$16 = 1.0D;
/*  63 */           double $$17 = 1.0D * $$14 * ($$2.nextFloat() + 0.328D);
/*  64 */           double $$18 = ($$2.nextFloat() * 2.0F) * Math.PI;
/*     */           
/*  66 */           double $$19 = $$17 * Math.sin($$18) + 0.5D;
/*  67 */           double $$20 = $$17 * Math.cos($$18) + 0.5D;
/*     */           
/*  69 */           BlockPos $$21 = $$4.offset(Mth.floor($$19), $$12 - 1, Mth.floor($$20));
/*  70 */           BlockPos $$22 = $$21.above(5);
/*     */ 
/*     */           
/*  73 */           if (makeLimb($$0, $$1, $$2, $$21, $$22, false, $$5)) {
/*     */             
/*  75 */             int $$23 = $$4.getX() - $$21.getX();
/*  76 */             int $$24 = $$4.getZ() - $$21.getZ();
/*     */             
/*  78 */             double $$25 = $$21.getY() - Math.sqrt(($$23 * $$23 + $$24 * $$24)) * 0.381D;
/*  79 */             int $$26 = ($$25 > $$11) ? $$11 : (int)$$25;
/*  80 */             BlockPos $$27 = new BlockPos($$4.getX(), $$26, $$4.getZ());
/*     */ 
/*     */             
/*  83 */             if (makeLimb($$0, $$1, $$2, $$27, $$21, false, $$5))
/*     */             {
/*  85 */               $$13.add(new FoliageCoords($$21, $$27.getY())); } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*  90 */     makeLimb($$0, $$1, $$2, $$4, $$4.above($$8), true, $$5);
/*  91 */     makeBranches($$0, $$1, $$2, $$7, $$4, $$13, $$5);
/*     */     
/*  93 */     List<FoliagePlacer.FoliageAttachment> $$28 = Lists.newArrayList();
/*  94 */     for (FoliageCoords $$29 : $$13) {
/*  95 */       if (trimBranches($$7, $$29.getBranchBase() - $$4.getY())) {
/*  96 */         $$28.add($$29.attachment);
/*     */       }
/*     */     } 
/*  99 */     return $$28;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean makeLimb(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, BlockPos $$4, boolean $$5, TreeConfiguration $$6) {
/* 104 */     if (!$$5 && Objects.equals($$3, $$4)) {
/* 105 */       return true;
/*     */     }
/*     */     
/* 108 */     BlockPos $$7 = $$4.offset(-$$3.getX(), -$$3.getY(), -$$3.getZ());
/*     */     
/* 110 */     int $$8 = getSteps($$7);
/*     */     
/* 112 */     float $$9 = $$7.getX() / $$8;
/* 113 */     float $$10 = $$7.getY() / $$8;
/* 114 */     float $$11 = $$7.getZ() / $$8;
/*     */     
/* 116 */     for (int $$12 = 0; $$12 <= $$8; $$12++) {
/* 117 */       BlockPos $$13 = $$3.offset(Mth.floor(0.5F + $$12 * $$9), Mth.floor(0.5F + $$12 * $$10), Mth.floor(0.5F + $$12 * $$11));
/* 118 */       if ($$5) {
/* 119 */         placeLog($$0, $$1, $$2, $$13, $$6, $$2 -> (BlockState)$$2.trySetValue((Property)RotatedPillarBlock.AXIS, (Comparable)getLogAxis($$0, $$1)));
/*     */       
/*     */       }
/* 122 */       else if (!isFree($$0, $$13)) {
/* 123 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     return true;
/*     */   }
/*     */   
/*     */   private int getSteps(BlockPos $$0) {
/* 131 */     int $$1 = Mth.abs($$0.getX());
/* 132 */     int $$2 = Mth.abs($$0.getY());
/* 133 */     int $$3 = Mth.abs($$0.getZ());
/*     */     
/* 135 */     return Math.max($$1, Math.max($$2, $$3));
/*     */   }
/*     */   
/*     */   private Direction.Axis getLogAxis(BlockPos $$0, BlockPos $$1) {
/* 139 */     Direction.Axis $$2 = Direction.Axis.Y;
/* 140 */     int $$3 = Math.abs($$1.getX() - $$0.getX());
/* 141 */     int $$4 = Math.abs($$1.getZ() - $$0.getZ());
/* 142 */     int $$5 = Math.max($$3, $$4);
/*     */     
/* 144 */     if ($$5 > 0) {
/* 145 */       if ($$3 == $$5) {
/* 146 */         $$2 = Direction.Axis.X;
/*     */       } else {
/* 148 */         $$2 = Direction.Axis.Z;
/*     */       } 
/*     */     }
/* 151 */     return $$2;
/*     */   }
/*     */   
/*     */   private boolean trimBranches(int $$0, int $$1) {
/* 155 */     return ($$1 >= $$0 * 0.2D);
/*     */   }
/*     */   
/*     */   private void makeBranches(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, List<FoliageCoords> $$5, TreeConfiguration $$6) {
/* 159 */     for (FoliageCoords $$7 : $$5) {
/* 160 */       int $$8 = $$7.getBranchBase();
/* 161 */       BlockPos $$9 = new BlockPos($$4.getX(), $$8, $$4.getZ());
/*     */       
/* 163 */       if (!$$9.equals($$7.attachment.pos()) && trimBranches($$3, $$8 - $$4.getY())) {
/* 164 */         makeLimb($$0, $$1, $$2, $$9, $$7.attachment.pos(), true, $$6);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static float treeShape(int $$0, int $$1) {
/* 171 */     if ($$1 < $$0 * 0.3F) {
/* 172 */       return -1.0F;
/*     */     }
/*     */     
/* 175 */     float $$2 = $$0 / 2.0F;
/* 176 */     float $$3 = $$2 - $$1;
/*     */     
/* 178 */     float $$4 = Mth.sqrt($$2 * $$2 - $$3 * $$3);
/* 179 */     if ($$3 == 0.0F) {
/* 180 */       $$4 = $$2;
/* 181 */     } else if (Math.abs($$3) >= $$2) {
/* 182 */       return 0.0F;
/*     */     } 
/*     */     
/* 185 */     return $$4 * 0.5F;
/*     */   }
/*     */   
/*     */   private static class FoliageCoords {
/*     */     final FoliagePlacer.FoliageAttachment attachment;
/*     */     private final int branchBase;
/*     */     
/*     */     public FoliageCoords(BlockPos $$0, int $$1) {
/* 193 */       this.attachment = new FoliagePlacer.FoliageAttachment($$0, 0, false);
/* 194 */       this.branchBase = $$1;
/*     */     }
/*     */     
/*     */     public int getBranchBase() {
/* 198 */       return this.branchBase;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\FancyTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */