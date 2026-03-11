/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class LockIconButton extends Button {
/*    */   private boolean locked;
/*    */   
/*    */   public LockIconButton(int $$0, int $$1, Button.OnPress $$2) {
/* 13 */     super($$0, $$1, 20, 20, (Component)Component.translatable("narrator.button.difficulty_lock"), $$2, DEFAULT_NARRATION);
/*    */   }
/*    */ 
/*    */   
/*    */   protected MutableComponent createNarrationMessage() {
/* 18 */     return CommonComponents.joinForNarration(new Component[] { (Component)super.createNarrationMessage(), isLocked() ? (Component)Component.translatable("narrator.button.difficulty_lock.locked") : (Component)Component.translatable("narrator.button.difficulty_lock.unlocked") });
/*    */   }
/*    */   
/*    */   public boolean isLocked() {
/* 22 */     return this.locked;
/*    */   }
/*    */   
/*    */   public void setLocked(boolean $$0) {
/* 26 */     this.locked = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*    */     Icon $$6;
/* 32 */     if (!this.active) {
/* 33 */       Icon $$4 = this.locked ? Icon.LOCKED_DISABLED : Icon.UNLOCKED_DISABLED;
/* 34 */     } else if (isHoveredOrFocused()) {
/* 35 */       Icon $$5 = this.locked ? Icon.LOCKED_HOVER : Icon.UNLOCKED_HOVER;
/*    */     } else {
/* 37 */       $$6 = this.locked ? Icon.LOCKED : Icon.UNLOCKED;
/*    */     } 
/*    */     
/* 40 */     $$0.blitSprite($$6.sprite, getX(), getY(), this.width, this.height);
/*    */   }
/*    */   
/*    */   private enum Icon {
/* 44 */     LOCKED((String)new ResourceLocation("widget/locked_button")),
/* 45 */     LOCKED_HOVER((String)new ResourceLocation("widget/locked_button_highlighted")),
/* 46 */     LOCKED_DISABLED((String)new ResourceLocation("widget/locked_button_disabled")),
/* 47 */     UNLOCKED((String)new ResourceLocation("widget/unlocked_button")),
/* 48 */     UNLOCKED_HOVER((String)new ResourceLocation("widget/unlocked_button_highlighted")),
/* 49 */     UNLOCKED_DISABLED((String)new ResourceLocation("widget/unlocked_button_disabled"));
/*    */     
/*    */     final ResourceLocation sprite;
/*    */ 
/*    */     
/*    */     Icon(ResourceLocation $$0) {
/* 55 */       this.sprite = $$0;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\LockIconButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */