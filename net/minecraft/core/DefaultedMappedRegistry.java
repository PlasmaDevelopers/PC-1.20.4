/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.mojang.serialization.Lifecycle;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class DefaultedMappedRegistry<T>
/*    */   extends MappedRegistry<T> implements DefaultedRegistry<T> {
/*    */   private final ResourceLocation defaultKey;
/*    */   private Holder.Reference<T> defaultValue;
/*    */   
/*    */   public DefaultedMappedRegistry(String $$0, ResourceKey<? extends Registry<T>> $$1, Lifecycle $$2, boolean $$3) {
/* 17 */     super($$1, $$2, $$3);
/* 18 */     this.defaultKey = new ResourceLocation($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Holder.Reference<T> registerMapping(int $$0, ResourceKey<T> $$1, T $$2, Lifecycle $$3) {
/* 23 */     Holder.Reference<T> $$4 = super.registerMapping($$0, $$1, $$2, $$3);
/* 24 */     if (this.defaultKey.equals($$1.location())) {
/* 25 */       this.defaultValue = $$4;
/*    */     }
/* 27 */     return $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId(@Nullable T $$0) {
/* 32 */     int $$1 = super.getId($$0);
/* 33 */     return ($$1 == -1) ? super.getId(this.defaultValue.value()) : $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ResourceLocation getKey(T $$0) {
/* 39 */     ResourceLocation $$1 = super.getKey($$0);
/* 40 */     return ($$1 == null) ? this.defaultKey : $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public T get(@Nullable ResourceLocation $$0) {
/* 46 */     T $$1 = super.get($$0);
/* 47 */     return ($$1 == null) ? this.defaultValue.value() : $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<T> getOptional(@Nullable ResourceLocation $$0) {
/* 52 */     return Optional.ofNullable(super.get($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public T byId(int $$0) {
/* 58 */     T $$1 = super.byId($$0);
/* 59 */     return ($$1 == null) ? this.defaultValue.value() : $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Holder.Reference<T>> getRandom(RandomSource $$0) {
/* 64 */     return super.getRandom($$0).or(() -> Optional.of(this.defaultValue));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getDefaultKey() {
/* 69 */     return this.defaultKey;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\DefaultedMappedRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */