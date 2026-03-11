/*    */ package net.minecraft.client.resources.language;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.SortedMap;
/*    */ import java.util.TreeMap;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.metadata.language.LanguageMetadataSection;
/*    */ import net.minecraft.locale.Language;
/*    */ import net.minecraft.server.packs.PackResources;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class LanguageManager implements ResourceManagerReloadListener {
/* 23 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 24 */   private static final LanguageInfo DEFAULT_LANGUAGE = new LanguageInfo("US", "English", false);
/*    */   
/* 26 */   private Map<String, LanguageInfo> languages = (Map<String, LanguageInfo>)ImmutableMap.of("en_us", DEFAULT_LANGUAGE);
/*    */   private String currentCode;
/*    */   
/*    */   public LanguageManager(String $$0) {
/* 30 */     this.currentCode = $$0;
/*    */   }
/*    */   
/*    */   private static Map<String, LanguageInfo> extractLanguages(Stream<PackResources> $$0) {
/* 34 */     Map<String, LanguageInfo> $$1 = Maps.newHashMap();
/*    */     
/* 36 */     $$0.forEach($$1 -> {
/*    */           try {
/*    */             LanguageMetadataSection $$2 = (LanguageMetadataSection)$$1.getMetadataSection((MetadataSectionSerializer)LanguageMetadataSection.TYPE);
/*    */             if ($$2 != null) {
/*    */               Objects.requireNonNull($$0);
/*    */               $$2.languages().forEach($$0::putIfAbsent);
/*    */             } 
/* 43 */           } catch (RuntimeException|java.io.IOException $$3) {
/*    */             LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", $$1.packId(), $$3);
/*    */           } 
/*    */         });
/*    */     
/* 48 */     return (Map<String, LanguageInfo>)ImmutableMap.copyOf($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onResourceManagerReload(ResourceManager $$0) {
/* 53 */     this.languages = extractLanguages($$0.listPacks());
/* 54 */     List<String> $$1 = new ArrayList<>(2);
/* 55 */     boolean $$2 = DEFAULT_LANGUAGE.bidirectional();
/* 56 */     $$1.add("en_us");
/*    */     
/* 58 */     if (!this.currentCode.equals("en_us")) {
/* 59 */       LanguageInfo $$3 = this.languages.get(this.currentCode);
/* 60 */       if ($$3 != null) {
/* 61 */         $$1.add(this.currentCode);
/* 62 */         $$2 = $$3.bidirectional();
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     ClientLanguage $$4 = ClientLanguage.loadFrom($$0, $$1, $$2);
/*    */     
/* 68 */     I18n.setLanguage($$4);
/*    */     
/* 70 */     Language.inject($$4);
/*    */   }
/*    */   
/*    */   public void setSelected(String $$0) {
/* 74 */     this.currentCode = $$0;
/*    */   }
/*    */   
/*    */   public String getSelected() {
/* 78 */     return this.currentCode;
/*    */   }
/*    */   
/*    */   public SortedMap<String, LanguageInfo> getLanguages() {
/* 82 */     return new TreeMap<>(this.languages);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public LanguageInfo getLanguage(String $$0) {
/* 87 */     return this.languages.get($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\language\LanguageManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */