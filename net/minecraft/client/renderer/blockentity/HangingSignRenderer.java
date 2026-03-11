/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.Model;
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
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SignBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HangingSignRenderer
/*     */   extends SignRenderer
/*     */ {
/*     */   private static final String PLANK = "plank";
/*     */   private static final String V_CHAINS = "vChains";
/*     */   private static final String NORMAL_CHAINS = "normalChains";
/*     */   private static final String CHAIN_L_1 = "chainL1";
/*     */   private static final String CHAIN_L_2 = "chainL2";
/*     */   private static final String CHAIN_R_1 = "chainR1";
/*     */   private static final String CHAIN_R_2 = "chainR2";
/*     */   private static final String BOARD = "board";
/*     */   private static final float MODEL_RENDER_SCALE = 1.0F;
/*     */   private static final float TEXT_RENDER_SCALE = 0.9F;
/*  49 */   private static final Vec3 TEXT_OFFSET = new Vec3(0.0D, -0.3199999928474426D, 0.0729999989271164D);
/*     */   private final Map<WoodType, HangingSignModel> hangingSignModels;
/*     */   
/*     */   public HangingSignRenderer(BlockEntityRendererProvider.Context $$0) {
/*  53 */     super($$0);
/*  54 */     this.hangingSignModels = (Map<WoodType, HangingSignModel>)WoodType.values().collect(ImmutableMap.toImmutableMap($$0 -> $$0, $$1 -> new HangingSignModel($$0.bakeLayer(ModelLayers.createHangingSignModelName($$1)))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSignModelRenderScale() {
/*  62 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSignTextRenderScale() {
/*  67 */     return 0.9F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(SignBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*  72 */     BlockState $$6 = $$0.getBlockState();
/*  73 */     SignBlock $$7 = (SignBlock)$$6.getBlock();
/*  74 */     WoodType $$8 = SignBlock.getWoodType((Block)$$7);
/*  75 */     HangingSignModel $$9 = this.hangingSignModels.get($$8);
/*  76 */     $$9.evaluateVisibleParts($$6);
/*  77 */     renderSignWithText($$0, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */   }
/*     */ 
/*     */   
/*     */   void translateSign(PoseStack $$0, float $$1, BlockState $$2) {
/*  82 */     $$0.translate(0.5D, 0.9375D, 0.5D);
/*  83 */     $$0.mulPose(Axis.YP.rotationDegrees($$1));
/*  84 */     $$0.translate(0.0F, -0.3125F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   void renderSignModel(PoseStack $$0, int $$1, int $$2, Model $$3, VertexConsumer $$4) {
/*  89 */     HangingSignModel $$5 = (HangingSignModel)$$3;
/*  90 */     $$5.root.render($$0, $$4, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   Material getSignMaterial(WoodType $$0) {
/*  95 */     return Sheets.getHangingSignMaterial($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   Vec3 getTextOffset() {
/* 100 */     return TEXT_OFFSET;
/*     */   }
/*     */   
/*     */   public static LayerDefinition createHangingSignLayer() {
/* 104 */     MeshDefinition $$0 = new MeshDefinition();
/* 105 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/* 107 */     $$1.addOrReplaceChild("board", 
/* 108 */         CubeListBuilder.create()
/* 109 */         .texOffs(0, 12).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 10.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/* 112 */     $$1.addOrReplaceChild("plank", 
/* 113 */         CubeListBuilder.create()
/* 114 */         .texOffs(0, 0).addBox(-8.0F, -6.0F, -2.0F, 16.0F, 2.0F, 4.0F), PartPose.ZERO);
/*     */ 
/*     */     
/* 117 */     PartDefinition $$2 = $$1.addOrReplaceChild("normalChains", 
/* 118 */         CubeListBuilder.create(), PartPose.ZERO);
/*     */ 
/*     */     
/* 121 */     $$2.addOrReplaceChild("chainL1", 
/* 122 */         CubeListBuilder.create()
/* 123 */         .texOffs(0, 6).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F), 
/* 124 */         PartPose.offsetAndRotation(-5.0F, -6.0F, 0.0F, 0.0F, -0.7853982F, 0.0F));
/*     */     
/* 126 */     $$2.addOrReplaceChild("chainL2", 
/* 127 */         CubeListBuilder.create()
/* 128 */         .texOffs(6, 6).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F), 
/* 129 */         PartPose.offsetAndRotation(-5.0F, -6.0F, 0.0F, 0.0F, 0.7853982F, 0.0F));
/*     */     
/* 131 */     $$2.addOrReplaceChild("chainR1", 
/* 132 */         CubeListBuilder.create()
/* 133 */         .texOffs(0, 6).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F), 
/* 134 */         PartPose.offsetAndRotation(5.0F, -6.0F, 0.0F, 0.0F, -0.7853982F, 0.0F));
/*     */     
/* 136 */     $$2.addOrReplaceChild("chainR2", 
/* 137 */         CubeListBuilder.create()
/* 138 */         .texOffs(6, 6).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F), 
/* 139 */         PartPose.offsetAndRotation(5.0F, -6.0F, 0.0F, 0.0F, 0.7853982F, 0.0F));
/*     */     
/* 141 */     $$1.addOrReplaceChild("vChains", 
/* 142 */         CubeListBuilder.create()
/* 143 */         .texOffs(14, 6).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 6.0F, 0.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/* 147 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */   
/*     */   public static final class HangingSignModel extends Model {
/*     */     public final ModelPart root;
/*     */     public final ModelPart plank;
/*     */     public final ModelPart vChains;
/*     */     public final ModelPart normalChains;
/*     */     
/*     */     public HangingSignModel(ModelPart $$0) {
/* 157 */       super(RenderType::entityCutoutNoCull);
/* 158 */       this.root = $$0;
/* 159 */       this.plank = $$0.getChild("plank");
/* 160 */       this.normalChains = $$0.getChild("normalChains");
/* 161 */       this.vChains = $$0.getChild("vChains");
/*     */     }
/*     */     
/*     */     public void evaluateVisibleParts(BlockState $$0) {
/* 165 */       boolean $$1 = !($$0.getBlock() instanceof net.minecraft.world.level.block.CeilingHangingSignBlock);
/* 166 */       this.plank.visible = $$1;
/* 167 */       this.vChains.visible = false;
/* 168 */       this.normalChains.visible = true;
/*     */       
/* 170 */       if (!$$1) {
/* 171 */         boolean $$2 = ((Boolean)$$0.getValue((Property)BlockStateProperties.ATTACHED)).booleanValue();
/* 172 */         this.normalChains.visible = !$$2;
/* 173 */         this.vChains.visible = $$2;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 179 */       this.root.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\HangingSignRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */