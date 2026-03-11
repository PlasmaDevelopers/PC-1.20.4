/*     */ package net.minecraft.client.resources.metadata.gui;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.util.StringRepresentable;
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
/*     */ public enum Type
/*     */   implements StringRepresentable
/*     */ {
/*     */   public static final Codec<Type> CODEC;
/* 102 */   STRETCH("stretch", (Codec)GuiSpriteScaling.Stretch.CODEC),
/* 103 */   TILE("tile", (Codec)GuiSpriteScaling.Tile.CODEC),
/* 104 */   NINE_SLICE("nine_slice", (Codec)GuiSpriteScaling.NineSlice.CODEC);
/*     */   
/*     */   static {
/* 107 */     CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values);
/*     */   }
/*     */   
/*     */   private final String key;
/*     */   
/*     */   Type(String $$0, Codec<? extends GuiSpriteScaling> $$1) {
/* 113 */     this.key = $$0;
/* 114 */     this.codec = $$1;
/*     */   }
/*     */   private final Codec<? extends GuiSpriteScaling> codec;
/*     */   
/*     */   public String getSerializedName() {
/* 119 */     return this.key;
/*     */   }
/*     */   
/*     */   public Codec<? extends GuiSpriteScaling> codec() {
/* 123 */     return this.codec;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\gui\GuiSpriteScaling$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */