/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.item.Instrument;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class SetInstrumentFunction extends LootItemConditionalFunction {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)TagKey.hashedCodec(Registries.INSTRUMENT).fieldOf("options").forGetter(())).apply((Applicative)$$0, SetInstrumentFunction::new));
/*    */   }
/*    */   
/*    */   public static final Codec<SetInstrumentFunction> CODEC;
/*    */   private final TagKey<Instrument> options;
/*    */   
/*    */   private SetInstrumentFunction(List<LootItemCondition> $$0, TagKey<Instrument> $$1) {
/* 23 */     super($$0);
/* 24 */     this.options = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 29 */     return LootItemFunctions.SET_INSTRUMENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 34 */     InstrumentItem.setRandom($$0, this.options, $$1.getRandom());
/* 35 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> setInstrumentOptions(TagKey<Instrument> $$0) {
/* 39 */     return simpleBuilder($$1 -> new SetInstrumentFunction($$1, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetInstrumentFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */