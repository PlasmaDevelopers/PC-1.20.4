/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.Util;
/*     */ 
/*     */ public class SnbtPrinterTagVisitor implements TagVisitor {
/*     */   static {
/*  21 */     KEY_ORDER = (Map<String, List<String>>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put("{}", Lists.newArrayList((Object[])new String[] { "DataVersion", "author", "size", "data", "entities", "palette", "palettes" }));
/*     */           $$0.put("{}.data.[].{}", Lists.newArrayList((Object[])new String[] { "pos", "state", "nbt" }));
/*     */           $$0.put("{}.entities.[].{}", Lists.newArrayList((Object[])new String[] { "blockPos", "pos" }));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Map<String, List<String>> KEY_ORDER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private static final Set<String> NO_INDENTATION = Sets.newHashSet((Object[])new String[] { "{}.size.[]", "{}.data.[].{}", "{}.palette.[].{}", "{}.entities.[].{}" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
/*     */   
/*  51 */   private static final String NAME_VALUE_SEPARATOR = String.valueOf(':');
/*  52 */   private static final String ELEMENT_SEPARATOR = String.valueOf(',');
/*     */   
/*     */   private static final String LIST_OPEN = "[";
/*     */   
/*     */   private static final String LIST_CLOSE = "]";
/*     */   private static final String LIST_TYPE_SEPARATOR = ";";
/*     */   private static final String ELEMENT_SPACING = " ";
/*     */   private static final String STRUCT_OPEN = "{";
/*     */   private static final String STRUCT_CLOSE = "}";
/*     */   private static final String NEWLINE = "\n";
/*     */   private final String indentation;
/*     */   private final int depth;
/*     */   private final List<String> path;
/*  65 */   private String result = "";
/*     */   
/*     */   public SnbtPrinterTagVisitor() {
/*  68 */     this("    ", 0, Lists.newArrayList());
/*     */   }
/*     */   
/*     */   public SnbtPrinterTagVisitor(String $$0, int $$1, List<String> $$2) {
/*  72 */     this.indentation = $$0;
/*  73 */     this.depth = $$1;
/*  74 */     this.path = $$2;
/*     */   }
/*     */   
/*     */   public String visit(Tag $$0) {
/*  78 */     $$0.accept(this);
/*     */     
/*  80 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitString(StringTag $$0) {
/*  85 */     this.result = StringTag.quoteAndEscape($$0.getAsString());
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitByte(ByteTag $$0) {
/*  90 */     this.result = "" + $$0.getAsNumber() + "b";
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitShort(ShortTag $$0) {
/*  95 */     this.result = "" + $$0.getAsNumber() + "s";
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInt(IntTag $$0) {
/* 100 */     this.result = String.valueOf($$0.getAsNumber());
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLong(LongTag $$0) {
/* 105 */     this.result = "" + $$0.getAsNumber() + "L";
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitFloat(FloatTag $$0) {
/* 110 */     this.result = "" + $$0.getAsFloat() + "f";
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDouble(DoubleTag $$0) {
/* 115 */     this.result = "" + $$0.getAsDouble() + "d";
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitByteArray(ByteArrayTag $$0) {
/* 120 */     StringBuilder $$1 = (new StringBuilder("[")).append("B").append(";");
/*     */     
/* 122 */     byte[] $$2 = $$0.getAsByteArray();
/* 123 */     for (int $$3 = 0; $$3 < $$2.length; $$3++) {
/* 124 */       $$1.append(" ").append($$2[$$3]).append("B");
/*     */       
/* 126 */       if ($$3 != $$2.length - 1) {
/* 127 */         $$1.append(ELEMENT_SEPARATOR);
/*     */       }
/*     */     } 
/*     */     
/* 131 */     $$1.append("]");
/* 132 */     this.result = $$1.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntArray(IntArrayTag $$0) {
/* 137 */     StringBuilder $$1 = (new StringBuilder("[")).append("I").append(";");
/*     */     
/* 139 */     int[] $$2 = $$0.getAsIntArray();
/* 140 */     for (int $$3 = 0; $$3 < $$2.length; $$3++) {
/* 141 */       $$1.append(" ").append($$2[$$3]);
/* 142 */       if ($$3 != $$2.length - 1) {
/* 143 */         $$1.append(ELEMENT_SEPARATOR);
/*     */       }
/*     */     } 
/*     */     
/* 147 */     $$1.append("]");
/* 148 */     this.result = $$1.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLongArray(LongArrayTag $$0) {
/* 153 */     String $$1 = "L";
/* 154 */     StringBuilder $$2 = (new StringBuilder("[")).append("L").append(";");
/*     */     
/* 156 */     long[] $$3 = $$0.getAsLongArray();
/* 157 */     for (int $$4 = 0; $$4 < $$3.length; $$4++) {
/* 158 */       $$2.append(" ").append($$3[$$4]).append("L");
/* 159 */       if ($$4 != $$3.length - 1) {
/* 160 */         $$2.append(ELEMENT_SEPARATOR);
/*     */       }
/*     */     } 
/*     */     
/* 164 */     $$2.append("]");
/* 165 */     this.result = $$2.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitList(ListTag $$0) {
/* 170 */     if ($$0.isEmpty()) {
/* 171 */       this.result = "[]";
/*     */       
/*     */       return;
/*     */     } 
/* 175 */     StringBuilder $$1 = new StringBuilder("[");
/* 176 */     pushPath("[]");
/*     */     
/* 178 */     String $$2 = NO_INDENTATION.contains(pathString()) ? "" : this.indentation;
/* 179 */     if (!$$2.isEmpty()) {
/* 180 */       $$1.append("\n");
/*     */     }
/*     */     
/* 183 */     for (int $$3 = 0; $$3 < $$0.size(); $$3++) {
/* 184 */       $$1.append(Strings.repeat($$2, this.depth + 1));
/* 185 */       $$1.append((new SnbtPrinterTagVisitor($$2, this.depth + 1, this.path)).visit($$0.get($$3)));
/* 186 */       if ($$3 != $$0.size() - 1) {
/* 187 */         $$1.append(ELEMENT_SEPARATOR).append($$2.isEmpty() ? " " : "\n");
/*     */       }
/*     */     } 
/* 190 */     if (!$$2.isEmpty()) {
/* 191 */       $$1.append("\n").append(Strings.repeat($$2, this.depth));
/*     */     }
/* 193 */     $$1.append("]");
/*     */     
/* 195 */     this.result = $$1.toString();
/* 196 */     popPath();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitCompound(CompoundTag $$0) {
/* 201 */     if ($$0.isEmpty()) {
/* 202 */       this.result = "{}";
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 207 */     StringBuilder $$1 = new StringBuilder("{");
/* 208 */     pushPath("{}");
/*     */     
/* 210 */     String $$2 = NO_INDENTATION.contains(pathString()) ? "" : this.indentation;
/* 211 */     if (!$$2.isEmpty()) {
/* 212 */       $$1.append("\n");
/*     */     }
/*     */     
/* 215 */     Collection<String> $$3 = getKeys($$0);
/* 216 */     for (Iterator<String> $$4 = $$3.iterator(); $$4.hasNext(); ) {
/* 217 */       String $$5 = $$4.next();
/* 218 */       Tag $$6 = $$0.get($$5);
/*     */       
/* 220 */       pushPath($$5);
/* 221 */       $$1.append(Strings.repeat($$2, this.depth + 1))
/* 222 */         .append(handleEscapePretty($$5))
/* 223 */         .append(NAME_VALUE_SEPARATOR)
/* 224 */         .append(" ")
/* 225 */         .append((new SnbtPrinterTagVisitor($$2, this.depth + 1, this.path)).visit($$6));
/*     */       
/* 227 */       popPath();
/*     */       
/* 229 */       if ($$4.hasNext()) {
/* 230 */         $$1.append(ELEMENT_SEPARATOR).append($$2.isEmpty() ? " " : "\n");
/*     */       }
/*     */     } 
/* 233 */     if (!$$2.isEmpty()) {
/* 234 */       $$1.append("\n").append(Strings.repeat($$2, this.depth));
/*     */     }
/* 236 */     $$1.append("}");
/* 237 */     this.result = $$1.toString();
/* 238 */     popPath();
/*     */   }
/*     */   
/*     */   private void popPath() {
/* 242 */     this.path.remove(this.path.size() - 1);
/*     */   }
/*     */   
/*     */   private void pushPath(String $$0) {
/* 246 */     this.path.add($$0);
/*     */   }
/*     */   
/*     */   protected List<String> getKeys(CompoundTag $$0) {
/* 250 */     Set<String> $$1 = Sets.newHashSet($$0.getAllKeys());
/* 251 */     List<String> $$2 = Lists.newArrayList();
/*     */     
/* 253 */     List<String> $$3 = KEY_ORDER.get(pathString());
/* 254 */     if ($$3 != null) {
/* 255 */       for (String $$4 : $$3) {
/* 256 */         if ($$1.remove($$4)) {
/* 257 */           $$2.add($$4);
/*     */         }
/*     */       } 
/* 260 */       if (!$$1.isEmpty()) {
/* 261 */         Objects.requireNonNull($$2); $$1.stream().sorted().forEach($$2::add);
/*     */       } 
/*     */     } else {
/* 264 */       $$2.addAll($$1);
/* 265 */       Collections.sort($$2);
/*     */     } 
/* 267 */     return $$2;
/*     */   }
/*     */   
/*     */   public String pathString() {
/* 271 */     return String.join(".", (Iterable)this.path);
/*     */   }
/*     */   
/*     */   protected static String handleEscapePretty(String $$0) {
/* 275 */     if (SIMPLE_VALUE.matcher($$0).matches()) {
/* 276 */       return $$0;
/*     */     }
/*     */     
/* 279 */     return StringTag.quoteAndEscape($$0);
/*     */   }
/*     */   
/*     */   public void visitEnd(EndTag $$0) {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\SnbtPrinterTagVisitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */