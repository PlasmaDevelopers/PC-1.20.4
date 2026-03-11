/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SlideDownBlockTrigger extends SimpleCriterionTrigger<SlideDownBlockTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 20 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, BlockState $$1) {
/* 24 */     trigger($$0, $$1 -> $$1.matches($$0));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<Holder<Block>> block; private final Optional<StatePropertiesPredicate> state; public static final Codec<TriggerInstance> CODEC;
/* 27 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<Holder<Block>> $$1, Optional<StatePropertiesPredicate> $$2) { this.player = $$0; this.block = $$1; this.state = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/SlideDownBlockTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 27 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/SlideDownBlockTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/SlideDownBlockTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/SlideDownBlockTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/SlideDownBlockTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/SlideDownBlockTrigger$TriggerInstance;
/* 27 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Holder<Block>> block() { return this.block; } public Optional<StatePropertiesPredicate> state() { return this.state; }
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 32 */       CODEC = ExtraCodecs.validate(RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(BuiltInRegistries.BLOCK.holderByNameCodec(), "block").forGetter(TriggerInstance::block), (App)ExtraCodecs.strictOptionalField(StatePropertiesPredicate.CODEC, "state").forGetter(TriggerInstance::state)).apply((Applicative)$$0, TriggerInstance::new)), TriggerInstance::validate);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private static DataResult<TriggerInstance> validate(TriggerInstance $$0) {
/* 39 */       return $$0.block.<DataResult<TriggerInstance>>flatMap($$1 -> $$0.state.flatMap(()).map(()))
/*    */ 
/*    */         
/* 42 */         .orElseGet(() -> DataResult.success($$0));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> slidesDownBlock(Block $$0) {
/* 46 */       return CriteriaTriggers.HONEY_BLOCK_SLIDE.createCriterion(new TriggerInstance(Optional.empty(), (Optional)Optional.of($$0.builtInRegistryHolder()), Optional.empty()));
/*    */     }
/*    */     
/*    */     public boolean matches(BlockState $$0) {
/* 50 */       if (this.block.isPresent() && !$$0.is(this.block.get())) {
/* 51 */         return false;
/*    */       }
/* 53 */       if (this.state.isPresent() && !((StatePropertiesPredicate)this.state.get()).matches($$0)) {
/* 54 */         return false;
/*    */       }
/* 56 */       return true;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\SlideDownBlockTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */