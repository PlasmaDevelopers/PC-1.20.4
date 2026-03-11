/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.Lighting;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.client.model.PlayerModel;
/*     */ import net.minecraft.client.model.geom.EntityModelSet;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.client.sounds.SoundManager;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionfc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerSkinWidget
/*     */   extends AbstractWidget
/*     */ {
/*     */   private static final float MODEL_OFFSET = 0.0625F;
/*     */   private static final float MODEL_HEIGHT = 2.125F;
/*     */   private static final float Z_OFFSET = 100.0F;
/*     */   private static final float ROTATION_SENSITIVITY = 2.5F;
/*     */   private static final float DEFAULT_ROTATION_X = -5.0F;
/*     */   private static final float DEFAULT_ROTATION_Y = 30.0F;
/*     */   private static final float ROTATION_X_LIMIT = 50.0F;
/*     */   private final Model model;
/*     */   private final Supplier<PlayerSkin> skin;
/*  39 */   private float rotationX = -5.0F;
/*  40 */   private float rotationY = 30.0F;
/*     */   
/*     */   public PlayerSkinWidget(int $$0, int $$1, EntityModelSet $$2, Supplier<PlayerSkin> $$3) {
/*  43 */     super(0, 0, $$0, $$1, CommonComponents.EMPTY);
/*  44 */     this.model = Model.bake($$2);
/*  45 */     this.skin = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  50 */     $$0.pose().pushPose();
/*  51 */     $$0.pose().translate(getX() + getWidth() / 2.0F, (getY() + getHeight()), 100.0F);
/*  52 */     float $$4 = getHeight() / 2.125F;
/*  53 */     $$0.pose().scale($$4, $$4, $$4);
/*  54 */     $$0.pose().translate(0.0F, -0.0625F, 0.0F);
/*     */ 
/*     */     
/*  57 */     Matrix4f $$5 = $$0.pose().last().pose();
/*  58 */     $$5.rotateAround((Quaternionfc)Axis.XP.rotationDegrees(this.rotationX), 0.0F, -1.0625F, 0.0F);
/*  59 */     $$0.pose().mulPose(Axis.YP.rotationDegrees(this.rotationY));
/*     */     
/*  61 */     this.model.render($$0, this.skin.get());
/*     */     
/*  63 */     $$0.pose().popPose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDrag(double $$0, double $$1, double $$2, double $$3) {
/*  68 */     this.rotationX = Mth.clamp(this.rotationX - (float)$$3 * 2.5F, -50.0F, 50.0F);
/*  69 */     this.rotationY += (float)$$2 * 2.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDownSound(SoundManager $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateWidgetNarration(NarrationElementOutput $$0) {}
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/*  88 */     return null;
/*     */   }
/*     */   private static final class Model extends Record { private final PlayerModel<?> wideModel; private final PlayerModel<?> slimModel;
/*  91 */     private Model(PlayerModel<?> $$0, PlayerModel<?> $$1) { this.wideModel = $$0; this.slimModel = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #91	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  91 */       //   0	7	0	this	Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model; } public PlayerModel<?> wideModel() { return this.wideModel; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #91	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #91	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;
/*  91 */       //   0	8	1	$$0	Ljava/lang/Object; } public PlayerModel<?> slimModel() { return this.slimModel; }
/*     */      public static Model bake(EntityModelSet $$0) {
/*  93 */       PlayerModel<?> $$1 = new PlayerModel($$0.bakeLayer(ModelLayers.PLAYER), false);
/*  94 */       PlayerModel<?> $$2 = new PlayerModel($$0.bakeLayer(ModelLayers.PLAYER_SLIM), true);
/*  95 */       $$1.young = false;
/*  96 */       $$2.young = false;
/*  97 */       return new Model($$1, $$2);
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, PlayerSkin $$1) {
/* 101 */       $$0.flush();
/* 102 */       Lighting.setupForEntityInInventory();
/*     */       
/* 104 */       $$0.pose().pushPose();
/* 105 */       $$0.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, 1.0F, -1.0F));
/* 106 */       $$0.pose().translate(0.0F, -1.5F, 0.0F);
/*     */       
/* 108 */       PlayerModel<?> $$2 = ($$1.model() == PlayerSkin.Model.SLIM) ? this.slimModel : this.wideModel;
/* 109 */       RenderType $$3 = $$2.renderType($$1.texture());
/* 110 */       $$2.renderToBuffer($$0.pose(), $$0.bufferSource().getBuffer($$3), 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 112 */       $$0.pose().popPose();
/*     */       
/* 114 */       $$0.flush();
/* 115 */       Lighting.setupFor3DItems();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\PlayerSkinWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */