/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.BitSet;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.BulkSectionAccess;
/*     */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
/*     */ 
/*     */ public class OreFeature extends Feature<OreConfiguration> {
/*     */   public OreFeature(Codec<OreConfiguration> $$0) {
/*  20 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<OreConfiguration> $$0) {
/*  25 */     RandomSource $$1 = $$0.random();
/*  26 */     BlockPos $$2 = $$0.origin();
/*  27 */     WorldGenLevel $$3 = $$0.level();
/*  28 */     OreConfiguration $$4 = $$0.config();
/*  29 */     float $$5 = $$1.nextFloat() * 3.1415927F;
/*     */     
/*  31 */     float $$6 = $$4.size / 8.0F;
/*  32 */     int $$7 = Mth.ceil(($$4.size / 16.0F * 2.0F + 1.0F) / 2.0F);
/*  33 */     double $$8 = $$2.getX() + Math.sin($$5) * $$6;
/*  34 */     double $$9 = $$2.getX() - Math.sin($$5) * $$6;
/*  35 */     double $$10 = $$2.getZ() + Math.cos($$5) * $$6;
/*  36 */     double $$11 = $$2.getZ() - Math.cos($$5) * $$6;
/*     */     
/*  38 */     int $$12 = 2;
/*  39 */     double $$13 = ($$2.getY() + $$1.nextInt(3) - 2);
/*  40 */     double $$14 = ($$2.getY() + $$1.nextInt(3) - 2);
/*     */     
/*  42 */     int $$15 = $$2.getX() - Mth.ceil($$6) - $$7;
/*  43 */     int $$16 = $$2.getY() - 2 - $$7;
/*  44 */     int $$17 = $$2.getZ() - Mth.ceil($$6) - $$7;
/*  45 */     int $$18 = 2 * (Mth.ceil($$6) + $$7);
/*  46 */     int $$19 = 2 * (2 + $$7);
/*     */ 
/*     */     
/*  49 */     for (int $$20 = $$15; $$20 <= $$15 + $$18; $$20++) {
/*  50 */       for (int $$21 = $$17; $$21 <= $$17 + $$18; $$21++) {
/*  51 */         if ($$16 <= $$3.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, $$20, $$21)) {
/*  52 */           return doPlace($$3, $$1, $$4, $$8, $$9, $$10, $$11, $$13, $$14, $$15, $$16, $$17, $$18, $$19);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean doPlace(WorldGenLevel $$0, RandomSource $$1, OreConfiguration $$2, double $$3, double $$4, double $$5, double $$6, double $$7, double $$8, int $$9, int $$10, int $$11, int $$12, int $$13) {
/*  61 */     int $$14 = 0;
/*     */     
/*  63 */     BitSet $$15 = new BitSet($$12 * $$13 * $$12);
/*  64 */     BlockPos.MutableBlockPos $$16 = new BlockPos.MutableBlockPos();
/*  65 */     int $$17 = $$2.size;
/*  66 */     double[] $$18 = new double[$$17 * 4];
/*     */     
/*  68 */     for (int $$19 = 0; $$19 < $$17; $$19++) {
/*  69 */       float $$20 = $$19 / $$17;
/*  70 */       double $$21 = Mth.lerp($$20, $$3, $$4);
/*  71 */       double $$22 = Mth.lerp($$20, $$7, $$8);
/*  72 */       double $$23 = Mth.lerp($$20, $$5, $$6);
/*     */       
/*  74 */       double $$24 = $$1.nextDouble() * $$17 / 16.0D;
/*  75 */       double $$25 = ((Mth.sin(3.1415927F * $$20) + 1.0F) * $$24 + 1.0D) / 2.0D;
/*     */       
/*  77 */       $$18[$$19 * 4 + 0] = $$21;
/*  78 */       $$18[$$19 * 4 + 1] = $$22;
/*  79 */       $$18[$$19 * 4 + 2] = $$23;
/*  80 */       $$18[$$19 * 4 + 3] = $$25;
/*     */     } 
/*     */     
/*  83 */     for (int $$26 = 0; $$26 < $$17 - 1; $$26++) {
/*  84 */       if ($$18[$$26 * 4 + 3] > 0.0D)
/*     */       {
/*     */ 
/*     */         
/*  88 */         for (int $$27 = $$26 + 1; $$27 < $$17; $$27++) {
/*  89 */           if ($$18[$$27 * 4 + 3] > 0.0D) {
/*     */ 
/*     */ 
/*     */             
/*  93 */             double $$28 = $$18[$$26 * 4 + 0] - $$18[$$27 * 4 + 0];
/*  94 */             double $$29 = $$18[$$26 * 4 + 1] - $$18[$$27 * 4 + 1];
/*  95 */             double $$30 = $$18[$$26 * 4 + 2] - $$18[$$27 * 4 + 2];
/*  96 */             double $$31 = $$18[$$26 * 4 + 3] - $$18[$$27 * 4 + 3];
/*     */             
/*  98 */             if ($$31 * $$31 > $$28 * $$28 + $$29 * $$29 + $$30 * $$30)
/*  99 */               if ($$31 > 0.0D) {
/* 100 */                 $$18[$$27 * 4 + 3] = -1.0D;
/*     */               } else {
/* 102 */                 $$18[$$26 * 4 + 3] = -1.0D;
/*     */               }  
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 108 */     BulkSectionAccess $$32 = new BulkSectionAccess((LevelAccessor)$$0); 
/* 109 */     try { for (int $$33 = 0; $$33 < $$17; $$33++) {
/* 110 */         double $$34 = $$18[$$33 * 4 + 3];
/* 111 */         if ($$34 >= 0.0D) {
/*     */ 
/*     */ 
/*     */           
/* 115 */           double $$35 = $$18[$$33 * 4 + 0];
/* 116 */           double $$36 = $$18[$$33 * 4 + 1];
/* 117 */           double $$37 = $$18[$$33 * 4 + 2];
/*     */ 
/*     */           
/* 120 */           int $$38 = Math.max(Mth.floor($$35 - $$34), $$9);
/* 121 */           int $$39 = Math.max(Mth.floor($$36 - $$34), $$10);
/* 122 */           int $$40 = Math.max(Mth.floor($$37 - $$34), $$11);
/*     */           
/* 124 */           int $$41 = Math.max(Mth.floor($$35 + $$34), $$38);
/* 125 */           int $$42 = Math.max(Mth.floor($$36 + $$34), $$39);
/* 126 */           int $$43 = Math.max(Mth.floor($$37 + $$34), $$40);
/*     */           
/* 128 */           for (int $$44 = $$38; $$44 <= $$41; $$44++) {
/* 129 */             double $$45 = ($$44 + 0.5D - $$35) / $$34;
/* 130 */             if ($$45 * $$45 < 1.0D)
/* 131 */               for (int $$46 = $$39; $$46 <= $$42; $$46++) {
/* 132 */                 double $$47 = ($$46 + 0.5D - $$36) / $$34;
/* 133 */                 if ($$45 * $$45 + $$47 * $$47 < 1.0D)
/* 134 */                   for (int $$48 = $$40; $$48 <= $$43; $$48++) {
/* 135 */                     double $$49 = ($$48 + 0.5D - $$37) / $$34;
/* 136 */                     if ($$45 * $$45 + $$47 * $$47 + $$49 * $$49 < 1.0D && 
/* 137 */                       !$$0.isOutsideBuildHeight($$46)) {
/*     */ 
/*     */                       
/* 140 */                       int $$50 = $$44 - $$9 + ($$46 - $$10) * $$12 + ($$48 - $$11) * $$12 * $$13;
/* 141 */                       if (!$$15.get($$50)) {
/*     */ 
/*     */                         
/* 144 */                         $$15.set($$50);
/*     */                         
/* 146 */                         $$16.set($$44, $$46, $$48);
/* 147 */                         if ($$0.ensureCanWrite((BlockPos)$$16)) {
/*     */ 
/*     */                           
/* 150 */                           LevelChunkSection $$51 = $$32.getSection((BlockPos)$$16);
/* 151 */                           if ($$51 != null) {
/*     */ 
/*     */                             
/* 154 */                             int $$52 = SectionPos.sectionRelative($$44);
/* 155 */                             int $$53 = SectionPos.sectionRelative($$46);
/* 156 */                             int $$54 = SectionPos.sectionRelative($$48);
/*     */                             
/* 158 */                             BlockState $$55 = $$51.getBlockState($$52, $$53, $$54);
/* 159 */                             for (OreConfiguration.TargetBlockState $$56 : $$2.targetStates) {
/* 160 */                               Objects.requireNonNull($$32); if (canPlaceOre($$55, $$32::getBlockState, $$1, $$2, $$56, $$16)) {
/* 161 */                                 $$51.setBlockState($$52, $$53, $$54, $$56.state, false);
/* 162 */                                 $$14++; break;
/*     */                               } 
/*     */                             } 
/*     */                           } 
/*     */                         } 
/*     */                       } 
/*     */                     } 
/*     */                   }  
/*     */               }  
/*     */           } 
/*     */         } 
/* 173 */       }  $$32.close(); } catch (Throwable throwable) { try { $$32.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 175 */      return ($$14 > 0);
/*     */   }
/*     */   
/*     */   public static boolean canPlaceOre(BlockState $$0, Function<BlockPos, BlockState> $$1, RandomSource $$2, OreConfiguration $$3, OreConfiguration.TargetBlockState $$4, BlockPos.MutableBlockPos $$5) {
/* 179 */     if (!$$4.target.test($$0, $$2)) {
/* 180 */       return false;
/*     */     }
/* 182 */     if (shouldSkipAirCheck($$2, $$3.discardChanceOnAirExposure)) {
/* 183 */       return true;
/*     */     }
/* 185 */     return !isAdjacentToAir($$1, (BlockPos)$$5);
/*     */   }
/*     */   
/*     */   protected static boolean shouldSkipAirCheck(RandomSource $$0, float $$1) {
/* 189 */     if ($$1 <= 0.0F) {
/* 190 */       return true;
/*     */     }
/* 192 */     if ($$1 >= 1.0F) {
/* 193 */       return false;
/*     */     }
/* 195 */     return ($$0.nextFloat() >= $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\OreFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */