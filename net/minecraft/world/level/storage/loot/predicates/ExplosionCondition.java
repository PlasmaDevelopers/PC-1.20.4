/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ 
/*    */ public class ExplosionCondition
/*    */   implements LootItemCondition {
/* 13 */   private static final ExplosionCondition INSTANCE = new ExplosionCondition();
/* 14 */   public static final Codec<ExplosionCondition> CODEC = Codec.unit(INSTANCE);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 21 */     return LootItemConditions.SURVIVES_EXPLOSION;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 26 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.EXPLOSION_RADIUS);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 31 */     Float $$1 = (Float)$$0.getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
/* 32 */     if ($$1 != null) {
/* 33 */       RandomSource $$2 = $$0.getRandom();
/* 34 */       float $$3 = 1.0F / $$1.floatValue();
/* 35 */       return ($$2.nextFloat() <= $$3);
/*    */     } 
/*    */     
/* 38 */     return true;
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder survivesExplosion() {
/* 42 */     return () -> INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\ExplosionCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */