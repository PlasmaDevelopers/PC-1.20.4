/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.TrialSpawnerBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
/*    */ import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerData;
/*    */ 
/*    */ public class TrialSpawnerRenderer
/*    */   implements BlockEntityRenderer<TrialSpawnerBlockEntity> {
/*    */   public TrialSpawnerRenderer(BlockEntityRendererProvider.Context $$0) {
/* 16 */     this.entityRenderer = $$0.getEntityRenderer();
/*    */   }
/*    */   private final EntityRenderDispatcher entityRenderer;
/*    */   
/*    */   public void render(TrialSpawnerBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 21 */     Level $$6 = $$0.getLevel();
/* 22 */     if ($$6 == null) {
/*    */       return;
/*    */     }
/* 25 */     TrialSpawner $$7 = $$0.getTrialSpawner();
/* 26 */     TrialSpawnerData $$8 = $$7.getData();
/* 27 */     Entity $$9 = $$8.getOrCreateDisplayEntity($$7, $$6, $$7.getState());
/* 28 */     if ($$9 != null)
/* 29 */       SpawnerRenderer.renderEntityInSpawner($$1, $$2, $$3, $$4, $$9, this.entityRenderer, $$8.getOSpin(), $$8.getSpin()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\TrialSpawnerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */