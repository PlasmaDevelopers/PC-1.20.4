/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.phys.Vec3;
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
/*     */ class TrackedListener
/*     */   implements GameEventListener
/*     */ {
/*     */   public final PositionSource listenerSource;
/*     */   public final int listenerRange;
/*     */   
/*     */   public TrackedListener(PositionSource $$0, int $$1) {
/* 136 */     this.listenerSource = $$0;
/* 137 */     this.listenerRange = $$1;
/*     */   }
/*     */   
/*     */   public boolean isExpired(Level $$0, Vec3 $$1) {
/* 141 */     return this.listenerSource.getPosition($$0)
/* 142 */       .filter($$1 -> ($$1.distanceToSqr($$0) <= 1024.0D))
/* 143 */       .isPresent();
/*     */   }
/*     */   
/*     */   public Optional<Vec3> getPosition(Level $$0) {
/* 147 */     return this.listenerSource.getPosition($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public PositionSource getListenerSource() {
/* 152 */     return this.listenerSource;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getListenerRadius() {
/* 157 */     return this.listenerRange;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleGameEvent(ServerLevel $$0, GameEvent $$1, GameEvent.Context $$2, Vec3 $$3) {
/* 163 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\GameEventListenerRenderer$TrackedListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */