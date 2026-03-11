/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.chunk.DataLayer;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ 
/*     */ public class LevelLightEngine
/*     */   implements LightEventListener
/*     */ {
/*     */   public static final int LIGHT_SECTION_PADDING = 1;
/*     */   protected final LevelHeightAccessor levelHeightAccessor;
/*     */   @Nullable
/*     */   private final LightEngine<?, ?> blockEngine;
/*     */   @Nullable
/*     */   private final LightEngine<?, ?> skyEngine;
/*     */   
/*     */   public LevelLightEngine(LightChunkGetter $$0, boolean $$1, boolean $$2) {
/*  23 */     this.levelHeightAccessor = (LevelHeightAccessor)$$0.getLevel();
/*  24 */     this.blockEngine = $$1 ? new BlockLightEngine($$0) : null;
/*  25 */     this.skyEngine = $$2 ? new SkyLightEngine($$0) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkBlock(BlockPos $$0) {
/*  31 */     if (this.blockEngine != null) {
/*  32 */       this.blockEngine.checkBlock($$0);
/*     */     }
/*  34 */     if (this.skyEngine != null) {
/*  35 */       this.skyEngine.checkBlock($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasLightWork() {
/*  42 */     if (this.skyEngine != null && this.skyEngine.hasLightWork()) {
/*  43 */       return true;
/*     */     }
/*  45 */     return (this.blockEngine != null && this.blockEngine.hasLightWork());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int runLightUpdates() {
/*  51 */     int $$0 = 0;
/*  52 */     if (this.blockEngine != null) {
/*  53 */       $$0 += this.blockEngine.runLightUpdates();
/*     */     }
/*  55 */     if (this.skyEngine != null) {
/*  56 */       $$0 += this.skyEngine.runLightUpdates();
/*     */     }
/*  58 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateSectionStatus(SectionPos $$0, boolean $$1) {
/*  67 */     if (this.blockEngine != null) {
/*  68 */       this.blockEngine.updateSectionStatus($$0, $$1);
/*     */     }
/*  70 */     if (this.skyEngine != null) {
/*  71 */       this.skyEngine.updateSectionStatus($$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLightEnabled(ChunkPos $$0, boolean $$1) {
/*  78 */     if (this.blockEngine != null) {
/*  79 */       this.blockEngine.setLightEnabled($$0, $$1);
/*     */     }
/*  81 */     if (this.skyEngine != null) {
/*  82 */       this.skyEngine.setLightEnabled($$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void propagateLightSources(ChunkPos $$0) {
/*  89 */     if (this.blockEngine != null) {
/*  90 */       this.blockEngine.propagateLightSources($$0);
/*     */     }
/*  92 */     if (this.skyEngine != null) {
/*  93 */       this.skyEngine.propagateLightSources($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public LayerLightEventListener getLayerListener(LightLayer $$0) {
/*  98 */     if ($$0 == LightLayer.BLOCK) {
/*  99 */       if (this.blockEngine == null) {
/* 100 */         return LayerLightEventListener.DummyLightLayerEventListener.INSTANCE;
/*     */       }
/* 102 */       return this.blockEngine;
/*     */     } 
/* 104 */     if (this.skyEngine == null) {
/* 105 */       return LayerLightEventListener.DummyLightLayerEventListener.INSTANCE;
/*     */     }
/* 107 */     return this.skyEngine;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDebugData(LightLayer $$0, SectionPos $$1) {
/* 112 */     if ($$0 == LightLayer.BLOCK) {
/* 113 */       if (this.blockEngine != null) {
/* 114 */         return this.blockEngine.getDebugData($$1.asLong());
/*     */       }
/*     */     }
/* 117 */     else if (this.skyEngine != null) {
/* 118 */       return this.skyEngine.getDebugData($$1.asLong());
/*     */     } 
/*     */     
/* 121 */     return "n/a";
/*     */   }
/*     */   
/*     */   public LayerLightSectionStorage.SectionType getDebugSectionType(LightLayer $$0, SectionPos $$1) {
/* 125 */     if ($$0 == LightLayer.BLOCK) {
/* 126 */       if (this.blockEngine != null) {
/* 127 */         return this.blockEngine.getDebugSectionType($$1.asLong());
/*     */       }
/*     */     }
/* 130 */     else if (this.skyEngine != null) {
/* 131 */       return this.skyEngine.getDebugSectionType($$1.asLong());
/*     */     } 
/*     */     
/* 134 */     return LayerLightSectionStorage.SectionType.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueSectionData(LightLayer $$0, SectionPos $$1, @Nullable DataLayer $$2) {
/* 142 */     if ($$0 == LightLayer.BLOCK) {
/* 143 */       if (this.blockEngine != null) {
/* 144 */         this.blockEngine.queueSectionData($$1.asLong(), $$2);
/*     */       }
/*     */     }
/* 147 */     else if (this.skyEngine != null) {
/* 148 */       this.skyEngine.queueSectionData($$1.asLong(), $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void retainData(ChunkPos $$0, boolean $$1) {
/* 154 */     if (this.blockEngine != null) {
/* 155 */       this.blockEngine.retainData($$0, $$1);
/*     */     }
/* 157 */     if (this.skyEngine != null) {
/* 158 */       this.skyEngine.retainData($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getRawBrightness(BlockPos $$0, int $$1) {
/* 163 */     int $$2 = (this.skyEngine == null) ? 0 : (this.skyEngine.getLightValue($$0) - $$1);
/* 164 */     int $$3 = (this.blockEngine == null) ? 0 : this.blockEngine.getLightValue($$0);
/*     */     
/* 166 */     return Math.max($$3, $$2);
/*     */   }
/*     */   
/*     */   public boolean lightOnInSection(SectionPos $$0) {
/* 170 */     long $$1 = $$0.asLong();
/* 171 */     return (this.blockEngine == null || (this.blockEngine.storage.lightOnInSection($$1) && (this.skyEngine == null || this.skyEngine.storage
/* 172 */       .lightOnInSection($$1))));
/*     */   }
/*     */   
/*     */   public int getLightSectionCount() {
/* 176 */     return this.levelHeightAccessor.getSectionsCount() + 2;
/*     */   }
/*     */   
/*     */   public int getMinLightSection() {
/* 180 */     return this.levelHeightAccessor.getMinSection() - 1;
/*     */   }
/*     */   
/*     */   public int getMaxLightSection() {
/* 184 */     return getMinLightSection() + getLightSectionCount();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\LevelLightEngine.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */