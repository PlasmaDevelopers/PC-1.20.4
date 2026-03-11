/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelWriter;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
/*     */ 
/*     */ public class IcebergFeature extends Feature<BlockStateConfiguration> {
/*     */   public IcebergFeature(Codec<BlockStateConfiguration> $$0) {
/*  16 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<BlockStateConfiguration> $$0) {
/*  21 */     BlockPos $$1 = $$0.origin();
/*  22 */     WorldGenLevel $$2 = $$0.level();
/*  23 */     $$1 = new BlockPos($$1.getX(), $$0.chunkGenerator().getSeaLevel(), $$1.getZ());
/*  24 */     RandomSource $$3 = $$0.random();
/*  25 */     boolean $$4 = ($$3.nextDouble() > 0.7D);
/*  26 */     BlockState $$5 = ((BlockStateConfiguration)$$0.config()).state;
/*     */ 
/*     */     
/*  29 */     double $$6 = $$3.nextDouble() * 2.0D * Math.PI;
/*  30 */     int $$7 = 11 - $$3.nextInt(5);
/*  31 */     int $$8 = 3 + $$3.nextInt(3);
/*  32 */     boolean $$9 = ($$3.nextDouble() > 0.7D);
/*     */     
/*  34 */     int $$10 = 11;
/*  35 */     int $$11 = $$9 ? ($$3.nextInt(6) + 6) : ($$3.nextInt(15) + 3);
/*  36 */     if (!$$9 && $$3.nextDouble() > 0.9D) {
/*  37 */       $$11 += $$3.nextInt(19) + 7;
/*     */     }
/*     */     
/*  40 */     int $$12 = Math.min($$11 + $$3.nextInt(11), 18);
/*  41 */     int $$13 = Math.min($$11 + $$3.nextInt(7) - $$3.nextInt(5), 11);
/*  42 */     int $$14 = $$9 ? $$7 : 11;
/*     */ 
/*     */     
/*  45 */     for (int $$15 = -$$14; $$15 < $$14; $$15++) {
/*  46 */       for (int $$16 = -$$14; $$16 < $$14; $$16++) {
/*  47 */         for (int $$17 = 0; $$17 < $$11; $$17++) {
/*  48 */           int $$18 = $$9 ? heightDependentRadiusEllipse($$17, $$11, $$13) : heightDependentRadiusRound($$3, $$17, $$11, $$13);
/*  49 */           if ($$9 || $$15 < $$18)
/*     */           {
/*     */             
/*  52 */             generateIcebergBlock((LevelAccessor)$$2, $$3, $$1, $$11, $$15, $$17, $$16, $$18, $$14, $$9, $$8, $$6, $$4, $$5);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     smooth((LevelAccessor)$$2, $$1, $$13, $$11, $$9, $$7);
/*     */ 
/*     */     
/*  61 */     for (int $$19 = -$$14; $$19 < $$14; $$19++) {
/*  62 */       for (int $$20 = -$$14; $$20 < $$14; $$20++) {
/*  63 */         for (int $$21 = -1; $$21 > -$$12; $$21--) {
/*  64 */           int $$22 = $$9 ? Mth.ceil($$14 * (1.0F - (float)Math.pow($$21, 2.0D) / $$12 * 8.0F)) : $$14;
/*  65 */           int $$23 = heightDependentRadiusSteep($$3, -$$21, $$12, $$13);
/*  66 */           if ($$19 < $$23)
/*     */           {
/*     */             
/*  69 */             generateIcebergBlock((LevelAccessor)$$2, $$3, $$1, $$12, $$19, $$21, $$20, $$23, $$22, $$9, $$8, $$6, $$4, $$5);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     boolean $$24 = $$9 ? (($$3.nextDouble() > 0.1D)) : (($$3.nextDouble() > 0.7D));
/*  76 */     if ($$24) {
/*  77 */       generateCutOut($$3, (LevelAccessor)$$2, $$13, $$11, $$1, $$9, $$7, $$6, $$8);
/*     */     }
/*     */     
/*  80 */     return true;
/*     */   }
/*     */   
/*     */   private void generateCutOut(RandomSource $$0, LevelAccessor $$1, int $$2, int $$3, BlockPos $$4, boolean $$5, int $$6, double $$7, int $$8) {
/*  84 */     int $$9 = $$0.nextBoolean() ? -1 : 1;
/*  85 */     int $$10 = $$0.nextBoolean() ? -1 : 1;
/*     */     
/*  87 */     int $$11 = $$0.nextInt(Math.max($$2 / 2 - 2, 1));
/*  88 */     if ($$0.nextBoolean()) {
/*  89 */       $$11 = $$2 / 2 + 1 - $$0.nextInt(Math.max($$2 - $$2 / 2 - 1, 1));
/*     */     }
/*  91 */     int $$12 = $$0.nextInt(Math.max($$2 / 2 - 2, 1));
/*  92 */     if ($$0.nextBoolean()) {
/*  93 */       $$12 = $$2 / 2 + 1 - $$0.nextInt(Math.max($$2 - $$2 / 2 - 1, 1));
/*     */     }
/*     */     
/*  96 */     if ($$5) {
/*  97 */       $$11 = $$12 = $$0.nextInt(Math.max($$6 - 5, 1));
/*     */     }
/*     */     
/* 100 */     BlockPos $$13 = new BlockPos($$9 * $$11, 0, $$10 * $$12);
/* 101 */     double $$14 = $$5 ? ($$7 + 1.5707963267948966D) : ($$0.nextDouble() * 2.0D * Math.PI);
/*     */     
/* 103 */     for (int $$15 = 0; $$15 < $$3 - 3; $$15++) {
/* 104 */       int $$16 = heightDependentRadiusRound($$0, $$15, $$3, $$2);
/* 105 */       carve($$16, $$15, $$4, $$1, false, $$14, $$13, $$6, $$8);
/*     */     } 
/*     */     
/* 108 */     for (int $$17 = -1; $$17 > -$$3 + $$0.nextInt(5); $$17--) {
/* 109 */       int $$18 = heightDependentRadiusSteep($$0, -$$17, $$3, $$2);
/* 110 */       carve($$18, $$17, $$4, $$1, true, $$14, $$13, $$6, $$8);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void carve(int $$0, int $$1, BlockPos $$2, LevelAccessor $$3, boolean $$4, double $$5, BlockPos $$6, int $$7, int $$8) {
/* 115 */     int $$9 = $$0 + 1 + $$7 / 3;
/* 116 */     int $$10 = Math.min($$0 - 3, 3) + $$8 / 2 - 1;
/*     */     
/* 118 */     for (int $$11 = -$$9; $$11 < $$9; $$11++) {
/* 119 */       for (int $$12 = -$$9; $$12 < $$9; $$12++) {
/* 120 */         double $$13 = signedDistanceEllipse($$11, $$12, $$6, $$9, $$10, $$5);
/* 121 */         if ($$13 < 0.0D) {
/* 122 */           BlockPos $$14 = $$2.offset($$11, $$1, $$12);
/* 123 */           BlockState $$15 = $$3.getBlockState($$14);
/* 124 */           if (isIcebergState($$15) || $$15.is(Blocks.SNOW_BLOCK)) {
/* 125 */             if ($$4) {
/* 126 */               setBlock((LevelWriter)$$3, $$14, Blocks.WATER.defaultBlockState());
/*     */             } else {
/* 128 */               setBlock((LevelWriter)$$3, $$14, Blocks.AIR.defaultBlockState());
/* 129 */               removeFloatingSnowLayer($$3, $$14);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFloatingSnowLayer(LevelAccessor $$0, BlockPos $$1) {
/* 138 */     if ($$0.getBlockState($$1.above()).is(Blocks.SNOW)) {
/* 139 */       setBlock((LevelWriter)$$0, $$1.above(), Blocks.AIR.defaultBlockState());
/*     */     }
/*     */   }
/*     */   
/*     */   private void generateIcebergBlock(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, boolean $$9, int $$10, double $$11, boolean $$12, BlockState $$13) {
/* 144 */     double $$14 = $$9 ? signedDistanceEllipse($$4, $$6, BlockPos.ZERO, $$8, getEllipseC($$5, $$3, $$10), $$11) : signedDistanceCircle($$4, $$6, BlockPos.ZERO, $$7, $$1);
/* 145 */     if ($$14 < 0.0D) {
/* 146 */       BlockPos $$15 = $$2.offset($$4, $$5, $$6);
/* 147 */       double $$16 = $$9 ? -0.5D : (-6 - $$1.nextInt(3));
/* 148 */       if ($$14 > $$16 && $$1.nextDouble() > 0.9D) {
/*     */         return;
/*     */       }
/* 151 */       setIcebergBlock($$15, $$0, $$1, $$3 - $$5, $$3, $$9, $$12, $$13);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setIcebergBlock(BlockPos $$0, LevelAccessor $$1, RandomSource $$2, int $$3, int $$4, boolean $$5, boolean $$6, BlockState $$7) {
/* 156 */     BlockState $$8 = $$1.getBlockState($$0);
/* 157 */     if ($$8.isAir() || $$8.is(Blocks.SNOW_BLOCK) || $$8.is(Blocks.ICE) || $$8.is(Blocks.WATER)) {
/* 158 */       boolean $$9 = (!$$5 || $$2.nextDouble() > 0.05D);
/* 159 */       int $$10 = $$5 ? 3 : 2;
/* 160 */       if ($$6 && !$$8.is(Blocks.WATER) && $$3 <= $$2.nextInt(Math.max(1, $$4 / $$10)) + $$4 * 0.6D && $$9) {
/* 161 */         setBlock((LevelWriter)$$1, $$0, Blocks.SNOW_BLOCK.defaultBlockState());
/*     */       } else {
/* 163 */         setBlock((LevelWriter)$$1, $$0, $$7);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getEllipseC(int $$0, int $$1, int $$2) {
/* 169 */     int $$3 = $$2;
/* 170 */     if ($$0 > 0 && $$1 - $$0 <= 3) {
/* 171 */       $$3 -= 4 - $$1 - $$0;
/*     */     }
/*     */     
/* 174 */     return $$3;
/*     */   }
/*     */   
/*     */   private double signedDistanceCircle(int $$0, int $$1, BlockPos $$2, int $$3, RandomSource $$4) {
/* 178 */     float $$5 = 10.0F * Mth.clamp($$4.nextFloat(), 0.2F, 0.8F) / $$3;
/* 179 */     return $$5 + Math.pow(($$0 - $$2.getX()), 2.0D) + Math.pow(($$1 - $$2.getZ()), 2.0D) - Math.pow($$3, 2.0D);
/*     */   }
/*     */   
/*     */   private double signedDistanceEllipse(int $$0, int $$1, BlockPos $$2, int $$3, int $$4, double $$5) {
/* 183 */     return Math.pow((($$0 - $$2.getX()) * Math.cos($$5) - ($$1 - $$2.getZ()) * Math.sin($$5)) / $$3, 2.0D) + Math.pow((($$0 - $$2.getX()) * Math.sin($$5) + ($$1 - $$2.getZ()) * Math.cos($$5)) / $$4, 2.0D) - 1.0D;
/*     */   }
/*     */   
/*     */   private int heightDependentRadiusRound(RandomSource $$0, int $$1, int $$2, int $$3) {
/* 187 */     float $$4 = 3.5F - $$0.nextFloat();
/* 188 */     float $$5 = (1.0F - (float)Math.pow($$1, 2.0D) / $$2 * $$4) * $$3;
/*     */     
/* 190 */     if ($$2 > 15 + $$0.nextInt(5)) {
/* 191 */       int $$6 = ($$1 < 3 + $$0.nextInt(6)) ? ($$1 / 2) : $$1;
/* 192 */       $$5 = (1.0F - $$6 / $$2 * $$4 * 0.4F) * $$3;
/*     */     } 
/*     */     
/* 195 */     return Mth.ceil($$5 / 2.0F);
/*     */   }
/*     */   
/*     */   private int heightDependentRadiusEllipse(int $$0, int $$1, int $$2) {
/* 199 */     float $$3 = 1.0F;
/* 200 */     float $$4 = (1.0F - (float)Math.pow($$0, 2.0D) / $$1 * 1.0F) * $$2;
/* 201 */     return Mth.ceil($$4 / 2.0F);
/*     */   }
/*     */   
/*     */   private int heightDependentRadiusSteep(RandomSource $$0, int $$1, int $$2, int $$3) {
/* 205 */     float $$4 = 1.0F + $$0.nextFloat() / 2.0F;
/* 206 */     float $$5 = (1.0F - $$1 / $$2 * $$4) * $$3;
/* 207 */     return Mth.ceil($$5 / 2.0F);
/*     */   }
/*     */   
/*     */   private static boolean isIcebergState(BlockState $$0) {
/* 211 */     return ($$0.is(Blocks.PACKED_ICE) || $$0.is(Blocks.SNOW_BLOCK) || $$0.is(Blocks.BLUE_ICE));
/*     */   }
/*     */   
/*     */   private boolean belowIsAir(BlockGetter $$0, BlockPos $$1) {
/* 215 */     return $$0.getBlockState($$1.below()).isAir();
/*     */   }
/*     */   
/*     */   private void smooth(LevelAccessor $$0, BlockPos $$1, int $$2, int $$3, boolean $$4, int $$5) {
/* 219 */     int $$6 = $$4 ? $$5 : ($$2 / 2);
/*     */     
/* 221 */     for (int $$7 = -$$6; $$7 <= $$6; $$7++) {
/* 222 */       for (int $$8 = -$$6; $$8 <= $$6; $$8++) {
/* 223 */         for (int $$9 = 0; $$9 <= $$3; $$9++) {
/* 224 */           BlockPos $$10 = $$1.offset($$7, $$9, $$8);
/* 225 */           BlockState $$11 = $$0.getBlockState($$10);
/*     */ 
/*     */           
/* 228 */           if (isIcebergState($$11) || $$11.is(Blocks.SNOW))
/* 229 */             if (belowIsAir((BlockGetter)$$0, $$10)) {
/* 230 */               setBlock((LevelWriter)$$0, $$10, Blocks.AIR.defaultBlockState());
/* 231 */               setBlock((LevelWriter)$$0, $$10.above(), Blocks.AIR.defaultBlockState());
/*     */ 
/*     */             
/*     */             }
/* 235 */             else if (isIcebergState($$11)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 241 */               BlockState[] $$12 = { $$0.getBlockState($$10.west()), $$0.getBlockState($$10.east()), $$0.getBlockState($$10.north()), $$0.getBlockState($$10.south()) };
/*     */               
/* 243 */               int $$13 = 0;
/* 244 */               for (BlockState $$14 : $$12) {
/* 245 */                 if (!isIcebergState($$14)) {
/* 246 */                   $$13++;
/*     */                 }
/*     */               } 
/* 249 */               if ($$13 >= 3)
/* 250 */                 setBlock((LevelWriter)$$0, $$10, Blocks.AIR.defaultBlockState()); 
/*     */             }  
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\IcebergFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */