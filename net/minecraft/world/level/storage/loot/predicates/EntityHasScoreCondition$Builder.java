/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.world.level.storage.loot.IntRange;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */   implements LootItemCondition.Builder
/*    */ {
/* 68 */   private final ImmutableMap.Builder<String, IntRange> scores = ImmutableMap.builder();
/*    */   private final LootContext.EntityTarget entityTarget;
/*    */   
/*    */   public Builder(LootContext.EntityTarget $$0) {
/* 72 */     this.entityTarget = $$0;
/*    */   }
/*    */   
/*    */   public Builder withScore(String $$0, IntRange $$1) {
/* 76 */     this.scores.put($$0, $$1);
/* 77 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemCondition build() {
/* 82 */     return new EntityHasScoreCondition((Map<String, IntRange>)this.scores.build(), this.entityTarget);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\EntityHasScoreCondition$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */