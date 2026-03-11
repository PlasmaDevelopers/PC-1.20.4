/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Predicate
/*    */ {
/*    */   private final ResourceLocation property;
/*    */   private final float value;
/*    */   
/*    */   public Predicate(ResourceLocation $$0, float $$1) {
/* 66 */     this.property = $$0;
/* 67 */     this.value = $$1;
/*    */   }
/*    */   
/*    */   public ResourceLocation getProperty() {
/* 71 */     return this.property;
/*    */   }
/*    */   
/*    */   public float getValue() {
/* 75 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\ItemOverride$Predicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */