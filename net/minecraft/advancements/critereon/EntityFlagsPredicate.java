/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ public final class EntityFlagsPredicate extends Record {
/*    */   private final Optional<Boolean> isOnFire;
/*    */   private final Optional<Boolean> isCrouching;
/*    */   private final Optional<Boolean> isSprinting;
/*    */   private final Optional<Boolean> isSwimming;
/*    */   private final Optional<Boolean> isBaby;
/*    */   public static final Codec<EntityFlagsPredicate> CODEC;
/*    */   
/* 11 */   public EntityFlagsPredicate(Optional<Boolean> $$0, Optional<Boolean> $$1, Optional<Boolean> $$2, Optional<Boolean> $$3, Optional<Boolean> $$4) { this.isOnFire = $$0; this.isCrouching = $$1; this.isSprinting = $$2; this.isSwimming = $$3; this.isBaby = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EntityFlagsPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityFlagsPredicate; } public Optional<Boolean> isOnFire() { return this.isOnFire; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EntityFlagsPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityFlagsPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EntityFlagsPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityFlagsPredicate;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Boolean> isCrouching() { return this.isCrouching; } public Optional<Boolean> isSprinting() { return this.isSprinting; } public Optional<Boolean> isSwimming() { return this.isSwimming; } public Optional<Boolean> isBaby() { return this.isBaby; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "is_on_fire").forGetter(EntityFlagsPredicate::isOnFire), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "is_sneaking").forGetter(EntityFlagsPredicate::isCrouching), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "is_sprinting").forGetter(EntityFlagsPredicate::isSprinting), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "is_swimming").forGetter(EntityFlagsPredicate::isSwimming), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "is_baby").forGetter(EntityFlagsPredicate::isBaby)).apply((Applicative)$$0, EntityFlagsPredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(Entity $$0) {
/* 28 */     if (this.isOnFire.isPresent() && $$0.isOnFire() != ((Boolean)this.isOnFire.get()).booleanValue()) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     if (this.isCrouching.isPresent() && $$0.isCrouching() != ((Boolean)this.isCrouching.get()).booleanValue()) {
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     if (this.isSprinting.isPresent() && $$0.isSprinting() != ((Boolean)this.isSprinting.get()).booleanValue()) {
/* 37 */       return false;
/*    */     }
/*    */     
/* 40 */     if (this.isSwimming.isPresent() && $$0.isSwimming() != ((Boolean)this.isSwimming.get()).booleanValue()) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     if (this.isBaby.isPresent() && $$0 instanceof LivingEntity) { LivingEntity $$1 = (LivingEntity)$$0; if ($$1.isBaby() != ((Boolean)this.isBaby.get()).booleanValue()) {
/* 45 */         return false;
/*    */       } }
/*    */     
/* 48 */     return true;
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 52 */     private Optional<Boolean> isOnFire = Optional.empty();
/* 53 */     private Optional<Boolean> isCrouching = Optional.empty();
/* 54 */     private Optional<Boolean> isSprinting = Optional.empty();
/* 55 */     private Optional<Boolean> isSwimming = Optional.empty();
/* 56 */     private Optional<Boolean> isBaby = Optional.empty();
/*    */     
/*    */     public static Builder flags() {
/* 59 */       return new Builder();
/*    */     }
/*    */     
/*    */     public Builder setOnFire(Boolean $$0) {
/* 63 */       this.isOnFire = Optional.of($$0);
/* 64 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setCrouching(Boolean $$0) {
/* 68 */       this.isCrouching = Optional.of($$0);
/* 69 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setSprinting(Boolean $$0) {
/* 73 */       this.isSprinting = Optional.of($$0);
/* 74 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setSwimming(Boolean $$0) {
/* 78 */       this.isSwimming = Optional.of($$0);
/* 79 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setIsBaby(Boolean $$0) {
/* 83 */       this.isBaby = Optional.of($$0);
/* 84 */       return this;
/*    */     }
/*    */     
/*    */     public EntityFlagsPredicate build() {
/* 88 */       return new EntityFlagsPredicate(this.isOnFire, this.isCrouching, this.isSprinting, this.isSwimming, this.isBaby);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityFlagsPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */