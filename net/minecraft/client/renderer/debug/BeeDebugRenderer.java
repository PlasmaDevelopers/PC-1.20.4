/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.protocol.common.custom.BeeDebugPayload;
/*     */ import net.minecraft.network.protocol.common.custom.HiveDebugPayload;
/*     */ import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeeDebugRenderer
/*     */   implements DebugRenderer.SimpleDebugRenderer
/*     */ {
/*     */   private static final boolean SHOW_GOAL_FOR_ALL_BEES = true;
/*     */   private static final boolean SHOW_NAME_FOR_ALL_BEES = true;
/*     */   private static final boolean SHOW_HIVE_FOR_ALL_BEES = true;
/*     */   private static final boolean SHOW_FLOWER_POS_FOR_ALL_BEES = true;
/*     */   private static final boolean SHOW_TRAVEL_TICKS_FOR_ALL_BEES = true;
/*     */   private static final boolean SHOW_PATH_FOR_ALL_BEES = false;
/*     */   private static final boolean SHOW_GOAL_FOR_SELECTED_BEE = true;
/*     */   private static final boolean SHOW_NAME_FOR_SELECTED_BEE = true;
/*     */   private static final boolean SHOW_HIVE_FOR_SELECTED_BEE = true;
/*     */   private static final boolean SHOW_FLOWER_POS_FOR_SELECTED_BEE = true;
/*     */   private static final boolean SHOW_TRAVEL_TICKS_FOR_SELECTED_BEE = true;
/*     */   private static final boolean SHOW_PATH_FOR_SELECTED_BEE = true;
/*     */   private static final boolean SHOW_HIVE_MEMBERS = true;
/*     */   private static final boolean SHOW_BLACKLISTS = true;
/*     */   private static final int MAX_RENDER_DIST_FOR_HIVE_OVERLAY = 30;
/*     */   private static final int MAX_RENDER_DIST_FOR_BEE_OVERLAY = 30;
/*     */   private static final int MAX_TARGETING_DIST = 8;
/*     */   private static final int HIVE_TIMEOUT = 20;
/*     */   private static final float TEXT_SCALE = 0.02F;
/*     */   private static final int WHITE = -1;
/*     */   private static final int YELLOW = -256;
/*     */   private static final int ORANGE = -23296;
/*     */   private static final int GREEN = -16711936;
/*     */   private static final int GRAY = -3355444;
/*     */   private static final int PINK = -98404;
/*     */   private static final int RED = -65536;
/*     */   private final Minecraft minecraft;
/*  72 */   private final Map<BlockPos, HiveDebugInfo> hives = new HashMap<>();
/*  73 */   private final Map<UUID, BeeDebugPayload.BeeInfo> beeInfosPerEntity = new HashMap<>();
/*     */   
/*     */   @Nullable
/*     */   private UUID lastLookedAtUuid;
/*     */   
/*     */   public BeeDebugRenderer(Minecraft $$0) {
/*  79 */     this.minecraft = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  84 */     this.hives.clear();
/*  85 */     this.beeInfosPerEntity.clear();
/*  86 */     this.lastLookedAtUuid = null;
/*     */   }
/*     */   
/*     */   public void addOrUpdateHiveInfo(HiveDebugPayload.HiveInfo $$0, long $$1) {
/*  90 */     this.hives.put($$0.pos(), new HiveDebugInfo($$0, $$1));
/*     */   }
/*     */   
/*     */   public void addOrUpdateBeeInfo(BeeDebugPayload.BeeInfo $$0) {
/*  94 */     this.beeInfosPerEntity.put($$0.uuid(), $$0);
/*     */   }
/*     */   
/*     */   public void removeBeeInfo(int $$0) {
/*  98 */     this.beeInfosPerEntity.values().removeIf($$1 -> ($$1.id() == $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 103 */     clearRemovedHives();
/* 104 */     clearRemovedBees();
/*     */     
/* 106 */     doRender($$0, $$1);
/*     */     
/* 108 */     if (!this.minecraft.player.isSpectator()) {
/* 109 */       updateLastLookedAtUuid();
/*     */     }
/*     */   }
/*     */   
/*     */   private void clearRemovedBees() {
/* 114 */     this.beeInfosPerEntity.entrySet().removeIf($$0 -> (this.minecraft.level.getEntity(((BeeDebugPayload.BeeInfo)$$0.getValue()).id()) == null));
/*     */   }
/*     */   
/*     */   private void clearRemovedHives() {
/* 118 */     long $$0 = this.minecraft.level.getGameTime() - 20L;
/* 119 */     this.hives.entrySet().removeIf($$1 -> (((HiveDebugInfo)$$1.getValue()).lastSeen() < $$0));
/*     */   }
/*     */   
/*     */   private void doRender(PoseStack $$0, MultiBufferSource $$1) {
/* 123 */     BlockPos $$2 = getCamera().getBlockPosition();
/*     */     
/* 125 */     this.beeInfosPerEntity.values().forEach($$2 -> {
/*     */           if (isPlayerCloseEnoughToMob($$2)) {
/*     */             renderBeeInfo($$0, $$1, $$2);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 132 */     renderFlowerInfos($$0, $$1);
/*     */ 
/*     */ 
/*     */     
/* 136 */     for (BlockPos $$3 : this.hives.keySet()) {
/* 137 */       if ($$2.closerThan((Vec3i)$$3, 30.0D)) {
/* 138 */         highlightHive($$0, $$1, $$3);
/*     */       }
/*     */     } 
/*     */     
/* 142 */     Map<BlockPos, Set<UUID>> $$4 = createHiveBlacklistMap();
/*     */     
/* 144 */     this.hives.values().forEach($$4 -> {
/*     */           if ($$0.closerThan((Vec3i)$$4.info.pos(), 30.0D)) {
/*     */             Set<UUID> $$5 = (Set<UUID>)$$1.get($$4.info.pos());
/*     */             
/*     */             renderHiveInfo($$2, $$3, $$4.info, ($$5 == null) ? Sets.newHashSet() : $$5);
/*     */           } 
/*     */         });
/* 151 */     getGhostHives().forEach(($$3, $$4) -> {
/*     */           if ($$0.closerThan((Vec3i)$$3, 30.0D)) {
/*     */             renderGhostHive($$1, $$2, $$3, $$4);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<BlockPos, Set<UUID>> createHiveBlacklistMap() {
/* 163 */     Map<BlockPos, Set<UUID>> $$0 = Maps.newHashMap();
/*     */     
/* 165 */     this.beeInfosPerEntity.values().forEach($$1 -> $$1.blacklistedHives().forEach(()));
/*     */ 
/*     */     
/* 168 */     return $$0;
/*     */   }
/*     */   
/*     */   private void renderFlowerInfos(PoseStack $$0, MultiBufferSource $$1) {
/* 172 */     Map<BlockPos, Set<UUID>> $$2 = Maps.newHashMap();
/*     */     
/* 174 */     this.beeInfosPerEntity.values().forEach($$1 -> {
/*     */           if ($$1.flowerPos() != null) {
/*     */             ((Set<UUID>)$$0.computeIfAbsent($$1.flowerPos(), ())).add($$1.uuid());
/*     */           }
/*     */         });
/*     */     
/* 180 */     $$2.forEach(($$2, $$3) -> {
/*     */           Set<String> $$4 = (Set<String>)$$3.stream().map(DebugEntityNameGenerator::getEntityName).collect(Collectors.toSet());
/*     */           int $$5 = 1;
/*     */           renderTextOverPos($$0, $$1, $$4.toString(), $$2, $$5++, -256);
/*     */           renderTextOverPos($$0, $$1, "Flower", $$2, $$5++, -1);
/*     */           float $$6 = 0.05F;
/*     */           DebugRenderer.renderFilledBox($$0, $$1, $$2, 0.05F, 0.8F, 0.8F, 0.0F, 0.3F);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getBeeUuidsAsString(Collection<UUID> $$0) {
/* 193 */     if ($$0.isEmpty())
/* 194 */       return "-"; 
/* 195 */     if ($$0.size() > 3) {
/* 196 */       return "" + $$0.size() + " bees";
/*     */     }
/* 198 */     return ((Set)$$0.stream().map(DebugEntityNameGenerator::getEntityName).collect(Collectors.toSet())).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void highlightHive(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2) {
/* 203 */     float $$3 = 0.05F;
/* 204 */     DebugRenderer.renderFilledBox($$0, $$1, $$2, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
/*     */   }
/*     */   
/*     */   private void renderGhostHive(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2, List<String> $$3) {
/* 208 */     float $$4 = 0.05F;
/* 209 */     DebugRenderer.renderFilledBox($$0, $$1, $$2, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
/* 210 */     renderTextOverPos($$0, $$1, "" + $$3, $$2, 0, -256);
/* 211 */     renderTextOverPos($$0, $$1, "Ghost Hive", $$2, 1, -65536);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderHiveInfo(PoseStack $$0, MultiBufferSource $$1, HiveDebugPayload.HiveInfo $$2, Collection<UUID> $$3) {
/* 217 */     int $$4 = 0;
/*     */     
/* 219 */     if (!$$3.isEmpty()) {
/* 220 */       renderTextOverHive($$0, $$1, "Blacklisted by " + getBeeUuidsAsString($$3), $$2, $$4++, -65536);
/*     */     }
/*     */     
/* 223 */     renderTextOverHive($$0, $$1, "Out: " + getBeeUuidsAsString(getHiveMembers($$2.pos())), $$2, $$4++, -3355444);
/*     */     
/* 225 */     if ($$2.occupantCount() == 0) {
/* 226 */       renderTextOverHive($$0, $$1, "In: -", $$2, $$4++, -256);
/* 227 */     } else if ($$2.occupantCount() == 1) {
/* 228 */       renderTextOverHive($$0, $$1, "In: 1 bee", $$2, $$4++, -256);
/*     */     } else {
/* 230 */       renderTextOverHive($$0, $$1, "In: " + $$2.occupantCount() + " bees", $$2, $$4++, -256);
/*     */     } 
/*     */     
/* 233 */     renderTextOverHive($$0, $$1, "Honey: " + $$2.honeyLevel(), $$2, $$4++, -23296);
/*     */     
/* 235 */     renderTextOverHive($$0, $$1, $$2.hiveType() + $$2.hiveType(), $$2, $$4++, -1);
/*     */   }
/*     */   
/*     */   private void renderPath(PoseStack $$0, MultiBufferSource $$1, BeeDebugPayload.BeeInfo $$2) {
/* 239 */     if ($$2.path() != null) {
/* 240 */       PathfindingRenderer.renderPath($$0, $$1, $$2.path(), 0.5F, false, false, getCamera().getPosition().x(), getCamera().getPosition().y(), getCamera().getPosition().z());
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderBeeInfo(PoseStack $$0, MultiBufferSource $$1, BeeDebugPayload.BeeInfo $$2) {
/* 245 */     boolean $$3 = isBeeSelected($$2);
/*     */ 
/*     */     
/* 248 */     int $$4 = 0;
/*     */ 
/*     */     
/* 251 */     renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$4++, $$2.toString(), -1, 0.03F);
/*     */ 
/*     */ 
/*     */     
/* 255 */     if ($$2.hivePos() == null) {
/* 256 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$4++, "No hive", -98404, 0.02F);
/*     */     } else {
/* 258 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$4++, "Hive: " + getPosDescription($$2, $$2.hivePos()), -256, 0.02F);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 263 */     if ($$2.flowerPos() == null) {
/* 264 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$4++, "No flower", -98404, 0.02F);
/*     */     } else {
/* 266 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$4++, "Flower: " + getPosDescription($$2, $$2.flowerPos()), -256, 0.02F);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 271 */     for (String $$5 : $$2.goals()) {
/* 272 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$4++, $$5, -16711936, 0.02F);
/*     */     }
/*     */ 
/*     */     
/* 276 */     if ($$3) {
/* 277 */       renderPath($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/* 281 */     if ($$2.travelTicks() > 0) {
/* 282 */       int $$6 = ($$2.travelTicks() < 600) ? -3355444 : -23296;
/* 283 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$4++, "Travelling: " + $$2.travelTicks() + " ticks", $$6, 0.02F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void renderTextOverHive(PoseStack $$0, MultiBufferSource $$1, String $$2, HiveDebugPayload.HiveInfo $$3, int $$4, int $$5) {
/* 289 */     renderTextOverPos($$0, $$1, $$2, $$3.pos(), $$4, $$5);
/*     */   }
/*     */   
/*     */   private static void renderTextOverPos(PoseStack $$0, MultiBufferSource $$1, String $$2, BlockPos $$3, int $$4, int $$5) {
/* 293 */     double $$6 = 1.3D;
/* 294 */     double $$7 = 0.2D;
/*     */     
/* 296 */     double $$8 = $$3.getX() + 0.5D;
/* 297 */     double $$9 = $$3.getY() + 1.3D + $$4 * 0.2D;
/* 298 */     double $$10 = $$3.getZ() + 0.5D;
/*     */     
/* 300 */     DebugRenderer.renderFloatingText($$0, $$1, $$2, $$8, $$9, $$10, $$5, 0.02F, true, 0.0F, true);
/*     */   }
/*     */   
/*     */   private static void renderTextOverMob(PoseStack $$0, MultiBufferSource $$1, Position $$2, int $$3, String $$4, int $$5, float $$6) {
/* 304 */     double $$7 = 2.4D;
/* 305 */     double $$8 = 0.25D;
/*     */ 
/*     */ 
/*     */     
/* 309 */     BlockPos $$9 = BlockPos.containing($$2);
/*     */     
/* 311 */     double $$10 = $$9.getX() + 0.5D;
/* 312 */     double $$11 = $$2.y() + 2.4D + $$3 * 0.25D;
/* 313 */     double $$12 = $$9.getZ() + 0.5D;
/*     */     
/* 315 */     float $$13 = 0.5F;
/* 316 */     DebugRenderer.renderFloatingText($$0, $$1, $$4, $$10, $$11, $$12, $$5, $$6, false, 0.5F, true);
/*     */   }
/*     */   
/*     */   private Camera getCamera() {
/* 320 */     return this.minecraft.gameRenderer.getMainCamera();
/*     */   }
/*     */   
/*     */   private Set<String> getHiveMemberNames(HiveDebugPayload.HiveInfo $$0) {
/* 324 */     return (Set<String>)getHiveMembers($$0.pos())
/* 325 */       .stream()
/* 326 */       .map(DebugEntityNameGenerator::getEntityName)
/* 327 */       .collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */   
/*     */   private String getPosDescription(BeeDebugPayload.BeeInfo $$0, BlockPos $$1) {
/* 332 */     double $$2 = Math.sqrt($$1.distToCenterSqr((Position)$$0.pos()));
/* 333 */     double $$3 = Math.round($$2 * 10.0D) / 10.0D;
/* 334 */     return $$1.toShortString() + " (dist " + $$1.toShortString() + ")";
/*     */   }
/*     */   
/*     */   private boolean isBeeSelected(BeeDebugPayload.BeeInfo $$0) {
/* 338 */     return Objects.equals(this.lastLookedAtUuid, $$0.uuid());
/*     */   }
/*     */   
/*     */   private boolean isPlayerCloseEnoughToMob(BeeDebugPayload.BeeInfo $$0) {
/* 342 */     LocalPlayer localPlayer = this.minecraft.player;
/* 343 */     BlockPos $$2 = BlockPos.containing(localPlayer.getX(), $$0.pos().y(), localPlayer.getZ());
/* 344 */     BlockPos $$3 = BlockPos.containing((Position)$$0.pos());
/* 345 */     return $$2.closerThan((Vec3i)$$3, 30.0D);
/*     */   }
/*     */   
/*     */   private Collection<UUID> getHiveMembers(BlockPos $$0) {
/* 349 */     return (Collection<UUID>)this.beeInfosPerEntity.values()
/* 350 */       .stream()
/* 351 */       .filter($$1 -> $$1.hasHive($$0))
/* 352 */       .map(BeeDebugPayload.BeeInfo::uuid)
/* 353 */       .collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<BlockPos, List<String>> getGhostHives() {
/* 361 */     Map<BlockPos, List<String>> $$0 = Maps.newHashMap();
/* 362 */     for (BeeDebugPayload.BeeInfo $$1 : this.beeInfosPerEntity.values()) {
/* 363 */       if ($$1.hivePos() != null && !this.hives.containsKey($$1.hivePos()))
/*     */       {
/* 365 */         ((List<String>)$$0.computeIfAbsent($$1.hivePos(), $$0 -> Lists.newArrayList())).add($$1.generateName());
/*     */       }
/*     */     } 
/* 368 */     return $$0;
/*     */   }
/*     */   
/*     */   private void updateLastLookedAtUuid() {
/* 372 */     DebugRenderer.getTargetedEntity(this.minecraft.getCameraEntity(), 8).ifPresent($$0 -> this.lastLookedAtUuid = $$0.getUUID());
/*     */   }
/*     */   private static final class HiveDebugInfo extends Record { final HiveDebugPayload.HiveInfo info; private final long lastSeen;
/* 375 */     HiveDebugInfo(HiveDebugPayload.HiveInfo $$0, long $$1) { this.info = $$0; this.lastSeen = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/debug/BeeDebugRenderer$HiveDebugInfo;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #375	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 375 */       //   0	7	0	this	Lnet/minecraft/client/renderer/debug/BeeDebugRenderer$HiveDebugInfo; } public HiveDebugPayload.HiveInfo info() { return this.info; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/debug/BeeDebugRenderer$HiveDebugInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #375	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/debug/BeeDebugRenderer$HiveDebugInfo; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/debug/BeeDebugRenderer$HiveDebugInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #375	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/renderer/debug/BeeDebugRenderer$HiveDebugInfo;
/* 375 */       //   0	8	1	$$0	Ljava/lang/Object; } public long lastSeen() { return this.lastSeen; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\BeeDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */