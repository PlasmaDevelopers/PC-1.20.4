/*     */ package net.minecraft.server.network;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.HashedWheelTimer;
/*     */ import io.netty.util.Timeout;
/*     */ import io.netty.util.Timer;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LatencySimulator
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/* 200 */   private static final Timer TIMER = (Timer)new HashedWheelTimer();
/*     */   
/*     */   private final int delay;
/*     */   private final int jitter;
/* 204 */   private final List<DelayedMessage> queuedMessages = Lists.newArrayList();
/*     */   
/*     */   public LatencySimulator(int $$0, int $$1) {
/* 207 */     this.delay = $$0;
/* 208 */     this.jitter = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext $$0, Object $$1) {
/* 213 */     delayDownstream($$0, $$1);
/*     */   }
/*     */   
/*     */   private void delayDownstream(ChannelHandlerContext $$0, Object $$1) {
/* 217 */     int $$2 = this.delay + (int)(Math.random() * this.jitter);
/* 218 */     this.queuedMessages.add(new DelayedMessage($$0, $$1));
/* 219 */     TIMER.newTimeout(this::onTimeout, $$2, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   private void onTimeout(Timeout $$0) {
/* 223 */     DelayedMessage $$1 = this.queuedMessages.remove(0);
/* 224 */     $$1.ctx.fireChannelRead($$1.msg);
/*     */   }
/*     */   
/*     */   private static class DelayedMessage {
/*     */     public final ChannelHandlerContext ctx;
/*     */     public final Object msg;
/*     */     
/*     */     public DelayedMessage(ChannelHandlerContext $$0, Object $$1) {
/* 232 */       this.ctx = $$0;
/* 233 */       this.msg = $$1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerConnectionListener$LatencySimulator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */