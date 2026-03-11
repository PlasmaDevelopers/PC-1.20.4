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
/*     */ public class FrogAnimation
/*     */ {
/*  13 */   public static final AnimationDefinition FROG_CROAK = AnimationDefinition.Builder.withLength(3.0F)
/*  14 */     .addAnimation("croaking_body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  15 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.375F, 
/*  16 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4167F, 
/*  17 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4583F, 
/*  18 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.9583F, 
/*  19 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/*  20 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  22 */         })).addAnimation("croaking_body", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] { 
/*  23 */           new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0D, 0.0D, 0.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.375F, 
/*  24 */             KeyframeAnimations.scaleVec(0.0D, 0.0D, 0.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4167F, 
/*  25 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4583F, 
/*  26 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5417F, 
/*  27 */             KeyframeAnimations.scaleVec(1.2999999523162842D, 2.0999999046325684D, 1.600000023841858D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  28 */             KeyframeAnimations.scaleVec(1.2999999523162842D, 2.0999999046325684D, 1.600000023841858D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7083F, 
/*  29 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.25F, 
/*  30 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.3333F, 
/*  31 */             KeyframeAnimations.scaleVec(1.2999999523162842D, 2.0999999046325684D, 1.600000023841858D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.4167F, 
/*  32 */             KeyframeAnimations.scaleVec(1.2999999523162842D, 2.0999999046325684D, 1.600000023841858D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.5F, 
/*  33 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.5833F, 
/*  34 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.6667F, 
/*  35 */             KeyframeAnimations.scaleVec(1.2999999523162842D, 2.0999999046325684D, 1.600000023841858D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.875F, 
/*  36 */             KeyframeAnimations.scaleVec(1.2999999523162842D, 2.0999999046325684D, 1.600000023841858D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.9583F, 
/*  37 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/*  38 */             KeyframeAnimations.scaleVec(0.0D, 0.0D, 0.0D), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  40 */         })).build();
/*     */   
/*  42 */   public static final AnimationDefinition FROG_WALK = AnimationDefinition.Builder.withLength(1.25F)
/*  43 */     .looping()
/*  44 */     .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  45 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2917F, 
/*  46 */             KeyframeAnimations.degreeVec(7.5F, -2.67F, -7.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  47 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7917F, 
/*  48 */             KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/*  49 */             KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/*  50 */             KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  52 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  53 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.1F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2917F, 
/*  54 */             KeyframeAnimations.posVec(-0.5F, -0.25F, -0.13F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  55 */             KeyframeAnimations.posVec(-0.5F, 0.1F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9583F, 
/*  56 */             KeyframeAnimations.posVec(0.5F, 1.0F, -0.11F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/*  57 */             KeyframeAnimations.posVec(0.0F, 0.1F, -2.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  59 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  60 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.125F, 
/*  61 */             KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4583F, 
/*  62 */             KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  63 */             KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9583F, 
/*  64 */             KeyframeAnimations.degreeVec(7.5F, 2.33F, 7.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/*  65 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  67 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  68 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.5F, 0.1F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2917F, 
/*  69 */             KeyframeAnimations.posVec(-0.5F, 1.0F, 0.12F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  70 */             KeyframeAnimations.posVec(0.0F, 0.1F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9583F, 
/*  71 */             KeyframeAnimations.posVec(0.5F, -0.25F, -0.13F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/*  72 */             KeyframeAnimations.posVec(0.5F, 0.1F, 2.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  74 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  75 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/*  76 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2917F, 
/*  77 */             KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/*  78 */             KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7917F, 
/*  79 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/*  80 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  82 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  83 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.1F, 1.2F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/*  84 */             KeyframeAnimations.posVec(0.0F, 0.1F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4583F, 
/*  85 */             KeyframeAnimations.posVec(0.0F, 2.0F, 1.06F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7917F, 
/*  86 */             KeyframeAnimations.posVec(0.0F, 0.1F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/*  87 */             KeyframeAnimations.posVec(0.0F, 0.1F, 1.2F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  89 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  90 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(-33.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.0417F, 
/*  91 */             KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/*  92 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7917F, 
/*  93 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9583F, 
/*  94 */             KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/*  95 */             KeyframeAnimations.degreeVec(-33.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  97 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  98 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.14F, 0.11F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/*  99 */             KeyframeAnimations.posVec(0.0F, 0.1F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7917F, 
/* 100 */             KeyframeAnimations.posVec(0.0F, 0.1F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/* 101 */             KeyframeAnimations.posVec(0.0F, 2.0F, 0.95F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 102 */             KeyframeAnimations.posVec(0.0F, 1.14F, 0.11F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 104 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 105 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2917F, 
/* 106 */             KeyframeAnimations.degreeVec(-7.5F, 0.33F, 7.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/* 107 */             KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9583F, 
/* 108 */             KeyframeAnimations.degreeVec(-7.5F, 0.33F, -7.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 109 */             KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 111 */         })).build();
/*     */   
/* 113 */   public static final AnimationDefinition FROG_JUMP = AnimationDefinition.Builder.withLength(0.5F)
/* 114 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 115 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 116 */             KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 118 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 119 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 120 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 122 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 123 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(-56.14F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 124 */             KeyframeAnimations.degreeVec(-56.14F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 126 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 127 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 128 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 130 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 131 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(-56.14F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 132 */             KeyframeAnimations.degreeVec(-56.14F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 134 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 135 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 136 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 138 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 139 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 140 */             KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 142 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 143 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 144 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 146 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 147 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 148 */             KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 150 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 151 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 152 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 154 */         })).build();
/*     */   
/* 156 */   public static final AnimationDefinition FROG_TONGUE = AnimationDefinition.Builder.withLength(0.5F)
/* 157 */     .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 158 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.0833F, 
/* 159 */             KeyframeAnimations.degreeVec(-60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4167F, 
/* 160 */             KeyframeAnimations.degreeVec(-60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 161 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 163 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/* 164 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.0833F, 
/* 165 */             KeyframeAnimations.degreeVec(0.998F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4167F, 
/* 166 */             KeyframeAnimations.degreeVec(0.998F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 167 */             KeyframeAnimations.degreeVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 169 */         })).addAnimation("tongue", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 170 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.0833F, 
/* 171 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4167F, 
/* 172 */             KeyframeAnimations.degreeVec(-18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 173 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 175 */         })).addAnimation("tongue", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/* 176 */           new Keyframe(0.0833F, KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 177 */             KeyframeAnimations.scaleVec(0.5D, 1.0D, 5.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4167F, 
/* 178 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 180 */         })).build();
/*     */   
/* 182 */   public static final AnimationDefinition FROG_SWIM = AnimationDefinition.Builder.withLength(1.04167F)
/* 183 */     .looping()
/* 184 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 185 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 186 */             KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 187 */             KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 188 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 190 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 191 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 192 */             KeyframeAnimations.degreeVec(45.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 193 */             KeyframeAnimations.degreeVec(-22.5F, -22.5F, -22.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.875F, 
/* 194 */             KeyframeAnimations.degreeVec(-45.0F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 195 */             KeyframeAnimations.degreeVec(22.5F, 0.0F, 22.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 196 */             KeyframeAnimations.degreeVec(90.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 198 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 199 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.64F, 2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 200 */             KeyframeAnimations.posVec(0.0F, -0.64F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 201 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.875F, 
/* 202 */             KeyframeAnimations.posVec(0.0F, -0.27F, -1.14F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 203 */             KeyframeAnimations.posVec(0.0F, -1.45F, 0.43F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 204 */             KeyframeAnimations.posVec(0.0F, -0.64F, 2.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 206 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 207 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 208 */             KeyframeAnimations.degreeVec(45.0F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 209 */             KeyframeAnimations.degreeVec(-22.5F, 22.5F, 22.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.875F, 
/* 210 */             KeyframeAnimations.degreeVec(-45.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 211 */             KeyframeAnimations.degreeVec(22.5F, 0.0F, -22.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 212 */             KeyframeAnimations.degreeVec(90.0F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 214 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 215 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.64F, 2.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 216 */             KeyframeAnimations.posVec(0.0F, -0.64F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 217 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.875F, 
/* 218 */             KeyframeAnimations.posVec(0.0F, -0.27F, -1.14F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 219 */             KeyframeAnimations.posVec(0.0F, -1.45F, 0.43F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 220 */             KeyframeAnimations.posVec(0.0F, -0.64F, 2.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 222 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 223 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25F, 
/* 224 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 225 */             KeyframeAnimations.degreeVec(67.5F, -45.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7917F, 
/* 226 */             KeyframeAnimations.degreeVec(90.0F, 45.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 227 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 228 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 230 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 231 */           new Keyframe(0.0F, KeyframeAnimations.posVec(-2.5F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25F, 
/* 232 */             KeyframeAnimations.posVec(-2.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 233 */             KeyframeAnimations.posVec(1.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7917F, 
/* 234 */             KeyframeAnimations.posVec(0.58F, 0.0F, -2.83F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 235 */             KeyframeAnimations.posVec(-2.5F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 236 */             KeyframeAnimations.posVec(-2.5F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 238 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 239 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25F, 
/* 240 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 241 */             KeyframeAnimations.degreeVec(67.5F, 45.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7917F, 
/* 242 */             KeyframeAnimations.degreeVec(90.0F, -45.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 243 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 244 */             KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 246 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 247 */           new Keyframe(0.0F, KeyframeAnimations.posVec(2.5F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25F, 
/* 248 */             KeyframeAnimations.posVec(2.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 249 */             KeyframeAnimations.posVec(-1.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7917F, 
/* 250 */             KeyframeAnimations.posVec(-0.58F, 0.0F, -2.83F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 251 */             KeyframeAnimations.posVec(2.5F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0417F, 
/* 252 */             KeyframeAnimations.posVec(2.5F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 254 */         })).build();
/*     */   
/* 256 */   public static final AnimationDefinition FROG_IDLE_WATER = AnimationDefinition.Builder.withLength(3.0F)
/* 257 */     .looping()
/* 258 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 259 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.625F, 
/* 260 */             KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 261 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 263 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 264 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 265 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -45.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 266 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 268 */         })).addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 269 */           new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 270 */             KeyframeAnimations.posVec(-1.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 271 */             KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 273 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 274 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 275 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 276 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.5F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 278 */         })).addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 279 */           new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2083F, 
/* 280 */             KeyframeAnimations.posVec(1.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 281 */             KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 283 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 284 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 285 */             KeyframeAnimations.degreeVec(22.5F, -22.5F, -45.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 286 */             KeyframeAnimations.degreeVec(22.5F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 288 */         })).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 289 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 290 */             KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 291 */             KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 293 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 294 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 295 */             KeyframeAnimations.degreeVec(22.5F, 22.5F, 45.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 296 */             KeyframeAnimations.degreeVec(22.5F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 298 */         })).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 299 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 300 */             KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.0F, 
/* 301 */             KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 303 */         })).build();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\definitions\FrogAnimation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */