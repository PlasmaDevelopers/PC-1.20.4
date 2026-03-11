/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderBuffers;
/*    */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ItemPickupParticle
/*    */   extends Particle
/*    */ {
/*    */   private static final int LIFE_TIME = 3;
/*    */   private final RenderBuffers renderBuffers;
/*    */   private final Entity itemEntity;
/*    */   private final Entity target;
/*    */   private int life;
/*    */   private final EntityRenderDispatcher entityRenderDispatcher;
/*    */   private double targetX;
/*    */   private double targetY;
/*    */   private double targetZ;
/*    */   private double targetXOld;
/*    */   private double targetYOld;
/*    */   private double targetZOld;
/*    */   
/*    */   public ItemPickupParticle(EntityRenderDispatcher $$0, RenderBuffers $$1, ClientLevel $$2, Entity $$3, Entity $$4) {
/* 32 */     this($$0, $$1, $$2, $$3, $$4, $$3.getDeltaMovement());
/*    */   }
/*    */   
/*    */   private ItemPickupParticle(EntityRenderDispatcher $$0, RenderBuffers $$1, ClientLevel $$2, Entity $$3, Entity $$4, Vec3 $$5) {
/* 36 */     super($$2, $$3.getX(), $$3.getY(), $$3.getZ(), $$5.x, $$5.y, $$5.z);
/* 37 */     this.renderBuffers = $$1;
/* 38 */     this.itemEntity = getSafeCopy($$3);
/* 39 */     this.target = $$4;
/* 40 */     this.entityRenderDispatcher = $$0;
/* 41 */     updatePosition();
/* 42 */     saveOldPosition();
/*    */   }
/*    */   
/*    */   private Entity getSafeCopy(Entity $$0) {
/* 46 */     if (!($$0 instanceof ItemEntity)) {
/* 47 */       return $$0;
/*    */     }
/*    */     
/* 50 */     return (Entity)((ItemEntity)$$0).copy();
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 55 */     return ParticleRenderType.CUSTOM;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/* 60 */     float $$3 = (this.life + $$2) / 3.0F;
/* 61 */     $$3 *= $$3;
/*    */     
/* 63 */     double $$4 = Mth.lerp($$2, this.targetXOld, this.targetX);
/* 64 */     double $$5 = Mth.lerp($$2, this.targetYOld, this.targetY);
/* 65 */     double $$6 = Mth.lerp($$2, this.targetZOld, this.targetZ);
/*    */     
/* 67 */     double $$7 = Mth.lerp($$3, this.itemEntity.getX(), $$4);
/* 68 */     double $$8 = Mth.lerp($$3, this.itemEntity.getY(), $$5);
/* 69 */     double $$9 = Mth.lerp($$3, this.itemEntity.getZ(), $$6);
/*    */     
/* 71 */     MultiBufferSource.BufferSource $$10 = this.renderBuffers.bufferSource();
/* 72 */     Vec3 $$11 = $$1.getPosition();
/* 73 */     this.entityRenderDispatcher.render(this.itemEntity, $$7 - $$11.x(), $$8 - $$11.y(), $$9 - $$11.z(), this.itemEntity.getYRot(), $$2, new PoseStack(), (MultiBufferSource)$$10, this.entityRenderDispatcher.getPackedLightCoords(this.itemEntity, $$2));
/* 74 */     $$10.endBatch();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 79 */     this.life++;
/* 80 */     if (this.life == 3) {
/* 81 */       remove();
/*    */     }
/* 83 */     saveOldPosition();
/* 84 */     updatePosition();
/*    */   }
/*    */   
/*    */   private void updatePosition() {
/* 88 */     this.targetX = this.target.getX();
/* 89 */     this.targetY = (this.target.getY() + this.target.getEyeY()) / 2.0D;
/* 90 */     this.targetZ = this.target.getZ();
/*    */   }
/*    */   
/*    */   private void saveOldPosition() {
/* 94 */     this.targetXOld = this.targetX;
/* 95 */     this.targetYOld = this.targetY;
/* 96 */     this.targetZOld = this.targetZ;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ItemPickupParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */