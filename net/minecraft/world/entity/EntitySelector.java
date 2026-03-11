/*    */ package net.minecraft.world.entity;
/*    */ import com.google.common.base.Predicates;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.scores.PlayerTeam;
/*    */ import net.minecraft.world.scores.Team;
/*    */ 
/*    */ public final class EntitySelector {
/*    */   public static final Predicate<Entity> LIVING_ENTITY_STILL_ALIVE;
/*    */   public static final Predicate<Entity> ENTITY_NOT_BEING_RIDDEN;
/*    */   public static final Predicate<Entity> CONTAINER_ENTITY_SELECTOR;
/*    */   public static final Predicate<Entity> NO_CREATIVE_OR_SPECTATOR;
/*    */   public static final Predicate<Entity> NO_SPECTATORS;
/* 16 */   public static final Predicate<Entity> ENTITY_STILL_ALIVE = Entity::isAlive; static {
/* 17 */     LIVING_ENTITY_STILL_ALIVE = ($$0 -> ($$0.isAlive() && $$0 instanceof LivingEntity));
/* 18 */     ENTITY_NOT_BEING_RIDDEN = ($$0 -> ($$0.isAlive() && !$$0.isVehicle() && !$$0.isPassenger()));
/* 19 */     CONTAINER_ENTITY_SELECTOR = ($$0 -> ($$0 instanceof net.minecraft.world.Container && $$0.isAlive()));
/* 20 */     NO_CREATIVE_OR_SPECTATOR = ($$0 -> (!($$0 instanceof Player) || (!$$0.isSpectator() && !((Player)$$0).isCreative())));
/* 21 */     NO_SPECTATORS = ($$0 -> !$$0.isSpectator());
/* 22 */   } public static final Predicate<Entity> CAN_BE_COLLIDED_WITH = NO_SPECTATORS.and(Entity::canBeCollidedWith);
/*    */   
/*    */   public static class MobCanWearArmorEntitySelector implements Predicate<Entity> {
/*    */     private final ItemStack itemStack;
/*    */     
/*    */     public MobCanWearArmorEntitySelector(ItemStack $$0) {
/* 28 */       this.itemStack = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean test(@Nullable Entity $$0) {
/* 33 */       if (!$$0.isAlive()) {
/* 34 */         return false;
/*    */       }
/* 36 */       if (!($$0 instanceof LivingEntity)) {
/* 37 */         return false;
/*    */       }
/* 39 */       LivingEntity $$1 = (LivingEntity)$$0;
/* 40 */       return $$1.canTakeItem(this.itemStack);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Predicate<Entity> withinDistance(double $$0, double $$1, double $$2, double $$3) {
/* 45 */     double $$4 = $$3 * $$3;
/* 46 */     return $$4 -> ($$4 != null && $$4.distanceToSqr($$0, $$1, $$2) <= $$3);
/*    */   }
/*    */   
/*    */   public static Predicate<Entity> pushableBy(Entity $$0) {
/* 50 */     PlayerTeam playerTeam = $$0.getTeam();
/* 51 */     Team.CollisionRule $$2 = (playerTeam == null) ? Team.CollisionRule.ALWAYS : playerTeam.getCollisionRule();
/* 52 */     if ($$2 == Team.CollisionRule.NEVER) {
/* 53 */       return (Predicate<Entity>)Predicates.alwaysFalse();
/*    */     }
/* 55 */     return NO_SPECTATORS.and($$3 -> {
/*    */           if (!$$3.isPushable()) {
/*    */             return false;
/*    */           }
/*    */           if (($$0.level()).isClientSide && (!($$3 instanceof Player) || !((Player)$$3).isLocalPlayer())) {
/*    */             return false;
/*    */           }
/*    */           PlayerTeam playerTeam = $$3.getTeam();
/*    */           Team.CollisionRule $$5 = (playerTeam == null) ? Team.CollisionRule.ALWAYS : playerTeam.getCollisionRule();
/*    */           if ($$5 == Team.CollisionRule.NEVER) {
/*    */             return false;
/*    */           }
/* 67 */           boolean $$6 = ($$1 != null && $$1.isAlliedTo((Team)playerTeam));
/* 68 */           return (($$2 == Team.CollisionRule.PUSH_OWN_TEAM || $$5 == Team.CollisionRule.PUSH_OWN_TEAM) && $$6) ? false : (
/*    */ 
/*    */             
/* 71 */             !(($$2 == Team.CollisionRule.PUSH_OTHER_TEAMS || $$5 == Team.CollisionRule.PUSH_OTHER_TEAMS) && !$$6));
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Predicate<Entity> notRiding(Entity $$0) {
/* 79 */     return $$1 -> {
/*    */         while ($$1.isPassenger()) {
/*    */           $$1 = $$1.getVehicle();
/*    */           if ($$1 == $$0)
/*    */             return false; 
/*    */         } 
/*    */         return true;
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\EntitySelector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */