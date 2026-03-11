/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.tabs.GridLayoutTab;
/*     */ import net.minecraft.client.gui.layouts.CommonLayouts;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WorldTab
/*     */   extends GridLayoutTab
/*     */ {
/* 181 */   private static final Component TITLE = (Component)Component.translatable("createWorld.tab.world.title");
/* 182 */   private static final Component AMPLIFIED_HELP_TEXT = (Component)Component.translatable("generator.minecraft.amplified.info");
/* 183 */   private static final Component GENERATE_STRUCTURES = (Component)Component.translatable("selectWorld.mapFeatures");
/* 184 */   private static final Component GENERATE_STRUCTURES_INFO = (Component)Component.translatable("selectWorld.mapFeatures.info");
/* 185 */   private static final Component BONUS_CHEST = (Component)Component.translatable("selectWorld.bonusItems");
/* 186 */   private static final Component SEED_LABEL = (Component)Component.translatable("selectWorld.enterSeed");
/* 187 */   static final Component SEED_EMPTY_HINT = (Component)Component.translatable("selectWorld.seedInfo").withStyle(ChatFormatting.DARK_GRAY);
/*     */   private static final int WORLD_TAB_WIDTH = 310;
/*     */   private final EditBox seedEdit;
/*     */   private final Button customizeTypeButton;
/*     */   
/*     */   WorldTab() {
/* 193 */     super(TITLE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     GridLayout.RowHelper $$0 = this.layout.columnSpacing(10).rowSpacing(8).createRowHelper(2);
/*     */     
/* 200 */     CycleButton<WorldCreationUiState.WorldTypeEntry> $$1 = (CycleButton<WorldCreationUiState.WorldTypeEntry>)$$0.addChild((LayoutElement)CycleButton.builder(WorldCreationUiState.WorldTypeEntry::describePreset)
/* 201 */         .withValues(createWorldTypeValueSupplier())
/* 202 */         .withCustomNarration(WorldTab::createTypeButtonNarration)
/* 203 */         .create(0, 0, 150, 20, (Component)Component.translatable("selectWorld.mapType"), ($$0, $$1) -> CreateWorldScreen.this.uiState.setWorldType($$1)));
/*     */ 
/*     */     
/* 206 */     $$1.setValue(paramCreateWorldScreen.uiState.getWorldType());
/* 207 */     paramCreateWorldScreen.uiState.addListener($$1 -> {
/*     */           WorldCreationUiState.WorldTypeEntry $$2 = $$1.getWorldType();
/*     */           
/*     */           $$0.setValue($$2);
/*     */           
/*     */           if ($$2.isAmplified()) {
/*     */             $$0.setTooltip(Tooltip.create(AMPLIFIED_HELP_TEXT));
/*     */           } else {
/*     */             $$0.setTooltip(null);
/*     */           } 
/*     */           $$0.active = (CreateWorldScreen.this.uiState.getWorldType().preset() != null);
/*     */         });
/* 219 */     this.customizeTypeButton = (Button)$$0.addChild((LayoutElement)Button.builder((Component)Component.translatable("selectWorld.customizeType"), $$0 -> openPresetEditor()).build());
/* 220 */     paramCreateWorldScreen.uiState.addListener($$0 -> this.customizeTypeButton.active = (!$$0.isDebug() && $$0.getPresetEditor() != null));
/*     */     
/* 222 */     this.seedEdit = new EditBox(CreateWorldScreen.access$300(paramCreateWorldScreen), 308, 20, (Component)Component.translatable("selectWorld.enterSeed"))
/*     */       {
/*     */         protected MutableComponent createNarrationMessage() {
/* 225 */           return super.createNarrationMessage().append(CommonComponents.NARRATION_SEPARATOR).append(CreateWorldScreen.WorldTab.SEED_EMPTY_HINT);
/*     */         }
/*     */       };
/* 228 */     this.seedEdit.setHint(SEED_EMPTY_HINT);
/* 229 */     this.seedEdit.setValue(paramCreateWorldScreen.uiState.getSeed());
/* 230 */     this.seedEdit.setResponder($$0 -> CreateWorldScreen.this.uiState.setSeed(this.seedEdit.getValue()));
/* 231 */     $$0.addChild((LayoutElement)CommonLayouts.labeledElement(CreateWorldScreen.access$400(paramCreateWorldScreen), (LayoutElement)this.seedEdit, SEED_LABEL), 2);
/*     */     
/* 233 */     SwitchGrid.Builder $$2 = SwitchGrid.builder(310);
/* 234 */     Objects.requireNonNull(paramCreateWorldScreen.uiState); Objects.requireNonNull(paramCreateWorldScreen.uiState); $$2.addSwitch(GENERATE_STRUCTURES, paramCreateWorldScreen.uiState::isGenerateStructures, paramCreateWorldScreen.uiState::setGenerateStructures)
/* 235 */       .withIsActiveCondition(() -> !CreateWorldScreen.this.uiState.isDebug())
/* 236 */       .withInfo(GENERATE_STRUCTURES_INFO);
/* 237 */     Objects.requireNonNull(paramCreateWorldScreen.uiState); Objects.requireNonNull(paramCreateWorldScreen.uiState); $$2.addSwitch(BONUS_CHEST, paramCreateWorldScreen.uiState::isBonusChest, paramCreateWorldScreen.uiState::setBonusChest)
/* 238 */       .withIsActiveCondition(() -> (!CreateWorldScreen.this.uiState.isHardcore() && !CreateWorldScreen.this.uiState.isDebug()));
/* 239 */     SwitchGrid $$3 = $$2.build($$1 -> $$0.addChild($$1, 2));
/* 240 */     paramCreateWorldScreen.uiState.addListener($$1 -> $$0.refreshStates());
/*     */   }
/*     */   
/*     */   private void openPresetEditor() {
/* 244 */     PresetEditor $$0 = CreateWorldScreen.this.uiState.getPresetEditor();
/* 245 */     if ($$0 != null) {
/* 246 */       CreateWorldScreen.access$500(CreateWorldScreen.this).setScreen($$0.createEditScreen(CreateWorldScreen.this, CreateWorldScreen.this.uiState.getSettings()));
/*     */     }
/*     */   }
/*     */   
/*     */   private CycleButton.ValueListSupplier<WorldCreationUiState.WorldTypeEntry> createWorldTypeValueSupplier() {
/* 251 */     return new CycleButton.ValueListSupplier<WorldCreationUiState.WorldTypeEntry>()
/*     */       {
/*     */         public List<WorldCreationUiState.WorldTypeEntry> getSelectedList() {
/* 254 */           return CycleButton.DEFAULT_ALT_LIST_SELECTOR.getAsBoolean() ? CreateWorldScreen.this.uiState.getAltPresetList() : CreateWorldScreen.this.uiState.getNormalPresetList();
/*     */         }
/*     */ 
/*     */         
/*     */         public List<WorldCreationUiState.WorldTypeEntry> getDefaultList() {
/* 259 */           return CreateWorldScreen.this.uiState.getNormalPresetList();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static MutableComponent createTypeButtonNarration(CycleButton<WorldCreationUiState.WorldTypeEntry> $$0) {
/* 265 */     if (((WorldCreationUiState.WorldTypeEntry)$$0.getValue()).isAmplified()) {
/* 266 */       return CommonComponents.joinForNarration(new Component[] { (Component)$$0.createDefaultNarrationMessage(), AMPLIFIED_HELP_TEXT });
/*     */     }
/* 268 */     return $$0.createDefaultNarrationMessage();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\CreateWorldScreen$WorldTab.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */