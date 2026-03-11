/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.level.storage.loot.IntRange;
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
/* 47 */   private Optional<Long> period = Optional.empty();
/*    */   private final IntRange value;
/*    */   
/*    */   public Builder(IntRange $$0) {
/* 51 */     this.value = $$0;
/*    */   }
/*    */   
/*    */   public Builder setPeriod(long $$0) {
/* 55 */     this.period = Optional.of(Long.valueOf($$0));
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public TimeCheck build() {
/* 61 */     return new TimeCheck(this.period, this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\TimeCheck$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */