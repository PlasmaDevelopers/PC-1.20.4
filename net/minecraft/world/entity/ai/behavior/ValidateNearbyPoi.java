/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.network.protocol.game.DebugPackets;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*    */ import net.minecraft.world.level.block.BedBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class ValidateNearbyPoi
/*    */ {
/*    */   public static BehaviorControl<LivingEntity> create(Predicate<Holder<PoiType>> $$0, MemoryModuleType<GlobalPos> $$1) {
/* 26 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.present($$0)).apply((Applicative)$$2, ()));
/*    */   }
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
/*    */   private static final int MAX_DISTANCE = 16;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean bedIsOccupied(ServerLevel $$0, BlockPos $$1, LivingEntity $$2) {
/* 49 */     BlockState $$3 = $$0.getBlockState($$1);
/* 50 */     return ($$3.is(BlockTags.BEDS) && ((Boolean)$$3.getValue((Property)BedBlock.OCCUPIED)).booleanValue() && !$$2.isSleeping());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\ValidateNearbyPoi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */