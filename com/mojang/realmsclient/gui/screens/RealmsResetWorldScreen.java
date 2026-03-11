/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.WorldTemplate;
/*     */ import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.util.WorldGenerationInfo;
/*     */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*     */ import com.mojang.realmsclient.util.task.ResettingGeneratedWorldTask;
/*     */ import com.mojang.realmsclient.util.task.ResettingTemplateWorldTask;
/*     */ import com.mojang.realmsclient.util.task.SwitchSlotTask;
/*     */ import com.mojang.realmsclient.util.task.WorldCreationTask;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsResetWorldScreen extends RealmsScreen {
/*  35 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  37 */   static final ResourceLocation SLOT_FRAME_SPRITE = new ResourceLocation("widget/slot_frame");
/*     */   
/*  39 */   private static final Component CREATE_REALM_TITLE = (Component)Component.translatable("mco.selectServer.create");
/*  40 */   private static final Component CREATE_REALM_SUBTITLE = (Component)Component.translatable("mco.selectServer.create.subtitle");
/*     */   
/*  42 */   private static final Component CREATE_WORLD_TITLE = (Component)Component.translatable("mco.configure.world.switch.slot");
/*  43 */   private static final Component CREATE_WORLD_SUBTITLE = (Component)Component.translatable("mco.configure.world.switch.slot.subtitle");
/*     */   
/*  45 */   private static final Component RESET_WORLD_TITLE = (Component)Component.translatable("mco.reset.world.title");
/*  46 */   private static final Component RESET_WORLD_SUBTITLE = (Component)Component.translatable("mco.reset.world.warning");
/*     */   
/*  48 */   public static final Component CREATE_WORLD_RESET_TASK_TITLE = (Component)Component.translatable("mco.create.world.reset.title");
/*  49 */   private static final Component RESET_WORLD_RESET_TASK_TITLE = (Component)Component.translatable("mco.reset.world.resetting.screen.title");
/*     */   
/*  51 */   private static final Component WORLD_TEMPLATES_TITLE = (Component)Component.translatable("mco.reset.world.template");
/*  52 */   private static final Component ADVENTURES_TITLE = (Component)Component.translatable("mco.reset.world.adventure");
/*  53 */   private static final Component EXPERIENCES_TITLE = (Component)Component.translatable("mco.reset.world.experience");
/*  54 */   private static final Component INSPIRATION_TITLE = (Component)Component.translatable("mco.reset.world.inspiration");
/*     */   
/*     */   private final Screen lastScreen;
/*     */   
/*     */   private final RealmsServer serverData;
/*     */   private final Component subtitle;
/*     */   private final int subtitleColor;
/*     */   private final Component resetTaskTitle;
/*  62 */   private static final ResourceLocation UPLOAD_LOCATION = new ResourceLocation("textures/gui/realms/upload.png");
/*  63 */   private static final ResourceLocation ADVENTURE_MAP_LOCATION = new ResourceLocation("textures/gui/realms/adventure.png");
/*  64 */   private static final ResourceLocation SURVIVAL_SPAWN_LOCATION = new ResourceLocation("textures/gui/realms/survival_spawn.png");
/*  65 */   private static final ResourceLocation NEW_WORLD_LOCATION = new ResourceLocation("textures/gui/realms/new_world.png");
/*  66 */   private static final ResourceLocation EXPERIENCE_LOCATION = new ResourceLocation("textures/gui/realms/experience.png");
/*  67 */   private static final ResourceLocation INSPIRATION_LOCATION = new ResourceLocation("textures/gui/realms/inspiration.png");
/*     */   
/*     */   WorldTemplatePaginatedList templates;
/*     */   
/*     */   WorldTemplatePaginatedList adventuremaps;
/*     */   
/*     */   WorldTemplatePaginatedList experiences;
/*     */   WorldTemplatePaginatedList inspirations;
/*     */   public final int slot;
/*     */   @Nullable
/*     */   private final WorldCreationTask worldCreationTask;
/*     */   private final Runnable resetWorldRunnable;
/*  79 */   private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout((Screen)this);
/*     */   
/*     */   private RealmsResetWorldScreen(Screen $$0, RealmsServer $$1, int $$2, Component $$3, Component $$4, int $$5, Component $$6, Runnable $$7) {
/*  82 */     this($$0, $$1, $$2, $$3, $$4, $$5, $$6, (WorldCreationTask)null, $$7);
/*     */   }
/*     */   
/*     */   public RealmsResetWorldScreen(Screen $$0, RealmsServer $$1, int $$2, Component $$3, Component $$4, int $$5, Component $$6, @Nullable WorldCreationTask $$7, Runnable $$8) {
/*  86 */     super($$3);
/*  87 */     this.lastScreen = $$0;
/*  88 */     this.serverData = $$1;
/*  89 */     this.slot = $$2;
/*  90 */     this.subtitle = $$4;
/*  91 */     this.subtitleColor = $$5;
/*  92 */     this.resetTaskTitle = $$6;
/*  93 */     this.worldCreationTask = $$7;
/*  94 */     this.resetWorldRunnable = $$8;
/*     */   }
/*     */   
/*     */   public static RealmsResetWorldScreen forNewRealm(Screen $$0, RealmsServer $$1, WorldCreationTask $$2, Runnable $$3) {
/*  98 */     return new RealmsResetWorldScreen($$0, $$1, $$1.activeSlot, CREATE_REALM_TITLE, CREATE_REALM_SUBTITLE, -6250336, CREATE_WORLD_RESET_TASK_TITLE, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static RealmsResetWorldScreen forEmptySlot(Screen $$0, int $$1, RealmsServer $$2, Runnable $$3) {
/* 102 */     return new RealmsResetWorldScreen($$0, $$2, $$1, CREATE_WORLD_TITLE, CREATE_WORLD_SUBTITLE, -6250336, CREATE_WORLD_RESET_TASK_TITLE, $$3);
/*     */   }
/*     */   
/*     */   public static RealmsResetWorldScreen forResetSlot(Screen $$0, RealmsServer $$1, Runnable $$2) {
/* 106 */     return new RealmsResetWorldScreen($$0, $$1, $$1.activeSlot, RESET_WORLD_TITLE, RESET_WORLD_SUBTITLE, -65536, RESET_WORLD_RESET_TASK_TITLE, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 111 */     LinearLayout $$0 = LinearLayout.vertical();
/* 112 */     $$0.addChild((LayoutElement)new StringWidget(this.title, this.font), LayoutSettings::alignHorizontallyCenter);
/* 113 */     $$0.addChild((LayoutElement)SpacerElement.height(3));
/* 114 */     $$0.addChild((LayoutElement)(new StringWidget(this.subtitle, this.font)).setColor(this.subtitleColor), LayoutSettings::alignHorizontallyCenter);
/* 115 */     this.layout.addToHeader((LayoutElement)$$0);
/*     */     
/* 117 */     (new Thread("Realms-reset-world-fetcher")
/*     */       {
/*     */         public void run() {
/* 120 */           RealmsClient $$0 = RealmsClient.create();
/*     */           try {
/* 122 */             WorldTemplatePaginatedList $$1 = $$0.fetchWorldTemplates(1, 10, RealmsServer.WorldType.NORMAL);
/* 123 */             WorldTemplatePaginatedList $$2 = $$0.fetchWorldTemplates(1, 10, RealmsServer.WorldType.ADVENTUREMAP);
/* 124 */             WorldTemplatePaginatedList $$3 = $$0.fetchWorldTemplates(1, 10, RealmsServer.WorldType.EXPERIENCE);
/* 125 */             WorldTemplatePaginatedList $$4 = $$0.fetchWorldTemplates(1, 10, RealmsServer.WorldType.INSPIRATION);
/* 126 */             RealmsResetWorldScreen.this.minecraft.execute(() -> {
/*     */                   RealmsResetWorldScreen.this.templates = $$0;
/*     */                   RealmsResetWorldScreen.this.adventuremaps = $$1;
/*     */                   RealmsResetWorldScreen.this.experiences = $$2;
/*     */                   RealmsResetWorldScreen.this.inspirations = $$3;
/*     */                 });
/* 132 */           } catch (RealmsServiceException $$5) {
/* 133 */             RealmsResetWorldScreen.LOGGER.error("Couldn't fetch templates in reset world", (Throwable)$$5);
/*     */           } 
/*     */         }
/* 136 */       }).start();
/*     */     
/* 138 */     addRenderableWidget((GuiEventListener)new FrameButton(frame(1), row(0) + 10, RealmsResetNormalWorldScreen.TITLE, NEW_WORLD_LOCATION, $$0 -> this.minecraft.setScreen((Screen)new RealmsResetNormalWorldScreen(this::generationSelectionCallback, this.title))));
/*     */ 
/*     */     
/* 141 */     addRenderableWidget((GuiEventListener)new FrameButton(frame(2), row(0) + 10, RealmsSelectFileToUploadScreen.TITLE, UPLOAD_LOCATION, $$0 -> this.minecraft.setScreen((Screen)new RealmsSelectFileToUploadScreen(this.serverData.id, this.slot, this))));
/*     */ 
/*     */     
/* 144 */     addRenderableWidget((GuiEventListener)new FrameButton(frame(3), row(0) + 10, WORLD_TEMPLATES_TITLE, SURVIVAL_SPAWN_LOCATION, $$0 -> this.minecraft.setScreen((Screen)new RealmsSelectWorldTemplateScreen(WORLD_TEMPLATES_TITLE, this::templateSelectionCallback, RealmsServer.WorldType.NORMAL, this.templates))));
/*     */ 
/*     */     
/* 147 */     addRenderableWidget((GuiEventListener)new FrameButton(frame(1), row(6) + 20, ADVENTURES_TITLE, ADVENTURE_MAP_LOCATION, $$0 -> this.minecraft.setScreen((Screen)new RealmsSelectWorldTemplateScreen(ADVENTURES_TITLE, this::templateSelectionCallback, RealmsServer.WorldType.ADVENTUREMAP, this.adventuremaps))));
/*     */ 
/*     */     
/* 150 */     addRenderableWidget((GuiEventListener)new FrameButton(frame(2), row(6) + 20, EXPERIENCES_TITLE, EXPERIENCE_LOCATION, $$0 -> this.minecraft.setScreen((Screen)new RealmsSelectWorldTemplateScreen(EXPERIENCES_TITLE, this::templateSelectionCallback, RealmsServer.WorldType.EXPERIENCE, this.experiences))));
/*     */ 
/*     */     
/* 153 */     addRenderableWidget((GuiEventListener)new FrameButton(frame(3), row(6) + 20, INSPIRATION_TITLE, INSPIRATION_LOCATION, $$0 -> this.minecraft.setScreen((Screen)new RealmsSelectWorldTemplateScreen(INSPIRATION_TITLE, this::templateSelectionCallback, RealmsServer.WorldType.INSPIRATION, this.inspirations))));
/*     */ 
/*     */ 
/*     */     
/* 157 */     this.layout.addToFooter((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).build());
/*     */     
/* 159 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/* 160 */     this.layout.arrangeElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/* 165 */     return (Component)CommonComponents.joinForNarration(new Component[] { getTitle(), this.subtitle });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 170 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */   
/*     */   private int frame(int $$0) {
/* 174 */     return this.width / 2 - 130 + ($$0 - 1) * 100;
/*     */   }
/*     */   
/*     */   private void templateSelectionCallback(@Nullable WorldTemplate $$0) {
/* 178 */     this.minecraft.setScreen((Screen)this);
/* 179 */     if ($$0 != null) {
/* 180 */       runResetTasks((LongRunningTask)new ResettingTemplateWorldTask($$0, this.serverData.id, this.resetTaskTitle, this.resetWorldRunnable));
/*     */     }
/*     */   }
/*     */   
/*     */   private void generationSelectionCallback(@Nullable WorldGenerationInfo $$0) {
/* 185 */     this.minecraft.setScreen((Screen)this);
/* 186 */     if ($$0 != null) {
/* 187 */       runResetTasks((LongRunningTask)new ResettingGeneratedWorldTask($$0, this.serverData.id, this.resetTaskTitle, this.resetWorldRunnable));
/*     */     }
/*     */   }
/*     */   
/*     */   private void runResetTasks(LongRunningTask $$0) {
/* 192 */     List<LongRunningTask> $$1 = new ArrayList<>();
/* 193 */     if (this.worldCreationTask != null) {
/* 194 */       $$1.add(this.worldCreationTask);
/*     */     }
/* 196 */     if (this.slot != this.serverData.activeSlot)
/* 197 */       $$1.add(new SwitchSlotTask(this.serverData.id, this.slot, () -> {
/*     */             
/* 199 */             }));  $$1.add($$0);
/* 200 */     this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen(this.lastScreen, $$1.<LongRunningTask>toArray(new LongRunningTask[0])));
/*     */   }
/*     */   
/*     */   public void switchSlot(Runnable $$0) {
/* 204 */     this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen(this.lastScreen, new LongRunningTask[] { (LongRunningTask)new SwitchSlotTask(this.serverData.id, this.slot, () -> this.minecraft.execute($$0)) }));
/*     */   }
/*     */   
/*     */   private class FrameButton extends Button {
/*     */     private static final int WIDTH = 60;
/*     */     private static final int HEIGHT = 72;
/*     */     private static final int IMAGE_SIZE = 56;
/*     */     private final ResourceLocation image;
/*     */     
/*     */     FrameButton(int $$0, int $$1, Component $$2, ResourceLocation $$3, Button.OnPress $$4) {
/* 214 */       super($$0, $$1, 60, 72, $$2, $$4, DEFAULT_NARRATION);
/* 215 */       this.image = $$3;
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 220 */       boolean $$4 = isHoveredOrFocused();
/* 221 */       if ($$4) {
/* 222 */         $$0.setColor(0.56F, 0.56F, 0.56F, 1.0F);
/*     */       }
/* 224 */       int $$5 = getX();
/* 225 */       int $$6 = getY();
/* 226 */       $$0.blit(this.image, $$5 + 2, $$6 + 14, 0.0F, 0.0F, 56, 56, 56, 56);
/* 227 */       $$0.blitSprite(RealmsResetWorldScreen.SLOT_FRAME_SPRITE, $$5, $$6 + 12, 60, 60);
/* 228 */       $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 230 */       int $$7 = $$4 ? -6250336 : -1;
/* 231 */       $$0.drawCenteredString(RealmsResetWorldScreen.this.font, getMessage(), $$5 + 30, $$6, $$7);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsResetWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */