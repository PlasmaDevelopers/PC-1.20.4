/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.audio.ListenerTransform;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.client.sounds.SoundEventListener;
/*     */ import net.minecraft.client.sounds.SoundManager;
/*     */ import net.minecraft.client.sounds.WeighedSoundEvents;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class SubtitleOverlay implements SoundEventListener {
/*     */   private static final long DISPLAY_TIME = 3000L;
/*  24 */   private final List<Subtitle> subtitles = Lists.newArrayList();
/*     */   private final Minecraft minecraft;
/*     */   private boolean isListening;
/*  27 */   private final List<Subtitle> audibleSubtitles = new ArrayList<>();
/*     */   
/*     */   public SubtitleOverlay(Minecraft $$0) {
/*  30 */     this.minecraft = $$0;
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0) {
/*  34 */     SoundManager $$1 = this.minecraft.getSoundManager();
/*  35 */     if (!this.isListening && ((Boolean)this.minecraft.options.showSubtitles().get()).booleanValue()) {
/*  36 */       $$1.addListener(this);
/*  37 */       this.isListening = true;
/*  38 */     } else if (this.isListening && !((Boolean)this.minecraft.options.showSubtitles().get()).booleanValue()) {
/*  39 */       $$1.removeListener(this);
/*  40 */       this.isListening = false;
/*     */     } 
/*     */     
/*  43 */     if (!this.isListening) {
/*     */       return;
/*     */     }
/*     */     
/*  47 */     ListenerTransform $$2 = $$1.getListenerTransform();
/*  48 */     Vec3 $$3 = $$2.position();
/*  49 */     Vec3 $$4 = $$2.forward();
/*  50 */     Vec3 $$5 = $$2.right();
/*     */     
/*  52 */     this.audibleSubtitles.clear();
/*  53 */     for (Subtitle $$6 : this.subtitles) {
/*  54 */       if ($$6.isAudibleFrom($$3)) {
/*  55 */         this.audibleSubtitles.add($$6);
/*     */       }
/*     */     } 
/*     */     
/*  59 */     if (this.audibleSubtitles.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  63 */     int $$7 = 0;
/*  64 */     int $$8 = 0;
/*     */     
/*  66 */     double $$9 = ((Double)this.minecraft.options.notificationDisplayTime().get()).doubleValue();
/*  67 */     for ($$10 = this.audibleSubtitles.iterator(); $$10.hasNext(); ) {
/*  68 */       Subtitle $$11 = $$10.next();
/*  69 */       if ($$11.getTime() + 3000.0D * $$9 <= Util.getMillis()) {
/*  70 */         $$10.remove(); continue;
/*     */       } 
/*  72 */       $$8 = Math.max($$8, this.minecraft.font.width((FormattedText)$$11.getText()));
/*     */     } 
/*     */ 
/*     */     
/*  76 */     $$8 += this.minecraft.font.width("<") + this.minecraft.font.width(" ") + this.minecraft.font.width(">") + this.minecraft.font.width(" ");
/*     */     
/*  78 */     for (Subtitle $$12 : this.audibleSubtitles) {
/*  79 */       int $$13 = 255;
/*  80 */       Component $$14 = $$12.getText();
/*     */       
/*  82 */       Vec3 $$15 = $$12.getLocation().subtract($$3).normalize();
/*  83 */       double $$16 = $$5.dot($$15);
/*  84 */       double $$17 = $$4.dot($$15);
/*  85 */       boolean $$18 = ($$17 > 0.5D);
/*     */       
/*  87 */       int $$19 = $$8 / 2;
/*  88 */       Objects.requireNonNull(this.minecraft.font); int $$20 = 9;
/*  89 */       int $$21 = $$20 / 2;
/*  90 */       float $$22 = 1.0F;
/*  91 */       int $$23 = this.minecraft.font.width((FormattedText)$$14);
/*  92 */       int $$24 = Mth.floor(Mth.clampedLerp(255.0F, 75.0F, (float)(Util.getMillis() - $$12.getTime()) / (float)(3000.0D * $$9)));
/*  93 */       int $$25 = $$24 << 16 | $$24 << 8 | $$24;
/*     */       
/*  95 */       $$0.pose().pushPose();
/*  96 */       $$0.pose().translate($$0.guiWidth() - $$19 * 1.0F - 2.0F, ($$0.guiHeight() - 35) - ($$7 * ($$20 + 1)) * 1.0F, 0.0F);
/*  97 */       $$0.pose().scale(1.0F, 1.0F, 1.0F);
/*     */       
/*  99 */       $$0.fill(-$$19 - 1, -$$21 - 1, $$19 + 1, $$21 + 1, this.minecraft.options.getBackgroundColor(0.8F));
/*     */       
/* 101 */       int $$26 = $$25 + -16777216;
/* 102 */       if (!$$18) {
/* 103 */         if ($$16 > 0.0D) {
/* 104 */           $$0.drawString(this.minecraft.font, ">", $$19 - this.minecraft.font.width(">"), -$$21, $$26);
/* 105 */         } else if ($$16 < 0.0D) {
/* 106 */           $$0.drawString(this.minecraft.font, "<", -$$19, -$$21, $$26);
/*     */         } 
/*     */       }
/*     */       
/* 110 */       $$0.drawString(this.minecraft.font, $$14, -$$23 / 2, -$$21, $$26);
/*     */       
/* 112 */       $$0.pose().popPose();
/* 113 */       $$7++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlaySound(SoundInstance $$0, WeighedSoundEvents $$1, float $$2) {
/* 119 */     if ($$1.getSubtitle() == null) {
/*     */       return;
/*     */     }
/* 122 */     Component $$3 = $$1.getSubtitle();
/* 123 */     if (!this.subtitles.isEmpty()) {
/* 124 */       for (Subtitle $$4 : this.subtitles) {
/* 125 */         if ($$4.getText().equals($$3)) {
/* 126 */           $$4.refresh(new Vec3($$0.getX(), $$0.getY(), $$0.getZ()));
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/* 132 */     this.subtitles.add(new Subtitle($$3, $$2, new Vec3($$0.getX(), $$0.getY(), $$0.getZ())));
/*     */   }
/*     */   
/*     */   public static class Subtitle {
/*     */     private final Component text;
/*     */     private final float range;
/*     */     private long time;
/*     */     private Vec3 location;
/*     */     
/*     */     public Subtitle(Component $$0, float $$1, Vec3 $$2) {
/* 142 */       this.text = $$0;
/* 143 */       this.range = $$1;
/* 144 */       this.location = $$2;
/* 145 */       this.time = Util.getMillis();
/*     */     }
/*     */     
/*     */     public Component getText() {
/* 149 */       return this.text;
/*     */     }
/*     */     
/*     */     public long getTime() {
/* 153 */       return this.time;
/*     */     }
/*     */     
/*     */     public Vec3 getLocation() {
/* 157 */       return this.location;
/*     */     }
/*     */     
/*     */     public void refresh(Vec3 $$0) {
/* 161 */       this.location = $$0;
/* 162 */       this.time = Util.getMillis();
/*     */     }
/*     */     
/*     */     public boolean isAudibleFrom(Vec3 $$0) {
/* 166 */       return (Float.isInfinite(this.range) || $$0.closerThan((Position)this.location, this.range));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\SubtitleOverlay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */