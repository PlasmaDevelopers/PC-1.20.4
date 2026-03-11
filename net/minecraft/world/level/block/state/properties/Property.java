/*     */ package net.minecraft.world.level.block.state.properties;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Collection;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.level.block.state.StateHolder;
/*     */ 
/*     */ public abstract class Property<T extends Comparable<T>> {
/*     */   private final Class<T> clazz;
/*     */   private final String name;
/*     */   @Nullable
/*     */   private Integer hashCode;
/*     */   private final Codec<T> codec;
/*     */   private final Codec<Value<T>> valueCodec;
/*     */   
/*     */   protected Property(String $$0, Class<T> $$1) {
/*  19 */     this.codec = Codec.STRING.comapFlatMap($$0 -> (DataResult)getValue($$0).<DataResult>map(DataResult::success).orElseGet(()), this::getName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  24 */     this.valueCodec = this.codec.xmap(this::value, Value::value);
/*     */ 
/*     */     
/*  27 */     this.clazz = $$1;
/*  28 */     this.name = $$0;
/*     */   }
/*     */   
/*     */   public Value<T> value(T $$0) {
/*  32 */     return new Value<>(this, $$0);
/*     */   }
/*     */   
/*     */   public Value<T> value(StateHolder<?, ?> $$0) {
/*  36 */     return new Value<>(this, (T)$$0.getValue(this));
/*     */   }
/*     */   
/*     */   public Stream<Value<T>> getAllValues() {
/*  40 */     return getPossibleValues().stream().map(this::value);
/*     */   }
/*     */   
/*     */   public Codec<T> codec() {
/*  44 */     return this.codec;
/*     */   }
/*     */   
/*     */   public Codec<Value<T>> valueCodec() {
/*  48 */     return this.valueCodec;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  52 */     return this.name;
/*     */   }
/*     */   
/*     */   public Class<T> getValueClass() {
/*  56 */     return this.clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  67 */     return MoreObjects.toStringHelper(this)
/*  68 */       .add("name", this.name)
/*  69 */       .add("clazz", this.clazz)
/*  70 */       .add("values", getPossibleValues())
/*  71 */       .toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  76 */     if (this == $$0) {
/*  77 */       return true;
/*     */     }
/*     */     
/*  80 */     if ($$0 instanceof Property) { Property<?> $$1 = (Property)$$0;
/*  81 */       return (this.clazz.equals($$1.clazz) && this.name.equals($$1.name)); }
/*     */ 
/*     */     
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/*  89 */     if (this.hashCode == null) {
/*  90 */       this.hashCode = Integer.valueOf(generateHashCode());
/*     */     }
/*  92 */     return this.hashCode.intValue();
/*     */   }
/*     */   
/*     */   public int generateHashCode() {
/*  96 */     return 31 * this.clazz.hashCode() + this.name.hashCode();
/*     */   }
/*     */   public abstract Collection<T> getPossibleValues();
/*     */   public abstract String getName(T paramT);
/* 100 */   public <U, S extends StateHolder<?, S>> DataResult<S> parseValue(DynamicOps<U> $$0, S $$1, U $$2) { DataResult<T> $$3 = this.codec.parse($$0, $$2);
/* 101 */     return $$3.map($$1 -> (StateHolder)$$0.setValue(this, $$1)).setPartial($$1); } public abstract Optional<T> getValue(String paramString); public static final class Value<T extends Comparable<T>> extends Record
/*     */   {
/*     */     private final Property<T> property; private final T value; public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/state/properties/Property$Value;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #104	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/block/state/properties/Property$Value;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/block/state/properties/Property$Value<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/state/properties/Property$Value;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #104	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/block/state/properties/Property$Value;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/block/state/properties/Property$Value<TT;>; } public Property<T> property() {
/* 104 */       return this.property; } public T value() { return this.value; }
/*     */ 
/*     */ 
/*     */     
/*     */     public Value(Property<T> $$0, T $$1) {
/* 109 */       if (!$$0.getPossibleValues().contains($$1))
/* 110 */         throw new IllegalArgumentException("Value " + $$1 + " does not belong to property " + $$0); 
/*     */       this.property = $$0;
/*     */       this.value = $$1;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 116 */       return this.property.getName() + "=" + this.property.getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\Property.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */