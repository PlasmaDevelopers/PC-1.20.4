/*    */ package net.minecraft.client.renderer.debug;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.network.protocol.common.custom.BreezeDebugPayload;
/*    */ import net.minecraft.util.FastColor;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*    */ import net.minecraft.world.level.entity.EntityTypeTest;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class BreezeDebugRenderer {
/* 24 */   private static final int JUMP_TARGET_LINE_COLOR = FastColor.ARGB32.color(255, 255, 100, 255);
/* 25 */   private static final int TARGET_LINE_COLOR = FastColor.ARGB32.color(255, 100, 255, 255);
/* 26 */   private static final int INNER_CIRCLE_COLOR = FastColor.ARGB32.color(255, 0, 255, 0);
/* 27 */   private static final int MIDDLE_CIRCLE_COLOR = FastColor.ARGB32.color(255, 255, 165, 0);
/* 28 */   private static final int OUTER_CIRCLE_COLOR = FastColor.ARGB32.color(255, 255, 0, 0);
/*    */   
/*    */   private static final int CIRCLE_VERTICES = 20;
/*    */   
/*    */   private static final float SEGMENT_SIZE_RADIANS = 0.31415927F;
/*    */   private final Minecraft minecraft;
/* 34 */   private final Map<Integer, BreezeDebugPayload.BreezeInfo> perEntity = new HashMap<>();
/*    */   
/*    */   public BreezeDebugRenderer(Minecraft $$0) {
/* 37 */     this.minecraft = $$0;
/*    */   }
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 41 */     LocalPlayer $$5 = this.minecraft.player;
/*    */     
/* 43 */     $$5.level().getEntities((EntityTypeTest)EntityType.BREEZE, $$5.getBoundingBox().inflate(100.0D), $$0 -> true)
/* 44 */       .forEach($$6 -> {
/*    */           Optional<BreezeDebugPayload.BreezeInfo> $$7 = Optional.ofNullable(this.perEntity.get(Integer.valueOf($$6.getId())));
/*    */           $$7.map(BreezeDebugPayload.BreezeInfo::attackTarget).map(()).map(()).ifPresent(());
/*    */           $$7.map(BreezeDebugPayload.BreezeInfo::jumpTarget).ifPresent(());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void drawLine(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4, Vec3 $$5, Vec3 $$6, int $$7) {
/* 71 */     VertexConsumer $$8 = $$1.getBuffer(RenderType.debugLineStrip(2.0D));
/* 72 */     $$8.vertex($$0.last().pose(), (float)($$5.x - $$2), (float)($$5.y - $$3), (float)($$5.z - $$4)).color($$7).endVertex();
/* 73 */     $$8.vertex($$0.last().pose(), (float)($$6.x - $$2), (float)($$6.y - $$3), (float)($$6.z - $$4)).color($$7).endVertex();
/*    */   }
/*    */   
/*    */   private static void drawCircle(Matrix4f $$0, double $$1, double $$2, double $$3, VertexConsumer $$4, Vec3 $$5, float $$6, int $$7) {
/* 77 */     for (int $$8 = 0; $$8 < 20; $$8++) {
/* 78 */       drawCircleVertex($$8, $$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */     }
/* 80 */     drawCircleVertex(0, $$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */   }
/*    */   
/*    */   private static void drawCircleVertex(int $$0, Matrix4f $$1, double $$2, double $$3, double $$4, VertexConsumer $$5, Vec3 $$6, float $$7, int $$8) {
/* 84 */     float $$9 = $$0 * 0.31415927F;
/* 85 */     Vec3 $$10 = $$6.add($$7 * Math.cos($$9), 0.0D, $$7 * Math.sin($$9));
/* 86 */     $$5.vertex($$1, (float)($$10.x - $$2), (float)($$10.y - $$3), (float)($$10.z - $$4)).color($$8).endVertex();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 90 */     this.perEntity.clear();
/*    */   }
/*    */   
/*    */   public void add(BreezeDebugPayload.BreezeInfo $$0) {
/* 94 */     this.perEntity.put(Integer.valueOf($$0.id()), $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\BreezeDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */