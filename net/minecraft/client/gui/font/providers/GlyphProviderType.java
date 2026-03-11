/*    */ package net.minecraft.client.gui.font.providers;
/*    */ import com.mojang.blaze3d.font.SpaceProvider;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum GlyphProviderType implements StringRepresentable {
/*    */   public static final Codec<GlyphProviderType> CODEC;
/*  9 */   BITMAP("bitmap", (MapCodec)BitmapProvider.Definition.CODEC),
/* 10 */   TTF("ttf", (MapCodec)TrueTypeGlyphProviderDefinition.CODEC),
/* 11 */   SPACE("space", SpaceProvider.Definition.CODEC),
/* 12 */   UNIHEX("unihex", (MapCodec)UnihexProvider.Definition.CODEC),
/* 13 */   REFERENCE("reference", (MapCodec)ProviderReferenceDefinition.CODEC);
/*    */   
/*    */   static {
/* 16 */     CODEC = (Codec<GlyphProviderType>)StringRepresentable.fromEnum(GlyphProviderType::values);
/*    */   }
/*    */   private final String name;
/*    */   private final MapCodec<? extends GlyphProviderDefinition> codec;
/*    */   
/*    */   GlyphProviderType(String $$0, MapCodec<? extends GlyphProviderDefinition> $$1) {
/* 22 */     this.name = $$0;
/* 23 */     this.codec = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 28 */     return this.name;
/*    */   }
/*    */   
/*    */   public MapCodec<? extends GlyphProviderDefinition> mapCodec() {
/* 32 */     return this.codec;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\GlyphProviderType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */