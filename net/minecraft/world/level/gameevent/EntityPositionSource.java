/*    */ package net.minecraft.world.level.gameevent;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class EntityPositionSource implements PositionSource {
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.CODEC.fieldOf("source_entity").forGetter(EntityPositionSource::getUuid), (App)Codec.FLOAT.fieldOf("y_offset").orElse(Float.valueOf(0.0F)).forGetter(())).apply((Applicative)$$0, ()));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<EntityPositionSource> CODEC;
/*    */   private Either<Entity, Either<UUID, Integer>> entityOrUuidOrId;
/*    */   final float yOffset;
/*    */   
/*    */   public EntityPositionSource(Entity $$0, float $$1) {
/* 27 */     this(Either.left($$0), $$1);
/*    */   }
/*    */   
/*    */   EntityPositionSource(Either<Entity, Either<UUID, Integer>> $$0, float $$1) {
/* 31 */     this.entityOrUuidOrId = $$0;
/* 32 */     this.yOffset = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Vec3> getPosition(Level $$0) {
/* 37 */     if (this.entityOrUuidOrId.left().isEmpty()) {
/* 38 */       resolveEntity($$0);
/*    */     }
/* 40 */     return this.entityOrUuidOrId.left().map($$0 -> $$0.position().add(0.0D, this.yOffset, 0.0D));
/*    */   }
/*    */   
/*    */   private void resolveEntity(Level $$0) {
/* 44 */     ((Optional)this.entityOrUuidOrId.map(Optional::of, $$1 -> {
/*    */           Objects.requireNonNull($$0);
/*    */ 
/*    */ 
/*    */           
/*    */           return Optional.ofNullable((Entity)$$1.map((), $$0::getEntity));
/* 50 */         })).ifPresent($$0 -> this.entityOrUuidOrId = Either.left($$0));
/*    */   }
/*    */   
/*    */   private UUID getUuid() {
/* 54 */     return (UUID)this.entityOrUuidOrId.map(Entity::getUUID, $$0 -> (UUID)$$0.map(Function.identity(), ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   int getId() {
/* 66 */     return ((Integer)this.entityOrUuidOrId.map(Entity::getId, $$0 -> (Integer)$$0.map((), Function.identity()))).intValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PositionSourceType<?> getType() {
/* 79 */     return PositionSourceType.ENTITY;
/*    */   }
/*    */   
/*    */   public static class Type
/*    */     implements PositionSourceType<EntityPositionSource> {
/*    */     public EntityPositionSource read(FriendlyByteBuf $$0) {
/* 85 */       return new EntityPositionSource(Either.right(Either.right(Integer.valueOf($$0.readVarInt()))), $$0.readFloat());
/*    */     }
/*    */ 
/*    */     
/*    */     public void write(FriendlyByteBuf $$0, EntityPositionSource $$1) {
/* 90 */       $$0.writeVarInt($$1.getId());
/* 91 */       $$0.writeFloat($$1.yOffset);
/*    */     }
/*    */ 
/*    */     
/*    */     public Codec<EntityPositionSource> codec() {
/* 96 */       return EntityPositionSource.CODEC;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\EntityPositionSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */