/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*    */ 
/*    */ public class SetItemCountFunction extends LootItemConditionalFunction {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)NumberProviders.CODEC.fieldOf("count").forGetter(()), (App)Codec.BOOL.fieldOf("add").orElse(Boolean.valueOf(false)).forGetter(()))).apply((Applicative)$$0, SetItemCountFunction::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<SetItemCountFunction> CODEC;
/*    */   private final NumberProvider value;
/*    */   private final boolean add;
/*    */   
/*    */   private SetItemCountFunction(List<LootItemCondition> $$0, NumberProvider $$1, boolean $$2) {
/* 26 */     super($$0);
/* 27 */     this.value = $$1;
/* 28 */     this.add = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 33 */     return LootItemFunctions.SET_COUNT;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 38 */     return this.value.getReferencedContextParams();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 43 */     int $$2 = this.add ? $$0.getCount() : 0;
/* 44 */     $$0.setCount(Mth.clamp($$2 + this.value.getInt($$1), 0, $$0.getMaxStackSize()));
/* 45 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> setCount(NumberProvider $$0) {
/* 49 */     return simpleBuilder($$1 -> new SetItemCountFunction($$1, $$0, false));
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> setCount(NumberProvider $$0, boolean $$1) {
/* 53 */     return simpleBuilder($$2 -> new SetItemCountFunction($$2, $$0, $$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetItemCountFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */