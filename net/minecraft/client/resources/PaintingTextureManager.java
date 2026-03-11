/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.decoration.PaintingVariant;
/*    */ 
/*    */ public class PaintingTextureManager extends TextureAtlasHolder {
/* 10 */   private static final ResourceLocation BACK_SPRITE_LOCATION = new ResourceLocation("back");
/*    */   
/*    */   public PaintingTextureManager(TextureManager $$0) {
/* 13 */     super($$0, new ResourceLocation("textures/atlas/paintings.png"), new ResourceLocation("paintings"));
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite get(PaintingVariant $$0) {
/* 17 */     return getSprite(BuiltInRegistries.PAINTING_VARIANT.getKey($$0));
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite getBackSprite() {
/* 21 */     return getSprite(BACK_SPRITE_LOCATION);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\PaintingTextureManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */