/*     */ package net.minecraft.client.animation.definitions;
/*     */ 
/*     */ import net.minecraft.client.animation.AnimationChannel;
/*     */ import net.minecraft.client.animation.AnimationDefinition;
/*     */ import net.minecraft.client.animation.Keyframe;
/*     */ import net.minecraft.client.animation.KeyframeAnimations;
/*     */ 
/*     */ 
/*     */ public class BreezeAnimation
/*     */ {
/*  11 */   public static final AnimationDefinition SHOOT = AnimationDefinition.Builder.withLength(1.125F)
/*  12 */     .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  13 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/*  14 */             KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/*  15 */             KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/*  16 */             KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  17 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  19 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  20 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/*  21 */             KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7917F, 
/*  22 */             KeyframeAnimations.posVec(0.0F, -1.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9583F, 
/*  23 */             KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  24 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  26 */         })).addAnimation("wind_bottom", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  27 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  29 */         })).addAnimation("wind_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  30 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/*  31 */             KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/*  32 */             KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/*  33 */             KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  34 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  36 */         })).addAnimation("wind_mid", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  37 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/*  38 */             KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/*  39 */             KeyframeAnimations.posVec(0.0F, 0.0F, 6.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/*  40 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  41 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  43 */         })).addAnimation("wind_top", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  44 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/*  45 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/*  46 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/*  47 */             KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  48 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  50 */         })).addAnimation("wind_top", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  51 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/*  52 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.8333F, 
/*  53 */             KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9583F, 
/*  54 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  55 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  57 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  58 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/*  59 */             KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/*  60 */             KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/*  61 */             KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  62 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  64 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  65 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/*  66 */             KeyframeAnimations.posVec(0.0F, 3.0F, 5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.8333F, 
/*  67 */             KeyframeAnimations.posVec(0.0F, 3.0F, 6.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9583F, 
/*  68 */             KeyframeAnimations.posVec(0.0F, 3.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  69 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  71 */         })).addAnimation("rods", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  72 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/*  73 */             KeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  75 */         })).build();
/*     */   
/*  77 */   public static final AnimationDefinition JUMP = AnimationDefinition.Builder.withLength(1.125F)
/*  78 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  79 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/*  80 */             KeyframeAnimations.posVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  81 */             KeyframeAnimations.posVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/*  82 */             KeyframeAnimations.posVec(0.0F, 11.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  83 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  85 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  86 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/*  87 */             KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  88 */             KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.8333F, 
/*  89 */             KeyframeAnimations.degreeVec(-19.25F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  90 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  92 */         })).addAnimation("wind_body", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/*  93 */           new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/*  94 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  95 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/*  96 */             KeyframeAnimations.scaleVec(1.0D, 1.2999999523162842D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  97 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  99 */         })).addAnimation("wind_bottom", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 100 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/* 101 */             KeyframeAnimations.degreeVec(0.0F, 90.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/* 102 */             KeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 104 */         })).addAnimation("wind_bottom", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/* 105 */           new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 106 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/* 107 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 108 */             KeyframeAnimations.scaleVec(1.0D, 1.100000023841858D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/* 109 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 111 */         })).addAnimation("wind_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 112 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/* 113 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/* 114 */             KeyframeAnimations.degreeVec(0.0F, 180.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 116 */         })).addAnimation("wind_mid", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 117 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 118 */             KeyframeAnimations.posVec(0.0F, -6.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/* 119 */             KeyframeAnimations.posVec(0.0F, -6.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 120 */             KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/* 121 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 123 */         })).addAnimation("wind_top", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 124 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/* 125 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/* 126 */             KeyframeAnimations.degreeVec(0.0F, 90.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 128 */         })).addAnimation("wind_top", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 129 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 130 */             KeyframeAnimations.posVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/* 131 */             KeyframeAnimations.posVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 132 */             KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/* 133 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 135 */         })).addAnimation("rods", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 136 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.8333F, 
/* 137 */             KeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 139 */         })).build();
/*     */   
/* 141 */   public static final AnimationDefinition SLIDE = AnimationDefinition.Builder.withLength(1.0F)
/* 142 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 143 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2F, 
/* 144 */             KeyframeAnimations.posVec(0.0F, 0.0F, -6.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 146 */         })).addAnimation("wind_mid", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 147 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2F, 
/* 148 */             KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 150 */         })).addAnimation("wind_top", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 151 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2F, 
/* 152 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 154 */         })).build();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\definitions\BreezeAnimation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */