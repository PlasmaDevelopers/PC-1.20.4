/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class AnyOfCondition extends CompositeLootItemCondition {
/*  8 */   public static final Codec<AnyOfCondition> CODEC = createCodec(AnyOfCondition::new);
/*    */   
/*    */   AnyOfCondition(List<LootItemCondition> $$0) {
/* 11 */     super($$0, LootItemConditions.orConditions((List)$$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 16 */     return LootItemConditions.ANY_OF;
/*    */   }
/*    */   
/*    */   public static class Builder extends CompositeLootItemCondition.Builder {
/*    */     public Builder(LootItemCondition.Builder... $$0) {
/* 21 */       super($$0);
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder or(LootItemCondition.Builder $$0) {
/* 26 */       addTerm($$0);
/* 27 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     protected LootItemCondition create(List<LootItemCondition> $$0) {
/* 32 */       return new AnyOfCondition($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder anyOf(LootItemCondition.Builder... $$0) {
/* 37 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\AnyOfCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */