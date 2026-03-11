/*     */ package net.minecraft.world.entity.ai;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.ExpirableValue;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends MapCodec<Brain<E>>
/*     */ {
/*     */   public <T> Stream<T> keys(DynamicOps<T> $$0) {
/*  78 */     return memoryTypes.stream()
/*  79 */       .flatMap($$0 -> $$0.getCodec().map(()).stream())
/*  80 */       .map($$1 -> $$0.createString($$1.toString()));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> DataResult<Brain<E>> decode(DynamicOps<T> $$0, MapLike<T> $$1) {
/*  85 */     MutableObject<DataResult<ImmutableList.Builder<Brain.MemoryValue<?>>>> $$2 = new MutableObject(DataResult.success(ImmutableList.builder()));
/*     */     
/*  87 */     $$1.entries().forEach($$2 -> {
/*     */           DataResult<MemoryModuleType<?>> $$3 = BuiltInRegistries.MEMORY_MODULE_TYPE.byNameCodec().parse($$0, $$2.getFirst());
/*     */           
/*     */           DataResult<? extends Brain.MemoryValue<?>> $$4 = $$3.flatMap(());
/*     */           $$1.setValue(((DataResult)$$1.getValue()).apply2(ImmutableList.Builder::add, $$4));
/*     */         });
/*  93 */     Objects.requireNonNull(Brain.LOGGER); ImmutableList<Brain.MemoryValue<?>> $$3 = ((DataResult)$$2.getValue()).resultOrPartial(Brain.LOGGER::error).map(ImmutableList.Builder::build).orElseGet(ImmutableList::of);
/*  94 */     Objects.requireNonNull(codecReference); return DataResult.success(new Brain<>(memoryTypes, sensorTypes, $$3, codecReference::getValue));
/*     */   }
/*     */   
/*     */   private <T, U> DataResult<Brain.MemoryValue<U>> captureRead(MemoryModuleType<U> $$0, DynamicOps<T> $$1, T $$2) {
/*  98 */     return ((DataResult)$$0.getCodec().map(DataResult::success).orElseGet(() -> DataResult.error(())))
/*  99 */       .flatMap($$2 -> $$2.parse($$0, $$1))
/* 100 */       .map($$1 -> new Brain.MemoryValue($$0, Optional.of($$1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> RecordBuilder<T> encode(Brain<E> $$0, DynamicOps<T> $$1, RecordBuilder<T> $$2) {
/* 105 */     $$0.memories().forEach($$2 -> $$2.serialize($$0, $$1));
/* 106 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\Brain$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */