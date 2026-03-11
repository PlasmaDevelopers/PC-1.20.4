/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface EntityGetter
/*     */ {
/*     */   default <T extends Entity> List<T> getEntitiesOfClass(Class<T> $$0, AABB $$1, Predicate<? super T> $$2) {
/*  27 */     return getEntities(EntityTypeTest.forClass($$0), $$1, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default List<Entity> getEntities(@Nullable Entity $$0, AABB $$1) {
/*  33 */     return getEntities($$0, $$1, EntitySelector.NO_SPECTATORS);
/*     */   }
/*     */   
/*     */   default boolean isUnobstructed(@Nullable Entity $$0, VoxelShape $$1) {
/*  37 */     if ($$1.isEmpty()) {
/*  38 */       return true;
/*     */     }
/*     */     
/*  41 */     for (Entity $$2 : getEntities($$0, $$1.bounds())) {
/*  42 */       if (!$$2.isRemoved() && $$2.blocksBuilding && ($$0 == null || !$$2.isPassengerOfSameVehicle($$0)) && 
/*  43 */         Shapes.joinIsNotEmpty($$1, Shapes.create($$2.getBoundingBox()), BooleanOp.AND)) {
/*  44 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  48 */     return true;
/*     */   }
/*     */   
/*     */   default <T extends Entity> List<T> getEntitiesOfClass(Class<T> $$0, AABB $$1) {
/*  52 */     return getEntitiesOfClass($$0, $$1, EntitySelector.NO_SPECTATORS);
/*     */   }
/*     */ 
/*     */   
/*     */   default List<VoxelShape> getEntityCollisions(@Nullable Entity $$0, AABB $$1) {
/*  57 */     if ($$1.getSize() < 1.0E-7D) {
/*  58 */       return List.of();
/*     */     }
/*     */     
/*  61 */     Objects.requireNonNull($$0); Predicate<Entity> $$2 = ($$0 == null) ? EntitySelector.CAN_BE_COLLIDED_WITH : EntitySelector.NO_SPECTATORS.and($$0::canCollideWith);
/*  62 */     List<Entity> $$3 = getEntities($$0, $$1.inflate(1.0E-7D), $$2);
/*     */     
/*  64 */     if ($$3.isEmpty()) {
/*  65 */       return List.of();
/*     */     }
/*     */     
/*  68 */     ImmutableList.Builder<VoxelShape> $$4 = ImmutableList.builderWithExpectedSize($$3.size());
/*  69 */     for (Entity $$5 : $$3) {
/*  70 */       $$4.add(Shapes.create($$5.getBoundingBox()));
/*     */     }
/*     */     
/*  73 */     return (List<VoxelShape>)$$4.build();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default Player getNearestPlayer(double $$0, double $$1, double $$2, double $$3, @Nullable Predicate<Entity> $$4) {
/*  78 */     double $$5 = -1.0D;
/*  79 */     Player $$6 = null;
/*     */     
/*  81 */     for (Player $$7 : players()) {
/*  82 */       if ($$4 != null && !$$4.test($$7)) {
/*     */         continue;
/*     */       }
/*     */       
/*  86 */       double $$8 = $$7.distanceToSqr($$0, $$1, $$2);
/*  87 */       if (($$3 < 0.0D || $$8 < $$3 * $$3) && ($$5 == -1.0D || $$8 < $$5)) {
/*  88 */         $$5 = $$8;
/*  89 */         $$6 = $$7;
/*     */       } 
/*     */     } 
/*  92 */     return $$6;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default Player getNearestPlayer(Entity $$0, double $$1) {
/*  97 */     return getNearestPlayer($$0.getX(), $$0.getY(), $$0.getZ(), $$1, false);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default Player getNearestPlayer(double $$0, double $$1, double $$2, double $$3, boolean $$4) {
/* 102 */     Predicate<Entity> $$5 = $$4 ? EntitySelector.NO_CREATIVE_OR_SPECTATOR : EntitySelector.NO_SPECTATORS;
/* 103 */     return getNearestPlayer($$0, $$1, $$2, $$3, $$5);
/*     */   }
/*     */   
/*     */   default boolean hasNearbyAlivePlayer(double $$0, double $$1, double $$2, double $$3) {
/* 107 */     for (Player $$4 : players()) {
/* 108 */       if (!EntitySelector.NO_SPECTATORS.test($$4) || !EntitySelector.LIVING_ENTITY_STILL_ALIVE.test($$4)) {
/*     */         continue;
/*     */       }
/* 111 */       double $$5 = $$4.distanceToSqr($$0, $$1, $$2);
/* 112 */       if ($$3 < 0.0D || $$5 < $$3 * $$3) {
/* 113 */         return true;
/*     */       }
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default Player getNearestPlayer(TargetingConditions $$0, LivingEntity $$1) {
/* 121 */     return getNearestEntity(players(), $$0, $$1, $$1.getX(), $$1.getY(), $$1.getZ());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default Player getNearestPlayer(TargetingConditions $$0, LivingEntity $$1, double $$2, double $$3, double $$4) {
/* 126 */     return getNearestEntity(players(), $$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default Player getNearestPlayer(TargetingConditions $$0, double $$1, double $$2, double $$3) {
/* 131 */     return getNearestEntity(players(), $$0, null, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default <T extends LivingEntity> T getNearestEntity(Class<? extends T> $$0, TargetingConditions $$1, @Nullable LivingEntity $$2, double $$3, double $$4, double $$5, AABB $$6) {
/* 136 */     return getNearestEntity(getEntitiesOfClass($$0, $$6, $$0 -> true), $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   @Nullable
/*     */   default <T extends LivingEntity> T getNearestEntity(List<? extends T> $$0, TargetingConditions $$1, @Nullable LivingEntity $$2, double $$3, double $$4, double $$5) {
/*     */     LivingEntity livingEntity;
/* 141 */     double $$6 = -1.0D;
/* 142 */     T $$7 = null;
/* 143 */     for (LivingEntity livingEntity1 : $$0) {
/* 144 */       if (!$$1.test($$2, livingEntity1)) {
/*     */         continue;
/*     */       }
/*     */       
/* 148 */       double $$9 = livingEntity1.distanceToSqr($$3, $$4, $$5);
/* 149 */       if ($$6 == -1.0D || $$9 < $$6) {
/* 150 */         $$6 = $$9;
/* 151 */         livingEntity = livingEntity1;
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     return (T)livingEntity;
/*     */   }
/*     */   
/*     */   default List<Player> getNearbyPlayers(TargetingConditions $$0, LivingEntity $$1, AABB $$2) {
/* 159 */     List<Player> $$3 = Lists.newArrayList();
/* 160 */     for (Player $$4 : players()) {
/* 161 */       if ($$2.contains($$4.getX(), $$4.getY(), $$4.getZ()) && $$0.test($$1, (LivingEntity)$$4)) {
/* 162 */         $$3.add($$4);
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return $$3;
/*     */   }
/*     */   
/*     */   default <T extends LivingEntity> List<T> getNearbyEntities(Class<T> $$0, TargetingConditions $$1, LivingEntity $$2, AABB $$3) {
/* 170 */     List<T> $$4 = (List)getEntitiesOfClass((Class)$$0, $$3, $$0 -> true);
/* 171 */     List<T> $$5 = Lists.newArrayList();
/*     */     
/* 173 */     for (LivingEntity livingEntity : $$4) {
/* 174 */       if ($$1.test($$2, livingEntity)) {
/* 175 */         $$5.add((T)livingEntity);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return $$5;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default Player getPlayerByUUID(UUID $$0) {
/* 184 */     for (int $$1 = 0; $$1 < players().size(); $$1++) {
/* 185 */       Player $$2 = players().get($$1);
/* 186 */       if ($$0.equals($$2.getUUID())) {
/* 187 */         return $$2;
/*     */       }
/*     */     } 
/* 190 */     return null;
/*     */   }
/*     */   
/*     */   List<Entity> getEntities(@Nullable Entity paramEntity, AABB paramAABB, Predicate<? super Entity> paramPredicate);
/*     */   
/*     */   <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> paramEntityTypeTest, AABB paramAABB, Predicate<? super T> paramPredicate);
/*     */   
/*     */   List<? extends Player> players();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\EntityGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */