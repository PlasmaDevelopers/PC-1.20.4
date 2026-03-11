/*    */ package net.minecraft.advancements.critereon;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/* 46 */   private MinMaxBounds.Doubles dealtDamage = MinMaxBounds.Doubles.ANY;
/* 47 */   private MinMaxBounds.Doubles takenDamage = MinMaxBounds.Doubles.ANY;
/* 48 */   private Optional<EntityPredicate> sourceEntity = Optional.empty();
/* 49 */   private Optional<Boolean> blocked = Optional.empty();
/* 50 */   private Optional<DamageSourcePredicate> type = Optional.empty();
/*    */   
/*    */   public static Builder damageInstance() {
/* 53 */     return new Builder();
/*    */   }
/*    */   
/*    */   public Builder dealtDamage(MinMaxBounds.Doubles $$0) {
/* 57 */     this.dealtDamage = $$0;
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public Builder takenDamage(MinMaxBounds.Doubles $$0) {
/* 62 */     this.takenDamage = $$0;
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public Builder sourceEntity(EntityPredicate $$0) {
/* 67 */     this.sourceEntity = Optional.of($$0);
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public Builder blocked(Boolean $$0) {
/* 72 */     this.blocked = Optional.of($$0);
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   public Builder type(DamageSourcePredicate $$0) {
/* 77 */     this.type = Optional.of($$0);
/* 78 */     return this;
/*    */   }
/*    */   
/*    */   public Builder type(DamageSourcePredicate.Builder $$0) {
/* 82 */     this.type = Optional.of($$0.build());
/* 83 */     return this;
/*    */   }
/*    */   
/*    */   public DamagePredicate build() {
/* 87 */     return new DamagePredicate(this.dealtDamage, this.takenDamage, this.sourceEntity, this.blocked, this.type);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\DamagePredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */