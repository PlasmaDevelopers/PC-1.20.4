/*    */ package net.minecraft.client.gui.components.toasts;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*    */ import net.minecraft.client.resources.sounds.SoundInstance;
/*    */ import net.minecraft.client.sounds.SoundManager;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public interface Toast {
/* 11 */   public static final Object NO_TOKEN = new Object();
/*    */   
/*    */   public static final int SLOT_HEIGHT = 32;
/*    */   
/*    */   Visibility render(GuiGraphics paramGuiGraphics, ToastComponent paramToastComponent, long paramLong);
/*    */   
/*    */   default Object getToken() {
/* 18 */     return NO_TOKEN;
/*    */   }
/*    */   
/*    */   default int width() {
/* 22 */     return 160;
/*    */   }
/*    */   
/*    */   default int height() {
/* 26 */     return 32;
/*    */   }
/*    */   
/*    */   default int slotCount() {
/* 30 */     return Mth.positiveCeilDiv(height(), 32);
/*    */   }
/*    */   
/*    */   public enum Visibility {
/* 34 */     SHOW((String)SoundEvents.UI_TOAST_IN),
/* 35 */     HIDE((String)SoundEvents.UI_TOAST_OUT);
/*    */     
/*    */     private final SoundEvent soundEvent;
/*    */ 
/*    */     
/*    */     Visibility(SoundEvent $$0) {
/* 41 */       this.soundEvent = $$0;
/*    */     }
/*    */     
/*    */     public void playSound(SoundManager $$0) {
/* 45 */       $$0.play((SoundInstance)SimpleSoundInstance.forUI(this.soundEvent, 1.0F, 1.0F));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\Toast.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */