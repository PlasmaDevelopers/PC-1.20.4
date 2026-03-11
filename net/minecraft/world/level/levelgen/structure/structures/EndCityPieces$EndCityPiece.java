/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.RandomizableContainer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.decoration.ItemFrame;
/*    */ import net.minecraft.world.entity.monster.Shulker;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*    */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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
/*    */ public class EndCityPiece
/*    */   extends TemplateStructurePiece
/*    */ {
/*    */   public EndCityPiece(StructureTemplateManager $$0, String $$1, BlockPos $$2, Rotation $$3, boolean $$4) {
/* 44 */     super(StructurePieceType.END_CITY_PIECE, 0, $$0, makeResourceLocation($$1), $$1, makeSettings($$4, $$3), $$2);
/*    */   }
/*    */   
/*    */   public EndCityPiece(StructureTemplateManager $$0, CompoundTag $$1) {
/* 48 */     super(StructurePieceType.END_CITY_PIECE, $$1, $$0, $$1 -> makeSettings($$0.getBoolean("OW"), Rotation.valueOf($$0.getString("Rot"))));
/*    */   }
/*    */   
/*    */   private static StructurePlaceSettings makeSettings(boolean $$0, Rotation $$1) {
/* 52 */     BlockIgnoreProcessor $$2 = $$0 ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
/* 53 */     return (new StructurePlaceSettings()).setIgnoreEntities(true).addProcessor((StructureProcessor)$$2).setRotation($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation makeTemplateLocation() {
/* 58 */     return makeResourceLocation(this.templateName);
/*    */   }
/*    */   
/*    */   private static ResourceLocation makeResourceLocation(String $$0) {
/* 62 */     return new ResourceLocation("end_city/" + $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 67 */     super.addAdditionalSaveData($$0, $$1);
/*    */     
/* 69 */     $$1.putString("Rot", this.placeSettings.getRotation().name());
/* 70 */     $$1.putBoolean("OW", (this.placeSettings.getProcessors().get(0) == BlockIgnoreProcessor.STRUCTURE_BLOCK));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {
/* 75 */     if ($$0.startsWith("Chest")) {
/* 76 */       BlockPos $$5 = $$1.below();
/*    */       
/* 78 */       if ($$4.isInside((Vec3i)$$5)) {
/* 79 */         RandomizableContainer.setBlockEntityLootTable((BlockGetter)$$2, $$3, $$5, BuiltInLootTables.END_CITY_TREASURE);
/*    */       }
/* 81 */     } else if ($$4.isInside((Vec3i)$$1) && Level.isInSpawnableBounds($$1)) {
/* 82 */       if ($$0.startsWith("Sentry")) {
/* 83 */         Shulker $$6 = (Shulker)EntityType.SHULKER.create((Level)$$2.getLevel());
/* 84 */         if ($$6 != null) {
/* 85 */           $$6.setPos($$1.getX() + 0.5D, $$1.getY(), $$1.getZ() + 0.5D);
/* 86 */           $$2.addFreshEntity((Entity)$$6);
/*    */         } 
/* 88 */       } else if ($$0.startsWith("Elytra")) {
/* 89 */         ItemFrame $$7 = new ItemFrame((Level)$$2.getLevel(), $$1, this.placeSettings.getRotation().rotate(Direction.SOUTH));
/* 90 */         $$7.setItem(new ItemStack((ItemLike)Items.ELYTRA), false);
/* 91 */         $$2.addFreshEntity((Entity)$$7);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\EndCityPieces$EndCityPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */