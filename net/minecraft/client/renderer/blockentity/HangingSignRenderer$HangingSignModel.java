/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.model.Model;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
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
/*     */ public final class HangingSignModel
/*     */   extends Model
/*     */ {
/*     */   public final ModelPart root;
/*     */   public final ModelPart plank;
/*     */   public final ModelPart vChains;
/*     */   public final ModelPart normalChains;
/*     */   
/*     */   public HangingSignModel(ModelPart $$0) {
/* 157 */     super(RenderType::entityCutoutNoCull);
/* 158 */     this.root = $$0;
/* 159 */     this.plank = $$0.getChild("plank");
/* 160 */     this.normalChains = $$0.getChild("normalChains");
/* 161 */     this.vChains = $$0.getChild("vChains");
/*     */   }
/*     */   
/*     */   public void evaluateVisibleParts(BlockState $$0) {
/* 165 */     boolean $$1 = !($$0.getBlock() instanceof net.minecraft.world.level.block.CeilingHangingSignBlock);
/* 166 */     this.plank.visible = $$1;
/* 167 */     this.vChains.visible = false;
/* 168 */     this.normalChains.visible = true;
/*     */     
/* 170 */     if (!$$1) {
/* 171 */       boolean $$2 = ((Boolean)$$0.getValue((Property)BlockStateProperties.ATTACHED)).booleanValue();
/* 172 */       this.normalChains.visible = !$$2;
/* 173 */       this.vChains.visible = $$2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 179 */     this.root.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\HangingSignRenderer$HangingSignModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */