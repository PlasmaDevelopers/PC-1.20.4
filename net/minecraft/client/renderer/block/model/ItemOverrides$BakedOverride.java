/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.model.BakedModel;
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
/*     */ class BakedOverride
/*     */ {
/*     */   private final ItemOverrides.PropertyMatcher[] matchers;
/*     */   @Nullable
/*     */   final BakedModel model;
/*     */   
/*     */   BakedOverride(ItemOverrides.PropertyMatcher[] $$0, @Nullable BakedModel $$1) {
/* 116 */     this.matchers = $$0;
/* 117 */     this.model = $$1;
/*     */   }
/*     */   
/*     */   boolean test(float[] $$0) {
/* 121 */     for (ItemOverrides.PropertyMatcher $$1 : this.matchers) {
/* 122 */       float $$2 = $$0[$$1.index];
/* 123 */       if ($$2 < $$1.value) {
/* 124 */         return false;
/*     */       }
/*     */     } 
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\ItemOverrides$BakedOverride.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */