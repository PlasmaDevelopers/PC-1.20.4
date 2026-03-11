/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShortTag
/*     */   extends NumericTag
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 10;
/*     */   
/*     */   private static class Cache
/*     */   {
/*     */     private static final int HIGH = 1024;
/*     */     private static final int LOW = -128;
/*  18 */     static final ShortTag[] cache = new ShortTag[1153];
/*     */     
/*     */     static {
/*  21 */       for (int $$0 = 0; $$0 < cache.length; $$0++) {
/*  22 */         cache[$$0] = new ShortTag((short)(-128 + $$0));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*  27 */   public static final TagType<ShortTag> TYPE = new TagType.StaticSize<ShortTag>()
/*     */     {
/*     */       public ShortTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  30 */         return ShortTag.valueOf(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  35 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static short readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  39 */         $$1.accountBytes(10L);
/*  40 */         return $$0.readShort();
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/*  45 */         return 2;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  50 */         return "SHORT";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  55 */         return "TAG_Short";
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isValue() {
/*  60 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   private final short data;
/*     */   
/*     */   ShortTag(short $$0) {
/*  67 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public static ShortTag valueOf(short $$0) {
/*  71 */     if ($$0 >= -128 && $$0 <= 1024) {
/*  72 */       return Cache.cache[$$0 - -128];
/*     */     }
/*  74 */     return new ShortTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  79 */     $$0.writeShort(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  84 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  89 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<ShortTag> getType() {
/*  94 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShortTag copy() {
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 104 */     if (this == $$0) {
/* 105 */       return true;
/*     */     }
/*     */     
/* 108 */     return ($$0 instanceof ShortTag && this.data == ((ShortTag)$$0).data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 118 */     $$0.visitShort(this);
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
/* 133 */     return this.data;
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
/* 153 */     return Short.valueOf(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 158 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ShortTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */