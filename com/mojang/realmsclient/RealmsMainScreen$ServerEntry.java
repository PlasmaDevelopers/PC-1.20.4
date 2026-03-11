/*      */ package com.mojang.realmsclient;
/*      */ 
/*      */ import com.mojang.realmsclient.dto.RealmsServer;
/*      */ import com.mojang.realmsclient.gui.screens.RealmsCreateRealmScreen;
/*      */ import com.mojang.realmsclient.util.RealmsUtil;
/*      */ import java.util.Objects;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ChatFormatting;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.gui.GuiGraphics;
/*      */ import net.minecraft.client.gui.components.Tooltip;
/*      */ import net.minecraft.client.gui.navigation.CommonInputs;
/*      */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*      */ import net.minecraft.client.gui.screens.Screen;
/*      */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SoundInstance;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.network.chat.CommonComponents;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ServerEntry
/*      */   extends RealmsMainScreen.Entry
/*      */ {
/*      */   private static final int SKIN_HEAD_LARGE_WIDTH = 36;
/*      */   private final RealmsServer serverData;
/*      */   @Nullable
/*      */   private final Tooltip tooltip;
/*      */   
/*      */   public ServerEntry(RealmsServer $$0) {
/* 1058 */     this.serverData = $$0;
/* 1059 */     boolean $$1 = paramRealmsMainScreen.isSelfOwnedServer($$0);
/* 1060 */     if (RealmsMainScreen.isSnapshot() && $$1 && $$0.isSnapshotRealm()) {
/* 1061 */       this.tooltip = Tooltip.create((Component)Component.translatable("mco.snapshot.paired", new Object[] { $$0.parentWorldName }));
/* 1062 */     } else if (!$$1 && $$0.needsUpgrade()) {
/* 1063 */       this.tooltip = Tooltip.create((Component)Component.translatable("mco.snapshot.friendsRealm.upgrade", new Object[] { $$0.owner }));
/* 1064 */     } else if (!$$1 && $$0.needsDowngrade()) {
/* 1065 */       this.tooltip = Tooltip.create((Component)Component.translatable("mco.snapshot.friendsRealm.downgrade", new Object[] { $$0.activeVersion }));
/*      */     } else {
/* 1067 */       this.tooltip = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 1073 */     if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
/* 1074 */       $$0.blitSprite(RealmsMainScreen.NEW_REALM_SPRITE, $$3 - 5, $$2 + $$5 / 2 - 10, 40, 20);
/*      */       
/* 1076 */       Objects.requireNonNull(RealmsMainScreen.access$2300(RealmsMainScreen.this)); int $$10 = $$2 + $$5 / 2 - 9 / 2;
/* 1077 */       $$0.drawString(RealmsMainScreen.access$2400(RealmsMainScreen.this), RealmsMainScreen.SERVER_UNITIALIZED_TEXT, $$3 + 40 - 2, $$10, 8388479);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1082 */     RealmsUtil.renderPlayerFace($$0, $$3, $$2, 32, this.serverData.ownerUUID);
/*      */     
/* 1084 */     renderFirstLine($$0, $$2, $$3, $$4);
/* 1085 */     renderSecondLine($$0, $$2, $$3);
/* 1086 */     renderThirdLine($$0, $$2, $$3, this.serverData);
/*      */     
/* 1088 */     renderStatusLights(this.serverData, $$0, $$3 + $$4, $$2, $$6, $$7);
/*      */     
/* 1090 */     if (this.tooltip != null) {
/* 1091 */       this.tooltip.refreshTooltipForNextRenderPass($$8, isFocused(), new ScreenRectangle($$3, $$2, $$4, $$5));
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderFirstLine(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 1096 */     int $$4 = textX($$2);
/* 1097 */     int $$5 = firstLineY($$1);
/*      */     
/* 1099 */     Component $$6 = RealmsMainScreen.getVersionComponent(this.serverData.activeVersion, this.serverData.isCompatible());
/* 1100 */     int $$7 = versionTextX($$2, $$3, $$6);
/* 1101 */     renderClampedName($$0, this.serverData.getName(), $$4, $$5, $$7, -1);
/* 1102 */     if ($$6 != CommonComponents.EMPTY) {
/* 1103 */       $$0.drawString(RealmsMainScreen.access$2500(RealmsMainScreen.this), $$6, $$7, $$5, -8355712, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderSecondLine(GuiGraphics $$0, int $$1, int $$2) {
/* 1108 */     int $$3 = textX($$2);
/* 1109 */     int $$4 = firstLineY($$1);
/* 1110 */     int $$5 = secondLineY($$4);
/*      */     
/* 1112 */     if (this.serverData.worldType == RealmsServer.WorldType.MINIGAME) {
/* 1113 */       MutableComponent mutableComponent = Component.literal(this.serverData.getMinigameName()).withStyle(ChatFormatting.GRAY);
/* 1114 */       $$0.drawString(RealmsMainScreen.access$2600(RealmsMainScreen.this), (Component)Component.translatable("mco.selectServer.minigameName", new Object[] { mutableComponent }).withColor(-171), $$3, $$5, -1, false);
/*      */     } else {
/* 1116 */       $$0.drawString(RealmsMainScreen.access$2700(RealmsMainScreen.this), this.serverData.getDescription(), $$3, secondLineY($$4), -8355712, false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void playRealm() {
/* 1121 */     RealmsMainScreen.access$2800(RealmsMainScreen.this).getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 1122 */     RealmsMainScreen.play(this.serverData, (Screen)RealmsMainScreen.this);
/*      */   }
/*      */   
/*      */   private void createUnitializedRealm() {
/* 1126 */     RealmsMainScreen.access$2900(RealmsMainScreen.this).getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 1127 */     RealmsCreateRealmScreen $$0 = new RealmsCreateRealmScreen(RealmsMainScreen.this, this.serverData);
/* 1128 */     RealmsMainScreen.access$3000(RealmsMainScreen.this).setScreen((Screen)$$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 1133 */     if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
/* 1134 */       createUnitializedRealm();
/* 1135 */     } else if (RealmsMainScreen.this.shouldPlayButtonBeActive(this.serverData)) {
/* 1136 */       if (Util.getMillis() - RealmsMainScreen.this.lastClickTime < 250L && isFocused()) {
/* 1137 */         playRealm();
/*      */       }
/* 1139 */       RealmsMainScreen.this.lastClickTime = Util.getMillis();
/*      */     } 
/* 1141 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 1146 */     if (CommonInputs.selected($$0)) {
/* 1147 */       if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
/* 1148 */         createUnitializedRealm();
/* 1149 */         return true;
/* 1150 */       }  if (RealmsMainScreen.this.shouldPlayButtonBeActive(this.serverData)) {
/* 1151 */         playRealm();
/* 1152 */         return true;
/*      */       } 
/*      */     } 
/* 1155 */     return super.keyPressed($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public Component getNarration() {
/* 1160 */     if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
/* 1161 */       return RealmsMainScreen.UNITIALIZED_WORLD_NARRATION;
/*      */     }
/* 1163 */     return (Component)Component.translatable("narrator.select", new Object[] { this.serverData.name });
/*      */   }
/*      */ 
/*      */   
/*      */   public RealmsServer getServer() {
/* 1168 */     return this.serverData;
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\RealmsMainScreen$ServerEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */