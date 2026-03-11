/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ public class StructuresBecomeConfiguredFix extends DataFix {
/*     */   public StructuresBecomeConfiguredFix(Schema $$0) {
/*  23 */     super($$0, false);
/*     */   }
/*     */   private static final class Conversion extends Record { private final Map<String, String> biomeMapping; final String fallback;
/*  26 */     private Conversion(Map<String, String> $$0, String $$1) { this.biomeMapping = $$0; this.fallback = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #26	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  26 */       //   0	7	0	this	Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion; } public Map<String, String> biomeMapping() { return this.biomeMapping; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #26	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #26	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;
/*  26 */       //   0	8	1	$$0	Ljava/lang/Object; } public String fallback() { return this.fallback; }
/*     */      public static Conversion trivial(String $$0) {
/*  28 */       return new Conversion(Map.of(), $$0);
/*     */     }
/*     */     
/*     */     public static Conversion biomeMapped(Map<List<String>, String> $$0, String $$1) {
/*  32 */       return new Conversion(unpack($$0), $$1);
/*     */     }
/*     */     
/*     */     private static Map<String, String> unpack(Map<List<String>, String> $$0) {
/*  36 */       ImmutableMap.Builder<String, String> $$1 = ImmutableMap.builder();
/*  37 */       for (Iterator<Map.Entry<List<String>, String>> iterator = $$0.entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<List<String>, String> $$2 = iterator.next();
/*  38 */         ((List)$$2.getKey()).forEach($$2 -> $$0.put($$2, $$1.getValue())); }
/*     */       
/*  40 */       return (Map<String, String>)$$1.build();
/*     */     } }
/*     */ 
/*     */   
/*  44 */   private static final Map<String, Conversion> CONVERSION_MAP = (Map<String, Conversion>)ImmutableMap.builder()
/*  45 */     .put("mineshaft", Conversion.biomeMapped(
/*  46 */         Map.of(
/*  47 */           List.of("minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands"), "minecraft:mineshaft_mesa"), "minecraft:mineshaft"))
/*     */ 
/*     */ 
/*     */     
/*  51 */     .put("shipwreck", Conversion.biomeMapped(
/*  52 */         Map.of(
/*  53 */           List.of("minecraft:beach", "minecraft:snowy_beach"), "minecraft:shipwreck_beached"), "minecraft:shipwreck"))
/*     */ 
/*     */ 
/*     */     
/*  57 */     .put("ocean_ruin", Conversion.biomeMapped(
/*  58 */         Map.of(
/*  59 */           List.of("minecraft:warm_ocean", "minecraft:lukewarm_ocean", "minecraft:deep_lukewarm_ocean"), "minecraft:ocean_ruin_warm"), "minecraft:ocean_ruin_cold"))
/*     */ 
/*     */ 
/*     */     
/*  63 */     .put("village", Conversion.biomeMapped(
/*  64 */         Map.of(
/*  65 */           List.of("minecraft:desert"), "minecraft:village_desert", 
/*  66 */           List.of("minecraft:savanna"), "minecraft:village_savanna", 
/*  67 */           List.of("minecraft:snowy_plains"), "minecraft:village_snowy", 
/*  68 */           List.of("minecraft:taiga"), "minecraft:village_taiga"), "minecraft:village_plains"))
/*     */ 
/*     */ 
/*     */     
/*  72 */     .put("ruined_portal", Conversion.biomeMapped(
/*  73 */         Map.of(
/*  74 */           List.of("minecraft:desert"), "minecraft:ruined_portal_desert", 
/*  75 */           List.of(new String[] { "minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands", "minecraft:windswept_hills", "minecraft:windswept_forest", "minecraft:windswept_gravelly_hills", "minecraft:savanna_plateau", "minecraft:windswept_savanna", "minecraft:stony_shore", "minecraft:meadow", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:snowy_slopes" }, ), "minecraft:ruined_portal_mountain", 
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
/*  91 */           List.of("minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle"), "minecraft:ruined_portal_jungle", 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  96 */           List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean"), "minecraft:ruined_portal_ocean"), "minecraft:ruined_portal"))
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
/* 110 */     .put("pillager_outpost", Conversion.trivial("minecraft:pillager_outpost"))
/* 111 */     .put("mansion", Conversion.trivial("minecraft:mansion"))
/* 112 */     .put("jungle_pyramid", Conversion.trivial("minecraft:jungle_pyramid"))
/* 113 */     .put("desert_pyramid", Conversion.trivial("minecraft:desert_pyramid"))
/* 114 */     .put("igloo", Conversion.trivial("minecraft:igloo"))
/* 115 */     .put("swamp_hut", Conversion.trivial("minecraft:swamp_hut"))
/* 116 */     .put("stronghold", Conversion.trivial("minecraft:stronghold"))
/* 117 */     .put("monument", Conversion.trivial("minecraft:monument"))
/* 118 */     .put("fortress", Conversion.trivial("minecraft:fortress"))
/* 119 */     .put("endcity", Conversion.trivial("minecraft:end_city"))
/* 120 */     .put("buried_treasure", Conversion.trivial("minecraft:buried_treasure"))
/* 121 */     .put("nether_fossil", Conversion.trivial("minecraft:nether_fossil"))
/* 122 */     .put("bastion_remnant", Conversion.trivial("minecraft:bastion_remnant"))
/* 123 */     .build();
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/* 127 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 128 */     Type<?> $$1 = getInputSchema().getType(References.CHUNK);
/*     */     
/* 130 */     return writeFixAndRead("StucturesToConfiguredStructures", $$0, $$1, this::fix);
/*     */   }
/*     */   
/*     */   private Dynamic<?> fix(Dynamic<?> $$0) {
/* 134 */     return $$0.update("structures", $$1 -> $$1.update("starts", ()).update("References", ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Dynamic<?> updateStarts(Dynamic<?> $$0, Dynamic<?> $$1) {
/* 142 */     Map<? extends Dynamic<?>, ? extends Dynamic<?>> $$2 = $$0.getMapValues().result().get();
/*     */     
/* 144 */     List<Dynamic<?>> $$3 = new ArrayList<>();
/* 145 */     $$2.forEach(($$1, $$2) -> {
/*     */           if ($$2.get("id").asString("INVALID").equals("INVALID")) {
/*     */             $$0.add($$1);
/*     */           }
/*     */         });
/* 150 */     for (Dynamic<?> $$4 : $$3) {
/* 151 */       $$0 = $$0.remove($$4.asString(""));
/*     */     }
/*     */ 
/*     */     
/* 155 */     return $$0.updateMapValues($$1 -> updateStart($$1, $$0));
/*     */   }
/*     */   
/*     */   private Pair<Dynamic<?>, Dynamic<?>> updateStart(Pair<Dynamic<?>, Dynamic<?>> $$0, Dynamic<?> $$1) {
/* 159 */     Dynamic<?> $$2 = findUpdatedStructureType($$0, $$1);
/* 160 */     return new Pair($$2, ((Dynamic)$$0.getSecond()).set("id", $$2));
/*     */   }
/*     */   
/*     */   private Dynamic<?> updateReferences(Dynamic<?> $$0, Dynamic<?> $$1) {
/* 164 */     Map<? extends Dynamic<?>, ? extends Dynamic<?>> $$2 = $$0.getMapValues().result().get();
/*     */     
/* 166 */     List<Dynamic<?>> $$3 = new ArrayList<>();
/* 167 */     $$2.forEach(($$1, $$2) -> {
/*     */           if ($$2.asLongStream().count() == 0L) {
/*     */             $$0.add($$1);
/*     */           }
/*     */         });
/* 172 */     for (Dynamic<?> $$4 : $$3) {
/* 173 */       $$0 = $$0.remove($$4.asString(""));
/*     */     }
/*     */ 
/*     */     
/* 177 */     return $$0.updateMapValues($$1 -> updateReference($$1, $$0));
/*     */   }
/*     */   
/*     */   private Pair<Dynamic<?>, Dynamic<?>> updateReference(Pair<Dynamic<?>, Dynamic<?>> $$0, Dynamic<?> $$1) {
/* 181 */     return $$0.mapFirst($$2 -> findUpdatedStructureType($$0, $$1));
/*     */   }
/*     */   
/*     */   private Dynamic<?> findUpdatedStructureType(Pair<Dynamic<?>, Dynamic<?>> $$0, Dynamic<?> $$1) {
/* 185 */     String $$2 = ((Dynamic)$$0.getFirst()).asString("UNKNOWN").toLowerCase(Locale.ROOT);
/* 186 */     Conversion $$3 = CONVERSION_MAP.get($$2);
/* 187 */     if ($$3 == null) {
/* 188 */       throw new IllegalStateException("Found unknown structure: " + $$2);
/*     */     }
/* 190 */     Dynamic<?> $$4 = (Dynamic)$$0.getSecond();
/* 191 */     String $$5 = $$3.fallback;
/* 192 */     if (!$$3.biomeMapping().isEmpty()) {
/* 193 */       Optional<String> $$6 = guessConfiguration($$1, $$3);
/* 194 */       if ($$6.isPresent()) {
/* 195 */         $$5 = $$6.get();
/*     */       }
/*     */     } 
/* 198 */     Dynamic<?> $$7 = $$4.createString($$5);
/* 199 */     return $$7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Optional<String> guessConfiguration(Dynamic<?> $$0, Conversion $$1) {
/* 206 */     Object2IntArrayMap<String> $$2 = new Object2IntArrayMap();
/* 207 */     $$0.get("sections").asList(Function.identity()).forEach($$2 -> $$2.get("biomes").get("palette").asList(Function.identity()).forEach(()));
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
/* 218 */     return $$2.object2IntEntrySet().stream()
/* 219 */       .max(Comparator.comparingInt(Object2IntMap.Entry::getIntValue))
/* 220 */       .map(Map.Entry::getKey);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\StructuresBecomeConfiguredFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */