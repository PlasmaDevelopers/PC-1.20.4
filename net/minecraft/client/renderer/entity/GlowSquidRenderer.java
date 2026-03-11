/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.SquidModel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.GlowSquid;
/*    */ import net.minecraft.world.entity.animal.Squid;
/*    */ 
/*    */ public class GlowSquidRenderer extends SquidRenderer<GlowSquid> {
/* 11 */   private static final ResourceLocation GLOW_SQUID_LOCATION = new ResourceLocation("textures/entity/squid/glow_squid.png");
/*    */   
/*    */   public GlowSquidRenderer(EntityRendererProvider.Context $$0, SquidModel<GlowSquid> $$1) {
/* 14 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(GlowSquid $$0) {
/* 19 */     return GLOW_SQUID_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(GlowSquid $$0, BlockPos $$1) {
/* 24 */     int $$2 = (int)Mth.clampedLerp(0.0F, 15.0F, 1.0F - $$0.getDarkTicksRemaining() / 10.0F);
/* 25 */     if ($$2 == 15) {
/* 26 */       return 15;
/*    */     }
/* 28 */     return Math.max($$2, super.getBlockLightLevel($$0, $$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\GlowSquidRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */