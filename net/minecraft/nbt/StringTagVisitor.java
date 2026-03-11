/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class StringTagVisitor
/*     */   implements TagVisitor {
/*  10 */   private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
/*     */   
/*  12 */   private final StringBuilder builder = new StringBuilder();
/*     */   
/*     */   public String visit(Tag $$0) {
/*  15 */     $$0.accept(this);
/*     */     
/*  17 */     return this.builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitString(StringTag $$0) {
/*  22 */     this.builder.append(StringTag.quoteAndEscape($$0.getAsString()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitByte(ByteTag $$0) {
/*  27 */     this.builder.append($$0.getAsNumber()).append('b');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitShort(ShortTag $$0) {
/*  32 */     this.builder.append($$0.getAsNumber()).append('s');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInt(IntTag $$0) {
/*  37 */     this.builder.append($$0.getAsNumber());
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLong(LongTag $$0) {
/*  42 */     this.builder.append($$0.getAsNumber()).append('L');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitFloat(FloatTag $$0) {
/*  47 */     this.builder.append($$0.getAsFloat()).append('f');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDouble(DoubleTag $$0) {
/*  52 */     this.builder.append($$0.getAsDouble()).append('d');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitByteArray(ByteArrayTag $$0) {
/*  57 */     this.builder.append("[B;");
/*  58 */     byte[] $$1 = $$0.getAsByteArray();
/*  59 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/*  60 */       if ($$2 != 0) {
/*  61 */         this.builder.append(',');
/*     */       }
/*  63 */       this.builder.append($$1[$$2]).append('B');
/*     */     } 
/*  65 */     this.builder.append(']');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntArray(IntArrayTag $$0) {
/*  70 */     this.builder.append("[I;");
/*  71 */     int[] $$1 = $$0.getAsIntArray();
/*  72 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/*  73 */       if ($$2 != 0) {
/*  74 */         this.builder.append(',');
/*     */       }
/*  76 */       this.builder.append($$1[$$2]);
/*     */     } 
/*  78 */     this.builder.append(']');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLongArray(LongArrayTag $$0) {
/*  83 */     this.builder.append("[L;");
/*  84 */     long[] $$1 = $$0.getAsLongArray();
/*  85 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/*  86 */       if ($$2 != 0) {
/*  87 */         this.builder.append(',');
/*     */       }
/*  89 */       this.builder.append($$1[$$2]).append('L');
/*     */     } 
/*  91 */     this.builder.append(']');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitList(ListTag $$0) {
/*  96 */     this.builder.append('[');
/*  97 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/*  98 */       if ($$1 != 0) {
/*  99 */         this.builder.append(',');
/*     */       }
/* 101 */       this.builder.append((new StringTagVisitor()).visit($$0.get($$1)));
/*     */     } 
/* 103 */     this.builder.append(']');
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitCompound(CompoundTag $$0) {
/* 108 */     this.builder.append('{');
/*     */     
/* 110 */     List<String> $$1 = Lists.newArrayList($$0.getAllKeys());
/* 111 */     Collections.sort($$1);
/* 112 */     for (String $$2 : $$1) {
/* 113 */       if (this.builder.length() != 1) {
/* 114 */         this.builder.append(',');
/*     */       }
/* 116 */       this.builder.append(handleEscape($$2)).append(':').append((new StringTagVisitor()).visit($$0.get($$2)));
/*     */     } 
/*     */     
/* 119 */     this.builder.append('}');
/*     */   }
/*     */   
/*     */   protected static String handleEscape(String $$0) {
/* 123 */     if (SIMPLE_VALUE.matcher($$0).matches()) {
/* 124 */       return $$0;
/*     */     }
/*     */     
/* 127 */     return StringTag.quoteAndEscape($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd(EndTag $$0) {
/* 132 */     this.builder.append("END");
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\StringTagVisitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */