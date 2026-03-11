/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SelectWorldScreen
/*     */   extends Screen {
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  28 */   public static final WorldOptions TEST_OPTIONS = new WorldOptions("test1".hashCode(), true, false);
/*     */   
/*     */   protected final Screen lastScreen;
/*     */   
/*     */   private Button deleteButton;
/*     */   
/*     */   private Button selectButton;
/*     */   private Button renameButton;
/*     */   private Button copyButton;
/*     */   protected EditBox searchBox;
/*     */   private WorldSelectionList list;
/*     */   
/*     */   public SelectWorldScreen(Screen $$0) {
/*  41 */     super((Component)Component.translatable("selectWorld.title"));
/*  42 */     this.lastScreen = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  47 */     this.searchBox = new EditBox(this.font, this.width / 2 - 100, 22, 200, 20, this.searchBox, (Component)Component.translatable("selectWorld.search"));
/*  48 */     this.searchBox.setResponder($$0 -> this.list.updateFilter($$0));
/*  49 */     addWidget((GuiEventListener)this.searchBox);
/*     */     
/*  51 */     this.list = (WorldSelectionList)addRenderableWidget((GuiEventListener)new WorldSelectionList(this, this.minecraft, this.width, this.height - 112, 48, 36, this.searchBox.getValue(), this.list));
/*     */     
/*  53 */     this.selectButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(LevelSummary.PLAY_WORLD, $$0 -> this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::joinWorld)).bounds(this.width / 2 - 154, this.height - 52, 150, 20).build());
/*  54 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectWorld.create"), $$0 -> CreateWorldScreen.openFresh(this.minecraft, this)).bounds(this.width / 2 + 4, this.height - 52, 150, 20).build());
/*     */     
/*  56 */     this.renameButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectWorld.edit"), $$0 -> this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::editWorld)).bounds(this.width / 2 - 154, this.height - 28, 72, 20).build());
/*  57 */     this.deleteButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectWorld.delete"), $$0 -> this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::deleteWorld)).bounds(this.width / 2 - 76, this.height - 28, 72, 20).build());
/*  58 */     this.copyButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectWorld.recreate"), $$0 -> this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::recreateWorld)).bounds(this.width / 2 + 4, this.height - 28, 72, 20).build());
/*  59 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 + 82, this.height - 28, 72, 20).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     updateButtonStatus((LevelSummary)null);
/*     */     
/*  83 */     setInitialFocus((GuiEventListener)this.searchBox);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  88 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  93 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/*  95 */     this.searchBox.render($$0, $$1, $$2, $$3);
/*     */     
/*  97 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
/*     */   }
/*     */   
/*     */   public void updateButtonStatus(@Nullable LevelSummary $$0) {
/* 101 */     if ($$0 == null) {
/* 102 */       this.selectButton.setMessage(LevelSummary.PLAY_WORLD);
/* 103 */       this.selectButton.active = false;
/* 104 */       this.renameButton.active = false;
/* 105 */       this.copyButton.active = false;
/* 106 */       this.deleteButton.active = false;
/*     */     } else {
/* 108 */       this.selectButton.setMessage($$0.primaryActionMessage());
/* 109 */       this.selectButton.active = $$0.primaryActionActive();
/* 110 */       this.renameButton.active = $$0.canEdit();
/* 111 */       this.copyButton.active = $$0.canRecreate();
/* 112 */       this.deleteButton.active = $$0.canDelete();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removed() {
/* 119 */     if (this.list != null)
/* 120 */       this.list.children().forEach(WorldSelectionList.Entry::close); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\SelectWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */