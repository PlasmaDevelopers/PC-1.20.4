/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
/*    */ 
/*    */ public class TheEndGatewayRenderer extends TheEndPortalRenderer<TheEndGatewayBlockEntity> {
/* 12 */   private static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/end_gateway_beam.png");
/*    */   
/*    */   public TheEndGatewayRenderer(BlockEntityRendererProvider.Context $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(TheEndGatewayBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 20 */     if ($$0.isSpawning() || $$0.isCoolingDown()) {
/* 21 */       float $$6 = $$0.isSpawning() ? $$0.getSpawnPercent($$1) : $$0.getCooldownPercent($$1);
/* 22 */       double $$7 = $$0.isSpawning() ? $$0.getLevel().getMaxBuildHeight() : 50.0D;
/* 23 */       $$6 = Mth.sin($$6 * 3.1415927F);
/* 24 */       int $$8 = Mth.floor($$6 * $$7);
/* 25 */       float[] $$9 = $$0.isSpawning() ? DyeColor.MAGENTA.getTextureDiffuseColors() : DyeColor.PURPLE.getTextureDiffuseColors();
/* 26 */       long $$10 = $$0.getLevel().getGameTime();
/* 27 */       BeaconRenderer.renderBeaconBeam($$2, $$3, BEAM_LOCATION, $$1, $$6, $$10, -$$8, $$8 * 2, $$9, 0.15F, 0.175F);
/*    */     } 
/* 29 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getOffsetUp() {
/* 34 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getOffsetDown() {
/* 39 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected RenderType renderType() {
/* 44 */     return RenderType.endGateway();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getViewDistance() {
/* 49 */     return 256;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\TheEndGatewayRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */