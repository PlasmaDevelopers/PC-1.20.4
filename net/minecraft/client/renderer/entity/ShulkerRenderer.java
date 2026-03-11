/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.ShulkerModel;
/*    */ import net.minecraft.client.renderer.Sheets;
/*    */ import net.minecraft.client.renderer.culling.Frustum;
/*    */ import net.minecraft.client.resources.model.Material;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.monster.Shulker;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ShulkerRenderer extends MobRenderer<Shulker, ShulkerModel<Shulker>> {
/* 19 */   private static final ResourceLocation DEFAULT_TEXTURE_LOCATION = new ResourceLocation("textures/" + Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION.texture().getPath() + ".png"); private static final ResourceLocation[] TEXTURE_LOCATION; static {
/* 20 */     TEXTURE_LOCATION = (ResourceLocation[])Sheets.SHULKER_TEXTURE_LOCATION.stream().map($$0 -> new ResourceLocation("textures/" + $$0.texture().getPath() + ".png")).toArray($$0 -> new ResourceLocation[$$0]);
/*    */   }
/*    */   public ShulkerRenderer(EntityRendererProvider.Context $$0) {
/* 23 */     super($$0, new ShulkerModel($$0.bakeLayer(ModelLayers.SHULKER)), 0.0F);
/*    */     
/* 25 */     addLayer((RenderLayer<Shulker, ShulkerModel<Shulker>>)new ShulkerHeadLayer(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3 getRenderOffset(Shulker $$0, float $$1) {
/* 30 */     return $$0.getRenderPosition($$1).orElse(super.getRenderOffset($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender(Shulker $$0, Frustum $$1, double $$2, double $$3, double $$4) {
/* 35 */     if (super.shouldRender($$0, $$1, $$2, $$3, $$4)) {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     return $$0.getRenderPosition(0.0F).filter($$2 -> {
/*    */           EntityType<?> $$3 = $$0.getType();
/*    */           
/*    */           float $$4 = $$3.getHeight() / 2.0F;
/*    */           float $$5 = $$3.getWidth() / 2.0F;
/*    */           Vec3 $$6 = Vec3.atBottomCenterOf((Vec3i)$$0.blockPosition());
/*    */           return $$1.isVisible((new AABB($$2.x, $$2.y + $$4, $$2.z, $$6.x, $$6.y + $$4, $$6.z)).inflate($$5, $$4, $$5));
/* 46 */         }).isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Shulker $$0) {
/* 51 */     return getTextureLocation($$0.getColor());
/*    */   }
/*    */   
/*    */   public static ResourceLocation getTextureLocation(@Nullable DyeColor $$0) {
/* 55 */     if ($$0 == null) {
/* 56 */       return DEFAULT_TEXTURE_LOCATION;
/*    */     }
/* 58 */     return TEXTURE_LOCATION[$$0.getId()];
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(Shulker $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 63 */     super.setupRotations($$0, $$1, $$2, $$3 + 180.0F, $$4);
/*    */     
/* 65 */     $$1.translate(0.0D, 0.5D, 0.0D);
/* 66 */     $$1.mulPose($$0.getAttachFace().getOpposite().getRotation());
/* 67 */     $$1.translate(0.0D, -0.5D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ShulkerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */