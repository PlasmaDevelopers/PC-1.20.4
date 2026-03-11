/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiRecord;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.level.entity.EntityAccess;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class GolemRandomStrollInVillageGoal extends RandomStrollGoal {
/*     */   private static final int POI_SECTION_SCAN_RADIUS = 2;
/*     */   private static final int VILLAGER_SCAN_RADIUS = 32;
/*     */   
/*     */   public GolemRandomStrollInVillageGoal(PathfinderMob $$0, double $$1) {
/*  25 */     super($$0, $$1, 240, false);
/*     */   }
/*     */   private static final int RANDOM_POS_XY_DISTANCE = 10; private static final int RANDOM_POS_Y_DISTANCE = 7;
/*     */   
/*     */   @Nullable
/*     */   protected Vec3 getPosition() {
/*     */     Vec3 $$2;
/*  32 */     float $$0 = (this.mob.level()).random.nextFloat();
/*  33 */     if ((this.mob.level()).random.nextFloat() < 0.3F) {
/*  34 */       return getPositionTowardsAnywhere();
/*     */     }
/*     */     
/*  37 */     if ($$0 < 0.7F) {
/*  38 */       Vec3 $$1 = getPositionTowardsVillagerWhoWantsGolem();
/*  39 */       if ($$1 == null) {
/*  40 */         $$1 = getPositionTowardsPoi();
/*     */       }
/*     */     } else {
/*  43 */       $$2 = getPositionTowardsPoi();
/*  44 */       if ($$2 == null) {
/*  45 */         $$2 = getPositionTowardsVillagerWhoWantsGolem();
/*     */       }
/*     */     } 
/*     */     
/*  49 */     return ($$2 == null) ? getPositionTowardsAnywhere() : $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Vec3 getPositionTowardsAnywhere() {
/*  54 */     return LandRandomPos.getPos(this.mob, 10, 7);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Vec3 getPositionTowardsVillagerWhoWantsGolem() {
/*  59 */     ServerLevel $$0 = (ServerLevel)this.mob.level();
/*  60 */     List<Villager> $$1 = $$0.getEntities((EntityTypeTest)EntityType.VILLAGER, this.mob.getBoundingBox().inflate(32.0D), this::doesVillagerWantGolem);
/*  61 */     if ($$1.isEmpty()) {
/*  62 */       return null;
/*     */     }
/*  64 */     Villager $$2 = $$1.get((this.mob.level()).random.nextInt($$1.size()));
/*  65 */     Vec3 $$3 = $$2.position();
/*  66 */     return LandRandomPos.getPosTowards(this.mob, 10, 7, $$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Vec3 getPositionTowardsPoi() {
/*  71 */     SectionPos $$0 = getRandomVillageSection();
/*  72 */     if ($$0 == null) {
/*  73 */       return null;
/*     */     }
/*     */     
/*  76 */     BlockPos $$1 = getRandomPoiWithinSection($$0);
/*  77 */     if ($$1 == null)
/*     */     {
/*  79 */       return null;
/*     */     }
/*     */     
/*  82 */     return LandRandomPos.getPosTowards(this.mob, 10, 7, Vec3.atBottomCenterOf((Vec3i)$$1));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private SectionPos getRandomVillageSection() {
/*  87 */     ServerLevel $$0 = (ServerLevel)this.mob.level();
/*     */ 
/*     */ 
/*     */     
/*  91 */     List<SectionPos> $$1 = (List<SectionPos>)SectionPos.cube(SectionPos.of((EntityAccess)this.mob), 2).filter($$1 -> ($$0.sectionsToVillage($$1) == 0)).collect(Collectors.toList());
/*     */     
/*  93 */     if ($$1.isEmpty()) {
/*  94 */       return null;
/*     */     }
/*  96 */     return $$1.get($$0.random.nextInt($$1.size()));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockPos getRandomPoiWithinSection(SectionPos $$0) {
/* 101 */     ServerLevel $$1 = (ServerLevel)this.mob.level();
/* 102 */     PoiManager $$2 = $$1.getPoiManager();
/*     */ 
/*     */     
/* 105 */     List<BlockPos> $$3 = (List<BlockPos>)$$2.getInRange($$0 -> true, $$0.center(), 8, PoiManager.Occupancy.IS_OCCUPIED).map(PoiRecord::getPos).collect(Collectors.toList());
/*     */     
/* 107 */     if ($$3.isEmpty()) {
/* 108 */       return null;
/*     */     }
/* 110 */     return $$3.get($$1.random.nextInt($$3.size()));
/*     */   }
/*     */   
/*     */   private boolean doesVillagerWantGolem(Villager $$0) {
/* 114 */     return $$0.wantsToSpawnGolem(this.mob.level().getGameTime());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\GolemRandomStrollInVillageGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */