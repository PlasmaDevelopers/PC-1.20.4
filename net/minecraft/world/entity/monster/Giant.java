/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityDimensions;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ 
/*    */ public class Giant extends Monster {
/*    */   public Giant(EntityType<? extends Giant> $$0, Level $$1) {
/* 15 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 20 */     return 10.440001F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float ridingOffset(Entity $$0) {
/* 25 */     return -3.75F;
/*    */   }
/*    */   
/*    */   public static AttributeSupplier.Builder createAttributes() {
/* 29 */     return Monster.createMonsterAttributes()
/* 30 */       .add(Attributes.MAX_HEALTH, 100.0D)
/* 31 */       .add(Attributes.MOVEMENT_SPEED, 0.5D)
/* 32 */       .add(Attributes.ATTACK_DAMAGE, 50.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 37 */     return $$1.getPathfindingCostFromLightLevels($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Giant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */