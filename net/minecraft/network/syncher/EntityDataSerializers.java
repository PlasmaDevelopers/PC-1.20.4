/*     */ package net.minecraft.network.syncher;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.Rotations;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleType;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.animal.CatVariant;
/*     */ import net.minecraft.world.entity.animal.FrogVariant;
/*     */ import net.minecraft.world.entity.animal.sniffer.Sniffer;
/*     */ import net.minecraft.world.entity.decoration.PaintingVariant;
/*     */ import net.minecraft.world.entity.npc.VillagerData;
/*     */ import net.minecraft.world.entity.npc.VillagerProfession;
/*     */ import net.minecraft.world.entity.npc.VillagerType;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class EntityDataSerializers {
/*  33 */   private static final CrudeIncrementalIntIdentityHashBiMap<EntityDataSerializer<?>> SERIALIZERS = CrudeIncrementalIntIdentityHashBiMap.create(16); public static final EntityDataSerializer<Byte> BYTE;
/*     */   static {
/*  35 */     BYTE = EntityDataSerializer.simple(($$0, $$1) -> $$0.writeByte($$1.byteValue()), FriendlyByteBuf::readByte);
/*     */   }
/*  37 */   public static final EntityDataSerializer<Integer> INT = EntityDataSerializer.simple(FriendlyByteBuf::writeVarInt, FriendlyByteBuf::readVarInt);
/*     */   
/*  39 */   public static final EntityDataSerializer<Long> LONG = EntityDataSerializer.simple(FriendlyByteBuf::writeVarLong, FriendlyByteBuf::readVarLong);
/*     */   
/*  41 */   public static final EntityDataSerializer<Float> FLOAT = EntityDataSerializer.simple(FriendlyByteBuf::writeFloat, FriendlyByteBuf::readFloat);
/*     */   
/*  43 */   public static final EntityDataSerializer<String> STRING = EntityDataSerializer.simple(FriendlyByteBuf::writeUtf, FriendlyByteBuf::readUtf);
/*     */   
/*  45 */   public static final EntityDataSerializer<Component> COMPONENT = EntityDataSerializer.simple(FriendlyByteBuf::writeComponent, FriendlyByteBuf::readComponentTrusted);
/*     */   
/*  47 */   public static final EntityDataSerializer<Optional<Component>> OPTIONAL_COMPONENT = EntityDataSerializer.optional(FriendlyByteBuf::writeComponent, FriendlyByteBuf::readComponentTrusted);
/*     */   
/*  49 */   public static final EntityDataSerializer<ItemStack> ITEM_STACK = new EntityDataSerializer<ItemStack>()
/*     */     {
/*     */       public void write(FriendlyByteBuf $$0, ItemStack $$1) {
/*  52 */         $$0.writeItem($$1);
/*     */       }
/*     */ 
/*     */       
/*     */       public ItemStack read(FriendlyByteBuf $$0) {
/*  57 */         return $$0.readItem();
/*     */       }
/*     */ 
/*     */       
/*     */       public ItemStack copy(ItemStack $$0) {
/*  62 */         return $$0.copy();
/*     */       }
/*     */     };
/*     */   
/*  66 */   public static final EntityDataSerializer<BlockState> BLOCK_STATE = EntityDataSerializer.simpleId((IdMap<BlockState>)Block.BLOCK_STATE_REGISTRY);
/*     */   
/*  68 */   public static final EntityDataSerializer<Optional<BlockState>> OPTIONAL_BLOCK_STATE = new EntityDataSerializer.ForValueType<Optional<BlockState>>()
/*     */     {
/*     */       public void write(FriendlyByteBuf $$0, Optional<BlockState> $$1) {
/*  71 */         if ($$1.isPresent()) {
/*  72 */           $$0.writeVarInt(Block.getId($$1.get()));
/*     */         } else {
/*  74 */           $$0.writeVarInt(0);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public Optional<BlockState> read(FriendlyByteBuf $$0) {
/*  80 */         int $$1 = $$0.readVarInt();
/*  81 */         if ($$1 == 0) {
/*  82 */           return Optional.empty();
/*     */         }
/*  84 */         return Optional.of(Block.stateById($$1));
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  89 */   public static final EntityDataSerializer<Boolean> BOOLEAN = EntityDataSerializer.simple(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean);
/*     */   
/*  91 */   public static final EntityDataSerializer<ParticleOptions> PARTICLE = new EntityDataSerializer.ForValueType<ParticleOptions>()
/*     */     {
/*     */       public void write(FriendlyByteBuf $$0, ParticleOptions $$1) {
/*  94 */         $$0.writeId((IdMap)BuiltInRegistries.PARTICLE_TYPE, $$1.getType());
/*  95 */         $$1.writeToNetwork($$0);
/*     */       }
/*     */ 
/*     */       
/*     */       public ParticleOptions read(FriendlyByteBuf $$0) {
/* 100 */         return readParticle($$0, (ParticleType<ParticleOptions>)$$0.readById((IdMap)BuiltInRegistries.PARTICLE_TYPE));
/*     */       }
/*     */       
/*     */       private <T extends ParticleOptions> T readParticle(FriendlyByteBuf $$0, ParticleType<T> $$1) {
/* 104 */         return (T)$$1.getDeserializer().fromNetwork($$1, $$0);
/*     */       }
/*     */     };
/*     */   
/* 108 */   public static final EntityDataSerializer<Rotations> ROTATIONS = new EntityDataSerializer.ForValueType<Rotations>()
/*     */     {
/*     */       public void write(FriendlyByteBuf $$0, Rotations $$1) {
/* 111 */         $$0.writeFloat($$1.getX());
/* 112 */         $$0.writeFloat($$1.getY());
/* 113 */         $$0.writeFloat($$1.getZ());
/*     */       }
/*     */ 
/*     */       
/*     */       public Rotations read(FriendlyByteBuf $$0) {
/* 118 */         return new Rotations($$0.readFloat(), $$0.readFloat(), $$0.readFloat());
/*     */       }
/*     */     };
/*     */   
/* 122 */   public static final EntityDataSerializer<BlockPos> BLOCK_POS = EntityDataSerializer.simple(FriendlyByteBuf::writeBlockPos, FriendlyByteBuf::readBlockPos);
/*     */   
/* 124 */   public static final EntityDataSerializer<Optional<BlockPos>> OPTIONAL_BLOCK_POS = EntityDataSerializer.optional(FriendlyByteBuf::writeBlockPos, FriendlyByteBuf::readBlockPos);
/*     */   
/* 126 */   public static final EntityDataSerializer<Direction> DIRECTION = EntityDataSerializer.simpleEnum(Direction.class);
/*     */   
/* 128 */   public static final EntityDataSerializer<Optional<UUID>> OPTIONAL_UUID = EntityDataSerializer.optional(FriendlyByteBuf::writeUUID, FriendlyByteBuf::readUUID);
/*     */   
/* 130 */   public static final EntityDataSerializer<Optional<GlobalPos>> OPTIONAL_GLOBAL_POS = EntityDataSerializer.optional(FriendlyByteBuf::writeGlobalPos, FriendlyByteBuf::readGlobalPos);
/*     */   
/* 132 */   public static final EntityDataSerializer<CompoundTag> COMPOUND_TAG = new EntityDataSerializer<CompoundTag>()
/*     */     {
/*     */       public void write(FriendlyByteBuf $$0, CompoundTag $$1) {
/* 135 */         $$0.writeNbt((Tag)$$1);
/*     */       }
/*     */ 
/*     */       
/*     */       public CompoundTag read(FriendlyByteBuf $$0) {
/* 140 */         return $$0.readNbt();
/*     */       }
/*     */ 
/*     */       
/*     */       public CompoundTag copy(CompoundTag $$0) {
/* 145 */         return $$0.copy();
/*     */       }
/*     */     };
/*     */   
/* 149 */   public static final EntityDataSerializer<VillagerData> VILLAGER_DATA = new EntityDataSerializer.ForValueType<VillagerData>()
/*     */     {
/*     */       public void write(FriendlyByteBuf $$0, VillagerData $$1) {
/* 152 */         $$0.writeId((IdMap)BuiltInRegistries.VILLAGER_TYPE, $$1.getType());
/* 153 */         $$0.writeId((IdMap)BuiltInRegistries.VILLAGER_PROFESSION, $$1.getProfession());
/* 154 */         $$0.writeVarInt($$1.getLevel());
/*     */       }
/*     */ 
/*     */       
/*     */       public VillagerData read(FriendlyByteBuf $$0) {
/* 159 */         return new VillagerData((VillagerType)$$0
/* 160 */             .readById((IdMap)BuiltInRegistries.VILLAGER_TYPE), (VillagerProfession)$$0
/* 161 */             .readById((IdMap)BuiltInRegistries.VILLAGER_PROFESSION), $$0
/* 162 */             .readVarInt());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 167 */   public static final EntityDataSerializer<OptionalInt> OPTIONAL_UNSIGNED_INT = new EntityDataSerializer.ForValueType<OptionalInt>()
/*     */     {
/*     */       public void write(FriendlyByteBuf $$0, OptionalInt $$1) {
/* 170 */         $$0.writeVarInt($$1.orElse(-1) + 1);
/*     */       }
/*     */ 
/*     */       
/*     */       public OptionalInt read(FriendlyByteBuf $$0) {
/* 175 */         int $$1 = $$0.readVarInt();
/* 176 */         return ($$1 == 0) ? OptionalInt.empty() : OptionalInt.of($$1 - 1);
/*     */       }
/*     */     };
/*     */   
/* 180 */   public static final EntityDataSerializer<Pose> POSE = EntityDataSerializer.simpleEnum(Pose.class);
/*     */   
/* 182 */   public static final EntityDataSerializer<CatVariant> CAT_VARIANT = EntityDataSerializer.simpleId((IdMap<CatVariant>)BuiltInRegistries.CAT_VARIANT);
/*     */   
/* 184 */   public static final EntityDataSerializer<FrogVariant> FROG_VARIANT = EntityDataSerializer.simpleId((IdMap<FrogVariant>)BuiltInRegistries.FROG_VARIANT);
/*     */   
/* 186 */   public static final EntityDataSerializer<Holder<PaintingVariant>> PAINTING_VARIANT = EntityDataSerializer.simpleId(BuiltInRegistries.PAINTING_VARIANT.asHolderIdMap());
/*     */   
/* 188 */   public static final EntityDataSerializer<Sniffer.State> SNIFFER_STATE = EntityDataSerializer.simpleEnum(Sniffer.State.class);
/*     */   
/* 190 */   public static final EntityDataSerializer<Vector3f> VECTOR3 = EntityDataSerializer.simple(FriendlyByteBuf::writeVector3f, FriendlyByteBuf::readVector3f);
/*     */   
/* 192 */   public static final EntityDataSerializer<Quaternionf> QUATERNION = EntityDataSerializer.simple(FriendlyByteBuf::writeQuaternion, FriendlyByteBuf::readQuaternion);
/*     */   
/*     */   static {
/* 195 */     registerSerializer(BYTE);
/* 196 */     registerSerializer(INT);
/* 197 */     registerSerializer(LONG);
/* 198 */     registerSerializer(FLOAT);
/* 199 */     registerSerializer(STRING);
/* 200 */     registerSerializer(COMPONENT);
/* 201 */     registerSerializer(OPTIONAL_COMPONENT);
/* 202 */     registerSerializer(ITEM_STACK);
/* 203 */     registerSerializer(BOOLEAN);
/* 204 */     registerSerializer(ROTATIONS);
/* 205 */     registerSerializer(BLOCK_POS);
/* 206 */     registerSerializer(OPTIONAL_BLOCK_POS);
/* 207 */     registerSerializer(DIRECTION);
/* 208 */     registerSerializer(OPTIONAL_UUID);
/* 209 */     registerSerializer(BLOCK_STATE);
/* 210 */     registerSerializer(OPTIONAL_BLOCK_STATE);
/* 211 */     registerSerializer(COMPOUND_TAG);
/* 212 */     registerSerializer(PARTICLE);
/* 213 */     registerSerializer(VILLAGER_DATA);
/* 214 */     registerSerializer(OPTIONAL_UNSIGNED_INT);
/* 215 */     registerSerializer(POSE);
/* 216 */     registerSerializer(CAT_VARIANT);
/* 217 */     registerSerializer(FROG_VARIANT);
/* 218 */     registerSerializer(OPTIONAL_GLOBAL_POS);
/* 219 */     registerSerializer(PAINTING_VARIANT);
/* 220 */     registerSerializer(SNIFFER_STATE);
/* 221 */     registerSerializer(VECTOR3);
/* 222 */     registerSerializer(QUATERNION);
/*     */   }
/*     */   
/*     */   public static void registerSerializer(EntityDataSerializer<?> $$0) {
/* 226 */     SERIALIZERS.add($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static EntityDataSerializer<?> getSerializer(int $$0) {
/* 231 */     return (EntityDataSerializer)SERIALIZERS.byId($$0);
/*     */   }
/*     */   
/*     */   public static int getSerializedId(EntityDataSerializer<?> $$0) {
/* 235 */     return SERIALIZERS.getId($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\EntityDataSerializers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */