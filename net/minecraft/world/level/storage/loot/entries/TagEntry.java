/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function6;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class TagEntry extends LootPoolSingletonContainer {
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)TagKey.codec(Registries.ITEM).fieldOf("name").forGetter(()), (App)Codec.BOOL.fieldOf("expand").forGetter(())).and(singletonFields($$0)).apply((Applicative)$$0, TagEntry::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<TagEntry> CODEC;
/*    */   private final TagKey<Item> tag;
/*    */   private final boolean expand;
/*    */   
/*    */   private TagEntry(TagKey<Item> $$0, boolean $$1, int $$2, int $$3, List<LootItemCondition> $$4, List<LootItemFunction> $$5) {
/* 28 */     super($$2, $$3, $$4, $$5);
/* 29 */     this.tag = $$0;
/* 30 */     this.expand = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryType getType() {
/* 35 */     return LootPoolEntries.TAG;
/*    */   }
/*    */ 
/*    */   
/*    */   public void createItemStack(Consumer<ItemStack> $$0, LootContext $$1) {
/* 40 */     BuiltInRegistries.ITEM.getTagOrEmpty(this.tag).forEach($$1 -> $$0.accept(new ItemStack($$1)));
/*    */   }
/*    */   
/*    */   private boolean expandTag(LootContext $$0, Consumer<LootPoolEntry> $$1) {
/* 44 */     if (canRun($$0)) {
/* 45 */       for (Holder<Item> $$2 : (Iterable<Holder<Item>>)BuiltInRegistries.ITEM.getTagOrEmpty(this.tag)) {
/* 46 */         $$1.accept(new LootPoolSingletonContainer.EntryBase()
/*    */             {
/*    */               public void createItemStack(Consumer<ItemStack> $$0, LootContext $$1) {
/* 49 */                 $$0.accept(new ItemStack(item));
/*    */               }
/*    */             });
/*    */       } 
/* 53 */       return true;
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean expand(LootContext $$0, Consumer<LootPoolEntry> $$1) {
/* 60 */     if (this.expand) {
/* 61 */       return expandTag($$0, $$1);
/*    */     }
/* 63 */     return super.expand($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public static LootPoolSingletonContainer.Builder<?> tagContents(TagKey<Item> $$0) {
/* 68 */     return simpleBuilder(($$1, $$2, $$3, $$4) -> new TagEntry($$0, false, $$1, $$2, $$3, $$4));
/*    */   }
/*    */   
/*    */   public static LootPoolSingletonContainer.Builder<?> expandTag(TagKey<Item> $$0) {
/* 72 */     return simpleBuilder(($$1, $$2, $$3, $$4) -> new TagEntry($$0, true, $$1, $$2, $$3, $$4));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\TagEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */