/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public abstract class SpriteIconButton
/*     */   extends Button {
/*     */   protected final ResourceLocation sprite;
/*     */   protected final int spriteWidth;
/*     */   protected final int spriteHeight;
/*     */   
/*     */   SpriteIconButton(int $$0, int $$1, Component $$2, int $$3, int $$4, ResourceLocation $$5, Button.OnPress $$6) {
/*  16 */     super(0, 0, $$0, $$1, $$2, $$6, DEFAULT_NARRATION);
/*  17 */     this.spriteWidth = $$3;
/*  18 */     this.spriteHeight = $$4;
/*  19 */     this.sprite = $$5;
/*     */   }
/*     */   
/*     */   public static Builder builder(Component $$0, Button.OnPress $$1, boolean $$2) {
/*  23 */     return new Builder($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static class CenteredIcon extends SpriteIconButton {
/*     */     protected CenteredIcon(int $$0, int $$1, Component $$2, int $$3, int $$4, ResourceLocation $$5, Button.OnPress $$6) {
/*  28 */       super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  33 */       super.renderWidget($$0, $$1, $$2, $$3);
/*  34 */       int $$4 = getX() + getWidth() / 2 - this.spriteWidth / 2;
/*  35 */       int $$5 = getY() + getHeight() / 2 - this.spriteHeight / 2;
/*  36 */       $$0.blitSprite(this.sprite, $$4, $$5, this.spriteWidth, this.spriteHeight);
/*     */     }
/*     */     
/*     */     public void renderString(GuiGraphics $$0, Font $$1, int $$2) {}
/*     */   }
/*     */   
/*     */   public static class TextAndIcon
/*     */     extends SpriteIconButton
/*     */   {
/*     */     protected TextAndIcon(int $$0, int $$1, Component $$2, int $$3, int $$4, ResourceLocation $$5, Button.OnPress $$6) {
/*  46 */       super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  51 */       super.renderWidget($$0, $$1, $$2, $$3);
/*  52 */       int $$4 = getX() + getWidth() - this.spriteWidth - 2;
/*  53 */       int $$5 = getY() + getHeight() / 2 - this.spriteHeight / 2;
/*  54 */       $$0.blitSprite(this.sprite, $$4, $$5, this.spriteWidth, this.spriteHeight);
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderString(GuiGraphics $$0, Font $$1, int $$2) {
/*  59 */       int $$3 = getX() + 2;
/*  60 */       int $$4 = getX() + getWidth() - this.spriteWidth - 4;
/*  61 */       int $$5 = getX() + getWidth() / 2;
/*  62 */       renderScrollingString($$0, $$1, getMessage(), $$5, $$3, getY(), $$4, getY() + getHeight(), $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final Component message;
/*     */     private final Button.OnPress onPress;
/*     */     private final boolean iconOnly;
/*  71 */     private int width = 150;
/*  72 */     private int height = 20;
/*     */     
/*     */     @Nullable
/*     */     private ResourceLocation sprite;
/*     */     private int spriteWidth;
/*     */     private int spriteHeight;
/*     */     
/*     */     public Builder(Component $$0, Button.OnPress $$1, boolean $$2) {
/*  80 */       this.message = $$0;
/*  81 */       this.onPress = $$1;
/*  82 */       this.iconOnly = $$2;
/*     */     }
/*     */     
/*     */     public Builder width(int $$0) {
/*  86 */       this.width = $$0;
/*  87 */       return this;
/*     */     }
/*     */     
/*     */     public Builder size(int $$0, int $$1) {
/*  91 */       this.width = $$0;
/*  92 */       this.height = $$1;
/*  93 */       return this;
/*     */     }
/*     */     
/*     */     public Builder sprite(ResourceLocation $$0, int $$1, int $$2) {
/*  97 */       this.sprite = $$0;
/*  98 */       this.spriteWidth = $$1;
/*  99 */       this.spriteHeight = $$2;
/* 100 */       return this;
/*     */     }
/*     */     
/*     */     public SpriteIconButton build() {
/* 104 */       if (this.sprite == null) {
/* 105 */         throw new IllegalStateException("Sprite not set");
/*     */       }
/* 107 */       if (this.iconOnly) {
/* 108 */         return new SpriteIconButton.CenteredIcon(this.width, this.height, this.message, this.spriteWidth, this.spriteHeight, this.sprite, this.onPress);
/*     */       }
/* 110 */       return new SpriteIconButton.TextAndIcon(this.width, this.height, this.message, this.spriteWidth, this.spriteHeight, this.sprite, this.onPress);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\SpriteIconButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */