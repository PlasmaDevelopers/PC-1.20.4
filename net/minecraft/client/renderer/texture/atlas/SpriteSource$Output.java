/*    */ package net.minecraft.client.renderer.texture.atlas;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.client.renderer.texture.SpriteContents;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.Resource;
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
/*    */ public interface Output
/*    */ {
/*    */   default void add(ResourceLocation $$0, Resource $$1) {
/* 21 */     add($$0, $$2 -> $$2.loadSprite($$0, $$1));
/*    */   }
/*    */   
/*    */   void add(ResourceLocation paramResourceLocation, SpriteSource.SpriteSupplier paramSpriteSupplier);
/*    */   
/*    */   void removeAll(Predicate<ResourceLocation> paramPredicate);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\SpriteSource$Output.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */