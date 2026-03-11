/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ 
/*     */ public abstract class AbstractSchoolingFish
/*     */   extends AbstractFish {
/*  19 */   private int schoolSize = 1; @Nullable
/*     */   private AbstractSchoolingFish leader;
/*     */   public AbstractSchoolingFish(EntityType<? extends AbstractSchoolingFish> $$0, Level $$1) {
/*  22 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  27 */     super.registerGoals();
/*     */     
/*  29 */     this.goalSelector.addGoal(5, (Goal)new FollowFlockLeaderGoal(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSpawnClusterSize() {
/*  34 */     return getMaxSchoolSize();
/*     */   }
/*     */   
/*     */   public int getMaxSchoolSize() {
/*  38 */     return super.getMaxSpawnClusterSize();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRandomSwim() {
/*  43 */     return !isFollower();
/*     */   }
/*     */   
/*     */   public boolean isFollower() {
/*  47 */     return (this.leader != null && this.leader.isAlive());
/*     */   }
/*     */   
/*     */   public AbstractSchoolingFish startFollowing(AbstractSchoolingFish $$0) {
/*  51 */     this.leader = $$0;
/*  52 */     $$0.addFollower();
/*     */     
/*  54 */     return $$0;
/*     */   }
/*     */   
/*     */   public void stopFollowing() {
/*  58 */     this.leader.removeFollower();
/*  59 */     this.leader = null;
/*     */   }
/*     */   
/*     */   private void addFollower() {
/*  63 */     this.schoolSize++;
/*     */   }
/*     */   
/*     */   private void removeFollower() {
/*  67 */     this.schoolSize--;
/*     */   }
/*     */   
/*     */   public boolean canBeFollowed() {
/*  71 */     return (hasFollowers() && this.schoolSize < getMaxSchoolSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  76 */     super.tick();
/*     */ 
/*     */     
/*  79 */     if (hasFollowers() && (level()).random.nextInt(200) == 1) {
/*  80 */       List<? extends AbstractFish> $$0 = level().getEntitiesOfClass(getClass(), getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
/*  81 */       if ($$0.size() <= 1) {
/*  82 */         this.schoolSize = 1;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasFollowers() {
/*  88 */     return (this.schoolSize > 1);
/*     */   }
/*     */   
/*     */   public boolean inRangeOfLeader() {
/*  92 */     return (distanceToSqr((Entity)this.leader) <= 121.0D);
/*     */   }
/*     */   
/*     */   public void pathToLeader() {
/*  96 */     if (isFollower()) {
/*  97 */       getNavigation().moveTo((Entity)this.leader, 1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addFollowers(Stream<? extends AbstractSchoolingFish> $$0) {
/* 102 */     $$0.limit((getMaxSchoolSize() - this.schoolSize)).filter($$0 -> ($$0 != this)).forEach($$0 -> $$0.startFollowing(this));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 108 */     super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 110 */     if ($$3 == null) {
/* 111 */       $$3 = new SchoolSpawnGroupData(this);
/*     */     } else {
/* 113 */       startFollowing(((SchoolSpawnGroupData)$$3).leader);
/*     */     } 
/*     */     
/* 116 */     return $$3;
/*     */   }
/*     */   
/*     */   public static class SchoolSpawnGroupData implements SpawnGroupData {
/*     */     public final AbstractSchoolingFish leader;
/*     */     
/*     */     public SchoolSpawnGroupData(AbstractSchoolingFish $$0) {
/* 123 */       this.leader = $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\AbstractSchoolingFish.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */