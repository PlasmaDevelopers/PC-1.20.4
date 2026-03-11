/*     */ package net.minecraft.world.level.gameevent;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ListenerInfo
/*     */   implements Comparable<GameEvent.ListenerInfo>
/*     */ {
/*     */   private final GameEvent gameEvent;
/*     */   private final Vec3 source;
/*     */   private final GameEvent.Context context;
/*     */   private final GameEventListener recipient;
/*     */   private final double distanceToRecipient;
/*     */   
/*     */   public ListenerInfo(GameEvent $$0, Vec3 $$1, GameEvent.Context $$2, GameEventListener $$3, Vec3 $$4) {
/* 143 */     this.gameEvent = $$0;
/* 144 */     this.source = $$1;
/* 145 */     this.context = $$2;
/* 146 */     this.recipient = $$3;
/* 147 */     this.distanceToRecipient = $$1.distanceToSqr($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ListenerInfo $$0) {
/* 152 */     return Double.compare(this.distanceToRecipient, $$0.distanceToRecipient);
/*     */   }
/*     */   
/*     */   public GameEvent gameEvent() {
/* 156 */     return this.gameEvent;
/*     */   }
/*     */   
/*     */   public Vec3 source() {
/* 160 */     return this.source;
/*     */   }
/*     */   
/*     */   public GameEvent.Context context() {
/* 164 */     return this.context;
/*     */   }
/*     */   
/*     */   public GameEventListener recipient() {
/* 168 */     return this.recipient;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\GameEvent$ListenerInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */