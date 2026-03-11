/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.renderer.texture.SpriteLoader;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ 
/*    */ public abstract class TextureAtlasHolder
/*    */   implements PreparableReloadListener, AutoCloseable {
/*    */   private final TextureAtlas textureAtlas;
/*    */   
/*    */   public TextureAtlasHolder(TextureManager $$0, ResourceLocation $$1, ResourceLocation $$2) {
/* 23 */     this($$0, $$1, $$2, SpriteLoader.DEFAULT_METADATA_SECTIONS);
/*    */   }
/*    */   private final ResourceLocation atlasInfoLocation; private final Set<MetadataSectionSerializer<?>> metadataSections;
/*    */   public TextureAtlasHolder(TextureManager $$0, ResourceLocation $$1, ResourceLocation $$2, Set<MetadataSectionSerializer<?>> $$3) {
/* 27 */     this.atlasInfoLocation = $$2;
/* 28 */     this.textureAtlas = new TextureAtlas($$1);
/* 29 */     $$0.register(this.textureAtlas.location(), (AbstractTexture)this.textureAtlas);
/* 30 */     this.metadataSections = $$3;
/*    */   }
/*    */   
/*    */   protected TextureAtlasSprite getSprite(ResourceLocation $$0) {
/* 34 */     return this.textureAtlas.getSprite($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/* 41 */     Objects.requireNonNull($$0); return SpriteLoader.create(this.textureAtlas).loadAndStitch($$1, this.atlasInfoLocation, 0, $$4, this.metadataSections).thenCompose(SpriteLoader.Preparations::waitForUpload).thenCompose($$0::wait)
/* 42 */       .thenAcceptAsync($$1 -> apply($$1, $$0), $$5);
/*    */   }
/*    */   
/*    */   private void apply(SpriteLoader.Preparations $$0, ProfilerFiller $$1) {
/* 46 */     $$1.startTick();
/* 47 */     $$1.push("upload");
/* 48 */     this.textureAtlas.upload($$0);
/* 49 */     $$1.pop();
/* 50 */     $$1.endTick();
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 55 */     this.textureAtlas.clearTextureData();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\TextureAtlasHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */