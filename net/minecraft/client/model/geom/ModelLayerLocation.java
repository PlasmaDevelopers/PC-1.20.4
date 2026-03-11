/*    */ package net.minecraft.client.model.geom;
/*    */ 
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public final class ModelLayerLocation {
/*    */   private final ResourceLocation model;
/*    */   private final String layer;
/*    */   
/*    */   public ModelLayerLocation(ResourceLocation $$0, String $$1) {
/* 10 */     this.model = $$0;
/* 11 */     this.layer = $$1;
/*    */   }
/*    */   
/*    */   public ResourceLocation getModel() {
/* 15 */     return this.model;
/*    */   }
/*    */   
/*    */   public String getLayer() {
/* 19 */     return this.layer;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 24 */     if (this == $$0) {
/* 25 */       return true;
/*    */     }
/* 27 */     if ($$0 instanceof ModelLayerLocation) { ModelLayerLocation $$1 = (ModelLayerLocation)$$0;
/* 28 */       return (this.model.equals($$1.model) && this.layer.equals($$1.layer)); }
/*    */     
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 35 */     int $$0 = this.model.hashCode();
/* 36 */     $$0 = 31 * $$0 + this.layer.hashCode();
/* 37 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "" + this.model + "#" + this.model;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\ModelLayerLocation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */