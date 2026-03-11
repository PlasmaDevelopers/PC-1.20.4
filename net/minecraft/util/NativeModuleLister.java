/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Platform;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.platform.win32.Kernel32;
/*     */ import com.sun.jna.platform.win32.Kernel32Util;
/*     */ import com.sun.jna.platform.win32.Tlhelp32;
/*     */ import com.sun.jna.platform.win32.Version;
/*     */ import com.sun.jna.platform.win32.Win32Exception;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class NativeModuleLister
/*     */ {
/*  29 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int LANG_MASK = 65535;
/*     */   
/*     */   private static final int DEFAULT_LANG = 1033;
/*     */   private static final int CODEPAGE_MASK = -65536;
/*     */   private static final int DEFAULT_CODEPAGE = 78643200;
/*     */   
/*     */   public static List<NativeModuleInfo> listModules() {
/*  38 */     if (!Platform.isWindows()) {
/*  39 */       return (List<NativeModuleInfo>)ImmutableList.of();
/*     */     }
/*     */     
/*  42 */     int $$0 = Kernel32.INSTANCE.GetCurrentProcessId();
/*     */     
/*  44 */     ImmutableList.Builder<NativeModuleInfo> $$1 = ImmutableList.builder();
/*     */     
/*  46 */     List<Tlhelp32.MODULEENTRY32W> $$2 = Kernel32Util.getModules($$0);
/*     */     
/*  48 */     for (Tlhelp32.MODULEENTRY32W $$3 : $$2) {
/*  49 */       String $$4 = $$3.szModule();
/*  50 */       Optional<NativeModuleVersion> $$5 = tryGetVersion($$3.szExePath());
/*  51 */       $$1.add(new NativeModuleInfo($$4, $$5));
/*     */     } 
/*     */     
/*  54 */     return (List<NativeModuleInfo>)$$1.build();
/*     */   }
/*     */   
/*     */   private static Optional<NativeModuleVersion> tryGetVersion(String $$0) {
/*     */     try {
/*  59 */       IntByReference $$1 = new IntByReference();
/*     */       
/*  61 */       int $$2 = Version.INSTANCE.GetFileVersionInfoSize($$0, $$1);
/*     */       
/*  63 */       if ($$2 == 0) {
/*  64 */         int $$3 = Native.getLastError();
/*  65 */         if ($$3 == 1813 || $$3 == 1812) {
/*  66 */           return Optional.empty();
/*     */         }
/*  68 */         throw new Win32Exception($$3);
/*     */       } 
/*     */       
/*  71 */       Memory memory = new Memory($$2);
/*     */       
/*  73 */       if (!Version.INSTANCE.GetFileVersionInfo($$0, 0, $$2, (Pointer)memory)) {
/*  74 */         throw new Win32Exception(Native.getLastError());
/*     */       }
/*     */       
/*  77 */       IntByReference $$5 = new IntByReference();
/*  78 */       Pointer $$6 = queryVersionValue((Pointer)memory, "\\VarFileInfo\\Translation", $$5);
/*  79 */       int[] $$7 = $$6.getIntArray(0L, $$5.getValue() / 4);
/*     */       
/*  81 */       OptionalInt $$8 = findLangAndCodepage($$7);
/*  82 */       if ($$8.isEmpty()) {
/*  83 */         return Optional.empty();
/*     */       }
/*     */       
/*  86 */       int $$9 = $$8.getAsInt();
/*  87 */       int $$10 = $$9 & 0xFFFF;
/*  88 */       int $$11 = ($$9 & 0xFFFF0000) >> 16;
/*  89 */       String $$12 = queryVersionString((Pointer)memory, langTableKey("FileDescription", $$10, $$11), $$5);
/*  90 */       String $$13 = queryVersionString((Pointer)memory, langTableKey("CompanyName", $$10, $$11), $$5);
/*  91 */       String $$14 = queryVersionString((Pointer)memory, langTableKey("FileVersion", $$10, $$11), $$5);
/*     */       
/*  93 */       return Optional.of(new NativeModuleVersion($$12, $$14, $$13));
/*  94 */     } catch (Exception $$15) {
/*  95 */       LOGGER.info("Failed to find module info for {}", $$0, $$15);
/*     */       
/*  97 */       return Optional.empty();
/*     */     } 
/*     */   }
/*     */   private static String langTableKey(String $$0, int $$1, int $$2) {
/* 101 */     return String.format(Locale.ROOT, "\\StringFileInfo\\%04x%04x\\%s", new Object[] { Integer.valueOf($$1), Integer.valueOf($$2), $$0 });
/*     */   }
/*     */   
/*     */   private static OptionalInt findLangAndCodepage(int[] $$0) {
/* 105 */     OptionalInt $$1 = OptionalInt.empty();
/* 106 */     for (int $$2 : $$0) {
/* 107 */       if (($$2 & 0xFFFF0000) == 78643200 && (
/* 108 */         $$2 & 0xFFFF) == 1033) {
/* 109 */         return OptionalInt.of($$2);
/*     */       }
/*     */       
/* 112 */       $$1 = OptionalInt.of($$2);
/*     */     } 
/* 114 */     return $$1;
/*     */   }
/*     */   
/*     */   private static Pointer queryVersionValue(Pointer $$0, String $$1, IntByReference $$2) {
/* 118 */     PointerByReference $$3 = new PointerByReference();
/* 119 */     if (!Version.INSTANCE.VerQueryValue($$0, $$1, $$3, $$2)) {
/* 120 */       throw new UnsupportedOperationException("Can't get version value " + $$1);
/*     */     }
/* 122 */     return $$3.getValue();
/*     */   }
/*     */   
/*     */   private static String queryVersionString(Pointer $$0, String $$1, IntByReference $$2) {
/*     */     try {
/* 127 */       Pointer $$3 = queryVersionValue($$0, $$1, $$2);
/*     */       
/* 129 */       byte[] $$4 = $$3.getByteArray(0L, ($$2.getValue() - 1) * 2);
/* 130 */       return new String($$4, StandardCharsets.UTF_16LE);
/* 131 */     } catch (Exception $$5) {
/* 132 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void addCrashSection(CrashReportCategory $$0) {
/* 137 */     $$0.setDetail("Modules", () -> (String)listModules().stream().sorted(Comparator.comparing(())).map(()).collect(Collectors.joining()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NativeModuleVersion
/*     */   {
/*     */     public final String description;
/*     */     
/*     */     public final String version;
/*     */     
/*     */     public final String company;
/*     */ 
/*     */     
/*     */     public NativeModuleVersion(String $$0, String $$1, String $$2) {
/* 152 */       this.description = $$0;
/* 153 */       this.version = $$1;
/* 154 */       this.company = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 159 */       return this.description + ":" + this.description + ":" + this.version;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NativeModuleInfo {
/*     */     public final String name;
/*     */     public final Optional<NativeModuleLister.NativeModuleVersion> version;
/*     */     
/*     */     public NativeModuleInfo(String $$0, Optional<NativeModuleLister.NativeModuleVersion> $$1) {
/* 168 */       this.name = $$0;
/* 169 */       this.version = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 174 */       return this.version.<String>map($$0 -> this.name + ":" + this.name).orElse(this.name);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\NativeModuleLister.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */