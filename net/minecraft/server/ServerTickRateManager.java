/*     */ package net.minecraft.server;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundTickingStatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundTickingStepPacket;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.world.TickRateManager;
/*     */ 
/*     */ public class ServerTickRateManager extends TickRateManager {
/*  11 */   private long remainingSprintTicks = 0L;
/*  12 */   private long sprintTickStartTime = 0L;
/*  13 */   private long sprintTimeSpend = 0L;
/*  14 */   private long scheduledCurrentSprintTicks = 0L;
/*     */   private boolean previousIsFrozen = false;
/*     */   private final MinecraftServer server;
/*     */   
/*     */   public ServerTickRateManager(MinecraftServer $$0) {
/*  19 */     this.server = $$0;
/*     */   }
/*     */   
/*     */   public boolean isSprinting() {
/*  23 */     return (this.scheduledCurrentSprintTicks > 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFrozen(boolean $$0) {
/*  28 */     super.setFrozen($$0);
/*  29 */     updateStateToClients();
/*     */   }
/*     */   
/*     */   private void updateStateToClients() {
/*  33 */     this.server.getPlayerList().broadcastAll((Packet)ClientboundTickingStatePacket.from(this));
/*     */   }
/*     */   
/*     */   private void updateStepTicks() {
/*  37 */     this.server.getPlayerList().broadcastAll((Packet)ClientboundTickingStepPacket.from(this));
/*     */   }
/*     */   
/*     */   public boolean stepGameIfPaused(int $$0) {
/*  41 */     if (!isFrozen()) {
/*  42 */       return false;
/*     */     }
/*  44 */     this.frozenTicksToRun = $$0;
/*  45 */     updateStepTicks();
/*  46 */     return true;
/*     */   }
/*     */   
/*     */   public boolean stopStepping() {
/*  50 */     if (this.frozenTicksToRun > 0) {
/*  51 */       this.frozenTicksToRun = 0;
/*  52 */       updateStepTicks();
/*  53 */       return true;
/*     */     } 
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public boolean stopSprinting() {
/*  59 */     if (this.remainingSprintTicks > 0L) {
/*  60 */       finishTickSprint();
/*  61 */       return true;
/*     */     } 
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requestGameToSprint(int $$0) {
/*  68 */     boolean $$1 = (this.remainingSprintTicks > 0L);
/*  69 */     this.sprintTimeSpend = 0L;
/*  70 */     this.scheduledCurrentSprintTicks = $$0;
/*  71 */     this.remainingSprintTicks = $$0;
/*  72 */     this.previousIsFrozen = isFrozen();
/*  73 */     setFrozen(false);
/*  74 */     return $$1;
/*     */   }
/*     */   
/*     */   private void finishTickSprint() {
/*  78 */     long $$0 = this.scheduledCurrentSprintTicks - this.remainingSprintTicks;
/*  79 */     double $$1 = Math.max(1.0D, this.sprintTimeSpend) / TimeUtil.NANOSECONDS_PER_MILLISECOND;
/*  80 */     int $$2 = (int)((TimeUtil.MILLISECONDS_PER_SECOND * $$0) / $$1);
/*  81 */     String $$3 = String.format("%.2f", new Object[] { Double.valueOf(($$0 == 0L) ? millisecondsPerTick() : ($$1 / $$0)) });
/*  82 */     this.scheduledCurrentSprintTicks = 0L;
/*  83 */     this.sprintTimeSpend = 0L;
/*  84 */     this.server.createCommandSourceStack().sendSuccess(() -> Component.translatable("commands.tick.sprint.report", new Object[] { Integer.valueOf($$0), $$1 }), true);
/*  85 */     this.remainingSprintTicks = 0L;
/*  86 */     setFrozen(this.previousIsFrozen);
/*  87 */     this.server.onTickRateChanged();
/*     */   }
/*     */   
/*     */   public boolean checkShouldSprintThisTick() {
/*  91 */     if (!this.runGameElements) {
/*  92 */       return false;
/*     */     }
/*  94 */     if (this.remainingSprintTicks > 0L) {
/*  95 */       this.sprintTickStartTime = System.nanoTime();
/*  96 */       this.remainingSprintTicks--;
/*  97 */       return true;
/*     */     } 
/*  99 */     finishTickSprint();
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTickWork() {
/* 105 */     this.sprintTimeSpend += System.nanoTime() - this.sprintTickStartTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTickRate(float $$0) {
/* 110 */     super.setTickRate($$0);
/* 111 */     this.server.onTickRateChanged();
/* 112 */     updateStateToClients();
/*     */   }
/*     */   
/*     */   public void updateJoiningPlayer(ServerPlayer $$0) {
/* 116 */     $$0.connection.send((Packet)ClientboundTickingStatePacket.from(this));
/* 117 */     $$0.connection.send((Packet)ClientboundTickingStepPacket.from(this));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ServerTickRateManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */