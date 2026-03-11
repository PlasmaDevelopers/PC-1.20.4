/*    */ package net.minecraft.data.advancements.packs;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.data.advancements.AdvancementSubProvider;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ 
/*    */ public class UpdateOneTwentyOneAdventureAdvancements
/*    */   implements AdvancementSubProvider
/*    */ {
/*    */   public void generate(HolderLookup.Provider $$0, Consumer<AdvancementHolder> $$1) {
/* 16 */     AdvancementHolder $$2 = AdvancementSubProvider.createPlaceholder("adventure/root");
/* 17 */     VanillaAdventureAdvancements.createMonsterHunterAdvancement($$2, $$1, (List<EntityType<?>>)Stream.concat(VanillaAdventureAdvancements.MOBS_TO_KILL.stream(), Stream.of(EntityType.BREEZE)).collect(Collectors.toList()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\packs\UpdateOneTwentyOneAdventureAdvancements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */