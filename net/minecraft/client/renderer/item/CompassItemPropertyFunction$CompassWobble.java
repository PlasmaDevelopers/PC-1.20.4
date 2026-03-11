/*     */ package net.minecraft.client.renderer.item;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CompassWobble
/*     */ {
/*     */   double rotation;
/*     */   private double deltaRotation;
/*     */   private long lastUpdateTick;
/*     */   
/*     */   boolean shouldUpdate(long $$0) {
/* 118 */     return (this.lastUpdateTick != $$0);
/*     */   }
/*     */   
/*     */   void update(long $$0, double $$1) {
/* 122 */     this.lastUpdateTick = $$0;
/* 123 */     double $$2 = $$1 - this.rotation;
/* 124 */     $$2 = Mth.positiveModulo($$2 + 0.5D, 1.0D) - 0.5D;
/*     */     
/* 126 */     this.deltaRotation += $$2 * 0.1D;
/* 127 */     this.deltaRotation *= 0.8D;
/* 128 */     this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\item\CompassItemPropertyFunction$CompassWobble.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */