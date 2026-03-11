/*    */ package net.minecraft.world.level.storage.loot;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.JsonOps;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class LootDataType<T> {
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 21 */   public static final LootDataType<LootItemCondition> PREDICATE = new LootDataType(LootItemConditions.CODEC, "predicates", (Validator)createSimpleValidator());
/* 22 */   public static final LootDataType<LootItemFunction> MODIFIER = new LootDataType(LootItemFunctions.CODEC, "item_modifiers", (Validator)createSimpleValidator());
/* 23 */   public static final LootDataType<LootTable> TABLE = new LootDataType((Codec)LootTable.CODEC, "loot_tables", (Validator)createLootTableValidator());
/*    */   
/*    */   private final Codec<T> codec;
/*    */   private final String directory;
/*    */   private final Validator<T> validator;
/*    */   
/*    */   private LootDataType(Codec<T> $$0, String $$1, Validator<T> $$2) {
/* 30 */     this.codec = $$0;
/* 31 */     this.directory = $$1;
/* 32 */     this.validator = $$2;
/*    */   }
/*    */   
/*    */   public String directory() {
/* 36 */     return this.directory;
/*    */   }
/*    */   
/*    */   public void runValidation(ValidationContext $$0, LootDataId<T> $$1, T $$2) {
/* 40 */     this.validator.run($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public Optional<T> deserialize(ResourceLocation $$0, JsonElement $$1) {
/* 44 */     DataResult<T> $$2 = this.codec.parse((DynamicOps)JsonOps.INSTANCE, $$1);
/* 45 */     $$2.error().ifPresent($$1 -> LOGGER.error("Couldn't parse element {}:{} - {}", new Object[] { this.directory, $$0, $$1.message() }));
/* 46 */     return $$2.result();
/*    */   }
/*    */   
/*    */   public static Stream<LootDataType<?>> values() {
/* 50 */     return Stream.of((LootDataType<?>[])new LootDataType[] { PREDICATE, MODIFIER, TABLE });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T extends LootContextUser> Validator<T> createSimpleValidator() {
/* 59 */     return ($$0, $$1, $$2) -> $$2.validate($$0.enterElement("{" + ($$1.type()).directory + ":" + $$1.location() + "}", $$1));
/*    */   }
/*    */   
/*    */   private static Validator<LootTable> createLootTableValidator() {
/* 63 */     return ($$0, $$1, $$2) -> $$2.validate($$0.setParams($$2.getParamSet()).enterElement("{" + ($$1.type()).directory + ":" + $$1.location() + "}", $$1));
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Validator<T> {
/*    */     void run(ValidationContext param1ValidationContext, LootDataId<T> param1LootDataId, T param1T);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootDataType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */