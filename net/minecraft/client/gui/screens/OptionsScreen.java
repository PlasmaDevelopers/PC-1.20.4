/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.LockIconButton;
/*     */ import net.minecraft.client.gui.layouts.EqualSpacingLayout;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.SpacerElement;
/*     */ import net.minecraft.client.gui.screens.controls.ControlsScreen;
/*     */ import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
/*     */ import net.minecraft.client.gui.screens.telemetry.TelemetryInfoScreen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.world.Difficulty;
/*     */ 
/*     */ public class OptionsScreen extends Screen {
/*  27 */   private static final Component SKIN_CUSTOMIZATION = (Component)Component.translatable("options.skinCustomisation");
/*  28 */   private static final Component SOUNDS = (Component)Component.translatable("options.sounds");
/*  29 */   private static final Component VIDEO = (Component)Component.translatable("options.video");
/*  30 */   private static final Component CONTROLS = (Component)Component.translatable("options.controls");
/*  31 */   private static final Component LANGUAGE = (Component)Component.translatable("options.language");
/*  32 */   private static final Component CHAT = (Component)Component.translatable("options.chat");
/*  33 */   private static final Component RESOURCEPACK = (Component)Component.translatable("options.resourcepack");
/*  34 */   private static final Component ACCESSIBILITY = (Component)Component.translatable("options.accessibility");
/*  35 */   private static final Component TELEMETRY = (Component)Component.translatable("options.telemetry");
/*  36 */   private static final Component CREDITS_AND_ATTRIBUTION = (Component)Component.translatable("options.credits_and_attribution");
/*     */   
/*     */   private static final int COLUMNS = 2;
/*     */   
/*     */   private final Screen lastScreen;
/*     */   private final Options options;
/*     */   private CycleButton<Difficulty> difficultyButton;
/*     */   private LockIconButton lockButton;
/*     */   
/*     */   public OptionsScreen(Screen $$0, Options $$1) {
/*  46 */     super((Component)Component.translatable("options.title"));
/*  47 */     this.lastScreen = $$0;
/*  48 */     this.options = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  53 */     GridLayout $$0 = new GridLayout();
/*  54 */     $$0.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
/*     */     
/*  56 */     GridLayout.RowHelper $$1 = $$0.createRowHelper(2);
/*     */     
/*  58 */     $$1.addChild((LayoutElement)this.options.fov().createButton(this.minecraft.options, 0, 0, 150));
/*  59 */     $$1.addChild(createOnlineButton());
/*     */     
/*  61 */     $$1.addChild((LayoutElement)SpacerElement.height(26), 2);
/*     */     
/*  63 */     $$1.addChild((LayoutElement)openScreenButton(SKIN_CUSTOMIZATION, () -> new SkinCustomizationScreen(this, this.options)));
/*  64 */     $$1.addChild((LayoutElement)openScreenButton(SOUNDS, () -> new SoundOptionsScreen(this, this.options)));
/*  65 */     $$1.addChild((LayoutElement)openScreenButton(VIDEO, () -> new VideoSettingsScreen(this, this.options)));
/*  66 */     $$1.addChild((LayoutElement)openScreenButton(CONTROLS, () -> new ControlsScreen(this, this.options)));
/*  67 */     $$1.addChild((LayoutElement)openScreenButton(LANGUAGE, () -> new LanguageSelectScreen(this, this.options, this.minecraft.getLanguageManager())));
/*  68 */     $$1.addChild((LayoutElement)openScreenButton(CHAT, () -> new ChatOptionsScreen(this, this.options)));
/*  69 */     $$1.addChild((LayoutElement)openScreenButton(RESOURCEPACK, () -> new PackSelectionScreen(this.minecraft.getResourcePackRepository(), this::applyPacks, this.minecraft.getResourcePackDirectory(), (Component)Component.translatable("resourcePack.title"))));
/*  70 */     $$1.addChild((LayoutElement)openScreenButton(ACCESSIBILITY, () -> new AccessibilityOptionsScreen(this, this.options)));
/*  71 */     $$1.addChild((LayoutElement)openScreenButton(TELEMETRY, () -> new TelemetryInfoScreen(this, this.options)));
/*  72 */     $$1.addChild((LayoutElement)openScreenButton(CREDITS_AND_ATTRIBUTION, () -> new CreditsAndAttributionScreen(this)));
/*     */     
/*  74 */     $$1.addChild(
/*  75 */         (LayoutElement)Button.builder(CommonComponents.GUI_DONE, $$0 -> this.minecraft.setScreen(this.lastScreen)).width(200).build(), 2, $$1
/*     */         
/*  77 */         .newCellSettings().paddingTop(6));
/*     */ 
/*     */     
/*  80 */     $$0.arrangeElements();
/*  81 */     FrameLayout.alignInRectangle((LayoutElement)$$0, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
/*  82 */     $$0.visitWidgets(this::addRenderableWidget);
/*     */   }
/*     */   
/*     */   private void applyPacks(PackRepository $$0) {
/*  86 */     this.options.updateResourcePacks($$0);
/*  87 */     this.minecraft.setScreen(this);
/*     */   }
/*     */   
/*     */   private LayoutElement createOnlineButton() {
/*  91 */     if (this.minecraft.level != null && this.minecraft.hasSingleplayerServer()) {
/*  92 */       this.difficultyButton = createDifficultyButton(0, 0, "options.difficulty", this.minecraft);
/*     */       
/*  94 */       if (!this.minecraft.level.getLevelData().isHardcore()) {
/*  95 */         this.lockButton = new LockIconButton(0, 0, $$0 -> this.minecraft.setScreen(new ConfirmScreen(this::lockCallback, (Component)Component.translatable("difficulty.lock.title"), (Component)Component.translatable("difficulty.lock.question", new Object[] { this.minecraft.level.getLevelData().getDifficulty().getDisplayName() }))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 102 */         this.difficultyButton.setWidth(this.difficultyButton.getWidth() - this.lockButton.getWidth());
/*     */         
/* 104 */         this.lockButton.setLocked(this.minecraft.level.getLevelData().isDifficultyLocked());
/* 105 */         this.lockButton.active = !this.lockButton.isLocked();
/* 106 */         this.difficultyButton.active = !this.lockButton.isLocked();
/*     */         
/* 108 */         EqualSpacingLayout $$0 = new EqualSpacingLayout(150, 0, EqualSpacingLayout.Orientation.HORIZONTAL);
/* 109 */         $$0.addChild((LayoutElement)this.difficultyButton);
/* 110 */         $$0.addChild((LayoutElement)this.lockButton);
/*     */         
/* 112 */         return (LayoutElement)$$0;
/*     */       } 
/* 114 */       this.difficultyButton.active = false;
/* 115 */       return (LayoutElement)this.difficultyButton;
/*     */     } 
/*     */     
/* 118 */     return (LayoutElement)Button.builder((Component)Component.translatable("options.online"), $$0 -> this.minecraft.setScreen(OnlineOptionsScreen.createOnlineOptionsScreen(this.minecraft, this, this.options))).bounds(this.width / 2 + 5, this.height / 6 - 12 + 24, 150, 20).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static CycleButton<Difficulty> createDifficultyButton(int $$0, int $$1, String $$2, Minecraft $$3) {
/* 123 */     return CycleButton.builder(Difficulty::getDisplayName)
/* 124 */       .withValues((Object[])Difficulty.values())
/* 125 */       .withInitialValue($$3.level.getDifficulty())
/* 126 */       .create($$0, $$1, 150, 20, (Component)Component.translatable($$2), ($$1, $$2) -> $$0.getConnection().send((Packet)new ServerboundChangeDifficultyPacket($$2)));
/*     */   }
/*     */ 
/*     */   
/*     */   private void lockCallback(boolean $$0) {
/* 131 */     this.minecraft.setScreen(this);
/* 132 */     if ($$0 && this.minecraft.level != null) {
/* 133 */       this.minecraft.getConnection().send((Packet)new ServerboundLockDifficultyPacket(true));
/* 134 */       this.lockButton.setLocked(true);
/* 135 */       this.lockButton.active = false;
/* 136 */       this.difficultyButton.active = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/* 142 */     this.options.save();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 147 */     super.render($$0, $$1, $$2, $$3);
/* 148 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
/*     */   }
/*     */   
/*     */   private Button openScreenButton(Component $$0, Supplier<Screen> $$1) {
/* 152 */     return Button.builder($$0, $$1 -> this.minecraft.setScreen($$0.get())).build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\OptionsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */