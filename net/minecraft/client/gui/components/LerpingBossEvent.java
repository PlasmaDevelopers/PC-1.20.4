/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.BossEvent;
/*    */ 
/*    */ public class LerpingBossEvent
/*    */   extends BossEvent {
/*    */   private static final long LERP_MILLISECONDS = 100L;
/*    */   protected float targetPercent;
/*    */   protected long setTime;
/*    */   
/*    */   public LerpingBossEvent(UUID $$0, Component $$1, float $$2, BossEvent.BossBarColor $$3, BossEvent.BossBarOverlay $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 16 */     super($$0, $$1, $$3, $$4);
/* 17 */     this.targetPercent = $$2;
/* 18 */     this.progress = $$2;
/* 19 */     this.setTime = Util.getMillis();
/* 20 */     setDarkenScreen($$5);
/* 21 */     setPlayBossMusic($$6);
/* 22 */     setCreateWorldFog($$7);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProgress(float $$0) {
/* 27 */     this.progress = getProgress();
/* 28 */     this.targetPercent = $$0;
/* 29 */     this.setTime = Util.getMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getProgress() {
/* 34 */     long $$0 = Util.getMillis() - this.setTime;
/* 35 */     float $$1 = Mth.clamp((float)$$0 / 100.0F, 0.0F, 1.0F);
/* 36 */     return Mth.lerp($$1, this.progress, this.targetPercent);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\LerpingBossEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */