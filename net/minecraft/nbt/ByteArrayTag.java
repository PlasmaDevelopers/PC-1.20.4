/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteArrayTag
/*     */   extends CollectionTag<ByteTag>
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 24;
/*     */   
/*  20 */   public static final TagType<ByteArrayTag> TYPE = new TagType.VariableSize<ByteArrayTag>()
/*     */     {
/*     */       public ByteArrayTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  23 */         return new ByteArrayTag(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  28 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static byte[] readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  32 */         $$1.accountBytes(24L);
/*  33 */         int $$2 = $$0.readInt();
/*  34 */         $$1.accountBytes(1L, $$2);
/*  35 */         byte[] $$3 = new byte[$$2];
/*  36 */         $$0.readFully($$3);
/*  37 */         return $$3;
/*     */       }
/*     */ 
/*     */       
/*     */       public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  42 */         $$0.skipBytes($$0.readInt() * 1);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  47 */         return "BYTE[]";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  52 */         return "TAG_Byte_Array";
/*     */       }
/*     */     };
/*     */   
/*     */   private byte[] data;
/*     */   
/*     */   public ByteArrayTag(byte[] $$0) {
/*  59 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public ByteArrayTag(List<Byte> $$0) {
/*  63 */     this(toArray($$0));
/*     */   }
/*     */   
/*     */   private static byte[] toArray(List<Byte> $$0) {
/*  67 */     byte[] $$1 = new byte[$$0.size()];
/*  68 */     for (int $$2 = 0; $$2 < $$0.size(); $$2++) {
/*  69 */       Byte $$3 = $$0.get($$2);
/*  70 */       $$1[$$2] = ($$3 == null) ? 0 : $$3.byteValue();
/*     */     } 
/*     */     
/*  73 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  78 */     $$0.writeInt(this.data.length);
/*  79 */     $$0.write(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  84 */     return 24 + 1 * this.data.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  89 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<ByteArrayTag> getType() {
/*  94 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  99 */     return getAsString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag copy() {
/* 104 */     byte[] $$0 = new byte[this.data.length];
/* 105 */     System.arraycopy(this.data, 0, $$0, 0, this.data.length);
/* 106 */     return new ByteArrayTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 111 */     if (this == $$0) {
/* 112 */       return true;
/*     */     }
/*     */     
/* 115 */     return ($$0 instanceof ByteArrayTag && Arrays.equals(this.data, ((ByteArrayTag)$$0).data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Arrays.hashCode(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 125 */     $$0.visitByteArray(this);
/*     */   }
/*     */   
/*     */   public byte[] getAsByteArray() {
/* 129 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 134 */     return this.data.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteTag get(int $$0) {
/* 139 */     return ByteTag.valueOf(this.data[$$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteTag set(int $$0, ByteTag $$1) {
/* 144 */     byte $$2 = this.data[$$0];
/* 145 */     this.data[$$0] = $$1.getAsByte();
/* 146 */     return ByteTag.valueOf($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int $$0, ByteTag $$1) {
/* 151 */     this.data = ArrayUtils.add(this.data, $$0, $$1.getAsByte());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTag(int $$0, Tag $$1) {
/* 156 */     if ($$1 instanceof NumericTag) {
/* 157 */       this.data[$$0] = ((NumericTag)$$1).getAsByte();
/* 158 */       return true;
/*     */     } 
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addTag(int $$0, Tag $$1) {
/* 165 */     if ($$1 instanceof NumericTag) {
/* 166 */       this.data = ArrayUtils.add(this.data, $$0, ((NumericTag)$$1).getAsByte());
/* 167 */       return true;
/*     */     } 
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteTag remove(int $$0) {
/* 174 */     byte $$1 = this.data[$$0];
/* 175 */     this.data = ArrayUtils.remove(this.data, $$0);
/* 176 */     return ByteTag.valueOf($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getElementType() {
/* 181 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 186 */     this.data = new byte[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 191 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ByteArrayTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */