/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.model.Model;
/*     */ import net.minecraft.client.model.geom.EntityModelSet;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.util.FastColor;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SignBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SignText;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class SignRenderer implements BlockEntityRenderer<SignBlockEntity> {
/*     */   private static final String STICK = "stick";
/*  44 */   private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16); private static final int BLACK_TEXT_OUTLINE_COLOR = -988212;
/*     */   private static final float RENDER_SCALE = 0.6666667F;
/*  46 */   private static final Vec3 TEXT_OFFSET = new Vec3(0.0D, 0.3333333432674408D, 0.046666666865348816D);
/*     */   
/*     */   private final Map<WoodType, SignModel> signModels;
/*     */   private final Font font;
/*     */   
/*     */   public SignRenderer(BlockEntityRendererProvider.Context $$0) {
/*  52 */     this.signModels = (Map<WoodType, SignModel>)WoodType.values().collect(ImmutableMap.toImmutableMap($$0 -> $$0, $$1 -> new SignModel($$0.bakeLayer(ModelLayers.createSignModelName($$1)))));
/*     */ 
/*     */ 
/*     */     
/*  56 */     this.font = $$0.getFont();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(SignBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*  61 */     BlockState $$6 = $$0.getBlockState();
/*  62 */     SignBlock $$7 = (SignBlock)$$6.getBlock();
/*  63 */     WoodType $$8 = SignBlock.getWoodType((Block)$$7);
/*  64 */     SignModel $$9 = this.signModels.get($$8);
/*  65 */     $$9.stick.visible = $$6.getBlock() instanceof net.minecraft.world.level.block.StandingSignBlock;
/*  66 */     renderSignWithText($$0, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */   }
/*     */   
/*     */   public float getSignModelRenderScale() {
/*  70 */     return 0.6666667F;
/*     */   }
/*     */   
/*     */   public float getSignTextRenderScale() {
/*  74 */     return 0.6666667F;
/*     */   }
/*     */ 
/*     */   
/*     */   void renderSignWithText(SignBlockEntity $$0, PoseStack $$1, MultiBufferSource $$2, int $$3, int $$4, BlockState $$5, SignBlock $$6, WoodType $$7, Model $$8) {
/*  79 */     $$1.pushPose();
/*  80 */     translateSign($$1, -$$6.getYRotationDegrees($$5), $$5);
/*  81 */     renderSign($$1, $$2, $$3, $$4, $$7, $$8);
/*  82 */     renderSignText($$0.getBlockPos(), $$0.getFrontText(), $$1, $$2, $$3, $$0.getTextLineHeight(), $$0.getMaxTextLineWidth(), true);
/*  83 */     renderSignText($$0.getBlockPos(), $$0.getBackText(), $$1, $$2, $$3, $$0.getTextLineHeight(), $$0.getMaxTextLineWidth(), false);
/*  84 */     $$1.popPose();
/*     */   }
/*     */   
/*     */   void translateSign(PoseStack $$0, float $$1, BlockState $$2) {
/*  88 */     $$0.translate(0.5F, 0.75F * getSignModelRenderScale(), 0.5F);
/*  89 */     $$0.mulPose(Axis.YP.rotationDegrees($$1));
/*     */     
/*  91 */     if (!($$2.getBlock() instanceof net.minecraft.world.level.block.StandingSignBlock)) {
/*  92 */       $$0.translate(0.0F, -0.3125F, -0.4375F);
/*     */     }
/*     */   }
/*     */   
/*     */   void renderSign(PoseStack $$0, MultiBufferSource $$1, int $$2, int $$3, WoodType $$4, Model $$5) {
/*  97 */     $$0.pushPose();
/*  98 */     float $$6 = getSignModelRenderScale();
/*  99 */     $$0.scale($$6, -$$6, -$$6);
/* 100 */     Material $$7 = getSignMaterial($$4);
/* 101 */     Objects.requireNonNull($$5); VertexConsumer $$8 = $$7.buffer($$1, $$5::renderType);
/* 102 */     renderSignModel($$0, $$2, $$3, $$5, $$8);
/* 103 */     $$0.popPose();
/*     */   }
/*     */   
/*     */   void renderSignModel(PoseStack $$0, int $$1, int $$2, Model $$3, VertexConsumer $$4) {
/* 107 */     SignModel $$5 = (SignModel)$$3;
/* 108 */     $$5.root.render($$0, $$4, $$1, $$2);
/*     */   }
/*     */   
/*     */   Material getSignMaterial(WoodType $$0) {
/* 112 */     return Sheets.getSignMaterial($$0);
/*     */   } void renderSignText(BlockPos $$0, SignText $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5, int $$6, boolean $$7) {
/*     */     int $$14;
/*     */     boolean $$15;
/*     */     int $$16;
/* 117 */     $$2.pushPose();
/* 118 */     translateSignText($$2, $$7, getTextOffset());
/* 119 */     int $$8 = getDarkColor($$1);
/* 120 */     int $$9 = 4 * $$5 / 2;
/* 121 */     FormattedCharSequence[] $$10 = $$1.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), $$1 -> {
/*     */           List<FormattedCharSequence> $$2 = this.font.split((FormattedText)$$1, $$0);
/*     */ 
/*     */           
/*     */           return $$2.isEmpty() ? FormattedCharSequence.EMPTY : $$2.get(0);
/*     */         });
/*     */ 
/*     */     
/* 129 */     if ($$1.hasGlowingText()) {
/* 130 */       int $$11 = $$1.getColor().getTextColor();
/* 131 */       boolean $$12 = isOutlineVisible($$0, $$11);
/* 132 */       int $$13 = 15728880;
/*     */     } else {
/* 134 */       $$14 = $$8;
/* 135 */       $$15 = false;
/* 136 */       $$16 = $$4;
/*     */     } 
/*     */     
/* 139 */     for (int $$17 = 0; $$17 < 4; $$17++) {
/* 140 */       FormattedCharSequence $$18 = $$10[$$17];
/* 141 */       float $$19 = (-this.font.width($$18) / 2);
/*     */       
/* 143 */       if ($$15) {
/* 144 */         this.font.drawInBatch8xOutline($$18, $$19, ($$17 * $$5 - $$9), $$14, $$8, $$2.last().pose(), $$3, $$16);
/*     */       } else {
/* 146 */         this.font.drawInBatch($$18, $$19, ($$17 * $$5 - $$9), $$14, false, $$2.last().pose(), $$3, Font.DisplayMode.POLYGON_OFFSET, 0, $$16);
/*     */       } 
/*     */     } 
/* 149 */     $$2.popPose();
/*     */   }
/*     */   
/*     */   private void translateSignText(PoseStack $$0, boolean $$1, Vec3 $$2) {
/* 153 */     if (!$$1) {
/* 154 */       $$0.mulPose(Axis.YP.rotationDegrees(180.0F));
/*     */     }
/*     */     
/* 157 */     float $$3 = 0.015625F * getSignTextRenderScale();
/* 158 */     $$0.translate($$2.x, $$2.y, $$2.z);
/* 159 */     $$0.scale($$3, -$$3, $$3);
/*     */   }
/*     */   
/*     */   Vec3 getTextOffset() {
/* 163 */     return TEXT_OFFSET;
/*     */   }
/*     */   
/*     */   static boolean isOutlineVisible(BlockPos $$0, int $$1) {
/* 167 */     if ($$1 == DyeColor.BLACK.getTextColor()) {
/* 168 */       return true;
/*     */     }
/*     */     
/* 171 */     Minecraft $$2 = Minecraft.getInstance();
/* 172 */     LocalPlayer $$3 = $$2.player;
/* 173 */     if ($$3 != null && $$2.options.getCameraType().isFirstPerson() && $$3.isScoping()) {
/* 174 */       return true;
/*     */     }
/*     */     
/* 177 */     Entity $$4 = $$2.getCameraEntity();
/* 178 */     if ($$4 != null && $$4.distanceToSqr(Vec3.atCenterOf((Vec3i)$$0)) < OUTLINE_RENDER_DISTANCE) {
/* 179 */       return true;
/*     */     }
/*     */     
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   public static int getDarkColor(SignText $$0) {
/* 186 */     int $$1 = $$0.getColor().getTextColor();
/*     */     
/* 188 */     if ($$1 == DyeColor.BLACK.getTextColor() && $$0.hasGlowingText()) {
/* 189 */       return -988212;
/*     */     }
/*     */     
/* 192 */     double $$2 = 0.4D;
/* 193 */     int $$3 = (int)(FastColor.ARGB32.red($$1) * 0.4D);
/* 194 */     int $$4 = (int)(FastColor.ARGB32.green($$1) * 0.4D);
/* 195 */     int $$5 = (int)(FastColor.ARGB32.blue($$1) * 0.4D);
/*     */     
/* 197 */     return FastColor.ARGB32.color(0, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public static SignModel createSignModel(EntityModelSet $$0, WoodType $$1) {
/* 201 */     return new SignModel($$0.bakeLayer(ModelLayers.createSignModelName($$1)));
/*     */   }
/*     */   
/*     */   public static LayerDefinition createSignLayer() {
/* 205 */     MeshDefinition $$0 = new MeshDefinition();
/* 206 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/* 208 */     $$1.addOrReplaceChild("sign", 
/* 209 */         CubeListBuilder.create()
/* 210 */         .texOffs(0, 0).addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/* 213 */     $$1.addOrReplaceChild("stick", 
/* 214 */         CubeListBuilder.create()
/* 215 */         .texOffs(0, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/* 219 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */   
/*     */   public static final class SignModel extends Model {
/*     */     public final ModelPart root;
/*     */     public final ModelPart stick;
/*     */     
/*     */     public SignModel(ModelPart $$0) {
/* 227 */       super(RenderType::entityCutoutNoCull);
/* 228 */       this.root = $$0;
/* 229 */       this.stick = $$0.getChild("stick");
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 234 */       this.root.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\SignRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */