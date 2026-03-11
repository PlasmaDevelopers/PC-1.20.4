/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.SpriteCoordinateExpander;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class TextureAtlasSprite
/*     */ {
/*     */   private final ResourceLocation atlasLocation;
/*     */   private final SpriteContents contents;
/*     */   final int x;
/*     */   final int y;
/*     */   private final float u0;
/*     */   private final float u1;
/*     */   private final float v0;
/*     */   private final float v1;
/*     */   
/*     */   protected TextureAtlasSprite(ResourceLocation $$0, SpriteContents $$1, int $$2, int $$3, int $$4, int $$5) {
/*  21 */     this.atlasLocation = $$0;
/*  22 */     this.contents = $$1;
/*     */     
/*  24 */     this.x = $$4;
/*  25 */     this.y = $$5;
/*     */     
/*  27 */     this.u0 = $$4 / $$2;
/*  28 */     this.u1 = ($$4 + $$1.width()) / $$2;
/*  29 */     this.v0 = $$5 / $$3;
/*  30 */     this.v1 = ($$5 + $$1.height()) / $$3;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  34 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  38 */     return this.y;
/*     */   }
/*     */   
/*     */   public float getU0() {
/*  42 */     return this.u0;
/*     */   }
/*     */   
/*     */   public float getU1() {
/*  46 */     return this.u1;
/*     */   }
/*     */   
/*     */   public SpriteContents contents() {
/*  50 */     return this.contents;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ticker createTicker() {
/*  55 */     final SpriteTicker ticker = this.contents.createTicker();
/*  56 */     if ($$0 != null) {
/*  57 */       return new Ticker()
/*     */         {
/*     */           public void tickAndUpload() {
/*  60 */             ticker.tickAndUpload(TextureAtlasSprite.this.x, TextureAtlasSprite.this.y);
/*     */           }
/*     */ 
/*     */           
/*     */           public void close() {
/*  65 */             ticker.close();
/*     */           }
/*     */         };
/*     */     }
/*  69 */     return null;
/*     */   }
/*     */   
/*     */   public float getU(float $$0) {
/*  73 */     float $$1 = this.u1 - this.u0;
/*  74 */     return this.u0 + $$1 * $$0;
/*     */   }
/*     */   
/*     */   public float getUOffset(float $$0) {
/*  78 */     float $$1 = this.u1 - this.u0;
/*  79 */     return ($$0 - this.u0) / $$1;
/*     */   }
/*     */   
/*     */   public float getV0() {
/*  83 */     return this.v0;
/*     */   }
/*     */   
/*     */   public float getV1() {
/*  87 */     return this.v1;
/*     */   }
/*     */   
/*     */   public float getV(float $$0) {
/*  91 */     float $$1 = this.v1 - this.v0;
/*  92 */     return this.v0 + $$1 * $$0;
/*     */   }
/*     */   
/*     */   public float getVOffset(float $$0) {
/*  96 */     float $$1 = this.v1 - this.v0;
/*  97 */     return ($$0 - this.v0) / $$1;
/*     */   }
/*     */   
/*     */   public ResourceLocation atlasLocation() {
/* 101 */     return this.atlasLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 106 */     return "TextureAtlasSprite{contents='" + this.contents + "', u0=" + this.u0 + ", u1=" + this.u1 + ", v0=" + this.v0 + ", v1=" + this.v1 + "}";
/*     */   }
/*     */   
/*     */   public void uploadFirstFrame() {
/* 110 */     this.contents.uploadFirstFrame(this.x, this.y);
/*     */   }
/*     */   
/*     */   private float atlasSize() {
/* 114 */     float $$0 = this.contents.width() / (this.u1 - this.u0);
/* 115 */     float $$1 = this.contents.height() / (this.v1 - this.v0);
/* 116 */     return Math.max($$1, $$0);
/*     */   }
/*     */   
/*     */   public float uvShrinkRatio() {
/* 120 */     return 4.0F / atlasSize();
/*     */   }
/*     */   
/*     */   public VertexConsumer wrap(VertexConsumer $$0) {
/* 124 */     return (VertexConsumer)new SpriteCoordinateExpander($$0, this);
/*     */   }
/*     */   
/*     */   public static interface Ticker extends AutoCloseable {
/*     */     void tickAndUpload();
/*     */     
/*     */     void close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\TextureAtlasSprite.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */