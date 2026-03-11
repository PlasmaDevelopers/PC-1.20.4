/*     */ package net.minecraft.client.gui.screens.reporting;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportReason;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ 
/*     */ public class ReportReasonSelectionScreen extends Screen {
/*  19 */   private static final Component REASON_TITLE = (Component)Component.translatable("gui.abuseReport.reason.title");
/*  20 */   private static final Component REASON_DESCRIPTION = (Component)Component.translatable("gui.abuseReport.reason.description");
/*  21 */   private static final Component READ_INFO_LABEL = (Component)Component.translatable("gui.abuseReport.read_info");
/*     */   
/*     */   private static final int FOOTER_HEIGHT = 95;
/*     */   
/*     */   private static final int BUTTON_WIDTH = 150;
/*     */   
/*     */   private static final int BUTTON_HEIGHT = 20;
/*     */   
/*     */   private static final int CONTENT_WIDTH = 320;
/*     */   private static final int PADDING = 4;
/*     */   @Nullable
/*     */   private final Screen lastScreen;
/*     */   @Nullable
/*     */   private ReasonSelectionList reasonSelectionList;
/*     */   @Nullable
/*     */   ReportReason currentlySelectedReason;
/*     */   private final Consumer<ReportReason> onSelectedReason;
/*     */   
/*     */   public ReportReasonSelectionScreen(@Nullable Screen $$0, @Nullable ReportReason $$1, Consumer<ReportReason> $$2) {
/*  40 */     super(REASON_TITLE);
/*  41 */     this.lastScreen = $$0;
/*  42 */     this.currentlySelectedReason = $$1;
/*  43 */     this.onSelectedReason = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  48 */     this.reasonSelectionList = (ReasonSelectionList)addRenderableWidget((GuiEventListener)new ReasonSelectionList(this.minecraft));
/*     */     
/*  50 */     Objects.requireNonNull(this.reasonSelectionList); ReasonSelectionList.Entry $$0 = (ReasonSelectionList.Entry)Optionull.map(this.currentlySelectedReason, this.reasonSelectionList::findEntry);
/*  51 */     this.reasonSelectionList.setSelected($$0);
/*     */     
/*  53 */     int $$1 = this.width / 2 - 150 - 5;
/*  54 */     addRenderableWidget((GuiEventListener)Button.builder(READ_INFO_LABEL, ConfirmLinkScreen.confirmLink(this, "https://aka.ms/aboutjavareporting"))
/*  55 */         .bounds($$1, buttonTop(), 150, 20)
/*  56 */         .build());
/*     */     
/*  58 */     int $$2 = this.width / 2 + 5;
/*  59 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> {
/*     */             ReasonSelectionList.Entry $$1 = (ReasonSelectionList.Entry)this.reasonSelectionList.getSelected();
/*     */             if ($$1 != null) {
/*     */               this.onSelectedReason.accept($$1.getReason());
/*     */             }
/*     */             this.minecraft.setScreen(this.lastScreen);
/*  65 */           }).bounds($$2, buttonTop(), 150, 20).build());
/*     */     
/*  67 */     super.init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  72 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/*  74 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 16, 16777215);
/*     */     
/*  76 */     $$0.fill(contentLeft(), descriptionTop(), contentRight(), descriptionBottom(), 2130706432);
/*     */     
/*  78 */     $$0.drawString(this.font, REASON_DESCRIPTION, contentLeft() + 4, descriptionTop() + 4, -8421505);
/*     */     
/*  80 */     ReasonSelectionList.Entry $$4 = (ReasonSelectionList.Entry)this.reasonSelectionList.getSelected();
/*  81 */     if ($$4 != null) {
/*  82 */       int $$5 = contentLeft() + 4 + 16;
/*  83 */       int $$6 = contentRight() - 4;
/*  84 */       Objects.requireNonNull(this.font); int $$7 = descriptionTop() + 4 + 9 + 2;
/*  85 */       int $$8 = descriptionBottom() - 4;
/*     */       
/*  87 */       int $$9 = $$6 - $$5;
/*  88 */       int $$10 = $$8 - $$7;
/*     */       
/*  90 */       int $$11 = this.font.wordWrapHeight((FormattedText)$$4.reason.description(), $$9);
/*  91 */       $$0.drawWordWrap(this.font, (FormattedText)$$4.reason.description(), $$5, $$7 + ($$10 - $$11) / 2, $$9, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  97 */     renderDirtBackground($$0);
/*     */   }
/*     */   
/*     */   private int buttonTop() {
/* 101 */     return this.height - 20 - 4;
/*     */   }
/*     */   
/*     */   private int contentLeft() {
/* 105 */     return (this.width - 320) / 2;
/*     */   }
/*     */   
/*     */   private int contentRight() {
/* 109 */     return (this.width + 320) / 2;
/*     */   }
/*     */   
/*     */   private int descriptionTop() {
/* 113 */     return this.height - 95 + 4;
/*     */   }
/*     */   
/*     */   private int descriptionBottom() {
/* 117 */     return buttonTop() - 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 122 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */   
/*     */   public class ReasonSelectionList extends ObjectSelectionList<ReasonSelectionList.Entry> {
/*     */     public ReasonSelectionList(Minecraft $$1) {
/* 127 */       super($$1, $$0.width, $$0.height - 95 - 40, 40, 18);
/* 128 */       for (ReportReason $$2 : ReportReason.values()) {
/* 129 */         addEntry((AbstractSelectionList.Entry)new Entry($$2));
/*     */       }
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Entry findEntry(ReportReason $$0) {
/* 135 */       return children().stream()
/* 136 */         .filter($$1 -> ($$1.reason == $$0))
/* 137 */         .findFirst()
/* 138 */         .orElse(null);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/* 143 */       return 320;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollbarPosition() {
/* 148 */       return getRowRight() - 2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable Entry $$0) {
/* 153 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/* 154 */       ReportReasonSelectionScreen.this.currentlySelectedReason = ($$0 != null) ? $$0.getReason() : null;
/*     */     }
/*     */     
/*     */     public class Entry extends ObjectSelectionList.Entry<Entry> {
/*     */       final ReportReason reason;
/*     */       
/*     */       public Entry(ReportReason $$1) {
/* 161 */         this.reason = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 166 */         int $$10 = $$3 + 1;
/* 167 */         Objects.requireNonNull(ReportReasonSelectionScreen.this.font); int $$11 = $$2 + ($$5 - 9) / 2 + 1;
/* 168 */         $$0.drawString(ReportReasonSelectionScreen.this.font, this.reason.title(), $$10, $$11, -1);
/*     */       }
/*     */ 
/*     */       
/*     */       public Component getNarration() {
/* 173 */         return (Component)Component.translatable("gui.abuseReport.reason.narration", new Object[] { this.reason.title(), this.reason.description() });
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 178 */         ReportReasonSelectionScreen.ReasonSelectionList.this.setSelected(this);
/* 179 */         return true;
/*     */       }
/*     */       
/*     */       public ReportReason getReason() {
/* 183 */         return this.reason; } } } public class Entry extends ObjectSelectionList.Entry<ReasonSelectionList.Entry> { public ReportReason getReason() { return this.reason; }
/*     */ 
/*     */     
/*     */     final ReportReason reason;
/*     */     
/*     */     public Entry(ReportReason $$1) {
/*     */       this.reason = $$1;
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       int $$10 = $$3 + 1;
/*     */       Objects.requireNonNull(ReportReasonSelectionScreen.this.font);
/*     */       int $$11 = $$2 + ($$5 - 9) / 2 + 1;
/*     */       $$0.drawString(ReportReasonSelectionScreen.this.font, this.reason.title(), $$10, $$11, -1);
/*     */     }
/*     */     
/*     */     public Component getNarration() {
/*     */       return (Component)Component.translatable("gui.abuseReport.reason.narration", new Object[] { this.reason.title(), this.reason.description() });
/*     */     }
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*     */       ReportReasonSelectionScreen.ReasonSelectionList.this.setSelected(this);
/*     */       return true;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\ReportReasonSelectionScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */