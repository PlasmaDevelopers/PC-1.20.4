/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends ObjectSelectionList.Entry<RealmsSelectFileToUploadScreen.Entry>
/*     */ {
/*     */   private final LevelSummary levelSummary;
/*     */   private final String name;
/*     */   private final Component id;
/*     */   private final Component info;
/*     */   
/*     */   public Entry(LevelSummary $$0) {
/*     */     Component $$2;
/*     */     MutableComponent mutableComponent;
/* 147 */     this.levelSummary = $$0;
/* 148 */     this.name = $$0.getLevelName();
/* 149 */     this.id = (Component)Component.translatable("mco.upload.entry.id", new Object[] { $$0.getLevelId(), RealmsSelectFileToUploadScreen.formatLastPlayed($$0) });
/*     */ 
/*     */     
/* 152 */     if ($$0.isHardcore()) {
/* 153 */       Component $$1 = RealmsSelectFileToUploadScreen.HARDCORE_TEXT;
/*     */     } else {
/* 155 */       $$2 = RealmsSelectFileToUploadScreen.gameModeName($$0);
/*     */     } 
/*     */     
/* 158 */     if ($$0.hasCheats()) {
/* 159 */       mutableComponent = Component.translatable("mco.upload.entry.cheats", new Object[] { $$2.getString(), RealmsSelectFileToUploadScreen.CHEATS_TEXT });
/*     */     }
/*     */     
/* 162 */     this.info = (Component)mutableComponent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 167 */     renderItem($$0, $$1, $$3, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 172 */     RealmsSelectFileToUploadScreen.this.worldSelectionList.selectItem(RealmsSelectFileToUploadScreen.this.levelList.indexOf(this.levelSummary));
/* 173 */     return true;
/*     */   }
/*     */   
/*     */   protected void renderItem(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/*     */     String $$5;
/* 178 */     if (this.name.isEmpty()) {
/* 179 */       String $$4 = "" + RealmsSelectFileToUploadScreen.WORLD_TEXT + " " + RealmsSelectFileToUploadScreen.WORLD_TEXT;
/*     */     } else {
/* 181 */       $$5 = this.name;
/*     */     } 
/*     */     
/* 184 */     $$0.drawString(RealmsSelectFileToUploadScreen.access$200(RealmsSelectFileToUploadScreen.this), $$5, $$2 + 2, $$3 + 1, 16777215, false);
/* 185 */     $$0.drawString(RealmsSelectFileToUploadScreen.access$300(RealmsSelectFileToUploadScreen.this), this.id, $$2 + 2, $$3 + 12, -8355712, false);
/* 186 */     $$0.drawString(RealmsSelectFileToUploadScreen.access$400(RealmsSelectFileToUploadScreen.this), this.info, $$2 + 2, $$3 + 12 + 10, -8355712, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 191 */     Component $$0 = CommonComponents.joinLines(new Component[] {
/* 192 */           (Component)Component.literal(this.levelSummary.getLevelName()), 
/* 193 */           (Component)Component.literal(RealmsSelectFileToUploadScreen.formatLastPlayed(this.levelSummary)), 
/* 194 */           RealmsSelectFileToUploadScreen.gameModeName(this.levelSummary)
/*     */         });
/* 196 */     return (Component)Component.translatable("narrator.select", new Object[] { $$0 });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSelectFileToUploadScreen$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */