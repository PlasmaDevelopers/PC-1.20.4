/*     */ package net.minecraft.data.models;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.data.BlockFamily;
/*     */ import net.minecraft.data.models.model.ModelLocationUtils;
/*     */ import net.minecraft.data.models.model.ModelTemplate;
/*     */ import net.minecraft.data.models.model.ModelTemplates;
/*     */ import net.minecraft.data.models.model.TextureMapping;
/*     */ import net.minecraft.data.models.model.TexturedModel;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BlockFamilyProvider
/*     */ {
/*     */   private final TextureMapping mapping;
/* 584 */   private final Map<ModelTemplate, ResourceLocation> models = Maps.newHashMap();
/*     */   
/*     */   @Nullable
/*     */   private BlockFamily family;
/*     */   @Nullable
/*     */   private ResourceLocation fullBlock;
/* 590 */   private final Set<Block> skipGeneratingModelsFor = new HashSet<>();
/*     */   
/*     */   public BlockFamilyProvider(TextureMapping $$0) {
/* 593 */     this.mapping = $$0;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider fullBlock(Block $$0, ModelTemplate $$1) {
/* 597 */     this.fullBlock = $$1.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 598 */     if (BlockModelGenerators.this.fullBlockModelCustomGenerators.containsKey($$0)) {
/* 599 */       BlockModelGenerators.this.blockStateOutput.accept(((BlockModelGenerators.BlockStateGeneratorSupplier)BlockModelGenerators.this.fullBlockModelCustomGenerators.get($$0)).create($$0, this.fullBlock, this.mapping, BlockModelGenerators.this.modelOutput));
/*     */     } else {
/* 601 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$0, this.fullBlock));
/*     */     } 
/* 603 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider donateModelTo(Block $$0, Block $$1) {
/* 607 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation($$0);
/* 608 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$1, $$2));
/* 609 */     BlockModelGenerators.this.delegateItemModel($$1, $$2);
/* 610 */     this.skipGeneratingModelsFor.add($$1);
/* 611 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider button(Block $$0) {
/* 615 */     ResourceLocation $$1 = ModelTemplates.BUTTON.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 616 */     ResourceLocation $$2 = ModelTemplates.BUTTON_PRESSED.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 617 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createButton($$0, $$1, $$2));
/*     */     
/* 619 */     ResourceLocation $$3 = ModelTemplates.BUTTON_INVENTORY.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 620 */     BlockModelGenerators.this.delegateItemModel($$0, $$3);
/* 621 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider wall(Block $$0) {
/* 625 */     ResourceLocation $$1 = ModelTemplates.WALL_POST.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 626 */     ResourceLocation $$2 = ModelTemplates.WALL_LOW_SIDE.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 627 */     ResourceLocation $$3 = ModelTemplates.WALL_TALL_SIDE.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 628 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createWall($$0, $$1, $$2, $$3));
/*     */     
/* 630 */     ResourceLocation $$4 = ModelTemplates.WALL_INVENTORY.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 631 */     BlockModelGenerators.this.delegateItemModel($$0, $$4);
/* 632 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider customFence(Block $$0) {
/* 636 */     TextureMapping $$1 = TextureMapping.customParticle($$0);
/*     */     
/* 638 */     ResourceLocation $$2 = ModelTemplates.CUSTOM_FENCE_POST.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 639 */     ResourceLocation $$3 = ModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 640 */     ResourceLocation $$4 = ModelTemplates.CUSTOM_FENCE_SIDE_EAST.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 641 */     ResourceLocation $$5 = ModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 642 */     ResourceLocation $$6 = ModelTemplates.CUSTOM_FENCE_SIDE_WEST.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 643 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createCustomFence($$0, $$2, $$3, $$4, $$5, $$6));
/*     */     
/* 645 */     ResourceLocation $$7 = ModelTemplates.CUSTOM_FENCE_INVENTORY.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 646 */     BlockModelGenerators.this.delegateItemModel($$0, $$7);
/* 647 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider fence(Block $$0) {
/* 651 */     ResourceLocation $$1 = ModelTemplates.FENCE_POST.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 652 */     ResourceLocation $$2 = ModelTemplates.FENCE_SIDE.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 653 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFence($$0, $$1, $$2));
/*     */     
/* 655 */     ResourceLocation $$3 = ModelTemplates.FENCE_INVENTORY.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 656 */     BlockModelGenerators.this.delegateItemModel($$0, $$3);
/* 657 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider customFenceGate(Block $$0) {
/* 661 */     TextureMapping $$1 = TextureMapping.customParticle($$0);
/*     */     
/* 663 */     ResourceLocation $$2 = ModelTemplates.CUSTOM_FENCE_GATE_OPEN.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 664 */     ResourceLocation $$3 = ModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 665 */     ResourceLocation $$4 = ModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 666 */     ResourceLocation $$5 = ModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/* 667 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFenceGate($$0, $$2, $$3, $$4, $$5, false));
/* 668 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider fenceGate(Block $$0) {
/* 672 */     ResourceLocation $$1 = ModelTemplates.FENCE_GATE_OPEN.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 673 */     ResourceLocation $$2 = ModelTemplates.FENCE_GATE_CLOSED.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 674 */     ResourceLocation $$3 = ModelTemplates.FENCE_GATE_WALL_OPEN.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 675 */     ResourceLocation $$4 = ModelTemplates.FENCE_GATE_WALL_CLOSED.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 676 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFenceGate($$0, $$1, $$2, $$3, $$4, true));
/* 677 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider pressurePlate(Block $$0) {
/* 681 */     ResourceLocation $$1 = ModelTemplates.PRESSURE_PLATE_UP.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 682 */     ResourceLocation $$2 = ModelTemplates.PRESSURE_PLATE_DOWN.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 683 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate($$0, $$1, $$2));
/* 684 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider sign(Block $$0) {
/* 688 */     if (this.family == null) {
/* 689 */       throw new IllegalStateException("Family not defined");
/*     */     }
/* 691 */     Block $$1 = (Block)this.family.getVariants().get(BlockFamily.Variant.WALL_SIGN);
/* 692 */     ResourceLocation $$2 = ModelTemplates.PARTICLE_ONLY.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/* 693 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$0, $$2));
/* 694 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$1, $$2));
/* 695 */     BlockModelGenerators.this.createSimpleFlatItemModel($$0.asItem());
/* 696 */     BlockModelGenerators.this.skipAutoItemBlock($$1);
/* 697 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider slab(Block $$0) {
/* 701 */     if (this.fullBlock == null) {
/* 702 */       throw new IllegalStateException("Full block not generated yet");
/*     */     }
/* 704 */     ResourceLocation $$1 = getOrCreateModel(ModelTemplates.SLAB_BOTTOM, $$0);
/* 705 */     ResourceLocation $$2 = getOrCreateModel(ModelTemplates.SLAB_TOP, $$0);
/*     */     
/* 707 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSlab($$0, $$1, $$2, this.fullBlock));
/* 708 */     BlockModelGenerators.this.delegateItemModel($$0, $$1);
/* 709 */     return this;
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider stairs(Block $$0) {
/* 713 */     ResourceLocation $$1 = getOrCreateModel(ModelTemplates.STAIRS_INNER, $$0);
/* 714 */     ResourceLocation $$2 = getOrCreateModel(ModelTemplates.STAIRS_STRAIGHT, $$0);
/* 715 */     ResourceLocation $$3 = getOrCreateModel(ModelTemplates.STAIRS_OUTER, $$0);
/*     */     
/* 717 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createStairs($$0, $$1, $$2, $$3));
/* 718 */     BlockModelGenerators.this.delegateItemModel($$0, $$2);
/* 719 */     return this;
/*     */   }
/*     */   
/*     */   private BlockFamilyProvider fullBlockVariant(Block $$0) {
/* 723 */     TexturedModel $$1 = BlockModelGenerators.this.texturedModels.getOrDefault($$0, TexturedModel.CUBE.get($$0));
/* 724 */     ResourceLocation $$2 = $$1.create($$0, BlockModelGenerators.this.modelOutput);
/* 725 */     BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$0, $$2));
/* 726 */     return this;
/*     */   }
/*     */   
/*     */   private BlockFamilyProvider door(Block $$0) {
/* 730 */     BlockModelGenerators.this.createDoor($$0);
/* 731 */     return this;
/*     */   }
/*     */   
/*     */   private void trapdoor(Block $$0) {
/* 735 */     if (BlockModelGenerators.this.nonOrientableTrapdoor.contains($$0)) {
/* 736 */       BlockModelGenerators.this.createTrapdoor($$0);
/*     */     } else {
/* 738 */       BlockModelGenerators.this.createOrientableTrapdoor($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ResourceLocation getOrCreateModel(ModelTemplate $$0, Block $$1) {
/* 743 */     return this.models.computeIfAbsent($$0, $$1 -> $$1.create($$0, this.mapping, BlockModelGenerators.this.modelOutput));
/*     */   }
/*     */   
/*     */   public BlockFamilyProvider generateFor(BlockFamily $$0) {
/* 747 */     this.family = $$0;
/* 748 */     $$0.getVariants().forEach(($$0, $$1) -> {
/*     */           if (this.skipGeneratingModelsFor.contains($$1)) {
/*     */             return;
/*     */           }
/*     */           BiConsumer<BlockFamilyProvider, Block> $$2 = BlockModelGenerators.SHAPE_CONSUMERS.get($$0);
/*     */           if ($$2 != null) {
/*     */             $$2.accept(this, $$1);
/*     */           }
/*     */         });
/* 757 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\BlockModelGenerators$BlockFamilyProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */