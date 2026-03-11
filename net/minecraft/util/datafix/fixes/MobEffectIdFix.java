/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*     */ 
/*     */ public class MobEffectIdFix extends DataFix {
/*     */   static {
/*  24 */     ID_MAP = (Int2ObjectMap<String>)Util.make(new Int2ObjectOpenHashMap(), $$0 -> {
/*     */           $$0.put(1, "minecraft:speed");
/*     */           $$0.put(2, "minecraft:slowness");
/*     */           $$0.put(3, "minecraft:haste");
/*     */           $$0.put(4, "minecraft:mining_fatigue");
/*     */           $$0.put(5, "minecraft:strength");
/*     */           $$0.put(6, "minecraft:instant_health");
/*     */           $$0.put(7, "minecraft:instant_damage");
/*     */           $$0.put(8, "minecraft:jump_boost");
/*     */           $$0.put(9, "minecraft:nausea");
/*     */           $$0.put(10, "minecraft:regeneration");
/*     */           $$0.put(11, "minecraft:resistance");
/*     */           $$0.put(12, "minecraft:fire_resistance");
/*     */           $$0.put(13, "minecraft:water_breathing");
/*     */           $$0.put(14, "minecraft:invisibility");
/*     */           $$0.put(15, "minecraft:blindness");
/*     */           $$0.put(16, "minecraft:night_vision");
/*     */           $$0.put(17, "minecraft:hunger");
/*     */           $$0.put(18, "minecraft:weakness");
/*     */           $$0.put(19, "minecraft:poison");
/*     */           $$0.put(20, "minecraft:wither");
/*     */           $$0.put(21, "minecraft:health_boost");
/*     */           $$0.put(22, "minecraft:absorption");
/*     */           $$0.put(23, "minecraft:saturation");
/*     */           $$0.put(24, "minecraft:glowing");
/*     */           $$0.put(25, "minecraft:levitation");
/*     */           $$0.put(26, "minecraft:luck");
/*     */           $$0.put(27, "minecraft:unluck");
/*     */           $$0.put(28, "minecraft:slow_falling");
/*     */           $$0.put(29, "minecraft:conduit_power");
/*     */           $$0.put(30, "minecraft:dolphins_grace");
/*     */           $$0.put(31, "minecraft:bad_omen");
/*     */           $$0.put(32, "minecraft:hero_of_the_village");
/*     */           $$0.put(33, "minecraft:darkness");
/*     */         });
/*     */   }
/*  60 */   private static final Set<String> MOB_EFFECT_INSTANCE_CARRIER_ITEMS = Set.of("minecraft:potion", "minecraft:splash_potion", "minecraft:lingering_potion", "minecraft:tipped_arrow");
/*     */ 
/*     */   
/*     */   private static final Int2ObjectMap<String> ID_MAP;
/*     */ 
/*     */ 
/*     */   
/*     */   public MobEffectIdFix(Schema $$0) {
/*  68 */     super($$0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Optional<Dynamic<T>> getAndConvertMobEffectId(Dynamic<T> $$0, String $$1) {
/*  77 */     Objects.requireNonNull($$0); return $$0.get($$1).asNumber().result().map($$0 -> (String)ID_MAP.get($$0.intValue())).map($$0::createString);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> setFieldIfPresent(Dynamic<T> $$0, String $$1, Optional<Dynamic<T>> $$2) {
/*  81 */     if ($$2.isEmpty()) {
/*  82 */       return $$0;
/*     */     }
/*  84 */     return $$0.set($$1, $$2.get());
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> replaceField(Dynamic<T> $$0, String $$1, String $$2, Optional<Dynamic<T>> $$3) {
/*  88 */     return setFieldIfPresent($$0.remove($$1), $$2, $$3);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> renameField(Dynamic<T> $$0, String $$1, String $$2) {
/*  92 */     return setFieldIfPresent($$0.remove($$1), $$2, $$0.get($$1).result());
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> updateMobEffectIdField(Dynamic<T> $$0, String $$1, Dynamic<T> $$2, String $$3) {
/*  96 */     Optional<Dynamic<T>> $$4 = getAndConvertMobEffectId($$0, $$1);
/*  97 */     return replaceField($$2, $$1, $$3, $$4);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> updateMobEffectIdField(Dynamic<T> $$0, String $$1, String $$2) {
/* 101 */     return updateMobEffectIdField($$0, $$1, $$0, $$2);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> updateMobEffectInstance(Dynamic<T> $$0) {
/* 105 */     $$0 = updateMobEffectIdField($$0, "Id", "id");
/* 106 */     $$0 = renameField($$0, "Ambient", "ambient");
/* 107 */     $$0 = renameField($$0, "Amplifier", "amplifier");
/* 108 */     $$0 = renameField($$0, "Duration", "duration");
/* 109 */     $$0 = renameField($$0, "ShowParticles", "show_particles");
/* 110 */     $$0 = renameField($$0, "ShowIcon", "show_icon");
/* 111 */     $$0 = renameField($$0, "FactorCalculationData", "factor_calculation_data");
/*     */     
/* 113 */     Optional<Dynamic<T>> $$1 = $$0.get("HiddenEffect").result().map(MobEffectIdFix::updateMobEffectInstance);
/* 114 */     return replaceField($$0, "HiddenEffect", "hidden_effect", $$1);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> updateMobEffectInstanceList(Dynamic<T> $$0, String $$1, String $$2) {
/* 118 */     Optional<Dynamic<T>> $$3 = $$0.get($$1).asStreamOpt().result().map($$1 -> $$0.createList($$1.map(MobEffectIdFix::updateMobEffectInstance)));
/* 119 */     return replaceField($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> updateSuspiciousStewEntry(Dynamic<T> $$0, Dynamic<T> $$1) {
/* 123 */     $$1 = updateMobEffectIdField($$0, "EffectId", $$1, "id");
/*     */     
/* 125 */     Optional<Dynamic<T>> $$2 = $$0.get("EffectDuration").result();
/* 126 */     return replaceField($$1, "EffectDuration", "duration", $$2);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> updateSuspiciousStewEntry(Dynamic<T> $$0) {
/* 130 */     return updateSuspiciousStewEntry($$0, $$0);
/*     */   }
/*     */   
/*     */   private Typed<?> updateNamedChoice(Typed<?> $$0, DSL.TypeReference $$1, String $$2, Function<Dynamic<?>, Dynamic<?>> $$3) {
/* 134 */     Type<?> $$4 = getInputSchema().getChoiceType($$1, $$2);
/* 135 */     Type<?> $$5 = getOutputSchema().getChoiceType($$1, $$2);
/* 136 */     return $$0.updateTyped(DSL.namedChoice($$2, $$4), $$5, $$1 -> $$1.update(DSL.remainderFinder(), $$0));
/*     */   }
/*     */   
/*     */   private TypeRewriteRule blockEntityFixer() {
/* 140 */     Type<?> $$0 = getInputSchema().getType(References.BLOCK_ENTITY);
/* 141 */     return fixTypeEverywhereTyped("BlockEntityMobEffectIdFix", $$0, $$0 -> updateNamedChoice($$0, References.BLOCK_ENTITY, "minecraft:beacon", ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Dynamic<T> fixMooshroomTag(Dynamic<T> $$0) {
/* 151 */     Dynamic<T> $$1 = $$0.emptyMap();
/* 152 */     Dynamic<T> $$2 = updateSuspiciousStewEntry($$0, $$1);
/*     */     
/* 154 */     if (!$$2.equals($$1)) {
/* 155 */       $$0 = $$0.set("stew_effects", $$0.createList(Stream.of($$2)));
/*     */     }
/* 157 */     return $$0.remove("EffectId").remove("EffectDuration");
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> fixArrowTag(Dynamic<T> $$0) {
/* 161 */     return updateMobEffectInstanceList($$0, "CustomPotionEffects", "custom_potion_effects");
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> fixAreaEffectCloudTag(Dynamic<T> $$0) {
/* 165 */     return updateMobEffectInstanceList($$0, "Effects", "effects");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Dynamic<?> updateLivingEntityTag(Dynamic<?> $$0) {
/* 170 */     return updateMobEffectInstanceList($$0, "ActiveEffects", "active_effects");
/*     */   }
/*     */   
/*     */   private TypeRewriteRule entityFixer() {
/* 174 */     Type<?> $$0 = getInputSchema().getType(References.ENTITY);
/* 175 */     return fixTypeEverywhereTyped("EntityMobEffectIdFix", $$0, $$0 -> {
/*     */           $$0 = updateNamedChoice($$0, References.ENTITY, "minecraft:mooshroom", MobEffectIdFix::fixMooshroomTag);
/*     */           $$0 = updateNamedChoice($$0, References.ENTITY, "minecraft:arrow", MobEffectIdFix::fixArrowTag);
/*     */           $$0 = updateNamedChoice($$0, References.ENTITY, "minecraft:area_effect_cloud", MobEffectIdFix::fixAreaEffectCloudTag);
/*     */           return $$0.update(DSL.remainderFinder(), MobEffectIdFix::updateLivingEntityTag);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private TypeRewriteRule playerFixer() {
/* 185 */     Type<?> $$0 = getInputSchema().getType(References.PLAYER);
/* 186 */     return fixTypeEverywhereTyped("PlayerMobEffectIdFix", $$0, $$0 -> $$0.update(DSL.remainderFinder(), MobEffectIdFix::updateLivingEntityTag));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Dynamic<T> fixSuspiciousStewTag(Dynamic<T> $$0) {
/* 195 */     Optional<Dynamic<T>> $$1 = $$0.get("Effects").asStreamOpt().result().map($$1 -> $$0.createList($$1.map(MobEffectIdFix::updateSuspiciousStewEntry)));
/*     */     
/* 197 */     return replaceField($$0, "Effects", "effects", $$1);
/*     */   }
/*     */   
/*     */   private TypeRewriteRule itemStackFixer() {
/* 201 */     OpticFinder<Pair<String, String>> $$0 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/*     */     
/* 203 */     Type<?> $$1 = getInputSchema().getType(References.ITEM_STACK);
/* 204 */     OpticFinder<?> $$2 = $$1.findField("tag");
/* 205 */     return fixTypeEverywhereTyped("ItemStackMobEffectIdFix", $$1, $$2 -> {
/*     */           Optional<Pair<String, String>> $$3 = $$2.getOptional($$0);
/*     */           if ($$3.isPresent()) {
/*     */             String $$4 = (String)((Pair)$$3.get()).getSecond();
/*     */             if ($$4.equals("minecraft:suspicious_stew")) {
/*     */               return $$2.updateTyped($$1, ());
/*     */             }
/*     */             if (MOB_EFFECT_INSTANCE_CARRIER_ITEMS.contains($$4)) {
/*     */               return $$2.updateTyped($$1, ());
/*     */             }
/*     */           } 
/*     */           return $$2;
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/* 222 */     return TypeRewriteRule.seq(
/* 223 */         blockEntityFixer(), new TypeRewriteRule[] {
/* 224 */           entityFixer(), 
/* 225 */           playerFixer(), 
/* 226 */           itemStackFixer()
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\MobEffectIdFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */