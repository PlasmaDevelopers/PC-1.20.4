/*      */ package net.minecraft.network;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Multimap;
/*      */ import com.google.gson.Gson;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.properties.Property;
/*      */ import com.mojang.authlib.properties.PropertyMap;
/*      */ import com.mojang.datafixers.util.Either;
/*      */ import com.mojang.serialization.Codec;
/*      */ import com.mojang.serialization.DataResult;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import com.mojang.serialization.JsonOps;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufInputStream;
/*      */ import io.netty.buffer.ByteBufOutputStream;
/*      */ import io.netty.handler.codec.DecoderException;
/*      */ import io.netty.handler.codec.EncoderException;
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*      */ import it.unimi.dsi.fastutil.ints.IntList;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import java.security.PublicKey;
/*      */ import java.time.Instant;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.UUID;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.ToIntFunction;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.GlobalPos;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.IdMap;
/*      */ import net.minecraft.core.Registry;
/*      */ import net.minecraft.core.SectionPos;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.EndTag;
/*      */ import net.minecraft.nbt.NbtAccounter;
/*      */ import net.minecraft.nbt.NbtIo;
/*      */ import net.minecraft.nbt.NbtOps;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.ComponentSerialization;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.util.Crypt;
/*      */ import net.minecraft.util.CryptException;
/*      */ import net.minecraft.util.GsonHelper;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import org.joml.Quaternionf;
/*      */ import org.joml.Vector3f;
/*      */ 
/*      */ 
/*      */ public class FriendlyByteBuf
/*      */   extends ByteBuf
/*      */ {
/*      */   public static final int DEFAULT_NBT_QUOTA = 2097152;
/*      */   private final ByteBuf source;
/*      */   public static final short MAX_STRING_LENGTH = 32767;
/*      */   public static final int MAX_COMPONENT_STRING_LENGTH = 262144;
/*      */   private static final int PUBLIC_KEY_SIZE = 256;
/*      */   private static final int MAX_PUBLIC_KEY_HEADER_SIZE = 256;
/*      */   private static final int MAX_PUBLIC_KEY_LENGTH = 512;
/*   99 */   private static final Gson GSON = new Gson();
/*      */   
/*      */   public FriendlyByteBuf(ByteBuf $$0) {
/*  102 */     this.source = $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public <T> T readWithCodecTrusted(DynamicOps<Tag> $$0, Codec<T> $$1) {
/*  118 */     return readWithCodec($$0, $$1, NbtAccounter.unlimitedHeap());
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public <T> T readWithCodec(DynamicOps<Tag> $$0, Codec<T> $$1, NbtAccounter $$2) {
/*  123 */     Tag $$3 = readNbt($$2);
/*  124 */     return (T)Util.getOrThrow($$1.parse($$0, $$3), $$1 -> new DecoderException("Failed to decode: " + $$1 + " " + $$0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public <T> FriendlyByteBuf writeWithCodec(DynamicOps<Tag> $$0, Codec<T> $$1, T $$2) {
/*  135 */     Tag $$3 = (Tag)Util.getOrThrow($$1.encodeStart($$0, $$2), $$1 -> new EncoderException("Failed to encode: " + $$1 + " " + $$0));
/*  136 */     writeNbt($$3);
/*  137 */     return this;
/*      */   }
/*      */   
/*      */   public <T> T readJsonWithCodec(Codec<T> $$0) {
/*  141 */     JsonElement $$1 = (JsonElement)GsonHelper.fromJson(GSON, readUtf(), JsonElement.class);
/*  142 */     DataResult<T> $$2 = $$0.parse((DynamicOps)JsonOps.INSTANCE, $$1);
/*  143 */     return (T)Util.getOrThrow($$2, $$0 -> new DecoderException("Failed to decode json: " + $$0));
/*      */   }
/*      */   
/*      */   public <T> void writeJsonWithCodec(Codec<T> $$0, T $$1) {
/*  147 */     DataResult<JsonElement> $$2 = $$0.encodeStart((DynamicOps)JsonOps.INSTANCE, $$1);
/*  148 */     writeUtf(GSON.toJson((JsonElement)Util.getOrThrow($$2, $$1 -> new EncoderException("Failed to encode: " + $$1 + " " + $$0))));
/*      */   }
/*      */   
/*      */   public <T> void writeId(IdMap<T> $$0, T $$1) {
/*  152 */     int $$2 = $$0.getId($$1);
/*  153 */     if ($$2 == -1) {
/*  154 */       throw new IllegalArgumentException("Can't find id for '" + $$1 + "' in map " + $$0);
/*      */     }
/*  156 */     writeVarInt($$2);
/*      */   }
/*      */   public <T> void writeId(IdMap<Holder<T>> $$0, Holder<T> $$1, Writer<T> $$2) {
/*      */     int $$3;
/*  160 */     switch ($$1.kind()) {
/*      */       case REFERENCE:
/*  162 */         $$3 = $$0.getId($$1);
/*  163 */         if ($$3 == -1) {
/*  164 */           throw new IllegalArgumentException("Can't find id for '" + $$1.value() + "' in map " + $$0);
/*      */         }
/*  166 */         writeVarInt($$3 + 1);
/*      */         break;
/*      */       case DIRECT:
/*  169 */         writeVarInt(0);
/*  170 */         $$2.accept(this, (T)$$1.value());
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public <T> T readById(IdMap<T> $$0) {
/*  177 */     int $$1 = readVarInt();
/*  178 */     return (T)$$0.byId($$1);
/*      */   }
/*      */   
/*      */   public <T> Holder<T> readById(IdMap<Holder<T>> $$0, Reader<T> $$1) {
/*  182 */     int $$2 = readVarInt();
/*  183 */     if ($$2 == 0) {
/*  184 */       return Holder.direct($$1.apply(this));
/*      */     }
/*      */     
/*  187 */     Holder<T> $$3 = (Holder<T>)$$0.byId($$2 - 1);
/*  188 */     if ($$3 == null) {
/*  189 */       throw new IllegalArgumentException("Can't find element with id " + $$2);
/*      */     }
/*  191 */     return $$3;
/*      */   }
/*      */   
/*      */   public static <T> IntFunction<T> limitValue(IntFunction<T> $$0, int $$1) {
/*  195 */     return $$2 -> {
/*      */         if ($$2 > $$0) {
/*      */           throw new DecoderException("Value " + $$2 + " is larger than limit " + $$0);
/*      */         }
/*      */         return $$1.apply($$2);
/*      */       };
/*      */   }
/*      */   
/*      */   public <T, C extends Collection<T>> C readCollection(IntFunction<C> $$0, Reader<T> $$1) {
/*  204 */     int $$2 = readVarInt();
/*  205 */     Collection collection = (Collection)$$0.apply($$2);
/*  206 */     for (int $$4 = 0; $$4 < $$2; $$4++) {
/*  207 */       collection.add($$1.apply(this));
/*      */     }
/*  209 */     return (C)collection;
/*      */   }
/*      */   
/*      */   public <T> void writeCollection(Collection<T> $$0, Writer<T> $$1) {
/*  213 */     writeVarInt($$0.size());
/*  214 */     for (T $$2 : $$0) {
/*  215 */       $$1.accept(this, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   public <T> List<T> readList(Reader<T> $$0) {
/*  220 */     return readCollection(Lists::newArrayListWithCapacity, $$0);
/*      */   }
/*      */   
/*      */   public IntList readIntIdList() {
/*  224 */     int $$0 = readVarInt();
/*  225 */     IntArrayList intArrayList = new IntArrayList();
/*  226 */     for (int $$2 = 0; $$2 < $$0; $$2++) {
/*  227 */       intArrayList.add(readVarInt());
/*      */     }
/*  229 */     return (IntList)intArrayList;
/*      */   }
/*      */   
/*      */   public void writeIntIdList(IntList $$0) {
/*  233 */     writeVarInt($$0.size());
/*  234 */     $$0.forEach(this::writeVarInt);
/*      */   }
/*      */   
/*      */   public <K, V, M extends Map<K, V>> M readMap(IntFunction<M> $$0, Reader<K> $$1, Reader<V> $$2) {
/*  238 */     int $$3 = readVarInt();
/*  239 */     Map<K, V> map = (Map)$$0.apply($$3);
/*  240 */     for (int $$5 = 0; $$5 < $$3; $$5++) {
/*  241 */       K $$6 = $$1.apply(this);
/*  242 */       V $$7 = $$2.apply(this);
/*  243 */       map.put($$6, $$7);
/*      */     } 
/*  245 */     return (M)map;
/*      */   }
/*      */   
/*      */   public <K, V> Map<K, V> readMap(Reader<K> $$0, Reader<V> $$1) {
/*  249 */     return readMap(Maps::newHashMapWithExpectedSize, $$0, $$1);
/*      */   }
/*      */   
/*      */   public <K, V> void writeMap(Map<K, V> $$0, Writer<K> $$1, Writer<V> $$2) {
/*  253 */     writeVarInt($$0.size());
/*  254 */     $$0.forEach(($$2, $$3) -> {
/*      */           $$0.accept(this, $$2);
/*      */           $$1.accept(this, $$3);
/*      */         });
/*      */   }
/*      */   
/*      */   public void readWithCount(Consumer<FriendlyByteBuf> $$0) {
/*  261 */     int $$1 = readVarInt();
/*  262 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/*  263 */       $$0.accept(this);
/*      */     }
/*      */   }
/*      */   
/*      */   public <E extends Enum<E>> void writeEnumSet(EnumSet<E> $$0, Class<E> $$1) {
/*  268 */     Enum[] arrayOfEnum = (Enum[])$$1.getEnumConstants();
/*  269 */     BitSet $$3 = new BitSet(arrayOfEnum.length);
/*  270 */     for (int $$4 = 0; $$4 < arrayOfEnum.length; $$4++) {
/*  271 */       $$3.set($$4, $$0.contains(arrayOfEnum[$$4]));
/*      */     }
/*  273 */     writeFixedBitSet($$3, arrayOfEnum.length);
/*      */   }
/*      */   
/*      */   public <E extends Enum<E>> EnumSet<E> readEnumSet(Class<E> $$0) {
/*  277 */     Enum[] arrayOfEnum = (Enum[])$$0.getEnumConstants();
/*  278 */     BitSet $$2 = readFixedBitSet(arrayOfEnum.length);
/*  279 */     EnumSet<E> $$3 = EnumSet.noneOf($$0);
/*  280 */     for (int $$4 = 0; $$4 < arrayOfEnum.length; $$4++) {
/*  281 */       if ($$2.get($$4)) {
/*  282 */         $$3.add((E)arrayOfEnum[$$4]);
/*      */       }
/*      */     } 
/*  285 */     return $$3;
/*      */   }
/*      */   
/*      */   public <T> void writeOptional(Optional<T> $$0, Writer<T> $$1) {
/*  289 */     if ($$0.isPresent()) {
/*  290 */       writeBoolean(true);
/*  291 */       $$1.accept(this, $$0.get());
/*      */     } else {
/*  293 */       writeBoolean(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T> Optional<T> readOptional(Reader<T> $$0) {
/*  298 */     if (readBoolean()) {
/*  299 */       return Optional.of($$0.apply(this));
/*      */     }
/*  301 */     return Optional.empty();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public <T> T readNullable(Reader<T> $$0) {
/*  306 */     if (readBoolean()) {
/*  307 */       return $$0.apply(this);
/*      */     }
/*  309 */     return null;
/*      */   }
/*      */   
/*      */   public <T> void writeNullable(@Nullable T $$0, Writer<T> $$1) {
/*  313 */     if ($$0 != null) {
/*  314 */       writeBoolean(true);
/*  315 */       $$1.accept(this, $$0);
/*      */     } else {
/*  317 */       writeBoolean(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <L, R> void writeEither(Either<L, R> $$0, Writer<L> $$1, Writer<R> $$2) {
/*  322 */     $$0.ifLeft($$1 -> {
/*      */           writeBoolean(true);
/*      */           $$0.accept(this, $$1);
/*  325 */         }).ifRight($$1 -> {
/*      */           writeBoolean(false);
/*      */           $$0.accept(this, $$1);
/*      */         });
/*      */   }
/*      */   
/*      */   public <L, R> Either<L, R> readEither(Reader<L> $$0, Reader<R> $$1) {
/*  332 */     if (readBoolean()) {
/*  333 */       return Either.left($$0.apply(this));
/*      */     }
/*  335 */     return Either.right($$1.apply(this));
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] readByteArray() {
/*  340 */     return readByteArray(readableBytes());
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeByteArray(byte[] $$0) {
/*  344 */     writeVarInt($$0.length);
/*  345 */     writeBytes($$0);
/*      */     
/*  347 */     return this;
/*      */   }
/*      */   
/*      */   public byte[] readByteArray(int $$0) {
/*  351 */     int $$1 = readVarInt();
/*  352 */     if ($$1 > $$0) {
/*  353 */       throw new DecoderException("ByteArray with size " + $$1 + " is bigger than allowed " + $$0);
/*      */     }
/*  355 */     byte[] $$2 = new byte[$$1];
/*  356 */     readBytes($$2);
/*      */     
/*  358 */     return $$2;
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeVarIntArray(int[] $$0) {
/*  362 */     writeVarInt($$0.length);
/*      */     
/*  364 */     for (int $$1 : $$0) {
/*  365 */       writeVarInt($$1);
/*      */     }
/*      */     
/*  368 */     return this;
/*      */   }
/*      */   
/*      */   public int[] readVarIntArray() {
/*  372 */     return readVarIntArray(readableBytes());
/*      */   }
/*      */   
/*      */   public int[] readVarIntArray(int $$0) {
/*  376 */     int $$1 = readVarInt();
/*  377 */     if ($$1 > $$0) {
/*  378 */       throw new DecoderException("VarIntArray with size " + $$1 + " is bigger than allowed " + $$0);
/*      */     }
/*  380 */     int[] $$2 = new int[$$1];
/*      */     
/*  382 */     for (int $$3 = 0; $$3 < $$2.length; $$3++) {
/*  383 */       $$2[$$3] = readVarInt();
/*      */     }
/*      */     
/*  386 */     return $$2;
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeLongArray(long[] $$0) {
/*  390 */     writeVarInt($$0.length);
/*      */     
/*  392 */     for (long $$1 : $$0) {
/*  393 */       writeLong($$1);
/*      */     }
/*      */     
/*  396 */     return this;
/*      */   }
/*      */   
/*      */   public long[] readLongArray() {
/*  400 */     return readLongArray(null);
/*      */   }
/*      */   
/*      */   public long[] readLongArray(@Nullable long[] $$0) {
/*  404 */     return readLongArray($$0, readableBytes() / 8);
/*      */   }
/*      */   
/*      */   public long[] readLongArray(@Nullable long[] $$0, int $$1) {
/*  408 */     int $$2 = readVarInt();
/*  409 */     if ($$0 == null || $$0.length != $$2) {
/*  410 */       if ($$2 > $$1) {
/*  411 */         throw new DecoderException("LongArray with size " + $$2 + " is bigger than allowed " + $$1);
/*      */       }
/*  413 */       $$0 = new long[$$2];
/*      */     } 
/*      */     
/*  416 */     for (int $$3 = 0; $$3 < $$0.length; $$3++) {
/*  417 */       $$0[$$3] = readLong();
/*      */     }
/*      */     
/*  420 */     return $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos readBlockPos() {
/*  428 */     return BlockPos.of(readLong());
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeBlockPos(BlockPos $$0) {
/*  432 */     writeLong($$0.asLong());
/*  433 */     return this;
/*      */   }
/*      */   
/*      */   public ChunkPos readChunkPos() {
/*  437 */     return new ChunkPos(readLong());
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeChunkPos(ChunkPos $$0) {
/*  441 */     writeLong($$0.toLong());
/*  442 */     return this;
/*      */   }
/*      */   
/*      */   public SectionPos readSectionPos() {
/*  446 */     return SectionPos.of(readLong());
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeSectionPos(SectionPos $$0) {
/*  450 */     writeLong($$0.asLong());
/*  451 */     return this;
/*      */   }
/*      */   
/*      */   public GlobalPos readGlobalPos() {
/*  455 */     ResourceKey<Level> $$0 = readResourceKey(Registries.DIMENSION);
/*  456 */     BlockPos $$1 = readBlockPos();
/*  457 */     return GlobalPos.of($$0, $$1);
/*      */   }
/*      */   
/*      */   public void writeGlobalPos(GlobalPos $$0) {
/*  461 */     writeResourceKey($$0.dimension());
/*  462 */     writeBlockPos($$0.pos());
/*      */   }
/*      */   
/*      */   public Vector3f readVector3f() {
/*  466 */     return new Vector3f(readFloat(), readFloat(), readFloat());
/*      */   }
/*      */   
/*      */   public void writeVector3f(Vector3f $$0) {
/*  470 */     writeFloat($$0.x());
/*  471 */     writeFloat($$0.y());
/*  472 */     writeFloat($$0.z());
/*      */   }
/*      */   
/*      */   public Quaternionf readQuaternion() {
/*  476 */     return new Quaternionf(readFloat(), readFloat(), readFloat(), readFloat());
/*      */   }
/*      */   
/*      */   public void writeQuaternion(Quaternionf $$0) {
/*  480 */     writeFloat($$0.x);
/*  481 */     writeFloat($$0.y);
/*  482 */     writeFloat($$0.z);
/*  483 */     writeFloat($$0.w);
/*      */   }
/*      */   
/*      */   public Vec3 readVec3() {
/*  487 */     return new Vec3(readDouble(), readDouble(), readDouble());
/*      */   }
/*      */   
/*      */   public void writeVec3(Vec3 $$0) {
/*  491 */     writeDouble($$0.x());
/*  492 */     writeDouble($$0.y());
/*  493 */     writeDouble($$0.z());
/*      */   }
/*      */   
/*      */   public Component readComponent() {
/*  497 */     return readWithCodec((DynamicOps<Tag>)NbtOps.INSTANCE, ComponentSerialization.CODEC, NbtAccounter.create(2097152L));
/*      */   }
/*      */   
/*      */   public Component readComponentTrusted() {
/*  501 */     return readWithCodecTrusted((DynamicOps<Tag>)NbtOps.INSTANCE, ComponentSerialization.CODEC);
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeComponent(Component $$0) {
/*  505 */     return writeWithCodec((DynamicOps<Tag>)NbtOps.INSTANCE, ComponentSerialization.CODEC, $$0);
/*      */   }
/*      */   
/*      */   public <T extends Enum<T>> T readEnum(Class<T> $$0) {
/*  509 */     return (T)((Enum[])$$0.getEnumConstants())[readVarInt()];
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeEnum(Enum<?> $$0) {
/*  513 */     return writeVarInt($$0.ordinal());
/*      */   }
/*      */   
/*      */   public <T> T readById(IntFunction<T> $$0) {
/*  517 */     int $$1 = readVarInt();
/*  518 */     return $$0.apply($$1);
/*      */   }
/*      */   
/*      */   public <T> FriendlyByteBuf writeById(ToIntFunction<T> $$0, T $$1) {
/*  522 */     int $$2 = $$0.applyAsInt($$1);
/*  523 */     return writeVarInt($$2);
/*      */   }
/*      */   
/*      */   public int readVarInt() {
/*  527 */     return VarInt.read(this.source);
/*      */   }
/*      */   
/*      */   public long readVarLong() {
/*  531 */     return VarLong.read(this.source);
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeUUID(UUID $$0) {
/*  535 */     writeLong($$0.getMostSignificantBits());
/*  536 */     writeLong($$0.getLeastSignificantBits());
/*      */     
/*  538 */     return this;
/*      */   }
/*      */   
/*      */   public UUID readUUID() {
/*  542 */     return new UUID(readLong(), readLong());
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeVarInt(int $$0) {
/*  546 */     VarInt.write(this.source, $$0);
/*  547 */     return this;
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeVarLong(long $$0) {
/*  551 */     VarLong.write(this.source, $$0);
/*  552 */     return this;
/*      */   }
/*      */   public FriendlyByteBuf writeNbt(@Nullable Tag $$0) {
/*      */     EndTag endTag;
/*  556 */     if ($$0 == null) {
/*  557 */       endTag = EndTag.INSTANCE;
/*      */     }
/*      */     
/*      */     try {
/*  561 */       NbtIo.writeAnyTag((Tag)endTag, (DataOutput)new ByteBufOutputStream(this));
/*  562 */     } catch (IOException $$1) {
/*  563 */       throw new EncoderException($$1);
/*      */     } 
/*      */     
/*  566 */     return this;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public CompoundTag readNbt() {
/*  571 */     Tag $$0 = readNbt(NbtAccounter.create(2097152L));
/*  572 */     if ($$0 == null || $$0 instanceof CompoundTag) {
/*  573 */       return (CompoundTag)$$0;
/*      */     }
/*  575 */     throw new DecoderException("Not a compound tag: " + $$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Tag readNbt(NbtAccounter $$0) {
/*      */     try {
/*  581 */       Tag $$1 = NbtIo.readAnyTag((DataInput)new ByteBufInputStream(this), $$0);
/*  582 */       if ($$1.getId() == 0) {
/*  583 */         return null;
/*      */       }
/*  585 */       return $$1;
/*  586 */     } catch (IOException $$2) {
/*  587 */       throw new EncoderException($$2);
/*      */     } 
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeItem(ItemStack $$0) {
/*  592 */     if ($$0.isEmpty()) {
/*  593 */       writeBoolean(false);
/*      */     } else {
/*  595 */       writeBoolean(true);
/*  596 */       Item $$1 = $$0.getItem();
/*  597 */       writeId((IdMap<Item>)BuiltInRegistries.ITEM, $$1);
/*  598 */       writeByte($$0.getCount());
/*      */       
/*  600 */       CompoundTag $$2 = null;
/*  601 */       if ($$1.canBeDepleted() || $$1.shouldOverrideMultiplayerNbt()) {
/*  602 */         $$2 = $$0.getTag();
/*      */       }
/*  604 */       writeNbt((Tag)$$2);
/*      */     } 
/*      */     
/*  607 */     return this;
/*      */   }
/*      */   
/*      */   public ItemStack readItem() {
/*  611 */     if (!readBoolean()) {
/*  612 */       return ItemStack.EMPTY;
/*      */     }
/*      */     
/*  615 */     Item $$0 = readById((IdMap<Item>)BuiltInRegistries.ITEM);
/*  616 */     int $$1 = readByte();
/*      */     
/*  618 */     ItemStack $$2 = new ItemStack((ItemLike)$$0, $$1);
/*  619 */     $$2.setTag(readNbt());
/*      */     
/*  621 */     return $$2;
/*      */   }
/*      */   
/*      */   public String readUtf() {
/*  625 */     return readUtf(32767);
/*      */   }
/*      */   
/*      */   public String readUtf(int $$0) {
/*  629 */     return Utf8String.read(this.source, $$0);
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeUtf(String $$0) {
/*  633 */     return writeUtf($$0, 32767);
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeUtf(String $$0, int $$1) {
/*  637 */     Utf8String.write(this.source, $$0, $$1);
/*  638 */     return this;
/*      */   }
/*      */   
/*      */   public ResourceLocation readResourceLocation() {
/*  642 */     return new ResourceLocation(readUtf(32767));
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeResourceLocation(ResourceLocation $$0) {
/*  646 */     writeUtf($$0.toString());
/*  647 */     return this;
/*      */   }
/*      */   
/*      */   public <T> ResourceKey<T> readResourceKey(ResourceKey<? extends Registry<T>> $$0) {
/*  651 */     ResourceLocation $$1 = readResourceLocation();
/*  652 */     return ResourceKey.create($$0, $$1);
/*      */   }
/*      */   
/*      */   public void writeResourceKey(ResourceKey<?> $$0) {
/*  656 */     writeResourceLocation($$0.location());
/*      */   }
/*      */   
/*      */   public <T> ResourceKey<? extends Registry<T>> readRegistryKey() {
/*  660 */     ResourceLocation $$0 = readResourceLocation();
/*  661 */     return ResourceKey.createRegistryKey($$0);
/*      */   }
/*      */   
/*      */   public Date readDate() {
/*  665 */     return new Date(readLong());
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writeDate(Date $$0) {
/*  669 */     writeLong($$0.getTime());
/*  670 */     return this;
/*      */   }
/*      */   
/*      */   public Instant readInstant() {
/*  674 */     return Instant.ofEpochMilli(readLong());
/*      */   }
/*      */   
/*      */   public void writeInstant(Instant $$0) {
/*  678 */     writeLong($$0.toEpochMilli());
/*      */   }
/*      */   
/*      */   public PublicKey readPublicKey() {
/*      */     try {
/*  683 */       return Crypt.byteToPublicKey(readByteArray(512));
/*  684 */     } catch (CryptException $$0) {
/*  685 */       throw new DecoderException("Malformed public key bytes", $$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public FriendlyByteBuf writePublicKey(PublicKey $$0) {
/*  690 */     writeByteArray($$0.getEncoded());
/*  691 */     return this;
/*      */   }
/*      */   
/*      */   public BlockHitResult readBlockHitResult() {
/*  695 */     BlockPos $$0 = readBlockPos();
/*  696 */     Direction $$1 = readEnum(Direction.class);
/*  697 */     float $$2 = readFloat();
/*  698 */     float $$3 = readFloat();
/*  699 */     float $$4 = readFloat();
/*  700 */     boolean $$5 = readBoolean();
/*      */     
/*  702 */     return new BlockHitResult(new Vec3($$0
/*  703 */           .getX() + $$2, $$0
/*  704 */           .getY() + $$3, $$0
/*  705 */           .getZ() + $$4), $$1, $$0, $$5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBlockHitResult(BlockHitResult $$0) {
/*  713 */     BlockPos $$1 = $$0.getBlockPos();
/*  714 */     writeBlockPos($$1);
/*  715 */     writeEnum((Enum<?>)$$0.getDirection());
/*  716 */     Vec3 $$2 = $$0.getLocation();
/*  717 */     writeFloat((float)($$2.x - $$1.getX()));
/*  718 */     writeFloat((float)($$2.y - $$1.getY()));
/*  719 */     writeFloat((float)($$2.z - $$1.getZ()));
/*  720 */     writeBoolean($$0.isInside());
/*      */   }
/*      */   
/*      */   public BitSet readBitSet() {
/*  724 */     return BitSet.valueOf(readLongArray());
/*      */   }
/*      */   
/*      */   public void writeBitSet(BitSet $$0) {
/*  728 */     writeLongArray($$0.toLongArray());
/*      */   }
/*      */   
/*      */   public BitSet readFixedBitSet(int $$0) {
/*  732 */     byte[] $$1 = new byte[Mth.positiveCeilDiv($$0, 8)];
/*  733 */     readBytes($$1);
/*  734 */     return BitSet.valueOf($$1);
/*      */   }
/*      */   
/*      */   public void writeFixedBitSet(BitSet $$0, int $$1) {
/*  738 */     if ($$0.length() > $$1) {
/*  739 */       throw new EncoderException("BitSet is larger than expected size (" + $$0.length() + ">" + $$1 + ")");
/*      */     }
/*  741 */     byte[] $$2 = $$0.toByteArray();
/*  742 */     writeBytes(Arrays.copyOf($$2, Mth.positiveCeilDiv($$1, 8)));
/*      */   }
/*      */   
/*      */   public GameProfile readGameProfile() {
/*  746 */     UUID $$0 = readUUID();
/*  747 */     String $$1 = readUtf(16);
/*  748 */     GameProfile $$2 = new GameProfile($$0, $$1);
/*  749 */     $$2.getProperties().putAll((Multimap)readGameProfileProperties());
/*  750 */     return $$2;
/*      */   }
/*      */   
/*      */   public void writeGameProfile(GameProfile $$0) {
/*  754 */     writeUUID($$0.getId());
/*  755 */     writeUtf($$0.getName());
/*  756 */     writeGameProfileProperties($$0.getProperties());
/*      */   }
/*      */   
/*      */   public PropertyMap readGameProfileProperties() {
/*  760 */     PropertyMap $$0 = new PropertyMap();
/*  761 */     readWithCount($$1 -> {
/*      */           Property $$2 = readProperty();
/*      */           $$0.put($$2.name(), $$2);
/*      */         });
/*  765 */     return $$0;
/*      */   }
/*      */   
/*      */   public void writeGameProfileProperties(PropertyMap $$0) {
/*  769 */     writeCollection($$0.values(), FriendlyByteBuf::writeProperty);
/*      */   }
/*      */   
/*      */   public Property readProperty() {
/*  773 */     String $$0 = readUtf();
/*  774 */     String $$1 = readUtf();
/*  775 */     String $$2 = readNullable(FriendlyByteBuf::readUtf);
/*  776 */     return new Property($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public void writeProperty(Property $$0) {
/*  780 */     writeUtf($$0.name());
/*  781 */     writeUtf($$0.value());
/*  782 */     writeNullable($$0.signature(), FriendlyByteBuf::writeUtf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isContiguous() {
/*  789 */     return this.source.isContiguous();
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxFastWritableBytes() {
/*  794 */     return this.source.maxFastWritableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  799 */     return this.source.capacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf capacity(int $$0) {
/*  804 */     this.source.capacity($$0);
/*  805 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*  810 */     return this.source.maxCapacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*  815 */     return this.source.alloc();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*  820 */     return this.source.order();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder $$0) {
/*  825 */     return this.source.order($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/*  830 */     return this.source;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  835 */     return this.source.isDirect();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/*  840 */     return this.source.isReadOnly();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf asReadOnly() {
/*  845 */     return this.source.asReadOnly();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*  850 */     return this.source.readerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf readerIndex(int $$0) {
/*  855 */     this.source.readerIndex($$0);
/*  856 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/*  861 */     return this.source.writerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writerIndex(int $$0) {
/*  866 */     this.source.writerIndex($$0);
/*  867 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setIndex(int $$0, int $$1) {
/*  872 */     this.source.setIndex($$0, $$1);
/*  873 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  878 */     return this.source.readableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  883 */     return this.source.writableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  888 */     return this.source.maxWritableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  893 */     return this.source.isReadable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int $$0) {
/*  898 */     return this.source.isReadable($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  903 */     return this.source.isWritable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int $$0) {
/*  908 */     return this.source.isWritable($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf clear() {
/*  913 */     this.source.clear();
/*  914 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf markReaderIndex() {
/*  919 */     this.source.markReaderIndex();
/*  920 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf resetReaderIndex() {
/*  925 */     this.source.resetReaderIndex();
/*  926 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf markWriterIndex() {
/*  931 */     this.source.markWriterIndex();
/*  932 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf resetWriterIndex() {
/*  937 */     this.source.resetWriterIndex();
/*  938 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf discardReadBytes() {
/*  943 */     this.source.discardReadBytes();
/*  944 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf discardSomeReadBytes() {
/*  949 */     this.source.discardSomeReadBytes();
/*  950 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf ensureWritable(int $$0) {
/*  955 */     this.source.ensureWritable($$0);
/*  956 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int $$0, boolean $$1) {
/*  961 */     return this.source.ensureWritable($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int $$0) {
/*  966 */     return this.source.getBoolean($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int $$0) {
/*  971 */     return this.source.getByte($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int $$0) {
/*  976 */     return this.source.getUnsignedByte($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int $$0) {
/*  981 */     return this.source.getShort($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShortLE(int $$0) {
/*  986 */     return this.source.getShortLE($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int $$0) {
/*  991 */     return this.source.getUnsignedShort($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShortLE(int $$0) {
/*  996 */     return this.source.getUnsignedShortLE($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMedium(int $$0) {
/* 1001 */     return this.source.getMedium($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMediumLE(int $$0) {
/* 1006 */     return this.source.getMediumLE($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int $$0) {
/* 1011 */     return this.source.getUnsignedMedium($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMediumLE(int $$0) {
/* 1016 */     return this.source.getUnsignedMediumLE($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int $$0) {
/* 1021 */     return this.source.getInt($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getIntLE(int $$0) {
/* 1026 */     return this.source.getIntLE($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int $$0) {
/* 1031 */     return this.source.getUnsignedInt($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedIntLE(int $$0) {
/* 1036 */     return this.source.getUnsignedIntLE($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int $$0) {
/* 1041 */     return this.source.getLong($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLongLE(int $$0) {
/* 1046 */     return this.source.getLongLE($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(int $$0) {
/* 1051 */     return this.source.getChar($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int $$0) {
/* 1056 */     return this.source.getFloat($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int $$0) {
/* 1061 */     return this.source.getDouble($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf getBytes(int $$0, ByteBuf $$1) {
/* 1066 */     this.source.getBytes($$0, $$1);
/* 1067 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf getBytes(int $$0, ByteBuf $$1, int $$2) {
/* 1072 */     this.source.getBytes($$0, $$1, $$2);
/* 1073 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf getBytes(int $$0, ByteBuf $$1, int $$2, int $$3) {
/* 1078 */     this.source.getBytes($$0, $$1, $$2, $$3);
/* 1079 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf getBytes(int $$0, byte[] $$1) {
/* 1084 */     this.source.getBytes($$0, $$1);
/* 1085 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf getBytes(int $$0, byte[] $$1, int $$2, int $$3) {
/* 1090 */     this.source.getBytes($$0, $$1, $$2, $$3);
/* 1091 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf getBytes(int $$0, ByteBuffer $$1) {
/* 1096 */     this.source.getBytes($$0, $$1);
/* 1097 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf getBytes(int $$0, OutputStream $$1, int $$2) throws IOException {
/* 1102 */     this.source.getBytes($$0, $$1, $$2);
/* 1103 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int $$0, GatheringByteChannel $$1, int $$2) throws IOException {
/* 1108 */     return this.source.getBytes($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int $$0, FileChannel $$1, long $$2, int $$3) throws IOException {
/* 1113 */     return this.source.getBytes($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence getCharSequence(int $$0, int $$1, Charset $$2) {
/* 1118 */     return this.source.getCharSequence($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setBoolean(int $$0, boolean $$1) {
/* 1123 */     this.source.setBoolean($$0, $$1);
/* 1124 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setByte(int $$0, int $$1) {
/* 1129 */     this.source.setByte($$0, $$1);
/* 1130 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setShort(int $$0, int $$1) {
/* 1135 */     this.source.setShort($$0, $$1);
/* 1136 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setShortLE(int $$0, int $$1) {
/* 1141 */     this.source.setShortLE($$0, $$1);
/* 1142 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setMedium(int $$0, int $$1) {
/* 1147 */     this.source.setMedium($$0, $$1);
/* 1148 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setMediumLE(int $$0, int $$1) {
/* 1153 */     this.source.setMediumLE($$0, $$1);
/* 1154 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setInt(int $$0, int $$1) {
/* 1159 */     this.source.setInt($$0, $$1);
/* 1160 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setIntLE(int $$0, int $$1) {
/* 1165 */     this.source.setIntLE($$0, $$1);
/* 1166 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setLong(int $$0, long $$1) {
/* 1171 */     this.source.setLong($$0, $$1);
/* 1172 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setLongLE(int $$0, long $$1) {
/* 1177 */     this.source.setLongLE($$0, $$1);
/* 1178 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setChar(int $$0, int $$1) {
/* 1183 */     this.source.setChar($$0, $$1);
/* 1184 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setFloat(int $$0, float $$1) {
/* 1189 */     this.source.setFloat($$0, $$1);
/* 1190 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setDouble(int $$0, double $$1) {
/* 1195 */     this.source.setDouble($$0, $$1);
/* 1196 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setBytes(int $$0, ByteBuf $$1) {
/* 1201 */     this.source.setBytes($$0, $$1);
/* 1202 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setBytes(int $$0, ByteBuf $$1, int $$2) {
/* 1207 */     this.source.setBytes($$0, $$1, $$2);
/* 1208 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setBytes(int $$0, ByteBuf $$1, int $$2, int $$3) {
/* 1213 */     this.source.setBytes($$0, $$1, $$2, $$3);
/* 1214 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setBytes(int $$0, byte[] $$1) {
/* 1219 */     this.source.setBytes($$0, $$1);
/* 1220 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setBytes(int $$0, byte[] $$1, int $$2, int $$3) {
/* 1225 */     this.source.setBytes($$0, $$1, $$2, $$3);
/* 1226 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setBytes(int $$0, ByteBuffer $$1) {
/* 1231 */     this.source.setBytes($$0, $$1);
/* 1232 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int $$0, InputStream $$1, int $$2) throws IOException {
/* 1237 */     return this.source.setBytes($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int $$0, ScatteringByteChannel $$1, int $$2) throws IOException {
/* 1242 */     return this.source.setBytes($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int $$0, FileChannel $$1, long $$2, int $$3) throws IOException {
/* 1247 */     return this.source.setBytes($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf setZero(int $$0, int $$1) {
/* 1252 */     this.source.setZero($$0, $$1);
/* 1253 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setCharSequence(int $$0, CharSequence $$1, Charset $$2) {
/* 1258 */     return this.source.setCharSequence($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/* 1263 */     return this.source.readBoolean();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/* 1268 */     return this.source.readByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/* 1273 */     return this.source.readUnsignedByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/* 1278 */     return this.source.readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShortLE() {
/* 1283 */     return this.source.readShortLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/* 1288 */     return this.source.readUnsignedShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShortLE() {
/* 1293 */     return this.source.readUnsignedShortLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/* 1298 */     return this.source.readMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMediumLE() {
/* 1303 */     return this.source.readMediumLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/* 1308 */     return this.source.readUnsignedMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMediumLE() {
/* 1313 */     return this.source.readUnsignedMediumLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/* 1318 */     return this.source.readInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readIntLE() {
/* 1323 */     return this.source.readIntLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/* 1328 */     return this.source.readUnsignedInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedIntLE() {
/* 1333 */     return this.source.readUnsignedIntLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/* 1338 */     return this.source.readLong();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLongLE() {
/* 1343 */     return this.source.readLongLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/* 1348 */     return this.source.readChar();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/* 1353 */     return this.source.readFloat();
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/* 1358 */     return this.source.readDouble();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int $$0) {
/* 1363 */     return this.source.readBytes($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int $$0) {
/* 1368 */     return this.source.readSlice($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readRetainedSlice(int $$0) {
/* 1373 */     return this.source.readRetainedSlice($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf readBytes(ByteBuf $$0) {
/* 1378 */     this.source.readBytes($$0);
/* 1379 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf readBytes(ByteBuf $$0, int $$1) {
/* 1384 */     this.source.readBytes($$0, $$1);
/* 1385 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf readBytes(ByteBuf $$0, int $$1, int $$2) {
/* 1390 */     this.source.readBytes($$0, $$1, $$2);
/* 1391 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf readBytes(byte[] $$0) {
/* 1396 */     this.source.readBytes($$0);
/* 1397 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf readBytes(byte[] $$0, int $$1, int $$2) {
/* 1402 */     this.source.readBytes($$0, $$1, $$2);
/* 1403 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf readBytes(ByteBuffer $$0) {
/* 1408 */     this.source.readBytes($$0);
/* 1409 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf readBytes(OutputStream $$0, int $$1) throws IOException {
/* 1414 */     this.source.readBytes($$0, $$1);
/* 1415 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel $$0, int $$1) throws IOException {
/* 1420 */     return this.source.readBytes($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence readCharSequence(int $$0, Charset $$1) {
/* 1425 */     return this.source.readCharSequence($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(FileChannel $$0, long $$1, int $$2) throws IOException {
/* 1430 */     return this.source.readBytes($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf skipBytes(int $$0) {
/* 1435 */     this.source.skipBytes($$0);
/* 1436 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeBoolean(boolean $$0) {
/* 1441 */     this.source.writeBoolean($$0);
/* 1442 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeByte(int $$0) {
/* 1447 */     this.source.writeByte($$0);
/* 1448 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeShort(int $$0) {
/* 1453 */     this.source.writeShort($$0);
/* 1454 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeShortLE(int $$0) {
/* 1459 */     this.source.writeShortLE($$0);
/* 1460 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeMedium(int $$0) {
/* 1465 */     this.source.writeMedium($$0);
/* 1466 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeMediumLE(int $$0) {
/* 1471 */     this.source.writeMediumLE($$0);
/* 1472 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeInt(int $$0) {
/* 1477 */     this.source.writeInt($$0);
/* 1478 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeIntLE(int $$0) {
/* 1483 */     this.source.writeIntLE($$0);
/* 1484 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeLong(long $$0) {
/* 1489 */     this.source.writeLong($$0);
/* 1490 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeLongLE(long $$0) {
/* 1495 */     this.source.writeLongLE($$0);
/* 1496 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeChar(int $$0) {
/* 1501 */     this.source.writeChar($$0);
/* 1502 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeFloat(float $$0) {
/* 1507 */     this.source.writeFloat($$0);
/* 1508 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeDouble(double $$0) {
/* 1513 */     this.source.writeDouble($$0);
/* 1514 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeBytes(ByteBuf $$0) {
/* 1519 */     this.source.writeBytes($$0);
/* 1520 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeBytes(ByteBuf $$0, int $$1) {
/* 1525 */     this.source.writeBytes($$0, $$1);
/* 1526 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeBytes(ByteBuf $$0, int $$1, int $$2) {
/* 1531 */     this.source.writeBytes($$0, $$1, $$2);
/* 1532 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeBytes(byte[] $$0) {
/* 1537 */     this.source.writeBytes($$0);
/* 1538 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeBytes(byte[] $$0, int $$1, int $$2) {
/* 1543 */     this.source.writeBytes($$0, $$1, $$2);
/* 1544 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeBytes(ByteBuffer $$0) {
/* 1549 */     this.source.writeBytes($$0);
/* 1550 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream $$0, int $$1) throws IOException {
/* 1555 */     return this.source.writeBytes($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel $$0, int $$1) throws IOException {
/* 1560 */     return this.source.writeBytes($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(FileChannel $$0, long $$1, int $$2) throws IOException {
/* 1565 */     return this.source.writeBytes($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf writeZero(int $$0) {
/* 1570 */     this.source.writeZero($$0);
/* 1571 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeCharSequence(CharSequence $$0, Charset $$1) {
/* 1576 */     return this.source.writeCharSequence($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int $$0, int $$1, byte $$2) {
/* 1581 */     return this.source.indexOf($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte $$0) {
/* 1586 */     return this.source.bytesBefore($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int $$0, byte $$1) {
/* 1591 */     return this.source.bytesBefore($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int $$0, int $$1, byte $$2) {
/* 1596 */     return this.source.bytesBefore($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteProcessor $$0) {
/* 1601 */     return this.source.forEachByte($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(int $$0, int $$1, ByteProcessor $$2) {
/* 1606 */     return this.source.forEachByte($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteProcessor $$0) {
/* 1611 */     return this.source.forEachByteDesc($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int $$0, int $$1, ByteProcessor $$2) {
/* 1616 */     return this.source.forEachByteDesc($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/* 1621 */     return this.source.copy();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int $$0, int $$1) {
/* 1626 */     return this.source.copy($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/* 1631 */     return this.source.slice();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice() {
/* 1636 */     return this.source.retainedSlice();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int $$0, int $$1) {
/* 1641 */     return this.source.slice($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice(int $$0, int $$1) {
/* 1646 */     return this.source.retainedSlice($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/* 1651 */     return this.source.duplicate();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedDuplicate() {
/* 1656 */     return this.source.retainedDuplicate();
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/* 1661 */     return this.source.nioBufferCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/* 1666 */     return this.source.nioBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int $$0, int $$1) {
/* 1671 */     return this.source.nioBuffer($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int $$0, int $$1) {
/* 1676 */     return this.source.internalNioBuffer($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/* 1681 */     return this.source.nioBuffers();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int $$0, int $$1) {
/* 1686 */     return this.source.nioBuffers($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/* 1691 */     return this.source.hasArray();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/* 1696 */     return this.source.array();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/* 1701 */     return this.source.arrayOffset();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/* 1706 */     return this.source.hasMemoryAddress();
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/* 1711 */     return this.source.memoryAddress();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset $$0) {
/* 1716 */     return this.source.toString($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int $$0, int $$1, Charset $$2) {
/* 1721 */     return this.source.toString($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1726 */     return this.source.hashCode();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object $$0) {
/* 1731 */     return this.source.equals($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf $$0) {
/* 1736 */     return this.source.compareTo($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1741 */     return this.source.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf retain(int $$0) {
/* 1746 */     this.source.retain($$0);
/* 1747 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf retain() {
/* 1752 */     this.source.retain();
/* 1753 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf touch() {
/* 1758 */     this.source.touch();
/* 1759 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FriendlyByteBuf touch(Object $$0) {
/* 1764 */     this.source.touch($$0);
/* 1765 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int refCnt() {
/* 1770 */     return this.source.refCnt();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release() {
/* 1775 */     return this.source.release();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release(int $$0) {
/* 1780 */     return this.source.release($$0);
/*      */   }
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface Reader<T> extends Function<FriendlyByteBuf, T> {
/*      */     default Reader<Optional<T>> asOptional() {
/* 1786 */       return $$0 -> $$0.readOptional(this);
/*      */     }
/*      */   }
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface Writer<T> extends BiConsumer<FriendlyByteBuf, T> {
/*      */     default Writer<Optional<T>> asOptional() {
/* 1793 */       return ($$0, $$1) -> $$0.writeOptional($$1, this);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\FriendlyByteBuf.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */