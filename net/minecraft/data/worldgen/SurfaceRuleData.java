/*     */ package net.minecraft.data.worldgen;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.Noises;
/*     */ import net.minecraft.world.level.levelgen.SurfaceRules;
/*     */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SurfaceRuleData
/*     */ {
/*  21 */   private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
/*     */   
/*  23 */   private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
/*  24 */   private static final SurfaceRules.RuleSource WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
/*  25 */   private static final SurfaceRules.RuleSource ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
/*  26 */   private static final SurfaceRules.RuleSource TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
/*  27 */   private static final SurfaceRules.RuleSource RED_SAND = makeStateRule(Blocks.RED_SAND);
/*  28 */   private static final SurfaceRules.RuleSource RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
/*  29 */   private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
/*  30 */   private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
/*  31 */   private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
/*  32 */   private static final SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
/*  33 */   private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
/*  34 */   private static final SurfaceRules.RuleSource MYCELIUM = makeStateRule(Blocks.MYCELIUM);
/*  35 */   private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
/*  36 */   private static final SurfaceRules.RuleSource CALCITE = makeStateRule(Blocks.CALCITE);
/*  37 */   private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
/*  38 */   private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
/*  39 */   private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
/*  40 */   private static final SurfaceRules.RuleSource PACKED_ICE = makeStateRule(Blocks.PACKED_ICE);
/*  41 */   private static final SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
/*  42 */   private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
/*  43 */   private static final SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
/*  44 */   private static final SurfaceRules.RuleSource ICE = makeStateRule(Blocks.ICE);
/*  45 */   private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
/*     */   
/*  47 */   private static final SurfaceRules.RuleSource LAVA = makeStateRule(Blocks.LAVA);
/*  48 */   private static final SurfaceRules.RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
/*  49 */   private static final SurfaceRules.RuleSource SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);
/*  50 */   private static final SurfaceRules.RuleSource SOUL_SOIL = makeStateRule(Blocks.SOUL_SOIL);
/*  51 */   private static final SurfaceRules.RuleSource BASALT = makeStateRule(Blocks.BASALT);
/*  52 */   private static final SurfaceRules.RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
/*  53 */   private static final SurfaceRules.RuleSource WARPED_WART_BLOCK = makeStateRule(Blocks.WARPED_WART_BLOCK);
/*  54 */   private static final SurfaceRules.RuleSource WARPED_NYLIUM = makeStateRule(Blocks.WARPED_NYLIUM);
/*  55 */   private static final SurfaceRules.RuleSource NETHER_WART_BLOCK = makeStateRule(Blocks.NETHER_WART_BLOCK);
/*  56 */   private static final SurfaceRules.RuleSource CRIMSON_NYLIUM = makeStateRule(Blocks.CRIMSON_NYLIUM);
/*     */   
/*  58 */   private static final SurfaceRules.RuleSource ENDSTONE = makeStateRule(Blocks.END_STONE);
/*     */   
/*     */   private static SurfaceRules.RuleSource makeStateRule(Block $$0) {
/*  61 */     return SurfaceRules.state($$0.defaultBlockState());
/*     */   }
/*     */   
/*     */   public static SurfaceRules.RuleSource overworld() {
/*  65 */     return overworldLike(true, false, true);
/*     */   }
/*     */   
/*     */   public static SurfaceRules.RuleSource overworldLike(boolean $$0, boolean $$1, boolean $$2) {
/*  69 */     SurfaceRules.ConditionSource $$3 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(97), 2);
/*  70 */     SurfaceRules.ConditionSource $$4 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0);
/*  71 */     SurfaceRules.ConditionSource $$5 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
/*  72 */     SurfaceRules.ConditionSource $$6 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
/*     */     
/*  74 */     SurfaceRules.ConditionSource $$7 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0);
/*  75 */     SurfaceRules.ConditionSource $$8 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
/*  76 */     SurfaceRules.ConditionSource $$9 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
/*     */ 
/*     */ 
/*     */     
/*  80 */     SurfaceRules.ConditionSource $$10 = SurfaceRules.waterBlockCheck(-1, 0);
/*  81 */     SurfaceRules.ConditionSource $$11 = SurfaceRules.waterBlockCheck(0, 0);
/*     */     
/*  83 */     SurfaceRules.ConditionSource $$12 = SurfaceRules.waterStartCheck(-6, -1);
/*     */     
/*  85 */     SurfaceRules.ConditionSource $$13 = SurfaceRules.hole();
/*  86 */     SurfaceRules.ConditionSource $$14 = SurfaceRules.isBiome(new ResourceKey[] { Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN });
/*  87 */     SurfaceRules.ConditionSource $$15 = SurfaceRules.steep();
/*     */     
/*  89 */     SurfaceRules.RuleSource $$16 = SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/*  90 */           SurfaceRules.ifTrue($$11, GRASS_BLOCK), DIRT
/*     */         });
/*     */ 
/*     */     
/*  94 */     SurfaceRules.RuleSource $$17 = SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/*  95 */           SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND
/*     */         });
/*     */ 
/*     */     
/*  99 */     SurfaceRules.RuleSource $$18 = SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 100 */           SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL
/*     */         });
/*     */ 
/*     */     
/* 104 */     SurfaceRules.ConditionSource $$19 = SurfaceRules.isBiome(new ResourceKey[] { Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH });
/* 105 */     SurfaceRules.ConditionSource $$20 = SurfaceRules.isBiome(new ResourceKey[] { Biomes.DESERT });
/*     */     
/* 107 */     SurfaceRules.RuleSource $$21 = SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 108 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.STONY_PEAKS }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 109 */                 SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.0125D, 0.0125D), CALCITE), STONE
/*     */ 
/*     */               
/* 112 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.STONY_SHORE }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 113 */                 SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.GRAVEL, -0.05D, 0.05D), $$18), STONE
/*     */ 
/*     */               
/* 116 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WINDSWEPT_HILLS }, ), SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D), STONE)), 
/* 117 */           SurfaceRules.ifTrue($$19, $$17), 
/* 118 */           SurfaceRules.ifTrue($$20, $$17), 
/* 119 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.DRIPSTONE_CAVES }, ), STONE)
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 124 */     SurfaceRules.RuleSource $$22 = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.45D, 0.58D), SurfaceRules.ifTrue($$11, POWDER_SNOW));
/* 125 */     SurfaceRules.RuleSource $$23 = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35D, 0.6D), SurfaceRules.ifTrue($$11, POWDER_SNOW));
/*     */     
/* 127 */     SurfaceRules.RuleSource $$24 = SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 128 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.FROZEN_PEAKS }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 129 */                 SurfaceRules.ifTrue($$15, PACKED_ICE), 
/* 130 */                 SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PACKED_ICE, -0.5D, 0.2D), PACKED_ICE), 
/* 131 */                 SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, -0.0625D, 0.025D), ICE), 
/* 132 */                 SurfaceRules.ifTrue($$11, SNOW_BLOCK)
/*     */               
/* 134 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.SNOWY_SLOPES }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 135 */                 SurfaceRules.ifTrue($$15, STONE), $$22, 
/*     */                 
/* 137 */                 SurfaceRules.ifTrue($$11, SNOW_BLOCK)
/*     */               
/* 139 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.JAGGED_PEAKS }, ), STONE), 
/* 140 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.GROVE }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] { $$22, DIRT })), $$21, 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 145 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WINDSWEPT_SAVANNA }, ), SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D), STONE)), 
/* 146 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WINDSWEPT_GRAVELLY_HILLS }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 147 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(2.0D), $$18), 
/* 148 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D), STONE), 
/* 149 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0D), DIRT), $$18
/*     */ 
/*     */               
/* 152 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.MANGROVE_SWAMP }, ), MUD), DIRT
/*     */         });
/*     */ 
/*     */     
/* 156 */     SurfaceRules.RuleSource $$25 = SurfaceRules.sequence(new SurfaceRules.RuleSource[] { 
/* 157 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.FROZEN_PEAKS }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 158 */                 SurfaceRules.ifTrue($$15, PACKED_ICE), 
/* 159 */                 SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PACKED_ICE, 0.0D, 0.2D), PACKED_ICE), 
/* 160 */                 SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0D, 0.025D), ICE), 
/* 161 */                 SurfaceRules.ifTrue($$11, SNOW_BLOCK)
/*     */               
/* 163 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.SNOWY_SLOPES }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 164 */                 SurfaceRules.ifTrue($$15, STONE), $$23, 
/*     */                 
/* 166 */                 SurfaceRules.ifTrue($$11, SNOW_BLOCK)
/*     */               
/* 168 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.JAGGED_PEAKS }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 169 */                 SurfaceRules.ifTrue($$15, STONE), 
/* 170 */                 SurfaceRules.ifTrue($$11, SNOW_BLOCK)
/*     */               
/* 172 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.GROVE }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] { $$23, 
/*     */                 
/* 174 */                 SurfaceRules.ifTrue($$11, SNOW_BLOCK)
/*     */ 
/*     */               
/* 177 */               })), $$21, SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WINDSWEPT_SAVANNA }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 178 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D), STONE), 
/* 179 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(-0.5D), COARSE_DIRT)
/*     */               
/* 181 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WINDSWEPT_GRAVELLY_HILLS }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 182 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(2.0D), $$18), 
/* 183 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D), STONE), 
/* 184 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0D), $$16), $$18
/*     */ 
/*     */               
/* 187 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 188 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D), COARSE_DIRT), 
/* 189 */                 SurfaceRules.ifTrue(surfaceNoiseAbove(-0.95D), PODZOL)
/*     */               
/* 191 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.ICE_SPIKES }, ), SurfaceRules.ifTrue($$11, SNOW_BLOCK)), 
/* 192 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.MANGROVE_SWAMP }, ), MUD), 
/* 193 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.MUSHROOM_FIELDS }, ), MYCELIUM), $$16 });
/*     */ 
/*     */ 
/*     */     
/* 197 */     SurfaceRules.ConditionSource $$26 = SurfaceRules.noiseCondition(Noises.SURFACE, -0.909D, -0.5454D);
/* 198 */     SurfaceRules.ConditionSource $$27 = SurfaceRules.noiseCondition(Noises.SURFACE, -0.1818D, 0.1818D);
/* 199 */     SurfaceRules.ConditionSource $$28 = SurfaceRules.noiseCondition(Noises.SURFACE, 0.5454D, 0.909D);
/*     */     
/* 201 */     SurfaceRules.RuleSource $$29 = SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 202 */           SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 203 */                 SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WOODED_BADLANDS }, ), SurfaceRules.ifTrue($$3, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 204 */                         SurfaceRules.ifTrue($$26, COARSE_DIRT), 
/* 205 */                         SurfaceRules.ifTrue($$27, COARSE_DIRT), 
/* 206 */                         SurfaceRules.ifTrue($$28, COARSE_DIRT), $$16
/*     */ 
/*     */                       
/* 209 */                       }))), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.SWAMP }, ), SurfaceRules.ifTrue($$8, SurfaceRules.ifTrue(SurfaceRules.not($$9), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0D), WATER)))), 
/* 210 */                 SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.MANGROVE_SWAMP }, ), SurfaceRules.ifTrue($$7, SurfaceRules.ifTrue(SurfaceRules.not($$9), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0D), WATER))))
/*     */               
/* 212 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 213 */                 SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 214 */                       SurfaceRules.ifTrue($$4, ORANGE_TERRACOTTA), 
/* 215 */                       SurfaceRules.ifTrue($$6, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 216 */                             SurfaceRules.ifTrue($$26, TERRACOTTA), 
/* 217 */                             SurfaceRules.ifTrue($$27, TERRACOTTA), 
/* 218 */                             SurfaceRules.ifTrue($$28, TERRACOTTA), 
/* 219 */                             SurfaceRules.bandlands()
/*     */                           
/* 221 */                           })), SurfaceRules.ifTrue($$10, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 222 */                             SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, RED_SANDSTONE), RED_SAND
/*     */ 
/*     */                           
/* 225 */                           })), SurfaceRules.ifTrue(SurfaceRules.not($$13), ORANGE_TERRACOTTA), 
/* 226 */                       SurfaceRules.ifTrue($$12, WHITE_TERRACOTTA), $$18
/*     */ 
/*     */                     
/* 229 */                     })), SurfaceRules.ifTrue($$5, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 230 */                       SurfaceRules.ifTrue($$9, SurfaceRules.ifTrue(SurfaceRules.not($$6), ORANGE_TERRACOTTA)), 
/* 231 */                       SurfaceRules.bandlands()
/*     */                     
/* 233 */                     })), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.ifTrue($$12, WHITE_TERRACOTTA))
/*     */               
/* 235 */               })), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue($$10, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 236 */                   SurfaceRules.ifTrue($$14, SurfaceRules.ifTrue($$13, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 237 */                           SurfaceRules.ifTrue($$11, AIR), 
/* 238 */                           SurfaceRules.ifTrue(SurfaceRules.temperature(), ICE), WATER
/*     */ 
/*     */                         
/*     */                         }))), $$25
/*     */                 
/* 243 */                 }))), SurfaceRules.ifTrue($$12, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 244 */                 SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue($$14, SurfaceRules.ifTrue($$13, WATER))), 
/* 245 */                 SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, $$24), 
/* 246 */                 SurfaceRules.ifTrue($$19, SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SANDSTONE)), 
/* 247 */                 SurfaceRules.ifTrue($$20, SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SANDSTONE))
/*     */               
/* 249 */               })), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 250 */                 SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS }, ), STONE), 
/* 251 */                 SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN }, ), $$17), $$18
/*     */               }))
/*     */         });
/*     */ 
/*     */     
/* 256 */     ImmutableList.Builder<SurfaceRules.RuleSource> $$30 = ImmutableList.builder();
/*     */     
/* 258 */     if ($$1) {
/* 259 */       $$30.add(SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK));
/*     */     }
/* 261 */     if ($$2) {
/* 262 */       $$30.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK));
/*     */     }
/* 264 */     SurfaceRules.RuleSource $$31 = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), $$29);
/* 265 */     $$30.add($$0 ? $$31 : $$29);
/* 266 */     $$30.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), DEEPSLATE));
/*     */     
/* 268 */     return SurfaceRules.sequence((SurfaceRules.RuleSource[])$$30.build().toArray($$0 -> new SurfaceRules.RuleSource[$$0]));
/*     */   }
/*     */   
/*     */   public static SurfaceRules.RuleSource nether() {
/* 272 */     SurfaceRules.ConditionSource $$0 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);
/* 273 */     SurfaceRules.ConditionSource $$1 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(32), 0);
/*     */     
/* 275 */     SurfaceRules.ConditionSource $$2 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0);
/* 276 */     SurfaceRules.ConditionSource $$3 = SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0));
/*     */     
/* 278 */     SurfaceRules.ConditionSource $$4 = SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0);
/*     */     
/* 280 */     SurfaceRules.ConditionSource $$5 = SurfaceRules.hole();
/*     */     
/* 282 */     SurfaceRules.ConditionSource $$6 = SurfaceRules.noiseCondition(Noises.SOUL_SAND_LAYER, -0.012D);
/* 283 */     SurfaceRules.ConditionSource $$7 = SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -0.012D);
/* 284 */     SurfaceRules.ConditionSource $$8 = SurfaceRules.noiseCondition(Noises.PATCH, -0.012D);
/* 285 */     SurfaceRules.ConditionSource $$9 = SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.54D);
/* 286 */     SurfaceRules.ConditionSource $$10 = SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17D);
/* 287 */     SurfaceRules.ConditionSource $$11 = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0D);
/*     */     
/* 289 */     SurfaceRules.RuleSource $$12 = SurfaceRules.ifTrue($$8, SurfaceRules.ifTrue($$2, SurfaceRules.ifTrue($$3, GRAVEL)));
/*     */     
/* 291 */     return SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 292 */           SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK), 
/* 293 */           SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK), 
/* 294 */           SurfaceRules.ifTrue($$4, NETHERRACK), 
/* 295 */           SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.BASALT_DELTAS }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 296 */                 SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, BASALT), 
/* 297 */                 SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/*     */                       
/* 299 */                       $$12, SurfaceRules.ifTrue($$11, BASALT), BLACKSTONE
/*     */                     
/*     */                     }))
/*     */               
/* 303 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.SOUL_SAND_VALLEY }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 304 */                 SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 305 */                       SurfaceRules.ifTrue($$11, SOUL_SAND), SOUL_SOIL
/*     */ 
/*     */                     
/* 308 */                     })), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/*     */                       
/* 310 */                       $$12, SurfaceRules.ifTrue($$11, SOUL_SAND), SOUL_SOIL
/*     */                     
/*     */                     }))
/*     */               
/* 314 */               })), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 315 */                 SurfaceRules.ifTrue(SurfaceRules.not($$1), SurfaceRules.ifTrue($$5, LAVA)), 
/* 316 */                 SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WARPED_FOREST }, ), SurfaceRules.ifTrue(SurfaceRules.not($$9), SurfaceRules.ifTrue($$0, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 317 */                           SurfaceRules.ifTrue($$10, WARPED_WART_BLOCK), WARPED_NYLIUM
/*     */ 
/*     */                         
/* 320 */                         })))), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.CRIMSON_FOREST }, ), SurfaceRules.ifTrue(SurfaceRules.not($$9), SurfaceRules.ifTrue($$0, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 321 */                           SurfaceRules.ifTrue($$10, NETHER_WART_BLOCK), CRIMSON_NYLIUM
/*     */                         
/*     */                         }))))
/*     */               
/* 325 */               })), SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.NETHER_WASTES }, ), SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 326 */                 SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.ifTrue($$6, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 327 */                         SurfaceRules.ifTrue(SurfaceRules.not($$5), SurfaceRules.ifTrue($$2, SurfaceRules.ifTrue($$3, SOUL_SAND))), NETHERRACK
/*     */ 
/*     */                       
/* 330 */                       }))), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue($$0, SurfaceRules.ifTrue($$3, SurfaceRules.ifTrue($$7, SurfaceRules.sequence(new SurfaceRules.RuleSource[] {
/* 331 */                             SurfaceRules.ifTrue($$1, GRAVEL), 
/* 332 */                             SurfaceRules.ifTrue(SurfaceRules.not($$5), GRAVEL)
/*     */                           })))))
/*     */               })), NETHERRACK
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static SurfaceRules.RuleSource end() {
/* 340 */     return ENDSTONE;
/*     */   }
/*     */   
/*     */   public static SurfaceRules.RuleSource air() {
/* 344 */     return AIR;
/*     */   }
/*     */   
/*     */   private static SurfaceRules.ConditionSource surfaceNoiseAbove(double $$0) {
/* 348 */     return SurfaceRules.noiseCondition(Noises.SURFACE, $$0 / 8.25D, Double.MAX_VALUE);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\SurfaceRuleData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */