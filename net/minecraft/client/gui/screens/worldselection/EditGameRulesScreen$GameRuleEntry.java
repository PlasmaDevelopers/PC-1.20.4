/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GameRuleEntry
/*     */   extends EditGameRulesScreen.RuleEntry
/*     */ {
/*     */   private final List<FormattedCharSequence> label;
/* 147 */   protected final List<AbstractWidget> children = Lists.newArrayList();
/*     */   
/*     */   public GameRuleEntry(List<FormattedCharSequence> $$1, Component $$2) {
/* 150 */     super($$1);
/* 151 */     this.label = (EditGameRulesScreen.access$100($$0)).font.split((FormattedText)$$2, 175);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends GuiEventListener> children() {
/* 156 */     return (List)this.children;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends NarratableEntry> narratables() {
/* 161 */     return (List)this.children;
/*     */   }
/*     */   
/*     */   protected void renderLabel(GuiGraphics $$0, int $$1, int $$2) {
/* 165 */     if (this.label.size() == 1) {
/* 166 */       $$0.drawString((EditGameRulesScreen.access$200(EditGameRulesScreen.this)).font, this.label.get(0), $$2, $$1 + 5, 16777215, false);
/* 167 */     } else if (this.label.size() >= 2) {
/* 168 */       $$0.drawString((EditGameRulesScreen.access$300(EditGameRulesScreen.this)).font, this.label.get(0), $$2, $$1, 16777215, false);
/* 169 */       $$0.drawString((EditGameRulesScreen.access$400(EditGameRulesScreen.this)).font, this.label.get(1), $$2, $$1 + 10, 16777215, false);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\EditGameRulesScreen$GameRuleEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */