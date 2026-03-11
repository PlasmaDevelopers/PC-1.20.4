/*     */ package net.minecraft.client.resources;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.SignatureState;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTextures;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.nio.file.Path;
/*     */ import java.time.Duration;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.HttpTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SkinManager {
/*  32 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final MinecraftSessionService sessionService;
/*     */   private final LoadingCache<CacheKey, CompletableFuture<PlayerSkin>> skinCache;
/*     */   private final TextureCache skinTextures;
/*     */   private final TextureCache capeTextures;
/*     */   private final TextureCache elytraTextures;
/*     */   
/*     */   public SkinManager(TextureManager $$0, Path $$1, final MinecraftSessionService sessionService, final Executor mainThreadExecutor) {
/*  41 */     this.sessionService = sessionService;
/*  42 */     this.skinTextures = new TextureCache($$0, $$1, MinecraftProfileTexture.Type.SKIN);
/*  43 */     this.capeTextures = new TextureCache($$0, $$1, MinecraftProfileTexture.Type.CAPE);
/*  44 */     this.elytraTextures = new TextureCache($$0, $$1, MinecraftProfileTexture.Type.ELYTRA);
/*  45 */     this
/*     */       
/*  47 */       .skinCache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofSeconds(15L)).build(new CacheLoader<CacheKey, CompletableFuture<PlayerSkin>>()
/*     */         {
/*     */           public CompletableFuture<PlayerSkin> load(SkinManager.CacheKey $$0) {
/*  50 */             return CompletableFuture.supplyAsync(() -> {
/*     */                   Property $$2 = $$0.packedTextures();
/*     */                   if ($$2 == null) {
/*     */                     return MinecraftProfileTextures.EMPTY;
/*     */                   }
/*     */                   MinecraftProfileTextures $$3 = $$1.unpackTextures($$2);
/*     */                   if ($$3.signatureState() == SignatureState.INVALID) {
/*     */                     SkinManager.LOGGER.warn("Profile contained invalid signature for textures property (profile id: {})", $$0.profileId());
/*     */                   }
/*     */                   return $$3;
/*  60 */                 }Util.backgroundExecutor())
/*  61 */               .thenComposeAsync($$1 -> SkinManager.this.registerTextures($$0.profileId(), $$1), mainThreadExecutor);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public Supplier<PlayerSkin> lookupInsecure(GameProfile $$0) {
/*  67 */     CompletableFuture<PlayerSkin> $$1 = getOrLoad($$0);
/*  68 */     PlayerSkin $$2 = DefaultPlayerSkin.get($$0);
/*  69 */     return () -> (PlayerSkin)$$0.getNow($$1);
/*     */   }
/*     */   
/*     */   public PlayerSkin getInsecureSkin(GameProfile $$0) {
/*  73 */     PlayerSkin $$1 = getOrLoad($$0).getNow(null);
/*  74 */     if ($$1 != null) {
/*  75 */       return $$1;
/*     */     }
/*  77 */     return DefaultPlayerSkin.get($$0);
/*     */   }
/*     */   
/*     */   public CompletableFuture<PlayerSkin> getOrLoad(GameProfile $$0) {
/*  81 */     Property $$1 = this.sessionService.getPackedTextures($$0);
/*  82 */     return (CompletableFuture<PlayerSkin>)this.skinCache.getUnchecked(new CacheKey($$0.getId(), $$1));
/*     */   } CompletableFuture<PlayerSkin> registerTextures(UUID $$0, MinecraftProfileTextures $$1) {
/*     */     CompletableFuture<ResourceLocation> $$6;
/*     */     PlayerSkin.Model $$7;
/*  86 */     MinecraftProfileTexture $$2 = $$1.skin();
/*     */ 
/*     */     
/*  89 */     if ($$2 != null) {
/*  90 */       CompletableFuture<ResourceLocation> $$3 = this.skinTextures.getOrLoad($$2);
/*  91 */       PlayerSkin.Model $$4 = PlayerSkin.Model.byName($$2.getMetadata("model"));
/*     */     } else {
/*  93 */       PlayerSkin $$5 = DefaultPlayerSkin.get($$0);
/*  94 */       $$6 = CompletableFuture.completedFuture($$5.texture());
/*  95 */       $$7 = $$5.model();
/*     */     } 
/*  97 */     String $$8 = (String)Optionull.map($$2, MinecraftProfileTexture::getUrl);
/*     */     
/*  99 */     MinecraftProfileTexture $$9 = $$1.cape();
/* 100 */     CompletableFuture<ResourceLocation> $$10 = ($$9 != null) ? this.capeTextures.getOrLoad($$9) : CompletableFuture.<ResourceLocation>completedFuture(null);
/*     */     
/* 102 */     MinecraftProfileTexture $$11 = $$1.elytra();
/* 103 */     CompletableFuture<ResourceLocation> $$12 = ($$11 != null) ? this.elytraTextures.getOrLoad($$11) : CompletableFuture.<ResourceLocation>completedFuture(null);
/*     */     
/* 105 */     return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { $$6, $$10, $$12
/* 106 */         }).thenApply($$6 -> new PlayerSkin($$0.join(), $$1, $$2.join(), $$3.join(), $$4, ($$5.signatureState() == SignatureState.SIGNED)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static class TextureCache
/*     */   {
/*     */     private final TextureManager textureManager;
/*     */     private final Path root;
/*     */     private final MinecraftProfileTexture.Type type;
/* 115 */     private final Map<String, CompletableFuture<ResourceLocation>> textures = (Map<String, CompletableFuture<ResourceLocation>>)new Object2ObjectOpenHashMap();
/*     */     
/*     */     TextureCache(TextureManager $$0, Path $$1, MinecraftProfileTexture.Type $$2) {
/* 118 */       this.textureManager = $$0;
/* 119 */       this.root = $$1;
/* 120 */       this.type = $$2;
/*     */     }
/*     */     
/*     */     public CompletableFuture<ResourceLocation> getOrLoad(MinecraftProfileTexture $$0) {
/* 124 */       String $$1 = $$0.getHash();
/* 125 */       CompletableFuture<ResourceLocation> $$2 = this.textures.get($$1);
/* 126 */       if ($$2 == null) {
/* 127 */         $$2 = registerTexture($$0);
/* 128 */         this.textures.put($$1, $$2);
/*     */       } 
/* 130 */       return $$2;
/*     */     } private ResourceLocation getTextureLocation(String $$0) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/resources/SkinManager$2.$SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type : [I
/*     */       //   3: aload_0
/*     */       //   4: getfield type : Lcom/mojang/authlib/minecraft/MinecraftProfileTexture$Type;
/*     */       //   7: invokevirtual ordinal : ()I
/*     */       //   10: iaload
/*     */       //   11: tableswitch default -> 36, 1 -> 44, 2 -> 49, 3 -> 54
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: ldc 'skins'
/*     */       //   46: goto -> 56
/*     */       //   49: ldc 'capes'
/*     */       //   51: goto -> 56
/*     */       //   54: ldc 'elytra'
/*     */       //   56: astore_2
/*     */       //   57: new net/minecraft/resources/ResourceLocation
/*     */       //   60: dup
/*     */       //   61: aload_2
/*     */       //   62: aload_1
/*     */       //   63: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */       //   68: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   71: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #144	-> 0
/*     */       //   #145	-> 44
/*     */       //   #146	-> 49
/*     */       //   #147	-> 54
/*     */       //   #149	-> 57
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	72	0	this	Lnet/minecraft/client/resources/SkinManager$TextureCache;
/*     */       //   0	72	1	$$0	Ljava/lang/String;
/*     */       //   57	15	2	$$1	Ljava/lang/String;
/*     */     } private CompletableFuture<ResourceLocation> registerTexture(MinecraftProfileTexture $$0) {
/* 134 */       String $$1 = Hashing.sha1().hashUnencodedChars($$0.getHash()).toString();
/* 135 */       ResourceLocation $$2 = getTextureLocation($$1);
/* 136 */       Path $$3 = this.root.resolve(($$1.length() > 2) ? $$1.substring(0, 2) : "xx").resolve($$1);
/* 137 */       CompletableFuture<ResourceLocation> $$4 = new CompletableFuture<>();
/* 138 */       HttpTexture $$5 = new HttpTexture($$3.toFile(), $$0.getUrl(), DefaultPlayerSkin.getDefaultTexture(), (this.type == MinecraftProfileTexture.Type.SKIN), () -> $$0.complete($$1));
/* 139 */       this.textureManager.register($$2, (AbstractTexture)$$5);
/* 140 */       return $$4;
/*     */     } }
/*     */   private static final class CacheKey extends Record { private final UUID profileId; @Nullable
/*     */     private final Property packedTextures;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/SkinManager$CacheKey;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #154	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/SkinManager$CacheKey;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/SkinManager$CacheKey;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #154	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/SkinManager$CacheKey;
/*     */     }
/*     */     
/*     */     CacheKey(UUID $$0, @Nullable Property $$1) {
/* 154 */       this.profileId = $$0; this.packedTextures = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/SkinManager$CacheKey;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #154	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/resources/SkinManager$CacheKey;
/* 154 */       //   0	8	1	$$0	Ljava/lang/Object; } public UUID profileId() { return this.profileId; } @Nullable public Property packedTextures() { return this.packedTextures; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\SkinManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */