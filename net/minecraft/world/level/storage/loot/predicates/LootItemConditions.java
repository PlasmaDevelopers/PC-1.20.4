/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public class LootItemConditions
/*    */ {
/* 13 */   private static final Codec<LootItemCondition> TYPED_CODEC = BuiltInRegistries.LOOT_CONDITION_TYPE.byNameCodec()
/* 14 */     .dispatch("condition", LootItemCondition::getType, LootItemConditionType::codec);
/*    */   
/* 16 */   public static final Codec<LootItemCondition> CODEC = ExtraCodecs.lazyInitializedCodec(() -> ExtraCodecs.withAlternative(TYPED_CODEC, AllOfCondition.INLINE_CODEC));
/*    */   
/* 18 */   public static final LootItemConditionType INVERTED = register("inverted", (Codec)InvertedLootItemCondition.CODEC);
/* 19 */   public static final LootItemConditionType ANY_OF = register("any_of", (Codec)AnyOfCondition.CODEC);
/* 20 */   public static final LootItemConditionType ALL_OF = register("all_of", (Codec)AllOfCondition.CODEC);
/* 21 */   public static final LootItemConditionType RANDOM_CHANCE = register("random_chance", (Codec)LootItemRandomChanceCondition.CODEC);
/* 22 */   public static final LootItemConditionType RANDOM_CHANCE_WITH_LOOTING = register("random_chance_with_looting", (Codec)LootItemRandomChanceWithLootingCondition.CODEC);
/* 23 */   public static final LootItemConditionType ENTITY_PROPERTIES = register("entity_properties", (Codec)LootItemEntityPropertyCondition.CODEC);
/* 24 */   public static final LootItemConditionType KILLED_BY_PLAYER = register("killed_by_player", (Codec)LootItemKilledByPlayerCondition.CODEC);
/* 25 */   public static final LootItemConditionType ENTITY_SCORES = register("entity_scores", (Codec)EntityHasScoreCondition.CODEC);
/* 26 */   public static final LootItemConditionType BLOCK_STATE_PROPERTY = register("block_state_property", (Codec)LootItemBlockStatePropertyCondition.CODEC);
/* 27 */   public static final LootItemConditionType MATCH_TOOL = register("match_tool", (Codec)MatchTool.CODEC);
/* 28 */   public static final LootItemConditionType TABLE_BONUS = register("table_bonus", (Codec)BonusLevelTableCondition.CODEC);
/* 29 */   public static final LootItemConditionType SURVIVES_EXPLOSION = register("survives_explosion", (Codec)ExplosionCondition.CODEC);
/* 30 */   public static final LootItemConditionType DAMAGE_SOURCE_PROPERTIES = register("damage_source_properties", (Codec)DamageSourceCondition.CODEC);
/* 31 */   public static final LootItemConditionType LOCATION_CHECK = register("location_check", (Codec)LocationCheck.CODEC);
/* 32 */   public static final LootItemConditionType WEATHER_CHECK = register("weather_check", (Codec)WeatherCheck.CODEC);
/* 33 */   public static final LootItemConditionType REFERENCE = register("reference", (Codec)ConditionReference.CODEC);
/* 34 */   public static final LootItemConditionType TIME_CHECK = register("time_check", (Codec)TimeCheck.CODEC);
/* 35 */   public static final LootItemConditionType VALUE_CHECK = register("value_check", (Codec)ValueCheckCondition.CODEC);
/*    */   
/*    */   private static LootItemConditionType register(String $$0, Codec<? extends LootItemCondition> $$1) {
/* 38 */     return (LootItemConditionType)Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, new ResourceLocation($$0), new LootItemConditionType($$1));
/*    */   }
/*    */   
/*    */   public static <T> Predicate<T> andConditions(List<? extends Predicate<T>> $$0) {
/* 42 */     List<Predicate<T>> $$1 = List.copyOf($$0);
/* 43 */     switch ($$1.size()) { case 0: case 1: case 2:  }  return $$1 -> {
/*    */         for (Predicate<T> $$2 : (Iterable<Predicate<T>>)$$0) {
/*    */           if (!$$2.test((T)$$1)) {
/*    */             return false;
/*    */           }
/*    */         } 
/*    */         return true;
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> Predicate<T> orConditions(List<? extends Predicate<T>> $$0) {
/* 59 */     List<Predicate<T>> $$1 = List.copyOf($$0);
/* 60 */     switch ($$1.size()) { case 0: case 1: case 2:  }  return $$1 -> {
/*    */         for (Predicate<T> $$2 : (Iterable<Predicate<T>>)$$0) {
/*    */           if ($$2.test((T)$$1))
/*    */             return true; 
/*    */         } 
/*    */         return false;
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LootItemConditions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */