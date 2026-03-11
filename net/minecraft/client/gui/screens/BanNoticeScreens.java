/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.mojang.authlib.minecraft.BanDetails;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.multiplayer.chat.report.BanReason;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class BanNoticeScreens
/*     */ {
/*  19 */   private static final Component TEMPORARY_BAN_TITLE = (Component)Component.translatable("gui.banned.title.temporary").withStyle(ChatFormatting.BOLD);
/*  20 */   private static final Component PERMANENT_BAN_TITLE = (Component)Component.translatable("gui.banned.title.permanent").withStyle(ChatFormatting.BOLD);
/*     */   
/*  22 */   public static final Component NAME_BAN_TITLE = (Component)Component.translatable("gui.banned.name.title").withStyle(ChatFormatting.BOLD);
/*     */   
/*  24 */   private static final Component SKIN_BAN_TITLE = (Component)Component.translatable("gui.banned.skin.title").withStyle(ChatFormatting.BOLD);
/*  25 */   private static final Component SKIN_BAN_DESCRIPTION = (Component)Component.translatable("gui.banned.skin.description", new Object[] { Component.literal("https://aka.ms/mcjavamoderation") });
/*     */   
/*     */   public static ConfirmLinkScreen create(BooleanConsumer $$0, BanDetails $$1) {
/*  28 */     return new ConfirmLinkScreen($$0, getBannedTitle($$1), getBannedScreenText($$1), "https://aka.ms/mcjavamoderation", CommonComponents.GUI_ACKNOWLEDGE, true);
/*     */   }
/*     */   
/*     */   public static ConfirmLinkScreen createSkinBan(Runnable $$0) {
/*  32 */     String $$1 = "https://aka.ms/mcjavamoderation";
/*  33 */     return new ConfirmLinkScreen($$1 -> { if ($$1) Util.getPlatform().openUri("https://aka.ms/mcjavamoderation");  $$0.run(); }SKIN_BAN_TITLE, SKIN_BAN_DESCRIPTION, "https://aka.ms/mcjavamoderation", CommonComponents.GUI_ACKNOWLEDGE, true);
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
/*     */   public static ConfirmLinkScreen createNameBan(String $$0, Runnable $$1) {
/*  49 */     String $$2 = "https://aka.ms/mcjavamoderation";
/*  50 */     return new ConfirmLinkScreen($$1 -> { if ($$1) Util.getPlatform().openUri("https://aka.ms/mcjavamoderation");  $$0.run(); }NAME_BAN_TITLE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  58 */         (Component)Component.translatable("gui.banned.name.description", new Object[] { Component.literal($$0).withStyle(ChatFormatting.YELLOW), "https://aka.ms/mcjavamoderation" }), "https://aka.ms/mcjavamoderation", CommonComponents.GUI_ACKNOWLEDGE, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Component getBannedTitle(BanDetails $$0) {
/*  66 */     return isTemporaryBan($$0) ? TEMPORARY_BAN_TITLE : PERMANENT_BAN_TITLE;
/*     */   }
/*     */   
/*     */   private static Component getBannedScreenText(BanDetails $$0) {
/*  70 */     return (Component)Component.translatable("gui.banned.description", new Object[] {
/*  71 */           getBanReasonText($$0), 
/*  72 */           getBanStatusText($$0), 
/*  73 */           Component.literal("https://aka.ms/mcjavamoderation")
/*     */         });
/*     */   }
/*     */   
/*     */   private static Component getBanReasonText(BanDetails $$0) {
/*  78 */     String $$1 = $$0.reason();
/*  79 */     String $$2 = $$0.reasonMessage();
/*  80 */     if (StringUtils.isNumeric($$1)) {
/*  81 */       MutableComponent mutableComponent; int $$3 = Integer.parseInt($$1);
/*  82 */       BanReason $$4 = BanReason.byId($$3);
/*     */       
/*  84 */       if ($$4 != null) {
/*  85 */         mutableComponent = ComponentUtils.mergeStyles($$4.title().copy(), Style.EMPTY.withBold(Boolean.valueOf(true)));
/*  86 */       } else if ($$2 != null) {
/*  87 */         mutableComponent = Component.translatable("gui.banned.description.reason_id_message", new Object[] { Integer.valueOf($$3), $$2 }).withStyle(ChatFormatting.BOLD);
/*     */       } else {
/*  89 */         mutableComponent = Component.translatable("gui.banned.description.reason_id", new Object[] { Integer.valueOf($$3) }).withStyle(ChatFormatting.BOLD);
/*     */       } 
/*  91 */       return (Component)Component.translatable("gui.banned.description.reason", new Object[] { mutableComponent });
/*     */     } 
/*  93 */     return (Component)Component.translatable("gui.banned.description.unknownreason");
/*     */   }
/*     */   
/*     */   private static Component getBanStatusText(BanDetails $$0) {
/*  97 */     if (isTemporaryBan($$0)) {
/*  98 */       Component $$1 = getBanDurationText($$0);
/*  99 */       return (Component)Component.translatable("gui.banned.description.temporary", new Object[] {
/* 100 */             Component.translatable("gui.banned.description.temporary.duration", new Object[] { $$1 }).withStyle(ChatFormatting.BOLD)
/*     */           });
/*     */     } 
/* 103 */     return (Component)Component.translatable("gui.banned.description.permanent").withStyle(ChatFormatting.BOLD);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Component getBanDurationText(BanDetails $$0) {
/* 108 */     Duration $$1 = Duration.between(Instant.now(), $$0.expires());
/* 109 */     long $$2 = $$1.toHours();
/* 110 */     if ($$2 > 72L)
/* 111 */       return (Component)CommonComponents.days($$1.toDays()); 
/* 112 */     if ($$2 < 1L) {
/* 113 */       return (Component)CommonComponents.minutes($$1.toMinutes());
/*     */     }
/* 115 */     return (Component)CommonComponents.hours($$1.toHours());
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isTemporaryBan(BanDetails $$0) {
/* 120 */     return ($$0.expires() != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\BanNoticeScreens.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */