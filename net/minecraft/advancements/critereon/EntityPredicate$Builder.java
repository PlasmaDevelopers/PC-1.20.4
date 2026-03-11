/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/* 162 */   private Optional<EntityTypePredicate> entityType = Optional.empty();
/* 163 */   private Optional<DistancePredicate> distanceToPlayer = Optional.empty();
/* 164 */   private Optional<LocationPredicate> location = Optional.empty();
/* 165 */   private Optional<LocationPredicate> steppingOnLocation = Optional.empty();
/* 166 */   private Optional<MobEffectsPredicate> effects = Optional.empty();
/* 167 */   private Optional<NbtPredicate> nbt = Optional.empty();
/* 168 */   private Optional<EntityFlagsPredicate> flags = Optional.empty();
/* 169 */   private Optional<EntityEquipmentPredicate> equipment = Optional.empty();
/* 170 */   private Optional<EntitySubPredicate> subPredicate = Optional.empty();
/* 171 */   private Optional<EntityPredicate> vehicle = Optional.empty();
/* 172 */   private Optional<EntityPredicate> passenger = Optional.empty();
/* 173 */   private Optional<EntityPredicate> targetedEntity = Optional.empty();
/* 174 */   private Optional<String> team = Optional.empty();
/*     */   
/*     */   public static Builder entity() {
/* 177 */     return new Builder();
/*     */   }
/*     */   
/*     */   public Builder of(EntityType<?> $$0) {
/* 181 */     this.entityType = Optional.of(EntityTypePredicate.of($$0));
/* 182 */     return this;
/*     */   }
/*     */   
/*     */   public Builder of(TagKey<EntityType<?>> $$0) {
/* 186 */     this.entityType = Optional.of(EntityTypePredicate.of($$0));
/* 187 */     return this;
/*     */   }
/*     */   
/*     */   public Builder entityType(EntityTypePredicate $$0) {
/* 191 */     this.entityType = Optional.of($$0);
/* 192 */     return this;
/*     */   }
/*     */   
/*     */   public Builder distance(DistancePredicate $$0) {
/* 196 */     this.distanceToPlayer = Optional.of($$0);
/* 197 */     return this;
/*     */   }
/*     */   
/*     */   public Builder located(LocationPredicate.Builder $$0) {
/* 201 */     this.location = Optional.of($$0.build());
/* 202 */     return this;
/*     */   }
/*     */   
/*     */   public Builder steppingOn(LocationPredicate.Builder $$0) {
/* 206 */     this.steppingOnLocation = Optional.of($$0.build());
/* 207 */     return this;
/*     */   }
/*     */   
/*     */   public Builder effects(MobEffectsPredicate.Builder $$0) {
/* 211 */     this.effects = $$0.build();
/* 212 */     return this;
/*     */   }
/*     */   
/*     */   public Builder nbt(NbtPredicate $$0) {
/* 216 */     this.nbt = Optional.of($$0);
/* 217 */     return this;
/*     */   }
/*     */   
/*     */   public Builder flags(EntityFlagsPredicate.Builder $$0) {
/* 221 */     this.flags = Optional.of($$0.build());
/* 222 */     return this;
/*     */   }
/*     */   
/*     */   public Builder equipment(EntityEquipmentPredicate.Builder $$0) {
/* 226 */     this.equipment = Optional.of($$0.build());
/* 227 */     return this;
/*     */   }
/*     */   
/*     */   public Builder equipment(EntityEquipmentPredicate $$0) {
/* 231 */     this.equipment = Optional.of($$0);
/* 232 */     return this;
/*     */   }
/*     */   
/*     */   public Builder subPredicate(EntitySubPredicate $$0) {
/* 236 */     this.subPredicate = Optional.of($$0);
/* 237 */     return this;
/*     */   }
/*     */   
/*     */   public Builder vehicle(Builder $$0) {
/* 241 */     this.vehicle = Optional.of($$0.build());
/* 242 */     return this;
/*     */   }
/*     */   
/*     */   public Builder passenger(Builder $$0) {
/* 246 */     this.passenger = Optional.of($$0.build());
/* 247 */     return this;
/*     */   }
/*     */   
/*     */   public Builder targetedEntity(Builder $$0) {
/* 251 */     this.targetedEntity = Optional.of($$0.build());
/* 252 */     return this;
/*     */   }
/*     */   
/*     */   public Builder team(String $$0) {
/* 256 */     this.team = Optional.of($$0);
/* 257 */     return this;
/*     */   }
/*     */   
/*     */   public EntityPredicate build() {
/* 261 */     return new EntityPredicate(this.entityType, this.distanceToPlayer, this.location, this.steppingOnLocation, this.effects, this.nbt, this.flags, this.equipment, this.subPredicate, this.vehicle, this.passenger, this.targetedEntity, this.team);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */