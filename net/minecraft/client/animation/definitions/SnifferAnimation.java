/*     */ package net.minecraft.client.animation.definitions;
/*     */ 
/*     */ import net.minecraft.client.animation.AnimationChannel;
/*     */ import net.minecraft.client.animation.AnimationDefinition;
/*     */ import net.minecraft.client.animation.Keyframe;
/*     */ import net.minecraft.client.animation.KeyframeAnimations;
/*     */ 
/*     */ 
/*     */ public class SnifferAnimation
/*     */ {
/*  11 */   public static final AnimationDefinition BABY_TRANSFORM = AnimationDefinition.Builder.withLength(0.0F)
/*  12 */     .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/*  13 */           new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.2000000476837158D, 1.2000000476837158D, 1.2000000476837158D), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  15 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  16 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  18 */         })).build();
/*     */   
/*  20 */   public static final AnimationDefinition SNIFFER_SNIFFSNIFF = AnimationDefinition.Builder.withLength(8.0F)
/*  21 */     .looping()
/*  22 */     .addAnimation("nose", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/*  23 */           new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5417F, 
/*  24 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/*  25 */             KeyframeAnimations.scaleVec(1.0D, 0.5D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/*  26 */             KeyframeAnimations.scaleVec(1.0D, 2.5D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7917F, 
/*  27 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9167F, 
/*  28 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/*  29 */             KeyframeAnimations.scaleVec(1.0D, 3.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.125F, 
/*  30 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/*  31 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  33 */         })).build();
/*     */   
/*  35 */   public static final AnimationDefinition SNIFFER_LONGSNIFF = AnimationDefinition.Builder.withLength(1.0F)
/*  36 */     .addAnimation("nose", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/*  37 */           new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0833F, 
/*  38 */             KeyframeAnimations.scaleVec(1.0D, 0.699999988079071D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.125F, 
/*  39 */             KeyframeAnimations.scaleVec(1.0D, 3.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25F, 
/*  40 */             KeyframeAnimations.scaleVec(1.0D, 3.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083F, 
/*  41 */             KeyframeAnimations.scaleVec(1.0D, 4.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.8333F, 
/*  42 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/*  43 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/*  45 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  46 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.125F, 
/*  47 */             KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.875F, 
/*  48 */             KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/*  49 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  51 */         })).build();
/*     */   
/*  53 */   public static final AnimationDefinition SNIFFER_WALK = AnimationDefinition.Builder.withLength(2.0F)
/*  54 */     .looping()
/*  55 */     .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  56 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/*  57 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/*  58 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/*  59 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/*  60 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  62 */         })).addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  63 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/*  64 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/*  65 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/*  66 */             KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/*  67 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  69 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  70 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/*  71 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, 
/*  72 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/*  73 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/*  74 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/*  75 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/*  76 */             KeyframeAnimations.degreeVec(-7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  78 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  79 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.67F, -0.67F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/*  80 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, 
/*  81 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/*  82 */             KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/*  83 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.9167F, 
/*  84 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/*  85 */             KeyframeAnimations.posVec(0.0F, 2.67F, -0.67F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  87 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/*  88 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/*  89 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/*  90 */             KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/*  91 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/*  92 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/*  93 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/*  94 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/*  96 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/*  97 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/*  98 */             KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/*  99 */             KeyframeAnimations.posVec(0.0F, 2.22F, 0.78F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 100 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 101 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 102 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 103 */             KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 105 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 106 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 107 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 108 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 109 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 110 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 112 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 113 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 114 */             KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 115 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 116 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 117 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 119 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 120 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 121 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 122 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 123 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 124 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 125 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 127 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 128 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 129 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/* 130 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 131 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 132 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 133 */             KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 135 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 136 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 137 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/* 138 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 139 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 140 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 141 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 142 */             KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 144 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 145 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.22F, 0.78F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, 
/* 146 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/* 147 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 148 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 149 */             KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 150 */             KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 151 */             KeyframeAnimations.posVec(0.0F, 2.22F, 0.78F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 153 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 154 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 155 */             KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 156 */             KeyframeAnimations.degreeVec(1.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 157 */             KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 158 */             KeyframeAnimations.degreeVec(1.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 160 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 161 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2083F, 
/* 162 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.375F, 
/* 163 */             KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 164 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2083F, 
/* 165 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 166 */             KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 167 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 169 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 170 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.1667F, 
/* 171 */             KeyframeAnimations.degreeVec(9.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.875F, 
/* 172 */             KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.25F, 
/* 173 */             KeyframeAnimations.degreeVec(7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.75F, 
/* 174 */             KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, 
/* 175 */             KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 177 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 178 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 179 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 180 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5F, 
/* 181 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, 
/* 182 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 184 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 185 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5F, 
/* 186 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 187 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5F, 
/* 188 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, 
/* 189 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 191 */         })).build();
/*     */   
/* 193 */   public static final AnimationDefinition SNIFFER_SNIFF_SEARCH = AnimationDefinition.Builder.withLength(2.0F)
/* 194 */     .looping()
/* 195 */     .addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 196 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/* 197 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 198 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 199 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 200 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 202 */         })).addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 203 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 204 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 205 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 206 */             KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 207 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 209 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 210 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 211 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, 
/* 212 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 213 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 214 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 215 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 216 */             KeyframeAnimations.degreeVec(-7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 218 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 219 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.67F, -0.67F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 220 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, 
/* 221 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 222 */             KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 223 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.9167F, 
/* 224 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 225 */             KeyframeAnimations.posVec(0.0F, 2.67F, -0.67F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 227 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 228 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/* 229 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 230 */             KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 231 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 232 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 233 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 234 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 236 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 237 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/* 238 */             KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 239 */             KeyframeAnimations.posVec(0.0F, 2.22F, 0.78F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 240 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 241 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 242 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 243 */             KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 245 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 246 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 247 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 248 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 249 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 250 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 252 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 253 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 254 */             KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 255 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 256 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 257 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 259 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 260 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 261 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 262 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 263 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 264 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 265 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 267 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 268 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 269 */             KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/* 270 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, 
/* 271 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 272 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 273 */             KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 275 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 276 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, 
/* 277 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/* 278 */             KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 279 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 280 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 281 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 282 */             KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 284 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 285 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.22F, 0.78F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, 
/* 286 */             KeyframeAnimations.posVec(0.0F, 4.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, 
/* 287 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 288 */             KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 289 */             KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 290 */             KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 291 */             KeyframeAnimations.posVec(0.0F, 2.22F, 0.78F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 293 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 294 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 295 */             KeyframeAnimations.degreeVec(1.25F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 296 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 297 */             KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 299 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 300 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.125F, 
/* 301 */             KeyframeAnimations.degreeVec(33.61503F, 11.46526F, 9.803F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.875F, 
/* 302 */             KeyframeAnimations.degreeVec(34.71128F, 17.67415F, 14.15251F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, 
/* 303 */             KeyframeAnimations.degreeVec(37.21128F, -17.67415F, -14.15251F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.875F, 
/* 304 */             KeyframeAnimations.degreeVec(38.30529F, -21.62827F, -17.40292F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 305 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 307 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 308 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 309 */             KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 311 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 312 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/* 313 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 314 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 315 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 316 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 317 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 318 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 319 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 320 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 322 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 323 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, 
/* 324 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 325 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 326 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 327 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 328 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 329 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 330 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 331 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 333 */         })).addAnimation("nose", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] { 
/* 334 */           new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.0833F, 
/* 335 */             KeyframeAnimations.scaleVec(1.0D, 1.5D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2083F, 
/* 336 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.375F, 
/* 337 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 338 */             KeyframeAnimations.scaleVec(1.0D, 2.5D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.625F, 
/* 339 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.8333F, 
/* 340 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9167F, 
/* 341 */             KeyframeAnimations.scaleVec(1.0D, 2.5D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0833F, 
/* 342 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2917F, 
/* 343 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.3333F, 
/* 344 */             KeyframeAnimations.scaleVec(1.0D, 2.5D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5F, 
/* 345 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.625F, 
/* 346 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.6667F, 
/* 347 */             KeyframeAnimations.scaleVec(1.0D, 3.5D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.8333F, 
/* 348 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, 
/* 349 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 351 */         })).build();
/*     */   
/* 353 */   public static final AnimationDefinition SNIFFER_DIG = AnimationDefinition.Builder.withLength(8.0F)
/* 354 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/* 355 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 356 */             KeyframeAnimations.degreeVec(1.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 357 */             KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 358 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 359 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.5F, 
/* 360 */             KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/* 361 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.5F, 
/* 362 */             KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(4.0F, 
/* 363 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(4.5F, 
/* 364 */             KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(5.6667F, 
/* 365 */             KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(5.8333F, 
/* 366 */             KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(6.0F, 
/* 367 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 369 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 370 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 371 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 372 */             KeyframeAnimations.posVec(0.0F, -7.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 374 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/* 375 */           new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 376 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5417F, 
/* 377 */             KeyframeAnimations.scaleVec(1.0399999618530273D, 0.9800000190734863D, 1.0199999809265137D), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 378 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 380 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] { 
/* 381 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.1667F, 
/* 382 */             KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.4167F, 
/* 383 */             KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5F, 
/* 384 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5833F, 
/* 385 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.875F, 
/* 386 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0833F, 
/* 387 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.5F, 
/* 388 */             KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.6667F, 
/* 389 */             KeyframeAnimations.degreeVec(38.44F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.875F, 
/* 390 */             KeyframeAnimations.degreeVec(10.95951F, 13.57454F, -14.93501F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.2083F, 
/* 391 */             KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.5833F, 
/* 392 */             KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.7917F, 
/* 393 */             KeyframeAnimations.degreeVec(4.2932F, -16.187F, 10.90042F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.125F, 
/* 394 */             KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.4167F, 
/* 395 */             KeyframeAnimations.degreeVec(54.71135F, 7.98009F, -5.56662F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.5F, 
/* 396 */             KeyframeAnimations.degreeVec(55.72895F, -6.77684F, 4.46197F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.5833F, 
/* 397 */             KeyframeAnimations.degreeVec(54.71135F, 7.98009F, -5.56662F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.6667F, 
/* 398 */             KeyframeAnimations.degreeVec(55.72895F, -6.77684F, 4.46197F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.75F, 
/* 399 */             KeyframeAnimations.degreeVec(54.71135F, 7.98009F, -5.56662F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(4.8333F, 
/* 400 */             KeyframeAnimations.degreeVec(55.72895F, -6.77684F, 4.46197F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.0F, 
/* 401 */             KeyframeAnimations.degreeVec(65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.75F, 
/* 402 */             KeyframeAnimations.degreeVec(65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.9167F, 
/* 403 */             KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(6.25F, 
/* 404 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 406 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] { 
/* 407 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.625F, 
/* 408 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 409 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 410 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 411 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.875F, 
/* 412 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0833F, 
/* 413 */             KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.2917F, 
/* 414 */             KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.6667F, 
/* 415 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.2083F, 
/* 416 */             KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.5833F, 
/* 417 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(4.125F, 
/* 418 */             KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(5.0F, 
/* 419 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(5.75F, 
/* 420 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(6.0F, 
/* 421 */             KeyframeAnimations.posVec(0.0F, 1.5F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(6.25F, 
/* 422 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 424 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 425 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 426 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4167F, 
/* 427 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -50.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 428 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(5.9167F, 
/* 429 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(6.0833F, 
/* 430 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -65.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(6.3333F, 
/* 431 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 433 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 434 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 435 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4167F, 
/* 436 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 50.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5833F, 
/* 437 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(5.9167F, 
/* 438 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(6.0833F, 
/* 439 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 65.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(6.3333F, 
/* 440 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 442 */         })).addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 443 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2083F, 
/* 444 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 445 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 447 */         })).addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 448 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2083F, 
/* 449 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2917F, 
/* 450 */             KeyframeAnimations.posVec(-2.0F, -0.75F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 451 */             KeyframeAnimations.posVec(-4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 453 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 454 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 455 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4167F, 
/* 456 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 458 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 459 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 460 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 461 */             KeyframeAnimations.posVec(-2.0F, -0.75F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4167F, 
/* 462 */             KeyframeAnimations.posVec(-4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 464 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 465 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 466 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 467 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 469 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 470 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 471 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4167F, 
/* 472 */             KeyframeAnimations.posVec(-2.0F, -0.75F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 473 */             KeyframeAnimations.posVec(-4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 475 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 476 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2083F, 
/* 477 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 478 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 480 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 481 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2083F, 
/* 482 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2917F, 
/* 483 */             KeyframeAnimations.posVec(2.0F, -0.75F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 484 */             KeyframeAnimations.posVec(4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 486 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 487 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 488 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4167F, 
/* 489 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 491 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 492 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 493 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 494 */             KeyframeAnimations.posVec(2.0F, -0.75F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4167F, 
/* 495 */             KeyframeAnimations.posVec(4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 497 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 498 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 499 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 500 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 502 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 503 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.3333F, 
/* 504 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4167F, 
/* 505 */             KeyframeAnimations.posVec(2.0F, -0.75F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 506 */             KeyframeAnimations.posVec(4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 508 */         })).build();
/*     */   
/* 510 */   public static final AnimationDefinition SNIFFER_STAND_UP = AnimationDefinition.Builder.withLength(3.0F)
/* 511 */     .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 512 */           new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 513 */             KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 514 */             KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.7083F, 
/* 515 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 517 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 518 */           new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -7.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, 
/* 519 */             KeyframeAnimations.posVec(0.0F, -7.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 520 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.7083F, 
/* 521 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 523 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 524 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, 
/* 525 */             KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7083F, 
/* 526 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, 
/* 527 */             KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 528 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 530 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 531 */           new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 532 */             KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 534 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 535 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/* 536 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2083F, 
/* 537 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 539 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 540 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.9167F, 
/* 541 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2083F, 
/* 542 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 544 */         })).addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 545 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 546 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 548 */         })).addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 549 */           new Keyframe(0.0F, KeyframeAnimations.posVec(-4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2083F, 
/* 550 */             KeyframeAnimations.posVec(6.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 551 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 553 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 554 */           new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5833F, 
/* 555 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 557 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 558 */           new Keyframe(0.0833F, KeyframeAnimations.posVec(-4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 559 */             KeyframeAnimations.posVec(6.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5833F, 
/* 560 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 562 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 563 */           new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 564 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 566 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 567 */           new Keyframe(0.1667F, KeyframeAnimations.posVec(-4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4167F, 
/* 568 */             KeyframeAnimations.posVec(6.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 569 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 571 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 572 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 573 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 575 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 576 */           new Keyframe(0.0F, KeyframeAnimations.posVec(4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2083F, 
/* 577 */             KeyframeAnimations.posVec(-6.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583F, 
/* 578 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 580 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 581 */           new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5833F, 
/* 582 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 584 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 585 */           new Keyframe(0.0833F, KeyframeAnimations.posVec(4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333F, 
/* 586 */             KeyframeAnimations.posVec(-6.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5833F, 
/* 587 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 589 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 590 */           new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 591 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 593 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 594 */           new Keyframe(0.1667F, KeyframeAnimations.posVec(4.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4167F, 
/* 595 */             KeyframeAnimations.posVec(-6.0F, -5.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6667F, 
/* 596 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 598 */         })).build();
/*     */   
/* 600 */   public static final AnimationDefinition SNIFFER_BABY_FALL = AnimationDefinition.Builder.withLength(4.0F)
/* 601 */     .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 602 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5F, 
/* 603 */             KeyframeAnimations.degreeVec(-98.91F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.9583F, 
/* 604 */             KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.7083F, 
/* 605 */             KeyframeAnimations.degreeVec(-68.28F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.9583F, 
/* 606 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 608 */         })).addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 609 */           new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 20.0F, 17.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5F, 
/* 610 */             KeyframeAnimations.posVec(0.0F, 25.19F, 20.37F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.9583F, 
/* 611 */             KeyframeAnimations.posVec(0.0F, 20.0F, 17.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.7083F, 
/* 612 */             KeyframeAnimations.posVec(0.0F, 17.06F, 11.25F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.8333F, 
/* 613 */             KeyframeAnimations.posVec(0.0F, 9.85F, 2.2F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.9583F, 
/* 614 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 616 */         })).addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe[] {
/* 617 */           new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.9583F, 
/* 618 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.9167F, 
/* 619 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/* 620 */             KeyframeAnimations.scaleVec(1.0499999523162842D, 0.949999988079071D, 1.0499999523162842D), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0833F, 
/* 621 */             KeyframeAnimations.scaleVec(1.0D, 1.0D, 1.0D), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 623 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 624 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2917F, 
/* 625 */             KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.9583F, 
/* 626 */             KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.75F, 
/* 627 */             KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.9167F, 
/* 628 */             KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0417F, 
/* 629 */             KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.125F, 
/* 630 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 632 */         })).addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 633 */           new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 7.0F, 19.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 634 */             KeyframeAnimations.posVec(0.0F, 7.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.9583F, 
/* 635 */             KeyframeAnimations.posVec(0.0F, 7.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.75F, 
/* 636 */             KeyframeAnimations.posVec(0.0F, 7.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.9583F, 
/* 637 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 639 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 640 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.9583F, 
/* 641 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.7083F, 
/* 642 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.9167F, 
/* 643 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.125F, 
/* 644 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 646 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 647 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.9583F, 
/* 648 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.7083F, 
/* 649 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.9167F, 
/* 650 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(3.125F, 
/* 651 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 653 */         })).addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 654 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 655 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 656 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 657 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.25F, 
/* 658 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.5F, 
/* 659 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.75F, 
/* 660 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/* 661 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 663 */         })).addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 664 */           new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 665 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 667 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 668 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 669 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.625F, 
/* 670 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.875F, 
/* 671 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.125F, 
/* 672 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.375F, 
/* 673 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.625F, 
/* 674 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/* 675 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 677 */         })).addAnimation("right_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 678 */           new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 679 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 681 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 682 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 683 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 684 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 685 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 686 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.25F, 
/* 687 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.5F, 
/* 688 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/* 689 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 691 */         })).addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 692 */           new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 693 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 695 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 696 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 697 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 698 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 699 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.25F, 
/* 700 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.5F, 
/* 701 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.75F, 
/* 702 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/* 703 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 705 */         })).addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 706 */           new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 707 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 709 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 710 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, 
/* 711 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.625F, 
/* 712 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.875F, 
/* 713 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.125F, 
/* 714 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.375F, 
/* 715 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.625F, 
/* 716 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/* 717 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 719 */         })).addAnimation("left_mid_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 720 */           new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 721 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 723 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 724 */           new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, 
/* 725 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 726 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, 
/* 727 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, 
/* 728 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.25F, 
/* 729 */             KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.5F, 
/* 730 */             KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(3.0F, 
/* 731 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 733 */         })).addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe[] {
/* 734 */           new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5F, 
/* 735 */             KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
/*     */         
/* 737 */         })).build();
/*     */   
/* 739 */   public static final AnimationDefinition SNIFFER_HAPPY = AnimationDefinition.Builder.withLength(2.0F)
/* 740 */     .looping()
/* 741 */     .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 742 */           new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, 
/* 743 */             KeyframeAnimations.degreeVec(-32.00206F, 19.3546F, -11.70092F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, 
/* 744 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.5F, 
/* 745 */             KeyframeAnimations.degreeVec(-32.00206F, -19.3546F, 11.70092F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, 
/* 746 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 748 */         })).addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 749 */           new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75F, 
/* 750 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -67.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 751 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.125F, 
/* 752 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, -67.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2917F, 
/* 753 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 755 */         })).addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[] {
/* 756 */           new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.75F, 
/* 757 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 67.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.9583F, 
/* 758 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.125F, 
/* 759 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 67.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.2917F, 
/* 760 */             KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
/*     */         
/* 762 */         })).build();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\definitions\SnifferAnimation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */