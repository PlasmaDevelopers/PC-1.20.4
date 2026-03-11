/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ShieldModel;
/*     */ import net.minecraft.client.model.SkullModelBase;
/*     */ import net.minecraft.client.model.TridentModel;
/*     */ import net.minecraft.client.model.geom.EntityModelSet;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.renderer.blockentity.BannerRenderer;
/*     */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
/*     */ import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
/*     */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemDisplayContext;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.ShieldItem;
/*     */ import net.minecraft.world.level.block.AbstractBannerBlock;
/*     */ import net.minecraft.world.level.block.AbstractSkullBlock;
/*     */ import net.minecraft.world.level.block.BedBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ShulkerBoxBlock;
/*     */ import net.minecraft.world.level.block.SkullBlock;
/*     */ import net.minecraft.world.level.block.entity.BannerBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BannerPattern;
/*     */ import net.minecraft.world.level.block.entity.BedBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ConduitBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
/*     */   static {
/*  56 */     SHULKER_BOXES = (ShulkerBoxBlockEntity[])Arrays.<DyeColor>stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map($$0 -> new ShulkerBoxBlockEntity($$0, BlockPos.ZERO, Blocks.SHULKER_BOX.defaultBlockState())).toArray($$0 -> new ShulkerBoxBlockEntity[$$0]);
/*  57 */   } private static final ShulkerBoxBlockEntity DEFAULT_SHULKER_BOX = new ShulkerBoxBlockEntity(BlockPos.ZERO, Blocks.SHULKER_BOX.defaultBlockState());
/*     */   private static final ShulkerBoxBlockEntity[] SHULKER_BOXES;
/*  59 */   private final ChestBlockEntity chest = new ChestBlockEntity(BlockPos.ZERO, Blocks.CHEST.defaultBlockState());
/*  60 */   private final ChestBlockEntity trappedChest = (ChestBlockEntity)new TrappedChestBlockEntity(BlockPos.ZERO, Blocks.TRAPPED_CHEST.defaultBlockState());
/*  61 */   private final EnderChestBlockEntity enderChest = new EnderChestBlockEntity(BlockPos.ZERO, Blocks.ENDER_CHEST.defaultBlockState());
/*  62 */   private final BannerBlockEntity banner = new BannerBlockEntity(BlockPos.ZERO, Blocks.WHITE_BANNER.defaultBlockState());
/*  63 */   private final BedBlockEntity bed = new BedBlockEntity(BlockPos.ZERO, Blocks.RED_BED.defaultBlockState());
/*  64 */   private final ConduitBlockEntity conduit = new ConduitBlockEntity(BlockPos.ZERO, Blocks.CONDUIT.defaultBlockState());
/*  65 */   private final DecoratedPotBlockEntity decoratedPot = new DecoratedPotBlockEntity(BlockPos.ZERO, Blocks.DECORATED_POT.defaultBlockState());
/*     */   
/*     */   private ShieldModel shieldModel;
/*     */   
/*     */   private TridentModel tridentModel;
/*     */   private Map<SkullBlock.Type, SkullModelBase> skullModels;
/*     */   private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
/*     */   private final EntityModelSet entityModelSet;
/*     */   
/*     */   public BlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher $$0, EntityModelSet $$1) {
/*  75 */     this.blockEntityRenderDispatcher = $$0;
/*  76 */     this.entityModelSet = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(ResourceManager $$0) {
/*  81 */     this.shieldModel = new ShieldModel(this.entityModelSet.bakeLayer(ModelLayers.SHIELD));
/*  82 */     this.tridentModel = new TridentModel(this.entityModelSet.bakeLayer(ModelLayers.TRIDENT));
/*  83 */     this.skullModels = SkullBlockRenderer.createSkullRenderers(this.entityModelSet);
/*     */   }
/*     */   
/*     */   public void renderByItem(ItemStack $$0, ItemDisplayContext $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*  87 */     Item $$6 = $$0.getItem();
/*     */     
/*  89 */     if ($$6 instanceof BlockItem) {
/*  90 */       ShulkerBoxBlockEntity shulkerBoxBlockEntity; Block $$7 = ((BlockItem)$$6).getBlock();
/*  91 */       if ($$7 instanceof AbstractSkullBlock) { AbstractSkullBlock $$8 = (AbstractSkullBlock)$$7;
/*  92 */         CompoundTag $$9 = $$0.getTag();
/*  93 */         GameProfile $$10 = ($$9 != null) ? SkullBlockEntity.getOrResolveGameProfile($$9) : null;
/*     */         
/*  95 */         SkullModelBase $$11 = this.skullModels.get($$8.getType());
/*  96 */         RenderType $$12 = SkullBlockRenderer.getRenderType($$8.getType(), $$10);
/*  97 */         SkullBlockRenderer.renderSkull(null, 180.0F, 0.0F, $$2, $$3, $$4, $$11, $$12);
/*     */         
/*     */         return; }
/*     */       
/* 101 */       BlockState $$13 = $$7.defaultBlockState();
/* 102 */       if ($$7 instanceof AbstractBannerBlock) {
/* 103 */         this.banner.fromItem($$0, ((AbstractBannerBlock)$$7).getColor());
/* 104 */         BannerBlockEntity bannerBlockEntity = this.banner;
/* 105 */       } else if ($$7 instanceof BedBlock) {
/* 106 */         this.bed.setColor(((BedBlock)$$7).getColor());
/* 107 */         BedBlockEntity bedBlockEntity = this.bed;
/* 108 */       } else if ($$13.is(Blocks.CONDUIT)) {
/* 109 */         ConduitBlockEntity conduitBlockEntity = this.conduit;
/* 110 */       } else if ($$13.is(Blocks.CHEST)) {
/* 111 */         ChestBlockEntity chestBlockEntity = this.chest;
/* 112 */       } else if ($$13.is(Blocks.ENDER_CHEST)) {
/* 113 */         EnderChestBlockEntity enderChestBlockEntity = this.enderChest;
/* 114 */       } else if ($$13.is(Blocks.TRAPPED_CHEST)) {
/* 115 */         ChestBlockEntity chestBlockEntity = this.trappedChest;
/* 116 */       } else if ($$13.is(Blocks.DECORATED_POT)) {
/* 117 */         this.decoratedPot.setFromItem($$0);
/* 118 */         DecoratedPotBlockEntity decoratedPotBlockEntity = this.decoratedPot;
/* 119 */       } else if ($$7 instanceof ShulkerBoxBlock) {
/* 120 */         DyeColor $$21 = ShulkerBoxBlock.getColorFromItem($$6);
/* 121 */         if ($$21 == null) {
/* 122 */           shulkerBoxBlockEntity = DEFAULT_SHULKER_BOX;
/*     */         } else {
/* 124 */           shulkerBoxBlockEntity = SHULKER_BOXES[$$21.getId()];
/*     */         } 
/*     */       } else {
/*     */         return;
/*     */       } 
/* 129 */       this.blockEntityRenderDispatcher.renderItem((BlockEntity)shulkerBoxBlockEntity, $$2, $$3, $$4, $$5);
/*     */       
/*     */       return;
/*     */     } 
/* 133 */     if ($$0.is(Items.SHIELD)) {
/* 134 */       boolean $$25 = (BlockItem.getBlockEntityData($$0) != null);
/*     */       
/* 136 */       $$2.pushPose();
/* 137 */       $$2.scale(1.0F, -1.0F, -1.0F);
/*     */       
/* 139 */       Material $$26 = $$25 ? ModelBakery.SHIELD_BASE : ModelBakery.NO_PATTERN_SHIELD;
/*     */       
/* 141 */       VertexConsumer $$27 = $$26.sprite().wrap(ItemRenderer.getFoilBufferDirect($$3, this.shieldModel.renderType($$26.atlasLocation()), true, $$0.hasFoil()));
/* 142 */       this.shieldModel.handle().render($$2, $$27, $$4, $$5, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 144 */       if ($$25) {
/* 145 */         List<Pair<Holder<BannerPattern>, DyeColor>> $$28 = BannerBlockEntity.createPatterns(ShieldItem.getColor($$0), BannerBlockEntity.getItemPatterns($$0));
/* 146 */         BannerRenderer.renderPatterns($$2, $$3, $$4, $$5, this.shieldModel.plate(), $$26, false, $$28, $$0.hasFoil());
/*     */       } else {
/* 148 */         this.shieldModel.plate().render($$2, $$27, $$4, $$5, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 151 */       $$2.popPose();
/* 152 */     } else if ($$0.is(Items.TRIDENT)) {
/* 153 */       $$2.pushPose();
/* 154 */       $$2.scale(1.0F, -1.0F, -1.0F);
/*     */       
/* 156 */       VertexConsumer $$29 = ItemRenderer.getFoilBufferDirect($$3, this.tridentModel.renderType(TridentModel.TEXTURE), false, $$0.hasFoil());
/* 157 */       this.tridentModel.renderToBuffer($$2, $$29, $$4, $$5, 1.0F, 1.0F, 1.0F, 1.0F);
/* 158 */       $$2.popPose();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\BlockEntityWithoutLevelRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */