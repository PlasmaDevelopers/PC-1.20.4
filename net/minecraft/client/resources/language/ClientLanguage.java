/*    */ package net.minecraft.client.resources.language;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.locale.Language;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class ClientLanguage extends Language {
/* 21 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Map<String, String> storage;
/*    */   private final boolean defaultRightToLeft;
/*    */   
/*    */   private ClientLanguage(Map<String, String> $$0, boolean $$1) {
/* 27 */     this.storage = $$0;
/* 28 */     this.defaultRightToLeft = $$1;
/*    */   }
/*    */   
/*    */   public static ClientLanguage loadFrom(ResourceManager $$0, List<String> $$1, boolean $$2) {
/* 32 */     Map<String, String> $$3 = Maps.newHashMap();
/*    */     
/* 34 */     for (String $$4 : $$1) {
/* 35 */       String $$5 = String.format(Locale.ROOT, "lang/%s.json", new Object[] { $$4 });
/*    */       
/* 37 */       for (String $$6 : $$0.getNamespaces()) {
/*    */         try {
/* 39 */           ResourceLocation $$7 = new ResourceLocation($$6, $$5);
/* 40 */           appendFrom($$4, $$0.getResourceStack($$7), $$3);
/* 41 */         } catch (Exception $$8) {
/* 42 */           LOGGER.warn("Skipped language file: {}:{} ({})", new Object[] { $$6, $$5, $$8.toString() });
/*    */         } 
/*    */       } 
/*    */     } 
/* 46 */     return new ClientLanguage((Map<String, String>)ImmutableMap.copyOf($$3), $$2);
/*    */   }
/*    */   
/*    */   private static void appendFrom(String $$0, List<Resource> $$1, Map<String, String> $$2) {
/* 50 */     for (Resource $$3 : $$1) { 
/* 51 */       try { InputStream $$4 = $$3.open(); 
/* 52 */         try { Objects.requireNonNull($$2); Language.loadFromJson($$4, $$2::put);
/* 53 */           if ($$4 != null) $$4.close();  } catch (Throwable throwable) { if ($$4 != null) try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$5)
/* 54 */       { LOGGER.warn("Failed to load translations for {} from pack {}", new Object[] { $$0, $$3.sourcePackId(), $$5 }); }
/*    */        }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public String getOrDefault(String $$0, String $$1) {
/* 61 */     return this.storage.getOrDefault($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean has(String $$0) {
/* 66 */     return this.storage.containsKey($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDefaultRightToLeft() {
/* 71 */     return this.defaultRightToLeft;
/*    */   }
/*    */ 
/*    */   
/*    */   public FormattedCharSequence getVisualOrder(FormattedText $$0) {
/* 76 */     return FormattedBidiReorder.reorder($$0, this.defaultRightToLeft);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\language\ClientLanguage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */