/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.util.WorldGenerationInfo;
/*     */ import com.mojang.realmsclient.util.task.CreateSnapshotRealmTask;
/*     */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*     */ import com.mojang.realmsclient.util.task.WorldCreationTask;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.CommonLayouts;
/*     */ import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class RealmsCreateRealmScreen extends RealmsScreen {
/*  20 */   private static final Component CREATE_REALM_TEXT = (Component)Component.translatable("mco.selectServer.create");
/*  21 */   private static final Component NAME_LABEL = (Component)Component.translatable("mco.configure.world.name");
/*  22 */   private static final Component DESCRIPTION_LABEL = (Component)Component.translatable("mco.configure.world.description");
/*     */   
/*     */   private static final int BUTTON_SPACING = 10;
/*     */   
/*     */   private static final int CONTENT_WIDTH = 210;
/*     */   
/*     */   private final RealmsMainScreen lastScreen;
/*  29 */   private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout((Screen)this);
/*     */   
/*     */   private EditBox nameBox;
/*     */   private EditBox descriptionBox;
/*     */   private final Runnable createWorldRunnable;
/*     */   
/*     */   public RealmsCreateRealmScreen(RealmsMainScreen $$0, RealmsServer $$1) {
/*  36 */     super(CREATE_REALM_TEXT);
/*  37 */     this.lastScreen = $$0;
/*  38 */     this.createWorldRunnable = (() -> createWorld($$0));
/*     */   }
/*     */   
/*     */   public RealmsCreateRealmScreen(RealmsMainScreen $$0, long $$1) {
/*  42 */     super(CREATE_REALM_TEXT);
/*  43 */     this.lastScreen = $$0;
/*  44 */     this.createWorldRunnable = (() -> createSnapshotWorld($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  49 */     this.layout.addToHeader((LayoutElement)new StringWidget(this.title, this.font));
/*     */     
/*  51 */     LinearLayout $$0 = ((LinearLayout)this.layout.addToContents((LayoutElement)LinearLayout.vertical())).spacing(10);
/*     */     
/*  53 */     Button $$1 = Button.builder(CommonComponents.GUI_CONTINUE, $$0 -> this.createWorldRunnable.run()).build();
/*  54 */     $$1.active = false;
/*     */     
/*  56 */     this.nameBox = new EditBox(this.font, 210, 20, NAME_LABEL);
/*  57 */     this.nameBox.setResponder($$1 -> $$0.active = !Util.isBlank($$1));
/*  58 */     this.descriptionBox = new EditBox(this.font, 210, 20, DESCRIPTION_LABEL);
/*     */     
/*  60 */     $$0.addChild((LayoutElement)CommonLayouts.labeledElement(this.font, (LayoutElement)this.nameBox, NAME_LABEL));
/*  61 */     $$0.addChild((LayoutElement)CommonLayouts.labeledElement(this.font, (LayoutElement)this.descriptionBox, DESCRIPTION_LABEL));
/*     */     
/*  63 */     LinearLayout $$2 = (LinearLayout)this.layout.addToFooter((LayoutElement)LinearLayout.horizontal().spacing(10));
/*  64 */     $$2.addChild((LayoutElement)$$1);
/*  65 */     $$2.addChild((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).build());
/*     */     
/*  67 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  68 */     repositionElements();
/*     */     
/*  70 */     setInitialFocus((GuiEventListener)this.nameBox);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  75 */     this.layout.arrangeElements();
/*     */   }
/*     */   
/*     */   private void createWorld(RealmsServer $$0) {
/*  79 */     WorldCreationTask $$1 = new WorldCreationTask($$0.id, this.nameBox.getValue(), this.descriptionBox.getValue());
/*  80 */     RealmsResetWorldScreen $$2 = RealmsResetWorldScreen.forNewRealm((Screen)this, $$0, $$1, () -> this.minecraft.execute(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     this.minecraft.setScreen((Screen)$$2);
/*     */   }
/*     */   
/*     */   private void createSnapshotWorld(long $$0) {
/*  91 */     RealmsResetNormalWorldScreen realmsResetNormalWorldScreen = new RealmsResetNormalWorldScreen($$1 -> { if ($$1 == null) { this.minecraft.setScreen((Screen)this); return; }  this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen((Screen)this, new LongRunningTask[] { (LongRunningTask)new CreateSnapshotRealmTask(this.lastScreen, $$0, $$1, this.nameBox.getValue(), this.descriptionBox.getValue()) })); }CREATE_REALM_TEXT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.minecraft.setScreen((Screen)realmsResetNormalWorldScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 105 */     this.minecraft.setScreen((Screen)this.lastScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsCreateRealmScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */