/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public class ChanneledLightningTrigger extends SimpleCriterionTrigger<ChanneledLightningTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 20 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, Collection<? extends Entity> $$1) {
/* 24 */     List<LootContext> $$2 = (List<LootContext>)$$1.stream().map($$1 -> EntityPredicate.createContext($$0, $$1)).collect(Collectors.toList());
/* 25 */     trigger($$0, $$1 -> $$1.matches($$0));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final List<ContextAwarePredicate> victims; public static final Codec<TriggerInstance> CODEC;
/* 28 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, List<ContextAwarePredicate> $$1) { this.player = $$0; this.victims = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/ChanneledLightningTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 28 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/ChanneledLightningTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/ChanneledLightningTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/ChanneledLightningTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/ChanneledLightningTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/ChanneledLightningTrigger$TriggerInstance;
/* 28 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<ContextAwarePredicate> victims() { return this.victims; }
/*    */ 
/*    */     
/*    */     static {
/* 32 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC.listOf(), "victims", List.of()).forGetter(TriggerInstance::victims)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> channeledLightning(EntityPredicate.Builder... $$0) {
/* 38 */       return CriteriaTriggers.CHANNELED_LIGHTNING.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0)));
/*    */     }
/*    */     
/*    */     public boolean matches(Collection<? extends LootContext> $$0) {
/* 42 */       for (ContextAwarePredicate $$1 : this.victims) {
/* 43 */         boolean $$2 = false;
/* 44 */         for (LootContext $$3 : $$0) {
/* 45 */           if ($$1.matches($$3)) {
/* 46 */             $$2 = true;
/*    */             break;
/*    */           } 
/*    */         } 
/* 50 */         if (!$$2) {
/* 51 */           return false;
/*    */         }
/*    */       } 
/* 54 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public void validate(CriterionValidator $$0) {
/* 59 */       super.validate($$0);
/* 60 */       $$0.validateEntities(this.victims, ".victims");
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ChanneledLightningTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */