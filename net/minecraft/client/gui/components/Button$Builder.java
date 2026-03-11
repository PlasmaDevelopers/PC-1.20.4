/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/*    */   private final Component message;
/*    */   private final Button.OnPress onPress;
/*    */   @Nullable
/*    */   private Tooltip tooltip;
/*    */   private int x;
/*    */   private int y;
/* 32 */   private int width = 150;
/* 33 */   private int height = 20;
/* 34 */   private Button.CreateNarration createNarration = Button.DEFAULT_NARRATION;
/*    */   
/*    */   public Builder(Component $$0, Button.OnPress $$1) {
/* 37 */     this.message = $$0;
/* 38 */     this.onPress = $$1;
/*    */   }
/*    */   
/*    */   public Builder pos(int $$0, int $$1) {
/* 42 */     this.x = $$0;
/* 43 */     this.y = $$1;
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public Builder width(int $$0) {
/* 48 */     this.width = $$0;
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public Builder size(int $$0, int $$1) {
/* 53 */     this.width = $$0;
/* 54 */     this.height = $$1;
/* 55 */     return this;
/*    */   }
/*    */   
/*    */   public Builder bounds(int $$0, int $$1, int $$2, int $$3) {
/* 59 */     return pos($$0, $$1).size($$2, $$3);
/*    */   }
/*    */   
/*    */   public Builder tooltip(@Nullable Tooltip $$0) {
/* 63 */     this.tooltip = $$0;
/* 64 */     return this;
/*    */   }
/*    */   
/*    */   public Builder createNarration(Button.CreateNarration $$0) {
/* 68 */     this.createNarration = $$0;
/* 69 */     return this;
/*    */   }
/*    */   
/*    */   public Button build() {
/* 73 */     Button $$0 = new Button(this.x, this.y, this.width, this.height, this.message, this.onPress, this.createNarration);
/* 74 */     $$0.setTooltip(this.tooltip);
/* 75 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\Button$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */