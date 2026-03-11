/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.StringEscapeUtils;
/*    */ 
/*    */ public class CsvOutput
/*    */ {
/*    */   private static final String LINE_SEPARATOR = "\r\n";
/*    */   private static final String FIELD_SEPARATOR = ",";
/*    */   private final Writer output;
/*    */   private final int columnCount;
/*    */   
/*    */   CsvOutput(Writer $$0, List<String> $$1) throws IOException {
/* 20 */     this.output = $$0;
/* 21 */     this.columnCount = $$1.size();
/* 22 */     writeLine($$1.stream());
/*    */   }
/*    */   
/*    */   public static Builder builder() {
/* 26 */     return new Builder();
/*    */   }
/*    */   
/*    */   public void writeRow(Object... $$0) throws IOException {
/* 30 */     if ($$0.length != this.columnCount) {
/* 31 */       throw new IllegalArgumentException("Invalid number of columns, expected " + this.columnCount + ", but got " + $$0.length);
/*    */     }
/*    */     
/* 34 */     writeLine(Stream.of($$0));
/*    */   }
/*    */   
/*    */   private void writeLine(Stream<?> $$0) throws IOException {
/* 38 */     this.output.write((String)$$0.<CharSequence>map(CsvOutput::getStringValue).collect(Collectors.joining(",")) + "\r\n");
/*    */   }
/*    */   
/*    */   private static String getStringValue(@Nullable Object $$0) {
/* 42 */     return StringEscapeUtils.escapeCsv(($$0 != null) ? $$0.toString() : "[null]");
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 46 */     private final List<String> headers = Lists.newArrayList();
/*    */     
/*    */     public Builder addColumn(String $$0) {
/* 49 */       this.headers.add($$0);
/* 50 */       return this;
/*    */     }
/*    */     
/*    */     public CsvOutput build(Writer $$0) throws IOException {
/* 54 */       return new CsvOutput($$0, this.headers);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\CsvOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */