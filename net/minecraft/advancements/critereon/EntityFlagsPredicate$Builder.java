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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/* 52 */   private Optional<Boolean> isOnFire = Optional.empty();
/* 53 */   private Optional<Boolean> isCrouching = Optional.empty();
/* 54 */   private Optional<Boolean> isSprinting = Optional.empty();
/* 55 */   private Optional<Boolean> isSwimming = Optional.empty();
/* 56 */   private Optional<Boolean> isBaby = Optional.empty();
/*    */   
/*    */   public static Builder flags() {
/* 59 */     return new Builder();
/*    */   }
/*    */   
/*    */   public Builder setOnFire(Boolean $$0) {
/* 63 */     this.isOnFire = Optional.of($$0);
/* 64 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setCrouching(Boolean $$0) {
/* 68 */     this.isCrouching = Optional.of($$0);
/* 69 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setSprinting(Boolean $$0) {
/* 73 */     this.isSprinting = Optional.of($$0);
/* 74 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setSwimming(Boolean $$0) {
/* 78 */     this.isSwimming = Optional.of($$0);
/* 79 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setIsBaby(Boolean $$0) {
/* 83 */     this.isBaby = Optional.of($$0);
/* 84 */     return this;
/*    */   }
/*    */   
/*    */   public EntityFlagsPredicate build() {
/* 88 */     return new EntityFlagsPredicate(this.isOnFire, this.isCrouching, this.isSprinting, this.isSwimming, this.isBaby);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityFlagsPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */