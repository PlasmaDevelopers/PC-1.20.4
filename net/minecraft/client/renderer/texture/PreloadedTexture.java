/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ 
/*    */ public class PreloadedTexture extends SimpleTexture {
/*    */   @Nullable
/*    */   private CompletableFuture<SimpleTexture.TextureImage> future;
/*    */   
/*    */   public PreloadedTexture(ResourceManager $$0, ResourceLocation $$1, Executor $$2) {
/* 17 */     super($$1);
/* 18 */     this.future = CompletableFuture.supplyAsync(() -> SimpleTexture.TextureImage.load($$0, $$1), $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected SimpleTexture.TextureImage getTextureImage(ResourceManager $$0) {
/* 23 */     if (this.future != null) {
/* 24 */       SimpleTexture.TextureImage $$1 = this.future.join();
/* 25 */       this.future = null;
/* 26 */       return $$1;
/*    */     } 
/* 28 */     return SimpleTexture.TextureImage.load($$0, this.location);
/*    */   }
/*    */   
/*    */   public CompletableFuture<Void> getFuture() {
/* 32 */     return (this.future == null) ? CompletableFuture.<Void>completedFuture(null) : this.future.<Void>thenApply($$0 -> null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset(TextureManager $$0, ResourceManager $$1, ResourceLocation $$2, Executor $$3) {
/* 37 */     this.future = CompletableFuture.supplyAsync(() -> SimpleTexture.TextureImage.load($$0, this.location), Util.backgroundExecutor());
/* 38 */     this.future.thenRunAsync(() -> $$0.register(this.location, this), executor($$3));
/*    */   }
/*    */   
/*    */   private static Executor executor(Executor $$0) {
/* 42 */     return $$1 -> $$0.execute(());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\PreloadedTexture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */