/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.ComponentPath;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*    */ import net.minecraft.client.sounds.SoundManager;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public abstract class ImageWidget
/*    */   extends AbstractWidget {
/*    */   ImageWidget(int $$0, int $$1, int $$2, int $$3) {
/* 15 */     super($$0, $$1, $$2, $$3, CommonComponents.EMPTY);
/*    */   }
/*    */   
/*    */   public static ImageWidget texture(int $$0, int $$1, ResourceLocation $$2, int $$3, int $$4) {
/* 19 */     return new Texture(0, 0, $$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   public static ImageWidget sprite(int $$0, int $$1, ResourceLocation $$2) {
/* 23 */     return new Sprite(0, 0, $$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateWidgetNarration(NarrationElementOutput $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void playDownSound(SoundManager $$0) {}
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   private static class Sprite extends ImageWidget {
/*    */     private final ResourceLocation sprite;
/*    */     
/*    */     public Sprite(int $$0, int $$1, int $$2, int $$3, ResourceLocation $$4) {
/* 49 */       super($$0, $$1, $$2, $$3);
/* 50 */       this.sprite = $$4;
/*    */     }
/*    */ 
/*    */     
/*    */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 55 */       $$0.blitSprite(this.sprite, getX(), getY(), getWidth(), getHeight());
/*    */     }
/*    */   }
/*    */   
/*    */   private static class Texture extends ImageWidget {
/*    */     private final ResourceLocation texture;
/*    */     private final int textureWidth;
/*    */     private final int textureHeight;
/*    */     
/*    */     public Texture(int $$0, int $$1, int $$2, int $$3, ResourceLocation $$4, int $$5, int $$6) {
/* 65 */       super($$0, $$1, $$2, $$3);
/* 66 */       this.texture = $$4;
/* 67 */       this.textureWidth = $$5;
/* 68 */       this.textureHeight = $$6;
/*    */     }
/*    */ 
/*    */     
/*    */     protected void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 73 */       $$0.blit(this.texture, getX(), getY(), getWidth(), getHeight(), 0.0F, 0.0F, getWidth(), getHeight(), this.textureWidth, this.textureHeight);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ImageWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */