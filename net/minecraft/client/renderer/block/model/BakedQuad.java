/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ public class BakedQuad {
/*    */   protected final int[] vertices;
/*    */   protected final int tintIndex;
/*    */   protected final Direction direction;
/*    */   protected final TextureAtlasSprite sprite;
/*    */   private final boolean shade;
/*    */   
/*    */   public BakedQuad(int[] $$0, int $$1, Direction $$2, TextureAtlasSprite $$3, boolean $$4) {
/* 14 */     this.vertices = $$0;
/* 15 */     this.tintIndex = $$1;
/* 16 */     this.direction = $$2;
/* 17 */     this.sprite = $$3;
/* 18 */     this.shade = $$4;
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite getSprite() {
/* 22 */     return this.sprite;
/*    */   }
/*    */   
/*    */   public int[] getVertices() {
/* 26 */     return this.vertices;
/*    */   }
/*    */   
/*    */   public boolean isTinted() {
/* 30 */     return (this.tintIndex != -1);
/*    */   }
/*    */   
/*    */   public int getTintIndex() {
/* 34 */     return this.tintIndex;
/*    */   }
/*    */   
/*    */   public Direction getDirection() {
/* 38 */     return this.direction;
/*    */   }
/*    */   
/*    */   public boolean isShade() {
/* 42 */     return this.shade;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BakedQuad.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */