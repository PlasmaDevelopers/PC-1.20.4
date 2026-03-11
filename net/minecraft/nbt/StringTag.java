/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringTag
/*     */   implements Tag
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 36;
/*     */   
/*  16 */   public static final TagType<StringTag> TYPE = new TagType.VariableSize<StringTag>()
/*     */     {
/*     */       public StringTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  19 */         return StringTag.valueOf(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  24 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static String readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  28 */         $$1.accountBytes(36L);
/*     */ 
/*     */         
/*  31 */         String $$2 = $$0.readUTF();
/*  32 */         $$1.accountBytes(2L, $$2.length());
/*  33 */         return $$2;
/*     */       }
/*     */ 
/*     */       
/*     */       public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  38 */         StringTag.skipString($$0);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  43 */         return "STRING";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  48 */         return "TAG_String";
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isValue() {
/*  53 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   public static void skipString(DataInput $$0) throws IOException {
/*  58 */     $$0.skipBytes($$0.readUnsignedShort());
/*     */   }
/*     */   
/*  61 */   private static final StringTag EMPTY = new StringTag("");
/*     */   
/*     */   private static final char DOUBLE_QUOTE = '"';
/*     */   private static final char SINGLE_QUOTE = '\'';
/*     */   private static final char ESCAPE = '\\';
/*     */   private static final char NOT_SET = '\000';
/*     */   private final String data;
/*     */   
/*     */   private StringTag(String $$0) {
/*  70 */     Objects.requireNonNull($$0, "Null string not allowed");
/*  71 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public static StringTag valueOf(String $$0) {
/*  75 */     if ($$0.isEmpty()) {
/*  76 */       return EMPTY;
/*     */     }
/*  78 */     return new StringTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  83 */     $$0.writeUTF(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  88 */     return 36 + 2 * this.data.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  93 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<StringTag> getType() {
/*  98 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return super.getAsString();
/*     */   }
/*     */ 
/*     */   
/*     */   public StringTag copy() {
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 113 */     if (this == $$0) {
/* 114 */       return true;
/*     */     }
/*     */     
/* 117 */     return ($$0 instanceof StringTag && Objects.equals(this.data, ((StringTag)$$0).data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return this.data.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAsString() {
/* 127 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 132 */     $$0.visitString(this);
/*     */   }
/*     */   
/*     */   public static String quoteAndEscape(String $$0) {
/* 136 */     StringBuilder $$1 = new StringBuilder(" ");
/* 137 */     char $$2 = Character.MIN_VALUE;
/* 138 */     for (int $$3 = 0; $$3 < $$0.length(); $$3++) {
/* 139 */       char $$4 = $$0.charAt($$3);
/* 140 */       if ($$4 == '\\') {
/* 141 */         $$1.append('\\');
/* 142 */       } else if ($$4 == '"' || $$4 == '\'') {
/* 143 */         if ($$2 == '\000') {
/* 144 */           $$2 = ($$4 == '"') ? '\'' : '"';
/*     */         }
/* 146 */         if ($$2 == $$4) {
/* 147 */           $$1.append('\\');
/*     */         }
/*     */       } 
/* 150 */       $$1.append($$4);
/*     */     } 
/* 152 */     if ($$2 == '\000') {
/* 153 */       $$2 = '"';
/*     */     }
/*     */     
/* 156 */     $$1.setCharAt(0, $$2);
/* 157 */     $$1.append($$2);
/* 158 */     return $$1.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 163 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\StringTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */