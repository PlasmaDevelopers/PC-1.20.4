/*     */ package net.minecraft.client.gui.screens.debug;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.MultiPlayerGameMode;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public class GameModeSwitcherScreen
/*     */   extends Screen {
/*  25 */   static final ResourceLocation SLOT_SPRITE = new ResourceLocation("gamemode_switcher/slot");
/*  26 */   static final ResourceLocation SELECTION_SPRITE = new ResourceLocation("gamemode_switcher/selection");
/*     */   
/*  28 */   private enum GameModeIcon { CREATIVE((String)Component.translatable("gameMode.creative"), "gamemode creative", (Component)new ItemStack((ItemLike)Blocks.GRASS_BLOCK)),
/*  29 */     SURVIVAL((String)Component.translatable("gameMode.survival"), "gamemode survival", (Component)new ItemStack((ItemLike)Items.IRON_SWORD)),
/*  30 */     ADVENTURE((String)Component.translatable("gameMode.adventure"), "gamemode adventure", (Component)new ItemStack((ItemLike)Items.MAP)),
/*  31 */     SPECTATOR((String)Component.translatable("gameMode.spectator"), "gamemode spectator", (Component)new ItemStack((ItemLike)Items.ENDER_EYE));
/*     */     
/*  33 */     protected static final GameModeIcon[] VALUES = values();
/*     */     
/*     */     private static final int ICON_AREA = 16;
/*     */     
/*     */     protected static final int ICON_TOP_LEFT = 5;
/*     */     
/*     */     final Component name;
/*     */     
/*     */     GameModeIcon(Component $$0, String $$1, ItemStack $$2) {
/*  42 */       this.name = $$0;
/*  43 */       this.command = $$1;
/*  44 */       this.renderStack = $$2;
/*     */     } final String command; final ItemStack renderStack; static {
/*     */     
/*     */     } void drawIcon(GuiGraphics $$0, int $$1, int $$2) {
/*  48 */       $$0.renderItem(this.renderStack, $$1, $$2);
/*     */     }
/*     */     
/*     */     Component getName() {
/*  52 */       return this.name;
/*     */     }
/*     */     
/*     */     String getCommand() {
/*  56 */       return this.command;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     GameModeIcon getNext() {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$1.$SwitchMap$net$minecraft$client$gui$screens$debug$GameModeSwitcherScreen$GameModeIcon : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: tableswitch default -> 40, 1 -> 48, 2 -> 54, 3 -> 60, 4 -> 66
/*     */       //   40: new java/lang/IncompatibleClassChangeError
/*     */       //   43: dup
/*     */       //   44: invokespecial <init> : ()V
/*     */       //   47: athrow
/*     */       //   48: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon.SURVIVAL : Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */       //   51: goto -> 69
/*     */       //   54: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon.ADVENTURE : Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */       //   57: goto -> 69
/*     */       //   60: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon.SPECTATOR : Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */       //   63: goto -> 69
/*     */       //   66: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon.CREATIVE : Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */       //   69: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #60	-> 0
/*     */       //   #61	-> 48
/*     */       //   #62	-> 54
/*     */       //   #63	-> 60
/*     */       //   #64	-> 66
/*     */       //   #60	-> 69
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	70	0	this	Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static GameModeIcon getFromGameType(GameType $$0) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$1.$SwitchMap$net$minecraft$world$level$GameType : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: tableswitch default -> 40, 1 -> 48, 2 -> 54, 3 -> 60, 4 -> 66
/*     */       //   40: new java/lang/IncompatibleClassChangeError
/*     */       //   43: dup
/*     */       //   44: invokespecial <init> : ()V
/*     */       //   47: athrow
/*     */       //   48: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon.SPECTATOR : Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */       //   51: goto -> 69
/*     */       //   54: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon.SURVIVAL : Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */       //   57: goto -> 69
/*     */       //   60: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon.CREATIVE : Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */       //   63: goto -> 69
/*     */       //   66: getstatic net/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon.ADVENTURE : Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;
/*     */       //   69: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #69	-> 0
/*     */       //   #70	-> 48
/*     */       //   #71	-> 54
/*     */       //   #72	-> 60
/*     */       //   #73	-> 66
/*     */       //   #69	-> 69
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	70	0	$$0	Lnet/minecraft/world/level/GameType;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static final ResourceLocation GAMEMODE_SWITCHER_LOCATION = new ResourceLocation("textures/gui/container/gamemode_switcher.png");
/*     */   
/*     */   private static final int SPRITE_SHEET_WIDTH = 128;
/*     */   private static final int SPRITE_SHEET_HEIGHT = 128;
/*     */   private static final int SLOT_AREA = 26;
/*     */   private static final int SLOT_PADDING = 5;
/*     */   private static final int SLOT_AREA_PADDED = 31;
/*     */   private static final int HELP_TIPS_OFFSET_Y = 5;
/*  86 */   private static final int ALL_SLOTS_WIDTH = (GameModeIcon.values()).length * 31 - 5;
/*     */   
/*  88 */   private static final Component SELECT_KEY = (Component)Component.translatable("debug.gamemodes.select_next", new Object[] { Component.translatable("debug.gamemodes.press_f4").withStyle(ChatFormatting.AQUA) });
/*     */   
/*     */   private final GameModeIcon previousHovered;
/*     */   
/*     */   private GameModeIcon currentlyHovered;
/*     */   
/*     */   private int firstMouseX;
/*     */   private int firstMouseY;
/*     */   private boolean setFirstMousePos;
/*  97 */   private final List<GameModeSlot> slots = Lists.newArrayList();
/*     */   
/*     */   public GameModeSwitcherScreen() {
/* 100 */     super(GameNarrator.NO_TITLE);
/*     */     
/* 102 */     this.previousHovered = GameModeIcon.getFromGameType(getDefaultSelected());
/* 103 */     this.currentlyHovered = this.previousHovered;
/*     */   }
/*     */   
/*     */   private GameType getDefaultSelected() {
/* 107 */     MultiPlayerGameMode $$0 = (Minecraft.getInstance()).gameMode;
/* 108 */     GameType $$1 = $$0.getPreviousPlayerMode();
/* 109 */     if ($$1 != null) {
/* 110 */       return $$1;
/*     */     }
/* 112 */     return ($$0.getPlayerMode() == GameType.CREATIVE) ? GameType.SURVIVAL : GameType.CREATIVE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 117 */     super.init();
/*     */     
/* 119 */     this.currentlyHovered = this.previousHovered;
/*     */     
/* 121 */     for (int $$0 = 0; $$0 < GameModeIcon.VALUES.length; $$0++) {
/* 122 */       GameModeIcon $$1 = GameModeIcon.VALUES[$$0];
/* 123 */       this.slots.add(new GameModeSlot($$1, this.width / 2 - ALL_SLOTS_WIDTH / 2 + $$0 * 31, this.height / 2 - 31));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 129 */     if (checkToClose()) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     $$0.pose().pushPose();
/* 134 */     RenderSystem.enableBlend();
/* 135 */     int $$4 = this.width / 2 - 62;
/* 136 */     int $$5 = this.height / 2 - 31 - 27;
/* 137 */     $$0.blit(GAMEMODE_SWITCHER_LOCATION, $$4, $$5, 0.0F, 0.0F, 125, 75, 128, 128);
/* 138 */     $$0.pose().popPose();
/*     */     
/* 140 */     super.render($$0, $$1, $$2, $$3);
/*     */ 
/*     */     
/* 143 */     $$0.drawCenteredString(this.font, this.currentlyHovered.getName(), this.width / 2, this.height / 2 - 31 - 20, -1);
/*     */ 
/*     */     
/* 146 */     $$0.drawCenteredString(this.font, SELECT_KEY, this.width / 2, this.height / 2 + 5, 16777215);
/*     */     
/* 148 */     if (!this.setFirstMousePos) {
/* 149 */       this.firstMouseX = $$1;
/* 150 */       this.firstMouseY = $$2;
/*     */       
/* 152 */       this.setFirstMousePos = true;
/*     */     } 
/*     */     
/* 155 */     boolean $$6 = (this.firstMouseX == $$1 && this.firstMouseY == $$2);
/*     */     
/* 157 */     for (GameModeSlot $$7 : this.slots) {
/* 158 */       $$7.render($$0, $$1, $$2, $$3);
/*     */       
/* 160 */       $$7.setSelected((this.currentlyHovered == $$7.icon));
/*     */       
/* 162 */       if (!$$6 && $$7.isHoveredOrFocused()) {
/* 163 */         this.currentlyHovered = $$7.icon;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {}
/*     */ 
/*     */   
/*     */   private void switchToHoveredGameMode() {
/* 174 */     switchToHoveredGameMode(this.minecraft, this.currentlyHovered);
/*     */   }
/*     */   
/*     */   private static void switchToHoveredGameMode(Minecraft $$0, GameModeIcon $$1) {
/* 178 */     if ($$0.gameMode == null || $$0.player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 182 */     GameModeIcon $$2 = GameModeIcon.getFromGameType($$0.gameMode.getPlayerMode());
/* 183 */     GameModeIcon $$3 = $$1;
/*     */     
/* 185 */     if ($$0.player.hasPermissions(2) && $$3 != $$2) {
/* 186 */       $$0.player.connection.sendUnsignedCommand($$3.getCommand());
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean checkToClose() {
/* 191 */     if (!InputConstants.isKeyDown(this.minecraft.getWindow().getWindow(), 292)) {
/* 192 */       switchToHoveredGameMode();
/* 193 */       this.minecraft.setScreen(null);
/*     */       
/* 195 */       return true;
/*     */     } 
/*     */     
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 203 */     if ($$0 == 293) {
/* 204 */       this.setFirstMousePos = false;
/* 205 */       this.currentlyHovered = this.currentlyHovered.getNext();
/* 206 */       return true;
/*     */     } 
/*     */     
/* 209 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 214 */     return false;
/*     */   }
/*     */   
/*     */   public class GameModeSlot extends AbstractWidget {
/*     */     final GameModeSwitcherScreen.GameModeIcon icon;
/*     */     private boolean isSelected;
/*     */     
/*     */     public GameModeSlot(GameModeSwitcherScreen.GameModeIcon $$1, int $$2, int $$3) {
/* 222 */       super($$2, $$3, 26, 26, $$1.getName());
/* 223 */       this.icon = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 228 */       drawSlot($$0);
/* 229 */       this.icon.drawIcon($$0, getX() + 5, getY() + 5);
/*     */       
/* 231 */       if (this.isSelected) {
/* 232 */         drawSelection($$0);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 238 */       defaultButtonNarrationText($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isHoveredOrFocused() {
/* 243 */       return (super.isHoveredOrFocused() || this.isSelected);
/*     */     }
/*     */     
/*     */     public void setSelected(boolean $$0) {
/* 247 */       this.isSelected = $$0;
/*     */     }
/*     */     
/*     */     private void drawSlot(GuiGraphics $$0) {
/* 251 */       $$0.blitSprite(GameModeSwitcherScreen.SLOT_SPRITE, getX(), getY(), 26, 26);
/*     */     }
/*     */     
/*     */     private void drawSelection(GuiGraphics $$0) {
/* 255 */       $$0.blitSprite(GameModeSwitcherScreen.SELECTION_SPRITE, getX(), getY(), 26, 26);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\debug\GameModeSwitcherScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */