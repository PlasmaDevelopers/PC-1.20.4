/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public interface BlockEntityRenderer<T extends net.minecraft.world.level.block.entity.BlockEntity>
/*    */ {
/*    */   default boolean shouldRenderOffScreen(T $$0) {
/* 12 */     return false;
/*    */   }
/*    */   void render(T paramT, float paramFloat, PoseStack paramPoseStack, MultiBufferSource paramMultiBufferSource, int paramInt1, int paramInt2);
/*    */   default int getViewDistance() {
/* 16 */     return 64;
/*    */   }
/*    */   
/*    */   default boolean shouldRender(T $$0, Vec3 $$1) {
/* 20 */     return Vec3.atCenterOf((Vec3i)$$0.getBlockPos()).closerThan((Position)$$1, getViewDistance());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BlockEntityRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */