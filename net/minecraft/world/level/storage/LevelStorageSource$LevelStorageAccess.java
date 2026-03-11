/*     */ package net.minecraft.world.level.storage;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.FileVisitResult;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.SimpleFileVisitor;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtIo;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.DirectoryLock;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LevelStorageAccess
/*     */   implements AutoCloseable
/*     */ {
/*     */   final DirectoryLock lock;
/*     */   final LevelStorageSource.LevelDirectory levelDirectory;
/*     */   private final String levelId;
/* 389 */   private final Map<LevelResource, Path> resources = Maps.newHashMap();
/*     */   
/*     */   LevelStorageAccess(String $$1, Path $$2) throws IOException {
/* 392 */     this.levelId = $$1;
/* 393 */     this.levelDirectory = new LevelStorageSource.LevelDirectory($$2);
/* 394 */     this.lock = DirectoryLock.create($$2);
/*     */   }
/*     */   
/*     */   public void safeClose() {
/*     */     try {
/* 399 */       close();
/* 400 */     } catch (IOException $$0) {
/* 401 */       LevelStorageSource.LOGGER.warn("Failed to unlock access to level {}", getLevelId(), $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public LevelStorageSource parent() {
/* 406 */     return LevelStorageSource.this;
/*     */   }
/*     */   
/*     */   public LevelStorageSource.LevelDirectory getLevelDirectory() {
/* 410 */     return this.levelDirectory;
/*     */   }
/*     */   
/*     */   public String getLevelId() {
/* 414 */     return this.levelId;
/*     */   }
/*     */   
/*     */   public Path getLevelPath(LevelResource $$0) {
/* 418 */     Objects.requireNonNull(this.levelDirectory); return this.resources.computeIfAbsent($$0, this.levelDirectory::resourcePath);
/*     */   }
/*     */   
/*     */   public Path getDimensionPath(ResourceKey<Level> $$0) {
/* 422 */     return DimensionType.getStorageFolder($$0, this.levelDirectory.path());
/*     */   }
/*     */   
/*     */   private void checkLock() {
/* 426 */     if (!this.lock.isValid()) {
/* 427 */       throw new IllegalStateException("Lock is no longer valid");
/*     */     }
/*     */   }
/*     */   
/*     */   public PlayerDataStorage createPlayerStorage() {
/* 432 */     checkLock();
/* 433 */     return new PlayerDataStorage(this, LevelStorageSource.this.fixerUpper);
/*     */   }
/*     */   
/*     */   public LevelSummary getSummary(Dynamic<?> $$0) {
/* 437 */     checkLock();
/* 438 */     return LevelStorageSource.this.makeLevelSummary($$0, this.levelDirectory, false);
/*     */   }
/*     */   
/*     */   public Dynamic<?> getDataTag() throws IOException {
/* 442 */     return getDataTag(false);
/*     */   }
/*     */   
/*     */   public Dynamic<?> getDataTagFallback() throws IOException {
/* 446 */     return getDataTag(true);
/*     */   }
/*     */   
/*     */   private Dynamic<?> getDataTag(boolean $$0) throws IOException {
/* 450 */     checkLock();
/* 451 */     return LevelStorageSource.readLevelDataTagFixed($$0 ? this.levelDirectory.oldDataFile() : this.levelDirectory.dataFile(), LevelStorageSource.this.fixerUpper);
/*     */   }
/*     */   
/*     */   public void saveDataTag(RegistryAccess $$0, WorldData $$1) {
/* 455 */     saveDataTag($$0, $$1, null);
/*     */   }
/*     */   
/*     */   public void saveDataTag(RegistryAccess $$0, WorldData $$1, @Nullable CompoundTag $$2) {
/* 459 */     CompoundTag $$3 = $$1.createTag($$0, $$2);
/*     */     
/* 461 */     CompoundTag $$4 = new CompoundTag();
/* 462 */     $$4.put("Data", (Tag)$$3);
/*     */     
/* 464 */     saveLevelData($$4);
/*     */   }
/*     */   
/*     */   private void saveLevelData(CompoundTag $$0) {
/* 468 */     Path $$1 = this.levelDirectory.path();
/*     */     try {
/* 470 */       Path $$2 = Files.createTempFile($$1, "level", ".dat", (FileAttribute<?>[])new FileAttribute[0]);
/* 471 */       NbtIo.writeCompressed($$0, $$2);
/*     */       
/* 473 */       Path $$3 = this.levelDirectory.oldDataFile();
/* 474 */       Path $$4 = this.levelDirectory.dataFile();
/* 475 */       Util.safeReplaceFile($$4, $$2, $$3);
/* 476 */     } catch (Exception $$5) {
/* 477 */       LevelStorageSource.LOGGER.error("Failed to save level {}", $$1, $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Optional<Path> getIconFile() {
/* 482 */     if (!this.lock.isValid()) {
/* 483 */       return Optional.empty();
/*     */     }
/* 485 */     return Optional.of(this.levelDirectory.iconFile());
/*     */   }
/*     */   
/*     */   public void deleteLevel() throws IOException {
/* 489 */     checkLock();
/*     */     
/* 491 */     final Path lockPath = this.levelDirectory.lockFile();
/*     */     
/* 493 */     LevelStorageSource.LOGGER.info("Deleting level {}", this.levelId);
/* 494 */     for (int $$1 = 1; $$1 <= 5; $$1++) {
/* 495 */       LevelStorageSource.LOGGER.info("Attempt {}...", Integer.valueOf($$1));
/*     */       
/*     */       try {
/* 498 */         Files.walkFileTree(this.levelDirectory.path(), new SimpleFileVisitor<Path>()
/*     */             {
/*     */               public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 501 */                 if (!$$0.equals(lockPath)) {
/* 502 */                   LevelStorageSource.LOGGER.debug("Deleting {}", $$0);
/* 503 */                   Files.delete($$0);
/*     */                 } 
/* 505 */                 return FileVisitResult.CONTINUE;
/*     */               }
/*     */ 
/*     */               
/*     */               public FileVisitResult postVisitDirectory(Path $$0, @Nullable IOException $$1) throws IOException {
/* 510 */                 if ($$1 != null) {
/* 511 */                   throw $$1;
/*     */                 }
/*     */                 
/* 514 */                 if ($$0.equals(LevelStorageSource.LevelStorageAccess.this.levelDirectory.path())) {
/*     */                   
/* 516 */                   LevelStorageSource.LevelStorageAccess.this.lock.close();
/* 517 */                   Files.deleteIfExists(lockPath);
/*     */                 } 
/* 519 */                 Files.delete($$0);
/* 520 */                 return FileVisitResult.CONTINUE;
/*     */               }
/*     */             });
/*     */         break;
/* 524 */       } catch (IOException $$2) {
/* 525 */         if ($$1 < 5) {
/* 526 */           LevelStorageSource.LOGGER.warn("Failed to delete {}", this.levelDirectory.path(), $$2);
/*     */           try {
/* 528 */             Thread.sleep(500L);
/* 529 */           } catch (InterruptedException interruptedException) {}
/*     */         } else {
/*     */           
/* 532 */           throw $$2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renameLevel(String $$0) throws IOException {
/* 539 */     modifyLevelDataWithoutDatafix($$1 -> $$1.putString("LevelName", $$0.trim()));
/*     */   }
/*     */   
/*     */   public void renameAndDropPlayer(String $$0) throws IOException {
/* 543 */     modifyLevelDataWithoutDatafix($$1 -> {
/*     */           $$1.putString("LevelName", $$0.trim());
/*     */           $$1.remove("Player");
/*     */         });
/*     */   }
/*     */   
/*     */   private void modifyLevelDataWithoutDatafix(Consumer<CompoundTag> $$0) throws IOException {
/* 550 */     checkLock();
/*     */     
/* 552 */     CompoundTag $$1 = LevelStorageSource.readLevelDataTagRaw(this.levelDirectory.dataFile());
/* 553 */     $$0.accept($$1.getCompound("Data"));
/* 554 */     saveLevelData($$1);
/*     */   }
/*     */   
/*     */   public long makeWorldBackup() throws IOException {
/* 558 */     checkLock();
/* 559 */     String $$0 = LocalDateTime.now().format(LevelStorageSource.FORMATTER) + "_" + LocalDateTime.now().format(LevelStorageSource.FORMATTER);
/*     */     
/* 561 */     Path $$1 = LevelStorageSource.this.getBackupPath();
/*     */     try {
/* 563 */       FileUtil.createDirectoriesSafe($$1);
/* 564 */     } catch (IOException $$2) {
/* 565 */       throw new RuntimeException($$2);
/*     */     } 
/* 567 */     Path $$3 = $$1.resolve(FileUtil.findAvailableName($$1, $$0, ".zip"));
/*     */     
/* 569 */     final ZipOutputStream stream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream($$3, new java.nio.file.OpenOption[0]))); 
/* 570 */     try { final Path rootPath = Paths.get(this.levelId, new String[0]);
/*     */       
/* 572 */       Files.walkFileTree(this.levelDirectory.path(), new SimpleFileVisitor<Path>()
/*     */           {
/*     */             public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 575 */               if ($$0.endsWith("session.lock")) {
/* 576 */                 return FileVisitResult.CONTINUE;
/*     */               }
/* 578 */               String $$2 = rootPath.resolve(LevelStorageSource.LevelStorageAccess.this.levelDirectory.path().relativize($$0)).toString().replace('\\', '/');
/* 579 */               ZipEntry $$3 = new ZipEntry($$2);
/* 580 */               stream.putNextEntry($$3);
/* 581 */               Files.asByteSource($$0.toFile()).copyTo(stream);
/* 582 */               stream.closeEntry();
/* 583 */               return FileVisitResult.CONTINUE;
/*     */             }
/*     */           });
/* 586 */       $$4.close(); } catch (Throwable throwable) { try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 588 */      return Files.size($$3);
/*     */   }
/*     */   
/*     */   public boolean hasWorldData() {
/* 592 */     return (Files.exists(this.levelDirectory.dataFile(), new java.nio.file.LinkOption[0]) || Files.exists(this.levelDirectory.oldDataFile(), new java.nio.file.LinkOption[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 597 */     this.lock.close();
/*     */   }
/*     */   
/*     */   public boolean restoreLevelDataFromOld() {
/* 601 */     return Util.safeReplaceOrMoveFile(this.levelDirectory.dataFile(), this.levelDirectory.oldDataFile(), this.levelDirectory.corruptedDataFile(LocalDateTime.now()), true);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instant getFileModificationTime(boolean $$0) {
/* 606 */     return LevelStorageSource.getFileModificationTime($$0 ? this.levelDirectory.oldDataFile() : this.levelDirectory.dataFile());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\LevelStorageSource$LevelStorageAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */