/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelWriter;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ 
/*     */ public class HugeFungusFeature extends Feature<HugeFungusConfiguration> {
/*     */   public HugeFungusFeature(Codec<HugeFungusConfiguration> $$0) {
/*  20 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<HugeFungusConfiguration> $$0) {
/*  25 */     WorldGenLevel $$1 = $$0.level();
/*  26 */     BlockPos $$2 = $$0.origin();
/*  27 */     RandomSource $$3 = $$0.random();
/*  28 */     ChunkGenerator $$4 = $$0.chunkGenerator();
/*  29 */     HugeFungusConfiguration $$5 = $$0.config();
/*  30 */     Block $$6 = $$5.validBaseState.getBlock();
/*  31 */     BlockPos $$7 = null;
/*     */     
/*  33 */     BlockState $$8 = $$1.getBlockState($$2.below());
/*  34 */     if ($$8.is($$6)) {
/*  35 */       $$7 = $$2;
/*     */     }
/*     */     
/*  38 */     if ($$7 == null) {
/*  39 */       return false;
/*     */     }
/*     */     
/*  42 */     int $$9 = Mth.nextInt($$3, 4, 13);
/*  43 */     if ($$3.nextInt(12) == 0) {
/*  44 */       $$9 *= 2;
/*     */     }
/*     */     
/*  47 */     if (!$$5.planted) {
/*  48 */       int $$10 = $$4.getGenDepth();
/*  49 */       if ($$7.getY() + $$9 + 1 >= $$10) {
/*  50 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  54 */     boolean $$11 = (!$$5.planted && $$3.nextFloat() < 0.06F);
/*     */     
/*  56 */     $$1.setBlock($$2, Blocks.AIR.defaultBlockState(), 4);
/*     */     
/*  58 */     placeStem($$1, $$3, $$5, $$7, $$9, $$11);
/*  59 */     placeHat($$1, $$3, $$5, $$7, $$9, $$11);
/*     */     
/*  61 */     return true;
/*     */   }
/*     */   private static final float HUGE_PROBABILITY = 0.06F;
/*     */   private static boolean isReplaceable(WorldGenLevel $$0, BlockPos $$1, HugeFungusConfiguration $$2, boolean $$3) {
/*  65 */     if ($$0.isStateAtPosition($$1, BlockBehaviour.BlockStateBase::canBeReplaced)) {
/*  66 */       return true;
/*     */     }
/*  68 */     if ($$3)
/*     */     {
/*     */       
/*  71 */       return $$2.replaceableBlocks.test($$0, $$1);
/*     */     }
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeStem(WorldGenLevel $$0, RandomSource $$1, HugeFungusConfiguration $$2, BlockPos $$3, int $$4, boolean $$5) {
/*  78 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/*  79 */     BlockState $$7 = $$2.stemState;
/*  80 */     int $$8 = $$5 ? 1 : 0;
/*     */     
/*  82 */     for (int $$9 = -$$8; $$9 <= $$8; $$9++) {
/*  83 */       for (int $$10 = -$$8; $$10 <= $$8; $$10++) {
/*  84 */         boolean $$11 = ($$5 && Mth.abs($$9) == $$8 && Mth.abs($$10) == $$8);
/*     */         
/*  86 */         for (int $$12 = 0; $$12 < $$4; $$12++) {
/*  87 */           $$6.setWithOffset((Vec3i)$$3, $$9, $$12, $$10);
/*  88 */           if (isReplaceable($$0, (BlockPos)$$6, $$2, true)) {
/*  89 */             if ($$2.planted) {
/*  90 */               if (!$$0.getBlockState($$6.below()).isAir()) {
/*  91 */                 $$0.destroyBlock((BlockPos)$$6, true);
/*     */               }
/*     */               
/*  94 */               $$0.setBlock((BlockPos)$$6, $$7, 3);
/*     */             }
/*  96 */             else if ($$11) {
/*  97 */               if ($$1.nextFloat() < 0.1F) {
/*  98 */                 setBlock((LevelWriter)$$0, (BlockPos)$$6, $$7);
/*     */               }
/*     */             } else {
/* 101 */               setBlock((LevelWriter)$$0, (BlockPos)$$6, $$7);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeHat(WorldGenLevel $$0, RandomSource $$1, HugeFungusConfiguration $$2, BlockPos $$3, int $$4, boolean $$5) {
/* 111 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/* 112 */     boolean $$7 = $$2.hatState.is(Blocks.NETHER_WART_BLOCK);
/* 113 */     int $$8 = Math.min($$1.nextInt(1 + $$4 / 3) + 5, $$4);
/* 114 */     int $$9 = $$4 - $$8;
/* 115 */     for (int $$10 = $$9; $$10 <= $$4; $$10++) {
/* 116 */       int $$11 = ($$10 < $$4 - $$1.nextInt(3)) ? 2 : 1;
/* 117 */       if ($$8 > 8 && $$10 < $$9 + 4) {
/* 118 */         $$11 = 3;
/*     */       }
/*     */       
/* 121 */       if ($$5) {
/* 122 */         $$11++;
/*     */       }
/*     */       
/* 125 */       for (int $$12 = -$$11; $$12 <= $$11; $$12++) {
/* 126 */         for (int $$13 = -$$11; $$13 <= $$11; $$13++) {
/* 127 */           boolean $$14 = ($$12 == -$$11 || $$12 == $$11);
/* 128 */           boolean $$15 = ($$13 == -$$11 || $$13 == $$11);
/* 129 */           boolean $$16 = (!$$14 && !$$15 && $$10 != $$4);
/* 130 */           boolean $$17 = ($$14 && $$15);
/* 131 */           boolean $$18 = ($$10 < $$9 + 3);
/*     */           
/* 133 */           $$6.setWithOffset((Vec3i)$$3, $$12, $$10, $$13);
/* 134 */           if (isReplaceable($$0, (BlockPos)$$6, $$2, false)) {
/* 135 */             if ($$2.planted && !$$0.getBlockState($$6.below()).isAir()) {
/* 136 */               $$0.destroyBlock((BlockPos)$$6, true);
/*     */             }
/*     */             
/* 139 */             if ($$18) {
/* 140 */               if (!$$16) {
/* 141 */                 placeHatDropBlock((LevelAccessor)$$0, $$1, (BlockPos)$$6, $$2.hatState, $$7);
/*     */               }
/* 143 */             } else if ($$16) {
/* 144 */               placeHatBlock((LevelAccessor)$$0, $$1, $$2, $$6, 0.1F, 0.2F, $$7 ? 0.1F : 0.0F);
/* 145 */             } else if ($$17) {
/* 146 */               placeHatBlock((LevelAccessor)$$0, $$1, $$2, $$6, 0.01F, 0.7F, $$7 ? 0.083F : 0.0F);
/*     */             } else {
/* 148 */               placeHatBlock((LevelAccessor)$$0, $$1, $$2, $$6, 5.0E-4F, 0.98F, $$7 ? 0.07F : 0.0F);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void placeHatBlock(LevelAccessor $$0, RandomSource $$1, HugeFungusConfiguration $$2, BlockPos.MutableBlockPos $$3, float $$4, float $$5, float $$6) {
/* 157 */     if ($$1.nextFloat() < $$4) {
/* 158 */       setBlock((LevelWriter)$$0, (BlockPos)$$3, $$2.decorState);
/* 159 */     } else if ($$1.nextFloat() < $$5) {
/* 160 */       setBlock((LevelWriter)$$0, (BlockPos)$$3, $$2.hatState);
/* 161 */       if ($$1.nextFloat() < $$6) {
/* 162 */         tryPlaceWeepingVines((BlockPos)$$3, $$0, $$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void placeHatDropBlock(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 168 */     if ($$0.getBlockState($$2.below()).is($$3.getBlock())) {
/* 169 */       setBlock((LevelWriter)$$0, $$2, $$3);
/* 170 */     } else if ($$1.nextFloat() < 0.15D) {
/* 171 */       setBlock((LevelWriter)$$0, $$2, $$3);
/* 172 */       if ($$4 && $$1.nextInt(11) == 0) {
/* 173 */         tryPlaceWeepingVines($$2, $$0, $$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void tryPlaceWeepingVines(BlockPos $$0, LevelAccessor $$1, RandomSource $$2) {
/* 179 */     BlockPos.MutableBlockPos $$3 = $$0.mutable().move(Direction.DOWN);
/*     */     
/* 181 */     if (!$$1.isEmptyBlock((BlockPos)$$3)) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     int $$4 = Mth.nextInt($$2, 1, 5);
/* 186 */     if ($$2.nextInt(7) == 0) {
/* 187 */       $$4 *= 2;
/*     */     }
/*     */     
/* 190 */     int $$5 = 23;
/* 191 */     int $$6 = 25;
/* 192 */     WeepingVinesFeature.placeWeepingVinesColumn($$1, $$2, $$3, $$4, 23, 25);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\HugeFungusFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */