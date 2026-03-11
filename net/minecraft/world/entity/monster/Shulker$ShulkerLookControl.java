/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
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
/*     */ class ShulkerLookControl
/*     */   extends LookControl
/*     */ {
/*     */   public ShulkerLookControl(Mob $$0) {
/* 118 */     super($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clampHeadRotationToBody() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected Optional<Float> getYRotD() {
/* 128 */     Direction $$0 = Shulker.this.getAttachFace().getOpposite();
/*     */ 
/*     */     
/* 131 */     Vector3f $$1 = $$0.getRotation().transform(new Vector3f((Vector3fc)Shulker.FORWARD));
/*     */     
/* 133 */     Vec3i $$2 = $$0.getNormal();
/* 134 */     Vector3f $$3 = new Vector3f($$2.getX(), $$2.getY(), $$2.getZ());
/* 135 */     $$3.cross((Vector3fc)$$1);
/*     */     
/* 137 */     double $$4 = this.wantedX - this.mob.getX();
/* 138 */     double $$5 = this.wantedY - this.mob.getEyeY();
/* 139 */     double $$6 = this.wantedZ - this.mob.getZ();
/*     */ 
/*     */     
/* 142 */     Vector3f $$7 = new Vector3f((float)$$4, (float)$$5, (float)$$6);
/* 143 */     float $$8 = $$3.dot((Vector3fc)$$7);
/* 144 */     float $$9 = $$1.dot((Vector3fc)$$7);
/*     */     
/* 146 */     return (Math.abs($$8) > 1.0E-5F || Math.abs($$9) > 1.0E-5F) ? Optional.<Float>of(Float.valueOf((float)(Mth.atan2(-$$8, $$9) * 57.2957763671875D))) : Optional.<Float>empty();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Optional<Float> getXRotD() {
/* 151 */     return Optional.of(Float.valueOf(0.0F));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Shulker$ShulkerLookControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */