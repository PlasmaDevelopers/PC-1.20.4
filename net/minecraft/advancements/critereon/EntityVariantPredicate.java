/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class EntityVariantPredicate<V> {
/*    */   private final Function<Entity, Optional<V>> getter;
/*    */   private final EntitySubPredicate.Type type;
/*    */   
/*    */   public static <V> EntityVariantPredicate<V> create(Registry<V> $$0, Function<Entity, Optional<V>> $$1) {
/* 20 */     return new EntityVariantPredicate<>($$0.byNameCodec(), $$1);
/*    */   }
/*    */   
/*    */   public static <V> EntityVariantPredicate<V> create(Codec<V> $$0, Function<Entity, Optional<V>> $$1) {
/* 24 */     return new EntityVariantPredicate<>($$0, $$1);
/*    */   }
/*    */   
/*    */   private EntityVariantPredicate(Codec<V> $$0, Function<Entity, Optional<V>> $$1) {
/* 28 */     this.getter = $$1;
/*    */     
/* 30 */     MapCodec<SubPredicate<V>> $$2 = RecordCodecBuilder.mapCodec($$1 -> $$1.group((App)$$0.fieldOf("variant").forGetter(SubPredicate::variant)).apply((Applicative)$$1, this::createPredicate));
/*    */ 
/*    */     
/* 33 */     this.type = new EntitySubPredicate.Type((MapCodec)$$2);
/*    */   }
/*    */   
/*    */   public EntitySubPredicate.Type type() {
/* 37 */     return this.type;
/*    */   }
/*    */   
/*    */   public SubPredicate<V> createPredicate(V $$0) {
/* 41 */     return new SubPredicate<>(this.type, this.getter, $$0);
/*    */   }
/*    */   public static final class SubPredicate<V> extends Record implements EntitySubPredicate { private final EntitySubPredicate.Type type; private final Function<Entity, Optional<V>> getter; private final V variant;
/* 44 */     public SubPredicate(EntitySubPredicate.Type $$0, Function<Entity, Optional<V>> $$1, V $$2) { this.type = $$0; this.getter = $$1; this.variant = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #44	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 44 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate<TV;>; } public EntitySubPredicate.Type type() { return this.type; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #44	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate<TV;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #44	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 44 */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate<TV;>; } public Function<Entity, Optional<V>> getter() { return this.getter; } public V variant() { return this.variant; }
/*    */     
/*    */     public boolean matches(Entity $$0, ServerLevel $$1, @Nullable Vec3 $$2) {
/* 47 */       return ((Optional)this.getter.apply($$0)).filter($$0 -> $$0.equals(this.variant)).isPresent();
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityVariantPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */