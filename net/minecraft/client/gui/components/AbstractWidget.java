/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*     */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.client.sounds.SoundManager;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractWidget
/*     */   implements Renderable, GuiEventListener, LayoutElement, NarratableEntry
/*     */ {
/*     */   private static final double PERIOD_PER_SCROLLED_PIXEL = 0.5D;
/*     */   private static final double MIN_SCROLL_PERIOD = 3.0D;
/*     */   protected int width;
/*     */   protected int height;
/*     */   private int x;
/*  38 */   protected float alpha = 1.0F; private int y; private Component message; protected boolean isHovered;
/*     */   public boolean active = true;
/*     */   public boolean visible = true;
/*     */   private int tabOrderGroup;
/*     */   private boolean focused;
/*     */   @Nullable
/*     */   private Tooltip tooltip;
/*     */   
/*     */   public AbstractWidget(int $$0, int $$1, int $$2, int $$3, Component $$4) {
/*  47 */     this.x = $$0;
/*  48 */     this.y = $$1;
/*  49 */     this.width = $$2;
/*  50 */     this.height = $$3;
/*  51 */     this.message = $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  56 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  61 */     if (!this.visible) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     this.isHovered = ($$1 >= getX() && $$2 >= getY() && $$1 < getX() + this.width && $$2 < getY() + this.height);
/*  66 */     renderWidget($$0, $$1, $$2, $$3);
/*     */     
/*  68 */     if (this.tooltip != null) {
/*  69 */       this.tooltip.refreshTooltipForNextRenderPass(isHovered(), isFocused(), getRectangle());
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTooltip(@Nullable Tooltip $$0) {
/*  74 */     this.tooltip = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Tooltip getTooltip() {
/*  79 */     return this.tooltip;
/*     */   }
/*     */   
/*     */   public void setTooltipDelay(int $$0) {
/*  83 */     if (this.tooltip != null) {
/*  84 */       this.tooltip.setDelay($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected MutableComponent createNarrationMessage() {
/*  89 */     return wrapDefaultNarrationMessage(getMessage());
/*     */   }
/*     */   
/*     */   public static MutableComponent wrapDefaultNarrationMessage(Component $$0) {
/*  93 */     return Component.translatable("gui.narrate.button", new Object[] { $$0 });
/*     */   }
/*     */   
/*     */   protected abstract void renderWidget(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2, float paramFloat);
/*     */   
/*     */   protected static void renderScrollingString(GuiGraphics $$0, Font $$1, Component $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/*  99 */     renderScrollingString($$0, $$1, $$2, ($$3 + $$5) / 2, $$3, $$4, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   protected static void renderScrollingString(GuiGraphics $$0, Font $$1, Component $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
/* 103 */     int $$9 = $$1.width((FormattedText)$$2);
/* 104 */     Objects.requireNonNull($$1); int $$10 = ($$5 + $$7 - 9) / 2 + 1;
/* 105 */     int $$11 = $$6 - $$4;
/* 106 */     if ($$9 > $$11) {
/* 107 */       int $$12 = $$9 - $$11;
/* 108 */       double $$13 = Util.getMillis() / 1000.0D;
/* 109 */       double $$14 = Math.max($$12 * 0.5D, 3.0D);
/* 110 */       double $$15 = Math.sin(1.5707963267948966D * Math.cos(6.283185307179586D * $$13 / $$14)) / 2.0D + 0.5D;
/* 111 */       double $$16 = Mth.lerp($$15, 0.0D, $$12);
/* 112 */       $$0.enableScissor($$4, $$5, $$6, $$7);
/* 113 */       $$0.drawString($$1, $$2, $$4 - (int)$$16, $$10, $$8);
/* 114 */       $$0.disableScissor();
/*     */     } else {
/* 116 */       int $$17 = Mth.clamp($$3, $$4 + $$9 / 2, $$6 - $$9 / 2);
/* 117 */       $$0.drawCenteredString($$1, $$2, $$17, $$10, $$8);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void renderScrollingString(GuiGraphics $$0, Font $$1, int $$2, int $$3) {
/* 122 */     int $$4 = getX() + $$2;
/* 123 */     int $$5 = getX() + getWidth() - $$2;
/* 124 */     renderScrollingString($$0, $$1, getMessage(), $$4, getY(), $$5, getY() + getHeight(), $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(double $$0, double $$1) {}
/*     */ 
/*     */   
/*     */   public void onRelease(double $$0, double $$1) {}
/*     */ 
/*     */   
/*     */   protected void onDrag(double $$0, double $$1, double $$2, double $$3) {}
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 138 */     if (!this.active || !this.visible) {
/* 139 */       return false;
/*     */     }
/* 141 */     if (isValidClickButton($$2)) {
/* 142 */       boolean $$3 = clicked($$0, $$1);
/* 143 */       if ($$3) {
/* 144 */         playDownSound(Minecraft.getInstance().getSoundManager());
/* 145 */         onClick($$0, $$1);
/* 146 */         return true;
/*     */       } 
/*     */     } 
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 154 */     if (isValidClickButton($$2)) {
/* 155 */       onRelease($$0, $$1);
/* 156 */       return true;
/*     */     } 
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isValidClickButton(int $$0) {
/* 162 */     return ($$0 == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 167 */     if (isValidClickButton($$2)) {
/* 168 */       onDrag($$0, $$1, $$3, $$4);
/* 169 */       return true;
/*     */     } 
/* 171 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean clicked(double $$0, double $$1) {
/* 175 */     return (this.active && this.visible && $$0 >= getX() && $$1 >= getY() && $$0 < (getX() + getWidth()) && $$1 < (getY() + getHeight()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 181 */     if (!this.active || !this.visible) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     if (!isFocused()) {
/* 186 */       return ComponentPath.leaf(this);
/*     */     }
/* 188 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(double $$0, double $$1) {
/* 193 */     return (this.active && this.visible && $$0 >= getX() && $$1 >= getY() && $$0 < (getX() + this.width) && $$1 < (getY() + this.height));
/*     */   }
/*     */   
/*     */   public void playDownSound(SoundManager $$0) {
/* 197 */     $$0.play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 202 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(int $$0) {
/* 206 */     this.width = $$0;
/*     */   }
/*     */   
/*     */   public void setHeight(int $$0) {
/* 210 */     this.height = $$0;
/*     */   }
/*     */   
/*     */   public void setAlpha(float $$0) {
/* 214 */     this.alpha = $$0;
/*     */   }
/*     */   
/*     */   public void setMessage(Component $$0) {
/* 218 */     this.message = $$0;
/*     */   }
/*     */   
/*     */   public Component getMessage() {
/* 222 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFocused() {
/* 227 */     return this.focused;
/*     */   }
/*     */   
/*     */   public boolean isHovered() {
/* 231 */     return this.isHovered;
/*     */   }
/*     */   
/*     */   public boolean isHoveredOrFocused() {
/* 235 */     return (isHovered() || isFocused());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 240 */     return (this.visible && this.active);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocused(boolean $$0) {
/* 248 */     this.focused = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public NarratableEntry.NarrationPriority narrationPriority() {
/* 253 */     if (isFocused()) {
/* 254 */       return NarratableEntry.NarrationPriority.FOCUSED;
/*     */     }
/* 256 */     if (this.isHovered) {
/* 257 */       return NarratableEntry.NarrationPriority.HOVERED;
/*     */     }
/* 259 */     return NarratableEntry.NarrationPriority.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void updateNarration(NarrationElementOutput $$0) {
/* 264 */     updateWidgetNarration($$0);
/* 265 */     if (this.tooltip != null) {
/* 266 */       this.tooltip.updateNarration($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void updateWidgetNarration(NarrationElementOutput paramNarrationElementOutput);
/*     */   
/*     */   protected void defaultButtonNarrationText(NarrationElementOutput $$0) {
/* 274 */     $$0.add(NarratedElementType.TITLE, (Component)createNarrationMessage());
/* 275 */     if (this.active) {
/* 276 */       if (isFocused()) {
/* 277 */         $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.button.usage.focused"));
/*     */       } else {
/* 279 */         $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.button.usage.hovered"));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/* 286 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setX(int $$0) {
/* 291 */     this.x = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/* 296 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setY(int $$0) {
/* 301 */     this.y = $$0;
/*     */   }
/*     */   
/*     */   public int getRight() {
/* 305 */     return getX() + getWidth();
/*     */   }
/*     */   
/*     */   public int getBottom() {
/* 309 */     return getY() + getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitWidgets(Consumer<AbstractWidget> $$0) {
/* 314 */     $$0.accept(this);
/*     */   }
/*     */   
/*     */   public void setSize(int $$0, int $$1) {
/* 318 */     this.width = $$0;
/* 319 */     this.height = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ScreenRectangle getRectangle() {
/* 324 */     return super.getRectangle();
/*     */   }
/*     */   
/*     */   public void setRectangle(int $$0, int $$1, int $$2, int $$3) {
/* 328 */     setSize($$0, $$1);
/* 329 */     setPosition($$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTabOrderGroup() {
/* 334 */     return this.tabOrderGroup;
/*     */   }
/*     */   
/*     */   public void setTabOrderGroup(int $$0) {
/* 338 */     this.tabOrderGroup = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\AbstractWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */