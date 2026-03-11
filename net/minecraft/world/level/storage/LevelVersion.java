/*    */ package net.minecraft.world.level.storage;
/*    */ 
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.OptionalDynamic;
/*    */ import net.minecraft.SharedConstants;
/*    */ 
/*    */ public class LevelVersion {
/*    */   private final int levelDataVersion;
/*    */   private final long lastPlayed;
/*    */   private final String minecraftVersionName;
/*    */   private final DataVersion minecraftVersion;
/*    */   private final boolean snapshot;
/*    */   
/*    */   private LevelVersion(int $$0, long $$1, String $$2, int $$3, String $$4, boolean $$5) {
/* 15 */     this.levelDataVersion = $$0;
/* 16 */     this.lastPlayed = $$1;
/* 17 */     this.minecraftVersionName = $$2;
/* 18 */     this.minecraftVersion = new DataVersion($$3, $$4);
/* 19 */     this.snapshot = $$5;
/*    */   }
/*    */   
/*    */   public static LevelVersion parse(Dynamic<?> $$0) {
/* 23 */     int $$1 = $$0.get("version").asInt(0);
/* 24 */     long $$2 = $$0.get("LastPlayed").asLong(0L);
/* 25 */     OptionalDynamic<?> $$3 = $$0.get("Version");
/*    */     
/* 27 */     if ($$3.result().isPresent()) {
/* 28 */       return new LevelVersion($$1, $$2, $$3
/*    */ 
/*    */           
/* 31 */           .get("Name").asString(SharedConstants.getCurrentVersion().getName()), $$3
/* 32 */           .get("Id").asInt(SharedConstants.getCurrentVersion().getDataVersion().getVersion()), $$3
/* 33 */           .get("Series").asString(DataVersion.MAIN_SERIES), $$3
/* 34 */           .get("Snapshot").asBoolean(!SharedConstants.getCurrentVersion().isStable()));
/*    */     }
/*    */     
/* 37 */     return new LevelVersion($$1, $$2, "", 0, DataVersion.MAIN_SERIES, false);
/*    */   }
/*    */   
/*    */   public int levelDataVersion() {
/* 41 */     return this.levelDataVersion;
/*    */   }
/*    */   
/*    */   public long lastPlayed() {
/* 45 */     return this.lastPlayed;
/*    */   }
/*    */   
/*    */   public String minecraftVersionName() {
/* 49 */     return this.minecraftVersionName;
/*    */   }
/*    */   
/*    */   public DataVersion minecraftVersion() {
/* 53 */     return this.minecraftVersion;
/*    */   }
/*    */   
/*    */   public boolean snapshot() {
/* 57 */     return this.snapshot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\LevelVersion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */