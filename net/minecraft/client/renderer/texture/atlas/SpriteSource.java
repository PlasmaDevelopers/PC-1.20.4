/*    */ package net.minecraft.client.renderer.texture.atlas;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.client.renderer.texture.SpriteContents;
/*    */ import net.minecraft.resources.FileToIdConverter;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ 
/*    */ public interface SpriteSource
/*    */ {
/* 13 */   public static final FileToIdConverter TEXTURE_ID_CONVERTER = new FileToIdConverter("textures", ".png");
/*    */   
/*    */   void run(ResourceManager paramResourceManager, Output paramOutput);
/*    */   
/*    */   SpriteSourceType type();
/*    */   
/*    */   public static interface Output {
/*    */     default void add(ResourceLocation $$0, Resource $$1) {
/* 21 */       add($$0, $$2 -> $$2.loadSprite($$0, $$1));
/*    */     }
/*    */     
/*    */     void add(ResourceLocation param1ResourceLocation, SpriteSource.SpriteSupplier param1SpriteSupplier);
/*    */     
/*    */     void removeAll(Predicate<ResourceLocation> param1Predicate);
/*    */   }
/*    */   
/*    */   public static interface SpriteSupplier extends Function<SpriteResourceLoader, SpriteContents> {
/*    */     default void discard() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\SpriteSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */