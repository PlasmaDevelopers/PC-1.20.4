/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
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
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.BedBlock;
/*     */ import net.minecraft.world.level.block.DoubleBlockCombiner;
/*     */ import net.minecraft.world.level.block.entity.BedBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BedPart;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class BedRenderer implements BlockEntityRenderer<BedBlockEntity> {
/*     */   private final ModelPart headRoot;
/*     */   
/*     */   public BedRenderer(BlockEntityRendererProvider.Context $$0) {
/*  34 */     this.headRoot = $$0.bakeLayer(ModelLayers.BED_HEAD);
/*  35 */     this.footRoot = $$0.bakeLayer(ModelLayers.BED_FOOT);
/*     */   }
/*     */   private final ModelPart footRoot;
/*     */   public static LayerDefinition createHeadLayer() {
/*  39 */     MeshDefinition $$0 = new MeshDefinition();
/*  40 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  42 */     $$1.addOrReplaceChild("main", 
/*  43 */         CubeListBuilder.create()
/*  44 */         .texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     $$1.addOrReplaceChild("left_leg", 
/*  50 */         CubeListBuilder.create()
/*  51 */         .texOffs(50, 6).addBox(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F), 
/*  52 */         PartPose.rotation(1.5707964F, 0.0F, 1.5707964F));
/*     */     
/*  54 */     $$1.addOrReplaceChild("right_leg", 
/*  55 */         CubeListBuilder.create()
/*  56 */         .texOffs(50, 18).addBox(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F), 
/*  57 */         PartPose.rotation(1.5707964F, 0.0F, 3.1415927F));
/*     */ 
/*     */     
/*  60 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createFootLayer() {
/*  64 */     MeshDefinition $$0 = new MeshDefinition();
/*  65 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  67 */     $$1.addOrReplaceChild("main", 
/*  68 */         CubeListBuilder.create()
/*  69 */         .texOffs(0, 22).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  73 */     $$1.addOrReplaceChild("left_leg", 
/*  74 */         CubeListBuilder.create()
/*  75 */         .texOffs(50, 0).addBox(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), 
/*  76 */         PartPose.rotation(1.5707964F, 0.0F, 0.0F));
/*     */     
/*  78 */     $$1.addOrReplaceChild("right_leg", 
/*  79 */         CubeListBuilder.create()
/*  80 */         .texOffs(50, 12).addBox(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), 
/*  81 */         PartPose.rotation(1.5707964F, 0.0F, 4.712389F));
/*     */ 
/*     */     
/*  84 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(BedBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*  89 */     Material $$6 = Sheets.BED_TEXTURES[$$0.getColor().getId()];
/*  90 */     Level $$7 = $$0.getLevel();
/*  91 */     if ($$7 != null) {
/*  92 */       BlockState $$8 = $$0.getBlockState();
/*  93 */       DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> $$9 = DoubleBlockCombiner.combineWithNeigbour(BlockEntityType.BED, BedBlock::getBlockType, BedBlock::getConnectedDirection, ChestBlock.FACING, $$8, (LevelAccessor)$$7, $$0.getBlockPos(), ($$0, $$1) -> false);
/*  94 */       int $$10 = ((Int2IntFunction)$$9.apply(new BrightnessCombiner<>())).get($$4);
/*  95 */       renderPiece($$2, $$3, ($$8.getValue((Property)BedBlock.PART) == BedPart.HEAD) ? this.headRoot : this.footRoot, (Direction)$$8.getValue((Property)BedBlock.FACING), $$6, $$10, $$5, false);
/*     */     } else {
/*  97 */       renderPiece($$2, $$3, this.headRoot, Direction.SOUTH, $$6, $$4, $$5, false);
/*  98 */       renderPiece($$2, $$3, this.footRoot, Direction.SOUTH, $$6, $$4, $$5, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderPiece(PoseStack $$0, MultiBufferSource $$1, ModelPart $$2, Direction $$3, Material $$4, int $$5, int $$6, boolean $$7) {
/* 103 */     $$0.pushPose();
/* 104 */     $$0.translate(0.0F, 0.5625F, $$7 ? -1.0F : 0.0F);
/* 105 */     $$0.mulPose(Axis.XP.rotationDegrees(90.0F));
/* 106 */     $$0.translate(0.5F, 0.5F, 0.5F);
/* 107 */     $$0.mulPose(Axis.ZP.rotationDegrees(180.0F + $$3.toYRot()));
/* 108 */     $$0.translate(-0.5F, -0.5F, -0.5F);
/*     */     
/* 110 */     VertexConsumer $$8 = $$4.buffer($$1, RenderType::entitySolid);
/* 111 */     $$2.render($$0, $$8, $$5, $$6);
/*     */     
/* 113 */     $$0.popPose();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BedRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */