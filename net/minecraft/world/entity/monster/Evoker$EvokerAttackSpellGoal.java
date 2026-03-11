/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.projectile.EvokerFangs;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EvokerAttackSpellGoal
/*     */   extends SpellcasterIllager.SpellcasterUseSpellGoal
/*     */ {
/*     */   protected int getCastingTime() {
/* 176 */     return 40;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getCastingInterval() {
/* 181 */     return 100;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void performSpellCasting() {
/* 187 */     LivingEntity $$0 = Evoker.this.getTarget();
/* 188 */     double $$1 = Math.min($$0.getY(), Evoker.this.getY());
/* 189 */     double $$2 = Math.max($$0.getY(), Evoker.this.getY()) + 1.0D;
/* 190 */     float $$3 = (float)Mth.atan2($$0.getZ() - Evoker.this.getZ(), $$0.getX() - Evoker.this.getX());
/* 191 */     if (Evoker.this.distanceToSqr((Entity)$$0) < 9.0D) {
/*     */       
/* 193 */       for (int $$4 = 0; $$4 < 5; $$4++) {
/* 194 */         float $$5 = $$3 + $$4 * 3.1415927F * 0.4F;
/* 195 */         createSpellEntity(Evoker.this.getX() + Mth.cos($$5) * 1.5D, Evoker.this.getZ() + Mth.sin($$5) * 1.5D, $$1, $$2, $$5, 0);
/*     */       } 
/*     */       
/* 198 */       for (int $$6 = 0; $$6 < 8; $$6++) {
/* 199 */         float $$7 = $$3 + $$6 * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
/* 200 */         createSpellEntity(Evoker.this.getX() + Mth.cos($$7) * 2.5D, Evoker.this.getZ() + Mth.sin($$7) * 2.5D, $$1, $$2, $$7, 3);
/*     */       } 
/*     */     } else {
/*     */       
/* 204 */       for (int $$8 = 0; $$8 < 16; $$8++) {
/* 205 */         double $$9 = 1.25D * ($$8 + 1);
/* 206 */         int $$10 = 1 * $$8;
/* 207 */         createSpellEntity(Evoker.this.getX() + Mth.cos($$3) * $$9, Evoker.this.getZ() + Mth.sin($$3) * $$9, $$1, $$2, $$3, $$10);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createSpellEntity(double $$0, double $$1, double $$2, double $$3, float $$4, int $$5) {
/* 214 */     BlockPos $$6 = BlockPos.containing($$0, $$3, $$1);
/* 215 */     boolean $$7 = false;
/* 216 */     double $$8 = 0.0D;
/*     */     do {
/* 218 */       BlockPos $$9 = $$6.below();
/* 219 */       BlockState $$10 = Evoker.this.level().getBlockState($$9);
/* 220 */       if ($$10.isFaceSturdy((BlockGetter)Evoker.this.level(), $$9, Direction.UP)) {
/* 221 */         if (!Evoker.this.level().isEmptyBlock($$6)) {
/* 222 */           BlockState $$11 = Evoker.this.level().getBlockState($$6);
/* 223 */           VoxelShape $$12 = $$11.getCollisionShape((BlockGetter)Evoker.this.level(), $$6);
/* 224 */           if (!$$12.isEmpty()) {
/* 225 */             $$8 = $$12.max(Direction.Axis.Y);
/*     */           }
/*     */         } 
/* 228 */         $$7 = true;
/*     */         break;
/*     */       } 
/* 231 */       $$6 = $$6.below();
/* 232 */     } while ($$6.getY() >= Mth.floor($$2) - 1);
/* 233 */     if ($$7) {
/* 234 */       Evoker.this.level().addFreshEntity((Entity)new EvokerFangs(Evoker.this.level(), $$0, $$6.getY() + $$8, $$1, $$4, $$5, (LivingEntity)Evoker.this));
/* 235 */       Evoker.this.level().gameEvent(GameEvent.ENTITY_PLACE, new Vec3($$0, $$6.getY() + $$8, $$1), GameEvent.Context.of((Entity)Evoker.this));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSpellPrepareSound() {
/* 241 */     return SoundEvents.EVOKER_PREPARE_ATTACK;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SpellcasterIllager.IllagerSpell getSpell() {
/* 246 */     return SpellcasterIllager.IllagerSpell.FANGS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Evoker$EvokerAttackSpellGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */