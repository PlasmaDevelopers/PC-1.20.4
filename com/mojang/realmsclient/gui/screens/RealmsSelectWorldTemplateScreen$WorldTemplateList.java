/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.dto.WorldTemplate;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.realms.RealmsObjectSelectionList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WorldTemplateList
/*     */   extends RealmsObjectSelectionList<RealmsSelectWorldTemplateScreen.Entry>
/*     */ {
/*     */   public WorldTemplateList(RealmsSelectWorldTemplateScreen paramRealmsSelectWorldTemplateScreen) {
/* 253 */     this(Collections.emptyList());
/*     */   }
/*     */   
/*     */   public WorldTemplateList(Iterable<WorldTemplate> $$0) {
/* 257 */     super(paramRealmsSelectWorldTemplateScreen.width, paramRealmsSelectWorldTemplateScreen.height - 36 - paramRealmsSelectWorldTemplateScreen.getHeaderHeight(), paramRealmsSelectWorldTemplateScreen.getHeaderHeight(), 46);
/* 258 */     $$0.forEach(this::addEntry);
/*     */   }
/*     */   
/*     */   public void addEntry(WorldTemplate $$0) {
/* 262 */     addEntry(new RealmsSelectWorldTemplateScreen.Entry(RealmsSelectWorldTemplateScreen.this, $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 267 */     if (RealmsSelectWorldTemplateScreen.this.currentLink != null) {
/* 268 */       ConfirmLinkScreen.confirmLinkNow((Screen)RealmsSelectWorldTemplateScreen.this, RealmsSelectWorldTemplateScreen.this.currentLink);
/* 269 */       return true;
/*     */     } 
/* 271 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable RealmsSelectWorldTemplateScreen.Entry $$0) {
/* 276 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/* 277 */     RealmsSelectWorldTemplateScreen.this.selectedTemplate = ($$0 == null) ? null : $$0.template;
/* 278 */     RealmsSelectWorldTemplateScreen.this.updateButtonStates();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPosition() {
/* 283 */     return getItemCount() * 46;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 288 */     return 300;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 292 */     return (getItemCount() == 0);
/*     */   }
/*     */   
/*     */   public List<WorldTemplate> getTemplates() {
/* 296 */     return (List<WorldTemplate>)children().stream().map($$0 -> $$0.template).collect(Collectors.toList());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSelectWorldTemplateScreen$WorldTemplateList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */