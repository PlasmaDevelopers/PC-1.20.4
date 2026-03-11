/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.components.tabs.GridLayoutTab;
/*     */ import net.minecraft.client.gui.layouts.CommonLayouts;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.Difficulty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GameTab
/*     */   extends GridLayoutTab
/*     */ {
/* 121 */   private static final Component TITLE = (Component)Component.translatable("createWorld.tab.game.title");
/* 122 */   private static final Component ALLOW_CHEATS = (Component)Component.translatable("selectWorld.allowCommands");
/*     */   private final EditBox nameEdit;
/*     */   
/*     */   GameTab() {
/* 126 */     super(TITLE);
/*     */ 
/*     */ 
/*     */     
/* 130 */     GridLayout.RowHelper $$0 = this.layout.rowSpacing(8).createRowHelper(1);
/* 131 */     LayoutSettings $$1 = $$0.newCellSettings();
/*     */     
/* 133 */     this.nameEdit = new EditBox(CreateWorldScreen.access$000(paramCreateWorldScreen), 208, 20, (Component)Component.translatable("selectWorld.enterName"));
/* 134 */     this.nameEdit.setValue(paramCreateWorldScreen.uiState.getName());
/* 135 */     Objects.requireNonNull(paramCreateWorldScreen.uiState); this.nameEdit.setResponder(paramCreateWorldScreen.uiState::setName);
/* 136 */     paramCreateWorldScreen.uiState.addListener($$0 -> this.nameEdit.setTooltip(Tooltip.create((Component)Component.translatable("selectWorld.targetFolder", new Object[] { Component.literal($$0.getTargetFolder()).withStyle(ChatFormatting.ITALIC) }))));
/* 137 */     CreateWorldScreen.access$100(paramCreateWorldScreen, (GuiEventListener)this.nameEdit);
/* 138 */     $$0.addChild((LayoutElement)CommonLayouts.labeledElement(CreateWorldScreen.access$200(paramCreateWorldScreen), (LayoutElement)this.nameEdit, CreateWorldScreen.NAME_LABEL), $$0.newCellSettings().alignHorizontallyCenter());
/*     */     
/* 140 */     CycleButton<WorldCreationUiState.SelectedGameMode> $$2 = (CycleButton<WorldCreationUiState.SelectedGameMode>)$$0.addChild(
/* 141 */         (LayoutElement)CycleButton.builder($$0 -> $$0.displayName)
/* 142 */         .withValues((Object[])new WorldCreationUiState.SelectedGameMode[] { WorldCreationUiState.SelectedGameMode.SURVIVAL, WorldCreationUiState.SelectedGameMode.HARDCORE, WorldCreationUiState.SelectedGameMode.CREATIVE
/* 143 */           }, ).create(0, 0, 210, 20, CreateWorldScreen.GAME_MODEL_LABEL, ($$0, $$1) -> CreateWorldScreen.this.uiState.setGameMode($$1)), $$1);
/*     */ 
/*     */ 
/*     */     
/* 147 */     paramCreateWorldScreen.uiState.addListener($$1 -> {
/*     */           $$0.setValue($$1.getGameMode());
/*     */           
/*     */           $$0.active = !$$1.isDebug();
/*     */           $$0.setTooltip(Tooltip.create($$1.getGameMode().getInfo()));
/*     */         });
/* 153 */     CycleButton<Difficulty> $$3 = (CycleButton<Difficulty>)$$0.addChild(
/* 154 */         (LayoutElement)CycleButton.builder(Difficulty::getDisplayName)
/* 155 */         .withValues((Object[])Difficulty.values())
/* 156 */         .create(0, 0, 210, 20, (Component)Component.translatable("options.difficulty"), ($$0, $$1) -> CreateWorldScreen.this.uiState.setDifficulty($$1)), $$1);
/*     */ 
/*     */     
/* 159 */     paramCreateWorldScreen.uiState.addListener($$1 -> {
/*     */           $$0.setValue(CreateWorldScreen.this.uiState.getDifficulty());
/*     */           
/*     */           $$0.active = !CreateWorldScreen.this.uiState.isHardcore();
/*     */           $$0.setTooltip(Tooltip.create(CreateWorldScreen.this.uiState.getDifficulty().getInfo()));
/*     */         });
/* 165 */     CycleButton<Boolean> $$4 = (CycleButton<Boolean>)$$0.addChild((LayoutElement)CycleButton.onOffBuilder()
/* 166 */         .withTooltip($$0 -> Tooltip.create(CreateWorldScreen.ALLOW_CHEATS_INFO))
/* 167 */         .create(0, 0, 210, 20, ALLOW_CHEATS, ($$0, $$1) -> CreateWorldScreen.this.uiState.setAllowCheats($$1.booleanValue())));
/*     */     
/* 169 */     paramCreateWorldScreen.uiState.addListener($$1 -> {
/*     */           $$0.setValue(Boolean.valueOf(CreateWorldScreen.this.uiState.isAllowCheats()));
/* 171 */           $$0.active = (!CreateWorldScreen.this.uiState.isDebug() && !CreateWorldScreen.this.uiState.isHardcore());
/*     */         });
/*     */     
/* 174 */     if (!SharedConstants.getCurrentVersion().isStable())
/* 175 */       $$0.addChild((LayoutElement)Button.builder(CreateWorldScreen.EXPERIMENTS_LABEL, $$0 -> CreateWorldScreen.this.openExperimentsScreen(CreateWorldScreen.this.uiState.getSettings().dataConfiguration())).width(210).build()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\CreateWorldScreen$GameTab.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */