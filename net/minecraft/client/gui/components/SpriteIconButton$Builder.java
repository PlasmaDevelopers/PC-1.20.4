/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
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
/*     */ public class Builder
/*     */ {
/*     */   private final Component message;
/*     */   private final Button.OnPress onPress;
/*     */   private final boolean iconOnly;
/*  71 */   private int width = 150;
/*  72 */   private int height = 20;
/*     */   
/*     */   @Nullable
/*     */   private ResourceLocation sprite;
/*     */   private int spriteWidth;
/*     */   private int spriteHeight;
/*     */   
/*     */   public Builder(Component $$0, Button.OnPress $$1, boolean $$2) {
/*  80 */     this.message = $$0;
/*  81 */     this.onPress = $$1;
/*  82 */     this.iconOnly = $$2;
/*     */   }
/*     */   
/*     */   public Builder width(int $$0) {
/*  86 */     this.width = $$0;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public Builder size(int $$0, int $$1) {
/*  91 */     this.width = $$0;
/*  92 */     this.height = $$1;
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sprite(ResourceLocation $$0, int $$1, int $$2) {
/*  97 */     this.sprite = $$0;
/*  98 */     this.spriteWidth = $$1;
/*  99 */     this.spriteHeight = $$2;
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public SpriteIconButton build() {
/* 104 */     if (this.sprite == null) {
/* 105 */       throw new IllegalStateException("Sprite not set");
/*     */     }
/* 107 */     if (this.iconOnly) {
/* 108 */       return new SpriteIconButton.CenteredIcon(this.width, this.height, this.message, this.spriteWidth, this.spriteHeight, this.sprite, this.onPress);
/*     */     }
/* 110 */     return new SpriteIconButton.TextAndIcon(this.width, this.height, this.message, this.spriteWidth, this.spriteHeight, this.sprite, this.onPress);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\SpriteIconButton$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */