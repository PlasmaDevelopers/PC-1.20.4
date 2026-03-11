/*     */ package net.minecraft.world.level.levelgen;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.BitSet;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.LongStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeResolver;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.ProtoChunk;
/*     */ 
/*     */ public final class BelowZeroRetrogen {
/*  30 */   private static final BitSet EMPTY = new BitSet(0); private static final Codec<BitSet> BITSET_CODEC;
/*     */   static {
/*  32 */     BITSET_CODEC = Codec.LONG_STREAM.xmap($$0 -> BitSet.valueOf($$0.toArray()), $$0 -> LongStream.of($$0.toLongArray()));
/*  33 */     NON_EMPTY_CHUNK_STATUS = BuiltInRegistries.CHUNK_STATUS.byNameCodec().comapFlatMap($$0 -> ($$0 == ChunkStatus.EMPTY) ? DataResult.error(()) : DataResult.success($$0), 
/*     */         
/*  35 */         Function.identity());
/*     */ 
/*     */     
/*  38 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)NON_EMPTY_CHUNK_STATUS.fieldOf("target_status").forGetter(BelowZeroRetrogen::targetStatus), (App)BITSET_CODEC.optionalFieldOf("missing_bedrock").forGetter(())).apply((Applicative)$$0, BelowZeroRetrogen::new));
/*     */   }
/*     */   
/*     */   private static final Codec<ChunkStatus> NON_EMPTY_CHUNK_STATUS;
/*     */   public static final Codec<BelowZeroRetrogen> CODEC;
/*  43 */   private static final Set<ResourceKey<Biome>> RETAINED_RETROGEN_BIOMES = Set.of(Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES, Biomes.DEEP_DARK);
/*  44 */   public static final LevelHeightAccessor UPGRADE_HEIGHT_ACCESSOR = new LevelHeightAccessor()
/*     */     {
/*     */       public int getHeight() {
/*  47 */         return 64;
/*     */       }
/*     */ 
/*     */       
/*     */       public int getMinBuildHeight() {
/*  52 */         return -64;
/*     */       }
/*     */     };
/*     */   
/*     */   private final ChunkStatus targetStatus;
/*     */   private final BitSet missingBedrock;
/*     */   
/*     */   private BelowZeroRetrogen(ChunkStatus $$0, Optional<BitSet> $$1) {
/*  60 */     this.targetStatus = $$0;
/*  61 */     this.missingBedrock = $$1.orElse(EMPTY);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BelowZeroRetrogen read(CompoundTag $$0) {
/*  66 */     ChunkStatus $$1 = ChunkStatus.byName($$0.getString("target_status"));
/*  67 */     if ($$1 == ChunkStatus.EMPTY) {
/*  68 */       return null;
/*     */     }
/*     */     
/*  71 */     return new BelowZeroRetrogen($$1, 
/*     */         
/*  73 */         Optional.of(BitSet.valueOf($$0.getLongArray("missing_bedrock"))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void replaceOldBedrock(ProtoChunk $$0) {
/*  78 */     int $$1 = 4;
/*  79 */     BlockPos.betweenClosed(0, 0, 0, 15, 4, 15).forEach($$1 -> {
/*     */           if ($$0.getBlockState($$1).is(Blocks.BEDROCK)) {
/*     */             $$0.setBlockState($$1, Blocks.DEEPSLATE.defaultBlockState(), false);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void applyBedrockMask(ProtoChunk $$0) {
/*  87 */     LevelHeightAccessor $$1 = $$0.getHeightAccessorForGeneration();
/*  88 */     int $$2 = $$1.getMinBuildHeight();
/*  89 */     int $$3 = $$1.getMaxBuildHeight() - 1;
/*     */     
/*  91 */     for (int $$4 = 0; $$4 < 16; $$4++) {
/*  92 */       for (int $$5 = 0; $$5 < 16; $$5++) {
/*  93 */         if (hasBedrockHole($$4, $$5)) {
/*  94 */           BlockPos.betweenClosed($$4, $$2, $$5, $$4, $$3, $$5).forEach($$1 -> $$0.setBlockState($$1, Blocks.AIR.defaultBlockState(), false));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ChunkStatus targetStatus() {
/* 101 */     return this.targetStatus;
/*     */   }
/*     */   
/*     */   public boolean hasBedrockHoles() {
/* 105 */     return !this.missingBedrock.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean hasBedrockHole(int $$0, int $$1) {
/* 109 */     return this.missingBedrock.get(($$1 & 0xF) * 16 + ($$0 & 0xF));
/*     */   }
/*     */ 
/*     */   
/*     */   public static BiomeResolver getBiomeResolver(BiomeResolver $$0, ChunkAccess $$1) {
/* 114 */     if (!$$1.isUpgrading()) {
/* 115 */       return $$0;
/*     */     }
/*     */     
/* 118 */     Objects.requireNonNull(RETAINED_RETROGEN_BIOMES); Predicate<ResourceKey<Biome>> $$2 = RETAINED_RETROGEN_BIOMES::contains;
/*     */     
/* 120 */     return ($$3, $$4, $$5, $$6) -> {
/*     */         Holder<Biome> $$7 = $$0.getNoiseBiome($$3, $$4, $$5, $$6);
/*     */         return $$7.is($$1) ? $$7 : $$2.getNoiseBiome($$3, 0, $$5);
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\BelowZeroRetrogen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */