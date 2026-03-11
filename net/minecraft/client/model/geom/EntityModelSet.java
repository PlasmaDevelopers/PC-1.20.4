/*    */ package net.minecraft.client.model.geom;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*    */ 
/*    */ public class EntityModelSet
/*    */   implements ResourceManagerReloadListener
/*    */ {
/* 12 */   private Map<ModelLayerLocation, LayerDefinition> roots = (Map<ModelLayerLocation, LayerDefinition>)ImmutableMap.of();
/*    */   
/*    */   public ModelPart bakeLayer(ModelLayerLocation $$0) {
/* 15 */     LayerDefinition $$1 = this.roots.get($$0);
/* 16 */     if ($$1 == null) {
/* 17 */       throw new IllegalArgumentException("No model for layer " + $$0);
/*    */     }
/* 19 */     return $$1.bakeRoot();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onResourceManagerReload(ResourceManager $$0) {
/* 24 */     this.roots = (Map<ModelLayerLocation, LayerDefinition>)ImmutableMap.copyOf(LayerDefinitions.createRoots());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\EntityModelSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */