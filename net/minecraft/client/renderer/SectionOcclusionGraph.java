/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ChunkTrackingView;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SectionOcclusionGraph {
/*  40 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  42 */   private static final Direction[] DIRECTIONS = Direction.values();
/*     */   
/*     */   private static final int MINIMUM_ADVANCED_CULLING_DISTANCE = 60;
/*  45 */   private static final double CEILED_SECTION_DIAGONAL = Math.ceil(Math.sqrt(3.0D) * 16.0D);
/*     */   
/*     */   private boolean needsFullUpdate = true;
/*     */   
/*     */   @Nullable
/*     */   private Future<?> fullUpdateTask;
/*     */   @Nullable
/*     */   private ViewArea viewArea;
/*  53 */   private final AtomicReference<GraphState> currentGraph = new AtomicReference<>();
/*  54 */   private final AtomicReference<GraphEvents> nextGraphEvents = new AtomicReference<>();
/*     */   
/*  56 */   private final AtomicBoolean needsFrustumUpdate = new AtomicBoolean(false);
/*     */   
/*     */   public void waitAndReset(@Nullable ViewArea $$0) {
/*  59 */     if (this.fullUpdateTask != null) {
/*     */       try {
/*  61 */         this.fullUpdateTask.get();
/*  62 */         this.fullUpdateTask = null;
/*  63 */       } catch (Exception $$1) {
/*  64 */         LOGGER.warn("Full update failed", $$1);
/*     */       } 
/*     */     }
/*  67 */     this.viewArea = $$0;
/*  68 */     if ($$0 != null) {
/*  69 */       this.currentGraph.set(new GraphState($$0.sections.length));
/*  70 */       invalidate();
/*     */     } else {
/*  72 */       this.currentGraph.set(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invalidate() {
/*  77 */     this.needsFullUpdate = true;
/*     */   }
/*     */   
/*     */   public void addSectionsInFrustum(Frustum $$0, List<SectionRenderDispatcher.RenderSection> $$1) {
/*  81 */     for (Node $$2 : (((GraphState)this.currentGraph.get()).storage()).renderSections) {
/*  82 */       if ($$0.isVisible($$2.section.getBoundingBox())) {
/*  83 */         $$1.add($$2.section);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean consumeFrustumUpdate() {
/*  89 */     return this.needsFrustumUpdate.compareAndSet(true, false);
/*     */   }
/*     */   
/*     */   public void onChunkLoaded(ChunkPos $$0) {
/*  93 */     GraphEvents $$1 = this.nextGraphEvents.get();
/*  94 */     if ($$1 != null) {
/*  95 */       addNeighbors($$1, $$0);
/*     */     }
/*  97 */     GraphEvents $$2 = ((GraphState)this.currentGraph.get()).events;
/*     */     
/*  99 */     if ($$2 != $$1) {
/* 100 */       addNeighbors($$2, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onSectionCompiled(SectionRenderDispatcher.RenderSection $$0) {
/* 105 */     GraphEvents $$1 = this.nextGraphEvents.get();
/* 106 */     if ($$1 != null) {
/* 107 */       $$1.sectionsToPropagateFrom.add($$0);
/*     */     }
/* 109 */     GraphEvents $$2 = ((GraphState)this.currentGraph.get()).events;
/*     */     
/* 111 */     if ($$2 != $$1) {
/* 112 */       $$2.sectionsToPropagateFrom.add($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(boolean $$0, Camera $$1, Frustum $$2, List<SectionRenderDispatcher.RenderSection> $$3) {
/* 117 */     Vec3 $$4 = $$1.getPosition();
/* 118 */     if (this.needsFullUpdate && (this.fullUpdateTask == null || this.fullUpdateTask.isDone())) {
/* 119 */       scheduleFullUpdate($$0, $$1, $$4);
/*     */     }
/* 121 */     runPartialUpdate($$0, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private void scheduleFullUpdate(boolean $$0, Camera $$1, Vec3 $$2) {
/* 125 */     this.needsFullUpdate = false;
/* 126 */     this.fullUpdateTask = Util.backgroundExecutor().submit(() -> {
/*     */           GraphState $$3 = new GraphState(this.viewArea.sections.length);
/*     */           this.nextGraphEvents.set($$3.events);
/*     */           Queue<Node> $$4 = Queues.newArrayDeque();
/*     */           initializeQueueForFullUpdate($$0, $$4);
/*     */           $$4.forEach(());
/*     */           runUpdates($$3.storage, $$1, $$4, $$2, ());
/*     */           this.currentGraph.set($$3);
/*     */           this.nextGraphEvents.set(null);
/*     */           this.needsFrustumUpdate.set(true);
/*     */         });
/*     */   }
/*     */   
/*     */   private void runPartialUpdate(boolean $$0, Frustum $$1, List<SectionRenderDispatcher.RenderSection> $$2, Vec3 $$3) {
/* 140 */     GraphState $$4 = this.currentGraph.get();
/* 141 */     queueSectionsWithNewNeighbors($$4);
/* 142 */     if (!$$4.events.sectionsToPropagateFrom.isEmpty()) {
/* 143 */       Queue<Node> $$5 = Queues.newArrayDeque();
/* 144 */       while (!$$4.events.sectionsToPropagateFrom.isEmpty()) {
/* 145 */         SectionRenderDispatcher.RenderSection $$6 = $$4.events.sectionsToPropagateFrom.poll();
/* 146 */         Node $$7 = $$4.storage.sectionToNodeMap.get($$6);
/*     */         
/* 148 */         if ($$7 != null && $$7.section == $$6) {
/* 149 */           $$5.add($$7);
/*     */         }
/*     */       } 
/* 152 */       Frustum $$8 = LevelRenderer.offsetFrustum($$1);
/* 153 */       Consumer<SectionRenderDispatcher.RenderSection> $$9 = $$2 -> {
/*     */           if ($$0.isVisible($$2.getBoundingBox())) {
/*     */             $$1.add($$2);
/*     */           }
/*     */         };
/* 158 */       runUpdates($$4.storage, $$3, $$5, $$0, $$9);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void queueSectionsWithNewNeighbors(GraphState $$0) {
/* 163 */     for (LongIterator $$1 = $$0.events.chunksWhichReceivedNeighbors.iterator(); $$1.hasNext(); ) {
/* 164 */       long $$2 = $$1.nextLong();
/* 165 */       List<SectionRenderDispatcher.RenderSection> $$3 = (List<SectionRenderDispatcher.RenderSection>)$$0.storage.chunksWaitingForNeighbors.get($$2);
/* 166 */       if ($$3 != null && ((SectionRenderDispatcher.RenderSection)$$3.get(0)).hasAllNeighbors()) {
/* 167 */         $$0.events.sectionsToPropagateFrom.addAll($$3);
/* 168 */         $$0.storage.chunksWaitingForNeighbors.remove($$2);
/*     */       } 
/*     */     } 
/* 171 */     $$0.events.chunksWhichReceivedNeighbors.clear();
/*     */   }
/*     */   
/*     */   private void addNeighbors(GraphEvents $$0, ChunkPos $$1) {
/* 175 */     $$0.chunksWhichReceivedNeighbors.add(ChunkPos.asLong($$1.x - 1, $$1.z));
/* 176 */     $$0.chunksWhichReceivedNeighbors.add(ChunkPos.asLong($$1.x, $$1.z - 1));
/* 177 */     $$0.chunksWhichReceivedNeighbors.add(ChunkPos.asLong($$1.x + 1, $$1.z));
/* 178 */     $$0.chunksWhichReceivedNeighbors.add(ChunkPos.asLong($$1.x, $$1.z + 1));
/*     */   }
/*     */   
/*     */   private void initializeQueueForFullUpdate(Camera $$0, Queue<Node> $$1) {
/* 182 */     int $$2 = 16;
/* 183 */     Vec3 $$3 = $$0.getPosition();
/* 184 */     BlockPos $$4 = $$0.getBlockPosition();
/* 185 */     SectionRenderDispatcher.RenderSection $$5 = this.viewArea.getRenderSectionAt($$4);
/*     */     
/* 187 */     if ($$5 == null) {
/*     */       
/* 189 */       LevelHeightAccessor $$6 = this.viewArea.getLevelHeightAccessor();
/* 190 */       boolean $$7 = ($$4.getY() > $$6.getMinBuildHeight());
/* 191 */       int $$8 = $$7 ? ($$6.getMaxBuildHeight() - 8) : ($$6.getMinBuildHeight() + 8);
/*     */       
/* 193 */       int $$9 = Mth.floor($$3.x / 16.0D) * 16;
/* 194 */       int $$10 = Mth.floor($$3.z / 16.0D) * 16;
/* 195 */       int $$11 = this.viewArea.getViewDistance();
/* 196 */       List<Node> $$12 = Lists.newArrayList();
/* 197 */       for (int $$13 = -$$11; $$13 <= $$11; $$13++) {
/* 198 */         for (int $$14 = -$$11; $$14 <= $$11; $$14++) {
/* 199 */           SectionRenderDispatcher.RenderSection $$15 = this.viewArea.getRenderSectionAt(new BlockPos($$9 + SectionPos.sectionToBlockCoord($$13, 8), $$8, $$10 + SectionPos.sectionToBlockCoord($$14, 8)));
/* 200 */           if ($$15 != null && isInViewDistance($$4, $$15.getOrigin())) {
/* 201 */             Direction $$16 = $$7 ? Direction.DOWN : Direction.UP;
/* 202 */             Node $$17 = new Node($$15, $$16, 0);
/* 203 */             $$17.setDirections($$17.directions, $$16);
/* 204 */             if ($$13 > 0) {
/* 205 */               $$17.setDirections($$17.directions, Direction.EAST);
/* 206 */             } else if ($$13 < 0) {
/* 207 */               $$17.setDirections($$17.directions, Direction.WEST);
/*     */             } 
/* 209 */             if ($$14 > 0) {
/* 210 */               $$17.setDirections($$17.directions, Direction.SOUTH);
/* 211 */             } else if ($$14 < 0) {
/* 212 */               $$17.setDirections($$17.directions, Direction.NORTH);
/*     */             } 
/* 214 */             $$12.add($$17);
/*     */           } 
/*     */         } 
/*     */       } 
/* 218 */       $$12.sort(Comparator.comparingDouble($$1 -> $$0.distSqr((Vec3i)$$1.section.getOrigin().offset(8, 8, 8))));
/* 219 */       $$1.addAll($$12);
/*     */     } else {
/* 221 */       $$1.add(new Node($$5, null, 0));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runUpdates(GraphStorage $$0, Vec3 $$1, Queue<Node> $$2, boolean $$3, Consumer<SectionRenderDispatcher.RenderSection> $$4) {
/* 226 */     int $$5 = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     BlockPos $$6 = new BlockPos(Mth.floor($$1.x / 16.0D) * 16, Mth.floor($$1.y / 16.0D) * 16, Mth.floor($$1.z / 16.0D) * 16);
/*     */     
/* 233 */     BlockPos $$7 = $$6.offset(8, 8, 8);
/*     */     
/* 235 */     while (!$$2.isEmpty()) {
/* 236 */       Node $$8 = $$2.poll();
/* 237 */       SectionRenderDispatcher.RenderSection $$9 = $$8.section;
/*     */       
/* 239 */       if ($$0.renderSections.add($$8)) {
/* 240 */         $$4.accept($$8.section);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 246 */       boolean $$10 = (Math.abs($$9.getOrigin().getX() - $$6.getX()) > 60 || Math.abs($$9.getOrigin().getY() - $$6.getY()) > 60 || Math.abs($$9.getOrigin().getZ() - $$6.getZ()) > 60);
/*     */       
/* 248 */       for (Direction $$11 : DIRECTIONS) {
/* 249 */         SectionRenderDispatcher.RenderSection $$12 = getRelativeFrom($$6, $$9, $$11);
/*     */         
/* 251 */         if ($$12 == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 255 */         if ($$3 && $$8.hasDirection($$11.getOpposite())) {
/*     */           continue;
/*     */         }
/*     */         
/* 259 */         if ($$3 && $$8.hasSourceDirections()) {
/* 260 */           SectionRenderDispatcher.CompiledSection $$13 = $$9.getCompiled();
/* 261 */           boolean $$14 = false;
/* 262 */           for (int $$15 = 0; $$15 < DIRECTIONS.length; $$15++) {
/* 263 */             if ($$8.hasSourceDirection($$15) && $$13.facesCanSeeEachother(DIRECTIONS[$$15].getOpposite(), $$11)) {
/* 264 */               $$14 = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 269 */           if (!$$14) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 274 */         if ($$3 && $$10) {
/* 275 */           BlockPos $$16 = $$12.getOrigin();
/* 276 */           BlockPos $$17 = $$16.offset((
/* 277 */               ($$11.getAxis() == Direction.Axis.X) ? ($$7.getX() > $$16.getX()) : ($$7.getX() < $$16.getX())) ? 16 : 0, 
/* 278 */               (($$11.getAxis() == Direction.Axis.Y) ? ($$7.getY() > $$16.getY()) : ($$7.getY() < $$16.getY())) ? 16 : 0, 
/* 279 */               (($$11.getAxis() == Direction.Axis.Z) ? ($$7.getZ() > $$16.getZ()) : ($$7.getZ() < $$16.getZ())) ? 16 : 0);
/*     */           
/* 281 */           Vec3 $$18 = new Vec3($$17.getX(), $$17.getY(), $$17.getZ());
/* 282 */           Vec3 $$19 = $$1.subtract($$18).normalize().scale(CEILED_SECTION_DIAGONAL);
/* 283 */           boolean $$20 = true;
/* 284 */           while ($$1.subtract($$18).lengthSqr() > 3600.0D) {
/* 285 */             $$18 = $$18.add($$19);
/* 286 */             LevelHeightAccessor $$21 = this.viewArea.getLevelHeightAccessor();
/* 287 */             if ($$18.y > $$21.getMaxBuildHeight() || $$18.y < $$21.getMinBuildHeight()) {
/*     */               break;
/*     */             }
/* 290 */             SectionRenderDispatcher.RenderSection $$22 = this.viewArea.getRenderSectionAt(BlockPos.containing($$18.x, $$18.y, $$18.z));
/* 291 */             if ($$22 == null || $$0.sectionToNodeMap.get($$22) == null) {
/* 292 */               $$20 = false;
/*     */               break;
/*     */             } 
/*     */           } 
/* 296 */           if (!$$20) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 301 */         Node $$23 = $$0.sectionToNodeMap.get($$12);
/* 302 */         if ($$23 != null) {
/*     */           
/* 304 */           $$23.addSourceDirection($$11);
/*     */         }
/*     */         else {
/*     */           
/* 308 */           Node $$24 = new Node($$12, $$11, $$8.step + 1);
/* 309 */           $$24.setDirections($$8.directions, $$11);
/*     */           
/* 311 */           if ($$12.hasAllNeighbors()) {
/* 312 */             $$2.add($$24);
/* 313 */             $$0.sectionToNodeMap.put($$12, $$24);
/*     */           }
/* 315 */           else if (isInViewDistance($$6, $$12.getOrigin())) {
/* 316 */             $$0.sectionToNodeMap.put($$12, $$24);
/* 317 */             ((List<SectionRenderDispatcher.RenderSection>)$$0.chunksWaitingForNeighbors.computeIfAbsent(ChunkPos.asLong($$12.getOrigin()), $$0 -> new ArrayList())).add($$12);
/*     */           } 
/*     */         } 
/*     */         continue;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private boolean isInViewDistance(BlockPos $$0, BlockPos $$1) {
/* 325 */     int $$2 = SectionPos.blockToSectionCoord($$0.getX());
/* 326 */     int $$3 = SectionPos.blockToSectionCoord($$0.getZ());
/*     */     
/* 328 */     int $$4 = SectionPos.blockToSectionCoord($$1.getX());
/* 329 */     int $$5 = SectionPos.blockToSectionCoord($$1.getZ());
/*     */     
/* 331 */     return ChunkTrackingView.isInViewDistance($$2, $$3, this.viewArea.getViewDistance(), $$4, $$5);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private SectionRenderDispatcher.RenderSection getRelativeFrom(BlockPos $$0, SectionRenderDispatcher.RenderSection $$1, Direction $$2) {
/* 336 */     BlockPos $$3 = $$1.getRelativeOrigin($$2);
/*     */     
/* 338 */     if (!isInViewDistance($$0, $$3)) {
/* 339 */       return null;
/*     */     }
/* 341 */     if (Mth.abs($$0.getY() - $$3.getY()) > this.viewArea.getViewDistance() * 16) {
/* 342 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 346 */     return this.viewArea.getRenderSectionAt($$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @VisibleForDebug
/*     */   protected Node getNode(SectionRenderDispatcher.RenderSection $$0) {
/* 352 */     return ((GraphState)this.currentGraph.get()).storage.sectionToNodeMap.get($$0);
/*     */   }
/*     */   private static final class GraphState extends Record { final SectionOcclusionGraph.GraphStorage storage; final SectionOcclusionGraph.GraphEvents events;
/* 355 */     public SectionOcclusionGraph.GraphEvents events() { return this.events; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphState;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #355	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphState;
/* 355 */       //   0	8	1	$$0	Ljava/lang/Object; } public SectionOcclusionGraph.GraphStorage storage() { return this.storage; } private GraphState(SectionOcclusionGraph.GraphStorage $$0, SectionOcclusionGraph.GraphEvents $$1) { this.storage = $$0; this.events = $$1; } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphState;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #355	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphState;
/*     */     } public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphState;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #355	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphState;
/*     */     } public GraphState(int $$0) {
/* 360 */       this(new SectionOcclusionGraph.GraphStorage($$0), new SectionOcclusionGraph.GraphEvents());
/*     */     } }
/*     */   private static final class GraphEvents extends Record { final LongSet chunksWhichReceivedNeighbors; final BlockingQueue<SectionRenderDispatcher.RenderSection> sectionsToPropagateFrom;
/*     */     
/* 364 */     public BlockingQueue<SectionRenderDispatcher.RenderSection> sectionsToPropagateFrom() { return this.sectionsToPropagateFrom; } public LongSet chunksWhichReceivedNeighbors() { return this.chunksWhichReceivedNeighbors; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphEvents;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #364	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphEvents;
/* 364 */       //   0	8	1	$$0	Ljava/lang/Object; } private GraphEvents(LongSet $$0, BlockingQueue<SectionRenderDispatcher.RenderSection> $$1) { this.chunksWhichReceivedNeighbors = $$0; this.sectionsToPropagateFrom = $$1; } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphEvents;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #364	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphEvents;
/*     */     } public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphEvents;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #364	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/SectionOcclusionGraph$GraphEvents;
/*     */     } public GraphEvents() {
/* 369 */       this((LongSet)new LongOpenHashSet(), new LinkedBlockingQueue<>());
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class GraphStorage {
/*     */     public final SectionOcclusionGraph.SectionToNodeMap sectionToNodeMap;
/*     */     public final LinkedHashSet<SectionOcclusionGraph.Node> renderSections;
/*     */     public final Long2ObjectMap<List<SectionRenderDispatcher.RenderSection>> chunksWaitingForNeighbors;
/*     */     
/*     */     public GraphStorage(int $$0) {
/* 379 */       this.sectionToNodeMap = new SectionOcclusionGraph.SectionToNodeMap($$0);
/* 380 */       this.renderSections = new LinkedHashSet<>($$0);
/* 381 */       this.chunksWaitingForNeighbors = (Long2ObjectMap<List<SectionRenderDispatcher.RenderSection>>)new Long2ObjectOpenHashMap();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SectionToNodeMap {
/*     */     private final SectionOcclusionGraph.Node[] nodes;
/*     */     
/*     */     SectionToNodeMap(int $$0) {
/* 389 */       this.nodes = new SectionOcclusionGraph.Node[$$0];
/*     */     }
/*     */     
/*     */     public void put(SectionRenderDispatcher.RenderSection $$0, SectionOcclusionGraph.Node $$1) {
/* 393 */       this.nodes[$$0.index] = $$1;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public SectionOcclusionGraph.Node get(SectionRenderDispatcher.RenderSection $$0) {
/* 398 */       int $$1 = $$0.index;
/* 399 */       if ($$1 < 0 || $$1 >= this.nodes.length) {
/* 400 */         return null;
/*     */       }
/* 402 */       return this.nodes[$$1];
/*     */     }
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   protected static class Node {
/*     */     @VisibleForDebug
/*     */     protected final SectionRenderDispatcher.RenderSection section;
/*     */     private byte sourceDirections;
/*     */     byte directions;
/*     */     @VisibleForDebug
/*     */     protected final int step;
/*     */     
/*     */     Node(SectionRenderDispatcher.RenderSection $$0, @Nullable Direction $$1, int $$2) {
/* 416 */       this.section = $$0;
/* 417 */       if ($$1 != null) {
/* 418 */         addSourceDirection($$1);
/*     */       }
/* 420 */       this.step = $$2;
/*     */     }
/*     */     
/*     */     void setDirections(byte $$0, Direction $$1) {
/* 424 */       this.directions = (byte)(this.directions | $$0 | 1 << $$1.ordinal());
/*     */     }
/*     */     
/*     */     boolean hasDirection(Direction $$0) {
/* 428 */       return ((this.directions & 1 << $$0.ordinal()) > 0);
/*     */     }
/*     */     
/*     */     void addSourceDirection(Direction $$0) {
/* 432 */       this.sourceDirections = (byte)(this.sourceDirections | this.sourceDirections | 1 << $$0.ordinal());
/*     */     }
/*     */     
/*     */     @VisibleForDebug
/*     */     protected boolean hasSourceDirection(int $$0) {
/* 437 */       return ((this.sourceDirections & 1 << $$0) > 0);
/*     */     }
/*     */     
/*     */     boolean hasSourceDirections() {
/* 441 */       return (this.sourceDirections != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 446 */       return this.section.getOrigin().hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 451 */       if (!($$0 instanceof Node)) {
/* 452 */         return false;
/*     */       }
/* 454 */       Node $$1 = (Node)$$0;
/* 455 */       return this.section.getOrigin().equals($$1.section.getOrigin());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\SectionOcclusionGraph.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */