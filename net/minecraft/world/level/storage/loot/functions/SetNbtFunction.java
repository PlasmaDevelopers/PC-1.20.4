/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.TagParser;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class SetNbtFunction extends LootItemConditionalFunction {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)TagParser.AS_CODEC.fieldOf("tag").forGetter(())).apply((Applicative)$$0, SetNbtFunction::new));
/*    */   }
/*    */   
/*    */   public static final Codec<SetNbtFunction> CODEC;
/*    */   private final CompoundTag tag;
/*    */   
/*    */   private SetNbtFunction(List<LootItemCondition> $$0, CompoundTag $$1) {
/* 21 */     super($$0);
/* 22 */     this.tag = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 27 */     return LootItemFunctions.SET_NBT;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 32 */     $$0.getOrCreateTag().merge(this.tag);
/* 33 */     return $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static LootItemConditionalFunction.Builder<?> setTag(CompoundTag $$0) {
/* 41 */     return simpleBuilder($$1 -> new SetNbtFunction($$1, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetNbtFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */