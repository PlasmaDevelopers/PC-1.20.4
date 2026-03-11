/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*    */ import net.minecraft.client.resources.sounds.SoundInstance;
/*    */ import net.minecraft.client.sounds.SoundManager;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ 
/*    */ public class PageButton extends Button {
/* 12 */   private static final ResourceLocation PAGE_FORWARD_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/page_forward_highlighted");
/* 13 */   private static final ResourceLocation PAGE_FORWARD_SPRITE = new ResourceLocation("widget/page_forward");
/* 14 */   private static final ResourceLocation PAGE_BACKWARD_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/page_backward_highlighted");
/* 15 */   private static final ResourceLocation PAGE_BACKWARD_SPRITE = new ResourceLocation("widget/page_backward");
/*    */   private final boolean isForward;
/*    */   private final boolean playTurnSound;
/*    */   
/*    */   public PageButton(int $$0, int $$1, boolean $$2, Button.OnPress $$3, boolean $$4) {
/* 20 */     super($$0, $$1, 23, 13, CommonComponents.EMPTY, $$3, DEFAULT_NARRATION);
/* 21 */     this.isForward = $$2;
/* 22 */     this.playTurnSound = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*    */     ResourceLocation $$5;
/* 28 */     if (this.isForward) {
/* 29 */       ResourceLocation $$4 = isHoveredOrFocused() ? PAGE_FORWARD_HIGHLIGHTED_SPRITE : PAGE_FORWARD_SPRITE;
/*    */     } else {
/* 31 */       $$5 = isHoveredOrFocused() ? PAGE_BACKWARD_HIGHLIGHTED_SPRITE : PAGE_BACKWARD_SPRITE;
/*    */     } 
/* 33 */     $$0.blitSprite($$5, getX(), getY(), 23, 13);
/*    */   }
/*    */ 
/*    */   
/*    */   public void playDownSound(SoundManager $$0) {
/* 38 */     if (this.playTurnSound)
/* 39 */       $$0.play((SoundInstance)SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\PageButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */