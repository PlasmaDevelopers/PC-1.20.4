/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class PanoramaRenderer
/*    */ {
/*    */   private final Minecraft minecraft;
/*    */   private final CubeMap cubeMap;
/*    */   private float spin;
/*    */   private float bob;
/*    */   
/*    */   public PanoramaRenderer(CubeMap $$0) {
/* 13 */     this.cubeMap = $$0;
/* 14 */     this.minecraft = Minecraft.getInstance();
/*    */   }
/*    */   
/*    */   public void render(float $$0, float $$1) {
/* 18 */     float $$2 = (float)($$0 * ((Double)this.minecraft.options.panoramaSpeed().get()).doubleValue());
/* 19 */     this.spin = wrap(this.spin + $$2 * 0.1F, 360.0F);
/* 20 */     this.bob = wrap(this.bob + $$2 * 0.001F, 6.2831855F);
/*    */     
/* 22 */     this.cubeMap.render(this.minecraft, 10.0F, -this.spin, $$1);
/*    */   }
/*    */   
/*    */   private static float wrap(float $$0, float $$1) {
/* 26 */     return ($$0 > $$1) ? ($$0 - $$1) : $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\PanoramaRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */