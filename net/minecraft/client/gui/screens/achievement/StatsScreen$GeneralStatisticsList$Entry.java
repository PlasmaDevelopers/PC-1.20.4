/*     */ package net.minecraft.client.gui.screens.achievement;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.stats.Stat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Entry
/*     */   extends ObjectSelectionList.Entry<StatsScreen.GeneralStatisticsList.Entry>
/*     */ {
/*     */   private final Stat<ResourceLocation> stat;
/*     */   private final Component statDisplay;
/*     */   
/*     */   Entry(Stat<ResourceLocation> $$0) {
/* 146 */     this.stat = $$0;
/* 147 */     this.statDisplay = (Component)Component.translatable(StatsScreen.getTranslationKey($$0));
/*     */   }
/*     */   
/*     */   private String getValueText() {
/* 151 */     return this.stat.format(this.this$1.this$0.stats.getValue(this.stat));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 156 */     $$0.drawString(StatsScreen.access$000(this.this$1.this$0), this.statDisplay, $$3 + 2, $$2 + 1, ($$1 % 2 == 0) ? 16777215 : 9474192);
/* 157 */     String $$10 = getValueText();
/* 158 */     $$0.drawString(StatsScreen.access$100(this.this$1.this$0), $$10, $$3 + 2 + 213 - StatsScreen.access$200(this.this$1.this$0).width($$10), $$2 + 1, ($$1 % 2 == 0) ? 16777215 : 9474192);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 163 */     return (Component)Component.translatable("narrator.select", new Object[] { Component.empty().append(this.statDisplay).append(CommonComponents.SPACE).append(getValueText()) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\achievement\StatsScreen$GeneralStatisticsList$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */