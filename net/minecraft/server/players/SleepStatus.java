/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SleepStatus
/*    */ {
/*    */   private int activePlayers;
/*    */   private int sleepingPlayers;
/*    */   
/*    */   public boolean areEnoughSleeping(int $$0) {
/* 16 */     return (this.sleepingPlayers >= sleepersNeeded($$0));
/*    */   }
/*    */   
/*    */   public boolean areEnoughDeepSleeping(int $$0, List<ServerPlayer> $$1) {
/* 20 */     int $$2 = (int)$$1.stream().filter(Player::isSleepingLongEnough).count();
/* 21 */     return ($$2 >= sleepersNeeded($$0));
/*    */   }
/*    */   
/*    */   public int sleepersNeeded(int $$0) {
/* 25 */     return Math.max(1, Mth.ceil((this.activePlayers * $$0) / 100.0F));
/*    */   }
/*    */   
/*    */   public void removeAllSleepers() {
/* 29 */     this.sleepingPlayers = 0;
/*    */   }
/*    */   
/*    */   public int amountSleeping() {
/* 33 */     return this.sleepingPlayers;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean update(List<ServerPlayer> $$0) {
/* 38 */     int $$1 = this.activePlayers;
/* 39 */     int $$2 = this.sleepingPlayers;
/* 40 */     this.activePlayers = 0;
/* 41 */     this.sleepingPlayers = 0;
/*    */     
/* 43 */     for (ServerPlayer $$3 : $$0) {
/* 44 */       if (!$$3.isSpectator()) {
/* 45 */         this.activePlayers++;
/* 46 */         if ($$3.isSleeping()) {
/* 47 */           this.sleepingPlayers++;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     return (($$2 > 0 || this.sleepingPlayers > 0) && ($$1 != this.activePlayers || $$2 != this.sleepingPlayers));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\SleepStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */