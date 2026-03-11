/*     */ package net.minecraft.world.level.storage;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtAccounter;
/*     */ import net.minecraft.nbt.NbtIo;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.saveddata.SavedData;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class DimensionDataStorage
/*     */ {
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  28 */   private final Map<String, SavedData> cache = Maps.newHashMap();
/*     */   private final DataFixer fixerUpper;
/*     */   private final File dataFolder;
/*     */   
/*     */   public DimensionDataStorage(File $$0, DataFixer $$1) {
/*  33 */     this.fixerUpper = $$1;
/*  34 */     this.dataFolder = $$0;
/*     */   }
/*     */   
/*     */   private File getDataFile(String $$0) {
/*  38 */     return new File(this.dataFolder, $$0 + ".dat");
/*     */   }
/*     */   
/*     */   public <T extends SavedData> T computeIfAbsent(SavedData.Factory<T> $$0, String $$1) {
/*  42 */     T $$2 = get($$0, $$1);
/*  43 */     if ($$2 != null) {
/*  44 */       return $$2;
/*     */     }
/*     */     
/*  47 */     SavedData savedData = $$0.constructor().get();
/*  48 */     set($$1, savedData);
/*  49 */     return (T)savedData;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends SavedData> T get(SavedData.Factory<T> $$0, String $$1) {
/*  55 */     SavedData $$2 = this.cache.get($$1);
/*  56 */     if ($$2 == null && 
/*  57 */       !this.cache.containsKey($$1)) {
/*  58 */       $$2 = readSavedData($$0.deserializer(), $$0.type(), $$1);
/*  59 */       this.cache.put($$1, $$2);
/*     */     } 
/*     */     
/*  62 */     return (T)$$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private <T extends SavedData> T readSavedData(Function<CompoundTag, T> $$0, DataFixTypes $$1, String $$2) {
/*     */     try {
/*  68 */       File $$3 = getDataFile($$2);
/*  69 */       if ($$3.exists()) {
/*  70 */         CompoundTag $$4 = readTagFromDisk($$2, $$1, SharedConstants.getCurrentVersion().getDataVersion().getVersion());
/*  71 */         return $$0.apply($$4.getCompound("data"));
/*     */       } 
/*  73 */     } catch (Exception $$5) {
/*  74 */       LOGGER.error("Error loading saved data: {}", $$2, $$5);
/*     */     } 
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   public void set(String $$0, SavedData $$1) {
/*  80 */     this.cache.put($$0, $$1);
/*     */   }
/*     */   
/*     */   public CompoundTag readTagFromDisk(String $$0, DataFixTypes $$1, int $$2) throws IOException {
/*  84 */     File $$3 = getDataFile($$0);
/*     */     
/*  86 */     FileInputStream $$4 = new FileInputStream($$3); 
/*  87 */     try { PushbackInputStream $$5 = new PushbackInputStream($$4, 2);
/*     */       
/*     */       try { CompoundTag $$8;
/*  90 */         if (isGzip($$5)) {
/*  91 */           CompoundTag $$6 = NbtIo.readCompressed($$5, NbtAccounter.unlimitedHeap());
/*     */         } else {
/*  93 */           DataInputStream $$7 = new DataInputStream($$5); 
/*  94 */           try { $$8 = NbtIo.read($$7);
/*  95 */             $$7.close(); } catch (Throwable throwable) { try { $$7.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */              throw throwable; }
/*     */         
/*  98 */         }  int $$10 = NbtUtils.getDataVersion($$8, 1343);
/*  99 */         CompoundTag compoundTag1 = $$1.update(this.fixerUpper, $$8, $$10, $$2);
/* 100 */         $$5.close(); $$4.close(); return compoundTag1; } catch (Throwable $$9) { try { $$5.close(); } catch (Throwable throwable) { $$9.addSuppressed(throwable); }  throw $$9; }  }
/*     */     catch (Throwable throwable) { try { $$4.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 104 */      } private boolean isGzip(PushbackInputStream $$0) throws IOException { byte[] $$1 = new byte[2];
/* 105 */     boolean $$2 = false;
/* 106 */     int $$3 = $$0.read($$1, 0, 2);
/* 107 */     if ($$3 == 2) {
/* 108 */       int $$4 = ($$1[1] & 0xFF) << 8 | $$1[0] & 0xFF;
/* 109 */       if ($$4 == 35615) {
/* 110 */         $$2 = true;
/*     */       }
/*     */     } 
/* 113 */     if ($$3 != 0) {
/* 114 */       $$0.unread($$1, 0, $$3);
/*     */     }
/* 116 */     return $$2; }
/*     */ 
/*     */   
/*     */   public void save() {
/* 120 */     this.cache.forEach(($$0, $$1) -> {
/*     */           if ($$1 != null)
/*     */             $$1.save(getDataFile($$0)); 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\DimensionDataStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */