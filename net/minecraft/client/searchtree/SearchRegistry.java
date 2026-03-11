/*    */ package net.minecraft.client.searchtree;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class SearchRegistry
/*    */   implements ResourceManagerReloadListener {
/*    */   public static class Key<T> {}
/*    */   
/* 16 */   public static final Key<ItemStack> CREATIVE_NAMES = new Key<>();
/* 17 */   public static final Key<ItemStack> CREATIVE_TAGS = new Key<>();
/* 18 */   public static final Key<RecipeCollection> RECIPE_COLLECTIONS = new Key<>();
/*    */   
/* 20 */   private final Map<Key<?>, TreeEntry<?>> searchTrees = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void onResourceManagerReload(ResourceManager $$0) {
/* 24 */     for (TreeEntry<?> $$1 : this.searchTrees.values()) {
/* 25 */       $$1.refresh();
/*    */     }
/*    */   }
/*    */   
/*    */   public <T> void register(Key<T> $$0, TreeBuilderSupplier<T> $$1) {
/* 30 */     this.searchTrees.put($$0, new TreeEntry($$1));
/*    */   }
/*    */   
/*    */   private <T> TreeEntry<T> getSupplier(Key<T> $$0) {
/* 34 */     TreeEntry<T> $$1 = (TreeEntry<T>)this.searchTrees.get($$0);
/* 35 */     if ($$1 == null) {
/* 36 */       throw new IllegalStateException("Tree builder not registered");
/*    */     }
/* 38 */     return $$1;
/*    */   }
/*    */   
/*    */   public <T> void populate(Key<T> $$0, List<T> $$1) {
/* 42 */     getSupplier($$0).populate($$1);
/*    */   }
/*    */   
/*    */   public <T> SearchTree<T> getTree(Key<T> $$0) {
/* 46 */     return (getSupplier($$0)).tree;
/*    */   }
/*    */   
/*    */   public static interface TreeBuilderSupplier<T> extends Function<List<T>, RefreshableSearchTree<T>> {}
/*    */   
/*    */   private static class TreeEntry<T> {
/*    */     private final SearchRegistry.TreeBuilderSupplier<T> factory;
/* 53 */     RefreshableSearchTree<T> tree = RefreshableSearchTree.empty();
/*    */     
/*    */     TreeEntry(SearchRegistry.TreeBuilderSupplier<T> $$0) {
/* 56 */       this.factory = $$0;
/*    */     }
/*    */     
/*    */     void populate(List<T> $$0) {
/* 60 */       this.tree = this.factory.apply($$0);
/* 61 */       this.tree.refresh();
/*    */     }
/*    */     
/*    */     void refresh() {
/* 65 */       this.tree.refresh();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\SearchRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */