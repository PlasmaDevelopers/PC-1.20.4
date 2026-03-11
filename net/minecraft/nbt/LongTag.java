/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongTag
/*     */   extends NumericTag
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 16;
/*     */   
/*     */   private static class Cache
/*     */   {
/*     */     private static final int HIGH = 1024;
/*     */     private static final int LOW = -128;
/*  18 */     static final LongTag[] cache = new LongTag[1153];
/*     */     
/*     */     static {
/*  21 */       for (int $$0 = 0; $$0 < cache.length; $$0++) {
/*  22 */         cache[$$0] = new LongTag((-128 + $$0));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*  27 */   public static final TagType<LongTag> TYPE = new TagType.StaticSize<LongTag>()
/*     */     {
/*     */       public LongTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  30 */         return LongTag.valueOf(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  35 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static long readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  39 */         $$1.accountBytes(16L);
/*  40 */         return $$0.readLong();
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/*  45 */         return 8;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  50 */         return "LONG";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  55 */         return "TAG_Long";
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isValue() {
/*  60 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   private final long data;
/*     */   
/*     */   LongTag(long $$0) {
/*  67 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public static LongTag valueOf(long $$0) {
/*  71 */     if ($$0 >= -128L && $$0 <= 1024L) {
/*  72 */       return Cache.cache[(int)$$0 - -128];
/*     */     }
/*  74 */     return new LongTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  79 */     $$0.writeLong(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  84 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  89 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<LongTag> getType() {
/*  94 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public LongTag copy() {
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 104 */     if (this == $$0) {
/* 105 */       return true;
/*     */     }
/*     */     
/* 108 */     return ($$0 instanceof LongTag && this.data == ((LongTag)$$0).data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return (int)(this.data ^ this.data >>> 32L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 118 */     $$0.visitLong(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getAsLong() {
/* 123 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAsInt() {
/* 128 */     return (int)(this.data & 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getAsShort() {
/* 133 */     return (short)(int)(this.data & 0xFFFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getAsByte() {
/* 138 */     return (byte)(int)(this.data & 0xFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAsDouble() {
/* 143 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAsFloat() {
/* 148 */     return (float)this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public Number getAsNumber() {
/* 153 */     return Long.valueOf(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 158 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\LongTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */