/*     */ package net.minecraft.util.datafix.fixes;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ 
/*     */ public class LevelDataGeneratorOptionsFix extends DataFix {
/*     */   static {
/*  29 */     MAP = (Map<String, String>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put("0", "minecraft:ocean");
/*     */           $$0.put("1", "minecraft:plains");
/*     */           $$0.put("2", "minecraft:desert");
/*     */           $$0.put("3", "minecraft:mountains");
/*     */           $$0.put("4", "minecraft:forest");
/*     */           $$0.put("5", "minecraft:taiga");
/*     */           $$0.put("6", "minecraft:swamp");
/*     */           $$0.put("7", "minecraft:river");
/*     */           $$0.put("8", "minecraft:nether");
/*     */           $$0.put("9", "minecraft:the_end");
/*     */           $$0.put("10", "minecraft:frozen_ocean");
/*     */           $$0.put("11", "minecraft:frozen_river");
/*     */           $$0.put("12", "minecraft:snowy_tundra");
/*     */           $$0.put("13", "minecraft:snowy_mountains");
/*     */           $$0.put("14", "minecraft:mushroom_fields");
/*     */           $$0.put("15", "minecraft:mushroom_field_shore");
/*     */           $$0.put("16", "minecraft:beach");
/*     */           $$0.put("17", "minecraft:desert_hills");
/*     */           $$0.put("18", "minecraft:wooded_hills");
/*     */           $$0.put("19", "minecraft:taiga_hills");
/*     */           $$0.put("20", "minecraft:mountain_edge");
/*     */           $$0.put("21", "minecraft:jungle");
/*     */           $$0.put("22", "minecraft:jungle_hills");
/*     */           $$0.put("23", "minecraft:jungle_edge");
/*     */           $$0.put("24", "minecraft:deep_ocean");
/*     */           $$0.put("25", "minecraft:stone_shore");
/*     */           $$0.put("26", "minecraft:snowy_beach");
/*     */           $$0.put("27", "minecraft:birch_forest");
/*     */           $$0.put("28", "minecraft:birch_forest_hills");
/*     */           $$0.put("29", "minecraft:dark_forest");
/*     */           $$0.put("30", "minecraft:snowy_taiga");
/*     */           $$0.put("31", "minecraft:snowy_taiga_hills");
/*     */           $$0.put("32", "minecraft:giant_tree_taiga");
/*     */           $$0.put("33", "minecraft:giant_tree_taiga_hills");
/*     */           $$0.put("34", "minecraft:wooded_mountains");
/*     */           $$0.put("35", "minecraft:savanna");
/*     */           $$0.put("36", "minecraft:savanna_plateau");
/*     */           $$0.put("37", "minecraft:badlands");
/*     */           $$0.put("38", "minecraft:wooded_badlands_plateau");
/*     */           $$0.put("39", "minecraft:badlands_plateau");
/*     */           $$0.put("40", "minecraft:small_end_islands");
/*     */           $$0.put("41", "minecraft:end_midlands");
/*     */           $$0.put("42", "minecraft:end_highlands");
/*     */           $$0.put("43", "minecraft:end_barrens");
/*     */           $$0.put("44", "minecraft:warm_ocean");
/*     */           $$0.put("45", "minecraft:lukewarm_ocean");
/*     */           $$0.put("46", "minecraft:cold_ocean");
/*     */           $$0.put("47", "minecraft:deep_warm_ocean");
/*     */           $$0.put("48", "minecraft:deep_lukewarm_ocean");
/*     */           $$0.put("49", "minecraft:deep_cold_ocean");
/*     */           $$0.put("50", "minecraft:deep_frozen_ocean");
/*     */           $$0.put("127", "minecraft:the_void");
/*     */           $$0.put("129", "minecraft:sunflower_plains");
/*     */           $$0.put("130", "minecraft:desert_lakes");
/*     */           $$0.put("131", "minecraft:gravelly_mountains");
/*     */           $$0.put("132", "minecraft:flower_forest");
/*     */           $$0.put("133", "minecraft:taiga_mountains");
/*     */           $$0.put("134", "minecraft:swamp_hills");
/*     */           $$0.put("140", "minecraft:ice_spikes");
/*     */           $$0.put("149", "minecraft:modified_jungle");
/*     */           $$0.put("151", "minecraft:modified_jungle_edge");
/*     */           $$0.put("155", "minecraft:tall_birch_forest");
/*     */           $$0.put("156", "minecraft:tall_birch_hills");
/*     */           $$0.put("157", "minecraft:dark_forest_hills");
/*     */           $$0.put("158", "minecraft:snowy_taiga_mountains");
/*     */           $$0.put("160", "minecraft:giant_spruce_taiga");
/*     */           $$0.put("161", "minecraft:giant_spruce_taiga_hills");
/*     */           $$0.put("162", "minecraft:modified_gravelly_mountains");
/*     */           $$0.put("163", "minecraft:shattered_savanna");
/*     */           $$0.put("164", "minecraft:shattered_savanna_plateau");
/*     */           $$0.put("165", "minecraft:eroded_badlands");
/*     */           $$0.put("166", "minecraft:modified_wooded_badlands_plateau");
/*     */           $$0.put("167", "minecraft:modified_badlands_plateau");
/*     */         });
/*     */   }
/*     */   
/*     */   static final Map<String, String> MAP;
/*     */   public static final String GENERATOR_OPTIONS = "generatorOptions";
/*     */   
/*     */   public LevelDataGeneratorOptionsFix(Schema $$0, boolean $$1) {
/* 110 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/* 115 */     Type<?> $$0 = getOutputSchema().getType(References.LEVEL);
/* 116 */     return fixTypeEverywhereTyped("LevelDataGeneratorOptionsFix", getInputSchema().getType(References.LEVEL), $$0, $$1 -> Util.writeAndReadTypedOrThrow($$1, $$0, ()));
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
/*     */   private static <T> Dynamic<T> convert(String $$0, DynamicOps<T> $$1) {
/*     */     List<Pair<Integer, String>> $$12;
/* 133 */     Iterator<String> $$2 = Splitter.on(';').split($$0).iterator();
/*     */ 
/*     */     
/* 136 */     String $$3 = "minecraft:plains";
/* 137 */     Map<String, Map<String, String>> $$4 = Maps.newHashMap();
/*     */     
/* 139 */     if (!$$0.isEmpty() && $$2.hasNext()) {
/* 140 */       List<Pair<Integer, String>> $$5 = getLayersInfoFromString($$2.next());
/*     */       
/* 142 */       if (!$$5.isEmpty()) {
/* 143 */         if ($$2.hasNext()) {
/* 144 */           $$3 = MAP.getOrDefault($$2.next(), "minecraft:plains");
/*     */         }
/*     */         
/* 147 */         if ($$2.hasNext()) {
/* 148 */           String[] $$6 = ((String)$$2.next()).toLowerCase(Locale.ROOT).split(",");
/*     */           
/* 150 */           for (String $$7 : $$6) {
/* 151 */             String[] $$8 = $$7.split("\\(", 2);
/*     */             
/* 153 */             if (!$$8[0].isEmpty()) {
/* 154 */               $$4.put($$8[0], Maps.newHashMap());
/*     */               
/* 156 */               if ($$8.length > 1 && $$8[1].endsWith(")") && $$8[1].length() > 1) {
/* 157 */                 String[] $$9 = $$8[1].substring(0, $$8[1].length() - 1).split(" ");
/*     */                 
/* 159 */                 for (String $$10 : $$9) {
/* 160 */                   String[] $$11 = $$10.split("=", 2);
/* 161 */                   if ($$11.length == 2) {
/* 162 */                     ((Map<String, String>)$$4.get($$8[0])).put($$11[0], $$11[1]);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else {
/* 169 */           $$4.put("village", Maps.newHashMap());
/*     */         } 
/*     */       } 
/*     */     } else {
/* 173 */       $$12 = Lists.newArrayList();
/* 174 */       $$12.add(Pair.of(Integer.valueOf(1), "minecraft:bedrock"));
/* 175 */       $$12.add(Pair.of(Integer.valueOf(2), "minecraft:dirt"));
/* 176 */       $$12.add(Pair.of(Integer.valueOf(1), "minecraft:grass_block"));
/* 177 */       $$4.put("village", Maps.newHashMap());
/*     */     } 
/*     */     
/* 180 */     T $$13 = (T)$$1.createList($$12.stream().map($$1 -> $$0.createMap((Map)ImmutableMap.of($$0.createString("height"), $$0.createInt(((Integer)$$1.getFirst()).intValue()), $$0.createString("block"), $$0.createString((String)$$1.getSecond())))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     T $$14 = (T)$$1.createMap((Map)$$4.entrySet().stream().map($$1 -> Pair.of($$0.createString(((String)$$1.getKey()).toLowerCase(Locale.ROOT)), $$0.createMap((Map)((Map)$$1.getValue()).entrySet().stream().map(()).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 192 */         .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
/*     */     
/* 194 */     return new Dynamic($$1, $$1.createMap((Map)ImmutableMap.of($$1
/* 195 */             .createString("layers"), $$13, $$1
/* 196 */             .createString("biome"), $$1.createString($$3), $$1
/* 197 */             .createString("structures"), $$14)));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Pair<Integer, String> getLayerInfoFromString(String $$0) {
/*     */     int $$4;
/* 203 */     String[] $$1 = $$0.split("\\*", 2);
/*     */ 
/*     */     
/* 206 */     if ($$1.length == 2) {
/*     */       try {
/* 208 */         int $$2 = Integer.parseInt($$1[0]);
/* 209 */       } catch (NumberFormatException $$3) {
/* 210 */         return null;
/*     */       } 
/*     */     } else {
/* 213 */       $$4 = 1;
/*     */     } 
/*     */     
/* 216 */     String $$5 = $$1[$$1.length - 1];
/* 217 */     return Pair.of(Integer.valueOf($$4), $$5);
/*     */   }
/*     */   
/*     */   private static List<Pair<Integer, String>> getLayersInfoFromString(String $$0) {
/* 221 */     List<Pair<Integer, String>> $$1 = Lists.newArrayList();
/* 222 */     String[] $$2 = $$0.split(",");
/*     */     
/* 224 */     for (String $$3 : $$2) {
/* 225 */       Pair<Integer, String> $$4 = getLayerInfoFromString($$3);
/* 226 */       if ($$4 == null) {
/* 227 */         return Collections.emptyList();
/*     */       }
/* 229 */       $$1.add($$4);
/*     */     } 
/*     */     
/* 232 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\LevelDataGeneratorOptionsFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */