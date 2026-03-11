/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
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
/*     */ public class CategoryRuleEntry
/*     */   extends EditGameRulesScreen.RuleEntry
/*     */ {
/*     */   final Component label;
/*     */   
/*     */   public CategoryRuleEntry(Component $$1) {
/* 110 */     super(null);
/* 111 */     this.label = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 116 */     $$0.drawCenteredString((EditGameRulesScreen.access$000(EditGameRulesScreen.this)).font, this.label, $$3 + $$4 / 2, $$2 + 5, 16777215);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends GuiEventListener> children() {
/* 121 */     return (List<? extends GuiEventListener>)ImmutableList.of();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends NarratableEntry> narratables() {
/* 126 */     return (List<? extends NarratableEntry>)ImmutableList.of(new NarratableEntry()
/*     */         {
/*     */           public NarratableEntry.NarrationPriority narrationPriority() {
/* 129 */             return NarratableEntry.NarrationPriority.HOVERED;
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateNarration(NarrationElementOutput $$0) {
/* 134 */             $$0.add(NarratedElementType.TITLE, EditGameRulesScreen.CategoryRuleEntry.this.label);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\EditGameRulesScreen$CategoryRuleEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */