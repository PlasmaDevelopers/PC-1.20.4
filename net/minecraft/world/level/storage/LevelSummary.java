/*     */ package net.minecraft.world.level.storage;
/*     */ 
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.WorldVersion;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.StringUtil;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class LevelSummary
/*     */   implements Comparable<LevelSummary> {
/*  19 */   public static final Component PLAY_WORLD = (Component)Component.translatable("selectWorld.select");
/*     */   
/*     */   private final LevelSettings settings;
/*     */   private final LevelVersion levelVersion;
/*     */   private final String levelId;
/*     */   private final boolean requiresManualConversion;
/*     */   private final boolean locked;
/*     */   private final boolean experimental;
/*     */   private final Path icon;
/*     */   @Nullable
/*     */   private Component info;
/*     */   
/*     */   public LevelSummary(LevelSettings $$0, LevelVersion $$1, String $$2, boolean $$3, boolean $$4, boolean $$5, Path $$6) {
/*  32 */     this.settings = $$0;
/*  33 */     this.levelVersion = $$1;
/*  34 */     this.levelId = $$2;
/*  35 */     this.locked = $$4;
/*  36 */     this.experimental = $$5;
/*  37 */     this.icon = $$6;
/*  38 */     this.requiresManualConversion = $$3;
/*     */   }
/*     */   
/*     */   public String getLevelId() {
/*  42 */     return this.levelId;
/*     */   }
/*     */   
/*     */   public String getLevelName() {
/*  46 */     return StringUtils.isEmpty(this.settings.levelName()) ? this.levelId : this.settings.levelName();
/*     */   }
/*     */   
/*     */   public Path getIcon() {
/*  50 */     return this.icon;
/*     */   }
/*     */   
/*     */   public boolean requiresManualConversion() {
/*  54 */     return this.requiresManualConversion;
/*     */   }
/*     */   
/*     */   public boolean isExperimental() {
/*  58 */     return this.experimental;
/*     */   }
/*     */   
/*     */   public long getLastPlayed() {
/*  62 */     return this.levelVersion.lastPlayed();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(LevelSummary $$0) {
/*  67 */     if (getLastPlayed() < $$0.getLastPlayed()) {
/*  68 */       return 1;
/*     */     }
/*  70 */     if (getLastPlayed() > $$0.getLastPlayed()) {
/*  71 */       return -1;
/*     */     }
/*  73 */     return this.levelId.compareTo($$0.levelId);
/*     */   }
/*     */   
/*     */   public LevelSettings getSettings() {
/*  77 */     return this.settings;
/*     */   }
/*     */   
/*     */   public GameType getGameMode() {
/*  81 */     return this.settings.gameType();
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/*  85 */     return this.settings.hardcore();
/*     */   }
/*     */   
/*     */   public boolean hasCheats() {
/*  89 */     return this.settings.allowCommands();
/*     */   }
/*     */   
/*     */   public MutableComponent getWorldVersionName() {
/*  93 */     if (StringUtil.isNullOrEmpty(this.levelVersion.minecraftVersionName())) {
/*  94 */       return Component.translatable("selectWorld.versionUnknown");
/*     */     }
/*  96 */     return Component.literal(this.levelVersion.minecraftVersionName());
/*     */   }
/*     */   
/*     */   public LevelVersion levelVersion() {
/* 100 */     return this.levelVersion;
/*     */   }
/*     */   
/*     */   public boolean shouldBackup() {
/* 104 */     return backupStatus().shouldBackup();
/*     */   }
/*     */   
/*     */   public boolean isDowngrade() {
/* 108 */     return (backupStatus() == BackupStatus.DOWNGRADE);
/*     */   }
/*     */   
/*     */   public BackupStatus backupStatus() {
/* 112 */     WorldVersion $$0 = SharedConstants.getCurrentVersion();
/* 113 */     int $$1 = $$0.getDataVersion().getVersion();
/* 114 */     int $$2 = this.levelVersion.minecraftVersion().getVersion();
/* 115 */     if (!$$0.isStable() && $$2 < $$1)
/* 116 */       return BackupStatus.UPGRADE_TO_SNAPSHOT; 
/* 117 */     if ($$2 > $$1) {
/* 118 */       return BackupStatus.DOWNGRADE;
/*     */     }
/* 120 */     return BackupStatus.NONE;
/*     */   }
/*     */   
/*     */   public boolean isLocked() {
/* 124 */     return this.locked;
/*     */   }
/*     */   
/*     */   public boolean isDisabled() {
/* 128 */     if (isLocked() || requiresManualConversion()) {
/* 129 */       return true;
/*     */     }
/*     */     
/* 132 */     return !isCompatible();
/*     */   }
/*     */   
/*     */   public boolean isCompatible() {
/* 136 */     return SharedConstants.getCurrentVersion().getDataVersion().isCompatible(this.levelVersion.minecraftVersion());
/*     */   }
/*     */   
/*     */   public Component getInfo() {
/* 140 */     if (this.info == null) {
/* 141 */       this.info = createInfo();
/*     */     }
/*     */     
/* 144 */     return this.info;
/*     */   }
/*     */   
/*     */   private Component createInfo() {
/* 148 */     if (isLocked()) {
/* 149 */       return (Component)Component.translatable("selectWorld.locked").withStyle(ChatFormatting.RED);
/*     */     }
/* 151 */     if (requiresManualConversion()) {
/* 152 */       return (Component)Component.translatable("selectWorld.conversion").withStyle(ChatFormatting.RED);
/*     */     }
/* 154 */     if (!isCompatible()) {
/* 155 */       return (Component)Component.translatable("selectWorld.incompatible.info", new Object[] { getWorldVersionName() }).withStyle(ChatFormatting.RED);
/*     */     }
/*     */ 
/*     */     
/* 159 */     MutableComponent $$0 = isHardcore() ? Component.empty().append((Component)Component.translatable("gameMode.hardcore").withColor(-65536)) : Component.translatable("gameMode." + getGameMode().getName());
/*     */     
/* 161 */     if (hasCheats()) {
/* 162 */       $$0.append(", ").append((Component)Component.translatable("selectWorld.cheats"));
/*     */     }
/*     */     
/* 165 */     if (isExperimental()) {
/* 166 */       $$0.append(", ").append((Component)Component.translatable("selectWorld.experimental").withStyle(ChatFormatting.YELLOW));
/*     */     }
/*     */     
/* 169 */     MutableComponent $$1 = getWorldVersionName();
/* 170 */     MutableComponent $$2 = Component.literal(", ").append((Component)Component.translatable("selectWorld.version")).append(CommonComponents.SPACE);
/* 171 */     if (shouldBackup()) {
/* 172 */       $$2.append((Component)$$1.withStyle(isDowngrade() ? ChatFormatting.RED : ChatFormatting.ITALIC));
/*     */     } else {
/* 174 */       $$2.append((Component)$$1);
/*     */     } 
/* 176 */     $$0.append((Component)$$2);
/* 177 */     return (Component)$$0;
/*     */   }
/*     */   
/*     */   public Component primaryActionMessage() {
/* 181 */     return PLAY_WORLD;
/*     */   }
/*     */   
/*     */   public boolean primaryActionActive() {
/* 185 */     return !isDisabled();
/*     */   }
/*     */   
/*     */   public boolean canEdit() {
/* 189 */     return !isDisabled();
/*     */   }
/*     */   
/*     */   public boolean canRecreate() {
/* 193 */     return !isDisabled();
/*     */   }
/*     */   
/*     */   public boolean canDelete() {
/* 197 */     return true;
/*     */   }
/*     */   
/*     */   public enum BackupStatus {
/* 201 */     NONE(false, false, ""),
/* 202 */     DOWNGRADE(true, true, "downgrade"),
/* 203 */     UPGRADE_TO_SNAPSHOT(true, false, "snapshot");
/*     */     
/*     */     private final boolean shouldBackup;
/*     */     private final boolean severe;
/*     */     private final String translationKey;
/*     */     
/*     */     BackupStatus(boolean $$0, boolean $$1, String $$2) {
/* 210 */       this.shouldBackup = $$0;
/* 211 */       this.severe = $$1;
/* 212 */       this.translationKey = $$2;
/*     */     }
/*     */     
/*     */     public boolean shouldBackup() {
/* 216 */       return this.shouldBackup;
/*     */     }
/*     */     
/*     */     public boolean isSevere() {
/* 220 */       return this.severe;
/*     */     }
/*     */     
/*     */     public String getTranslationKey() {
/* 224 */       return this.translationKey;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SymlinkLevelSummary extends LevelSummary {
/* 229 */     private static final Component MORE_INFO_BUTTON = (Component)Component.translatable("symlink_warning.more_info");
/* 230 */     private static final Component INFO = (Component)Component.translatable("symlink_warning.title").withColor(-65536);
/*     */     
/*     */     public SymlinkLevelSummary(String $$0, Path $$1) {
/* 233 */       super(null, null, $$0, false, false, false, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getLevelName() {
/* 238 */       return getLevelId();
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getInfo() {
/* 243 */       return INFO;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLastPlayed() {
/* 248 */       return -1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDisabled() {
/* 253 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component primaryActionMessage() {
/* 258 */       return MORE_INFO_BUTTON;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean primaryActionActive() {
/* 263 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canEdit() {
/* 268 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canRecreate() {
/* 273 */       return false;
/*     */     } }
/*     */   
/*     */   public static class CorruptedLevelSummary extends LevelSummary {
/*     */     static {
/* 278 */       INFO = (Component)Component.translatable("recover_world.warning").withStyle($$0 -> $$0.withColor(-65536));
/* 279 */     } private static final Component RECOVER = (Component)Component.translatable("recover_world.button"); private static final Component INFO;
/*     */     private final long lastPlayed;
/*     */     
/*     */     public CorruptedLevelSummary(String $$0, Path $$1, long $$2) {
/* 283 */       super(null, null, $$0, false, false, false, $$1);
/* 284 */       this.lastPlayed = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getLevelName() {
/* 289 */       return getLevelId();
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getInfo() {
/* 294 */       return INFO;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLastPlayed() {
/* 299 */       return this.lastPlayed;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDisabled() {
/* 304 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component primaryActionMessage() {
/* 309 */       return RECOVER;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean primaryActionActive() {
/* 314 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canEdit() {
/* 319 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canRecreate() {
/* 324 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\LevelSummary.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */