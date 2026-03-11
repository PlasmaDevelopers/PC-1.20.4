/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.HttpTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.resources.ResourceLocation;
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
/*     */ class TextureCache
/*     */ {
/*     */   private final TextureManager textureManager;
/*     */   private final Path root;
/*     */   private final MinecraftProfileTexture.Type type;
/* 115 */   private final Map<String, CompletableFuture<ResourceLocation>> textures = (Map<String, CompletableFuture<ResourceLocation>>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   TextureCache(TextureManager $$0, Path $$1, MinecraftProfileTexture.Type $$2) {
/* 118 */     this.textureManager = $$0;
/* 119 */     this.root = $$1;
/* 120 */     this.type = $$2;
/*     */   }
/*     */   
/*     */   public CompletableFuture<ResourceLocation> getOrLoad(MinecraftProfileTexture $$0) {
/* 124 */     String $$1 = $$0.getHash();
/* 125 */     CompletableFuture<ResourceLocation> $$2 = this.textures.get($$1);
/* 126 */     if ($$2 == null) {
/* 127 */       $$2 = registerTexture($$0);
/* 128 */       this.textures.put($$1, $$2);
/*     */     } 
/* 130 */     return $$2;
/*     */   }
/*     */   
/*     */   private CompletableFuture<ResourceLocation> registerTexture(MinecraftProfileTexture $$0) {
/* 134 */     String $$1 = Hashing.sha1().hashUnencodedChars($$0.getHash()).toString();
/* 135 */     ResourceLocation $$2 = getTextureLocation($$1);
/* 136 */     Path $$3 = this.root.resolve(($$1.length() > 2) ? $$1.substring(0, 2) : "xx").resolve($$1);
/* 137 */     CompletableFuture<ResourceLocation> $$4 = new CompletableFuture<>();
/* 138 */     HttpTexture $$5 = new HttpTexture($$3.toFile(), $$0.getUrl(), DefaultPlayerSkin.getDefaultTexture(), (this.type == MinecraftProfileTexture.Type.SKIN), () -> $$0.complete($$1));
/* 139 */     this.textureManager.register($$2, (AbstractTexture)$$5);
/* 140 */     return $$4;
/*     */   }
/*     */   
/*     */   private ResourceLocation getTextureLocation(String $$0) {
/* 144 */     switch (SkinManager.null.$SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[this.type.ordinal()]) { default: throw new IncompatibleClassChangeError();
/*     */       case 1: 
/*     */       case 2: 
/* 147 */       case 3: break; }  String $$1 = "elytra";
/*     */     
/* 149 */     return new ResourceLocation($$1 + "/" + $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\SkinManager$TextureCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */