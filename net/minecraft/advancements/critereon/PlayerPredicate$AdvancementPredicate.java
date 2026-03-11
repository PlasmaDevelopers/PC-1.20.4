/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.advancements.AdvancementProgress;
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
/*    */ interface AdvancementPredicate
/*    */   extends Predicate<AdvancementProgress>
/*    */ {
/*    */   public static final Codec<AdvancementPredicate> CODEC;
/*    */   
/*    */   static {
/* 63 */     CODEC = Codec.either(PlayerPredicate.AdvancementDonePredicate.CODEC, PlayerPredicate.AdvancementCriterionsPredicate.CODEC).xmap($$0 -> (AdvancementPredicate)$$0.map((), ()), $$0 -> {
/*    */           if ($$0 instanceof PlayerPredicate.AdvancementDonePredicate) {
/*    */             PlayerPredicate.AdvancementDonePredicate $$1 = (PlayerPredicate.AdvancementDonePredicate)$$0;
/*    */             return Either.left($$1);
/*    */           } 
/*    */           if ($$0 instanceof PlayerPredicate.AdvancementCriterionsPredicate) {
/*    */             PlayerPredicate.AdvancementCriterionsPredicate $$2 = (PlayerPredicate.AdvancementCriterionsPredicate)$$0;
/*    */             return Either.right($$2);
/*    */           } 
/*    */           throw new UnsupportedOperationException();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\PlayerPredicate$AdvancementPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */