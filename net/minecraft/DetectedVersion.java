/*     */ package net.minecraft;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.util.Date;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.world.level.storage.DataVersion;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DetectedVersion
/*     */   implements WorldVersion
/*     */ {
/*  19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  21 */   public static final WorldVersion BUILT_IN = new DetectedVersion();
/*     */   
/*     */   private final String id;
/*     */   private final String name;
/*     */   private final boolean stable;
/*     */   private final DataVersion worldVersion;
/*     */   private final int protocolVersion;
/*     */   private final int resourcePackVersion;
/*     */   private final int dataPackVersion;
/*     */   private final Date buildTime;
/*     */   
/*     */   private DetectedVersion() {
/*  33 */     this.id = UUID.randomUUID().toString().replaceAll("-", "");
/*  34 */     this.name = "1.20.4";
/*  35 */     this.stable = true;
/*  36 */     this.worldVersion = new DataVersion(3700, "main");
/*  37 */     this.protocolVersion = SharedConstants.getProtocolVersion();
/*  38 */     this.resourcePackVersion = 22;
/*  39 */     this.dataPackVersion = 26;
/*  40 */     this.buildTime = new Date();
/*     */   }
/*     */   
/*     */   private DetectedVersion(JsonObject $$0) {
/*  44 */     this.id = GsonHelper.getAsString($$0, "id");
/*  45 */     this.name = GsonHelper.getAsString($$0, "name");
/*  46 */     this.stable = GsonHelper.getAsBoolean($$0, "stable");
/*  47 */     this.worldVersion = new DataVersion(GsonHelper.getAsInt($$0, "world_version"), GsonHelper.getAsString($$0, "series_id", DataVersion.MAIN_SERIES));
/*  48 */     this.protocolVersion = GsonHelper.getAsInt($$0, "protocol_version");
/*     */     
/*  50 */     JsonObject $$1 = GsonHelper.getAsJsonObject($$0, "pack_version");
/*  51 */     this.resourcePackVersion = GsonHelper.getAsInt($$1, "resource");
/*  52 */     this.dataPackVersion = GsonHelper.getAsInt($$1, "data");
/*  53 */     this.buildTime = Date.from(ZonedDateTime.parse(GsonHelper.getAsString($$0, "build_time")).toInstant());
/*     */   }
/*     */   public static WorldVersion tryDetectVersion() {
/*     */     
/*  57 */     try { InputStream $$0 = DetectedVersion.class.getResourceAsStream("/version.json"); 
/*  58 */       try { if ($$0 == null)
/*  59 */         { LOGGER.warn("Missing version information!");
/*  60 */           WorldVersion worldVersion = BUILT_IN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  66 */           if ($$0 != null) $$0.close();  return worldVersion; }  InputStreamReader $$1 = new InputStreamReader($$0); try { DetectedVersion detectedVersion = new DetectedVersion(GsonHelper.parse($$1)); $$1.close(); if ($$0 != null) $$0.close();  return detectedVersion; } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException|com.google.gson.JsonParseException $$2)
/*  67 */     { throw new IllegalStateException("Game version information is corrupt", $$2); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  73 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  78 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public DataVersion getDataVersion() {
/*  83 */     return this.worldVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProtocolVersion() {
/*  88 */     return this.protocolVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPackVersion(PackType $$0) {
/*  93 */     return ($$0 == PackType.SERVER_DATA) ? this.dataPackVersion : this.resourcePackVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getBuildTime() {
/*  98 */     return this.buildTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStable() {
/* 103 */     return this.stable;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\DetectedVersion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */