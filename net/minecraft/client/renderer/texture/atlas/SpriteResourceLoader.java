/*    */ package net.minecraft.client.renderer.texture.atlas;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.texture.SpriteContents;
/*    */ import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
/*    */ import net.minecraft.client.resources.metadata.animation.FrameSize;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ import net.minecraft.server.packs.resources.ResourceMetadata;
/*    */ import net.minecraft.util.Mth;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface SpriteResourceLoader
/*    */ {
/* 22 */   public static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   static SpriteResourceLoader create(Collection<MetadataSectionSerializer<?>> $$0) {
/* 25 */     return ($$1, $$2) -> {
/*    */         ResourceMetadata $$3; NativeImage $$7;
/*    */         try {
/*    */           $$3 = $$2.metadata().copySections($$0);
/* 29 */         } catch (Exception $$4) {
/*    */           LOGGER.error("Unable to parse metadata from {}", $$1, $$4); return null;
/*    */         }  try {
/*    */           InputStream $$6 = $$2.open(); 
/*    */           try { $$7 = NativeImage.read($$6); if ($$6 != null)
/*    */               $$6.close();  }
/* 35 */           catch (Throwable throwable) { if ($$6 != null) try { $$6.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*    */         
/* 37 */         } catch (IOException $$9) {
/*    */           LOGGER.error("Using missing texture, unable to load {}", $$1, $$9);
/*    */           return null;
/*    */         } 
/*    */         AnimationMetadataSection $$11 = $$3.getSection((MetadataSectionSerializer)AnimationMetadataSection.SERIALIZER).orElse(AnimationMetadataSection.EMPTY);
/*    */         FrameSize $$12 = $$11.calculateFrameSize($$7.getWidth(), $$7.getHeight());
/*    */         if (Mth.isMultipleOf($$7.getWidth(), $$12.width()) && Mth.isMultipleOf($$7.getHeight(), $$12.height()))
/*    */           return new SpriteContents($$1, $$12, $$7, $$3); 
/*    */         LOGGER.error("Image {} size {},{} is not multiple of frame size {},{}", new Object[] { $$1, Integer.valueOf($$7.getWidth()), Integer.valueOf($$7.getHeight()), Integer.valueOf($$12.width()), Integer.valueOf($$12.height()) });
/*    */         $$7.close();
/*    */         return null;
/*    */       };
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   SpriteContents loadSprite(ResourceLocation paramResourceLocation, Resource paramResource);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\SpriteResourceLoader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */