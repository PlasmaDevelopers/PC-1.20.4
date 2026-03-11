/*    */ package net.minecraft.client.gui.screens.worldselection;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.client.gui.layouts.GridLayout;
/*    */ import net.minecraft.client.gui.layouts.LayoutElement;
/*    */ import net.minecraft.client.gui.layouts.SpacerElement;
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
/*    */ public class Builder
/*    */ {
/*    */   final int width;
/* 29 */   private final List<SwitchGrid.SwitchBuilder> switchBuilders = new ArrayList<>();
/*    */   int paddingLeft;
/* 31 */   int rowSpacing = 4;
/*    */   int rowCount;
/* 33 */   Optional<SwitchGrid.InfoUnderneathSettings> infoUnderneath = Optional.empty();
/*    */   
/*    */   public Builder(int $$0) {
/* 36 */     this.width = $$0;
/*    */   }
/*    */   
/*    */   void increaseRow() {
/* 40 */     this.rowCount++;
/*    */   }
/*    */   
/*    */   public SwitchGrid.SwitchBuilder addSwitch(Component $$0, BooleanSupplier $$1, Consumer<Boolean> $$2) {
/* 44 */     SwitchGrid.SwitchBuilder $$3 = new SwitchGrid.SwitchBuilder($$0, $$1, $$2, 44);
/* 45 */     this.switchBuilders.add($$3);
/* 46 */     return $$3;
/*    */   }
/*    */   
/*    */   public Builder withPaddingLeft(int $$0) {
/* 50 */     this.paddingLeft = $$0;
/* 51 */     return this;
/*    */   }
/*    */   
/*    */   public Builder withRowSpacing(int $$0) {
/* 55 */     this.rowSpacing = $$0;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public SwitchGrid build(Consumer<LayoutElement> $$0) {
/* 61 */     GridLayout $$1 = (new GridLayout()).rowSpacing(this.rowSpacing);
/*    */     
/* 63 */     $$1.addChild((LayoutElement)SpacerElement.width(this.width - 44), 0, 0);
/* 64 */     $$1.addChild((LayoutElement)SpacerElement.width(44), 0, 1);
/*    */     
/* 66 */     List<SwitchGrid.LabeledSwitch> $$2 = new ArrayList<>();
/* 67 */     this.rowCount = 0;
/* 68 */     for (SwitchGrid.SwitchBuilder $$3 : this.switchBuilders) {
/* 69 */       $$2.add($$3.build(this, $$1, 0));
/*    */     }
/*    */     
/* 72 */     $$1.arrangeElements();
/* 73 */     $$0.accept($$1);
/* 74 */     SwitchGrid $$4 = new SwitchGrid($$2);
/* 75 */     $$4.refreshStates();
/* 76 */     return $$4;
/*    */   }
/*    */   
/*    */   public Builder withInfoUnderneath(int $$0, boolean $$1) {
/* 80 */     this.infoUnderneath = Optional.of(new SwitchGrid.InfoUnderneathSettings($$0, $$1));
/* 81 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\SwitchGrid$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */