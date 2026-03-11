/*     */ package net.minecraft.util.datafix.fixes;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicLike;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.OptionalDynamic;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ 
/*     */ public class WorldGenSettingsFix extends DataFix {
/*     */   private static final String VILLAGE = "minecraft:village";
/*     */   private static final String DESERT_PYRAMID = "minecraft:desert_pyramid";
/*     */   private static final String IGLOO = "minecraft:igloo";
/*     */   private static final String JUNGLE_TEMPLE = "minecraft:jungle_pyramid";
/*     */   private static final String SWAMP_HUT = "minecraft:swamp_hut";
/*     */   private static final String PILLAGER_OUTPOST = "minecraft:pillager_outpost";
/*     */   private static final String END_CITY = "minecraft:endcity";
/*     */   private static final String WOODLAND_MANSION = "minecraft:mansion";
/*     */   private static final String OCEAN_MONUMENT = "minecraft:monument";
/*     */   
/*     */   public WorldGenSettingsFix(Schema $$0) {
/*  28 */     super($$0, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/*  33 */     return fixTypeEverywhereTyped("WorldGenSettings building", getInputSchema().getType(References.WORLD_GEN_SETTINGS), $$0 -> $$0.update(DSL.remainderFinder(), WorldGenSettingsFix::fix));
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> noise(long $$0, DynamicLike<T> $$1, Dynamic<T> $$2, Dynamic<T> $$3) {
/*  37 */     return $$1.createMap((Map)ImmutableMap.of($$1
/*  38 */           .createString("type"), $$1.createString("minecraft:noise"), $$1
/*  39 */           .createString("biome_source"), $$3, $$1
/*  40 */           .createString("seed"), $$1.createLong($$0), $$1
/*  41 */           .createString("settings"), $$2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Dynamic<T> vanillaBiomeSource(Dynamic<T> $$0, long $$1, boolean $$2, boolean $$3) {
/*  49 */     ImmutableMap.Builder<Dynamic<T>, Dynamic<T>> $$4 = ImmutableMap.builder().put($$0.createString("type"), $$0.createString("minecraft:vanilla_layered")).put($$0.createString("seed"), $$0.createLong($$1)).put($$0.createString("large_biomes"), $$0.createBoolean($$3));
/*     */     
/*  51 */     if ($$2) {
/*  52 */       $$4.put($$0.createString("legacy_biome_init_layer"), $$0.createBoolean($$2));
/*     */     }
/*     */     
/*  55 */     return $$0.createMap((Map)$$4.build());
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
/*  68 */   private static final ImmutableMap<String, StructureFeatureConfiguration> DEFAULTS = ImmutableMap.builder()
/*  69 */     .put("minecraft:village", new StructureFeatureConfiguration(32, 8, 10387312))
/*  70 */     .put("minecraft:desert_pyramid", new StructureFeatureConfiguration(32, 8, 14357617))
/*  71 */     .put("minecraft:igloo", new StructureFeatureConfiguration(32, 8, 14357618))
/*  72 */     .put("minecraft:jungle_pyramid", new StructureFeatureConfiguration(32, 8, 14357619))
/*  73 */     .put("minecraft:swamp_hut", new StructureFeatureConfiguration(32, 8, 14357620))
/*  74 */     .put("minecraft:pillager_outpost", new StructureFeatureConfiguration(32, 8, 165745296))
/*  75 */     .put("minecraft:monument", new StructureFeatureConfiguration(32, 5, 10387313))
/*  76 */     .put("minecraft:endcity", new StructureFeatureConfiguration(20, 11, 10387313))
/*  77 */     .put("minecraft:mansion", new StructureFeatureConfiguration(80, 20, 10387319))
/*  78 */     .build();
/*     */   private static final class StructureFeatureConfiguration { public static final Codec<StructureFeatureConfiguration> CODEC; final int spacing; final int separation; final int salt;
/*     */     static {
/*  81 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("spacing").forGetter(()), (App)Codec.INT.fieldOf("separation").forGetter(()), (App)Codec.INT.fieldOf("salt").forGetter(())).apply((Applicative)$$0, StructureFeatureConfiguration::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StructureFeatureConfiguration(int $$0, int $$1, int $$2) {
/*  92 */       this.spacing = $$0;
/*  93 */       this.separation = $$1;
/*  94 */       this.salt = $$2;
/*     */     }
/*     */     
/*     */     public <T> Dynamic<T> serialize(DynamicOps<T> $$0) {
/*  98 */       return new Dynamic($$0, CODEC.encodeStart($$0, this).result().orElse($$0.emptyMap()));
/*     */     } }
/*     */   
/*     */   private static <T> Dynamic<T> fix(Dynamic<T> $$0) {
/*     */     Dynamic<T> $$27;
/* 103 */     DynamicOps<T> $$1 = $$0.getOps();
/*     */     
/* 105 */     long $$2 = $$0.get("RandomSeed").asLong(0L);
/*     */     
/* 107 */     Optional<String> $$3 = $$0.get("generatorName").asString().map($$0 -> $$0.toLowerCase(Locale.ROOT)).result();
/*     */     
/* 109 */     Optional<String> $$4 = $$0.get("legacy_custom_options").asString().result().map(Optional::of).orElseGet(() -> $$0.equals(Optional.of("customized")) ? $$1.get("generatorOptions").asString().result() : Optional.empty());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     boolean $$5 = false;
/* 117 */     if ($$3.equals(Optional.of("customized")))
/* 118 */     { Dynamic<T> $$6 = defaultOverworld($$0, $$2); }
/* 119 */     else if ($$3.isEmpty())
/* 120 */     { Dynamic<T> $$7 = defaultOverworld($$0, $$2); }
/*     */     else
/* 122 */     { Dynamic<T> $$10, $$11, $$22; String str; boolean $$28; byte b; boolean $$29; OptionalDynamic<T> $$8; ImmutableMap.Builder<T, T> $$30; Map<Dynamic<T>, Dynamic<T>> $$9; OptionalDynamic<T> $$12; OptionalDynamic<?> $$13; Optional<String> $$14; Dynamic<T> $$17, $$18, $$21; switch ((String)$$3.get())
/*     */       { case "flat":
/* 124 */           $$8 = $$0.get("generatorOptions");
/* 125 */           $$9 = fixFlatStructures($$1, $$8);
/*     */           
/* 127 */           $$10 = $$0.createMap((Map)ImmutableMap.of($$0
/* 128 */                 .createString("type"), $$0.createString("minecraft:flat"), $$0
/* 129 */                 .createString("settings"), $$0.createMap((Map)ImmutableMap.of($$0
/* 130 */                     .createString("structures"), $$0.createMap($$9), $$0
/* 131 */                     .createString("layers"), $$8.get("layers").result().orElseGet(() -> $$0.createList(Stream.of(new Dynamic[] { $$0.createMap((Map)ImmutableMap.of($$0.createString("height"), $$0.createInt(1), $$0.createString("block"), $$0.createString("minecraft:bedrock"))), $$0.createMap((Map)ImmutableMap.of($$0.createString("height"), $$0.createInt(2), $$0.createString("block"), $$0.createString("minecraft:dirt"))), $$0.createMap((Map)ImmutableMap.of($$0.createString("height"), $$0.createInt(1), $$0.createString("block"), $$0.createString("minecraft:grass_block"))) }))), $$0
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
/* 145 */                     .createString("biome"), $$0.createString($$8.get("biome").asString("minecraft:plains"))))));
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
/* 201 */           $$28 = $$0.get("MapFeatures").asBoolean(true);
/* 202 */           $$29 = $$0.get("BonusChest").asBoolean(false);
/*     */           
/* 204 */           $$30 = ImmutableMap.builder();
/* 205 */           $$30.put($$1.createString("seed"), $$1.createLong($$2));
/* 206 */           $$30.put($$1.createString("generate_features"), $$1.createBoolean($$28));
/* 207 */           $$30.put($$1.createString("bonus_chest"), $$1.createBoolean($$29));
/* 208 */           $$30.put($$1.createString("dimensions"), vanillaLevels($$0, $$2, $$10, $$5));
/* 209 */           $$4.ifPresent($$2 -> $$0.put($$1.createString("legacy_custom_options"), $$1.createString($$2)));
/*     */           
/* 211 */           return new Dynamic($$1, $$1.createMap((Map)$$30.build()));case "debug_all_block_states": $$11 = $$0.createMap((Map)ImmutableMap.of($$0.createString("type"), $$0.createString("minecraft:debug"))); $$28 = $$0.get("MapFeatures").asBoolean(true); $$29 = $$0.get("BonusChest").asBoolean(false); $$30 = ImmutableMap.builder(); $$30.put($$1.createString("seed"), $$1.createLong($$2)); $$30.put($$1.createString("generate_features"), $$1.createBoolean($$28)); $$30.put($$1.createString("bonus_chest"), $$1.createBoolean($$29)); $$30.put($$1.createString("dimensions"), vanillaLevels($$0, $$2, $$11, $$5)); $$4.ifPresent($$2 -> $$0.put($$1.createString("legacy_custom_options"), $$1.createString($$2))); return new Dynamic($$1, $$1.createMap((Map)$$30.build()));case "buffet": $$12 = $$0.get("generatorOptions"); $$13 = $$12.get("chunk_generator"); $$14 = $$13.get("type").asString().result(); if (Objects.equals($$14, Optional.of("minecraft:caves"))) { Dynamic<T> $$15 = $$0.createString("minecraft:caves"); $$5 = true; } else if (Objects.equals($$14, Optional.of("minecraft:floating_islands"))) { Dynamic<T> $$16 = $$0.createString("minecraft:floating_islands"); } else { $$17 = $$0.createString("minecraft:overworld"); }  $$18 = $$12.get("biome_source").result().orElseGet(() -> $$0.createMap((Map)ImmutableMap.of($$0.createString("type"), $$0.createString("minecraft:fixed")))); if ($$18.get("type").asString().result().equals(Optional.of("minecraft:fixed"))) { String $$19 = $$18.get("options").get("biomes").asStream().findFirst().flatMap($$0 -> $$0.asString().result()).orElse("minecraft:ocean"); Dynamic<T> $$20 = $$18.remove("options").set("biome", $$0.createString($$19)); } else { $$21 = $$18; }  $$22 = noise($$2, (DynamicLike<T>)$$0, $$17, $$21); $$28 = $$0.get("MapFeatures").asBoolean(true); $$29 = $$0.get("BonusChest").asBoolean(false); $$30 = ImmutableMap.builder(); $$30.put($$1.createString("seed"), $$1.createLong($$2)); $$30.put($$1.createString("generate_features"), $$1.createBoolean($$28)); $$30.put($$1.createString("bonus_chest"), $$1.createBoolean($$29)); $$30.put($$1.createString("dimensions"), vanillaLevels($$0, $$2, $$22, $$5)); $$4.ifPresent($$2 -> $$0.put($$1.createString("legacy_custom_options"), $$1.createString($$2))); return new Dynamic($$1, $$1.createMap((Map)$$30.build())); }  boolean $$23 = ((String)$$3.get()).equals("default"); boolean $$24 = (((String)$$3.get()).equals("default_1_1") || ($$23 && $$0.get("generatorVersion").asInt(0) == 0)); boolean $$25 = ((String)$$3.get()).equals("amplified"); boolean $$26 = ((String)$$3.get()).equals("largebiomes"); $$27 = noise($$2, (DynamicLike<T>)$$0, $$0.createString($$25 ? "minecraft:amplified" : "minecraft:overworld"), vanillaBiomeSource($$0, $$2, $$24, $$26)); }  boolean bool1 = $$0.get("MapFeatures").asBoolean(true); boolean bool2 = $$0.get("BonusChest").asBoolean(false); ImmutableMap.Builder builder = ImmutableMap.builder(); builder.put($$1.createString("seed"), $$1.createLong($$2)); builder.put($$1.createString("generate_features"), $$1.createBoolean(bool1)); builder.put($$1.createString("bonus_chest"), $$1.createBoolean(bool2)); builder.put($$1.createString("dimensions"), vanillaLevels($$0, $$2, $$27, $$5)); $$4.ifPresent($$2 -> $$0.put($$1.createString("legacy_custom_options"), $$1.createString($$2))); return new Dynamic($$1, $$1.createMap((Map)builder.build()));
/*     */   }
/*     */   
/*     */   protected static <T> Dynamic<T> defaultOverworld(Dynamic<T> $$0, long $$1) {
/* 215 */     return noise($$1, (DynamicLike<T>)$$0, $$0.createString("minecraft:overworld"), vanillaBiomeSource($$0, $$1, false, false));
/*     */   }
/*     */   
/*     */   protected static <T> T vanillaLevels(Dynamic<T> $$0, long $$1, Dynamic<T> $$2, boolean $$3) {
/* 219 */     DynamicOps<T> $$4 = $$0.getOps();
/* 220 */     return (T)$$4.createMap((Map)ImmutableMap.of($$4
/* 221 */           .createString("minecraft:overworld"), $$4.createMap((Map)ImmutableMap.of($$4
/* 222 */               .createString("type"), $$4.createString("minecraft:overworld" + ($$3 ? "_caves" : "")), $$4
/* 223 */               .createString("generator"), $$2.getValue())), $$4
/*     */           
/* 225 */           .createString("minecraft:the_nether"), $$4.createMap((Map)ImmutableMap.of($$4
/* 226 */               .createString("type"), $$4.createString("minecraft:the_nether"), $$4
/* 227 */               .createString("generator"), noise($$1, (DynamicLike<T>)$$0, $$0.createString("minecraft:nether"), $$0.createMap((Map)ImmutableMap.of($$0
/* 228 */                     .createString("type"), $$0.createString("minecraft:multi_noise"), $$0
/* 229 */                     .createString("seed"), $$0.createLong($$1), $$0
/* 230 */                     .createString("preset"), $$0.createString("minecraft:nether"))))
/* 231 */               .getValue())), $$4
/*     */           
/* 233 */           .createString("minecraft:the_end"), $$4.createMap((Map)ImmutableMap.of($$4
/* 234 */               .createString("type"), $$4.createString("minecraft:the_end"), $$4
/* 235 */               .createString("generator"), noise($$1, (DynamicLike<T>)$$0, $$0.createString("minecraft:end"), $$0.createMap((Map)ImmutableMap.of($$0
/* 236 */                     .createString("type"), $$0.createString("minecraft:the_end"), $$0
/* 237 */                     .createString("seed"), $$0.createLong($$1))))
/* 238 */               .getValue()))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Map<Dynamic<T>, Dynamic<T>> fixFlatStructures(DynamicOps<T> $$0, OptionalDynamic<T> $$1) {
/* 244 */     MutableInt $$2 = new MutableInt(32);
/* 245 */     MutableInt $$3 = new MutableInt(3);
/* 246 */     MutableInt $$4 = new MutableInt(128);
/* 247 */     MutableBoolean $$5 = new MutableBoolean(false);
/* 248 */     Map<String, StructureFeatureConfiguration> $$6 = Maps.newHashMap();
/*     */     
/* 250 */     if ($$1.result().isEmpty()) {
/* 251 */       $$5.setTrue();
/* 252 */       $$6.put("minecraft:village", (StructureFeatureConfiguration)DEFAULTS.get("minecraft:village"));
/*     */     } 
/*     */     
/* 255 */     $$1.get("structures").flatMap(Dynamic::getMapValues).result().ifPresent($$5 -> $$5.forEach(()));
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
/* 318 */     ImmutableMap.Builder<Dynamic<T>, Dynamic<T>> $$7 = ImmutableMap.builder();
/* 319 */     $$7.put($$1.createString("structures"), $$1.createMap((Map)$$6.entrySet().stream().collect(Collectors.toMap($$1 -> $$0.createString((String)$$1.getKey()), $$1 -> ((StructureFeatureConfiguration)$$1.getValue()).serialize($$0)))));
/*     */ 
/*     */ 
/*     */     
/* 323 */     if ($$5.isTrue()) {
/* 324 */       $$7.put($$1.createString("stronghold"), $$1.createMap((Map)ImmutableMap.of($$1
/* 325 */               .createString("distance"), $$1.createInt($$2.getValue().intValue()), $$1
/* 326 */               .createString("spread"), $$1.createInt($$3.getValue().intValue()), $$1
/* 327 */               .createString("count"), $$1.createInt($$4.getValue().intValue()))));
/*     */     }
/*     */     
/* 330 */     return (Map<Dynamic<T>, Dynamic<T>>)$$7.build();
/*     */   }
/*     */   
/*     */   private static int getInt(String $$0, int $$1) {
/* 334 */     return NumberUtils.toInt($$0, $$1);
/*     */   }
/*     */   
/*     */   private static int getInt(String $$0, int $$1, int $$2) {
/* 338 */     return Math.max($$2, getInt($$0, $$1));
/*     */   }
/*     */   
/*     */   private static void setSpacing(Map<String, StructureFeatureConfiguration> $$0, String $$1, String $$2, int $$3) {
/* 342 */     StructureFeatureConfiguration $$4 = $$0.getOrDefault($$1, (StructureFeatureConfiguration)DEFAULTS.get($$1));
/* 343 */     int $$5 = getInt($$2, $$4.spacing, $$3);
/* 344 */     $$0.put($$1, new StructureFeatureConfiguration($$5, $$4.separation, $$4.salt));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\WorldGenSettingsFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */