/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.metadata.animation.AnimationFrame;
/*    */ import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
/*    */ import net.minecraft.client.resources.metadata.animation.FrameSize;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.resources.ResourceMetadata;
/*    */ 
/*    */ public final class MissingTextureAtlasSprite
/*    */ {
/*    */   private static final int MISSING_IMAGE_WIDTH = 16;
/* 18 */   private static final ResourceLocation MISSING_TEXTURE_LOCATION = new ResourceLocation("missingno"); private static final int MISSING_IMAGE_HEIGHT = 16; private static final String MISSING_TEXTURE_NAME = "missingno";
/* 19 */   private static final ResourceMetadata SPRITE_METADATA = (new ResourceMetadata.Builder())
/* 20 */     .put((MetadataSectionSerializer)AnimationMetadataSection.SERIALIZER, new AnimationMetadataSection((List)ImmutableList.of(new AnimationFrame(0, -1)), 16, 16, 1, false))
/* 21 */     .build();
/*    */   
/*    */   @Nullable
/*    */   private static DynamicTexture missingTexture;
/*    */   
/*    */   private static NativeImage generateMissingImage(int $$0, int $$1) {
/* 27 */     NativeImage $$2 = new NativeImage($$0, $$1, false);
/* 28 */     int $$3 = -16777216;
/* 29 */     int $$4 = -524040;
/* 30 */     for (int $$5 = 0; $$5 < $$1; $$5++) {
/* 31 */       for (int $$6 = 0; $$6 < $$0; $$6++) {
/* 32 */         if (((($$5 < $$1 / 2) ? 1 : 0) ^ (($$6 < $$0 / 2) ? 1 : 0)) != 0) {
/* 33 */           $$2.setPixelRGBA($$6, $$5, -524040);
/*    */         } else {
/* 35 */           $$2.setPixelRGBA($$6, $$5, -16777216);
/*    */         } 
/*    */       } 
/*    */     } 
/* 39 */     return $$2;
/*    */   }
/*    */   
/*    */   public static SpriteContents create() {
/* 43 */     NativeImage $$0 = generateMissingImage(16, 16);
/* 44 */     return new SpriteContents(MISSING_TEXTURE_LOCATION, new FrameSize(16, 16), $$0, SPRITE_METADATA);
/*    */   }
/*    */   
/*    */   public static ResourceLocation getLocation() {
/* 48 */     return MISSING_TEXTURE_LOCATION;
/*    */   }
/*    */   
/*    */   public static DynamicTexture getTexture() {
/* 52 */     if (missingTexture == null) {
/* 53 */       NativeImage $$0 = generateMissingImage(16, 16);
/* 54 */       $$0.untrack();
/* 55 */       missingTexture = new DynamicTexture($$0);
/* 56 */       Minecraft.getInstance().getTextureManager().register(MISSING_TEXTURE_LOCATION, missingTexture);
/*    */     } 
/* 58 */     return missingTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\MissingTextureAtlasSprite.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */