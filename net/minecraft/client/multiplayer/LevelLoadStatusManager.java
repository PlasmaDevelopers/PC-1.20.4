/*    */ package net.minecraft.client.multiplayer;
/*    */ 
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public class LevelLoadStatusManager
/*    */ {
/*    */   private final LocalPlayer player;
/*    */   private final ClientLevel level;
/*    */   private final LevelRenderer levelRenderer;
/* 12 */   private Status status = Status.WAITING_FOR_SERVER;
/*    */   
/*    */   public LevelLoadStatusManager(LocalPlayer $$0, ClientLevel $$1, LevelRenderer $$2) {
/* 15 */     this.player = $$0;
/* 16 */     this.level = $$1;
/* 17 */     this.levelRenderer = $$2;
/*    */   } public void tick() {
/*    */     BlockPos $$0;
/*    */     boolean $$1;
/* 21 */     switch (this.status) {
/*    */ 
/*    */       
/*    */       case WAITING_FOR_PLAYER_CHUNK:
/* 25 */         $$0 = this.player.blockPosition();
/* 26 */         $$1 = this.level.isOutsideBuildHeight($$0.getY());
/*    */         
/* 28 */         if ($$1 || this.levelRenderer.isSectionCompiled($$0) || this.player.isSpectator() || !this.player.isAlive()) {
/* 29 */           this.status = Status.LEVEL_READY;
/*    */         }
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean levelReady() {
/* 36 */     return (this.status == Status.LEVEL_READY);
/*    */   }
/*    */   
/*    */   public void loadingPacketsReceived() {
/* 40 */     if (this.status == Status.WAITING_FOR_SERVER) {
/* 41 */       this.status = Status.WAITING_FOR_PLAYER_CHUNK;
/*    */     }
/*    */   }
/*    */   
/*    */   private enum Status
/*    */   {
/* 47 */     WAITING_FOR_SERVER,
/*    */     
/* 49 */     WAITING_FOR_PLAYER_CHUNK,
/*    */     
/* 51 */     LEVEL_READY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\LevelLoadStatusManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */