/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.Sheets;
/*    */ import net.minecraft.client.renderer.blockentity.SignRenderer;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.client.resources.model.Material;
/*    */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class SignEditScreen
/*    */   extends AbstractSignEditScreen {
/*    */   public static final float MAGIC_SCALE_NUMBER = 62.500004F;
/*    */   public static final float MAGIC_TEXT_SCALE = 0.9765628F;
/* 20 */   private static final Vector3f TEXT_SCALE = new Vector3f(0.9765628F, 0.9765628F, 0.9765628F);
/*    */   @Nullable
/*    */   private SignRenderer.SignModel signModel;
/*    */   
/*    */   public SignEditScreen(SignBlockEntity $$0, boolean $$1, boolean $$2) {
/* 25 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 30 */     super.init();
/*    */     
/* 32 */     this.signModel = SignRenderer.createSignModel(this.minecraft.getEntityModels(), this.woodType);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void offsetSign(GuiGraphics $$0, BlockState $$1) {
/* 37 */     super.offsetSign($$0, $$1);
/*    */     
/* 39 */     boolean $$2 = $$1.getBlock() instanceof net.minecraft.world.level.block.StandingSignBlock;
/* 40 */     if (!$$2) {
/* 41 */       $$0.pose().translate(0.0F, 35.0F, 0.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderSignBackground(GuiGraphics $$0, BlockState $$1) {
/* 47 */     if (this.signModel == null) {
/*    */       return;
/*    */     }
/*    */     
/* 51 */     boolean $$2 = $$1.getBlock() instanceof net.minecraft.world.level.block.StandingSignBlock;
/*    */     
/* 53 */     $$0.pose().translate(0.0F, 31.0F, 0.0F);
/* 54 */     $$0.pose().scale(62.500004F, 62.500004F, -62.500004F);
/*    */     
/* 56 */     Material $$3 = Sheets.getSignMaterial(this.woodType);
/* 57 */     Objects.requireNonNull(this.signModel); VertexConsumer $$4 = $$3.buffer((MultiBufferSource)$$0.bufferSource(), this.signModel::renderType);
/*    */     
/* 59 */     this.signModel.stick.visible = $$2;
/* 60 */     this.signModel.root.render($$0.pose(), $$4, 15728880, OverlayTexture.NO_OVERLAY);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vector3f getSignTextScale() {
/* 65 */     return TEXT_SCALE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\SignEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */