/*    */ package net.minecraft.client.gui.components.toasts;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.advancements.AdvancementType;
/*    */ import net.minecraft.advancements.DisplayInfo;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*    */ import net.minecraft.client.resources.sounds.SoundInstance;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class AdvancementToast implements Toast {
/* 16 */   private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("toast/advancement");
/*    */   public static final int DISPLAY_TIME = 5000;
/*    */   private final AdvancementHolder advancement;
/*    */   private boolean playedSound;
/*    */   
/*    */   public AdvancementToast(AdvancementHolder $$0) {
/* 22 */     this.advancement = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Toast.Visibility render(GuiGraphics $$0, ToastComponent $$1, long $$2) {
/* 27 */     DisplayInfo $$3 = this.advancement.value().display().orElse(null);
/* 28 */     $$0.blitSprite(BACKGROUND_SPRITE, 0, 0, width(), height());
/*    */     
/* 30 */     if ($$3 != null) {
/* 31 */       List<FormattedCharSequence> $$4 = ($$1.getMinecraft()).font.split((FormattedText)$$3.getTitle(), 125);
/* 32 */       int $$5 = ($$3.getType() == AdvancementType.CHALLENGE) ? 16746751 : 16776960;
/*    */       
/* 34 */       if ($$4.size() == 1) {
/* 35 */         $$0.drawString(($$1.getMinecraft()).font, $$3.getType().getDisplayName(), 30, 7, $$5 | 0xFF000000, false);
/* 36 */         $$0.drawString(($$1.getMinecraft()).font, $$4.get(0), 30, 18, -1, false);
/*    */       } else {
/* 38 */         int $$6 = 1500;
/* 39 */         float $$7 = 300.0F;
/* 40 */         if ($$2 < 1500L) {
/* 41 */           int $$8 = Mth.floor(Mth.clamp((float)(1500L - $$2) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 0x4000000;
/* 42 */           $$0.drawString(($$1.getMinecraft()).font, $$3.getType().getDisplayName(), 30, 11, $$5 | $$8, false);
/*    */         } else {
/* 44 */           int $$9 = Mth.floor(Mth.clamp((float)($$2 - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 0x4000000;
/* 45 */           Objects.requireNonNull(($$1.getMinecraft()).font); int $$10 = height() / 2 - $$4.size() * 9 / 2;
/* 46 */           for (FormattedCharSequence $$11 : $$4) {
/* 47 */             $$0.drawString(($$1.getMinecraft()).font, $$11, 30, $$10, 0xFFFFFF | $$9, false);
/* 48 */             Objects.requireNonNull(($$1.getMinecraft()).font); $$10 += 9;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 53 */       if (!this.playedSound && $$2 > 0L) {
/* 54 */         this.playedSound = true;
/* 55 */         if ($$3.getType() == AdvancementType.CHALLENGE) {
/* 56 */           $$1.getMinecraft().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
/*    */         }
/*    */       } 
/*    */       
/* 60 */       $$0.renderFakeItem($$3.getIcon(), 8, 8);
/* 61 */       return ($$2 >= 5000.0D * $$1.getNotificationDisplayTimeMultiplier()) ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
/*    */     } 
/* 63 */     return Toast.Visibility.HIDE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\AdvancementToast.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */