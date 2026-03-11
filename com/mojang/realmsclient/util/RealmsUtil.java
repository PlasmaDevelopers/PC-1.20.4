/*    */ package com.mojang.realmsclient.util;
/*    */ 
/*    */ import com.mojang.authlib.yggdrasil.ProfileResult;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.PlayerFaceRenderer;
/*    */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*    */ import net.minecraft.client.resources.PlayerSkin;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class RealmsUtil
/*    */ {
/* 15 */   private static final Component RIGHT_NOW = (Component)Component.translatable("mco.util.time.now");
/*    */   
/*    */   private static final int MINUTES = 60;
/*    */   private static final int HOURS = 3600;
/*    */   private static final int DAYS = 86400;
/*    */   
/*    */   public static Component convertToAgePresentation(long $$0) {
/* 22 */     if ($$0 < 0L) {
/* 23 */       return RIGHT_NOW;
/*    */     }
/*    */     
/* 26 */     long $$1 = $$0 / 1000L;
/*    */     
/* 28 */     if ($$1 < 60L) {
/* 29 */       return (Component)Component.translatable("mco.time.secondsAgo", new Object[] { Long.valueOf($$1) });
/*    */     }
/*    */     
/* 32 */     if ($$1 < 3600L) {
/* 33 */       long $$2 = $$1 / 60L;
/* 34 */       return (Component)Component.translatable("mco.time.minutesAgo", new Object[] { Long.valueOf($$2) });
/*    */     } 
/*    */     
/* 37 */     if ($$1 < 86400L) {
/* 38 */       long $$3 = $$1 / 3600L;
/* 39 */       return (Component)Component.translatable("mco.time.hoursAgo", new Object[] { Long.valueOf($$3) });
/*    */     } 
/*    */     
/* 42 */     long $$4 = $$1 / 86400L;
/* 43 */     return (Component)Component.translatable("mco.time.daysAgo", new Object[] { Long.valueOf($$4) });
/*    */   }
/*    */   
/*    */   public static Component convertToAgePresentationFromInstant(Date $$0) {
/* 47 */     return convertToAgePresentation(System.currentTimeMillis() - $$0.getTime());
/*    */   }
/*    */   
/*    */   public static void renderPlayerFace(GuiGraphics $$0, int $$1, int $$2, int $$3, UUID $$4) {
/* 51 */     Minecraft $$5 = Minecraft.getInstance();
/* 52 */     ProfileResult $$6 = $$5.getMinecraftSessionService().fetchProfile($$4, false);
/* 53 */     PlayerSkin $$7 = ($$6 != null) ? $$5.getSkinManager().getInsecureSkin($$6.profile()) : DefaultPlayerSkin.get($$4);
/* 54 */     PlayerFaceRenderer.draw($$0, $$7.texture(), $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\RealmsUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */