/*     */ package net.minecraft.client.gui.layouts;
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
/*     */ public interface LayoutSettings
/*     */ {
/*     */   LayoutSettings padding(int paramInt);
/*     */   
/*     */   LayoutSettings padding(int paramInt1, int paramInt2);
/*     */   
/*     */   LayoutSettings padding(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   LayoutSettings paddingLeft(int paramInt);
/*     */   
/*     */   LayoutSettings paddingTop(int paramInt);
/*     */   
/*     */   LayoutSettings paddingRight(int paramInt);
/*     */   
/*     */   LayoutSettings paddingBottom(int paramInt);
/*     */   
/*     */   LayoutSettings paddingHorizontal(int paramInt);
/*     */   
/*     */   LayoutSettings paddingVertical(int paramInt);
/*     */   
/*     */   LayoutSettings align(float paramFloat1, float paramFloat2);
/*     */   
/*     */   LayoutSettings alignHorizontally(float paramFloat);
/*     */   
/*     */   LayoutSettings alignVertically(float paramFloat);
/*     */   
/*     */   default LayoutSettings alignHorizontallyLeft() {
/*  39 */     return alignHorizontally(0.0F);
/*     */   }
/*     */   
/*     */   default LayoutSettings alignHorizontallyCenter() {
/*  43 */     return alignHorizontally(0.5F);
/*     */   }
/*     */   
/*     */   default LayoutSettings alignHorizontallyRight() {
/*  47 */     return alignHorizontally(1.0F);
/*     */   }
/*     */   
/*     */   default LayoutSettings alignVerticallyTop() {
/*  51 */     return alignVertically(0.0F);
/*     */   }
/*     */   
/*     */   default LayoutSettings alignVerticallyMiddle() {
/*  55 */     return alignVertically(0.5F);
/*     */   }
/*     */   
/*     */   default LayoutSettings alignVerticallyBottom() {
/*  59 */     return alignVertically(1.0F);
/*     */   }
/*     */   
/*     */   LayoutSettings copy();
/*     */   
/*     */   LayoutSettingsImpl getExposed();
/*     */   
/*     */   static LayoutSettings defaults() {
/*  67 */     return new LayoutSettingsImpl();
/*     */   }
/*     */   
/*     */   public static class LayoutSettingsImpl
/*     */     implements LayoutSettings {
/*     */     public int paddingLeft;
/*     */     public int paddingTop;
/*     */     public int paddingRight;
/*     */     public int paddingBottom;
/*     */     public float xAlignment;
/*     */     public float yAlignment;
/*     */     
/*     */     public LayoutSettingsImpl() {}
/*     */     
/*     */     public LayoutSettingsImpl(LayoutSettingsImpl $$0) {
/*  82 */       this.paddingLeft = $$0.paddingLeft;
/*  83 */       this.paddingTop = $$0.paddingTop;
/*  84 */       this.paddingRight = $$0.paddingRight;
/*  85 */       this.paddingBottom = $$0.paddingBottom;
/*  86 */       this.xAlignment = $$0.xAlignment;
/*  87 */       this.yAlignment = $$0.yAlignment;
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl padding(int $$0) {
/*  92 */       return padding($$0, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl padding(int $$0, int $$1) {
/*  97 */       return paddingHorizontal($$0).paddingVertical($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl padding(int $$0, int $$1, int $$2, int $$3) {
/* 102 */       return paddingLeft($$0)
/* 103 */         .paddingRight($$2)
/* 104 */         .paddingTop($$1)
/* 105 */         .paddingBottom($$3);
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl paddingLeft(int $$0) {
/* 110 */       this.paddingLeft = $$0;
/* 111 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl paddingTop(int $$0) {
/* 116 */       this.paddingTop = $$0;
/* 117 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl paddingRight(int $$0) {
/* 122 */       this.paddingRight = $$0;
/* 123 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl paddingBottom(int $$0) {
/* 128 */       this.paddingBottom = $$0;
/* 129 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl paddingHorizontal(int $$0) {
/* 134 */       return paddingLeft($$0).paddingRight($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl paddingVertical(int $$0) {
/* 139 */       return paddingTop($$0).paddingBottom($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl align(float $$0, float $$1) {
/* 144 */       this.xAlignment = $$0;
/* 145 */       this.yAlignment = $$1;
/* 146 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl alignHorizontally(float $$0) {
/* 151 */       this.xAlignment = $$0;
/* 152 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl alignVertically(float $$0) {
/* 157 */       this.yAlignment = $$0;
/* 158 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl copy() {
/* 163 */       return new LayoutSettingsImpl(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public LayoutSettingsImpl getExposed() {
/* 168 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\LayoutSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */