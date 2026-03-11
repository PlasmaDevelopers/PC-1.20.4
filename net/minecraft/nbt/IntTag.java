/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntTag
/*     */   extends NumericTag
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 12;
/*     */   
/*     */   private static class Cache
/*     */   {
/*     */     private static final int HIGH = 1024;
/*     */     private static final int LOW = -128;
/*  18 */     static final IntTag[] cache = new IntTag[1153];
/*     */     
/*     */     static {
/*  21 */       for (int $$0 = 0; $$0 < cache.length; $$0++) {
/*  22 */         cache[$$0] = new IntTag(-128 + $$0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*  27 */   public static final TagType<IntTag> TYPE = new TagType.StaticSize<IntTag>()
/*     */     {
/*     */       public IntTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  30 */         return IntTag.valueOf(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  35 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static int readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  39 */         $$1.accountBytes(12L);
/*  40 */         return $$0.readInt();
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/*  45 */         return 4;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  50 */         return "INT";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  55 */         return "TAG_Int";
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isValue() {
/*  60 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   private final int data;
/*     */   
/*     */   IntTag(int $$0) {
/*  67 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public static IntTag valueOf(int $$0) {
/*  71 */     if ($$0 >= -128 && $$0 <= 1024) {
/*  72 */       return Cache.cache[$$0 - -128];
/*     */     }
/*  74 */     return new IntTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  79 */     $$0.writeInt(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  84 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  89 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<IntTag> getType() {
/*  94 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntTag copy() {
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 104 */     if (this == $$0) {
/* 105 */       return true;
/*     */     }
/*     */     
/* 108 */     return ($$0 instanceof IntTag && this.data == ((IntTag)$$0).data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 118 */     $$0.visitInt(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getAsLong() {
/* 123 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAsInt() {
/* 128 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getAsShort() {
/* 133 */     return (short)(this.data & 0xFFFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getAsByte() {
/* 138 */     return (byte)(this.data & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAsDouble() {
/* 143 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAsFloat() {
/* 148 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public Number getAsNumber() {
/* 153 */     return Integer.valueOf(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 158 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\IntTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */