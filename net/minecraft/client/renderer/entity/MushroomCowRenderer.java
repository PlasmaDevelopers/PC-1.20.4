/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.CowModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.MushroomCowMushroomLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.MushroomCow;
/*    */ 
/*    */ public class MushroomCowRenderer extends MobRenderer<MushroomCow, CowModel<MushroomCow>> {
/*    */   static {
/* 14 */     TEXTURES = (Map<MushroomCow.MushroomType, ResourceLocation>)Util.make(Maps.newHashMap(), $$0 -> {
/*    */           $$0.put(MushroomCow.MushroomType.BROWN, new ResourceLocation("textures/entity/cow/brown_mooshroom.png"));
/*    */           $$0.put(MushroomCow.MushroomType.RED, new ResourceLocation("textures/entity/cow/red_mooshroom.png"));
/*    */         });
/*    */   } private static final Map<MushroomCow.MushroomType, ResourceLocation> TEXTURES;
/*    */   public MushroomCowRenderer(EntityRendererProvider.Context $$0) {
/* 20 */     super($$0, new CowModel($$0.bakeLayer(ModelLayers.MOOSHROOM)), 0.7F);
/*    */     
/* 22 */     addLayer((RenderLayer<MushroomCow, CowModel<MushroomCow>>)new MushroomCowMushroomLayer(this, $$0.getBlockRenderDispatcher()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(MushroomCow $$0) {
/* 27 */     return TEXTURES.get($$0.getVariant());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\MushroomCowRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */