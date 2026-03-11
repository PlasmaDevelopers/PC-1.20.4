/*    */ package net.minecraft.util.datafix;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixer;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import net.minecraft.SharedConstants;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtOps;
/*    */ import net.minecraft.util.datafix.fixes.References;
/*    */ 
/*    */ public enum DataFixTypes {
/* 18 */   LEVEL(References.LEVEL),
/* 19 */   PLAYER(References.PLAYER),
/* 20 */   CHUNK(References.CHUNK),
/* 21 */   HOTBAR(References.HOTBAR),
/* 22 */   OPTIONS(References.OPTIONS),
/* 23 */   STRUCTURE(References.STRUCTURE),
/* 24 */   STATS(References.STATS),
/* 25 */   SAVED_DATA_COMMAND_STORAGE(References.SAVED_DATA_COMMAND_STORAGE),
/* 26 */   SAVED_DATA_FORCED_CHUNKS(References.SAVED_DATA_FORCED_CHUNKS),
/* 27 */   SAVED_DATA_MAP_DATA(References.SAVED_DATA_MAP_DATA),
/* 28 */   SAVED_DATA_MAP_INDEX(References.SAVED_DATA_MAP_INDEX),
/* 29 */   SAVED_DATA_RAIDS(References.SAVED_DATA_RAIDS),
/* 30 */   SAVED_DATA_RANDOM_SEQUENCES(References.SAVED_DATA_RANDOM_SEQUENCES),
/* 31 */   SAVED_DATA_SCOREBOARD(References.SAVED_DATA_SCOREBOARD),
/* 32 */   SAVED_DATA_STRUCTURE_FEATURE_INDICES(References.SAVED_DATA_STRUCTURE_FEATURE_INDICES),
/* 33 */   ADVANCEMENTS(References.ADVANCEMENTS),
/* 34 */   POI_CHUNK(References.POI_CHUNK),
/* 35 */   WORLD_GEN_SETTINGS(References.WORLD_GEN_SETTINGS),
/* 36 */   ENTITY_CHUNK(References.ENTITY_CHUNK); public static final Set<DSL.TypeReference> TYPES_FOR_LEVEL_LIST;
/*    */   
/*    */   static {
/* 39 */     TYPES_FOR_LEVEL_LIST = Set.of(LEVEL.type);
/*    */   }
/*    */   private final DSL.TypeReference type;
/*    */   
/*    */   DataFixTypes(DSL.TypeReference $$0) {
/* 44 */     this.type = $$0;
/*    */   }
/*    */   
/*    */   static int currentVersion() {
/* 48 */     return SharedConstants.getCurrentVersion().getDataVersion().getVersion();
/*    */   }
/*    */   
/*    */   public <A> Codec<A> wrapCodec(final Codec<A> codec, final DataFixer dataFixer, final int defaultVersion) {
/* 52 */     return new Codec<A>()
/*    */       {
/*    */         public <T> DataResult<T> encode(A $$0, DynamicOps<T> $$1, T $$2) {
/* 55 */           return codec.encode($$0, $$1, $$2).flatMap($$1 -> $$0.mergeToMap($$1, $$0.createString("DataVersion"), $$0.createInt(DataFixTypes.currentVersion())));
/*    */         }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> $$0, T $$1) {
/* 63 */           Objects.requireNonNull($$0);
/*    */ 
/*    */           
/* 66 */           int $$2 = ((Integer)$$0.get($$1, "DataVersion").flatMap($$0::getNumberValue).map(Number::intValue).result().orElse(Integer.valueOf(defaultVersion))).intValue();
/* 67 */           Dynamic<T> $$3 = new Dynamic($$0, $$0.remove($$1, "DataVersion"));
/* 68 */           Dynamic<T> $$4 = DataFixTypes.this.updateToCurrentVersion(dataFixer, $$3, $$2);
/* 69 */           return codec.decode($$4);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public <T> Dynamic<T> update(DataFixer $$0, Dynamic<T> $$1, int $$2, int $$3) {
/* 75 */     return $$0.update(this.type, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public <T> Dynamic<T> updateToCurrentVersion(DataFixer $$0, Dynamic<T> $$1, int $$2) {
/* 79 */     return update($$0, $$1, $$2, currentVersion());
/*    */   }
/*    */   
/*    */   public CompoundTag update(DataFixer $$0, CompoundTag $$1, int $$2, int $$3) {
/* 83 */     return (CompoundTag)update($$0, new Dynamic((DynamicOps)NbtOps.INSTANCE, $$1), $$2, $$3).getValue();
/*    */   }
/*    */   
/*    */   public CompoundTag updateToCurrentVersion(DataFixer $$0, CompoundTag $$1, int $$2) {
/* 87 */     return update($$0, $$1, $$2, currentVersion());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\DataFixTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */