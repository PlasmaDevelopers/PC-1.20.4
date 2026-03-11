/*    */ package net.minecraft.client.gui.components.toasts;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class TutorialToast
/*    */   implements Toast {
/* 12 */   private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("toast/tutorial");
/*    */   
/*    */   public static final int PROGRESS_BAR_WIDTH = 154;
/*    */   public static final int PROGRESS_BAR_HEIGHT = 1;
/*    */   public static final int PROGRESS_BAR_X = 3;
/*    */   public static final int PROGRESS_BAR_Y = 28;
/*    */   private final Icons icon;
/*    */   private final Component title;
/*    */   @Nullable
/*    */   private final Component message;
/* 22 */   private Toast.Visibility visibility = Toast.Visibility.SHOW;
/*    */   private long lastProgressTime;
/*    */   private float lastProgress;
/*    */   private float progress;
/*    */   private final boolean progressable;
/*    */   
/*    */   public TutorialToast(Icons $$0, Component $$1, @Nullable Component $$2, boolean $$3) {
/* 29 */     this.icon = $$0;
/* 30 */     this.title = $$1;
/* 31 */     this.message = $$2;
/* 32 */     this.progressable = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public Toast.Visibility render(GuiGraphics $$0, ToastComponent $$1, long $$2) {
/* 37 */     $$0.blitSprite(BACKGROUND_SPRITE, 0, 0, width(), height());
/* 38 */     this.icon.render($$0, 6, 6);
/*    */     
/* 40 */     if (this.message == null) {
/* 41 */       $$0.drawString(($$1.getMinecraft()).font, this.title, 30, 12, -11534256, false);
/*    */     } else {
/* 43 */       $$0.drawString(($$1.getMinecraft()).font, this.title, 30, 7, -11534256, false);
/* 44 */       $$0.drawString(($$1.getMinecraft()).font, this.message, 30, 18, -16777216, false);
/*    */     } 
/*    */     
/* 47 */     if (this.progressable) {
/* 48 */       int $$5; $$0.fill(3, 28, 157, 29, -1);
/* 49 */       float $$3 = Mth.clampedLerp(this.lastProgress, this.progress, (float)($$2 - this.lastProgressTime) / 100.0F);
/*    */       
/* 51 */       if (this.progress >= this.lastProgress) {
/* 52 */         int $$4 = -16755456;
/*    */       } else {
/* 54 */         $$5 = -11206656;
/*    */       } 
/* 56 */       $$0.fill(3, 28, (int)(3.0F + 154.0F * $$3), 29, $$5);
/* 57 */       this.lastProgress = $$3;
/* 58 */       this.lastProgressTime = $$2;
/*    */     } 
/*    */     
/* 61 */     return this.visibility;
/*    */   }
/*    */   
/*    */   public void hide() {
/* 65 */     this.visibility = Toast.Visibility.HIDE;
/*    */   }
/*    */   
/*    */   public void updateProgress(float $$0) {
/* 69 */     this.progress = $$0;
/*    */   }
/*    */   
/*    */   public enum Icons {
/* 73 */     MOVEMENT_KEYS((String)new ResourceLocation("toast/movement_keys")),
/* 74 */     MOUSE((String)new ResourceLocation("toast/mouse")),
/* 75 */     TREE((String)new ResourceLocation("toast/tree")),
/* 76 */     RECIPE_BOOK((String)new ResourceLocation("toast/recipe_book")),
/* 77 */     WOODEN_PLANKS((String)new ResourceLocation("toast/wooden_planks")),
/* 78 */     SOCIAL_INTERACTIONS((String)new ResourceLocation("toast/social_interactions")),
/* 79 */     RIGHT_CLICK((String)new ResourceLocation("toast/right_click"));
/*    */     
/*    */     private final ResourceLocation sprite;
/*    */ 
/*    */     
/*    */     Icons(ResourceLocation $$0) {
/* 85 */       this.sprite = $$0;
/*    */     }
/*    */     
/*    */     public void render(GuiGraphics $$0, int $$1, int $$2) {
/* 89 */       RenderSystem.enableBlend();
/* 90 */       $$0.blitSprite(this.sprite, $$1, $$2, 20, 20);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\TutorialToast.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */