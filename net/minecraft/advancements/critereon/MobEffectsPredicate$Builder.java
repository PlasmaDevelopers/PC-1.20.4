/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.effect.MobEffect;
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
/* 43 */   private final ImmutableMap.Builder<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> effectMap = ImmutableMap.builder();
/*    */   
/*    */   public static Builder effects() {
/* 46 */     return new Builder();
/*    */   }
/*    */   
/*    */   public Builder and(MobEffect $$0) {
/* 50 */     this.effectMap.put($$0.builtInRegistryHolder(), new MobEffectsPredicate.MobEffectInstancePredicate());
/* 51 */     return this;
/*    */   }
/*    */   
/*    */   public Builder and(MobEffect $$0, MobEffectsPredicate.MobEffectInstancePredicate $$1) {
/* 55 */     this.effectMap.put($$0.builtInRegistryHolder(), $$1);
/* 56 */     return this;
/*    */   }
/*    */   
/*    */   public Optional<MobEffectsPredicate> build() {
/* 60 */     return Optional.of(new MobEffectsPredicate((Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate>)this.effectMap.build()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\MobEffectsPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */