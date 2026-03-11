/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Transformation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Display;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ 
/*     */ public abstract class DisplayRenderer<T extends Display, S> extends EntityRenderer<T> {
/*     */   private final EntityRenderDispatcher entityRenderDispatcher;
/*     */   
/*     */   protected DisplayRenderer(EntityRendererProvider.Context $$0) {
/*  31 */     super($$0);
/*  32 */     this.entityRenderDispatcher = $$0.getEntityRenderDispatcher();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(T $$0) {
/*  37 */     return TextureAtlas.LOCATION_BLOCKS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(T $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  42 */     Display.RenderState $$6 = $$0.renderState();
/*  43 */     if ($$6 == null) {
/*     */       return;
/*     */     }
/*  46 */     S $$7 = getSubState($$0);
/*  47 */     if ($$7 == null) {
/*     */       return;
/*     */     }
/*  50 */     float $$8 = $$0.calculateInterpolationProgress($$2);
/*     */     
/*  52 */     this.shadowRadius = $$6.shadowRadius().get($$8);
/*  53 */     this.shadowStrength = $$6.shadowStrength().get($$8);
/*     */     
/*  55 */     int $$9 = $$6.brightnessOverride();
/*  56 */     int $$10 = ($$9 != -1) ? $$9 : $$5;
/*  57 */     super.render($$0, $$1, $$2, $$3, $$4, $$10);
/*     */     
/*  59 */     $$3.pushPose();
/*  60 */     $$3.mulPose(calculateOrientation($$6, $$0, $$2, new Quaternionf()));
/*  61 */     Transformation $$11 = (Transformation)$$6.transformation().get($$8);
/*  62 */     $$3.mulPoseMatrix($$11.getMatrix());
/*     */     
/*  64 */     $$3.last().normal().rotate((Quaternionfc)$$11.getLeftRotation()).rotate((Quaternionfc)$$11.getRightRotation());
/*  65 */     renderInner($$0, $$7, $$3, $$4, $$10, $$8);
/*  66 */     $$3.popPose();
/*     */   }
/*     */   
/*     */   private Quaternionf calculateOrientation(Display.RenderState $$0, T $$1, float $$2, Quaternionf $$3) {
/*  70 */     Camera $$4 = this.entityRenderDispatcher.camera;
/*     */     
/*  72 */     switch ($$0.billboardConstraints()) { default: throw new IncompatibleClassChangeError();case LEFT: case RIGHT: case CENTER: case null: break; }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       $$3.rotationYXZ(-0.017453292F * cameraYrot($$4), 0.017453292F * cameraXRot($$4), 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private static float cameraYrot(Camera $$0) {
/*  85 */     return $$0.getYRot() - 180.0F;
/*     */   }
/*     */   
/*     */   private static float cameraXRot(Camera $$0) {
/*  89 */     return -$$0.getXRot();
/*     */   }
/*     */   
/*     */   private static <T extends Display> float entityYRot(T $$0, float $$1) {
/*  93 */     return Mth.rotLerp($$1, ((Display)$$0).yRotO, $$0.getYRot());
/*     */   }
/*     */   
/*     */   private static <T extends Display> float entityXRot(T $$0, float $$1) {
/*  97 */     return Mth.lerp($$1, ((Display)$$0).xRotO, $$0.getXRot());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected abstract S getSubState(T paramT);
/*     */   
/*     */   protected abstract void renderInner(T paramT, S paramS, PoseStack paramPoseStack, MultiBufferSource paramMultiBufferSource, int paramInt, float paramFloat);
/*     */   
/*     */   public static class BlockDisplayRenderer extends DisplayRenderer<Display.BlockDisplay, Display.BlockDisplay.BlockRenderState> {
/*     */     private final BlockRenderDispatcher blockRenderer;
/*     */     
/*     */     protected BlockDisplayRenderer(EntityRendererProvider.Context $$0) {
/* 109 */       super($$0);
/* 110 */       this.blockRenderer = $$0.getBlockRenderDispatcher();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected Display.BlockDisplay.BlockRenderState getSubState(Display.BlockDisplay $$0) {
/* 116 */       return $$0.blockRenderState();
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderInner(Display.BlockDisplay $$0, Display.BlockDisplay.BlockRenderState $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, float $$5) {
/* 121 */       this.blockRenderer.renderSingleBlock($$1.blockState(), $$2, $$3, $$4, OverlayTexture.NO_OVERLAY);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ItemDisplayRenderer extends DisplayRenderer<Display.ItemDisplay, Display.ItemDisplay.ItemRenderState> {
/*     */     private final ItemRenderer itemRenderer;
/*     */     
/*     */     protected ItemDisplayRenderer(EntityRendererProvider.Context $$0) {
/* 129 */       super($$0);
/* 130 */       this.itemRenderer = $$0.getItemRenderer();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected Display.ItemDisplay.ItemRenderState getSubState(Display.ItemDisplay $$0) {
/* 136 */       return $$0.itemRenderState();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void renderInner(Display.ItemDisplay $$0, Display.ItemDisplay.ItemRenderState $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, float $$5) {
/* 142 */       $$2.mulPose(Axis.YP.rotation(3.1415927F));
/* 143 */       this.itemRenderer.renderStatic($$1.itemStack(), $$1.itemTransform(), $$4, OverlayTexture.NO_OVERLAY, $$2, $$3, $$0.level(), $$0.getId());
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TextDisplayRenderer extends DisplayRenderer<Display.TextDisplay, Display.TextDisplay.TextRenderState> {
/*     */     private final Font font;
/*     */     
/*     */     protected TextDisplayRenderer(EntityRendererProvider.Context $$0) {
/* 151 */       super($$0);
/* 152 */       this.font = $$0.getFont();
/*     */     }
/*     */     
/*     */     private Display.TextDisplay.CachedInfo splitLines(Component $$0, int $$1) {
/* 156 */       List<FormattedCharSequence> $$2 = this.font.split((FormattedText)$$0, $$1);
/* 157 */       List<Display.TextDisplay.CachedLine> $$3 = new ArrayList<>($$2.size());
/*     */       
/* 159 */       int $$4 = 0;
/* 160 */       for (FormattedCharSequence $$5 : $$2) {
/* 161 */         int $$6 = this.font.width($$5);
/* 162 */         $$4 = Math.max($$4, $$6);
/* 163 */         $$3.add(new Display.TextDisplay.CachedLine($$5, $$6));
/*     */       } 
/*     */       
/* 166 */       return new Display.TextDisplay.CachedInfo($$3, $$4);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected Display.TextDisplay.TextRenderState getSubState(Display.TextDisplay $$0) {
/* 172 */       return $$0.textRenderState();
/*     */     }
/*     */     
/*     */     public void renderInner(Display.TextDisplay $$0, Display.TextDisplay.TextRenderState $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, float $$5) {
/*     */       int $$14;
/* 177 */       byte $$6 = $$1.flags();
/*     */       
/* 179 */       boolean $$7 = (($$6 & 0x2) != 0);
/* 180 */       boolean $$8 = (($$6 & 0x4) != 0);
/* 181 */       boolean $$9 = (($$6 & 0x1) != 0);
/* 182 */       Display.TextDisplay.Align $$10 = Display.TextDisplay.getAlign($$6);
/* 183 */       byte $$11 = (byte)$$1.textOpacity().get($$5);
/*     */       
/* 185 */       if ($$8) {
/* 186 */         float $$12 = (Minecraft.getInstance()).options.getBackgroundOpacity(0.25F);
/* 187 */         int $$13 = (int)($$12 * 255.0F) << 24;
/*     */       } else {
/* 189 */         $$14 = $$1.backgroundColor().get($$5);
/*     */       } 
/*     */       
/* 192 */       float $$15 = 0.0F;
/* 193 */       Matrix4f $$16 = $$2.last().pose();
/*     */       
/* 195 */       $$16.rotate(3.1415927F, 0.0F, 1.0F, 0.0F);
/* 196 */       $$16.scale(-0.025F, -0.025F, -0.025F);
/* 197 */       Display.TextDisplay.CachedInfo $$17 = $$0.cacheDisplay(this::splitLines);
/*     */ 
/*     */       
/* 200 */       Objects.requireNonNull(this.font); int $$18 = 9 + 1;
/*     */       
/* 202 */       int $$19 = $$17.width();
/* 203 */       int $$20 = $$17.lines().size() * $$18;
/* 204 */       $$16.translate(1.0F - $$19 / 2.0F, -$$20, 0.0F);
/*     */       
/* 206 */       if ($$14 != 0) {
/* 207 */         VertexConsumer $$21 = $$3.getBuffer($$7 ? RenderType.textBackgroundSeeThrough() : RenderType.textBackground());
/* 208 */         $$21.vertex($$16, -1.0F, -1.0F, 0.0F).color($$14).uv2($$4).endVertex();
/* 209 */         $$21.vertex($$16, -1.0F, $$20, 0.0F).color($$14).uv2($$4).endVertex();
/* 210 */         $$21.vertex($$16, $$19, $$20, 0.0F).color($$14).uv2($$4).endVertex();
/* 211 */         $$21.vertex($$16, $$19, -1.0F, 0.0F).color($$14).uv2($$4).endVertex();
/*     */       } 
/*     */       
/* 214 */       for (Display.TextDisplay.CachedLine $$22 : $$17.lines()) {
/* 215 */         switch ($$10) { default: throw new IncompatibleClassChangeError();
/*     */           case LEFT: 
/*     */           case RIGHT: 
/* 218 */           case CENTER: break; }  float $$23 = $$19 / 2.0F - $$22.width() / 2.0F;
/*     */         
/* 220 */         this.font.drawInBatch($$22.contents(), $$23, $$15, $$11 << 24 | 0xFFFFFF, $$9, $$16, $$3, $$7 ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.POLYGON_OFFSET, 0, $$4);
/* 221 */         $$15 += $$18;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\DisplayRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */