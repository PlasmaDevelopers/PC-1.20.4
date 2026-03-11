/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.chunk.DataLayer;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ 
/*     */ public class SkyLightSectionStorage extends LayerLightSectionStorage<SkyLightSectionStorage.SkyDataLayerStorageMap> {
/*     */   protected SkyLightSectionStorage(LightChunkGetter $$0) {
/*  14 */     super(LightLayer.SKY, $$0, new SkyDataLayerStorageMap(new Long2ObjectOpenHashMap(), new Long2IntOpenHashMap(), 2147483647));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLightValue(long $$0) {
/*  19 */     return getLightValue($$0, false);
/*     */   }
/*     */   
/*     */   protected int getLightValue(long $$0, boolean $$1) {
/*  23 */     long $$2 = SectionPos.blockToSection($$0);
/*  24 */     int $$3 = SectionPos.y($$2);
/*  25 */     SkyDataLayerStorageMap $$4 = $$1 ? this.updatingSectionData : this.visibleSectionData;
/*  26 */     int $$5 = $$4.topSections.get(SectionPos.getZeroNode($$2));
/*  27 */     if ($$5 == $$4.currentLowestY || $$3 >= $$5) {
/*  28 */       if ($$1 && !lightOnInSection($$2)) {
/*  29 */         return 0;
/*     */       }
/*  31 */       return 15;
/*     */     } 
/*  33 */     DataLayer $$6 = getDataLayer($$4, $$2);
/*  34 */     if ($$6 == null) {
/*  35 */       $$0 = BlockPos.getFlatIndex($$0);
/*  36 */       while ($$6 == null) {
/*  37 */         $$3++;
/*  38 */         if ($$3 >= $$5) {
/*  39 */           return 15;
/*     */         }
/*  41 */         $$2 = SectionPos.offset($$2, Direction.UP);
/*  42 */         $$6 = getDataLayer($$4, $$2);
/*     */       } 
/*     */     } 
/*  45 */     return $$6.get(
/*  46 */         SectionPos.sectionRelative(BlockPos.getX($$0)), 
/*  47 */         SectionPos.sectionRelative(BlockPos.getY($$0)), 
/*  48 */         SectionPos.sectionRelative(BlockPos.getZ($$0)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onNodeAdded(long $$0) {
/*  54 */     int $$1 = SectionPos.y($$0);
/*  55 */     if (this.updatingSectionData.currentLowestY > $$1) {
/*  56 */       this.updatingSectionData.currentLowestY = $$1;
/*  57 */       this.updatingSectionData.topSections.defaultReturnValue(this.updatingSectionData.currentLowestY);
/*     */     } 
/*  59 */     long $$2 = SectionPos.getZeroNode($$0);
/*  60 */     int $$3 = this.updatingSectionData.topSections.get($$2);
/*  61 */     if ($$3 < $$1 + 1) {
/*  62 */       this.updatingSectionData.topSections.put($$2, $$1 + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onNodeRemoved(long $$0) {
/*  68 */     long $$1 = SectionPos.getZeroNode($$0);
/*  69 */     int $$2 = SectionPos.y($$0);
/*  70 */     if (this.updatingSectionData.topSections.get($$1) == $$2 + 1) {
/*  71 */       long $$3 = $$0;
/*  72 */       while (!storingLightForSection($$3) && hasLightDataAtOrBelow($$2)) {
/*  73 */         $$2--;
/*  74 */         $$3 = SectionPos.offset($$3, Direction.DOWN);
/*     */       } 
/*  76 */       if (storingLightForSection($$3)) {
/*  77 */         this.updatingSectionData.topSections.put($$1, $$2 + 1);
/*     */       } else {
/*  79 */         this.updatingSectionData.topSections.remove($$1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected DataLayer createDataLayer(long $$0) {
/*  86 */     DataLayer $$1 = (DataLayer)this.queuedSections.get($$0);
/*  87 */     if ($$1 != null) {
/*  88 */       return $$1;
/*     */     }
/*     */     
/*  91 */     int $$2 = this.updatingSectionData.topSections.get(SectionPos.getZeroNode($$0));
/*     */     
/*  93 */     if ($$2 == this.updatingSectionData.currentLowestY || SectionPos.y($$0) >= $$2) {
/*     */       
/*  95 */       if (lightOnInSection($$0)) {
/*  96 */         return new DataLayer(15);
/*     */       }
/*  98 */       return new DataLayer();
/*     */     } 
/*     */ 
/*     */     
/* 102 */     long $$3 = SectionPos.offset($$0, Direction.UP);
/*     */     DataLayer $$4;
/* 104 */     while (($$4 = getDataLayer($$3, true)) == null) {
/* 105 */       $$3 = SectionPos.offset($$3, Direction.UP);
/*     */     }
/*     */     
/* 108 */     return repeatFirstLayer($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static DataLayer repeatFirstLayer(DataLayer $$0) {
/* 113 */     if ($$0.isDefinitelyHomogenous()) {
/* 114 */       return $$0.copy();
/*     */     }
/* 116 */     byte[] $$1 = $$0.getData();
/* 117 */     byte[] $$2 = new byte[2048];
/*     */     
/* 119 */     for (int $$3 = 0; $$3 < 16; $$3++) {
/* 120 */       System.arraycopy($$1, 0, $$2, $$3 * 128, 128);
/*     */     }
/*     */     
/* 123 */     return new DataLayer($$2);
/*     */   }
/*     */   
/*     */   protected boolean hasLightDataAtOrBelow(int $$0) {
/* 127 */     return ($$0 >= this.updatingSectionData.currentLowestY);
/*     */   }
/*     */   
/*     */   protected boolean isAboveData(long $$0) {
/* 131 */     long $$1 = SectionPos.getZeroNode($$0);
/* 132 */     int $$2 = this.updatingSectionData.topSections.get($$1);
/* 133 */     return ($$2 == this.updatingSectionData.currentLowestY || SectionPos.y($$0) >= $$2);
/*     */   }
/*     */   
/*     */   protected int getTopSectionY(long $$0) {
/* 137 */     return this.updatingSectionData.topSections.get($$0);
/*     */   }
/*     */   
/*     */   protected int getBottomSectionY() {
/* 141 */     return this.updatingSectionData.currentLowestY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final class SkyDataLayerStorageMap
/*     */     extends DataLayerStorageMap<SkyDataLayerStorageMap>
/*     */   {
/*     */     int currentLowestY;
/*     */ 
/*     */     
/*     */     final Long2IntOpenHashMap topSections;
/*     */ 
/*     */     
/*     */     public SkyDataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> $$0, Long2IntOpenHashMap $$1, int $$2) {
/* 156 */       super($$0);
/* 157 */       this.topSections = $$1;
/* 158 */       $$1.defaultReturnValue($$2);
/* 159 */       this.currentLowestY = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public SkyDataLayerStorageMap copy() {
/* 164 */       return new SkyDataLayerStorageMap(this.map.clone(), this.topSections.clone(), this.currentLowestY);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\SkyLightSectionStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */