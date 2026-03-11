/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public abstract class AbstractScrollWidget
/*     */   extends AbstractWidget implements Renderable, GuiEventListener {
/*  11 */   private static final WidgetSprites BACKGROUND_SPRITES = new WidgetSprites(new ResourceLocation("widget/text_field"), new ResourceLocation("widget/text_field_highlighted"));
/*  12 */   private static final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("widget/scroller");
/*     */   
/*     */   private static final int INNER_PADDING = 4;
/*     */   
/*     */   private static final int SCROLL_BAR_WIDTH = 8;
/*     */   private double scrollAmount;
/*     */   private boolean scrolling;
/*     */   
/*     */   public AbstractScrollWidget(int $$0, int $$1, int $$2, int $$3, Component $$4) {
/*  21 */     super($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  26 */     if (!this.visible) {
/*  27 */       return false;
/*     */     }
/*     */     
/*  30 */     boolean $$3 = withinContentAreaPoint($$0, $$1);
/*  31 */     boolean $$4 = (scrollbarVisible() && $$0 >= (getX() + this.width) && $$0 <= (getX() + this.width + 8) && $$1 >= getY() && $$1 < (getY() + this.height));
/*     */     
/*  33 */     if ($$4 && $$2 == 0) {
/*  34 */       this.scrolling = true;
/*  35 */       return true;
/*     */     } 
/*     */     
/*  38 */     return ($$3 || $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/*  43 */     if ($$2 == 0) {
/*  44 */       this.scrolling = false;
/*     */     }
/*  46 */     return super.mouseReleased($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/*  51 */     if (!this.visible || !isFocused() || !this.scrolling) {
/*  52 */       return false;
/*     */     }
/*     */     
/*  55 */     if ($$1 < getY()) {
/*  56 */       setScrollAmount(0.0D);
/*  57 */     } else if ($$1 > (getY() + this.height)) {
/*  58 */       setScrollAmount(getMaxScrollAmount());
/*     */     } else {
/*  60 */       int $$5 = getScrollBarHeight();
/*  61 */       double $$6 = Math.max(1, getMaxScrollAmount() / (this.height - $$5));
/*  62 */       setScrollAmount(this.scrollAmount + $$4 * $$6);
/*     */     } 
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/*  69 */     if (!this.visible) {
/*  70 */       return false;
/*     */     }
/*     */     
/*  73 */     setScrollAmount(this.scrollAmount - $$3 * scrollRate());
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  79 */     boolean $$3 = ($$0 == 265);
/*  80 */     boolean $$4 = ($$0 == 264);
/*  81 */     if ($$3 || $$4) {
/*  82 */       double $$5 = this.scrollAmount;
/*  83 */       setScrollAmount(this.scrollAmount + ($$3 ? -1 : true) * scrollRate());
/*  84 */       if ($$5 != this.scrollAmount) {
/*  85 */         return true;
/*     */       }
/*     */     } 
/*  88 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  93 */     if (!this.visible) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     renderBackground($$0);
/*     */     
/*  99 */     $$0.enableScissor(getX() + 1, getY() + 1, getX() + this.width - 1, getY() + this.height - 1);
/*     */     
/* 101 */     $$0.pose().pushPose();
/* 102 */     $$0.pose().translate(0.0D, -this.scrollAmount, 0.0D);
/* 103 */     renderContents($$0, $$1, $$2, $$3);
/* 104 */     $$0.pose().popPose();
/*     */     
/* 106 */     $$0.disableScissor();
/*     */     
/* 108 */     renderDecorations($$0);
/*     */   }
/*     */   
/*     */   private int getScrollBarHeight() {
/* 112 */     return Mth.clamp((int)((this.height * this.height) / getContentHeight()), 32, this.height);
/*     */   }
/*     */   
/*     */   protected void renderDecorations(GuiGraphics $$0) {
/* 116 */     if (scrollbarVisible()) {
/* 117 */       renderScrollBar($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected int innerPadding() {
/* 122 */     return 4;
/*     */   }
/*     */   
/*     */   protected int totalInnerPadding() {
/* 126 */     return innerPadding() * 2;
/*     */   }
/*     */   
/*     */   protected double scrollAmount() {
/* 130 */     return this.scrollAmount;
/*     */   }
/*     */   
/*     */   protected void setScrollAmount(double $$0) {
/* 134 */     this.scrollAmount = Mth.clamp($$0, 0.0D, getMaxScrollAmount());
/*     */   }
/*     */   
/*     */   protected int getMaxScrollAmount() {
/* 138 */     return Math.max(0, getContentHeight() - this.height - 4);
/*     */   }
/*     */   
/*     */   private int getContentHeight() {
/* 142 */     return getInnerHeight() + 4;
/*     */   }
/*     */   
/*     */   protected void renderBackground(GuiGraphics $$0) {
/* 146 */     renderBorder($$0, getX(), getY(), getWidth(), getHeight());
/*     */   }
/*     */   
/*     */   protected void renderBorder(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 150 */     ResourceLocation $$5 = BACKGROUND_SPRITES.get(isActive(), isFocused());
/* 151 */     $$0.blitSprite($$5, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private void renderScrollBar(GuiGraphics $$0) {
/* 155 */     int $$1 = getScrollBarHeight();
/* 156 */     int $$2 = getX() + this.width;
/* 157 */     int $$3 = Math.max(getY(), (int)this.scrollAmount * (this.height - $$1) / getMaxScrollAmount() + getY());
/*     */     
/* 159 */     $$0.blitSprite(SCROLLER_SPRITE, $$2, $$3, 8, $$1);
/*     */   }
/*     */   
/*     */   protected boolean withinContentAreaTopBottom(int $$0, int $$1) {
/* 163 */     return ($$1 - this.scrollAmount >= getY() && $$0 - this.scrollAmount <= (getY() + this.height));
/*     */   }
/*     */   
/*     */   protected boolean withinContentAreaPoint(double $$0, double $$1) {
/* 167 */     return ($$0 >= getX() && $$0 < (getX() + this.width) && $$1 >= getY() && $$1 < (getY() + this.height));
/*     */   }
/*     */   
/*     */   protected boolean scrollbarVisible() {
/* 171 */     return (getInnerHeight() > getHeight());
/*     */   }
/*     */   
/*     */   public int scrollbarWidth() {
/* 175 */     return 8;
/*     */   }
/*     */   
/*     */   protected abstract int getInnerHeight();
/*     */   
/*     */   protected abstract double scrollRate();
/*     */   
/*     */   protected abstract void renderContents(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2, float paramFloat);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\AbstractScrollWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */