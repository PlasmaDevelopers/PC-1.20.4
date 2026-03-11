/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.OptionalInt;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.decoration.ItemFrame;
/*     */ import net.minecraft.world.item.ItemDisplayContext;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.MapItem;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ItemFrameRenderer<T extends ItemFrame>
/*     */   extends EntityRenderer<T> {
/*  30 */   private static final ModelResourceLocation FRAME_LOCATION = ModelResourceLocation.vanilla("item_frame", "map=false");
/*  31 */   private static final ModelResourceLocation MAP_FRAME_LOCATION = ModelResourceLocation.vanilla("item_frame", "map=true");
/*     */   
/*  33 */   private static final ModelResourceLocation GLOW_FRAME_LOCATION = ModelResourceLocation.vanilla("glow_item_frame", "map=false");
/*  34 */   private static final ModelResourceLocation GLOW_MAP_FRAME_LOCATION = ModelResourceLocation.vanilla("glow_item_frame", "map=true");
/*     */   
/*     */   public static final int GLOW_FRAME_BRIGHTNESS = 5;
/*     */   
/*     */   public static final int BRIGHT_MAP_LIGHT_ADJUSTMENT = 30;
/*     */   private final ItemRenderer itemRenderer;
/*     */   private final BlockRenderDispatcher blockRenderer;
/*     */   
/*     */   public ItemFrameRenderer(EntityRendererProvider.Context $$0) {
/*  43 */     super($$0);
/*  44 */     this.itemRenderer = $$0.getItemRenderer();
/*  45 */     this.blockRenderer = $$0.getBlockRenderDispatcher();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getBlockLightLevel(T $$0, BlockPos $$1) {
/*  50 */     if ($$0.getType() == EntityType.GLOW_ITEM_FRAME) {
/*  51 */       return Math.max(5, super.getBlockLightLevel($$0, $$1));
/*     */     }
/*  53 */     return super.getBlockLightLevel($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(T $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  58 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*  59 */     $$3.pushPose();
/*     */     
/*  61 */     Direction $$6 = $$0.getDirection();
/*     */     
/*  63 */     Vec3 $$7 = getRenderOffset($$0, $$2);
/*     */     
/*  65 */     $$3.translate(-$$7.x(), -$$7.y(), -$$7.z());
/*     */     
/*  67 */     double $$8 = 0.46875D;
/*  68 */     $$3.translate($$6.getStepX() * 0.46875D, $$6.getStepY() * 0.46875D, $$6.getStepZ() * 0.46875D);
/*     */     
/*  70 */     $$3.mulPose(Axis.XP.rotationDegrees($$0.getXRot()));
/*  71 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F - $$0.getYRot()));
/*     */     
/*  73 */     boolean $$9 = $$0.isInvisible();
/*  74 */     ItemStack $$10 = $$0.getItem();
/*  75 */     if (!$$9) {
/*  76 */       ModelManager $$11 = this.blockRenderer.getBlockModelShaper().getModelManager();
/*  77 */       ModelResourceLocation $$12 = getFrameModelResourceLoc($$0, $$10);
/*     */       
/*  79 */       $$3.pushPose();
/*  80 */       $$3.translate(-0.5F, -0.5F, -0.5F);
/*  81 */       this.blockRenderer.getModelRenderer().renderModel($$3.last(), $$4.getBuffer(Sheets.solidBlockSheet()), null, $$11.getModel($$12), 1.0F, 1.0F, 1.0F, $$5, OverlayTexture.NO_OVERLAY);
/*  82 */       $$3.popPose();
/*     */     } 
/*     */     
/*  85 */     if (!$$10.isEmpty()) {
/*  86 */       OptionalInt $$13 = $$0.getFramedMapId();
/*     */       
/*  88 */       if ($$9) {
/*  89 */         $$3.translate(0.0F, 0.0F, 0.5F);
/*     */       } else {
/*  91 */         $$3.translate(0.0F, 0.0F, 0.4375F);
/*     */       } 
/*     */       
/*  94 */       int $$14 = $$13.isPresent() ? ($$0.getRotation() % 4 * 2) : $$0.getRotation();
/*  95 */       $$3.mulPose(Axis.ZP.rotationDegrees($$14 * 360.0F / 8.0F));
/*     */       
/*  97 */       if ($$13.isPresent()) {
/*  98 */         $$3.mulPose(Axis.ZP.rotationDegrees(180.0F));
/*  99 */         float $$15 = 0.0078125F;
/* 100 */         $$3.scale(0.0078125F, 0.0078125F, 0.0078125F);
/* 101 */         $$3.translate(-64.0F, -64.0F, 0.0F);
/*     */         
/* 103 */         MapItemSavedData $$16 = MapItem.getSavedData(Integer.valueOf($$13.getAsInt()), $$0.level());
/* 104 */         $$3.translate(0.0F, 0.0F, -1.0F);
/* 105 */         if ($$16 != null) {
/* 106 */           int $$17 = getLightVal($$0, 15728850, $$5);
/* 107 */           (Minecraft.getInstance()).gameRenderer.getMapRenderer().render($$3, $$4, $$13.getAsInt(), $$16, true, $$17);
/*     */         } 
/*     */       } else {
/* 110 */         int $$18 = getLightVal($$0, 15728880, $$5);
/*     */         
/* 112 */         $$3.scale(0.5F, 0.5F, 0.5F);
/* 113 */         this.itemRenderer.renderStatic($$10, ItemDisplayContext.FIXED, $$18, OverlayTexture.NO_OVERLAY, $$3, $$4, $$0.level(), $$0.getId());
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     $$3.popPose();
/*     */   }
/*     */   
/*     */   private int getLightVal(T $$0, int $$1, int $$2) {
/* 121 */     return ($$0.getType() == EntityType.GLOW_ITEM_FRAME) ? $$1 : $$2;
/*     */   }
/*     */   
/*     */   private ModelResourceLocation getFrameModelResourceLoc(T $$0, ItemStack $$1) {
/* 125 */     boolean $$2 = ($$0.getType() == EntityType.GLOW_ITEM_FRAME);
/* 126 */     if ($$1.is(Items.FILLED_MAP)) {
/* 127 */       return $$2 ? GLOW_MAP_FRAME_LOCATION : MAP_FRAME_LOCATION;
/*     */     }
/* 129 */     return $$2 ? GLOW_FRAME_LOCATION : FRAME_LOCATION;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getRenderOffset(T $$0, float $$1) {
/* 134 */     return new Vec3(($$0.getDirection().getStepX() * 0.3F), -0.25D, ($$0.getDirection().getStepZ() * 0.3F));
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(T $$0) {
/* 139 */     return TextureAtlas.LOCATION_BLOCKS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldShowName(T $$0) {
/* 144 */     if (!Minecraft.renderNames() || $$0.getItem().isEmpty() || !$$0.getItem().hasCustomHoverName() || this.entityRenderDispatcher.crosshairPickEntity != $$0) {
/* 145 */       return false;
/*     */     }
/*     */     
/* 148 */     double $$1 = this.entityRenderDispatcher.distanceToSqr((Entity)$$0);
/* 149 */     float $$2 = $$0.isDiscrete() ? 32.0F : 64.0F;
/*     */     
/* 151 */     return ($$1 < ($$2 * $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderNameTag(T $$0, Component $$1, PoseStack $$2, MultiBufferSource $$3, int $$4) {
/* 156 */     super.renderNameTag($$0, $$0.getItem().getHoverName(), $$2, $$3, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ItemFrameRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */