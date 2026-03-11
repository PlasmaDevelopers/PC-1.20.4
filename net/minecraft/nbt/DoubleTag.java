/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleTag
/*     */   extends NumericTag
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 16;
/*  17 */   public static final DoubleTag ZERO = new DoubleTag(0.0D);
/*     */   
/*  19 */   public static final TagType<DoubleTag> TYPE = new TagType.StaticSize<DoubleTag>()
/*     */     {
/*     */       public DoubleTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  22 */         return DoubleTag.valueOf(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  27 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static double readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  31 */         $$1.accountBytes(16L);
/*  32 */         return $$0.readDouble();
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/*  37 */         return 8;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  42 */         return "DOUBLE";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  47 */         return "TAG_Double";
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isValue() {
/*  52 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   private final double data;
/*     */   
/*     */   private DoubleTag(double $$0) {
/*  59 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public static DoubleTag valueOf(double $$0) {
/*  63 */     if ($$0 == 0.0D) {
/*  64 */       return ZERO;
/*     */     }
/*  66 */     return new DoubleTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  71 */     $$0.writeDouble(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  76 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  81 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<DoubleTag> getType() {
/*  86 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleTag copy() {
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  96 */     if (this == $$0) {
/*  97 */       return true;
/*     */     }
/*     */     
/* 100 */     return ($$0 instanceof DoubleTag && this.data == ((DoubleTag)$$0).data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     long $$0 = Double.doubleToLongBits(this.data);
/* 106 */     return (int)($$0 ^ $$0 >>> 32L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 111 */     $$0.visitDouble(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getAsLong() {
/* 116 */     return (long)Math.floor(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAsInt() {
/* 121 */     return Mth.floor(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getAsShort() {
/* 126 */     return (short)(Mth.floor(this.data) & 0xFFFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getAsByte() {
/* 131 */     return (byte)(Mth.floor(this.data) & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAsDouble() {
/* 136 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAsFloat() {
/* 141 */     return (float)this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public Number getAsNumber() {
/* 146 */     return Double.valueOf(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 151 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\DoubleTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */