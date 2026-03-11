/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
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
/*     */ public class TextureImage
/*     */   implements Closeable
/*     */ {
/*     */   @Nullable
/*     */   private final TextureMetadataSection metadata;
/*     */   @Nullable
/*     */   private final NativeImage image;
/*     */   @Nullable
/*     */   private final IOException exception;
/*     */   
/*     */   public TextureImage(IOException $$0) {
/*  70 */     this.exception = $$0;
/*  71 */     this.metadata = null;
/*  72 */     this.image = null;
/*     */   }
/*     */   
/*     */   public TextureImage(@Nullable TextureMetadataSection $$0, NativeImage $$1) {
/*  76 */     this.exception = null;
/*  77 */     this.metadata = $$0;
/*  78 */     this.image = $$1;
/*     */   }
/*     */   public static TextureImage load(ResourceManager $$0, ResourceLocation $$1) {
/*     */     try {
/*     */       NativeImage $$4;
/*  83 */       Resource $$2 = $$0.getResourceOrThrow($$1);
/*     */ 
/*     */       
/*  86 */       InputStream $$3 = $$2.open(); 
/*  87 */       try { $$4 = NativeImage.read($$3);
/*  88 */         if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/*  89 */           try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  TextureMetadataSection $$6 = null;
/*     */       try {
/*  91 */         $$6 = $$2.metadata().getSection((MetadataSectionSerializer)TextureMetadataSection.SERIALIZER).orElse(null);
/*  92 */       } catch (RuntimeException $$7) {
/*  93 */         SimpleTexture.LOGGER.warn("Failed reading metadata of: {}", $$1, $$7);
/*     */       } 
/*  95 */       return new TextureImage($$6, $$4);
/*  96 */     } catch (IOException $$8) {
/*  97 */       return new TextureImage($$8);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TextureMetadataSection getTextureMetadata() {
/* 103 */     return this.metadata;
/*     */   }
/*     */   
/*     */   public NativeImage getImage() throws IOException {
/* 107 */     if (this.exception != null) {
/* 108 */       throw this.exception;
/*     */     }
/* 110 */     return this.image;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 115 */     if (this.image != null) {
/* 116 */       this.image.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void throwIfError() throws IOException {
/* 121 */     if (this.exception != null)
/* 122 */       throw this.exception; 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\SimpleTexture$TextureImage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */