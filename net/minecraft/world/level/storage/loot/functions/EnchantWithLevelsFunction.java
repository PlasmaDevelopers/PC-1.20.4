/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*    */ 
/*    */ public class EnchantWithLevelsFunction extends LootItemConditionalFunction {
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)NumberProviders.CODEC.fieldOf("levels").forGetter(()), (App)Codec.BOOL.fieldOf("treasure").orElse(Boolean.valueOf(false)).forGetter(()))).apply((Applicative)$$0, EnchantWithLevelsFunction::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<EnchantWithLevelsFunction> CODEC;
/*    */   private final NumberProvider levels;
/*    */   private final boolean treasure;
/*    */   
/*    */   EnchantWithLevelsFunction(List<LootItemCondition> $$0, NumberProvider $$1, boolean $$2) {
/* 27 */     super($$0);
/* 28 */     this.levels = $$1;
/* 29 */     this.treasure = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 34 */     return LootItemFunctions.ENCHANT_WITH_LEVELS;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 39 */     return this.levels.getReferencedContextParams();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 44 */     RandomSource $$2 = $$1.getRandom();
/* 45 */     return EnchantmentHelper.enchantItem($$2, $$0, this.levels.getInt($$1), this.treasure);
/*    */   }
/*    */   
/*    */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*    */     private final NumberProvider levels;
/*    */     private boolean treasure;
/*    */     
/*    */     public Builder(NumberProvider $$0) {
/* 53 */       this.levels = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     protected Builder getThis() {
/* 58 */       return this;
/*    */     }
/*    */     
/*    */     public Builder allowTreasure() {
/* 62 */       this.treasure = true;
/* 63 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootItemFunction build() {
/* 68 */       return new EnchantWithLevelsFunction(getConditions(), this.levels, this.treasure);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder enchantWithLevels(NumberProvider $$0) {
/* 73 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\EnchantWithLevelsFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */