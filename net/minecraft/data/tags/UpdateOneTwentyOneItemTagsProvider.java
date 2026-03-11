/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class UpdateOneTwentyOneItemTagsProvider
/*    */   extends ItemTagsProvider
/*    */ {
/*    */   public UpdateOneTwentyOneItemTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1, CompletableFuture<TagsProvider.TagLookup<Item>> $$2, CompletableFuture<TagsProvider.TagLookup<Block>> $$3) {
/* 15 */     super($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 21 */     tag(ItemTags.STAIRS).add(new Item[] { Items.TUFF_STAIRS, Items.POLISHED_TUFF_STAIRS, Items.TUFF_BRICK_STAIRS });
/* 22 */     tag(ItemTags.SLABS).add(new Item[] { Items.TUFF_SLAB, Items.POLISHED_TUFF_SLAB, Items.TUFF_BRICK_SLAB });
/* 23 */     tag(ItemTags.WALLS).add(new Item[] { Items.TUFF_WALL, Items.POLISHED_TUFF_WALL, Items.TUFF_BRICK_WALL });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\UpdateOneTwentyOneItemTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */