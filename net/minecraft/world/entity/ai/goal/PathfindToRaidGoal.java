/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.EnumSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.entity.raid.Raid;
/*    */ import net.minecraft.world.entity.raid.Raider;
/*    */ import net.minecraft.world.entity.raid.Raids;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class PathfindToRaidGoal<T extends Raider>
/*    */   extends Goal {
/*    */   private static final int RECRUITMENT_SEARCH_TICK_DELAY = 20;
/*    */   private static final float SPEED_MODIFIER = 1.0F;
/*    */   private final T mob;
/*    */   private int recruitmentTick;
/*    */   
/*    */   public PathfindToRaidGoal(T $$0) {
/* 24 */     this.mob = $$0;
/* 25 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 30 */     return (this.mob.getTarget() == null && 
/* 31 */       !this.mob.hasControllingPassenger() && this.mob
/* 32 */       .hasActiveRaid() && 
/* 33 */       !this.mob.getCurrentRaid().isOver() && 
/* 34 */       !((ServerLevel)this.mob.level()).isVillage(this.mob.blockPosition()));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 39 */     return (this.mob.hasActiveRaid() && 
/* 40 */       !this.mob.getCurrentRaid().isOver() && this.mob
/* 41 */       .level() instanceof ServerLevel && 
/* 42 */       !((ServerLevel)this.mob.level()).isVillage(this.mob.blockPosition()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 47 */     if (this.mob.hasActiveRaid()) {
/* 48 */       Raid $$0 = this.mob.getCurrentRaid();
/* 49 */       if (((Raider)this.mob).tickCount > this.recruitmentTick) {
/* 50 */         this.recruitmentTick = ((Raider)this.mob).tickCount + 20;
/* 51 */         recruitNearby($$0);
/*    */       } 
/*    */       
/* 54 */       if (!this.mob.isPathFinding()) {
/* 55 */         Vec3 $$1 = DefaultRandomPos.getPosTowards((PathfinderMob)this.mob, 15, 4, Vec3.atBottomCenterOf((Vec3i)$$0.getCenter()), 1.5707963705062866D);
/* 56 */         if ($$1 != null) {
/* 57 */           this.mob.getNavigation().moveTo($$1.x, $$1.y, $$1.z, 1.0D);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void recruitNearby(Raid $$0) {
/* 64 */     if ($$0.isActive()) {
/* 65 */       Set<Raider> $$1 = Sets.newHashSet();
/*    */       
/* 67 */       List<Raider> $$2 = this.mob.level().getEntitiesOfClass(Raider.class, this.mob.getBoundingBox().inflate(16.0D), $$1 -> (!$$1.hasActiveRaid() && Raids.canJoinRaid($$1, $$0)));
/* 68 */       $$1.addAll($$2);
/*    */       
/* 70 */       for (Raider $$3 : $$1)
/* 71 */         $$0.joinRaid($$0.getGroupsSpawned(), $$3, null, true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\PathfindToRaidGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */