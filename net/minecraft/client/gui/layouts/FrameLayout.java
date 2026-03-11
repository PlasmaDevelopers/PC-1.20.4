/*     */ package net.minecraft.client.gui.layouts;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FrameLayout
/*     */   extends AbstractLayout
/*     */ {
/*  19 */   private final List<ChildContainer> children = new ArrayList<>();
/*     */   
/*     */   private int minWidth;
/*     */   private int minHeight;
/*  23 */   private final LayoutSettings defaultChildLayoutSettings = LayoutSettings.defaults().align(0.5F, 0.5F);
/*     */   
/*     */   public FrameLayout() {
/*  26 */     this(0, 0, 0, 0);
/*     */   }
/*     */   
/*     */   public FrameLayout(int $$0, int $$1) {
/*  30 */     this(0, 0, $$0, $$1);
/*     */   }
/*     */   
/*     */   public FrameLayout(int $$0, int $$1, int $$2, int $$3) {
/*  34 */     super($$0, $$1, $$2, $$3);
/*  35 */     setMinDimensions($$2, $$3);
/*     */   }
/*     */   
/*     */   public FrameLayout setMinDimensions(int $$0, int $$1) {
/*  39 */     return setMinWidth($$0).setMinHeight($$1);
/*     */   }
/*     */   
/*     */   public FrameLayout setMinHeight(int $$0) {
/*  43 */     this.minHeight = $$0;
/*  44 */     return this;
/*     */   }
/*     */   
/*     */   public FrameLayout setMinWidth(int $$0) {
/*  48 */     this.minWidth = $$0;
/*  49 */     return this;
/*     */   }
/*     */   
/*     */   public LayoutSettings newChildLayoutSettings() {
/*  53 */     return this.defaultChildLayoutSettings.copy();
/*     */   }
/*     */   
/*     */   public LayoutSettings defaultChildLayoutSetting() {
/*  57 */     return this.defaultChildLayoutSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrangeElements() {
/*  62 */     super.arrangeElements();
/*     */     
/*  64 */     int $$0 = this.minWidth;
/*  65 */     int $$1 = this.minHeight;
/*     */     
/*  67 */     for (ChildContainer $$2 : this.children) {
/*  68 */       $$0 = Math.max($$0, $$2.getWidth());
/*  69 */       $$1 = Math.max($$1, $$2.getHeight());
/*     */     } 
/*     */     
/*  72 */     for (ChildContainer $$3 : this.children) {
/*  73 */       $$3.setX(getX(), $$0);
/*  74 */       $$3.setY(getY(), $$1);
/*     */     } 
/*     */     
/*  77 */     this.width = $$0;
/*  78 */     this.height = $$1;
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0) {
/*  82 */     return addChild($$0, newChildLayoutSettings());
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, LayoutSettings $$1) {
/*  86 */     this.children.add(new ChildContainer((LayoutElement)$$0, $$1));
/*  87 */     return $$0;
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, Consumer<LayoutSettings> $$1) {
/*  91 */     return addChild($$0, (LayoutSettings)Util.make(newChildLayoutSettings(), $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitChildren(Consumer<LayoutElement> $$0) {
/*  96 */     this.children.forEach($$1 -> $$0.accept($$1.child));
/*     */   }
/*     */   
/*     */   public static void centerInRectangle(LayoutElement $$0, int $$1, int $$2, int $$3, int $$4) {
/* 100 */     alignInRectangle($$0, $$1, $$2, $$3, $$4, 0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public static void centerInRectangle(LayoutElement $$0, ScreenRectangle $$1) {
/* 104 */     centerInRectangle($$0, $$1.position().x(), $$1.position().y(), $$1.width(), $$1.height());
/*     */   }
/*     */   
/*     */   public static void alignInRectangle(LayoutElement $$0, ScreenRectangle $$1, float $$2, float $$3) {
/* 108 */     alignInRectangle($$0, $$1.left(), $$1.top(), $$1.width(), $$1.height(), $$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void alignInRectangle(LayoutElement $$0, int $$1, int $$2, int $$3, int $$4, float $$5, float $$6) {
/* 119 */     Objects.requireNonNull($$0); alignInDimension($$1, $$3, $$0.getWidth(), $$0::setX, $$5);
/* 120 */     Objects.requireNonNull($$0); alignInDimension($$2, $$4, $$0.getHeight(), $$0::setY, $$6);
/*     */   }
/*     */   
/*     */   public static void alignInDimension(int $$0, int $$1, int $$2, Consumer<Integer> $$3, float $$4) {
/* 124 */     int $$5 = (int)Mth.lerp($$4, 0.0F, ($$1 - $$2));
/* 125 */     $$3.accept(Integer.valueOf($$0 + $$5));
/*     */   }
/*     */   
/*     */   private static class ChildContainer extends AbstractLayout.AbstractChildWrapper {
/*     */     protected ChildContainer(LayoutElement $$0, LayoutSettings $$1) {
/* 130 */       super($$0, $$1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\FrameLayout.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */