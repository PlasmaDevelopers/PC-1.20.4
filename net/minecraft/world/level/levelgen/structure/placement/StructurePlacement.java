/*     */ package net.minecraft.world.level.levelgen.structure.placement;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.RegistryFileCodec;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
/*     */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ 
/*     */ public abstract class StructurePlacement {
/*  23 */   public static final Codec<StructurePlacement> CODEC = BuiltInRegistries.STRUCTURE_PLACEMENT.byNameCodec().dispatch(StructurePlacement::type, StructurePlacementType::codec); private static final int HIGHLY_ARBITRARY_RANDOM_SALT = 10387320; private final Vec3i locateOffset; private final FrequencyReductionMethod frequencyReductionMethod; private final float frequency;
/*     */   private final int salt;
/*     */   private final Optional<ExclusionZone> exclusionZone;
/*     */   
/*  27 */   protected static <S extends StructurePlacement> Products.P5<RecordCodecBuilder.Mu<S>, Vec3i, FrequencyReductionMethod, Float, Integer, Optional<ExclusionZone>> placementCodec(RecordCodecBuilder.Instance<S> $$0) { return $$0.group(
/*  28 */         (App)Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(StructurePlacement::locateOffset), (App)FrequencyReductionMethod.CODEC
/*  29 */         .optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT).forGetter(StructurePlacement::frequencyReductionMethod), 
/*  30 */         (App)Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", Float.valueOf(1.0F)).forGetter(StructurePlacement::frequency), (App)ExtraCodecs.NON_NEGATIVE_INT
/*  31 */         .fieldOf("salt").forGetter(StructurePlacement::salt), (App)ExclusionZone.CODEC
/*  32 */         .optionalFieldOf("exclusion_zone").forGetter(StructurePlacement::exclusionZone)); }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface FrequencyReducer {
/*     */     boolean shouldGenerate(long param1Long, int param1Int1, int param1Int2, int param1Int3, float param1Float); }
/*     */   @Deprecated
/*     */   public static final class ExclusionZone extends Record { private final Holder<StructureSet> otherSet; private final int chunkCount; public static final Codec<ExclusionZone> CODEC;
/*  39 */     public Holder<StructureSet> otherSet() { return this.otherSet; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #39	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  39 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone; } public int chunkCount() { return this.chunkCount; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #39	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #39	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;
/*  40 */       //   0	8	1	$$0	Ljava/lang/Object; } public ExclusionZone(Holder<StructureSet> $$0, int $$1) { this.otherSet = $$0; this.chunkCount = $$1; } static {
/*  41 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RegistryFileCodec.create(Registries.STRUCTURE_SET, StructureSet.DIRECT_CODEC, false).fieldOf("other_set").forGetter(ExclusionZone::otherSet), (App)Codec.intRange(1, 16).fieldOf("chunk_count").forGetter(ExclusionZone::chunkCount)).apply((Applicative)$$0, ExclusionZone::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isPlacementForbidden(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
/*  48 */       return $$0.hasStructureChunkInRange(this.otherSet, $$1, $$2, this.chunkCount);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructurePlacement(Vec3i $$0, FrequencyReductionMethod $$1, float $$2, int $$3, Optional<ExclusionZone> $$4) {
/*  59 */     this.locateOffset = $$0;
/*  60 */     this.frequencyReductionMethod = $$1;
/*  61 */     this.frequency = $$2;
/*  62 */     this.salt = $$3;
/*  63 */     this.exclusionZone = $$4;
/*     */   }
/*     */   
/*     */   protected Vec3i locateOffset() {
/*  67 */     return this.locateOffset;
/*     */   }
/*     */   
/*     */   protected FrequencyReductionMethod frequencyReductionMethod() {
/*  71 */     return this.frequencyReductionMethod;
/*     */   }
/*     */   
/*     */   protected float frequency() {
/*  75 */     return this.frequency;
/*     */   }
/*     */   
/*     */   protected int salt() {
/*  79 */     return this.salt;
/*     */   }
/*     */   
/*     */   protected Optional<ExclusionZone> exclusionZone() {
/*  83 */     return this.exclusionZone;
/*     */   }
/*     */   
/*     */   public boolean isStructureChunk(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
/*  87 */     if (!isPlacementChunk($$0, $$1, $$2)) {
/*  88 */       return false;
/*     */     }
/*  90 */     if (this.frequency < 1.0F && !this.frequencyReductionMethod.shouldGenerate($$0.getLevelSeed(), this.salt, $$1, $$2, this.frequency)) {
/*  91 */       return false;
/*     */     }
/*  93 */     if (this.exclusionZone.isPresent() && ((ExclusionZone)this.exclusionZone.get()).isPlacementForbidden($$0, $$1, $$2)) {
/*  94 */       return false;
/*     */     }
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   protected abstract boolean isPlacementChunk(ChunkGeneratorStructureState paramChunkGeneratorStructureState, int paramInt1, int paramInt2);
/*     */   
/*     */   public BlockPos getLocatePos(ChunkPos $$0) {
/* 102 */     return (new BlockPos($$0.getMinBlockX(), 0, $$0.getMinBlockZ())).offset(locateOffset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract StructurePlacementType<?> type();
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean probabilityReducer(long $$0, int $$1, int $$2, int $$3, float $$4) {
/* 113 */     WorldgenRandom $$5 = new WorldgenRandom((RandomSource)new LegacyRandomSource(0L));
/* 114 */     $$5.setLargeFeatureWithSalt($$0, $$1, $$2, $$3);
/* 115 */     return ($$5.nextFloat() < $$4);
/*     */   }
/*     */   
/*     */   private static boolean legacyProbabilityReducerWithDouble(long $$0, int $$1, int $$2, int $$3, float $$4) {
/* 119 */     WorldgenRandom $$5 = new WorldgenRandom((RandomSource)new LegacyRandomSource(0L));
/* 120 */     $$5.setLargeFeatureSeed($$0, $$2, $$3);
/* 121 */     return ($$5.nextDouble() < $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean legacyArbitrarySaltProbabilityReducer(long $$0, int $$1, int $$2, int $$3, float $$4) {
/* 126 */     WorldgenRandom $$5 = new WorldgenRandom((RandomSource)new LegacyRandomSource(0L));
/* 127 */     $$5.setLargeFeatureWithSalt($$0, $$2, $$3, 10387320);
/* 128 */     return ($$5.nextFloat() < $$4);
/*     */   }
/*     */   
/*     */   private static boolean legacyPillagerOutpostReducer(long $$0, int $$1, int $$2, int $$3, float $$4) {
/* 132 */     int $$5 = $$2 >> 4;
/* 133 */     int $$6 = $$3 >> 4;
/*     */ 
/*     */     
/* 136 */     WorldgenRandom $$7 = new WorldgenRandom((RandomSource)new LegacyRandomSource(0L));
/* 137 */     $$7.setSeed(($$5 ^ $$6 << 4) ^ $$0);
/* 138 */     $$7.nextInt();
/*     */     
/* 140 */     return ($$7.nextInt((int)(1.0F / $$4)) == 0);
/*     */   }
/*     */   
/*     */   public enum FrequencyReductionMethod implements StringRepresentable {
/* 144 */     DEFAULT("default", StructurePlacement::probabilityReducer),
/* 145 */     LEGACY_TYPE_1("legacy_type_1", StructurePlacement::legacyPillagerOutpostReducer),
/* 146 */     LEGACY_TYPE_2("legacy_type_2", StructurePlacement::legacyArbitrarySaltProbabilityReducer),
/* 147 */     LEGACY_TYPE_3("legacy_type_3", StructurePlacement::legacyProbabilityReducerWithDouble);
/*     */ 
/*     */     
/* 150 */     public static final Codec<FrequencyReductionMethod> CODEC = (Codec<FrequencyReductionMethod>)StringRepresentable.fromEnum(FrequencyReductionMethod::values); private final String name; private final StructurePlacement.FrequencyReducer reducer;
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     FrequencyReductionMethod(String $$0, StructurePlacement.FrequencyReducer $$1) {
/* 156 */       this.name = $$0;
/* 157 */       this.reducer = $$1;
/*     */     }
/*     */     
/*     */     public boolean shouldGenerate(long $$0, int $$1, int $$2, int $$3, float $$4) {
/* 161 */       return this.reducer.shouldGenerate($$0, $$1, $$2, $$3, $$4);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 166 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\placement\StructurePlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */