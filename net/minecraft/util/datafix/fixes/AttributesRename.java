/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class AttributesRename extends DataFix {
/* 17 */   private static final Map<String, String> RENAMES = (Map<String, String>)ImmutableMap.builder()
/* 18 */     .put("generic.maxHealth", "generic.max_health")
/* 19 */     .put("Max Health", "generic.max_health")
/*    */     
/* 21 */     .put("zombie.spawnReinforcements", "zombie.spawn_reinforcements")
/* 22 */     .put("Spawn Reinforcements Chance", "zombie.spawn_reinforcements")
/*    */     
/* 24 */     .put("horse.jumpStrength", "horse.jump_strength")
/* 25 */     .put("Jump Strength", "horse.jump_strength")
/*    */     
/* 27 */     .put("generic.followRange", "generic.follow_range")
/* 28 */     .put("Follow Range", "generic.follow_range")
/*    */     
/* 30 */     .put("generic.knockbackResistance", "generic.knockback_resistance")
/* 31 */     .put("Knockback Resistance", "generic.knockback_resistance")
/*    */     
/* 33 */     .put("generic.movementSpeed", "generic.movement_speed")
/* 34 */     .put("Movement Speed", "generic.movement_speed")
/*    */     
/* 36 */     .put("generic.flyingSpeed", "generic.flying_speed")
/* 37 */     .put("Flying Speed", "generic.flying_speed")
/*    */     
/* 39 */     .put("generic.attackDamage", "generic.attack_damage")
/* 40 */     .put("generic.attackKnockback", "generic.attack_knockback")
/* 41 */     .put("generic.attackSpeed", "generic.attack_speed")
/* 42 */     .put("generic.armorToughness", "generic.armor_toughness")
/* 43 */     .build();
/*    */   
/*    */   public AttributesRename(Schema $$0) {
/* 46 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 51 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/* 52 */     OpticFinder<?> $$1 = $$0.findField("tag");
/* 53 */     return TypeRewriteRule.seq(
/* 54 */         fixTypeEverywhereTyped("Rename ItemStack Attributes", $$0, $$1 -> $$1.updateTyped($$0, AttributesRename::fixItemStackTag)), new TypeRewriteRule[] {
/*    */ 
/*    */           
/* 57 */           fixTypeEverywhereTyped("Rename Entity Attributes", getInputSchema().getType(References.ENTITY), AttributesRename::fixEntity), 
/* 58 */           fixTypeEverywhereTyped("Rename Player Attributes", getInputSchema().getType(References.PLAYER), AttributesRename::fixEntity)
/*    */         });
/*    */   }
/*    */   
/*    */   private static Dynamic<?> fixName(Dynamic<?> $$0) {
/* 63 */     Objects.requireNonNull($$0); return (Dynamic)DataFixUtils.orElse($$0.asString().result().map($$0 -> (String)RENAMES.getOrDefault($$0, $$0)).map($$0::createString), $$0);
/*    */   }
/*    */   
/*    */   private static Typed<?> fixItemStackTag(Typed<?> $$0) {
/* 67 */     return $$0.update(DSL.remainderFinder(), $$0 -> $$0.update("AttributeModifiers", ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Typed<?> fixEntity(Typed<?> $$0) {
/* 75 */     return $$0.update(DSL.remainderFinder(), $$0 -> $$0.update("Attributes", ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\AttributesRename.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */