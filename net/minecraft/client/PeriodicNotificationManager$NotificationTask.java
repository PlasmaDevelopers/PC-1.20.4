/*     */ package net.minecraft.client;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NotificationTask
/*     */   extends TimerTask
/*     */ {
/* 136 */   private final Minecraft minecraft = Minecraft.getInstance();
/*     */   
/*     */   private final List<PeriodicNotificationManager.Notification> notifications;
/*     */   
/*     */   private final long period;
/*     */   private final AtomicLong elapsed;
/*     */   
/*     */   public NotificationTask(List<PeriodicNotificationManager.Notification> $$0, long $$1, long $$2) {
/* 144 */     this.notifications = $$0;
/* 145 */     this.period = $$2;
/* 146 */     this.elapsed = new AtomicLong($$1);
/*     */   }
/*     */   
/*     */   public NotificationTask reset(List<PeriodicNotificationManager.Notification> $$0, long $$1) {
/* 150 */     cancel();
/*     */     
/* 152 */     return new NotificationTask($$0, this.elapsed.get(), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 157 */     long $$0 = this.elapsed.getAndAdd(this.period);
/* 158 */     long $$1 = this.elapsed.get();
/*     */     
/* 160 */     for (PeriodicNotificationManager.Notification $$2 : this.notifications) {
/* 161 */       if ($$0 < $$2.delay) {
/*     */         continue;
/*     */       }
/*     */       
/* 165 */       long $$3 = $$0 / $$2.period;
/* 166 */       long $$4 = $$1 / $$2.period;
/*     */       
/* 168 */       if ($$3 != $$4) {
/* 169 */         this.minecraft.execute(() -> SystemToast.add(Minecraft.getInstance().getToasts(), SystemToast.SystemToastId.PERIODIC_NOTIFICATION, (Component)Component.translatable($$0.title, new Object[] { Long.valueOf($$1) }), (Component)Component.translatable($$0.message, new Object[] { Long.valueOf($$1) })));
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\PeriodicNotificationManager$NotificationTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */