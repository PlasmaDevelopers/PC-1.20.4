/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.GameEventTags;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*    */ 
/*    */ public class GameEventTagsProvider
/*    */   extends IntrinsicHolderTagsProvider<GameEvent> {
/*    */   @VisibleForTesting
/* 16 */   static final GameEvent[] VIBRATIONS_EXCEPT_FLAP = new GameEvent[] { GameEvent.BLOCK_ATTACH, GameEvent.BLOCK_CHANGE, GameEvent.BLOCK_CLOSE, GameEvent.BLOCK_DESTROY, GameEvent.BLOCK_DETACH, GameEvent.BLOCK_OPEN, GameEvent.BLOCK_PLACE, GameEvent.BLOCK_ACTIVATE, GameEvent.BLOCK_DEACTIVATE, GameEvent.CONTAINER_CLOSE, GameEvent.CONTAINER_OPEN, GameEvent.DRINK, GameEvent.EAT, GameEvent.ELYTRA_GLIDE, GameEvent.ENTITY_DAMAGE, GameEvent.ENTITY_DIE, GameEvent.ENTITY_DISMOUNT, GameEvent.ENTITY_INTERACT, GameEvent.ENTITY_MOUNT, GameEvent.ENTITY_PLACE, GameEvent.ENTITY_ACTION, GameEvent.EQUIP, GameEvent.EXPLODE, GameEvent.FLUID_PICKUP, GameEvent.FLUID_PLACE, GameEvent.HIT_GROUND, GameEvent.INSTRUMENT_PLAY, GameEvent.ITEM_INTERACT_FINISH, GameEvent.LIGHTNING_STRIKE, GameEvent.NOTE_BLOCK_PLAY, GameEvent.PRIME_FUSE, GameEvent.PROJECTILE_LAND, GameEvent.PROJECTILE_SHOOT, GameEvent.SHEAR, GameEvent.SPLASH, GameEvent.STEP, GameEvent.SWIM, GameEvent.TELEPORT, GameEvent.UNEQUIP };
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameEventTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 60 */     super($$0, Registries.GAME_EVENT, $$1, $$0 -> $$0.builtInRegistryHolder().key());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 65 */     tag(GameEventTags.VIBRATIONS).add(VIBRATIONS_EXCEPT_FLAP).add(VibrationSystem.RESONANCE_EVENTS).add(GameEvent.FLAP);
/*    */     
/* 67 */     tag(GameEventTags.SHRIEKER_CAN_LISTEN).add(GameEvent.SCULK_SENSOR_TENDRILS_CLICKING);
/*    */     
/* 69 */     tag(GameEventTags.WARDEN_CAN_LISTEN).add(VIBRATIONS_EXCEPT_FLAP).add(VibrationSystem.RESONANCE_EVENTS).add(GameEvent.SHRIEK).addTag(GameEventTags.SHRIEKER_CAN_LISTEN);
/*    */     
/* 71 */     tag(GameEventTags.IGNORE_VIBRATIONS_SNEAKING).add(new GameEvent[] { GameEvent.HIT_GROUND, GameEvent.PROJECTILE_SHOOT, GameEvent.STEP, GameEvent.SWIM, GameEvent.ITEM_INTERACT_START, GameEvent.ITEM_INTERACT_FINISH });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     tag(GameEventTags.ALLAY_CAN_LISTEN).add(GameEvent.NOTE_BLOCK_PLAY);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\GameEventTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */