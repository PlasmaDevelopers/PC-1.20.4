/*    */ package net.minecraft.world.level.storage;
/*    */ 
/*    */ import com.mojang.datafixers.DataFixer;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.File;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtAccounter;
/*    */ import net.minecraft.nbt.NbtIo;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.util.datafix.DataFixTypes;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class PlayerDataStorage {
/* 20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private final File playerDir;
/*    */   protected final DataFixer fixerUpper;
/*    */   
/*    */   public PlayerDataStorage(LevelStorageSource.LevelStorageAccess $$0, DataFixer $$1) {
/* 25 */     this.fixerUpper = $$1;
/* 26 */     this.playerDir = $$0.getLevelPath(LevelResource.PLAYER_DATA_DIR).toFile();
/* 27 */     this.playerDir.mkdirs();
/*    */   }
/*    */   
/*    */   public void save(Player $$0) {
/*    */     try {
/* 32 */       CompoundTag $$1 = $$0.saveWithoutId(new CompoundTag());
/* 33 */       Path $$2 = this.playerDir.toPath();
/* 34 */       Path $$3 = Files.createTempFile($$2, $$0.getStringUUID() + "-", ".dat", (FileAttribute<?>[])new FileAttribute[0]);
/* 35 */       NbtIo.writeCompressed($$1, $$3);
/*    */       
/* 37 */       Path $$4 = $$2.resolve($$0.getStringUUID() + ".dat");
/* 38 */       Path $$5 = $$2.resolve($$0.getStringUUID() + ".dat_old");
/* 39 */       Util.safeReplaceFile($$4, $$3, $$5);
/* 40 */     } catch (Exception $$6) {
/* 41 */       LOGGER.warn("Failed to save player data for {}", $$0.getName().getString());
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public CompoundTag load(Player $$0) {
/* 47 */     CompoundTag $$1 = null;
/*    */     try {
/* 49 */       File $$2 = new File(this.playerDir, $$0.getStringUUID() + ".dat");
/* 50 */       if ($$2.exists() && $$2.isFile()) {
/* 51 */         $$1 = NbtIo.readCompressed($$2.toPath(), NbtAccounter.unlimitedHeap());
/*    */       }
/* 53 */     } catch (Exception $$3) {
/* 54 */       LOGGER.warn("Failed to load player data for {}", $$0.getName().getString());
/*    */     } 
/* 56 */     if ($$1 != null) {
/* 57 */       int $$4 = NbtUtils.getDataVersion($$1, -1);
/* 58 */       $$1 = DataFixTypes.PLAYER.updateToCurrentVersion(this.fixerUpper, $$1, $$4);
/* 59 */       $$0.load($$1);
/*    */     } 
/* 61 */     return $$1;
/*    */   }
/*    */   
/*    */   public String[] getSeenPlayers() {
/* 65 */     String[] $$0 = this.playerDir.list();
/* 66 */     if ($$0 == null) {
/* 67 */       $$0 = new String[0];
/*    */     }
/*    */     
/* 70 */     for (int $$1 = 0; $$1 < $$0.length; $$1++) {
/* 71 */       if ($$0[$$1].endsWith(".dat")) {
/* 72 */         $$0[$$1] = $$0[$$1].substring(0, $$0[$$1].length() - 4);
/*    */       }
/*    */     } 
/*    */     
/* 76 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\PlayerDataStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */