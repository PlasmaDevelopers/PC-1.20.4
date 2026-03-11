/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.damagesource.DamageType;
/*    */ 
/*    */ public final class DamageSourcePredicate extends Record {
/*    */   private final List<TagPredicate<DamageType>> tags;
/*    */   private final Optional<EntityPredicate> directEntity;
/*    */   private final Optional<EntityPredicate> sourceEntity;
/*    */   public static final Codec<DamageSourcePredicate> CODEC;
/*    */   
/* 17 */   public DamageSourcePredicate(List<TagPredicate<DamageType>> $$0, Optional<EntityPredicate> $$1, Optional<EntityPredicate> $$2) { this.tags = $$0; this.directEntity = $$1; this.sourceEntity = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/DamageSourcePredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 17 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/DamageSourcePredicate; } public List<TagPredicate<DamageType>> tags() { return this.tags; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/DamageSourcePredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/DamageSourcePredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/DamageSourcePredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/DamageSourcePredicate;
/* 17 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<EntityPredicate> directEntity() { return this.directEntity; } public Optional<EntityPredicate> sourceEntity() { return this.sourceEntity; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 22 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(TagPredicate.<T>codec(Registries.DAMAGE_TYPE).listOf(), "tags", List.of()).forGetter(DamageSourcePredicate::tags), (App)ExtraCodecs.strictOptionalField(EntityPredicate.CODEC, "direct_entity").forGetter(DamageSourcePredicate::directEntity), (App)ExtraCodecs.strictOptionalField(EntityPredicate.CODEC, "source_entity").forGetter(DamageSourcePredicate::sourceEntity)).apply((Applicative)$$0, DamageSourcePredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(ServerPlayer $$0, DamageSource $$1) {
/* 29 */     return matches($$0.serverLevel(), $$0.position(), $$1);
/*    */   }
/*    */   
/*    */   public boolean matches(ServerLevel $$0, Vec3 $$1, DamageSource $$2) {
/* 33 */     for (TagPredicate<DamageType> $$3 : this.tags) {
/* 34 */       if (!$$3.matches($$2.typeHolder())) {
/* 35 */         return false;
/*    */       }
/*    */     } 
/* 38 */     if (this.directEntity.isPresent() && !((EntityPredicate)this.directEntity.get()).matches($$0, $$1, $$2.getDirectEntity())) {
/* 39 */       return false;
/*    */     }
/* 41 */     if (this.sourceEntity.isPresent() && !((EntityPredicate)this.sourceEntity.get()).matches($$0, $$1, $$2.getEntity())) {
/* 42 */       return false;
/*    */     }
/* 44 */     return true;
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 48 */     private final ImmutableList.Builder<TagPredicate<DamageType>> tags = ImmutableList.builder();
/* 49 */     private Optional<EntityPredicate> directEntity = Optional.empty();
/* 50 */     private Optional<EntityPredicate> sourceEntity = Optional.empty();
/*    */     
/*    */     public static Builder damageType() {
/* 53 */       return new Builder();
/*    */     }
/*    */     
/*    */     public Builder tag(TagPredicate<DamageType> $$0) {
/* 57 */       this.tags.add($$0);
/* 58 */       return this;
/*    */     }
/*    */     
/*    */     public Builder direct(EntityPredicate.Builder $$0) {
/* 62 */       this.directEntity = Optional.of($$0.build());
/* 63 */       return this;
/*    */     }
/*    */     
/*    */     public Builder source(EntityPredicate.Builder $$0) {
/* 67 */       this.sourceEntity = Optional.of($$0.build());
/* 68 */       return this;
/*    */     }
/*    */     
/*    */     public DamageSourcePredicate build() {
/* 72 */       return new DamageSourcePredicate((List<TagPredicate<DamageType>>)this.tags.build(), this.directEntity, this.sourceEntity);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\DamageSourcePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */