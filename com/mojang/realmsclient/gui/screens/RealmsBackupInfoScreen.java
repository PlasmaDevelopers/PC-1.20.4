/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.realmsclient.dto.Backup;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.level.GameType;
/*     */ 
/*     */ public class RealmsBackupInfoScreen extends RealmsScreen {
/*  19 */   private static final Component TITLE = (Component)Component.translatable("mco.backup.info.title");
/*  20 */   private static final Component UNKNOWN = (Component)Component.translatable("mco.backup.unknown");
/*     */   
/*     */   private final Screen lastScreen;
/*     */   
/*     */   final Backup backup;
/*  25 */   final HeaderAndFooterLayout layout = new HeaderAndFooterLayout((Screen)this);
/*     */   
/*     */   private BackupInfoList backupInfoList;
/*     */   
/*     */   public RealmsBackupInfoScreen(Screen $$0, Backup $$1) {
/*  30 */     super(TITLE);
/*  31 */     this.lastScreen = $$0;
/*  32 */     this.backup = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  37 */     this.layout.addToHeader((LayoutElement)new StringWidget(TITLE, this.font));
/*  38 */     this.backupInfoList = (BackupInfoList)this.layout.addToContents((LayoutElement)new BackupInfoList(this.minecraft));
/*  39 */     this.layout.addToFooter((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).build());
/*     */     
/*  41 */     repositionElements();
/*  42 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  47 */     this.backupInfoList.setSize(this.width, this.height - this.layout.getFooterHeight() - this.layout.getHeaderHeight());
/*  48 */     this.layout.arrangeElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  53 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */   
/*     */   Component checkForSpecificMetadata(String $$0, String $$1) {
/*  57 */     String $$2 = $$0.toLowerCase(Locale.ROOT);
/*  58 */     if ($$2.contains("game") && $$2.contains("mode"))
/*  59 */       return gameModeMetadata($$1); 
/*  60 */     if ($$2.contains("game") && $$2.contains("difficulty")) {
/*  61 */       return gameDifficultyMetadata($$1);
/*     */     }
/*  63 */     return (Component)Component.literal($$1);
/*     */   }
/*     */   
/*     */   private Component gameDifficultyMetadata(String $$0) {
/*     */     try {
/*  68 */       return ((Difficulty)RealmsSlotOptionsScreen.DIFFICULTIES.get(Integer.parseInt($$0))).getDisplayName();
/*  69 */     } catch (Exception $$1) {
/*  70 */       return UNKNOWN;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Component gameModeMetadata(String $$0) {
/*     */     try {
/*  76 */       return ((GameType)RealmsSlotOptionsScreen.GAME_MODES.get(Integer.parseInt($$0))).getShortDisplayName();
/*  77 */     } catch (Exception $$1) {
/*  78 */       return UNKNOWN;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class BackupInfoListEntry extends ObjectSelectionList.Entry<BackupInfoListEntry> {
/*  83 */     private static final Component TEMPLATE_NAME = (Component)Component.translatable("mco.backup.entry.templateName");
/*  84 */     private static final Component GAME_DIFFICULTY = (Component)Component.translatable("mco.backup.entry.gameDifficulty");
/*  85 */     private static final Component NAME = (Component)Component.translatable("mco.backup.entry.name");
/*  86 */     private static final Component GAME_SERVER_VERSION = (Component)Component.translatable("mco.backup.entry.gameServerVersion");
/*  87 */     private static final Component UPLOADED = (Component)Component.translatable("mco.backup.entry.uploaded");
/*  88 */     private static final Component ENABLED_PACK = (Component)Component.translatable("mco.backup.entry.enabledPack");
/*  89 */     private static final Component DESCRIPTION = (Component)Component.translatable("mco.backup.entry.description");
/*  90 */     private static final Component GAME_MODE = (Component)Component.translatable("mco.backup.entry.gameMode");
/*  91 */     private static final Component SEED = (Component)Component.translatable("mco.backup.entry.seed");
/*  92 */     private static final Component WORLD_TYPE = (Component)Component.translatable("mco.backup.entry.worldType");
/*  93 */     private static final Component UNDEFINED = (Component)Component.translatable("mco.backup.entry.undefined");
/*     */     
/*     */     private final String key;
/*     */     private final String value;
/*     */     
/*     */     public BackupInfoListEntry(String $$0, String $$1) {
/*  99 */       this.key = $$0;
/* 100 */       this.value = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 105 */       $$0.drawString(RealmsBackupInfoScreen.this.font, translateKey(this.key), $$3, $$2, -6250336);
/* 106 */       $$0.drawString(RealmsBackupInfoScreen.this.font, RealmsBackupInfoScreen.this.checkForSpecificMetadata(this.key, this.value), $$3, $$2 + 12, -1);
/*     */     }
/*     */     
/*     */     private Component translateKey(String $$0) {
/* 110 */       switch ($$0) { case "template_name": case "game_difficulty": case "name": case "game_server_version": case "uploaded": case "enabled_packs": case "description": case "game_mode": case "seed": case "world_type":  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 121 */         UNDEFINED;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 127 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 132 */       return (Component)Component.translatable("narrator.select", new Object[] { this.key + " " + this.key });
/*     */     }
/*     */   }
/*     */   
/*     */   private class BackupInfoList extends ObjectSelectionList<BackupInfoListEntry> {
/*     */     public BackupInfoList(Minecraft $$0) {
/* 138 */       super($$0, RealmsBackupInfoScreen.this.width, RealmsBackupInfoScreen.this.height - RealmsBackupInfoScreen.this.layout.getFooterHeight() - RealmsBackupInfoScreen.this.layout.getHeaderHeight(), RealmsBackupInfoScreen.this.layout.getHeaderHeight(), 36);
/* 139 */       if (RealmsBackupInfoScreen.this.backup.changeList != null)
/* 140 */         RealmsBackupInfoScreen.this.backup.changeList.forEach(($$0, $$1) -> addEntry((AbstractSelectionList.Entry)new RealmsBackupInfoScreen.BackupInfoListEntry($$0, $$1))); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsBackupInfoScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */