/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*     */ import java.util.Calendar;
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
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.AbstractChestBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ChestBlock;
/*     */ import net.minecraft.world.level.block.DoubleBlockCombiner;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.LidBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.ChestType;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ 
/*     */ public class ChestRenderer<T extends BlockEntity & LidBlockEntity>
/*     */   implements BlockEntityRenderer<T>
/*     */ {
/*     */   private static final String BOTTOM = "bottom";
/*     */   private static final String LID = "lid";
/*     */   private static final String LOCK = "lock";
/*     */   private final ModelPart lid;
/*     */   private final ModelPart bottom;
/*     */   private final ModelPart lock;
/*     */   private final ModelPart doubleLeftLid;
/*     */   private final ModelPart doubleLeftBottom;
/*     */   private final ModelPart doubleLeftLock;
/*     */   private final ModelPart doubleRightLid;
/*     */   private final ModelPart doubleRightBottom;
/*     */   private final ModelPart doubleRightLock;
/*     */   private boolean xmasTextures;
/*     */   
/*     */   public ChestRenderer(BlockEntityRendererProvider.Context $$0) {
/*  53 */     Calendar $$1 = Calendar.getInstance();
/*  54 */     if ($$1.get(2) + 1 == 12 && $$1.get(5) >= 24 && $$1.get(5) <= 26) {
/*  55 */       this.xmasTextures = true;
/*     */     }
/*     */     
/*  58 */     ModelPart $$2 = $$0.bakeLayer(ModelLayers.CHEST);
/*  59 */     this.bottom = $$2.getChild("bottom");
/*  60 */     this.lid = $$2.getChild("lid");
/*  61 */     this.lock = $$2.getChild("lock");
/*     */     
/*  63 */     ModelPart $$3 = $$0.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
/*  64 */     this.doubleLeftBottom = $$3.getChild("bottom");
/*  65 */     this.doubleLeftLid = $$3.getChild("lid");
/*  66 */     this.doubleLeftLock = $$3.getChild("lock");
/*     */     
/*  68 */     ModelPart $$4 = $$0.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
/*  69 */     this.doubleRightBottom = $$4.getChild("bottom");
/*  70 */     this.doubleRightLid = $$4.getChild("lid");
/*  71 */     this.doubleRightLock = $$4.getChild("lock");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createSingleBodyLayer() {
/*  75 */     MeshDefinition $$0 = new MeshDefinition();
/*  76 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  78 */     $$1.addOrReplaceChild("bottom", 
/*  79 */         CubeListBuilder.create()
/*  80 */         .texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  83 */     $$1.addOrReplaceChild("lid", 
/*  84 */         CubeListBuilder.create()
/*  85 */         .texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), 
/*  86 */         PartPose.offset(0.0F, 9.0F, 1.0F));
/*     */     
/*  88 */     $$1.addOrReplaceChild("lock", 
/*  89 */         CubeListBuilder.create()
/*  90 */         .texOffs(0, 0).addBox(7.0F, -2.0F, 14.0F, 2.0F, 4.0F, 1.0F), 
/*  91 */         PartPose.offset(0.0F, 9.0F, 1.0F));
/*     */ 
/*     */     
/*  94 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createDoubleBodyRightLayer() {
/*  98 */     MeshDefinition $$0 = new MeshDefinition();
/*  99 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/* 101 */     $$1.addOrReplaceChild("bottom", 
/* 102 */         CubeListBuilder.create()
/* 103 */         .texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), PartPose.ZERO);
/*     */ 
/*     */     
/* 106 */     $$1.addOrReplaceChild("lid", 
/* 107 */         CubeListBuilder.create()
/* 108 */         .texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), 
/* 109 */         PartPose.offset(0.0F, 9.0F, 1.0F));
/*     */     
/* 111 */     $$1.addOrReplaceChild("lock", 
/* 112 */         CubeListBuilder.create()
/* 113 */         .texOffs(0, 0).addBox(15.0F, -2.0F, 14.0F, 1.0F, 4.0F, 1.0F), 
/* 114 */         PartPose.offset(0.0F, 9.0F, 1.0F));
/*     */     
/* 116 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createDoubleBodyLeftLayer() {
/* 120 */     MeshDefinition $$0 = new MeshDefinition();
/* 121 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/* 123 */     $$1.addOrReplaceChild("bottom", 
/* 124 */         CubeListBuilder.create()
/* 125 */         .texOffs(0, 19).addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), PartPose.ZERO);
/*     */ 
/*     */     
/* 128 */     $$1.addOrReplaceChild("lid", 
/* 129 */         CubeListBuilder.create()
/* 130 */         .texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), 
/* 131 */         PartPose.offset(0.0F, 9.0F, 1.0F));
/*     */     
/* 133 */     $$1.addOrReplaceChild("lock", 
/* 134 */         CubeListBuilder.create()
/* 135 */         .texOffs(0, 0).addBox(0.0F, -2.0F, 14.0F, 1.0F, 4.0F, 1.0F), 
/* 136 */         PartPose.offset(0.0F, 9.0F, 1.0F));
/*     */ 
/*     */     
/* 139 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */   
/*     */   public void render(T $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*     */     DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> $$15;
/* 144 */     Level $$6 = $$0.getLevel();
/* 145 */     boolean $$7 = ($$6 != null);
/*     */     
/* 147 */     BlockState $$8 = $$7 ? $$0.getBlockState() : (BlockState)Blocks.CHEST.defaultBlockState().setValue((Property)ChestBlock.FACING, (Comparable)Direction.SOUTH);
/* 148 */     ChestType $$9 = $$8.hasProperty((Property)ChestBlock.TYPE) ? (ChestType)$$8.getValue((Property)ChestBlock.TYPE) : ChestType.SINGLE;
/* 149 */     Block $$10 = $$8.getBlock();
/* 150 */     if (!($$10 instanceof AbstractChestBlock)) {
/*     */       return;
/*     */     }
/* 153 */     AbstractChestBlock<?> $$11 = (AbstractChestBlock)$$10;
/* 154 */     boolean $$12 = ($$9 != ChestType.SINGLE);
/*     */     
/* 156 */     $$2.pushPose();
/*     */     
/* 158 */     float $$13 = ((Direction)$$8.getValue((Property)ChestBlock.FACING)).toYRot();
/* 159 */     $$2.translate(0.5F, 0.5F, 0.5F);
/* 160 */     $$2.mulPose(Axis.YP.rotationDegrees(-$$13));
/* 161 */     $$2.translate(-0.5F, -0.5F, -0.5F);
/*     */ 
/*     */     
/* 164 */     if ($$7) {
/* 165 */       DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> $$14 = $$11.combine($$8, $$6, $$0.getBlockPos(), true);
/*     */     } else {
/* 167 */       $$15 = DoubleBlockCombiner.Combiner::acceptNone;
/*     */     } 
/*     */     
/* 170 */     float $$16 = ((Float2FloatFunction)$$15.apply(ChestBlock.opennessCombiner((LidBlockEntity)$$0))).get($$1);
/*     */     
/* 172 */     $$16 = 1.0F - $$16;
/* 173 */     $$16 = 1.0F - $$16 * $$16 * $$16;
/*     */     
/* 175 */     int $$17 = ((Int2IntFunction)$$15.apply(new BrightnessCombiner<>())).applyAsInt($$4);
/* 176 */     Material $$18 = Sheets.chooseMaterial((BlockEntity)$$0, $$9, this.xmasTextures);
/*     */     
/* 178 */     VertexConsumer $$19 = $$18.buffer($$3, RenderType::entityCutout);
/* 179 */     if ($$12) {
/* 180 */       if ($$9 == ChestType.LEFT) {
/* 181 */         render($$2, $$19, this.doubleLeftLid, this.doubleLeftLock, this.doubleLeftBottom, $$16, $$17, $$5);
/*     */       } else {
/* 183 */         render($$2, $$19, this.doubleRightLid, this.doubleRightLock, this.doubleRightBottom, $$16, $$17, $$5);
/*     */       } 
/*     */     } else {
/* 186 */       render($$2, $$19, this.lid, this.lock, this.bottom, $$16, $$17, $$5);
/*     */     } 
/*     */     
/* 189 */     $$2.popPose();
/*     */   }
/*     */   
/*     */   private void render(PoseStack $$0, VertexConsumer $$1, ModelPart $$2, ModelPart $$3, ModelPart $$4, float $$5, int $$6, int $$7) {
/* 193 */     $$2.xRot = -($$5 * 1.5707964F);
/* 194 */     $$3.xRot = $$2.xRot;
/*     */     
/* 196 */     $$2.render($$0, $$1, $$6, $$7);
/* 197 */     $$3.render($$0, $$1, $$6, $$7);
/* 198 */     $$4.render($$0, $$1, $$6, $$7);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\ChestRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */