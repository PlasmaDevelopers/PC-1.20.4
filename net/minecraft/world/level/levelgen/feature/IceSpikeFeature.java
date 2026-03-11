/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelWriter;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*     */ 
/*     */ public class IceSpikeFeature extends Feature<NoneFeatureConfiguration> {
/*     */   public IceSpikeFeature(Codec<NoneFeatureConfiguration> $$0) {
/*  14 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/*  19 */     BlockPos $$1 = $$0.origin();
/*  20 */     RandomSource $$2 = $$0.random();
/*  21 */     WorldGenLevel $$3 = $$0.level();
/*  22 */     while ($$3.isEmptyBlock($$1) && $$1.getY() > $$3.getMinBuildHeight() + 2) {
/*  23 */       $$1 = $$1.below();
/*     */     }
/*     */     
/*  26 */     if (!$$3.getBlockState($$1).is(Blocks.SNOW_BLOCK)) {
/*  27 */       return false;
/*     */     }
/*  29 */     $$1 = $$1.above($$2.nextInt(4));
/*     */     
/*  31 */     int $$4 = $$2.nextInt(4) + 7;
/*  32 */     int $$5 = $$4 / 4 + $$2.nextInt(2);
/*     */     
/*  34 */     if ($$5 > 1 && $$2.nextInt(60) == 0) {
/*  35 */       $$1 = $$1.above(10 + $$2.nextInt(30));
/*     */     }
/*     */     
/*  38 */     for (int $$6 = 0; $$6 < $$4; $$6++) {
/*  39 */       float $$7 = (1.0F - $$6 / $$4) * $$5;
/*  40 */       int $$8 = Mth.ceil($$7);
/*     */       
/*  42 */       for (int $$9 = -$$8; $$9 <= $$8; $$9++) {
/*  43 */         float $$10 = Mth.abs($$9) - 0.25F;
/*  44 */         for (int $$11 = -$$8; $$11 <= $$8; $$11++) {
/*  45 */           float $$12 = Mth.abs($$11) - 0.25F;
/*  46 */           if (($$9 == 0 && $$11 == 0) || $$10 * $$10 + $$12 * $$12 <= $$7 * $$7)
/*     */           {
/*     */             
/*  49 */             if (($$9 != -$$8 && $$9 != $$8 && $$11 != -$$8 && $$11 != $$8) || 
/*  50 */               $$2.nextFloat() <= 0.75F) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  55 */               BlockState $$13 = $$3.getBlockState($$1.offset($$9, $$6, $$11));
/*     */               
/*  57 */               if ($$13.isAir() || isDirt($$13) || $$13.is(Blocks.SNOW_BLOCK) || $$13.is(Blocks.ICE)) {
/*  58 */                 setBlock((LevelWriter)$$3, $$1.offset($$9, $$6, $$11), Blocks.PACKED_ICE.defaultBlockState());
/*     */               }
/*     */               
/*  61 */               if ($$6 != 0 && $$8 > 1) {
/*  62 */                 $$13 = $$3.getBlockState($$1.offset($$9, -$$6, $$11));
/*     */                 
/*  64 */                 if ($$13.isAir() || isDirt($$13) || $$13.is(Blocks.SNOW_BLOCK) || $$13.is(Blocks.ICE))
/*  65 */                   setBlock((LevelWriter)$$3, $$1.offset($$9, -$$6, $$11), Blocks.PACKED_ICE.defaultBlockState()); 
/*     */               } 
/*     */             }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  71 */     int $$14 = $$5 - 1;
/*  72 */     if ($$14 < 0) {
/*  73 */       $$14 = 0;
/*  74 */     } else if ($$14 > 1) {
/*  75 */       $$14 = 1;
/*     */     } 
/*  77 */     for (int $$15 = -$$14; $$15 <= $$14; $$15++) {
/*  78 */       for (int $$16 = -$$14; $$16 <= $$14; $$16++) {
/*  79 */         BlockPos $$17 = $$1.offset($$15, -1, $$16);
/*  80 */         int $$18 = 50;
/*  81 */         if (Math.abs($$15) == 1 && Math.abs($$16) == 1) {
/*  82 */           $$18 = $$2.nextInt(5);
/*     */         }
/*  84 */         while ($$17.getY() > 50) {
/*  85 */           BlockState $$19 = $$3.getBlockState($$17);
/*     */           
/*  87 */           if ($$19.isAir() || isDirt($$19) || $$19.is(Blocks.SNOW_BLOCK) || $$19.is(Blocks.ICE) || $$19.is(Blocks.PACKED_ICE)) {
/*  88 */             setBlock((LevelWriter)$$3, $$17, Blocks.PACKED_ICE.defaultBlockState());
/*     */ 
/*     */ 
/*     */             
/*  92 */             $$17 = $$17.below();
/*  93 */             $$18--;
/*  94 */             if ($$18 <= 0) {
/*  95 */               $$17 = $$17.below($$2.nextInt(5) + 1);
/*  96 */               $$18 = $$2.nextInt(5);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\IceSpikeFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */