/*     */ package net.minecraft.util.datafix.schemas;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.types.templates.Hook;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  95 */     Dynamic<T> $$2 = new Dynamic($$0, $$1);
/*     */     
/*  97 */     return (T)((Dynamic)DataFixUtils.orElse($$2
/*  98 */         .get("CriteriaName").asString().get().left()
/*  99 */         .map($$0 -> {
/*     */             int $$1 = $$0.indexOf(':');
/*     */             if ($$1 < 0) {
/*     */               return Pair.of("_special", $$0);
/*     */             }
/*     */             try {
/*     */               ResourceLocation $$2 = ResourceLocation.of($$0.substring(0, $$1), '.');
/*     */               ResourceLocation $$3 = ResourceLocation.of($$0.substring($$1 + 1), '.');
/*     */               return Pair.of($$2.toString(), $$3.toString());
/* 108 */             } catch (Exception $$4) {
/*     */               
/*     */               return Pair.of("_special", $$0);
/*     */             } 
/* 112 */           }).map($$1 -> $$0.set("CriteriaType", $$0.createMap((Map)ImmutableMap.of($$0.createString("type"), $$0.createString((String)$$1.getFirst()), $$0.createString("id"), $$0.createString((String)$$1.getSecond()))))), $$2))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       .getValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1451_6$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */