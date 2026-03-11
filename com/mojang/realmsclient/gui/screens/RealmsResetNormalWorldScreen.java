/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.realmsclient.util.LevelType;
/*     */ import com.mojang.realmsclient.util.WorldGenerationInfo;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.CommonLayouts;
/*     */ import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.worldselection.ExperimentsScreen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.repository.PackSource;
/*     */ import net.minecraft.server.packs.repository.ServerPacksSource;
/*     */ 
/*     */ public class RealmsResetNormalWorldScreen extends RealmsScreen {
/*  26 */   private static final Component SEED_LABEL = (Component)Component.translatable("mco.reset.world.seed");
/*  27 */   public static final Component TITLE = (Component)Component.translatable("mco.reset.world.generate");
/*     */   
/*     */   private static final int BUTTON_SPACING = 10;
/*     */   
/*     */   private static final int CONTENT_WIDTH = 210;
/*  32 */   private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout((Screen)this);
/*     */   
/*     */   private final Consumer<WorldGenerationInfo> callback;
/*     */   
/*     */   private EditBox seedEdit;
/*  37 */   private LevelType levelType = LevelType.DEFAULT;
/*     */   private boolean generateStructures = true;
/*  39 */   private final Set<String> experiments = new HashSet<>();
/*     */   
/*     */   private final Component buttonTitle;
/*     */   
/*     */   public RealmsResetNormalWorldScreen(Consumer<WorldGenerationInfo> $$0, Component $$1) {
/*  44 */     super(TITLE);
/*  45 */     this.callback = $$0;
/*  46 */     this.buttonTitle = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  51 */     this.seedEdit = new EditBox(this.font, 210, 20, (Component)Component.translatable("mco.reset.world.seed"));
/*  52 */     this.seedEdit.setMaxLength(32);
/*  53 */     setInitialFocus((GuiEventListener)this.seedEdit);
/*     */     
/*  55 */     this.layout.addToHeader((LayoutElement)new StringWidget(this.title, this.font));
/*     */     
/*  57 */     LinearLayout $$0 = ((LinearLayout)this.layout.addToContents((LayoutElement)LinearLayout.vertical())).spacing(10);
/*     */     
/*  59 */     $$0.addChild((LayoutElement)CommonLayouts.labeledElement(this.font, (LayoutElement)this.seedEdit, SEED_LABEL));
/*     */     
/*  61 */     $$0.addChild((LayoutElement)CycleButton.builder(LevelType::getName)
/*  62 */         .withValues((Object[])LevelType.values())
/*  63 */         .withInitialValue(this.levelType)
/*  64 */         .create(0, 0, 210, 20, (Component)Component.translatable("selectWorld.mapType"), ($$0, $$1) -> this.levelType = $$1));
/*     */     
/*  66 */     $$0.addChild(
/*  67 */         (LayoutElement)CycleButton.onOffBuilder(this.generateStructures).create(0, 0, 210, 20, 
/*  68 */           (Component)Component.translatable("selectWorld.mapFeatures"), ($$0, $$1) -> this.generateStructures = $$1.booleanValue()));
/*     */ 
/*     */     
/*  71 */     createExperimentsButton($$0);
/*     */     
/*  73 */     LinearLayout $$1 = (LinearLayout)this.layout.addToFooter((LayoutElement)LinearLayout.horizontal().spacing(10));
/*  74 */     $$1.addChild((LayoutElement)Button.builder(this.buttonTitle, $$0 -> this.callback.accept(createWorldGenerationInfo())).build());
/*  75 */     $$1.addChild((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).build());
/*     */     
/*  77 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  78 */     repositionElements();
/*     */   }
/*     */   
/*     */   private void createExperimentsButton(LinearLayout $$0) {
/*  82 */     PackRepository $$1 = ServerPacksSource.createVanillaTrustedRepository();
/*  83 */     $$1.reload();
/*     */     
/*  85 */     $$0.addChild(
/*  86 */         (LayoutElement)Button.builder((Component)Component.translatable("selectWorld.experiments"), $$1 -> this.minecraft.setScreen((Screen)new ExperimentsScreen((Screen)this, $$0, ())))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         .width(210).build());
/*     */   }
/*     */ 
/*     */   
/*     */   private WorldGenerationInfo createWorldGenerationInfo() {
/* 102 */     return new WorldGenerationInfo(this.seedEdit.getValue(), this.levelType, this.generateStructures, this.experiments);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/* 107 */     this.layout.arrangeElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 112 */     this.callback.accept(null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsResetNormalWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */