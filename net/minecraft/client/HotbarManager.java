/*    */ package net.minecraft.client;
/*    */ 
/*    */ import com.mojang.datafixers.DataFixer;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.nio.file.Path;
/*    */ import net.minecraft.client.player.inventory.Hotbar;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtIo;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.util.datafix.DataFixTypes;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class HotbarManager
/*    */ {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public static final int NUM_HOTBAR_GROUPS = 9;
/*    */   private final Path optionsFile;
/*    */   private final DataFixer fixerUpper;
/* 21 */   private final Hotbar[] hotbars = new Hotbar[9];
/*    */   private boolean loaded;
/*    */   
/*    */   public HotbarManager(Path $$0, DataFixer $$1) {
/* 25 */     this.optionsFile = $$0.resolve("hotbar.nbt");
/* 26 */     this.fixerUpper = $$1;
/*    */     
/* 28 */     for (int $$2 = 0; $$2 < 9; $$2++) {
/* 29 */       this.hotbars[$$2] = new Hotbar();
/*    */     }
/*    */   }
/*    */   
/*    */   private void load() {
/*    */     try {
/* 35 */       CompoundTag $$0 = NbtIo.read(this.optionsFile);
/* 36 */       if ($$0 == null) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 41 */       int $$1 = NbtUtils.getDataVersion($$0, 1343);
/* 42 */       $$0 = DataFixTypes.HOTBAR.updateToCurrentVersion(this.fixerUpper, $$0, $$1);
/*    */       
/* 44 */       for (int $$2 = 0; $$2 < 9; $$2++) {
/* 45 */         this.hotbars[$$2].fromTag($$0.getList(String.valueOf($$2), 10));
/*    */       }
/* 47 */     } catch (Exception $$3) {
/* 48 */       LOGGER.error("Failed to load creative mode options", $$3);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void save() {
/*    */     try {
/* 54 */       CompoundTag $$0 = NbtUtils.addCurrentDataVersion(new CompoundTag());
/* 55 */       for (int $$1 = 0; $$1 < 9; $$1++) {
/* 56 */         $$0.put(String.valueOf($$1), (Tag)get($$1).createTag());
/*    */       }
/* 58 */       NbtIo.write($$0, this.optionsFile);
/* 59 */     } catch (Exception $$2) {
/* 60 */       LOGGER.error("Failed to save creative mode options", $$2);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Hotbar get(int $$0) {
/* 65 */     if (!this.loaded) {
/* 66 */       load();
/* 67 */       this.loaded = true;
/*    */     } 
/* 69 */     return this.hotbars[$$0];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\HotbarManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */