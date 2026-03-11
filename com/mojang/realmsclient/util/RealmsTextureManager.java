/*    */ package com.mojang.realmsclient.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Base64;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class RealmsTextureManager {
/* 20 */   private static final Map<String, RealmsTexture> TEXTURES = Maps.newHashMap();
/*    */   
/* 22 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 23 */   private static final ResourceLocation TEMPLATE_ICON_LOCATION = new ResourceLocation("textures/gui/presets/isles.png");
/*    */   
/*    */   public static ResourceLocation worldTemplate(String $$0, @Nullable String $$1) {
/* 26 */     if ($$1 == null) {
/* 27 */       return TEMPLATE_ICON_LOCATION;
/*    */     }
/* 29 */     return getTexture($$0, $$1);
/*    */   }
/*    */   
/*    */   private static ResourceLocation getTexture(String $$0, String $$1) {
/* 33 */     RealmsTexture $$2 = TEXTURES.get($$0);
/* 34 */     if ($$2 != null && $$2.image().equals($$1)) {
/* 35 */       return $$2.textureId;
/*    */     }
/*    */     
/* 38 */     NativeImage $$3 = loadImage($$1);
/* 39 */     if ($$3 == null) {
/* 40 */       ResourceLocation $$4 = MissingTextureAtlasSprite.getLocation();
/* 41 */       TEXTURES.put($$0, new RealmsTexture($$1, $$4));
/* 42 */       return $$4;
/*    */     } 
/*    */     
/* 45 */     ResourceLocation $$5 = new ResourceLocation("realms", "dynamic/" + $$0);
/* 46 */     Minecraft.getInstance().getTextureManager().register($$5, (AbstractTexture)new DynamicTexture($$3));
/* 47 */     TEXTURES.put($$0, new RealmsTexture($$1, $$5));
/*    */     
/* 49 */     return $$5;
/*    */   }
/*    */   public static final class RealmsTexture extends Record { private final String image; final ResourceLocation textureId;
/* 52 */     public RealmsTexture(String $$0, ResourceLocation $$1) { this.image = $$0; this.textureId = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/util/RealmsTextureManager$RealmsTexture;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #52	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 52 */       //   0	7	0	this	Lcom/mojang/realmsclient/util/RealmsTextureManager$RealmsTexture; } public String image() { return this.image; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/util/RealmsTextureManager$RealmsTexture;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #52	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lcom/mojang/realmsclient/util/RealmsTextureManager$RealmsTexture; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/util/RealmsTextureManager$RealmsTexture;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #52	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lcom/mojang/realmsclient/util/RealmsTextureManager$RealmsTexture;
/* 52 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation textureId() { return this.textureId; }
/*    */      }
/*    */   
/*    */   @Nullable
/*    */   private static NativeImage loadImage(String $$0) {
/* 57 */     byte[] $$1 = Base64.getDecoder().decode($$0);
/* 58 */     ByteBuffer $$2 = MemoryUtil.memAlloc($$1.length);
/*    */     try {
/* 60 */       return NativeImage.read($$2.put($$1).flip());
/* 61 */     } catch (IOException $$3) {
/* 62 */       LOGGER.warn("Failed to load world image: {}", $$0, $$3);
/*    */     } finally {
/* 64 */       MemoryUtil.memFree($$2);
/*    */     } 
/* 66 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\RealmsTextureManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */