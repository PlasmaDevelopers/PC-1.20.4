/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntityZombieSplitFix
/*    */   extends SimpleEntityRenameFix {
/*    */   public EntityZombieSplitFix(Schema $$0, boolean $$1) {
/* 11 */     super("EntityZombieSplitFix", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Pair<String, Dynamic<?>> getNewNameAndTag(String $$0, Dynamic<?> $$1) {
/* 16 */     if (Objects.equals("Zombie", $$0))
/* 17 */     { String $$2 = "Zombie";
/* 18 */       int $$3 = $$1.get("ZombieType").asInt(0);
/* 19 */       switch ($$3)
/*    */       
/*    */       { 
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
/*    */         default:
/* 35 */           $$1 = $$1.remove("ZombieType");
/* 36 */           return Pair.of($$2, $$1);
/*    */         case 1: case 2: case 3: case 4: case 5: $$2 = "ZombieVillager"; $$1 = $$1.set("Profession", $$1.createInt($$3 - 1));
/* 38 */         case 6: break; }  $$2 = "Husk"; }  return Pair.of($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityZombieSplitFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */