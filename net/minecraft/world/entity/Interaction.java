/*     */ package net.minecraft.world.entity;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ 
/*     */ public class Interaction extends Entity implements Attackable, Targeting {
/*  28 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  30 */   private static final EntityDataAccessor<Float> DATA_WIDTH_ID = SynchedEntityData.defineId(Interaction.class, EntityDataSerializers.FLOAT);
/*     */   
/*  32 */   private static final EntityDataAccessor<Float> DATA_HEIGHT_ID = SynchedEntityData.defineId(Interaction.class, EntityDataSerializers.FLOAT);
/*  33 */   private static final EntityDataAccessor<Boolean> DATA_RESPONSE_ID = SynchedEntityData.defineId(Interaction.class, EntityDataSerializers.BOOLEAN); private static final String TAG_WIDTH = "width"; private static final String TAG_HEIGHT = "height"; private static final String TAG_ATTACK = "attack"; private static final String TAG_INTERACTION = "interaction"; private static final String TAG_RESPONSE = "response"; @Nullable
/*     */   private PlayerAction attack; @Nullable
/*     */   private PlayerAction interaction;
/*     */   
/*     */   private static final class PlayerAction extends Record { private final UUID player;
/*     */     private final long timestamp;
/*     */     public static final Codec<PlayerAction> CODEC;
/*     */     
/*  41 */     PlayerAction(UUID $$0, long $$1) { this.player = $$0; this.timestamp = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Interaction$PlayerAction;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  41 */       //   0	7	0	this	Lnet/minecraft/world/entity/Interaction$PlayerAction; } public UUID player() { return this.player; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Interaction$PlayerAction;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/entity/Interaction$PlayerAction; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Interaction$PlayerAction;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/entity/Interaction$PlayerAction;
/*  41 */       //   0	8	1	$$0	Ljava/lang/Object; } public long timestamp() { return this.timestamp; } static {
/*  42 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.CODEC.fieldOf("player").forGetter(PlayerAction::player), (App)Codec.LONG.fieldOf("timestamp").forGetter(PlayerAction::timestamp)).apply((Applicative)$$0, PlayerAction::new));
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interaction(EntityType<?> $$0, Level $$1) {
/*  54 */     super($$0, $$1);
/*  55 */     this.noPhysics = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  60 */     this.entityData.define(DATA_WIDTH_ID, Float.valueOf(1.0F));
/*  61 */     this.entityData.define(DATA_HEIGHT_ID, Float.valueOf(1.0F));
/*  62 */     this.entityData.define(DATA_RESPONSE_ID, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  67 */     if ($$0.contains("width", 99)) {
/*  68 */       setWidth($$0.getFloat("width"));
/*     */     }
/*  70 */     if ($$0.contains("height", 99)) {
/*  71 */       setHeight($$0.getFloat("height"));
/*     */     }
/*  73 */     if ($$0.contains("attack")) {
/*  74 */       Objects.requireNonNull(LOGGER); PlayerAction.CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$0.get("attack")).resultOrPartial(Util.prefix("Interaction entity", LOGGER::error)).ifPresent($$0 -> this.attack = (PlayerAction)$$0.getFirst());
/*     */     } else {
/*  76 */       this.attack = null;
/*     */     } 
/*  78 */     if ($$0.contains("interaction")) {
/*  79 */       Objects.requireNonNull(LOGGER); PlayerAction.CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$0.get("interaction")).resultOrPartial(Util.prefix("Interaction entity", LOGGER::error)).ifPresent($$0 -> this.interaction = (PlayerAction)$$0.getFirst());
/*     */     } else {
/*  81 */       this.interaction = null;
/*     */     } 
/*  83 */     setResponse($$0.getBoolean("response"));
/*  84 */     setBoundingBox(makeBoundingBox());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  89 */     $$0.putFloat("width", getWidth());
/*  90 */     $$0.putFloat("height", getHeight());
/*  91 */     if (this.attack != null) {
/*  92 */       PlayerAction.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.attack).result().ifPresent($$1 -> $$0.put("attack", $$1));
/*     */     }
/*  94 */     if (this.interaction != null) {
/*  95 */       PlayerAction.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.interaction).result().ifPresent($$1 -> $$0.put("interaction", $$1));
/*     */     }
/*  97 */     $$0.putBoolean("response", getResponse());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 102 */     super.onSyncedDataUpdated($$0);
/*     */     
/* 104 */     if (DATA_HEIGHT_ID.equals($$0) || DATA_WIDTH_ID.equals($$0)) {
/* 105 */       setBoundingBox(makeBoundingBox());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeHitByProjectile() {
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public PushReaction getPistonPushReaction() {
/* 121 */     return PushReaction.IGNORE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIgnoringBlockTriggers() {
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean skipAttackInteraction(Entity $$0) {
/* 131 */     if ($$0 instanceof Player) { Player $$1 = (Player)$$0;
/* 132 */       this.attack = new PlayerAction($$1.getUUID(), level().getGameTime());
/* 133 */       if ($$1 instanceof ServerPlayer) { ServerPlayer $$2 = (ServerPlayer)$$1;
/* 134 */         CriteriaTriggers.PLAYER_HURT_ENTITY.trigger($$2, this, $$1.damageSources().generic(), 1.0F, 1.0F, false); }
/*     */       
/* 136 */       return !getResponse(); }
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/* 143 */     if ((level()).isClientSide) {
/* 144 */       return getResponse() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
/*     */     }
/* 146 */     this.interaction = new PlayerAction($$0.getUUID(), level().getGameTime());
/* 147 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getLastAttacker() {
/* 157 */     if (this.attack != null) {
/* 158 */       return (LivingEntity)level().getPlayerByUUID(this.attack.player());
/*     */     }
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getTarget() {
/* 166 */     if (this.interaction != null) {
/* 167 */       return (LivingEntity)level().getPlayerByUUID(this.interaction.player());
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */   
/*     */   private void setWidth(float $$0) {
/* 173 */     this.entityData.set(DATA_WIDTH_ID, Float.valueOf($$0));
/*     */   }
/*     */   
/*     */   private float getWidth() {
/* 177 */     return ((Float)this.entityData.get(DATA_WIDTH_ID)).floatValue();
/*     */   }
/*     */   
/*     */   private void setHeight(float $$0) {
/* 181 */     this.entityData.set(DATA_HEIGHT_ID, Float.valueOf($$0));
/*     */   }
/*     */   
/*     */   private float getHeight() {
/* 185 */     return ((Float)this.entityData.get(DATA_HEIGHT_ID)).floatValue();
/*     */   }
/*     */   
/*     */   private void setResponse(boolean $$0) {
/* 189 */     this.entityData.set(DATA_RESPONSE_ID, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   private boolean getResponse() {
/* 193 */     return ((Boolean)this.entityData.get(DATA_RESPONSE_ID)).booleanValue();
/*     */   }
/*     */   
/*     */   private EntityDimensions getDimensions() {
/* 197 */     return EntityDimensions.scalable(getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 202 */     return getDimensions();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AABB makeBoundingBox() {
/* 207 */     return getDimensions().makeBoundingBox(position());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Interaction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */