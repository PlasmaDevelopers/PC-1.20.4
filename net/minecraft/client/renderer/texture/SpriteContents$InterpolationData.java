/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import java.util.List;
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
/*     */ final class InterpolationData
/*     */   implements AutoCloseable
/*     */ {
/*     */   private final NativeImage[] activeFrame;
/*     */   
/*     */   InterpolationData() {
/* 207 */     this.activeFrame = new NativeImage[paramSpriteContents.byMipLevel.length];
/*     */     
/* 209 */     for (int $$0 = 0; $$0 < this.activeFrame.length; $$0++) {
/* 210 */       int $$1 = paramSpriteContents.width >> $$0;
/* 211 */       int $$2 = paramSpriteContents.height >> $$0;
/* 212 */       this.activeFrame[$$0] = new NativeImage($$1, $$2, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void uploadInterpolatedFrame(int $$0, int $$1, SpriteContents.Ticker $$2) {
/* 218 */     SpriteContents.AnimatedTexture $$3 = $$2.animationInfo;
/* 219 */     List<SpriteContents.FrameInfo> $$4 = $$3.frames;
/* 220 */     SpriteContents.FrameInfo $$5 = $$4.get($$2.frame);
/* 221 */     double $$6 = 1.0D - $$2.subFrame / $$5.time;
/*     */     
/* 223 */     int $$7 = $$5.index;
/* 224 */     int $$8 = ((SpriteContents.FrameInfo)$$4.get(($$2.frame + 1) % $$4.size())).index;
/* 225 */     if ($$7 != $$8) {
/* 226 */       for (int $$9 = 0; $$9 < this.activeFrame.length; $$9++) {
/* 227 */         int $$10 = SpriteContents.this.width >> $$9;
/* 228 */         int $$11 = SpriteContents.this.height >> $$9;
/* 229 */         for (int $$12 = 0; $$12 < $$11; $$12++) {
/* 230 */           for (int $$13 = 0; $$13 < $$10; $$13++) {
/* 231 */             int $$14 = getPixel($$3, $$7, $$9, $$13, $$12);
/* 232 */             int $$15 = getPixel($$3, $$8, $$9, $$13, $$12);
/* 233 */             int $$16 = mix($$6, $$14 >> 16 & 0xFF, $$15 >> 16 & 0xFF);
/* 234 */             int $$17 = mix($$6, $$14 >> 8 & 0xFF, $$15 >> 8 & 0xFF);
/* 235 */             int $$18 = mix($$6, $$14 & 0xFF, $$15 & 0xFF);
/*     */             
/* 237 */             this.activeFrame[$$9].setPixelRGBA($$13, $$12, $$14 & 0xFF000000 | $$16 << 16 | $$17 << 8 | $$18);
/*     */           } 
/*     */         } 
/*     */       } 
/* 241 */       SpriteContents.this.upload($$0, $$1, 0, 0, this.activeFrame);
/*     */     } 
/*     */   }
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
/*     */   private int getPixel(SpriteContents.AnimatedTexture $$0, int $$1, int $$2, int $$3, int $$4) {
/* 257 */     return SpriteContents.this.byMipLevel[$$2].getPixelRGBA($$3 + ($$0.getFrameX($$1) * SpriteContents.this.width >> $$2), $$4 + ($$0.getFrameY($$1) * SpriteContents.this.height >> $$2));
/*     */   }
/*     */   
/*     */   private int mix(double $$0, int $$1, int $$2) {
/* 261 */     return (int)($$0 * $$1 + (1.0D - $$0) * $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 266 */     for (NativeImage $$0 : this.activeFrame)
/* 267 */       $$0.close(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\SpriteContents$InterpolationData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */