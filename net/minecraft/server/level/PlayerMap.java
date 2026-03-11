/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
/*    */ import java.util.Set;
/*    */ 
/*    */ public final class PlayerMap
/*    */ {
/*  9 */   private final Object2BooleanMap<ServerPlayer> players = (Object2BooleanMap<ServerPlayer>)new Object2BooleanOpenHashMap();
/*    */   
/*    */   public Set<ServerPlayer> getAllPlayers() {
/* 12 */     return (Set<ServerPlayer>)this.players.keySet();
/*    */   }
/*    */   
/*    */   public void addPlayer(ServerPlayer $$0, boolean $$1) {
/* 16 */     this.players.put($$0, $$1);
/*    */   }
/*    */   
/*    */   public void removePlayer(ServerPlayer $$0) {
/* 20 */     this.players.removeBoolean($$0);
/*    */   }
/*    */   
/*    */   public void ignorePlayer(ServerPlayer $$0) {
/* 24 */     this.players.replace($$0, true);
/*    */   }
/*    */   
/*    */   public void unIgnorePlayer(ServerPlayer $$0) {
/* 28 */     this.players.replace($$0, false);
/*    */   }
/*    */   
/*    */   public boolean ignoredOrUnknown(ServerPlayer $$0) {
/* 32 */     return this.players.getOrDefault($$0, true);
/*    */   }
/*    */   
/*    */   public boolean ignored(ServerPlayer $$0) {
/* 36 */     return this.players.getBoolean($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\PlayerMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */