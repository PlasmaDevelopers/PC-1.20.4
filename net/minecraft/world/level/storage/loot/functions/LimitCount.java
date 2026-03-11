/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.IntRange;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class LimitCount extends LootItemConditionalFunction {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)IntRange.CODEC.fieldOf("limit").forGetter(())).apply((Applicative)$$0, LimitCount::new));
/*    */   }
/*    */   
/*    */   public static final Codec<LimitCount> CODEC;
/*    */   private final IntRange limiter;
/*    */   
/*    */   private LimitCount(List<LootItemCondition> $$0, IntRange $$1) {
/* 22 */     super($$0);
/* 23 */     this.limiter = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 28 */     return LootItemFunctions.LIMIT_COUNT;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 33 */     return this.limiter.getReferencedContextParams();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 38 */     int $$2 = this.limiter.clamp($$1, $$0.getCount());
/* 39 */     $$0.setCount($$2);
/* 40 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> limitCount(IntRange $$0) {
/* 44 */     return simpleBuilder($$1 -> new LimitCount($$1, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\LimitCount.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */