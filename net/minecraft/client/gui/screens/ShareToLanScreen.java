/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.server.IntegratedServer;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.server.commands.PublishCommand;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.world.level.GameType;
/*     */ 
/*     */ public class ShareToLanScreen extends Screen {
/*     */   private static final int PORT_LOWER_BOUND = 1024;
/*     */   private static final int PORT_HIGHER_BOUND = 65535;
/*  21 */   private static final Component ALLOW_COMMANDS_LABEL = (Component)Component.translatable("selectWorld.allowCommands");
/*  22 */   private static final Component GAME_MODE_LABEL = (Component)Component.translatable("selectWorld.gameMode");
/*  23 */   private static final Component INFO_TEXT = (Component)Component.translatable("lanServer.otherPlayers");
/*  24 */   private static final Component PORT_INFO_TEXT = (Component)Component.translatable("lanServer.port");
/*  25 */   private static final Component PORT_UNAVAILABLE = (Component)Component.translatable("lanServer.port.unavailable.new", new Object[] { Integer.valueOf(1024), Integer.valueOf(65535) });
/*  26 */   private static final Component INVALID_PORT = (Component)Component.translatable("lanServer.port.invalid.new", new Object[] { Integer.valueOf(1024), Integer.valueOf(65535) });
/*     */   
/*     */   private static final int INVALID_PORT_COLOR = 16733525;
/*     */   private final Screen lastScreen;
/*  30 */   private GameType gameMode = GameType.SURVIVAL;
/*     */   private boolean commands;
/*  32 */   private int port = HttpUtil.getAvailablePort();
/*     */   @Nullable
/*     */   private EditBox portEdit;
/*     */   
/*     */   public ShareToLanScreen(Screen $$0) {
/*  37 */     super((Component)Component.translatable("lanServer.title"));
/*  38 */     this.lastScreen = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  43 */     IntegratedServer $$0 = this.minecraft.getSingleplayerServer();
/*  44 */     this.gameMode = $$0.getDefaultGameType();
/*  45 */     this.commands = $$0.getWorldData().getAllowCommands();
/*  46 */     addRenderableWidget(CycleButton.builder(GameType::getShortDisplayName)
/*  47 */         .withValues((Object[])new GameType[] { GameType.SURVIVAL, GameType.SPECTATOR, GameType.CREATIVE, GameType.ADVENTURE
/*  48 */           }).withInitialValue(this.gameMode)
/*  49 */         .create(this.width / 2 - 155, 100, 150, 20, GAME_MODE_LABEL, ($$0, $$1) -> this.gameMode = $$1));
/*     */     
/*  51 */     addRenderableWidget(CycleButton.onOffBuilder(this.commands).create(this.width / 2 + 5, 100, 150, 20, ALLOW_COMMANDS_LABEL, ($$0, $$1) -> this.commands = $$1.booleanValue()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     Button $$1 = Button.builder((Component)Component.translatable("lanServer.start"), $$1 -> { MutableComponent mutableComponent; this.minecraft.setScreen(null); if ($$0.publishServer(this.gameMode, this.commands, this.port)) { mutableComponent = PublishCommand.getSuccessMessage(this.port); } else { mutableComponent = Component.translatable("commands.publish.failed"); }  this.minecraft.gui.getChat().addMessage((Component)mutableComponent); this.minecraft.updateTitle(); }).bounds(this.width / 2 - 155, this.height - 28, 150, 20).build();
/*     */     
/*  67 */     this.portEdit = new EditBox(this.font, this.width / 2 - 75, 160, 150, 20, (Component)Component.translatable("lanServer.port"));
/*  68 */     this.portEdit.setResponder($$1 -> {
/*     */           Component $$2 = tryParsePort($$1);
/*     */           this.portEdit.setHint((Component)Component.literal("" + this.port).withStyle(ChatFormatting.DARK_GRAY));
/*     */           if ($$2 == null) {
/*     */             this.portEdit.setTextColor(14737632);
/*     */             this.portEdit.setTooltip(null);
/*     */             $$0.active = true;
/*     */           } else {
/*     */             this.portEdit.setTextColor(16733525);
/*     */             this.portEdit.setTooltip(Tooltip.create($$2));
/*     */             $$0.active = false;
/*     */           } 
/*     */         });
/*  81 */     this.portEdit.setHint((Component)Component.literal("" + this.port).withStyle(ChatFormatting.DARK_GRAY));
/*  82 */     addRenderableWidget(this.portEdit);
/*  83 */     addRenderableWidget($$1);
/*  84 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 + 5, this.height - 28, 150, 20).build());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Component tryParsePort(String $$0) {
/*  89 */     if ($$0.isBlank()) {
/*  90 */       this.port = HttpUtil.getAvailablePort();
/*  91 */       return null;
/*     */     } 
/*     */     try {
/*  94 */       this.port = Integer.parseInt($$0);
/*     */       
/*  96 */       if (this.port < 1024 || this.port > 65535)
/*  97 */         return INVALID_PORT; 
/*  98 */       if (!HttpUtil.isPortAvailable(this.port)) {
/*  99 */         return PORT_UNAVAILABLE;
/*     */       }
/* 101 */       return null;
/*     */     }
/* 103 */     catch (NumberFormatException $$1) {
/* 104 */       this.port = HttpUtil.getAvailablePort();
/* 105 */       return INVALID_PORT;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 112 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 114 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 50, 16777215);
/* 115 */     $$0.drawCenteredString(this.font, INFO_TEXT, this.width / 2, 82, 16777215);
/* 116 */     $$0.drawCenteredString(this.font, PORT_INFO_TEXT, this.width / 2, 142, 16777215);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ShareToLanScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */