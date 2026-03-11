/*    */ package net.minecraft.world.entity.animal;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.entity.MobType;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*    */ 
/*    */ public abstract class WaterAnimal extends PathfinderMob {
/*    */   protected WaterAnimal(EntityType<? extends WaterAnimal> $$0, Level $$1) {
/* 20 */     super($$0, $$1);
/*    */     
/* 22 */     setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public MobType getMobType() {
/* 27 */     return MobType.WATER;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 32 */     return $$0.isUnobstructed((Entity)this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmbientSoundInterval() {
/* 37 */     return 120;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getExperienceReward() {
/* 42 */     return 1 + (level()).random.nextInt(3);
/*    */   }
/*    */   
/*    */   protected void handleAirSupply(int $$0) {
/* 46 */     if (isAlive() && !isInWaterOrBubble()) {
/* 47 */       setAirSupply($$0 - 1);
/* 48 */       if (getAirSupply() == -20) {
/* 49 */         setAirSupply(0);
/* 50 */         hurt(damageSources().drown(), 2.0F);
/*    */       } 
/*    */     } else {
/* 53 */       setAirSupply(300);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void baseTick() {
/* 59 */     int $$0 = getAirSupply();
/* 60 */     super.baseTick();
/* 61 */     handleAirSupply($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPushedByFluid() {
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeLeashed(Player $$0) {
/* 72 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends WaterAnimal> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 76 */     int $$5 = $$1.getSeaLevel();
/* 77 */     int $$6 = $$5 - 13;
/* 78 */     return ($$3.getY() >= $$6 && $$3
/* 79 */       .getY() <= $$5 && $$1
/* 80 */       .getFluidState($$3.below()).is(FluidTags.WATER) && $$1
/* 81 */       .getBlockState($$3.above()).is(Blocks.WATER));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\WaterAnimal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */