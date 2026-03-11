/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.RealmsWorldOptions;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSliderButton;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.realms.RealmsLabel;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.level.GameType;
/*     */ 
/*     */ public class RealmsSlotOptionsScreen extends RealmsScreen {
/*     */   private static final int DEFAULT_DIFFICULTY = 2;
/*  29 */   public static final List<Difficulty> DIFFICULTIES = (List<Difficulty>)ImmutableList.of(Difficulty.PEACEFUL, Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARD);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_GAME_MODE = 0;
/*     */ 
/*     */ 
/*     */   
/*  37 */   public static final List<GameType> GAME_MODES = (List<GameType>)ImmutableList.of(GameType.SURVIVAL, GameType.CREATIVE, GameType.ADVENTURE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final Component NAME_LABEL = (Component)Component.translatable("mco.configure.world.edit.slot.name");
/*  44 */   static final Component SPAWN_PROTECTION_TEXT = (Component)Component.translatable("mco.configure.world.spawnProtection");
/*  45 */   private static final Component SPAWN_WARNING_TITLE = (Component)Component.translatable("mco.configure.world.spawn_toggle.title").withStyle(new ChatFormatting[] { ChatFormatting.RED, ChatFormatting.BOLD });
/*     */   
/*     */   private EditBox nameEdit;
/*     */   
/*     */   protected final RealmsConfigureWorldScreen parent;
/*     */   
/*     */   private int column1X;
/*     */   
/*     */   private int columnWidth;
/*     */   
/*     */   private final RealmsWorldOptions options;
/*     */   
/*     */   private final RealmsServer.WorldType worldType;
/*     */   
/*     */   private Difficulty difficulty;
/*     */   private GameType gameMode;
/*     */   private final String defaultSlotName;
/*     */   private String worldName;
/*     */   private boolean pvp;
/*     */   private boolean spawnNPCs;
/*     */   private boolean spawnAnimals;
/*     */   private boolean spawnMonsters;
/*     */   int spawnProtection;
/*     */   private boolean commandBlocks;
/*     */   private boolean forceGameMode;
/*     */   SettingsSlider spawnProtectionButton;
/*     */   
/*     */   public RealmsSlotOptionsScreen(RealmsConfigureWorldScreen $$0, RealmsWorldOptions $$1, RealmsServer.WorldType $$2, int $$3) {
/*  73 */     super((Component)Component.translatable("mco.configure.world.buttons.options"));
/*  74 */     this.parent = $$0;
/*  75 */     this.options = $$1;
/*  76 */     this.worldType = $$2;
/*     */     
/*  78 */     this.difficulty = findByIndex(DIFFICULTIES, $$1.difficulty, 2);
/*  79 */     this.gameMode = findByIndex(GAME_MODES, $$1.gameMode, 0);
/*     */     
/*  81 */     this.defaultSlotName = $$1.getDefaultSlotName($$3);
/*  82 */     setWorldName($$1.getSlotName($$3));
/*     */     
/*  84 */     if ($$2 == RealmsServer.WorldType.NORMAL) {
/*  85 */       this.pvp = $$1.pvp;
/*  86 */       this.spawnProtection = $$1.spawnProtection;
/*  87 */       this.forceGameMode = $$1.forceGameMode;
/*     */       
/*  89 */       this.spawnAnimals = $$1.spawnAnimals;
/*  90 */       this.spawnMonsters = $$1.spawnMonsters;
/*  91 */       this.spawnNPCs = $$1.spawnNPCs;
/*  92 */       this.commandBlocks = $$1.commandBlocks;
/*     */     } else {
/*  94 */       this.pvp = true;
/*  95 */       this.spawnProtection = 0;
/*  96 */       this.forceGameMode = false;
/*     */       
/*  98 */       this.spawnAnimals = true;
/*  99 */       this.spawnMonsters = true;
/* 100 */       this.spawnNPCs = true;
/* 101 */       this.commandBlocks = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 107 */     if ($$0 == 256) {
/* 108 */       this.minecraft.setScreen((Screen)this.parent);
/* 109 */       return true;
/*     */     } 
/* 111 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private static <T> T findByIndex(List<T> $$0, int $$1, int $$2) {
/*     */     try {
/* 116 */       return $$0.get($$1);
/* 117 */     } catch (IndexOutOfBoundsException $$3) {
/* 118 */       return $$0.get($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static <T> int findIndex(List<T> $$0, T $$1, int $$2) {
/* 123 */     int $$3 = $$0.indexOf($$1);
/* 124 */     return ($$3 == -1) ? $$2 : $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 129 */     this.columnWidth = 170;
/* 130 */     this.column1X = this.width / 2 - this.columnWidth;
/* 131 */     int $$0 = this.width / 2 + 10;
/*     */     
/* 133 */     if (this.worldType != RealmsServer.WorldType.NORMAL) {
/*     */       MutableComponent mutableComponent1;
/* 135 */       if (this.worldType == RealmsServer.WorldType.ADVENTUREMAP) {
/* 136 */         mutableComponent1 = Component.translatable("mco.configure.world.edit.subscreen.adventuremap");
/* 137 */       } else if (this.worldType == RealmsServer.WorldType.INSPIRATION) {
/* 138 */         mutableComponent1 = Component.translatable("mco.configure.world.edit.subscreen.inspiration");
/*     */       } else {
/* 140 */         mutableComponent1 = Component.translatable("mco.configure.world.edit.subscreen.experience");
/*     */       } 
/* 142 */       addLabel(new RealmsLabel((Component)mutableComponent1, this.width / 2, 26, 16711680));
/*     */     } 
/*     */     
/* 145 */     this.nameEdit = new EditBox(this.minecraft.font, this.column1X, row(1), this.columnWidth, 20, null, (Component)Component.translatable("mco.configure.world.edit.slot.name"));
/* 146 */     this.nameEdit.setMaxLength(10);
/* 147 */     this.nameEdit.setValue(this.worldName);
/* 148 */     this.nameEdit.setResponder(this::setWorldName);
/* 149 */     magicalSpecialHackyFocus((GuiEventListener)this.nameEdit);
/*     */     
/* 151 */     CycleButton<Boolean> $$4 = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(this.pvp).create($$0, row(1), this.columnWidth, 20, (Component)Component.translatable("mco.configure.world.pvp"), ($$0, $$1) -> this.pvp = $$1.booleanValue()));
/*     */     
/* 153 */     addRenderableWidget((GuiEventListener)CycleButton.builder(GameType::getShortDisplayName)
/* 154 */         .withValues(GAME_MODES)
/* 155 */         .withInitialValue(this.gameMode)
/* 156 */         .create(this.column1X, row(3), this.columnWidth, 20, (Component)Component.translatable("selectWorld.gameMode"), ($$0, $$1) -> this.gameMode = $$1));
/*     */ 
/*     */     
/* 159 */     MutableComponent mutableComponent = Component.translatable("mco.configure.world.spawn_toggle.message");
/* 160 */     CycleButton<Boolean> $$6 = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(this.spawnAnimals).create($$0, row(3), this.columnWidth, 20, (Component)Component.translatable("mco.configure.world.spawnAnimals"), confirmDangerousOption((Component)mutableComponent, $$0 -> this.spawnAnimals = $$0.booleanValue())));
/* 161 */     CycleButton<Boolean> $$7 = CycleButton.onOffBuilder((this.difficulty != Difficulty.PEACEFUL && this.spawnMonsters)).create($$0, row(5), this.columnWidth, 20, (Component)Component.translatable("mco.configure.world.spawnMonsters"), confirmDangerousOption((Component)mutableComponent, $$0 -> this.spawnMonsters = $$0.booleanValue()));
/*     */     
/* 163 */     addRenderableWidget((GuiEventListener)CycleButton.builder(Difficulty::getDisplayName)
/* 164 */         .withValues(DIFFICULTIES)
/* 165 */         .withInitialValue(this.difficulty)
/* 166 */         .create(this.column1X, row(5), this.columnWidth, 20, (Component)Component.translatable("options.difficulty"), ($$1, $$2) -> {
/*     */             this.difficulty = $$2;
/*     */             
/*     */             if (this.worldType == RealmsServer.WorldType.NORMAL) {
/*     */               boolean $$3 = (this.difficulty != Difficulty.PEACEFUL);
/*     */               $$0.active = $$3;
/* 172 */               $$0.setValue(Boolean.valueOf(($$3 && this.spawnMonsters)));
/*     */             } 
/*     */           }));
/*     */     
/* 176 */     addRenderableWidget((GuiEventListener)$$7);
/* 177 */     this.spawnProtectionButton = (SettingsSlider)addRenderableWidget((GuiEventListener)new SettingsSlider(this.column1X, row(7), this.columnWidth, this.spawnProtection, 0.0F, 16.0F));
/* 178 */     CycleButton<Boolean> $$8 = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(this.spawnNPCs).create($$0, row(7), this.columnWidth, 20, (Component)Component.translatable("mco.configure.world.spawnNPCs"), confirmDangerousOption((Component)Component.translatable("mco.configure.world.spawn_toggle.message.npc"), $$0 -> this.spawnNPCs = $$0.booleanValue())));
/* 179 */     CycleButton<Boolean> $$9 = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(this.forceGameMode).create(this.column1X, row(9), this.columnWidth, 20, (Component)Component.translatable("mco.configure.world.forceGameMode"), ($$0, $$1) -> this.forceGameMode = $$1.booleanValue()));
/* 180 */     CycleButton<Boolean> $$10 = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(this.commandBlocks).create($$0, row(9), this.columnWidth, 20, (Component)Component.translatable("mco.configure.world.commandBlocks"), ($$0, $$1) -> this.commandBlocks = $$1.booleanValue()));
/*     */     
/* 182 */     if (this.worldType != RealmsServer.WorldType.NORMAL) {
/* 183 */       $$4.active = false;
/* 184 */       $$6.active = false;
/* 185 */       $$8.active = false;
/* 186 */       $$7.active = false;
/* 187 */       this.spawnProtectionButton.active = false;
/* 188 */       $$10.active = false;
/* 189 */       $$9.active = false;
/*     */     } 
/*     */     
/* 192 */     if (this.difficulty == Difficulty.PEACEFUL) {
/* 193 */       $$7.active = false;
/*     */     }
/*     */     
/* 196 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.done"), $$0 -> saveSettings()).bounds(this.column1X, row(13), this.columnWidth, 20).build());
/* 197 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.minecraft.setScreen((Screen)this.parent)).bounds($$0, row(13), this.columnWidth, 20).build());
/* 198 */     addWidget((GuiEventListener)this.nameEdit);
/*     */   }
/*     */   
/*     */   private CycleButton.OnValueChange<Boolean> confirmDangerousOption(Component $$0, Consumer<Boolean> $$1) {
/* 202 */     return ($$2, $$3) -> {
/*     */         if ($$3.booleanValue()) {
/*     */           $$0.accept(Boolean.valueOf(true));
/*     */         } else {
/*     */           this.minecraft.setScreen((Screen)new ConfirmScreen((), SPAWN_WARNING_TITLE, $$1, CommonComponents.GUI_PROCEED, CommonComponents.GUI_CANCEL));
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/* 226 */     return (Component)CommonComponents.joinForNarration(new Component[] { getTitle(), createLabelNarration() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 231 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 233 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 17, -1);
/* 234 */     $$0.drawString(this.font, NAME_LABEL, this.column1X + this.columnWidth / 2 - this.font.width((FormattedText)NAME_LABEL) / 2, row(0) - 5, -1, false);
/*     */     
/* 236 */     this.nameEdit.render($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private void setWorldName(String $$0) {
/* 240 */     if ($$0.equals(this.defaultSlotName)) {
/* 241 */       this.worldName = "";
/*     */     } else {
/* 243 */       this.worldName = $$0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class SettingsSlider extends AbstractSliderButton {
/*     */     private final double minValue;
/*     */     private final double maxValue;
/*     */     
/*     */     public SettingsSlider(int $$0, int $$1, int $$2, int $$3, float $$4, float $$5) {
/* 252 */       super($$0, $$1, $$2, 20, CommonComponents.EMPTY, 0.0D);
/*     */       
/* 254 */       this.minValue = $$4;
/* 255 */       this.maxValue = $$5;
/*     */       
/* 257 */       this.value = ((Mth.clamp($$3, $$4, $$5) - $$4) / ($$5 - $$4));
/*     */       
/* 259 */       updateMessage();
/*     */     }
/*     */ 
/*     */     
/*     */     public void applyValue() {
/* 264 */       if (!RealmsSlotOptionsScreen.this.spawnProtectionButton.active) {
/*     */         return;
/*     */       }
/*     */       
/* 268 */       RealmsSlotOptionsScreen.this.spawnProtection = (int)Mth.lerp(Mth.clamp(this.value, 0.0D, 1.0D), this.minValue, this.maxValue);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void updateMessage() {
/* 273 */       setMessage((Component)CommonComponents.optionNameValue(RealmsSlotOptionsScreen.SPAWN_PROTECTION_TEXT, (RealmsSlotOptionsScreen.this.spawnProtection == 0) ? CommonComponents.OPTION_OFF : (Component)Component.literal(String.valueOf(RealmsSlotOptionsScreen.this.spawnProtection))));
/*     */     }
/*     */   }
/*     */   
/*     */   private void saveSettings() {
/* 278 */     int $$0 = findIndex(DIFFICULTIES, this.difficulty, 2);
/* 279 */     int $$1 = findIndex(GAME_MODES, this.gameMode, 0);
/*     */     
/* 281 */     if (this.worldType == RealmsServer.WorldType.ADVENTUREMAP || this.worldType == RealmsServer.WorldType.EXPERIENCE || this.worldType == RealmsServer.WorldType.INSPIRATION) {
/* 282 */       this.parent.saveSlotSettings(new RealmsWorldOptions(this.options.pvp, this.options.spawnAnimals, this.options.spawnMonsters, this.options.spawnNPCs, this.options.spawnProtection, this.options.commandBlocks, $$0, $$1, this.options.forceGameMode, this.worldName, this.options.version, this.options.compatibility));
/*     */     } else {
/* 284 */       boolean $$2 = (this.worldType == RealmsServer.WorldType.NORMAL && this.difficulty != Difficulty.PEACEFUL && this.spawnMonsters);
/* 285 */       this.parent.saveSlotSettings(new RealmsWorldOptions(this.pvp, this.spawnAnimals, $$2, this.spawnNPCs, this.spawnProtection, this.commandBlocks, $$0, $$1, this.forceGameMode, this.worldName, this.options.version, this.options.compatibility));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSlotOptionsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */