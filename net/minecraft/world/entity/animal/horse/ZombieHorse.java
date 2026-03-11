/*    */ package net.minecraft.world.entity.animal.horse;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.AgeableMob;
/*    */ import net.minecraft.world.entity.EntityDimensions;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.entity.MobType;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ import net.minecraft.world.entity.animal.Animal;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.BlockAndTintGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ 
/*    */ public class ZombieHorse extends AbstractHorse {
/*    */   public ZombieHorse(EntityType<? extends ZombieHorse> $$0, Level $$1) {
/* 27 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public static AttributeSupplier.Builder createAttributes() {
/* 31 */     return createBaseHorseAttributes()
/* 32 */       .add(Attributes.MAX_HEALTH, 15.0D)
/* 33 */       .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
/*    */   }
/*    */   
/*    */   public static boolean checkZombieHorseSpawnRules(EntityType<? extends Animal> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 37 */     if (MobSpawnType.isSpawner($$2)) {
/* 38 */       return (MobSpawnType.ignoresLightRequirements($$2) || isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*    */     }
/* 40 */     return Animal.checkAnimalSpawnRules($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void randomizeAttributes(RandomSource $$0) {
/* 45 */     Objects.requireNonNull($$0); getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(generateJumpStrength($$0::nextDouble));
/*    */   }
/*    */ 
/*    */   
/*    */   public MobType getMobType() {
/* 50 */     return MobType.UNDEAD;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 55 */     return SoundEvents.ZOMBIE_HORSE_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 60 */     return SoundEvents.ZOMBIE_HORSE_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 65 */     return SoundEvents.ZOMBIE_HORSE_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 71 */     return (AgeableMob)EntityType.ZOMBIE_HORSE.create((Level)$$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 76 */     if (!isTamed()) {
/* 77 */       return InteractionResult.PASS;
/*    */     }
/* 79 */     return super.mobInteract($$0, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addBehaviourGoals() {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected float getPassengersRidingOffsetY(EntityDimensions $$0, float $$1) {
/* 89 */     return $$0.height - (isBaby() ? 0.03125F : 0.28125F) * $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\ZombieHorse.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */