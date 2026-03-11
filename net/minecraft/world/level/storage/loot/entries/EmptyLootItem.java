/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class EmptyLootItem extends LootPoolSingletonContainer {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> singletonFields($$0).apply((Applicative)$$0, EmptyLootItem::new));
/*    */   } public static final Codec<EmptyLootItem> CODEC;
/*    */   private EmptyLootItem(int $$0, int $$1, List<LootItemCondition> $$2, List<LootItemFunction> $$3) {
/* 17 */     super($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryType getType() {
/* 22 */     return LootPoolEntries.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void createItemStack(Consumer<ItemStack> $$0, LootContext $$1) {}
/*    */ 
/*    */   
/*    */   public static LootPoolSingletonContainer.Builder<?> emptyItem() {
/* 30 */     return simpleBuilder(EmptyLootItem::new);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\EmptyLootItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */