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
/*     */ public class FloatTag
/*     */   extends NumericTag
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 12;
/*  17 */   public static final FloatTag ZERO = new FloatTag(0.0F);
/*     */   
/*  19 */   public static final TagType<FloatTag> TYPE = new TagType.StaticSize<FloatTag>()
/*     */     {
/*     */       public FloatTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  22 */         return FloatTag.valueOf(readAccounted($$0, $$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  27 */         return $$1.visit(readAccounted($$0, $$2));
/*     */       }
/*     */       
/*     */       private static float readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  31 */         $$1.accountBytes(12L);
/*  32 */         return $$0.readFloat();
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/*  37 */         return 4;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  42 */         return "FLOAT";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/*  47 */         return "TAG_Float";
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isValue() {
/*  52 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   private final float data;
/*     */   
/*     */   private FloatTag(float $$0) {
/*  59 */     this.data = $$0;
/*     */   }
/*     */   
/*     */   public static FloatTag valueOf(float $$0) {
/*  63 */     if ($$0 == 0.0F) {
/*  64 */       return ZERO;
/*     */     }
/*  66 */     return new FloatTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/*  71 */     $$0.writeFloat(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/*  76 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  81 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<FloatTag> getType() {
/*  86 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatTag copy() {
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  96 */     if (this == $$0) {
/*  97 */       return true;
/*     */     }
/*     */     
/* 100 */     return ($$0 instanceof FloatTag && this.data == ((FloatTag)$$0).data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     return Float.floatToIntBits(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 110 */     $$0.visitFloat(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getAsLong() {
/* 115 */     return (long)this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAsInt() {
/* 120 */     return Mth.floor(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getAsShort() {
/* 125 */     return (short)(Mth.floor(this.data) & 0xFFFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getAsByte() {
/* 130 */     return (byte)(Mth.floor(this.data) & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAsDouble() {
/* 135 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAsFloat() {
/* 140 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public Number getAsNumber() {
/* 145 */     return Float.valueOf(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 150 */     return $$0.visit(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\FloatTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */