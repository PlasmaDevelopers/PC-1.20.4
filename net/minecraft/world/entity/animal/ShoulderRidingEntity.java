/*    */ package net.minecraft.world.entity.animal;
/*    */ 
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.TamableAnimal;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public abstract class ShoulderRidingEntity
/*    */   extends TamableAnimal
/*    */ {
/*    */   private static final int RIDE_COOLDOWN = 100;
/*    */   private int rideCooldownCounter;
/*    */   
/*    */   protected ShoulderRidingEntity(EntityType<? extends ShoulderRidingEntity> $$0, Level $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   public boolean setEntityOnShoulder(ServerPlayer $$0) {
/* 20 */     CompoundTag $$1 = new CompoundTag();
/* 21 */     $$1.putString("id", getEncodeId());
/* 22 */     saveWithoutId($$1);
/*    */     
/* 24 */     if ($$0.setEntityOnShoulder($$1)) {
/* 25 */       discard();
/* 26 */       return true;
/*    */     } 
/*    */     
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 34 */     this.rideCooldownCounter++;
/* 35 */     super.tick();
/*    */   }
/*    */   
/*    */   public boolean canSitOnShoulder() {
/* 39 */     return (this.rideCooldownCounter > 100);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\ShoulderRidingEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */