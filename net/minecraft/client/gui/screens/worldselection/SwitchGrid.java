/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ import net.minecraft.client.gui.layouts.SpacerElement;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ 
/*     */ class SwitchGrid {
/*     */   private static final int DEFAULT_SWITCH_BUTTON_WIDTH = 44;
/*     */   private final List<LabeledSwitch> switches;
/*     */   
/*     */   public static class Builder {
/*     */     final int width;
/*  29 */     private final List<SwitchGrid.SwitchBuilder> switchBuilders = new ArrayList<>();
/*     */     int paddingLeft;
/*  31 */     int rowSpacing = 4;
/*     */     int rowCount;
/*  33 */     Optional<SwitchGrid.InfoUnderneathSettings> infoUnderneath = Optional.empty();
/*     */     
/*     */     public Builder(int $$0) {
/*  36 */       this.width = $$0;
/*     */     }
/*     */     
/*     */     void increaseRow() {
/*  40 */       this.rowCount++;
/*     */     }
/*     */     
/*     */     public SwitchGrid.SwitchBuilder addSwitch(Component $$0, BooleanSupplier $$1, Consumer<Boolean> $$2) {
/*  44 */       SwitchGrid.SwitchBuilder $$3 = new SwitchGrid.SwitchBuilder($$0, $$1, $$2, 44);
/*  45 */       this.switchBuilders.add($$3);
/*  46 */       return $$3;
/*     */     }
/*     */     
/*     */     public Builder withPaddingLeft(int $$0) {
/*  50 */       this.paddingLeft = $$0;
/*  51 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withRowSpacing(int $$0) {
/*  55 */       this.rowSpacing = $$0;
/*  56 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public SwitchGrid build(Consumer<LayoutElement> $$0) {
/*  61 */       GridLayout $$1 = (new GridLayout()).rowSpacing(this.rowSpacing);
/*     */       
/*  63 */       $$1.addChild((LayoutElement)SpacerElement.width(this.width - 44), 0, 0);
/*  64 */       $$1.addChild((LayoutElement)SpacerElement.width(44), 0, 1);
/*     */       
/*  66 */       List<SwitchGrid.LabeledSwitch> $$2 = new ArrayList<>();
/*  67 */       this.rowCount = 0;
/*  68 */       for (SwitchGrid.SwitchBuilder $$3 : this.switchBuilders) {
/*  69 */         $$2.add($$3.build(this, $$1, 0));
/*     */       }
/*     */       
/*  72 */       $$1.arrangeElements();
/*  73 */       $$0.accept($$1);
/*  74 */       SwitchGrid $$4 = new SwitchGrid($$2);
/*  75 */       $$4.refreshStates();
/*  76 */       return $$4;
/*     */     }
/*     */     
/*     */     public Builder withInfoUnderneath(int $$0, boolean $$1) {
/*  80 */       this.infoUnderneath = Optional.of(new SwitchGrid.InfoUnderneathSettings($$0, $$1));
/*  81 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SwitchGrid(List<LabeledSwitch> $$0) {
/*  88 */     this.switches = $$0;
/*     */   }
/*     */   
/*     */   public void refreshStates() {
/*  92 */     this.switches.forEach(LabeledSwitch::refreshState);
/*     */   }
/*     */   
/*     */   public static Builder builder(int $$0) {
/*  96 */     return new Builder($$0);
/*     */   }
/*     */   
/*     */   public static class SwitchBuilder {
/*     */     private final Component label;
/*     */     private final BooleanSupplier stateSupplier;
/*     */     private final Consumer<Boolean> onClicked;
/*     */     @Nullable
/*     */     private Component info;
/*     */     @Nullable
/*     */     private BooleanSupplier isActiveCondition;
/*     */     private final int buttonWidth;
/*     */     
/*     */     SwitchBuilder(Component $$0, BooleanSupplier $$1, Consumer<Boolean> $$2, int $$3) {
/* 110 */       this.label = $$0;
/* 111 */       this.stateSupplier = $$1;
/* 112 */       this.onClicked = $$2;
/* 113 */       this.buttonWidth = $$3;
/*     */     }
/*     */     
/*     */     public SwitchBuilder withIsActiveCondition(BooleanSupplier $$0) {
/* 117 */       this.isActiveCondition = $$0;
/* 118 */       return this;
/*     */     }
/*     */     
/*     */     public SwitchBuilder withInfo(Component $$0) {
/* 122 */       this.info = $$0;
/* 123 */       return this;
/*     */     }
/*     */     
/*     */     SwitchGrid.LabeledSwitch build(SwitchGrid.Builder $$0, GridLayout $$1, int $$2) {
/* 127 */       $$0.increaseRow();
/* 128 */       StringWidget $$3 = (new StringWidget(this.label, (Minecraft.getInstance()).font)).alignLeft();
/* 129 */       $$1.addChild((LayoutElement)$$3, $$0.rowCount, $$2, $$1.newCellSettings().align(0.0F, 0.5F).paddingLeft($$0.paddingLeft));
/*     */       
/* 131 */       Optional<SwitchGrid.InfoUnderneathSettings> $$4 = $$0.infoUnderneath;
/*     */       
/* 133 */       CycleButton.Builder<Boolean> $$5 = CycleButton.onOffBuilder(this.stateSupplier.getAsBoolean());
/* 134 */       $$5.displayOnlyValue();
/*     */       
/* 136 */       boolean $$6 = (this.info != null && $$4.isEmpty());
/* 137 */       if ($$6) {
/* 138 */         Tooltip $$7 = Tooltip.create(this.info);
/* 139 */         $$5.withTooltip($$1 -> $$0);
/*     */       } 
/*     */       
/* 142 */       if (this.info != null && !$$6) {
/* 143 */         $$5.withCustomNarration($$0 -> CommonComponents.joinForNarration(new Component[] { this.label, (Component)$$0.createDefaultNarrationMessage(), this.info }));
/*     */       } else {
/* 145 */         $$5.withCustomNarration($$0 -> CommonComponents.joinForNarration(new Component[] { this.label, (Component)$$0.createDefaultNarrationMessage() }));
/*     */       } 
/*     */       
/* 148 */       CycleButton<Boolean> $$8 = $$5.create(0, 0, this.buttonWidth, 20, (Component)Component.empty(), ($$0, $$1) -> this.onClicked.accept($$1));
/* 149 */       if (this.isActiveCondition != null) {
/* 150 */         $$8.active = this.isActiveCondition.getAsBoolean();
/*     */       }
/* 152 */       $$1.addChild((LayoutElement)$$8, $$0.rowCount, $$2 + 1, $$1.newCellSettings().alignHorizontallyRight());
/*     */       
/* 154 */       if (this.info != null) {
/* 155 */         $$4.ifPresent($$3 -> {
/*     */               MutableComponent mutableComponent = this.info.copy().withStyle(ChatFormatting.GRAY);
/*     */               
/*     */               Font $$5 = (Minecraft.getInstance()).font;
/*     */               MultiLineTextWidget $$6 = new MultiLineTextWidget((Component)mutableComponent, $$5);
/*     */               $$6.setMaxWidth($$0.width - $$0.paddingLeft - this.buttonWidth);
/*     */               $$6.setMaxRows($$3.maxInfoRows());
/*     */               $$0.increaseRow();
/*     */               Objects.requireNonNull($$5);
/*     */               int $$7 = $$3.alwaysMaxHeight ? (9 * $$3.maxInfoRows - $$6.getHeight()) : 0;
/*     */               $$1.addChild((LayoutElement)$$6, $$0.rowCount, $$2, $$1.newCellSettings().paddingTop(-$$0.rowSpacing).paddingBottom($$7));
/*     */             });
/*     */       }
/* 168 */       return new SwitchGrid.LabeledSwitch($$8, this.stateSupplier, this.isActiveCondition);
/*     */     } }
/*     */   private static final class LabeledSwitch extends Record { private final CycleButton<Boolean> button; private final BooleanSupplier stateSupplier; @Nullable
/*     */     private final BooleanSupplier isActiveCondition;
/* 172 */     LabeledSwitch(CycleButton<Boolean> $$0, BooleanSupplier $$1, @Nullable BooleanSupplier $$2) { this.button = $$0; this.stateSupplier = $$1; this.isActiveCondition = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$LabeledSwitch;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #172	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 172 */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$LabeledSwitch; } public CycleButton<Boolean> button() { return this.button; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$LabeledSwitch;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #172	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$LabeledSwitch; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$LabeledSwitch;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #172	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$LabeledSwitch;
/* 172 */       //   0	8	1	$$0	Ljava/lang/Object; } public BooleanSupplier stateSupplier() { return this.stateSupplier; } @Nullable public BooleanSupplier isActiveCondition() { return this.isActiveCondition; }
/*     */      public void refreshState() {
/* 174 */       this.button.setValue(Boolean.valueOf(this.stateSupplier.getAsBoolean()));
/* 175 */       if (this.isActiveCondition != null)
/* 176 */         this.button.active = this.isActiveCondition.getAsBoolean(); 
/*     */     } }
/*     */   private static final class InfoUnderneathSettings extends Record { final int maxInfoRows;
/*     */     final boolean alwaysMaxHeight;
/*     */     
/* 181 */     InfoUnderneathSettings(int $$0, boolean $$1) { this.maxInfoRows = $$0; this.alwaysMaxHeight = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$InfoUnderneathSettings;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #181	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$InfoUnderneathSettings; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$InfoUnderneathSettings;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #181	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$InfoUnderneathSettings; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$InfoUnderneathSettings;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #181	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/worldselection/SwitchGrid$InfoUnderneathSettings;
/* 181 */       //   0	8	1	$$0	Ljava/lang/Object; } public int maxInfoRows() { return this.maxInfoRows; } public boolean alwaysMaxHeight() { return this.alwaysMaxHeight; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\SwitchGrid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */