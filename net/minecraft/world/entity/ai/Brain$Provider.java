/*    */ package net.minecraft.world.entity.ai;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Collection;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*    */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Provider<E extends LivingEntity>
/*    */ {
/*    */   private final Collection<? extends MemoryModuleType<?>> memoryTypes;
/*    */   private final Collection<? extends SensorType<? extends Sensor<? super E>>> sensorTypes;
/*    */   private final Codec<Brain<E>> codec;
/*    */   
/*    */   Provider(Collection<? extends MemoryModuleType<?>> $$0, Collection<? extends SensorType<? extends Sensor<? super E>>> $$1) {
/* 58 */     this.memoryTypes = $$0;
/* 59 */     this.sensorTypes = $$1;
/* 60 */     this.codec = Brain.codec($$0, $$1);
/*    */   }
/*    */   
/*    */   public Brain<E> makeBrain(Dynamic<?> $$0) {
/* 64 */     Objects.requireNonNull(Brain.LOGGER); return this.codec.parse($$0).resultOrPartial(Brain.LOGGER::error).orElseGet(() -> new Brain<>(this.memoryTypes, this.sensorTypes, ImmutableList.of(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\Brain$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */