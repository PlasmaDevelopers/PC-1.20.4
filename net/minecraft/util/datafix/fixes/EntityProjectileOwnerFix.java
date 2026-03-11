/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.OptionalDynamic;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ public class EntityProjectileOwnerFix
/*     */   extends DataFix
/*     */ {
/*     */   public EntityProjectileOwnerFix(Schema $$0) {
/*  19 */     super($$0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/*  24 */     Schema $$0 = getInputSchema();
/*  25 */     return fixTypeEverywhereTyped("EntityProjectileOwner", $$0.getType(References.ENTITY), this::updateProjectiles);
/*     */   }
/*     */   
/*     */   private Typed<?> updateProjectiles(Typed<?> $$0) {
/*  29 */     $$0 = updateEntity($$0, "minecraft:egg", this::updateOwnerThrowable);
/*  30 */     $$0 = updateEntity($$0, "minecraft:ender_pearl", this::updateOwnerThrowable);
/*  31 */     $$0 = updateEntity($$0, "minecraft:experience_bottle", this::updateOwnerThrowable);
/*  32 */     $$0 = updateEntity($$0, "minecraft:snowball", this::updateOwnerThrowable);
/*  33 */     $$0 = updateEntity($$0, "minecraft:potion", this::updateOwnerThrowable);
/*  34 */     $$0 = updateEntity($$0, "minecraft:potion", this::updateItemPotion);
/*  35 */     $$0 = updateEntity($$0, "minecraft:llama_spit", this::updateOwnerLlamaSpit);
/*  36 */     $$0 = updateEntity($$0, "minecraft:arrow", this::updateOwnerArrow);
/*  37 */     $$0 = updateEntity($$0, "minecraft:spectral_arrow", this::updateOwnerArrow);
/*  38 */     $$0 = updateEntity($$0, "minecraft:trident", this::updateOwnerArrow);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     return $$0;
/*     */   }
/*     */   
/*     */   private Dynamic<?> updateOwnerArrow(Dynamic<?> $$0) {
/*  52 */     long $$1 = $$0.get("OwnerUUIDMost").asLong(0L);
/*  53 */     long $$2 = $$0.get("OwnerUUIDLeast").asLong(0L);
/*     */     
/*  55 */     return setUUID($$0, $$1, $$2).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
/*     */   }
/*     */   
/*     */   private Dynamic<?> updateOwnerLlamaSpit(Dynamic<?> $$0) {
/*  59 */     OptionalDynamic<?> $$1 = $$0.get("Owner");
/*  60 */     long $$2 = $$1.get("OwnerUUIDMost").asLong(0L);
/*  61 */     long $$3 = $$1.get("OwnerUUIDLeast").asLong(0L);
/*     */     
/*  63 */     return setUUID($$0, $$2, $$3).remove("Owner");
/*     */   }
/*     */   
/*     */   private Dynamic<?> updateItemPotion(Dynamic<?> $$0) {
/*  67 */     OptionalDynamic<?> $$1 = $$0.get("Potion");
/*  68 */     return $$0.set("Item", $$1.orElseEmptyMap()).remove("Potion");
/*     */   }
/*     */   
/*     */   private Dynamic<?> updateOwnerThrowable(Dynamic<?> $$0) {
/*  72 */     String $$1 = "owner";
/*  73 */     OptionalDynamic<?> $$2 = $$0.get("owner");
/*  74 */     long $$3 = $$2.get("M").asLong(0L);
/*  75 */     long $$4 = $$2.get("L").asLong(0L);
/*     */     
/*  77 */     return setUUID($$0, $$3, $$4).remove("owner");
/*     */   }
/*     */   
/*     */   private Dynamic<?> setUUID(Dynamic<?> $$0, long $$1, long $$2) {
/*  81 */     String $$3 = "OwnerUUID";
/*  82 */     if ($$1 != 0L && $$2 != 0L) {
/*  83 */       return $$0.set("OwnerUUID", $$0.createIntList(Arrays.stream(createUUIDArray($$1, $$2))));
/*     */     }
/*  85 */     return $$0;
/*     */   }
/*     */   
/*     */   private static int[] createUUIDArray(long $$0, long $$1) {
/*  89 */     return new int[] { (int)($$0 >> 32L), (int)$$0, (int)($$1 >> 32L), (int)$$1 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Typed<?> updateEntity(Typed<?> $$0, String $$1, Function<Dynamic<?>, Dynamic<?>> $$2) {
/*  98 */     Type<?> $$3 = getInputSchema().getChoiceType(References.ENTITY, $$1);
/*  99 */     Type<?> $$4 = getOutputSchema().getChoiceType(References.ENTITY, $$1);
/* 100 */     return $$0.updateTyped(DSL.namedChoice($$1, $$3), $$4, $$1 -> $$1.update(DSL.remainderFinder(), $$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityProjectileOwnerFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */