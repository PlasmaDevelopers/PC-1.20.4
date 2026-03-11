/*    */ package net.minecraft.client.model.geom.builders;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ 
/*    */ public class LayerDefinition {
/*    */   private final MeshDefinition mesh;
/*    */   private final MaterialDefinition material;
/*    */   
/*    */   private LayerDefinition(MeshDefinition $$0, MaterialDefinition $$1) {
/* 10 */     this.mesh = $$0;
/* 11 */     this.material = $$1;
/*    */   }
/*    */   
/*    */   public ModelPart bakeRoot() {
/* 15 */     return this.mesh.getRoot().bake(this.material.xTexSize, this.material.yTexSize);
/*    */   }
/*    */   
/*    */   public static LayerDefinition create(MeshDefinition $$0, int $$1, int $$2) {
/* 19 */     return new LayerDefinition($$0, new MaterialDefinition($$1, $$2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\builders\LayerDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */