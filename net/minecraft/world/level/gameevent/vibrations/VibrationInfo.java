/*    */ package net.minecraft.world.level.gameevent.vibrations;
/*    */ 
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public final class VibrationInfo extends Record {
/*    */   private final GameEvent gameEvent;
/*    */   private final float distance;
/*    */   private final Vec3 pos;
/*    */   @Nullable
/*    */   private final UUID uuid;
/*    */   @Nullable
/*    */   private final UUID projectileOwnerUuid;
/*    */   @Nullable
/*    */   private final Entity entity;
/*    */   public static final Codec<VibrationInfo> CODEC;
/*    */   
/* 17 */   public VibrationInfo(GameEvent $$0, float $$1, Vec3 $$2, @Nullable UUID $$3, @Nullable UUID $$4, @Nullable Entity $$5) { this.gameEvent = $$0; this.distance = $$1; this.pos = $$2; this.uuid = $$3; this.projectileOwnerUuid = $$4; this.entity = $$5; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/gameevent/vibrations/VibrationInfo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 17 */     //   0	7	0	this	Lnet/minecraft/world/level/gameevent/vibrations/VibrationInfo; } public GameEvent gameEvent() { return this.gameEvent; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/gameevent/vibrations/VibrationInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/gameevent/vibrations/VibrationInfo; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/gameevent/vibrations/VibrationInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/gameevent/vibrations/VibrationInfo;
/* 17 */     //   0	8	1	$$0	Ljava/lang/Object; } public float distance() { return this.distance; } public Vec3 pos() { return this.pos; } @Nullable public UUID uuid() { return this.uuid; } @Nullable public UUID projectileOwnerUuid() { return this.projectileOwnerUuid; } @Nullable public Entity entity() { return this.entity; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 25 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.GAME_EVENT.byNameCodec().fieldOf("game_event").forGetter(VibrationInfo::gameEvent), (App)Codec.floatRange(0.0F, Float.MAX_VALUE).fieldOf("distance").forGetter(VibrationInfo::distance), (App)Vec3.CODEC.fieldOf("pos").forGetter(VibrationInfo::pos), (App)UUIDUtil.CODEC.optionalFieldOf("source").forGetter(()), (App)UUIDUtil.CODEC.optionalFieldOf("projectile_owner").forGetter(())).apply((Applicative)$$0, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public VibrationInfo(GameEvent $$0, float $$1, Vec3 $$2, @Nullable UUID $$3, @Nullable UUID $$4) {
/* 34 */     this($$0, $$1, $$2, $$3, $$4, null);
/*    */   }
/*    */   
/*    */   public VibrationInfo(GameEvent $$0, float $$1, Vec3 $$2, @Nullable Entity $$3) {
/* 38 */     this($$0, $$1, $$2, ($$3 == null) ? null : $$3.getUUID(), getProjectileOwner($$3), $$3);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static UUID getProjectileOwner(@Nullable Entity $$0) {
/* 43 */     if ($$0 instanceof Projectile) { Projectile $$1 = (Projectile)$$0; if ($$1.getOwner() != null)
/* 44 */         return $$1.getOwner().getUUID();  }
/*    */     
/* 46 */     return null;
/*    */   }
/*    */   
/*    */   public Optional<Entity> getEntity(ServerLevel $$0) {
/* 50 */     return Optional.<Entity>ofNullable(this.entity).or(() -> {
/*    */           Objects.requireNonNull($$0);
/*    */           return Optional.<UUID>ofNullable(this.uuid).map($$0::getEntity);
/*    */         }); } public Optional<Entity> getProjectileOwner(ServerLevel $$0) {
/* 54 */     return getEntity($$0)
/* 55 */       .filter($$0 -> $$0 instanceof Projectile)
/* 56 */       .map($$0 -> (Projectile)$$0)
/* 57 */       .map(Projectile::getOwner)
/* 58 */       .or(() -> {
/*    */           Objects.requireNonNull($$0);
/*    */           return Optional.<UUID>ofNullable(this.projectileOwnerUuid).map($$0::getEntity);
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\vibrations\VibrationInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */