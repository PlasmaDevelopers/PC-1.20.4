/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.blaze3d.platform.GlUtil;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GpuWarnlistManager
/*     */   extends SimplePreparableReloadListener<GpuWarnlistManager.Preparations> {
/*  25 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  26 */   private static final ResourceLocation GPU_WARNLIST_LOCATION = new ResourceLocation("gpu_warnlist.json");
/*     */   
/*  28 */   private ImmutableMap<String, String> warnings = ImmutableMap.of();
/*     */   private boolean showWarning;
/*     */   private boolean warningDismissed;
/*     */   private boolean skipFabulous;
/*     */   
/*     */   public boolean hasWarnings() {
/*  34 */     return !this.warnings.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean willShowWarning() {
/*  38 */     return (hasWarnings() && !this.warningDismissed);
/*     */   }
/*     */   
/*     */   public void showWarning() {
/*  42 */     this.showWarning = true;
/*     */   }
/*     */   
/*     */   public void dismissWarning() {
/*  46 */     this.warningDismissed = true;
/*     */   }
/*     */   
/*     */   public void dismissWarningAndSkipFabulous() {
/*  50 */     this.warningDismissed = true;
/*  51 */     this.skipFabulous = true;
/*     */   }
/*     */   
/*     */   public boolean isShowingWarning() {
/*  55 */     return (this.showWarning && !this.warningDismissed);
/*     */   }
/*     */   
/*     */   public boolean isSkippingFabulous() {
/*  59 */     return this.skipFabulous;
/*     */   }
/*     */   
/*     */   public void resetWarnings() {
/*  63 */     this.showWarning = false;
/*  64 */     this.warningDismissed = false;
/*  65 */     this.skipFabulous = false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getRendererWarnings() {
/*  70 */     return (String)this.warnings.get("renderer");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getVersionWarnings() {
/*  75 */     return (String)this.warnings.get("version");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getVendorWarnings() {
/*  80 */     return (String)this.warnings.get("vendor");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getAllWarnings() {
/*  85 */     StringBuilder $$0 = new StringBuilder();
/*  86 */     this.warnings.forEach(($$1, $$2) -> $$0.append($$1).append(": ").append($$2));
/*  87 */     return ($$0.length() == 0) ? null : $$0.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Preparations prepare(ResourceManager $$0, ProfilerFiller $$1) {
/*  92 */     List<Pattern> $$2 = Lists.newArrayList();
/*  93 */     List<Pattern> $$3 = Lists.newArrayList();
/*  94 */     List<Pattern> $$4 = Lists.newArrayList();
/*     */     
/*  96 */     $$1.startTick();
/*     */     
/*  98 */     JsonObject $$5 = parseJson($$0, $$1);
/*  99 */     if ($$5 != null) {
/* 100 */       $$1.push("compile_regex");
/*     */       
/* 102 */       compilePatterns($$5.getAsJsonArray("renderer"), $$2);
/* 103 */       compilePatterns($$5.getAsJsonArray("version"), $$3);
/* 104 */       compilePatterns($$5.getAsJsonArray("vendor"), $$4);
/*     */       
/* 106 */       $$1.pop();
/*     */     } 
/*     */     
/* 109 */     $$1.endTick();
/* 110 */     return new Preparations($$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void apply(Preparations $$0, ResourceManager $$1, ProfilerFiller $$2) {
/* 115 */     this.warnings = $$0.apply();
/*     */   }
/*     */   
/*     */   private static void compilePatterns(JsonArray $$0, List<Pattern> $$1) {
/* 119 */     $$0.forEach($$1 -> $$0.add(Pattern.compile($$1.getAsString(), 2)));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static JsonObject parseJson(ResourceManager $$0, ProfilerFiller $$1) {
/* 124 */     $$1.push("parse_json");
/*     */     
/* 126 */     JsonObject $$2 = null; 
/* 127 */     try { Reader $$3 = $$0.openAsReader(GPU_WARNLIST_LOCATION); 
/* 128 */       try { $$2 = JsonParser.parseReader($$3).getAsJsonObject();
/* 129 */         if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException|com.google.gson.JsonSyntaxException $$4)
/* 130 */     { LOGGER.warn("Failed to load GPU warnlist"); }
/*     */ 
/*     */     
/* 133 */     $$1.pop();
/*     */     
/* 135 */     return $$2;
/*     */   }
/*     */   
/*     */   protected static final class Preparations {
/*     */     private final List<Pattern> rendererPatterns;
/*     */     private final List<Pattern> versionPatterns;
/*     */     private final List<Pattern> vendorPatterns;
/*     */     
/*     */     Preparations(List<Pattern> $$0, List<Pattern> $$1, List<Pattern> $$2) {
/* 144 */       this.rendererPatterns = $$0;
/* 145 */       this.versionPatterns = $$1;
/* 146 */       this.vendorPatterns = $$2;
/*     */     }
/*     */     
/*     */     private static String matchAny(List<Pattern> $$0, String $$1) {
/* 150 */       List<String> $$2 = Lists.newArrayList();
/* 151 */       for (Pattern $$3 : $$0) {
/* 152 */         Matcher $$4 = $$3.matcher($$1);
/* 153 */         while ($$4.find()) {
/* 154 */           $$2.add($$4.group());
/*     */         }
/*     */       } 
/* 157 */       return String.join(", ", (Iterable)$$2);
/*     */     }
/*     */     
/*     */     ImmutableMap<String, String> apply() {
/* 161 */       ImmutableMap.Builder<String, String> $$0 = new ImmutableMap.Builder();
/*     */       
/* 163 */       String $$1 = matchAny(this.rendererPatterns, GlUtil.getRenderer());
/* 164 */       if (!$$1.isEmpty()) {
/* 165 */         $$0.put("renderer", $$1);
/*     */       }
/*     */       
/* 168 */       String $$2 = matchAny(this.versionPatterns, GlUtil.getOpenGLVersion());
/* 169 */       if (!$$2.isEmpty()) {
/* 170 */         $$0.put("version", $$2);
/*     */       }
/*     */       
/* 173 */       String $$3 = matchAny(this.vendorPatterns, GlUtil.getVendor());
/* 174 */       if (!$$3.isEmpty()) {
/* 175 */         $$0.put("vendor", $$3);
/*     */       }
/*     */       
/* 178 */       return $$0.build();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\GpuWarnlistManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */