/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.level.block.BannerBlock;
/*     */ import net.minecraft.world.level.block.WallBannerBlock;
/*     */ import net.minecraft.world.level.block.entity.BannerBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BannerPattern;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RotationSegment;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BannerRenderer
/*     */   implements BlockEntityRenderer<BannerBlockEntity>
/*     */ {
/*     */   private static final int BANNER_WIDTH = 20;
/*     */   private static final int BANNER_HEIGHT = 40;
/*     */   private static final int MAX_PATTERNS = 16;
/*     */   public static final String FLAG = "flag";
/*     */   
/*     */   public BannerRenderer(BlockEntityRendererProvider.Context $$0) {
/*  46 */     ModelPart $$1 = $$0.bakeLayer(ModelLayers.BANNER);
/*  47 */     this.flag = $$1.getChild("flag");
/*  48 */     this.pole = $$1.getChild("pole");
/*  49 */     this.bar = $$1.getChild("bar");
/*     */   }
/*     */   private static final String POLE = "pole"; private static final String BAR = "bar"; private final ModelPart flag; private final ModelPart pole; private final ModelPart bar;
/*     */   public static LayerDefinition createBodyLayer() {
/*  53 */     MeshDefinition $$0 = new MeshDefinition();
/*  54 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  56 */     $$1.addOrReplaceChild("flag", 
/*  57 */         CubeListBuilder.create()
/*  58 */         .texOffs(0, 0).addBox(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  61 */     $$1.addOrReplaceChild("pole", 
/*  62 */         CubeListBuilder.create()
/*  63 */         .texOffs(44, 0).addBox(-1.0F, -30.0F, -1.0F, 2.0F, 42.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  66 */     $$1.addOrReplaceChild("bar", 
/*  67 */         CubeListBuilder.create()
/*  68 */         .texOffs(0, 42).addBox(-10.0F, -32.0F, -1.0F, 20.0F, 2.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  71 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */   
/*     */   public void render(BannerBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*     */     long $$10;
/*  76 */     List<Pair<Holder<BannerPattern>, DyeColor>> $$6 = $$0.getPatterns();
/*  77 */     float $$7 = 0.6666667F;
/*  78 */     boolean $$8 = ($$0.getLevel() == null);
/*     */     
/*  80 */     $$2.pushPose();
/*     */ 
/*     */     
/*  83 */     if ($$8) {
/*  84 */       long $$9 = 0L;
/*  85 */       $$2.translate(0.5F, 0.5F, 0.5F);
/*  86 */       this.pole.visible = true;
/*     */     } else {
/*  88 */       $$10 = $$0.getLevel().getGameTime();
/*     */       
/*  90 */       BlockState $$11 = $$0.getBlockState();
/*  91 */       if ($$11.getBlock() instanceof BannerBlock) {
/*  92 */         $$2.translate(0.5F, 0.5F, 0.5F);
/*     */         
/*  94 */         float $$12 = -RotationSegment.convertToDegrees(((Integer)$$11.getValue((Property)BannerBlock.ROTATION)).intValue());
/*  95 */         $$2.mulPose(Axis.YP.rotationDegrees($$12));
/*     */         
/*  97 */         this.pole.visible = true;
/*     */       } else {
/*  99 */         $$2.translate(0.5F, -0.16666667F, 0.5F);
/* 100 */         float $$13 = -((Direction)$$11.getValue((Property)WallBannerBlock.FACING)).toYRot();
/* 101 */         $$2.mulPose(Axis.YP.rotationDegrees($$13));
/* 102 */         $$2.translate(0.0F, -0.3125F, -0.4375F);
/*     */         
/* 104 */         this.pole.visible = false;
/*     */       } 
/*     */     } 
/* 107 */     $$2.pushPose();
/* 108 */     $$2.scale(0.6666667F, -0.6666667F, -0.6666667F);
/* 109 */     VertexConsumer $$14 = ModelBakery.BANNER_BASE.buffer($$3, RenderType::entitySolid);
/* 110 */     this.pole.render($$2, $$14, $$4, $$5);
/* 111 */     this.bar.render($$2, $$14, $$4, $$5);
/*     */     
/* 113 */     BlockPos $$15 = $$0.getBlockPos();
/*     */     
/* 115 */     float $$16 = ((float)Math.floorMod(($$15.getX() * 7 + $$15.getY() * 9 + $$15.getZ() * 13) + $$10, 100L) + $$1) / 100.0F;
/* 116 */     this.flag.xRot = (-0.0125F + 0.01F * Mth.cos(6.2831855F * $$16)) * 3.1415927F;
/*     */     
/* 118 */     this.flag.y = -32.0F;
/*     */     
/* 120 */     renderPatterns($$2, $$3, $$4, $$5, this.flag, ModelBakery.BANNER_BASE, true, $$6);
/*     */     
/* 122 */     $$2.popPose();
/* 123 */     $$2.popPose();
/*     */   }
/*     */   
/*     */   public static void renderPatterns(PoseStack $$0, MultiBufferSource $$1, int $$2, int $$3, ModelPart $$4, Material $$5, boolean $$6, List<Pair<Holder<BannerPattern>, DyeColor>> $$7) {
/* 127 */     renderPatterns($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, false);
/*     */   }
/*     */   
/*     */   public static void renderPatterns(PoseStack $$0, MultiBufferSource $$1, int $$2, int $$3, ModelPart $$4, Material $$5, boolean $$6, List<Pair<Holder<BannerPattern>, DyeColor>> $$7, boolean $$8) {
/* 131 */     $$4.render($$0, $$5.buffer($$1, RenderType::entitySolid, $$8), $$2, $$3);
/*     */     
/* 133 */     for (int $$9 = 0; $$9 < 17 && $$9 < $$7.size(); $$9++) {
/* 134 */       Pair<Holder<BannerPattern>, DyeColor> $$10 = $$7.get($$9);
/* 135 */       float[] $$11 = ((DyeColor)$$10.getSecond()).getTextureDiffuseColors();
/* 136 */       ((Holder)$$10.getFirst()).unwrapKey().map($$1 -> $$0 ? Sheets.getBannerMaterial($$1) : Sheets.getShieldMaterial($$1)).ifPresent($$6 -> $$0.render($$1, $$6.buffer($$2, RenderType::entityNoOutline), $$3, $$4, $$5[0], $$5[1], $$5[2], 1.0F));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BannerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */