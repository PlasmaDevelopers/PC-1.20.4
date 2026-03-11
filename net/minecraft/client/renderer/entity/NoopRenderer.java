/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class NoopRenderer<T extends Entity> extends EntityRenderer<T> {
/*    */   public NoopRenderer(EntityRendererProvider.Context $$0) {
/*  9 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(T $$0) {
/* 14 */     return TextureAtlas.LOCATION_BLOCKS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\NoopRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */