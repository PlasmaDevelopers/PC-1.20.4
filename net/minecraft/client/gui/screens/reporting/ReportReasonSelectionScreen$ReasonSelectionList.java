/*     */ package net.minecraft.client.gui.screens.reporting;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportReason;
/*     */ import net.minecraft.network.chat.Component;
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
/*     */ public class ReasonSelectionList
/*     */   extends ObjectSelectionList<ReportReasonSelectionScreen.ReasonSelectionList.Entry>
/*     */ {
/*     */   public ReasonSelectionList(Minecraft $$1) {
/* 127 */     super($$1, $$0.width, $$0.height - 95 - 40, 40, 18);
/* 128 */     for (ReportReason $$2 : ReportReason.values()) {
/* 129 */       addEntry((AbstractSelectionList.Entry)new Entry($$2));
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entry findEntry(ReportReason $$0) {
/* 135 */     return children().stream()
/* 136 */       .filter($$1 -> ($$1.reason == $$0))
/* 137 */       .findFirst()
/* 138 */       .orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 143 */     return 320;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollbarPosition() {
/* 148 */     return getRowRight() - 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable Entry $$0) {
/* 153 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/* 154 */     ReportReasonSelectionScreen.this.currentlySelectedReason = ($$0 != null) ? $$0.getReason() : null;
/*     */   }
/*     */   
/*     */   public class Entry extends ObjectSelectionList.Entry<Entry> {
/*     */     final ReportReason reason;
/*     */     
/*     */     public Entry(ReportReason $$1) {
/* 161 */       this.reason = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 166 */       int $$10 = $$3 + 1;
/* 167 */       Objects.requireNonNull(ReportReasonSelectionScreen.access$000(this.this$1.this$0)); int $$11 = $$2 + ($$5 - 9) / 2 + 1;
/* 168 */       $$0.drawString(ReportReasonSelectionScreen.access$100(this.this$1.this$0), this.reason.title(), $$10, $$11, -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 173 */       return (Component)Component.translatable("gui.abuseReport.reason.narration", new Object[] { this.reason.title(), this.reason.description() });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 178 */       ReportReasonSelectionScreen.ReasonSelectionList.this.setSelected(this);
/* 179 */       return true;
/*     */     }
/*     */     
/*     */     public ReportReason getReason() {
/* 183 */       return this.reason;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\ReportReasonSelectionScreen$ReasonSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */