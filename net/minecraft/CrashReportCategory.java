/*     */ package net.minecraft;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class CrashReportCategory {
/*     */   private final String title;
/*  15 */   private final List<Entry> entries = Lists.newArrayList();
/*  16 */   private StackTraceElement[] stackTrace = new StackTraceElement[0];
/*     */   
/*     */   public CrashReportCategory(String $$0) {
/*  19 */     this.title = $$0;
/*     */   }
/*     */   
/*     */   public static String formatLocation(LevelHeightAccessor $$0, double $$1, double $$2, double $$3) {
/*  23 */     return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", new Object[] { Double.valueOf($$1), Double.valueOf($$2), Double.valueOf($$3), formatLocation($$0, BlockPos.containing($$1, $$2, $$3)) });
/*     */   }
/*     */   
/*     */   public static String formatLocation(LevelHeightAccessor $$0, BlockPos $$1) {
/*  27 */     return formatLocation($$0, $$1.getX(), $$1.getY(), $$1.getZ());
/*     */   }
/*     */   
/*     */   public static String formatLocation(LevelHeightAccessor $$0, int $$1, int $$2, int $$3) {
/*  31 */     StringBuilder $$4 = new StringBuilder();
/*     */     
/*     */     try {
/*  34 */       $$4.append(String.format(Locale.ROOT, "World: (%d,%d,%d)", new Object[] { Integer.valueOf($$1), Integer.valueOf($$2), Integer.valueOf($$3) }));
/*  35 */     } catch (Throwable $$5) {
/*  36 */       $$4.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  39 */     $$4.append(", ");
/*     */     
/*     */     try {
/*  42 */       int $$6 = SectionPos.blockToSectionCoord($$1);
/*  43 */       int $$7 = SectionPos.blockToSectionCoord($$2);
/*  44 */       int $$8 = SectionPos.blockToSectionCoord($$3);
/*  45 */       int $$9 = $$1 & 0xF;
/*  46 */       int $$10 = $$2 & 0xF;
/*  47 */       int $$11 = $$3 & 0xF;
/*  48 */       int $$12 = SectionPos.sectionToBlockCoord($$6);
/*  49 */       int $$13 = $$0.getMinBuildHeight();
/*  50 */       int $$14 = SectionPos.sectionToBlockCoord($$8);
/*  51 */       int $$15 = SectionPos.sectionToBlockCoord($$6 + 1) - 1;
/*  52 */       int $$16 = $$0.getMaxBuildHeight() - 1;
/*  53 */       int $$17 = SectionPos.sectionToBlockCoord($$8 + 1) - 1;
/*  54 */       $$4.append(String.format(Locale.ROOT, "Section: (at %d,%d,%d in %d,%d,%d; chunk contains blocks %d,%d,%d to %d,%d,%d)", new Object[] { Integer.valueOf($$9), Integer.valueOf($$10), Integer.valueOf($$11), Integer.valueOf($$6), Integer.valueOf($$7), Integer.valueOf($$8), Integer.valueOf($$12), Integer.valueOf($$13), Integer.valueOf($$14), Integer.valueOf($$15), Integer.valueOf($$16), Integer.valueOf($$17) }));
/*  55 */     } catch (Throwable $$18) {
/*  56 */       $$4.append("(Error finding chunk loc)");
/*     */     } 
/*     */     
/*  59 */     $$4.append(", ");
/*     */     
/*     */     try {
/*  62 */       int $$19 = $$1 >> 9;
/*  63 */       int $$20 = $$3 >> 9;
/*  64 */       int $$21 = $$19 << 5;
/*  65 */       int $$22 = $$20 << 5;
/*  66 */       int $$23 = ($$19 + 1 << 5) - 1;
/*  67 */       int $$24 = ($$20 + 1 << 5) - 1;
/*  68 */       int $$25 = $$19 << 9;
/*  69 */       int $$26 = $$0.getMinBuildHeight();
/*  70 */       int $$27 = $$20 << 9;
/*  71 */       int $$28 = ($$19 + 1 << 9) - 1;
/*  72 */       int $$29 = $$0.getMaxBuildHeight() - 1;
/*  73 */       int $$30 = ($$20 + 1 << 9) - 1;
/*  74 */       $$4.append(String.format(Locale.ROOT, "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,%d,%d to %d,%d,%d)", new Object[] { Integer.valueOf($$19), Integer.valueOf($$20), Integer.valueOf($$21), Integer.valueOf($$22), Integer.valueOf($$23), Integer.valueOf($$24), Integer.valueOf($$25), Integer.valueOf($$26), Integer.valueOf($$27), Integer.valueOf($$28), Integer.valueOf($$29), Integer.valueOf($$30) }));
/*  75 */     } catch (Throwable $$31) {
/*  76 */       $$4.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  79 */     return $$4.toString();
/*     */   }
/*     */   
/*     */   public CrashReportCategory setDetail(String $$0, CrashReportDetail<String> $$1) {
/*     */     try {
/*  84 */       setDetail($$0, $$1.call());
/*  85 */     } catch (Throwable $$2) {
/*  86 */       setDetailError($$0, $$2);
/*     */     } 
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public CrashReportCategory setDetail(String $$0, Object $$1) {
/*  92 */     this.entries.add(new Entry($$0, $$1));
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public void setDetailError(String $$0, Throwable $$1) {
/*  97 */     setDetail($$0, $$1);
/*     */   }
/*     */   
/*     */   public int fillInStackTrace(int $$0) {
/* 101 */     StackTraceElement[] $$1 = Thread.currentThread().getStackTrace();
/*     */ 
/*     */     
/* 104 */     if ($$1.length <= 0) {
/* 105 */       return 0;
/*     */     }
/*     */     
/* 108 */     this.stackTrace = new StackTraceElement[$$1.length - 3 - $$0];
/* 109 */     System.arraycopy($$1, 3 + $$0, this.stackTrace, 0, this.stackTrace.length);
/* 110 */     return this.stackTrace.length;
/*     */   }
/*     */   
/*     */   public boolean validateStackTrace(StackTraceElement $$0, StackTraceElement $$1) {
/* 114 */     if (this.stackTrace.length == 0 || $$0 == null) {
/* 115 */       return false;
/*     */     }
/*     */     
/* 118 */     StackTraceElement $$2 = this.stackTrace[0];
/*     */ 
/*     */     
/* 121 */     if ($$2.isNativeMethod() != $$0.isNativeMethod() || 
/* 122 */       !$$2.getClassName().equals($$0.getClassName()) || 
/* 123 */       !$$2.getFileName().equals($$0.getFileName()) || 
/* 124 */       !$$2.getMethodName().equals($$0.getMethodName()))
/*     */     {
/* 126 */       return false;
/*     */     }
/*     */     
/* 129 */     if ((($$1 != null) ? true : false) != ((this.stackTrace.length > 1) ? true : false)) {
/* 130 */       return false;
/*     */     }
/* 132 */     if ($$1 != null && !this.stackTrace[1].equals($$1)) {
/* 133 */       return false;
/*     */     }
/*     */     
/* 136 */     this.stackTrace[0] = $$0;
/*     */     
/* 138 */     return true;
/*     */   }
/*     */   
/*     */   public void trimStacktrace(int $$0) {
/* 142 */     StackTraceElement[] $$1 = new StackTraceElement[this.stackTrace.length - $$0];
/* 143 */     System.arraycopy(this.stackTrace, 0, $$1, 0, $$1.length);
/* 144 */     this.stackTrace = $$1;
/*     */   }
/*     */   
/*     */   public void getDetails(StringBuilder $$0) {
/* 148 */     $$0.append("-- ").append(this.title).append(" --\n");
/* 149 */     $$0.append("Details:");
/*     */     
/* 151 */     for (Entry $$1 : this.entries) {
/* 152 */       $$0.append("\n\t");
/* 153 */       $$0.append($$1.getKey());
/* 154 */       $$0.append(": ");
/* 155 */       $$0.append($$1.getValue());
/*     */     } 
/*     */     
/* 158 */     if (this.stackTrace != null && this.stackTrace.length > 0) {
/* 159 */       $$0.append("\nStacktrace:");
/*     */       
/* 161 */       for (StackTraceElement $$2 : this.stackTrace) {
/* 162 */         $$0.append("\n\tat ");
/* 163 */         $$0.append($$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public StackTraceElement[] getStacktrace() {
/* 169 */     return this.stackTrace;
/*     */   }
/*     */   
/*     */   public static void populateBlockDetails(CrashReportCategory $$0, LevelHeightAccessor $$1, BlockPos $$2, @Nullable BlockState $$3) {
/* 173 */     if ($$3 != null) {
/* 174 */       Objects.requireNonNull($$3); $$0.setDetail("Block", $$3::toString);
/*     */     } 
/*     */     
/* 177 */     $$0.setDetail("Block location", () -> formatLocation($$0, $$1));
/*     */   }
/*     */   
/*     */   private static class Entry {
/*     */     private final String key;
/*     */     private final String value;
/*     */     
/*     */     public Entry(String $$0, @Nullable Object $$1) {
/* 185 */       this.key = $$0;
/*     */       
/* 187 */       if ($$1 == null)
/* 188 */       { this.value = "~~NULL~~"; }
/* 189 */       else if ($$1 instanceof Throwable) { Throwable $$2 = (Throwable)$$1;
/* 190 */         this.value = "~~ERROR~~ " + $$2.getClass().getSimpleName() + ": " + $$2.getMessage(); }
/*     */       else
/* 192 */       { this.value = $$1.toString(); }
/*     */     
/*     */     }
/*     */     
/*     */     public String getKey() {
/* 197 */       return this.key;
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 201 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\CrashReportCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */