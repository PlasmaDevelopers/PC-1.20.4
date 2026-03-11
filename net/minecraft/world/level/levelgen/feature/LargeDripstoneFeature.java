/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.FloatProvider;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.Column;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LargeDripstoneFeature
/*     */   extends Feature<LargeDripstoneConfiguration>
/*     */ {
/*     */   public LargeDripstoneFeature(Codec<LargeDripstoneConfiguration> $$0) {
/*  28 */     super($$0);
/*     */   }
/*     */   
/*     */   public boolean place(FeaturePlaceContext<LargeDripstoneConfiguration> $$0) {
/*     */     WindOffsetter $$13;
/*  33 */     WorldGenLevel $$1 = $$0.level();
/*  34 */     BlockPos $$2 = $$0.origin();
/*  35 */     LargeDripstoneConfiguration $$3 = $$0.config();
/*  36 */     RandomSource $$4 = $$0.random();
/*     */     
/*  38 */     if (!DripstoneUtils.isEmptyOrWater((LevelAccessor)$$1, $$2)) {
/*  39 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  43 */     Optional<Column> $$5 = Column.scan((LevelSimulatedReader)$$1, $$2, $$3.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava);
/*  44 */     if ($$5.isEmpty() || !($$5.get() instanceof Column.Range))
/*     */     {
/*     */ 
/*     */       
/*  48 */       return false;
/*     */     }
/*     */     
/*  51 */     Column.Range $$6 = (Column.Range)$$5.get();
/*     */     
/*  53 */     if ($$6.height() < 4)
/*     */     {
/*  55 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  59 */     int $$7 = (int)($$6.height() * $$3.maxColumnRadiusToCaveHeightRatio);
/*  60 */     int $$8 = Mth.clamp($$7, $$3.columnRadius.getMinValue(), $$3.columnRadius.getMaxValue());
/*  61 */     int $$9 = Mth.randomBetweenInclusive($$4, $$3.columnRadius.getMinValue(), $$8);
/*     */     
/*  63 */     LargeDripstone $$10 = makeDripstone($$2.atY($$6.ceiling() - 1), false, $$4, $$9, $$3.stalactiteBluntness, $$3.heightScale);
/*  64 */     LargeDripstone $$11 = makeDripstone($$2.atY($$6.floor() + 1), true, $$4, $$9, $$3.stalagmiteBluntness, $$3.heightScale);
/*     */ 
/*     */     
/*  67 */     if ($$10.isSuitableForWind($$3) && $$11.isSuitableForWind($$3)) {
/*  68 */       WindOffsetter $$12 = new WindOffsetter($$2.getY(), $$4, $$3.windSpeed);
/*     */     } else {
/*  70 */       $$13 = WindOffsetter.noWind();
/*     */     } 
/*     */     
/*  73 */     boolean $$14 = $$10.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary($$1, $$13);
/*  74 */     boolean $$15 = $$11.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary($$1, $$13);
/*     */     
/*  76 */     if ($$14) {
/*  77 */       $$10.placeBlocks($$1, $$4, $$13);
/*     */     }
/*     */     
/*  80 */     if ($$15) {
/*  81 */       $$11.placeBlocks($$1, $$4, $$13);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     return true;
/*     */   }
/*     */   
/*     */   private static LargeDripstone makeDripstone(BlockPos $$0, boolean $$1, RandomSource $$2, int $$3, FloatProvider $$4, FloatProvider $$5) {
/*  92 */     return new LargeDripstone($$0, $$1, $$3, $$4.sample($$2), $$5.sample($$2));
/*     */   }
/*     */   
/*     */   private void placeDebugMarkers(WorldGenLevel $$0, BlockPos $$1, Column.Range $$2, WindOffsetter $$3) {
/*  96 */     $$0.setBlock($$3.offset($$1.atY($$2.ceiling() - 1)), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
/*  97 */     $$0.setBlock($$3.offset($$1.atY($$2.floor() + 1)), Blocks.GOLD_BLOCK.defaultBlockState(), 2);
/*  98 */     BlockPos.MutableBlockPos $$4 = $$1.atY($$2.floor() + 2).mutable();
/*  99 */     while ($$4.getY() < $$2.ceiling() - 1) {
/* 100 */       BlockPos $$5 = $$3.offset((BlockPos)$$4);
/* 101 */       if (DripstoneUtils.isEmptyOrWater((LevelAccessor)$$0, $$5) || $$0.getBlockState($$5).is(Blocks.DRIPSTONE_BLOCK)) {
/* 102 */         $$0.setBlock($$5, Blocks.CREEPER_HEAD.defaultBlockState(), 2);
/*     */       }
/* 104 */       $$4.move(Direction.UP);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class LargeDripstone {
/*     */     private BlockPos root;
/*     */     private final boolean pointingUp;
/*     */     private int radius;
/*     */     private final double bluntness;
/*     */     private final double scale;
/*     */     
/*     */     LargeDripstone(BlockPos $$0, boolean $$1, int $$2, double $$3, double $$4) {
/* 116 */       this.root = $$0;
/* 117 */       this.pointingUp = $$1;
/* 118 */       this.radius = $$2;
/* 119 */       this.bluntness = $$3;
/* 120 */       this.scale = $$4;
/*     */     }
/*     */     
/*     */     private int getHeight() {
/* 124 */       return getHeightAtRadius(0.0F);
/*     */     }
/*     */     
/*     */     private int getMinY() {
/* 128 */       if (this.pointingUp) {
/* 129 */         return this.root.getY();
/*     */       }
/* 131 */       return this.root.getY() - getHeight();
/*     */     }
/*     */ 
/*     */     
/*     */     private int getMaxY() {
/* 136 */       if (!this.pointingUp) {
/* 137 */         return this.root.getY();
/*     */       }
/* 139 */       return this.root.getY() + getHeight();
/*     */     }
/*     */ 
/*     */     
/*     */     boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel $$0, LargeDripstoneFeature.WindOffsetter $$1) {
/* 144 */       while (this.radius > 1) {
/* 145 */         BlockPos.MutableBlockPos $$2 = this.root.mutable();
/* 146 */         int $$3 = Math.min(10, getHeight());
/* 147 */         for (int $$4 = 0; $$4 < $$3; $$4++) {
/* 148 */           if ($$0.getBlockState((BlockPos)$$2).is(Blocks.LAVA)) {
/* 149 */             return false;
/*     */           }
/* 151 */           if (DripstoneUtils.isCircleMostlyEmbeddedInStone($$0, $$1.offset((BlockPos)$$2), this.radius)) {
/* 152 */             this.root = (BlockPos)$$2;
/* 153 */             return true;
/*     */           } 
/* 155 */           $$2.move(this.pointingUp ? Direction.DOWN : Direction.UP);
/*     */         } 
/* 157 */         this.radius /= 2;
/*     */       } 
/* 159 */       return false;
/*     */     }
/*     */     
/*     */     private int getHeightAtRadius(float $$0) {
/* 163 */       return (int)DripstoneUtils.getDripstoneHeight($$0, this.radius, this.scale, this.bluntness);
/*     */     }
/*     */     
/*     */     void placeBlocks(WorldGenLevel $$0, RandomSource $$1, LargeDripstoneFeature.WindOffsetter $$2) {
/* 167 */       for (int $$3 = -this.radius; $$3 <= this.radius; $$3++) {
/* 168 */         for (int $$4 = -this.radius; $$4 <= this.radius; $$4++) {
/* 169 */           float $$5 = Mth.sqrt(($$3 * $$3 + $$4 * $$4));
/* 170 */           if ($$5 <= this.radius) {
/*     */ 
/*     */ 
/*     */             
/* 174 */             int $$6 = getHeightAtRadius($$5);
/* 175 */             if ($$6 > 0) {
/*     */ 
/*     */               
/* 178 */               if ($$1.nextFloat() < 0.2D)
/*     */               {
/* 180 */                 $$6 = (int)($$6 * Mth.randomBetween($$1, 0.8F, 1.0F));
/*     */               }
/*     */               
/* 183 */               BlockPos.MutableBlockPos $$7 = this.root.offset($$3, 0, $$4).mutable();
/* 184 */               boolean $$8 = false;
/*     */               
/* 186 */               int $$9 = this.pointingUp ? $$0.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$7.getX(), $$7.getZ()) : Integer.MAX_VALUE;
/* 187 */               for (int $$10 = 0; $$10 < $$6 && 
/* 188 */                 $$7.getY() < $$9; $$10++) {
/*     */ 
/*     */                 
/* 191 */                 BlockPos $$11 = $$2.offset((BlockPos)$$7);
/* 192 */                 if (DripstoneUtils.isEmptyOrWaterOrLava((LevelAccessor)$$0, $$11)) {
/* 193 */                   $$8 = true;
/* 194 */                   Block $$12 = Blocks.DRIPSTONE_BLOCK;
/* 195 */                   $$0.setBlock($$11, $$12.defaultBlockState(), 2);
/* 196 */                 } else if ($$8 && $$0.getBlockState($$11).is(BlockTags.BASE_STONE_OVERWORLD)) {
/*     */                   break;
/*     */                 } 
/* 199 */                 $$7.move(this.pointingUp ? Direction.UP : Direction.DOWN);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isSuitableForWind(LargeDripstoneConfiguration $$0) {
/* 209 */       return (this.radius >= $$0.minRadiusForWind && this.bluntness >= $$0.minBluntnessForWind);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class WindOffsetter
/*     */   {
/*     */     private final int originY;
/*     */     
/*     */     @Nullable
/*     */     private final Vec3 windSpeed;
/*     */     
/*     */     WindOffsetter(int $$0, RandomSource $$1, FloatProvider $$2) {
/* 222 */       this.originY = $$0;
/*     */       
/* 224 */       float $$3 = $$2.sample($$1);
/*     */       
/* 226 */       float $$4 = Mth.randomBetween($$1, 0.0F, 3.1415927F);
/* 227 */       this.windSpeed = new Vec3((Mth.cos($$4) * $$3), 0.0D, (Mth.sin($$4) * $$3));
/*     */     }
/*     */     
/*     */     private WindOffsetter() {
/* 231 */       this.originY = 0;
/* 232 */       this.windSpeed = null;
/*     */     }
/*     */     
/*     */     static WindOffsetter noWind() {
/* 236 */       return new WindOffsetter();
/*     */     }
/*     */     
/*     */     BlockPos offset(BlockPos $$0) {
/* 240 */       if (this.windSpeed == null) {
/* 241 */         return $$0;
/*     */       }
/* 243 */       int $$1 = this.originY - $$0.getY();
/* 244 */       Vec3 $$2 = this.windSpeed.scale($$1);
/* 245 */       return $$0.offset(Mth.floor($$2.x), 0, Mth.floor($$2.z));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\LargeDripstoneFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */