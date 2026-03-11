/*     */ package net.minecraft.world.level.chunk.storage;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtAccounter;
/*     */ import net.minecraft.nbt.NbtIo;
/*     */ import net.minecraft.nbt.StreamTagVisitor;
/*     */ import net.minecraft.util.ExceptionCollector;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ 
/*     */ public final class RegionFileStorage
/*     */   implements AutoCloseable {
/*     */   public static final String ANVIL_EXTENSION = ".mca";
/*     */   private static final int MAX_CACHE_SIZE = 256;
/*  22 */   private final Long2ObjectLinkedOpenHashMap<RegionFile> regionCache = new Long2ObjectLinkedOpenHashMap();
/*     */   private final Path folder;
/*     */   private final boolean sync;
/*     */   
/*     */   RegionFileStorage(Path $$0, boolean $$1) {
/*  27 */     this.folder = $$0;
/*  28 */     this.sync = $$1;
/*     */   }
/*     */   
/*     */   private RegionFile getRegionFile(ChunkPos $$0) throws IOException {
/*  32 */     long $$1 = ChunkPos.asLong($$0.getRegionX(), $$0.getRegionZ());
/*  33 */     RegionFile $$2 = (RegionFile)this.regionCache.getAndMoveToFirst($$1);
/*  34 */     if ($$2 != null) {
/*  35 */       return $$2;
/*     */     }
/*     */     
/*  38 */     if (this.regionCache.size() >= 256) {
/*  39 */       ((RegionFile)this.regionCache.removeLast()).close();
/*     */     }
/*     */     
/*  42 */     FileUtil.createDirectoriesSafe(this.folder);
/*     */     
/*  44 */     Path $$3 = this.folder.resolve("r." + $$0.getRegionX() + "." + $$0.getRegionZ() + ".mca");
/*  45 */     RegionFile $$4 = new RegionFile($$3, this.folder, this.sync);
/*  46 */     this.regionCache.putAndMoveToFirst($$1, $$4);
/*  47 */     return $$4;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag read(ChunkPos $$0) throws IOException {
/*  52 */     RegionFile $$1 = getRegionFile($$0);
/*  53 */     DataInputStream $$2 = $$1.getChunkDataInputStream($$0); 
/*  54 */     try { if ($$2 == null)
/*  55 */       { CompoundTag compoundTag1 = null;
/*     */ 
/*     */ 
/*     */         
/*  59 */         if ($$2 != null) $$2.close();  return compoundTag1; }  CompoundTag compoundTag = NbtIo.read($$2); if ($$2 != null) $$2.close();  return compoundTag; } catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  63 */      } public void scanChunk(ChunkPos $$0, StreamTagVisitor $$1) throws IOException { RegionFile $$2 = getRegionFile($$0);
/*  64 */     DataInputStream $$3 = $$2.getChunkDataInputStream($$0); 
/*  65 */     try { if ($$3 != null) {
/*  66 */         NbtIo.parse($$3, $$1, NbtAccounter.unlimitedHeap());
/*     */       }
/*  68 */       if ($$3 != null) $$3.close();  }
/*     */     catch (Throwable throwable) { if ($$3 != null)
/*     */         try {
/*     */           $$3.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }   throw throwable; }
/*  75 */      } protected void write(ChunkPos $$0, @Nullable CompoundTag $$1) throws IOException { RegionFile $$2 = getRegionFile($$0);
/*  76 */     if ($$1 == null) {
/*  77 */       $$2.clear($$0);
/*     */     } else {
/*  79 */       DataOutputStream $$3 = $$2.getChunkDataOutputStream($$0); 
/*  80 */       try { NbtIo.write($$1, $$3);
/*  81 */         if ($$3 != null) $$3.close();  }
/*     */       catch (Throwable throwable) { if ($$3 != null)
/*     */           try { $$3.close(); }
/*     */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/*     */     
/*  87 */     }  } public void close() throws IOException { ExceptionCollector<IOException> $$0 = new ExceptionCollector();
/*  88 */     for (ObjectIterator<RegionFile> objectIterator = this.regionCache.values().iterator(); objectIterator.hasNext(); ) { RegionFile $$1 = objectIterator.next();
/*     */       try {
/*  90 */         $$1.close();
/*  91 */       } catch (IOException $$2) {
/*  92 */         $$0.add($$2);
/*     */       }  }
/*     */     
/*  95 */     $$0.throwIfPresent(); }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/*  99 */     for (ObjectIterator<RegionFile> objectIterator = this.regionCache.values().iterator(); objectIterator.hasNext(); ) { RegionFile $$0 = objectIterator.next();
/* 100 */       $$0.flush(); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\RegionFileStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */