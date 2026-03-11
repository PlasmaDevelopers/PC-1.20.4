/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.datafix.PackedBitStorage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Section
/*     */ {
/*     */   protected static final String BLOCK_STATES_TAG = "BlockStates";
/*     */   protected static final String NAME_TAG = "Name";
/*     */   protected static final String PROPERTIES_TAG = "Properties";
/* 193 */   private final Type<Pair<String, Dynamic<?>>> blockStateType = DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType());
/* 194 */   protected final OpticFinder<List<Pair<String, Dynamic<?>>>> paletteFinder = DSL.fieldFinder("Palette", (Type)DSL.list(this.blockStateType));
/*     */   
/*     */   protected final List<Dynamic<?>> palette;
/*     */   protected final int index;
/*     */   @Nullable
/*     */   protected PackedBitStorage storage;
/*     */   
/*     */   public Section(Typed<?> $$0, Schema $$1) {
/* 202 */     if (!Objects.equals($$1.getType(References.BLOCK_STATE), this.blockStateType)) {
/* 203 */       throw new IllegalStateException("Block state type is not what was expected.");
/*     */     }
/*     */     
/* 206 */     Optional<List<Pair<String, Dynamic<?>>>> $$2 = $$0.getOptional(this.paletteFinder);
/*     */     
/* 208 */     this.palette = (List<Dynamic<?>>)$$2.map($$0 -> (List)$$0.stream().map(Pair::getSecond).collect(Collectors.toList())).orElse(ImmutableList.of());
/*     */     
/* 210 */     Dynamic<?> $$3 = (Dynamic)$$0.get(DSL.remainderFinder());
/* 211 */     this.index = $$3.get("Y").asInt(0);
/*     */     
/* 213 */     readStorage($$3);
/*     */   }
/*     */   
/*     */   protected void readStorage(Dynamic<?> $$0) {
/* 217 */     if (skippable()) {
/* 218 */       this.storage = null;
/*     */     } else {
/* 220 */       long[] $$1 = $$0.get("BlockStates").asLongStream().toArray();
/* 221 */       int $$2 = Math.max(4, DataFixUtils.ceillog2(this.palette.size()));
/* 222 */       this.storage = new PackedBitStorage($$2, 4096, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Typed<?> write(Typed<?> $$0) {
/* 227 */     if (isSkippable()) {
/* 228 */       return $$0;
/*     */     }
/* 230 */     return $$0
/* 231 */       .update(DSL.remainderFinder(), $$0 -> $$0.set("BlockStates", $$0.createLongList(Arrays.stream(this.storage.getRaw()))))
/* 232 */       .set(this.paletteFinder, this.palette.stream().map($$0 -> Pair.of(References.BLOCK_STATE.typeName(), $$0)).collect(Collectors.toList()));
/*     */   }
/*     */   
/*     */   public boolean isSkippable() {
/* 236 */     return (this.storage == null);
/*     */   }
/*     */   
/*     */   public int getBlock(int $$0) {
/* 240 */     return this.storage.get($$0);
/*     */   }
/*     */   
/*     */   protected int getStateId(String $$0, boolean $$1, int $$2) {
/* 244 */     return LeavesFix.LEAVES.get($$0).intValue() << 5 | ($$1 ? 16 : 0) | $$2;
/*     */   }
/*     */   
/*     */   int getIndex() {
/* 248 */     return this.index;
/*     */   }
/*     */   
/*     */   protected abstract boolean skippable();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\LeavesFix$Section.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */