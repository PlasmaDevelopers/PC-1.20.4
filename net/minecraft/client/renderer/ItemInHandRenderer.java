/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.player.AbstractClientPlayer;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*     */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*     */ import net.minecraft.client.renderer.entity.player.PlayerRenderer;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.CrossbowItem;
/*     */ import net.minecraft.world.item.ItemDisplayContext;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.MapItem;
/*     */ import net.minecraft.world.item.UseAnim;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class ItemInHandRenderer {
/*  32 */   private static final RenderType MAP_BACKGROUND = RenderType.text(new ResourceLocation("textures/map/map_background.png"));
/*  33 */   private static final RenderType MAP_BACKGROUND_CHECKERBOARD = RenderType.text(new ResourceLocation("textures/map/map_background_checkerboard.png"));
/*     */   
/*     */   private static final float ITEM_SWING_X_POS_SCALE = -0.4F;
/*     */   
/*     */   private static final float ITEM_SWING_Y_POS_SCALE = 0.2F;
/*     */   
/*     */   private static final float ITEM_SWING_Z_POS_SCALE = -0.2F;
/*     */   
/*     */   private static final float ITEM_HEIGHT_SCALE = -0.6F;
/*     */   
/*     */   private static final float ITEM_POS_X = 0.56F;
/*     */   
/*     */   private static final float ITEM_POS_Y = -0.52F;
/*     */   
/*     */   private static final float ITEM_POS_Z = -0.72F;
/*     */   
/*     */   private static final float ITEM_PRESWING_ROT_Y = 45.0F;
/*     */   
/*     */   private static final float ITEM_SWING_X_ROT_AMOUNT = -80.0F;
/*     */   
/*     */   private static final float ITEM_SWING_Y_ROT_AMOUNT = -20.0F;
/*     */   
/*     */   private static final float ITEM_SWING_Z_ROT_AMOUNT = -20.0F;
/*     */   
/*     */   private static final float EAT_JIGGLE_X_ROT_AMOUNT = 10.0F;
/*     */   
/*     */   private static final float EAT_JIGGLE_Y_ROT_AMOUNT = 90.0F;
/*     */   
/*     */   private static final float EAT_JIGGLE_Z_ROT_AMOUNT = 30.0F;
/*     */   
/*     */   private static final float EAT_JIGGLE_X_POS_SCALE = 0.6F;
/*     */   
/*     */   private static final float EAT_JIGGLE_Y_POS_SCALE = -0.5F;
/*     */   
/*     */   private static final float EAT_JIGGLE_Z_POS_SCALE = 0.0F;
/*     */   
/*     */   private static final double EAT_JIGGLE_EXPONENT = 27.0D;
/*     */   
/*     */   private static final float EAT_EXTRA_JIGGLE_CUTOFF = 0.8F;
/*     */   
/*     */   private static final float EAT_EXTRA_JIGGLE_SCALE = 0.1F;
/*     */   
/*     */   private static final float ARM_SWING_X_POS_SCALE = -0.3F;
/*     */   
/*     */   private static final float ARM_SWING_Y_POS_SCALE = 0.4F;
/*     */   
/*     */   private static final float ARM_SWING_Z_POS_SCALE = -0.4F;
/*     */   
/*     */   private static final float ARM_SWING_Y_ROT_AMOUNT = 70.0F;
/*     */   
/*     */   private static final float ARM_SWING_Z_ROT_AMOUNT = -20.0F;
/*     */   
/*     */   private static final float ARM_HEIGHT_SCALE = -0.6F;
/*     */   
/*     */   private static final float ARM_POS_SCALE = 0.8F;
/*     */   
/*     */   private static final float ARM_POS_X = 0.8F;
/*     */   
/*     */   private static final float ARM_POS_Y = -0.75F;
/*     */   
/*     */   private static final float ARM_POS_Z = -0.9F;
/*     */   
/*     */   private static final float ARM_PRESWING_ROT_Y = 45.0F;
/*     */   
/*     */   private static final float ARM_PREROTATION_X_OFFSET = -1.0F;
/*     */   
/*     */   private static final float ARM_PREROTATION_Y_OFFSET = 3.6F;
/*     */   
/*     */   private static final float ARM_PREROTATION_Z_OFFSET = 3.5F;
/*     */   
/*     */   private static final float ARM_POSTROTATION_X_OFFSET = 5.6F;
/*     */   
/*     */   private static final int ARM_ROT_X = 200;
/*     */   
/*     */   private static final int ARM_ROT_Y = -135;
/*     */   
/*     */   private static final int ARM_ROT_Z = 120;
/*     */   private static final float MAP_SWING_X_POS_SCALE = -0.4F;
/*     */   private static final float MAP_SWING_Z_POS_SCALE = -0.2F;
/*     */   private static final float MAP_HANDS_POS_X = 0.0F;
/*     */   private static final float MAP_HANDS_POS_Y = 0.04F;
/*     */   private static final float MAP_HANDS_POS_Z = -0.72F;
/*     */   private static final float MAP_HANDS_HEIGHT_SCALE = -1.2F;
/*     */   private static final float MAP_HANDS_TILT_SCALE = -0.5F;
/*     */   private static final float MAP_PLAYER_PITCH_SCALE = 45.0F;
/*     */   private static final float MAP_HANDS_Z_ROT_AMOUNT = -85.0F;
/*     */   private static final float MAPHAND_X_ROT_AMOUNT = 45.0F;
/*     */   private static final float MAPHAND_Y_ROT_AMOUNT = 92.0F;
/*     */   private static final float MAPHAND_Z_ROT_AMOUNT = -41.0F;
/*     */   private static final float MAP_HAND_X_POS = 0.3F;
/*     */   private static final float MAP_HAND_Y_POS = -1.1F;
/*     */   private static final float MAP_HAND_Z_POS = 0.45F;
/*     */   private static final float MAP_SWING_X_ROT_AMOUNT = 20.0F;
/*     */   private static final float MAP_PRE_ROT_SCALE = 0.38F;
/*     */   private static final float MAP_GLOBAL_X_POS = -0.5F;
/*     */   private static final float MAP_GLOBAL_Y_POS = -0.5F;
/*     */   private static final float MAP_GLOBAL_Z_POS = 0.0F;
/*     */   private static final float MAP_FINAL_SCALE = 0.0078125F;
/*     */   private static final int MAP_BORDER = 7;
/*     */   private static final int MAP_HEIGHT = 128;
/*     */   private static final int MAP_WIDTH = 128;
/*     */   private static final float BOW_CHARGE_X_POS_SCALE = 0.0F;
/*     */   private static final float BOW_CHARGE_Y_POS_SCALE = 0.0F;
/*     */   private static final float BOW_CHARGE_Z_POS_SCALE = 0.04F;
/*     */   private static final float BOW_CHARGE_SHAKE_X_SCALE = 0.0F;
/*     */   private static final float BOW_CHARGE_SHAKE_Y_SCALE = 0.004F;
/*     */   private static final float BOW_CHARGE_SHAKE_Z_SCALE = 0.0F;
/*     */   private static final float BOW_CHARGE_Z_SCALE = 0.2F;
/*     */   private static final float BOW_MIN_SHAKE_CHARGE = 0.1F;
/*     */   private final Minecraft minecraft;
/* 143 */   private ItemStack mainHandItem = ItemStack.EMPTY;
/* 144 */   private ItemStack offHandItem = ItemStack.EMPTY;
/*     */   private float mainHandHeight;
/*     */   private float oMainHandHeight;
/*     */   private float offHandHeight;
/*     */   private float oOffHandHeight;
/*     */   private final EntityRenderDispatcher entityRenderDispatcher;
/*     */   private final ItemRenderer itemRenderer;
/*     */   
/*     */   public ItemInHandRenderer(Minecraft $$0, EntityRenderDispatcher $$1, ItemRenderer $$2) {
/* 153 */     this.minecraft = $$0;
/* 154 */     this.entityRenderDispatcher = $$1;
/* 155 */     this.itemRenderer = $$2;
/*     */   }
/*     */   
/*     */   public void renderItem(LivingEntity $$0, ItemStack $$1, ItemDisplayContext $$2, boolean $$3, PoseStack $$4, MultiBufferSource $$5, int $$6) {
/* 159 */     if ($$1.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     this.itemRenderer.renderStatic($$0, $$1, $$2, $$3, $$4, $$5, $$0.level(), $$6, OverlayTexture.NO_OVERLAY, $$0.getId() + $$2.ordinal());
/*     */   }
/*     */   
/*     */   private float calculateMapTilt(float $$0) {
/* 167 */     float $$1 = 1.0F - $$0 / 45.0F + 0.1F;
/* 168 */     $$1 = Mth.clamp($$1, 0.0F, 1.0F);
/* 169 */     $$1 = -Mth.cos($$1 * 3.1415927F) * 0.5F + 0.5F;
/* 170 */     return $$1;
/*     */   }
/*     */   
/*     */   private void renderMapHand(PoseStack $$0, MultiBufferSource $$1, int $$2, HumanoidArm $$3) {
/* 174 */     PlayerRenderer $$4 = (PlayerRenderer)this.entityRenderDispatcher.getRenderer((Entity)this.minecraft.player);
/*     */     
/* 176 */     $$0.pushPose();
/*     */     
/* 178 */     float $$5 = ($$3 == HumanoidArm.RIGHT) ? 1.0F : -1.0F;
/*     */     
/* 180 */     $$0.mulPose(Axis.YP.rotationDegrees(92.0F));
/* 181 */     $$0.mulPose(Axis.XP.rotationDegrees(45.0F));
/* 182 */     $$0.mulPose(Axis.ZP.rotationDegrees($$5 * -41.0F));
/* 183 */     $$0.translate($$5 * 0.3F, -1.1F, 0.45F);
/*     */     
/* 185 */     if ($$3 == HumanoidArm.RIGHT) {
/* 186 */       $$4.renderRightHand($$0, $$1, $$2, (AbstractClientPlayer)this.minecraft.player);
/*     */     } else {
/* 188 */       $$4.renderLeftHand($$0, $$1, $$2, (AbstractClientPlayer)this.minecraft.player);
/*     */     } 
/* 190 */     $$0.popPose();
/*     */   }
/*     */   
/*     */   private void renderOneHandedMap(PoseStack $$0, MultiBufferSource $$1, int $$2, float $$3, HumanoidArm $$4, float $$5, ItemStack $$6) {
/* 194 */     float $$7 = ($$4 == HumanoidArm.RIGHT) ? 1.0F : -1.0F;
/*     */     
/* 196 */     $$0.translate($$7 * 0.125F, -0.125F, 0.0F);
/*     */     
/* 198 */     if (!this.minecraft.player.isInvisible()) {
/* 199 */       $$0.pushPose();
/* 200 */       $$0.mulPose(Axis.ZP.rotationDegrees($$7 * 10.0F));
/* 201 */       renderPlayerArm($$0, $$1, $$2, $$3, $$5, $$4);
/* 202 */       $$0.popPose();
/*     */     } 
/*     */     
/* 205 */     $$0.pushPose();
/* 206 */     $$0.translate($$7 * 0.51F, -0.08F + $$3 * -1.2F, -0.75F);
/*     */     
/* 208 */     float $$8 = Mth.sqrt($$5);
/* 209 */     float $$9 = Mth.sin($$8 * 3.1415927F);
/* 210 */     float $$10 = -0.5F * $$9;
/* 211 */     float $$11 = 0.4F * Mth.sin($$8 * 6.2831855F);
/* 212 */     float $$12 = -0.3F * Mth.sin($$5 * 3.1415927F);
/* 213 */     $$0.translate($$7 * $$10, $$11 - 0.3F * $$9, $$12);
/*     */     
/* 215 */     $$0.mulPose(Axis.XP.rotationDegrees($$9 * -45.0F));
/* 216 */     $$0.mulPose(Axis.YP.rotationDegrees($$7 * $$9 * -30.0F));
/* 217 */     renderMap($$0, $$1, $$2, $$6);
/* 218 */     $$0.popPose();
/*     */   }
/*     */   
/*     */   private void renderTwoHandedMap(PoseStack $$0, MultiBufferSource $$1, int $$2, float $$3, float $$4, float $$5) {
/* 222 */     float $$6 = Mth.sqrt($$5);
/* 223 */     float $$7 = -0.2F * Mth.sin($$5 * 3.1415927F);
/* 224 */     float $$8 = -0.4F * Mth.sin($$6 * 3.1415927F);
/* 225 */     $$0.translate(0.0F, -$$7 / 2.0F, $$8);
/*     */     
/* 227 */     float $$9 = calculateMapTilt($$3);
/* 228 */     $$0.translate(0.0F, 0.04F + $$4 * -1.2F + $$9 * -0.5F, -0.72F);
/* 229 */     $$0.mulPose(Axis.XP.rotationDegrees($$9 * -85.0F));
/*     */     
/* 231 */     if (!this.minecraft.player.isInvisible()) {
/* 232 */       $$0.pushPose();
/* 233 */       $$0.mulPose(Axis.YP.rotationDegrees(90.0F));
/*     */       
/* 235 */       renderMapHand($$0, $$1, $$2, HumanoidArm.RIGHT);
/* 236 */       renderMapHand($$0, $$1, $$2, HumanoidArm.LEFT);
/*     */       
/* 238 */       $$0.popPose();
/*     */     } 
/*     */     
/* 241 */     float $$10 = Mth.sin($$6 * 3.1415927F);
/* 242 */     $$0.mulPose(Axis.XP.rotationDegrees($$10 * 20.0F));
/*     */     
/* 244 */     $$0.scale(2.0F, 2.0F, 2.0F);
/*     */     
/* 246 */     renderMap($$0, $$1, $$2, this.mainHandItem);
/*     */   }
/*     */   
/*     */   private void renderMap(PoseStack $$0, MultiBufferSource $$1, int $$2, ItemStack $$3) {
/* 250 */     $$0.mulPose(Axis.YP.rotationDegrees(180.0F));
/* 251 */     $$0.mulPose(Axis.ZP.rotationDegrees(180.0F));
/*     */     
/* 253 */     $$0.scale(0.38F, 0.38F, 0.38F);
/*     */     
/* 255 */     $$0.translate(-0.5F, -0.5F, 0.0F);
/* 256 */     $$0.scale(0.0078125F, 0.0078125F, 0.0078125F);
/*     */     
/* 258 */     Integer $$4 = MapItem.getMapId($$3);
/* 259 */     MapItemSavedData $$5 = MapItem.getSavedData($$4, (Level)this.minecraft.level);
/*     */     
/* 261 */     VertexConsumer $$6 = $$1.getBuffer(($$5 == null) ? MAP_BACKGROUND : MAP_BACKGROUND_CHECKERBOARD);
/*     */     
/* 263 */     Matrix4f $$7 = $$0.last().pose();
/*     */     
/* 265 */     $$6.vertex($$7, -7.0F, 135.0F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2($$2).endVertex();
/* 266 */     $$6.vertex($$7, 135.0F, 135.0F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2($$2).endVertex();
/* 267 */     $$6.vertex($$7, 135.0F, -7.0F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2($$2).endVertex();
/* 268 */     $$6.vertex($$7, -7.0F, -7.0F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2($$2).endVertex();
/*     */     
/* 270 */     if ($$5 != null) {
/* 271 */       this.minecraft.gameRenderer.getMapRenderer().render($$0, $$1, $$4.intValue(), $$5, false, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderPlayerArm(PoseStack $$0, MultiBufferSource $$1, int $$2, float $$3, float $$4, HumanoidArm $$5) {
/* 276 */     boolean $$6 = ($$5 != HumanoidArm.LEFT);
/* 277 */     float $$7 = $$6 ? 1.0F : -1.0F;
/* 278 */     float $$8 = Mth.sqrt($$4);
/* 279 */     float $$9 = -0.3F * Mth.sin($$8 * 3.1415927F);
/* 280 */     float $$10 = 0.4F * Mth.sin($$8 * 6.2831855F);
/* 281 */     float $$11 = -0.4F * Mth.sin($$4 * 3.1415927F);
/* 282 */     $$0.translate($$7 * ($$9 + 0.64000005F), $$10 + -0.6F + $$3 * -0.6F, $$11 + -0.71999997F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 288 */     $$0.mulPose(Axis.YP.rotationDegrees($$7 * 45.0F));
/*     */     
/* 290 */     float $$12 = Mth.sin($$4 * $$4 * 3.1415927F);
/* 291 */     float $$13 = Mth.sin($$8 * 3.1415927F);
/* 292 */     $$0.mulPose(Axis.YP.rotationDegrees($$7 * $$13 * 70.0F));
/* 293 */     $$0.mulPose(Axis.ZP.rotationDegrees($$7 * $$12 * -20.0F));
/*     */     
/* 295 */     LocalPlayer localPlayer = this.minecraft.player;
/*     */     
/* 297 */     $$0.translate($$7 * -1.0F, 3.6F, 3.5F);
/* 298 */     $$0.mulPose(Axis.ZP.rotationDegrees($$7 * 120.0F));
/* 299 */     $$0.mulPose(Axis.XP.rotationDegrees(200.0F));
/* 300 */     $$0.mulPose(Axis.YP.rotationDegrees($$7 * -135.0F));
/* 301 */     $$0.translate($$7 * 5.6F, 0.0F, 0.0F);
/*     */     
/* 303 */     PlayerRenderer $$15 = (PlayerRenderer)this.entityRenderDispatcher.getRenderer((Entity)localPlayer);
/* 304 */     if ($$6) {
/* 305 */       $$15.renderRightHand($$0, $$1, $$2, (AbstractClientPlayer)localPlayer);
/*     */     } else {
/* 307 */       $$15.renderLeftHand($$0, $$1, $$2, (AbstractClientPlayer)localPlayer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyEatTransform(PoseStack $$0, float $$1, HumanoidArm $$2, ItemStack $$3) {
/* 312 */     float $$4 = this.minecraft.player.getUseItemRemainingTicks() - $$1 + 1.0F;
/* 313 */     float $$5 = $$4 / $$3.getUseDuration();
/*     */     
/* 315 */     if ($$5 < 0.8F) {
/* 316 */       float $$6 = Mth.abs(Mth.cos($$4 / 4.0F * 3.1415927F) * 0.1F);
/* 317 */       $$0.translate(0.0F, $$6, 0.0F);
/*     */     } 
/*     */     
/* 320 */     float $$7 = 1.0F - (float)Math.pow($$5, 27.0D);
/*     */     
/* 322 */     int $$8 = ($$2 == HumanoidArm.RIGHT) ? 1 : -1;
/* 323 */     $$0.translate($$7 * 0.6F * $$8, $$7 * -0.5F, $$7 * 0.0F);
/*     */     
/* 325 */     $$0.mulPose(Axis.YP.rotationDegrees($$8 * $$7 * 90.0F));
/* 326 */     $$0.mulPose(Axis.XP.rotationDegrees($$7 * 10.0F));
/* 327 */     $$0.mulPose(Axis.ZP.rotationDegrees($$8 * $$7 * 30.0F));
/*     */   }
/*     */   
/*     */   private void applyBrushTransform(PoseStack $$0, float $$1, HumanoidArm $$2, ItemStack $$3, float $$4) {
/* 331 */     applyItemArmTransform($$0, $$2, $$4);
/*     */     
/* 333 */     float $$5 = (this.minecraft.player.getUseItemRemainingTicks() % 10);
/* 334 */     float $$6 = $$5 - $$1 + 1.0F;
/* 335 */     float $$7 = 1.0F - $$6 / 10.0F;
/*     */     
/* 337 */     float $$8 = -90.0F;
/* 338 */     float $$9 = 60.0F;
/* 339 */     float $$10 = 150.0F;
/* 340 */     float $$11 = -15.0F;
/*     */     
/* 342 */     int $$12 = 2;
/*     */     
/* 344 */     float $$13 = -15.0F + 75.0F * Mth.cos($$7 * 2.0F * 3.1415927F);
/* 345 */     if ($$2 != HumanoidArm.RIGHT) {
/* 346 */       $$0.translate(0.1D, 0.83D, 0.35D);
/*     */       
/* 348 */       $$0.mulPose(Axis.XP.rotationDegrees(-80.0F));
/* 349 */       $$0.mulPose(Axis.YP.rotationDegrees(-90.0F));
/*     */       
/* 351 */       $$0.mulPose(Axis.XP.rotationDegrees($$13));
/* 352 */       $$0.translate(-0.3D, 0.22D, 0.35D);
/*     */     } else {
/* 354 */       $$0.translate(-0.25D, 0.22D, 0.35D);
/*     */       
/* 356 */       $$0.mulPose(Axis.XP.rotationDegrees(-80.0F));
/* 357 */       $$0.mulPose(Axis.YP.rotationDegrees(90.0F));
/* 358 */       $$0.mulPose(Axis.ZP.rotationDegrees(0.0F));
/*     */       
/* 360 */       $$0.mulPose(Axis.XP.rotationDegrees($$13));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyItemArmAttackTransform(PoseStack $$0, HumanoidArm $$1, float $$2) {
/* 365 */     int $$3 = ($$1 == HumanoidArm.RIGHT) ? 1 : -1;
/*     */     
/* 367 */     float $$4 = Mth.sin($$2 * $$2 * 3.1415927F);
/* 368 */     $$0.mulPose(Axis.YP.rotationDegrees($$3 * (45.0F + $$4 * -20.0F)));
/*     */     
/* 370 */     float $$5 = Mth.sin(Mth.sqrt($$2) * 3.1415927F);
/* 371 */     $$0.mulPose(Axis.ZP.rotationDegrees($$3 * $$5 * -20.0F));
/* 372 */     $$0.mulPose(Axis.XP.rotationDegrees($$5 * -80.0F));
/* 373 */     $$0.mulPose(Axis.YP.rotationDegrees($$3 * -45.0F));
/*     */   }
/*     */   
/*     */   private void applyItemArmTransform(PoseStack $$0, HumanoidArm $$1, float $$2) {
/* 377 */     int $$3 = ($$1 == HumanoidArm.RIGHT) ? 1 : -1;
/* 378 */     $$0.translate($$3 * 0.56F, -0.52F + $$2 * -0.6F, -0.72F);
/*     */   }
/*     */   
/*     */   public void renderHandsWithItems(float $$0, PoseStack $$1, MultiBufferSource.BufferSource $$2, LocalPlayer $$3, int $$4) {
/* 382 */     float $$5 = $$3.getAttackAnim($$0);
/* 383 */     InteractionHand $$6 = (InteractionHand)MoreObjects.firstNonNull($$3.swingingArm, InteractionHand.MAIN_HAND);
/* 384 */     float $$7 = Mth.lerp($$0, $$3.xRotO, $$3.getXRot());
/*     */     
/* 386 */     HandRenderSelection $$8 = evaluateWhichHandsToRender($$3);
/*     */     
/* 388 */     float $$9 = Mth.lerp($$0, $$3.xBobO, $$3.xBob);
/* 389 */     float $$10 = Mth.lerp($$0, $$3.yBobO, $$3.yBob);
/* 390 */     $$1.mulPose(Axis.XP.rotationDegrees(($$3.getViewXRot($$0) - $$9) * 0.1F));
/* 391 */     $$1.mulPose(Axis.YP.rotationDegrees(($$3.getViewYRot($$0) - $$10) * 0.1F));
/*     */     
/* 393 */     if ($$8.renderMainHand) {
/* 394 */       float $$11 = ($$6 == InteractionHand.MAIN_HAND) ? $$5 : 0.0F;
/* 395 */       float $$12 = 1.0F - Mth.lerp($$0, this.oMainHandHeight, this.mainHandHeight);
/* 396 */       renderArmWithItem((AbstractClientPlayer)$$3, $$0, $$7, InteractionHand.MAIN_HAND, $$11, this.mainHandItem, $$12, $$1, $$2, $$4);
/*     */     } 
/*     */     
/* 399 */     if ($$8.renderOffHand) {
/* 400 */       float $$13 = ($$6 == InteractionHand.OFF_HAND) ? $$5 : 0.0F;
/* 401 */       float $$14 = 1.0F - Mth.lerp($$0, this.oOffHandHeight, this.offHandHeight);
/* 402 */       renderArmWithItem((AbstractClientPlayer)$$3, $$0, $$7, InteractionHand.OFF_HAND, $$13, this.offHandItem, $$14, $$1, $$2, $$4);
/*     */     } 
/*     */     
/* 405 */     $$2.endBatch();
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   enum HandRenderSelection {
/* 410 */     RENDER_BOTH_HANDS(true, true),
/* 411 */     RENDER_MAIN_HAND_ONLY(true, false),
/* 412 */     RENDER_OFF_HAND_ONLY(false, true);
/*     */     final boolean renderMainHand;
/*     */     final boolean renderOffHand;
/*     */     
/*     */     HandRenderSelection(boolean $$0, boolean $$1) {
/* 417 */       this.renderMainHand = $$0;
/* 418 */       this.renderOffHand = $$1;
/*     */     }
/*     */     
/*     */     public static HandRenderSelection onlyForHand(InteractionHand $$0) {
/* 422 */       return ($$0 == InteractionHand.MAIN_HAND) ? RENDER_MAIN_HAND_ONLY : RENDER_OFF_HAND_ONLY;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static HandRenderSelection evaluateWhichHandsToRender(LocalPlayer $$0) {
/* 429 */     ItemStack $$1 = $$0.getMainHandItem();
/* 430 */     ItemStack $$2 = $$0.getOffhandItem();
/*     */     
/* 432 */     boolean $$3 = ($$1.is(Items.BOW) || $$2.is(Items.BOW));
/* 433 */     boolean $$4 = ($$1.is(Items.CROSSBOW) || $$2.is(Items.CROSSBOW));
/*     */     
/* 435 */     if (!$$3 && !$$4) {
/* 436 */       return HandRenderSelection.RENDER_BOTH_HANDS;
/*     */     }
/*     */     
/* 439 */     if ($$0.isUsingItem()) {
/* 440 */       return selectionUsingItemWhileHoldingBowLike($$0);
/*     */     }
/*     */     
/* 443 */     if (isChargedCrossbow($$1)) {
/* 444 */       return HandRenderSelection.RENDER_MAIN_HAND_ONLY;
/*     */     }
/*     */     
/* 447 */     return HandRenderSelection.RENDER_BOTH_HANDS;
/*     */   }
/*     */   
/*     */   private static HandRenderSelection selectionUsingItemWhileHoldingBowLike(LocalPlayer $$0) {
/* 451 */     ItemStack $$1 = $$0.getUseItem();
/* 452 */     InteractionHand $$2 = $$0.getUsedItemHand();
/*     */     
/* 454 */     if ($$1.is(Items.BOW) || $$1.is(Items.CROSSBOW)) {
/* 455 */       return HandRenderSelection.onlyForHand($$2);
/*     */     }
/*     */ 
/*     */     
/* 459 */     return ($$2 == InteractionHand.MAIN_HAND && isChargedCrossbow($$0.getOffhandItem())) ? 
/* 460 */       HandRenderSelection.RENDER_MAIN_HAND_ONLY : 
/* 461 */       HandRenderSelection.RENDER_BOTH_HANDS;
/*     */   }
/*     */   
/*     */   private static boolean isChargedCrossbow(ItemStack $$0) {
/* 465 */     return ($$0.is(Items.CROSSBOW) && CrossbowItem.isCharged($$0));
/*     */   }
/*     */   
/*     */   private void renderArmWithItem(AbstractClientPlayer $$0, float $$1, float $$2, InteractionHand $$3, float $$4, ItemStack $$5, float $$6, PoseStack $$7, MultiBufferSource $$8, int $$9) {
/* 469 */     if ($$0.isScoping()) {
/*     */       return;
/*     */     }
/*     */     
/* 473 */     boolean $$10 = ($$3 == InteractionHand.MAIN_HAND);
/* 474 */     HumanoidArm $$11 = $$10 ? $$0.getMainArm() : $$0.getMainArm().getOpposite();
/*     */     
/* 476 */     $$7.pushPose();
/* 477 */     if ($$5.isEmpty()) {
/* 478 */       if ($$10 && !$$0.isInvisible()) {
/* 479 */         renderPlayerArm($$7, $$8, $$9, $$6, $$4, $$11);
/*     */       }
/*     */     }
/* 482 */     else if ($$5.is(Items.FILLED_MAP)) {
/* 483 */       if ($$10 && this.offHandItem.isEmpty()) {
/* 484 */         renderTwoHandedMap($$7, $$8, $$9, $$2, $$6, $$4);
/*     */       } else {
/* 486 */         renderOneHandedMap($$7, $$8, $$9, $$6, $$11, $$4, $$5);
/*     */       } 
/* 488 */     } else if ($$5.is(Items.CROSSBOW)) {
/* 489 */       boolean $$12 = CrossbowItem.isCharged($$5);
/* 490 */       boolean $$13 = ($$11 == HumanoidArm.RIGHT);
/* 491 */       int $$14 = $$13 ? 1 : -1;
/*     */       
/* 493 */       if ($$0.isUsingItem() && $$0.getUseItemRemainingTicks() > 0 && $$0.getUsedItemHand() == $$3) {
/* 494 */         applyItemArmTransform($$7, $$11, $$6);
/*     */         
/* 496 */         $$7.translate($$14 * -0.4785682F, -0.094387F, 0.05731531F);
/* 497 */         $$7.mulPose(Axis.XP.rotationDegrees(-11.935F));
/* 498 */         $$7.mulPose(Axis.YP.rotationDegrees($$14 * 65.3F));
/* 499 */         $$7.mulPose(Axis.ZP.rotationDegrees($$14 * -9.785F));
/*     */         
/* 501 */         float $$15 = $$5.getUseDuration() - this.minecraft.player.getUseItemRemainingTicks() - $$1 + 1.0F;
/* 502 */         float $$16 = $$15 / CrossbowItem.getChargeDuration($$5);
/* 503 */         if ($$16 > 1.0F) {
/* 504 */           $$16 = 1.0F;
/*     */         }
/* 506 */         if ($$16 > 0.1F) {
/* 507 */           float $$17 = Mth.sin(($$15 - 0.1F) * 1.3F);
/* 508 */           float $$18 = $$16 - 0.1F;
/* 509 */           float $$19 = $$17 * $$18;
/* 510 */           $$7.translate($$19 * 0.0F, $$19 * 0.004F, $$19 * 0.0F);
/*     */         } 
/* 512 */         $$7.translate($$16 * 0.0F, $$16 * 0.0F, $$16 * 0.04F);
/*     */         
/* 514 */         $$7.scale(1.0F, 1.0F, 1.0F + $$16 * 0.2F);
/* 515 */         $$7.mulPose(Axis.YN.rotationDegrees($$14 * 45.0F));
/*     */       } else {
/* 517 */         float $$20 = -0.4F * Mth.sin(Mth.sqrt($$4) * 3.1415927F);
/* 518 */         float $$21 = 0.2F * Mth.sin(Mth.sqrt($$4) * 6.2831855F);
/* 519 */         float $$22 = -0.2F * Mth.sin($$4 * 3.1415927F);
/* 520 */         $$7.translate($$14 * $$20, $$21, $$22);
/* 521 */         applyItemArmTransform($$7, $$11, $$6);
/* 522 */         applyItemArmAttackTransform($$7, $$11, $$4);
/*     */ 
/*     */         
/* 525 */         if ($$12 && $$4 < 0.001F && $$10) {
/* 526 */           $$7.translate($$14 * -0.641864F, 0.0F, 0.0F);
/* 527 */           $$7.mulPose(Axis.YP.rotationDegrees($$14 * 10.0F));
/*     */         } 
/*     */       } 
/*     */       
/* 531 */       renderItem((LivingEntity)$$0, $$5, $$13 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !$$13, $$7, $$8, $$9);
/*     */     } else {
/* 533 */       boolean $$23 = ($$11 == HumanoidArm.RIGHT);
/* 534 */       if ($$0.isUsingItem() && $$0.getUseItemRemainingTicks() > 0 && $$0.getUsedItemHand() == $$3) {
/* 535 */         float $$25, $$30, $$26, $$31; int $$24 = $$23 ? 1 : -1;
/* 536 */         switch ($$5.getUseAnimation()) {
/*     */           case NONE:
/* 538 */             applyItemArmTransform($$7, $$11, $$6);
/*     */             break;
/*     */           case EAT:
/*     */           case DRINK:
/* 542 */             applyEatTransform($$7, $$1, $$11, $$5);
/* 543 */             applyItemArmTransform($$7, $$11, $$6);
/*     */             break;
/*     */           case BLOCK:
/* 546 */             applyItemArmTransform($$7, $$11, $$6);
/*     */             break;
/*     */           case BOW:
/* 549 */             applyItemArmTransform($$7, $$11, $$6);
/*     */             
/* 551 */             $$7.translate($$24 * -0.2785682F, 0.18344387F, 0.15731531F);
/* 552 */             $$7.mulPose(Axis.XP.rotationDegrees(-13.935F));
/* 553 */             $$7.mulPose(Axis.YP.rotationDegrees($$24 * 35.3F));
/* 554 */             $$7.mulPose(Axis.ZP.rotationDegrees($$24 * -9.785F));
/*     */             
/* 556 */             $$25 = $$5.getUseDuration() - this.minecraft.player.getUseItemRemainingTicks() - $$1 + 1.0F;
/* 557 */             $$26 = $$25 / 20.0F;
/* 558 */             $$26 = ($$26 * $$26 + $$26 * 2.0F) / 3.0F;
/* 559 */             if ($$26 > 1.0F) {
/* 560 */               $$26 = 1.0F;
/*     */             }
/* 562 */             if ($$26 > 0.1F) {
/* 563 */               float $$27 = Mth.sin(($$25 - 0.1F) * 1.3F);
/* 564 */               float $$28 = $$26 - 0.1F;
/* 565 */               float $$29 = $$27 * $$28;
/* 566 */               $$7.translate($$29 * 0.0F, $$29 * 0.004F, $$29 * 0.0F);
/*     */             } 
/* 568 */             $$7.translate($$26 * 0.0F, $$26 * 0.0F, $$26 * 0.04F);
/*     */             
/* 570 */             $$7.scale(1.0F, 1.0F, 1.0F + $$26 * 0.2F);
/* 571 */             $$7.mulPose(Axis.YN.rotationDegrees($$24 * 45.0F));
/*     */             break;
/*     */           
/*     */           case SPEAR:
/* 575 */             applyItemArmTransform($$7, $$11, $$6);
/*     */             
/* 577 */             $$7.translate($$24 * -0.5F, 0.7F, 0.1F);
/* 578 */             $$7.mulPose(Axis.XP.rotationDegrees(-55.0F));
/* 579 */             $$7.mulPose(Axis.YP.rotationDegrees($$24 * 35.3F));
/* 580 */             $$7.mulPose(Axis.ZP.rotationDegrees($$24 * -9.785F));
/*     */             
/* 582 */             $$30 = $$5.getUseDuration() - this.minecraft.player.getUseItemRemainingTicks() - $$1 + 1.0F;
/* 583 */             $$31 = $$30 / 10.0F;
/* 584 */             if ($$31 > 1.0F) {
/* 585 */               $$31 = 1.0F;
/*     */             }
/* 587 */             if ($$31 > 0.1F) {
/* 588 */               float $$32 = Mth.sin(($$30 - 0.1F) * 1.3F);
/* 589 */               float $$33 = $$31 - 0.1F;
/* 590 */               float $$34 = $$32 * $$33;
/* 591 */               $$7.translate($$34 * 0.0F, $$34 * 0.004F, $$34 * 0.0F);
/*     */             } 
/* 593 */             $$7.translate(0.0F, 0.0F, $$31 * 0.2F);
/*     */             
/* 595 */             $$7.scale(1.0F, 1.0F, 1.0F + $$31 * 0.2F);
/* 596 */             $$7.mulPose(Axis.YN.rotationDegrees($$24 * 45.0F));
/*     */             break;
/*     */           
/*     */           case BRUSH:
/* 600 */             applyBrushTransform($$7, $$1, $$11, $$5, $$6);
/*     */             break;
/*     */         } 
/*     */       
/* 604 */       } else if ($$0.isAutoSpinAttack()) {
/* 605 */         applyItemArmTransform($$7, $$11, $$6);
/* 606 */         int $$35 = $$23 ? 1 : -1;
/* 607 */         $$7.translate($$35 * -0.4F, 0.8F, 0.3F);
/* 608 */         $$7.mulPose(Axis.YP.rotationDegrees($$35 * 65.0F));
/* 609 */         $$7.mulPose(Axis.ZP.rotationDegrees($$35 * -85.0F));
/*     */       } else {
/* 611 */         float $$36 = -0.4F * Mth.sin(Mth.sqrt($$4) * 3.1415927F);
/* 612 */         float $$37 = 0.2F * Mth.sin(Mth.sqrt($$4) * 6.2831855F);
/* 613 */         float $$38 = -0.2F * Mth.sin($$4 * 3.1415927F);
/* 614 */         int $$39 = $$23 ? 1 : -1;
/* 615 */         $$7.translate($$39 * $$36, $$37, $$38);
/* 616 */         applyItemArmTransform($$7, $$11, $$6);
/* 617 */         applyItemArmAttackTransform($$7, $$11, $$4);
/*     */       } 
/*     */       
/* 620 */       renderItem((LivingEntity)$$0, $$5, $$23 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !$$23, $$7, $$8, $$9);
/*     */     } 
/*     */     
/* 623 */     $$7.popPose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 628 */     this.oMainHandHeight = this.mainHandHeight;
/* 629 */     this.oOffHandHeight = this.offHandHeight;
/*     */     
/* 631 */     LocalPlayer $$0 = this.minecraft.player;
/* 632 */     ItemStack $$1 = $$0.getMainHandItem();
/* 633 */     ItemStack $$2 = $$0.getOffhandItem();
/*     */     
/* 635 */     if (ItemStack.matches(this.mainHandItem, $$1)) {
/* 636 */       this.mainHandItem = $$1;
/*     */     }
/* 638 */     if (ItemStack.matches(this.offHandItem, $$2)) {
/* 639 */       this.offHandItem = $$2;
/*     */     }
/*     */     
/* 642 */     if ($$0.isHandsBusy()) {
/* 643 */       this.mainHandHeight = Mth.clamp(this.mainHandHeight - 0.4F, 0.0F, 1.0F);
/* 644 */       this.offHandHeight = Mth.clamp(this.offHandHeight - 0.4F, 0.0F, 1.0F);
/*     */     } else {
/* 646 */       float $$3 = $$0.getAttackStrengthScale(1.0F);
/* 647 */       this.mainHandHeight += Mth.clamp(((this.mainHandItem == $$1) ? ($$3 * $$3 * $$3) : 0.0F) - this.mainHandHeight, -0.4F, 0.4F);
/* 648 */       this.offHandHeight += Mth.clamp(((this.offHandItem == $$2) ? true : false) - this.offHandHeight, -0.4F, 0.4F);
/*     */     } 
/*     */     
/* 651 */     if (this.mainHandHeight < 0.1F) {
/* 652 */       this.mainHandItem = $$1;
/*     */     }
/*     */     
/* 655 */     if (this.offHandHeight < 0.1F) {
/* 656 */       this.offHandItem = $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   public void itemUsed(InteractionHand $$0) {
/* 661 */     if ($$0 == InteractionHand.MAIN_HAND) {
/* 662 */       this.mainHandHeight = 0.0F;
/*     */     } else {
/* 664 */       this.offHandHeight = 0.0F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\ItemInHandRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */