/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
/*     */ import net.minecraft.util.datafix.PackedBitStorage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Section
/*     */ {
/* 370 */   private final CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> palette = CrudeIncrementalIntIdentityHashBiMap.create(32);
/*     */   
/*     */   private final List<Dynamic<?>> listTag;
/*     */   private final Dynamic<?> section;
/*     */   private final boolean hasData;
/* 375 */   final Int2ObjectMap<IntList> toFix = (Int2ObjectMap<IntList>)new Int2ObjectLinkedOpenHashMap();
/*     */   
/* 377 */   final IntList update = (IntList)new IntArrayList();
/*     */   public final int y;
/* 379 */   private final Set<Dynamic<?>> seen = Sets.newIdentityHashSet();
/* 380 */   private final int[] buffer = new int[4096];
/*     */   
/*     */   public Section(Dynamic<?> $$0) {
/* 383 */     this.listTag = Lists.newArrayList();
/* 384 */     this.section = $$0;
/* 385 */     this.y = $$0.get("Y").asInt(0);
/* 386 */     this.hasData = $$0.get("Blocks").result().isPresent();
/*     */   }
/*     */   
/*     */   public Dynamic<?> getBlock(int $$0) {
/* 390 */     if ($$0 < 0 || $$0 > 4095) {
/* 391 */       return ChunkPalettedStorageFix.AIR;
/*     */     }
/*     */     
/* 394 */     Dynamic<?> $$1 = (Dynamic)this.palette.byId(this.buffer[$$0]);
/* 395 */     return ($$1 == null) ? ChunkPalettedStorageFix.AIR : $$1;
/*     */   }
/*     */   
/*     */   public void setBlock(int $$0, Dynamic<?> $$1) {
/* 399 */     if (this.seen.add($$1)) {
/* 400 */       this.listTag.add("%%FILTER_ME%%".equals(ChunkPalettedStorageFix.getName($$1)) ? ChunkPalettedStorageFix.AIR : $$1);
/*     */     }
/* 402 */     this.buffer[$$0] = ChunkPalettedStorageFix.idFor(this.palette, $$1);
/*     */   }
/*     */   
/*     */   public int upgrade(int $$0) {
/* 406 */     if (!this.hasData) {
/* 407 */       return $$0;
/*     */     }
/* 409 */     ByteBuffer $$1 = this.section.get("Blocks").asByteBufferOpt().result().get();
/* 410 */     ChunkPalettedStorageFix.DataLayer $$2 = this.section.get("Data").asByteBufferOpt().map($$0 -> new ChunkPalettedStorageFix.DataLayer(DataFixUtils.toArray($$0))).result().orElseGet(DataLayer::new);
/* 411 */     ChunkPalettedStorageFix.DataLayer $$3 = this.section.get("Add").asByteBufferOpt().map($$0 -> new ChunkPalettedStorageFix.DataLayer(DataFixUtils.toArray($$0))).result().orElseGet(DataLayer::new);
/*     */     
/* 413 */     this.seen.add(ChunkPalettedStorageFix.AIR);
/* 414 */     ChunkPalettedStorageFix.idFor(this.palette, ChunkPalettedStorageFix.AIR);
/* 415 */     this.listTag.add(ChunkPalettedStorageFix.AIR);
/*     */     
/* 417 */     for (int $$4 = 0; $$4 < 4096; $$4++) {
/* 418 */       int $$5 = $$4 & 0xF;
/* 419 */       int $$6 = $$4 >> 8 & 0xF;
/* 420 */       int $$7 = $$4 >> 4 & 0xF;
/* 421 */       int $$8 = $$3.get($$5, $$6, $$7) << 12 | ($$1.get($$4) & 0xFF) << 4 | $$2.get($$5, $$6, $$7);
/*     */       
/* 423 */       if (ChunkPalettedStorageFix.FIX.get($$8 >> 4)) {
/* 424 */         addFix($$8 >> 4, $$4);
/*     */       }
/* 426 */       if (ChunkPalettedStorageFix.VIRTUAL.get($$8 >> 4)) {
/*     */         
/* 428 */         int $$9 = ChunkPalettedStorageFix.getSideMask(($$5 == 0), ($$5 == 15), ($$7 == 0), ($$7 == 15));
/* 429 */         if ($$9 == 0) {
/*     */           
/* 431 */           this.update.add($$4);
/*     */         } else {
/* 433 */           $$0 |= $$9;
/*     */         } 
/*     */       } 
/*     */       
/* 437 */       setBlock($$4, BlockStateData.getTag($$8));
/*     */     } 
/*     */     
/* 440 */     return $$0;
/*     */   }
/*     */   private void addFix(int $$0, int $$1) {
/*     */     IntArrayList intArrayList;
/* 444 */     IntList $$2 = (IntList)this.toFix.get($$0);
/* 445 */     if ($$2 == null) {
/* 446 */       intArrayList = new IntArrayList();
/* 447 */       this.toFix.put($$0, intArrayList);
/*     */     } 
/* 449 */     intArrayList.add($$1);
/*     */   }
/*     */   
/*     */   public Dynamic<?> write() {
/* 453 */     Dynamic<?> $$0 = this.section;
/* 454 */     if (!this.hasData) {
/* 455 */       return $$0;
/*     */     }
/* 457 */     $$0 = $$0.set("Palette", $$0.createList(this.listTag.stream()));
/*     */     
/* 459 */     int $$1 = Math.max(4, DataFixUtils.ceillog2(this.seen.size()));
/* 460 */     PackedBitStorage $$2 = new PackedBitStorage($$1, 4096);
/* 461 */     for (int $$3 = 0; $$3 < this.buffer.length; $$3++) {
/* 462 */       $$2.set($$3, this.buffer[$$3]);
/*     */     }
/*     */     
/* 465 */     $$0 = $$0.set("BlockStates", $$0.createLongList(Arrays.stream($$2.getRaw())));
/*     */     
/* 467 */     $$0 = $$0.remove("Blocks");
/* 468 */     $$0 = $$0.remove("Data");
/* 469 */     $$0 = $$0.remove("Add");
/*     */     
/* 471 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkPalettedStorageFix$Section.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */