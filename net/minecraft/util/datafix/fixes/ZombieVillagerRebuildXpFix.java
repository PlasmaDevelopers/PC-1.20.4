/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class ZombieVillagerRebuildXpFix extends NamedEntityFix {
/*    */   public ZombieVillagerRebuildXpFix(Schema $$0, boolean $$1) {
/* 11 */     super($$0, $$1, "Zombie Villager XP rebuild", References.ENTITY, "minecraft:zombie_villager");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 16 */     return $$0.update(DSL.remainderFinder(), $$0 -> {
/*    */           Optional<Number> $$1 = $$0.get("Xp").asNumber().result();
/*    */           if ($$1.isEmpty()) {
/*    */             int $$2 = $$0.get("VillagerData").get("level").asInt(1);
/*    */             return $$0.set("Xp", $$0.createInt(VillagerRebuildLevelAndXpFix.getMinXpPerLevel($$2)));
/*    */           } 
/*    */           return $$0;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ZombieVillagerRebuildXpFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */