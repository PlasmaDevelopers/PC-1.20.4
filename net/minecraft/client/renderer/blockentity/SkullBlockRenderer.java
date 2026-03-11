/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.model.PiglinHeadModel;
/*     */ import net.minecraft.client.model.SkullModel;
/*     */ import net.minecraft.client.model.SkullModelBase;
/*     */ import net.minecraft.client.model.dragon.DragonHeadModel;
/*     */ import net.minecraft.client.model.geom.EntityModelSet;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.client.resources.SkinManager;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.block.AbstractSkullBlock;
/*     */ import net.minecraft.world.level.block.SkullBlock;
/*     */ import net.minecraft.world.level.block.WallSkullBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RotationSegment;
/*     */ 
/*     */ public class SkullBlockRenderer implements BlockEntityRenderer<SkullBlockEntity> {
/*     */   private final Map<SkullBlock.Type, SkullModelBase> modelByType;
/*     */   
/*     */   static {
/*  36 */     SKIN_BY_TYPE = (Map<SkullBlock.Type, ResourceLocation>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put(SkullBlock.Types.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
/*     */           $$0.put(SkullBlock.Types.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
/*     */           $$0.put(SkullBlock.Types.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
/*     */           $$0.put(SkullBlock.Types.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
/*     */           $$0.put(SkullBlock.Types.DRAGON, new ResourceLocation("textures/entity/enderdragon/dragon.png"));
/*     */           $$0.put(SkullBlock.Types.PIGLIN, new ResourceLocation("textures/entity/piglin/piglin.png"));
/*     */           $$0.put(SkullBlock.Types.PLAYER, DefaultPlayerSkin.getDefaultTexture());
/*     */         });
/*     */   } private static final Map<SkullBlock.Type, ResourceLocation> SKIN_BY_TYPE;
/*     */   public static Map<SkullBlock.Type, SkullModelBase> createSkullRenderers(EntityModelSet $$0) {
/*  47 */     ImmutableMap.Builder<SkullBlock.Type, SkullModelBase> $$1 = ImmutableMap.builder();
/*     */     
/*  49 */     $$1.put(SkullBlock.Types.SKELETON, new SkullModel($$0.bakeLayer(ModelLayers.SKELETON_SKULL)));
/*  50 */     $$1.put(SkullBlock.Types.WITHER_SKELETON, new SkullModel($$0.bakeLayer(ModelLayers.WITHER_SKELETON_SKULL)));
/*  51 */     $$1.put(SkullBlock.Types.PLAYER, new SkullModel($$0.bakeLayer(ModelLayers.PLAYER_HEAD)));
/*  52 */     $$1.put(SkullBlock.Types.ZOMBIE, new SkullModel($$0.bakeLayer(ModelLayers.ZOMBIE_HEAD)));
/*  53 */     $$1.put(SkullBlock.Types.CREEPER, new SkullModel($$0.bakeLayer(ModelLayers.CREEPER_HEAD)));
/*  54 */     $$1.put(SkullBlock.Types.DRAGON, new DragonHeadModel($$0.bakeLayer(ModelLayers.DRAGON_SKULL)));
/*  55 */     $$1.put(SkullBlock.Types.PIGLIN, new PiglinHeadModel($$0.bakeLayer(ModelLayers.PIGLIN_HEAD)));
/*  56 */     return (Map<SkullBlock.Type, SkullModelBase>)$$1.build();
/*     */   }
/*     */   
/*     */   public SkullBlockRenderer(BlockEntityRendererProvider.Context $$0) {
/*  60 */     this.modelByType = createSkullRenderers($$0.getModelSet());
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(SkullBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*  65 */     float $$6 = $$0.getAnimation($$1);
/*     */     
/*  67 */     BlockState $$7 = $$0.getBlockState();
/*  68 */     boolean $$8 = $$7.getBlock() instanceof WallSkullBlock;
/*  69 */     Direction $$9 = $$8 ? (Direction)$$7.getValue((Property)WallSkullBlock.FACING) : null;
/*  70 */     int $$10 = $$8 ? RotationSegment.convertToSegment($$9.getOpposite()) : ((Integer)$$7.getValue((Property)SkullBlock.ROTATION)).intValue();
/*  71 */     float $$11 = RotationSegment.convertToDegrees($$10);
/*     */     
/*  73 */     SkullBlock.Type $$12 = ((AbstractSkullBlock)$$7.getBlock()).getType();
/*  74 */     SkullModelBase $$13 = this.modelByType.get($$12);
/*  75 */     RenderType $$14 = getRenderType($$12, $$0.getOwnerProfile());
/*     */     
/*  77 */     renderSkull($$9, $$11, $$6, $$2, $$3, $$4, $$13, $$14);
/*     */   }
/*     */   
/*     */   public static void renderSkull(@Nullable Direction $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5, SkullModelBase $$6, RenderType $$7) {
/*  81 */     $$3.pushPose();
/*     */     
/*  83 */     if ($$0 == null) {
/*  84 */       $$3.translate(0.5F, 0.0F, 0.5F);
/*     */     } else {
/*  86 */       float $$8 = 0.25F;
/*  87 */       $$3.translate(0.5F - $$0
/*  88 */           .getStepX() * 0.25F, 0.25F, 0.5F - $$0
/*     */           
/*  90 */           .getStepZ() * 0.25F);
/*     */     } 
/*     */ 
/*     */     
/*  94 */     $$3.scale(-1.0F, -1.0F, 1.0F);
/*     */     
/*  96 */     VertexConsumer $$9 = $$4.getBuffer($$7);
/*  97 */     $$6.setupAnim($$2, $$1, 0.0F);
/*  98 */     $$6.renderToBuffer($$3, $$9, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 100 */     $$3.popPose();
/*     */   }
/*     */   
/*     */   public static RenderType getRenderType(SkullBlock.Type $$0, @Nullable GameProfile $$1) {
/* 104 */     ResourceLocation $$2 = SKIN_BY_TYPE.get($$0);
/* 105 */     if ($$0 != SkullBlock.Types.PLAYER || $$1 == null) {
/* 106 */       return RenderType.entityCutoutNoCullZOffset($$2);
/*     */     }
/* 108 */     SkinManager $$3 = Minecraft.getInstance().getSkinManager();
/* 109 */     return RenderType.entityTranslucent($$3.getInsecureSkin($$1).texture());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\SkullBlockRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */