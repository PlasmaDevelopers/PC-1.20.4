/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import java.util.Optional;
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
/* 41 */   private Optional<Boolean> isRaining = Optional.empty();
/* 42 */   private Optional<Boolean> isThundering = Optional.empty();
/*    */   
/*    */   public Builder setRaining(boolean $$0) {
/* 45 */     this.isRaining = Optional.of(Boolean.valueOf($$0));
/* 46 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setThundering(boolean $$0) {
/* 50 */     this.isThundering = Optional.of(Boolean.valueOf($$0));
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public WeatherCheck build() {
/* 56 */     return new WeatherCheck(this.isRaining, this.isThundering);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\WeatherCheck$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */