/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ 
/*     */ public class CompoundTag implements Tag {
/*     */   static {
/*  23 */     CODEC = Codec.PASSTHROUGH.comapFlatMap($$0 -> {
/*     */           Tag $$1 = (Tag)$$0.convert(NbtOps.INSTANCE).getValue();
/*     */           if ($$1 instanceof CompoundTag) {
/*     */             CompoundTag $$2 = (CompoundTag)$$1;
/*     */             return DataResult.success(($$2 == $$0.getValue()) ? $$2.copy() : $$2);
/*     */           } 
/*     */           return DataResult.error(());
/*     */         }$$0 -> new Dynamic(NbtOps.INSTANCE, $$0.copy()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Codec<CompoundTag> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SELF_SIZE_IN_BYTES = 48;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAP_ENTRY_SIZE_IN_BYTES = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final TagType<CompoundTag> TYPE = new TagType.VariableSize<CompoundTag>()
/*     */     {
/*     */       public CompoundTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  61 */         $$1.pushDepth();
/*     */         try {
/*  63 */           return loadCompound($$0, $$1);
/*     */         } finally {
/*  65 */           $$1.popDepth();
/*     */         } 
/*     */       }
/*     */       
/*     */       private static CompoundTag loadCompound(DataInput $$0, NbtAccounter $$1) throws IOException {
/*  70 */         $$1.accountBytes(48L);
/*     */         
/*  72 */         Map<String, Tag> $$2 = Maps.newHashMap();
/*     */         byte $$3;
/*  74 */         while (($$3 = $$0.readByte()) != 0) {
/*  75 */           String $$4 = readString($$0, $$1);
/*  76 */           Tag $$5 = CompoundTag.readNamedTagData(TagTypes.getType($$3), $$4, $$0, $$1);
/*  77 */           if ($$2.put($$4, $$5) == null) {
/*  78 */             $$1.accountBytes(36L);
/*     */           }
/*     */         } 
/*  81 */         return new CompoundTag($$2);
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  86 */         $$2.pushDepth();
/*     */         try {
/*  88 */           return parseCompound($$0, $$1, $$2);
/*     */         } finally {
/*  90 */           $$2.popDepth();
/*     */         } 
/*     */       }
/*     */       
/*     */       private static StreamTagVisitor.ValueResult parseCompound(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/*  95 */         $$2.accountBytes(48L);
/*     */ 
/*     */         
/*     */         byte $$3;
/*     */         
/* 100 */         while (($$3 = $$0.readByte()) != 0) {
/* 101 */           TagType<?> $$4 = TagTypes.getType($$3);
/*     */           
/* 103 */           switch ($$1.visitEntry($$4)) {
/*     */             case HALT:
/* 105 */               return StreamTagVisitor.ValueResult.HALT;
/*     */             case BREAK:
/* 107 */               StringTag.skipString($$0);
/* 108 */               $$4.skip($$0, $$2);
/*     */               break;
/*     */             case null:
/* 111 */               StringTag.skipString($$0);
/* 112 */               $$4.skip($$0, $$2);
/*     */               continue;
/*     */           } 
/*     */           
/* 116 */           String $$5 = readString($$0, $$2);
/* 117 */           switch ($$1.visitEntry($$4, $$5)) {
/*     */             case HALT:
/* 119 */               return StreamTagVisitor.ValueResult.HALT;
/*     */             case BREAK:
/* 121 */               $$4.skip($$0, $$2);
/*     */               break;
/*     */             case null:
/* 124 */               $$4.skip($$0, $$2);
/*     */               continue;
/*     */           } 
/*     */           
/* 128 */           $$2.accountBytes(36L);
/* 129 */           switch ($$4.parse($$0, $$1, $$2)) {
/*     */             case HALT:
/* 131 */               return StreamTagVisitor.ValueResult.HALT;
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         } 
/* 137 */         if ($$3 != 0) {
/* 138 */           while (($$3 = $$0.readByte()) != 0) {
/* 139 */             StringTag.skipString($$0);
/* 140 */             TagTypes.getType($$3).skip($$0, $$2);
/*     */           } 
/*     */         }
/*     */         
/* 144 */         return $$1.visitContainerEnd();
/*     */       }
/*     */       
/*     */       private static String readString(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 148 */         String $$2 = $$0.readUTF();
/* 149 */         $$1.accountBytes(28L);
/* 150 */         $$1.accountBytes(2L, $$2.length());
/* 151 */         return $$2;
/*     */       }
/*     */ 
/*     */       
/*     */       public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 156 */         $$1.pushDepth();
/*     */         try {
/*     */           byte $$2;
/* 159 */           while (($$2 = $$0.readByte()) != 0) {
/* 160 */             StringTag.skipString($$0);
/* 161 */             TagTypes.getType($$2).skip($$0, $$1);
/*     */           } 
/*     */         } finally {
/* 164 */           $$1.popDepth();
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/* 170 */         return "COMPOUND";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrettyName() {
/* 175 */         return "TAG_Compound";
/*     */       }
/*     */     };
/*     */   
/*     */   private final Map<String, Tag> tags;
/*     */   
/*     */   protected CompoundTag(Map<String, Tag> $$0) {
/* 182 */     this.tags = $$0;
/*     */   }
/*     */   
/*     */   public CompoundTag() {
/* 186 */     this(Maps.newHashMap());
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput $$0) throws IOException {
/* 191 */     for (String $$1 : this.tags.keySet()) {
/* 192 */       Tag $$2 = this.tags.get($$1);
/* 193 */       writeNamedTag($$1, $$2, $$0);
/*     */     } 
/* 195 */     $$0.writeByte(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeInBytes() {
/* 200 */     int $$0 = 48;
/* 201 */     for (Map.Entry<String, Tag> $$1 : this.tags.entrySet()) {
/* 202 */       $$0 += 28 + 2 * ((String)$$1.getKey()).length();
/* 203 */       $$0 += 36;
/* 204 */       $$0 += ((Tag)$$1.getValue()).sizeInBytes();
/*     */     } 
/* 206 */     return $$0;
/*     */   }
/*     */   
/*     */   public Set<String> getAllKeys() {
/* 210 */     return this.tags.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/* 215 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagType<CompoundTag> getType() {
/* 220 */     return TYPE;
/*     */   }
/*     */   
/*     */   public int size() {
/* 224 */     return this.tags.size();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Tag put(String $$0, Tag $$1) {
/* 229 */     return this.tags.put($$0, $$1);
/*     */   }
/*     */   
/*     */   public void putByte(String $$0, byte $$1) {
/* 233 */     this.tags.put($$0, ByteTag.valueOf($$1));
/*     */   }
/*     */   
/*     */   public void putShort(String $$0, short $$1) {
/* 237 */     this.tags.put($$0, ShortTag.valueOf($$1));
/*     */   }
/*     */   
/*     */   public void putInt(String $$0, int $$1) {
/* 241 */     this.tags.put($$0, IntTag.valueOf($$1));
/*     */   }
/*     */   
/*     */   public void putLong(String $$0, long $$1) {
/* 245 */     this.tags.put($$0, LongTag.valueOf($$1));
/*     */   }
/*     */   
/*     */   public void putUUID(String $$0, UUID $$1) {
/* 249 */     this.tags.put($$0, NbtUtils.createUUID($$1));
/*     */   }
/*     */   
/*     */   public UUID getUUID(String $$0) {
/* 253 */     return NbtUtils.loadUUID(get($$0));
/*     */   }
/*     */   
/*     */   public boolean hasUUID(String $$0) {
/* 257 */     Tag $$1 = get($$0);
/* 258 */     return ($$1 != null && $$1.getType() == IntArrayTag.TYPE && (((IntArrayTag)$$1).getAsIntArray()).length == 4);
/*     */   }
/*     */   
/*     */   public void putFloat(String $$0, float $$1) {
/* 262 */     this.tags.put($$0, FloatTag.valueOf($$1));
/*     */   }
/*     */   
/*     */   public void putDouble(String $$0, double $$1) {
/* 266 */     this.tags.put($$0, DoubleTag.valueOf($$1));
/*     */   }
/*     */   
/*     */   public void putString(String $$0, String $$1) {
/* 270 */     this.tags.put($$0, StringTag.valueOf($$1));
/*     */   }
/*     */   
/*     */   public void putByteArray(String $$0, byte[] $$1) {
/* 274 */     this.tags.put($$0, new ByteArrayTag($$1));
/*     */   }
/*     */   
/*     */   public void putByteArray(String $$0, List<Byte> $$1) {
/* 278 */     this.tags.put($$0, new ByteArrayTag($$1));
/*     */   }
/*     */   
/*     */   public void putIntArray(String $$0, int[] $$1) {
/* 282 */     this.tags.put($$0, new IntArrayTag($$1));
/*     */   }
/*     */   
/*     */   public void putIntArray(String $$0, List<Integer> $$1) {
/* 286 */     this.tags.put($$0, new IntArrayTag($$1));
/*     */   }
/*     */   
/*     */   public void putLongArray(String $$0, long[] $$1) {
/* 290 */     this.tags.put($$0, new LongArrayTag($$1));
/*     */   }
/*     */   
/*     */   public void putLongArray(String $$0, List<Long> $$1) {
/* 294 */     this.tags.put($$0, new LongArrayTag($$1));
/*     */   }
/*     */   
/*     */   public void putBoolean(String $$0, boolean $$1) {
/* 298 */     this.tags.put($$0, ByteTag.valueOf($$1));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Tag get(String $$0) {
/* 303 */     return this.tags.get($$0);
/*     */   }
/*     */   
/*     */   public byte getTagType(String $$0) {
/* 307 */     Tag $$1 = this.tags.get($$0);
/* 308 */     if ($$1 == null) {
/* 309 */       return 0;
/*     */     }
/* 311 */     return $$1.getId();
/*     */   }
/*     */   
/*     */   public boolean contains(String $$0) {
/* 315 */     return this.tags.containsKey($$0);
/*     */   }
/*     */   
/*     */   public boolean contains(String $$0, int $$1) {
/* 319 */     int $$2 = getTagType($$0);
/* 320 */     if ($$2 == $$1) {
/* 321 */       return true;
/*     */     }
/* 323 */     if ($$1 == 99) {
/* 324 */       return ($$2 == 1 || $$2 == 2 || $$2 == 3 || $$2 == 4 || $$2 == 5 || $$2 == 6);
/*     */     }
/*     */     
/* 327 */     return false;
/*     */   }
/*     */   
/*     */   public byte getByte(String $$0) {
/*     */     try {
/* 332 */       if (contains($$0, 99)) {
/* 333 */         return ((NumericTag)this.tags.get($$0)).getAsByte();
/*     */       }
/* 335 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 337 */     return 0;
/*     */   }
/*     */   
/*     */   public short getShort(String $$0) {
/*     */     try {
/* 342 */       if (contains($$0, 99)) {
/* 343 */         return ((NumericTag)this.tags.get($$0)).getAsShort();
/*     */       }
/* 345 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 347 */     return 0;
/*     */   }
/*     */   
/*     */   public int getInt(String $$0) {
/*     */     try {
/* 352 */       if (contains($$0, 99)) {
/* 353 */         return ((NumericTag)this.tags.get($$0)).getAsInt();
/*     */       }
/* 355 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 357 */     return 0;
/*     */   }
/*     */   
/*     */   public long getLong(String $$0) {
/*     */     try {
/* 362 */       if (contains($$0, 99)) {
/* 363 */         return ((NumericTag)this.tags.get($$0)).getAsLong();
/*     */       }
/* 365 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 367 */     return 0L;
/*     */   }
/*     */   
/*     */   public float getFloat(String $$0) {
/*     */     try {
/* 372 */       if (contains($$0, 99)) {
/* 373 */         return ((NumericTag)this.tags.get($$0)).getAsFloat();
/*     */       }
/* 375 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 377 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public double getDouble(String $$0) {
/*     */     try {
/* 382 */       if (contains($$0, 99)) {
/* 383 */         return ((NumericTag)this.tags.get($$0)).getAsDouble();
/*     */       }
/* 385 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 387 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public String getString(String $$0) {
/*     */     try {
/* 392 */       if (contains($$0, 8)) {
/* 393 */         return ((Tag)this.tags.get($$0)).getAsString();
/*     */       }
/* 395 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 397 */     return "";
/*     */   }
/*     */   
/*     */   public byte[] getByteArray(String $$0) {
/*     */     try {
/* 402 */       if (contains($$0, 7)) {
/* 403 */         return ((ByteArrayTag)this.tags.get($$0)).getAsByteArray();
/*     */       }
/* 405 */     } catch (ClassCastException $$1) {
/* 406 */       throw new ReportedException(createReport($$0, ByteArrayTag.TYPE, $$1));
/*     */     } 
/* 408 */     return new byte[0];
/*     */   }
/*     */   
/*     */   public int[] getIntArray(String $$0) {
/*     */     try {
/* 413 */       if (contains($$0, 11)) {
/* 414 */         return ((IntArrayTag)this.tags.get($$0)).getAsIntArray();
/*     */       }
/* 416 */     } catch (ClassCastException $$1) {
/* 417 */       throw new ReportedException(createReport($$0, IntArrayTag.TYPE, $$1));
/*     */     } 
/* 419 */     return new int[0];
/*     */   }
/*     */   
/*     */   public long[] getLongArray(String $$0) {
/*     */     try {
/* 424 */       if (contains($$0, 12)) {
/* 425 */         return ((LongArrayTag)this.tags.get($$0)).getAsLongArray();
/*     */       }
/* 427 */     } catch (ClassCastException $$1) {
/* 428 */       throw new ReportedException(createReport($$0, LongArrayTag.TYPE, $$1));
/*     */     } 
/* 430 */     return new long[0];
/*     */   }
/*     */   
/*     */   public CompoundTag getCompound(String $$0) {
/*     */     try {
/* 435 */       if (contains($$0, 10)) {
/* 436 */         return (CompoundTag)this.tags.get($$0);
/*     */       }
/* 438 */     } catch (ClassCastException $$1) {
/* 439 */       throw new ReportedException(createReport($$0, TYPE, $$1));
/*     */     } 
/* 441 */     return new CompoundTag();
/*     */   }
/*     */   
/*     */   public ListTag getList(String $$0, int $$1) {
/*     */     try {
/* 446 */       if (getTagType($$0) == 9) {
/* 447 */         ListTag $$2 = (ListTag)this.tags.get($$0);
/* 448 */         if ($$2.isEmpty() || $$2.getElementType() == $$1) {
/* 449 */           return $$2;
/*     */         }
/* 451 */         return new ListTag();
/*     */       } 
/* 453 */     } catch (ClassCastException $$3) {
/* 454 */       throw new ReportedException(createReport($$0, ListTag.TYPE, $$3));
/*     */     } 
/* 456 */     return new ListTag();
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String $$0) {
/* 460 */     return (getByte($$0) != 0);
/*     */   }
/*     */   
/*     */   public void remove(String $$0) {
/* 464 */     this.tags.remove($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 469 */     return getAsString();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 473 */     return this.tags.isEmpty();
/*     */   }
/*     */   
/*     */   private CrashReport createReport(String $$0, TagType<?> $$1, ClassCastException $$2) {
/* 477 */     CrashReport $$3 = CrashReport.forThrowable($$2, "Reading NBT data");
/* 478 */     CrashReportCategory $$4 = $$3.addCategory("Corrupt NBT tag", 1);
/*     */     
/* 480 */     $$4.setDetail("Tag type found", () -> ((Tag)this.tags.get($$0)).getType().getName());
/* 481 */     Objects.requireNonNull($$1); $$4.setDetail("Tag type expected", $$1::getName);
/* 482 */     $$4.setDetail("Tag name", $$0);
/*     */     
/* 484 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag copy() {
/* 489 */     Map<String, Tag> $$0 = Maps.newHashMap(Maps.transformValues(this.tags, Tag::copy));
/* 490 */     return new CompoundTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 495 */     if (this == $$0) {
/* 496 */       return true;
/*     */     }
/*     */     
/* 499 */     return ($$0 instanceof CompoundTag && Objects.equals(this.tags, ((CompoundTag)$$0).tags));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 504 */     return this.tags.hashCode();
/*     */   }
/*     */   
/*     */   private static void writeNamedTag(String $$0, Tag $$1, DataOutput $$2) throws IOException {
/* 508 */     $$2.writeByte($$1.getId());
/* 509 */     if ($$1.getId() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 513 */     $$2.writeUTF($$0);
/*     */     
/* 515 */     $$1.write($$2);
/*     */   }
/*     */   
/*     */   static Tag readNamedTagData(TagType<?> $$0, String $$1, DataInput $$2, NbtAccounter $$3) {
/*     */     try {
/* 520 */       return (Tag)$$0.load($$2, $$3);
/* 521 */     } catch (IOException $$4) {
/* 522 */       CrashReport $$5 = CrashReport.forThrowable($$4, "Loading NBT data");
/* 523 */       CrashReportCategory $$6 = $$5.addCategory("NBT Tag");
/* 524 */       $$6.setDetail("Tag name", $$1);
/* 525 */       $$6.setDetail("Tag type", $$0.getName());
/* 526 */       throw new ReportedNbtException($$5);
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag merge(CompoundTag $$0) {
/* 544 */     for (String $$1 : $$0.tags.keySet()) {
/* 545 */       Tag $$2 = $$0.tags.get($$1);
/*     */ 
/*     */       
/* 548 */       if ($$2.getId() == 10) {
/* 549 */         if (contains($$1, 10)) {
/* 550 */           CompoundTag $$3 = getCompound($$1);
/* 551 */           $$3.merge((CompoundTag)$$2); continue;
/*     */         } 
/* 553 */         put($$1, $$2.copy());
/*     */         continue;
/*     */       } 
/* 556 */       put($$1, $$2.copy());
/*     */     } 
/*     */     
/* 559 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(TagVisitor $$0) {
/* 564 */     $$0.visitCompound(this);
/*     */   }
/*     */   
/*     */   protected Map<String, Tag> entries() {
/* 568 */     return Collections.unmodifiableMap(this.tags);
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 573 */     for (Map.Entry<String, Tag> $$1 : this.tags.entrySet()) {
/* 574 */       Tag $$2 = $$1.getValue();
/* 575 */       TagType<?> $$3 = $$2.getType();
/* 576 */       StreamTagVisitor.EntryResult $$4 = $$0.visitEntry($$3);
/* 577 */       switch ($$4) {
/*     */         case HALT:
/* 579 */           return StreamTagVisitor.ValueResult.HALT;
/*     */         case BREAK:
/* 581 */           return $$0.visitContainerEnd();
/*     */         
/*     */         case null:
/*     */           continue;
/*     */       } 
/* 586 */       $$4 = $$0.visitEntry($$3, $$1.getKey());
/* 587 */       switch ($$4) {
/*     */         case HALT:
/* 589 */           return StreamTagVisitor.ValueResult.HALT;
/*     */         case BREAK:
/* 591 */           return $$0.visitContainerEnd();
/*     */         
/*     */         case null:
/*     */           continue;
/*     */       } 
/* 596 */       StreamTagVisitor.ValueResult $$5 = $$2.accept($$0);
/* 597 */       switch ($$5) {
/*     */         case HALT:
/* 599 */           return StreamTagVisitor.ValueResult.HALT;
/*     */         case BREAK:
/* 601 */           return $$0.visitContainerEnd();
/*     */       } 
/*     */     } 
/* 604 */     return $$0.visitContainerEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\CompoundTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */