/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.EntityModel;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.PlayerModelPart;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ import net.minecraft.world.scores.Team;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class LivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
/*  30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final float EYE_BED_OFFSET = 0.1F;
/*     */   
/*     */   protected M model;
/*  35 */   protected final List<RenderLayer<T, M>> layers = Lists.newArrayList();
/*     */   
/*     */   public LivingEntityRenderer(EntityRendererProvider.Context $$0, M $$1, float $$2) {
/*  38 */     super($$0);
/*  39 */     this.model = $$1;
/*  40 */     this.shadowRadius = $$2;
/*     */   }
/*     */   
/*     */   protected final boolean addLayer(RenderLayer<T, M> $$0) {
/*  44 */     return this.layers.add($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public M getModel() {
/*  49 */     return this.model;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(T $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  54 */     $$3.pushPose();
/*     */ 
/*     */     
/*  57 */     ((EntityModel)this.model).attackTime = getAttackAnim($$0, $$2);
/*  58 */     ((EntityModel)this.model).riding = $$0.isPassenger();
/*  59 */     ((EntityModel)this.model).young = $$0.isBaby();
/*     */     
/*  61 */     float $$6 = Mth.rotLerp($$2, ((LivingEntity)$$0).yBodyRotO, ((LivingEntity)$$0).yBodyRot);
/*  62 */     float $$7 = Mth.rotLerp($$2, ((LivingEntity)$$0).yHeadRotO, ((LivingEntity)$$0).yHeadRot);
/*  63 */     float $$8 = $$7 - $$6;
/*     */ 
/*     */     
/*  66 */     if ($$0.isPassenger()) { Entity entity = $$0.getVehicle(); if (entity instanceof LivingEntity) { LivingEntity $$9 = (LivingEntity)entity;
/*  67 */         $$6 = Mth.rotLerp($$2, $$9.yBodyRotO, $$9.yBodyRot);
/*     */         
/*  69 */         $$8 = $$7 - $$6;
/*  70 */         float $$10 = Mth.wrapDegrees($$8);
/*  71 */         if ($$10 < -85.0F) {
/*  72 */           $$10 = -85.0F;
/*     */         }
/*  74 */         if ($$10 >= 85.0F) {
/*  75 */           $$10 = 85.0F;
/*     */         }
/*  77 */         $$6 = $$7 - $$10;
/*  78 */         if ($$10 * $$10 > 2500.0F) {
/*  79 */           $$6 += $$10 * 0.2F;
/*     */         }
/*     */         
/*  82 */         $$8 = $$7 - $$6; }
/*     */        }
/*     */     
/*  85 */     float $$11 = Mth.lerp($$2, ((LivingEntity)$$0).xRotO, $$0.getXRot());
/*     */     
/*  87 */     if (isEntityUpsideDown((LivingEntity)$$0)) {
/*  88 */       $$11 *= -1.0F;
/*  89 */       $$8 *= -1.0F;
/*     */     } 
/*     */     
/*  92 */     if ($$0.hasPose(Pose.SLEEPING)) {
/*  93 */       Direction $$12 = $$0.getBedOrientation();
/*  94 */       if ($$12 != null) {
/*  95 */         float $$13 = $$0.getEyeHeight(Pose.STANDING) - 0.1F;
/*  96 */         $$3.translate(-$$12.getStepX() * $$13, 0.0F, -$$12.getStepZ() * $$13);
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     float $$14 = getBob($$0, $$2);
/* 101 */     setupRotations($$0, $$3, $$14, $$6, $$2);
/*     */ 
/*     */ 
/*     */     
/* 105 */     $$3.scale(-1.0F, -1.0F, 1.0F);
/* 106 */     scale($$0, $$3, $$2);
/*     */ 
/*     */     
/* 109 */     $$3.translate(0.0F, -1.501F, 0.0F);
/*     */ 
/*     */     
/* 112 */     float $$15 = 0.0F;
/* 113 */     float $$16 = 0.0F;
/* 114 */     if (!$$0.isPassenger() && $$0.isAlive()) {
/* 115 */       $$15 = ((LivingEntity)$$0).walkAnimation.speed($$2);
/* 116 */       $$16 = ((LivingEntity)$$0).walkAnimation.position($$2);
/* 117 */       if ($$0.isBaby()) {
/* 118 */         $$16 *= 3.0F;
/*     */       }
/*     */       
/* 121 */       if ($$15 > 1.0F) {
/* 122 */         $$15 = 1.0F;
/*     */       }
/*     */     } 
/*     */     
/* 126 */     this.model.prepareMobModel((Entity)$$0, $$16, $$15, $$2);
/*     */     
/* 128 */     this.model.setupAnim((Entity)$$0, $$16, $$15, $$14, $$8, $$11);
/*     */     
/* 130 */     Minecraft $$17 = Minecraft.getInstance();
/* 131 */     boolean $$18 = isBodyVisible($$0);
/* 132 */     boolean $$19 = (!$$18 && !$$0.isInvisibleTo((Player)$$17.player));
/* 133 */     boolean $$20 = $$17.shouldEntityAppearGlowing((Entity)$$0);
/*     */     
/* 135 */     RenderType $$21 = getRenderType($$0, $$18, $$19, $$20);
/* 136 */     if ($$21 != null) {
/* 137 */       VertexConsumer $$22 = $$4.getBuffer($$21);
/* 138 */       int $$23 = getOverlayCoords((LivingEntity)$$0, getWhiteOverlayProgress($$0, $$2));
/* 139 */       this.model.renderToBuffer($$3, $$22, $$5, $$23, 1.0F, 1.0F, 1.0F, $$19 ? 0.15F : 1.0F);
/*     */     } 
/*     */     
/* 142 */     if (!$$0.isSpectator()) {
/* 143 */       for (RenderLayer<T, M> $$24 : this.layers) {
/* 144 */         $$24.render($$3, $$4, $$5, (Entity)$$0, $$16, $$15, $$2, $$14, $$8, $$11);
/*     */       }
/*     */     }
/*     */     
/* 148 */     $$3.popPose();
/* 149 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected RenderType getRenderType(T $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 154 */     ResourceLocation $$4 = getTextureLocation($$0);
/* 155 */     if ($$2) {
/* 156 */       return RenderType.itemEntityTranslucentCull($$4);
/*     */     }
/* 158 */     if ($$1) {
/* 159 */       return this.model.renderType($$4);
/*     */     }
/* 161 */     if ($$3) {
/* 162 */       return RenderType.outline($$4);
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   public static int getOverlayCoords(LivingEntity $$0, float $$1) {
/* 168 */     return OverlayTexture.pack(OverlayTexture.u($$1), OverlayTexture.v(($$0.hurtTime > 0 || $$0.deathTime > 0)));
/*     */   }
/*     */   
/*     */   protected boolean isBodyVisible(T $$0) {
/* 172 */     return !$$0.isInvisible();
/*     */   }
/*     */   
/*     */   private static float sleepDirectionToRotation(Direction $$0) {
/* 176 */     switch ($$0) {
/*     */       case ALWAYS:
/* 178 */         return 90.0F;
/*     */       case NEVER:
/* 180 */         return 0.0F;
/*     */       case HIDE_FOR_OTHER_TEAMS:
/* 182 */         return 270.0F;
/*     */       case HIDE_FOR_OWN_TEAM:
/* 184 */         return 180.0F;
/*     */     } 
/* 186 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isShaking(T $$0) {
/* 191 */     return $$0.isFullyFrozen();
/*     */   }
/*     */   
/*     */   protected void setupRotations(T $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 195 */     if (isShaking($$0)) {
/* 196 */       $$3 += (float)(Math.cos(((LivingEntity)$$0).tickCount * 3.25D) * Math.PI * 0.4000000059604645D);
/*     */     }
/*     */     
/* 199 */     if (!$$0.hasPose(Pose.SLEEPING)) {
/* 200 */       $$1.mulPose(Axis.YP.rotationDegrees(180.0F - $$3));
/*     */     }
/* 202 */     if (((LivingEntity)$$0).deathTime > 0) {
/* 203 */       float $$5 = (((LivingEntity)$$0).deathTime + $$4 - 1.0F) / 20.0F * 1.6F;
/* 204 */       $$5 = Mth.sqrt($$5);
/* 205 */       if ($$5 > 1.0F) {
/* 206 */         $$5 = 1.0F;
/*     */       }
/* 208 */       $$1.mulPose(Axis.ZP.rotationDegrees($$5 * getFlipDegrees($$0)));
/* 209 */     } else if ($$0.isAutoSpinAttack()) {
/* 210 */       $$1.mulPose(Axis.XP.rotationDegrees(-90.0F - $$0.getXRot()));
/* 211 */       $$1.mulPose(Axis.YP.rotationDegrees((((LivingEntity)$$0).tickCount + $$4) * -75.0F));
/* 212 */     } else if ($$0.hasPose(Pose.SLEEPING)) {
/* 213 */       Direction $$6 = $$0.getBedOrientation();
/* 214 */       float $$7 = ($$6 != null) ? sleepDirectionToRotation($$6) : $$3;
/* 215 */       $$1.mulPose(Axis.YP.rotationDegrees($$7));
/* 216 */       $$1.mulPose(Axis.ZP.rotationDegrees(getFlipDegrees($$0)));
/* 217 */       $$1.mulPose(Axis.YP.rotationDegrees(270.0F));
/* 218 */     } else if (isEntityUpsideDown((LivingEntity)$$0)) {
/* 219 */       $$1.translate(0.0F, $$0.getBbHeight() + 0.1F, 0.0F);
/* 220 */       $$1.mulPose(Axis.ZP.rotationDegrees(180.0F));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected float getAttackAnim(T $$0, float $$1) {
/* 225 */     return $$0.getAttackAnim($$1);
/*     */   }
/*     */   
/*     */   protected float getBob(T $$0, float $$1) {
/* 229 */     return ((LivingEntity)$$0).tickCount + $$1;
/*     */   }
/*     */   
/*     */   protected float getFlipDegrees(T $$0) {
/* 233 */     return 90.0F;
/*     */   }
/*     */   
/*     */   protected float getWhiteOverlayProgress(T $$0, float $$1) {
/* 237 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void scale(T $$0, PoseStack $$1, float $$2) {}
/*     */ 
/*     */   
/*     */   protected boolean shouldShowName(T $$0) {
/* 245 */     double $$1 = this.entityRenderDispatcher.distanceToSqr((Entity)$$0);
/* 246 */     float $$2 = $$0.isDiscrete() ? 32.0F : 64.0F;
/* 247 */     if ($$1 >= ($$2 * $$2)) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     Minecraft $$3 = Minecraft.getInstance();
/* 252 */     LocalPlayer $$4 = $$3.player;
/* 253 */     boolean $$5 = !$$0.isInvisibleTo((Player)$$4);
/* 254 */     if ($$0 != $$4) {
/* 255 */       PlayerTeam playerTeam1 = $$0.getTeam();
/* 256 */       PlayerTeam playerTeam2 = $$4.getTeam();
/* 257 */       if (playerTeam1 != null) {
/* 258 */         Team.Visibility $$8 = playerTeam1.getNameTagVisibility();
/* 259 */         switch ($$8) {
/*     */           case ALWAYS:
/* 261 */             return $$5;
/*     */           case NEVER:
/* 263 */             return false;
/*     */           case HIDE_FOR_OTHER_TEAMS:
/* 265 */             return (playerTeam2 == null) ? $$5 : ((playerTeam1.isAlliedTo((Team)playerTeam2) && (playerTeam1.canSeeFriendlyInvisibles() || $$5)));
/*     */           case HIDE_FOR_OWN_TEAM:
/* 267 */             return (playerTeam2 == null) ? $$5 : ((!playerTeam1.isAlliedTo((Team)playerTeam2) && $$5));
/*     */         } 
/* 269 */         return true;
/*     */       } 
/*     */     } 
/* 272 */     return (Minecraft.renderNames() && $$0 != $$3.getCameraEntity() && $$5 && !$$0.isVehicle());
/*     */   }
/*     */   
/*     */   public static boolean isEntityUpsideDown(LivingEntity $$0) {
/* 276 */     if ($$0 instanceof Player || $$0.hasCustomName()) {
/* 277 */       String $$1 = ChatFormatting.stripFormatting($$0.getName().getString());
/* 278 */       if ("Dinnerbone".equals($$1) || "Grumm".equals($$1)) {
/* 279 */         return (!($$0 instanceof Player) || ((Player)$$0).isModelPartShown(PlayerModelPart.CAPE));
/*     */       }
/*     */     } 
/* 282 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\LivingEntityRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */