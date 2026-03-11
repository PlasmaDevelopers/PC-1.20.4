/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelWriter;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.FireBlock;
/*     */ import net.minecraft.world.level.block.IronBarsBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ public class SpikeFeature extends Feature<SpikeConfiguration> {
/*     */   public static final int NUMBER_OF_SPIKES = 10;
/*  34 */   private static final LoadingCache<Long, List<EndSpike>> SPIKE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new SpikeCacheLoader()); private static final int SPIKE_DISTANCE = 42;
/*     */   
/*     */   public SpikeFeature(Codec<SpikeConfiguration> $$0) {
/*  37 */     super($$0);
/*     */   }
/*     */   
/*     */   public static List<EndSpike> getSpikesForLevel(WorldGenLevel $$0) {
/*  41 */     RandomSource $$1 = RandomSource.create($$0.getSeed());
/*  42 */     long $$2 = $$1.nextLong() & 0xFFFFL;
/*  43 */     return (List<EndSpike>)SPIKE_CACHE.getUnchecked(Long.valueOf($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<SpikeConfiguration> $$0) {
/*  48 */     SpikeConfiguration $$1 = $$0.config();
/*  49 */     WorldGenLevel $$2 = $$0.level();
/*  50 */     RandomSource $$3 = $$0.random();
/*  51 */     BlockPos $$4 = $$0.origin();
/*  52 */     List<EndSpike> $$5 = $$1.getSpikes();
/*  53 */     if ($$5.isEmpty()) {
/*  54 */       $$5 = getSpikesForLevel($$2);
/*     */     }
/*     */     
/*  57 */     for (EndSpike $$6 : $$5) {
/*  58 */       if ($$6.isCenterWithinChunk($$4)) {
/*  59 */         placeSpike((ServerLevelAccessor)$$2, $$3, $$1, $$6);
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return true;
/*     */   }
/*     */   
/*     */   private void placeSpike(ServerLevelAccessor $$0, RandomSource $$1, SpikeConfiguration $$2, EndSpike $$3) {
/*  67 */     int $$4 = $$3.getRadius();
/*  68 */     for (BlockPos $$5 : BlockPos.betweenClosed(new BlockPos($$3.getCenterX() - $$4, $$0.getMinBuildHeight(), $$3.getCenterZ() - $$4), new BlockPos($$3.getCenterX() + $$4, $$3.getHeight() + 10, $$3.getCenterZ() + $$4))) {
/*  69 */       if ($$5.distToLowCornerSqr($$3.getCenterX(), $$5.getY(), $$3.getCenterZ()) <= ($$4 * $$4 + 1) && $$5.getY() < $$3.getHeight()) {
/*  70 */         setBlock((LevelWriter)$$0, $$5, Blocks.OBSIDIAN.defaultBlockState()); continue;
/*  71 */       }  if ($$5.getY() > 65) {
/*  72 */         setBlock((LevelWriter)$$0, $$5, Blocks.AIR.defaultBlockState());
/*     */       }
/*     */     } 
/*     */     
/*  76 */     if ($$3.isGuarded()) {
/*  77 */       int $$6 = -2;
/*  78 */       int $$7 = 2;
/*  79 */       int $$8 = 3;
/*     */       
/*  81 */       BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
/*  82 */       for (int $$10 = -2; $$10 <= 2; $$10++) {
/*  83 */         for (int $$11 = -2; $$11 <= 2; $$11++) {
/*  84 */           for (int $$12 = 0; $$12 <= 3; $$12++) {
/*  85 */             boolean $$13 = (Mth.abs($$10) == 2);
/*  86 */             boolean $$14 = (Mth.abs($$11) == 2);
/*  87 */             boolean $$15 = ($$12 == 3);
/*     */             
/*  89 */             if ($$13 || $$14 || $$15) {
/*  90 */               boolean $$16 = ($$10 == -2 || $$10 == 2 || $$15);
/*  91 */               boolean $$17 = ($$11 == -2 || $$11 == 2 || $$15);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  97 */               BlockState $$18 = (BlockState)((BlockState)((BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(($$16 && $$11 != -2)))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(($$16 && $$11 != 2)))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(($$17 && $$10 != -2)))).setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(($$17 && $$10 != 2)));
/*     */               
/*  99 */               setBlock((LevelWriter)$$0, (BlockPos)$$9.set($$3.getCenterX() + $$10, $$3.getHeight() + $$12, $$3.getCenterZ() + $$11), $$18);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     EndCrystal $$19 = (EndCrystal)EntityType.END_CRYSTAL.create((Level)$$0.getLevel());
/* 107 */     if ($$19 != null) {
/* 108 */       $$19.setBeamTarget($$2.getCrystalBeamTarget());
/* 109 */       $$19.setInvulnerable($$2.isCrystalInvulnerable());
/* 110 */       $$19.moveTo($$3.getCenterX() + 0.5D, ($$3.getHeight() + 1), $$3.getCenterZ() + 0.5D, $$1.nextFloat() * 360.0F, 0.0F);
/* 111 */       $$0.addFreshEntity((Entity)$$19);
/*     */       
/* 113 */       BlockPos $$20 = $$19.blockPosition();
/* 114 */       setBlock((LevelWriter)$$0, $$20.below(), Blocks.BEDROCK.defaultBlockState());
/* 115 */       setBlock((LevelWriter)$$0, $$20, FireBlock.getState((BlockGetter)$$0, $$20));
/*     */     } 
/*     */   }
/*     */   public static class EndSpike { public static final Codec<EndSpike> CODEC; private final int centerX; private final int centerZ;
/*     */     static {
/* 120 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("centerX").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.INT.fieldOf("centerZ").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.INT.fieldOf("radius").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.INT.fieldOf("height").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.BOOL.fieldOf("guarded").orElse(Boolean.valueOf(false)).forGetter(())).apply((Applicative)$$0, EndSpike::new));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final int radius;
/*     */ 
/*     */     
/*     */     private final int height;
/*     */     
/*     */     private final boolean guarded;
/*     */     
/*     */     private final AABB topBoundingBox;
/*     */ 
/*     */     
/*     */     public EndSpike(int $$0, int $$1, int $$2, int $$3, boolean $$4) {
/* 136 */       this.centerX = $$0;
/* 137 */       this.centerZ = $$1;
/* 138 */       this.radius = $$2;
/* 139 */       this.height = $$3;
/* 140 */       this.guarded = $$4;
/*     */       
/* 142 */       this.topBoundingBox = new AABB(($$0 - $$2), DimensionType.MIN_Y, ($$1 - $$2), ($$0 + $$2), DimensionType.MAX_Y, ($$1 + $$2));
/*     */     }
/*     */     
/*     */     public boolean isCenterWithinChunk(BlockPos $$0) {
/* 146 */       return (SectionPos.blockToSectionCoord($$0.getX()) == SectionPos.blockToSectionCoord(this.centerX) && 
/* 147 */         SectionPos.blockToSectionCoord($$0.getZ()) == SectionPos.blockToSectionCoord(this.centerZ));
/*     */     }
/*     */     
/*     */     public int getCenterX() {
/* 151 */       return this.centerX;
/*     */     }
/*     */     
/*     */     public int getCenterZ() {
/* 155 */       return this.centerZ;
/*     */     }
/*     */     
/*     */     public int getRadius() {
/* 159 */       return this.radius;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 163 */       return this.height;
/*     */     }
/*     */     
/*     */     public boolean isGuarded() {
/* 167 */       return this.guarded;
/*     */     }
/*     */     
/*     */     public AABB getTopBoundingBox() {
/* 171 */       return this.topBoundingBox;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class SpikeCacheLoader
/*     */     extends CacheLoader<Long, List<EndSpike>> {
/*     */     public List<SpikeFeature.EndSpike> load(Long $$0) {
/* 178 */       IntArrayList $$1 = Util.toShuffledList(IntStream.range(0, 10), RandomSource.create($$0.longValue()));
/*     */       
/* 180 */       List<SpikeFeature.EndSpike> $$2 = Lists.newArrayList();
/* 181 */       for (int $$3 = 0; $$3 < 10; $$3++) {
/* 182 */         int $$4 = Mth.floor(42.0D * Math.cos(2.0D * (-3.141592653589793D + 0.3141592653589793D * $$3)));
/* 183 */         int $$5 = Mth.floor(42.0D * Math.sin(2.0D * (-3.141592653589793D + 0.3141592653589793D * $$3)));
/* 184 */         int $$6 = $$1.get($$3).intValue();
/* 185 */         int $$7 = 2 + $$6 / 3;
/* 186 */         int $$8 = 76 + $$6 * 3;
/* 187 */         boolean $$9 = ($$6 == 1 || $$6 == 2);
/* 188 */         $$2.add(new SpikeFeature.EndSpike($$4, $$5, $$7, $$8, $$9));
/*     */       } 
/* 190 */       return $$2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SpikeFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */