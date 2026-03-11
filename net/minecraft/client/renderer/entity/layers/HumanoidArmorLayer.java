/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.model.HumanoidModel;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.ArmorItem;
/*     */ import net.minecraft.world.item.ArmorMaterial;
/*     */ import net.minecraft.world.item.DyeableArmorItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.armortrim.ArmorTrim;
/*     */ import net.minecraft.world.item.armortrim.TrimPattern;
/*     */ 
/*     */ public class HumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
/*  28 */   private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
/*     */   private final A innerModel;
/*     */   private final A outerModel;
/*     */   private final TextureAtlas armorTrimAtlas;
/*     */   
/*     */   public HumanoidArmorLayer(RenderLayerParent<T, M> $$0, A $$1, A $$2, ModelManager $$3) {
/*  34 */     super($$0);
/*  35 */     this.innerModel = $$1;
/*  36 */     this.outerModel = $$2;
/*  37 */     this.armorTrimAtlas = $$3.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/*  42 */     renderArmorPiece($$0, $$1, $$3, EquipmentSlot.CHEST, $$2, getArmorModel(EquipmentSlot.CHEST));
/*  43 */     renderArmorPiece($$0, $$1, $$3, EquipmentSlot.LEGS, $$2, getArmorModel(EquipmentSlot.LEGS));
/*  44 */     renderArmorPiece($$0, $$1, $$3, EquipmentSlot.FEET, $$2, getArmorModel(EquipmentSlot.FEET));
/*  45 */     renderArmorPiece($$0, $$1, $$3, EquipmentSlot.HEAD, $$2, getArmorModel(EquipmentSlot.HEAD));
/*     */   }
/*     */   private void renderArmorPiece(PoseStack $$0, MultiBufferSource $$1, T $$2, EquipmentSlot $$3, int $$4, A $$5) {
/*     */     ArmorItem $$7;
/*  49 */     ItemStack $$6 = $$2.getItemBySlot($$3);
/*  50 */     Item item = $$6.getItem(); if (item instanceof ArmorItem) { $$7 = (ArmorItem)item; }
/*     */     else
/*     */     { return; }
/*     */     
/*  54 */     if ($$7.getEquipmentSlot() != $$3) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     ((HumanoidModel)getParentModel()).copyPropertiesTo((HumanoidModel)$$5);
/*     */     
/*  60 */     setPartVisibility($$5, $$3);
/*     */     
/*  62 */     boolean $$9 = usesInnerModel($$3);
/*     */     
/*  64 */     if ($$7 instanceof DyeableArmorItem) { DyeableArmorItem $$10 = (DyeableArmorItem)$$7;
/*  65 */       int $$11 = $$10.getColor($$6);
/*  66 */       float $$12 = ($$11 >> 16 & 0xFF) / 255.0F;
/*  67 */       float $$13 = ($$11 >> 8 & 0xFF) / 255.0F;
/*  68 */       float $$14 = ($$11 & 0xFF) / 255.0F;
/*     */       
/*  70 */       renderModel($$0, $$1, $$4, $$7, $$5, $$9, $$12, $$13, $$14, null);
/*     */ 
/*     */       
/*  73 */       renderModel($$0, $$1, $$4, $$7, $$5, $$9, 1.0F, 1.0F, 1.0F, "overlay"); }
/*     */     else
/*  75 */     { renderModel($$0, $$1, $$4, $$7, $$5, $$9, 1.0F, 1.0F, 1.0F, null); }
/*     */ 
/*     */     
/*  78 */     ArmorTrim.getTrim($$2.level().registryAccess(), $$6, true).ifPresent($$6 -> renderTrim($$0.getMaterial(), $$1, $$2, $$3, $$6, (A)$$4, $$5));
/*     */ 
/*     */ 
/*     */     
/*  82 */     if ($$6.hasFoil()) {
/*  83 */       renderGlint($$0, $$1, $$4, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setPartVisibility(A $$0, EquipmentSlot $$1) {
/*  88 */     $$0.setAllVisible(false);
/*     */ 
/*     */     
/*  91 */     switch ($$1) {
/*     */       case HEAD:
/*  93 */         ((HumanoidModel)$$0).head.visible = true;
/*  94 */         ((HumanoidModel)$$0).hat.visible = true;
/*     */         break;
/*     */       
/*     */       case CHEST:
/*  98 */         ((HumanoidModel)$$0).body.visible = true;
/*  99 */         ((HumanoidModel)$$0).rightArm.visible = true;
/* 100 */         ((HumanoidModel)$$0).leftArm.visible = true;
/*     */         break;
/*     */       
/*     */       case LEGS:
/* 104 */         ((HumanoidModel)$$0).body.visible = true;
/* 105 */         ((HumanoidModel)$$0).rightLeg.visible = true;
/* 106 */         ((HumanoidModel)$$0).leftLeg.visible = true;
/*     */         break;
/*     */       
/*     */       case FEET:
/* 110 */         ((HumanoidModel)$$0).rightLeg.visible = true;
/* 111 */         ((HumanoidModel)$$0).leftLeg.visible = true;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderModel(PoseStack $$0, MultiBufferSource $$1, int $$2, ArmorItem $$3, A $$4, boolean $$5, float $$6, float $$7, float $$8, @Nullable String $$9) {
/* 118 */     VertexConsumer $$10 = $$1.getBuffer(RenderType.armorCutoutNoCull(getArmorLocation($$3, $$5, $$9)));
/* 119 */     $$4.renderToBuffer($$0, $$10, $$2, OverlayTexture.NO_OVERLAY, $$6, $$7, $$8, 1.0F);
/*     */   }
/*     */   
/*     */   private void renderTrim(ArmorMaterial $$0, PoseStack $$1, MultiBufferSource $$2, int $$3, ArmorTrim $$4, A $$5, boolean $$6) {
/* 123 */     TextureAtlasSprite $$7 = this.armorTrimAtlas.getSprite($$6 ? $$4.innerTexture($$0) : $$4.outerTexture($$0));
/* 124 */     VertexConsumer $$8 = $$7.wrap($$2.getBuffer(Sheets.armorTrimsSheet(((TrimPattern)$$4.pattern().value()).decal())));
/* 125 */     $$5.renderToBuffer($$1, $$8, $$3, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   private void renderGlint(PoseStack $$0, MultiBufferSource $$1, int $$2, A $$3) {
/* 129 */     $$3.renderToBuffer($$0, $$1.getBuffer(RenderType.armorEntityGlint()), $$2, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   private A getArmorModel(EquipmentSlot $$0) {
/* 133 */     return usesInnerModel($$0) ? this.innerModel : this.outerModel;
/*     */   }
/*     */   
/*     */   private boolean usesInnerModel(EquipmentSlot $$0) {
/* 137 */     return ($$0 == EquipmentSlot.LEGS);
/*     */   }
/*     */   
/*     */   private ResourceLocation getArmorLocation(ArmorItem $$0, boolean $$1, @Nullable String $$2) {
/* 141 */     String $$3 = "textures/models/armor/" + $$0.getMaterial().getName() + "_layer_" + ($$1 ? 2 : 1) + (($$2 == null) ? "" : ("_" + $$2)) + ".png";
/* 142 */     return ARMOR_LOCATION_CACHE.computeIfAbsent($$3, ResourceLocation::new);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\HumanoidArmorLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */