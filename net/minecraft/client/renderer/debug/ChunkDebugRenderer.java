/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientChunkCache;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.server.IntegratedServer;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerChunkCache;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ 
/*    */ public class ChunkDebugRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer {
/*    */   final Minecraft minecraft;
/* 25 */   private double lastUpdateTime = Double.MIN_VALUE;
/* 26 */   private final int radius = 12;
/*    */   @Nullable
/*    */   private ChunkData data;
/*    */   
/*    */   public ChunkDebugRenderer(Minecraft $$0) {
/* 31 */     this.minecraft = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 36 */     double $$5 = Util.getNanos();
/* 37 */     if ($$5 - this.lastUpdateTime > 3.0E9D) {
/* 38 */       this.lastUpdateTime = $$5;
/*    */       
/* 40 */       IntegratedServer $$6 = this.minecraft.getSingleplayerServer();
/*    */       
/* 42 */       if ($$6 != null) {
/* 43 */         this.data = new ChunkData($$6, $$2, $$4);
/*    */       } else {
/* 45 */         this.data = null;
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     if (this.data != null) {
/* 50 */       Map<ChunkPos, String> $$7 = this.data.serverData.getNow(null);
/* 51 */       double $$8 = (this.minecraft.gameRenderer.getMainCamera().getPosition()).y * 0.85D;
/* 52 */       for (Map.Entry<ChunkPos, String> $$9 : this.data.clientData.entrySet()) {
/* 53 */         ChunkPos $$10 = $$9.getKey();
/* 54 */         String $$11 = $$9.getValue();
/* 55 */         if ($$7 != null) {
/* 56 */           $$11 = $$11 + $$11;
/*    */         }
/* 58 */         String[] $$12 = $$11.split("\n");
/* 59 */         int $$13 = 0;
/* 60 */         for (String $$14 : $$12) {
/* 61 */           DebugRenderer.renderFloatingText($$0, $$1, $$14, SectionPos.sectionToBlockCoord($$10.x, 8), $$8 + $$13, SectionPos.sectionToBlockCoord($$10.z, 8), -1, 0.15F, true, 0.0F, true);
/* 62 */           $$13 -= 2;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private final class ChunkData {
/*    */     final Map<ChunkPos, String> clientData;
/*    */     final CompletableFuture<Map<ChunkPos, String>> serverData;
/*    */     
/*    */     ChunkData(IntegratedServer $$0, double $$1, double $$2) {
/* 73 */       ClientLevel $$3 = ChunkDebugRenderer.this.minecraft.level;
/* 74 */       ResourceKey<Level> $$4 = $$3.dimension();
/* 75 */       int $$5 = SectionPos.posToSectionCoord($$1);
/* 76 */       int $$6 = SectionPos.posToSectionCoord($$2);
/*    */       
/* 78 */       ImmutableMap.Builder<ChunkPos, String> $$7 = ImmutableMap.builder();
/* 79 */       ClientChunkCache $$8 = $$3.getChunkSource();
/* 80 */       for (int $$9 = $$5 - 12; $$9 <= $$5 + 12; $$9++) {
/* 81 */         for (int $$10 = $$6 - 12; $$10 <= $$6 + 12; $$10++) {
/* 82 */           ChunkPos $$11 = new ChunkPos($$9, $$10);
/* 83 */           String $$12 = "";
/* 84 */           LevelChunk $$13 = $$8.getChunk($$9, $$10, false);
/* 85 */           $$12 = $$12 + "Client: ";
/* 86 */           if ($$13 == null) {
/* 87 */             $$12 = $$12 + "0n/a\n";
/*    */           } else {
/* 89 */             $$12 = $$12 + $$12;
/* 90 */             $$12 = $$12 + "\n";
/*    */           } 
/* 92 */           $$7.put($$11, $$12);
/*    */         } 
/*    */       } 
/* 95 */       this.clientData = (Map<ChunkPos, String>)$$7.build();
/* 96 */       this.serverData = $$0.submit(() -> {
/*    */             ServerLevel $$4 = $$0.getLevel($$1);
/*    */             if ($$4 == null)
/*    */               return (Map)ImmutableMap.of(); 
/*    */             ImmutableMap.Builder<ChunkPos, String> $$5 = ImmutableMap.builder();
/*    */             ServerChunkCache $$6 = $$4.getChunkSource();
/*    */             for (int $$7 = $$2 - 12; $$7 <= $$2 + 12; $$7++) {
/*    */               for (int $$8 = $$3 - 12; $$8 <= $$3 + 12; $$8++) {
/*    */                 ChunkPos $$9 = new ChunkPos($$7, $$8);
/*    */                 $$5.put($$9, "Server: " + $$6.getChunkDebugData($$9));
/*    */               } 
/*    */             } 
/*    */             return (Map)$$5.build();
/*    */           });
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\ChunkDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */