/*    */ package net.minecraft.world.entity.player;
/*    */ 
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ 
/*    */ public class Abilities {
/*    */   public boolean invulnerable;
/*    */   public boolean flying;
/*    */   public boolean mayfly;
/*    */   public boolean instabuild;
/*    */   public boolean mayBuild = true;
/* 12 */   private float flyingSpeed = 0.05F;
/* 13 */   private float walkingSpeed = 0.1F;
/*    */   
/*    */   public void addSaveData(CompoundTag $$0) {
/* 16 */     CompoundTag $$1 = new CompoundTag();
/*    */     
/* 18 */     $$1.putBoolean("invulnerable", this.invulnerable);
/* 19 */     $$1.putBoolean("flying", this.flying);
/* 20 */     $$1.putBoolean("mayfly", this.mayfly);
/* 21 */     $$1.putBoolean("instabuild", this.instabuild);
/* 22 */     $$1.putBoolean("mayBuild", this.mayBuild);
/* 23 */     $$1.putFloat("flySpeed", this.flyingSpeed);
/* 24 */     $$1.putFloat("walkSpeed", this.walkingSpeed);
/* 25 */     $$0.put("abilities", (Tag)$$1);
/*    */   }
/*    */   
/*    */   public void loadSaveData(CompoundTag $$0) {
/* 29 */     if ($$0.contains("abilities", 10)) {
/* 30 */       CompoundTag $$1 = $$0.getCompound("abilities");
/*    */       
/* 32 */       this.invulnerable = $$1.getBoolean("invulnerable");
/* 33 */       this.flying = $$1.getBoolean("flying");
/* 34 */       this.mayfly = $$1.getBoolean("mayfly");
/* 35 */       this.instabuild = $$1.getBoolean("instabuild");
/*    */       
/* 37 */       if ($$1.contains("flySpeed", 99)) {
/* 38 */         this.flyingSpeed = $$1.getFloat("flySpeed");
/* 39 */         this.walkingSpeed = $$1.getFloat("walkSpeed");
/*    */       } 
/* 41 */       if ($$1.contains("mayBuild", 1)) {
/* 42 */         this.mayBuild = $$1.getBoolean("mayBuild");
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public float getFlyingSpeed() {
/* 48 */     return this.flyingSpeed;
/*    */   }
/*    */   
/*    */   public void setFlyingSpeed(float $$0) {
/* 52 */     this.flyingSpeed = $$0;
/*    */   }
/*    */   
/*    */   public float getWalkingSpeed() {
/* 56 */     return this.walkingSpeed;
/*    */   }
/*    */   
/*    */   public void setWalkingSpeed(float $$0) {
/* 60 */     this.walkingSpeed = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\player\Abilities.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */