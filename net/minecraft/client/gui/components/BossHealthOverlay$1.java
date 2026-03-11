/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
/*     */ import net.minecraft.world.BossEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements ClientboundBossEventPacket.Handler
/*     */ {
/*     */   public void add(UUID $$0, Component $$1, float $$2, BossEvent.BossBarColor $$3, BossEvent.BossBarOverlay $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 109 */     BossHealthOverlay.this.events.put($$0, new LerpingBossEvent($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(UUID $$0) {
/* 114 */     BossHealthOverlay.this.events.remove($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgress(UUID $$0, float $$1) {
/* 119 */     ((LerpingBossEvent)BossHealthOverlay.this.events.get($$0)).setProgress($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateName(UUID $$0, Component $$1) {
/* 124 */     ((LerpingBossEvent)BossHealthOverlay.this.events.get($$0)).setName($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateStyle(UUID $$0, BossEvent.BossBarColor $$1, BossEvent.BossBarOverlay $$2) {
/* 129 */     LerpingBossEvent $$3 = BossHealthOverlay.this.events.get($$0);
/* 130 */     $$3.setColor($$1);
/* 131 */     $$3.setOverlay($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProperties(UUID $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 136 */     LerpingBossEvent $$4 = BossHealthOverlay.this.events.get($$0);
/* 137 */     $$4.setDarkenScreen($$1);
/* 138 */     $$4.setPlayBossMusic($$2);
/* 139 */     $$4.setCreateWorldFog($$3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\BossHealthOverlay$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */