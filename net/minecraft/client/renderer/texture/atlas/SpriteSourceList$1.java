/*    */ package net.minecraft.client.renderer.texture.atlas;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.function.Predicate;
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
/*    */ class null
/*    */   implements SpriteSource.Output
/*    */ {
/*    */   public void add(ResourceLocation $$0, SpriteSource.SpriteSupplier $$1) {
/* 42 */     SpriteSource.SpriteSupplier $$2 = sprites.put($$0, $$1);
/* 43 */     if ($$2 != null) {
/* 44 */       $$2.discard();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeAll(Predicate<ResourceLocation> $$0) {
/* 50 */     Iterator<Map.Entry<ResourceLocation, SpriteSource.SpriteSupplier>> $$1 = sprites.entrySet().iterator();
/* 51 */     while ($$1.hasNext()) {
/* 52 */       Map.Entry<ResourceLocation, SpriteSource.SpriteSupplier> $$2 = $$1.next();
/* 53 */       if ($$0.test($$2.getKey())) {
/* 54 */         ((SpriteSource.SpriteSupplier)$$2.getValue()).discard();
/* 55 */         $$1.remove();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\SpriteSourceList$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */