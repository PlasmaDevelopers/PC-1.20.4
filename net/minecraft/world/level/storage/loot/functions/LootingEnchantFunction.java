/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*    */ 
/*    */ public class LootingEnchantFunction extends LootItemConditionalFunction {
/*    */   static {
/* 25 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)NumberProviders.CODEC.fieldOf("count").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "limit", Integer.valueOf(0)).forGetter(()))).apply((Applicative)$$0, LootingEnchantFunction::new));
/*    */   }
/*    */   
/*    */   public static final int NO_LIMIT = 0;
/*    */   public static final Codec<LootingEnchantFunction> CODEC;
/*    */   private final NumberProvider value;
/*    */   private final int limit;
/*    */   
/*    */   LootingEnchantFunction(List<LootItemCondition> $$0, NumberProvider $$1, int $$2) {
/* 34 */     super($$0);
/* 35 */     this.value = $$1;
/* 36 */     this.limit = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 41 */     return LootItemFunctions.LOOTING_ENCHANT;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 46 */     return (Set<LootContextParam<?>>)Sets.union((Set)ImmutableSet.of(LootContextParams.KILLER_ENTITY), this.value.getReferencedContextParams());
/*    */   }
/*    */   
/*    */   private boolean hasLimit() {
/* 50 */     return (this.limit > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 55 */     Entity $$2 = (Entity)$$1.getParamOrNull(LootContextParams.KILLER_ENTITY);
/*    */     
/* 57 */     if ($$2 instanceof LivingEntity) {
/* 58 */       int $$3 = EnchantmentHelper.getMobLooting((LivingEntity)$$2);
/* 59 */       if ($$3 == 0) {
/* 60 */         return $$0;
/*    */       }
/* 62 */       float $$4 = $$3 * this.value.getFloat($$1);
/* 63 */       $$0.grow(Math.round($$4));
/*    */       
/* 65 */       if (hasLimit() && $$0.getCount() > this.limit) {
/* 66 */         $$0.setCount(this.limit);
/*    */       }
/*    */     } 
/*    */     
/* 70 */     return $$0;
/*    */   }
/*    */   
/*    */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*    */     private final NumberProvider count;
/* 75 */     private int limit = 0;
/*    */     
/*    */     public Builder(NumberProvider $$0) {
/* 78 */       this.count = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     protected Builder getThis() {
/* 83 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setLimit(int $$0) {
/* 87 */       this.limit = $$0;
/* 88 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootItemFunction build() {
/* 93 */       return new LootingEnchantFunction(getConditions(), this.count, this.limit);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder lootingMultiplier(NumberProvider $$0) {
/* 98 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\LootingEnchantFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */