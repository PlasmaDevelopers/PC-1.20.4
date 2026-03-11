/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.client.multiplayer.ClientChunkCache;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.server.IntegratedServer;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerChunkCache;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ChunkData
/*    */ {
/*    */   final Map<ChunkPos, String> clientData;
/*    */   final CompletableFuture<Map<ChunkPos, String>> serverData;
/*    */   
/*    */   ChunkData(IntegratedServer $$0, double $$1, double $$2) {
/* 73 */     ClientLevel $$3 = paramChunkDebugRenderer.minecraft.level;
/* 74 */     ResourceKey<Level> $$4 = $$3.dimension();
/* 75 */     int $$5 = SectionPos.posToSectionCoord($$1);
/* 76 */     int $$6 = SectionPos.posToSectionCoord($$2);
/*    */     
/* 78 */     ImmutableMap.Builder<ChunkPos, String> $$7 = ImmutableMap.builder();
/* 79 */     ClientChunkCache $$8 = $$3.getChunkSource();
/* 80 */     for (int $$9 = $$5 - 12; $$9 <= $$5 + 12; $$9++) {
/* 81 */       for (int $$10 = $$6 - 12; $$10 <= $$6 + 12; $$10++) {
/* 82 */         ChunkPos $$11 = new ChunkPos($$9, $$10);
/* 83 */         String $$12 = "";
/* 84 */         LevelChunk $$13 = $$8.getChunk($$9, $$10, false);
/* 85 */         $$12 = $$12 + "Client: ";
/* 86 */         if ($$13 == null) {
/* 87 */           $$12 = $$12 + "0n/a\n";
/*    */         } else {
/* 89 */           $$12 = $$12 + $$12;
/* 90 */           $$12 = $$12 + "\n";
/*    */         } 
/* 92 */         $$7.put($$11, $$12);
/*    */       } 
/*    */     } 
/* 95 */     this.clientData = (Map<ChunkPos, String>)$$7.build();
/* 96 */     this.serverData = $$0.submit(() -> {
/*    */           ServerLevel $$4 = $$0.getLevel($$1);
/*    */           if ($$4 == null)
/*    */             return (Map)ImmutableMap.of(); 
/*    */           ImmutableMap.Builder<ChunkPos, String> $$5 = ImmutableMap.builder();
/*    */           ServerChunkCache $$6 = $$4.getChunkSource();
/*    */           for (int $$7 = $$2 - 12; $$7 <= $$2 + 12; $$7++) {
/*    */             for (int $$8 = $$3 - 12; $$8 <= $$3 + 12; $$8++) {
/*    */               ChunkPos $$9 = new ChunkPos($$7, $$8);
/*    */               $$5.put($$9, "Server: " + $$6.getChunkDebugData($$9));
/*    */             } 
/*    */           } 
/*    */           return (Map)$$5.build();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\ChunkDebugRenderer$ChunkData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */