/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.platform.Monitor;
/*     */ import com.mojang.blaze3d.platform.VideoMode;
/*     */ import com.mojang.blaze3d.platform.Window;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.GraphicsStatus;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.OptionsList;
/*     */ import net.minecraft.client.renderer.GpuWarnlistManager;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class VideoSettingsScreen extends OptionsSubScreen {
/*  24 */   private static final Component FABULOUS = (Component)Component.translatable("options.graphics.fabulous").withStyle(ChatFormatting.ITALIC);
/*  25 */   private static final Component WARNING_MESSAGE = (Component)Component.translatable("options.graphics.warning.message", new Object[] { FABULOUS, FABULOUS });
/*  26 */   private static final Component WARNING_TITLE = (Component)Component.translatable("options.graphics.warning.title").withStyle(ChatFormatting.RED);
/*  27 */   private static final Component BUTTON_ACCEPT = (Component)Component.translatable("options.graphics.warning.accept");
/*  28 */   private static final Component BUTTON_CANCEL = (Component)Component.translatable("options.graphics.warning.cancel"); private OptionsList list; private final GpuWarnlistManager gpuWarnlistManager; private final int oldMipmaps;
/*     */   
/*     */   private static OptionInstance<?>[] options(Options $$0) {
/*  31 */     return (OptionInstance<?>[])new OptionInstance[] { $$0
/*  32 */         .graphicsMode(), $$0.renderDistance(), $$0
/*  33 */         .prioritizeChunkUpdates(), $$0.simulationDistance(), $$0
/*  34 */         .ambientOcclusion(), $$0.framerateLimit(), $$0
/*  35 */         .enableVsync(), $$0.bobView(), $$0
/*  36 */         .guiScale(), $$0.attackIndicator(), $$0
/*  37 */         .gamma(), $$0.cloudStatus(), $$0
/*  38 */         .fullscreen(), $$0.particles(), $$0
/*  39 */         .mipmapLevels(), $$0.entityShadows(), $$0
/*  40 */         .screenEffectScale(), $$0.entityDistanceScaling(), $$0
/*  41 */         .fovEffectScale(), $$0.showAutosaveIndicator(), $$0
/*  42 */         .glintSpeed(), $$0.glintStrength() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VideoSettingsScreen(Screen $$0, Options $$1) {
/*  52 */     super($$0, $$1, (Component)Component.translatable("options.videoTitle"));
/*  53 */     this.gpuWarnlistManager = $$0.minecraft.getGpuWarnlistManager();
/*     */     
/*  55 */     this.gpuWarnlistManager.resetWarnings();
/*  56 */     if ($$1.graphicsMode().get() == GraphicsStatus.FABULOUS)
/*     */     {
/*  58 */       this.gpuWarnlistManager.dismissWarning();
/*     */     }
/*     */     
/*  61 */     this.oldMipmaps = ((Integer)$$1.mipmapLevels().get()).intValue();
/*     */   }
/*     */   
/*     */   protected void init() {
/*     */     int $$5;
/*  66 */     this.list = addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height - 64, 32, 25));
/*     */     
/*  68 */     int $$0 = -1;
/*     */     
/*  70 */     Window $$1 = this.minecraft.getWindow();
/*  71 */     Monitor $$2 = $$1.findBestMonitor();
/*     */     
/*  73 */     if ($$2 == null) {
/*  74 */       int $$3 = -1;
/*     */     } else {
/*  76 */       Optional<VideoMode> $$4 = $$1.getPreferredFullscreenVideoMode();
/*  77 */       Objects.requireNonNull($$2); $$5 = ((Integer)$$4.<Integer>map($$2::getVideoModeIndex).orElse(Integer.valueOf(-1))).intValue();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     OptionInstance<Integer> $$6 = new OptionInstance("options.fullscreen.resolution", OptionInstance.noTooltip(), ($$1, $$2) -> { if ($$0 == null) return (Component)Component.translatable("options.fullscreen.unavailable");  if ($$2.intValue() == -1) return Options.genericValueLabel($$1, (Component)Component.translatable("options.fullscreen.current"));  VideoMode $$3 = $$0.getMode($$2.intValue()); return Options.genericValueLabel($$1, (Component)Component.translatable("options.fullscreen.entry", new Object[] { Integer.valueOf($$3.getWidth()), Integer.valueOf($$3.getHeight()), Integer.valueOf($$3.getRefreshRate()), Integer.valueOf($$3.getRedBits() + $$3.getGreenBits() + $$3.getBlueBits()) })); }(OptionInstance.ValueSet)new OptionInstance.IntRange(-1, ($$2 != null) ? ($$2.getModeCount() - 1) : -1), Integer.valueOf($$5), $$2 -> {
/*     */           if ($$0 == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*     */           $$1.setPreferredFullscreenVideoMode(($$2.intValue() == -1) ? Optional.empty() : Optional.<VideoMode>of($$0.getMode($$2.intValue())));
/*     */         });
/*     */     
/* 104 */     this.list.addBig($$6);
/* 105 */     this.list.addBig(this.options.biomeBlendRadius());
/* 106 */     this.list.addSmall((OptionInstance[])options(this.options));
/*     */     
/* 108 */     addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$1 -> {
/*     */             this.minecraft.options.save();
/*     */             $$0.changeFullscreenVideoMode();
/*     */             this.minecraft.setScreen(this.lastScreen);
/* 112 */           }).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/* 117 */     if (((Integer)this.options.mipmapLevels().get()).intValue() != this.oldMipmaps) {
/* 118 */       this.minecraft.updateMaxMipLevel(((Integer)this.options.mipmapLevels().get()).intValue());
/* 119 */       this.minecraft.delayTextureReload();
/*     */     } 
/* 121 */     super.removed();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 126 */     int $$3 = ((Integer)this.options.guiScale().get()).intValue();
/*     */     
/* 128 */     if (super.mouseClicked($$0, $$1, $$2)) {
/* 129 */       if (((Integer)this.options.guiScale().get()).intValue() != $$3) {
/* 130 */         this.minecraft.resizeDisplay();
/*     */       }
/*     */       
/* 133 */       if (this.gpuWarnlistManager.isShowingWarning()) {
/*     */         
/* 135 */         List<Component> $$4 = Lists.newArrayList((Object[])new Component[] { WARNING_MESSAGE, CommonComponents.NEW_LINE });
/*     */         
/* 137 */         String $$5 = this.gpuWarnlistManager.getRendererWarnings();
/* 138 */         if ($$5 != null) {
/* 139 */           $$4.add(CommonComponents.NEW_LINE);
/* 140 */           $$4.add(Component.translatable("options.graphics.warning.renderer", new Object[] { $$5 }).withStyle(ChatFormatting.GRAY));
/*     */         } 
/*     */         
/* 143 */         String $$6 = this.gpuWarnlistManager.getVendorWarnings();
/* 144 */         if ($$6 != null) {
/* 145 */           $$4.add(CommonComponents.NEW_LINE);
/* 146 */           $$4.add(Component.translatable("options.graphics.warning.vendor", new Object[] { $$6 }).withStyle(ChatFormatting.GRAY));
/*     */         } 
/*     */         
/* 149 */         String $$7 = this.gpuWarnlistManager.getVersionWarnings();
/* 150 */         if ($$7 != null) {
/* 151 */           $$4.add(CommonComponents.NEW_LINE);
/* 152 */           $$4.add(Component.translatable("options.graphics.warning.version", new Object[] { $$7 }).withStyle(ChatFormatting.GRAY));
/*     */         } 
/*     */         
/* 155 */         this.minecraft.setScreen(new UnsupportedGraphicsWarningScreen(WARNING_TITLE, $$4, ImmutableList.of(new UnsupportedGraphicsWarningScreen.ButtonOption(BUTTON_ACCEPT, $$0 -> { this.options.graphicsMode().set(GraphicsStatus.FABULOUS); (Minecraft.getInstance()).levelRenderer.allChanged(); this.gpuWarnlistManager.dismissWarning(); this.minecraft.setScreen(this); }), new UnsupportedGraphicsWarningScreen.ButtonOption(BUTTON_CANCEL, $$0 -> {
/*     */                     this.gpuWarnlistManager.dismissWarningAndSkipFabulous();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     this.minecraft.setScreen(this);
/*     */                   }))));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       return true;
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 178 */     if (Screen.hasControlDown()) {
/* 179 */       OptionInstance<Integer> $$4 = this.options.guiScale();
/* 180 */       int $$5 = ((Integer)$$4.get()).intValue() + (int)Math.signum($$3);
/* 181 */       if ($$5 != 0) {
/* 182 */         $$4.set(Integer.valueOf($$5));
/* 183 */         if (((Integer)$$4.get()).intValue() == $$5) {
/* 184 */           this.minecraft.resizeDisplay();
/* 185 */           return true;
/*     */         } 
/*     */       } 
/* 188 */       return false;
/*     */     } 
/* 190 */     return super.mouseScrolled($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 196 */     super.render($$0, $$1, $$2, $$3);
/* 197 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 202 */     renderDirtBackground($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\VideoSettingsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */