/*    */ package net.minecraft.client.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.net.SocketAddress;
/*    */ import net.minecraft.core.LayeredRegistryAccess;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.RegistryLayer;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.server.players.PlayerList;
/*    */ import net.minecraft.world.level.storage.PlayerDataStorage;
/*    */ 
/*    */ public class IntegratedPlayerList extends PlayerList {
/*    */   private CompoundTag playerData;
/*    */   
/*    */   public IntegratedPlayerList(IntegratedServer $$0, LayeredRegistryAccess<RegistryLayer> $$1, PlayerDataStorage $$2) {
/* 18 */     super($$0, $$1, $$2, 8);
/*    */     
/* 20 */     setViewDistance(10);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void save(ServerPlayer $$0) {
/* 25 */     if (getServer().isSingleplayerOwner($$0.getGameProfile())) {
/* 26 */       this.playerData = $$0.saveWithoutId(new CompoundTag());
/*    */     }
/*    */     
/* 29 */     super.save($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component canPlayerLogin(SocketAddress $$0, GameProfile $$1) {
/* 34 */     if (getServer().isSingleplayerOwner($$1) && getPlayerByName($$1.getName()) != null) {
/* 35 */       return (Component)Component.translatable("multiplayer.disconnect.name_taken");
/*    */     }
/*    */     
/* 38 */     return super.canPlayerLogin($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public IntegratedServer getServer() {
/* 43 */     return (IntegratedServer)super.getServer();
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag getSingleplayerData() {
/* 48 */     return this.playerData;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\server\IntegratedPlayerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */