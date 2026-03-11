/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class EntityHealthFix extends DataFix {
/*    */   public EntityHealthFix(Schema $$0, boolean $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */   
/* 18 */   private static final Set<String> ENTITIES = Sets.newHashSet((Object[])new String[] { "ArmorStand", "Bat", "Blaze", "CaveSpider", "Chicken", "Cow", "Creeper", "EnderDragon", "Enderman", "Endermite", "EntityHorse", "Ghast", "Giant", "Guardian", "LavaSlime", "MushroomCow", "Ozelot", "Pig", "PigZombie", "Rabbit", "Sheep", "Shulker", "Silverfish", "Skeleton", "Slime", "SnowMan", "Spider", "Squid", "Villager", "VillagerGolem", "Witch", "WitherBoss", "Wolf", "Zombie" });
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
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/*    */     float $$4;
/* 58 */     Optional<Number> $$1 = $$0.get("HealF").asNumber().result();
/* 59 */     Optional<Number> $$2 = $$0.get("Health").asNumber().result();
/* 60 */     if ($$1.isPresent()) {
/* 61 */       float $$3 = ((Number)$$1.get()).floatValue();
/* 62 */       $$0 = $$0.remove("HealF");
/* 63 */     } else if ($$2.isPresent()) {
/* 64 */       $$4 = ((Number)$$2.get()).floatValue();
/*    */     } else {
/* 66 */       return $$0;
/*    */     } 
/* 68 */     return $$0.set("Health", $$0.createFloat($$4));
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 73 */     return fixTypeEverywhereTyped("EntityHealthFix", getInputSchema().getType(References.ENTITY), $$0 -> $$0.update(DSL.remainderFinder(), this::fixTag));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityHealthFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */