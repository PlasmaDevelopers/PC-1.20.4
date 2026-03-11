/*     */ package net.minecraft.client.gui.components;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class Checkbox extends AbstractButton {
/*  17 */   private static final ResourceLocation CHECKBOX_SELECTED_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/checkbox_selected_highlighted");
/*  18 */   private static final ResourceLocation CHECKBOX_SELECTED_SPRITE = new ResourceLocation("widget/checkbox_selected");
/*  19 */   private static final ResourceLocation CHECKBOX_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/checkbox_highlighted");
/*  20 */   private static final ResourceLocation CHECKBOX_SPRITE = new ResourceLocation("widget/checkbox");
/*     */   
/*     */   private static final int TEXT_COLOR = 14737632;
/*     */   private static final int SPACING = 4;
/*     */   private static final int BOX_PADDING = 8;
/*     */   private boolean selected;
/*     */   private final OnValueChange onValueChange;
/*     */   
/*     */   public static interface OnValueChange
/*     */   {
/*     */     public static final OnValueChange NOP = ($$0, $$1) -> {
/*     */       
/*     */       };
/*     */     
/*     */     void onValueChange(Checkbox param1Checkbox, boolean param1Boolean);
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final Component message;
/*     */     private final Font font;
/*  41 */     private int x = 0;
/*  42 */     private int y = 0;
/*  43 */     private Checkbox.OnValueChange onValueChange = Checkbox.OnValueChange.NOP; private boolean selected = false;
/*     */     @Nullable
/*  45 */     private OptionInstance<Boolean> option = null;
/*     */     @Nullable
/*  47 */     private Tooltip tooltip = null;
/*     */ 
/*     */     
/*     */     Builder(Component $$0, Font $$1) {
/*  51 */       this.message = $$0;
/*  52 */       this.font = $$1;
/*     */     }
/*     */     
/*     */     public Builder pos(int $$0, int $$1) {
/*  56 */       this.x = $$0;
/*  57 */       this.y = $$1;
/*  58 */       return this;
/*     */     }
/*     */     
/*     */     public Builder onValueChange(Checkbox.OnValueChange $$0) {
/*  62 */       this.onValueChange = $$0;
/*  63 */       return this;
/*     */     }
/*     */     
/*     */     public Builder selected(boolean $$0) {
/*  67 */       this.selected = $$0;
/*  68 */       this.option = null;
/*  69 */       return this;
/*     */     }
/*     */     
/*     */     public Builder selected(OptionInstance<Boolean> $$0) {
/*  73 */       this.option = $$0;
/*  74 */       this.selected = ((Boolean)$$0.get()).booleanValue();
/*  75 */       return this;
/*     */     }
/*     */     
/*     */     public Builder tooltip(Tooltip $$0) {
/*  79 */       this.tooltip = $$0;
/*  80 */       return this;
/*     */     }
/*     */     
/*     */     public Checkbox build() {
/*  84 */       Checkbox.OnValueChange $$0 = (this.option == null) ? this.onValueChange : (($$0, $$1) -> {
/*     */           this.option.set(Boolean.valueOf($$1));
/*     */           
/*     */           this.onValueChange.onValueChange($$0, $$1);
/*     */         });
/*  89 */       Checkbox $$1 = new Checkbox(this.x, this.y, this.message, this.font, this.selected, $$0);
/*  90 */       $$1.setTooltip(this.tooltip);
/*  91 */       return $$1;
/*     */     }
/*     */   }
/*     */   
/*     */   Checkbox(int $$0, int $$1, Component $$2, Font $$3, boolean $$4, OnValueChange $$5) {
/*  96 */     super($$0, $$1, boxSize($$3) + 4 + $$3.width((FormattedText)$$2), boxSize($$3), $$2);
/*  97 */     this.selected = $$4;
/*  98 */     this.onValueChange = $$5;
/*     */   }
/*     */   
/*     */   public static Builder builder(Component $$0, Font $$1) {
/* 102 */     return new Builder($$0, $$1);
/*     */   }
/*     */   
/*     */   private static int boxSize(Font $$0) {
/* 106 */     Objects.requireNonNull($$0); return 9 + 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPress() {
/* 111 */     this.selected = !this.selected;
/* 112 */     this.onValueChange.onValueChange(this, this.selected);
/*     */   }
/*     */   
/*     */   public boolean selected() {
/* 116 */     return this.selected;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 121 */     $$0.add(NarratedElementType.TITLE, (Component)createNarrationMessage());
/* 122 */     if (this.active) {
/* 123 */       if (isFocused()) {
/* 124 */         $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.checkbox.usage.focused"));
/*     */       } else {
/* 126 */         $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.checkbox.usage.hovered"));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */     ResourceLocation $$7;
/* 133 */     Minecraft $$4 = Minecraft.getInstance();
/* 134 */     RenderSystem.enableDepthTest();
/* 135 */     Font $$5 = $$4.font;
/*     */     
/* 137 */     $$0.setColor(1.0F, 1.0F, 1.0F, this.alpha);
/*     */     
/* 139 */     RenderSystem.enableBlend();
/*     */     
/* 141 */     if (this.selected) {
/* 142 */       ResourceLocation $$6 = isFocused() ? CHECKBOX_SELECTED_HIGHLIGHTED_SPRITE : CHECKBOX_SELECTED_SPRITE;
/*     */     } else {
/* 144 */       $$7 = isFocused() ? CHECKBOX_HIGHLIGHTED_SPRITE : CHECKBOX_SPRITE;
/*     */     } 
/* 146 */     int $$8 = boxSize($$5);
/* 147 */     int $$9 = getX() + $$8 + 4;
/* 148 */     Objects.requireNonNull($$5); int $$10 = getY() + (this.height >> 1) - (9 >> 1);
/* 149 */     $$0.blitSprite($$7, getX(), getY(), $$8, $$8);
/*     */     
/* 151 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 152 */     $$0.drawString($$5, getMessage(), $$9, $$10, 0xE0E0E0 | Mth.ceil(this.alpha * 255.0F) << 24);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\Checkbox.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */