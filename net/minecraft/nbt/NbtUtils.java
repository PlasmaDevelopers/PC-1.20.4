/*     */ package net.minecraft.nbt;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public final class NbtUtils {
/*     */   private static final Comparator<ListTag> YXZ_LISTTAG_INT_COMPARATOR;
/*     */   private static final Comparator<ListTag> YXZ_LISTTAG_DOUBLE_COMPARATOR;
/*     */   public static final String SNBT_DATA_TAG = "data";
/*     */   private static final char PROPERTIES_START = '{';
/*     */   private static final char PROPERTIES_END = '}';
/*     */   private static final String ELEMENT_SEPARATOR = ",";
/*     */   private static final char KEY_VALUE_SEPARATOR = ':';
/*     */   
/*     */   static {
/*  46 */     YXZ_LISTTAG_INT_COMPARATOR = Comparator.<ListTag>comparingInt($$0 -> $$0.getInt(1)).thenComparingInt($$0 -> $$0.getInt(0)).thenComparingInt($$0 -> $$0.getInt(2));
/*  47 */     YXZ_LISTTAG_DOUBLE_COMPARATOR = Comparator.<ListTag>comparingDouble($$0 -> $$0.getDouble(1)).thenComparingDouble($$0 -> $$0.getDouble(0)).thenComparingDouble($$0 -> $$0.getDouble(2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final Splitter COMMA_SPLITTER = Splitter.on(",");
/*  56 */   private static final Splitter COLON_SPLITTER = Splitter.on(':').limit(2);
/*     */   
/*  58 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int INDENT = 2;
/*     */   
/*     */   private static final int NOT_FOUND = -1;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static GameProfile readGameProfile(CompoundTag $$0) {
/*  67 */     UUID $$1 = $$0.hasUUID("Id") ? $$0.getUUID("Id") : Util.NIL_UUID;
/*  68 */     String $$2 = $$0.getString("Name");
/*     */     
/*     */     try {
/*  71 */       GameProfile $$3 = new GameProfile($$1, $$2);
/*     */       
/*  73 */       if ($$0.contains("Properties", 10)) {
/*  74 */         CompoundTag $$4 = $$0.getCompound("Properties");
/*     */         
/*  76 */         for (String $$5 : $$4.getAllKeys()) {
/*  77 */           ListTag $$6 = $$4.getList($$5, 10);
/*  78 */           for (int $$7 = 0; $$7 < $$6.size(); $$7++) {
/*  79 */             CompoundTag $$8 = $$6.getCompound($$7);
/*  80 */             String $$9 = $$8.getString("Value");
/*     */             
/*  82 */             if ($$8.contains("Signature", 8)) {
/*  83 */               $$3.getProperties().put($$5, new Property($$5, $$9, $$8.getString("Signature")));
/*     */             } else {
/*  85 */               $$3.getProperties().put($$5, new Property($$5, $$9));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  91 */       return $$3;
/*  92 */     } catch (Throwable throwable) {
/*     */       
/*  94 */       return null;
/*     */     } 
/*     */   }
/*     */   public static CompoundTag writeGameProfile(CompoundTag $$0, GameProfile $$1) {
/*  98 */     if (!$$1.getName().isEmpty()) {
/*  99 */       $$0.putString("Name", $$1.getName());
/*     */     }
/* 101 */     if (!$$1.getId().equals(Util.NIL_UUID)) {
/* 102 */       $$0.putUUID("Id", $$1.getId());
/*     */     }
/* 104 */     if (!$$1.getProperties().isEmpty()) {
/* 105 */       CompoundTag $$2 = new CompoundTag();
/* 106 */       for (String $$3 : $$1.getProperties().keySet()) {
/* 107 */         ListTag $$4 = new ListTag();
/* 108 */         for (Property $$5 : $$1.getProperties().get($$3)) {
/* 109 */           CompoundTag $$6 = new CompoundTag();
/* 110 */           $$6.putString("Value", $$5.value());
/* 111 */           String $$7 = $$5.signature();
/* 112 */           if ($$7 != null) {
/* 113 */             $$6.putString("Signature", $$7);
/*     */           }
/* 115 */           $$4.add($$6);
/*     */         } 
/* 117 */         $$2.put($$3, $$4);
/*     */       } 
/* 119 */       $$0.put("Properties", $$2);
/*     */     } 
/*     */     
/* 122 */     return $$0;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static boolean compareNbt(@Nullable Tag $$0, @Nullable Tag $$1, boolean $$2) {
/* 127 */     if ($$0 == $$1) {
/* 128 */       return true;
/*     */     }
/* 130 */     if ($$0 == null) {
/* 131 */       return true;
/*     */     }
/* 133 */     if ($$1 == null) {
/* 134 */       return false;
/*     */     }
/* 136 */     if (!$$0.getClass().equals($$1.getClass())) {
/* 137 */       return false;
/*     */     }
/*     */     
/* 140 */     if ($$0 instanceof CompoundTag) { CompoundTag $$3 = (CompoundTag)$$0;
/* 141 */       CompoundTag $$4 = (CompoundTag)$$1;
/*     */       
/* 143 */       for (String $$5 : $$3.getAllKeys()) {
/* 144 */         Tag $$6 = $$3.get($$5);
/* 145 */         if (!compareNbt($$6, $$4.get($$5), $$2)) {
/* 146 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 150 */       return true; }
/* 151 */      if ($$0 instanceof ListTag) { ListTag $$7 = (ListTag)$$0; if ($$2) {
/* 152 */         ListTag $$8 = (ListTag)$$1;
/*     */         
/* 154 */         if ($$7.isEmpty()) {
/* 155 */           return $$8.isEmpty();
/*     */         }
/*     */         
/* 158 */         for (Tag $$9 : $$7) {
/* 159 */           boolean $$10 = false;
/* 160 */           for (Tag $$11 : $$8) {
/* 161 */             if (compareNbt($$9, $$11, $$2)) {
/* 162 */               $$10 = true;
/*     */               break;
/*     */             } 
/*     */           } 
/* 166 */           if (!$$10) {
/* 167 */             return false;
/*     */           }
/*     */         } 
/*     */         
/* 171 */         return true;
/*     */       }  }
/* 173 */      return $$0.equals($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IntArrayTag createUUID(UUID $$0) {
/* 178 */     return new IntArrayTag(UUIDUtil.uuidToIntArray($$0));
/*     */   }
/*     */   
/*     */   public static UUID loadUUID(Tag $$0) {
/* 182 */     if ($$0.getType() != IntArrayTag.TYPE) {
/* 183 */       throw new IllegalArgumentException("Expected UUID-Tag to be of type " + IntArrayTag.TYPE.getName() + ", but found " + $$0.getType().getName() + ".");
/*     */     }
/* 185 */     int[] $$1 = ((IntArrayTag)$$0).getAsIntArray();
/* 186 */     if ($$1.length != 4) {
/* 187 */       throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + $$1.length + ".");
/*     */     }
/* 189 */     return UUIDUtil.uuidFromIntArray($$1);
/*     */   }
/*     */   
/*     */   public static BlockPos readBlockPos(CompoundTag $$0) {
/* 193 */     return new BlockPos($$0.getInt("X"), $$0.getInt("Y"), $$0.getInt("Z"));
/*     */   }
/*     */   
/*     */   public static CompoundTag writeBlockPos(BlockPos $$0) {
/* 197 */     CompoundTag $$1 = new CompoundTag();
/* 198 */     $$1.putInt("X", $$0.getX());
/* 199 */     $$1.putInt("Y", $$0.getY());
/* 200 */     $$1.putInt("Z", $$0.getZ());
/* 201 */     return $$1;
/*     */   }
/*     */   
/*     */   public static BlockState readBlockState(HolderGetter<Block> $$0, CompoundTag $$1) {
/* 205 */     if (!$$1.contains("Name", 8)) {
/* 206 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 209 */     ResourceLocation $$2 = new ResourceLocation($$1.getString("Name"));
/* 210 */     Optional<? extends Holder<Block>> $$3 = $$0.get(ResourceKey.create(Registries.BLOCK, $$2));
/* 211 */     if ($$3.isEmpty()) {
/* 212 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 215 */     Block $$4 = (Block)((Holder)$$3.get()).value();
/* 216 */     BlockState $$5 = $$4.defaultBlockState();
/*     */     
/* 218 */     if ($$1.contains("Properties", 10)) {
/* 219 */       CompoundTag $$6 = $$1.getCompound("Properties");
/*     */       
/* 221 */       StateDefinition<Block, BlockState> $$7 = $$4.getStateDefinition();
/* 222 */       for (String $$8 : $$6.getAllKeys()) {
/* 223 */         Property<?> $$9 = $$7.getProperty($$8);
/* 224 */         if ($$9 != null) {
/* 225 */           $$5 = setValueHelper($$5, $$9, $$8, $$6, $$1);
/*     */         }
/*     */       } 
/*     */     } 
/* 229 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <S extends net.minecraft.world.level.block.state.StateHolder<?, S>, T extends Comparable<T>> S setValueHelper(S $$0, Property<T> $$1, String $$2, CompoundTag $$3, CompoundTag $$4) {
/* 234 */     Optional<T> $$5 = $$1.getValue($$3.getString($$2));
/* 235 */     if ($$5.isPresent()) {
/* 236 */       return (S)$$0.setValue($$1, (Comparable)$$5.get());
/*     */     }
/*     */     
/* 239 */     LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", new Object[] { $$2, $$3.getString($$2), $$4 });
/* 240 */     return $$0;
/*     */   }
/*     */   
/*     */   public static CompoundTag writeBlockState(BlockState $$0) {
/* 244 */     CompoundTag $$1 = new CompoundTag();
/* 245 */     $$1.putString("Name", BuiltInRegistries.BLOCK.getKey($$0.getBlock()).toString());
/*     */     
/* 247 */     ImmutableMap<Property<?>, Comparable<?>> $$2 = $$0.getValues();
/* 248 */     if (!$$2.isEmpty()) {
/* 249 */       CompoundTag $$3 = new CompoundTag();
/*     */       
/* 251 */       for (UnmodifiableIterator<Map.Entry<Property<?>, Comparable<?>>> unmodifiableIterator = $$2.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Property<?>, Comparable<?>> $$4 = unmodifiableIterator.next();
/* 252 */         Property<?> $$5 = $$4.getKey();
/* 253 */         $$3.putString($$5.getName(), getName($$5, $$4.getValue())); }
/*     */       
/* 255 */       $$1.put("Properties", $$3);
/*     */     } 
/*     */     
/* 258 */     return $$1;
/*     */   }
/*     */   
/*     */   public static CompoundTag writeFluidState(FluidState $$0) {
/* 262 */     CompoundTag $$1 = new CompoundTag();
/* 263 */     $$1.putString("Name", BuiltInRegistries.FLUID.getKey($$0.getType()).toString());
/*     */     
/* 265 */     ImmutableMap<Property<?>, Comparable<?>> $$2 = $$0.getValues();
/* 266 */     if (!$$2.isEmpty()) {
/* 267 */       CompoundTag $$3 = new CompoundTag();
/*     */       
/* 269 */       for (UnmodifiableIterator<Map.Entry<Property<?>, Comparable<?>>> unmodifiableIterator = $$2.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Property<?>, Comparable<?>> $$4 = unmodifiableIterator.next();
/* 270 */         Property<?> $$5 = $$4.getKey();
/* 271 */         $$3.putString($$5.getName(), getName($$5, $$4.getValue())); }
/*     */       
/* 273 */       $$1.put("Properties", $$3);
/*     */     } 
/*     */     
/* 276 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends Comparable<T>> String getName(Property<T> $$0, Comparable<?> $$1) {
/* 282 */     return $$0.getName($$1);
/*     */   }
/*     */   
/*     */   public static String prettyPrint(Tag $$0) {
/* 286 */     return prettyPrint($$0, false);
/*     */   }
/*     */   
/*     */   public static String prettyPrint(Tag $$0, boolean $$1) {
/* 290 */     return prettyPrint(new StringBuilder(), $$0, 0, $$1).toString(); } public static StringBuilder prettyPrint(StringBuilder $$0, Tag $$1, int $$2, boolean $$3) { ByteArrayTag $$4; ListTag $$8; IntArrayTag $$13; CompoundTag $$19; LongArrayTag $$25; byte[] $$5; int $$9, $$14[]; List<String> $$20; long[] $$26; int $$6, $$10, $$15, $$21; long $$27; String $$11; int $$17;
/*     */     String $$22;
/*     */     int $$23;
/*     */     long $$29;
/* 294 */     switch ($$1.getId())
/*     */     { case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 8:
/* 302 */         $$0.append($$1);
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
/*     */       case 0:
/* 469 */         return $$0;case 7: $$4 = (ByteArrayTag)$$1; $$5 = $$4.getAsByteArray(); $$6 = $$5.length; indent($$2, $$0).append("byte[").append($$6).append("] {\n"); if ($$3) { indent($$2 + 1, $$0); for (int $$7 = 0; $$7 < $$5.length; $$7++) { if ($$7 != 0) $$0.append(',');  if ($$7 % 16 == 0 && $$7 / 16 > 0) { $$0.append('\n'); if ($$7 < $$5.length) indent($$2 + 1, $$0);  } else if ($$7 != 0) { $$0.append(' '); }  $$0.append(String.format(Locale.ROOT, "0x%02X", new Object[] { Integer.valueOf($$5[$$7] & 0xFF) })); }  } else { indent($$2 + 1, $$0).append(" // Skipped, supply withBinaryBlobs true"); }  $$0.append('\n'); indent($$2, $$0).append('}');case 9: $$8 = (ListTag)$$1; $$9 = $$8.size(); $$10 = $$8.getElementType(); $$11 = ($$10 == 0) ? "undefined" : TagTypes.getType($$10).getPrettyName(); indent($$2, $$0).append("list<").append($$11).append(">[").append($$9).append("] ["); if ($$9 != 0) $$0.append('\n');  for ($$12 = 0; $$12 < $$9; $$12++) { if ($$12 != 0) $$0.append(",\n");  indent($$2 + 1, $$0); prettyPrint($$0, $$8.get($$12), $$2 + 1, $$3); }  if ($$9 != 0) $$0.append('\n');  indent($$2, $$0).append(']');case 11: $$13 = (IntArrayTag)$$1; $$14 = $$13.getAsIntArray(); $$15 = 0; for (int $$16 : $$14) { $$15 = Math.max($$15, String.format(Locale.ROOT, "%X", new Object[] { Integer.valueOf($$16) }).length()); }  $$17 = $$14.length; indent($$2, $$0).append("int[").append($$17).append("] {\n"); if ($$3) { indent($$2 + 1, $$0); for (int $$18 = 0; $$18 < $$14.length; $$18++) { if ($$18 != 0) $$0.append(',');  if ($$18 % 16 == 0 && $$18 / 16 > 0) { $$0.append('\n'); if ($$18 < $$14.length) indent($$2 + 1, $$0);  } else if ($$18 != 0) { $$0.append(' '); }  $$0.append(String.format(Locale.ROOT, "0x%0" + $$15 + "X", new Object[] { Integer.valueOf($$14[$$18]) })); }  } else { indent($$2 + 1, $$0).append(" // Skipped, supply withBinaryBlobs true"); }  $$0.append('\n'); indent($$2, $$0).append('}');case 10: $$19 = (CompoundTag)$$1; $$20 = Lists.newArrayList($$19.getAllKeys()); Collections.sort($$20); indent($$2, $$0).append('{'); if ($$0.length() - $$0.lastIndexOf("\n") > 2 * ($$2 + 1)) { $$0.append('\n'); indent($$2 + 1, $$0); }  $$21 = $$20.stream().mapToInt(String::length).max().orElse(0); $$22 = Strings.repeat(" ", $$21); for ($$23 = 0; $$23 < $$20.size(); $$23++) { if ($$23 != 0)
/*     */             $$0.append(",\n");  String $$24 = $$20.get($$23); indent($$2 + 1, $$0).append('"').append($$24).append('"').append($$22, 0, $$22.length() - $$24.length()).append(": "); prettyPrint($$0, $$19.get($$24), $$2 + 1, $$3); }  if (!$$20.isEmpty())
/*     */           $$0.append('\n');  indent($$2, $$0).append('}');case 12: $$25 = (LongArrayTag)$$1; $$26 = $$25.getAsLongArray(); $$27 = 0L; for (long $$28 : $$26) { $$27 = Math.max($$27, String.format(Locale.ROOT, "%X", new Object[] { Long.valueOf($$28) }).length()); }  $$29 = $$26.length; indent($$2, $$0).append("long[").append($$29).append("] {\n"); if ($$3) { indent($$2 + 1, $$0); for (int $$30 = 0; $$30 < $$26.length; $$30++) { if ($$30 != 0)
/*     */               $$0.append(',');  if ($$30 % 16 == 0 && $$30 / 16 > 0) { $$0.append('\n'); if ($$30 < $$26.length)
/* 473 */                 indent($$2 + 1, $$0);  } else if ($$30 != 0) { $$0.append(' '); }  $$0.append(String.format(Locale.ROOT, "0x%0" + $$27 + "X", new Object[] { Long.valueOf($$26[$$30]) })); }  } else { indent($$2 + 1, $$0).append(" // Skipped, supply withBinaryBlobs true"); }  $$0.append('\n'); indent($$2, $$0).append('}'); }  $$0.append("<UNKNOWN :(>"); } private static StringBuilder indent(int $$0, StringBuilder $$1) { int $$2 = $$1.lastIndexOf("\n") + 1;
/* 474 */     int $$3 = $$1.length() - $$2;
/*     */     
/* 476 */     for (int $$4 = 0; $$4 < 2 * $$0 - $$3; $$4++) {
/* 477 */       $$1.append(' ');
/*     */     }
/* 479 */     return $$1; }
/*     */ 
/*     */   
/*     */   public static Component toPrettyComponent(Tag $$0) {
/* 483 */     return (new TextComponentTagVisitor("", 0)).visit($$0);
/*     */   }
/*     */   
/*     */   public static String structureToSnbt(CompoundTag $$0) {
/* 487 */     return (new SnbtPrinterTagVisitor()).visit(packStructureTemplate($$0));
/*     */   }
/*     */   
/*     */   public static CompoundTag snbtToStructure(String $$0) throws CommandSyntaxException {
/* 491 */     return unpackStructureTemplate(TagParser.parseTag($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static CompoundTag packStructureTemplate(CompoundTag $$0) {
/*     */     ListTag $$3;
/* 498 */     boolean $$1 = $$0.contains("palettes", 9);
/* 499 */     if ($$1) {
/* 500 */       ListTag $$2 = $$0.getList("palettes", 9).getList(0);
/*     */     } else {
/* 502 */       $$3 = $$0.getList("palette", 10);
/*     */     } 
/*     */     
/* 505 */     Objects.requireNonNull(CompoundTag.class); ListTag $$4 = (ListTag)$$3.stream().map(CompoundTag.class::cast).map(NbtUtils::packBlockState).map(StringTag::valueOf).collect(Collectors.toCollection(ListTag::new));
/*     */     
/* 507 */     $$0.put("palette", $$4);
/*     */ 
/*     */     
/* 510 */     if ($$1) {
/* 511 */       ListTag $$5 = new ListTag();
/* 512 */       ListTag $$6 = $$0.getList("palettes", 9);
/* 513 */       Objects.requireNonNull(ListTag.class); $$6.stream().map(ListTag.class::cast).forEach($$2 -> {
/*     */             CompoundTag $$3 = new CompoundTag();
/*     */             
/*     */             for (int $$4 = 0; $$4 < $$2.size(); $$4++) {
/*     */               $$3.putString($$0.getString($$4), packBlockState($$2.getCompound($$4)));
/*     */             }
/*     */             $$1.add($$3);
/*     */           });
/* 521 */       $$0.put("palettes", $$5);
/*     */     } 
/*     */ 
/*     */     
/* 525 */     if ($$0.contains("entities", 9)) {
/* 526 */       ListTag $$7 = $$0.getList("entities", 10);
/* 527 */       Objects.requireNonNull(CompoundTag.class);
/*     */       
/* 529 */       ListTag $$8 = (ListTag)$$7.stream().map(CompoundTag.class::cast).sorted(Comparator.comparing($$0 -> $$0.getList("pos", 6), YXZ_LISTTAG_DOUBLE_COMPARATOR)).collect(Collectors.toCollection(ListTag::new));
/* 530 */       $$0.put("entities", $$8);
/*     */     } 
/*     */ 
/*     */     
/* 534 */     Objects.requireNonNull(CompoundTag.class);
/*     */ 
/*     */     
/* 537 */     ListTag $$9 = (ListTag)$$0.getList("blocks", 10).stream().map(CompoundTag.class::cast).sorted(Comparator.comparing($$0 -> $$0.getList("pos", 3), YXZ_LISTTAG_INT_COMPARATOR)).peek($$1 -> $$1.putString("state", $$0.getString($$1.getInt("state")))).collect(Collectors.toCollection(ListTag::new));
/*     */     
/* 539 */     $$0.put("data", $$9);
/* 540 */     $$0.remove("blocks");
/* 541 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static CompoundTag unpackStructureTemplate(CompoundTag $$0) {
/* 547 */     ListTag $$1 = $$0.getList("palette", 8);
/*     */ 
/*     */     
/* 550 */     Objects.requireNonNull(StringTag.class);
/*     */     
/* 552 */     Map<String, Tag> $$2 = (Map<String, Tag>)$$1.stream().map(StringTag.class::cast).map(StringTag::getAsString).collect(ImmutableMap.toImmutableMap(Function.identity(), NbtUtils::unpackBlockState));
/*     */     
/* 554 */     if ($$0.contains("palettes", 9)) {
/*     */       
/* 556 */       Objects.requireNonNull(CompoundTag.class); $$0.put("palettes", (Tag)$$0.getList("palettes", 10).stream().map(CompoundTag.class::cast)
/* 557 */           .map($$1 -> {
/*     */               Objects.requireNonNull($$1);
/*     */ 
/*     */ 
/*     */               
/*     */               return (ListTag)$$0.keySet().stream().map($$1::getString).map(NbtUtils::unpackBlockState).collect(Collectors.toCollection(ListTag::new));
/* 563 */             }).collect(Collectors.toCollection(ListTag::new)));
/*     */       
/* 565 */       $$0.remove("palette");
/*     */     } else {
/* 567 */       $$0.put("palette", (Tag)$$2.values().stream().collect(Collectors.toCollection(ListTag::new)));
/*     */     } 
/*     */     
/* 570 */     if ($$0.contains("data", 9)) {
/* 571 */       Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/* 572 */       object2IntOpenHashMap.defaultReturnValue(-1);
/* 573 */       for (int $$4 = 0; $$4 < $$1.size(); $$4++) {
/* 574 */         object2IntOpenHashMap.put($$1.getString($$4), $$4);
/*     */       }
/*     */       
/* 577 */       ListTag $$5 = $$0.getList("data", 10);
/* 578 */       for (int $$6 = 0; $$6 < $$5.size(); $$6++) {
/* 579 */         CompoundTag $$7 = $$5.getCompound($$6);
/* 580 */         String $$8 = $$7.getString("state");
/* 581 */         int $$9 = object2IntOpenHashMap.getInt($$8);
/* 582 */         if ($$9 == -1) {
/* 583 */           throw new IllegalStateException("Entry " + $$8 + " missing from palette");
/*     */         }
/* 585 */         $$7.putInt("state", $$9);
/*     */       } 
/*     */       
/* 588 */       $$0.put("blocks", $$5);
/* 589 */       $$0.remove("data");
/*     */     } 
/*     */     
/* 592 */     return $$0;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static String packBlockState(CompoundTag $$0) {
/* 597 */     StringBuilder $$1 = new StringBuilder($$0.getString("Name"));
/* 598 */     if ($$0.contains("Properties", 10)) {
/* 599 */       CompoundTag $$2 = $$0.getCompound("Properties");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 604 */       String $$3 = $$2.getAllKeys().stream().sorted().map($$1 -> $$1 + ":" + $$1).collect(Collectors.joining(","));
/*     */       
/* 606 */       $$1.append('{').append($$3).append('}');
/*     */     } 
/* 608 */     return $$1.toString();
/*     */   }
/*     */   @VisibleForTesting
/*     */   static CompoundTag unpackBlockState(String $$0) {
/*     */     String $$6;
/* 613 */     CompoundTag $$1 = new CompoundTag();
/* 614 */     int $$2 = $$0.indexOf('{');
/*     */ 
/*     */     
/* 617 */     if ($$2 >= 0) {
/* 618 */       String $$3 = $$0.substring(0, $$2);
/* 619 */       CompoundTag $$4 = new CompoundTag();
/* 620 */       if ($$2 + 2 <= $$0.length()) {
/* 621 */         String $$5 = $$0.substring($$2 + 1, $$0.indexOf('}', $$2));
/* 622 */         COMMA_SPLITTER.split($$5).forEach($$2 -> {
/*     */               List<String> $$3 = COLON_SPLITTER.splitToList($$2);
/*     */               
/*     */               if ($$3.size() == 2) {
/*     */                 $$0.putString($$3.get(0), $$3.get(1));
/*     */               } else {
/*     */                 LOGGER.error("Something went wrong parsing: '{}' -- incorrect gamedata!", $$1);
/*     */               } 
/*     */             });
/* 631 */         $$1.put("Properties", $$4);
/*     */       } 
/*     */     } else {
/* 634 */       $$6 = $$0;
/*     */     } 
/* 636 */     $$1.putString("Name", $$6);
/* 637 */     return $$1;
/*     */   }
/*     */   
/*     */   public static CompoundTag addCurrentDataVersion(CompoundTag $$0) {
/* 641 */     int $$1 = SharedConstants.getCurrentVersion().getDataVersion().getVersion();
/* 642 */     return addDataVersion($$0, $$1);
/*     */   }
/*     */   
/*     */   public static CompoundTag addDataVersion(CompoundTag $$0, int $$1) {
/* 646 */     $$0.putInt("DataVersion", $$1);
/* 647 */     return $$0;
/*     */   }
/*     */   
/*     */   public static int getDataVersion(CompoundTag $$0, int $$1) {
/* 651 */     return $$0.contains("DataVersion", 99) ? $$0.getInt("DataVersion") : $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\NbtUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */