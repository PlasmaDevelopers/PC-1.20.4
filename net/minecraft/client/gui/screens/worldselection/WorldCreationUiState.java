/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.tags.WorldPresetTags;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*     */ 
/*     */ public class WorldCreationUiState {
/*  30 */   private static final Component DEFAULT_WORLD_NAME = (Component)Component.translatable("selectWorld.newWorld");
/*     */   
/*  32 */   private final List<Consumer<WorldCreationUiState>> listeners = new ArrayList<>();
/*     */   
/*  34 */   private String name = DEFAULT_WORLD_NAME.getString();
/*     */   
/*  36 */   private SelectedGameMode gameMode = SelectedGameMode.SURVIVAL;
/*  37 */   private Difficulty difficulty = Difficulty.NORMAL;
/*     */   
/*     */   @Nullable
/*     */   private Boolean allowCheats;
/*     */   private String seed;
/*     */   private boolean generateStructures;
/*     */   private boolean bonusChest;
/*     */   private final Path savesFolder;
/*     */   private String targetFolder;
/*     */   private WorldCreationContext settings;
/*     */   private WorldTypeEntry worldType;
/*  48 */   private final List<WorldTypeEntry> normalPresetList = new ArrayList<>();
/*  49 */   private final List<WorldTypeEntry> altPresetList = new ArrayList<>();
/*     */   
/*  51 */   private GameRules gameRules = new GameRules();
/*     */   
/*     */   public WorldCreationUiState(Path $$0, WorldCreationContext $$1, Optional<ResourceKey<WorldPreset>> $$2, OptionalLong $$3) {
/*  54 */     this.savesFolder = $$0;
/*  55 */     this.settings = $$1;
/*  56 */     this.worldType = new WorldTypeEntry(findPreset($$1, $$2).orElse(null));
/*  57 */     updatePresetLists();
/*  58 */     this.seed = $$3.isPresent() ? Long.toString($$3.getAsLong()) : "";
/*  59 */     this.generateStructures = $$1.options().generateStructures();
/*  60 */     this.bonusChest = $$1.options().generateBonusChest();
/*  61 */     this.targetFolder = findResultFolder(this.name);
/*     */   }
/*     */   
/*     */   public void addListener(Consumer<WorldCreationUiState> $$0) {
/*  65 */     this.listeners.add($$0);
/*     */   }
/*     */   
/*     */   public void onChanged() {
/*  69 */     boolean $$0 = isBonusChest();
/*  70 */     if ($$0 != this.settings.options().generateBonusChest()) {
/*  71 */       this.settings = this.settings.withOptions($$1 -> $$1.withBonusChest($$0));
/*     */     }
/*  73 */     boolean $$1 = isGenerateStructures();
/*  74 */     if ($$1 != this.settings.options().generateStructures()) {
/*  75 */       this.settings = this.settings.withOptions($$1 -> $$1.withStructures($$0));
/*     */     }
/*  77 */     for (Consumer<WorldCreationUiState> $$2 : this.listeners) {
/*  78 */       $$2.accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setName(String $$0) {
/*  83 */     this.name = $$0;
/*  84 */     this.targetFolder = findResultFolder($$0);
/*  85 */     onChanged();
/*     */   }
/*     */   
/*     */   private String findResultFolder(String $$0) {
/*  89 */     String $$1 = $$0.trim();
/*     */     try {
/*  91 */       return FileUtil.findAvailableName(this.savesFolder, !$$1.isEmpty() ? $$1 : DEFAULT_WORLD_NAME.getString(), "");
/*  92 */     } catch (Exception exception) {
/*     */       
/*     */       try {
/*  95 */         return FileUtil.findAvailableName(this.savesFolder, "World", "");
/*  96 */       } catch (IOException $$2) {
/*  97 */         throw new RuntimeException("Could not create save folder", $$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public String getName() {
/* 102 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getTargetFolder() {
/* 106 */     return this.targetFolder;
/*     */   }
/*     */   
/*     */   public void setGameMode(SelectedGameMode $$0) {
/* 110 */     this.gameMode = $$0;
/* 111 */     onChanged();
/*     */   }
/*     */   
/*     */   public SelectedGameMode getGameMode() {
/* 115 */     if (isDebug()) {
/* 116 */       return SelectedGameMode.DEBUG;
/*     */     }
/* 118 */     return this.gameMode;
/*     */   }
/*     */   
/*     */   public void setDifficulty(Difficulty $$0) {
/* 122 */     this.difficulty = $$0;
/* 123 */     onChanged();
/*     */   }
/*     */   
/*     */   public Difficulty getDifficulty() {
/* 127 */     if (isHardcore()) {
/* 128 */       return Difficulty.HARD;
/*     */     }
/* 130 */     return this.difficulty;
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/* 134 */     return (getGameMode() == SelectedGameMode.HARDCORE);
/*     */   }
/*     */   
/*     */   public void setAllowCheats(boolean $$0) {
/* 138 */     this.allowCheats = Boolean.valueOf($$0);
/* 139 */     onChanged();
/*     */   }
/*     */   
/*     */   public boolean isAllowCheats() {
/* 143 */     if (isDebug()) {
/* 144 */       return true;
/*     */     }
/* 146 */     if (isHardcore()) {
/* 147 */       return false;
/*     */     }
/* 149 */     if (this.allowCheats == null) {
/* 150 */       return (getGameMode() == SelectedGameMode.CREATIVE);
/*     */     }
/* 152 */     return this.allowCheats.booleanValue();
/*     */   }
/*     */   
/*     */   public void setSeed(String $$0) {
/* 156 */     this.seed = $$0;
/* 157 */     this.settings = this.settings.withOptions($$0 -> $$0.withSeed(WorldOptions.parseSeed(getSeed())));
/* 158 */     onChanged();
/*     */   }
/*     */   
/*     */   public String getSeed() {
/* 162 */     return this.seed;
/*     */   }
/*     */   
/*     */   public void setGenerateStructures(boolean $$0) {
/* 166 */     this.generateStructures = $$0;
/* 167 */     onChanged();
/*     */   }
/*     */   
/*     */   public boolean isGenerateStructures() {
/* 171 */     if (isDebug()) {
/* 172 */       return false;
/*     */     }
/* 174 */     return this.generateStructures;
/*     */   }
/*     */   
/*     */   public void setBonusChest(boolean $$0) {
/* 178 */     this.bonusChest = $$0;
/* 179 */     onChanged();
/*     */   }
/*     */   
/*     */   public boolean isBonusChest() {
/* 183 */     if (isDebug() || isHardcore()) {
/* 184 */       return false;
/*     */     }
/* 186 */     return this.bonusChest;
/*     */   }
/*     */   
/*     */   public void setSettings(WorldCreationContext $$0) {
/* 190 */     this.settings = $$0;
/* 191 */     updatePresetLists();
/* 192 */     onChanged();
/*     */   }
/*     */   
/*     */   public WorldCreationContext getSettings() {
/* 196 */     return this.settings;
/*     */   }
/*     */   
/*     */   public void updateDimensions(WorldCreationContext.DimensionsUpdater $$0) {
/* 200 */     this.settings = this.settings.withDimensions($$0);
/* 201 */     onChanged();
/*     */   }
/*     */   
/*     */   protected boolean tryUpdateDataConfiguration(WorldDataConfiguration $$0) {
/* 205 */     WorldDataConfiguration $$1 = this.settings.dataConfiguration();
/* 206 */     if ($$1.dataPacks().getEnabled().equals($$0.dataPacks().getEnabled()) && $$1
/* 207 */       .enabledFeatures().equals($$0.enabledFeatures())) {
/*     */ 
/*     */       
/* 210 */       this.settings = new WorldCreationContext(this.settings.options(), this.settings.datapackDimensions(), this.settings.selectedDimensions(), this.settings.worldgenRegistries(), this.settings.dataPackResources(), $$0);
/* 211 */       return true;
/*     */     } 
/* 213 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isDebug() {
/* 217 */     return this.settings.selectedDimensions().isDebug();
/*     */   }
/*     */   
/*     */   public void setWorldType(WorldTypeEntry $$0) {
/* 221 */     this.worldType = $$0;
/* 222 */     Holder<WorldPreset> $$1 = $$0.preset();
/* 223 */     if ($$1 != null) {
/* 224 */       updateDimensions(($$1, $$2) -> ((WorldPreset)$$0.value()).createWorldDimensions());
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldTypeEntry getWorldType() {
/* 229 */     return this.worldType;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PresetEditor getPresetEditor() {
/* 234 */     Holder<WorldPreset> $$0 = getWorldType().preset();
/* 235 */     return ($$0 != null) ? PresetEditor.EDITORS.get($$0.unwrapKey()) : null;
/*     */   }
/*     */   
/*     */   public List<WorldTypeEntry> getNormalPresetList() {
/* 239 */     return this.normalPresetList;
/*     */   }
/*     */   
/*     */   public List<WorldTypeEntry> getAltPresetList() {
/* 243 */     return this.altPresetList;
/*     */   }
/*     */   
/*     */   private void updatePresetLists() {
/* 247 */     Registry<WorldPreset> $$0 = getSettings().worldgenLoadContext().registryOrThrow(Registries.WORLD_PRESET);
/*     */     
/* 249 */     this.normalPresetList.clear();
/* 250 */     this.normalPresetList.addAll(getNonEmptyList($$0, WorldPresetTags.NORMAL).orElseGet(() -> $$0.holders().map(WorldTypeEntry::new).toList()));
/* 251 */     this.altPresetList.clear();
/* 252 */     this.altPresetList.addAll(getNonEmptyList($$0, WorldPresetTags.EXTENDED).orElse(this.normalPresetList));
/* 253 */     Holder<WorldPreset> $$1 = this.worldType.preset();
/* 254 */     if ($$1 != null)
/*     */     {
/*     */       
/* 257 */       this.worldType = findPreset(getSettings(), $$1.unwrapKey()).<WorldTypeEntry>map(WorldTypeEntry::new).orElse(this.normalPresetList.get(0)); } 
/*     */   }
/*     */   public static final class WorldTypeEntry extends Record { @Nullable
/*     */     private final Holder<WorldPreset> preset;
/* 261 */     public WorldTypeEntry(@Nullable Holder<WorldPreset> $$0) { this.preset = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #261	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 261 */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry; } @Nullable public Holder<WorldPreset> preset() { return this.preset; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #261	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #261	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState$WorldTypeEntry;
/* 262 */       //   0	8	1	$$0	Ljava/lang/Object; } private static final Component CUSTOM_WORLD_DESCRIPTION = (Component)Component.translatable("generator.custom");
/*     */     
/*     */     public Component describePreset() {
/* 265 */       return Optional.<Holder<WorldPreset>>ofNullable(this.preset).flatMap(Holder::unwrapKey)
/* 266 */         .map($$0 -> Component.translatable($$0.location().toLanguageKey("generator")))
/* 267 */         .orElse(CUSTOM_WORLD_DESCRIPTION);
/*     */     }
/*     */     
/*     */     public boolean isAmplified() {
/* 271 */       return Optional.<Holder<WorldPreset>>ofNullable(this.preset).flatMap(Holder::unwrapKey).filter($$0 -> $$0.equals(WorldPresets.AMPLIFIED)).isPresent();
/*     */     } }
/*     */ 
/*     */   
/*     */   private static Optional<Holder<WorldPreset>> findPreset(WorldCreationContext $$0, Optional<ResourceKey<WorldPreset>> $$1) {
/* 276 */     return $$1.flatMap($$1 -> $$0.worldgenLoadContext().registryOrThrow(Registries.WORLD_PRESET).getHolder($$1));
/*     */   }
/*     */   
/*     */   private static Optional<List<WorldTypeEntry>> getNonEmptyList(Registry<WorldPreset> $$0, TagKey<WorldPreset> $$1) {
/* 280 */     return $$0.getTag($$1).map($$0 -> $$0.stream().map(WorldTypeEntry::new).toList()).filter($$0 -> !$$0.isEmpty());
/*     */   }
/*     */   
/*     */   public void setGameRules(GameRules $$0) {
/* 284 */     this.gameRules = $$0;
/* 285 */     onChanged();
/*     */   }
/*     */   
/*     */   public GameRules getGameRules() {
/* 289 */     return this.gameRules;
/*     */   }
/*     */   
/*     */   public enum SelectedGameMode {
/* 293 */     SURVIVAL("survival", GameType.SURVIVAL),
/* 294 */     HARDCORE("hardcore", GameType.SURVIVAL),
/* 295 */     CREATIVE("creative", GameType.CREATIVE),
/*     */     
/* 297 */     DEBUG("spectator", GameType.SPECTATOR);
/*     */     
/*     */     public final GameType gameType;
/*     */     
/*     */     public final Component displayName;
/*     */     private final Component info;
/*     */     
/*     */     SelectedGameMode(String $$0, GameType $$1) {
/* 305 */       this.gameType = $$1;
/* 306 */       this.displayName = (Component)Component.translatable("selectWorld.gameMode." + $$0);
/* 307 */       this.info = (Component)Component.translatable("selectWorld.gameMode." + $$0 + ".info");
/*     */     }
/*     */     
/*     */     public Component getInfo() {
/* 311 */       return this.info;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\WorldCreationUiState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */