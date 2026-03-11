/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SwitchBuilder
/*     */ {
/*     */   private final Component label;
/*     */   private final BooleanSupplier stateSupplier;
/*     */   private final Consumer<Boolean> onClicked;
/*     */   @Nullable
/*     */   private Component info;
/*     */   @Nullable
/*     */   private BooleanSupplier isActiveCondition;
/*     */   private final int buttonWidth;
/*     */   
/*     */   SwitchBuilder(Component $$0, BooleanSupplier $$1, Consumer<Boolean> $$2, int $$3) {
/* 110 */     this.label = $$0;
/* 111 */     this.stateSupplier = $$1;
/* 112 */     this.onClicked = $$2;
/* 113 */     this.buttonWidth = $$3;
/*     */   }
/*     */   
/*     */   public SwitchBuilder withIsActiveCondition(BooleanSupplier $$0) {
/* 117 */     this.isActiveCondition = $$0;
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public SwitchBuilder withInfo(Component $$0) {
/* 122 */     this.info = $$0;
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   SwitchGrid.LabeledSwitch build(SwitchGrid.Builder $$0, GridLayout $$1, int $$2) {
/* 127 */     $$0.increaseRow();
/* 128 */     StringWidget $$3 = (new StringWidget(this.label, (Minecraft.getInstance()).font)).alignLeft();
/* 129 */     $$1.addChild((LayoutElement)$$3, $$0.rowCount, $$2, $$1.newCellSettings().align(0.0F, 0.5F).paddingLeft($$0.paddingLeft));
/*     */     
/* 131 */     Optional<SwitchGrid.InfoUnderneathSettings> $$4 = $$0.infoUnderneath;
/*     */     
/* 133 */     CycleButton.Builder<Boolean> $$5 = CycleButton.onOffBuilder(this.stateSupplier.getAsBoolean());
/* 134 */     $$5.displayOnlyValue();
/*     */     
/* 136 */     boolean $$6 = (this.info != null && $$4.isEmpty());
/* 137 */     if ($$6) {
/* 138 */       Tooltip $$7 = Tooltip.create(this.info);
/* 139 */       $$5.withTooltip($$1 -> $$0);
/*     */     } 
/*     */     
/* 142 */     if (this.info != null && !$$6) {
/* 143 */       $$5.withCustomNarration($$0 -> CommonComponents.joinForNarration(new Component[] { this.label, (Component)$$0.createDefaultNarrationMessage(), this.info }));
/*     */     } else {
/* 145 */       $$5.withCustomNarration($$0 -> CommonComponents.joinForNarration(new Component[] { this.label, (Component)$$0.createDefaultNarrationMessage() }));
/*     */     } 
/*     */     
/* 148 */     CycleButton<Boolean> $$8 = $$5.create(0, 0, this.buttonWidth, 20, (Component)Component.empty(), ($$0, $$1) -> this.onClicked.accept($$1));
/* 149 */     if (this.isActiveCondition != null) {
/* 150 */       $$8.active = this.isActiveCondition.getAsBoolean();
/*     */     }
/* 152 */     $$1.addChild((LayoutElement)$$8, $$0.rowCount, $$2 + 1, $$1.newCellSettings().alignHorizontallyRight());
/*     */     
/* 154 */     if (this.info != null) {
/* 155 */       $$4.ifPresent($$3 -> {
/*     */             MutableComponent mutableComponent = this.info.copy().withStyle(ChatFormatting.GRAY);
/*     */             
/*     */             Font $$5 = (Minecraft.getInstance()).font;
/*     */             MultiLineTextWidget $$6 = new MultiLineTextWidget((Component)mutableComponent, $$5);
/*     */             $$6.setMaxWidth($$0.width - $$0.paddingLeft - this.buttonWidth);
/*     */             $$6.setMaxRows($$3.maxInfoRows());
/*     */             $$0.increaseRow();
/*     */             Objects.requireNonNull($$5);
/*     */             int $$7 = $$3.alwaysMaxHeight ? (9 * $$3.maxInfoRows - $$6.getHeight()) : 0;
/*     */             $$1.addChild((LayoutElement)$$6, $$0.rowCount, $$2, $$1.newCellSettings().paddingTop(-$$0.rowSpacing).paddingBottom($$7));
/*     */           });
/*     */     }
/* 168 */     return new SwitchGrid.LabeledSwitch($$8, this.stateSupplier, this.isActiveCondition);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\SwitchGrid$SwitchBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */