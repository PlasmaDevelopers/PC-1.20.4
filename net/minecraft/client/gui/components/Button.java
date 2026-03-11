/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ 
/*     */ public class Button extends AbstractButton {
/*     */   public static final int SMALL_WIDTH = 120;
/*     */   public static final int DEFAULT_WIDTH = 150;
/*     */   public static final int DEFAULT_HEIGHT = 20;
/*     */   
/*     */   static {
/*  15 */     DEFAULT_NARRATION = ($$0 -> (MutableComponent)$$0.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_SPACING = 8;
/*     */   protected static final CreateNarration DEFAULT_NARRATION;
/*     */   protected final OnPress onPress;
/*     */   protected final CreateNarration createNarration;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final Component message;
/*     */     private final Button.OnPress onPress;
/*     */     @Nullable
/*     */     private Tooltip tooltip;
/*     */     private int x;
/*     */     private int y;
/*  32 */     private int width = 150;
/*  33 */     private int height = 20;
/*  34 */     private Button.CreateNarration createNarration = Button.DEFAULT_NARRATION;
/*     */     
/*     */     public Builder(Component $$0, Button.OnPress $$1) {
/*  37 */       this.message = $$0;
/*  38 */       this.onPress = $$1;
/*     */     }
/*     */     
/*     */     public Builder pos(int $$0, int $$1) {
/*  42 */       this.x = $$0;
/*  43 */       this.y = $$1;
/*  44 */       return this;
/*     */     }
/*     */     
/*     */     public Builder width(int $$0) {
/*  48 */       this.width = $$0;
/*  49 */       return this;
/*     */     }
/*     */     
/*     */     public Builder size(int $$0, int $$1) {
/*  53 */       this.width = $$0;
/*  54 */       this.height = $$1;
/*  55 */       return this;
/*     */     }
/*     */     
/*     */     public Builder bounds(int $$0, int $$1, int $$2, int $$3) {
/*  59 */       return pos($$0, $$1).size($$2, $$3);
/*     */     }
/*     */     
/*     */     public Builder tooltip(@Nullable Tooltip $$0) {
/*  63 */       this.tooltip = $$0;
/*  64 */       return this;
/*     */     }
/*     */     
/*     */     public Builder createNarration(Button.CreateNarration $$0) {
/*  68 */       this.createNarration = $$0;
/*  69 */       return this;
/*     */     }
/*     */     
/*     */     public Button build() {
/*  73 */       Button $$0 = new Button(this.x, this.y, this.width, this.height, this.message, this.onPress, this.createNarration);
/*  74 */       $$0.setTooltip(this.tooltip);
/*  75 */       return $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder builder(Component $$0, OnPress $$1) {
/*  80 */     return new Builder($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Button(int $$0, int $$1, int $$2, int $$3, Component $$4, OnPress $$5, CreateNarration $$6) {
/*  87 */     super($$0, $$1, $$2, $$3, $$4);
/*     */     
/*  89 */     this.onPress = $$5;
/*  90 */     this.createNarration = $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPress() {
/*  95 */     this.onPress.onPress(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected MutableComponent createNarrationMessage() {
/* 100 */     return this.createNarration.createNarrationMessage(() -> super.createNarrationMessage());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 105 */     defaultButtonNarrationText($$0);
/*     */   }
/*     */   
/*     */   public static interface OnPress {
/*     */     void onPress(Button param1Button);
/*     */   }
/*     */   
/*     */   public static interface CreateNarration {
/*     */     MutableComponent createNarrationMessage(Supplier<MutableComponent> param1Supplier);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\Button.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */