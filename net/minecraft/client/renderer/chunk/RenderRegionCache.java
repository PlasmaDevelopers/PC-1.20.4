/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ 
/*    */ public class RenderRegionCache
/*    */ {
/*    */   private static final class ChunkInfo {
/*    */     private final LevelChunk chunk;
/*    */     @Nullable
/*    */     private RenderChunk renderChunk;
/*    */     
/*    */     ChunkInfo(LevelChunk $$0) {
/* 20 */       this.chunk = $$0;
/*    */     }
/*    */     
/*    */     public LevelChunk chunk() {
/* 24 */       return this.chunk;
/*    */     }
/*    */     
/*    */     public RenderChunk renderChunk() {
/* 28 */       if (this.renderChunk == null) {
/* 29 */         this.renderChunk = new RenderChunk(this.chunk);
/*    */       }
/* 31 */       return this.renderChunk;
/*    */     }
/*    */   }
/*    */   
/* 35 */   private final Long2ObjectMap<ChunkInfo> chunkInfoCache = (Long2ObjectMap<ChunkInfo>)new Long2ObjectOpenHashMap();
/*    */   
/*    */   @Nullable
/*    */   public RenderChunkRegion createRegion(Level $$0, BlockPos $$1, BlockPos $$2, int $$3) {
/* 39 */     int $$4 = SectionPos.blockToSectionCoord($$1.getX() - $$3);
/* 40 */     int $$5 = SectionPos.blockToSectionCoord($$1.getZ() - $$3);
/* 41 */     int $$6 = SectionPos.blockToSectionCoord($$2.getX() + $$3);
/* 42 */     int $$7 = SectionPos.blockToSectionCoord($$2.getZ() + $$3);
/*    */     
/* 44 */     ChunkInfo[][] $$8 = new ChunkInfo[$$6 - $$4 + 1][$$7 - $$5 + 1];
/*    */     
/* 46 */     for (int $$9 = $$4; $$9 <= $$6; $$9++) {
/* 47 */       for (int $$10 = $$5; $$10 <= $$7; $$10++) {
/* 48 */         $$8[$$9 - $$4][$$10 - $$5] = (ChunkInfo)this.chunkInfoCache.computeIfAbsent(ChunkPos.asLong($$9, $$10), $$1 -> new ChunkInfo($$0.getChunk(ChunkPos.getX($$1), ChunkPos.getZ($$1))));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 53 */     if (isAllEmpty($$1, $$2, $$4, $$5, $$8)) {
/* 54 */       return null;
/*    */     }
/*    */     
/* 57 */     RenderChunk[][] $$11 = new RenderChunk[$$6 - $$4 + 1][$$7 - $$5 + 1];
/* 58 */     for (int $$12 = $$4; $$12 <= $$6; $$12++) {
/* 59 */       for (int $$13 = $$5; $$13 <= $$7; $$13++) {
/* 60 */         $$11[$$12 - $$4][$$13 - $$5] = $$8[$$12 - $$4][$$13 - $$5].renderChunk();
/*    */       }
/*    */     } 
/*    */     
/* 64 */     return new RenderChunkRegion($$0, $$4, $$5, $$11);
/*    */   }
/*    */   
/*    */   private static boolean isAllEmpty(BlockPos $$0, BlockPos $$1, int $$2, int $$3, ChunkInfo[][] $$4) {
/* 68 */     int $$5 = SectionPos.blockToSectionCoord($$0.getX());
/* 69 */     int $$6 = SectionPos.blockToSectionCoord($$0.getZ());
/*    */     
/* 71 */     int $$7 = SectionPos.blockToSectionCoord($$1.getX());
/* 72 */     int $$8 = SectionPos.blockToSectionCoord($$1.getZ());
/*    */     
/* 74 */     for (int $$9 = $$5; $$9 <= $$7; $$9++) {
/* 75 */       for (int $$10 = $$6; $$10 <= $$8; $$10++) {
/* 76 */         LevelChunk $$11 = $$4[$$9 - $$2][$$10 - $$3].chunk();
/* 77 */         if (!$$11.isYSpaceEmpty($$0.getY(), $$1.getY())) {
/* 78 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/* 82 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\chunk\RenderRegionCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */