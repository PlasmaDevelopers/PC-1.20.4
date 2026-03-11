/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.AxolotlModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.axolotl.Axolotl;
/*    */ 
/*    */ public class AxolotlRenderer extends MobRenderer<Axolotl, AxolotlModel<Axolotl>> {
/*    */   static {
/* 14 */     TEXTURE_BY_TYPE = (Map<Axolotl.Variant, ResourceLocation>)Util.make(Maps.newHashMap(), $$0 -> {
/*    */           for (Axolotl.Variant $$1 : Axolotl.Variant.values()) {
/*    */             $$0.put($$1, new ResourceLocation(String.format(Locale.ROOT, "textures/entity/axolotl/axolotl_%s.png", new Object[] { $$1.getName() })));
/*    */           } 
/*    */         });
/*    */   } private static final Map<Axolotl.Variant, ResourceLocation> TEXTURE_BY_TYPE;
/*    */   public AxolotlRenderer(EntityRendererProvider.Context $$0) {
/* 21 */     super($$0, new AxolotlModel($$0.bakeLayer(ModelLayers.AXOLOTL)), 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Axolotl $$0) {
/* 26 */     return TEXTURE_BY_TYPE.get($$0.getVariant());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\AxolotlRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */