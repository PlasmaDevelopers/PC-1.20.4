/*    */ package net.minecraft.world.level.lighting;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.chunk.DataLayer;
/*    */ 
/*    */ public abstract class DataLayerStorageMap<M extends DataLayerStorageMap<M>>
/*    */ {
/*    */   private static final int CACHE_SIZE = 2;
/* 10 */   private final long[] lastSectionKeys = new long[2];
/* 11 */   private final DataLayer[] lastSections = new DataLayer[2];
/*    */   private boolean cacheEnabled;
/*    */   protected final Long2ObjectOpenHashMap<DataLayer> map;
/*    */   
/*    */   protected DataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> $$0) {
/* 16 */     this.map = $$0;
/* 17 */     clearCache();
/* 18 */     this.cacheEnabled = true;
/*    */   }
/*    */   
/*    */   public abstract M copy();
/*    */   
/*    */   public DataLayer copyDataLayer(long $$0) {
/* 24 */     DataLayer $$1 = ((DataLayer)this.map.get($$0)).copy();
/* 25 */     this.map.put($$0, $$1);
/* 26 */     clearCache();
/* 27 */     return $$1;
/*    */   }
/*    */   
/*    */   public boolean hasLayer(long $$0) {
/* 31 */     return this.map.containsKey($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public DataLayer getLayer(long $$0) {
/* 36 */     if (this.cacheEnabled) {
/* 37 */       for (int $$1 = 0; $$1 < 2; $$1++) {
/* 38 */         if ($$0 == this.lastSectionKeys[$$1]) {
/* 39 */           return this.lastSections[$$1];
/*    */         }
/*    */       } 
/*    */     }
/* 43 */     DataLayer $$2 = (DataLayer)this.map.get($$0);
/* 44 */     if ($$2 != null) {
/* 45 */       if (this.cacheEnabled) {
/* 46 */         for (int $$3 = 1; $$3 > 0; $$3--) {
/* 47 */           this.lastSectionKeys[$$3] = this.lastSectionKeys[$$3 - 1];
/* 48 */           this.lastSections[$$3] = this.lastSections[$$3 - 1];
/*    */         } 
/* 50 */         this.lastSectionKeys[0] = $$0;
/* 51 */         this.lastSections[0] = $$2;
/*    */       } 
/* 53 */       return $$2;
/*    */     } 
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public DataLayer removeLayer(long $$0) {
/* 61 */     return (DataLayer)this.map.remove($$0);
/*    */   }
/*    */   
/*    */   public void setLayer(long $$0, DataLayer $$1) {
/* 65 */     this.map.put($$0, $$1);
/*    */   }
/*    */   
/*    */   public void clearCache() {
/* 69 */     for (int $$0 = 0; $$0 < 2; $$0++) {
/* 70 */       this.lastSectionKeys[$$0] = Long.MAX_VALUE;
/* 71 */       this.lastSections[$$0] = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void disableCache() {
/* 76 */     this.cacheEnabled = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\DataLayerStorageMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */