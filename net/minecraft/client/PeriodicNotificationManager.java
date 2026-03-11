/*     */ package net.minecraft.client;
/*     */ import com.google.common.math.LongMath;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
/*     */ import java.io.Reader;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PeriodicNotificationManager extends SimplePreparableReloadListener<Map<String, List<PeriodicNotificationManager.Notification>>> implements AutoCloseable {
/*     */   private static final Codec<Map<String, List<Notification>>> CODEC;
/*     */   
/*     */   static {
/*  31 */     CODEC = (Codec<Map<String, List<Notification>>>)Codec.unboundedMap((Codec)Codec.STRING, RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.LONG.optionalFieldOf("delay", Long.valueOf(0L)).forGetter(Notification::delay), (App)Codec.LONG.fieldOf("period").forGetter(Notification::period), (App)Codec.STRING.fieldOf("title").forGetter(Notification::title), (App)Codec.STRING.fieldOf("message").forGetter(Notification::message)).apply((Applicative)$$0, Notification::new))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  36 */         .listOf());
/*     */   }
/*  38 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final ResourceLocation notifications;
/*     */   
/*     */   private final Object2BooleanFunction<String> selector;
/*     */   @Nullable
/*     */   private Timer timer;
/*     */   @Nullable
/*     */   private NotificationTask notificationTask;
/*     */   
/*     */   public PeriodicNotificationManager(ResourceLocation $$0, Object2BooleanFunction<String> $$1) {
/*  49 */     this.notifications = $$0;
/*  50 */     this.selector = $$1;
/*     */   }
/*     */   
/*     */   protected Map<String, List<Notification>> prepare(ResourceManager $$0, ProfilerFiller $$1) {
/*     */     
/*  55 */     try { Reader $$2 = $$0.openAsReader(this.notifications); 
/*  56 */       try { Map<String, List<Notification>> map = CODEC.parse((DynamicOps)JsonOps.INSTANCE, JsonParser.parseReader($$2)).result().orElseThrow();
/*  57 */         if ($$2 != null) $$2.close();  return map; } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$3)
/*  58 */     { LOGGER.warn("Failed to load {}", this.notifications, $$3);
/*     */       
/*  60 */       return (Map<String, List<Notification>>)ImmutableMap.of(); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void apply(Map<String, List<Notification>> $$0, ResourceManager $$1, ProfilerFiller $$2) {
/*  69 */     List<Notification> $$3 = (List<Notification>)$$0.entrySet().stream().filter($$0 -> ((Boolean)this.selector.apply($$0.getKey())).booleanValue()).map(Map.Entry::getValue).flatMap(Collection::stream).collect(Collectors.toList());
/*     */     
/*  71 */     if ($$3.isEmpty()) {
/*  72 */       stopTimer();
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     if ($$3.stream().anyMatch($$0 -> ($$0.period == 0L))) {
/*  77 */       Util.logAndPauseIfInIde("A periodic notification in " + this.notifications + " has a period of zero minutes");
/*  78 */       stopTimer();
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     long $$4 = calculateInitialDelay($$3);
/*  83 */     long $$5 = calculateOptimalPeriod($$3, $$4);
/*     */     
/*  85 */     if (this.timer == null) {
/*  86 */       this.timer = new Timer();
/*     */     }
/*     */     
/*  89 */     if (this.notificationTask == null) {
/*  90 */       this.notificationTask = new NotificationTask($$3, $$4, $$5);
/*     */     } else {
/*  92 */       this.notificationTask = this.notificationTask.reset($$3, $$5);
/*     */     } 
/*     */     
/*  95 */     this.timer.scheduleAtFixedRate(this.notificationTask, TimeUnit.MINUTES.toMillis($$4), TimeUnit.MINUTES.toMillis($$5));
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 100 */     stopTimer();
/*     */   }
/*     */   
/*     */   private void stopTimer() {
/* 104 */     if (this.timer != null) {
/* 105 */       this.timer.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   private long calculateOptimalPeriod(List<Notification> $$0, long $$1) {
/* 110 */     return $$0.stream()
/* 111 */       .mapToLong($$1 -> {
/*     */           long $$2 = $$1.delay - $$0;
/*     */           
/*     */           return LongMath.gcd($$2, $$1.period);
/* 115 */         }).reduce(LongMath::gcd)
/* 116 */       .orElseThrow(() -> new IllegalStateException("Empty notifications from: " + this.notifications));
/*     */   }
/*     */   
/*     */   private long calculateInitialDelay(List<Notification> $$0) {
/* 120 */     return $$0.stream()
/* 121 */       .mapToLong($$0 -> $$0.delay)
/* 122 */       .min()
/* 123 */       .orElse(0L);
/*     */   }
/*     */   public static final class Notification extends Record { final long delay; final long period; final String title; final String message;
/* 126 */     public String message() { return this.message; } public String title() { return this.title; } public long period() { return this.period; } public long delay() { return this.delay; }
/*     */     public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/PeriodicNotificationManager$Notification;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #126	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/PeriodicNotificationManager$Notification;
/*     */       //   0	8	1	$$0	Ljava/lang/Object; } public Notification(long $$0, long $$1, String $$2, String $$3) {
/* 128 */       this.delay = ($$0 != 0L) ? $$0 : $$1;
/* 129 */       this.period = $$1;
/* 130 */       this.title = $$2;
/* 131 */       this.message = $$3;
/*     */     } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/PeriodicNotificationManager$Notification;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #126	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/PeriodicNotificationManager$Notification;
/*     */     } public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/PeriodicNotificationManager$Notification;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #126	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/PeriodicNotificationManager$Notification;
/* 136 */     } } private static class NotificationTask extends TimerTask { private final Minecraft minecraft = Minecraft.getInstance();
/*     */     
/*     */     private final List<PeriodicNotificationManager.Notification> notifications;
/*     */     
/*     */     private final long period;
/*     */     private final AtomicLong elapsed;
/*     */     
/*     */     public NotificationTask(List<PeriodicNotificationManager.Notification> $$0, long $$1, long $$2) {
/* 144 */       this.notifications = $$0;
/* 145 */       this.period = $$2;
/* 146 */       this.elapsed = new AtomicLong($$1);
/*     */     }
/*     */     
/*     */     public NotificationTask reset(List<PeriodicNotificationManager.Notification> $$0, long $$1) {
/* 150 */       cancel();
/*     */       
/* 152 */       return new NotificationTask($$0, this.elapsed.get(), $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 157 */       long $$0 = this.elapsed.getAndAdd(this.period);
/* 158 */       long $$1 = this.elapsed.get();
/*     */       
/* 160 */       for (PeriodicNotificationManager.Notification $$2 : this.notifications) {
/* 161 */         if ($$0 < $$2.delay) {
/*     */           continue;
/*     */         }
/*     */         
/* 165 */         long $$3 = $$0 / $$2.period;
/* 166 */         long $$4 = $$1 / $$2.period;
/*     */         
/* 168 */         if ($$3 != $$4) {
/* 169 */           this.minecraft.execute(() -> SystemToast.add(Minecraft.getInstance().getToasts(), SystemToast.SystemToastId.PERIODIC_NOTIFICATION, (Component)Component.translatable($$0.title, new Object[] { Long.valueOf($$1) }), (Component)Component.translatable($$0.message, new Object[] { Long.valueOf($$1) })));
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\PeriodicNotificationManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */