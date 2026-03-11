/*     */ package com.mojang.realmsclient.dto;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.realmsclient.util.JsonUtils;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RealmsWorldOptions
/*     */   extends ValueObject
/*     */ {
/*     */   public final boolean pvp;
/*     */   public final boolean spawnAnimals;
/*     */   public final boolean spawnMonsters;
/*     */   public final boolean spawnNPCs;
/*     */   public final int spawnProtection;
/*     */   public final boolean commandBlocks;
/*     */   public final boolean forceGameMode;
/*     */   public final int difficulty;
/*     */   public final int gameMode;
/*     */   private final String slotName;
/*     */   public final String version;
/*     */   public final RealmsServer.Compatibility compatibility;
/*     */   public long templateId;
/*     */   @Nullable
/*     */   public String templateImage;
/*     */   public boolean empty;
/*     */   private static final boolean DEFAULT_FORCE_GAME_MODE = false;
/*     */   private static final boolean DEFAULT_PVP = true;
/*     */   private static final boolean DEFAULT_SPAWN_ANIMALS = true;
/*     */   private static final boolean DEFAULT_SPAWN_MONSTERS = true;
/*     */   private static final boolean DEFAULT_SPAWN_NPCS = true;
/*     */   private static final int DEFAULT_SPAWN_PROTECTION = 0;
/*     */   private static final boolean DEFAULT_COMMAND_BLOCKS = false;
/*     */   private static final int DEFAULT_DIFFICULTY = 2;
/*     */   private static final int DEFAULT_GAME_MODE = 0;
/*     */   private static final String DEFAULT_SLOT_NAME = "";
/*     */   private static final String DEFAULT_VERSION = "";
/*  46 */   private static final RealmsServer.Compatibility DEFAULT_COMPATIBILITY = RealmsServer.Compatibility.UNVERIFIABLE;
/*     */   private static final long DEFAULT_TEMPLATE_ID = -1L;
/*  48 */   private static final String DEFAULT_TEMPLATE_IMAGE = null;
/*     */   
/*     */   public RealmsWorldOptions(boolean $$0, boolean $$1, boolean $$2, boolean $$3, int $$4, boolean $$5, int $$6, int $$7, boolean $$8, String $$9, String $$10, RealmsServer.Compatibility $$11) {
/*  51 */     this.pvp = $$0;
/*  52 */     this.spawnAnimals = $$1;
/*  53 */     this.spawnMonsters = $$2;
/*  54 */     this.spawnNPCs = $$3;
/*  55 */     this.spawnProtection = $$4;
/*  56 */     this.commandBlocks = $$5;
/*  57 */     this.difficulty = $$6;
/*  58 */     this.gameMode = $$7;
/*  59 */     this.forceGameMode = $$8;
/*  60 */     this.slotName = $$9;
/*  61 */     this.version = $$10;
/*  62 */     this.compatibility = $$11;
/*     */   }
/*     */   
/*     */   public static RealmsWorldOptions createDefaults() {
/*  66 */     return new RealmsWorldOptions(true, true, true, true, 0, false, 2, 0, false, "", "", DEFAULT_COMPATIBILITY);
/*     */   }
/*     */   
/*     */   public static RealmsWorldOptions createEmptyDefaults() {
/*  70 */     RealmsWorldOptions $$0 = createDefaults();
/*  71 */     $$0.setEmpty(true);
/*  72 */     return $$0;
/*     */   }
/*     */   
/*     */   public void setEmpty(boolean $$0) {
/*  76 */     this.empty = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RealmsWorldOptions parse(JsonObject $$0) {
/*  92 */     RealmsWorldOptions $$1 = new RealmsWorldOptions(JsonUtils.getBooleanOr("pvp", $$0, true), JsonUtils.getBooleanOr("spawnAnimals", $$0, true), JsonUtils.getBooleanOr("spawnMonsters", $$0, true), JsonUtils.getBooleanOr("spawnNPCs", $$0, true), JsonUtils.getIntOr("spawnProtection", $$0, 0), JsonUtils.getBooleanOr("commandBlocks", $$0, false), JsonUtils.getIntOr("difficulty", $$0, 2), JsonUtils.getIntOr("gameMode", $$0, 0), JsonUtils.getBooleanOr("forceGameMode", $$0, false), JsonUtils.getRequiredStringOr("slotName", $$0, ""), JsonUtils.getRequiredStringOr("version", $$0, ""), RealmsServer.getCompatibility(JsonUtils.getRequiredStringOr("compatibility", $$0, RealmsServer.Compatibility.UNVERIFIABLE.name())));
/*     */ 
/*     */     
/*  95 */     $$1.templateId = JsonUtils.getLongOr("worldTemplateId", $$0, -1L);
/*  96 */     $$1.templateImage = JsonUtils.getStringOr("worldTemplateImage", $$0, DEFAULT_TEMPLATE_IMAGE);
/*     */     
/*  98 */     return $$1;
/*     */   }
/*     */   
/*     */   public String getSlotName(int $$0) {
/* 102 */     if (Util.isBlank(this.slotName)) {
/* 103 */       if (this.empty) {
/* 104 */         return I18n.get("mco.configure.world.slot.empty", new Object[0]);
/*     */       }
/*     */       
/* 107 */       return getDefaultSlotName($$0);
/*     */     } 
/* 109 */     return this.slotName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultSlotName(int $$0) {
/* 114 */     return I18n.get("mco.configure.world.slot", new Object[] { Integer.valueOf($$0) });
/*     */   }
/*     */   
/*     */   public String toJson() {
/* 118 */     JsonObject $$0 = new JsonObject();
/*     */     
/* 120 */     if (this.pvp != true) {
/* 121 */       $$0.addProperty("pvp", Boolean.valueOf(this.pvp));
/*     */     }
/*     */     
/* 124 */     if (this.spawnAnimals != true) {
/* 125 */       $$0.addProperty("spawnAnimals", Boolean.valueOf(this.spawnAnimals));
/*     */     }
/*     */     
/* 128 */     if (this.spawnMonsters != true) {
/* 129 */       $$0.addProperty("spawnMonsters", Boolean.valueOf(this.spawnMonsters));
/*     */     }
/*     */     
/* 132 */     if (this.spawnNPCs != true) {
/* 133 */       $$0.addProperty("spawnNPCs", Boolean.valueOf(this.spawnNPCs));
/*     */     }
/*     */     
/* 136 */     if (this.spawnProtection != 0) {
/* 137 */       $$0.addProperty("spawnProtection", Integer.valueOf(this.spawnProtection));
/*     */     }
/*     */     
/* 140 */     if (this.commandBlocks) {
/* 141 */       $$0.addProperty("commandBlocks", Boolean.valueOf(this.commandBlocks));
/*     */     }
/*     */     
/* 144 */     if (this.difficulty != 2) {
/* 145 */       $$0.addProperty("difficulty", Integer.valueOf(this.difficulty));
/*     */     }
/*     */     
/* 148 */     if (this.gameMode != 0) {
/* 149 */       $$0.addProperty("gameMode", Integer.valueOf(this.gameMode));
/*     */     }
/*     */     
/* 152 */     if (this.forceGameMode) {
/* 153 */       $$0.addProperty("forceGameMode", Boolean.valueOf(this.forceGameMode));
/*     */     }
/*     */     
/* 156 */     if (!Objects.equals(this.slotName, "")) {
/* 157 */       $$0.addProperty("slotName", this.slotName);
/*     */     }
/*     */     
/* 160 */     if (!Objects.equals(this.version, "")) {
/* 161 */       $$0.addProperty("version", this.version);
/*     */     }
/*     */     
/* 164 */     if (this.compatibility != DEFAULT_COMPATIBILITY) {
/* 165 */       $$0.addProperty("compatibility", this.compatibility.name());
/*     */     }
/*     */     
/* 168 */     return $$0.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public RealmsWorldOptions clone() {
/* 173 */     return new RealmsWorldOptions(this.pvp, this.spawnAnimals, this.spawnMonsters, this.spawnNPCs, this.spawnProtection, this.commandBlocks, this.difficulty, this.gameMode, this.forceGameMode, this.slotName, this.version, this.compatibility);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsWorldOptions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */