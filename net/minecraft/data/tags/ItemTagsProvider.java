/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.TagBuilder;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public abstract class ItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {
/* 18 */   private final Map<TagKey<Block>, TagKey<Item>> tagsToCopy = new HashMap<>(); private final CompletableFuture<TagsProvider.TagLookup<Block>> blockTags;
/*    */   
/*    */   public ItemTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1, CompletableFuture<TagsProvider.TagLookup<Block>> $$2) {
/* 21 */     super($$0, Registries.ITEM, $$1, $$0 -> $$0.builtInRegistryHolder().key());
/* 22 */     this.blockTags = $$2;
/*    */   }
/*    */   
/*    */   public ItemTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1, CompletableFuture<TagsProvider.TagLookup<Item>> $$2, CompletableFuture<TagsProvider.TagLookup<Block>> $$3) {
/* 26 */     super($$0, Registries.ITEM, $$1, $$2, $$0 -> $$0.builtInRegistryHolder().key());
/* 27 */     this.blockTags = $$3;
/*    */   }
/*    */   
/*    */   protected void copy(TagKey<Block> $$0, TagKey<Item> $$1) {
/* 31 */     this.tagsToCopy.put($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected CompletableFuture<HolderLookup.Provider> createContentsProvider() {
/* 36 */     return super.createContentsProvider().thenCombineAsync(this.blockTags, ($$0, $$1) -> {
/*    */           this.tagsToCopy.forEach(());
/*    */           return $$0;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\ItemTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */