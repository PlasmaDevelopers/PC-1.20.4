/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ 
/*    */ public class SequenceFunction implements LootItemFunction {
/*    */   public static final Codec<SequenceFunction> CODEC;
/*    */   
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)LootItemFunctions.CODEC.listOf().fieldOf("functions").forGetter(())).apply((Applicative)$$0, SequenceFunction::new));
/*    */ 
/*    */ 
/*    */     
/* 17 */     INLINE_CODEC = LootItemFunctions.CODEC.listOf().xmap(SequenceFunction::new, $$0 -> $$0.functions);
/*    */   }
/*    */   public static final Codec<SequenceFunction> INLINE_CODEC; private final List<LootItemFunction> functions;
/*    */   private final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;
/*    */   
/*    */   private SequenceFunction(List<LootItemFunction> $$0) {
/* 23 */     this.functions = $$0;
/* 24 */     this.compositeFunction = LootItemFunctions.compose((List)$$0);
/*    */   }
/*    */   
/*    */   public static SequenceFunction of(List<LootItemFunction> $$0) {
/* 28 */     return new SequenceFunction(List.copyOf($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack apply(ItemStack $$0, LootContext $$1) {
/* 33 */     return this.compositeFunction.apply($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 38 */     super.validate($$0);
/*    */     
/* 40 */     for (int $$1 = 0; $$1 < this.functions.size(); $$1++) {
/* 41 */       ((LootItemFunction)this.functions.get($$1)).validate($$0.forChild(".function[" + $$1 + "]"));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 47 */     return LootItemFunctions.SEQUENCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SequenceFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */