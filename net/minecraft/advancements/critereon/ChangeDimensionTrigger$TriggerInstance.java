/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<ResourceKey<Level>> from;
/*    */   private final Optional<ResourceKey<Level>> to;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/ChangeDimensionTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/ChangeDimensionTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/ChangeDimensionTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/ChangeDimensionTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 25 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ResourceKey<Level>> $$1, Optional<ResourceKey<Level>> $$2) { this.player = $$0; this.from = $$1; this.to = $$2; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/ChangeDimensionTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/ChangeDimensionTrigger$TriggerInstance;
/* 25 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ResourceKey<Level>> from() { return this.from; } public Optional<ResourceKey<Level>> to() { return this.to; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 30 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(ResourceKey.codec(Registries.DIMENSION), "from").forGetter(TriggerInstance::from), (App)ExtraCodecs.strictOptionalField(ResourceKey.codec(Registries.DIMENSION), "to").forGetter(TriggerInstance::to)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> changedDimension() {
/* 37 */     return CriteriaTriggers.CHANGED_DIMENSION.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty()));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> changedDimension(ResourceKey<Level> $$0, ResourceKey<Level> $$1) {
/* 41 */     return CriteriaTriggers.CHANGED_DIMENSION.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$0), Optional.of($$1)));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> changedDimensionTo(ResourceKey<Level> $$0) {
/* 45 */     return CriteriaTriggers.CHANGED_DIMENSION.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.of($$0)));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> changedDimensionFrom(ResourceKey<Level> $$0) {
/* 49 */     return CriteriaTriggers.CHANGED_DIMENSION.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$0), Optional.empty()));
/*    */   }
/*    */   
/*    */   public boolean matches(ResourceKey<Level> $$0, ResourceKey<Level> $$1) {
/* 53 */     if (this.from.isPresent() && this.from.get() != $$0) {
/* 54 */       return false;
/*    */     }
/* 56 */     if (this.to.isPresent() && this.to.get() != $$1) {
/* 57 */       return false;
/*    */     }
/* 59 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ChangeDimensionTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */