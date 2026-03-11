/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.model.BakedModel;
/*    */ import net.minecraft.client.resources.model.ModelManager;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ItemModelShaper {
/* 15 */   public final Int2ObjectMap<ModelResourceLocation> shapes = (Int2ObjectMap<ModelResourceLocation>)new Int2ObjectOpenHashMap(256);
/* 16 */   private final Int2ObjectMap<BakedModel> shapesCache = (Int2ObjectMap<BakedModel>)new Int2ObjectOpenHashMap(256);
/*    */   private final ModelManager modelManager;
/*    */   
/*    */   public ItemModelShaper(ModelManager $$0) {
/* 20 */     this.modelManager = $$0;
/*    */   }
/*    */   
/*    */   public BakedModel getItemModel(ItemStack $$0) {
/* 24 */     BakedModel $$1 = getItemModel($$0.getItem());
/*    */     
/* 26 */     return ($$1 == null) ? this.modelManager.getMissingModel() : $$1;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BakedModel getItemModel(Item $$0) {
/* 31 */     return (BakedModel)this.shapesCache.get(getIndex($$0));
/*    */   }
/*    */   
/*    */   private static int getIndex(Item $$0) {
/* 35 */     return Item.getId($$0);
/*    */   }
/*    */   
/*    */   public void register(Item $$0, ModelResourceLocation $$1) {
/* 39 */     this.shapes.put(getIndex($$0), $$1);
/*    */   }
/*    */   
/*    */   public ModelManager getModelManager() {
/* 43 */     return this.modelManager;
/*    */   }
/*    */   
/*    */   public void rebuildCache() {
/* 47 */     this.shapesCache.clear();
/* 48 */     for (ObjectIterator<Map.Entry<Integer, ModelResourceLocation>> objectIterator = this.shapes.entrySet().iterator(); objectIterator.hasNext(); ) { Map.Entry<Integer, ModelResourceLocation> $$0 = objectIterator.next();
/* 49 */       this.shapesCache.put($$0.getKey(), this.modelManager.getModel($$0.getValue())); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\ItemModelShaper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */