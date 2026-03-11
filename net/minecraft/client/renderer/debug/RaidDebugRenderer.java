/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RaidDebugRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer
/*    */ {
/*    */   private static final int MAX_RENDER_DIST = 160;
/*    */   private static final float TEXT_SCALE = 0.04F;
/*    */   private final Minecraft minecraft;
/* 21 */   private Collection<BlockPos> raidCenters = Lists.newArrayList();
/*    */   
/*    */   public RaidDebugRenderer(Minecraft $$0) {
/* 24 */     this.minecraft = $$0;
/*    */   }
/*    */   
/*    */   public void setRaidCenters(Collection<BlockPos> $$0) {
/* 28 */     this.raidCenters = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 33 */     BlockPos $$5 = getCamera().getBlockPosition();
/*    */     
/* 35 */     for (BlockPos $$6 : this.raidCenters) {
/* 36 */       if ($$5.closerThan((Vec3i)$$6, 160.0D)) {
/* 37 */         highlightRaidCenter($$0, $$1, $$6);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void highlightRaidCenter(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2) {
/* 43 */     DebugRenderer.renderFilledUnitCube($$0, $$1, $$2, 1.0F, 0.0F, 0.0F, 0.15F);
/* 44 */     int $$3 = -65536;
/* 45 */     renderTextOverBlock($$0, $$1, "Raid center", $$2, -65536);
/*    */   }
/*    */   
/*    */   private static void renderTextOverBlock(PoseStack $$0, MultiBufferSource $$1, String $$2, BlockPos $$3, int $$4) {
/* 49 */     double $$5 = $$3.getX() + 0.5D;
/* 50 */     double $$6 = $$3.getY() + 1.3D;
/* 51 */     double $$7 = $$3.getZ() + 0.5D;
/*    */     
/* 53 */     DebugRenderer.renderFloatingText($$0, $$1, $$2, $$5, $$6, $$7, $$4, 0.04F, true, 0.0F, true);
/*    */   }
/*    */   
/*    */   private Camera getCamera() {
/* 57 */     return this.minecraft.gameRenderer.getMainCamera();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\RaidDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */