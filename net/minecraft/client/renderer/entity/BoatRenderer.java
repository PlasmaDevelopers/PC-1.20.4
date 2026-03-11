/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.model.BoatModel;
/*     */ import net.minecraft.client.model.ChestBoatModel;
/*     */ import net.minecraft.client.model.ChestRaftModel;
/*     */ import net.minecraft.client.model.ListModel;
/*     */ import net.minecraft.client.model.RaftModel;
/*     */ import net.minecraft.client.model.WaterPatchModel;
/*     */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import org.joml.Quaternionf;
/*     */ 
/*     */ public class BoatRenderer extends EntityRenderer<Boat> {
/*     */   private final Map<Boat.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;
/*     */   
/*     */   public BoatRenderer(EntityRendererProvider.Context $$0, boolean $$1) {
/*  32 */     super($$0);
/*  33 */     this.shadowRadius = 0.8F;
/*     */     
/*  35 */     this
/*  36 */       .boatResources = (Map<Boat.Type, Pair<ResourceLocation, ListModel<Boat>>>)Stream.<Boat.Type>of(Boat.Type.values()).collect(ImmutableMap.toImmutableMap($$0 -> $$0, $$2 -> Pair.of(new ResourceLocation(getTextureLocation($$2, $$0)), createBoatModel($$1, $$2, $$0))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ListModel<Boat> createBoatModel(EntityRendererProvider.Context $$0, Boat.Type $$1, boolean $$2) {
/*  46 */     ModelLayerLocation $$3 = $$2 ? ModelLayers.createChestBoatModelName($$1) : ModelLayers.createBoatModelName($$1);
/*  47 */     ModelPart $$4 = $$0.bakeLayer($$3);
/*     */     
/*  49 */     if ($$1 == Boat.Type.BAMBOO) {
/*  50 */       return $$2 ? (ListModel<Boat>)new ChestRaftModel($$4) : (ListModel<Boat>)new RaftModel($$4);
/*     */     }
/*     */     
/*  53 */     return $$2 ? (ListModel<Boat>)new ChestBoatModel($$4) : (ListModel<Boat>)new BoatModel($$4);
/*     */   }
/*     */   
/*     */   private static String getTextureLocation(Boat.Type $$0, boolean $$1) {
/*  57 */     return $$1 ? ("textures/entity/chest_boat/" + 
/*  58 */       $$0.getName() + ".png") : ("textures/entity/boat/" + 
/*     */       
/*  60 */       $$0.getName() + ".png");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Boat $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  66 */     $$3.pushPose();
/*     */     
/*  68 */     $$3.translate(0.0F, 0.375F, 0.0F);
/*  69 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F - $$1));
/*  70 */     float $$6 = $$0.getHurtTime() - $$2;
/*  71 */     float $$7 = $$0.getDamage() - $$2;
/*  72 */     if ($$7 < 0.0F) {
/*  73 */       $$7 = 0.0F;
/*     */     }
/*  75 */     if ($$6 > 0.0F) {
/*  76 */       $$3.mulPose(Axis.XP.rotationDegrees(Mth.sin($$6) * $$6 * $$7 / 10.0F * $$0.getHurtDir()));
/*     */     }
/*     */     
/*  79 */     float $$8 = $$0.getBubbleAngle($$2);
/*  80 */     if (!Mth.equal($$8, 0.0F))
/*     */     {
/*  82 */       $$3.mulPose((new Quaternionf()).setAngleAxis($$0.getBubbleAngle($$2) * 0.017453292F, 1.0F, 0.0F, 1.0F));
/*     */     }
/*     */     
/*  85 */     Pair<ResourceLocation, ListModel<Boat>> $$9 = this.boatResources.get($$0.getVariant());
/*  86 */     ResourceLocation $$10 = (ResourceLocation)$$9.getFirst();
/*  87 */     ListModel<Boat> $$11 = (ListModel<Boat>)$$9.getSecond();
/*     */ 
/*     */     
/*  90 */     $$3.scale(-1.0F, -1.0F, 1.0F);
/*     */     
/*  92 */     $$3.mulPose(Axis.YP.rotationDegrees(90.0F));
/*  93 */     $$11.setupAnim((Entity)$$0, $$2, 0.0F, -0.1F, 0.0F, 0.0F);
/*     */     
/*  95 */     VertexConsumer $$12 = $$4.getBuffer($$11.renderType($$10));
/*  96 */     $$11.renderToBuffer($$3, $$12, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  98 */     if (!$$0.isUnderWater()) {
/*  99 */       VertexConsumer $$13 = $$4.getBuffer(RenderType.waterMask());
/* 100 */       if ($$11 instanceof WaterPatchModel) { WaterPatchModel $$14 = (WaterPatchModel)$$11;
/* 101 */         $$14.waterPatch().render($$3, $$13, $$5, OverlayTexture.NO_OVERLAY); }
/*     */     
/*     */     } 
/*     */     
/* 105 */     $$3.popPose();
/*     */     
/* 107 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(Boat $$0) {
/* 112 */     return (ResourceLocation)((Pair)this.boatResources.get($$0.getVariant())).getFirst();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\BoatRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */