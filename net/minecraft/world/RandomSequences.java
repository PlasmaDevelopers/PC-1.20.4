/*     */ package net.minecraft.world;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.levelgen.PositionalRandomFactory;
/*     */ import net.minecraft.world.level.saveddata.SavedData;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RandomSequences extends SavedData {
/*  21 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private final long worldSeed;
/*     */   private int salt;
/*     */   private boolean includeWorldSeed = true;
/*     */   private boolean includeSequenceId = true;
/*  26 */   private final Map<ResourceLocation, RandomSequence> sequences = (Map<ResourceLocation, RandomSequence>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   public static SavedData.Factory<RandomSequences> factory(long $$0) {
/*  29 */     return new SavedData.Factory(() -> new RandomSequences($$0), $$1 -> load($$0, $$1), DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES);
/*     */   }
/*     */   
/*     */   public RandomSequences(long $$0) {
/*  33 */     this.worldSeed = $$0;
/*     */   }
/*     */   
/*     */   private class DirtyMarkingRandomSource implements RandomSource {
/*     */     private final RandomSource random;
/*     */     
/*     */     DirtyMarkingRandomSource(RandomSource $$0) {
/*  40 */       this.random = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public RandomSource fork() {
/*  45 */       RandomSequences.this.setDirty();
/*  46 */       return this.random.fork();
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionalRandomFactory forkPositional() {
/*  51 */       RandomSequences.this.setDirty();
/*  52 */       return this.random.forkPositional();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSeed(long $$0) {
/*  57 */       RandomSequences.this.setDirty();
/*  58 */       this.random.setSeed($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int nextInt() {
/*  63 */       RandomSequences.this.setDirty();
/*  64 */       return this.random.nextInt();
/*     */     }
/*     */ 
/*     */     
/*     */     public int nextInt(int $$0) {
/*  69 */       RandomSequences.this.setDirty();
/*  70 */       return this.random.nextInt($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextLong() {
/*  75 */       RandomSequences.this.setDirty();
/*  76 */       return this.random.nextLong();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean nextBoolean() {
/*  81 */       RandomSequences.this.setDirty();
/*  82 */       return this.random.nextBoolean();
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/*  87 */       RandomSequences.this.setDirty();
/*  88 */       return this.random.nextFloat();
/*     */     }
/*     */ 
/*     */     
/*     */     public double nextDouble() {
/*  93 */       RandomSequences.this.setDirty();
/*  94 */       return this.random.nextDouble();
/*     */     }
/*     */ 
/*     */     
/*     */     public double nextGaussian() {
/*  99 */       RandomSequences.this.setDirty();
/* 100 */       return this.random.nextGaussian();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 105 */       if (this == $$0) {
/* 106 */         return true;
/*     */       }
/* 108 */       if ($$0 instanceof DirtyMarkingRandomSource) { DirtyMarkingRandomSource $$1 = (DirtyMarkingRandomSource)$$0;
/* 109 */         return this.random.equals($$1.random); }
/*     */       
/* 111 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public RandomSource get(ResourceLocation $$0) {
/* 116 */     RandomSource $$1 = ((RandomSequence)this.sequences.computeIfAbsent($$0, this::createSequence)).random();
/* 117 */     return new DirtyMarkingRandomSource($$1);
/*     */   }
/*     */   
/*     */   private RandomSequence createSequence(ResourceLocation $$0) {
/* 121 */     return createSequence($$0, this.salt, this.includeWorldSeed, this.includeSequenceId);
/*     */   }
/*     */   
/*     */   private RandomSequence createSequence(ResourceLocation $$0, int $$1, boolean $$2, boolean $$3) {
/* 125 */     long $$4 = ($$2 ? this.worldSeed : 0L) ^ $$1;
/* 126 */     return new RandomSequence($$4, $$3 ? Optional.<ResourceLocation>of($$0) : Optional.<ResourceLocation>empty());
/*     */   }
/*     */   
/*     */   public void forAllSequences(BiConsumer<ResourceLocation, RandomSequence> $$0) {
/* 130 */     this.sequences.forEach($$0);
/*     */   }
/*     */   
/*     */   public void setSeedDefaults(int $$0, boolean $$1, boolean $$2) {
/* 134 */     this.salt = $$0;
/* 135 */     this.includeWorldSeed = $$1;
/* 136 */     this.includeSequenceId = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 141 */     $$0.putInt("salt", this.salt);
/* 142 */     $$0.putBoolean("include_world_seed", this.includeWorldSeed);
/* 143 */     $$0.putBoolean("include_sequence_id", this.includeSequenceId);
/* 144 */     CompoundTag $$1 = new CompoundTag();
/* 145 */     this.sequences.forEach(($$1, $$2) -> $$0.put($$1.toString(), RandomSequence.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$2).result().orElseThrow()));
/* 146 */     $$0.put("sequences", (Tag)$$1);
/* 147 */     return $$0;
/*     */   }
/*     */   
/*     */   private static boolean getBooleanWithDefault(CompoundTag $$0, String $$1, boolean $$2) {
/* 151 */     if ($$0.contains($$1, 1)) {
/* 152 */       return $$0.getBoolean($$1);
/*     */     }
/* 154 */     return $$2;
/*     */   }
/*     */   
/*     */   public static RandomSequences load(long $$0, CompoundTag $$1) {
/* 158 */     RandomSequences $$2 = new RandomSequences($$0);
/* 159 */     $$2.setSeedDefaults($$1
/* 160 */         .getInt("salt"), 
/* 161 */         getBooleanWithDefault($$1, "include_world_seed", true), 
/* 162 */         getBooleanWithDefault($$1, "include_sequence_id", true));
/*     */     
/* 164 */     CompoundTag $$3 = $$1.getCompound("sequences");
/* 165 */     Set<String> $$4 = $$3.getAllKeys();
/* 166 */     for (String $$5 : $$4) {
/*     */       try {
/* 168 */         RandomSequence $$6 = (RandomSequence)((Pair)RandomSequence.CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$3.get($$5)).result().get()).getFirst();
/* 169 */         $$2.sequences.put(new ResourceLocation($$5), $$6);
/* 170 */       } catch (Exception $$7) {
/* 171 */         LOGGER.error("Failed to load random sequence {}", $$5, $$7);
/*     */       } 
/*     */     } 
/* 174 */     return $$2;
/*     */   }
/*     */   
/*     */   public int clear() {
/* 178 */     int $$0 = this.sequences.size();
/* 179 */     this.sequences.clear();
/* 180 */     return $$0;
/*     */   }
/*     */   
/*     */   public void reset(ResourceLocation $$0) {
/* 184 */     this.sequences.put($$0, createSequence($$0));
/*     */   }
/*     */   
/*     */   public void reset(ResourceLocation $$0, int $$1, boolean $$2, boolean $$3) {
/* 188 */     this.sequences.put($$0, createSequence($$0, $$1, $$2, $$3));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\RandomSequences.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */