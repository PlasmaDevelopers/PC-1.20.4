/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.HorseModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.horse.Horse;
/*    */ import net.minecraft.world.entity.animal.horse.Variant;
/*    */ 
/*    */ public final class HorseRenderer extends AbstractHorseRenderer<Horse, HorseModel<Horse>> {
/*    */   static {
/* 16 */     LOCATION_BY_VARIANT = (Map<Variant, ResourceLocation>)Util.make(Maps.newEnumMap(Variant.class), $$0 -> {
/*    */           $$0.put(Variant.WHITE, new ResourceLocation("textures/entity/horse/horse_white.png"));
/*    */           $$0.put(Variant.CREAMY, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
/*    */           $$0.put(Variant.CHESTNUT, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
/*    */           $$0.put(Variant.BROWN, new ResourceLocation("textures/entity/horse/horse_brown.png"));
/*    */           $$0.put(Variant.BLACK, new ResourceLocation("textures/entity/horse/horse_black.png"));
/*    */           $$0.put(Variant.GRAY, new ResourceLocation("textures/entity/horse/horse_gray.png"));
/*    */           $$0.put(Variant.DARK_BROWN, new ResourceLocation("textures/entity/horse/horse_darkbrown.png"));
/*    */         });
/*    */   } private static final Map<Variant, ResourceLocation> LOCATION_BY_VARIANT;
/*    */   public HorseRenderer(EntityRendererProvider.Context $$0) {
/* 27 */     super($$0, new HorseModel($$0.bakeLayer(ModelLayers.HORSE)), 1.1F);
/*    */     
/* 29 */     addLayer((RenderLayer<Horse, HorseModel<Horse>>)new HorseMarkingLayer(this));
/* 30 */     addLayer((RenderLayer<Horse, HorseModel<Horse>>)new HorseArmorLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Horse $$0) {
/* 35 */     return LOCATION_BY_VARIANT.get($$0.getVariant());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\HorseRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */