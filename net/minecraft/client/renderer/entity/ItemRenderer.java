/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.blaze3d.vertex.VertexMultiConsumer;
/*     */ import com.mojang.math.MatrixUtil;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.color.item.ItemColors;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
/*     */ import net.minecraft.client.renderer.ItemBlockRenderTypes;
/*     */ import net.minecraft.client.renderer.ItemModelShaper;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemDisplayContext;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ 
/*     */ public class ItemRenderer
/*     */   implements ResourceManagerReloadListener
/*     */ {
/*  46 */   public static final ResourceLocation ENCHANTED_GLINT_ENTITY = new ResourceLocation("textures/misc/enchanted_glint_entity.png");
/*  47 */   public static final ResourceLocation ENCHANTED_GLINT_ITEM = new ResourceLocation("textures/misc/enchanted_glint_item.png");
/*     */   
/*  49 */   private static final Set<Item> IGNORED = Sets.newHashSet((Object[])new Item[] { Items.AIR });
/*     */ 
/*     */   
/*     */   public static final int GUI_SLOT_CENTER_X = 8;
/*     */   
/*     */   public static final int GUI_SLOT_CENTER_Y = 8;
/*     */   
/*     */   public static final int ITEM_COUNT_BLIT_OFFSET = 200;
/*     */   
/*     */   public static final float COMPASS_FOIL_UI_SCALE = 0.5F;
/*     */   
/*     */   public static final float COMPASS_FOIL_FIRST_PERSON_SCALE = 0.75F;
/*     */   
/*     */   public static final float COMPASS_FOIL_TEXTURE_SCALE = 0.0078125F;
/*     */   
/*  64 */   private static final ModelResourceLocation TRIDENT_MODEL = ModelResourceLocation.vanilla("trident", "inventory");
/*  65 */   public static final ModelResourceLocation TRIDENT_IN_HAND_MODEL = ModelResourceLocation.vanilla("trident_in_hand", "inventory");
/*     */   
/*  67 */   private static final ModelResourceLocation SPYGLASS_MODEL = ModelResourceLocation.vanilla("spyglass", "inventory");
/*  68 */   public static final ModelResourceLocation SPYGLASS_IN_HAND_MODEL = ModelResourceLocation.vanilla("spyglass_in_hand", "inventory");
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   private final ItemModelShaper itemModelShaper;
/*     */   private final TextureManager textureManager;
/*     */   private final ItemColors itemColors;
/*     */   private final BlockEntityWithoutLevelRenderer blockEntityRenderer;
/*     */   
/*     */   public ItemRenderer(Minecraft $$0, TextureManager $$1, ModelManager $$2, ItemColors $$3, BlockEntityWithoutLevelRenderer $$4) {
/*  77 */     this.minecraft = $$0;
/*  78 */     this.textureManager = $$1;
/*  79 */     this.itemModelShaper = new ItemModelShaper($$2);
/*  80 */     this.blockEntityRenderer = $$4;
/*     */     
/*  82 */     for (Item $$5 : BuiltInRegistries.ITEM) {
/*  83 */       if (!IGNORED.contains($$5)) {
/*  84 */         this.itemModelShaper.register($$5, new ModelResourceLocation(BuiltInRegistries.ITEM.getKey($$5), "inventory"));
/*     */       }
/*     */     } 
/*  87 */     this.itemColors = $$3;
/*     */   }
/*     */   
/*     */   public ItemModelShaper getItemModelShaper() {
/*  91 */     return this.itemModelShaper;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderModelLists(BakedModel $$0, ItemStack $$1, int $$2, int $$3, PoseStack $$4, VertexConsumer $$5) {
/*  96 */     RandomSource $$6 = RandomSource.create();
/*  97 */     long $$7 = 42L;
/*  98 */     for (Direction $$8 : Direction.values()) {
/*  99 */       $$6.setSeed(42L);
/* 100 */       renderQuadList($$4, $$5, $$0.getQuads(null, $$8, $$6), $$1, $$2, $$3);
/*     */     } 
/* 102 */     $$6.setSeed(42L);
/* 103 */     renderQuadList($$4, $$5, $$0.getQuads(null, null, $$6), $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void render(ItemStack $$0, ItemDisplayContext $$1, boolean $$2, PoseStack $$3, MultiBufferSource $$4, int $$5, int $$6, BakedModel $$7) {
/* 107 */     if ($$0.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     $$3.pushPose();
/*     */     
/* 113 */     boolean $$8 = ($$1 == ItemDisplayContext.GUI || $$1 == ItemDisplayContext.GROUND || $$1 == ItemDisplayContext.FIXED);
/*     */     
/* 115 */     if ($$8) {
/* 116 */       if ($$0.is(Items.TRIDENT)) {
/* 117 */         $$7 = this.itemModelShaper.getModelManager().getModel(TRIDENT_MODEL);
/* 118 */       } else if ($$0.is(Items.SPYGLASS)) {
/* 119 */         $$7 = this.itemModelShaper.getModelManager().getModel(SPYGLASS_MODEL);
/*     */       } 
/*     */     }
/* 122 */     $$7.getTransforms().getTransform($$1).apply($$2, $$3);
/*     */     
/* 124 */     $$3.translate(-0.5F, -0.5F, -0.5F);
/* 125 */     if ($$7.isCustomRenderer() || ($$0.is(Items.TRIDENT) && !$$8)) {
/* 126 */       this.blockEntityRenderer.renderByItem($$0, $$1, $$3, $$4, $$5, $$6);
/*     */     } else {
/*     */       boolean $$11;
/*     */       
/*     */       VertexConsumer $$17;
/* 131 */       if ($$1 != ItemDisplayContext.GUI && !$$1.firstPerson() && $$0.getItem() instanceof BlockItem) {
/* 132 */         Block $$9 = ((BlockItem)$$0.getItem()).getBlock();
/* 133 */         boolean $$10 = (!($$9 instanceof net.minecraft.world.level.block.HalfTransparentBlock) && !($$9 instanceof net.minecraft.world.level.block.StainedGlassPaneBlock));
/*     */       } else {
/* 135 */         $$11 = true;
/*     */       } 
/*     */       
/* 138 */       RenderType $$12 = ItemBlockRenderTypes.getRenderType($$0, $$11);
/*     */ 
/*     */       
/* 141 */       if (hasAnimatedTexture($$0) && $$0.hasFoil()) {
/*     */         
/* 143 */         $$3.pushPose();
/* 144 */         PoseStack.Pose $$13 = $$3.last();
/* 145 */         if ($$1 == ItemDisplayContext.GUI) {
/* 146 */           MatrixUtil.mulComponentWise($$13.pose(), 0.5F);
/* 147 */         } else if ($$1.firstPerson()) {
/* 148 */           MatrixUtil.mulComponentWise($$13.pose(), 0.75F);
/*     */         } 
/* 150 */         if ($$11) {
/* 151 */           VertexConsumer $$14 = getCompassFoilBufferDirect($$4, $$12, $$13);
/*     */         } else {
/* 153 */           VertexConsumer $$15 = getCompassFoilBuffer($$4, $$12, $$13);
/*     */         } 
/* 155 */         $$3.popPose();
/*     */       }
/* 157 */       else if ($$11) {
/* 158 */         VertexConsumer $$16 = getFoilBufferDirect($$4, $$12, true, $$0.hasFoil());
/*     */       } else {
/* 160 */         $$17 = getFoilBuffer($$4, $$12, true, $$0.hasFoil());
/*     */       } 
/*     */ 
/*     */       
/* 164 */       renderModelLists($$7, $$0, $$5, $$6, $$3, $$17);
/*     */     } 
/* 166 */     $$3.popPose();
/*     */   }
/*     */   
/*     */   private static boolean hasAnimatedTexture(ItemStack $$0) {
/* 170 */     return ($$0.is(ItemTags.COMPASSES) || $$0.is(Items.CLOCK));
/*     */   }
/*     */   
/*     */   public static VertexConsumer getArmorFoilBuffer(MultiBufferSource $$0, RenderType $$1, boolean $$2, boolean $$3) {
/* 174 */     if ($$3) {
/* 175 */       return VertexMultiConsumer.create($$0
/* 176 */           .getBuffer($$2 ? RenderType.armorGlint() : RenderType.armorEntityGlint()), $$0
/* 177 */           .getBuffer($$1));
/*     */     }
/*     */     
/* 180 */     return $$0.getBuffer($$1);
/*     */   }
/*     */   
/*     */   public static VertexConsumer getCompassFoilBuffer(MultiBufferSource $$0, RenderType $$1, PoseStack.Pose $$2) {
/* 184 */     return VertexMultiConsumer.create((VertexConsumer)new SheetedDecalTextureGenerator($$0
/* 185 */           .getBuffer(RenderType.glint()), $$2.pose(), $$2.normal(), 0.0078125F), $$0
/* 186 */         .getBuffer($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public static VertexConsumer getCompassFoilBufferDirect(MultiBufferSource $$0, RenderType $$1, PoseStack.Pose $$2) {
/* 191 */     return VertexMultiConsumer.create((VertexConsumer)new SheetedDecalTextureGenerator($$0
/* 192 */           .getBuffer(RenderType.glintDirect()), $$2.pose(), $$2.normal(), 0.0078125F), $$0
/* 193 */         .getBuffer($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public static VertexConsumer getFoilBuffer(MultiBufferSource $$0, RenderType $$1, boolean $$2, boolean $$3) {
/* 198 */     if ($$3) {
/* 199 */       if (Minecraft.useShaderTransparency() && $$1 == Sheets.translucentItemSheet()) {
/* 200 */         return VertexMultiConsumer.create($$0
/* 201 */             .getBuffer(RenderType.glintTranslucent()), $$0
/* 202 */             .getBuffer($$1));
/*     */       }
/*     */ 
/*     */       
/* 206 */       return VertexMultiConsumer.create($$0
/* 207 */           .getBuffer($$2 ? RenderType.glint() : RenderType.entityGlint()), $$0
/* 208 */           .getBuffer($$1));
/*     */     } 
/*     */     
/* 211 */     return $$0.getBuffer($$1);
/*     */   }
/*     */   
/*     */   public static VertexConsumer getFoilBufferDirect(MultiBufferSource $$0, RenderType $$1, boolean $$2, boolean $$3) {
/* 215 */     if ($$3) {
/* 216 */       return VertexMultiConsumer.create($$0
/* 217 */           .getBuffer($$2 ? RenderType.glintDirect() : RenderType.entityGlintDirect()), $$0
/* 218 */           .getBuffer($$1));
/*     */     }
/*     */     
/* 221 */     return $$0.getBuffer($$1);
/*     */   }
/*     */   
/*     */   private void renderQuadList(PoseStack $$0, VertexConsumer $$1, List<BakedQuad> $$2, ItemStack $$3, int $$4, int $$5) {
/* 225 */     boolean $$6 = !$$3.isEmpty();
/*     */     
/* 227 */     PoseStack.Pose $$7 = $$0.last();
/*     */     
/* 229 */     for (BakedQuad $$8 : $$2) {
/* 230 */       int $$9 = -1;
/* 231 */       if ($$6 && $$8.isTinted()) {
/* 232 */         $$9 = this.itemColors.getColor($$3, $$8.getTintIndex());
/*     */       }
/*     */       
/* 235 */       float $$10 = ($$9 >> 16 & 0xFF) / 255.0F;
/* 236 */       float $$11 = ($$9 >> 8 & 0xFF) / 255.0F;
/* 237 */       float $$12 = ($$9 & 0xFF) / 255.0F;
/* 238 */       $$1.putBulkData($$7, $$8, $$10, $$11, $$12, $$4, $$5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedModel getModel(ItemStack $$0, @Nullable Level $$1, @Nullable LivingEntity $$2, int $$3) {
/*     */     BakedModel $$6;
/* 245 */     if ($$0.is(Items.TRIDENT)) {
/* 246 */       BakedModel $$4 = this.itemModelShaper.getModelManager().getModel(TRIDENT_IN_HAND_MODEL);
/* 247 */     } else if ($$0.is(Items.SPYGLASS)) {
/* 248 */       BakedModel $$5 = this.itemModelShaper.getModelManager().getModel(SPYGLASS_IN_HAND_MODEL);
/*     */     } else {
/* 250 */       $$6 = this.itemModelShaper.getItemModel($$0);
/*     */     } 
/*     */ 
/*     */     
/* 254 */     ClientLevel $$7 = ($$1 instanceof ClientLevel) ? (ClientLevel)$$1 : null;
/*     */     
/* 256 */     BakedModel $$8 = $$6.getOverrides().resolve($$6, $$0, $$7, $$2, $$3);
/* 257 */     return ($$8 == null) ? this.itemModelShaper.getModelManager().getMissingModel() : $$8;
/*     */   }
/*     */   
/*     */   public void renderStatic(ItemStack $$0, ItemDisplayContext $$1, int $$2, int $$3, PoseStack $$4, MultiBufferSource $$5, @Nullable Level $$6, int $$7) {
/* 261 */     renderStatic(null, $$0, $$1, false, $$4, $$5, $$6, $$2, $$3, $$7);
/*     */   }
/*     */   
/*     */   public void renderStatic(@Nullable LivingEntity $$0, ItemStack $$1, ItemDisplayContext $$2, boolean $$3, PoseStack $$4, MultiBufferSource $$5, @Nullable Level $$6, int $$7, int $$8, int $$9) {
/* 265 */     if ($$1.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 269 */     BakedModel $$10 = getModel($$1, $$6, $$0, $$9);
/*     */     
/* 271 */     render($$1, $$2, $$3, $$4, $$5, $$7, $$8, $$10);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(ResourceManager $$0) {
/* 276 */     this.itemModelShaper.rebuildCache();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ItemRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */