/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.platform.GlUtil;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public final class Preparations
/*     */ {
/*     */   private final List<Pattern> rendererPatterns;
/*     */   private final List<Pattern> versionPatterns;
/*     */   private final List<Pattern> vendorPatterns;
/*     */   
/*     */   Preparations(List<Pattern> $$0, List<Pattern> $$1, List<Pattern> $$2) {
/* 144 */     this.rendererPatterns = $$0;
/* 145 */     this.versionPatterns = $$1;
/* 146 */     this.vendorPatterns = $$2;
/*     */   }
/*     */   
/*     */   private static String matchAny(List<Pattern> $$0, String $$1) {
/* 150 */     List<String> $$2 = Lists.newArrayList();
/* 151 */     for (Pattern $$3 : $$0) {
/* 152 */       Matcher $$4 = $$3.matcher($$1);
/* 153 */       while ($$4.find()) {
/* 154 */         $$2.add($$4.group());
/*     */       }
/*     */     } 
/* 157 */     return String.join(", ", (Iterable)$$2);
/*     */   }
/*     */   
/*     */   ImmutableMap<String, String> apply() {
/* 161 */     ImmutableMap.Builder<String, String> $$0 = new ImmutableMap.Builder();
/*     */     
/* 163 */     String $$1 = matchAny(this.rendererPatterns, GlUtil.getRenderer());
/* 164 */     if (!$$1.isEmpty()) {
/* 165 */       $$0.put("renderer", $$1);
/*     */     }
/*     */     
/* 168 */     String $$2 = matchAny(this.versionPatterns, GlUtil.getOpenGLVersion());
/* 169 */     if (!$$2.isEmpty()) {
/* 170 */       $$0.put("version", $$2);
/*     */     }
/*     */     
/* 173 */     String $$3 = matchAny(this.vendorPatterns, GlUtil.getVendor());
/* 174 */     if (!$$3.isEmpty()) {
/* 175 */       $$0.put("vendor", $$3);
/*     */     }
/*     */     
/* 178 */     return $$0.build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\GpuWarnlistManager$Preparations.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */