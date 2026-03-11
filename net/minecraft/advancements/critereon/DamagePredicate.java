/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ public final class DamagePredicate extends Record {
/*    */   private final MinMaxBounds.Doubles dealtDamage;
/*    */   private final MinMaxBounds.Doubles takenDamage;
/*    */   private final Optional<EntityPredicate> sourceEntity;
/*    */   private final Optional<Boolean> blocked;
/*    */   private final Optional<DamageSourcePredicate> type;
/*    */   public static final Codec<DamagePredicate> CODEC;
/*    */   
/* 11 */   public DamagePredicate(MinMaxBounds.Doubles $$0, MinMaxBounds.Doubles $$1, Optional<EntityPredicate> $$2, Optional<Boolean> $$3, Optional<DamageSourcePredicate> $$4) { this.dealtDamage = $$0; this.takenDamage = $$1; this.sourceEntity = $$2; this.blocked = $$3; this.type = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/DamagePredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/DamagePredicate; } public MinMaxBounds.Doubles dealtDamage() { return this.dealtDamage; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/DamagePredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/DamagePredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/DamagePredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/DamagePredicate;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Doubles takenDamage() { return this.takenDamage; } public Optional<EntityPredicate> sourceEntity() { return this.sourceEntity; } public Optional<Boolean> blocked() { return this.blocked; } public Optional<DamageSourcePredicate> type() { return this.type; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "dealt", MinMaxBounds.Doubles.ANY).forGetter(DamagePredicate::dealtDamage), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "taken", MinMaxBounds.Doubles.ANY).forGetter(DamagePredicate::takenDamage), (App)ExtraCodecs.strictOptionalField(EntityPredicate.CODEC, "source_entity").forGetter(DamagePredicate::sourceEntity), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "blocked").forGetter(DamagePredicate::blocked), (App)ExtraCodecs.strictOptionalField(DamageSourcePredicate.CODEC, "type").forGetter(DamagePredicate::type)).apply((Applicative)$$0, DamagePredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(ServerPlayer $$0, DamageSource $$1, float $$2, float $$3, boolean $$4) {
/* 27 */     if (!this.dealtDamage.matches($$2)) {
/* 28 */       return false;
/*    */     }
/* 30 */     if (!this.takenDamage.matches($$3)) {
/* 31 */       return false;
/*    */     }
/* 33 */     if (this.sourceEntity.isPresent() && !((EntityPredicate)this.sourceEntity.get()).matches($$0, $$1.getEntity())) {
/* 34 */       return false;
/*    */     }
/* 36 */     if (this.blocked.isPresent() && ((Boolean)this.blocked.get()).booleanValue() != $$4) {
/* 37 */       return false;
/*    */     }
/* 39 */     if (this.type.isPresent() && !((DamageSourcePredicate)this.type.get()).matches($$0, $$1)) {
/* 40 */       return false;
/*    */     }
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 46 */     private MinMaxBounds.Doubles dealtDamage = MinMaxBounds.Doubles.ANY;
/* 47 */     private MinMaxBounds.Doubles takenDamage = MinMaxBounds.Doubles.ANY;
/* 48 */     private Optional<EntityPredicate> sourceEntity = Optional.empty();
/* 49 */     private Optional<Boolean> blocked = Optional.empty();
/* 50 */     private Optional<DamageSourcePredicate> type = Optional.empty();
/*    */     
/*    */     public static Builder damageInstance() {
/* 53 */       return new Builder();
/*    */     }
/*    */     
/*    */     public Builder dealtDamage(MinMaxBounds.Doubles $$0) {
/* 57 */       this.dealtDamage = $$0;
/* 58 */       return this;
/*    */     }
/*    */     
/*    */     public Builder takenDamage(MinMaxBounds.Doubles $$0) {
/* 62 */       this.takenDamage = $$0;
/* 63 */       return this;
/*    */     }
/*    */     
/*    */     public Builder sourceEntity(EntityPredicate $$0) {
/* 67 */       this.sourceEntity = Optional.of($$0);
/* 68 */       return this;
/*    */     }
/*    */     
/*    */     public Builder blocked(Boolean $$0) {
/* 72 */       this.blocked = Optional.of($$0);
/* 73 */       return this;
/*    */     }
/*    */     
/*    */     public Builder type(DamageSourcePredicate $$0) {
/* 77 */       this.type = Optional.of($$0);
/* 78 */       return this;
/*    */     }
/*    */     
/*    */     public Builder type(DamageSourcePredicate.Builder $$0) {
/* 82 */       this.type = Optional.of($$0.build());
/* 83 */       return this;
/*    */     }
/*    */     
/*    */     public DamagePredicate build() {
/* 87 */       return new DamagePredicate(this.dealtDamage, this.takenDamage, this.sourceEntity, this.blocked, this.type);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\DamagePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */