/*     */ package net.minecraft.client.renderer.entity.player;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Axis;
/*     */ import net.minecraft.client.model.EntityModel;
/*     */ import net.minecraft.client.model.HumanoidArmorModel;
/*     */ import net.minecraft.client.model.HumanoidModel;
/*     */ import net.minecraft.client.model.PlayerModel;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.player.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.entity.EntityRendererProvider;
/*     */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*     */ import net.minecraft.client.renderer.entity.layers.ArrowLayer;
/*     */ import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
/*     */ import net.minecraft.client.renderer.entity.layers.Deadmau5EarsLayer;
/*     */ import net.minecraft.client.renderer.entity.layers.ElytraLayer;
/*     */ import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
/*     */ import net.minecraft.client.renderer.entity.layers.ParrotOnShoulderLayer;
/*     */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*     */ import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.numbers.NumberFormat;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.PlayerModelPart;
/*     */ import net.minecraft.world.item.CrossbowItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.UseAnim;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.scores.DisplaySlot;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.ReadOnlyScoreInfo;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ import net.minecraft.world.scores.Scoreboard;
/*     */ 
/*     */ public class PlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
/*     */   public PlayerRenderer(EntityRendererProvider.Context $$0, boolean $$1) {
/*  47 */     super($$0, (EntityModel)new PlayerModel($$0.bakeLayer($$1 ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), $$1), 0.5F);
/*     */     
/*  49 */     addLayer((RenderLayer)new HumanoidArmorLayer((RenderLayerParent)this, (HumanoidModel)new HumanoidArmorModel($$0
/*  50 */             .bakeLayer($$1 ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)), (HumanoidModel)new HumanoidArmorModel($$0
/*  51 */             .bakeLayer($$1 ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)), $$0
/*  52 */           .getModelManager()));
/*     */     
/*  54 */     addLayer((RenderLayer)new PlayerItemInHandLayer((RenderLayerParent)this, $$0.getItemInHandRenderer()));
/*  55 */     addLayer((RenderLayer)new ArrowLayer($$0, this));
/*  56 */     addLayer((RenderLayer)new Deadmau5EarsLayer((RenderLayerParent)this));
/*  57 */     addLayer((RenderLayer)new CapeLayer((RenderLayerParent)this));
/*  58 */     addLayer((RenderLayer)new CustomHeadLayer((RenderLayerParent)this, $$0.getModelSet(), $$0.getItemInHandRenderer()));
/*  59 */     addLayer((RenderLayer)new ElytraLayer((RenderLayerParent)this, $$0.getModelSet()));
/*  60 */     addLayer((RenderLayer)new ParrotOnShoulderLayer((RenderLayerParent)this, $$0.getModelSet()));
/*  61 */     addLayer((RenderLayer)new SpinAttackEffectLayer((RenderLayerParent)this, $$0.getModelSet()));
/*  62 */     addLayer((RenderLayer)new BeeStingerLayer(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(AbstractClientPlayer $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  67 */     setModelProperties($$0);
/*  68 */     super.render((LivingEntity)$$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getRenderOffset(AbstractClientPlayer $$0, float $$1) {
/*  73 */     if ($$0.isCrouching()) {
/*  74 */       return new Vec3(0.0D, -0.125D, 0.0D);
/*     */     }
/*     */     
/*  77 */     return super.getRenderOffset((Entity)$$0, $$1);
/*     */   }
/*     */   
/*     */   private void setModelProperties(AbstractClientPlayer $$0) {
/*  81 */     PlayerModel<AbstractClientPlayer> $$1 = (PlayerModel<AbstractClientPlayer>)getModel();
/*  82 */     if ($$0.isSpectator()) {
/*  83 */       $$1.setAllVisible(false);
/*  84 */       $$1.head.visible = true;
/*  85 */       $$1.hat.visible = true;
/*     */     } else {
/*  87 */       $$1.setAllVisible(true);
/*     */       
/*  89 */       $$1.hat.visible = $$0.isModelPartShown(PlayerModelPart.HAT);
/*  90 */       $$1.jacket.visible = $$0.isModelPartShown(PlayerModelPart.JACKET);
/*  91 */       $$1.leftPants.visible = $$0.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
/*  92 */       $$1.rightPants.visible = $$0.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
/*  93 */       $$1.leftSleeve.visible = $$0.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
/*  94 */       $$1.rightSleeve.visible = $$0.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
/*     */       
/*  96 */       $$1.crouching = $$0.isCrouching();
/*     */       
/*  98 */       HumanoidModel.ArmPose $$2 = getArmPose($$0, InteractionHand.MAIN_HAND);
/*  99 */       HumanoidModel.ArmPose $$3 = getArmPose($$0, InteractionHand.OFF_HAND);
/*     */ 
/*     */       
/* 102 */       if ($$2.isTwoHanded()) {
/* 103 */         $$3 = $$0.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
/*     */       }
/*     */       
/* 106 */       if ($$0.getMainArm() == HumanoidArm.RIGHT) {
/* 107 */         $$1.rightArmPose = $$2;
/* 108 */         $$1.leftArmPose = $$3;
/*     */       } else {
/* 110 */         $$1.rightArmPose = $$3;
/* 111 */         $$1.leftArmPose = $$2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static HumanoidModel.ArmPose getArmPose(AbstractClientPlayer $$0, InteractionHand $$1) {
/* 118 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 119 */     if ($$2.isEmpty()) {
/* 120 */       return HumanoidModel.ArmPose.EMPTY;
/*     */     }
/*     */     
/* 123 */     if ($$0.getUsedItemHand() == $$1 && $$0.getUseItemRemainingTicks() > 0) {
/* 124 */       UseAnim $$3 = $$2.getUseAnimation();
/* 125 */       if ($$3 == UseAnim.BLOCK)
/* 126 */         return HumanoidModel.ArmPose.BLOCK; 
/* 127 */       if ($$3 == UseAnim.BOW)
/* 128 */         return HumanoidModel.ArmPose.BOW_AND_ARROW; 
/* 129 */       if ($$3 == UseAnim.SPEAR)
/* 130 */         return HumanoidModel.ArmPose.THROW_SPEAR; 
/* 131 */       if ($$3 == UseAnim.CROSSBOW && $$1 == $$0.getUsedItemHand())
/* 132 */         return HumanoidModel.ArmPose.CROSSBOW_CHARGE; 
/* 133 */       if ($$3 == UseAnim.SPYGLASS)
/* 134 */         return HumanoidModel.ArmPose.SPYGLASS; 
/* 135 */       if ($$3 == UseAnim.TOOT_HORN)
/* 136 */         return HumanoidModel.ArmPose.TOOT_HORN; 
/* 137 */       if ($$3 == UseAnim.BRUSH) {
/* 138 */         return HumanoidModel.ArmPose.BRUSH;
/*     */       }
/*     */     }
/* 141 */     else if (!$$0.swinging && $$2.is(Items.CROSSBOW) && CrossbowItem.isCharged($$2)) {
/* 142 */       return HumanoidModel.ArmPose.CROSSBOW_HOLD;
/*     */     } 
/*     */ 
/*     */     
/* 146 */     return HumanoidModel.ArmPose.ITEM;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(AbstractClientPlayer $$0) {
/* 151 */     return $$0.getSkin().texture();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void scale(AbstractClientPlayer $$0, PoseStack $$1, float $$2) {
/* 156 */     float $$3 = 0.9375F;
/* 157 */     $$1.scale(0.9375F, 0.9375F, 0.9375F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderNameTag(AbstractClientPlayer $$0, Component $$1, PoseStack $$2, MultiBufferSource $$3, int $$4) {
/* 162 */     double $$5 = this.entityRenderDispatcher.distanceToSqr((Entity)$$0);
/*     */     
/* 164 */     $$2.pushPose();
/* 165 */     if ($$5 < 100.0D) {
/* 166 */       Scoreboard $$6 = $$0.getScoreboard();
/* 167 */       Objective $$7 = $$6.getDisplayObjective(DisplaySlot.BELOW_NAME);
/* 168 */       if ($$7 != null) {
/* 169 */         ReadOnlyScoreInfo $$8 = $$6.getPlayerScoreInfo((ScoreHolder)$$0, $$7);
/* 170 */         MutableComponent mutableComponent = ReadOnlyScoreInfo.safeFormatValue($$8, $$7.numberFormatOrDefault((NumberFormat)StyledFormat.NO_STYLE));
/* 171 */         super.renderNameTag((Entity)$$0, (Component)Component.empty().append((Component)mutableComponent).append(CommonComponents.SPACE).append($$7.getDisplayName()), $$2, $$3, $$4);
/* 172 */         Objects.requireNonNull(getFont()); $$2.translate(0.0F, 9.0F * 1.15F * 0.025F, 0.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     super.renderNameTag((Entity)$$0, $$1, $$2, $$3, $$4);
/* 177 */     $$2.popPose();
/*     */   }
/*     */   
/*     */   public void renderRightHand(PoseStack $$0, MultiBufferSource $$1, int $$2, AbstractClientPlayer $$3) {
/* 181 */     renderHand($$0, $$1, $$2, $$3, ((PlayerModel)this.model).rightArm, ((PlayerModel)this.model).rightSleeve);
/*     */   }
/*     */   
/*     */   public void renderLeftHand(PoseStack $$0, MultiBufferSource $$1, int $$2, AbstractClientPlayer $$3) {
/* 185 */     renderHand($$0, $$1, $$2, $$3, ((PlayerModel)this.model).leftArm, ((PlayerModel)this.model).leftSleeve);
/*     */   }
/*     */   
/*     */   private void renderHand(PoseStack $$0, MultiBufferSource $$1, int $$2, AbstractClientPlayer $$3, ModelPart $$4, ModelPart $$5) {
/* 189 */     PlayerModel<AbstractClientPlayer> $$6 = (PlayerModel<AbstractClientPlayer>)getModel();
/* 190 */     setModelProperties($$3);
/*     */     
/* 192 */     $$6.attackTime = 0.0F;
/* 193 */     $$6.crouching = false;
/* 194 */     $$6.swimAmount = 0.0F;
/* 195 */     $$6.setupAnim((LivingEntity)$$3, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/* 196 */     $$4.xRot = 0.0F;
/* 197 */     ResourceLocation $$7 = $$3.getSkin().texture();
/* 198 */     $$4.render($$0, $$1.getBuffer(RenderType.entitySolid($$7)), $$2, OverlayTexture.NO_OVERLAY);
/* 199 */     $$5.xRot = 0.0F;
/* 200 */     $$5.render($$0, $$1.getBuffer(RenderType.entityTranslucent($$7)), $$2, OverlayTexture.NO_OVERLAY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupRotations(AbstractClientPlayer $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 205 */     float $$5 = $$0.getSwimAmount($$4);
/* 206 */     float $$6 = $$0.getViewXRot($$4);
/*     */     
/* 208 */     if ($$0.isFallFlying()) {
/* 209 */       super.setupRotations((LivingEntity)$$0, $$1, $$2, $$3, $$4);
/*     */       
/* 211 */       float $$7 = $$0.getFallFlyingTicks() + $$4;
/* 212 */       float $$8 = Mth.clamp($$7 * $$7 / 100.0F, 0.0F, 1.0F);
/*     */       
/* 214 */       if (!$$0.isAutoSpinAttack()) {
/* 215 */         $$1.mulPose(Axis.XP.rotationDegrees($$8 * (-90.0F - $$6)));
/*     */       }
/*     */       
/* 218 */       Vec3 $$9 = $$0.getViewVector($$4);
/* 219 */       Vec3 $$10 = $$0.getDeltaMovementLerped($$4);
/* 220 */       double $$11 = $$10.horizontalDistanceSqr();
/* 221 */       double $$12 = $$9.horizontalDistanceSqr();
/* 222 */       if ($$11 > 0.0D && $$12 > 0.0D) {
/* 223 */         double $$13 = ($$10.x * $$9.x + $$10.z * $$9.z) / Math.sqrt($$11 * $$12);
/* 224 */         double $$14 = $$10.x * $$9.z - $$10.z * $$9.x;
/* 225 */         $$1.mulPose(Axis.YP.rotation((float)(Math.signum($$14) * Math.acos($$13))));
/*     */       } 
/* 227 */     } else if ($$5 > 0.0F) {
/* 228 */       super.setupRotations((LivingEntity)$$0, $$1, $$2, $$3, $$4);
/*     */ 
/*     */ 
/*     */       
/* 232 */       float $$15 = $$0.isInWater() ? (-90.0F - $$6) : -90.0F;
/*     */ 
/*     */       
/* 235 */       float $$16 = Mth.lerp($$5, 0.0F, $$15);
/* 236 */       $$1.mulPose(Axis.XP.rotationDegrees($$16));
/*     */       
/* 238 */       if ($$0.isVisuallySwimming())
/*     */       {
/* 240 */         $$1.translate(0.0F, -1.0F, 0.3F);
/*     */       }
/*     */     } else {
/* 243 */       super.setupRotations((LivingEntity)$$0, $$1, $$2, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\player\PlayerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */