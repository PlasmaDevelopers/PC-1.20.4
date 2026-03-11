/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundBundlePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
/*     */ import net.minecraft.network.protocol.game.VecDeltaCodec;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.decoration.ItemFrame;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.MapItem;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerEntity
/*     */ {
/*  47 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */   
/*     */   private static final int TOLERANCE_LEVEL_ROTATION = 1;
/*     */ 
/*     */   
/*     */   private static final double TOLERANCE_LEVEL_POSITION = 7.62939453125E-6D;
/*     */ 
/*     */   
/*     */   public static final int FORCED_POS_UPDATE_PERIOD = 60;
/*     */ 
/*     */   
/*     */   private static final int FORCED_TELEPORT_PERIOD = 400;
/*     */   
/*     */   private final ServerLevel level;
/*     */   
/*     */   private final Entity entity;
/*     */   
/*     */   private final int updateInterval;
/*     */   
/*     */   private final boolean trackDelta;
/*     */   
/*     */   private final Consumer<Packet<?>> broadcast;
/*     */   
/*  71 */   private final VecDeltaCodec positionCodec = new VecDeltaCodec();
/*     */   private int yRotp;
/*     */   private int xRotp;
/*     */   private int yHeadRotp;
/*  75 */   private Vec3 ap = Vec3.ZERO;
/*     */   private int tickCount;
/*     */   private int teleportDelay;
/*  78 */   private List<Entity> lastPassengers = Collections.emptyList();
/*     */   
/*     */   private boolean wasRiding;
/*     */   
/*     */   private boolean wasOnGround;
/*     */   @Nullable
/*     */   private List<SynchedEntityData.DataValue<?>> trackedDataValues;
/*     */   
/*     */   public ServerEntity(ServerLevel $$0, Entity $$1, int $$2, boolean $$3, Consumer<Packet<?>> $$4) {
/*  87 */     this.level = $$0;
/*  88 */     this.broadcast = $$4;
/*  89 */     this.entity = $$1;
/*  90 */     this.updateInterval = $$2;
/*  91 */     this.trackDelta = $$3;
/*     */ 
/*     */     
/*  94 */     this.positionCodec.setBase($$1.trackingPosition());
/*     */     
/*  96 */     this.yRotp = Mth.floor($$1.getYRot() * 256.0F / 360.0F);
/*  97 */     this.xRotp = Mth.floor($$1.getXRot() * 256.0F / 360.0F);
/*     */     
/*  99 */     this.yHeadRotp = Mth.floor($$1.getYHeadRot() * 256.0F / 360.0F);
/* 100 */     this.wasOnGround = $$1.onGround();
/*     */     
/* 102 */     this.trackedDataValues = $$1.getEntityData().getNonDefaultValues();
/*     */   }
/*     */   
/*     */   public void sendChanges() {
/* 106 */     List<Entity> $$0 = this.entity.getPassengers();
/* 107 */     if (!$$0.equals(this.lastPassengers)) {
/* 108 */       this.broadcast.accept(new ClientboundSetPassengersPacket(this.entity));
/* 109 */       removedPassengers($$0, this.lastPassengers).forEach($$0 -> {
/*     */             if ($$0 instanceof ServerPlayer) {
/*     */               ServerPlayer $$1 = (ServerPlayer)$$0; $$1.connection.teleport($$1.getX(), $$1.getY(), $$1.getZ(), $$1.getYRot(), $$1.getXRot());
/*     */             } 
/*     */           });
/* 114 */       this.lastPassengers = $$0;
/*     */     } 
/*     */     
/* 117 */     Entity entity = this.entity; if (entity instanceof ItemFrame) { ItemFrame $$1 = (ItemFrame)entity; if (this.tickCount % 10 == 0) {
/* 118 */         ItemStack $$2 = $$1.getItem();
/*     */         
/* 120 */         if ($$2.getItem() instanceof MapItem) {
/* 121 */           Integer $$3 = MapItem.getMapId($$2);
/* 122 */           MapItemSavedData $$4 = MapItem.getSavedData($$3, this.level);
/* 123 */           if ($$4 != null) {
/* 124 */             for (ServerPlayer $$5 : this.level.players()) {
/* 125 */               $$4.tickCarriedBy($$5, $$2);
/*     */               
/* 127 */               Packet<?> $$6 = $$4.getUpdatePacket($$3.intValue(), $$5);
/* 128 */               if ($$6 != null) {
/* 129 */                 $$5.connection.send($$6);
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 135 */         sendDirtyEntityData();
/*     */       }  }
/*     */     
/* 138 */     if (this.tickCount % this.updateInterval == 0 || this.entity.hasImpulse || this.entity.getEntityData().isDirty()) {
/* 139 */       if (this.entity.isPassenger()) {
/*     */         
/* 141 */         int $$7 = Mth.floor(this.entity.getYRot() * 256.0F / 360.0F);
/* 142 */         int $$8 = Mth.floor(this.entity.getXRot() * 256.0F / 360.0F);
/* 143 */         boolean $$9 = (Math.abs($$7 - this.yRotp) >= 1 || Math.abs($$8 - this.xRotp) >= 1);
/* 144 */         if ($$9) {
/* 145 */           this.broadcast.accept(new ClientboundMoveEntityPacket.Rot(this.entity.getId(), (byte)$$7, (byte)$$8, this.entity.onGround()));
/* 146 */           this.yRotp = $$7;
/* 147 */           this.xRotp = $$8;
/*     */         } 
/*     */         
/* 150 */         this.positionCodec.setBase(this.entity.trackingPosition());
/*     */         
/* 152 */         sendDirtyEntityData();
/*     */         
/* 154 */         this.wasRiding = true;
/*     */       } else {
/* 156 */         ClientboundMoveEntityPacket.Rot rot; this.teleportDelay++;
/* 157 */         int $$10 = Mth.floor(this.entity.getYRot() * 256.0F / 360.0F);
/* 158 */         int $$11 = Mth.floor(this.entity.getXRot() * 256.0F / 360.0F);
/*     */         
/* 160 */         Vec3 $$12 = this.entity.trackingPosition();
/*     */ 
/*     */         
/* 163 */         boolean $$13 = (this.positionCodec.delta($$12).lengthSqr() >= 7.62939453125E-6D);
/*     */         
/* 165 */         Packet<?> $$14 = null;
/*     */         
/* 167 */         boolean $$15 = ($$13 || this.tickCount % 60 == 0);
/* 168 */         boolean $$16 = (Math.abs($$10 - this.yRotp) >= 1 || Math.abs($$11 - this.xRotp) >= 1);
/*     */         
/* 170 */         boolean $$17 = false;
/* 171 */         boolean $$18 = false;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 176 */         if (this.tickCount > 0 || this.entity instanceof net.minecraft.world.entity.projectile.AbstractArrow) {
/* 177 */           long $$19 = this.positionCodec.encodeX($$12);
/* 178 */           long $$20 = this.positionCodec.encodeY($$12);
/* 179 */           long $$21 = this.positionCodec.encodeZ($$12);
/* 180 */           boolean $$22 = ($$19 < -32768L || $$19 > 32767L || $$20 < -32768L || $$20 > 32767L || $$21 < -32768L || $$21 > 32767L);
/* 181 */           if ($$22 || this.teleportDelay > 400 || this.wasRiding || this.wasOnGround != this.entity.onGround()) {
/* 182 */             this.wasOnGround = this.entity.onGround();
/* 183 */             this.teleportDelay = 0;
/* 184 */             ClientboundTeleportEntityPacket clientboundTeleportEntityPacket = new ClientboundTeleportEntityPacket(this.entity);
/* 185 */             $$17 = true;
/* 186 */             $$18 = true;
/*     */           }
/* 188 */           else if (($$15 && $$16) || this.entity instanceof net.minecraft.world.entity.projectile.AbstractArrow) {
/* 189 */             ClientboundMoveEntityPacket.PosRot posRot = new ClientboundMoveEntityPacket.PosRot(this.entity.getId(), (short)(int)$$19, (short)(int)$$20, (short)(int)$$21, (byte)$$10, (byte)$$11, this.entity.onGround());
/* 190 */             $$17 = true;
/* 191 */             $$18 = true;
/* 192 */           } else if ($$15) {
/* 193 */             ClientboundMoveEntityPacket.Pos pos = new ClientboundMoveEntityPacket.Pos(this.entity.getId(), (short)(int)$$19, (short)(int)$$20, (short)(int)$$21, this.entity.onGround());
/* 194 */             $$17 = true;
/* 195 */           } else if ($$16) {
/* 196 */             rot = new ClientboundMoveEntityPacket.Rot(this.entity.getId(), (byte)$$10, (byte)$$11, this.entity.onGround());
/* 197 */             $$18 = true;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 202 */         if ((this.trackDelta || this.entity.hasImpulse || (this.entity instanceof LivingEntity && ((LivingEntity)this.entity).isFallFlying())) && this.tickCount > 0) {
/* 203 */           Vec3 $$23 = this.entity.getDeltaMovement();
/* 204 */           double $$24 = $$23.distanceToSqr(this.ap);
/*     */           
/* 206 */           if ($$24 > 1.0E-7D || ($$24 > 0.0D && $$23.lengthSqr() == 0.0D)) {
/* 207 */             this.ap = $$23;
/* 208 */             this.broadcast.accept(new ClientboundSetEntityMotionPacket(this.entity.getId(), this.ap));
/*     */           } 
/*     */         } 
/*     */         
/* 212 */         if (rot != null) {
/* 213 */           this.broadcast.accept(rot);
/*     */         }
/*     */         
/* 216 */         sendDirtyEntityData();
/*     */         
/* 218 */         if ($$17) {
/* 219 */           this.positionCodec.setBase($$12);
/*     */         }
/* 221 */         if ($$18) {
/* 222 */           this.yRotp = $$10;
/* 223 */           this.xRotp = $$11;
/*     */         } 
/*     */         
/* 226 */         this.wasRiding = false;
/*     */       } 
/*     */       
/* 229 */       int $$25 = Mth.floor(this.entity.getYHeadRot() * 256.0F / 360.0F);
/* 230 */       if (Math.abs($$25 - this.yHeadRotp) >= 1) {
/* 231 */         this.broadcast.accept(new ClientboundRotateHeadPacket(this.entity, (byte)$$25));
/* 232 */         this.yHeadRotp = $$25;
/*     */       } 
/* 234 */       this.entity.hasImpulse = false;
/*     */     } 
/*     */     
/* 237 */     this.tickCount++;
/*     */     
/* 239 */     if (this.entity.hurtMarked) {
/* 240 */       broadcastAndSend((Packet<?>)new ClientboundSetEntityMotionPacket(this.entity));
/* 241 */       this.entity.hurtMarked = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Stream<Entity> removedPassengers(List<Entity> $$0, List<Entity> $$1) {
/* 246 */     return $$1.stream().filter($$1 -> !$$0.contains($$1));
/*     */   }
/*     */   
/*     */   public void removePairing(ServerPlayer $$0) {
/* 250 */     this.entity.stopSeenByPlayer($$0);
/* 251 */     $$0.connection.send((Packet)new ClientboundRemoveEntitiesPacket(new int[] { this.entity.getId() }));
/*     */   }
/*     */   
/*     */   public void addPairing(ServerPlayer $$0) {
/* 255 */     List<Packet<ClientGamePacketListener>> $$1 = new ArrayList<>();
/* 256 */     Objects.requireNonNull($$1); sendPairingData($$0, $$1::add);
/* 257 */     $$0.connection.send((Packet)new ClientboundBundlePacket($$1));
/* 258 */     this.entity.startSeenByPlayer($$0);
/*     */   }
/*     */   
/*     */   public void sendPairingData(ServerPlayer $$0, Consumer<Packet<ClientGamePacketListener>> $$1) {
/* 262 */     if (this.entity.isRemoved()) {
/* 263 */       LOGGER.warn("Fetching packet for removed entity {}", this.entity);
/*     */     }
/* 265 */     Packet<ClientGamePacketListener> $$2 = this.entity.getAddEntityPacket();
/* 266 */     this.yHeadRotp = Mth.floor(this.entity.getYHeadRot() * 256.0F / 360.0F);
/* 267 */     $$1.accept($$2);
/*     */     
/* 269 */     if (this.trackedDataValues != null) {
/* 270 */       $$1.accept(new ClientboundSetEntityDataPacket(this.entity.getId(), this.trackedDataValues));
/*     */     }
/*     */     
/* 273 */     boolean $$3 = this.trackDelta;
/* 274 */     if (this.entity instanceof LivingEntity) {
/* 275 */       Collection<AttributeInstance> $$4 = ((LivingEntity)this.entity).getAttributes().getSyncableAttributes();
/*     */       
/* 277 */       if (!$$4.isEmpty()) {
/* 278 */         $$1.accept(new ClientboundUpdateAttributesPacket(this.entity.getId(), $$4));
/*     */       }
/* 280 */       if (((LivingEntity)this.entity).isFallFlying()) {
/* 281 */         $$3 = true;
/*     */       }
/*     */     } 
/*     */     
/* 285 */     this.ap = this.entity.getDeltaMovement();
/*     */     
/* 287 */     if ($$3 && !(this.entity instanceof LivingEntity)) {
/* 288 */       $$1.accept(new ClientboundSetEntityMotionPacket(this.entity.getId(), this.ap));
/*     */     }
/*     */     
/* 291 */     if (this.entity instanceof LivingEntity) {
/* 292 */       List<Pair<EquipmentSlot, ItemStack>> $$5 = Lists.newArrayList();
/* 293 */       for (EquipmentSlot $$6 : EquipmentSlot.values()) {
/* 294 */         ItemStack $$7 = ((LivingEntity)this.entity).getItemBySlot($$6);
/* 295 */         if (!$$7.isEmpty()) {
/* 296 */           $$5.add(Pair.of($$6, $$7.copy()));
/*     */         }
/*     */       } 
/* 299 */       if (!$$5.isEmpty()) {
/* 300 */         $$1.accept(new ClientboundSetEquipmentPacket(this.entity.getId(), $$5));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 306 */     if (!this.entity.getPassengers().isEmpty()) {
/* 307 */       $$1.accept(new ClientboundSetPassengersPacket(this.entity));
/*     */     }
/* 309 */     if (this.entity.isPassenger()) {
/* 310 */       $$1.accept(new ClientboundSetPassengersPacket(this.entity.getVehicle()));
/*     */     }
/*     */ 
/*     */     
/* 314 */     Entity entity = this.entity; if (entity instanceof Mob) { Mob $$8 = (Mob)entity;
/* 315 */       if ($$8.isLeashed()) {
/* 316 */         $$1.accept(new ClientboundSetEntityLinkPacket((Entity)$$8, $$8.getLeashHolder()));
/*     */       } }
/*     */   
/*     */   }
/*     */   
/*     */   private void sendDirtyEntityData() {
/* 322 */     SynchedEntityData $$0 = this.entity.getEntityData();
/* 323 */     List<SynchedEntityData.DataValue<?>> $$1 = $$0.packDirty();
/* 324 */     if ($$1 != null) {
/* 325 */       this.trackedDataValues = $$0.getNonDefaultValues();
/* 326 */       broadcastAndSend((Packet<?>)new ClientboundSetEntityDataPacket(this.entity.getId(), $$1));
/*     */     } 
/*     */     
/* 329 */     if (this.entity instanceof LivingEntity) {
/* 330 */       Set<AttributeInstance> $$2 = ((LivingEntity)this.entity).getAttributes().getDirtyAttributes();
/*     */       
/* 332 */       if (!$$2.isEmpty()) {
/* 333 */         broadcastAndSend((Packet<?>)new ClientboundUpdateAttributesPacket(this.entity.getId(), $$2));
/*     */       }
/*     */       
/* 336 */       $$2.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void broadcastAndSend(Packet<?> $$0) {
/* 341 */     this.broadcast.accept($$0);
/* 342 */     if (this.entity instanceof ServerPlayer)
/* 343 */       ((ServerPlayer)this.entity).connection.send($$0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ServerEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */