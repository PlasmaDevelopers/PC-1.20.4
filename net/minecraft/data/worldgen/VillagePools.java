/*    */ package net.minecraft.data.worldgen;
/*    */ 
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ public class VillagePools {
/*    */   public static void bootstrap(BootstapContext<StructureTemplatePool> $$0) {
/*  7 */     PlainVillagePools.bootstrap($$0);
/*  8 */     SnowyVillagePools.bootstrap($$0);
/*  9 */     SavannaVillagePools.bootstrap($$0);
/* 10 */     DesertVillagePools.bootstrap($$0);
/* 11 */     TaigaVillagePools.bootstrap($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\VillagePools.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */