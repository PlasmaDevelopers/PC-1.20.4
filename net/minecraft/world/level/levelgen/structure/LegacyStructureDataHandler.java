/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.storage.DimensionDataStorage;
/*     */ 
/*     */ public class LegacyStructureDataHandler {
/*     */   private static final Map<String, String> CURRENT_TO_LEGACY_MAP;
/*     */   
/*     */   static {
/*  27 */     CURRENT_TO_LEGACY_MAP = (Map<String, String>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put("Village", "Village");
/*     */           
/*     */           $$0.put("Mineshaft", "Mineshaft");
/*     */           
/*     */           $$0.put("Mansion", "Mansion");
/*     */           $$0.put("Igloo", "Temple");
/*     */           $$0.put("Desert_Pyramid", "Temple");
/*     */           $$0.put("Jungle_Pyramid", "Temple");
/*     */           $$0.put("Swamp_Hut", "Temple");
/*     */           $$0.put("Stronghold", "Stronghold");
/*     */           $$0.put("Monument", "Monument");
/*     */           $$0.put("Fortress", "Fortress");
/*     */           $$0.put("EndCity", "EndCity");
/*     */         });
/*  42 */     LEGACY_TO_CURRENT_MAP = (Map<String, String>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put("Iglu", "Igloo");
/*     */           $$0.put("TeDP", "Desert_Pyramid");
/*     */           $$0.put("TeJP", "Jungle_Pyramid");
/*     */           $$0.put("TeSH", "Swamp_Hut");
/*     */         });
/*     */   }
/*     */   private static final Map<String, String> LEGACY_TO_CURRENT_MAP;
/*  50 */   private static final Set<String> OLD_STRUCTURE_REGISTRY_KEYS = Set.of(new String[] { "pillager_outpost", "mineshaft", "mansion", "jungle_pyramid", "desert_pyramid", "igloo", "ruined_portal", "shipwreck", "swamp_hut", "stronghold", "monument", "ocean_ruin", "fortress", "endcity", "buried_treasure", "village", "nether_fossil", "bastion_remnant" });
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean hasLegacyData;
/*     */ 
/*     */   
/*     */   private final Map<String, Long2ObjectMap<CompoundTag>> dataMap;
/*     */ 
/*     */   
/*     */   private final Map<String, StructureFeatureIndexSavedData> indexMap;
/*     */ 
/*     */   
/*     */   private final List<String> legacyKeys;
/*     */ 
/*     */   
/*     */   private final List<String> currentKeys;
/*     */ 
/*     */ 
/*     */   
/*     */   public LegacyStructureDataHandler(@Nullable DimensionDataStorage $$0, List<String> $$1, List<String> $$2) {
/*     */     int i;
/*  72 */     this.dataMap = Maps.newHashMap();
/*  73 */     this.indexMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     this.legacyKeys = $$1;
/*  79 */     this.currentKeys = $$2;
/*  80 */     populateCaches($$0);
/*     */ 
/*     */     
/*  83 */     boolean $$3 = false;
/*  84 */     for (String $$4 : this.currentKeys) {
/*  85 */       i = $$3 | ((this.dataMap.get($$4) != null) ? 1 : 0);
/*     */     }
/*  87 */     this.hasLegacyData = i;
/*     */   }
/*     */   
/*     */   public void removeIndex(long $$0) {
/*  91 */     for (String $$1 : this.legacyKeys) {
/*  92 */       StructureFeatureIndexSavedData $$2 = this.indexMap.get($$1);
/*  93 */       if ($$2 != null && $$2.hasUnhandledIndex($$0)) {
/*  94 */         $$2.removeIndex($$0);
/*  95 */         $$2.setDirty();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public CompoundTag updateFromLegacy(CompoundTag $$0) {
/* 101 */     CompoundTag $$1 = $$0.getCompound("Level");
/*     */     
/* 103 */     ChunkPos $$2 = new ChunkPos($$1.getInt("xPos"), $$1.getInt("zPos"));
/*     */     
/* 105 */     if (isUnhandledStructureStart($$2.x, $$2.z)) {
/* 106 */       $$0 = updateStructureStart($$0, $$2);
/*     */     }
/*     */     
/* 109 */     CompoundTag $$3 = $$1.getCompound("Structures");
/* 110 */     CompoundTag $$4 = $$3.getCompound("References");
/*     */     
/* 112 */     for (String $$5 : this.currentKeys) {
/* 113 */       boolean $$6 = OLD_STRUCTURE_REGISTRY_KEYS.contains($$5.toLowerCase(Locale.ROOT));
/*     */       
/* 115 */       if ($$4.contains($$5, 12) || !$$6) {
/*     */         continue;
/*     */       }
/*     */       
/* 119 */       int $$7 = 8;
/* 120 */       LongArrayList longArrayList = new LongArrayList();
/*     */       
/* 122 */       for (int $$9 = $$2.x - 8; $$9 <= $$2.x + 8; $$9++) {
/* 123 */         for (int $$10 = $$2.z - 8; $$10 <= $$2.z + 8; $$10++) {
/* 124 */           if (hasLegacyStart($$9, $$10, $$5)) {
/* 125 */             longArrayList.add(ChunkPos.asLong($$9, $$10));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 130 */       $$4.putLongArray($$5, (List)longArrayList);
/*     */     } 
/*     */     
/* 133 */     $$3.put("References", (Tag)$$4);
/* 134 */     $$1.put("Structures", (Tag)$$3);
/* 135 */     $$0.put("Level", (Tag)$$1);
/*     */     
/* 137 */     return $$0;
/*     */   }
/*     */   
/*     */   private boolean hasLegacyStart(int $$0, int $$1, String $$2) {
/* 141 */     if (!this.hasLegacyData) {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     if (this.dataMap.get($$2) != null && ((StructureFeatureIndexSavedData)this.indexMap.get(CURRENT_TO_LEGACY_MAP.get($$2))).hasStartIndex(ChunkPos.asLong($$0, $$1))) {
/* 146 */       return true;
/*     */     }
/*     */     
/* 149 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isUnhandledStructureStart(int $$0, int $$1) {
/* 153 */     if (!this.hasLegacyData) {
/* 154 */       return false;
/*     */     }
/*     */     
/* 157 */     for (String $$2 : this.currentKeys) {
/* 158 */       if (this.dataMap.get($$2) != null && ((StructureFeatureIndexSavedData)this.indexMap.get(CURRENT_TO_LEGACY_MAP.get($$2))).hasUnhandledIndex(ChunkPos.asLong($$0, $$1))) {
/* 159 */         return true;
/*     */       }
/*     */     } 
/* 162 */     return false;
/*     */   }
/*     */   
/*     */   private CompoundTag updateStructureStart(CompoundTag $$0, ChunkPos $$1) {
/* 166 */     CompoundTag $$2 = $$0.getCompound("Level");
/* 167 */     CompoundTag $$3 = $$2.getCompound("Structures");
/* 168 */     CompoundTag $$4 = $$3.getCompound("Starts");
/*     */     
/* 170 */     for (String $$5 : this.currentKeys) {
/* 171 */       Long2ObjectMap<CompoundTag> $$6 = this.dataMap.get($$5);
/* 172 */       if ($$6 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 176 */       long $$7 = $$1.toLong();
/*     */       
/* 178 */       if (!((StructureFeatureIndexSavedData)this.indexMap.get(CURRENT_TO_LEGACY_MAP.get($$5))).hasUnhandledIndex($$7)) {
/*     */         continue;
/*     */       }
/*     */       
/* 182 */       CompoundTag $$8 = (CompoundTag)$$6.get($$7);
/* 183 */       if ($$8 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 187 */       $$4.put($$5, (Tag)$$8);
/*     */     } 
/*     */     
/* 190 */     $$3.put("Starts", (Tag)$$4);
/* 191 */     $$2.put("Structures", (Tag)$$3);
/* 192 */     $$0.put("Level", (Tag)$$2);
/*     */     
/* 194 */     return $$0;
/*     */   }
/*     */   
/*     */   private void populateCaches(@Nullable DimensionDataStorage $$0) {
/* 198 */     if ($$0 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 202 */     for (String $$1 : this.legacyKeys) {
/* 203 */       CompoundTag $$2 = new CompoundTag();
/*     */       try {
/* 205 */         $$2 = $$0.readTagFromDisk($$1, DataFixTypes.SAVED_DATA_STRUCTURE_FEATURE_INDICES, 1493).getCompound("data").getCompound("Features");
/* 206 */         if ($$2.isEmpty()) {
/*     */           continue;
/*     */         }
/* 209 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/* 212 */       for (String $$3 : $$2.getAllKeys()) {
/* 213 */         CompoundTag $$4 = $$2.getCompound($$3);
/* 214 */         long $$5 = ChunkPos.asLong($$4.getInt("ChunkX"), $$4.getInt("ChunkZ"));
/*     */ 
/*     */         
/* 217 */         ListTag $$6 = $$4.getList("Children", 10);
/* 218 */         if (!$$6.isEmpty()) {
/* 219 */           String $$7 = $$6.getCompound(0).getString("id");
/* 220 */           String $$8 = LEGACY_TO_CURRENT_MAP.get($$7);
/* 221 */           if ($$8 != null) {
/* 222 */             $$4.putString("id", $$8);
/*     */           }
/*     */         } 
/*     */         
/* 226 */         String $$9 = $$4.getString("id");
/*     */         
/* 228 */         ((Long2ObjectMap)this.dataMap.computeIfAbsent($$9, $$0 -> new Long2ObjectOpenHashMap())).put($$5, $$4);
/*     */       } 
/*     */       
/* 231 */       String $$10 = $$1 + "_index";
/* 232 */       StructureFeatureIndexSavedData $$11 = (StructureFeatureIndexSavedData)$$0.computeIfAbsent(StructureFeatureIndexSavedData.factory(), $$10);
/*     */       
/* 234 */       if ($$11.getAll().isEmpty()) {
/*     */         
/* 236 */         StructureFeatureIndexSavedData $$12 = new StructureFeatureIndexSavedData();
/* 237 */         this.indexMap.put($$1, $$12);
/* 238 */         for (String $$13 : $$2.getAllKeys()) {
/* 239 */           CompoundTag $$14 = $$2.getCompound($$13);
/* 240 */           $$12.addIndex(ChunkPos.asLong($$14.getInt("ChunkX"), $$14.getInt("ChunkZ")));
/*     */         } 
/* 242 */         $$12.setDirty(); continue;
/*     */       } 
/* 244 */       this.indexMap.put($$1, $$11);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static LegacyStructureDataHandler getLegacyStructureHandler(ResourceKey<Level> $$0, @Nullable DimensionDataStorage $$1) {
/* 250 */     if ($$0 == Level.OVERWORLD) {
/* 251 */       return new LegacyStructureDataHandler($$1, 
/*     */           
/* 253 */           (List<String>)ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 261 */           (List<String>)ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     if ($$0 == Level.NETHER) {
/* 274 */       ImmutableList immutableList = ImmutableList.of("Fortress");
/* 275 */       return new LegacyStructureDataHandler($$1, (List<String>)immutableList, (List<String>)immutableList);
/* 276 */     }  if ($$0 == Level.END) {
/* 277 */       ImmutableList immutableList = ImmutableList.of("EndCity");
/* 278 */       return new LegacyStructureDataHandler($$1, (List<String>)immutableList, (List<String>)immutableList);
/*     */     } 
/* 280 */     throw new RuntimeException(String.format(Locale.ROOT, "Unknown dimension type : %s", new Object[] { $$0 }));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\LegacyStructureDataHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */