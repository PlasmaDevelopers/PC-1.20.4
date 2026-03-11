/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class AllOfCondition extends CompositeLootItemCondition {
/*  8 */   public static final Codec<AllOfCondition> CODEC = createCodec(AllOfCondition::new);
/*  9 */   public static final Codec<AllOfCondition> INLINE_CODEC = createInlineCodec(AllOfCondition::new);
/*    */   
/*    */   AllOfCondition(List<LootItemCondition> $$0) {
/* 12 */     super($$0, LootItemConditions.andConditions((List)$$0));
/*    */   }
/*    */   
/*    */   public static AllOfCondition allOf(List<LootItemCondition> $$0) {
/* 16 */     return new AllOfCondition(List.copyOf($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 21 */     return LootItemConditions.ALL_OF;
/*    */   }
/*    */   
/*    */   public static class Builder extends CompositeLootItemCondition.Builder {
/*    */     public Builder(LootItemCondition.Builder... $$0) {
/* 26 */       super($$0);
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder and(LootItemCondition.Builder $$0) {
/* 31 */       addTerm($$0);
/* 32 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     protected LootItemCondition create(List<LootItemCondition> $$0) {
/* 37 */       return new AllOfCondition($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder allOf(LootItemCondition.Builder... $$0) {
/* 42 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\AllOfCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */