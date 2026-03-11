/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ public enum MobSpawnType {
/*  4 */   NATURAL,
/*  5 */   CHUNK_GENERATION,
/*  6 */   SPAWNER,
/*  7 */   STRUCTURE,
/*  8 */   BREEDING,
/*  9 */   MOB_SUMMONED,
/* 10 */   JOCKEY,
/* 11 */   EVENT,
/* 12 */   CONVERSION,
/* 13 */   REINFORCEMENT,
/* 14 */   TRIGGERED,
/* 15 */   BUCKET,
/* 16 */   SPAWN_EGG,
/* 17 */   COMMAND,
/* 18 */   DISPENSER,
/* 19 */   PATROL,
/* 20 */   TRIAL_SPAWNER;
/*    */ 
/*    */   
/*    */   public static boolean isSpawner(MobSpawnType $$0) {
/* 24 */     return ($$0 == SPAWNER || $$0 == TRIAL_SPAWNER);
/*    */   }
/*    */   
/*    */   public static boolean ignoresLightRequirements(MobSpawnType $$0) {
/* 28 */     return ($$0 == TRIAL_SPAWNER);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\MobSpawnType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */