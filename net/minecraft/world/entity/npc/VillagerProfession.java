/*    */ package net.minecraft.world.entity.npc;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*    */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*    */ 
/*    */ public final class VillagerProfession extends Record {
/*    */   private final String name;
/*    */   private final Predicate<Holder<PoiType>> heldJobSite;
/*    */   private final Predicate<Holder<PoiType>> acquirableJobSite;
/*    */   private final ImmutableSet<Item> requestedItems;
/*    */   private final ImmutableSet<Block> secondaryPoi;
/*    */   @Nullable
/*    */   private final SoundEvent workSound;
/*    */   public static final Predicate<Holder<PoiType>> ALL_ACQUIRABLE_JOBS;
/*    */   
/* 22 */   public VillagerProfession(String $$0, Predicate<Holder<PoiType>> $$1, Predicate<Holder<PoiType>> $$2, ImmutableSet<Item> $$3, ImmutableSet<Block> $$4, @Nullable SoundEvent $$5) { this.name = $$0; this.heldJobSite = $$1; this.acquirableJobSite = $$2; this.requestedItems = $$3; this.secondaryPoi = $$4; this.workSound = $$5; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/npc/VillagerProfession;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #22	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 22 */     //   0	7	0	this	Lnet/minecraft/world/entity/npc/VillagerProfession; } public String name() { return this.name; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/npc/VillagerProfession;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #22	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/npc/VillagerProfession;
/* 22 */     //   0	8	1	$$0	Ljava/lang/Object; } public Predicate<Holder<PoiType>> heldJobSite() { return this.heldJobSite; } public Predicate<Holder<PoiType>> acquirableJobSite() { return this.acquirableJobSite; } public ImmutableSet<Item> requestedItems() { return this.requestedItems; } public ImmutableSet<Block> secondaryPoi() { return this.secondaryPoi; } @Nullable public SoundEvent workSound() { return this.workSound; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 30 */     ALL_ACQUIRABLE_JOBS = ($$0 -> $$0.is(PoiTypeTags.ACQUIRABLE_JOB_SITE));
/*    */   }
/* 32 */   public static final VillagerProfession NONE = register("none", PoiType.NONE, ALL_ACQUIRABLE_JOBS, null);
/* 33 */   public static final VillagerProfession ARMORER = register("armorer", PoiTypes.ARMORER, SoundEvents.VILLAGER_WORK_ARMORER);
/* 34 */   public static final VillagerProfession BUTCHER = register("butcher", PoiTypes.BUTCHER, SoundEvents.VILLAGER_WORK_BUTCHER);
/* 35 */   public static final VillagerProfession CARTOGRAPHER = register("cartographer", PoiTypes.CARTOGRAPHER, SoundEvents.VILLAGER_WORK_CARTOGRAPHER);
/* 36 */   public static final VillagerProfession CLERIC = register("cleric", PoiTypes.CLERIC, SoundEvents.VILLAGER_WORK_CLERIC);
/* 37 */   public static final VillagerProfession FARMER = register("farmer", PoiTypes.FARMER, ImmutableSet.of(Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.BONE_MEAL), ImmutableSet.of(Blocks.FARMLAND), SoundEvents.VILLAGER_WORK_FARMER);
/* 38 */   public static final VillagerProfession FISHERMAN = register("fisherman", PoiTypes.FISHERMAN, SoundEvents.VILLAGER_WORK_FISHERMAN);
/* 39 */   public static final VillagerProfession FLETCHER = register("fletcher", PoiTypes.FLETCHER, SoundEvents.VILLAGER_WORK_FLETCHER);
/* 40 */   public static final VillagerProfession LEATHERWORKER = register("leatherworker", PoiTypes.LEATHERWORKER, SoundEvents.VILLAGER_WORK_LEATHERWORKER);
/* 41 */   public static final VillagerProfession LIBRARIAN = register("librarian", PoiTypes.LIBRARIAN, SoundEvents.VILLAGER_WORK_LIBRARIAN);
/* 42 */   public static final VillagerProfession MASON = register("mason", PoiTypes.MASON, SoundEvents.VILLAGER_WORK_MASON);
/* 43 */   public static final VillagerProfession NITWIT = register("nitwit", PoiType.NONE, PoiType.NONE, null);
/* 44 */   public static final VillagerProfession SHEPHERD = register("shepherd", PoiTypes.SHEPHERD, SoundEvents.VILLAGER_WORK_SHEPHERD);
/* 45 */   public static final VillagerProfession TOOLSMITH = register("toolsmith", PoiTypes.TOOLSMITH, SoundEvents.VILLAGER_WORK_TOOLSMITH);
/* 46 */   public static final VillagerProfession WEAPONSMITH = register("weaponsmith", PoiTypes.WEAPONSMITH, SoundEvents.VILLAGER_WORK_WEAPONSMITH);
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return this.name;
/*    */   }
/*    */   
/*    */   private static VillagerProfession register(String $$0, ResourceKey<PoiType> $$1, @Nullable SoundEvent $$2) {
/* 54 */     return register($$0, $$1 -> $$1.is($$0), $$1 -> $$1.is($$0), $$2);
/*    */   }
/*    */   
/*    */   private static VillagerProfession register(String $$0, Predicate<Holder<PoiType>> $$1, Predicate<Holder<PoiType>> $$2, @Nullable SoundEvent $$3) {
/* 58 */     return register($$0, $$1, $$2, ImmutableSet.of(), ImmutableSet.of(), $$3);
/*    */   }
/*    */   
/*    */   private static VillagerProfession register(String $$0, ResourceKey<PoiType> $$1, ImmutableSet<Item> $$2, ImmutableSet<Block> $$3, @Nullable SoundEvent $$4) {
/* 62 */     return register($$0, $$1 -> $$1.is($$0), $$1 -> $$1.is($$0), $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   private static VillagerProfession register(String $$0, Predicate<Holder<PoiType>> $$1, Predicate<Holder<PoiType>> $$2, ImmutableSet<Item> $$3, ImmutableSet<Block> $$4, @Nullable SoundEvent $$5) {
/* 66 */     return (VillagerProfession)Registry.register((Registry)BuiltInRegistries.VILLAGER_PROFESSION, new ResourceLocation($$0), new VillagerProfession($$0, $$1, $$2, $$3, $$4, $$5));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\VillagerProfession.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */