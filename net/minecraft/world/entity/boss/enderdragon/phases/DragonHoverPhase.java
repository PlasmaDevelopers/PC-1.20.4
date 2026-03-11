/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class DragonHoverPhase
/*    */   extends AbstractDragonPhaseInstance {
/*    */   @Nullable
/*    */   private Vec3 targetLocation;
/*    */   
/*    */   public DragonHoverPhase(EnderDragon $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doServerTick() {
/* 18 */     if (this.targetLocation == null) {
/* 19 */       this.targetLocation = this.dragon.position();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSitting() {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin() {
/* 30 */     this.targetLocation = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFlySpeed() {
/* 35 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3 getFlyTargetLocation() {
/* 41 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragonPhase<DragonHoverPhase> getPhase() {
/* 46 */     return EnderDragonPhase.HOVERING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonHoverPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */