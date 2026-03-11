/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.Lighting;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.model.PlayerModel;
/*     */ import net.minecraft.client.model.geom.EntityModelSet;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Model
/*     */   extends Record
/*     */ {
/*     */   private final PlayerModel<?> wideModel;
/*     */   private final PlayerModel<?> slimModel;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #91	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #91	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #91	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/client/gui/components/PlayerSkinWidget$Model;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   private Model(PlayerModel<?> $$0, PlayerModel<?> $$1) {
/*  91 */     this.wideModel = $$0; this.slimModel = $$1; } public PlayerModel<?> wideModel() { return this.wideModel; } public PlayerModel<?> slimModel() { return this.slimModel; }
/*     */    public static Model bake(EntityModelSet $$0) {
/*  93 */     PlayerModel<?> $$1 = new PlayerModel($$0.bakeLayer(ModelLayers.PLAYER), false);
/*  94 */     PlayerModel<?> $$2 = new PlayerModel($$0.bakeLayer(ModelLayers.PLAYER_SLIM), true);
/*  95 */     $$1.young = false;
/*  96 */     $$2.young = false;
/*  97 */     return new Model($$1, $$2);
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0, PlayerSkin $$1) {
/* 101 */     $$0.flush();
/* 102 */     Lighting.setupForEntityInInventory();
/*     */     
/* 104 */     $$0.pose().pushPose();
/* 105 */     $$0.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, 1.0F, -1.0F));
/* 106 */     $$0.pose().translate(0.0F, -1.5F, 0.0F);
/*     */     
/* 108 */     PlayerModel<?> $$2 = ($$1.model() == PlayerSkin.Model.SLIM) ? this.slimModel : this.wideModel;
/* 109 */     RenderType $$3 = $$2.renderType($$1.texture());
/* 110 */     $$2.renderToBuffer($$0.pose(), $$0.bufferSource().getBuffer($$3), 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 112 */     $$0.pose().popPose();
/*     */     
/* 114 */     $$0.flush();
/* 115 */     Lighting.setupFor3DItems();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\PlayerSkinWidget$Model.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */