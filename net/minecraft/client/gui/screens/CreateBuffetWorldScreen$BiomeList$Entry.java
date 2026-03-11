/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends ObjectSelectionList.Entry<CreateBuffetWorldScreen.BiomeList.Entry>
/*     */ {
/*     */   final Holder.Reference<Biome> biome;
/*     */   final Component name;
/*     */   
/*     */   public Entry(Holder.Reference<Biome> $$0) {
/* 109 */     this.biome = $$0;
/* 110 */     ResourceLocation $$1 = $$0.key().location();
/*     */     
/* 112 */     String $$2 = $$1.toLanguageKey("biome");
/* 113 */     if (Language.getInstance().has($$2)) {
/* 114 */       this.name = (Component)Component.translatable($$2);
/*     */     } else {
/* 116 */       this.name = (Component)Component.literal($$1.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 122 */     return (Component)Component.translatable("narrator.select", new Object[] { this.name });
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 127 */     $$0.drawString(this.this$1.this$0.font, this.name, $$3 + 5, $$2 + 2, 16777215);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 134 */     CreateBuffetWorldScreen.BiomeList.this.setSelected(this);
/* 135 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\CreateBuffetWorldScreen$BiomeList$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */