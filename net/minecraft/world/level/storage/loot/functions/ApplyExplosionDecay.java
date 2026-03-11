/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class ApplyExplosionDecay extends LootItemConditionalFunction {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).apply((Applicative)$$0, ApplyExplosionDecay::new));
/*    */   } public static final Codec<ApplyExplosionDecay> CODEC;
/*    */   private ApplyExplosionDecay(List<LootItemCondition> $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 22 */     return LootItemFunctions.EXPLOSION_DECAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 27 */     Float $$2 = (Float)$$1.getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
/*    */     
/* 29 */     if ($$2 != null) {
/* 30 */       RandomSource $$3 = $$1.getRandom();
/*    */       
/* 32 */       float $$4 = 1.0F / $$2.floatValue();
/* 33 */       int $$5 = $$0.getCount();
/* 34 */       int $$6 = 0;
/* 35 */       for (int $$7 = 0; $$7 < $$5; $$7++) {
/* 36 */         if ($$3.nextFloat() <= $$4) {
/* 37 */           $$6++;
/*    */         }
/*    */       } 
/*    */       
/* 41 */       $$0.setCount($$6);
/*    */     } 
/* 43 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> explosionDecay() {
/* 47 */     return simpleBuilder(ApplyExplosionDecay::new);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\ApplyExplosionDecay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */