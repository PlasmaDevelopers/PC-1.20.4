/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.animal.Sheep;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EvokerWololoSpellGoal
/*     */   extends SpellcasterIllager.SpellcasterUseSpellGoal
/*     */ {
/*     */   private final TargetingConditions wololoTargeting;
/*     */   
/*     */   public EvokerWololoSpellGoal() {
/* 308 */     this.wololoTargeting = TargetingConditions.forNonCombat().range(16.0D).selector($$0 -> (((Sheep)$$0).getColor() == DyeColor.BLUE));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 313 */     if (Evoker.this.getTarget() != null)
/*     */     {
/* 315 */       return false;
/*     */     }
/* 317 */     if (Evoker.this.isCastingSpell())
/*     */     {
/* 319 */       return false;
/*     */     }
/* 321 */     if (Evoker.this.tickCount < this.nextAttackTickCount) {
/* 322 */       return false;
/*     */     }
/* 324 */     if (!Evoker.this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 325 */       return false;
/*     */     }
/*     */     
/* 328 */     List<Sheep> $$0 = Evoker.this.level().getNearbyEntities(Sheep.class, this.wololoTargeting, (LivingEntity)Evoker.this, Evoker.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
/*     */     
/* 330 */     if ($$0.isEmpty()) {
/* 331 */       return false;
/*     */     }
/* 333 */     Evoker.this.setWololoTarget($$0.get(Evoker.access$400(Evoker.this).nextInt($$0.size())));
/* 334 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 340 */     return (Evoker.this.getWololoTarget() != null && this.attackWarmupDelay > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 345 */     super.stop();
/* 346 */     Evoker.this.setWololoTarget((Sheep)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void performSpellCasting() {
/* 351 */     Sheep $$0 = Evoker.this.getWololoTarget();
/* 352 */     if ($$0 != null && $$0.isAlive()) {
/* 353 */       $$0.setColor(DyeColor.RED);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getCastWarmupTime() {
/* 359 */     return 40;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getCastingTime() {
/* 364 */     return 60;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getCastingInterval() {
/* 369 */     return 140;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSpellPrepareSound() {
/* 374 */     return SoundEvents.EVOKER_PREPARE_WOLOLO;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SpellcasterIllager.IllagerSpell getSpell() {
/* 379 */     return SpellcasterIllager.IllagerSpell.WOLOLO;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Evoker$EvokerWololoSpellGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */