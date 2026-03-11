/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.OptionInstance;
/*    */ import net.minecraft.client.gui.Font;
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
/*    */   private final Font font;
/* 41 */   private int x = 0;
/* 42 */   private int y = 0;
/* 43 */   private Checkbox.OnValueChange onValueChange = Checkbox.OnValueChange.NOP;
/*    */   @Nullable
/* 45 */   private OptionInstance<Boolean> option = null; private boolean selected = false;
/*    */   @Nullable
/* 47 */   private Tooltip tooltip = null;
/*    */ 
/*    */   
/*    */   Builder(Component $$0, Font $$1) {
/* 51 */     this.message = $$0;
/* 52 */     this.font = $$1;
/*    */   }
/*    */   
/*    */   public Builder pos(int $$0, int $$1) {
/* 56 */     this.x = $$0;
/* 57 */     this.y = $$1;
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public Builder onValueChange(Checkbox.OnValueChange $$0) {
/* 62 */     this.onValueChange = $$0;
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public Builder selected(boolean $$0) {
/* 67 */     this.selected = $$0;
/* 68 */     this.option = null;
/* 69 */     return this;
/*    */   }
/*    */   
/*    */   public Builder selected(OptionInstance<Boolean> $$0) {
/* 73 */     this.option = $$0;
/* 74 */     this.selected = ((Boolean)$$0.get()).booleanValue();
/* 75 */     return this;
/*    */   }
/*    */   
/*    */   public Builder tooltip(Tooltip $$0) {
/* 79 */     this.tooltip = $$0;
/* 80 */     return this;
/*    */   }
/*    */   
/*    */   public Checkbox build() {
/* 84 */     Checkbox.OnValueChange $$0 = (this.option == null) ? this.onValueChange : (($$0, $$1) -> {
/*    */         this.option.set(Boolean.valueOf($$1));
/*    */         
/*    */         this.onValueChange.onValueChange($$0, $$1);
/*    */       });
/* 89 */     Checkbox $$1 = new Checkbox(this.x, this.y, this.message, this.font, this.selected, $$0);
/* 90 */     $$1.setTooltip(this.tooltip);
/* 91 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\Checkbox$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */