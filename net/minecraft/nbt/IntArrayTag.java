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
/*     */ public class IntArrayTag
/*     */   extends CollectionTag<IntTag>
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 24;
/*     */   
/*  20 */   public static final TagType<IntArrayTag> TYPE = new TagType.VariableSize<IntArrayTag>()
/*     */     {
/*     */       public IntArrayTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  23 */         return new IntArrayTag(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  28 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static int[] readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  32 */         $$1.accountBytes(24L);
/*     */         
/*  34 */         int $$2 = $$0.readInt();
/*  35 */         $$1.accountBytes(4L, $$2);
/*  36 */         int[] $$3 = new int[$$2];
/*  37 */         for (int $$4 = 0; $$4 < $$2; $$4++) {
/*  38 */           $$3[$$4] = $$0.readInt();
/*     */         }
/*  40 */         return $$3;
/*     */       }
/*     */ 
/*     */       
/*     */       public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  45 */         $$0.skipBytes($$0.readInt() * 4);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  50 */         return "INT[]";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  55 */         return "TAG_Int_Array";
/*     */       }
/*     */     };
/*     */   
/*     */   private int[] data;
/*     */   
/*     */   public IntArrayTag(int[] $$0) {
/*  62 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public IntArrayTag(List<Integer> $$0) {
/*  66 */     this(toArray($$0));
/*     */   }
/*     */   
/*     */   private static int[] toArray(List<Integer> $$0) {
/*  70 */     int[] $$1 = new int[$$0.size()];
/*  71 */     for (int $$2 = 0; $$2 < $$0.size(); $$2++) {
/*  72 */       Integer $$3 = $$0.get($$2);
/*  73 */       $$1[$$2] = ($$3 == null) ? 0 : $$3.intValue();
/*     */     } 
/*     */     
/*  76 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  81 */     $$0.writeInt(this.data.length);
/*  82 */     for (int $$1 : this.data) {
/*  83 */       $$0.writeInt($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  89 */     return 24 + 4 * this.data.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  94 */     return 11;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<IntArrayTag> getType() {
/*  99 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return getAsString();
/*     */   }
/*     */ 
/*     */   
/*     */   public IntArrayTag copy() {
/* 109 */     int[] $$0 = new int[this.data.length];
/* 110 */     System.arraycopy(this.data, 0, $$0, 0, this.data.length);
/* 111 */     return new IntArrayTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 116 */     if (this == $$0) {
/* 117 */       return true;
/*     */     }
/*     */     
/* 120 */     return ($$0 instanceof IntArrayTag && Arrays.equals(this.data, ((IntArrayTag)$$0).data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return Arrays.hashCode(this.data);
/*     */   }
/*     */   
/*     */   public int[] getAsIntArray() {
/* 129 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 134 */     $$0.visitIntArray(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 139 */     return this.data.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntTag get(int $$0) {
/* 144 */     return IntTag.valueOf(this.data[$$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntTag set(int $$0, IntTag $$1) {
/* 149 */     int $$2 = this.data[$$0];
/* 150 */     this.data[$$0] = $$1.getAsInt();
/* 151 */     return IntTag.valueOf($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int $$0, IntTag $$1) {
/* 156 */     this.data = ArrayUtils.add(this.data, $$0, $$1.getAsInt());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTag(int $$0, Tag $$1) {
/* 161 */     if ($$1 instanceof NumericTag) {
/* 162 */       this.data[$$0] = ((NumericTag)$$1).getAsInt();
/* 163 */       return true;
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addTag(int $$0, Tag $$1) {
/* 170 */     if ($$1 instanceof NumericTag) {
/* 171 */       this.data = ArrayUtils.add(this.data, $$0, ((NumericTag)$$1).getAsInt());
/* 172 */       return true;
/*     */     } 
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntTag remove(int $$0) {
/* 179 */     int $$1 = this.data[$$0];
/* 180 */     this.data = ArrayUtils.remove(this.data, $$0);
/* 181 */     return IntTag.valueOf($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getElementType() {
/* 186 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 191 */     this.data = new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 196 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\IntArrayTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */