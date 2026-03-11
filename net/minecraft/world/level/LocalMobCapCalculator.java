/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ChunkMap;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.MobCategory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalMobCapCalculator
/*    */ {
/* 19 */   private final Long2ObjectMap<List<ServerPlayer>> playersNearChunk = (Long2ObjectMap<List<ServerPlayer>>)new Long2ObjectOpenHashMap();
/* 20 */   private final Map<ServerPlayer, MobCounts> playerMobCounts = Maps.newHashMap();
/*    */   private final ChunkMap chunkMap;
/*    */   
/*    */   public LocalMobCapCalculator(ChunkMap $$0) {
/* 24 */     this.chunkMap = $$0;
/*    */   }
/*    */   
/*    */   private List<ServerPlayer> getPlayersNear(ChunkPos $$0) {
/* 28 */     return (List<ServerPlayer>)this.playersNearChunk.computeIfAbsent($$0.toLong(), $$1 -> this.chunkMap.getPlayersCloseForSpawning($$0));
/*    */   }
/*    */   
/*    */   public void addMob(ChunkPos $$0, MobCategory $$1) {
/* 32 */     for (ServerPlayer $$2 : getPlayersNear($$0)) {
/* 33 */       ((MobCounts)this.playerMobCounts.computeIfAbsent($$2, $$0 -> new MobCounts())).add($$1);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean canSpawn(MobCategory $$0, ChunkPos $$1) {
/* 38 */     for (ServerPlayer $$2 : getPlayersNear($$1)) {
/* 39 */       MobCounts $$3 = this.playerMobCounts.get($$2);
/* 40 */       if ($$3 == null || $$3.canSpawn($$0)) {
/* 41 */         return true;
/*    */       }
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   private static class MobCounts {
/* 48 */     private final Object2IntMap<MobCategory> counts = (Object2IntMap<MobCategory>)new Object2IntOpenHashMap((MobCategory.values()).length);
/*    */     
/*    */     public void add(MobCategory $$0) {
/* 51 */       this.counts.computeInt($$0, ($$0, $$1) -> Integer.valueOf(($$1 == null) ? 1 : ($$1.intValue() + 1)));
/*    */     }
/*    */     
/*    */     public boolean canSpawn(MobCategory $$0) {
/* 55 */       return (this.counts.getOrDefault($$0, 0) < $$0.getMaxInstancesPerChunk());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\LocalMobCapCalculator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */