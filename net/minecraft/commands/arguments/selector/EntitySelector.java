/*     */ package net.minecraft.commands.arguments.selector;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class EntitySelector {
/*     */   public static final int INFINITE = 2147483647;
/*     */   public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_ARBITRARY = ($$0, $$1) -> {
/*     */     
/*     */     };
/*     */   
/*  31 */   private static final EntityTypeTest<Entity, ?> ANY_TYPE = new EntityTypeTest<Entity, Entity>()
/*     */     {
/*     */       public Entity tryCast(Entity $$0) {
/*  34 */         return $$0;
/*     */       }
/*     */ 
/*     */       
/*     */       public Class<? extends Entity> getBaseClass() {
/*  39 */         return Entity.class;
/*     */       }
/*     */     };
/*     */   
/*     */   private final int maxResults;
/*     */   private final boolean includesEntities;
/*     */   private final boolean worldLimited;
/*     */   private final Predicate<Entity> predicate;
/*     */   private final MinMaxBounds.Doubles range;
/*     */   private final Function<Vec3, Vec3> position;
/*     */   @Nullable
/*     */   private final AABB aabb;
/*     */   private final BiConsumer<Vec3, List<? extends Entity>> order;
/*     */   private final boolean currentEntity;
/*     */   @Nullable
/*     */   private final String playerName;
/*     */   @Nullable
/*     */   private final UUID entityUUID;
/*     */   private final EntityTypeTest<Entity, ?> type;
/*     */   private final boolean usesSelector;
/*     */   
/*     */   public EntitySelector(int $$0, boolean $$1, boolean $$2, Predicate<Entity> $$3, MinMaxBounds.Doubles $$4, Function<Vec3, Vec3> $$5, @Nullable AABB $$6, BiConsumer<Vec3, List<? extends Entity>> $$7, boolean $$8, @Nullable String $$9, @Nullable UUID $$10, @Nullable EntityType<?> $$11, boolean $$12) {
/*  61 */     this.maxResults = $$0;
/*  62 */     this.includesEntities = $$1;
/*  63 */     this.worldLimited = $$2;
/*  64 */     this.predicate = $$3;
/*  65 */     this.range = $$4;
/*  66 */     this.position = $$5;
/*  67 */     this.aabb = $$6;
/*  68 */     this.order = $$7;
/*  69 */     this.currentEntity = $$8;
/*  70 */     this.playerName = $$9;
/*  71 */     this.entityUUID = $$10;
/*  72 */     this.type = ($$11 == null) ? ANY_TYPE : (EntityTypeTest)$$11;
/*  73 */     this.usesSelector = $$12;
/*     */   }
/*     */   
/*     */   public int getMaxResults() {
/*  77 */     return this.maxResults;
/*     */   }
/*     */   
/*     */   public boolean includesEntities() {
/*  81 */     return this.includesEntities;
/*     */   }
/*     */   
/*     */   public boolean isSelfSelector() {
/*  85 */     return this.currentEntity;
/*     */   }
/*     */   
/*     */   public boolean isWorldLimited() {
/*  89 */     return this.worldLimited;
/*     */   }
/*     */   
/*     */   public boolean usesSelector() {
/*  93 */     return this.usesSelector;
/*     */   }
/*     */   
/*     */   private void checkPermissions(CommandSourceStack $$0) throws CommandSyntaxException {
/*  97 */     if (this.usesSelector && !$$0.hasPermission(2)) {
/*  98 */       throw EntityArgument.ERROR_SELECTORS_NOT_ALLOWED.create();
/*     */     }
/*     */   }
/*     */   
/*     */   public Entity findSingleEntity(CommandSourceStack $$0) throws CommandSyntaxException {
/* 103 */     checkPermissions($$0);
/*     */     
/* 105 */     List<? extends Entity> $$1 = findEntities($$0);
/* 106 */     if ($$1.isEmpty()) {
/* 107 */       throw EntityArgument.NO_ENTITIES_FOUND.create();
/*     */     }
/* 109 */     if ($$1.size() > 1) {
/* 110 */       throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
/*     */     }
/* 112 */     return $$1.get(0);
/*     */   }
/*     */   
/*     */   public List<? extends Entity> findEntities(CommandSourceStack $$0) throws CommandSyntaxException {
/* 116 */     return findEntitiesRaw($$0).stream().filter($$1 -> $$1.getType().isEnabled($$0.enabledFeatures())).toList();
/*     */   }
/*     */   
/*     */   private List<? extends Entity> findEntitiesRaw(CommandSourceStack $$0) throws CommandSyntaxException {
/* 120 */     checkPermissions($$0);
/*     */     
/* 122 */     if (!this.includesEntities) {
/* 123 */       return (List)findPlayers($$0);
/*     */     }
/* 125 */     if (this.playerName != null) {
/* 126 */       ServerPlayer $$1 = $$0.getServer().getPlayerList().getPlayerByName(this.playerName);
/* 127 */       if ($$1 == null) {
/* 128 */         return Collections.emptyList();
/*     */       }
/* 130 */       return Lists.newArrayList((Object[])new ServerPlayer[] { $$1 });
/*     */     } 
/* 132 */     if (this.entityUUID != null) {
/* 133 */       for (ServerLevel $$2 : $$0.getServer().getAllLevels()) {
/* 134 */         Entity $$3 = $$2.getEntity(this.entityUUID);
/* 135 */         if ($$3 != null) {
/* 136 */           return Lists.newArrayList((Object[])new Entity[] { $$3 });
/*     */         }
/*     */       } 
/* 139 */       return Collections.emptyList();
/*     */     } 
/* 141 */     Vec3 $$4 = this.position.apply($$0.getPosition());
/* 142 */     Predicate<Entity> $$5 = getPredicate($$4);
/*     */     
/* 144 */     if (this.currentEntity) {
/* 145 */       if ($$0.getEntity() != null && $$5.test($$0.getEntity())) {
/* 146 */         return Lists.newArrayList((Object[])new Entity[] { $$0.getEntity() });
/*     */       }
/* 148 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/* 152 */     List<Entity> $$6 = Lists.newArrayList();
/*     */     
/* 154 */     if (isWorldLimited()) {
/* 155 */       addEntities($$6, $$0.getLevel(), $$4, $$5);
/*     */     } else {
/* 157 */       for (ServerLevel $$7 : $$0.getServer().getAllLevels()) {
/* 158 */         addEntities($$6, $$7, $$4, $$5);
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return sortAndLimit($$4, $$6);
/*     */   }
/*     */   
/*     */   private void addEntities(List<Entity> $$0, ServerLevel $$1, Vec3 $$2, Predicate<Entity> $$3) {
/* 166 */     int $$4 = getResultLimit();
/* 167 */     if ($$0.size() >= $$4) {
/*     */       return;
/*     */     }
/* 170 */     if (this.aabb != null) {
/* 171 */       $$1.getEntities(this.type, this.aabb.move($$2), $$3, $$0, $$4);
/*     */     } else {
/* 173 */       $$1.getEntities(this.type, $$3, $$0, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getResultLimit() {
/* 179 */     return (this.order == ORDER_ARBITRARY) ? this.maxResults : Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public ServerPlayer findSinglePlayer(CommandSourceStack $$0) throws CommandSyntaxException {
/* 183 */     checkPermissions($$0);
/*     */     
/* 185 */     List<ServerPlayer> $$1 = findPlayers($$0);
/* 186 */     if ($$1.size() != 1) {
/* 187 */       throw EntityArgument.NO_PLAYERS_FOUND.create();
/*     */     }
/* 189 */     return $$1.get(0);
/*     */   }
/*     */   public List<ServerPlayer> findPlayers(CommandSourceStack $$0) throws CommandSyntaxException {
/*     */     List<ServerPlayer> $$8;
/* 193 */     checkPermissions($$0);
/*     */     
/* 195 */     if (this.playerName != null) {
/* 196 */       ServerPlayer $$1 = $$0.getServer().getPlayerList().getPlayerByName(this.playerName);
/* 197 */       if ($$1 == null) {
/* 198 */         return Collections.emptyList();
/*     */       }
/* 200 */       return Lists.newArrayList((Object[])new ServerPlayer[] { $$1 });
/*     */     } 
/* 202 */     if (this.entityUUID != null) {
/* 203 */       ServerPlayer $$2 = $$0.getServer().getPlayerList().getPlayer(this.entityUUID);
/* 204 */       if ($$2 == null) {
/* 205 */         return Collections.emptyList();
/*     */       }
/* 207 */       return Lists.newArrayList((Object[])new ServerPlayer[] { $$2 });
/*     */     } 
/*     */ 
/*     */     
/* 211 */     Vec3 $$3 = this.position.apply($$0.getPosition());
/* 212 */     Predicate<Entity> $$4 = getPredicate($$3);
/*     */     
/* 214 */     if (this.currentEntity) {
/* 215 */       Entity entity = $$0.getEntity(); if (entity instanceof ServerPlayer) { ServerPlayer $$5 = (ServerPlayer)entity;
/* 216 */         if ($$4.test($$5)) {
/* 217 */           return Lists.newArrayList((Object[])new ServerPlayer[] { $$5 });
/*     */         } }
/*     */       
/* 220 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 225 */     int $$6 = getResultLimit();
/* 226 */     if (isWorldLimited()) {
/* 227 */       List<ServerPlayer> $$7 = $$0.getLevel().getPlayers($$4, $$6);
/*     */     } else {
/* 229 */       $$8 = Lists.newArrayList();
/* 230 */       for (ServerPlayer $$9 : $$0.getServer().getPlayerList().getPlayers()) {
/* 231 */         if ($$4.test($$9)) {
/* 232 */           $$8.add($$9);
/* 233 */           if ($$8.size() >= $$6) {
/* 234 */             return $$8;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     return sortAndLimit($$3, $$8);
/*     */   }
/*     */   
/*     */   private Predicate<Entity> getPredicate(Vec3 $$0) {
/* 244 */     Predicate<Entity> $$1 = this.predicate;
/* 245 */     if (this.aabb != null) {
/* 246 */       AABB $$2 = this.aabb.move($$0);
/* 247 */       $$1 = $$1.and($$1 -> $$0.intersects($$1.getBoundingBox()));
/*     */     } 
/*     */     
/* 250 */     if (!this.range.isAny()) {
/* 251 */       $$1 = $$1.and($$1 -> this.range.matchesSqr($$1.distanceToSqr($$0)));
/*     */     }
/* 253 */     return $$1;
/*     */   }
/*     */   
/*     */   private <T extends Entity> List<T> sortAndLimit(Vec3 $$0, List<T> $$1) {
/* 257 */     if ($$1.size() > 1) {
/* 258 */       this.order.accept($$0, $$1);
/*     */     }
/*     */     
/* 261 */     return $$1.subList(0, Math.min(this.maxResults, $$1.size()));
/*     */   }
/*     */   
/*     */   public static Component joinNames(List<? extends Entity> $$0) {
/* 265 */     return ComponentUtils.formatList($$0, Entity::getDisplayName);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\selector\EntitySelector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */