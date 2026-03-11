/*     */ package net.minecraft.client.renderer.debug;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ 
/*     */ public class GameEventListenerRenderer implements DebugRenderer.SimpleDebugRenderer {
/*     */   private final Minecraft minecraft;
/*     */   private static final int LISTENER_RENDER_DIST = 32;
/*     */   private static final float BOX_HEIGHT = 1.0F;
/*  31 */   private final List<TrackedGameEvent> trackedGameEvents = Lists.newArrayList();
/*  32 */   private final List<TrackedListener> trackedListeners = Lists.newArrayList();
/*     */   
/*     */   public GameEventListenerRenderer(Minecraft $$0) {
/*  35 */     this.minecraft = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/*  40 */     ClientLevel clientLevel = this.minecraft.level;
/*     */     
/*  42 */     if (clientLevel == null) {
/*  43 */       this.trackedGameEvents.clear();
/*  44 */       this.trackedListeners.clear();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  49 */     Vec3 $$6 = new Vec3($$2, 0.0D, $$4);
/*     */     
/*  51 */     this.trackedGameEvents.removeIf(TrackedGameEvent::isExpired);
/*  52 */     this.trackedListeners.removeIf($$2 -> $$2.isExpired($$0, $$1));
/*     */     
/*  54 */     VertexConsumer $$7 = $$1.getBuffer(RenderType.lines());
/*     */     
/*  56 */     for (Iterator<TrackedListener> iterator = this.trackedListeners.iterator(); iterator.hasNext(); ) { TrackedListener $$8 = iterator.next();
/*  57 */       $$8.getPosition((Level)clientLevel).ifPresent($$6 -> {
/*     */             double $$7 = $$6.x() - $$0.getListenerRadius();
/*     */             
/*     */             double $$8 = $$6.y() - $$0.getListenerRadius();
/*     */             
/*     */             double $$9 = $$6.z() - $$0.getListenerRadius();
/*     */             
/*     */             double $$10 = $$6.x() + $$0.getListenerRadius();
/*     */             double $$11 = $$6.y() + $$0.getListenerRadius();
/*     */             double $$12 = $$6.z() + $$0.getListenerRadius();
/*     */             LevelRenderer.renderVoxelShape($$1, $$2, Shapes.create(new AABB($$7, $$8, $$9, $$10, $$11, $$12)), -$$3, -$$4, -$$5, 1.0F, 1.0F, 0.0F, 0.35F, true);
/*     */           }); }
/*     */     
/*  70 */     VertexConsumer $$9 = $$1.getBuffer(RenderType.debugFilledBox());
/*  71 */     for (TrackedListener $$10 : this.trackedListeners) {
/*  72 */       $$10.getPosition((Level)clientLevel).ifPresent($$5 -> LevelRenderer.addChainedFilledBoxVertices($$0, $$1, $$5.x() - 0.25D - $$2, $$5.y() - $$3, $$5.z() - 0.25D - $$4, $$5.x() + 0.25D - $$2, $$5.y() - $$3 + 1.0D, $$5.z() + 0.25D - $$4, 1.0F, 1.0F, 0.0F, 0.35F));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     for (TrackedListener $$11 : this.trackedListeners) {
/*  91 */       $$11.getPosition((Level)clientLevel).ifPresent($$2 -> {
/*     */             DebugRenderer.renderFloatingText($$0, $$1, "Listener Origin", $$2.x(), $$2.y() + 1.7999999523162842D, $$2.z(), -1, 0.025F);
/*     */             
/*     */             DebugRenderer.renderFloatingText($$0, $$1, BlockPos.containing((Position)$$2).toString(), $$2.x(), $$2.y() + 1.5D, $$2.z(), -6959665, 0.025F);
/*     */           });
/*     */     } 
/*  97 */     for (TrackedGameEvent $$12 : this.trackedGameEvents) {
/*  98 */       Vec3 $$13 = $$12.position;
/*  99 */       double $$14 = 0.20000000298023224D;
/* 100 */       double $$15 = $$13.x - 0.20000000298023224D;
/* 101 */       double $$16 = $$13.y - 0.20000000298023224D;
/* 102 */       double $$17 = $$13.z - 0.20000000298023224D;
/*     */       
/* 104 */       double $$18 = $$13.x + 0.20000000298023224D;
/* 105 */       double $$19 = $$13.y + 0.20000000298023224D + 0.5D;
/* 106 */       double $$20 = $$13.z + 0.20000000298023224D;
/*     */       
/* 108 */       renderFilledBox($$0, $$1, new AABB($$15, $$16, $$17, $$18, $$19, $$20), 1.0F, 1.0F, 1.0F, 0.2F);
/* 109 */       DebugRenderer.renderFloatingText($$0, $$1, $$12.gameEvent.location().toString(), $$13.x, $$13.y + 0.8500000238418579D, $$13.z, -7564911, 0.0075F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void renderFilledBox(PoseStack $$0, MultiBufferSource $$1, AABB $$2, float $$3, float $$4, float $$5, float $$6) {
/* 114 */     Camera $$7 = (Minecraft.getInstance()).gameRenderer.getMainCamera();
/* 115 */     if (!$$7.isInitialized()) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     Vec3 $$8 = $$7.getPosition().reverse();
/* 120 */     DebugRenderer.renderFilledBox($$0, $$1, $$2.move($$8), $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public void trackGameEvent(ResourceKey<GameEvent> $$0, Vec3 $$1) {
/* 124 */     this.trackedGameEvents.add(new TrackedGameEvent(Util.getMillis(), $$0, $$1));
/*     */   }
/*     */   
/*     */   public void trackListener(PositionSource $$0, int $$1) {
/* 128 */     this.trackedListeners.add(new TrackedListener($$0, $$1));
/*     */   }
/*     */   
/*     */   private static class TrackedListener implements GameEventListener {
/*     */     public final PositionSource listenerSource;
/*     */     public final int listenerRange;
/*     */     
/*     */     public TrackedListener(PositionSource $$0, int $$1) {
/* 136 */       this.listenerSource = $$0;
/* 137 */       this.listenerRange = $$1;
/*     */     }
/*     */     
/*     */     public boolean isExpired(Level $$0, Vec3 $$1) {
/* 141 */       return this.listenerSource.getPosition($$0)
/* 142 */         .filter($$1 -> ($$1.distanceToSqr($$0) <= 1024.0D))
/* 143 */         .isPresent();
/*     */     }
/*     */     
/*     */     public Optional<Vec3> getPosition(Level $$0) {
/* 147 */       return this.listenerSource.getPosition($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionSource getListenerSource() {
/* 152 */       return this.listenerSource;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getListenerRadius() {
/* 157 */       return this.listenerRange;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean handleGameEvent(ServerLevel $$0, GameEvent $$1, GameEvent.Context $$2, Vec3 $$3) {
/* 163 */       return false;
/*     */     } }
/*     */   private static final class TrackedGameEvent extends Record { private final long timeStamp; final ResourceKey<GameEvent> gameEvent; final Vec3 position;
/*     */     
/* 167 */     TrackedGameEvent(long $$0, ResourceKey<GameEvent> $$1, Vec3 $$2) { this.timeStamp = $$0; this.gameEvent = $$1; this.position = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/debug/GameEventListenerRenderer$TrackedGameEvent;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #167	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 167 */       //   0	7	0	this	Lnet/minecraft/client/renderer/debug/GameEventListenerRenderer$TrackedGameEvent; } public long timeStamp() { return this.timeStamp; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/debug/GameEventListenerRenderer$TrackedGameEvent;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #167	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/debug/GameEventListenerRenderer$TrackedGameEvent; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/debug/GameEventListenerRenderer$TrackedGameEvent;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #167	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/renderer/debug/GameEventListenerRenderer$TrackedGameEvent;
/* 167 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceKey<GameEvent> gameEvent() { return this.gameEvent; } public Vec3 position() { return this.position; }
/*     */      public boolean isExpired() {
/* 169 */       return (Util.getMillis() - this.timeStamp > 3000L);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\GameEventListenerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */