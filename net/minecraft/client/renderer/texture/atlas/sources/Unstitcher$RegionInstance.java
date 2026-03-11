/*     */ package net.minecraft.client.renderer.texture.atlas.sources;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.SpriteContents;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteSource;
/*     */ import net.minecraft.client.resources.metadata.animation.FrameSize;
/*     */ import net.minecraft.server.packs.resources.ResourceMetadata;
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
/*     */ class RegionInstance
/*     */   implements SpriteSource.SpriteSupplier
/*     */ {
/*     */   private final LazyLoadedImage image;
/*     */   private final Unstitcher.Region region;
/*     */   private final double xDivisor;
/*     */   private final double yDivisor;
/*     */   
/*     */   RegionInstance(LazyLoadedImage $$0, Unstitcher.Region $$1, double $$2, double $$3) {
/*  83 */     this.image = $$0;
/*  84 */     this.region = $$1;
/*  85 */     this.xDivisor = $$2;
/*  86 */     this.yDivisor = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteContents apply(SpriteResourceLoader $$0) {
/*     */     try {
/*  92 */       NativeImage $$1 = this.image.get();
/*     */       
/*  94 */       double $$2 = $$1.getWidth() / this.xDivisor;
/*  95 */       double $$3 = $$1.getHeight() / this.yDivisor;
/*     */       
/*  97 */       int $$4 = Mth.floor(this.region.x * $$2);
/*  98 */       int $$5 = Mth.floor(this.region.y * $$3);
/*     */       
/* 100 */       int $$6 = Mth.floor(this.region.width * $$2);
/* 101 */       int $$7 = Mth.floor(this.region.height * $$3);
/*     */       
/* 103 */       NativeImage $$8 = new NativeImage(NativeImage.Format.RGBA, $$6, $$7, false);
/* 104 */       $$1.copyRect($$8, $$4, $$5, 0, 0, $$6, $$7, false, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       return new SpriteContents(this.region.sprite, new FrameSize($$6, $$7), $$8, ResourceMetadata.EMPTY);
/* 111 */     } catch (Exception $$9) {
/* 112 */       Unstitcher.LOGGER.error("Failed to unstitch region {}", this.region.sprite, $$9);
/*     */     } finally {
/* 114 */       this.image.release();
/*     */     } 
/* 116 */     return MissingTextureAtlasSprite.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public void discard() {
/* 121 */     this.image.release();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\sources\Unstitcher$RegionInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */