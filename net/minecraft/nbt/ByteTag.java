/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteTag
/*     */   extends NumericTag
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 9;
/*     */   
/*     */   private static class Cache
/*     */   {
/*  16 */     static final ByteTag[] cache = new ByteTag[256];
/*     */     
/*     */     static {
/*  19 */       for (int $$0 = 0; $$0 < cache.length; $$0++) {
/*  20 */         cache[$$0] = new ByteTag((byte)($$0 - 128));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*  25 */   public static final TagType<ByteTag> TYPE = new TagType.StaticSize<ByteTag>()
/*     */     {
/*     */       public ByteTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  28 */         return ByteTag.valueOf(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  33 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static byte readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  37 */         $$1.accountBytes(9L);
/*  38 */         return $$0.readByte();
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/*  43 */         return 1;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  48 */         return "BYTE";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  53 */         return "TAG_Byte";
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isValue() {
/*  58 */         return true;
/*     */       }
/*     */     };
/*     */   
/*  62 */   public static final ByteTag ZERO = valueOf((byte)0);
/*  63 */   public static final ByteTag ONE = valueOf((byte)1);
/*     */   
/*     */   private final byte data;
/*     */   
/*     */   ByteTag(byte $$0) {
/*  68 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public static ByteTag valueOf(byte $$0) {
/*  72 */     return Cache.cache[128 + $$0];
/*     */   }
/*     */   
/*     */   public static ByteTag valueOf(boolean $$0) {
/*  76 */     return $$0 ? ONE : ZERO;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  81 */     $$0.writeByte(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  86 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  91 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<ByteTag> getType() {
/*  96 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteTag copy() {
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 106 */     if (this == $$0) {
/* 107 */       return true;
/*     */     }
/*     */     
/* 110 */     return ($$0 instanceof ByteTag && this.data == ((ByteTag)$$0).data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 115 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 120 */     $$0.visitByte(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getAsLong() {
/* 125 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAsInt() {
/* 130 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getAsShort() {
/* 135 */     return (short)this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getAsByte() {
/* 140 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAsDouble() {
/* 145 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAsFloat() {
/* 150 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public Number getAsNumber() {
/* 155 */     return Byte.valueOf(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 160 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ByteTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */