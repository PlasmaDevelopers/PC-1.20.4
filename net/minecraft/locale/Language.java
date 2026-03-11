/*     */ package net.minecraft.locale;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.FormattedCharSink;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.StringDecomposer;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Language
/*     */ {
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  32 */   private static final Gson GSON = new Gson();
/*     */   
/*  34 */   private static final Pattern UNSUPPORTED_FORMAT_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
/*     */   public static final String DEFAULT = "en_us";
/*  36 */   private static volatile Language instance = loadDefault();
/*     */   
/*     */   private static Language loadDefault() {
/*  39 */     ImmutableMap.Builder<String, String> $$0 = ImmutableMap.builder();
/*  40 */     Objects.requireNonNull($$0); BiConsumer<String, String> $$1 = $$0::put;
/*  41 */     parseTranslations($$1, "/assets/minecraft/lang/en_us.json");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     final ImmutableMap storage = $$0.build();
/*  48 */     return new Language()
/*     */       {
/*     */         public String getOrDefault(String $$0, String $$1) {
/*  51 */           return (String)storage.getOrDefault($$0, $$1);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean has(String $$0) {
/*  56 */           return storage.containsKey($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isDefaultRightToLeft() {
/*  61 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public FormattedCharSequence getVisualOrder(FormattedText $$0) {
/*  67 */           return $$1 -> $$0.visit((), Style.EMPTY).isPresent();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static void parseTranslations(BiConsumer<String, String> $$0, String $$1) {
/*     */     
/*  75 */     try { InputStream $$2 = Language.class.getResourceAsStream($$1); 
/*  76 */       try { loadFromJson($$2, $$0);
/*  77 */         if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException|com.google.gson.JsonParseException $$3)
/*  78 */     { LOGGER.error("Couldn't read strings from {}", $$1, $$3); }
/*     */   
/*     */   }
/*     */   
/*     */   public static void loadFromJson(InputStream $$0, BiConsumer<String, String> $$1) {
/*  83 */     JsonObject $$2 = (JsonObject)GSON.fromJson(new InputStreamReader($$0, StandardCharsets.UTF_8), JsonObject.class);
/*  84 */     for (Map.Entry<String, JsonElement> $$3 : (Iterable<Map.Entry<String, JsonElement>>)$$2.entrySet()) {
/*  85 */       String $$4 = UNSUPPORTED_FORMAT_PATTERN.matcher(GsonHelper.convertToString($$3.getValue(), $$3.getKey())).replaceAll("%$1s");
/*  86 */       $$1.accept($$3.getKey(), $$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Language getInstance() {
/*  91 */     return instance;
/*     */   }
/*     */   
/*     */   public static void inject(Language $$0) {
/*  95 */     instance = $$0;
/*     */   }
/*     */   
/*     */   public String getOrDefault(String $$0) {
/*  99 */     return getOrDefault($$0, $$0);
/*     */   }
/*     */   
/*     */   public abstract String getOrDefault(String paramString1, String paramString2);
/*     */   
/*     */   public abstract boolean has(String paramString);
/*     */   
/*     */   public abstract boolean isDefaultRightToLeft();
/*     */   
/*     */   public abstract FormattedCharSequence getVisualOrder(FormattedText paramFormattedText);
/*     */   
/*     */   public List<FormattedCharSequence> getVisualOrder(List<FormattedText> $$0) {
/* 111 */     return (List<FormattedCharSequence>)$$0.stream().map(this::getVisualOrder).collect(ImmutableList.toImmutableList());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\locale\Language.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */