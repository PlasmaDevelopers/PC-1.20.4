/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*     */ import java.time.Duration;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.LoadingDotsWidget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.realms.RepeatedNarrator;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsLongRunningMcoTaskScreen extends RealmsScreen {
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  26 */   private static final RepeatedNarrator REPEATED_NARRATOR = new RepeatedNarrator(Duration.ofSeconds(5L));
/*     */   
/*     */   private final List<LongRunningTask> queuedTasks;
/*     */   
/*     */   private final Screen lastScreen;
/*  31 */   private final LinearLayout layout = LinearLayout.vertical();
/*     */   
/*     */   private volatile Component title;
/*     */   @Nullable
/*     */   private LoadingDotsWidget loadingDotsWidget;
/*     */   
/*     */   public RealmsLongRunningMcoTaskScreen(Screen $$0, LongRunningTask... $$1) {
/*  38 */     super(GameNarrator.NO_TITLE);
/*  39 */     this.lastScreen = $$0;
/*  40 */     this.queuedTasks = List.of($$1);
/*  41 */     if (this.queuedTasks.isEmpty()) {
/*  42 */       throw new IllegalArgumentException("No tasks added");
/*     */     }
/*     */     
/*  45 */     this.title = ((LongRunningTask)this.queuedTasks.get(0)).getTitle();
/*  46 */     Runnable $$2 = () -> {
/*     */         LongRunningTask[] arrayOfLongRunningTask = $$0; int i = arrayOfLongRunningTask.length; byte b = 0;
/*     */         while (b < i) {
/*     */           LongRunningTask $$1 = arrayOfLongRunningTask[b];
/*     */           setTitle($$1.getTitle());
/*     */           if (!$$1.aborted()) {
/*     */             $$1.run();
/*     */             b++;
/*     */           } 
/*     */         } 
/*     */       };
/*  57 */     Thread $$3 = new Thread($$2, "Realms-long-running-task");
/*  58 */     $$3.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new RealmsDefaultUncaughtExceptionHandler(LOGGER));
/*  59 */     $$3.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  64 */     super.tick();
/*     */     
/*  66 */     if (this.loadingDotsWidget != null) {
/*  67 */       REPEATED_NARRATOR.narrate(this.minecraft.getNarrator(), this.loadingDotsWidget.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  73 */     if ($$0 == 256) {
/*  74 */       cancel();
/*  75 */       return true;
/*     */     } 
/*  77 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  82 */     this.layout.defaultCellSetting().alignHorizontallyCenter();
/*  83 */     this.loadingDotsWidget = new LoadingDotsWidget(this.font, this.title);
/*  84 */     this.layout.addChild((LayoutElement)this.loadingDotsWidget, $$0 -> $$0.paddingBottom(30));
/*  85 */     this.layout.addChild((LayoutElement)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> cancel()).build());
/*  86 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  87 */     repositionElements();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  92 */     this.layout.arrangeElements();
/*  93 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*     */   }
/*     */   
/*     */   protected void cancel() {
/*  97 */     for (LongRunningTask $$0 : this.queuedTasks) {
/*  98 */       $$0.abortTask();
/*     */     }
/* 100 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */   
/*     */   public void setTitle(Component $$0) {
/* 104 */     if (this.loadingDotsWidget != null) {
/* 105 */       this.loadingDotsWidget.setMessage($$0);
/*     */     }
/* 107 */     this.title = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsLongRunningMcoTaskScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */