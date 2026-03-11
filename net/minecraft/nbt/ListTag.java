/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListTag
/*     */   extends CollectionTag<Tag>
/*     */ {
/*     */   private static final int SELF_SIZE_IN_BYTES = 37;
/*     */   
/*  26 */   public static final TagType<ListTag> TYPE = new TagType.VariableSize<ListTag>()
/*     */     {
/*     */       public ListTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  29 */         $$1.pushDepth();
/*     */         try {
/*  31 */           return loadList($$0, $$1);
/*     */         } finally {
/*  33 */           $$1.popDepth();
/*     */         } 
/*     */       }
/*     */       
/*     */       private static ListTag loadList(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  38 */         $$1.accountBytes(37L);
/*  39 */         byte $$2 = $$0.readByte();
/*  40 */         int $$3 = $$0.readInt();
/*  41 */         if ($$2 == 0 && $$3 > 0) {
/*  42 */           throw new NbtFormatException("Missing type on ListTag");
/*     */         }
/*  44 */         $$1.accountBytes(4L, $$3);
/*  45 */         TagType<?> $$4 = TagTypes.getType($$2);
/*  46 */         List<Tag> $$5 = Lists.newArrayListWithCapacity($$3);
/*  47 */         for (int $$6 = 0; $$6 < $$3; $$6++) {
/*  48 */           $$5.add((Tag)$$4.load($$0, $$1));
/*     */         }
/*  50 */         return new ListTag($$5, $$2);
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  55 */         $$2.pushDepth();
/*     */         try {
/*  57 */           return parseList($$0, $$1, $$2);
/*     */         } finally {
/*  59 */           $$2.popDepth();
/*     */         } 
/*     */       }
/*     */       
/*     */       private static StreamTagVisitor.ValueResult parseList(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  64 */         $$2.accountBytes(37L);
/*  65 */         TagType<?> $$3 = TagTypes.getType($$0.readByte());
/*  66 */         int $$4 = $$0.readInt();
/*  67 */         switch ($$1.visitList($$3, $$4)) {
/*     */           case HALT:
/*  69 */             return StreamTagVisitor.ValueResult.HALT;
/*     */           case BREAK:
/*  71 */             $$3.skip($$0, $$4, $$2);
/*  72 */             return $$1.visitContainerEnd();
/*     */         } 
/*     */         
/*  75 */         $$2.accountBytes(4L, $$4);
/*     */         
/*     */         int $$5;
/*  78 */         for ($$5 = 0; $$5 < $$4; $$5++) {
/*  79 */           switch ($$1.visitElement($$3, $$5)) {
/*     */             case HALT:
/*  81 */               return StreamTagVisitor.ValueResult.HALT;
/*     */             case BREAK:
/*  83 */               $$3.skip($$0, $$2);
/*     */               break;
/*     */             case SKIP:
/*  86 */               $$3.skip($$0, $$2);
/*     */               break;
/*     */             
/*     */             default:
/*  90 */               switch ($$3.parse($$0, $$1, $$2)) {
/*     */                 case HALT:
/*  92 */                   return StreamTagVisitor.ValueResult.HALT;
/*     */                 case BREAK:
/*     */                   break;
/*     */               }  break;
/*     */           } 
/*  97 */         }  int $$6 = $$4 - 1 - $$5;
/*  98 */         if ($$6 > 0) {
/*  99 */           $$3.skip($$0, $$6, $$2);
/*     */         }
/* 101 */         return $$1.visitContainerEnd();
/*     */       }
/*     */ 
/*     */       
/*     */       public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 106 */         $$1.pushDepth();
/*     */         try {
/* 108 */           TagType<?> $$2 = TagTypes.getType($$0.readByte());
/* 109 */           int $$3 = $$0.readInt();
/* 110 */           $$2.skip($$0, $$3, $$1);
/*     */         } finally {
/* 112 */           $$1.popDepth();
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/* 118 */         return "LIST";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/* 123 */         return "TAG_List";
/*     */       }
/*     */     };
/*     */   
/*     */   private final List<Tag> list;
/*     */   private byte type;
/*     */   
/*     */   ListTag(List<Tag> $$0, byte $$1) {
/* 131 */     this.list = $$0;
/* 132 */     this.type = $$1;
/*     */   }
/*     */   
/*     */   public ListTag() {
/* 136 */     this(Lists.newArrayList(), (byte)0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/* 141 */     if (this.list.isEmpty()) {
/* 142 */       this.type = 0;
/*     */     } else {
/* 144 */       this.type = ((Tag)this.list.get(0)).getId();
/*     */     } 
/*     */     
/* 147 */     $$0.writeByte(this.type);
/* 148 */     $$0.writeInt(this.list.size());
/* 149 */     for (Tag $$1 : this.list) {
/* 150 */       $$1.write($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/* 156 */     int $$0 = 37;
/* 157 */     $$0 += 4 * this.list.size();
/* 158 */     for (Tag $$1 : this.list) {
/* 159 */       $$0 += $$1.sizeInBytes();
/*     */     }
/* 161 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/* 166 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<ListTag> getType() {
/* 171 */     return TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 176 */     return getAsString();
/*     */   }
/*     */   
/*     */   private void updateTypeAfterRemove() {
/* 180 */     if (this.list.isEmpty()) {
/* 181 */       this.type = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag remove(int $$0) {
/* 187 */     Tag $$1 = this.list.remove($$0);
/* 188 */     updateTypeAfterRemove();
/* 189 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 194 */     return this.list.isEmpty();
/*     */   }
/*     */   
/*     */   public CompoundTag getCompound(int $$0) {
/* 198 */     if ($$0 >= 0 && $$0 < this.list.size()) {
/* 199 */       Tag $$1 = this.list.get($$0);
/* 200 */       if ($$1.getId() == 10) {
/* 201 */         return (CompoundTag)$$1;
/*     */       }
/*     */     } 
/* 204 */     return new CompoundTag();
/*     */   }
/*     */   
/*     */   public ListTag getList(int $$0) {
/* 208 */     if ($$0 >= 0 && $$0 < this.list.size()) {
/* 209 */       Tag $$1 = this.list.get($$0);
/* 210 */       if ($$1.getId() == 9) {
/* 211 */         return (ListTag)$$1;
/*     */       }
/*     */     } 
/* 214 */     return new ListTag();
/*     */   }
/*     */   
/*     */   public short getShort(int $$0) {
/* 218 */     if ($$0 >= 0 && $$0 < this.list.size()) {
/* 219 */       Tag $$1 = this.list.get($$0);
/* 220 */       if ($$1.getId() == 2) {
/* 221 */         return ((ShortTag)$$1).getAsShort();
/*     */       }
/*     */     } 
/* 224 */     return 0;
/*     */   }
/*     */   
/*     */   public int getInt(int $$0) {
/* 228 */     if ($$0 >= 0 && $$0 < this.list.size()) {
/* 229 */       Tag $$1 = this.list.get($$0);
/* 230 */       if ($$1.getId() == 3) {
/* 231 */         return ((IntTag)$$1).getAsInt();
/*     */       }
/*     */     } 
/* 234 */     return 0;
/*     */   }
/*     */   
/*     */   public int[] getIntArray(int $$0) {
/* 238 */     if ($$0 >= 0 && $$0 < this.list.size()) {
/* 239 */       Tag $$1 = this.list.get($$0);
/* 240 */       if ($$1.getId() == 11) {
/* 241 */         return ((IntArrayTag)$$1).getAsIntArray();
/*     */       }
/*     */     } 
/* 244 */     return new int[0];
/*     */   }
/*     */   
/*     */   public long[] getLongArray(int $$0) {
/* 248 */     if ($$0 >= 0 && $$0 < this.list.size()) {
/* 249 */       Tag $$1 = this.list.get($$0);
/* 250 */       if ($$1.getId() == 12) {
/* 251 */         return ((LongArrayTag)$$1).getAsLongArray();
/*     */       }
/*     */     } 
/* 254 */     return new long[0];
/*     */   }
/*     */   
/*     */   public double getDouble(int $$0) {
/* 258 */     if ($$0 >= 0 && $$0 < this.list.size()) {
/* 259 */       Tag $$1 = this.list.get($$0);
/* 260 */       if ($$1.getId() == 6) {
/* 261 */         return ((DoubleTag)$$1).getAsDouble();
/*     */       }
/*     */     } 
/* 264 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public float getFloat(int $$0) {
/* 268 */     if ($$0 >= 0 && $$0 < this.list.size()) {
/* 269 */       Tag $$1 = this.list.get($$0);
/* 270 */       if ($$1.getId() == 5) {
/* 271 */         return ((FloatTag)$$1).getAsFloat();
/*     */       }
/*     */     } 
/* 274 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public String getString(int $$0) {
/* 278 */     if ($$0 < 0 || $$0 >= this.list.size()) {
/* 279 */       return "";
/*     */     }
/* 281 */     Tag $$1 = this.list.get($$0);
/* 282 */     if ($$1.getId() == 8) {
/* 283 */       return $$1.getAsString();
/*     */     }
/* 285 */     return $$1.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 290 */     return this.list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag get(int $$0) {
/* 295 */     return this.list.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag set(int $$0, Tag $$1) {
/* 300 */     Tag $$2 = get($$0);
/* 301 */     if (!setTag($$0, $$1)) {
/* 302 */       throw new UnsupportedOperationException(String.format(Locale.ROOT, "Trying to add tag of type %d to list of %d", new Object[] { Byte.valueOf($$1.getId()), Byte.valueOf(this.type) }));
/*     */     }
/* 304 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int $$0, Tag $$1) {
/* 309 */     if (!addTag($$0, $$1)) {
/* 310 */       throw new UnsupportedOperationException(String.format(Locale.ROOT, "Trying to add tag of type %d to list of %d", new Object[] { Byte.valueOf($$1.getId()), Byte.valueOf(this.type) }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTag(int $$0, Tag $$1) {
/* 316 */     if (updateType($$1)) {
/* 317 */       this.list.set($$0, $$1);
/* 318 */       return true;
/*     */     } 
/* 320 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addTag(int $$0, Tag $$1) {
/* 325 */     if (updateType($$1)) {
/* 326 */       this.list.add($$0, $$1);
/* 327 */       return true;
/*     */     } 
/* 329 */     return false;
/*     */   }
/*     */   
/*     */   private boolean updateType(Tag $$0) {
/* 333 */     if ($$0.getId() == 0) {
/* 334 */       return false;
/*     */     }
/* 336 */     if (this.type == 0) {
/* 337 */       this.type = $$0.getId();
/* 338 */       return true;
/*     */     } 
/* 340 */     return (this.type == $$0.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public ListTag copy() {
/* 345 */     Iterable<Tag> $$0 = TagTypes.getType(this.type).isValue() ? this.list : Iterables.transform(this.list, Tag::copy);
/* 346 */     List<Tag> $$1 = Lists.newArrayList($$0);
/* 347 */     return new ListTag($$1, this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 352 */     if (this == $$0) {
/* 353 */       return true;
/*     */     }
/*     */     
/* 356 */     return ($$0 instanceof ListTag && Objects.equals(this.list, ((ListTag)$$0).list));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 361 */     return this.list.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 366 */     $$0.visitList(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getElementType() {
/* 371 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 376 */     this.list.clear();
/* 377 */     this.type = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 382 */     switch ($$0.visitList(TagTypes.getType(this.type), this.list.size())) {
/*     */       case HALT:
/* 384 */         return StreamTagVisitor.ValueResult.HALT;
/*     */       case BREAK:
/* 386 */         return $$0.visitContainerEnd();
/*     */     } 
/* 388 */     for (int $$1 = 0; $$1 < this.list.size(); $$1++) {
/* 389 */       Tag $$2 = this.list.get($$1);
/* 390 */       switch ($$0.visitElement($$2.getType(), $$1)) {
/*     */         case HALT:
/* 392 */           return StreamTagVisitor.ValueResult.HALT;
/*     */         case SKIP:
/*     */           break;
/*     */         case BREAK:
/* 396 */           return $$0.visitContainerEnd();
/*     */         default:
/* 398 */           switch ($$2.accept($$0)) {
/*     */             case HALT:
/* 400 */               return StreamTagVisitor.ValueResult.HALT;
/*     */             case BREAK:
/* 402 */               return $$0.visitContainerEnd();
/*     */           }  break;
/*     */       } 
/* 405 */     }  return $$0.visitContainerEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ListTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */