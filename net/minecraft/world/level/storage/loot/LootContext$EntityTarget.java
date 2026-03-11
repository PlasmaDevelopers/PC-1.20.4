/*     */ package net.minecraft.world.level.storage.loot;
/*     */ 
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
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
/*     */ public enum EntityTarget
/*     */   implements StringRepresentable
/*     */ {
/* 111 */   THIS("this", LootContextParams.THIS_ENTITY),
/* 112 */   KILLER("killer", LootContextParams.KILLER_ENTITY),
/* 113 */   DIRECT_KILLER("direct_killer", LootContextParams.DIRECT_KILLER_ENTITY),
/* 114 */   KILLER_PLAYER("killer_player", LootContextParams.LAST_DAMAGE_PLAYER);
/*     */   
/*     */   static {
/* 117 */     CODEC = StringRepresentable.fromEnum(EntityTarget::values);
/*     */   }
/*     */   public static final StringRepresentable.EnumCodec<EntityTarget> CODEC;
/*     */   private final String name;
/*     */   private final LootContextParam<? extends Entity> param;
/*     */   
/*     */   EntityTarget(String $$0, LootContextParam<? extends Entity> $$1) {
/* 124 */     this.name = $$0;
/* 125 */     this.param = $$1;
/*     */   }
/*     */   
/*     */   public LootContextParam<? extends Entity> getParam() {
/* 129 */     return this.param;
/*     */   }
/*     */   
/*     */   public static EntityTarget getByName(String $$0) {
/* 133 */     EntityTarget $$1 = (EntityTarget)CODEC.byName($$0);
/* 134 */     if ($$1 != null) {
/* 135 */       return $$1;
/*     */     }
/* 137 */     throw new IllegalArgumentException("Invalid entity target " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 142 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootContext$EntityTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */