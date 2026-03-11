/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
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
/*     */ public class LongArrayTag
/*     */   extends CollectionTag<LongTag>
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 24;
/*     */   
/*  21 */   public static final TagType<LongArrayTag> TYPE = new TagType.VariableSize<LongArrayTag>()
/*     */     {
/*     */       public LongArrayTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  24 */         return new LongArrayTag(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  29 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static long[] readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  33 */         $$1.accountBytes(24L);
/*  34 */         int $$2 = $$0.readInt();
/*  35 */         $$1.accountBytes(8L, $$2);
/*  36 */         long[] $$3 = new long[$$2];
/*  37 */         for (int $$4 = 0; $$4 < $$2; $$4++) {
/*  38 */           $$3[$$4] = $$0.readLong();
/*     */         }
/*  40 */         return $$3;
/*     */       }
/*     */ 
/*     */       
/*     */       public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  45 */         $$0.skipBytes($$0.readInt() * 8);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  50 */         return "LONG[]";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  55 */         return "TAG_Long_Array";
/*     */       }
/*     */     };
/*     */   
/*     */   private long[] data;
/*     */   
/*     */   public LongArrayTag(long[] $$0) {
/*  62 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public LongArrayTag(LongSet $$0) {
/*  66 */     this.data = $$0.toLongArray();
/*     */   }
/*     */   
/*     */   public LongArrayTag(List<Long> $$0) {
/*  70 */     this(toArray($$0));
/*     */   }
/*     */   
/*     */   private static long[] toArray(List<Long> $$0) {
/*  74 */     long[] $$1 = new long[$$0.size()];
/*  75 */     for (int $$2 = 0; $$2 < $$0.size(); $$2++) {
/*  76 */       Long $$3 = $$0.get($$2);
/*  77 */       $$1[$$2] = ($$3 == null) ? 0L : $$3.longValue();
/*     */     } 
/*     */     
/*  80 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  85 */     $$0.writeInt(this.data.length);
/*  86 */     for (long $$1 : this.data) {
/*  87 */       $$0.writeLong($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  93 */     return 24 + 8 * this.data.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  98 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<LongArrayTag> getType() {
/* 103 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return getAsString();
/*     */   }
/*     */ 
/*     */   
/*     */   public LongArrayTag copy() {
/* 113 */     long[] $$0 = new long[this.data.length];
/* 114 */     System.arraycopy(this.data, 0, $$0, 0, this.data.length);
/* 115 */     return new LongArrayTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 120 */     if (this == $$0) {
/* 121 */       return true;
/*     */     }
/*     */     
/* 124 */     return ($$0 instanceof LongArrayTag && Arrays.equals(this.data, ((LongArrayTag)$$0).data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     return Arrays.hashCode(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 134 */     $$0.visitLongArray(this);
/*     */   }
/*     */   
/*     */   public long[] getAsLongArray() {
/* 138 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 143 */     return this.data.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public LongTag get(int $$0) {
/* 148 */     return LongTag.valueOf(this.data[$$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public LongTag set(int $$0, LongTag $$1) {
/* 153 */     long $$2 = this.data[$$0];
/* 154 */     this.data[$$0] = $$1.getAsLong();
/* 155 */     return LongTag.valueOf($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int $$0, LongTag $$1) {
/* 160 */     this.data = ArrayUtils.add(this.data, $$0, $$1.getAsLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTag(int $$0, Tag $$1) {
/* 165 */     if ($$1 instanceof NumericTag) {
/* 166 */       this.data[$$0] = ((NumericTag)$$1).getAsLong();
/* 167 */       return true;
/*     */     } 
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addTag(int $$0, Tag $$1) {
/* 174 */     if ($$1 instanceof NumericTag) {
/* 175 */       this.data = ArrayUtils.add(this.data, $$0, ((NumericTag)$$1).getAsLong());
/* 176 */       return true;
/*     */     } 
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public LongTag remove(int $$0) {
/* 183 */     long $$1 = this.data[$$0];
/* 184 */     this.data = ArrayUtils.remove(this.data, $$0);
/* 185 */     return LongTag.valueOf($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getElementType() {
/* 190 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 195 */     this.data = new long[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 200 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\LongArrayTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */