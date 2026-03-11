/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ChestBlock;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WoodlandMansionPiece
/*     */   extends TemplateStructurePiece
/*     */ {
/*     */   public WoodlandMansionPiece(StructureTemplateManager $$0, String $$1, BlockPos $$2, Rotation $$3) {
/*  40 */     this($$0, $$1, $$2, $$3, Mirror.NONE);
/*     */   }
/*     */   
/*     */   public WoodlandMansionPiece(StructureTemplateManager $$0, String $$1, BlockPos $$2, Rotation $$3, Mirror $$4) {
/*  44 */     super(StructurePieceType.WOODLAND_MANSION_PIECE, 0, $$0, makeLocation($$1), $$1, makeSettings($$4, $$3), $$2);
/*     */   }
/*     */   
/*     */   public WoodlandMansionPiece(StructureTemplateManager $$0, CompoundTag $$1) {
/*  48 */     super(StructurePieceType.WOODLAND_MANSION_PIECE, $$1, $$0, $$1 -> makeSettings(Mirror.valueOf($$0.getString("Mi")), Rotation.valueOf($$0.getString("Rot"))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation makeTemplateLocation() {
/*  53 */     return makeLocation(this.templateName);
/*     */   }
/*     */   
/*     */   private static ResourceLocation makeLocation(String $$0) {
/*  57 */     return new ResourceLocation("woodland_mansion/" + $$0);
/*     */   }
/*     */   
/*     */   private static StructurePlaceSettings makeSettings(Mirror $$0, Rotation $$1) {
/*  61 */     return (new StructurePlaceSettings()).setIgnoreEntities(true).setRotation($$1).setMirror($$0).addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_BLOCK);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  66 */     super.addAdditionalSaveData($$0, $$1);
/*     */     
/*  68 */     $$1.putString("Rot", this.placeSettings.getRotation().name());
/*  69 */     $$1.putString("Mi", this.placeSettings.getMirror().name());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {
/*  74 */     if ($$0.startsWith("Chest")) {
/*  75 */       Rotation $$5 = this.placeSettings.getRotation();
/*  76 */       BlockState $$6 = Blocks.CHEST.defaultBlockState();
/*  77 */       if ("ChestWest".equals($$0)) {
/*  78 */         $$6 = (BlockState)$$6.setValue((Property)ChestBlock.FACING, (Comparable)$$5.rotate(Direction.WEST));
/*  79 */       } else if ("ChestEast".equals($$0)) {
/*  80 */         $$6 = (BlockState)$$6.setValue((Property)ChestBlock.FACING, (Comparable)$$5.rotate(Direction.EAST));
/*  81 */       } else if ("ChestSouth".equals($$0)) {
/*  82 */         $$6 = (BlockState)$$6.setValue((Property)ChestBlock.FACING, (Comparable)$$5.rotate(Direction.SOUTH));
/*  83 */       } else if ("ChestNorth".equals($$0)) {
/*  84 */         $$6 = (BlockState)$$6.setValue((Property)ChestBlock.FACING, (Comparable)$$5.rotate(Direction.NORTH));
/*     */       } 
/*  86 */       createChest($$2, $$4, $$3, $$1, BuiltInLootTables.WOODLAND_MANSION, $$6);
/*     */     } else {
/*  88 */       int $$8, $$9; List<Mob> $$7 = new ArrayList<>();
/*  89 */       switch ($$0) {
/*     */         case "Mage":
/*  91 */           $$7.add((Mob)EntityType.EVOKER.create((Level)$$2.getLevel()));
/*     */           break;
/*     */         case "Warrior":
/*  94 */           $$7.add((Mob)EntityType.VINDICATOR.create((Level)$$2.getLevel()));
/*     */           break;
/*     */         case "Group of Allays":
/*  97 */           $$8 = $$2.getRandom().nextInt(3) + 1;
/*  98 */           for ($$9 = 0; $$9 < $$8; $$9++) {
/*  99 */             $$7.add((Mob)EntityType.ALLAY.create((Level)$$2.getLevel()));
/*     */           }
/*     */           break;
/*     */         
/*     */         default:
/*     */           return;
/*     */       } 
/* 106 */       for (Mob $$10 : $$7) {
/* 107 */         if ($$10 == null) {
/*     */           continue;
/*     */         }
/* 110 */         $$10.setPersistenceRequired();
/* 111 */         $$10.moveTo($$1, 0.0F, 0.0F);
/* 112 */         $$10.finalizeSpawn($$2, $$2.getCurrentDifficultyAt($$10.blockPosition()), MobSpawnType.STRUCTURE, null, null);
/* 113 */         $$2.addFreshEntityWithPassengers((Entity)$$10);
/* 114 */         $$2.setBlock($$1, Blocks.AIR.defaultBlockState(), 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\WoodlandMansionPieces$WoodlandMansionPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */