/*    */ package net.minecraft.client.gui.font.glyphs;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.gui.font.GlyphRenderTypes;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class EmptyGlyph extends BakedGlyph {
/*  9 */   public static final EmptyGlyph INSTANCE = new EmptyGlyph();
/*    */   
/*    */   public EmptyGlyph() {
/* 12 */     super(GlyphRenderTypes.createForColorTexture(new ResourceLocation("")), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void render(boolean $$0, float $$1, float $$2, Matrix4f $$3, VertexConsumer $$4, float $$5, float $$6, float $$7, float $$8, int $$9) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\glyphs\EmptyGlyph.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */