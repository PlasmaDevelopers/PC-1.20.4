/*     */ package net.minecraft.world.level.gameevent;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class EuclideanGameEventListenerRegistry implements GameEventListenerRegistry {
/*  16 */   private final List<GameEventListener> listeners = Lists.newArrayList();
/*  17 */   private final Set<GameEventListener> listenersToRemove = Sets.newHashSet();
/*  18 */   private final List<GameEventListener> listenersToAdd = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private boolean processing;
/*     */   
/*     */   private final ServerLevel level;
/*     */   
/*     */   private final int sectionY;
/*     */   
/*     */   private final OnEmptyAction onEmptyAction;
/*     */ 
/*     */   
/*     */   public EuclideanGameEventListenerRegistry(ServerLevel $$0, int $$1, OnEmptyAction $$2) {
/*  31 */     this.level = $$0;
/*  32 */     this.sectionY = $$1;
/*  33 */     this.onEmptyAction = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  38 */     return this.listeners.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(GameEventListener $$0) {
/*  43 */     if (this.processing) {
/*  44 */       this.listenersToAdd.add($$0);
/*     */     } else {
/*  46 */       this.listeners.add($$0);
/*     */     } 
/*  48 */     DebugPackets.sendGameEventListenerInfo((Level)this.level, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregister(GameEventListener $$0) {
/*  53 */     if (this.processing) {
/*  54 */       this.listenersToRemove.add($$0);
/*     */     } else {
/*  56 */       this.listeners.remove($$0);
/*     */     } 
/*     */     
/*  59 */     if (this.listeners.isEmpty()) {
/*  60 */       this.onEmptyAction.apply(this.sectionY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitInRangeListeners(GameEvent $$0, Vec3 $$1, GameEvent.Context $$2, GameEventListenerRegistry.ListenerVisitor $$3) {
/*  66 */     this.processing = true;
/*  67 */     boolean $$4 = false;
/*     */     try {
/*  69 */       for (Iterator<GameEventListener> $$5 = this.listeners.iterator(); $$5.hasNext(); ) {
/*  70 */         GameEventListener $$6 = $$5.next();
/*  71 */         if (this.listenersToRemove.remove($$6)) {
/*  72 */           $$5.remove();
/*     */           
/*     */           continue;
/*     */         } 
/*  76 */         Optional<Vec3> $$7 = getPostableListenerPosition(this.level, $$1, $$6);
/*  77 */         if ($$7.isPresent()) {
/*  78 */           $$3.visit($$6, $$7.get());
/*  79 */           $$4 = true;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  83 */       this.processing = false;
/*     */     } 
/*     */     
/*  86 */     if (!this.listenersToAdd.isEmpty()) {
/*  87 */       this.listeners.addAll(this.listenersToAdd);
/*  88 */       this.listenersToAdd.clear();
/*     */     } 
/*     */     
/*  91 */     if (!this.listenersToRemove.isEmpty()) {
/*  92 */       this.listeners.removeAll(this.listenersToRemove);
/*  93 */       this.listenersToRemove.clear();
/*     */     } 
/*  95 */     return $$4;
/*     */   }
/*     */   
/*     */   private static Optional<Vec3> getPostableListenerPosition(ServerLevel $$0, Vec3 $$1, GameEventListener $$2) {
/*  99 */     Optional<Vec3> $$3 = $$2.getListenerSource().getPosition((Level)$$0);
/*     */     
/* 101 */     if ($$3.isEmpty()) {
/* 102 */       return Optional.empty();
/*     */     }
/*     */     
/* 105 */     double $$4 = BlockPos.containing((Position)$$3.get()).distSqr((Vec3i)BlockPos.containing((Position)$$1));
/* 106 */     int $$5 = $$2.getListenerRadius() * $$2.getListenerRadius();
/*     */     
/* 108 */     if ($$4 > $$5) {
/* 109 */       return Optional.empty();
/*     */     }
/* 111 */     return $$3;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface OnEmptyAction {
/*     */     void apply(int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\EuclideanGameEventListenerRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */