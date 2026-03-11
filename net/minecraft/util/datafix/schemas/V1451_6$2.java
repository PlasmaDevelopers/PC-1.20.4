/*     */ package net.minecraft.util.datafix.schemas;
/*     */ 
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.types.templates.Hook;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Optional;
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
/*     */ class null
/*     */   implements Hook.HookFunction
/*     */ {
/*     */   public <T> T apply(DynamicOps<T> $$0, T $$1) {
/* 133 */     Dynamic<T> $$2 = new Dynamic($$0, $$1);
/*     */     
/* 135 */     Optional<Dynamic<T>> $$3 = $$2.get("CriteriaType").get().get().left().flatMap($$1 -> {
/*     */           Optional<String> $$2 = $$1.get("type").asString().get().left();
/*     */           
/*     */           Optional<String> $$3 = $$1.get("id").asString().get().left();
/*     */           
/*     */           if ($$2.isPresent() && $$3.isPresent()) {
/*     */             String $$4 = $$2.get();
/*     */             
/*     */             return $$4.equals("_special") ? Optional.of($$0.createString($$3.get())) : Optional.of($$1.createString(V1451_6.packNamespacedWithDot($$4) + ":" + V1451_6.packNamespacedWithDot($$4)));
/*     */           } 
/*     */           
/*     */           return Optional.empty();
/*     */         });
/*     */     
/* 149 */     return (T)((Dynamic)DataFixUtils.orElse($$3.map($$1 -> $$0.set("CriteriaName", $$1).remove("CriteriaType")), $$2)).getValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1451_6$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */