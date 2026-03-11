/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Axis;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemDisplayContext;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class ItemEntityRenderer extends EntityRenderer<ItemEntity> {
/*     */   private static final float ITEM_BUNDLE_OFFSET_SCALE = 0.15F;
/*     */   private static final int ITEM_COUNT_FOR_5_BUNDLE = 48;
/*     */   private static final int ITEM_COUNT_FOR_4_BUNDLE = 32;
/*     */   private static final int ITEM_COUNT_FOR_3_BUNDLE = 16;
/*     */   private static final int ITEM_COUNT_FOR_2_BUNDLE = 1;
/*     */   private static final float FLAT_ITEM_BUNDLE_OFFSET_X = 0.0F;
/*     */   private static final float FLAT_ITEM_BUNDLE_OFFSET_Y = 0.0F;
/*     */   private static final float FLAT_ITEM_BUNDLE_OFFSET_Z = 0.09375F;
/*     */   private final ItemRenderer itemRenderer;
/*  28 */   private final RandomSource random = RandomSource.create();
/*     */   
/*     */   public ItemEntityRenderer(EntityRendererProvider.Context $$0) {
/*  31 */     super($$0);
/*  32 */     this.itemRenderer = $$0.getItemRenderer();
/*     */     
/*  34 */     this.shadowRadius = 0.15F;
/*  35 */     this.shadowStrength = 0.75F;
/*     */   }
/*     */   
/*     */   private int getRenderAmount(ItemStack $$0) {
/*  39 */     int $$1 = 1;
/*  40 */     if ($$0.getCount() > 48) {
/*  41 */       $$1 = 5;
/*  42 */     } else if ($$0.getCount() > 32) {
/*  43 */       $$1 = 4;
/*  44 */     } else if ($$0.getCount() > 16) {
/*  45 */       $$1 = 3;
/*  46 */     } else if ($$0.getCount() > 1) {
/*  47 */       $$1 = 2;
/*     */     } 
/*     */     
/*  50 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(ItemEntity $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  55 */     $$3.pushPose();
/*     */     
/*  57 */     ItemStack $$6 = $$0.getItem();
/*     */     
/*  59 */     int $$7 = $$6.isEmpty() ? 187 : (Item.getId($$6.getItem()) + $$6.getDamageValue());
/*  60 */     this.random.setSeed($$7);
/*     */     
/*  62 */     BakedModel $$8 = this.itemRenderer.getModel($$6, $$0.level(), null, $$0.getId());
/*     */     
/*  64 */     boolean $$9 = $$8.isGui3d();
/*  65 */     int $$10 = getRenderAmount($$6);
/*     */ 
/*     */     
/*  68 */     float $$11 = 0.25F;
/*  69 */     float $$12 = Mth.sin(($$0.getAge() + $$2) / 10.0F + $$0.bobOffs) * 0.1F + 0.1F;
/*  70 */     float $$13 = ($$8.getTransforms().getTransform(ItemDisplayContext.GROUND)).scale.y();
/*  71 */     $$3.translate(0.0F, $$12 + 0.25F * $$13, 0.0F);
/*     */ 
/*     */     
/*  74 */     float $$14 = $$0.getSpin($$2);
/*  75 */     $$3.mulPose(Axis.YP.rotation($$14));
/*     */     
/*  77 */     float $$15 = ($$8.getTransforms()).ground.scale.x();
/*  78 */     float $$16 = ($$8.getTransforms()).ground.scale.y();
/*  79 */     float $$17 = ($$8.getTransforms()).ground.scale.z();
/*     */     
/*  81 */     if (!$$9) {
/*  82 */       float $$18 = -0.0F * ($$10 - 1) * 0.5F * $$15;
/*  83 */       float $$19 = -0.0F * ($$10 - 1) * 0.5F * $$16;
/*  84 */       float $$20 = -0.09375F * ($$10 - 1) * 0.5F * $$17;
/*  85 */       $$3.translate($$18, $$19, $$20);
/*     */     } 
/*  87 */     for (int $$21 = 0; $$21 < $$10; $$21++) {
/*  88 */       $$3.pushPose();
/*  89 */       if ($$21 > 0) {
/*  90 */         if ($$9) {
/*  91 */           float $$22 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/*  92 */           float $$23 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/*  93 */           float $$24 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/*  94 */           $$3.translate($$22, $$23, $$24);
/*     */         } else {
/*  96 */           float $$25 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
/*  97 */           float $$26 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
/*  98 */           $$3.translate($$25, $$26, 0.0F);
/*     */         } 
/*     */       }
/* 101 */       this.itemRenderer.render($$6, ItemDisplayContext.GROUND, false, $$3, $$4, $$5, OverlayTexture.NO_OVERLAY, $$8);
/*     */       
/* 103 */       $$3.popPose();
/* 104 */       if (!$$9) {
/* 105 */         $$3.translate(0.0F * $$15, 0.0F * $$16, 0.09375F * $$17);
/*     */       }
/*     */     } 
/* 108 */     $$3.popPose();
/*     */     
/* 110 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ItemEntity $$0) {
/* 115 */     return TextureAtlas.LOCATION_BLOCKS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ItemEntityRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */