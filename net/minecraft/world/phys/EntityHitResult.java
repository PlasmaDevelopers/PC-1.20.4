/*    */ package net.minecraft.world.phys;
/*    */ 
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class EntityHitResult extends HitResult {
/*    */   private final Entity entity;
/*    */   
/*    */   public EntityHitResult(Entity $$0) {
/*  9 */     this($$0, $$0.position());
/*    */   }
/*    */   
/*    */   public EntityHitResult(Entity $$0, Vec3 $$1) {
/* 13 */     super($$1);
/*    */     
/* 15 */     this.entity = $$0;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 19 */     return this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public HitResult.Type getType() {
/* 24 */     return HitResult.Type.ENTITY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\EntityHitResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */