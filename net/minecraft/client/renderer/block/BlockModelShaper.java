/*    */ package net.minecraft.client.renderer.block;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.resources.model.BakedModel;
/*    */ import net.minecraft.client.resources.model.ModelManager;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BlockModelShaper
/*    */ {
/* 15 */   private Map<BlockState, BakedModel> modelByStateCache = Map.of();
/*    */   private final ModelManager modelManager;
/*    */   
/*    */   public BlockModelShaper(ModelManager $$0) {
/* 19 */     this.modelManager = $$0;
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite getParticleIcon(BlockState $$0) {
/* 23 */     return getBlockModel($$0).getParticleIcon();
/*    */   }
/*    */   
/*    */   public BakedModel getBlockModel(BlockState $$0) {
/* 27 */     BakedModel $$1 = this.modelByStateCache.get($$0);
/* 28 */     if ($$1 == null) {
/* 29 */       $$1 = this.modelManager.getMissingModel();
/*    */     }
/*    */     
/* 32 */     return $$1;
/*    */   }
/*    */   
/*    */   public ModelManager getModelManager() {
/* 36 */     return this.modelManager;
/*    */   }
/*    */   
/*    */   public void replaceCache(Map<BlockState, BakedModel> $$0) {
/* 40 */     this.modelByStateCache = $$0;
/*    */   }
/*    */   
/*    */   public static ModelResourceLocation stateToModelLocation(BlockState $$0) {
/* 44 */     return stateToModelLocation(BuiltInRegistries.BLOCK.getKey($$0.getBlock()), $$0);
/*    */   }
/*    */   
/*    */   public static ModelResourceLocation stateToModelLocation(ResourceLocation $$0, BlockState $$1) {
/* 48 */     return new ModelResourceLocation($$0, statePropertiesToString((Map<Property<?>, Comparable<?>>)$$1.getValues()));
/*    */   }
/*    */   
/*    */   public static String statePropertiesToString(Map<Property<?>, Comparable<?>> $$0) {
/* 52 */     StringBuilder $$1 = new StringBuilder();
/* 53 */     for (Map.Entry<Property<?>, Comparable<?>> $$2 : $$0.entrySet()) {
/* 54 */       if ($$1.length() != 0) {
/* 55 */         $$1.append(',');
/*    */       }
/*    */       
/* 58 */       Property<?> $$3 = $$2.getKey();
/* 59 */       $$1.append($$3.getName());
/* 60 */       $$1.append('=');
/* 61 */       $$1.append(getValue($$3, $$2.getValue()));
/*    */     } 
/*    */     
/* 64 */     return $$1.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T extends Comparable<T>> String getValue(Property<T> $$0, Comparable<?> $$1) {
/* 69 */     return $$0.getName($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\BlockModelShaper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */