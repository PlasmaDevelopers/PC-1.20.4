/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.SimpleContainer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ import net.minecraft.world.item.crafting.SmeltingRecipe;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SmeltItemFunction extends LootItemConditionalFunction {
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final Codec<SmeltItemFunction> CODEC;
/*    */   static {
/* 21 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).apply((Applicative)$$0, SmeltItemFunction::new));
/*    */   }
/*    */   private SmeltItemFunction(List<LootItemCondition> $$0) {
/* 24 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 29 */     return LootItemFunctions.FURNACE_SMELT;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 34 */     if ($$0.isEmpty()) {
/* 35 */       return $$0;
/*    */     }
/*    */     
/* 38 */     Optional<RecipeHolder<SmeltingRecipe>> $$2 = $$1.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, (Container)new SimpleContainer(new ItemStack[] { $$0 }, ), (Level)$$1.getLevel());
/* 39 */     if ($$2.isPresent()) {
/* 40 */       ItemStack $$3 = ((SmeltingRecipe)((RecipeHolder)$$2.get()).value()).getResultItem($$1.getLevel().registryAccess());
/*    */       
/* 42 */       if (!$$3.isEmpty()) {
/* 43 */         return $$3.copyWithCount($$0.getCount());
/*    */       }
/*    */     } 
/*    */     
/* 47 */     LOGGER.warn("Couldn't smelt {} because there is no smelting recipe", $$0);
/* 48 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> smelted() {
/* 52 */     return simpleBuilder(SmeltItemFunction::new);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SmeltItemFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */