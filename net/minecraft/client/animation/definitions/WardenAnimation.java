/*     */ package net.minecraft.client.animation.definitions;
/*     */ 
/*     */ import net.minecraft.client.animation.AnimationChannel;
/*     */ import net.minecraft.client.animation.AnimationDefinition;
/*     */ import net.minecraft.client.animation.Keyframe;
/*     */ import net.minecraft.client.animation.KeyframeAnimations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WardenAnimation
/*     */ {
/*  13 */   public static final AnimationDefinition WARDEN_EMERGE = AnimationDefinition.Builder.withLength(6.68F)
/*  14 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/*  15 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/*  16 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2F, 
/*  17 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/*  18 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/*  19 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/*  20 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/*  21 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/*  22 */             KeyframeAnimations.degreeVec(25.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.92F, 
/*  23 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.08F, 
/*  24 */             KeyframeAnimations.degreeVec(25.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.44F, 
/*  25 */             KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/*  26 */             KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.68F, 
/*  27 */             KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/*  28 */             KeyframeAnimations.degreeVec(70.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/*  29 */             KeyframeAnimations.degreeVec(70.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/*  30 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/*  32 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] { 
/*  33 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -63.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/*  34 */             KeyframeAnimations.posVec(0.0F, -56.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2F, 
/*  35 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/*  36 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/*  37 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/*  38 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/*  39 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.16F, 
/*  40 */             KeyframeAnimations.posVec(0.0F, -27.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/*  41 */             KeyframeAnimations.posVec(0.0F, -14.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.92F, 
/*  42 */             KeyframeAnimations.posVec(0.0F, -11.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.08F, 
/*  43 */             KeyframeAnimations.posVec(0.0F, -14.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.44F, 
/*  44 */             KeyframeAnimations.posVec(0.0F, -6.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/*  45 */             KeyframeAnimations.posVec(0.0F, -4.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.68F, 
/*  46 */             KeyframeAnimations.posVec(0.0F, -6.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/*  47 */             KeyframeAnimations.posVec(0.0F, -3.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/*  48 */             KeyframeAnimations.posVec(0.0F, -3.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/*  49 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/*  51 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/*  52 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/*  53 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92F, 
/*  54 */             KeyframeAnimations.degreeVec(0.74F, 0.0F, -40.38F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.16F, 
/*  55 */             KeyframeAnimations.degreeVec(-67.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/*  56 */             KeyframeAnimations.degreeVec(-67.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.32F, 
/*  57 */             KeyframeAnimations.degreeVec(-47.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4F, 
/*  58 */             KeyframeAnimations.degreeVec(-67.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/*  59 */             KeyframeAnimations.degreeVec(-67.5F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.76F, 
/*  60 */             KeyframeAnimations.degreeVec(-67.5F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84F, 
/*  61 */             KeyframeAnimations.degreeVec(-52.5F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.92F, 
/*  62 */             KeyframeAnimations.degreeVec(-67.5F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.64F, 
/*  63 */             KeyframeAnimations.degreeVec(-17.5F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/*  64 */             KeyframeAnimations.degreeVec(70.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.04F, 
/*  65 */             KeyframeAnimations.degreeVec(70.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.12F, 
/*  66 */             KeyframeAnimations.degreeVec(80.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.24F, 
/*  67 */             KeyframeAnimations.degreeVec(70.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/*  68 */             KeyframeAnimations.degreeVec(77.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/*  69 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/*  71 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] { 
/*  72 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/*  73 */             KeyframeAnimations.posVec(-8.0F, -11.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.92F, 
/*  74 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/*  75 */             KeyframeAnimations.posVec(0.0F, 0.47F, -0.95F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.32F, 
/*  76 */             KeyframeAnimations.posVec(0.0F, 0.47F, -0.95F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4F, 
/*  77 */             KeyframeAnimations.posVec(0.0F, 0.47F, -0.95F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/*  78 */             KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.76F, 
/*  79 */             KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84F, 
/*  80 */             KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.92F, 
/*  81 */             KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.64F, 
/*  82 */             KeyframeAnimations.posVec(0.0F, -2.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/*  83 */             KeyframeAnimations.posVec(0.0F, -4.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.04F, 
/*  84 */             KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.12F, 
/*  85 */             KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.24F, 
/*  86 */             KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/*  87 */             KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/*  88 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/*  90 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  91 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/*  92 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/*  93 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/*  94 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/*  95 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/*  96 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/*  97 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/*  98 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/*  99 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 101 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 102 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 103 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 104 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 105 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 106 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/* 107 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 108 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/* 109 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 110 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 112 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 113 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 114 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 115 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 116 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 117 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/* 118 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 119 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/* 120 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 121 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 123 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 124 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 125 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 126 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 127 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 128 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/* 129 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 130 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/* 131 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 132 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 134 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/* 135 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 136 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2F, 
/* 137 */             KeyframeAnimations.degreeVec(-152.5F, 2.5F, 7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/* 138 */             KeyframeAnimations.degreeVec(-180.0F, 12.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/* 139 */             KeyframeAnimations.degreeVec(-90.0F, 12.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 140 */             KeyframeAnimations.degreeVec(-90.0F, 12.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 141 */             KeyframeAnimations.degreeVec(-90.0F, 12.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.08F, 
/* 142 */             KeyframeAnimations.degreeVec(-95.0F, 12.5F, -10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.24F, 
/* 143 */             KeyframeAnimations.degreeVec(-83.93F, 3.93F, 5.71F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 144 */             KeyframeAnimations.degreeVec(-80.0F, 7.5F, 17.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/* 145 */             KeyframeAnimations.degreeVec(-67.5F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.08F, 
/* 146 */             KeyframeAnimations.degreeVec(-67.5F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.44F, 
/* 147 */             KeyframeAnimations.degreeVec(-55.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/* 148 */             KeyframeAnimations.degreeVec(-60.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.68F, 
/* 149 */             KeyframeAnimations.degreeVec(-55.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 150 */             KeyframeAnimations.degreeVec(-67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.56F, 
/* 151 */             KeyframeAnimations.degreeVec(-50.45F, 0.0F, 2.69F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.08F, 
/* 152 */             KeyframeAnimations.degreeVec(-62.72F, 0.0F, 4.3F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 153 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 155 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] { 
/* 156 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 157 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2F, 
/* 158 */             KeyframeAnimations.posVec(0.0F, -21.0F, 9.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/* 159 */             KeyframeAnimations.posVec(2.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/* 160 */             KeyframeAnimations.posVec(2.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 161 */             KeyframeAnimations.posVec(2.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 162 */             KeyframeAnimations.posVec(2.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.08F, 
/* 163 */             KeyframeAnimations.posVec(2.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.24F, 
/* 164 */             KeyframeAnimations.posVec(2.0F, 2.71F, 3.86F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 165 */             KeyframeAnimations.posVec(2.0F, 1.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/* 166 */             KeyframeAnimations.posVec(2.0F, 3.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.08F, 
/* 167 */             KeyframeAnimations.posVec(2.0F, 3.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.44F, 
/* 168 */             KeyframeAnimations.posVec(2.67F, 4.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/* 169 */             KeyframeAnimations.posVec(2.67F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.68F, 
/* 170 */             KeyframeAnimations.posVec(2.67F, 4.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 171 */             KeyframeAnimations.posVec(0.67F, 3.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 172 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 174 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/* 175 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12F, 
/* 176 */             KeyframeAnimations.degreeVec(-167.5F, -17.5F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6F, 
/* 177 */             KeyframeAnimations.degreeVec(-167.5F, -17.5F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.88F, 
/* 178 */             KeyframeAnimations.degreeVec(-175.0F, -17.5F, 15.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.16F, 
/* 179 */             KeyframeAnimations.degreeVec(-190.0F, -17.5F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.28F, 
/* 180 */             KeyframeAnimations.degreeVec(-90.0F, -5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/* 181 */             KeyframeAnimations.degreeVec(-90.0F, -17.5F, -12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/* 182 */             KeyframeAnimations.degreeVec(-90.0F, -17.5F, -12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 183 */             KeyframeAnimations.degreeVec(-90.0F, -17.5F, -12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 184 */             KeyframeAnimations.degreeVec(-90.0F, -17.5F, -12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.04F, 
/* 185 */             KeyframeAnimations.degreeVec(-81.29F, -10.64F, -14.21F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.16F, 
/* 186 */             KeyframeAnimations.degreeVec(-83.5F, -5.5F, -15.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/* 187 */             KeyframeAnimations.degreeVec(-62.5F, -7.5F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.92F, 
/* 188 */             KeyframeAnimations.degreeVec(-58.75F, -3.75F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.08F, 
/* 189 */             KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.44F, 
/* 190 */             KeyframeAnimations.degreeVec(-52.5F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/* 191 */             KeyframeAnimations.degreeVec(-50.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.68F, 
/* 192 */             KeyframeAnimations.degreeVec(-52.5F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 193 */             KeyframeAnimations.degreeVec(-72.5F, -2.5F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.56F, 
/* 194 */             KeyframeAnimations.degreeVec(-57.5F, -4.54F, 2.99F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.08F, 
/* 195 */             KeyframeAnimations.degreeVec(-70.99F, -5.77F, 1.78F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 196 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 198 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] { 
/* 199 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.12F, 
/* 200 */             KeyframeAnimations.posVec(0.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6F, 
/* 201 */             KeyframeAnimations.posVec(0.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.88F, 
/* 202 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2F, 
/* 203 */             KeyframeAnimations.posVec(-2.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/* 204 */             KeyframeAnimations.posVec(-4.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/* 205 */             KeyframeAnimations.posVec(-4.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 206 */             KeyframeAnimations.posVec(-4.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 207 */             KeyframeAnimations.posVec(-4.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.04F, 
/* 208 */             KeyframeAnimations.posVec(-3.23F, 5.7F, 4.97F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.16F, 
/* 209 */             KeyframeAnimations.posVec(-1.49F, 2.22F, 5.25F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/* 210 */             KeyframeAnimations.posVec(-1.14F, 1.71F, 1.86F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.92F, 
/* 211 */             KeyframeAnimations.posVec(-1.14F, 1.21F, 3.86F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.08F, 
/* 212 */             KeyframeAnimations.posVec(-1.14F, 2.71F, 4.86F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.44F, 
/* 213 */             KeyframeAnimations.posVec(-1.0F, 1.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.56F, 
/* 214 */             KeyframeAnimations.posVec(0.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.68F, 
/* 215 */             KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 216 */             KeyframeAnimations.posVec(-2.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 217 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 219 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/* 220 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 221 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 222 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 223 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 224 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.32F, 
/* 225 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.48F, 
/* 226 */             KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.6F, 
/* 227 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 228 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/* 229 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 230 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 232 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] { 
/* 233 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -63.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 234 */             KeyframeAnimations.posVec(0.0F, -56.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2F, 
/* 235 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/* 236 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/* 237 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 238 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 239 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 240 */             KeyframeAnimations.posVec(0.0F, -22.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.76F, 
/* 241 */             KeyframeAnimations.posVec(0.0F, -12.28F, 2.48F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.92F, 
/* 242 */             KeyframeAnimations.posVec(0.0F, -9.28F, 2.48F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.08F, 
/* 243 */             KeyframeAnimations.posVec(0.0F, -12.28F, 2.48F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.32F, 
/* 244 */             KeyframeAnimations.posVec(0.0F, -4.14F, 4.14F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.48F, 
/* 245 */             KeyframeAnimations.posVec(0.0F, -0.57F, -8.43F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.6F, 
/* 246 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 247 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/* 248 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 249 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 251 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/* 252 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 253 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 254 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 255 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 256 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.84F, 
/* 257 */             KeyframeAnimations.degreeVec(20.0F, 0.0F, -17.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.0F, 
/* 258 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.68F, 
/* 259 */             KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.84F, 
/* 260 */             KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 261 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/* 262 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 263 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 265 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] { 
/* 266 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -63.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.52F, 
/* 267 */             KeyframeAnimations.posVec(0.0F, -56.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2F, 
/* 268 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.68F, 
/* 269 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/* 270 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 271 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 272 */             KeyframeAnimations.posVec(0.0F, -32.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.36F, 
/* 273 */             KeyframeAnimations.posVec(0.0F, -22.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.84F, 
/* 274 */             KeyframeAnimations.posVec(-4.0F, 2.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.0F, 
/* 275 */             KeyframeAnimations.posVec(-4.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.68F, 
/* 276 */             KeyframeAnimations.posVec(-4.0F, 0.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.84F, 
/* 277 */             KeyframeAnimations.posVec(-2.0F, 2.0F, -3.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 278 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.8F, 
/* 279 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.64F, 
/* 280 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 282 */         })).build();
/*     */   
/* 284 */   public static final AnimationDefinition WARDEN_DIG = AnimationDefinition.Builder.withLength(5.0F)
/* 285 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/* 286 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25F, 
/* 287 */             KeyframeAnimations.degreeVec(4.13441F, 0.94736F, 1.2694F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 288 */             KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 289 */             KeyframeAnimations.degreeVec(54.45407F, -13.53935F, -18.14183F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 290 */             KeyframeAnimations.degreeVec(59.46442F, -10.8885F, 35.7954F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.3333F, 
/* 291 */             KeyframeAnimations.degreeVec(82.28261F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.625F, 
/* 292 */             KeyframeAnimations.degreeVec(53.23606F, 10.04715F, -29.72932F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 293 */             KeyframeAnimations.degreeVec(-17.71739F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5417F, 
/* 294 */             KeyframeAnimations.degreeVec(112.28261F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6667F, 
/* 295 */             KeyframeAnimations.degreeVec(116.06889F, 5.11581F, -24.50117F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.8333F, 
/* 296 */             KeyframeAnimations.degreeVec(121.56244F, -4.17248F, 19.57737F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0417F, 
/* 297 */             KeyframeAnimations.degreeVec(138.5689F, 5.11581F, -24.50117F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.25F, 
/* 298 */             KeyframeAnimations.degreeVec(144.06244F, -4.17248F, 19.57737F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.375F, 
/* 299 */             KeyframeAnimations.degreeVec(147.28261F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.625F, 
/* 300 */             KeyframeAnimations.degreeVec(147.28261F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.875F, 
/* 301 */             KeyframeAnimations.degreeVec(134.36221F, 8.81113F, -8.90172F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.0417F, 
/* 302 */             KeyframeAnimations.degreeVec(132.05966F, -8.35927F, 9.70506F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.25F, 
/* 303 */             KeyframeAnimations.degreeVec(134.36221F, 8.81113F, -8.90172F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.5F, 
/* 304 */             KeyframeAnimations.degreeVec(147.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 306 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] { 
/* 307 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 308 */             KeyframeAnimations.posVec(0.0F, -16.48454F, -6.5784F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 309 */             KeyframeAnimations.posVec(0.0F, -16.48454F, -6.5784F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 310 */             KeyframeAnimations.posVec(0.0F, -16.97F, -7.11F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.625F, 
/* 311 */             KeyframeAnimations.posVec(0.0F, -13.97F, -7.11F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 312 */             KeyframeAnimations.posVec(0.0F, -11.48454F, -0.5784F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5417F, 
/* 313 */             KeyframeAnimations.posVec(0.0F, -16.48454F, -6.5784F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6667F, 
/* 314 */             KeyframeAnimations.posVec(0.0F, -20.27F, -5.42F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.375F, 
/* 315 */             KeyframeAnimations.posVec(0.0F, -21.48454F, -5.5784F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.0417F, 
/* 316 */             KeyframeAnimations.posVec(0.0F, -22.48454F, -5.5784F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.5F, 
/* 317 */             KeyframeAnimations.posVec(0.0F, -40.0F, -8.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 319 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 320 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 321 */             KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2083F, 
/* 322 */             KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.75F, 
/* 323 */             KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.375F, 
/* 324 */             KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5417F, 
/* 325 */             KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.375F, 
/* 326 */             KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 328 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 329 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.375F, 
/* 330 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 332 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 333 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 334 */             KeyframeAnimations.degreeVec(-101.8036F, -21.29587F, 30.61478F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 335 */             KeyframeAnimations.degreeVec(-101.8036F, -21.29587F, 30.61478F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 336 */             KeyframeAnimations.degreeVec(48.7585F, -17.61941F, 9.9865F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.1667F, 
/* 337 */             KeyframeAnimations.degreeVec(48.7585F, -17.61941F, 9.9865F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4583F, 
/* 338 */             KeyframeAnimations.degreeVec(-101.8036F, -21.29587F, 30.61478F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.75F, 
/* 339 */             KeyframeAnimations.degreeVec(-89.04994F, -4.19657F, -1.47845F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 340 */             KeyframeAnimations.degreeVec(-158.30728F, 3.7152F, -1.52352F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5417F, 
/* 341 */             KeyframeAnimations.degreeVec(-89.04994F, -4.19657F, -1.47845F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.375F, 
/* 342 */             KeyframeAnimations.degreeVec(-120.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 344 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 345 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 346 */             KeyframeAnimations.posVec(2.22F, 0.0F, 0.86F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 347 */             KeyframeAnimations.posVec(3.12F, 0.0F, 4.29F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 348 */             KeyframeAnimations.posVec(1.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.375F, 
/* 349 */             KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 351 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/* 352 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2917F, 
/* 353 */             KeyframeAnimations.degreeVec(-63.89288F, -0.52011F, 2.09491F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 354 */             KeyframeAnimations.degreeVec(-63.89288F, -0.52011F, 2.09491F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 355 */             KeyframeAnimations.degreeVec(-62.87857F, 15.15061F, 9.97445F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9167F, 
/* 356 */             KeyframeAnimations.degreeVec(-86.93642F, 17.45026F, 4.05284F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.1667F, 
/* 357 */             KeyframeAnimations.degreeVec(-86.93642F, 17.45026F, 4.05284F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4583F, 
/* 358 */             KeyframeAnimations.degreeVec(-86.93642F, 17.45026F, 4.05284F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6667F, 
/* 359 */             KeyframeAnimations.degreeVec(63.0984F, 8.83573F, -8.71284F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8333F, 
/* 360 */             KeyframeAnimations.degreeVec(35.5984F, 8.83573F, -8.71284F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 361 */             KeyframeAnimations.degreeVec(-153.27473F, -0.02953F, 3.5235F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5417F, 
/* 362 */             KeyframeAnimations.degreeVec(-87.07754F, -0.02625F, 3.132F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.375F, 
/* 363 */             KeyframeAnimations.degreeVec(-120.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 365 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 366 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 367 */             KeyframeAnimations.posVec(-0.28F, 5.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 368 */             KeyframeAnimations.posVec(-1.51F, 4.35F, 4.33F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9167F, 
/* 369 */             KeyframeAnimations.posVec(-0.6F, 3.61F, 4.63F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.1667F, 
/* 370 */             KeyframeAnimations.posVec(-0.6F, 3.61F, 0.63F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6667F, 
/* 371 */             KeyframeAnimations.posVec(-2.85F, -0.1F, 3.33F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 372 */             KeyframeAnimations.posVec(-1.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.375F, 
/* 373 */             KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 375 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 376 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 377 */             KeyframeAnimations.degreeVec(113.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 378 */             KeyframeAnimations.degreeVec(113.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.3333F, 
/* 379 */             KeyframeAnimations.degreeVec(113.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.5833F, 
/* 380 */             KeyframeAnimations.degreeVec(182.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.8333F, 
/* 381 */             KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.0833F, 
/* 382 */             KeyframeAnimations.degreeVec(182.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2917F, 
/* 383 */             KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.5F, 
/* 384 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 386 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 387 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 388 */             KeyframeAnimations.posVec(0.0F, -13.98F, -2.37F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 389 */             KeyframeAnimations.posVec(0.0F, -13.98F, -2.37F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.3333F, 
/* 390 */             KeyframeAnimations.posVec(0.0F, -13.98F, -2.37F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.5833F, 
/* 391 */             KeyframeAnimations.posVec(0.0F, -7.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.8333F, 
/* 392 */             KeyframeAnimations.posVec(0.0F, -9.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.0833F, 
/* 393 */             KeyframeAnimations.posVec(0.0F, -16.71F, -3.69F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2917F, 
/* 394 */             KeyframeAnimations.posVec(0.0F, -28.0F, -5.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 396 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 397 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 398 */             KeyframeAnimations.degreeVec(114.98F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 399 */             KeyframeAnimations.degreeVec(114.98F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.3333F, 
/* 400 */             KeyframeAnimations.degreeVec(114.98F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.5833F, 
/* 401 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.8333F, 
/* 402 */             KeyframeAnimations.degreeVec(172.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.0833F, 
/* 403 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2917F, 
/* 404 */             KeyframeAnimations.degreeVec(197.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.5F, 
/* 405 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 407 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 408 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 409 */             KeyframeAnimations.posVec(0.0F, -14.01F, -2.35F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/* 410 */             KeyframeAnimations.posVec(0.0F, -14.01F, -2.35F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.3333F, 
/* 411 */             KeyframeAnimations.posVec(0.0F, -14.01F, -2.35F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.5833F, 
/* 412 */             KeyframeAnimations.posVec(0.0F, -5.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.8333F, 
/* 413 */             KeyframeAnimations.posVec(0.0F, -7.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.0833F, 
/* 414 */             KeyframeAnimations.posVec(0.0F, -15.5F, -3.76F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2917F, 
/* 415 */             KeyframeAnimations.posVec(0.0F, -28.0F, -5.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 417 */         })).build();
/*     */   
/* 419 */   public static final AnimationDefinition WARDEN_ROAR = AnimationDefinition.Builder.withLength(4.2F)
/* 420 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 421 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 422 */             KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6F, 
/* 423 */             KeyframeAnimations.degreeVec(32.5F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.84F, 
/* 424 */             KeyframeAnimations.degreeVec(38.33F, 0.0F, 2.99F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08F, 
/* 425 */             KeyframeAnimations.degreeVec(40.97F, 0.0F, -4.3F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.36F, 
/* 426 */             KeyframeAnimations.degreeVec(44.41F, 0.0F, 6.29F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 427 */             KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 428 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 430 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 431 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 432 */             KeyframeAnimations.posVec(0.0F, -1.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6F, 
/* 433 */             KeyframeAnimations.posVec(0.0F, -3.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 434 */             KeyframeAnimations.posVec(0.0F, -3.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 435 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 437 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 438 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 439 */             KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6F, 
/* 440 */             KeyframeAnimations.degreeVec(-32.5F, 0.0F, -27.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8F, 
/* 441 */             KeyframeAnimations.degreeVec(-32.5F, 0.0F, 26.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.04F, 
/* 442 */             KeyframeAnimations.degreeVec(-32.5F, 0.0F, -27.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.44F, 
/* 443 */             KeyframeAnimations.degreeVec(-32.5F, 0.0F, 26.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.84F, 
/* 444 */             KeyframeAnimations.degreeVec(-5.0F, 0.0F, -12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 445 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 447 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 448 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 449 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6F, 
/* 450 */             KeyframeAnimations.posVec(0.0F, -2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2F, 
/* 451 */             KeyframeAnimations.posVec(0.0F, -2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48F, 
/* 452 */             KeyframeAnimations.posVec(0.0F, -2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 453 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 455 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 456 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 457 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.76F, 
/* 458 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.85F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08F, 
/* 459 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.4F, 
/* 460 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.85F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.72F, 
/* 461 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 462 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.85F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 463 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 465 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 466 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 467 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.76F, 
/* 468 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.85F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.08F, 
/* 469 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.4F, 
/* 470 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.85F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.72F, 
/* 471 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 472 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.85F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 473 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 475 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 476 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.72F, 
/* 477 */             KeyframeAnimations.degreeVec(-120.0F, 0.0F, -20.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 478 */             KeyframeAnimations.degreeVec(-77.5F, 3.75F, 15.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48F, 
/* 479 */             KeyframeAnimations.degreeVec(67.5F, -32.5F, 20.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48F, 
/* 480 */             KeyframeAnimations.degreeVec(37.5F, -32.5F, 25.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 481 */             KeyframeAnimations.degreeVec(27.6F, -17.1F, 32.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 482 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 484 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 485 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.72F, 
/* 486 */             KeyframeAnimations.posVec(3.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48F, 
/* 487 */             KeyframeAnimations.posVec(4.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48F, 
/* 488 */             KeyframeAnimations.posVec(4.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 489 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 491 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 492 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.72F, 
/* 493 */             KeyframeAnimations.degreeVec(-125.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 494 */             KeyframeAnimations.degreeVec(-76.25F, -17.5F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48F, 
/* 495 */             KeyframeAnimations.degreeVec(62.5F, 42.5F, -12.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48F, 
/* 496 */             KeyframeAnimations.degreeVec(37.5F, 27.5F, -27.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 497 */             KeyframeAnimations.degreeVec(25.0F, 18.4F, -30.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 498 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 500 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 501 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.72F, 
/* 502 */             KeyframeAnimations.posVec(-3.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.48F, 
/* 503 */             KeyframeAnimations.posVec(-4.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.48F, 
/* 504 */             KeyframeAnimations.posVec(-4.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.2F, 
/* 505 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 507 */         })).build();
/*     */   
/* 509 */   public static final AnimationDefinition WARDEN_SNIFF = AnimationDefinition.Builder.withLength(4.16F)
/* 510 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 511 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.56F, 
/* 512 */             KeyframeAnimations.degreeVec(17.5F, 32.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.96F, 
/* 513 */             KeyframeAnimations.degreeVec(0.0F, 32.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2F, 
/* 514 */             KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.8F, 
/* 515 */             KeyframeAnimations.degreeVec(10.0F, -30.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.32F, 
/* 516 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 518 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 519 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.68F, 
/* 520 */             KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.96F, 
/* 521 */             KeyframeAnimations.degreeVec(-22.5F, 40.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.24F, 
/* 522 */             KeyframeAnimations.degreeVec(0.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.52F, 
/* 523 */             KeyframeAnimations.degreeVec(-35.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.76F, 
/* 524 */             KeyframeAnimations.degreeVec(0.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.28F, 
/* 525 */             KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.88F, 
/* 526 */             KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.32F, 
/* 527 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 529 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 530 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.96F, 
/* 531 */             KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2F, 
/* 532 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.76F, 
/* 533 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.32F, 
/* 534 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 536 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 537 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.96F, 
/* 538 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2F, 
/* 539 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.76F, 
/* 540 */             KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.32F, 
/* 541 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 543 */         })).build();
/*     */   
/* 545 */   public static final AnimationDefinition WARDEN_ATTACK = AnimationDefinition.Builder.withLength(0.33333F)
/* 546 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 547 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0417F, 
/* 548 */             KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2083F, 
/* 549 */             KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 550 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 552 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 553 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0417F, 
/* 554 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2083F, 
/* 555 */             KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 556 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 558 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 559 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0417F, 
/* 560 */             KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25F, 
/* 561 */             KeyframeAnimations.degreeVec(-30.17493F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 562 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 564 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 565 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0417F, 
/* 566 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25F, 
/* 567 */             KeyframeAnimations.posVec(0.0F, -2.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 568 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 570 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 571 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0417F, 
/* 572 */             KeyframeAnimations.degreeVec(-120.36119F, 40.78947F, -20.94102F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.1667F, 
/* 573 */             KeyframeAnimations.degreeVec(-90.0F, -45.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 574 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 576 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 577 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0417F, 
/* 578 */             KeyframeAnimations.posVec(4.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.1667F, 
/* 579 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 580 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 582 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 583 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0417F, 
/* 584 */             KeyframeAnimations.degreeVec(-120.36119F, -40.78947F, 20.94102F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.1667F, 
/* 585 */             KeyframeAnimations.degreeVec(-61.1632F, 42.85882F, 11.52421F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 586 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 588 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 589 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0417F, 
/* 590 */             KeyframeAnimations.posVec(-4.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.1667F, 
/* 591 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 592 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 594 */         })).build();
/*     */   
/* 596 */   public static final AnimationDefinition WARDEN_SONIC_BOOM = AnimationDefinition.Builder.withLength(3.0F)
/* 597 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 598 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0833F, 
/* 599 */             KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.625F, 
/* 600 */             KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.9167F, 
/* 601 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, 
/* 602 */             KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.4583F, 
/* 603 */             KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.7083F, 
/* 604 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.875F, 
/* 605 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 607 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 608 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0833F, 
/* 609 */             KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.625F, 
/* 610 */             KeyframeAnimations.posVec(0.0F, -4.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.9167F, 
/* 611 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.7083F, 
/* 612 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.875F, 
/* 613 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 615 */         })).addAnimation("right_ribcage", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 616 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5417F, 
/* 617 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.7917F, 
/* 618 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.875F, 
/* 619 */             KeyframeAnimations.degreeVec(0.0F, 125.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5F, 
/* 620 */             KeyframeAnimations.degreeVec(0.0F, 125.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6667F, 
/* 621 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 623 */         })).addAnimation("left_ribcage", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 624 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5417F, 
/* 625 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.7917F, 
/* 626 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.875F, 
/* 627 */             KeyframeAnimations.degreeVec(0.0F, -125.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5F, 
/* 628 */             KeyframeAnimations.degreeVec(0.0F, -125.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6667F, 
/* 629 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 631 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 632 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 633 */             KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.75F, 
/* 634 */             KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.9167F, 
/* 635 */             KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5F, 
/* 636 */             KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.7083F, 
/* 637 */             KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.875F, 
/* 638 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 640 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 641 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.9167F, 
/* 642 */             KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5F, 
/* 643 */             KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.7083F, 
/* 644 */             KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.875F, 
/* 645 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 647 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 648 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.875F, 
/* 649 */             KeyframeAnimations.degreeVec(-42.28659F, -32.69813F, -5.00825F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.1667F, 
/* 650 */             KeyframeAnimations.degreeVec(-29.83757F, -35.39626F, -45.28089F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.3333F, 
/* 651 */             KeyframeAnimations.degreeVec(-29.83757F, -35.39626F, -45.28089F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6667F, 
/* 652 */             KeyframeAnimations.degreeVec(-72.28659F, -32.69813F, -5.00825F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8333F, 
/* 653 */             KeyframeAnimations.degreeVec(35.26439F, -30.0F, 35.26439F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.9167F, 
/* 654 */             KeyframeAnimations.degreeVec(73.75484F, -13.0931F, 19.20518F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5F, 
/* 655 */             KeyframeAnimations.degreeVec(73.75484F, -13.0931F, 19.20518F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.75F, 
/* 656 */             KeyframeAnimations.degreeVec(58.20713F, -21.1064F, 28.7261F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 657 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 659 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 660 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8333F, 
/* 661 */             KeyframeAnimations.posVec(3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.75F, 
/* 662 */             KeyframeAnimations.posVec(3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 663 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 665 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 666 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.875F, 
/* 667 */             KeyframeAnimations.degreeVec(-33.80694F, 32.31058F, 6.87997F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.1667F, 
/* 668 */             KeyframeAnimations.degreeVec(-17.87827F, 34.62115F, 49.02433F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.3333F, 
/* 669 */             KeyframeAnimations.degreeVec(-17.87827F, 34.62115F, 49.02433F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6667F, 
/* 670 */             KeyframeAnimations.degreeVec(-51.30694F, 32.31058F, 6.87997F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8333F, 
/* 671 */             KeyframeAnimations.degreeVec(35.26439F, 30.0F, -35.26439F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.9167F, 
/* 672 */             KeyframeAnimations.degreeVec(73.75484F, 13.0931F, -19.20518F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5F, 
/* 673 */             KeyframeAnimations.degreeVec(73.75484F, 13.0931F, -19.20518F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.75F, 
/* 674 */             KeyframeAnimations.degreeVec(58.20713F, 21.1064F, -28.7261F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 675 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 677 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 678 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8333F, 
/* 679 */             KeyframeAnimations.posVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.75F, 
/* 680 */             KeyframeAnimations.posVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 681 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 683 */         })).build();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\definitions\WardenAnimation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */