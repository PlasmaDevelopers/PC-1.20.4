/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.critereon.EntityPredicate;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public final class LootItemEntityPropertyCondition extends Record implements LootItemCondition {
/*    */   private final Optional<EntityPredicate> predicate;
/*    */   private final LootContext.EntityTarget entityTarget;
/*    */   public static final Codec<LootItemEntityPropertyCondition> CODEC;
/*    */   
/* 17 */   public LootItemEntityPropertyCondition(Optional<EntityPredicate> $$0, LootContext.EntityTarget $$1) { this.predicate = $$0; this.entityTarget = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemEntityPropertyCondition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 17 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemEntityPropertyCondition; } public Optional<EntityPredicate> predicate() { return this.predicate; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemEntityPropertyCondition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemEntityPropertyCondition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/LootItemEntityPropertyCondition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LootItemEntityPropertyCondition;
/* 17 */     //   0	8	1	$$0	Ljava/lang/Object; } public LootContext.EntityTarget entityTarget() { return this.entityTarget; }
/*    */ 
/*    */   
/*    */   static {
/* 21 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.CODEC, "predicate").forGetter(LootItemEntityPropertyCondition::predicate), (App)LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(LootItemEntityPropertyCondition::entityTarget)).apply((Applicative)$$0, LootItemEntityPropertyCondition::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 28 */     return LootItemConditions.ENTITY_PROPERTIES;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 33 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.ORIGIN, this.entityTarget.getParam());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 38 */     Entity $$1 = (Entity)$$0.getParamOrNull(this.entityTarget.getParam());
/* 39 */     Vec3 $$2 = (Vec3)$$0.getParamOrNull(LootContextParams.ORIGIN);
/* 40 */     return (this.predicate.isEmpty() || ((EntityPredicate)this.predicate.get()).matches($$0.getLevel(), $$2, $$1));
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder entityPresent(LootContext.EntityTarget $$0) {
/* 44 */     return hasProperties($$0, EntityPredicate.Builder.entity());
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder hasProperties(LootContext.EntityTarget $$0, EntityPredicate.Builder $$1) {
/* 48 */     return () -> new LootItemEntityPropertyCondition(Optional.of($$0.build()), $$1);
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder hasProperties(LootContext.EntityTarget $$0, EntityPredicate $$1) {
/* 52 */     return () -> new LootItemEntityPropertyCondition(Optional.of($$0), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LootItemEntityPropertyCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */