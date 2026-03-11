/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.scores.PlayerTeam;
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
/*     */ class EvokerSummonSpellGoal
/*     */   extends SpellcasterIllager.SpellcasterUseSpellGoal
/*     */ {
/*     */   private final TargetingConditions vexCountTargeting;
/*     */   
/*     */   EvokerSummonSpellGoal() {
/* 251 */     this.vexCountTargeting = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().ignoreInvisibilityTesting();
/*     */   }
/*     */   
/*     */   public boolean canUse() {
/* 255 */     if (!super.canUse()) {
/* 256 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 260 */     int $$0 = Evoker.this.level().getNearbyEntities(Vex.class, this.vexCountTargeting, (LivingEntity)Evoker.this, Evoker.this.getBoundingBox().inflate(16.0D)).size();
/* 261 */     return (Evoker.access$000(Evoker.this).nextInt(8) + 1 > $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getCastingTime() {
/* 266 */     return 100;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getCastingInterval() {
/* 271 */     return 340;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void performSpellCasting() {
/* 276 */     ServerLevel $$0 = (ServerLevel)Evoker.this.level();
/* 277 */     PlayerTeam $$1 = Evoker.this.getTeam();
/* 278 */     for (int $$2 = 0; $$2 < 3; $$2++) {
/* 279 */       BlockPos $$3 = Evoker.this.blockPosition().offset(-2 + Evoker.access$100(Evoker.this).nextInt(5), 1, -2 + Evoker.access$200(Evoker.this).nextInt(5));
/* 280 */       Vex $$4 = (Vex)EntityType.VEX.create(Evoker.this.level());
/* 281 */       if ($$4 != null) {
/* 282 */         $$4.moveTo($$3, 0.0F, 0.0F);
/* 283 */         $$4.finalizeSpawn((ServerLevelAccessor)$$0, Evoker.this.level().getCurrentDifficultyAt($$3), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
/* 284 */         $$4.setOwner((Mob)Evoker.this);
/* 285 */         $$4.setBoundOrigin($$3);
/* 286 */         $$4.setLimitedLife(20 * (30 + Evoker.access$300(Evoker.this).nextInt(90)));
/* 287 */         if ($$1 != null) {
/* 288 */           $$0.getScoreboard().addPlayerToTeam($$4.getScoreboardName(), $$1);
/*     */         }
/* 290 */         $$0.addFreshEntityWithPassengers((Entity)$$4);
/* 291 */         $$0.gameEvent(GameEvent.ENTITY_PLACE, $$3, GameEvent.Context.of((Entity)Evoker.this));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSpellPrepareSound() {
/* 298 */     return SoundEvents.EVOKER_PREPARE_SUMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SpellcasterIllager.IllagerSpell getSpell() {
/* 303 */     return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Evoker$EvokerSummonSpellGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */