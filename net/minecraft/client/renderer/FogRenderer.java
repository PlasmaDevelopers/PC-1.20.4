/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.shaders.FogShape;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.material.FogType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FogRenderer
/*     */ {
/*     */   private static final int WATER_FOG_DISTANCE = 96;
/*     */   
/*     */   public enum FogMode
/*     */   {
/*  34 */     FOG_SKY, FOG_TERRAIN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private static final List<MobEffectFogFunction> MOB_EFFECT_FOG = Lists.newArrayList((Object[])new MobEffectFogFunction[] { new BlindnessFogFunction(), new DarknessFogFunction() });
/*     */   
/*     */   public static final float BIOME_FOG_TRANSITION_TIME = 5000.0F;
/*     */   
/*     */   private static float fogRed;
/*     */   
/*     */   private static float fogGreen;
/*     */   private static float fogBlue;
/*  48 */   private static int targetBiomeFog = -1;
/*  49 */   private static int previousBiomeFog = -1;
/*  50 */   private static long biomeChangedTime = -1L;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setupColor(Camera $$0, float $$1, ClientLevel $$2, int $$3, float $$4) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual getFluidInCamera : ()Lnet/minecraft/world/level/material/FogType;
/*     */     //   4: astore #5
/*     */     //   6: aload_0
/*     */     //   7: invokevirtual getEntity : ()Lnet/minecraft/world/entity/Entity;
/*     */     //   10: astore #6
/*     */     //   12: aload #5
/*     */     //   14: getstatic net/minecraft/world/level/material/FogType.WATER : Lnet/minecraft/world/level/material/FogType;
/*     */     //   17: if_acmpne -> 265
/*     */     //   20: invokestatic getMillis : ()J
/*     */     //   23: lstore #7
/*     */     //   25: aload_2
/*     */     //   26: aload_0
/*     */     //   27: invokevirtual getPosition : ()Lnet/minecraft/world/phys/Vec3;
/*     */     //   30: invokestatic containing : (Lnet/minecraft/core/Position;)Lnet/minecraft/core/BlockPos;
/*     */     //   33: invokevirtual getBiome : (Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;
/*     */     //   36: invokeinterface value : ()Ljava/lang/Object;
/*     */     //   41: checkcast net/minecraft/world/level/biome/Biome
/*     */     //   44: invokevirtual getWaterFogColor : ()I
/*     */     //   47: istore #9
/*     */     //   49: getstatic net/minecraft/client/renderer/FogRenderer.biomeChangedTime : J
/*     */     //   52: lconst_0
/*     */     //   53: lcmp
/*     */     //   54: ifge -> 72
/*     */     //   57: iload #9
/*     */     //   59: putstatic net/minecraft/client/renderer/FogRenderer.targetBiomeFog : I
/*     */     //   62: iload #9
/*     */     //   64: putstatic net/minecraft/client/renderer/FogRenderer.previousBiomeFog : I
/*     */     //   67: lload #7
/*     */     //   69: putstatic net/minecraft/client/renderer/FogRenderer.biomeChangedTime : J
/*     */     //   72: getstatic net/minecraft/client/renderer/FogRenderer.targetBiomeFog : I
/*     */     //   75: bipush #16
/*     */     //   77: ishr
/*     */     //   78: sipush #255
/*     */     //   81: iand
/*     */     //   82: istore #10
/*     */     //   84: getstatic net/minecraft/client/renderer/FogRenderer.targetBiomeFog : I
/*     */     //   87: bipush #8
/*     */     //   89: ishr
/*     */     //   90: sipush #255
/*     */     //   93: iand
/*     */     //   94: istore #11
/*     */     //   96: getstatic net/minecraft/client/renderer/FogRenderer.targetBiomeFog : I
/*     */     //   99: sipush #255
/*     */     //   102: iand
/*     */     //   103: istore #12
/*     */     //   105: getstatic net/minecraft/client/renderer/FogRenderer.previousBiomeFog : I
/*     */     //   108: bipush #16
/*     */     //   110: ishr
/*     */     //   111: sipush #255
/*     */     //   114: iand
/*     */     //   115: istore #13
/*     */     //   117: getstatic net/minecraft/client/renderer/FogRenderer.previousBiomeFog : I
/*     */     //   120: bipush #8
/*     */     //   122: ishr
/*     */     //   123: sipush #255
/*     */     //   126: iand
/*     */     //   127: istore #14
/*     */     //   129: getstatic net/minecraft/client/renderer/FogRenderer.previousBiomeFog : I
/*     */     //   132: sipush #255
/*     */     //   135: iand
/*     */     //   136: istore #15
/*     */     //   138: lload #7
/*     */     //   140: getstatic net/minecraft/client/renderer/FogRenderer.biomeChangedTime : J
/*     */     //   143: lsub
/*     */     //   144: l2f
/*     */     //   145: ldc 5000.0
/*     */     //   147: fdiv
/*     */     //   148: fconst_0
/*     */     //   149: fconst_1
/*     */     //   150: invokestatic clamp : (FFF)F
/*     */     //   153: fstore #16
/*     */     //   155: fload #16
/*     */     //   157: iload #13
/*     */     //   159: i2f
/*     */     //   160: iload #10
/*     */     //   162: i2f
/*     */     //   163: invokestatic lerp : (FFF)F
/*     */     //   166: fstore #17
/*     */     //   168: fload #16
/*     */     //   170: iload #14
/*     */     //   172: i2f
/*     */     //   173: iload #11
/*     */     //   175: i2f
/*     */     //   176: invokestatic lerp : (FFF)F
/*     */     //   179: fstore #18
/*     */     //   181: fload #16
/*     */     //   183: iload #15
/*     */     //   185: i2f
/*     */     //   186: iload #12
/*     */     //   188: i2f
/*     */     //   189: invokestatic lerp : (FFF)F
/*     */     //   192: fstore #19
/*     */     //   194: fload #17
/*     */     //   196: ldc 255.0
/*     */     //   198: fdiv
/*     */     //   199: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   202: fload #18
/*     */     //   204: ldc 255.0
/*     */     //   206: fdiv
/*     */     //   207: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   210: fload #19
/*     */     //   212: ldc 255.0
/*     */     //   214: fdiv
/*     */     //   215: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   218: getstatic net/minecraft/client/renderer/FogRenderer.targetBiomeFog : I
/*     */     //   221: iload #9
/*     */     //   223: if_icmpeq -> 262
/*     */     //   226: iload #9
/*     */     //   228: putstatic net/minecraft/client/renderer/FogRenderer.targetBiomeFog : I
/*     */     //   231: fload #17
/*     */     //   233: invokestatic floor : (F)I
/*     */     //   236: bipush #16
/*     */     //   238: ishl
/*     */     //   239: fload #18
/*     */     //   241: invokestatic floor : (F)I
/*     */     //   244: bipush #8
/*     */     //   246: ishl
/*     */     //   247: ior
/*     */     //   248: fload #19
/*     */     //   250: invokestatic floor : (F)I
/*     */     //   253: ior
/*     */     //   254: putstatic net/minecraft/client/renderer/FogRenderer.previousBiomeFog : I
/*     */     //   257: lload #7
/*     */     //   259: putstatic net/minecraft/client/renderer/FogRenderer.biomeChangedTime : J
/*     */     //   262: goto -> 817
/*     */     //   265: aload #5
/*     */     //   267: getstatic net/minecraft/world/level/material/FogType.LAVA : Lnet/minecraft/world/level/material/FogType;
/*     */     //   270: if_acmpne -> 296
/*     */     //   273: ldc 0.6
/*     */     //   275: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   278: ldc 0.1
/*     */     //   280: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   283: fconst_0
/*     */     //   284: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   287: ldc2_w -1
/*     */     //   290: putstatic net/minecraft/client/renderer/FogRenderer.biomeChangedTime : J
/*     */     //   293: goto -> 817
/*     */     //   296: aload #5
/*     */     //   298: getstatic net/minecraft/world/level/material/FogType.POWDER_SNOW : Lnet/minecraft/world/level/material/FogType;
/*     */     //   301: if_acmpne -> 341
/*     */     //   304: ldc 0.623
/*     */     //   306: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   309: ldc 0.734
/*     */     //   311: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   314: ldc 0.785
/*     */     //   316: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   319: ldc2_w -1
/*     */     //   322: putstatic net/minecraft/client/renderer/FogRenderer.biomeChangedTime : J
/*     */     //   325: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   328: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   331: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   334: fconst_0
/*     */     //   335: invokestatic clearColor : (FFFF)V
/*     */     //   338: goto -> 817
/*     */     //   341: ldc 0.25
/*     */     //   343: ldc 0.75
/*     */     //   345: iload_3
/*     */     //   346: i2f
/*     */     //   347: fmul
/*     */     //   348: ldc 32.0
/*     */     //   350: fdiv
/*     */     //   351: fadd
/*     */     //   352: fstore #7
/*     */     //   354: fconst_1
/*     */     //   355: fload #7
/*     */     //   357: f2d
/*     */     //   358: ldc2_w 0.25
/*     */     //   361: invokestatic pow : (DD)D
/*     */     //   364: d2f
/*     */     //   365: fsub
/*     */     //   366: fstore #7
/*     */     //   368: aload_2
/*     */     //   369: aload_0
/*     */     //   370: invokevirtual getPosition : ()Lnet/minecraft/world/phys/Vec3;
/*     */     //   373: fload_1
/*     */     //   374: invokevirtual getSkyColor : (Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;
/*     */     //   377: astore #8
/*     */     //   379: aload #8
/*     */     //   381: getfield x : D
/*     */     //   384: d2f
/*     */     //   385: fstore #9
/*     */     //   387: aload #8
/*     */     //   389: getfield y : D
/*     */     //   392: d2f
/*     */     //   393: fstore #10
/*     */     //   395: aload #8
/*     */     //   397: getfield z : D
/*     */     //   400: d2f
/*     */     //   401: fstore #11
/*     */     //   403: aload_2
/*     */     //   404: fload_1
/*     */     //   405: invokevirtual getTimeOfDay : (F)F
/*     */     //   408: ldc 6.2831855
/*     */     //   410: fmul
/*     */     //   411: invokestatic cos : (F)F
/*     */     //   414: fconst_2
/*     */     //   415: fmul
/*     */     //   416: ldc 0.5
/*     */     //   418: fadd
/*     */     //   419: fconst_0
/*     */     //   420: fconst_1
/*     */     //   421: invokestatic clamp : (FFF)F
/*     */     //   424: fstore #12
/*     */     //   426: aload_2
/*     */     //   427: invokevirtual getBiomeManager : ()Lnet/minecraft/world/level/biome/BiomeManager;
/*     */     //   430: astore #13
/*     */     //   432: aload_0
/*     */     //   433: invokevirtual getPosition : ()Lnet/minecraft/world/phys/Vec3;
/*     */     //   436: ldc2_w 2.0
/*     */     //   439: ldc2_w 2.0
/*     */     //   442: ldc2_w 2.0
/*     */     //   445: invokevirtual subtract : (DDD)Lnet/minecraft/world/phys/Vec3;
/*     */     //   448: ldc2_w 0.25
/*     */     //   451: invokevirtual scale : (D)Lnet/minecraft/world/phys/Vec3;
/*     */     //   454: astore #14
/*     */     //   456: aload #14
/*     */     //   458: aload_2
/*     */     //   459: aload #13
/*     */     //   461: fload #12
/*     */     //   463: <illegal opcode> fetch : (Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/level/biome/BiomeManager;F)Lnet/minecraft/util/CubicSampler$Vec3Fetcher;
/*     */     //   468: invokestatic gaussianSampleVec3 : (Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/util/CubicSampler$Vec3Fetcher;)Lnet/minecraft/world/phys/Vec3;
/*     */     //   471: astore #15
/*     */     //   473: aload #15
/*     */     //   475: invokevirtual x : ()D
/*     */     //   478: d2f
/*     */     //   479: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   482: aload #15
/*     */     //   484: invokevirtual y : ()D
/*     */     //   487: d2f
/*     */     //   488: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   491: aload #15
/*     */     //   493: invokevirtual z : ()D
/*     */     //   496: d2f
/*     */     //   497: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   500: iload_3
/*     */     //   501: iconst_4
/*     */     //   502: if_icmplt -> 653
/*     */     //   505: aload_2
/*     */     //   506: fload_1
/*     */     //   507: invokevirtual getSunAngle : (F)F
/*     */     //   510: invokestatic sin : (F)F
/*     */     //   513: fconst_0
/*     */     //   514: fcmpl
/*     */     //   515: ifle -> 523
/*     */     //   518: ldc -1.0
/*     */     //   520: goto -> 524
/*     */     //   523: fconst_1
/*     */     //   524: fstore #16
/*     */     //   526: new org/joml/Vector3f
/*     */     //   529: dup
/*     */     //   530: fload #16
/*     */     //   532: fconst_0
/*     */     //   533: fconst_0
/*     */     //   534: invokespecial <init> : (FFF)V
/*     */     //   537: astore #17
/*     */     //   539: aload_0
/*     */     //   540: invokevirtual getLookVector : ()Lorg/joml/Vector3f;
/*     */     //   543: aload #17
/*     */     //   545: invokevirtual dot : (Lorg/joml/Vector3fc;)F
/*     */     //   548: fstore #18
/*     */     //   550: fload #18
/*     */     //   552: fconst_0
/*     */     //   553: fcmpg
/*     */     //   554: ifge -> 560
/*     */     //   557: fconst_0
/*     */     //   558: fstore #18
/*     */     //   560: fload #18
/*     */     //   562: fconst_0
/*     */     //   563: fcmpl
/*     */     //   564: ifle -> 653
/*     */     //   567: aload_2
/*     */     //   568: invokevirtual effects : ()Lnet/minecraft/client/renderer/DimensionSpecialEffects;
/*     */     //   571: aload_2
/*     */     //   572: fload_1
/*     */     //   573: invokevirtual getTimeOfDay : (F)F
/*     */     //   576: fload_1
/*     */     //   577: invokevirtual getSunriseColor : (FF)[F
/*     */     //   580: astore #19
/*     */     //   582: aload #19
/*     */     //   584: ifnull -> 653
/*     */     //   587: fload #18
/*     */     //   589: aload #19
/*     */     //   591: iconst_3
/*     */     //   592: faload
/*     */     //   593: fmul
/*     */     //   594: fstore #18
/*     */     //   596: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   599: fconst_1
/*     */     //   600: fload #18
/*     */     //   602: fsub
/*     */     //   603: fmul
/*     */     //   604: aload #19
/*     */     //   606: iconst_0
/*     */     //   607: faload
/*     */     //   608: fload #18
/*     */     //   610: fmul
/*     */     //   611: fadd
/*     */     //   612: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   615: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   618: fconst_1
/*     */     //   619: fload #18
/*     */     //   621: fsub
/*     */     //   622: fmul
/*     */     //   623: aload #19
/*     */     //   625: iconst_1
/*     */     //   626: faload
/*     */     //   627: fload #18
/*     */     //   629: fmul
/*     */     //   630: fadd
/*     */     //   631: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   634: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   637: fconst_1
/*     */     //   638: fload #18
/*     */     //   640: fsub
/*     */     //   641: fmul
/*     */     //   642: aload #19
/*     */     //   644: iconst_2
/*     */     //   645: faload
/*     */     //   646: fload #18
/*     */     //   648: fmul
/*     */     //   649: fadd
/*     */     //   650: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   653: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   656: fload #9
/*     */     //   658: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   661: fsub
/*     */     //   662: fload #7
/*     */     //   664: fmul
/*     */     //   665: fadd
/*     */     //   666: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   669: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   672: fload #10
/*     */     //   674: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   677: fsub
/*     */     //   678: fload #7
/*     */     //   680: fmul
/*     */     //   681: fadd
/*     */     //   682: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   685: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   688: fload #11
/*     */     //   690: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   693: fsub
/*     */     //   694: fload #7
/*     */     //   696: fmul
/*     */     //   697: fadd
/*     */     //   698: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   701: aload_2
/*     */     //   702: fload_1
/*     */     //   703: invokevirtual getRainLevel : (F)F
/*     */     //   706: fstore #16
/*     */     //   708: fload #16
/*     */     //   710: fconst_0
/*     */     //   711: fcmpl
/*     */     //   712: ifle -> 761
/*     */     //   715: fconst_1
/*     */     //   716: fload #16
/*     */     //   718: ldc 0.5
/*     */     //   720: fmul
/*     */     //   721: fsub
/*     */     //   722: fstore #17
/*     */     //   724: fconst_1
/*     */     //   725: fload #16
/*     */     //   727: ldc_w 0.4
/*     */     //   730: fmul
/*     */     //   731: fsub
/*     */     //   732: fstore #18
/*     */     //   734: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   737: fload #17
/*     */     //   739: fmul
/*     */     //   740: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   743: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   746: fload #17
/*     */     //   748: fmul
/*     */     //   749: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   752: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   755: fload #18
/*     */     //   757: fmul
/*     */     //   758: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   761: aload_2
/*     */     //   762: fload_1
/*     */     //   763: invokevirtual getThunderLevel : (F)F
/*     */     //   766: fstore #17
/*     */     //   768: fload #17
/*     */     //   770: fconst_0
/*     */     //   771: fcmpl
/*     */     //   772: ifle -> 811
/*     */     //   775: fconst_1
/*     */     //   776: fload #17
/*     */     //   778: ldc 0.5
/*     */     //   780: fmul
/*     */     //   781: fsub
/*     */     //   782: fstore #18
/*     */     //   784: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   787: fload #18
/*     */     //   789: fmul
/*     */     //   790: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   793: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   796: fload #18
/*     */     //   798: fmul
/*     */     //   799: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   802: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   805: fload #18
/*     */     //   807: fmul
/*     */     //   808: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   811: ldc2_w -1
/*     */     //   814: putstatic net/minecraft/client/renderer/FogRenderer.biomeChangedTime : J
/*     */     //   817: aload_0
/*     */     //   818: invokevirtual getPosition : ()Lnet/minecraft/world/phys/Vec3;
/*     */     //   821: getfield y : D
/*     */     //   824: d2f
/*     */     //   825: aload_2
/*     */     //   826: invokevirtual getMinBuildHeight : ()I
/*     */     //   829: i2f
/*     */     //   830: fsub
/*     */     //   831: aload_2
/*     */     //   832: invokevirtual getLevelData : ()Lnet/minecraft/client/multiplayer/ClientLevel$ClientLevelData;
/*     */     //   835: invokevirtual getClearColorScale : ()F
/*     */     //   838: fmul
/*     */     //   839: fstore #7
/*     */     //   841: aload #6
/*     */     //   843: fload_1
/*     */     //   844: invokestatic getPriorityFogFunction : (Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/FogRenderer$MobEffectFogFunction;
/*     */     //   847: astore #8
/*     */     //   849: aload #8
/*     */     //   851: ifnull -> 887
/*     */     //   854: aload #6
/*     */     //   856: checkcast net/minecraft/world/entity/LivingEntity
/*     */     //   859: astore #9
/*     */     //   861: aload #8
/*     */     //   863: aload #9
/*     */     //   865: aload #9
/*     */     //   867: aload #8
/*     */     //   869: invokeinterface getMobEffect : ()Lnet/minecraft/world/effect/MobEffect;
/*     */     //   874: invokevirtual getEffect : (Lnet/minecraft/world/effect/MobEffect;)Lnet/minecraft/world/effect/MobEffectInstance;
/*     */     //   877: fload #7
/*     */     //   879: fload_1
/*     */     //   880: invokeinterface getModifiedVoidDarkness : (Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/effect/MobEffectInstance;FF)F
/*     */     //   885: fstore #7
/*     */     //   887: fload #7
/*     */     //   889: fconst_1
/*     */     //   890: fcmpg
/*     */     //   891: ifge -> 954
/*     */     //   894: aload #5
/*     */     //   896: getstatic net/minecraft/world/level/material/FogType.LAVA : Lnet/minecraft/world/level/material/FogType;
/*     */     //   899: if_acmpeq -> 954
/*     */     //   902: aload #5
/*     */     //   904: getstatic net/minecraft/world/level/material/FogType.POWDER_SNOW : Lnet/minecraft/world/level/material/FogType;
/*     */     //   907: if_acmpeq -> 954
/*     */     //   910: fload #7
/*     */     //   912: fconst_0
/*     */     //   913: fcmpg
/*     */     //   914: ifge -> 920
/*     */     //   917: fconst_0
/*     */     //   918: fstore #7
/*     */     //   920: fload #7
/*     */     //   922: fload #7
/*     */     //   924: fmul
/*     */     //   925: fstore #7
/*     */     //   927: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   930: fload #7
/*     */     //   932: fmul
/*     */     //   933: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   936: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   939: fload #7
/*     */     //   941: fmul
/*     */     //   942: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   945: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   948: fload #7
/*     */     //   950: fmul
/*     */     //   951: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   954: fload #4
/*     */     //   956: fconst_0
/*     */     //   957: fcmpl
/*     */     //   958: ifle -> 1025
/*     */     //   961: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   964: fconst_1
/*     */     //   965: fload #4
/*     */     //   967: fsub
/*     */     //   968: fmul
/*     */     //   969: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   972: ldc_w 0.7
/*     */     //   975: fmul
/*     */     //   976: fload #4
/*     */     //   978: fmul
/*     */     //   979: fadd
/*     */     //   980: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   983: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   986: fconst_1
/*     */     //   987: fload #4
/*     */     //   989: fsub
/*     */     //   990: fmul
/*     */     //   991: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   994: ldc 0.6
/*     */     //   996: fmul
/*     */     //   997: fload #4
/*     */     //   999: fmul
/*     */     //   1000: fadd
/*     */     //   1001: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   1004: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1007: fconst_1
/*     */     //   1008: fload #4
/*     */     //   1010: fsub
/*     */     //   1011: fmul
/*     */     //   1012: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1015: ldc 0.6
/*     */     //   1017: fmul
/*     */     //   1018: fload #4
/*     */     //   1020: fmul
/*     */     //   1021: fadd
/*     */     //   1022: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1025: aload #5
/*     */     //   1027: getstatic net/minecraft/world/level/material/FogType.WATER : Lnet/minecraft/world/level/material/FogType;
/*     */     //   1030: if_acmpne -> 1060
/*     */     //   1033: aload #6
/*     */     //   1035: instanceof net/minecraft/client/player/LocalPlayer
/*     */     //   1038: ifeq -> 1054
/*     */     //   1041: aload #6
/*     */     //   1043: checkcast net/minecraft/client/player/LocalPlayer
/*     */     //   1046: invokevirtual getWaterVision : ()F
/*     */     //   1049: fstore #9
/*     */     //   1051: goto -> 1111
/*     */     //   1054: fconst_1
/*     */     //   1055: fstore #9
/*     */     //   1057: goto -> 1111
/*     */     //   1060: aload #6
/*     */     //   1062: instanceof net/minecraft/world/entity/LivingEntity
/*     */     //   1065: ifeq -> 1108
/*     */     //   1068: aload #6
/*     */     //   1070: checkcast net/minecraft/world/entity/LivingEntity
/*     */     //   1073: astore #10
/*     */     //   1075: aload #10
/*     */     //   1077: getstatic net/minecraft/world/effect/MobEffects.NIGHT_VISION : Lnet/minecraft/world/effect/MobEffect;
/*     */     //   1080: invokevirtual hasEffect : (Lnet/minecraft/world/effect/MobEffect;)Z
/*     */     //   1083: ifeq -> 1108
/*     */     //   1086: aload #10
/*     */     //   1088: getstatic net/minecraft/world/effect/MobEffects.DARKNESS : Lnet/minecraft/world/effect/MobEffect;
/*     */     //   1091: invokevirtual hasEffect : (Lnet/minecraft/world/effect/MobEffect;)Z
/*     */     //   1094: ifne -> 1108
/*     */     //   1097: aload #10
/*     */     //   1099: fload_1
/*     */     //   1100: invokestatic getNightVisionScale : (Lnet/minecraft/world/entity/LivingEntity;F)F
/*     */     //   1103: fstore #9
/*     */     //   1105: goto -> 1111
/*     */     //   1108: fconst_0
/*     */     //   1109: fstore #9
/*     */     //   1111: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   1114: fconst_0
/*     */     //   1115: fcmpl
/*     */     //   1116: ifeq -> 1221
/*     */     //   1119: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   1122: fconst_0
/*     */     //   1123: fcmpl
/*     */     //   1124: ifeq -> 1221
/*     */     //   1127: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1130: fconst_0
/*     */     //   1131: fcmpl
/*     */     //   1132: ifeq -> 1221
/*     */     //   1135: fconst_1
/*     */     //   1136: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   1139: fdiv
/*     */     //   1140: fconst_1
/*     */     //   1141: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   1144: fdiv
/*     */     //   1145: fconst_1
/*     */     //   1146: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1149: fdiv
/*     */     //   1150: invokestatic min : (FF)F
/*     */     //   1153: invokestatic min : (FF)F
/*     */     //   1156: fstore #10
/*     */     //   1158: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   1161: fconst_1
/*     */     //   1162: fload #9
/*     */     //   1164: fsub
/*     */     //   1165: fmul
/*     */     //   1166: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   1169: fload #10
/*     */     //   1171: fmul
/*     */     //   1172: fload #9
/*     */     //   1174: fmul
/*     */     //   1175: fadd
/*     */     //   1176: putstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   1179: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   1182: fconst_1
/*     */     //   1183: fload #9
/*     */     //   1185: fsub
/*     */     //   1186: fmul
/*     */     //   1187: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   1190: fload #10
/*     */     //   1192: fmul
/*     */     //   1193: fload #9
/*     */     //   1195: fmul
/*     */     //   1196: fadd
/*     */     //   1197: putstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   1200: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1203: fconst_1
/*     */     //   1204: fload #9
/*     */     //   1206: fsub
/*     */     //   1207: fmul
/*     */     //   1208: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1211: fload #10
/*     */     //   1213: fmul
/*     */     //   1214: fload #9
/*     */     //   1216: fmul
/*     */     //   1217: fadd
/*     */     //   1218: putstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1221: getstatic net/minecraft/client/renderer/FogRenderer.fogRed : F
/*     */     //   1224: getstatic net/minecraft/client/renderer/FogRenderer.fogGreen : F
/*     */     //   1227: getstatic net/minecraft/client/renderer/FogRenderer.fogBlue : F
/*     */     //   1230: fconst_0
/*     */     //   1231: invokestatic clearColor : (FFFF)V
/*     */     //   1234: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #53	-> 0
/*     */     //   #54	-> 6
/*     */     //   #56	-> 12
/*     */     //   #57	-> 20
/*     */     //   #58	-> 25
/*     */     //   #59	-> 49
/*     */     //   #60	-> 57
/*     */     //   #61	-> 62
/*     */     //   #62	-> 67
/*     */     //   #65	-> 72
/*     */     //   #66	-> 84
/*     */     //   #67	-> 96
/*     */     //   #69	-> 105
/*     */     //   #70	-> 117
/*     */     //   #71	-> 129
/*     */     //   #73	-> 138
/*     */     //   #74	-> 155
/*     */     //   #75	-> 168
/*     */     //   #76	-> 181
/*     */     //   #77	-> 194
/*     */     //   #78	-> 202
/*     */     //   #79	-> 210
/*     */     //   #81	-> 218
/*     */     //   #82	-> 226
/*     */     //   #83	-> 231
/*     */     //   #84	-> 257
/*     */     //   #86	-> 262
/*     */     //   #87	-> 273
/*     */     //   #88	-> 278
/*     */     //   #89	-> 283
/*     */     //   #90	-> 287
/*     */     //   #91	-> 296
/*     */     //   #92	-> 304
/*     */     //   #93	-> 309
/*     */     //   #94	-> 314
/*     */     //   #95	-> 319
/*     */     //   #96	-> 325
/*     */     //   #98	-> 341
/*     */     //   #99	-> 354
/*     */     //   #101	-> 368
/*     */     //   #102	-> 379
/*     */     //   #103	-> 387
/*     */     //   #104	-> 395
/*     */     //   #106	-> 403
/*     */     //   #107	-> 426
/*     */     //   #109	-> 432
/*     */     //   #110	-> 456
/*     */     //   #112	-> 473
/*     */     //   #113	-> 482
/*     */     //   #114	-> 491
/*     */     //   #116	-> 500
/*     */     //   #117	-> 505
/*     */     //   #118	-> 526
/*     */     //   #119	-> 539
/*     */     //   #120	-> 550
/*     */     //   #121	-> 557
/*     */     //   #123	-> 560
/*     */     //   #124	-> 567
/*     */     //   #125	-> 582
/*     */     //   #126	-> 587
/*     */     //   #127	-> 596
/*     */     //   #128	-> 615
/*     */     //   #129	-> 634
/*     */     //   #134	-> 653
/*     */     //   #135	-> 669
/*     */     //   #136	-> 685
/*     */     //   #138	-> 701
/*     */     //   #139	-> 708
/*     */     //   #140	-> 715
/*     */     //   #141	-> 724
/*     */     //   #142	-> 734
/*     */     //   #143	-> 743
/*     */     //   #144	-> 752
/*     */     //   #146	-> 761
/*     */     //   #147	-> 768
/*     */     //   #148	-> 775
/*     */     //   #149	-> 784
/*     */     //   #150	-> 793
/*     */     //   #151	-> 802
/*     */     //   #153	-> 811
/*     */     //   #156	-> 817
/*     */     //   #157	-> 841
/*     */     //   #158	-> 849
/*     */     //   #159	-> 854
/*     */     //   #160	-> 861
/*     */     //   #163	-> 887
/*     */     //   #164	-> 910
/*     */     //   #165	-> 917
/*     */     //   #167	-> 920
/*     */     //   #168	-> 927
/*     */     //   #169	-> 936
/*     */     //   #170	-> 945
/*     */     //   #173	-> 954
/*     */     //   #174	-> 961
/*     */     //   #175	-> 983
/*     */     //   #176	-> 1004
/*     */     //   #180	-> 1025
/*     */     //   #181	-> 1033
/*     */     //   #182	-> 1041
/*     */     //   #184	-> 1054
/*     */     //   #186	-> 1060
/*     */     //   #187	-> 1097
/*     */     //   #189	-> 1108
/*     */     //   #192	-> 1111
/*     */     //   #193	-> 1135
/*     */     //   #194	-> 1158
/*     */     //   #195	-> 1179
/*     */     //   #196	-> 1200
/*     */     //   #199	-> 1221
/*     */     //   #200	-> 1234
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	1235	0	$$0	Lnet/minecraft/client/Camera;
/*     */     //   0	1235	1	$$1	F
/*     */     //   0	1235	2	$$2	Lnet/minecraft/client/multiplayer/ClientLevel;
/*     */     //   0	1235	3	$$3	I
/*     */     //   0	1235	4	$$4	F
/*     */     //   6	1229	5	$$5	Lnet/minecraft/world/level/material/FogType;
/*     */     //   12	1223	6	$$6	Lnet/minecraft/world/entity/Entity;
/*     */     //   25	237	7	$$7	J
/*     */     //   49	213	9	$$8	I
/*     */     //   84	178	10	$$9	I
/*     */     //   96	166	11	$$10	I
/*     */     //   105	157	12	$$11	I
/*     */     //   117	145	13	$$12	I
/*     */     //   129	133	14	$$13	I
/*     */     //   138	124	15	$$14	I
/*     */     //   155	107	16	$$15	F
/*     */     //   168	94	17	$$16	F
/*     */     //   181	81	18	$$17	F
/*     */     //   194	68	19	$$18	F
/*     */     //   354	463	7	$$19	F
/*     */     //   379	438	8	$$20	Lnet/minecraft/world/phys/Vec3;
/*     */     //   387	430	9	$$21	F
/*     */     //   395	422	10	$$22	F
/*     */     //   403	414	11	$$23	F
/*     */     //   426	391	12	$$24	F
/*     */     //   432	385	13	$$25	Lnet/minecraft/world/level/biome/BiomeManager;
/*     */     //   456	361	14	$$26	Lnet/minecraft/world/phys/Vec3;
/*     */     //   473	344	15	$$27	Lnet/minecraft/world/phys/Vec3;
/*     */     //   526	127	16	$$28	F
/*     */     //   539	114	17	$$29	Lorg/joml/Vector3f;
/*     */     //   550	103	18	$$30	F
/*     */     //   582	71	19	$$31	[F
/*     */     //   708	109	16	$$32	F
/*     */     //   724	37	17	$$33	F
/*     */     //   734	27	18	$$34	F
/*     */     //   768	49	17	$$35	F
/*     */     //   784	27	18	$$36	F
/*     */     //   841	394	7	$$37	F
/*     */     //   849	386	8	$$38	Lnet/minecraft/client/renderer/FogRenderer$MobEffectFogFunction;
/*     */     //   861	26	9	$$39	Lnet/minecraft/world/entity/LivingEntity;
/*     */     //   1051	3	9	$$40	F
/*     */     //   1057	3	9	$$41	F
/*     */     //   1075	33	10	$$42	Lnet/minecraft/world/entity/LivingEntity;
/*     */     //   1105	3	9	$$43	F
/*     */     //   1111	124	9	$$44	F
/*     */     //   1158	63	10	$$45	F
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setupNoFog() {
/* 203 */     RenderSystem.setShaderFogStart(Float.MAX_VALUE);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static MobEffectFogFunction getPriorityFogFunction(Entity $$0, float $$1) {
/* 208 */     if ($$0 instanceof LivingEntity) { LivingEntity $$2 = (LivingEntity)$$0;
/* 209 */       return MOB_EFFECT_FOG.stream().filter($$2 -> $$2.isEnabled($$0, $$1)).findFirst().orElse(null); }
/*     */     
/* 211 */     return null;
/*     */   }
/*     */   
/*     */   public static void setupFog(Camera $$0, FogMode $$1, float $$2, boolean $$3, float $$4) {
/* 215 */     FogType $$5 = $$0.getFluidInCamera();
/* 216 */     Entity $$6 = $$0.getEntity();
/*     */     
/* 218 */     FogData $$7 = new FogData($$1);
/* 219 */     MobEffectFogFunction $$8 = getPriorityFogFunction($$6, $$4);
/*     */     
/* 221 */     if ($$5 == FogType.LAVA) {
/* 222 */       if ($$6.isSpectator()) {
/* 223 */         $$7.start = -8.0F;
/* 224 */         $$7.end = $$2 * 0.5F;
/* 225 */       } else if ($$6 instanceof LivingEntity && ((LivingEntity)$$6).hasEffect(MobEffects.FIRE_RESISTANCE)) {
/* 226 */         $$7.start = 0.0F;
/* 227 */         $$7.end = 3.0F;
/*     */       } else {
/* 229 */         $$7.start = 0.25F;
/* 230 */         $$7.end = 1.0F;
/*     */       } 
/* 232 */     } else if ($$5 == FogType.POWDER_SNOW) {
/* 233 */       if ($$6.isSpectator()) {
/* 234 */         $$7.start = -8.0F;
/* 235 */         $$7.end = $$2 * 0.5F;
/*     */       } else {
/* 237 */         $$7.start = 0.0F;
/* 238 */         $$7.end = 2.0F;
/*     */       } 
/* 240 */     } else if ($$8 != null) {
/* 241 */       LivingEntity $$9 = (LivingEntity)$$6;
/* 242 */       MobEffectInstance $$10 = $$9.getEffect($$8.getMobEffect());
/*     */       
/* 244 */       if ($$10 != null) {
/* 245 */         $$8.setupFog($$7, $$9, $$10, $$2, $$4);
/*     */       }
/* 247 */     } else if ($$5 == FogType.WATER) {
/* 248 */       $$7.start = -8.0F;
/* 249 */       $$7.end = 96.0F;
/*     */       
/* 251 */       if ($$6 instanceof LocalPlayer) { LocalPlayer $$11 = (LocalPlayer)$$6;
/* 252 */         $$7.end *= Math.max(0.25F, $$11.getWaterVision());
/*     */         
/* 254 */         Holder<Biome> $$12 = $$11.level().getBiome($$11.blockPosition());
/* 255 */         if ($$12.is(BiomeTags.HAS_CLOSER_WATER_FOG)) {
/* 256 */           $$7.end *= 0.85F;
/*     */         } }
/*     */       
/* 259 */       if ($$7.end > $$2) {
/* 260 */         $$7.end = $$2;
/* 261 */         $$7.shape = FogShape.CYLINDER;
/*     */       }
/*     */     
/* 264 */     } else if ($$3) {
/* 265 */       $$7.start = $$2 * 0.05F;
/* 266 */       $$7.end = Math.min($$2, 192.0F) * 0.5F;
/* 267 */     } else if ($$1 == FogMode.FOG_SKY) {
/* 268 */       $$7.start = 0.0F;
/* 269 */       $$7.end = $$2;
/* 270 */       $$7.shape = FogShape.CYLINDER;
/*     */     } else {
/* 272 */       float $$13 = Mth.clamp($$2 / 10.0F, 4.0F, 64.0F);
/* 273 */       $$7.start = $$2 - $$13;
/* 274 */       $$7.end = $$2;
/* 275 */       $$7.shape = FogShape.CYLINDER;
/*     */     } 
/*     */     
/* 278 */     RenderSystem.setShaderFogStart($$7.start);
/* 279 */     RenderSystem.setShaderFogEnd($$7.end);
/* 280 */     RenderSystem.setShaderFogShape($$7.shape);
/*     */   }
/*     */   
/*     */   public static void levelFogColor() {
/* 284 */     RenderSystem.setShaderFogColor(fogRed, fogGreen, fogBlue);
/*     */   }
/*     */   
/*     */   private static class FogData {
/*     */     public final FogRenderer.FogMode mode;
/*     */     public float start;
/*     */     public float end;
/* 291 */     public FogShape shape = FogShape.SPHERE;
/*     */     
/*     */     public FogData(FogRenderer.FogMode $$0) {
/* 294 */       this.mode = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static interface MobEffectFogFunction {
/*     */     MobEffect getMobEffect();
/*     */     
/*     */     void setupFog(FogRenderer.FogData param1FogData, LivingEntity param1LivingEntity, MobEffectInstance param1MobEffectInstance, float param1Float1, float param1Float2);
/*     */     
/*     */     default boolean isEnabled(LivingEntity $$0, float $$1) {
/* 304 */       return $$0.hasEffect(getMobEffect());
/*     */     }
/*     */     
/*     */     default float getModifiedVoidDarkness(LivingEntity $$0, MobEffectInstance $$1, float $$2, float $$3) {
/* 308 */       MobEffectInstance $$4 = $$0.getEffect(getMobEffect());
/* 309 */       if ($$4 != null) {
/* 310 */         if ($$4.endsWithin(19)) {
/* 311 */           $$2 = 1.0F - $$4.getDuration() / 20.0F;
/*     */         } else {
/* 313 */           $$2 = 0.0F;
/*     */         } 
/*     */       }
/* 316 */       return $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BlindnessFogFunction
/*     */     implements MobEffectFogFunction {
/*     */     public MobEffect getMobEffect() {
/* 323 */       return MobEffects.BLINDNESS;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setupFog(FogRenderer.FogData $$0, LivingEntity $$1, MobEffectInstance $$2, float $$3, float $$4) {
/* 328 */       float $$5 = $$2.isInfiniteDuration() ? 5.0F : Mth.lerp(Math.min(1.0F, $$2.getDuration() / 20.0F), $$3, 5.0F);
/* 329 */       if ($$0.mode == FogRenderer.FogMode.FOG_SKY) {
/* 330 */         $$0.start = 0.0F;
/* 331 */         $$0.end = $$5 * 0.8F;
/*     */       } else {
/* 333 */         $$0.start = $$5 * 0.25F;
/* 334 */         $$0.end = $$5;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DarknessFogFunction
/*     */     implements MobEffectFogFunction {
/*     */     public MobEffect getMobEffect() {
/* 342 */       return MobEffects.DARKNESS;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setupFog(FogRenderer.FogData $$0, LivingEntity $$1, MobEffectInstance $$2, float $$3, float $$4) {
/* 347 */       if ($$2.getFactorData().isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 351 */       float $$5 = Mth.lerp(((MobEffectInstance.FactorData)$$2.getFactorData().get()).getFactor($$1, $$4), $$3, 15.0F);
/* 352 */       $$0.start = ($$0.mode == FogRenderer.FogMode.FOG_SKY) ? 0.0F : ($$5 * 0.75F);
/* 353 */       $$0.end = $$5;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getModifiedVoidDarkness(LivingEntity $$0, MobEffectInstance $$1, float $$2, float $$3) {
/* 358 */       if ($$1.getFactorData().isEmpty()) {
/* 359 */         return 0.0F;
/*     */       }
/*     */       
/* 362 */       return 1.0F - ((MobEffectInstance.FactorData)$$1.getFactorData().get()).getFactor($$0, $$3);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\FogRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */