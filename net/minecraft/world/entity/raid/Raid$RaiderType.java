/*     */ package net.minecraft.world.entity.raid;
/*     */ 
/*     */ import net.minecraft.world.entity.EntityType;
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
/*     */ enum RaiderType
/*     */ {
/*     */   static final RaiderType[] VALUES;
/*  88 */   VINDICATOR(EntityType.VINDICATOR, new int[] { 0, 0, 2, 0, 1, 4, 2, 5 }),
/*  89 */   EVOKER(EntityType.EVOKER, new int[] { 0, 0, 0, 0, 0, 1, 1, 2
/*     */     }),
/*  91 */   PILLAGER(EntityType.PILLAGER, new int[] { 0, 4, 3, 3, 4, 4, 4, 2 }),
/*  92 */   WITCH(EntityType.WITCH, new int[] { 0, 0, 0, 0, 3, 0, 0, 1 }),
/*  93 */   RAVAGER(EntityType.RAVAGER, new int[] { 0, 0, 0, 1, 0, 1, 0, 2 });
/*     */   
/*     */   static {
/*  96 */     VALUES = values();
/*     */   }
/*     */   
/*     */   final EntityType<? extends Raider> entityType;
/*     */   
/*     */   RaiderType(EntityType<? extends Raider> $$0, int[] $$1) {
/* 102 */     this.entityType = $$0;
/* 103 */     this.spawnsPerWaveBeforeBonus = $$1;
/*     */   }
/*     */   
/*     */   final int[] spawnsPerWaveBeforeBonus;
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\raid\Raid$RaiderType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */