/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
/*     */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*     */ import net.minecraft.network.protocol.common.custom.GameTestAddMarkerDebugPayload;
/*     */ import net.minecraft.network.protocol.common.custom.GameTestClearMarkersDebugPayload;
/*     */ import net.minecraft.network.protocol.common.custom.GoalDebugPayload;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.StringUtil;
/*     */ import net.minecraft.world.Nameable;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
/*     */ import net.minecraft.world.entity.ai.behavior.EntityTracker;
/*     */ import net.minecraft.world.entity.ai.goal.GoalSelector;
/*     */ import net.minecraft.world.entity.ai.goal.WrappedGoal;
/*     */ import net.minecraft.world.entity.ai.gossip.GossipType;
/*     */ import net.minecraft.world.entity.ai.memory.ExpirableValue;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiRecord;
/*     */ import net.minecraft.world.entity.animal.Bee;
/*     */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*     */ import net.minecraft.world.entity.raid.Raid;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
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
/*     */ public class DebugPackets
/*     */ {
/*  90 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static void sendGameTestAddMarker(ServerLevel $$0, BlockPos $$1, String $$2, int $$3, int $$4) {
/*  93 */     sendPacketToAllPlayers($$0, (CustomPacketPayload)new GameTestAddMarkerDebugPayload($$1, $$3, $$2, $$4));
/*     */   }
/*     */   
/*     */   public static void sendGameTestClearPacket(ServerLevel $$0) {
/*  97 */     sendPacketToAllPlayers($$0, (CustomPacketPayload)new GameTestClearMarkersDebugPayload());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendPoiPacketsForChunk(ServerLevel $$0, ChunkPos $$1) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendPoiAddedPacket(ServerLevel $$0, BlockPos $$1) {
/* 111 */     sendVillageSectionsPacket($$0, $$1);
/*     */   }
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
/*     */   public static void sendPoiRemovedPacket(ServerLevel $$0, BlockPos $$1) {
/* 135 */     sendVillageSectionsPacket($$0, $$1);
/*     */   }
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
/*     */   public static void sendPoiTicketCountPacket(ServerLevel $$0, BlockPos $$1) {
/* 151 */     sendVillageSectionsPacket($$0, $$1);
/*     */   }
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
/*     */   private static void sendVillageSectionsPacket(ServerLevel $$0, BlockPos $$1) {}
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
/*     */   public static void sendPathFindingPacket(Level $$0, Mob $$1, @Nullable Path $$2, float $$3) {}
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
/*     */   public static void sendNeighborsUpdatePacket(Level $$0, BlockPos $$1) {}
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
/*     */   public static void sendStructurePacket(WorldGenLevel $$0, StructureStart $$1) {}
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
/*     */   public static void sendGoalSelector(Level $$0, Mob $$1, GoalSelector $$2) {}
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
/*     */   public static void sendRaids(ServerLevel $$0, Collection<Raid> $$1) {}
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
/*     */   public static void sendEntityBrain(LivingEntity $$0) {}
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
/*     */   public static void sendBeeInfo(Bee $$0) {}
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
/*     */   public static void sendBreezeInfo(Breeze $$0) {}
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
/*     */   public static void sendGameEventInfo(Level $$0, GameEvent $$1, Vec3 $$2) {}
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
/*     */   public static void sendGameEventListenerInfo(Level $$0, GameEventListener $$1) {}
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
/*     */   public static void sendHiveInfo(Level $$0, BlockPos $$1, BlockState $$2, BeehiveBlockEntity $$3) {}
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
/*     */   private static List<String> getMemoryDescriptions(LivingEntity $$0, long $$1) {
/* 416 */     Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> $$2 = $$0.getBrain().getMemories();
/* 417 */     List<String> $$3 = Lists.newArrayList();
/* 418 */     for (Map.Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> $$4 : $$2.entrySet()) {
/* 419 */       String $$13; MemoryModuleType<?> $$5 = $$4.getKey();
/* 420 */       Optional<? extends ExpirableValue<?>> $$6 = $$4.getValue();
/*     */       
/* 422 */       if ($$6.isPresent()) {
/* 423 */         ExpirableValue<?> $$7 = $$6.get();
/* 424 */         Object $$8 = $$7.getValue();
/* 425 */         if ($$5 == MemoryModuleType.HEARD_BELL_TIME) {
/* 426 */           long $$9 = $$1 - ((Long)$$8).longValue();
/* 427 */           String $$10 = "" + $$9 + " ticks ago";
/* 428 */         } else if ($$7.canExpire()) {
/* 429 */           String $$11 = getShortDescription((ServerLevel)$$0.level(), $$8) + " (ttl: " + getShortDescription((ServerLevel)$$0.level(), $$8) + ")";
/*     */         } else {
/* 431 */           String $$12 = getShortDescription((ServerLevel)$$0.level(), $$8);
/*     */         } 
/*     */       } else {
/* 434 */         $$13 = "-";
/*     */       } 
/* 436 */       $$3.add(BuiltInRegistries.MEMORY_MODULE_TYPE.getKey($$5).getPath() + ": " + BuiltInRegistries.MEMORY_MODULE_TYPE.getKey($$5).getPath());
/*     */     } 
/* 438 */     $$3.sort(String::compareTo);
/* 439 */     return $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getShortDescription(ServerLevel $$0, @Nullable Object $$1) {
/* 447 */     if ($$1 == null)
/* 448 */       return "-"; 
/* 449 */     if ($$1 instanceof UUID)
/* 450 */       return getShortDescription($$0, $$0.getEntity((UUID)$$1)); 
/* 451 */     if ($$1 instanceof LivingEntity) {
/* 452 */       Entity $$2 = (Entity)$$1;
/* 453 */       return DebugEntityNameGenerator.getEntityName($$2);
/* 454 */     }  if ($$1 instanceof Nameable)
/* 455 */       return ((Nameable)$$1).getName().getString(); 
/* 456 */     if ($$1 instanceof WalkTarget)
/* 457 */       return getShortDescription($$0, ((WalkTarget)$$1).getTarget()); 
/* 458 */     if ($$1 instanceof EntityTracker)
/* 459 */       return getShortDescription($$0, ((EntityTracker)$$1).getEntity()); 
/* 460 */     if ($$1 instanceof GlobalPos)
/* 461 */       return getShortDescription($$0, ((GlobalPos)$$1).pos()); 
/* 462 */     if ($$1 instanceof BlockPosTracker)
/* 463 */       return getShortDescription($$0, ((BlockPosTracker)$$1).currentBlockPosition()); 
/* 464 */     if ($$1 instanceof DamageSource) {
/* 465 */       Entity $$3 = ((DamageSource)$$1).getEntity();
/* 466 */       return ($$3 == null) ? $$1.toString() : getShortDescription($$0, $$3);
/* 467 */     }  if ($$1 instanceof Collection) {
/*     */       
/* 469 */       List<String> $$4 = Lists.newArrayList();
/* 470 */       for (Object $$5 : $$1) {
/* 471 */         $$4.add(getShortDescription($$0, $$5));
/*     */       }
/* 473 */       return $$4.toString();
/*     */     } 
/* 475 */     return $$1.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sendPacketToAllPlayers(ServerLevel $$0, CustomPacketPayload $$1) {
/* 480 */     ClientboundCustomPayloadPacket clientboundCustomPayloadPacket = new ClientboundCustomPayloadPacket($$1);
/* 481 */     for (ServerPlayer $$3 : $$0.players())
/* 482 */       $$3.connection.send((Packet)clientboundCustomPayloadPacket); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\DebugPackets.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */