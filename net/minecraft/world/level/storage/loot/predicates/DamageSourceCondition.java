/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.advancements.critereon.DamageSourcePredicate;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public final class DamageSourceCondition extends Record implements LootItemCondition {
/*    */   private final Optional<DamageSourcePredicate> predicate;
/*    */   public static final Codec<DamageSourceCondition> CODEC;
/*    */   
/* 17 */   public DamageSourceCondition(Optional<DamageSourcePredicate> $$0) { this.predicate = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/DamageSourceCondition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 17 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/DamageSourceCondition; } public Optional<DamageSourcePredicate> predicate() { return this.predicate; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/DamageSourceCondition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/DamageSourceCondition; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/DamageSourceCondition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/DamageSourceCondition;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } static {
/* 20 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(DamageSourcePredicate.CODEC, "predicate").forGetter(DamageSourceCondition::predicate)).apply((Applicative)$$0, DamageSourceCondition::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 26 */     return LootItemConditions.DAMAGE_SOURCE_PROPERTIES;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 31 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.ORIGIN, LootContextParams.DAMAGE_SOURCE);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 36 */     DamageSource $$1 = (DamageSource)$$0.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
/* 37 */     Vec3 $$2 = (Vec3)$$0.getParamOrNull(LootContextParams.ORIGIN);
/* 38 */     if ($$2 == null || $$1 == null) {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     return (this.predicate.isEmpty() || ((DamageSourcePredicate)this.predicate.get()).matches($$0.getLevel(), $$2, $$1));
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder hasDamageSource(DamageSourcePredicate.Builder $$0) {
/* 46 */     return () -> new DamageSourceCondition(Optional.of($$0.build()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\DamageSourceCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */