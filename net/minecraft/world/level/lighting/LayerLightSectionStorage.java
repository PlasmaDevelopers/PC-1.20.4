/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.chunk.DataLayer;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LayerLightSectionStorage<M extends DataLayerStorageMap<M>>
/*     */ {
/*     */   private final LightLayer layer;
/*     */   protected final LightChunkGetter chunkSource;
/*  27 */   protected final Long2ByteMap sectionStates = (Long2ByteMap)new Long2ByteOpenHashMap();
/*     */   
/*  29 */   private final LongSet columnsWithSources = (LongSet)new LongOpenHashSet();
/*     */   
/*     */   protected volatile M visibleSectionData;
/*     */   
/*     */   protected final M updatingSectionData;
/*     */   
/*  35 */   protected final LongSet changedSections = (LongSet)new LongOpenHashSet();
/*  36 */   protected final LongSet sectionsAffectedByLightUpdates = (LongSet)new LongOpenHashSet();
/*     */ 
/*     */   
/*  39 */   protected final Long2ObjectMap<DataLayer> queuedSections = Long2ObjectMaps.synchronize((Long2ObjectMap)new Long2ObjectOpenHashMap());
/*     */   
/*  41 */   private final LongSet columnsToRetainQueuedDataFor = (LongSet)new LongOpenHashSet();
/*     */   
/*  43 */   private final LongSet toRemove = (LongSet)new LongOpenHashSet();
/*     */   
/*     */   protected volatile boolean hasInconsistencies;
/*     */   
/*     */   protected LayerLightSectionStorage(LightLayer $$0, LightChunkGetter $$1, M $$2) {
/*  48 */     this.layer = $$0;
/*  49 */     this.chunkSource = $$1;
/*  50 */     this.updatingSectionData = $$2;
/*  51 */     this.visibleSectionData = $$2.copy();
/*  52 */     this.visibleSectionData.disableCache();
/*  53 */     this.sectionStates.defaultReturnValue((byte)0);
/*     */   }
/*     */   
/*     */   protected boolean storingLightForSection(long $$0) {
/*  57 */     return (getDataLayer($$0, true) != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected DataLayer getDataLayer(long $$0, boolean $$1) {
/*  62 */     return getDataLayer($$1 ? this.updatingSectionData : this.visibleSectionData, $$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected DataLayer getDataLayer(M $$0, long $$1) {
/*  67 */     return $$0.getLayer($$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected DataLayer getDataLayerToWrite(long $$0) {
/*  72 */     DataLayer $$1 = this.updatingSectionData.getLayer($$0);
/*  73 */     if ($$1 == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     if (this.changedSections.add($$0)) {
/*  77 */       $$1 = $$1.copy();
/*  78 */       this.updatingSectionData.setLayer($$0, $$1);
/*  79 */       this.updatingSectionData.clearCache();
/*     */     } 
/*  81 */     return $$1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DataLayer getDataLayerData(long $$0) {
/*  86 */     DataLayer $$1 = (DataLayer)this.queuedSections.get($$0);
/*  87 */     if ($$1 != null) {
/*  88 */       return $$1;
/*     */     }
/*  90 */     return getDataLayer($$0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getLightValue(long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getStoredLevel(long $$0) {
/* 100 */     long $$1 = SectionPos.blockToSection($$0);
/* 101 */     DataLayer $$2 = getDataLayer($$1, true);
/* 102 */     return $$2.get(
/* 103 */         SectionPos.sectionRelative(BlockPos.getX($$0)), 
/* 104 */         SectionPos.sectionRelative(BlockPos.getY($$0)), 
/* 105 */         SectionPos.sectionRelative(BlockPos.getZ($$0)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setStoredLevel(long $$0, int $$1) {
/*     */     DataLayer $$4;
/* 112 */     long $$2 = SectionPos.blockToSection($$0);
/*     */     
/* 114 */     if (this.changedSections.add($$2)) {
/* 115 */       DataLayer $$3 = this.updatingSectionData.copyDataLayer($$2);
/*     */     } else {
/* 117 */       $$4 = getDataLayer($$2, true);
/*     */     } 
/* 119 */     $$4.set(
/* 120 */         SectionPos.sectionRelative(BlockPos.getX($$0)), 
/* 121 */         SectionPos.sectionRelative(BlockPos.getY($$0)), 
/* 122 */         SectionPos.sectionRelative(BlockPos.getZ($$0)), $$1);
/*     */ 
/*     */     
/* 125 */     Objects.requireNonNull(this.sectionsAffectedByLightUpdates); SectionPos.aroundAndAtBlockPos($$0, this.sectionsAffectedByLightUpdates::add);
/*     */   }
/*     */   
/*     */   protected void markSectionAndNeighborsAsAffected(long $$0) {
/* 129 */     int $$1 = SectionPos.x($$0);
/* 130 */     int $$2 = SectionPos.y($$0);
/* 131 */     int $$3 = SectionPos.z($$0);
/*     */     
/* 133 */     for (int $$4 = -1; $$4 <= 1; $$4++) {
/* 134 */       for (int $$5 = -1; $$5 <= 1; $$5++) {
/* 135 */         for (int $$6 = -1; $$6 <= 1; $$6++) {
/* 136 */           this.sectionsAffectedByLightUpdates.add(SectionPos.asLong($$1 + $$5, $$2 + $$6, $$3 + $$4));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected DataLayer createDataLayer(long $$0) {
/* 143 */     DataLayer $$1 = (DataLayer)this.queuedSections.get($$0);
/* 144 */     if ($$1 != null) {
/* 145 */       return $$1;
/*     */     }
/* 147 */     return new DataLayer();
/*     */   }
/*     */   
/*     */   protected boolean hasInconsistencies() {
/* 151 */     return this.hasInconsistencies;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void markNewInconsistencies(LightEngine<M, ?> $$0) {
/* 156 */     if (!this.hasInconsistencies) {
/*     */       return;
/*     */     }
/* 159 */     this.hasInconsistencies = false;
/*     */     LongIterator<Long> longIterator;
/* 161 */     for (longIterator = this.toRemove.iterator(); longIterator.hasNext(); ) { long $$1 = ((Long)longIterator.next()).longValue();
/* 162 */       DataLayer $$2 = (DataLayer)this.queuedSections.remove($$1);
/* 163 */       DataLayer $$3 = this.updatingSectionData.removeLayer($$1);
/* 164 */       if (this.columnsToRetainQueuedDataFor.contains(SectionPos.getZeroNode($$1))) {
/* 165 */         if ($$2 != null) {
/* 166 */           this.queuedSections.put($$1, $$2); continue;
/* 167 */         }  if ($$3 != null) {
/* 168 */           this.queuedSections.put($$1, $$3);
/*     */         }
/*     */       }  }
/*     */     
/* 172 */     this.updatingSectionData.clearCache();
/* 173 */     for (longIterator = this.toRemove.iterator(); longIterator.hasNext(); ) { long $$4 = ((Long)longIterator.next()).longValue();
/* 174 */       onNodeRemoved($$4);
/* 175 */       this.changedSections.add($$4); }
/*     */     
/* 177 */     this.toRemove.clear();
/*     */     
/* 179 */     ObjectIterator<Long2ObjectMap.Entry<DataLayer>> $$5 = Long2ObjectMaps.fastIterator(this.queuedSections);
/* 180 */     while ($$5.hasNext()) {
/* 181 */       Long2ObjectMap.Entry<DataLayer> $$6 = (Long2ObjectMap.Entry<DataLayer>)$$5.next();
/* 182 */       long $$7 = $$6.getLongKey();
/* 183 */       if (!storingLightForSection($$7)) {
/*     */         continue;
/*     */       }
/* 186 */       DataLayer $$8 = (DataLayer)$$6.getValue();
/*     */       
/* 188 */       if (this.updatingSectionData.getLayer($$7) != $$8) {
/*     */         
/* 190 */         this.updatingSectionData.setLayer($$7, $$8);
/* 191 */         this.changedSections.add($$7);
/*     */       } 
/* 193 */       $$5.remove();
/*     */     } 
/* 195 */     this.updatingSectionData.clearCache();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onNodeAdded(long $$0) {}
/*     */ 
/*     */   
/*     */   protected void onNodeRemoved(long $$0) {}
/*     */   
/*     */   protected void setLightEnabled(long $$0, boolean $$1) {
/* 205 */     if ($$1) {
/* 206 */       this.columnsWithSources.add($$0);
/*     */     } else {
/* 208 */       this.columnsWithSources.remove($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean lightOnInSection(long $$0) {
/* 213 */     long $$1 = SectionPos.getZeroNode($$0);
/* 214 */     return this.columnsWithSources.contains($$1);
/*     */   }
/*     */   
/*     */   public void retainData(long $$0, boolean $$1) {
/* 218 */     if ($$1) {
/* 219 */       this.columnsToRetainQueuedDataFor.add($$0);
/*     */     } else {
/* 221 */       this.columnsToRetainQueuedDataFor.remove($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void queueSectionData(long $$0, @Nullable DataLayer $$1) {
/* 226 */     if ($$1 != null) {
/* 227 */       this.queuedSections.put($$0, $$1);
/* 228 */       this.hasInconsistencies = true;
/*     */     } else {
/* 230 */       this.queuedSections.remove($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateSectionStatus(long $$0, boolean $$1) {
/* 235 */     byte $$2 = this.sectionStates.get($$0);
/* 236 */     byte $$3 = SectionState.hasData($$2, !$$1);
/* 237 */     if ($$2 == $$3) {
/*     */       return;
/*     */     }
/*     */     
/* 241 */     putSectionState($$0, $$3);
/*     */     
/* 243 */     int $$4 = $$1 ? -1 : 1;
/* 244 */     for (int $$5 = -1; $$5 <= 1; $$5++) {
/* 245 */       for (int $$6 = -1; $$6 <= 1; $$6++) {
/* 246 */         for (int $$7 = -1; $$7 <= 1; $$7++) {
/* 247 */           if ($$5 != 0 || $$6 != 0 || $$7 != 0) {
/*     */ 
/*     */             
/* 250 */             long $$8 = SectionPos.offset($$0, $$5, $$6, $$7);
/* 251 */             byte $$9 = this.sectionStates.get($$8);
/* 252 */             putSectionState($$8, SectionState.neighborCount($$9, SectionState.neighborCount($$9) + $$4));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected void putSectionState(long $$0, byte $$1) {
/* 259 */     if ($$1 != 0) {
/* 260 */       if (this.sectionStates.put($$0, $$1) == 0) {
/* 261 */         initializeSection($$0);
/*     */       }
/*     */     }
/* 264 */     else if (this.sectionStates.remove($$0) != 0) {
/* 265 */       removeSection($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeSection(long $$0) {
/* 271 */     if (!this.toRemove.remove($$0)) {
/* 272 */       this.updatingSectionData.setLayer($$0, createDataLayer($$0));
/* 273 */       this.changedSections.add($$0);
/* 274 */       onNodeAdded($$0);
/* 275 */       markSectionAndNeighborsAsAffected($$0);
/* 276 */       this.hasInconsistencies = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeSection(long $$0) {
/* 281 */     this.toRemove.add($$0);
/* 282 */     this.hasInconsistencies = true;
/*     */   }
/*     */   
/*     */   protected void swapSectionMap() {
/* 286 */     if (!this.changedSections.isEmpty()) {
/* 287 */       M $$0 = this.updatingSectionData.copy();
/* 288 */       $$0.disableCache();
/* 289 */       this.visibleSectionData = $$0;
/* 290 */       this.changedSections.clear();
/*     */     } 
/* 292 */     if (!this.sectionsAffectedByLightUpdates.isEmpty()) {
/* 293 */       LongIterator $$1 = this.sectionsAffectedByLightUpdates.iterator();
/* 294 */       while ($$1.hasNext()) {
/* 295 */         long $$2 = $$1.nextLong();
/* 296 */         this.chunkSource.onLightUpdate(this.layer, SectionPos.of($$2));
/*     */       } 
/* 298 */       this.sectionsAffectedByLightUpdates.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public SectionType getDebugSectionType(long $$0) {
/* 303 */     return SectionState.type(this.sectionStates.get($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static class SectionState
/*     */   {
/*     */     public static final byte EMPTY = 0;
/*     */     private static final int MIN_NEIGHBORS = 0;
/*     */     private static final int MAX_NEIGHBORS = 26;
/*     */     private static final byte HAS_DATA_BIT = 32;
/*     */     private static final byte NEIGHBOR_COUNT_BITS = 31;
/*     */     
/*     */     public static byte hasData(byte $$0, boolean $$1) {
/* 316 */       return (byte)($$1 ? ($$0 | 0x20) : ($$0 & 0xFFFFFFDF));
/*     */     }
/*     */     
/*     */     public static byte neighborCount(byte $$0, int $$1) {
/* 320 */       if ($$1 < 0 || $$1 > 26) {
/* 321 */         throw new IllegalArgumentException("Neighbor count was not within range [0; 26]");
/*     */       }
/* 323 */       return (byte)($$0 & 0xFFFFFFE0 | $$1 & 0x1F);
/*     */     }
/*     */     
/*     */     public static boolean hasData(byte $$0) {
/* 327 */       return (($$0 & 0x20) != 0);
/*     */     }
/*     */     
/*     */     public static int neighborCount(byte $$0) {
/* 331 */       return $$0 & 0x1F;
/*     */     }
/*     */     
/*     */     public static LayerLightSectionStorage.SectionType type(byte $$0) {
/* 335 */       if ($$0 == 0) {
/* 336 */         return LayerLightSectionStorage.SectionType.EMPTY;
/*     */       }
/* 338 */       if (hasData($$0)) {
/* 339 */         return LayerLightSectionStorage.SectionType.LIGHT_AND_DATA;
/*     */       }
/* 341 */       return LayerLightSectionStorage.SectionType.LIGHT_ONLY;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum SectionType {
/* 346 */     EMPTY("2"),
/* 347 */     LIGHT_ONLY("1"),
/* 348 */     LIGHT_AND_DATA("0");
/*     */     
/*     */     private final String display;
/*     */ 
/*     */     
/*     */     SectionType(String $$0) {
/* 354 */       this.display = $$0;
/*     */     }
/*     */     
/*     */     public String display() {
/* 358 */       return this.display;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\LayerLightSectionStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */