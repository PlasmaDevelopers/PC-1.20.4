/*      */ package net.minecraft.world.entity.animal;
/*      */ 
/*      */ import java.util.EnumSet;
/*      */ import java.util.Optional;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.tags.BlockTags;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.world.entity.ai.goal.Goal;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.DoublePlantBlock;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*      */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class BeePollinateGoal
/*      */   extends Bee.BaseBeeGoal
/*      */ {
/*      */   private static final int MIN_POLLINATION_TICKS = 400;
/*      */   private static final int MIN_FIND_FLOWER_RETRY_COOLDOWN = 20;
/*      */   private static final int MAX_FIND_FLOWER_RETRY_COOLDOWN = 60;
/*      */   private final Predicate<BlockState> VALID_POLLINATION_BLOCKS;
/*      */   private static final double ARRIVAL_THRESHOLD = 0.1D;
/*      */   private static final int POSITION_CHANGE_CHANCE = 25;
/*      */   private static final float SPEED_MODIFIER = 0.35F;
/*      */   private static final float HOVER_HEIGHT_WITHIN_FLOWER = 0.6F;
/*      */   private static final float HOVER_POS_OFFSET = 0.33333334F;
/*      */   private int successfulPollinatingTicks;
/*      */   private int lastSoundPlayedTick;
/*      */   private boolean pollinating;
/*      */   @Nullable
/*      */   private Vec3 hoverPos;
/*      */   private int pollinatingTicks;
/*      */   private static final int MAX_POLLINATING_TICKS = 600;
/*      */   
/*      */   BeePollinateGoal() {
/* 1100 */     this.VALID_POLLINATION_BLOCKS = ($$0 -> 
/* 1101 */       ($$0.hasProperty((Property)BlockStateProperties.WATERLOGGED) && ((Boolean)$$0.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue()) ? false : ($$0.is(BlockTags.FLOWERS) ? ($$0.is(Blocks.SUNFLOWER) ? (($$0.getValue((Property)DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER)) : true) : false));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1130 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeeUse() {
/* 1135 */     if (Bee.this.remainingCooldownBeforeLocatingNewFlower > 0) {
/* 1136 */       return false;
/*      */     }
/*      */     
/* 1139 */     if (Bee.this.hasNectar()) {
/* 1140 */       return false;
/*      */     }
/* 1142 */     if (Bee.this.level().isRaining()) {
/* 1143 */       return false;
/*      */     }
/*      */     
/* 1146 */     Optional<BlockPos> $$0 = findNearbyFlower();
/* 1147 */     if ($$0.isPresent()) {
/* 1148 */       Bee.this.savedFlowerPos = $$0.get();
/*      */       
/* 1150 */       Bee.access$1800(Bee.this).moveTo(Bee.this.savedFlowerPos.getX() + 0.5D, Bee.this.savedFlowerPos.getY() + 0.5D, Bee.this.savedFlowerPos.getZ() + 0.5D, 1.2000000476837158D);
/* 1151 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1155 */     Bee.this.remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(Bee.access$1900(Bee.this), 20, 60);
/* 1156 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeeContinueToUse() {
/* 1161 */     if (!this.pollinating) {
/* 1162 */       return false;
/*      */     }
/* 1164 */     if (!Bee.this.hasSavedFlowerPos()) {
/* 1165 */       return false;
/*      */     }
/* 1167 */     if (Bee.this.level().isRaining()) {
/* 1168 */       return false;
/*      */     }
/* 1170 */     if (hasPollinatedLongEnough()) {
/* 1171 */       return (Bee.access$2000(Bee.this).nextFloat() < 0.2F);
/*      */     }
/*      */     
/* 1174 */     if (Bee.this.tickCount % 20 == 0 && !Bee.this.isFlowerValid(Bee.this.savedFlowerPos)) {
/* 1175 */       Bee.this.savedFlowerPos = null;
/* 1176 */       return false;
/*      */     } 
/* 1178 */     return true;
/*      */   }
/*      */   
/*      */   private boolean hasPollinatedLongEnough() {
/* 1182 */     return (this.successfulPollinatingTicks > 400);
/*      */   }
/*      */   
/*      */   boolean isPollinating() {
/* 1186 */     return this.pollinating;
/*      */   }
/*      */   
/*      */   void stopPollinating() {
/* 1190 */     this.pollinating = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void start() {
/* 1195 */     this.successfulPollinatingTicks = 0;
/* 1196 */     this.pollinatingTicks = 0;
/* 1197 */     this.lastSoundPlayedTick = 0;
/* 1198 */     this.pollinating = true;
/* 1199 */     Bee.this.resetTicksWithoutNectarSinceExitingHive();
/*      */   }
/*      */ 
/*      */   
/*      */   public void stop() {
/* 1204 */     if (hasPollinatedLongEnough()) {
/* 1205 */       Bee.this.setHasNectar(true);
/*      */     }
/* 1207 */     this.pollinating = false;
/* 1208 */     Bee.access$2100(Bee.this).stop();
/*      */     
/* 1210 */     Bee.this.remainingCooldownBeforeLocatingNewFlower = 200;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean requiresUpdateEveryTick() {
/* 1215 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/* 1220 */     this.pollinatingTicks++;
/* 1221 */     if (this.pollinatingTicks > 600) {
/*      */       
/* 1223 */       Bee.this.savedFlowerPos = null;
/*      */       
/*      */       return;
/*      */     } 
/* 1227 */     Vec3 $$0 = Vec3.atBottomCenterOf((Vec3i)Bee.this.savedFlowerPos).add(0.0D, 0.6000000238418579D, 0.0D);
/*      */     
/* 1229 */     if ($$0.distanceTo(Bee.this.position()) > 1.0D) {
/* 1230 */       this.hoverPos = $$0;
/* 1231 */       setWantedPos();
/*      */       
/*      */       return;
/*      */     } 
/* 1235 */     if (this.hoverPos == null) {
/* 1236 */       this.hoverPos = $$0;
/*      */     }
/*      */     
/* 1239 */     boolean $$1 = (Bee.this.position().distanceTo(this.hoverPos) <= 0.1D);
/* 1240 */     boolean $$2 = true;
/*      */     
/* 1242 */     if (!$$1 && this.pollinatingTicks > 600) {
/*      */       
/* 1244 */       Bee.this.savedFlowerPos = null;
/*      */       
/*      */       return;
/*      */     } 
/* 1248 */     if ($$1) {
/* 1249 */       boolean $$3 = (Bee.access$2200(Bee.this).nextInt(25) == 0);
/* 1250 */       if ($$3) {
/* 1251 */         this.hoverPos = new Vec3($$0.x() + getOffset(), $$0.y(), $$0.z() + getOffset());
/*      */         
/* 1253 */         Bee.access$2300(Bee.this).stop();
/*      */       } else {
/* 1255 */         $$2 = false;
/*      */       } 
/*      */       
/* 1258 */       Bee.this.getLookControl().setLookAt($$0.x(), $$0.y(), $$0.z());
/*      */     } 
/*      */     
/* 1261 */     if ($$2) {
/* 1262 */       setWantedPos();
/*      */     }
/*      */     
/* 1265 */     this.successfulPollinatingTicks++;
/*      */     
/* 1267 */     if (Bee.access$2400(Bee.this).nextFloat() < 0.05F && this.successfulPollinatingTicks > this.lastSoundPlayedTick + 60) {
/* 1268 */       this.lastSoundPlayedTick = this.successfulPollinatingTicks;
/* 1269 */       Bee.this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setWantedPos() {
/* 1274 */     Bee.this.getMoveControl().setWantedPosition(this.hoverPos.x(), this.hoverPos.y(), this.hoverPos.z(), 0.3499999940395355D);
/*      */   }
/*      */   
/*      */   private float getOffset() {
/* 1278 */     return (Bee.access$2500(Bee.this).nextFloat() * 2.0F - 1.0F) * 0.33333334F;
/*      */   }
/*      */   
/*      */   private Optional<BlockPos> findNearbyFlower() {
/* 1282 */     return findNearestBlock(this.VALID_POLLINATION_BLOCKS, 5.0D);
/*      */   }
/*      */   
/*      */   private Optional<BlockPos> findNearestBlock(Predicate<BlockState> $$0, double $$1) {
/* 1286 */     BlockPos $$2 = Bee.this.blockPosition();
/*      */     
/* 1288 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos(); int $$4;
/* 1289 */     for ($$4 = 0; $$4 <= $$1; $$4 = ($$4 > 0) ? -$$4 : (1 - $$4)) {
/* 1290 */       for (int $$5 = 0; $$5 < $$1; $$5++) {
/* 1291 */         int $$6; for ($$6 = 0; $$6 <= $$5; $$6 = ($$6 > 0) ? -$$6 : (1 - $$6)) {
/*      */           
/* 1293 */           int $$7 = ($$6 < $$5 && $$6 > -$$5) ? $$5 : 0;
/* 1294 */           for (; $$7 <= $$5; $$7 = ($$7 > 0) ? -$$7 : (1 - $$7)) {
/* 1295 */             $$3.setWithOffset((Vec3i)$$2, $$6, $$4 - 1, $$7);
/* 1296 */             if ($$2.closerThan((Vec3i)$$3, $$1) && $$0.test(Bee.this.level().getBlockState((BlockPos)$$3))) {
/* 1297 */               return (Optional)Optional.of($$3);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1304 */     return Optional.empty();
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Bee$BeePollinateGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */