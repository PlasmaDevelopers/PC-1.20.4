/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.WorldTemplate;
/*     */ import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
/*     */ import com.mojang.realmsclient.util.TextRenderingUtils;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends Thread
/*     */ {
/*     */   null(String $$1) {
/* 165 */     super($$1);
/*     */   }
/*     */   public void run() {
/* 168 */     WorldTemplatePaginatedList $$0 = startPage;
/* 169 */     RealmsClient $$1 = RealmsClient.create();
/* 170 */     while ($$0 != null) {
/*     */       
/* 172 */       Either<WorldTemplatePaginatedList, Exception> $$2 = RealmsSelectWorldTemplateScreen.this.fetchTemplates($$0, $$1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       $$0 = RealmsSelectWorldTemplateScreen.access$000(RealmsSelectWorldTemplateScreen.this).submit(() -> { if ($$0.right().isPresent()) { RealmsSelectWorldTemplateScreen.LOGGER.error("Couldn't fetch templates", $$0.right().get()); if (RealmsSelectWorldTemplateScreen.this.worldTemplateList.isEmpty()) RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose(I18n.get("mco.template.select.failure", new Object[0]), new TextRenderingUtils.LineSegment[0]);  return null; }  WorldTemplatePaginatedList $$1 = $$0.left().get(); for (WorldTemplate $$2 : $$1.templates) RealmsSelectWorldTemplateScreen.this.worldTemplateList.addEntry($$2);  if ($$1.templates.isEmpty()) { if (RealmsSelectWorldTemplateScreen.this.worldTemplateList.isEmpty()) { String $$3 = I18n.get("mco.template.select.none", new Object[] { "%link" }); TextRenderingUtils.LineSegment $$4 = TextRenderingUtils.LineSegment.link(I18n.get("mco.template.select.none.linkTitle", new Object[0]), "https://aka.ms/MinecraftRealmsContentCreator"); RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose($$3, new TextRenderingUtils.LineSegment[] { $$4 }); }  return null; }  return $$1; }).join();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSelectWorldTemplateScreen$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */