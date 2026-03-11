/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.realms.RealmsLabel;
/*     */ import net.minecraft.realms.RealmsObjectSelectionList;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsSelectFileToUploadScreen extends RealmsScreen {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  29 */   public static final Component TITLE = (Component)Component.translatable("mco.upload.select.world.title");
/*  30 */   private static final Component UNABLE_TO_LOAD_WORLD = (Component)Component.translatable("selectWorld.unable_to_load");
/*  31 */   static final Component WORLD_TEXT = (Component)Component.translatable("selectWorld.world");
/*  32 */   static final Component HARDCORE_TEXT = (Component)Component.translatable("mco.upload.hardcore").withColor(-65536);
/*  33 */   static final Component CHEATS_TEXT = (Component)Component.translatable("selectWorld.cheats");
/*     */   
/*  35 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat();
/*     */   
/*     */   private final RealmsResetWorldScreen lastScreen;
/*     */   
/*     */   private final long worldId;
/*     */   
/*     */   private final int slotId;
/*     */   Button uploadButton;
/*  43 */   List<LevelSummary> levelList = Lists.newArrayList();
/*  44 */   int selectedWorld = -1;
/*     */   WorldSelectionList worldSelectionList;
/*     */   
/*     */   public RealmsSelectFileToUploadScreen(long $$0, int $$1, RealmsResetWorldScreen $$2) {
/*  48 */     super(TITLE);
/*  49 */     this.lastScreen = $$2;
/*  50 */     this.worldId = $$0;
/*  51 */     this.slotId = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadLevelList() throws Exception {
/*  56 */     LevelStorageSource.LevelCandidates $$0 = this.minecraft.getLevelSource().findLevelCandidates();
/*  57 */     this
/*     */       
/*  59 */       .levelList = (List<LevelSummary>)((List)this.minecraft.getLevelSource().loadLevelSummaries($$0).join()).stream().filter($$0 -> (!$$0.requiresManualConversion() && !$$0.isLocked())).collect(Collectors.toList());
/*     */     
/*  61 */     for (LevelSummary $$1 : this.levelList) {
/*  62 */       this.worldSelectionList.addEntry($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  68 */     this.worldSelectionList = (WorldSelectionList)addRenderableWidget((GuiEventListener)new WorldSelectionList());
/*     */     try {
/*  70 */       loadLevelList();
/*  71 */     } catch (Exception $$0) {
/*  72 */       LOGGER.error("Couldn't load level list", $$0);
/*  73 */       this.minecraft.setScreen((Screen)new RealmsGenericErrorScreen(UNABLE_TO_LOAD_WORLD, Component.nullToEmpty($$0.getMessage()), (Screen)this.lastScreen));
/*     */       
/*     */       return;
/*     */     } 
/*  77 */     this.uploadButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.upload.button.name"), $$0 -> upload()).bounds(this.width / 2 - 154, this.height - 32, 153, 20).build());
/*  78 */     this.uploadButton.active = (this.selectedWorld >= 0 && this.selectedWorld < this.levelList.size());
/*     */     
/*  80 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> this.minecraft.setScreen((Screen)this.lastScreen))
/*  81 */         .bounds(this.width / 2 + 6, this.height - 32, 153, 20).build());
/*     */     
/*  83 */     addLabel(new RealmsLabel((Component)Component.translatable("mco.upload.select.world.subtitle"), this.width / 2, row(-1), -6250336));
/*  84 */     if (this.levelList.isEmpty()) {
/*  85 */       addLabel(new RealmsLabel((Component)Component.translatable("mco.upload.select.world.none"), this.width / 2, this.height / 2 - 20, -1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/*  91 */     return (Component)CommonComponents.joinForNarration(new Component[] { getTitle(), createLabelNarration() });
/*     */   }
/*     */   
/*     */   private void upload() {
/*  95 */     if (this.selectedWorld != -1 && !((LevelSummary)this.levelList.get(this.selectedWorld)).isHardcore()) {
/*  96 */       LevelSummary $$0 = this.levelList.get(this.selectedWorld);
/*  97 */       this.minecraft.setScreen((Screen)new RealmsUploadScreen(this.worldId, this.slotId, this.lastScreen, $$0));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 103 */     super.render($$0, $$1, $$2, $$3);
/* 104 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 13, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 109 */     if ($$0 == 256) {
/* 110 */       this.minecraft.setScreen((Screen)this.lastScreen);
/* 111 */       return true;
/*     */     } 
/* 113 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private class WorldSelectionList extends RealmsObjectSelectionList<Entry> {
/*     */     public WorldSelectionList() {
/* 118 */       super(RealmsSelectFileToUploadScreen.this.width, RealmsSelectFileToUploadScreen.this.height - 40 - RealmsSelectFileToUploadScreen.row(0), RealmsSelectFileToUploadScreen.row(0), 36);
/*     */     }
/*     */     
/*     */     public void addEntry(LevelSummary $$0) {
/* 122 */       addEntry(new RealmsSelectFileToUploadScreen.Entry($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxPosition() {
/* 127 */       return RealmsSelectFileToUploadScreen.this.levelList.size() * 36;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable RealmsSelectFileToUploadScreen.Entry $$0) {
/* 132 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/*     */       
/* 134 */       RealmsSelectFileToUploadScreen.this.selectedWorld = children().indexOf($$0);
/* 135 */       RealmsSelectFileToUploadScreen.this.uploadButton.active = (RealmsSelectFileToUploadScreen.this.selectedWorld >= 0 && RealmsSelectFileToUploadScreen.this.selectedWorld < getItemCount() && !((LevelSummary)RealmsSelectFileToUploadScreen.this.levelList.get(RealmsSelectFileToUploadScreen.this.selectedWorld)).isHardcore());
/*     */     } }
/*     */   
/*     */   private class Entry extends ObjectSelectionList.Entry<Entry> {
/*     */     private final LevelSummary levelSummary;
/*     */     private final String name;
/*     */     private final Component id;
/*     */     private final Component info;
/*     */     
/*     */     public Entry(LevelSummary $$0) {
/*     */       Component $$2;
/*     */       MutableComponent mutableComponent;
/* 147 */       this.levelSummary = $$0;
/* 148 */       this.name = $$0.getLevelName();
/* 149 */       this.id = (Component)Component.translatable("mco.upload.entry.id", new Object[] { $$0.getLevelId(), RealmsSelectFileToUploadScreen.formatLastPlayed($$0) });
/*     */ 
/*     */       
/* 152 */       if ($$0.isHardcore()) {
/* 153 */         Component $$1 = RealmsSelectFileToUploadScreen.HARDCORE_TEXT;
/*     */       } else {
/* 155 */         $$2 = RealmsSelectFileToUploadScreen.gameModeName($$0);
/*     */       } 
/*     */       
/* 158 */       if ($$0.hasCheats()) {
/* 159 */         mutableComponent = Component.translatable("mco.upload.entry.cheats", new Object[] { $$2.getString(), RealmsSelectFileToUploadScreen.CHEATS_TEXT });
/*     */       }
/*     */       
/* 162 */       this.info = (Component)mutableComponent;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 167 */       renderItem($$0, $$1, $$3, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 172 */       RealmsSelectFileToUploadScreen.this.worldSelectionList.selectItem(RealmsSelectFileToUploadScreen.this.levelList.indexOf(this.levelSummary));
/* 173 */       return true;
/*     */     }
/*     */     
/*     */     protected void renderItem(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/*     */       String $$5;
/* 178 */       if (this.name.isEmpty()) {
/* 179 */         String $$4 = "" + RealmsSelectFileToUploadScreen.WORLD_TEXT + " " + RealmsSelectFileToUploadScreen.WORLD_TEXT;
/*     */       } else {
/* 181 */         $$5 = this.name;
/*     */       } 
/*     */       
/* 184 */       $$0.drawString(RealmsSelectFileToUploadScreen.this.font, $$5, $$2 + 2, $$3 + 1, 16777215, false);
/* 185 */       $$0.drawString(RealmsSelectFileToUploadScreen.this.font, this.id, $$2 + 2, $$3 + 12, -8355712, false);
/* 186 */       $$0.drawString(RealmsSelectFileToUploadScreen.this.font, this.info, $$2 + 2, $$3 + 12 + 10, -8355712, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 191 */       Component $$0 = CommonComponents.joinLines(new Component[] {
/* 192 */             (Component)Component.literal(this.levelSummary.getLevelName()), 
/* 193 */             (Component)Component.literal(RealmsSelectFileToUploadScreen.formatLastPlayed(this.levelSummary)), 
/* 194 */             RealmsSelectFileToUploadScreen.gameModeName(this.levelSummary)
/*     */           });
/* 196 */       return (Component)Component.translatable("narrator.select", new Object[] { $$0 });
/*     */     }
/*     */   }
/*     */   
/*     */   static Component gameModeName(LevelSummary $$0) {
/* 201 */     return $$0.getGameMode().getLongDisplayName();
/*     */   }
/*     */   
/*     */   static String formatLastPlayed(LevelSummary $$0) {
/* 205 */     return DATE_FORMAT.format(new Date($$0.getLastPlayed()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSelectFileToUploadScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */