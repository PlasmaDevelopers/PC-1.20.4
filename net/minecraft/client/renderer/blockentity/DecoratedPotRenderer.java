/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DecoratedPotRenderer
/*     */   implements BlockEntityRenderer<DecoratedPotBlockEntity>
/*     */ {
/*     */   private static final String NECK = "neck";
/*     */   private static final String FRONT = "front";
/*     */   private static final String BACK = "back";
/*     */   private static final String LEFT = "left";
/*     */   private static final String RIGHT = "right";
/*     */   private static final String TOP = "top";
/*     */   private static final String BOTTOM = "bottom";
/*     */   private final ModelPart neck;
/*     */   private final ModelPart frontSide;
/*     */   private final ModelPart backSide;
/*     */   private final ModelPart leftSide;
/*     */   private final ModelPart rightSide;
/*     */   private final ModelPart top;
/*     */   private final ModelPart bottom;
/*  49 */   private final Material baseMaterial = Objects.<Material>requireNonNull(Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.BASE));
/*     */   public DecoratedPotRenderer(BlockEntityRendererProvider.Context $$0) {
/*  51 */     ModelPart $$1 = $$0.bakeLayer(ModelLayers.DECORATED_POT_BASE);
/*  52 */     this.neck = $$1.getChild("neck");
/*  53 */     this.top = $$1.getChild("top");
/*  54 */     this.bottom = $$1.getChild("bottom");
/*     */     
/*  56 */     ModelPart $$2 = $$0.bakeLayer(ModelLayers.DECORATED_POT_SIDES);
/*  57 */     this.frontSide = $$2.getChild("front");
/*  58 */     this.backSide = $$2.getChild("back");
/*  59 */     this.leftSide = $$2.getChild("left");
/*  60 */     this.rightSide = $$2.getChild("right");
/*     */   }
/*     */   private static final float WOBBLE_AMPLITUDE = 0.125F;
/*     */   public static LayerDefinition createBaseLayer() {
/*  64 */     MeshDefinition $$0 = new MeshDefinition();
/*  65 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  67 */     CubeDeformation $$2 = new CubeDeformation(0.2F);
/*  68 */     CubeDeformation $$3 = new CubeDeformation(-0.1F);
/*     */     
/*  70 */     $$1.addOrReplaceChild("neck", 
/*  71 */         CubeListBuilder.create()
/*  72 */         .texOffs(0, 0).addBox(4.0F, 17.0F, 4.0F, 8.0F, 3.0F, 8.0F, $$3)
/*  73 */         .texOffs(0, 5).addBox(5.0F, 20.0F, 5.0F, 6.0F, 1.0F, 6.0F, $$2), 
/*  74 */         PartPose.offsetAndRotation(0.0F, 37.0F, 16.0F, 3.1415927F, 0.0F, 0.0F));
/*     */ 
/*     */     
/*  77 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(-14, 13).addBox(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 14.0F);
/*  78 */     $$1.addOrReplaceChild("top", $$4, PartPose.offsetAndRotation(1.0F, 16.0F, 1.0F, 0.0F, 0.0F, 0.0F));
/*  79 */     $$1.addOrReplaceChild("bottom", $$4, PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
/*     */     
/*  81 */     return LayerDefinition.create($$0, 32, 32);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createSidesLayer() {
/*  85 */     MeshDefinition $$0 = new MeshDefinition();
/*  86 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  88 */     CubeListBuilder $$2 = CubeListBuilder.create().texOffs(1, 0).addBox(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 0.0F, EnumSet.of(Direction.NORTH));
/*     */     
/*  90 */     $$1.addOrReplaceChild("back", $$2, PartPose.offsetAndRotation(15.0F, 16.0F, 1.0F, 0.0F, 0.0F, 3.1415927F));
/*  91 */     $$1.addOrReplaceChild("left", $$2, PartPose.offsetAndRotation(1.0F, 16.0F, 1.0F, 0.0F, -1.5707964F, 3.1415927F));
/*  92 */     $$1.addOrReplaceChild("right", $$2, PartPose.offsetAndRotation(15.0F, 16.0F, 15.0F, 0.0F, 1.5707964F, 3.1415927F));
/*  93 */     $$1.addOrReplaceChild("front", $$2, PartPose.offsetAndRotation(1.0F, 16.0F, 15.0F, 3.1415927F, 0.0F, 0.0F));
/*     */     
/*  95 */     return LayerDefinition.create($$0, 16, 16);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Material getMaterial(Item $$0) {
/* 100 */     Material $$1 = Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.getResourceKey($$0));
/* 101 */     if ($$1 == null) {
/* 102 */       $$1 = Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.getResourceKey(Items.BRICK));
/*     */     }
/* 104 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(DecoratedPotBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 111 */     $$2.pushPose();
/*     */     
/* 113 */     Direction $$6 = $$0.getDirection();
/*     */     
/* 115 */     $$2.translate(0.5D, 0.0D, 0.5D);
/* 116 */     $$2.mulPose(Axis.YP.rotationDegrees(180.0F - $$6.toYRot()));
/* 117 */     $$2.translate(-0.5D, 0.0D, -0.5D);
/*     */     
/* 119 */     DecoratedPotBlockEntity.WobbleStyle $$7 = $$0.lastWobbleStyle;
/* 120 */     if ($$7 != null && $$0.getLevel() != null) {
/* 121 */       float $$8 = ((float)($$0.getLevel().getGameTime() - $$0.wobbleStartedAtTick) + $$1) / $$7.duration;
/* 122 */       if ($$8 >= 0.0F && $$8 <= 1.0F)
/*     */       {
/*     */         
/* 125 */         if ($$7 == DecoratedPotBlockEntity.WobbleStyle.POSITIVE) {
/*     */ 
/*     */           
/* 128 */           float $$9 = 0.015625F;
/* 129 */           float $$10 = $$8 * 6.2831855F;
/*     */           
/* 131 */           float $$11 = -1.5F * (Mth.cos($$10) + 0.5F) * Mth.sin($$10 / 2.0F);
/* 132 */           $$2.rotateAround(Axis.XP.rotation($$11 * 0.015625F), 0.5F, 0.0F, 0.5F);
/*     */           
/* 134 */           float $$12 = Mth.sin($$10);
/* 135 */           $$2.rotateAround(Axis.ZP.rotation($$12 * 0.015625F), 0.5F, 0.0F, 0.5F);
/*     */         }
/*     */         else {
/*     */           
/* 139 */           float $$13 = Mth.sin(-$$8 * 3.0F * 3.1415927F) * 0.125F;
/* 140 */           float $$14 = 1.0F - $$8;
/* 141 */           $$2.rotateAround(Axis.YP.rotation($$13 * $$14), 0.5F, 0.0F, 0.5F);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 146 */     VertexConsumer $$15 = this.baseMaterial.buffer($$3, RenderType::entitySolid);
/*     */     
/* 148 */     this.neck.render($$2, $$15, $$4, $$5);
/* 149 */     this.top.render($$2, $$15, $$4, $$5);
/* 150 */     this.bottom.render($$2, $$15, $$4, $$5);
/*     */     
/* 152 */     DecoratedPotBlockEntity.Decorations $$16 = $$0.getDecorations();
/*     */     
/* 154 */     renderSide(this.frontSide, $$2, $$3, $$4, $$5, getMaterial($$16.front()));
/* 155 */     renderSide(this.backSide, $$2, $$3, $$4, $$5, getMaterial($$16.back()));
/* 156 */     renderSide(this.leftSide, $$2, $$3, $$4, $$5, getMaterial($$16.left()));
/* 157 */     renderSide(this.rightSide, $$2, $$3, $$4, $$5, getMaterial($$16.right()));
/*     */     
/* 159 */     $$2.popPose();
/*     */   }
/*     */   
/*     */   private void renderSide(ModelPart $$0, PoseStack $$1, MultiBufferSource $$2, int $$3, int $$4, @Nullable Material $$5) {
/* 163 */     if ($$5 == null) {
/* 164 */       $$5 = getMaterial(Items.BRICK);
/*     */     }
/* 166 */     if ($$5 != null)
/* 167 */       $$0.render($$1, $$5.buffer($$2, RenderType::entitySolid), $$3, $$4); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\DecoratedPotRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */