/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
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
/*     */ class BackupInfoListEntry
/*     */   extends ObjectSelectionList.Entry<RealmsBackupInfoScreen.BackupInfoListEntry>
/*     */ {
/*  83 */   private static final Component TEMPLATE_NAME = (Component)Component.translatable("mco.backup.entry.templateName");
/*  84 */   private static final Component GAME_DIFFICULTY = (Component)Component.translatable("mco.backup.entry.gameDifficulty");
/*  85 */   private static final Component NAME = (Component)Component.translatable("mco.backup.entry.name");
/*  86 */   private static final Component GAME_SERVER_VERSION = (Component)Component.translatable("mco.backup.entry.gameServerVersion");
/*  87 */   private static final Component UPLOADED = (Component)Component.translatable("mco.backup.entry.uploaded");
/*  88 */   private static final Component ENABLED_PACK = (Component)Component.translatable("mco.backup.entry.enabledPack");
/*  89 */   private static final Component DESCRIPTION = (Component)Component.translatable("mco.backup.entry.description");
/*  90 */   private static final Component GAME_MODE = (Component)Component.translatable("mco.backup.entry.gameMode");
/*  91 */   private static final Component SEED = (Component)Component.translatable("mco.backup.entry.seed");
/*  92 */   private static final Component WORLD_TYPE = (Component)Component.translatable("mco.backup.entry.worldType");
/*  93 */   private static final Component UNDEFINED = (Component)Component.translatable("mco.backup.entry.undefined");
/*     */   
/*     */   private final String key;
/*     */   private final String value;
/*     */   
/*     */   public BackupInfoListEntry(String $$0, String $$1) {
/*  99 */     this.key = $$0;
/* 100 */     this.value = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 105 */     $$0.drawString(RealmsBackupInfoScreen.access$000(RealmsBackupInfoScreen.this), translateKey(this.key), $$3, $$2, -6250336);
/* 106 */     $$0.drawString(RealmsBackupInfoScreen.access$100(RealmsBackupInfoScreen.this), RealmsBackupInfoScreen.this.checkForSpecificMetadata(this.key, this.value), $$3, $$2 + 12, -1);
/*     */   }
/*     */   
/*     */   private Component translateKey(String $$0) {
/* 110 */     switch ($$0) { case "template_name": case "game_difficulty": case "name": case "game_server_version": case "uploaded": case "enabled_packs": case "description": case "game_mode": case "seed": case "world_type":  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       UNDEFINED;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 132 */     return (Component)Component.translatable("narrator.select", new Object[] { this.key + " " + this.key });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsBackupInfoScreen$BackupInfoListEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */