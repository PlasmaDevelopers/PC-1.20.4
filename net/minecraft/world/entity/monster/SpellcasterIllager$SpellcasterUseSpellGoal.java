/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SpellcasterUseSpellGoal
/*     */   extends Goal
/*     */ {
/*     */   protected int attackWarmupDelay;
/*     */   protected int nextAttackTickCount;
/*     */   
/*     */   public boolean canUse() {
/* 152 */     LivingEntity $$0 = SpellcasterIllager.this.getTarget();
/* 153 */     if ($$0 == null || !$$0.isAlive()) {
/* 154 */       return false;
/*     */     }
/* 156 */     if (SpellcasterIllager.this.isCastingSpell())
/*     */     {
/* 158 */       return false;
/*     */     }
/* 160 */     if (SpellcasterIllager.this.tickCount < this.nextAttackTickCount) {
/* 161 */       return false;
/*     */     }
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 168 */     LivingEntity $$0 = SpellcasterIllager.this.getTarget();
/* 169 */     return ($$0 != null && $$0.isAlive() && this.attackWarmupDelay > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 174 */     this.attackWarmupDelay = adjustedTickDelay(getCastWarmupTime());
/* 175 */     SpellcasterIllager.this.spellCastingTickCount = getCastingTime();
/* 176 */     this.nextAttackTickCount = SpellcasterIllager.this.tickCount + getCastingInterval();
/* 177 */     SoundEvent $$0 = getSpellPrepareSound();
/* 178 */     if ($$0 != null) {
/* 179 */       SpellcasterIllager.this.playSound($$0, 1.0F, 1.0F);
/*     */     }
/* 181 */     SpellcasterIllager.this.setIsCastingSpell(getSpell());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 186 */     this.attackWarmupDelay--;
/* 187 */     if (this.attackWarmupDelay == 0) {
/* 188 */       performSpellCasting();
/* 189 */       SpellcasterIllager.this.playSound(SpellcasterIllager.this.getCastingSoundEvent(), 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void performSpellCasting();
/*     */   
/*     */   protected int getCastWarmupTime() {
/* 196 */     return 20;
/*     */   }
/*     */   
/*     */   protected abstract int getCastingTime();
/*     */   
/*     */   protected abstract int getCastingInterval();
/*     */   
/*     */   @Nullable
/*     */   protected abstract SoundEvent getSpellPrepareSound();
/*     */   
/*     */   protected abstract SpellcasterIllager.IllagerSpell getSpell();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\SpellcasterIllager$SpellcasterUseSpellGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */