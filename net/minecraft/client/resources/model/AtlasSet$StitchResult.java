/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.texture.SpriteLoader;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
/*    */ public class StitchResult
/*    */ {
/*    */   private final TextureAtlas atlas;
/*    */   private final SpriteLoader.Preparations preparations;
/*    */   
/*    */   public StitchResult(TextureAtlas $$0, SpriteLoader.Preparations $$1) {
/* 50 */     this.atlas = $$0;
/* 51 */     this.preparations = $$1;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public TextureAtlasSprite getSprite(ResourceLocation $$0) {
/* 56 */     return (TextureAtlasSprite)this.preparations.regions().get($$0);
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite missing() {
/* 60 */     return this.preparations.missing();
/*    */   }
/*    */   
/*    */   public CompletableFuture<Void> readyForUpload() {
/* 64 */     return this.preparations.readyForUpload();
/*    */   }
/*    */   
/*    */   public void upload() {
/* 68 */     this.atlas.upload(this.preparations);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\AtlasSet$StitchResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */