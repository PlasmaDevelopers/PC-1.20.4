/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.HorseModel;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*    */ 
/*    */ public class UndeadHorseRenderer extends AbstractHorseRenderer<AbstractHorse, HorseModel<AbstractHorse>> {
/* 14 */   private static final Map<EntityType<?>, ResourceLocation> MAP = Maps.newHashMap((Map)ImmutableMap.of(EntityType.ZOMBIE_HORSE, new ResourceLocation("textures/entity/horse/horse_zombie.png"), EntityType.SKELETON_HORSE, new ResourceLocation("textures/entity/horse/horse_skeleton.png")));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UndeadHorseRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1) {
/* 20 */     super($$0, new HorseModel($$0.bakeLayer($$1)), 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(AbstractHorse $$0) {
/* 25 */     return MAP.get($$0.getType());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\UndeadHorseRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */