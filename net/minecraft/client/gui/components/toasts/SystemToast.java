/*     */ package net.minecraft.client.gui.components.toasts;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ 
/*     */ public class SystemToast implements Toast {
/*  15 */   private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("toast/system");
/*     */   
/*     */   private static final int MAX_LINE_SIZE = 200;
/*     */   private static final int LINE_SPACING = 12;
/*     */   private static final int MARGIN = 10;
/*     */   private final SystemToastId id;
/*     */   private Component title;
/*     */   private List<FormattedCharSequence> messageLines;
/*     */   private long lastChanged;
/*     */   private boolean changed;
/*     */   private final int width;
/*     */   private boolean forceHide;
/*     */   
/*     */   public SystemToast(SystemToastId $$0, Component $$1, @Nullable Component $$2) {
/*  29 */     this($$0, $$1, (List<FormattedCharSequence>)nullToEmpty($$2), Math.max(160, 30 + Math.max(
/*  30 */             (Minecraft.getInstance()).font.width((FormattedText)$$1), 
/*  31 */             ($$2 == null) ? 0 : (Minecraft.getInstance()).font.width((FormattedText)$$2))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static SystemToast multiline(Minecraft $$0, SystemToastId $$1, Component $$2, Component $$3) {
/*  36 */     Font $$4 = $$0.font;
/*  37 */     List<FormattedCharSequence> $$5 = $$4.split((FormattedText)$$3, 200);
/*  38 */     Objects.requireNonNull($$4); int $$6 = Math.max(200, $$5.stream().mapToInt($$4::width).max().orElse(200));
/*  39 */     return new SystemToast($$1, $$2, $$5, $$6 + 30);
/*     */   }
/*     */   
/*     */   private SystemToast(SystemToastId $$0, Component $$1, List<FormattedCharSequence> $$2, int $$3) {
/*  43 */     this.id = $$0;
/*  44 */     this.title = $$1;
/*  45 */     this.messageLines = $$2;
/*  46 */     this.width = $$3;
/*     */   }
/*     */   
/*     */   private static ImmutableList<FormattedCharSequence> nullToEmpty(@Nullable Component $$0) {
/*  50 */     return ($$0 == null) ? ImmutableList.of() : ImmutableList.of($$0.getVisualOrderText());
/*     */   }
/*     */ 
/*     */   
/*     */   public int width() {
/*  55 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int height() {
/*  60 */     return 20 + Math.max(this.messageLines.size(), 1) * 12;
/*     */   }
/*     */   
/*     */   public void forceHide() {
/*  64 */     this.forceHide = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Toast.Visibility render(GuiGraphics $$0, ToastComponent $$1, long $$2) {
/*  69 */     if (this.changed) {
/*  70 */       this.lastChanged = $$2;
/*  71 */       this.changed = false;
/*     */     } 
/*     */     
/*  74 */     int $$3 = width();
/*  75 */     if ($$3 == 160 && this.messageLines.size() <= 1) {
/*  76 */       $$0.blitSprite(BACKGROUND_SPRITE, 0, 0, $$3, height());
/*     */     } else {
/*  78 */       int $$4 = height();
/*     */       
/*  80 */       int $$5 = 28;
/*  81 */       int $$6 = Math.min(4, $$4 - 28);
/*  82 */       renderBackgroundRow($$0, $$3, 0, 0, 28);
/*     */       
/*  84 */       for (int $$7 = 28; $$7 < $$4 - $$6; $$7 += 10) {
/*  85 */         renderBackgroundRow($$0, $$3, 16, $$7, Math.min(16, $$4 - $$7 - $$6));
/*     */       }
/*     */       
/*  88 */       renderBackgroundRow($$0, $$3, 32 - $$6, $$4 - $$6, $$6);
/*     */     } 
/*     */     
/*  91 */     if (this.messageLines.isEmpty()) {
/*  92 */       $$0.drawString(($$1.getMinecraft()).font, this.title, 18, 12, -256, false);
/*     */     } else {
/*  94 */       $$0.drawString(($$1.getMinecraft()).font, this.title, 18, 7, -256, false);
/*  95 */       for (int $$8 = 0; $$8 < this.messageLines.size(); $$8++) {
/*  96 */         $$0.drawString(($$1.getMinecraft()).font, this.messageLines.get($$8), 18, 18 + $$8 * 12, -1, false);
/*     */       }
/*     */     } 
/*     */     
/* 100 */     double $$9 = this.id.displayTime * $$1.getNotificationDisplayTimeMultiplier();
/* 101 */     long $$10 = $$2 - this.lastChanged;
/* 102 */     return (!this.forceHide && $$10 < $$9) ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
/*     */   }
/*     */   
/*     */   private void renderBackgroundRow(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 106 */     int $$5 = ($$2 == 0) ? 20 : 5;
/* 107 */     int $$6 = Math.min(60, $$1 - $$5);
/*     */ 
/*     */     
/* 110 */     ResourceLocation $$7 = BACKGROUND_SPRITE;
/* 111 */     $$0.blitSprite($$7, 160, 32, 0, $$2, 0, $$3, $$5, $$4);
/* 112 */     for (int $$8 = $$5; $$8 < $$1 - $$6; $$8 += 64) {
/* 113 */       $$0.blitSprite($$7, 160, 32, 32, $$2, $$8, $$3, Math.min(64, $$1 - $$8 - $$6), $$4);
/*     */     }
/* 115 */     $$0.blitSprite($$7, 160, 32, 160 - $$6, $$2, $$1 - $$6, $$3, $$6, $$4);
/*     */   }
/*     */   
/*     */   public void reset(Component $$0, @Nullable Component $$1) {
/* 119 */     this.title = $$0;
/* 120 */     this.messageLines = (List<FormattedCharSequence>)nullToEmpty($$1);
/* 121 */     this.changed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public SystemToastId getToken() {
/* 126 */     return this.id;
/*     */   }
/*     */   
/*     */   public static class SystemToastId {
/* 130 */     public static final SystemToastId NARRATOR_TOGGLE = new SystemToastId();
/* 131 */     public static final SystemToastId WORLD_BACKUP = new SystemToastId();
/* 132 */     public static final SystemToastId PACK_LOAD_FAILURE = new SystemToastId();
/* 133 */     public static final SystemToastId WORLD_ACCESS_FAILURE = new SystemToastId();
/* 134 */     public static final SystemToastId PACK_COPY_FAILURE = new SystemToastId();
/* 135 */     public static final SystemToastId PERIODIC_NOTIFICATION = new SystemToastId();
/* 136 */     public static final SystemToastId UNSECURE_SERVER_WARNING = new SystemToastId(10000L);
/*     */     
/*     */     final long displayTime;
/*     */     
/*     */     public SystemToastId(long $$0) {
/* 141 */       this.displayTime = $$0;
/*     */     }
/*     */     
/*     */     public SystemToastId() {
/* 145 */       this(5000L);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void add(ToastComponent $$0, SystemToastId $$1, Component $$2, @Nullable Component $$3) {
/* 150 */     $$0.addToast(new SystemToast($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public static void addOrUpdate(ToastComponent $$0, SystemToastId $$1, Component $$2, @Nullable Component $$3) {
/* 154 */     SystemToast $$4 = $$0.<SystemToast>getToast(SystemToast.class, $$1);
/* 155 */     if ($$4 == null) {
/* 156 */       add($$0, $$1, $$2, $$3);
/*     */     } else {
/* 158 */       $$4.reset($$2, $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void forceHide(ToastComponent $$0, SystemToastId $$1) {
/* 163 */     SystemToast $$2 = $$0.<SystemToast>getToast(SystemToast.class, $$1);
/* 164 */     if ($$2 != null) {
/* 165 */       $$2.forceHide();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void onWorldAccessFailure(Minecraft $$0, String $$1) {
/* 170 */     add($$0.getToasts(), SystemToastId.WORLD_ACCESS_FAILURE, (Component)Component.translatable("selectWorld.access_failure"), (Component)Component.literal($$1));
/*     */   }
/*     */   
/*     */   public static void onWorldDeleteFailure(Minecraft $$0, String $$1) {
/* 174 */     add($$0.getToasts(), SystemToastId.WORLD_ACCESS_FAILURE, (Component)Component.translatable("selectWorld.delete_failure"), (Component)Component.literal($$1));
/*     */   }
/*     */   
/*     */   public static void onPackCopyFailure(Minecraft $$0, String $$1) {
/* 178 */     add($$0.getToasts(), SystemToastId.PACK_COPY_FAILURE, (Component)Component.translatable("pack.copyFailure"), (Component)Component.literal($$1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\SystemToast.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */