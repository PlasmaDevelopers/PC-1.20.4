/*     */ package net.minecraft.network.syncher;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SynchedEntityData
/*     */ {
/*  28 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  29 */   private static final Object2IntMap<Class<? extends Entity>> ENTITY_ID_POOL = (Object2IntMap<Class<? extends Entity>>)new Object2IntOpenHashMap();
/*     */   
/*     */   private static final int MAX_ID_VALUE = 254;
/*     */   private final Entity entity;
/*  33 */   private final Int2ObjectMap<DataItem<?>> itemsById = (Int2ObjectMap<DataItem<?>>)new Int2ObjectOpenHashMap();
/*  34 */   private final ReadWriteLock lock = new ReentrantReadWriteLock();
/*     */   
/*     */   private boolean isDirty;
/*     */   
/*     */   public SynchedEntityData(Entity $$0) {
/*  39 */     this.entity = $$0;
/*     */   }
/*     */   public static <T> EntityDataAccessor<T> defineId(Class<? extends Entity> $$0, EntityDataSerializer<T> $$1) {
/*     */     int $$6;
/*  43 */     if (LOGGER.isDebugEnabled()) {
/*     */       try {
/*  45 */         Class<?> $$2 = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
/*  46 */         if (!$$2.equals($$0)) {
/*  47 */           LOGGER.debug("defineId called for: {} from {}", new Object[] { $$0, $$2, new RuntimeException() });
/*     */         }
/*  49 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  54 */     if (ENTITY_ID_POOL.containsKey($$0)) {
/*  55 */       int $$3 = ENTITY_ID_POOL.getInt($$0) + 1;
/*     */     } else {
/*  57 */       int $$4 = 0;
/*  58 */       Class<?> $$5 = $$0;
/*  59 */       while ($$5 != Entity.class) {
/*  60 */         $$5 = $$5.getSuperclass();
/*  61 */         if (ENTITY_ID_POOL.containsKey($$5)) {
/*  62 */           $$4 = ENTITY_ID_POOL.getInt($$5) + 1;
/*     */           break;
/*     */         } 
/*     */       } 
/*  66 */       $$6 = $$4;
/*     */     } 
/*  68 */     if ($$6 > 254) {
/*  69 */       throw new IllegalArgumentException("Data value id is too big with " + $$6 + "! (Max is 254)");
/*     */     }
/*  71 */     ENTITY_ID_POOL.put($$0, $$6);
/*  72 */     return $$1.createAccessor($$6);
/*     */   }
/*     */   
/*     */   public <T> void define(EntityDataAccessor<T> $$0, T $$1) {
/*  76 */     int $$2 = $$0.getId();
/*  77 */     if ($$2 > 254) {
/*  78 */       throw new IllegalArgumentException("Data value id is too big with " + $$2 + "! (Max is 254)");
/*     */     }
/*  80 */     if (this.itemsById.containsKey($$2)) {
/*  81 */       throw new IllegalArgumentException("Duplicate id value for " + $$2 + "!");
/*     */     }
/*  83 */     if (EntityDataSerializers.getSerializedId($$0.getSerializer()) < 0) {
/*  84 */       throw new IllegalArgumentException("Unregistered serializer " + $$0.getSerializer() + " for " + $$2 + "!");
/*     */     }
/*     */     
/*  87 */     createDataItem($$0, $$1);
/*     */   }
/*     */   
/*     */   private <T> void createDataItem(EntityDataAccessor<T> $$0, T $$1) {
/*  91 */     DataItem<T> $$2 = new DataItem<>($$0, $$1);
/*  92 */     this.lock.writeLock().lock();
/*  93 */     this.itemsById.put($$0.getId(), $$2);
/*  94 */     this.lock.writeLock().unlock();
/*     */   }
/*     */   
/*     */   public <T> boolean hasItem(EntityDataAccessor<T> $$0) {
/*  98 */     return this.itemsById.containsKey($$0.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> DataItem<T> getItem(EntityDataAccessor<T> $$0) {
/*     */     DataItem<T> $$1;
/* 104 */     this.lock.readLock().lock();
/*     */     
/*     */     try {
/* 107 */       $$1 = (DataItem<T>)this.itemsById.get($$0.getId());
/* 108 */     } catch (Throwable $$2) {
/* 109 */       CrashReport $$3 = CrashReport.forThrowable($$2, "Getting synched entity data");
/* 110 */       CrashReportCategory $$4 = $$3.addCategory("Synched entity data");
/*     */       
/* 112 */       $$4.setDetail("Data ID", $$0);
/* 113 */       throw new ReportedException($$3);
/*     */     } finally {
/* 115 */       this.lock.readLock().unlock();
/*     */     } 
/* 117 */     return $$1;
/*     */   }
/*     */   
/*     */   public <T> T get(EntityDataAccessor<T> $$0) {
/* 121 */     return getItem($$0).getValue();
/*     */   }
/*     */   
/*     */   public <T> void set(EntityDataAccessor<T> $$0, T $$1) {
/* 125 */     set($$0, $$1, false);
/*     */   }
/*     */   
/*     */   public <T> void set(EntityDataAccessor<T> $$0, T $$1, boolean $$2) {
/* 129 */     DataItem<T> $$3 = getItem($$0);
/*     */     
/* 131 */     if ($$2 || ObjectUtils.notEqual($$1, $$3.getValue())) {
/* 132 */       $$3.setValue($$1);
/* 133 */       this.entity.onSyncedDataUpdated($$0);
/* 134 */       $$3.setDirty(true);
/* 135 */       this.isDirty = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDirty() {
/* 140 */     return this.isDirty;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<DataValue<?>> packDirty() {
/* 145 */     List<DataValue<?>> $$0 = null;
/*     */     
/* 147 */     if (this.isDirty) {
/* 148 */       this.lock.readLock().lock();
/* 149 */       for (ObjectIterator<DataItem> objectIterator = this.itemsById.values().iterator(); objectIterator.hasNext(); ) { DataItem<?> $$1 = objectIterator.next();
/* 150 */         if ($$1.isDirty()) {
/* 151 */           $$1.setDirty(false);
/*     */           
/* 153 */           if ($$0 == null) {
/* 154 */             $$0 = new ArrayList<>();
/*     */           }
/* 156 */           $$0.add($$1.value());
/*     */         }  }
/*     */       
/* 159 */       this.lock.readLock().unlock();
/*     */     } 
/* 161 */     this.isDirty = false;
/*     */     
/* 163 */     return $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<DataValue<?>> getNonDefaultValues() {
/* 168 */     List<DataValue<?>> $$0 = null;
/*     */     
/* 170 */     this.lock.readLock().lock();
/* 171 */     for (ObjectIterator<DataItem> objectIterator = this.itemsById.values().iterator(); objectIterator.hasNext(); ) { DataItem<?> $$1 = objectIterator.next();
/* 172 */       if ($$1.isSetToDefault()) {
/*     */         continue;
/*     */       }
/* 175 */       if ($$0 == null) {
/* 176 */         $$0 = new ArrayList<>();
/*     */       }
/* 178 */       $$0.add($$1.value()); }
/*     */     
/* 180 */     this.lock.readLock().unlock();
/*     */     
/* 182 */     return $$0;
/*     */   }
/*     */   
/*     */   public void assignValues(List<DataValue<?>> $$0) {
/* 186 */     this.lock.writeLock().lock();
/*     */     try {
/* 188 */       for (DataValue<?> $$1 : $$0) {
/* 189 */         DataItem<?> $$2 = (DataItem)this.itemsById.get($$1.id);
/* 190 */         if ($$2 != null) {
/* 191 */           assignValue($$2, $$1);
/* 192 */           this.entity.onSyncedDataUpdated($$2.getAccessor());
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 196 */       this.lock.writeLock().unlock();
/*     */     } 
/* 198 */     this.entity.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> void assignValue(DataItem<T> $$0, DataValue<?> $$1) {
/* 203 */     if (!Objects.equals($$1.serializer(), $$0.accessor.getSerializer())) {
/* 204 */       throw new IllegalStateException(String.format(Locale.ROOT, "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)", new Object[] { Integer.valueOf($$0.accessor.getId()), this.entity, $$0.value, $$0.value.getClass(), $$1.value, $$1.value.getClass() }));
/*     */     }
/* 206 */     $$0.setValue($$1.value);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 210 */     return this.itemsById.isEmpty();
/*     */   }
/*     */   public static final class DataValue<T> extends Record { final int id; private final EntityDataSerializer<T> serializer; final T value;
/* 213 */     public DataValue(int $$0, EntityDataSerializer<T> $$1, T $$2) { this.id = $$0; this.serializer = $$1; this.value = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #213	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 213 */       //   0	7	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue<TT;>; } public int id() { return this.id; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #213	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #213	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 213 */       //   0	8	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue<TT;>; } public EntityDataSerializer<T> serializer() { return this.serializer; } public T value() { return this.value; }
/*     */      public static <T> DataValue<T> create(EntityDataAccessor<T> $$0, T $$1) {
/* 215 */       EntityDataSerializer<T> $$2 = $$0.getSerializer();
/* 216 */       return new DataValue<>($$0.getId(), $$2, $$2.copy($$1));
/*     */     }
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 220 */       int $$1 = EntityDataSerializers.getSerializedId(this.serializer);
/* 221 */       if ($$1 < 0) {
/* 222 */         throw new EncoderException("Unknown serializer type " + this.serializer);
/*     */       }
/* 224 */       $$0.writeByte(this.id);
/* 225 */       $$0.writeVarInt($$1);
/* 226 */       this.serializer.write($$0, this.value);
/*     */     }
/*     */     
/*     */     public static DataValue<?> read(FriendlyByteBuf $$0, int $$1) {
/* 230 */       int $$2 = $$0.readVarInt();
/* 231 */       EntityDataSerializer<?> $$3 = EntityDataSerializers.getSerializer($$2);
/* 232 */       if ($$3 == null) {
/* 233 */         throw new DecoderException("Unknown serializer type " + $$2);
/*     */       }
/*     */       
/* 236 */       return read($$0, $$1, $$3);
/*     */     }
/*     */     
/*     */     private static <T> DataValue<T> read(FriendlyByteBuf $$0, int $$1, EntityDataSerializer<T> $$2) {
/* 240 */       return new DataValue<>($$1, $$2, $$2.read($$0));
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class DataItem<T> {
/*     */     final EntityDataAccessor<T> accessor;
/*     */     T value;
/*     */     private final T initialValue;
/*     */     private boolean dirty;
/*     */     
/*     */     public DataItem(EntityDataAccessor<T> $$0, T $$1) {
/* 251 */       this.accessor = $$0;
/* 252 */       this.initialValue = $$1;
/* 253 */       this.value = $$1;
/*     */     }
/*     */     
/*     */     public EntityDataAccessor<T> getAccessor() {
/* 257 */       return this.accessor;
/*     */     }
/*     */     
/*     */     public void setValue(T $$0) {
/* 261 */       this.value = $$0;
/*     */     }
/*     */     
/*     */     public T getValue() {
/* 265 */       return this.value;
/*     */     }
/*     */     
/*     */     public boolean isDirty() {
/* 269 */       return this.dirty;
/*     */     }
/*     */     
/*     */     public void setDirty(boolean $$0) {
/* 273 */       this.dirty = $$0;
/*     */     }
/*     */     
/*     */     public boolean isSetToDefault() {
/* 277 */       return this.initialValue.equals(this.value);
/*     */     }
/*     */     
/*     */     public SynchedEntityData.DataValue<T> value() {
/* 281 */       return SynchedEntityData.DataValue.create(this.accessor, this.value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\SynchedEntityData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */