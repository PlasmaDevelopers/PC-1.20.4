/*     */ package net.minecraft.server.network;
/*     */ 
/*     */ import com.google.common.collect.Comparators;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.server.level.ChunkMap;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PlayerChunkSender
/*     */ {
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final float MIN_CHUNKS_PER_TICK = 0.01F;
/*     */   public static final float MAX_CHUNKS_PER_TICK = 64.0F;
/*     */   private static final float START_CHUNKS_PER_TICK = 9.0F;
/*     */   private static final int MAX_UNACKNOWLEDGED_BATCHES = 10;
/*  32 */   private final LongSet pendingChunks = (LongSet)new LongOpenHashSet();
/*     */   
/*     */   private final boolean memoryConnection;
/*  35 */   private float desiredChunksPerTick = 9.0F;
/*     */   private float batchQuota;
/*     */   private int unacknowledgedBatches;
/*  38 */   private int maxUnacknowledgedBatches = 1;
/*     */   
/*     */   public PlayerChunkSender(boolean $$0) {
/*  41 */     this.memoryConnection = $$0;
/*     */   }
/*     */   
/*     */   public void markChunkPendingToSend(LevelChunk $$0) {
/*  45 */     this.pendingChunks.add($$0.getPos().toLong());
/*     */   }
/*     */   
/*     */   public void dropChunk(ServerPlayer $$0, ChunkPos $$1) {
/*  49 */     if (!this.pendingChunks.remove($$1.toLong()))
/*     */     {
/*  51 */       if ($$0.isAlive()) {
/*  52 */         $$0.connection.send((Packet<?>)new ClientboundForgetLevelChunkPacket($$1));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendNextChunks(ServerPlayer $$0) {
/*  58 */     if (this.unacknowledgedBatches >= this.maxUnacknowledgedBatches) {
/*     */       return;
/*     */     }
/*  61 */     float $$1 = Math.max(1.0F, this.desiredChunksPerTick);
/*  62 */     this.batchQuota = Math.min(this.batchQuota + this.desiredChunksPerTick, $$1);
/*  63 */     if (this.batchQuota < 1.0F) {
/*     */       return;
/*     */     }
/*  66 */     if (this.pendingChunks.isEmpty()) {
/*     */       return;
/*     */     }
/*  69 */     ServerLevel $$2 = $$0.serverLevel();
/*  70 */     ChunkMap $$3 = ($$2.getChunkSource()).chunkMap;
/*     */     
/*  72 */     List<LevelChunk> $$4 = collectChunksToSend($$3, $$0.chunkPosition());
/*  73 */     if ($$4.isEmpty()) {
/*     */       return;
/*     */     }
/*  76 */     ServerGamePacketListenerImpl $$5 = $$0.connection;
/*     */     
/*  78 */     this.unacknowledgedBatches++;
/*  79 */     $$5.send((Packet<?>)new ClientboundChunkBatchStartPacket());
/*  80 */     for (LevelChunk $$6 : $$4) {
/*  81 */       sendChunk($$5, $$2, $$6);
/*     */     }
/*  83 */     $$5.send((Packet<?>)new ClientboundChunkBatchFinishedPacket($$4.size()));
/*  84 */     this.batchQuota -= $$4.size();
/*     */   }
/*     */   
/*     */   private static void sendChunk(ServerGamePacketListenerImpl $$0, ServerLevel $$1, LevelChunk $$2) {
/*  88 */     $$0.send((Packet<?>)new ClientboundLevelChunkWithLightPacket($$2, $$1.getLightEngine(), null, null));
/*  89 */     ChunkPos $$3 = $$2.getPos();
/*     */ 
/*     */ 
/*     */     
/*  93 */     DebugPackets.sendPoiPacketsForChunk($$1, $$3);
/*     */   }
/*     */   
/*     */   private List<LevelChunk> collectChunksToSend(ChunkMap $$0, ChunkPos $$1) {
/*     */     List<LevelChunk> $$4;
/*  98 */     int $$2 = Mth.floor(this.batchQuota);
/*  99 */     if (this.memoryConnection || this.pendingChunks.size() <= $$2) {
/*     */       
/* 101 */       Objects.requireNonNull($$0);
/*     */ 
/*     */       
/* 104 */       List<LevelChunk> $$3 = this.pendingChunks.longStream().<LevelChunk>mapToObj($$0::getChunkToSend).filter(Objects::nonNull).sorted(Comparator.comparingInt($$1 -> $$0.distanceSquared($$1.getPos()))).toList();
/*     */     } else {
/*     */       
/* 107 */       Objects.requireNonNull($$1);
/*     */       
/* 109 */       Objects.requireNonNull($$0);
/*     */       
/* 111 */       $$4 = ((List)this.pendingChunks.stream().collect(Comparators.least($$2, Comparator.comparingInt($$1::distanceSquared)))).stream().mapToLong(Long::longValue).<LevelChunk>mapToObj($$0::getChunkToSend).filter(Objects::nonNull).toList();
/*     */     } 
/* 113 */     for (LevelChunk $$5 : $$4) {
/* 114 */       this.pendingChunks.remove($$5.getPos().toLong());
/*     */     }
/* 116 */     return $$4;
/*     */   }
/*     */   
/*     */   public void onChunkBatchReceivedByClient(float $$0) {
/* 120 */     this.unacknowledgedBatches--;
/* 121 */     this.desiredChunksPerTick = Double.isNaN($$0) ? 0.01F : Mth.clamp($$0, 0.01F, 64.0F);
/* 122 */     if (this.unacknowledgedBatches == 0)
/*     */     {
/* 124 */       this.batchQuota = 1.0F;
/*     */     }
/* 126 */     this.maxUnacknowledgedBatches = 10;
/*     */   }
/*     */   
/*     */   public boolean isPending(long $$0) {
/* 130 */     return this.pendingChunks.contains($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\PlayerChunkSender.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */