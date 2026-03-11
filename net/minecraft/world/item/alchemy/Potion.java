/*    */ package net.minecraft.world.item.alchemy;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ 
/*    */ public class Potion {
/*    */   @Nullable
/*    */   private final String name;
/*    */   
/*    */   public static Potion byName(String $$0) {
/* 17 */     return (Potion)BuiltInRegistries.POTION.get(ResourceLocation.tryParse($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   private final ImmutableList<MobEffectInstance> effects;
/* 22 */   private final Holder.Reference<Potion> builtInRegistryHolder = BuiltInRegistries.POTION.createIntrusiveHolder(this);
/*    */   
/*    */   public Potion(MobEffectInstance... $$0) {
/* 25 */     this(null, $$0);
/*    */   }
/*    */   
/*    */   public Potion(@Nullable String $$0, MobEffectInstance... $$1) {
/* 29 */     this.name = $$0;
/* 30 */     this.effects = ImmutableList.copyOf((Object[])$$1);
/*    */   }
/*    */   
/*    */   public String getName(String $$0) {
/* 34 */     return $$0 + $$0;
/*    */   }
/*    */   
/*    */   public List<MobEffectInstance> getEffects() {
/* 38 */     return (List<MobEffectInstance>)this.effects;
/*    */   }
/*    */   
/*    */   public boolean hasInstantEffects() {
/* 42 */     if (!this.effects.isEmpty()) {
/* 43 */       for (UnmodifiableIterator<MobEffectInstance> unmodifiableIterator = this.effects.iterator(); unmodifiableIterator.hasNext(); ) { MobEffectInstance $$0 = unmodifiableIterator.next();
/* 44 */         if ($$0.getEffect().isInstantenous()) {
/* 45 */           return true;
/*    */         } }
/*    */     
/*    */     }
/*    */     
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public Holder.Reference<Potion> builtInRegistryHolder() {
/* 55 */     return this.builtInRegistryHolder;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\alchemy\Potion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */