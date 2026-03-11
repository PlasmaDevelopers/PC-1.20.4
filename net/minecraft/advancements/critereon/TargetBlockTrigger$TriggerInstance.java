/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final MinMaxBounds.Ints signalStrength;
/*    */   private final Optional<ContextAwarePredicate> projectile;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/TargetBlockTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/TargetBlockTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/TargetBlockTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/TargetBlockTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 26 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, MinMaxBounds.Ints $$1, Optional<ContextAwarePredicate> $$2) { this.player = $$0; this.signalStrength = $$1; this.projectile = $$2; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/TargetBlockTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/TargetBlockTrigger$TriggerInstance;
/* 26 */     //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Ints signalStrength() { return this.signalStrength; } public Optional<ContextAwarePredicate> projectile() { return this.projectile; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 31 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "signal_strength", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::signalStrength), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "projectile").forGetter(TriggerInstance::projectile)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> targetHit(MinMaxBounds.Ints $$0, Optional<ContextAwarePredicate> $$1) {
/* 38 */     return CriteriaTriggers.TARGET_BLOCK_HIT.createCriterion(new TriggerInstance(Optional.empty(), $$0, $$1));
/*    */   }
/*    */   
/*    */   public boolean matches(LootContext $$0, Vec3 $$1, int $$2) {
/* 42 */     if (!this.signalStrength.matches($$2)) {
/* 43 */       return false;
/*    */     }
/* 45 */     if (this.projectile.isPresent() && !((ContextAwarePredicate)this.projectile.get()).matches($$0)) {
/* 46 */       return false;
/*    */     }
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(CriterionValidator $$0) {
/* 53 */     super.validate($$0);
/* 54 */     $$0.validateEntity(this.projectile, ".projectile");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\TargetBlockTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */