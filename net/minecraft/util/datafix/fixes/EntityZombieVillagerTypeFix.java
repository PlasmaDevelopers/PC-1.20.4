/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class EntityZombieVillagerTypeFix extends NamedEntityFix {
/*    */   private static final int PROFESSION_MAX = 6;
/*    */   
/*    */   public EntityZombieVillagerTypeFix(Schema $$0, boolean $$1) {
/* 13 */     super($$0, $$1, "EntityZombieVillagerTypeFix", References.ENTITY, "Zombie");
/*    */   }
/*    */   
/* 16 */   private static final RandomSource RANDOM = RandomSource.create();
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 19 */     if ($$0.get("IsVillager").asBoolean(false)) {
/* 20 */       if ($$0.get("ZombieType").result().isEmpty()) {
/* 21 */         int $$1 = getVillagerProfession($$0.get("VillagerProfession").asInt(-1));
/* 22 */         if ($$1 == -1) {
/* 23 */           $$1 = getVillagerProfession(RANDOM.nextInt(6));
/*    */         }
/*    */         
/* 26 */         $$0 = $$0.set("ZombieType", $$0.createInt($$1));
/*    */       } 
/*    */       
/* 29 */       $$0 = $$0.remove("IsVillager");
/*    */     } 
/* 31 */     return $$0;
/*    */   }
/*    */   
/*    */   private int getVillagerProfession(int $$0) {
/* 35 */     if ($$0 < 0 || $$0 >= 6) {
/* 36 */       return -1;
/*    */     }
/* 38 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 43 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityZombieVillagerTypeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */