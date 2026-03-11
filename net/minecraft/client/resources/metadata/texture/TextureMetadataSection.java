/*    */ package net.minecraft.client.resources.metadata.texture;
/*    */ 
/*    */ public class TextureMetadataSection {
/*  4 */   public static final TextureMetadataSectionSerializer SERIALIZER = new TextureMetadataSectionSerializer();
/*    */   
/*    */   public static final boolean DEFAULT_BLUR = false;
/*    */   
/*    */   public static final boolean DEFAULT_CLAMP = false;
/*    */   private final boolean blur;
/*    */   private final boolean clamp;
/*    */   
/*    */   public TextureMetadataSection(boolean $$0, boolean $$1) {
/* 13 */     this.blur = $$0;
/* 14 */     this.clamp = $$1;
/*    */   }
/*    */   
/*    */   public boolean isBlur() {
/* 18 */     return this.blur;
/*    */   }
/*    */   
/*    */   public boolean isClamp() {
/* 22 */     return this.clamp;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\texture\TextureMetadataSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */