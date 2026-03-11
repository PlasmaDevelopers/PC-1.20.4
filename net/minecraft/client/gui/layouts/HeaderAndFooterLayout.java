/*     */ package net.minecraft.client.gui.layouts;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ 
/*     */ 
/*     */ public class HeaderAndFooterLayout
/*     */   implements Layout
/*     */ {
/*     */   public static final int DEFAULT_HEADER_AND_FOOTER_HEIGHT = 36;
/*     */   private static final int CONTENT_MARGIN_TOP = 30;
/*  12 */   private final FrameLayout headerFrame = new FrameLayout();
/*  13 */   private final FrameLayout footerFrame = new FrameLayout();
/*  14 */   private final FrameLayout contentsFrame = new FrameLayout();
/*     */   
/*     */   private final Screen screen;
/*     */   private int headerHeight;
/*     */   private int footerHeight;
/*     */   
/*     */   public HeaderAndFooterLayout(Screen $$0) {
/*  21 */     this($$0, 36);
/*     */   }
/*     */   
/*     */   public HeaderAndFooterLayout(Screen $$0, int $$1) {
/*  25 */     this($$0, $$1, $$1);
/*     */   }
/*     */   
/*     */   public HeaderAndFooterLayout(Screen $$0, int $$1, int $$2) {
/*  29 */     this.screen = $$0;
/*  30 */     this.headerHeight = $$1;
/*  31 */     this.footerHeight = $$2;
/*  32 */     this.headerFrame.defaultChildLayoutSetting().align(0.5F, 0.5F);
/*  33 */     this.footerFrame.defaultChildLayoutSetting().align(0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(int $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(int $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  48 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/*  53 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  58 */     return this.screen.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  63 */     return this.screen.height;
/*     */   }
/*     */   
/*     */   public int getFooterHeight() {
/*  67 */     return this.footerHeight;
/*     */   }
/*     */   
/*     */   public void setFooterHeight(int $$0) {
/*  71 */     this.footerHeight = $$0;
/*     */   }
/*     */   
/*     */   public void setHeaderHeight(int $$0) {
/*  75 */     this.headerHeight = $$0;
/*     */   }
/*     */   
/*     */   public int getHeaderHeight() {
/*  79 */     return this.headerHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitChildren(Consumer<LayoutElement> $$0) {
/*  84 */     this.headerFrame.visitChildren($$0);
/*  85 */     this.contentsFrame.visitChildren($$0);
/*  86 */     this.footerFrame.visitChildren($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrangeElements() {
/*  91 */     int $$0 = getHeaderHeight();
/*  92 */     int $$1 = getFooterHeight();
/*     */     
/*  94 */     this.headerFrame.setMinWidth(this.screen.width);
/*  95 */     this.headerFrame.setMinHeight($$0);
/*  96 */     this.headerFrame.setPosition(0, 0);
/*  97 */     this.headerFrame.arrangeElements();
/*     */     
/*  99 */     this.footerFrame.setMinWidth(this.screen.width);
/* 100 */     this.footerFrame.setMinHeight($$1);
/* 101 */     this.footerFrame.arrangeElements();
/* 102 */     this.footerFrame.setY(this.screen.height - $$1);
/*     */     
/* 104 */     this.contentsFrame.setMinWidth(this.screen.width);
/* 105 */     this.contentsFrame.arrangeElements();
/*     */     
/* 107 */     int $$2 = $$0 + 30;
/* 108 */     int $$3 = this.screen.height - $$1 - this.contentsFrame.getHeight();
/* 109 */     this.contentsFrame.setPosition(0, Math.min($$2, $$3));
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addToHeader(T $$0) {
/* 113 */     return this.headerFrame.addChild($$0);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addToHeader(T $$0, Consumer<LayoutSettings> $$1) {
/* 117 */     return this.headerFrame.addChild($$0, $$1);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addToFooter(T $$0) {
/* 121 */     return this.footerFrame.addChild($$0);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addToFooter(T $$0, Consumer<LayoutSettings> $$1) {
/* 125 */     return this.footerFrame.addChild($$0, $$1);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addToContents(T $$0) {
/* 129 */     return this.contentsFrame.addChild($$0);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addToContents(T $$0, Consumer<LayoutSettings> $$1) {
/* 133 */     return this.contentsFrame.addChild($$0, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\HeaderAndFooterLayout.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */