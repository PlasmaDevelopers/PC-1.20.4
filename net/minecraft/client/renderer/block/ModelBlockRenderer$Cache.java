/*     */ package net.minecraft.client.renderer.block;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockState;
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
/*     */ class Cache
/*     */ {
/*     */   private boolean enabled;
/*     */   private final Long2IntLinkedOpenHashMap colorCache;
/*     */   private final Long2FloatLinkedOpenHashMap brightnessCache;
/*     */   
/*     */   private Cache() {
/* 298 */     this.colorCache = (Long2IntLinkedOpenHashMap)Util.make(() -> {
/*     */           Long2IntLinkedOpenHashMap $$0 = new Long2IntLinkedOpenHashMap(100, 0.25F)
/*     */             {
/*     */               protected void rehash(int $$0) {}
/*     */             };
/*     */           
/*     */           $$0.defaultReturnValue(2147483647);
/*     */           
/*     */           return $$0;
/*     */         });
/* 308 */     this.brightnessCache = (Long2FloatLinkedOpenHashMap)Util.make(() -> {
/*     */           Long2FloatLinkedOpenHashMap $$0 = new Long2FloatLinkedOpenHashMap(100, 0.25F)
/*     */             {
/*     */               protected void rehash(int $$0) {}
/*     */             };
/*     */           $$0.defaultReturnValue(Float.NaN);
/*     */           return $$0;
/*     */         });
/*     */   }
/*     */   
/*     */   public void enable() {
/* 319 */     this.enabled = true;
/*     */   }
/*     */   
/*     */   public void disable() {
/* 323 */     this.enabled = false;
/* 324 */     this.colorCache.clear();
/* 325 */     this.brightnessCache.clear();
/*     */   }
/*     */   
/*     */   public int getLightColor(BlockState $$0, BlockAndTintGetter $$1, BlockPos $$2) {
/* 329 */     long $$3 = $$2.asLong();
/* 330 */     if (this.enabled) {
/* 331 */       int $$4 = this.colorCache.get($$3);
/* 332 */       if ($$4 != Integer.MAX_VALUE) {
/* 333 */         return $$4;
/*     */       }
/*     */     } 
/*     */     
/* 337 */     int $$5 = LevelRenderer.getLightColor($$1, $$0, $$2);
/* 338 */     if (this.enabled) {
/* 339 */       if (this.colorCache.size() == 100) {
/* 340 */         this.colorCache.removeFirstInt();
/*     */       }
/* 342 */       this.colorCache.put($$3, $$5);
/*     */     } 
/* 344 */     return $$5;
/*     */   }
/*     */   
/*     */   public float getShadeBrightness(BlockState $$0, BlockAndTintGetter $$1, BlockPos $$2) {
/* 348 */     long $$3 = $$2.asLong();
/* 349 */     if (this.enabled) {
/* 350 */       float $$4 = this.brightnessCache.get($$3);
/* 351 */       if (!Float.isNaN($$4)) {
/* 352 */         return $$4;
/*     */       }
/*     */     } 
/*     */     
/* 356 */     float $$5 = $$0.getShadeBrightness((BlockGetter)$$1, $$2);
/* 357 */     if (this.enabled) {
/* 358 */       if (this.brightnessCache.size() == 100) {
/* 359 */         this.brightnessCache.removeFirstFloat();
/*     */       }
/* 361 */       this.brightnessCache.put($$3, $$5);
/*     */     } 
/* 363 */     return $$5;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\ModelBlockRenderer$Cache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */