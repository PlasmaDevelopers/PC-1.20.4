/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.kinds.OptionalBox;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.DoorBlock;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InteractWithDoor
/*     */ {
/*     */   private static final int COOLDOWN_BEFORE_RERUNNING_IN_SAME_NODE = 20;
/*     */   private static final double SKIP_CLOSING_DOOR_IF_FURTHER_AWAY_THAN = 3.0D;
/*     */   private static final double MAX_DISTANCE_TO_HOLD_DOOR_OPEN_FOR_OTHER_MOBS = 2.0D;
/*     */   
/*     */   public static BehaviorControl<LivingEntity> create() {
/*  45 */     MutableObject<Node> $$0 = new MutableObject(null);
/*  46 */     MutableInt $$1 = new MutableInt(0);
/*     */     
/*  48 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.present(MemoryModuleType.PATH), (App)$$2.registered(MemoryModuleType.DOORS_TO_CLOSE), (App)$$2.registered(MemoryModuleType.NEAREST_LIVING_ENTITIES)).apply((Applicative)$$2, ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeDoorsThatIHaveOpenedOrPassedThrough(ServerLevel $$0, LivingEntity $$1, @Nullable Node $$2, @Nullable Node $$3, Set<GlobalPos> $$4, Optional<List<LivingEntity>> $$5) {
/*  98 */     Iterator<GlobalPos> $$6 = $$4.iterator();
/*  99 */     while ($$6.hasNext()) {
/* 100 */       GlobalPos $$7 = $$6.next();
/* 101 */       BlockPos $$8 = $$7.pos();
/*     */ 
/*     */       
/* 104 */       if ($$2 != null && $$2.asBlockPos().equals($$8)) {
/*     */         continue;
/*     */       }
/* 107 */       if ($$3 != null && $$3.asBlockPos().equals($$8)) {
/*     */         continue;
/*     */       }
/*     */       
/* 111 */       if (isDoorTooFarAway($$0, $$1, $$7)) {
/* 112 */         $$6.remove();
/*     */         continue;
/*     */       } 
/* 115 */       BlockState $$9 = $$0.getBlockState($$8);
/* 116 */       if (!$$9.is(BlockTags.WOODEN_DOORS, $$0 -> $$0.getBlock() instanceof DoorBlock)) {
/* 117 */         $$6.remove();
/*     */         continue;
/*     */       } 
/* 120 */       DoorBlock $$10 = (DoorBlock)$$9.getBlock();
/* 121 */       if (!$$10.isOpen($$9)) {
/* 122 */         $$6.remove();
/*     */         continue;
/*     */       } 
/* 125 */       if (areOtherMobsComingThroughDoor($$1, $$8, $$5)) {
/* 126 */         $$6.remove();
/*     */         continue;
/*     */       } 
/* 129 */       $$10.setOpen((Entity)$$1, (Level)$$0, $$9, $$8, false);
/* 130 */       $$6.remove();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean areOtherMobsComingThroughDoor(LivingEntity $$0, BlockPos $$1, Optional<List<LivingEntity>> $$2) {
/* 135 */     if ($$2.isEmpty()) {
/* 136 */       return false;
/*     */     }
/*     */     
/* 139 */     return ((List)$$2.get()).stream()
/* 140 */       .filter($$1 -> ($$1.getType() == $$0.getType()))
/* 141 */       .filter($$1 -> $$0.closerToCenterThan((Position)$$1.position(), 2.0D))
/* 142 */       .anyMatch($$1 -> isMobComingThroughDoor($$1.getBrain(), $$0));
/*     */   }
/*     */   
/*     */   private static boolean isMobComingThroughDoor(Brain<?> $$0, BlockPos $$1) {
/* 146 */     if (!$$0.hasMemoryValue(MemoryModuleType.PATH)) {
/* 147 */       return false;
/*     */     }
/* 149 */     Path $$2 = $$0.getMemory(MemoryModuleType.PATH).get();
/* 150 */     if ($$2.isDone())
/*     */     {
/* 152 */       return false;
/*     */     }
/*     */     
/* 155 */     Node $$3 = $$2.getPreviousNode();
/* 156 */     if ($$3 == null) {
/* 157 */       return false;
/*     */     }
/*     */     
/* 160 */     Node $$4 = $$2.getNextNode();
/* 161 */     return ($$1.equals($$3.asBlockPos()) || $$1.equals($$4.asBlockPos()));
/*     */   }
/*     */   
/*     */   private static boolean isDoorTooFarAway(ServerLevel $$0, LivingEntity $$1, GlobalPos $$2) {
/* 165 */     return ($$2.dimension() != $$0.dimension() || 
/* 166 */       !$$2.pos().closerToCenterThan((Position)$$1.position(), 3.0D));
/*     */   }
/*     */   
/*     */   private static Optional<Set<GlobalPos>> rememberDoorToClose(MemoryAccessor<OptionalBox.Mu, Set<GlobalPos>> $$0, Optional<Set<GlobalPos>> $$1, ServerLevel $$2, BlockPos $$3) {
/* 170 */     GlobalPos $$4 = GlobalPos.of($$2.dimension(), $$3);
/*     */     
/* 172 */     return Optional.of($$1.<Set<GlobalPos>>map($$1 -> {
/*     */             $$1.add($$0);
/*     */             return $$1;
/* 175 */           }).orElseGet(() -> {
/*     */             Set<GlobalPos> $$2 = Sets.newHashSet((Object[])new GlobalPos[] { $$0 });
/*     */             $$1.set($$2);
/*     */             return $$2;
/*     */           }));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\InteractWithDoor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */