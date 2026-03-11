/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ 
/*     */ public final class EntityPredicate extends Record {
/*     */   private final Optional<EntityTypePredicate> entityType;
/*     */   private final Optional<DistancePredicate> distanceToPlayer;
/*     */   private final Optional<LocationPredicate> location;
/*     */   private final Optional<LocationPredicate> steppingOnLocation;
/*     */   private final Optional<MobEffectsPredicate> effects;
/*     */   private final Optional<NbtPredicate> nbt;
/*     */   
/*  26 */   public EntityPredicate(Optional<EntityTypePredicate> $$0, Optional<DistancePredicate> $$1, Optional<LocationPredicate> $$2, Optional<LocationPredicate> $$3, Optional<MobEffectsPredicate> $$4, Optional<NbtPredicate> $$5, Optional<EntityFlagsPredicate> $$6, Optional<EntityEquipmentPredicate> $$7, Optional<EntitySubPredicate> $$8, Optional<EntityPredicate> $$9, Optional<EntityPredicate> $$10, Optional<EntityPredicate> $$11, Optional<String> $$12) { this.entityType = $$0; this.distanceToPlayer = $$1; this.location = $$2; this.steppingOnLocation = $$3; this.effects = $$4; this.nbt = $$5; this.flags = $$6; this.equipment = $$7; this.subPredicate = $$8; this.vehicle = $$9; this.passenger = $$10; this.targetedEntity = $$11; this.team = $$12; } private final Optional<EntityFlagsPredicate> flags; private final Optional<EntityEquipmentPredicate> equipment; private final Optional<EntitySubPredicate> subPredicate; private final Optional<EntityPredicate> vehicle; private final Optional<EntityPredicate> passenger; private final Optional<EntityPredicate> targetedEntity; private final Optional<String> team; public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EntityPredicate;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityPredicate; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EntityPredicate;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EntityPredicate;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityPredicate;
/*  26 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<EntityTypePredicate> entityType() { return this.entityType; } public Optional<DistancePredicate> distanceToPlayer() { return this.distanceToPlayer; } public Optional<LocationPredicate> location() { return this.location; } public Optional<LocationPredicate> steppingOnLocation() { return this.steppingOnLocation; } public Optional<MobEffectsPredicate> effects() { return this.effects; } public Optional<NbtPredicate> nbt() { return this.nbt; } public Optional<EntityFlagsPredicate> flags() { return this.flags; } public Optional<EntityEquipmentPredicate> equipment() { return this.equipment; } public Optional<EntitySubPredicate> subPredicate() { return this.subPredicate; } public Optional<EntityPredicate> vehicle() { return this.vehicle; } public Optional<EntityPredicate> passenger() { return this.passenger; } public Optional<EntityPredicate> targetedEntity() { return this.targetedEntity; } public Optional<String> team() { return this.team; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public static final Codec<EntityPredicate> CODEC = ExtraCodecs.recursive("EntityPredicate", $$0 -> RecordCodecBuilder.create(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public static final Codec<ContextAwarePredicate> ADVANCEMENT_CODEC = ExtraCodecs.withAlternative(ContextAwarePredicate.CODEC, CODEC, EntityPredicate::wrap);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ContextAwarePredicate wrap(Builder $$0) {
/*  65 */     return wrap($$0.build());
/*     */   }
/*     */   
/*     */   public static Optional<ContextAwarePredicate> wrap(Optional<EntityPredicate> $$0) {
/*  69 */     return $$0.map(EntityPredicate::wrap);
/*     */   }
/*     */   
/*     */   public static List<ContextAwarePredicate> wrap(Builder... $$0) {
/*  73 */     return Stream.<Builder>of($$0).map(EntityPredicate::wrap).toList();
/*     */   }
/*     */   
/*     */   public static ContextAwarePredicate wrap(EntityPredicate $$0) {
/*  77 */     LootItemCondition $$1 = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, $$0).build();
/*  78 */     return new ContextAwarePredicate(List.of($$1));
/*     */   }
/*     */   
/*     */   public boolean matches(ServerPlayer $$0, @Nullable Entity $$1) {
/*  82 */     return matches($$0.serverLevel(), $$0.position(), $$1);
/*     */   }
/*     */   
/*     */   public boolean matches(ServerLevel $$0, @Nullable Vec3 $$1, @Nullable Entity $$2) {
/*  86 */     if ($$2 == null) {
/*  87 */       return false;
/*     */     }
/*  89 */     if (this.entityType.isPresent() && !((EntityTypePredicate)this.entityType.get()).matches($$2.getType())) {
/*  90 */       return false;
/*     */     }
/*  92 */     if ($$1 == null) {
/*  93 */       if (this.distanceToPlayer.isPresent()) {
/*  94 */         return false;
/*     */       }
/*     */     }
/*  97 */     else if (this.distanceToPlayer.isPresent() && !((DistancePredicate)this.distanceToPlayer.get()).matches($$1.x, $$1.y, $$1.z, $$2.getX(), $$2.getY(), $$2.getZ())) {
/*  98 */       return false;
/*     */     } 
/*     */     
/* 101 */     if (this.location.isPresent() && !((LocationPredicate)this.location.get()).matches($$0, $$2.getX(), $$2.getY(), $$2.getZ())) {
/* 102 */       return false;
/*     */     }
/*     */     
/* 105 */     if (this.steppingOnLocation.isPresent()) {
/* 106 */       Vec3 $$3 = Vec3.atCenterOf((Vec3i)$$2.getOnPos());
/* 107 */       if (!((LocationPredicate)this.steppingOnLocation.get()).matches($$0, $$3.x(), $$3.y(), $$3.z())) {
/* 108 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 112 */     if (this.effects.isPresent() && !((MobEffectsPredicate)this.effects.get()).matches($$2)) {
/* 113 */       return false;
/*     */     }
/* 115 */     if (this.nbt.isPresent() && !((NbtPredicate)this.nbt.get()).matches($$2)) {
/* 116 */       return false;
/*     */     }
/* 118 */     if (this.flags.isPresent() && !((EntityFlagsPredicate)this.flags.get()).matches($$2)) {
/* 119 */       return false;
/*     */     }
/*     */     
/* 122 */     if (this.equipment.isPresent() && !((EntityEquipmentPredicate)this.equipment.get()).matches($$2)) {
/* 123 */       return false;
/*     */     }
/*     */     
/* 126 */     if (this.subPredicate.isPresent() && !((EntitySubPredicate)this.subPredicate.get()).matches($$2, $$0, $$1)) {
/* 127 */       return false;
/*     */     }
/*     */     
/* 130 */     if (this.vehicle.isPresent() && !((EntityPredicate)this.vehicle.get()).matches($$0, $$1, $$2.getVehicle())) {
/* 131 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 135 */     if (this.passenger.isPresent() && $$2.getPassengers().stream().noneMatch($$2 -> ((EntityPredicate)this.passenger.get()).matches($$0, $$1, $$2))) {
/* 136 */       return false;
/*     */     }
/*     */     
/* 139 */     if (this.targetedEntity.isPresent() && !((EntityPredicate)this.targetedEntity.get()).matches($$0, $$1, ($$2 instanceof Mob) ? (Entity)((Mob)$$2).getTarget() : null)) {
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     if (this.team.isPresent()) {
/* 144 */       PlayerTeam playerTeam = $$2.getTeam();
/* 145 */       if (playerTeam == null || !((String)this.team.get()).equals(playerTeam.getName())) {
/* 146 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LootContext createContext(ServerPlayer $$0, Entity $$1) {
/* 157 */     LootParams $$2 = (new LootParams.Builder($$0.serverLevel())).withParameter(LootContextParams.THIS_ENTITY, $$1).withParameter(LootContextParams.ORIGIN, $$0.position()).create(LootContextParamSets.ADVANCEMENT_ENTITY);
/* 158 */     return (new LootContext.Builder($$2)).create(Optional.empty());
/*     */   }
/*     */   
/*     */   public static class Builder {
/* 162 */     private Optional<EntityTypePredicate> entityType = Optional.empty();
/* 163 */     private Optional<DistancePredicate> distanceToPlayer = Optional.empty();
/* 164 */     private Optional<LocationPredicate> location = Optional.empty();
/* 165 */     private Optional<LocationPredicate> steppingOnLocation = Optional.empty();
/* 166 */     private Optional<MobEffectsPredicate> effects = Optional.empty();
/* 167 */     private Optional<NbtPredicate> nbt = Optional.empty();
/* 168 */     private Optional<EntityFlagsPredicate> flags = Optional.empty();
/* 169 */     private Optional<EntityEquipmentPredicate> equipment = Optional.empty();
/* 170 */     private Optional<EntitySubPredicate> subPredicate = Optional.empty();
/* 171 */     private Optional<EntityPredicate> vehicle = Optional.empty();
/* 172 */     private Optional<EntityPredicate> passenger = Optional.empty();
/* 173 */     private Optional<EntityPredicate> targetedEntity = Optional.empty();
/* 174 */     private Optional<String> team = Optional.empty();
/*     */     
/*     */     public static Builder entity() {
/* 177 */       return new Builder();
/*     */     }
/*     */     
/*     */     public Builder of(EntityType<?> $$0) {
/* 181 */       this.entityType = Optional.of(EntityTypePredicate.of($$0));
/* 182 */       return this;
/*     */     }
/*     */     
/*     */     public Builder of(TagKey<EntityType<?>> $$0) {
/* 186 */       this.entityType = Optional.of(EntityTypePredicate.of($$0));
/* 187 */       return this;
/*     */     }
/*     */     
/*     */     public Builder entityType(EntityTypePredicate $$0) {
/* 191 */       this.entityType = Optional.of($$0);
/* 192 */       return this;
/*     */     }
/*     */     
/*     */     public Builder distance(DistancePredicate $$0) {
/* 196 */       this.distanceToPlayer = Optional.of($$0);
/* 197 */       return this;
/*     */     }
/*     */     
/*     */     public Builder located(LocationPredicate.Builder $$0) {
/* 201 */       this.location = Optional.of($$0.build());
/* 202 */       return this;
/*     */     }
/*     */     
/*     */     public Builder steppingOn(LocationPredicate.Builder $$0) {
/* 206 */       this.steppingOnLocation = Optional.of($$0.build());
/* 207 */       return this;
/*     */     }
/*     */     
/*     */     public Builder effects(MobEffectsPredicate.Builder $$0) {
/* 211 */       this.effects = $$0.build();
/* 212 */       return this;
/*     */     }
/*     */     
/*     */     public Builder nbt(NbtPredicate $$0) {
/* 216 */       this.nbt = Optional.of($$0);
/* 217 */       return this;
/*     */     }
/*     */     
/*     */     public Builder flags(EntityFlagsPredicate.Builder $$0) {
/* 221 */       this.flags = Optional.of($$0.build());
/* 222 */       return this;
/*     */     }
/*     */     
/*     */     public Builder equipment(EntityEquipmentPredicate.Builder $$0) {
/* 226 */       this.equipment = Optional.of($$0.build());
/* 227 */       return this;
/*     */     }
/*     */     
/*     */     public Builder equipment(EntityEquipmentPredicate $$0) {
/* 231 */       this.equipment = Optional.of($$0);
/* 232 */       return this;
/*     */     }
/*     */     
/*     */     public Builder subPredicate(EntitySubPredicate $$0) {
/* 236 */       this.subPredicate = Optional.of($$0);
/* 237 */       return this;
/*     */     }
/*     */     
/*     */     public Builder vehicle(Builder $$0) {
/* 241 */       this.vehicle = Optional.of($$0.build());
/* 242 */       return this;
/*     */     }
/*     */     
/*     */     public Builder passenger(Builder $$0) {
/* 246 */       this.passenger = Optional.of($$0.build());
/* 247 */       return this;
/*     */     }
/*     */     
/*     */     public Builder targetedEntity(Builder $$0) {
/* 251 */       this.targetedEntity = Optional.of($$0.build());
/* 252 */       return this;
/*     */     }
/*     */     
/*     */     public Builder team(String $$0) {
/* 256 */       this.team = Optional.of($$0);
/* 257 */       return this;
/*     */     }
/*     */     
/*     */     public EntityPredicate build() {
/* 261 */       return new EntityPredicate(this.entityType, this.distanceToPlayer, this.location, this.steppingOnLocation, this.effects, this.nbt, this.flags, this.equipment, this.subPredicate, this.vehicle, this.passenger, this.targetedEntity, this.team);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */