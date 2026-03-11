/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.cache.CacheLoader;
/*    */ import com.mojang.authlib.SignatureState;
/*    */ import com.mojang.authlib.minecraft.MinecraftProfileTextures;
/*    */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*    */ import com.mojang.authlib.properties.Property;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionStage;
/*    */ import java.util.concurrent.Executor;
/*    */ import net.minecraft.Util;
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
/*    */ class null
/*    */   extends CacheLoader<SkinManager.CacheKey, CompletableFuture<PlayerSkin>>
/*    */ {
/*    */   public CompletableFuture<PlayerSkin> load(SkinManager.CacheKey $$0) {
/* 50 */     return CompletableFuture.supplyAsync(() -> {
/*    */           Property $$2 = $$0.packedTextures();
/*    */           if ($$2 == null) {
/*    */             return MinecraftProfileTextures.EMPTY;
/*    */           }
/*    */           MinecraftProfileTextures $$3 = $$1.unpackTextures($$2);
/*    */           if ($$3.signatureState() == SignatureState.INVALID) {
/*    */             SkinManager.LOGGER.warn("Profile contained invalid signature for textures property (profile id: {})", $$0.profileId());
/*    */           }
/*    */           return $$3;
/* 60 */         }Util.backgroundExecutor())
/* 61 */       .thenComposeAsync($$1 -> SkinManager.this.registerTextures($$0.profileId(), $$1), mainThreadExecutor);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\SkinManager$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */