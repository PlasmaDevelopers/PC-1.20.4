/*     */ package net.minecraft.client.gui.screens.telemetry;
/*     */ 
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*     */ import net.minecraft.client.gui.layouts.Layout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.layouts.SpacerElement;
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
/*     */ class ContentBuilder
/*     */ {
/*     */   private final int width;
/*     */   private final LinearLayout layout;
/* 138 */   private final MutableComponent narration = Component.empty();
/*     */   
/*     */   public ContentBuilder(int $$0) {
/* 141 */     this.width = $$0;
/*     */     
/* 143 */     this.layout = LinearLayout.vertical();
/* 144 */     this.layout.defaultCellSetting().alignHorizontallyLeft();
/*     */     
/* 146 */     this.layout.addChild((LayoutElement)SpacerElement.width($$0));
/*     */   }
/*     */   
/*     */   public void addLine(Font $$0, Component $$1) {
/* 150 */     addLine($$0, $$1, 0);
/*     */   }
/*     */   
/*     */   public void addLine(Font $$0, Component $$1, int $$2) {
/* 154 */     this.layout.addChild((LayoutElement)(new MultiLineTextWidget($$1, $$0)).setMaxWidth(this.width), $$1 -> $$1.paddingBottom($$0));
/* 155 */     this.narration.append($$1).append("\n");
/*     */   }
/*     */   
/*     */   public void addHeader(Font $$0, Component $$1) {
/* 159 */     this.layout.addChild((LayoutElement)(new MultiLineTextWidget($$1, $$0)).setMaxWidth(this.width - 64).setCentered(true), $$0 -> $$0.alignHorizontallyCenter().paddingHorizontal(32));
/* 160 */     this.narration.append($$1).append("\n");
/*     */   }
/*     */   
/*     */   public void addSpacer(int $$0) {
/* 164 */     this.layout.addChild((LayoutElement)SpacerElement.height($$0));
/*     */   }
/*     */   
/*     */   public TelemetryEventWidget.Content build() {
/* 168 */     this.layout.arrangeElements();
/* 169 */     return new TelemetryEventWidget.Content((Layout)this.layout, (Component)this.narration);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\telemetry\TelemetryEventWidget$ContentBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */