/*     */ package net.minecraft.world.level.timers;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.Table;
/*     */ import com.google.common.primitives.UnsignedLong;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TimerQueue<T> {
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final String CALLBACK_DATA_TAG = "Callback";
/*     */   private static final String TIMER_NAME_TAG = "Name";
/*     */   private static final String TIMER_TRIGGER_TIME_TAG = "TriggerTime";
/*     */   private final TimerCallbacks<T> callbacksRegistry;
/*     */   
/*     */   public static class Event<T> { public final long triggerTime;
/*     */     public final UnsignedLong sequentialId;
/*     */     public final String id;
/*     */     public final TimerCallback<T> callback;
/*     */     
/*     */     Event(long $$0, UnsignedLong $$1, String $$2, TimerCallback<T> $$3) {
/*  35 */       this.triggerTime = $$0;
/*  36 */       this.sequentialId = $$1;
/*  37 */       this.id = $$2;
/*  38 */       this.callback = $$3;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static <T> Comparator<Event<T>> createComparator() {
/*  43 */     return Comparator.comparingLong($$0 -> $$0.triggerTime).thenComparing($$0 -> $$0.sequentialId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  48 */   private final Queue<Event<T>> queue = new PriorityQueue<>((Comparator)createComparator());
/*     */   
/*  50 */   private UnsignedLong sequentialId = UnsignedLong.ZERO;
/*     */   
/*  52 */   private final Table<String, Long, Event<T>> events = (Table<String, Long, Event<T>>)HashBasedTable.create();
/*     */   
/*     */   public TimerQueue(TimerCallbacks<T> $$0, Stream<? extends Dynamic<?>> $$1) {
/*  55 */     this($$0);
/*  56 */     this.queue.clear();
/*  57 */     this.events.clear();
/*  58 */     this.sequentialId = UnsignedLong.ZERO;
/*     */     
/*  60 */     $$1.forEach($$0 -> {
/*     */           Tag $$1 = (Tag)$$0.convert((DynamicOps)NbtOps.INSTANCE).getValue();
/*     */           if ($$1 instanceof CompoundTag) {
/*     */             CompoundTag $$2 = (CompoundTag)$$1;
/*     */             loadEvent($$2);
/*     */           } else {
/*     */             LOGGER.warn("Invalid format of events: {}", $$1);
/*     */           } 
/*     */         });
/*     */   }
/*     */   public TimerQueue(TimerCallbacks<T> $$0) {
/*  71 */     this.callbacksRegistry = $$0;
/*     */   }
/*     */   
/*     */   public void tick(T $$0, long $$1) {
/*     */     while (true) {
/*  76 */       Event<T> $$2 = this.queue.peek();
/*  77 */       if ($$2 == null || $$2.triggerTime > $$1) {
/*     */         break;
/*     */       }
/*     */       
/*  81 */       this.queue.remove();
/*  82 */       this.events.remove($$2.id, Long.valueOf($$1));
/*     */       
/*  84 */       $$2.callback.handle($$0, this, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void schedule(String $$0, long $$1, TimerCallback<T> $$2) {
/*  89 */     if (this.events.contains($$0, Long.valueOf($$1))) {
/*     */       return;
/*     */     }
/*  92 */     this.sequentialId = this.sequentialId.plus(UnsignedLong.ONE);
/*  93 */     Event<T> $$3 = new Event<>($$1, this.sequentialId, $$0, $$2);
/*  94 */     this.events.put($$0, Long.valueOf($$1), $$3);
/*  95 */     this.queue.add($$3);
/*     */   }
/*     */   
/*     */   public int remove(String $$0) {
/*  99 */     Collection<Event<T>> $$1 = this.events.row($$0).values();
/* 100 */     Objects.requireNonNull(this.queue); $$1.forEach(this.queue::remove);
/* 101 */     int $$2 = $$1.size();
/* 102 */     $$1.clear();
/* 103 */     return $$2;
/*     */   }
/*     */   
/*     */   public Set<String> getEventsIds() {
/* 107 */     return Collections.unmodifiableSet(this.events.rowKeySet());
/*     */   }
/*     */   
/*     */   private void loadEvent(CompoundTag $$0) {
/* 111 */     CompoundTag $$1 = $$0.getCompound("Callback");
/* 112 */     TimerCallback<T> $$2 = this.callbacksRegistry.deserialize($$1);
/* 113 */     if ($$2 != null) {
/* 114 */       String $$3 = $$0.getString("Name");
/* 115 */       long $$4 = $$0.getLong("TriggerTime");
/* 116 */       schedule($$3, $$4, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private CompoundTag storeEvent(Event<T> $$0) {
/* 121 */     CompoundTag $$1 = new CompoundTag();
/* 122 */     $$1.putString("Name", $$0.id);
/* 123 */     $$1.putLong("TriggerTime", $$0.triggerTime);
/* 124 */     $$1.put("Callback", (Tag)this.callbacksRegistry.serialize($$0.callback));
/* 125 */     return $$1;
/*     */   }
/*     */   
/*     */   public ListTag store() {
/* 129 */     ListTag $$0 = new ListTag();
/* 130 */     Objects.requireNonNull($$0); this.queue.stream().sorted(createComparator()).map(this::storeEvent).forEach($$0::add);
/* 131 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\timers\TimerQueue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */