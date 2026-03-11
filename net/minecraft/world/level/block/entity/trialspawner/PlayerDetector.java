/*    */ package net.minecraft.world.level.block.entity.trialspawner;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.entity.EntityTypeTest;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public interface PlayerDetector {
/*    */   static {
/* 14 */     PLAYERS = (($$0, $$1, $$2) -> $$0.getPlayers(()).stream().map(Entity::getUUID).toList());
/*    */ 
/*    */ 
/*    */     
/* 18 */     SHEEP = (($$0, $$1, $$2) -> {
/*    */         AABB $$3 = (new AABB($$1)).inflate($$2);
/*    */         return $$0.getEntities((EntityTypeTest)EntityType.SHEEP, $$3, LivingEntity::isAlive).stream().map(Entity::getUUID).toList();
/*    */       });
/*    */   }
/*    */   
/*    */   public static final PlayerDetector PLAYERS;
/*    */   public static final PlayerDetector SHEEP;
/*    */   
/*    */   List<UUID> detect(ServerLevel paramServerLevel, BlockPos paramBlockPos, int paramInt);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\trialspawner\PlayerDetector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */