/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.protocol.common.custom.BrainDebugPayload;
/*     */ import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
/*     */ import net.minecraft.world.entity.Entity;
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
/*     */ public class BrainDebugRenderer
/*     */   implements DebugRenderer.SimpleDebugRenderer
/*     */ {
/*  39 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final boolean SHOW_NAME_FOR_ALL = true;
/*     */   
/*     */   private static final boolean SHOW_PROFESSION_FOR_ALL = false;
/*     */   
/*     */   private static final boolean SHOW_BEHAVIORS_FOR_ALL = false;
/*     */   
/*     */   private static final boolean SHOW_ACTIVITIES_FOR_ALL = false;
/*     */   
/*     */   private static final boolean SHOW_INVENTORY_FOR_ALL = false;
/*     */   
/*     */   private static final boolean SHOW_GOSSIPS_FOR_ALL = false;
/*     */   
/*     */   private static final boolean SHOW_PATH_FOR_ALL = false;
/*     */   
/*     */   private static final boolean SHOW_HEALTH_FOR_ALL = false;
/*     */   
/*     */   private static final boolean SHOW_WANTS_GOLEM_FOR_ALL = true;
/*     */   private static final boolean SHOW_ANGER_LEVEL_FOR_ALL = false;
/*     */   private static final boolean SHOW_NAME_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_PROFESSION_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_BEHAVIORS_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_ACTIVITIES_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_MEMORIES_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_INVENTORY_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_GOSSIPS_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_PATH_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_HEALTH_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_WANTS_GOLEM_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_ANGER_LEVEL_FOR_SELECTED = true;
/*     */   private static final boolean SHOW_POI_INFO = true;
/*     */   private static final int MAX_RENDER_DIST_FOR_BRAIN_INFO = 30;
/*     */   private static final int MAX_RENDER_DIST_FOR_POI_INFO = 30;
/*     */   private static final int MAX_TARGETING_DIST = 8;
/*     */   private static final float TEXT_SCALE = 0.02F;
/*     */   private static final int WHITE = -1;
/*     */   private static final int YELLOW = -256;
/*     */   private static final int CYAN = -16711681;
/*     */   private static final int GREEN = -16711936;
/*     */   private static final int GRAY = -3355444;
/*     */   private static final int PINK = -98404;
/*     */   private static final int RED = -65536;
/*     */   private static final int ORANGE = -23296;
/*     */   private final Minecraft minecraft;
/*  84 */   private final Map<BlockPos, PoiInfo> pois = Maps.newHashMap();
/*  85 */   private final Map<UUID, BrainDebugPayload.BrainDump> brainDumpsPerEntity = Maps.newHashMap();
/*     */   
/*     */   @Nullable
/*     */   private UUID lastLookedAtUuid;
/*     */   
/*     */   public BrainDebugRenderer(Minecraft $$0) {
/*  91 */     this.minecraft = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  96 */     this.pois.clear();
/*  97 */     this.brainDumpsPerEntity.clear();
/*  98 */     this.lastLookedAtUuid = null;
/*     */   }
/*     */   
/*     */   public void addPoi(PoiInfo $$0) {
/* 102 */     this.pois.put($$0.pos, $$0);
/*     */   }
/*     */   
/*     */   public void removePoi(BlockPos $$0) {
/* 106 */     this.pois.remove($$0);
/*     */   }
/*     */   
/*     */   public void setFreeTicketCount(BlockPos $$0, int $$1) {
/* 110 */     PoiInfo $$2 = this.pois.get($$0);
/* 111 */     if ($$2 == null) {
/* 112 */       LOGGER.warn("Strange, setFreeTicketCount was called for an unknown POI: {}", $$0);
/*     */       return;
/*     */     } 
/* 115 */     $$2.freeTicketCount = $$1;
/*     */   }
/*     */   
/*     */   public void addOrUpdateBrainDump(BrainDebugPayload.BrainDump $$0) {
/* 119 */     this.brainDumpsPerEntity.put($$0.uuid(), $$0);
/*     */   }
/*     */   
/*     */   public void removeBrainDump(int $$0) {
/* 123 */     this.brainDumpsPerEntity.values().removeIf($$1 -> ($$1.id() == $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 128 */     clearRemovedEntities();
/*     */     
/* 130 */     doRender($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 132 */     if (!this.minecraft.player.isSpectator()) {
/* 133 */       updateLastLookedAtUuid();
/*     */     }
/*     */   }
/*     */   
/*     */   private void clearRemovedEntities() {
/* 138 */     this.brainDumpsPerEntity.entrySet().removeIf($$0 -> {
/*     */           Entity $$1 = this.minecraft.level.getEntity(((BrainDebugPayload.BrainDump)$$0.getValue()).id());
/* 140 */           return ($$1 == null || $$1.isRemoved());
/*     */         });
/*     */   }
/*     */   
/*     */   private void doRender(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 145 */     BlockPos $$5 = BlockPos.containing($$2, $$3, $$4);
/*     */     
/* 147 */     this.brainDumpsPerEntity.values().forEach($$5 -> {
/*     */           if (isPlayerCloseEnoughToMob($$5)) {
/*     */             renderBrainInfo($$0, $$1, $$5, $$2, $$3, $$4);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 154 */     for (BlockPos $$6 : this.pois.keySet()) {
/* 155 */       if ($$5.closerThan((Vec3i)$$6, 30.0D)) {
/* 156 */         highlightPoi($$0, $$1, $$6);
/*     */       }
/*     */     } 
/*     */     
/* 160 */     this.pois.values().forEach($$3 -> {
/*     */           if ($$0.closerThan((Vec3i)$$3.pos, 30.0D)) {
/*     */             renderPoiInfo($$1, $$2, $$3);
/*     */           }
/*     */         });
/*     */     
/* 166 */     getGhostPois().forEach(($$3, $$4) -> {
/*     */           if ($$0.closerThan((Vec3i)$$3, 30.0D)) {
/*     */             renderGhostPoi($$1, $$2, $$3, $$4);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static void highlightPoi(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2) {
/* 175 */     float $$3 = 0.05F;
/* 176 */     DebugRenderer.renderFilledBox($$0, $$1, $$2, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
/*     */   }
/*     */   
/*     */   private void renderGhostPoi(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2, List<String> $$3) {
/* 180 */     float $$4 = 0.05F;
/* 181 */     DebugRenderer.renderFilledBox($$0, $$1, $$2, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
/* 182 */     renderTextOverPos($$0, $$1, "" + $$3, $$2, 0, -256);
/* 183 */     renderTextOverPos($$0, $$1, "Ghost POI", $$2, 1, -65536);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderPoiInfo(PoseStack $$0, MultiBufferSource $$1, PoiInfo $$2) {
/* 189 */     int $$3 = 0;
/* 190 */     Set<String> $$4 = getTicketHolderNames($$2);
/* 191 */     if ($$4.size() < 4) {
/* 192 */       renderTextOverPoi($$0, $$1, "Owners: " + $$4, $$2, $$3, -256);
/*     */     } else {
/* 194 */       renderTextOverPoi($$0, $$1, "" + $$4.size() + " ticket holders", $$2, $$3, -256);
/*     */     } 
/*     */     
/* 197 */     $$3++;
/*     */     
/* 199 */     Set<String> $$5 = getPotentialTicketHolderNames($$2);
/* 200 */     if ($$5.size() < 4) {
/* 201 */       renderTextOverPoi($$0, $$1, "Candidates: " + $$5, $$2, $$3, -23296);
/*     */     } else {
/* 203 */       renderTextOverPoi($$0, $$1, "" + $$5.size() + " potential owners", $$2, $$3, -23296);
/*     */     } 
/*     */     
/* 206 */     $$3++;
/* 207 */     renderTextOverPoi($$0, $$1, "Free tickets: " + $$2.freeTicketCount, $$2, $$3, -256);
/*     */     
/* 209 */     $$3++;
/* 210 */     renderTextOverPoi($$0, $$1, $$2.type, $$2, $$3, -1);
/*     */   }
/*     */   
/*     */   private void renderPath(PoseStack $$0, MultiBufferSource $$1, BrainDebugPayload.BrainDump $$2, double $$3, double $$4, double $$5) {
/* 214 */     if ($$2.path() != null) {
/* 215 */       PathfindingRenderer.renderPath($$0, $$1, $$2.path(), 0.5F, false, false, $$3, $$4, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderBrainInfo(PoseStack $$0, MultiBufferSource $$1, BrainDebugPayload.BrainDump $$2, double $$3, double $$4, double $$5) {
/* 220 */     boolean $$6 = isMobSelected($$2);
/* 221 */     int $$7 = 0;
/*     */ 
/*     */     
/* 224 */     renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, $$2.name(), -1, 0.03F);
/* 225 */     $$7++;
/*     */ 
/*     */     
/* 228 */     if ($$6) {
/* 229 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, $$2.profession() + " " + $$2.profession() + " xp", -1, 0.02F);
/* 230 */       $$7++;
/*     */     } 
/*     */     
/* 233 */     if ($$6) {
/* 234 */       int $$8 = ($$2.health() < $$2.maxHealth()) ? -23296 : -1;
/* 235 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, "health: " + String.format(Locale.ROOT, "%.1f", new Object[] { Float.valueOf($$2.health()) }) + " / " + String.format(Locale.ROOT, "%.1f", new Object[] { Float.valueOf($$2.maxHealth()) }), $$8, 0.02F);
/* 236 */       $$7++;
/*     */     } 
/*     */     
/* 239 */     if ($$6 && 
/* 240 */       !$$2.inventory().equals("")) {
/* 241 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, $$2.inventory(), -98404, 0.02F);
/* 242 */       $$7++;
/*     */     } 
/*     */ 
/*     */     
/* 246 */     if ($$6) {
/* 247 */       for (String $$9 : $$2.behaviors()) {
/* 248 */         renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, $$9, -16711681, 0.02F);
/* 249 */         $$7++;
/*     */       } 
/*     */     }
/* 252 */     if ($$6) {
/* 253 */       for (String $$10 : $$2.activities()) {
/* 254 */         renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, $$10, -16711936, 0.02F);
/* 255 */         $$7++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 260 */     if ($$2.wantsGolem()) {
/* 261 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, "Wants Golem", -23296, 0.02F);
/* 262 */       $$7++;
/*     */     } 
/*     */ 
/*     */     
/* 266 */     if ($$6 && 
/* 267 */       $$2.angerLevel() != -1) {
/* 268 */       renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, "Anger Level: " + $$2.angerLevel(), -98404, 0.02F);
/* 269 */       $$7++;
/*     */     } 
/*     */ 
/*     */     
/* 273 */     if ($$6) {
/* 274 */       for (String $$11 : $$2.gossips()) {
/* 275 */         if ($$11.startsWith($$2.name())) {
/* 276 */           renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, $$11, -1, 0.02F);
/*     */         } else {
/* 278 */           renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, $$11, -23296, 0.02F);
/*     */         } 
/* 280 */         $$7++;
/*     */       } 
/*     */     }
/*     */     
/* 284 */     if ($$6) {
/* 285 */       for (String $$12 : Lists.reverse($$2.memories())) {
/* 286 */         renderTextOverMob($$0, $$1, (Position)$$2.pos(), $$7, $$12, -3355444, 0.02F);
/* 287 */         $$7++;
/*     */       } 
/*     */     }
/*     */     
/* 291 */     if ($$6) {
/* 292 */       renderPath($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void renderTextOverPoi(PoseStack $$0, MultiBufferSource $$1, String $$2, PoiInfo $$3, int $$4, int $$5) {
/* 297 */     renderTextOverPos($$0, $$1, $$2, $$3.pos, $$4, $$5);
/*     */   }
/*     */   
/*     */   private static void renderTextOverPos(PoseStack $$0, MultiBufferSource $$1, String $$2, BlockPos $$3, int $$4, int $$5) {
/* 301 */     double $$6 = 1.3D;
/* 302 */     double $$7 = 0.2D;
/*     */     
/* 304 */     double $$8 = $$3.getX() + 0.5D;
/* 305 */     double $$9 = $$3.getY() + 1.3D + $$4 * 0.2D;
/* 306 */     double $$10 = $$3.getZ() + 0.5D;
/*     */     
/* 308 */     DebugRenderer.renderFloatingText($$0, $$1, $$2, $$8, $$9, $$10, $$5, 0.02F, true, 0.0F, true);
/*     */   }
/*     */   
/*     */   private static void renderTextOverMob(PoseStack $$0, MultiBufferSource $$1, Position $$2, int $$3, String $$4, int $$5, float $$6) {
/* 312 */     double $$7 = 2.4D;
/* 313 */     double $$8 = 0.25D;
/*     */ 
/*     */ 
/*     */     
/* 317 */     BlockPos $$9 = BlockPos.containing($$2);
/*     */     
/* 319 */     double $$10 = $$9.getX() + 0.5D;
/* 320 */     double $$11 = $$2.y() + 2.4D + $$3 * 0.25D;
/* 321 */     double $$12 = $$9.getZ() + 0.5D;
/*     */     
/* 323 */     float $$13 = 0.5F;
/* 324 */     DebugRenderer.renderFloatingText($$0, $$1, $$4, $$10, $$11, $$12, $$5, $$6, false, 0.5F, true);
/*     */   }
/*     */   
/*     */   private Set<String> getTicketHolderNames(PoiInfo $$0) {
/* 328 */     return (Set<String>)getTicketHolders($$0.pos)
/* 329 */       .stream()
/* 330 */       .map(DebugEntityNameGenerator::getEntityName)
/* 331 */       .collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<String> getPotentialTicketHolderNames(PoiInfo $$0) {
/* 336 */     return (Set<String>)getPotentialTicketHolders($$0.pos)
/* 337 */       .stream()
/* 338 */       .map(DebugEntityNameGenerator::getEntityName)
/* 339 */       .collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMobSelected(BrainDebugPayload.BrainDump $$0) {
/* 344 */     return Objects.equals(this.lastLookedAtUuid, $$0.uuid());
/*     */   }
/*     */   
/*     */   private boolean isPlayerCloseEnoughToMob(BrainDebugPayload.BrainDump $$0) {
/* 348 */     LocalPlayer localPlayer = this.minecraft.player;
/* 349 */     BlockPos $$2 = BlockPos.containing(localPlayer.getX(), $$0.pos().y(), localPlayer.getZ());
/* 350 */     BlockPos $$3 = BlockPos.containing((Position)$$0.pos());
/* 351 */     return $$2.closerThan((Vec3i)$$3, 30.0D);
/*     */   }
/*     */   
/*     */   private Collection<UUID> getTicketHolders(BlockPos $$0) {
/* 355 */     return (Collection<UUID>)this.brainDumpsPerEntity.values()
/* 356 */       .stream()
/* 357 */       .filter($$1 -> $$1.hasPoi($$0))
/* 358 */       .map(BrainDebugPayload.BrainDump::uuid)
/* 359 */       .collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection<UUID> getPotentialTicketHolders(BlockPos $$0) {
/* 364 */     return (Collection<UUID>)this.brainDumpsPerEntity.values()
/* 365 */       .stream()
/* 366 */       .filter($$1 -> $$1.hasPotentialPoi($$0))
/* 367 */       .map(BrainDebugPayload.BrainDump::uuid)
/* 368 */       .collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<BlockPos, List<String>> getGhostPois() {
/* 377 */     Map<BlockPos, List<String>> $$0 = Maps.newHashMap();
/* 378 */     for (BrainDebugPayload.BrainDump $$1 : this.brainDumpsPerEntity.values()) {
/* 379 */       for (BlockPos $$2 : Iterables.concat($$1.pois(), $$1.potentialPois())) {
/* 380 */         if (!this.pois.containsKey($$2))
/*     */         {
/* 382 */           ((List<String>)$$0.computeIfAbsent($$2, $$0 -> Lists.newArrayList())).add($$1.name());
/*     */         }
/*     */       } 
/*     */     } 
/* 386 */     return $$0;
/*     */   }
/*     */   
/*     */   private void updateLastLookedAtUuid() {
/* 390 */     DebugRenderer.getTargetedEntity(this.minecraft.getCameraEntity(), 8).ifPresent($$0 -> this.lastLookedAtUuid = $$0.getUUID());
/*     */   }
/*     */   
/*     */   public static class PoiInfo {
/*     */     public final BlockPos pos;
/*     */     public final String type;
/*     */     public int freeTicketCount;
/*     */     
/*     */     public PoiInfo(BlockPos $$0, String $$1, int $$2) {
/* 399 */       this.pos = $$0;
/* 400 */       this.type = $$1;
/* 401 */       this.freeTicketCount = $$2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\BrainDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */