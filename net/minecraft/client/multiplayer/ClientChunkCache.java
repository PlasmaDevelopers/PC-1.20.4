/*     */ package net.minecraft.client.multiplayer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkSource;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.EmptyLevelChunk;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ClientChunkCache extends ChunkSource {
/*  30 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final LevelChunk emptyChunk;
/*     */   private final LevelLightEngine lightEngine;
/*     */   volatile Storage storage;
/*     */   final ClientLevel level;
/*     */   
/*     */   public ClientChunkCache(ClientLevel $$0, int $$1) {
/*  38 */     this.level = $$0;
/*  39 */     this.emptyChunk = (LevelChunk)new EmptyLevelChunk($$0, new ChunkPos(0, 0), (Holder)$$0.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(Biomes.PLAINS));
/*  40 */     this.lightEngine = new LevelLightEngine((LightChunkGetter)this, true, $$0.dimensionType().hasSkyLight());
/*  41 */     this.storage = new Storage(calculateStorageRange($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelLightEngine getLightEngine() {
/*  46 */     return this.lightEngine;
/*     */   }
/*     */   
/*     */   private static boolean isValidChunk(@Nullable LevelChunk $$0, int $$1, int $$2) {
/*  50 */     if ($$0 == null) {
/*  51 */       return false;
/*     */     }
/*  53 */     ChunkPos $$3 = $$0.getPos();
/*  54 */     return ($$3.x == $$1 && $$3.z == $$2);
/*     */   }
/*     */   
/*     */   public void drop(ChunkPos $$0) {
/*  58 */     if (!this.storage.inRange($$0.x, $$0.z)) {
/*     */       return;
/*     */     }
/*  61 */     int $$1 = this.storage.getIndex($$0.x, $$0.z);
/*  62 */     LevelChunk $$2 = this.storage.getChunk($$1);
/*  63 */     if (isValidChunk($$2, $$0.x, $$0.z)) {
/*  64 */       this.storage.replace($$1, $$2, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LevelChunk getChunk(int $$0, int $$1, ChunkStatus $$2, boolean $$3) {
/*  71 */     if (this.storage.inRange($$0, $$1)) {
/*  72 */       LevelChunk $$4 = this.storage.getChunk(this.storage.getIndex($$0, $$1));
/*  73 */       if (isValidChunk($$4, $$0, $$1)) {
/*  74 */         return $$4;
/*     */       }
/*     */     } 
/*     */     
/*  78 */     if ($$3) {
/*  79 */       return this.emptyChunk;
/*     */     }
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockGetter getLevel() {
/*  86 */     return (BlockGetter)this.level;
/*     */   }
/*     */   
/*     */   public void replaceBiomes(int $$0, int $$1, FriendlyByteBuf $$2) {
/*  90 */     if (!this.storage.inRange($$0, $$1)) {
/*  91 */       LOGGER.warn("Ignoring chunk since it's not in the view range: {}, {}", Integer.valueOf($$0), Integer.valueOf($$1));
/*     */       return;
/*     */     } 
/*  94 */     int $$3 = this.storage.getIndex($$0, $$1);
/*     */     
/*  96 */     LevelChunk $$4 = this.storage.chunks.get($$3);
/*  97 */     if (!isValidChunk($$4, $$0, $$1)) {
/*  98 */       LOGGER.warn("Ignoring chunk since it's not present: {}, {}", Integer.valueOf($$0), Integer.valueOf($$1));
/*     */     } else {
/* 100 */       $$4.replaceBiomes($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public LevelChunk replaceWithPacketData(int $$0, int $$1, FriendlyByteBuf $$2, CompoundTag $$3, Consumer<ClientboundLevelChunkPacketData.BlockEntityTagOutput> $$4) {
/* 106 */     if (!this.storage.inRange($$0, $$1)) {
/* 107 */       LOGGER.warn("Ignoring chunk since it's not in the view range: {}, {}", Integer.valueOf($$0), Integer.valueOf($$1));
/* 108 */       return null;
/*     */     } 
/* 110 */     int $$5 = this.storage.getIndex($$0, $$1);
/*     */     
/* 112 */     LevelChunk $$6 = this.storage.chunks.get($$5);
/* 113 */     ChunkPos $$7 = new ChunkPos($$0, $$1);
/* 114 */     if (!isValidChunk($$6, $$0, $$1)) {
/*     */       
/* 116 */       $$6 = new LevelChunk(this.level, $$7);
/* 117 */       $$6.replaceWithPacketData($$2, $$3, $$4);
/* 118 */       this.storage.replace($$5, $$6);
/*     */     } else {
/* 120 */       $$6.replaceWithPacketData($$2, $$3, $$4);
/*     */     } 
/* 122 */     this.level.onChunkLoaded($$7);
/* 123 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BooleanSupplier $$0, boolean $$1) {}
/*     */ 
/*     */   
/*     */   public void updateViewCenter(int $$0, int $$1) {
/* 131 */     this.storage.viewCenterX = $$0;
/* 132 */     this.storage.viewCenterZ = $$1;
/*     */   }
/*     */   
/*     */   public void updateViewRadius(int $$0) {
/* 136 */     int $$1 = this.storage.chunkRadius;
/* 137 */     int $$2 = calculateStorageRange($$0);
/*     */     
/* 139 */     if ($$1 != $$2) {
/* 140 */       Storage $$3 = new Storage($$2);
/* 141 */       $$3.viewCenterX = this.storage.viewCenterX;
/* 142 */       $$3.viewCenterZ = this.storage.viewCenterZ;
/* 143 */       for (int $$4 = 0; $$4 < this.storage.chunks.length(); $$4++) {
/* 144 */         LevelChunk $$5 = this.storage.chunks.get($$4);
/* 145 */         if ($$5 != null) {
/* 146 */           ChunkPos $$6 = $$5.getPos();
/* 147 */           if ($$3.inRange($$6.x, $$6.z)) {
/* 148 */             $$3.replace($$3.getIndex($$6.x, $$6.z), $$5);
/*     */           }
/*     */         } 
/*     */       } 
/* 152 */       this.storage = $$3;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int calculateStorageRange(int $$0) {
/* 158 */     return Math.max(2, $$0) + 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String gatherStats() {
/* 163 */     return "" + this.storage.chunks.length() + ", " + this.storage.chunks.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunksCount() {
/* 168 */     return this.storage.chunkCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLightUpdate(LightLayer $$0, SectionPos $$1) {
/* 173 */     (Minecraft.getInstance()).levelRenderer.setSectionDirty($$1.x(), $$1.y(), $$1.z());
/*     */   }
/*     */   
/*     */   private final class Storage {
/*     */     final AtomicReferenceArray<LevelChunk> chunks;
/*     */     final int chunkRadius;
/*     */     private final int viewRange;
/*     */     volatile int viewCenterX;
/*     */     volatile int viewCenterZ;
/*     */     int chunkCount;
/*     */     
/*     */     Storage(int $$0) {
/* 185 */       this.chunkRadius = $$0;
/* 186 */       this.viewRange = $$0 * 2 + 1;
/* 187 */       this.chunks = new AtomicReferenceArray<>(this.viewRange * this.viewRange);
/*     */     }
/*     */     
/*     */     int getIndex(int $$0, int $$1) {
/* 191 */       return Math.floorMod($$1, this.viewRange) * this.viewRange + Math.floorMod($$0, this.viewRange);
/*     */     }
/*     */     
/*     */     protected void replace(int $$0, @Nullable LevelChunk $$1) {
/* 195 */       LevelChunk $$2 = this.chunks.getAndSet($$0, $$1);
/* 196 */       if ($$2 != null) {
/* 197 */         this.chunkCount--;
/* 198 */         ClientChunkCache.this.level.unload($$2);
/*     */       } 
/*     */       
/* 201 */       if ($$1 != null) {
/* 202 */         this.chunkCount++;
/*     */       }
/*     */     }
/*     */     
/*     */     protected LevelChunk replace(int $$0, LevelChunk $$1, @Nullable LevelChunk $$2) {
/* 207 */       if (this.chunks.compareAndSet($$0, $$1, $$2) && 
/* 208 */         $$2 == null) {
/* 209 */         this.chunkCount--;
/*     */       }
/*     */       
/* 212 */       ClientChunkCache.this.level.unload($$1);
/* 213 */       return $$1;
/*     */     }
/*     */     
/*     */     boolean inRange(int $$0, int $$1) {
/* 217 */       return (Math.abs($$0 - this.viewCenterX) <= this.chunkRadius && Math.abs($$1 - this.viewCenterZ) <= this.chunkRadius);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected LevelChunk getChunk(int $$0) {
/* 222 */       return this.chunks.get($$0);
/*     */     }
/*     */     
/*     */     private void dumpChunks(String $$0) {
/*     */       
/* 227 */       try { FileOutputStream $$1 = new FileOutputStream($$0); 
/* 228 */         try { int $$2 = ClientChunkCache.this.storage.chunkRadius;
/* 229 */           for (int $$3 = this.viewCenterZ - $$2; $$3 <= this.viewCenterZ + $$2; $$3++) {
/* 230 */             for (int $$4 = this.viewCenterX - $$2; $$4 <= this.viewCenterX + $$2; $$4++) {
/* 231 */               LevelChunk $$5 = ClientChunkCache.this.storage.chunks.get(ClientChunkCache.this.storage.getIndex($$4, $$3));
/* 232 */               if ($$5 != null) {
/* 233 */                 ChunkPos $$6 = $$5.getPos();
/* 234 */                 $$1.write(("" + $$6.x + "\t" + $$6.x + "\t" + $$6.z + "\n").getBytes(StandardCharsets.UTF_8));
/*     */               } 
/*     */             } 
/*     */           } 
/* 238 */           $$1.close(); } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$7)
/* 239 */       { ClientChunkCache.LOGGER.error("Failed to dump chunks to file {}", $$0, $$7); }
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientChunkCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */