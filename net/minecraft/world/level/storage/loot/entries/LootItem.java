/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class LootItem extends LootPoolSingletonContainer {
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("name").forGetter(())).and(singletonFields($$0)).apply((Applicative)$$0, LootItem::new));
/*    */   }
/*    */   
/*    */   public static final Codec<LootItem> CODEC;
/*    */   private final Holder<Item> item;
/*    */   
/*    */   private LootItem(Holder<Item> $$0, int $$1, int $$2, List<LootItemCondition> $$3, List<LootItemFunction> $$4) {
/* 25 */     super($$1, $$2, $$3, $$4);
/* 26 */     this.item = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryType getType() {
/* 31 */     return LootPoolEntries.ITEM;
/*    */   }
/*    */ 
/*    */   
/*    */   public void createItemStack(Consumer<ItemStack> $$0, LootContext $$1) {
/* 36 */     $$0.accept(new ItemStack(this.item));
/*    */   }
/*    */   
/*    */   public static LootPoolSingletonContainer.Builder<?> lootTableItem(ItemLike $$0) {
/* 40 */     return simpleBuilder(($$1, $$2, $$3, $$4) -> new LootItem((Holder<Item>)$$0.asItem().builtInRegistryHolder(), $$1, $$2, $$3, $$4));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\LootItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */