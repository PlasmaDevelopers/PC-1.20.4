/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.ChestedHorseModel;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
/*    */ 
/*    */ public class ChestedHorseRenderer<T extends AbstractChestedHorse> extends AbstractHorseRenderer<T, ChestedHorseModel<T>> {
/* 14 */   private static final Map<EntityType<?>, ResourceLocation> MAP = Maps.newHashMap((Map)ImmutableMap.of(EntityType.DONKEY, new ResourceLocation("textures/entity/horse/donkey.png"), EntityType.MULE, new ResourceLocation("textures/entity/horse/mule.png")));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChestedHorseRenderer(EntityRendererProvider.Context $$0, float $$1, ModelLayerLocation $$2) {
/* 20 */     super($$0, new ChestedHorseModel($$0.bakeLayer($$2)), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(T $$0) {
/* 25 */     return MAP.get($$0.getType());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ChestedHorseRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */