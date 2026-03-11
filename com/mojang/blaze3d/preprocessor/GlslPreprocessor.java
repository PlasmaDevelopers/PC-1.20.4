/*     */ package com.mojang.blaze3d.preprocessor;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.StringUtil;
/*     */ 
/*     */ 
/*     */ public abstract class GlslPreprocessor
/*     */ {
/*     */   private static final String C_COMMENT = "/\\*(?:[^*]|\\*+[^*/])*\\*+/";
/*     */   private static final String LINE_COMMENT = "//[^\\v]*";
/*  19 */   private static final Pattern REGEX_MOJ_IMPORT = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*moj_import(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(?:\"(.*)\"|<(.*)>))");
/*  20 */   private static final Pattern REGEX_VERSION = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*version(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(\\d+))\\b");
/*  21 */   private static final Pattern REGEX_ENDS_WITH_WHITESPACE = Pattern.compile("(?:^|\\v)(?:\\s|/\\*(?:[^*]|\\*+[^*/])*\\*+/|(//[^\\v]*))*\\z");
/*     */   
/*     */   public List<String> process(String $$0) {
/*  24 */     Context $$1 = new Context();
/*  25 */     List<String> $$2 = processImports($$0, $$1, "");
/*     */     
/*  27 */     $$2.set(0, setVersion($$2.get(0), $$1.glslVersion));
/*  28 */     return $$2;
/*     */   }
/*     */   
/*     */   private List<String> processImports(String $$0, Context $$1, String $$2) {
/*  32 */     int $$3 = $$1.sourceId;
/*     */     
/*  34 */     int $$4 = 0;
/*     */     
/*  36 */     String $$5 = "";
/*  37 */     List<String> $$6 = Lists.newArrayList();
/*  38 */     Matcher $$7 = REGEX_MOJ_IMPORT.matcher($$0);
/*  39 */     while ($$7.find()) {
/*  40 */       if (isDirectiveDisabled($$0, $$7, $$4)) {
/*     */         continue;
/*     */       }
/*     */       
/*  44 */       String $$8 = $$7.group(2);
/*  45 */       boolean $$9 = ($$8 != null);
/*  46 */       if (!$$9) {
/*  47 */         $$8 = $$7.group(3);
/*     */       }
/*     */       
/*  50 */       if ($$8 == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  54 */       String $$10 = $$0.substring($$4, $$7.start(1));
/*     */       
/*  56 */       String $$11 = $$2 + $$2;
/*  57 */       String $$12 = applyImport($$9, $$11);
/*  58 */       if (!Strings.isNullOrEmpty($$12)) {
/*  59 */         if (!StringUtil.endsWithNewLine($$12)) {
/*  60 */           $$12 = $$12 + $$12;
/*     */         }
/*     */ 
/*     */         
/*  64 */         int $$13 = ++$$1.sourceId;
/*  65 */         List<String> $$14 = processImports($$12, $$1, $$9 ? FileUtil.getFullResourcePath($$11) : "");
/*     */ 
/*     */         
/*  68 */         $$14.set(0, String.format(Locale.ROOT, "#line %d %d\n%s", new Object[] { Integer.valueOf(0), Integer.valueOf($$13), processVersions($$14.get(0), $$1) }));
/*     */         
/*  70 */         if (!Util.isBlank($$10)) {
/*  71 */           $$6.add($$10);
/*     */         }
/*  73 */         $$6.addAll($$14);
/*     */       } else {
/*     */         
/*  76 */         String $$15 = $$9 ? String.format(Locale.ROOT, "/*#moj_import \"%s\"*/", new Object[] { $$8 }) : String.format(Locale.ROOT, "/*#moj_import <%s>*/", new Object[] { $$8 });
/*  77 */         $$6.add($$5 + $$5 + $$10);
/*     */       } 
/*     */ 
/*     */       
/*  81 */       int $$16 = StringUtil.lineCount($$0.substring(0, $$7.end(1)));
/*  82 */       $$5 = String.format(Locale.ROOT, "#line %d %d", new Object[] { Integer.valueOf($$16), Integer.valueOf($$3) });
/*     */       
/*  84 */       $$4 = $$7.end(1);
/*     */     } 
/*     */     
/*  87 */     String $$17 = $$0.substring($$4);
/*  88 */     if (!Util.isBlank($$17)) {
/*  89 */       $$6.add($$5 + $$5);
/*     */     }
/*  91 */     return $$6;
/*     */   }
/*     */   
/*     */   private String processVersions(String $$0, Context $$1) {
/*  95 */     Matcher $$2 = REGEX_VERSION.matcher($$0);
/*  96 */     if ($$2.find() && isDirectiveEnabled($$0, $$2)) {
/*  97 */       $$1.glslVersion = Math.max($$1.glslVersion, Integer.parseInt($$2.group(2)));
/*     */       
/*  99 */       return $$0.substring(0, $$2.start(1)) + "/*" + $$0.substring(0, $$2.start(1)) + "*/" + $$0
/*     */         
/* 101 */         .substring($$2.start(1), $$2.end(1));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 106 */     return $$0;
/*     */   }
/*     */   
/*     */   private String setVersion(String $$0, int $$1) {
/* 110 */     Matcher $$2 = REGEX_VERSION.matcher($$0);
/* 111 */     if ($$2.find() && isDirectiveEnabled($$0, $$2)) {
/* 112 */       return $$0.substring(0, $$2.start(2)) + $$0.substring(0, $$2.start(2)) + 
/* 113 */         Math.max($$1, Integer.parseInt($$2.group(2)));
/*     */     }
/*     */     
/* 116 */     return $$0;
/*     */   }
/*     */   
/*     */   private static boolean isDirectiveEnabled(String $$0, Matcher $$1) {
/* 120 */     return !isDirectiveDisabled($$0, $$1, 0);
/*     */   }
/*     */   
/*     */   private static boolean isDirectiveDisabled(String $$0, Matcher $$1, int $$2) {
/* 124 */     int $$3 = $$1.start() - $$2;
/* 125 */     if ($$3 == 0) {
/* 126 */       return false;
/*     */     }
/*     */     
/* 129 */     Matcher $$4 = REGEX_ENDS_WITH_WHITESPACE.matcher($$0.substring($$2, $$1.start()));
/* 130 */     if (!$$4.find()) {
/* 131 */       return true;
/*     */     }
/*     */     
/* 134 */     int $$5 = $$4.end(1);
/* 135 */     return ($$5 == $$1.start());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public abstract String applyImport(boolean paramBoolean, String paramString);
/*     */   
/*     */   private static final class Context {
/*     */     int glslVersion;
/*     */     int sourceId;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\preprocessor\GlslPreprocessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */