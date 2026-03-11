/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.client.InputType;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.CommonInputs;
/*     */ import net.minecraft.client.sounds.SoundManager;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public abstract class AbstractSliderButton
/*     */   extends AbstractWidget {
/*  18 */   private static final ResourceLocation SLIDER_SPRITE = new ResourceLocation("widget/slider");
/*  19 */   private static final ResourceLocation HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_highlighted");
/*  20 */   private static final ResourceLocation SLIDER_HANDLE_SPRITE = new ResourceLocation("widget/slider_handle");
/*  21 */   private static final ResourceLocation SLIDER_HANDLE_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_handle_highlighted");
/*     */   
/*     */   protected static final int TEXT_MARGIN = 2;
/*     */   
/*     */   private static final int HANDLE_WIDTH = 8;
/*     */   private static final int HANDLE_HALF_WIDTH = 4;
/*     */   protected double value;
/*     */   private boolean canChangeValue;
/*     */   
/*     */   public AbstractSliderButton(int $$0, int $$1, int $$2, int $$3, Component $$4, double $$5) {
/*  31 */     super($$0, $$1, $$2, $$3, $$4);
/*  32 */     this.value = $$5;
/*     */   }
/*     */   
/*     */   private ResourceLocation getSprite() {
/*  36 */     if (isFocused() && !this.canChangeValue) {
/*  37 */       return HIGHLIGHTED_SPRITE;
/*     */     }
/*  39 */     return SLIDER_SPRITE;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getHandleSprite() {
/*  44 */     if (this.isHovered || this.canChangeValue) {
/*  45 */       return SLIDER_HANDLE_HIGHLIGHTED_SPRITE;
/*     */     }
/*  47 */     return SLIDER_HANDLE_SPRITE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected MutableComponent createNarrationMessage() {
/*  53 */     return Component.translatable("gui.narrate.slider", new Object[] { getMessage() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/*  58 */     $$0.add(NarratedElementType.TITLE, (Component)createNarrationMessage());
/*  59 */     if (this.active) {
/*  60 */       if (isFocused()) {
/*  61 */         $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.slider.usage.focused"));
/*     */       } else {
/*  63 */         $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.slider.usage.hovered"));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  70 */     Minecraft $$4 = Minecraft.getInstance();
/*     */     
/*  72 */     $$0.setColor(1.0F, 1.0F, 1.0F, this.alpha);
/*     */     
/*  74 */     RenderSystem.enableBlend();
/*  75 */     RenderSystem.defaultBlendFunc();
/*  76 */     RenderSystem.enableDepthTest();
/*  77 */     $$0.blitSprite(getSprite(), getX(), getY(), getWidth(), getHeight());
/*  78 */     $$0.blitSprite(getHandleSprite(), getX() + (int)(this.value * (this.width - 8)), getY(), 8, getHeight());
/*     */     
/*  80 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  82 */     int $$5 = this.active ? 16777215 : 10526880;
/*  83 */     renderScrollingString($$0, $$4.font, 2, $$5 | Mth.ceil(this.alpha * 255.0F) << 24);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(double $$0, double $$1) {
/*  88 */     setValueFromMouse($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(boolean $$0) {
/*  93 */     super.setFocused($$0);
/*  94 */     if (!$$0) {
/*  95 */       this.canChangeValue = false;
/*     */       return;
/*     */     } 
/*  98 */     InputType $$1 = Minecraft.getInstance().getLastInputType();
/*  99 */     if ($$1 == InputType.MOUSE || $$1 == InputType.KEYBOARD_TAB) {
/* 100 */       this.canChangeValue = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 106 */     if (CommonInputs.selected($$0)) {
/* 107 */       this.canChangeValue = !this.canChangeValue;
/* 108 */       return true;
/*     */     } 
/* 110 */     if (this.canChangeValue) {
/* 111 */       boolean $$3 = ($$0 == 263);
/* 112 */       if ($$3 || $$0 == 262) {
/* 113 */         float $$4 = $$3 ? -1.0F : 1.0F;
/* 114 */         setValue(this.value + ($$4 / (this.width - 8)));
/* 115 */         return true;
/*     */       } 
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   private void setValueFromMouse(double $$0) {
/* 122 */     setValue(($$0 - (getX() + 4)) / (this.width - 8));
/*     */   }
/*     */   
/*     */   private void setValue(double $$0) {
/* 126 */     double $$1 = this.value;
/* 127 */     this.value = Mth.clamp($$0, 0.0D, 1.0D);
/* 128 */     if ($$1 != this.value) {
/* 129 */       applyValue();
/*     */     }
/* 131 */     updateMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDrag(double $$0, double $$1, double $$2, double $$3) {
/* 136 */     setValueFromMouse($$0);
/* 137 */     super.onDrag($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDownSound(SoundManager $$0) {}
/*     */ 
/*     */   
/*     */   public void onRelease(double $$0, double $$1) {
/* 146 */     super.playDownSound(Minecraft.getInstance().getSoundManager());
/*     */   }
/*     */   
/*     */   protected abstract void updateMessage();
/*     */   
/*     */   protected abstract void applyValue();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\AbstractSliderButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */