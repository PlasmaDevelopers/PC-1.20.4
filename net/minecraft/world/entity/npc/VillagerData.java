/*    */ package net.minecraft.world.entity.npc;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public class VillagerData {
/*    */   public static final int MIN_VILLAGER_LEVEL = 1;
/* 10 */   private static final int[] NEXT_LEVEL_XP_THRESHOLDS = new int[] { 0, 10, 70, 150, 250 }; public static final int MAX_VILLAGER_LEVEL = 5; public static final Codec<VillagerData> CODEC; private final VillagerType type;
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.VILLAGER_TYPE.byNameCodec().fieldOf("type").orElseGet(()).forGetter(()), (App)BuiltInRegistries.VILLAGER_PROFESSION.byNameCodec().fieldOf("profession").orElseGet(()).forGetter(()), (App)Codec.INT.fieldOf("level").orElse(Integer.valueOf(1)).forGetter(())).apply((Applicative)$$0, VillagerData::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private final VillagerProfession profession;
/*    */   
/*    */   private final int level;
/*    */ 
/*    */   
/*    */   public VillagerData(VillagerType $$0, VillagerProfession $$1, int $$2) {
/* 23 */     this.type = $$0;
/* 24 */     this.profession = $$1;
/* 25 */     this.level = Math.max(1, $$2);
/*    */   }
/*    */   
/*    */   public VillagerType getType() {
/* 29 */     return this.type;
/*    */   }
/*    */   
/*    */   public VillagerProfession getProfession() {
/* 33 */     return this.profession;
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 37 */     return this.level;
/*    */   }
/*    */   
/*    */   public VillagerData setType(VillagerType $$0) {
/* 41 */     return new VillagerData($$0, this.profession, this.level);
/*    */   }
/*    */   
/*    */   public VillagerData setProfession(VillagerProfession $$0) {
/* 45 */     return new VillagerData(this.type, $$0, this.level);
/*    */   }
/*    */   
/*    */   public VillagerData setLevel(int $$0) {
/* 49 */     return new VillagerData(this.type, this.profession, $$0);
/*    */   }
/*    */   
/*    */   public static int getMinXpPerLevel(int $$0) {
/* 53 */     return canLevelUp($$0) ? NEXT_LEVEL_XP_THRESHOLDS[$$0 - 1] : 0;
/*    */   }
/*    */   
/*    */   public static int getMaxXpPerLevel(int $$0) {
/* 57 */     return canLevelUp($$0) ? NEXT_LEVEL_XP_THRESHOLDS[$$0] : 0;
/*    */   }
/*    */   
/*    */   public static boolean canLevelUp(int $$0) {
/* 61 */     return ($$0 >= 1 && $$0 < 5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\VillagerData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */