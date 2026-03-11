/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ 
/*    */ public abstract class TextureSheetParticle extends SingleQuadParticle {
/*    */   protected TextureSheetParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/*  8 */     super($$0, $$1, $$2, $$3);
/*    */   }
/*    */   protected TextureAtlasSprite sprite;
/*    */   protected TextureSheetParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 12 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setSprite(TextureAtlasSprite $$0) {
/* 18 */     this.sprite = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getU0() {
/* 23 */     return this.sprite.getU0();
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getU1() {
/* 28 */     return this.sprite.getU1();
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getV0() {
/* 33 */     return this.sprite.getV0();
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getV1() {
/* 38 */     return this.sprite.getV1();
/*    */   }
/*    */   
/*    */   public void pickSprite(SpriteSet $$0) {
/* 42 */     setSprite($$0.get(this.random));
/*    */   }
/*    */   
/*    */   public void setSpriteFromAge(SpriteSet $$0) {
/* 46 */     if (!this.removed)
/* 47 */       setSprite($$0.get(this.age, this.lifetime)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\TextureSheetParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */