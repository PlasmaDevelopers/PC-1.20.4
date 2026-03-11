/*     */ package net.minecraft.world.entity.animal;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.ItemUtils;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Cow extends Animal {
/*     */   public Cow(EntityType<? extends Cow> $$0, Level $$1) {
/*  39 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  44 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  45 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 2.0D));
/*  46 */     this.goalSelector.addGoal(2, (Goal)new BreedGoal(this, 1.0D));
/*  47 */     this.goalSelector.addGoal(3, (Goal)new TemptGoal((PathfinderMob)this, 1.25D, Ingredient.of(new ItemLike[] { (ItemLike)Items.WHEAT }, ), false));
/*  48 */     this.goalSelector.addGoal(4, (Goal)new FollowParentGoal(this, 1.25D));
/*  49 */     this.goalSelector.addGoal(5, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0D));
/*  50 */     this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/*  51 */     this.goalSelector.addGoal(7, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  55 */     return Mob.createMobAttributes()
/*  56 */       .add(Attributes.MAX_HEALTH, 10.0D)
/*  57 */       .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  62 */     return SoundEvents.COW_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  67 */     return SoundEvents.COW_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  72 */     return SoundEvents.COW_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/*  77 */     playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  82 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/*  87 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*  88 */     if ($$2.is(Items.BUCKET) && !isBaby()) {
/*  89 */       $$0.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
/*  90 */       ItemStack $$3 = ItemUtils.createFilledResult($$2, $$0, Items.MILK_BUCKET.getDefaultInstance());
/*  91 */       $$0.setItemInHand($$1, $$3);
/*  92 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*  94 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Cow getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 100 */     return (Cow)EntityType.COW.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 105 */     if (isBaby()) {
/* 106 */       return $$1.height * 0.95F;
/*     */     }
/* 108 */     return 1.3F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 113 */     return new Vector3f(0.0F, $$1.height - 0.03125F * $$2, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Cow.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */