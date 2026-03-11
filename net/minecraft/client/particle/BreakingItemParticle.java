/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ItemParticleOption;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public class BreakingItemParticle extends TextureSheetParticle {
/*    */   private final float uo;
/*    */   
/*    */   BreakingItemParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, ItemStack $$7) {
/* 15 */     this($$0, $$1, $$2, $$3, $$7);
/* 16 */     this.xd *= 0.10000000149011612D;
/* 17 */     this.yd *= 0.10000000149011612D;
/* 18 */     this.zd *= 0.10000000149011612D;
/* 19 */     this.xd += $$4;
/* 20 */     this.yd += $$5;
/* 21 */     this.zd += $$6;
/*    */   }
/*    */   private final float vo;
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 26 */     return ParticleRenderType.TERRAIN_SHEET;
/*    */   }
/*    */   
/*    */   protected BreakingItemParticle(ClientLevel $$0, double $$1, double $$2, double $$3, ItemStack $$4) {
/* 30 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 31 */     setSprite(Minecraft.getInstance().getItemRenderer().getModel($$4, (Level)$$0, null, 0).getParticleIcon());
/* 32 */     this.gravity = 1.0F;
/* 33 */     this.quadSize /= 2.0F;
/*    */     
/* 35 */     this.uo = this.random.nextFloat() * 3.0F;
/* 36 */     this.vo = this.random.nextFloat() * 3.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getU0() {
/* 41 */     return this.sprite.getU((this.uo + 1.0F) / 4.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getU1() {
/* 46 */     return this.sprite.getU(this.uo / 4.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getV0() {
/* 51 */     return this.sprite.getV(this.vo / 4.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getV1() {
/* 56 */     return this.sprite.getV((this.vo + 1.0F) / 4.0F);
/*    */   }
/*    */   
/*    */   public static class Provider
/*    */     implements ParticleProvider<ItemParticleOption> {
/*    */     public Particle createParticle(ItemParticleOption $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 62 */       return new BreakingItemParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, $$0.getItem());
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SlimeProvider
/*    */     implements ParticleProvider<SimpleParticleType> {
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 69 */       return new BreakingItemParticle($$1, $$2, $$3, $$4, new ItemStack((ItemLike)Items.SLIME_BALL));
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SnowballProvider
/*    */     implements ParticleProvider<SimpleParticleType> {
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 76 */       return new BreakingItemParticle($$1, $$2, $$3, $$4, new ItemStack((ItemLike)Items.SNOWBALL));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\BreakingItemParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */