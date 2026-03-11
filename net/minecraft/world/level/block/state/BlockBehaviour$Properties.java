/*     */ package net.minecraft.world.level.block.state;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToIntFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.flag.FeatureFlag;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SoundType;
/*     */ import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
/*     */ import net.minecraft.world.level.material.MapColor;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Properties
/*     */ {
/* 512 */   public static final Codec<Properties> CODEC = Codec.unit(() -> of());
/*     */   
/*     */   Function<BlockState, MapColor> mapColor = $$0 -> MapColor.NONE;
/*     */   
/*     */   boolean hasCollision = true;
/* 517 */   SoundType soundType = SoundType.STONE;
/*     */   ToIntFunction<BlockState> lightEmission = $$0 -> 0;
/*     */   float explosionResistance;
/*     */   float destroyTime;
/*     */   boolean requiresCorrectToolForDrops;
/*     */   boolean isRandomlyTicking;
/* 523 */   float friction = 0.6F;
/* 524 */   float speedFactor = 1.0F;
/* 525 */   float jumpFactor = 1.0F;
/*     */   ResourceLocation drops;
/*     */   boolean canOcclude = true;
/*     */   boolean isAir;
/*     */   boolean ignitedByLava;
/*     */   @Deprecated
/*     */   boolean liquid;
/*     */   @Deprecated
/*     */   boolean forceSolidOff;
/*     */   boolean forceSolidOn;
/* 535 */   PushReaction pushReaction = PushReaction.NORMAL;
/*     */   boolean spawnTerrainParticles = true;
/* 537 */   NoteBlockInstrument instrument = NoteBlockInstrument.HARP; boolean replaceable; BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn; BlockBehaviour.StatePredicate isRedstoneConductor; BlockBehaviour.StatePredicate isSuffocating; BlockBehaviour.StatePredicate isViewBlocking; BlockBehaviour.StatePredicate hasPostProcess; BlockBehaviour.StatePredicate emissiveRendering; boolean dynamicShape; FeatureFlagSet requiredFeatures; Optional<BlockBehaviour.OffsetFunction> offsetFunction;
/*     */   
/*     */   private Properties() {
/* 540 */     this.isValidSpawn = (($$0, $$1, $$2, $$3) -> 
/* 541 */       ($$0.isFaceSturdy($$1, $$2, Direction.UP) && $$0.getLightEmission() < 14));
/*     */     
/* 543 */     this.isRedstoneConductor = (($$0, $$1, $$2) -> $$0.isCollisionShapeFullBlock($$1, $$2));
/*     */ 
/*     */     
/* 546 */     this.isSuffocating = (($$0, $$1, $$2) -> 
/* 547 */       ($$0.blocksMotion() && $$0.isCollisionShapeFullBlock($$1, $$2)));
/*     */     
/* 549 */     this.isViewBlocking = this.isSuffocating;
/* 550 */     this.hasPostProcess = (($$0, $$1, $$2) -> false);
/* 551 */     this.emissiveRendering = (($$0, $$1, $$2) -> false);
/*     */ 
/*     */     
/* 554 */     this.requiredFeatures = FeatureFlags.VANILLA_SET;
/*     */     
/* 556 */     this.offsetFunction = Optional.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Properties of() {
/* 562 */     return new Properties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Properties ofFullCopy(BlockBehaviour $$0) {
/* 573 */     Properties $$1 = ofLegacyCopy($$0);
/* 574 */     Properties $$2 = $$0.properties;
/*     */     
/* 576 */     $$1.jumpFactor = $$2.jumpFactor;
/* 577 */     $$1.isRedstoneConductor = $$2.isRedstoneConductor;
/* 578 */     $$1.isValidSpawn = $$2.isValidSpawn;
/* 579 */     $$1.hasPostProcess = $$2.hasPostProcess;
/* 580 */     $$1.isSuffocating = $$2.isSuffocating;
/* 581 */     $$1.isViewBlocking = $$2.isViewBlocking;
/* 582 */     $$1.drops = $$2.drops;
/*     */     
/* 584 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Properties ofLegacyCopy(BlockBehaviour $$0) {
/* 593 */     Properties $$1 = new Properties();
/* 594 */     Properties $$2 = $$0.properties;
/*     */     
/* 596 */     $$1.destroyTime = $$2.destroyTime;
/* 597 */     $$1.explosionResistance = $$2.explosionResistance;
/* 598 */     $$1.hasCollision = $$2.hasCollision;
/* 599 */     $$1.isRandomlyTicking = $$2.isRandomlyTicking;
/* 600 */     $$1.lightEmission = $$2.lightEmission;
/* 601 */     $$1.mapColor = $$2.mapColor;
/* 602 */     $$1.soundType = $$2.soundType;
/* 603 */     $$1.friction = $$2.friction;
/* 604 */     $$1.speedFactor = $$2.speedFactor;
/* 605 */     $$1.dynamicShape = $$2.dynamicShape;
/* 606 */     $$1.canOcclude = $$2.canOcclude;
/* 607 */     $$1.isAir = $$2.isAir;
/* 608 */     $$1.ignitedByLava = $$2.ignitedByLava;
/* 609 */     $$1.liquid = $$2.liquid;
/* 610 */     $$1.forceSolidOff = $$2.forceSolidOff;
/* 611 */     $$1.forceSolidOn = $$2.forceSolidOn;
/* 612 */     $$1.pushReaction = $$2.pushReaction;
/* 613 */     $$1.requiresCorrectToolForDrops = $$2.requiresCorrectToolForDrops;
/* 614 */     $$1.offsetFunction = $$2.offsetFunction;
/* 615 */     $$1.spawnTerrainParticles = $$2.spawnTerrainParticles;
/* 616 */     $$1.requiredFeatures = $$2.requiredFeatures;
/* 617 */     $$1.emissiveRendering = $$2.emissiveRendering;
/* 618 */     $$1.instrument = $$2.instrument;
/* 619 */     $$1.replaceable = $$2.replaceable;
/*     */     
/* 621 */     return $$1;
/*     */   }
/*     */   
/*     */   public Properties mapColor(DyeColor $$0) {
/* 625 */     this.mapColor = ($$1 -> $$0.getMapColor());
/* 626 */     return this;
/*     */   }
/*     */   
/*     */   public Properties mapColor(MapColor $$0) {
/* 630 */     this.mapColor = ($$1 -> $$0);
/* 631 */     return this;
/*     */   }
/*     */   
/*     */   public Properties mapColor(Function<BlockState, MapColor> $$0) {
/* 635 */     this.mapColor = $$0;
/* 636 */     return this;
/*     */   }
/*     */   
/*     */   public Properties noCollission() {
/* 640 */     this.hasCollision = false;
/* 641 */     this.canOcclude = false;
/* 642 */     return this;
/*     */   }
/*     */   
/*     */   public Properties noOcclusion() {
/* 646 */     this.canOcclude = false;
/* 647 */     return this;
/*     */   }
/*     */   
/*     */   public Properties friction(float $$0) {
/* 651 */     this.friction = $$0;
/* 652 */     return this;
/*     */   }
/*     */   
/*     */   public Properties speedFactor(float $$0) {
/* 656 */     this.speedFactor = $$0;
/* 657 */     return this;
/*     */   }
/*     */   
/*     */   public Properties jumpFactor(float $$0) {
/* 661 */     this.jumpFactor = $$0;
/* 662 */     return this;
/*     */   }
/*     */   
/*     */   public Properties sound(SoundType $$0) {
/* 666 */     this.soundType = $$0;
/* 667 */     return this;
/*     */   }
/*     */   
/*     */   public Properties lightLevel(ToIntFunction<BlockState> $$0) {
/* 671 */     this.lightEmission = $$0;
/* 672 */     return this;
/*     */   }
/*     */   
/*     */   public Properties strength(float $$0, float $$1) {
/* 676 */     return destroyTime($$0).explosionResistance($$1);
/*     */   }
/*     */   
/*     */   public Properties instabreak() {
/* 680 */     return strength(0.0F);
/*     */   }
/*     */   
/*     */   public Properties strength(float $$0) {
/* 684 */     strength($$0, $$0);
/* 685 */     return this;
/*     */   }
/*     */   
/*     */   public Properties randomTicks() {
/* 689 */     this.isRandomlyTicking = true;
/* 690 */     return this;
/*     */   }
/*     */   
/*     */   public Properties dynamicShape() {
/* 694 */     this.dynamicShape = true;
/* 695 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties noLootTable() {
/* 703 */     this.drops = BuiltInLootTables.EMPTY;
/* 704 */     return this;
/*     */   }
/*     */   
/*     */   public Properties dropsLike(Block $$0) {
/* 708 */     this.drops = $$0.getLootTable();
/* 709 */     return this;
/*     */   }
/*     */   
/*     */   public Properties ignitedByLava() {
/* 713 */     this.ignitedByLava = true;
/* 714 */     return this;
/*     */   }
/*     */   
/*     */   public Properties liquid() {
/* 718 */     this.liquid = true;
/* 719 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties forceSolidOn() {
/* 726 */     this.forceSolidOn = true;
/* 727 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Properties forceSolidOff() {
/* 736 */     this.forceSolidOff = true;
/* 737 */     return this;
/*     */   }
/*     */   
/*     */   public Properties pushReaction(PushReaction $$0) {
/* 741 */     this.pushReaction = $$0;
/* 742 */     return this;
/*     */   }
/*     */   
/*     */   public Properties air() {
/* 746 */     this.isAir = true;
/* 747 */     return this;
/*     */   }
/*     */   
/*     */   public Properties isValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> $$0) {
/* 751 */     this.isValidSpawn = $$0;
/* 752 */     return this;
/*     */   }
/*     */   
/*     */   public Properties isRedstoneConductor(BlockBehaviour.StatePredicate $$0) {
/* 756 */     this.isRedstoneConductor = $$0;
/* 757 */     return this;
/*     */   }
/*     */   
/*     */   public Properties isSuffocating(BlockBehaviour.StatePredicate $$0) {
/* 761 */     this.isSuffocating = $$0;
/* 762 */     return this;
/*     */   }
/*     */   
/*     */   public Properties isViewBlocking(BlockBehaviour.StatePredicate $$0) {
/* 766 */     this.isViewBlocking = $$0;
/* 767 */     return this;
/*     */   }
/*     */   
/*     */   public Properties hasPostProcess(BlockBehaviour.StatePredicate $$0) {
/* 771 */     this.hasPostProcess = $$0;
/* 772 */     return this;
/*     */   }
/*     */   
/*     */   public Properties emissiveRendering(BlockBehaviour.StatePredicate $$0) {
/* 776 */     this.emissiveRendering = $$0;
/* 777 */     return this;
/*     */   }
/*     */   
/*     */   public Properties requiresCorrectToolForDrops() {
/* 781 */     this.requiresCorrectToolForDrops = true;
/* 782 */     return this;
/*     */   }
/*     */   
/*     */   public Properties destroyTime(float $$0) {
/* 786 */     this.destroyTime = $$0;
/* 787 */     return this;
/*     */   }
/*     */   
/*     */   public Properties explosionResistance(float $$0) {
/* 791 */     this.explosionResistance = Math.max(0.0F, $$0);
/* 792 */     return this;
/*     */   }
/*     */   
/*     */   public Properties offsetType(BlockBehaviour.OffsetType $$0) {
/* 796 */     switch (BlockBehaviour.null.$SwitchMap$net$minecraft$world$level$block$state$BlockBehaviour$OffsetType[$$0.ordinal()]) { default:
/* 797 */         this.offsetFunction = Optional.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 818 */         return this;case 1: this.offsetFunction = Optional.of(($$0, $$1, $$2) -> { Block $$3 = $$0.getBlock(); long $$4 = Mth.getSeed($$2.getX(), 0, $$2.getZ()); double $$5 = (((float)($$4 >> 4L & 0xFL) / 15.0F) - 1.0D) * $$3.getMaxVerticalOffset(); float $$6 = $$3.getMaxHorizontalOffset(); double $$7 = Mth.clamp((((float)($$4 & 0xFL) / 15.0F) - 0.5D) * 0.5D, -$$6, $$6); double $$8 = Mth.clamp((((float)($$4 >> 8L & 0xFL) / 15.0F) - 0.5D) * 0.5D, -$$6, $$6); return new Vec3($$7, $$5, $$8); }); return this;case 2: break; }  this.offsetFunction = Optional.of(($$0, $$1, $$2) -> { Block $$3 = $$0.getBlock(); long $$4 = Mth.getSeed($$2.getX(), 0, $$2.getZ()); float $$5 = $$3.getMaxHorizontalOffset(); double $$6 = Mth.clamp((((float)($$4 & 0xFL) / 15.0F) - 0.5D) * 0.5D, -$$5, $$5); double $$7 = Mth.clamp((((float)($$4 >> 8L & 0xFL) / 15.0F) - 0.5D) * 0.5D, -$$5, $$5); return new Vec3($$6, 0.0D, $$7); }); return this;
/*     */   }
/*     */   
/*     */   public Properties noTerrainParticles() {
/* 822 */     this.spawnTerrainParticles = false;
/* 823 */     return this;
/*     */   }
/*     */   
/*     */   public Properties requiredFeatures(FeatureFlag... $$0) {
/* 827 */     this.requiredFeatures = FeatureFlags.REGISTRY.subset($$0);
/* 828 */     return this;
/*     */   }
/*     */   
/*     */   public Properties instrument(NoteBlockInstrument $$0) {
/* 832 */     this.instrument = $$0;
/* 833 */     return this;
/*     */   }
/*     */   
/*     */   public Properties replaceable() {
/* 837 */     this.replaceable = true;
/* 838 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\BlockBehaviour$Properties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */