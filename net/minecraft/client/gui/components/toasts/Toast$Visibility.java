/*    */ package net.minecraft.client.gui.components.toasts;
/*    */ 
/*    */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*    */ import net.minecraft.client.resources.sounds.SoundInstance;
/*    */ import net.minecraft.client.sounds.SoundManager;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
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
/*    */ public enum Visibility
/*    */ {
/* 34 */   SHOW(SoundEvents.UI_TOAST_IN),
/* 35 */   HIDE(SoundEvents.UI_TOAST_OUT);
/*    */   
/*    */   private final SoundEvent soundEvent;
/*    */ 
/*    */   
/*    */   Visibility(SoundEvent $$0) {
/* 41 */     this.soundEvent = $$0;
/*    */   }
/*    */   
/*    */   public void playSound(SoundManager $$0) {
/* 45 */     $$0.play((SoundInstance)SimpleSoundInstance.forUI(this.soundEvent, 1.0F, 1.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\Toast$Visibility.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */