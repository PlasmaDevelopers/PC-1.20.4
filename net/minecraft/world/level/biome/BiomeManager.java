/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.google.common.hash.Hashing;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.util.LinearCongruentialGenerator;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class BiomeManager
/*     */ {
/*  12 */   public static final int CHUNK_CENTER_QUART = QuartPos.fromBlock(8);
/*     */   
/*     */   private static final int ZOOM_BITS = 2;
/*     */   
/*     */   private static final int ZOOM = 4;
/*     */   private static final int ZOOM_MASK = 3;
/*     */   private final NoiseBiomeSource noiseBiomeSource;
/*     */   private final long biomeZoomSeed;
/*     */   
/*     */   public BiomeManager(NoiseBiomeSource $$0, long $$1) {
/*  22 */     this.noiseBiomeSource = $$0;
/*  23 */     this.biomeZoomSeed = $$1;
/*     */   }
/*     */   
/*     */   public static long obfuscateSeed(long $$0) {
/*  27 */     return Hashing.sha256().hashLong($$0).asLong();
/*     */   }
/*     */   
/*     */   public BiomeManager withDifferentSource(NoiseBiomeSource $$0) {
/*  31 */     return new BiomeManager($$0, this.biomeZoomSeed);
/*     */   }
/*     */   
/*     */   public Holder<Biome> getBiome(BlockPos $$0) {
/*  35 */     int $$1 = $$0.getX() - 2;
/*  36 */     int $$2 = $$0.getY() - 2;
/*  37 */     int $$3 = $$0.getZ() - 2;
/*     */     
/*  39 */     int $$4 = $$1 >> 2;
/*  40 */     int $$5 = $$2 >> 2;
/*  41 */     int $$6 = $$3 >> 2;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  46 */     double $$7 = ($$1 & 0x3) / 4.0D;
/*  47 */     double $$8 = ($$2 & 0x3) / 4.0D;
/*  48 */     double $$9 = ($$3 & 0x3) / 4.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     int $$10 = 0;
/*  54 */     double $$11 = Double.POSITIVE_INFINITY;
/*  55 */     for (int $$12 = 0; $$12 < 8; $$12++) {
/*  56 */       boolean $$13 = (($$12 & 0x4) == 0);
/*  57 */       boolean $$14 = (($$12 & 0x2) == 0);
/*  58 */       boolean $$15 = (($$12 & 0x1) == 0);
/*     */       
/*  60 */       int $$16 = $$13 ? $$4 : ($$4 + 1);
/*  61 */       int $$17 = $$14 ? $$5 : ($$5 + 1);
/*  62 */       int $$18 = $$15 ? $$6 : ($$6 + 1);
/*     */       
/*  64 */       double $$19 = $$13 ? $$7 : ($$7 - 1.0D);
/*  65 */       double $$20 = $$14 ? $$8 : ($$8 - 1.0D);
/*  66 */       double $$21 = $$15 ? $$9 : ($$9 - 1.0D);
/*     */       
/*  68 */       double $$22 = getFiddledDistance(this.biomeZoomSeed, $$16, $$17, $$18, $$19, $$20, $$21);
/*  69 */       if ($$11 > $$22) {
/*  70 */         $$10 = $$12;
/*  71 */         $$11 = $$22;
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     int $$23 = (($$10 & 0x4) == 0) ? $$4 : ($$4 + 1);
/*  76 */     int $$24 = (($$10 & 0x2) == 0) ? $$5 : ($$5 + 1);
/*  77 */     int $$25 = (($$10 & 0x1) == 0) ? $$6 : ($$6 + 1);
/*     */     
/*  79 */     return this.noiseBiomeSource.getNoiseBiome($$23, $$24, $$25);
/*     */   }
/*     */   
/*     */   public Holder<Biome> getNoiseBiomeAtPosition(double $$0, double $$1, double $$2) {
/*  83 */     int $$3 = QuartPos.fromBlock(Mth.floor($$0));
/*  84 */     int $$4 = QuartPos.fromBlock(Mth.floor($$1));
/*  85 */     int $$5 = QuartPos.fromBlock(Mth.floor($$2));
/*  86 */     return getNoiseBiomeAtQuart($$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public Holder<Biome> getNoiseBiomeAtPosition(BlockPos $$0) {
/*  90 */     int $$1 = QuartPos.fromBlock($$0.getX());
/*  91 */     int $$2 = QuartPos.fromBlock($$0.getY());
/*  92 */     int $$3 = QuartPos.fromBlock($$0.getZ());
/*  93 */     return getNoiseBiomeAtQuart($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public Holder<Biome> getNoiseBiomeAtQuart(int $$0, int $$1, int $$2) {
/*  97 */     return this.noiseBiomeSource.getNoiseBiome($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private static double getFiddledDistance(long $$0, int $$1, int $$2, int $$3, double $$4, double $$5, double $$6) {
/* 101 */     long $$7 = $$0;
/*     */     
/* 103 */     $$7 = LinearCongruentialGenerator.next($$7, $$1);
/* 104 */     $$7 = LinearCongruentialGenerator.next($$7, $$2);
/* 105 */     $$7 = LinearCongruentialGenerator.next($$7, $$3);
/* 106 */     $$7 = LinearCongruentialGenerator.next($$7, $$1);
/* 107 */     $$7 = LinearCongruentialGenerator.next($$7, $$2);
/* 108 */     $$7 = LinearCongruentialGenerator.next($$7, $$3);
/*     */     
/* 110 */     double $$8 = getFiddle($$7);
/*     */     
/* 112 */     $$7 = LinearCongruentialGenerator.next($$7, $$0);
/*     */     
/* 114 */     double $$9 = getFiddle($$7);
/*     */     
/* 116 */     $$7 = LinearCongruentialGenerator.next($$7, $$0);
/*     */     
/* 118 */     double $$10 = getFiddle($$7);
/*     */     
/* 120 */     return Mth.square($$6 + $$10) + Mth.square($$5 + $$9) + Mth.square($$4 + $$8);
/*     */   }
/*     */   
/*     */   private static double getFiddle(long $$0) {
/* 124 */     double $$1 = Math.floorMod($$0 >> 24L, 1024) / 1024.0D;
/* 125 */     return ($$1 - 0.5D) * 0.9D;
/*     */   }
/*     */   
/*     */   public static interface NoiseBiomeSource {
/*     */     Holder<Biome> getNoiseBiome(int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */