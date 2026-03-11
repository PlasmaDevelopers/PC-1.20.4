/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.HorseModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.horse.Horse;
/*    */ import net.minecraft.world.entity.animal.horse.Markings;
/*    */ 
/*    */ public class HorseMarkingLayer extends RenderLayer<Horse, HorseModel<Horse>> {
/*    */   static {
/* 19 */     LOCATION_BY_MARKINGS = (Map<Markings, ResourceLocation>)Util.make(Maps.newEnumMap(Markings.class), $$0 -> {
/*    */           $$0.put(Markings.NONE, null);
/*    */           $$0.put(Markings.WHITE, new ResourceLocation("textures/entity/horse/horse_markings_white.png"));
/*    */           $$0.put(Markings.WHITE_FIELD, new ResourceLocation("textures/entity/horse/horse_markings_whitefield.png"));
/*    */           $$0.put(Markings.WHITE_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_whitedots.png"));
/*    */           $$0.put(Markings.BLACK_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_blackdots.png"));
/*    */         });
/*    */   } private static final Map<Markings, ResourceLocation> LOCATION_BY_MARKINGS;
/*    */   public HorseMarkingLayer(RenderLayerParent<Horse, HorseModel<Horse>> $$0) {
/* 28 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Horse $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 33 */     ResourceLocation $$10 = LOCATION_BY_MARKINGS.get($$3.getMarkings());
/*    */     
/* 35 */     if ($$10 == null || $$3.isInvisible()) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     VertexConsumer $$11 = $$1.getBuffer(RenderType.entityTranslucent($$10));
/* 40 */     getParentModel().renderToBuffer($$0, $$11, $$2, LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\HorseMarkingLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */