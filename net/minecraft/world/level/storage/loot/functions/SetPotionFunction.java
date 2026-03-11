/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.alchemy.Potion;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class SetPotionFunction extends LootItemConditionalFunction {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)BuiltInRegistries.POTION.holderByNameCodec().fieldOf("id").forGetter(())).apply((Applicative)$$0, SetPotionFunction::new));
/*    */   }
/*    */   
/*    */   public static final Codec<SetPotionFunction> CODEC;
/*    */   private final Holder<Potion> potion;
/*    */   
/*    */   private SetPotionFunction(List<LootItemCondition> $$0, Holder<Potion> $$1) {
/* 23 */     super($$0);
/* 24 */     this.potion = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 29 */     return LootItemFunctions.SET_POTION;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 34 */     PotionUtils.setPotion($$0, (Potion)this.potion.value());
/* 35 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> setPotion(Potion $$0) {
/* 39 */     return simpleBuilder($$1 -> new SetPotionFunction($$1, (Holder<Potion>)$$0.builtInRegistryHolder()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetPotionFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */