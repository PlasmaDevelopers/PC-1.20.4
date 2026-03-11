/*     */ package net.minecraft.client.gui.components.toasts;
/*     */ 
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.util.Mth;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ToastInstance<T extends Toast>
/*     */ {
/*     */   private static final long ANIMATION_TIME = 600L;
/*     */   private final T toast;
/*     */   final int index;
/*     */   final int slotCount;
/* 115 */   private long animationTime = -1L;
/* 116 */   private long visibleTime = -1L;
/* 117 */   private Toast.Visibility visibility = Toast.Visibility.SHOW;
/*     */   
/*     */   ToastInstance(T $$0, int $$1, int $$2) {
/* 120 */     this.toast = $$0;
/* 121 */     this.index = $$1;
/* 122 */     this.slotCount = $$2;
/*     */   }
/*     */   
/*     */   public T getToast() {
/* 126 */     return this.toast;
/*     */   }
/*     */   
/*     */   private float getVisibility(long $$0) {
/* 130 */     float $$1 = Mth.clamp((float)($$0 - this.animationTime) / 600.0F, 0.0F, 1.0F);
/* 131 */     $$1 *= $$1;
/* 132 */     if (this.visibility == Toast.Visibility.HIDE) {
/* 133 */       return 1.0F - $$1;
/*     */     }
/* 135 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean render(int $$0, GuiGraphics $$1) {
/* 140 */     long $$2 = Util.getMillis();
/*     */     
/* 142 */     if (this.animationTime == -1L) {
/* 143 */       this.animationTime = $$2;
/* 144 */       this.visibility.playSound(ToastComponent.this.minecraft.getSoundManager());
/*     */     } 
/*     */     
/* 147 */     if (this.visibility == Toast.Visibility.SHOW && $$2 - this.animationTime <= 600L) {
/* 148 */       this.visibleTime = $$2;
/*     */     }
/*     */     
/* 151 */     $$1.pose().pushPose();
/* 152 */     $$1.pose().translate($$0 - this.toast.width() * getVisibility($$2), (this.index * 32), 800.0F);
/* 153 */     Toast.Visibility $$3 = this.toast.render($$1, ToastComponent.this, $$2 - this.visibleTime);
/* 154 */     $$1.pose().popPose();
/*     */     
/* 156 */     if ($$3 != this.visibility) {
/* 157 */       this.animationTime = $$2 - (int)((1.0F - getVisibility($$2)) * 600.0F);
/* 158 */       this.visibility = $$3;
/* 159 */       this.visibility.playSound(ToastComponent.this.minecraft.getSoundManager());
/*     */     } 
/*     */     
/* 162 */     return (this.visibility == Toast.Visibility.HIDE && $$2 - this.animationTime > 600L);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\ToastComponent$ToastInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */