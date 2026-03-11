/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MutableSpriteSet
/*     */   implements SpriteSet
/*     */ {
/*     */   private List<TextureAtlasSprite> sprites;
/*     */   
/*     */   public TextureAtlasSprite get(int $$0, int $$1) {
/* 104 */     return this.sprites.get($$0 * (this.sprites.size() - 1) / $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite get(RandomSource $$0) {
/* 109 */     return this.sprites.get($$0.nextInt(this.sprites.size()));
/*     */   }
/*     */   
/*     */   public void rebind(List<TextureAtlasSprite> $$0) {
/* 113 */     this.sprites = (List<TextureAtlasSprite>)ImmutableList.copyOf($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleEngine$MutableSpriteSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */