/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.ToIntFunction;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.player.Player;
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
/*     */ enum Type
/*     */ {
/*     */   POINTS, LEVELS;
/*     */   public final BiConsumer<ServerPlayer, Integer> add;
/*     */   public final BiPredicate<ServerPlayer, Integer> set;
/*     */   public final String name;
/*     */   final ToIntFunction<ServerPlayer> query;
/*     */   
/*     */   static {
/* 137 */     POINTS = new Type("POINTS", 0, "points", Player::giveExperiencePoints, ($$0, $$1) -> {
/*     */           if ($$1.intValue() >= $$0.getXpNeededForNextLevel()) {
/*     */             return false;
/*     */           }
/*     */           $$0.setExperiencePoints($$1.intValue());
/*     */           return true;
/*     */         }$$0 -> Mth.floor($$0.experienceProgress * $$0.getXpNeededForNextLevel()));
/* 144 */     LEVELS = new Type("LEVELS", 1, "levels", ServerPlayer::giveExperienceLevels, ($$0, $$1) -> {
/*     */           $$0.setExperienceLevels($$1.intValue());
/*     */           return true;
/*     */         }$$0 -> $$0.experienceLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Type(String $$0, BiConsumer<ServerPlayer, Integer> $$1, BiPredicate<ServerPlayer, Integer> $$2, ToIntFunction<ServerPlayer> $$3) {
/* 155 */     this.add = $$1;
/* 156 */     this.name = $$0;
/* 157 */     this.set = $$2;
/* 158 */     this.query = $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ExperienceCommand$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */