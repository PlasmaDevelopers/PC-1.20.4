/*     */ package net.minecraft;
/*     */ 
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.InvalidPathException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ 
/*     */ public class FileUtil {
/*  20 */   private static final Pattern COPY_COUNTER_PATTERN = Pattern.compile("(<name>.*) \\((<count>\\d*)\\)", 66);
/*     */   
/*     */   private static final int MAX_FILE_NAME = 255;
/*  23 */   private static final Pattern RESERVED_WINDOWS_FILENAMES = Pattern.compile(".*\\.|(?:COM|CLOCK\\$|CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\..*)?", 2);
/*     */   
/*  25 */   private static final Pattern STRICT_PATH_SEGMENT_CHECK = Pattern.compile("[-._a-z0-9]+");
/*     */   
/*     */   public static String findAvailableName(Path $$0, String $$1, String $$2) throws IOException {
/*  28 */     for (char $$3 : SharedConstants.ILLEGAL_FILE_CHARACTERS) {
/*  29 */       $$1 = $$1.replace($$3, '_');
/*     */     }
/*     */     
/*  32 */     $$1 = $$1.replaceAll("[./\"]", "_");
/*     */     
/*  34 */     if (RESERVED_WINDOWS_FILENAMES.matcher($$1).matches()) {
/*  35 */       $$1 = "_" + $$1 + "_";
/*     */     }
/*     */     
/*  38 */     Matcher $$4 = COPY_COUNTER_PATTERN.matcher($$1);
/*  39 */     int $$5 = 0;
/*  40 */     if ($$4.matches()) {
/*  41 */       $$1 = $$4.group("name");
/*  42 */       $$5 = Integer.parseInt($$4.group("count"));
/*     */     } 
/*  44 */     if ($$1.length() > 255 - $$2.length()) {
/*  45 */       $$1 = $$1.substring(0, 255 - $$2.length());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*  51 */       String $$6 = $$1;
/*  52 */       if ($$5 != 0) {
/*  53 */         String $$7 = " (" + $$5 + ")";
/*  54 */         int $$8 = 255 - $$7.length();
/*  55 */         if ($$6.length() > $$8) {
/*  56 */           $$6 = $$6.substring(0, $$8);
/*     */         }
/*  58 */         $$6 = $$6 + $$6;
/*     */       } 
/*     */       
/*  61 */       $$6 = $$6 + $$6;
/*     */       
/*  63 */       Path $$9 = $$0.resolve($$6);
/*     */       try {
/*  65 */         Path $$10 = Files.createDirectory($$9, (FileAttribute<?>[])new FileAttribute[0]);
/*  66 */         Files.deleteIfExists($$10);
/*  67 */         return $$0.relativize($$10).toString();
/*  68 */       } catch (FileAlreadyExistsException $$11) {
/*  69 */         $$5++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isPathNormalized(Path $$0) {
/*  75 */     Path $$1 = $$0.normalize();
/*  76 */     return $$1.equals($$0);
/*     */   }
/*     */   
/*     */   public static boolean isPathPortable(Path $$0) {
/*  80 */     for (Path $$1 : $$0) {
/*  81 */       if (RESERVED_WINDOWS_FILENAMES.matcher($$1.toString()).matches()) {
/*  82 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  86 */     return true;
/*     */   }
/*     */   
/*     */   public static Path createPathToResource(Path $$0, String $$1, String $$2) {
/*  90 */     String $$3 = $$1 + $$1;
/*  91 */     Path $$4 = Paths.get($$3, new String[0]);
/*     */     
/*  93 */     if ($$4.endsWith($$2)) {
/*  94 */       throw new InvalidPathException($$3, "empty resource name");
/*     */     }
/*     */     
/*  97 */     return $$0.resolve($$4);
/*     */   }
/*     */   
/*     */   public static String getFullResourcePath(String $$0) {
/* 101 */     return FilenameUtils.getFullPath($$0).replace(File.separator, "/");
/*     */   }
/*     */   
/*     */   public static String normalizeResourcePath(String $$0) {
/* 105 */     return FilenameUtils.normalize($$0).replace(File.separator, "/");
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
/*     */   public static DataResult<List<String>> decomposePath(String $$0) {
/* 117 */     int $$1 = $$0.indexOf('/');
/* 118 */     if ($$1 == -1) {
/* 119 */       switch ($$0) { case "": case ".": case "..":  }  return 
/*     */ 
/*     */         
/* 122 */         !isValidStrictPathSegment($$0) ? 
/* 123 */         DataResult.error(() -> "Invalid path '" + $$0 + "'") : 
/*     */         
/* 125 */         DataResult.success(List.of($$0));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     List<String> $$2 = new ArrayList<>();
/*     */     
/* 132 */     int $$3 = 0;
/* 133 */     boolean $$4 = false;
/*     */     while (true) {
/* 135 */       String $$5 = $$0.substring($$3, $$1);
/* 136 */       switch ($$5) {
/*     */         case "":
/*     */         case ".":
/*     */         case "..":
/* 140 */           return DataResult.error(() -> "Invalid segment '" + $$0 + "' in path '" + $$1 + "'");
/*     */       } 
/* 142 */       if (!isValidStrictPathSegment($$5)) {
/* 143 */         return DataResult.error(() -> "Invalid segment '" + $$0 + "' in path '" + $$1 + "'");
/*     */       }
/* 145 */       $$2.add($$5);
/*     */ 
/*     */ 
/*     */       
/* 149 */       if ($$4) {
/* 150 */         return DataResult.success($$2);
/*     */       }
/* 152 */       $$3 = $$1 + 1;
/* 153 */       $$1 = $$0.indexOf('/', $$3);
/* 154 */       if ($$1 == -1) {
/* 155 */         $$1 = $$0.length();
/* 156 */         $$4 = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Path resolvePath(Path $$0, List<String> $$1) {
/* 163 */     int $$2 = $$1.size();
/* 164 */     switch ($$2) { case 0:
/*     */       
/*     */       case 1:
/*     */        }
/* 168 */      String[] $$3 = new String[$$2 - 1];
/* 169 */     for (int $$4 = 1; $$4 < $$2; $$4++) {
/* 170 */       $$3[$$4 - 1] = $$1.get($$4);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidStrictPathSegment(String $$0) {
/* 178 */     return STRICT_PATH_SEGMENT_CHECK.matcher($$0).matches();
/*     */   }
/*     */   
/*     */   public static void validatePath(String... $$0) {
/* 182 */     if ($$0.length == 0) {
/* 183 */       throw new IllegalArgumentException("Path must have at least one element");
/*     */     }
/* 185 */     for (String $$1 : $$0) {
/* 186 */       if ($$1.equals("..") || $$1.equals(".") || !isValidStrictPathSegment($$1)) {
/* 187 */         throw new IllegalArgumentException("Illegal segment " + $$1 + " in path " + Arrays.toString($$0));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void createDirectoriesSafe(Path $$0) throws IOException {
/* 194 */     Files.createDirectories(Files.exists($$0, new java.nio.file.LinkOption[0]) ? $$0.toRealPath(new java.nio.file.LinkOption[0]) : $$0, (FileAttribute<?>[])new FileAttribute[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\FileUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */