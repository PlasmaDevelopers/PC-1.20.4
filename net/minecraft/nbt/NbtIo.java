/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UTFDataFormatException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.DelegateDataOutput;
/*     */ import net.minecraft.util.FastBufferedInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NbtIo
/*     */ {
/*  30 */   private static final OpenOption[] SYNC_OUTPUT_OPTIONS = new OpenOption[] { StandardOpenOption.SYNC, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };
/*     */   
/*     */   public static CompoundTag readCompressed(Path $$0, NbtAccounter $$1) throws IOException {
/*  33 */     InputStream $$2 = Files.newInputStream($$0, new OpenOption[0]); 
/*  34 */     try { CompoundTag compoundTag = readCompressed($$2, $$1);
/*  35 */       if ($$2 != null) $$2.close();  return compoundTag; } catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  39 */      } private static DataInputStream createDecompressorStream(InputStream $$0) throws IOException { return new DataInputStream((InputStream)new FastBufferedInputStream(new GZIPInputStream($$0))); }
/*     */ 
/*     */   
/*     */   private static DataOutputStream createCompressorStream(OutputStream $$0) throws IOException {
/*  43 */     return new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream($$0)));
/*     */   }
/*     */   
/*     */   public static CompoundTag readCompressed(InputStream $$0, NbtAccounter $$1) throws IOException {
/*  47 */     DataInputStream $$2 = createDecompressorStream($$0); 
/*  48 */     try { CompoundTag compoundTag = read($$2, $$1);
/*  49 */       if ($$2 != null) $$2.close();  return compoundTag; } catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  53 */      } public static void parseCompressed(Path $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException { InputStream $$3 = Files.newInputStream($$0, new OpenOption[0]); 
/*  54 */     try { parseCompressed($$3, $$1, $$2);
/*  55 */       if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/*     */         try { $$3.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  59 */      } public static void parseCompressed(InputStream $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException { DataInputStream $$3 = createDecompressorStream($$0); 
/*  60 */     try { parse($$3, $$1, $$2);
/*  61 */       if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/*     */         try { $$3.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  65 */      } public static void writeCompressed(CompoundTag $$0, Path $$1) throws IOException { OutputStream $$2 = Files.newOutputStream($$1, SYNC_OUTPUT_OPTIONS); 
/*  66 */     try { OutputStream $$3 = new BufferedOutputStream($$2); 
/*  67 */       try { writeCompressed($$0, $$3);
/*  68 */         $$3.close(); } catch (Throwable throwable) { try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  72 */      } public static void writeCompressed(CompoundTag $$0, OutputStream $$1) throws IOException { DataOutputStream $$2 = createCompressorStream($$1); 
/*  73 */     try { write($$0, $$2);
/*  74 */       if ($$2 != null) $$2.close();  }
/*     */     catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  79 */      } public static void write(CompoundTag $$0, Path $$1) throws IOException { OutputStream $$2 = Files.newOutputStream($$1, SYNC_OUTPUT_OPTIONS); 
/*  80 */     try { OutputStream $$3 = new BufferedOutputStream($$2); 
/*  81 */       try { DataOutputStream $$4 = new DataOutputStream($$3);
/*     */         
/*  83 */         try { write($$0, $$4);
/*  84 */           $$4.close(); } catch (Throwable throwable) { try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  $$3.close(); } catch (Throwable throwable) { try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if ($$2 != null) $$2.close();  }
/*     */     catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  89 */      } @Nullable public static CompoundTag read(Path $$0) throws IOException { if (!Files.exists($$0, new java.nio.file.LinkOption[0])) {
/*  90 */       return null;
/*     */     }
/*     */     
/*  93 */     InputStream $$1 = Files.newInputStream($$0, new OpenOption[0]); 
/*  94 */     try { DataInputStream $$2 = new DataInputStream($$1);
/*     */       
/*  96 */       try { CompoundTag compoundTag = read($$2, NbtAccounter.unlimitedHeap());
/*  97 */         $$2.close(); if ($$1 != null) $$1.close();  return compoundTag; } catch (Throwable throwable) { try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 101 */      } public static CompoundTag read(DataInput $$0) throws IOException { return read($$0, NbtAccounter.unlimitedHeap()); }
/*     */ 
/*     */   
/*     */   public static CompoundTag read(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 105 */     Tag $$2 = readUnnamedTag($$0, $$1);
/* 106 */     if ($$2 instanceof CompoundTag) {
/* 107 */       return (CompoundTag)$$2;
/*     */     }
/* 109 */     throw new IOException("Root tag must be a named compound tag");
/*     */   }
/*     */   
/*     */   public static void write(CompoundTag $$0, DataOutput $$1) throws IOException {
/* 113 */     writeUnnamedTagWithFallback($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 117 */     TagType<?> $$3 = TagTypes.getType($$0.readByte());
/* 118 */     if ($$3 == EndTag.TYPE) {
/* 119 */       if ($$1.visitRootEntry(EndTag.TYPE) == StreamTagVisitor.ValueResult.CONTINUE) {
/* 120 */         $$1.visitEnd();
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 125 */     switch ($$1.visitRootEntry($$3)) {
/*     */ 
/*     */       
/*     */       case BREAK:
/* 129 */         StringTag.skipString($$0);
/* 130 */         $$3.skip($$0, $$2);
/*     */         break;
/*     */       case CONTINUE:
/* 133 */         StringTag.skipString($$0);
/* 134 */         $$3.parse($$0, $$1, $$2);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Tag readAnyTag(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 140 */     byte $$2 = $$0.readByte();
/* 141 */     if ($$2 == 0) {
/* 142 */       return EndTag.INSTANCE;
/*     */     }
/* 144 */     return readTagSafe($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void writeAnyTag(Tag $$0, DataOutput $$1) throws IOException {
/* 148 */     $$1.writeByte($$0.getId());
/* 149 */     if ($$0.getId() == 0) {
/*     */       return;
/*     */     }
/* 152 */     $$0.write($$1);
/*     */   }
/*     */   
/*     */   public static void writeUnnamedTag(Tag $$0, DataOutput $$1) throws IOException {
/* 156 */     $$1.writeByte($$0.getId());
/* 157 */     if ($$0.getId() == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 162 */     $$1.writeUTF("");
/*     */     
/* 164 */     $$0.write($$1);
/*     */   }
/*     */   
/*     */   public static void writeUnnamedTagWithFallback(Tag $$0, DataOutput $$1) throws IOException {
/* 168 */     writeUnnamedTag($$0, (DataOutput)new StringFallbackDataOutput($$1));
/*     */   }
/*     */   
/*     */   private static Tag readUnnamedTag(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 172 */     byte $$2 = $$0.readByte();
/* 173 */     if ($$2 == 0) {
/* 174 */       return EndTag.INSTANCE;
/*     */     }
/*     */ 
/*     */     
/* 178 */     StringTag.skipString($$0);
/*     */     
/* 180 */     return readTagSafe($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private static Tag readTagSafe(DataInput $$0, NbtAccounter $$1, byte $$2) {
/*     */     try {
/* 185 */       return (Tag)TagTypes.getType($$2).load($$0, $$1);
/* 186 */     } catch (IOException $$3) {
/* 187 */       CrashReport $$4 = CrashReport.forThrowable($$3, "Loading NBT data");
/* 188 */       CrashReportCategory $$5 = $$4.addCategory("NBT Tag");
/* 189 */       $$5.setDetail("Tag type", Byte.valueOf($$2));
/* 190 */       throw new ReportedNbtException($$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class StringFallbackDataOutput extends DelegateDataOutput {
/*     */     public StringFallbackDataOutput(DataOutput $$0) {
/* 196 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeUTF(String $$0) throws IOException {
/*     */       try {
/* 202 */         super.writeUTF($$0);
/* 203 */       } catch (UTFDataFormatException $$1) {
/* 204 */         Util.logAndPauseIfInIde("Failed to write NBT String", $$1);
/*     */         
/* 206 */         super.writeUTF("");
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\NbtIo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */