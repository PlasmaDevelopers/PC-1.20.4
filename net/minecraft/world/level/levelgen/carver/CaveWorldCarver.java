/*     */ package net.minecraft.world.level.levelgen.carver;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.chunk.CarvingMask;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.levelgen.Aquifer;
/*     */ 
/*     */ public class CaveWorldCarver
/*     */   extends WorldCarver<CaveCarverConfiguration> {
/*     */   public CaveWorldCarver(Codec<CaveCarverConfiguration> $$0) {
/*  19 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStartChunk(CaveCarverConfiguration $$0, RandomSource $$1) {
/*  24 */     return ($$1.nextFloat() <= $$0.probability);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean carve(CarvingContext $$0, CaveCarverConfiguration $$1, ChunkAccess $$2, Function<BlockPos, Holder<Biome>> $$3, RandomSource $$4, Aquifer $$5, ChunkPos $$6, CarvingMask $$7) {
/*  29 */     int $$8 = SectionPos.sectionToBlockCoord(getRange() * 2 - 1);
/*  30 */     int $$9 = $$4.nextInt($$4.nextInt($$4.nextInt(getCaveBound()) + 1) + 1);
/*     */     
/*  32 */     for (int $$10 = 0; $$10 < $$9; $$10++) {
/*     */       
/*  34 */       double $$11 = $$6.getBlockX($$4.nextInt(16));
/*  35 */       double $$12 = $$1.y.sample($$4, $$0);
/*  36 */       double $$13 = $$6.getBlockZ($$4.nextInt(16));
/*     */       
/*  38 */       double $$14 = $$1.horizontalRadiusMultiplier.sample($$4);
/*  39 */       double $$15 = $$1.verticalRadiusMultiplier.sample($$4);
/*  40 */       double $$16 = $$1.floorLevel.sample($$4);
/*  41 */       WorldCarver.CarveSkipChecker $$17 = ($$1, $$2, $$3, $$4, $$5) -> shouldSkip($$2, $$3, $$4, $$0);
/*     */       
/*  43 */       int $$18 = 1;
/*  44 */       if ($$4.nextInt(4) == 0) {
/*     */         
/*  46 */         double $$19 = $$1.yScale.sample($$4);
/*  47 */         float $$20 = 1.0F + $$4.nextFloat() * 6.0F;
/*  48 */         createRoom($$0, $$1, $$2, $$3, $$5, $$11, $$12, $$13, $$20, $$19, $$7, $$17);
/*  49 */         $$18 += $$4.nextInt(4);
/*     */       } 
/*     */       
/*  52 */       for (int $$21 = 0; $$21 < $$18; $$21++) {
/*     */         
/*  54 */         float $$22 = $$4.nextFloat() * 6.2831855F;
/*  55 */         float $$23 = ($$4.nextFloat() - 0.5F) / 4.0F;
/*  56 */         float $$24 = getThickness($$4);
/*  57 */         int $$25 = $$8 - $$4.nextInt($$8 / 4);
/*  58 */         int $$26 = 0;
/*  59 */         createTunnel($$0, $$1, $$2, $$3, $$4.nextLong(), $$5, $$11, $$12, $$13, $$14, $$15, $$24, $$22, $$23, 0, $$25, getYScale(), $$7, $$17);
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     return true;
/*     */   }
/*     */   
/*     */   protected int getCaveBound() {
/*  67 */     return 15;
/*     */   }
/*     */   
/*     */   protected float getThickness(RandomSource $$0) {
/*  71 */     float $$1 = $$0.nextFloat() * 2.0F + $$0.nextFloat();
/*  72 */     if ($$0.nextInt(10) == 0) {
/*  73 */       $$1 *= $$0.nextFloat() * $$0.nextFloat() * 3.0F + 1.0F;
/*     */     }
/*  75 */     return $$1;
/*     */   }
/*     */   
/*     */   protected double getYScale() {
/*  79 */     return 1.0D;
/*     */   }
/*     */   
/*     */   protected void createRoom(CarvingContext $$0, CaveCarverConfiguration $$1, ChunkAccess $$2, Function<BlockPos, Holder<Biome>> $$3, Aquifer $$4, double $$5, double $$6, double $$7, float $$8, double $$9, CarvingMask $$10, WorldCarver.CarveSkipChecker $$11) {
/*  83 */     double $$12 = 1.5D + (Mth.sin(1.5707964F) * $$8);
/*  84 */     double $$13 = $$12 * $$9;
/*     */ 
/*     */ 
/*     */     
/*  88 */     carveEllipsoid($$0, $$1, $$2, $$3, $$4, $$5 + 1.0D, $$6, $$7, $$12, $$13, $$10, $$11);
/*     */   }
/*     */   
/*     */   protected void createTunnel(CarvingContext $$0, CaveCarverConfiguration $$1, ChunkAccess $$2, Function<BlockPos, Holder<Biome>> $$3, long $$4, Aquifer $$5, double $$6, double $$7, double $$8, double $$9, double $$10, float $$11, float $$12, float $$13, int $$14, int $$15, double $$16, CarvingMask $$17, WorldCarver.CarveSkipChecker $$18) {
/*  92 */     RandomSource $$19 = RandomSource.create($$4);
/*     */     
/*  94 */     int $$20 = $$19.nextInt($$15 / 2) + $$15 / 4;
/*  95 */     boolean $$21 = ($$19.nextInt(6) == 0);
/*     */     
/*  97 */     float $$22 = 0.0F;
/*  98 */     float $$23 = 0.0F;
/*     */     
/* 100 */     for (int $$24 = $$14; $$24 < $$15; $$24++) {
/* 101 */       double $$25 = 1.5D + (Mth.sin(3.1415927F * $$24 / $$15) * $$11);
/* 102 */       double $$26 = $$25 * $$16;
/*     */       
/* 104 */       float $$27 = Mth.cos($$13);
/* 105 */       $$6 += (Mth.cos($$12) * $$27);
/* 106 */       $$7 += Mth.sin($$13);
/* 107 */       $$8 += (Mth.sin($$12) * $$27);
/*     */       
/* 109 */       $$13 *= $$21 ? 0.92F : 0.7F;
/* 110 */       $$13 += $$23 * 0.1F;
/* 111 */       $$12 += $$22 * 0.1F;
/*     */       
/* 113 */       $$23 *= 0.9F;
/* 114 */       $$22 *= 0.75F;
/* 115 */       $$23 += ($$19.nextFloat() - $$19.nextFloat()) * $$19.nextFloat() * 2.0F;
/* 116 */       $$22 += ($$19.nextFloat() - $$19.nextFloat()) * $$19.nextFloat() * 4.0F;
/*     */ 
/*     */       
/* 119 */       if ($$24 == $$20 && $$11 > 1.0F) {
/* 120 */         createTunnel($$0, $$1, $$2, $$3, $$19.nextLong(), $$5, $$6, $$7, $$8, $$9, $$10, $$19.nextFloat() * 0.5F + 0.5F, $$12 - 1.5707964F, $$13 / 3.0F, $$24, $$15, 1.0D, $$17, $$18);
/* 121 */         createTunnel($$0, $$1, $$2, $$3, $$19.nextLong(), $$5, $$6, $$7, $$8, $$9, $$10, $$19.nextFloat() * 0.5F + 0.5F, $$12 + 1.5707964F, $$13 / 3.0F, $$24, $$15, 1.0D, $$17, $$18);
/*     */         
/*     */         return;
/*     */       } 
/* 125 */       if ($$19.nextInt(4) != 0) {
/*     */ 
/*     */ 
/*     */         
/* 129 */         if (!canReach($$2.getPos(), $$6, $$8, $$24, $$15, $$11)) {
/*     */           return;
/*     */         }
/*     */         
/* 133 */         carveEllipsoid($$0, $$1, $$2, $$3, $$5, $$6, $$7, $$8, $$25 * $$9, $$26 * $$10, $$17, $$18);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean shouldSkip(double $$0, double $$1, double $$2, double $$3) {
/* 139 */     if ($$1 <= $$3) {
/* 140 */       return true;
/*     */     }
/*     */     
/* 143 */     return ($$0 * $$0 + $$1 * $$1 + $$2 * $$2 >= 1.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\CaveWorldCarver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */