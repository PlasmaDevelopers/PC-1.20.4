/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ 
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.IntTag;
/*     */ import net.minecraft.nbt.StreamTagVisitor;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.nbt.visitors.CollectFields;
/*     */ import net.minecraft.nbt.visitors.FieldSelector;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
/*     */ import net.minecraft.world.level.chunk.storage.ChunkStorage;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class StructureCheck {
/*  41 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int NO_STRUCTURE = -1;
/*     */   
/*     */   private final ChunkScanAccess storageAccess;
/*     */   
/*     */   private final RegistryAccess registryAccess;
/*     */   private final Registry<Biome> biomes;
/*     */   private final Registry<Structure> structureConfigs;
/*     */   private final StructureTemplateManager structureTemplateManager;
/*     */   private final ResourceKey<Level> dimension;
/*     */   private final ChunkGenerator chunkGenerator;
/*     */   private final RandomState randomState;
/*     */   private final LevelHeightAccessor heightAccessor;
/*     */   private final BiomeSource biomeSource;
/*     */   private final long seed;
/*     */   private final DataFixer fixerUpper;
/*  58 */   private final Long2ObjectMap<Object2IntMap<Structure>> loadedChunks = (Long2ObjectMap<Object2IntMap<Structure>>)new Long2ObjectOpenHashMap();
/*  59 */   private final Map<Structure, Long2BooleanMap> featureChecks = new HashMap<>();
/*     */   
/*     */   public StructureCheck(ChunkScanAccess $$0, RegistryAccess $$1, StructureTemplateManager $$2, ResourceKey<Level> $$3, ChunkGenerator $$4, RandomState $$5, LevelHeightAccessor $$6, BiomeSource $$7, long $$8, DataFixer $$9) {
/*  62 */     this.storageAccess = $$0;
/*  63 */     this.registryAccess = $$1;
/*  64 */     this.structureTemplateManager = $$2;
/*  65 */     this.dimension = $$3;
/*  66 */     this.chunkGenerator = $$4;
/*  67 */     this.randomState = $$5;
/*  68 */     this.heightAccessor = $$6;
/*  69 */     this.biomeSource = $$7;
/*  70 */     this.seed = $$8;
/*  71 */     this.fixerUpper = $$9;
/*  72 */     this.biomes = $$1.registryOrThrow(Registries.BIOME);
/*  73 */     this.structureConfigs = $$1.registryOrThrow(Registries.STRUCTURE);
/*     */   }
/*     */   
/*     */   public StructureCheckResult checkStart(ChunkPos $$0, Structure $$1, boolean $$2) {
/*  77 */     long $$3 = $$0.toLong();
/*  78 */     Object2IntMap<Structure> $$4 = (Object2IntMap<Structure>)this.loadedChunks.get($$3);
/*  79 */     if ($$4 != null) {
/*  80 */       return checkStructureInfo($$4, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*  84 */     StructureCheckResult $$5 = tryLoadFromStorage($$0, $$1, $$2, $$3);
/*  85 */     if ($$5 != null)
/*     */     {
/*  87 */       return $$5;
/*     */     }
/*     */ 
/*     */     
/*  91 */     boolean $$6 = ((Long2BooleanMap)this.featureChecks.computeIfAbsent($$1, $$0 -> new Long2BooleanOpenHashMap())).computeIfAbsent($$3, $$2 -> canCreateStructure($$0, $$1));
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (!$$6)
/*     */     {
/*  97 */       return StructureCheckResult.START_NOT_PRESENT;
/*     */     }
/*     */ 
/*     */     
/* 101 */     return StructureCheckResult.CHUNK_LOAD_NEEDED;
/*     */   }
/*     */   
/*     */   private boolean canCreateStructure(ChunkPos $$0, Structure $$1) {
/* 105 */     Objects.requireNonNull($$1.biomes()); return $$1.findValidGenerationPoint(new Structure.GenerationContext(this.registryAccess, this.chunkGenerator, this.biomeSource, this.randomState, this.structureTemplateManager, this.seed, $$0, this.heightAccessor, $$1.biomes()::contains)).isPresent();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private StructureCheckResult tryLoadFromStorage(ChunkPos $$0, Structure $$1, boolean $$2, long $$3) {
/*     */     CompoundTag $$9;
/* 113 */     CollectFields $$4 = new CollectFields(new FieldSelector[] { new FieldSelector(IntTag.TYPE, "DataVersion"), new FieldSelector("Level", "Structures", CompoundTag.TYPE, "Starts"), new FieldSelector("structures", CompoundTag.TYPE, "starts") });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 120 */       this.storageAccess.scanChunk($$0, (StreamTagVisitor)$$4).join();
/* 121 */     } catch (Exception $$5) {
/* 122 */       LOGGER.warn("Failed to read chunk {}", $$0, $$5);
/* 123 */       return StructureCheckResult.CHUNK_LOAD_NEEDED;
/*     */     } 
/* 125 */     Tag $$6 = $$4.getResult();
/* 126 */     if (!($$6 instanceof CompoundTag))
/*     */     {
/* 128 */       return null;
/*     */     }
/*     */     
/* 131 */     CompoundTag $$7 = (CompoundTag)$$6;
/* 132 */     int $$8 = ChunkStorage.getVersion($$7);
/*     */     
/* 134 */     if ($$8 <= 1493)
/*     */     {
/* 136 */       return StructureCheckResult.CHUNK_LOAD_NEEDED;
/*     */     }
/*     */     
/* 139 */     ChunkStorage.injectDatafixingContext($$7, this.dimension, this.chunkGenerator.getTypeNameForDataFixer());
/*     */ 
/*     */     
/*     */     try {
/* 143 */       $$9 = DataFixTypes.CHUNK.updateToCurrentVersion(this.fixerUpper, $$7, $$8);
/* 144 */     } catch (Exception $$10) {
/* 145 */       LOGGER.warn("Failed to partially datafix chunk {}", $$0, $$10);
/*     */       
/* 147 */       return StructureCheckResult.CHUNK_LOAD_NEEDED;
/*     */     } 
/*     */     
/* 150 */     Object2IntMap<Structure> $$12 = loadStructures($$9);
/* 151 */     if ($$12 == null)
/*     */     {
/* 153 */       return null;
/*     */     }
/*     */     
/* 156 */     storeFullResults($$3, $$12);
/* 157 */     return checkStructureInfo($$12, $$1, $$2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Object2IntMap<Structure> loadStructures(CompoundTag $$0) {
/* 162 */     if (!$$0.contains("structures", 10)) {
/* 163 */       return null;
/*     */     }
/* 165 */     CompoundTag $$1 = $$0.getCompound("structures");
/* 166 */     if (!$$1.contains("starts", 10)) {
/* 167 */       return null;
/*     */     }
/* 169 */     CompoundTag $$2 = $$1.getCompound("starts");
/* 170 */     if ($$2.isEmpty()) {
/* 171 */       return Object2IntMaps.emptyMap();
/*     */     }
/*     */     
/* 174 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/* 175 */     Registry<Structure> $$4 = this.registryAccess.registryOrThrow(Registries.STRUCTURE);
/* 176 */     for (String $$5 : $$2.getAllKeys()) {
/* 177 */       ResourceLocation $$6 = ResourceLocation.tryParse($$5);
/* 178 */       if ($$6 == null) {
/*     */         continue;
/*     */       }
/* 181 */       Structure $$7 = (Structure)$$4.get($$6);
/* 182 */       if ($$7 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 186 */       CompoundTag $$8 = $$2.getCompound($$5);
/*     */       
/* 188 */       if (!$$8.isEmpty()) {
/* 189 */         String $$9 = $$8.getString("id");
/* 190 */         if (!"INVALID".equals($$9)) {
/* 191 */           int $$10 = $$8.getInt("references");
/* 192 */           object2IntOpenHashMap.put($$7, $$10);
/*     */         } 
/*     */       } 
/*     */     } 
/* 196 */     return (Object2IntMap<Structure>)object2IntOpenHashMap;
/*     */   }
/*     */   
/*     */   private static Object2IntMap<Structure> deduplicateEmptyMap(Object2IntMap<Structure> $$0) {
/* 200 */     return $$0.isEmpty() ? Object2IntMaps.emptyMap() : $$0;
/*     */   }
/*     */   
/*     */   private StructureCheckResult checkStructureInfo(Object2IntMap<Structure> $$0, Structure $$1, boolean $$2) {
/* 204 */     int $$3 = $$0.getOrDefault($$1, -1);
/*     */     
/* 206 */     return ($$3 != -1 && (!$$2 || $$3 == 0)) ? StructureCheckResult.START_PRESENT : StructureCheckResult.START_NOT_PRESENT;
/*     */   }
/*     */   
/*     */   public void onStructureLoad(ChunkPos $$0, Map<Structure, StructureStart> $$1) {
/* 210 */     long $$2 = $$0.toLong();
/*     */     
/* 212 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/* 213 */     $$1.forEach(($$1, $$2) -> {
/*     */           if ($$2.isValid()) {
/*     */             $$0.put($$1, $$2.getReferences());
/*     */           }
/*     */         });
/* 218 */     storeFullResults($$2, (Object2IntMap<Structure>)object2IntOpenHashMap);
/*     */   }
/*     */   
/*     */   private void storeFullResults(long $$0, Object2IntMap<Structure> $$1) {
/* 222 */     this.loadedChunks.put($$0, deduplicateEmptyMap($$1));
/*     */ 
/*     */     
/* 225 */     this.featureChecks.values().forEach($$1 -> $$1.remove($$0));
/*     */   }
/*     */   
/*     */   public void incrementReference(ChunkPos $$0, Structure $$1) {
/* 229 */     this.loadedChunks.compute($$0.toLong(), ($$1, $$2) -> {
/*     */           Object2IntOpenHashMap object2IntOpenHashMap;
/*     */           if ($$2 == null || $$2.isEmpty())
/*     */             object2IntOpenHashMap = new Object2IntOpenHashMap(); 
/*     */           object2IntOpenHashMap.computeInt($$0, ());
/*     */           return (Object2IntMap)object2IntOpenHashMap;
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\StructureCheck.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */