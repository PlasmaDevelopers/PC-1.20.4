/*     */ package net.minecraft.client.gui.screens.telemetry;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractScrollWidget;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*     */ import net.minecraft.client.gui.layouts.Layout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.layouts.SpacerElement;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.telemetry.TelemetryEventType;
/*     */ import net.minecraft.client.telemetry.TelemetryProperty;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ 
/*     */ public class TelemetryEventWidget extends AbstractScrollWidget {
/*     */   private static final int HEADER_HORIZONTAL_PADDING = 32;
/*     */   private static final String TELEMETRY_REQUIRED_TRANSLATION_KEY = "telemetry.event.required";
/*     */   private static final String TELEMETRY_OPTIONAL_TRANSLATION_KEY = "telemetry.event.optional";
/*     */   private static final String TELEMETRY_OPTIONAL_DISABLED_TRANSLATION_KEY = "telemetry.event.optional.disabled";
/*  31 */   private static final Component PROPERTY_TITLE = (Component)Component.translatable("telemetry_info.property_title").withStyle(ChatFormatting.UNDERLINE);
/*     */   
/*     */   private final Font font;
/*     */   private Content content;
/*     */   @Nullable
/*     */   private DoubleConsumer onScrolledListener;
/*     */   
/*     */   public TelemetryEventWidget(int $$0, int $$1, int $$2, int $$3, Font $$4) {
/*  39 */     super($$0, $$1, $$2, $$3, (Component)Component.empty());
/*     */     
/*  41 */     this.font = $$4;
/*  42 */     this.content = buildContent(Minecraft.getInstance().telemetryOptInExtra());
/*     */   }
/*     */   
/*     */   public void onOptInChanged(boolean $$0) {
/*  46 */     this.content = buildContent($$0);
/*  47 */     setScrollAmount(scrollAmount());
/*     */   }
/*     */   
/*     */   private Content buildContent(boolean $$0) {
/*  51 */     ContentBuilder $$1 = new ContentBuilder(containerWidth());
/*  52 */     List<TelemetryEventType> $$2 = new ArrayList<>(TelemetryEventType.values());
/*  53 */     $$2.sort(Comparator.comparing(TelemetryEventType::isOptIn));
/*  54 */     for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
/*  55 */       TelemetryEventType $$4 = $$2.get($$3);
/*  56 */       boolean $$5 = ($$4.isOptIn() && !$$0);
/*     */       
/*  58 */       addEventType($$1, $$4, $$5);
/*     */       
/*  60 */       if ($$3 < $$2.size() - 1) {
/*  61 */         Objects.requireNonNull(this.font); $$1.addSpacer(9);
/*     */       } 
/*     */     } 
/*  64 */     return $$1.build();
/*     */   }
/*     */   
/*     */   public void setOnScrolledListener(@Nullable DoubleConsumer $$0) {
/*  68 */     this.onScrolledListener = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setScrollAmount(double $$0) {
/*  73 */     super.setScrollAmount($$0);
/*  74 */     if (this.onScrolledListener != null) {
/*  75 */       this.onScrolledListener.accept(scrollAmount());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getInnerHeight() {
/*  81 */     return this.content.container().getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   protected double scrollRate() {
/*  86 */     Objects.requireNonNull(this.font); return 9.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderContents(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  91 */     int $$4 = getY() + innerPadding();
/*  92 */     int $$5 = getX() + innerPadding();
/*     */     
/*  94 */     $$0.pose().pushPose();
/*  95 */     $$0.pose().translate($$5, $$4, 0.0D);
/*  96 */     this.content.container().visitWidgets($$4 -> $$4.render($$0, $$1, $$2, $$3));
/*  97 */     $$0.pose().popPose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateWidgetNarration(NarrationElementOutput $$0) {
/* 102 */     $$0.add(NarratedElementType.TITLE, this.content.narration());
/*     */   }
/*     */   
/*     */   private Component grayOutIfDisabled(Component $$0, boolean $$1) {
/* 106 */     if ($$1) {
/* 107 */       return (Component)$$0.copy().withStyle(ChatFormatting.GRAY);
/*     */     }
/* 109 */     return $$0;
/*     */   }
/*     */   
/*     */   private void addEventType(ContentBuilder $$0, TelemetryEventType $$1, boolean $$2) {
/* 113 */     String $$3 = $$1.isOptIn() ? ($$2 ? "telemetry.event.optional.disabled" : "telemetry.event.optional") : "telemetry.event.required";
/* 114 */     $$0.addHeader(this.font, grayOutIfDisabled((Component)Component.translatable($$3, new Object[] { $$1.title() }), $$2));
/* 115 */     $$0.addHeader(this.font, (Component)$$1.description().withStyle(ChatFormatting.GRAY));
/* 116 */     Objects.requireNonNull(this.font); $$0.addSpacer(9 / 2);
/*     */     
/* 118 */     $$0.addLine(this.font, grayOutIfDisabled(PROPERTY_TITLE, $$2), 2);
/*     */     
/* 120 */     addEventTypeProperties($$1, $$0, $$2);
/*     */   }
/*     */   
/*     */   private void addEventTypeProperties(TelemetryEventType $$0, ContentBuilder $$1, boolean $$2) {
/* 124 */     for (TelemetryProperty<?> $$3 : (Iterable<TelemetryProperty<?>>)$$0.properties()) {
/* 125 */       $$1.addLine(this.font, grayOutIfDisabled((Component)$$3.title(), $$2));
/*     */     }
/*     */   }
/*     */   
/*     */   private int containerWidth() {
/* 130 */     return this.width - totalInnerPadding();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ContentBuilder
/*     */   {
/*     */     private final int width;
/*     */     private final LinearLayout layout;
/* 138 */     private final MutableComponent narration = Component.empty();
/*     */     
/*     */     public ContentBuilder(int $$0) {
/* 141 */       this.width = $$0;
/*     */       
/* 143 */       this.layout = LinearLayout.vertical();
/* 144 */       this.layout.defaultCellSetting().alignHorizontallyLeft();
/*     */       
/* 146 */       this.layout.addChild((LayoutElement)SpacerElement.width($$0));
/*     */     }
/*     */     
/*     */     public void addLine(Font $$0, Component $$1) {
/* 150 */       addLine($$0, $$1, 0);
/*     */     }
/*     */     
/*     */     public void addLine(Font $$0, Component $$1, int $$2) {
/* 154 */       this.layout.addChild((LayoutElement)(new MultiLineTextWidget($$1, $$0)).setMaxWidth(this.width), $$1 -> $$1.paddingBottom($$0));
/* 155 */       this.narration.append($$1).append("\n");
/*     */     }
/*     */     
/*     */     public void addHeader(Font $$0, Component $$1) {
/* 159 */       this.layout.addChild((LayoutElement)(new MultiLineTextWidget($$1, $$0)).setMaxWidth(this.width - 64).setCentered(true), $$0 -> $$0.alignHorizontallyCenter().paddingHorizontal(32));
/* 160 */       this.narration.append($$1).append("\n");
/*     */     }
/*     */     
/*     */     public void addSpacer(int $$0) {
/* 164 */       this.layout.addChild((LayoutElement)SpacerElement.height($$0));
/*     */     }
/*     */     
/*     */     public TelemetryEventWidget.Content build() {
/* 168 */       this.layout.arrangeElements();
/* 169 */       return new TelemetryEventWidget.Content((Layout)this.layout, (Component)this.narration);
/*     */     } }
/*     */   private static final class Content extends Record { private final Layout container; private final Component narration;
/*     */     
/* 173 */     Content(Layout $$0, Component $$1) { this.container = $$0; this.narration = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/telemetry/TelemetryEventWidget$Content;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #173	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 173 */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/telemetry/TelemetryEventWidget$Content; } public Layout container() { return this.container; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/telemetry/TelemetryEventWidget$Content;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #173	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/telemetry/TelemetryEventWidget$Content; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/telemetry/TelemetryEventWidget$Content;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #173	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/telemetry/TelemetryEventWidget$Content;
/* 173 */       //   0	8	1	$$0	Ljava/lang/Object; } public Component narration() { return this.narration; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\telemetry\TelemetryEventWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */