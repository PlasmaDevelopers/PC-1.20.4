/*     */ package net.minecraft.world.level.block.entity;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.block.JigsawBlock;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*     */ 
/*     */ public class JigsawBlockEntity extends BlockEntity {
/*     */   public static final String TARGET = "target";
/*     */   public static final String POOL = "pool";
/*     */   public static final String JOINT = "joint";
/*     */   public static final String PLACEMENT_PRIORITY = "placement_priority";
/*     */   public static final String SELECTION_PRIORITY = "selection_priority";
/*     */   public static final String NAME = "name";
/*     */   public static final String FINAL_STATE = "final_state";
/*     */   
/*     */   public enum JointType implements StringRepresentable {
/*  24 */     ROLLABLE("rollable"),
/*  25 */     ALIGNED("aligned");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     JointType(String $$0) {
/*  30 */       this.name = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/*  35 */       return this.name;
/*     */     }
/*     */     
/*     */     public static Optional<JointType> byName(String $$0) {
/*  39 */       return Arrays.<JointType>stream(values()).filter($$1 -> $$1.getSerializedName().equals($$0)).findFirst();
/*     */     }
/*     */     
/*     */     public Component getTranslatedName() {
/*  43 */       return (Component)Component.translatable("jigsaw_block.joint." + this.name);
/*     */     }
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
/*  61 */   private ResourceLocation name = new ResourceLocation("empty");
/*  62 */   private ResourceLocation target = new ResourceLocation("empty");
/*  63 */   private ResourceKey<StructureTemplatePool> pool = ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation("empty"));
/*  64 */   private JointType joint = JointType.ROLLABLE;
/*  65 */   private String finalState = "minecraft:air";
/*     */   private int placementPriority;
/*     */   private int selectionPriority;
/*     */   
/*     */   public JigsawBlockEntity(BlockPos $$0, BlockState $$1) {
/*  70 */     super(BlockEntityType.JIGSAW, $$0, $$1);
/*     */   }
/*     */   
/*     */   public ResourceLocation getName() {
/*  74 */     return this.name;
/*     */   }
/*     */   
/*     */   public ResourceLocation getTarget() {
/*  78 */     return this.target;
/*     */   }
/*     */   
/*     */   public ResourceKey<StructureTemplatePool> getPool() {
/*  82 */     return this.pool;
/*     */   }
/*     */   
/*     */   public String getFinalState() {
/*  86 */     return this.finalState;
/*     */   }
/*     */   
/*     */   public JointType getJoint() {
/*  90 */     return this.joint;
/*     */   }
/*     */   
/*     */   public int getPlacementPriority() {
/*  94 */     return this.placementPriority;
/*     */   }
/*     */   
/*     */   public int getSelectionPriority() {
/*  98 */     return this.selectionPriority;
/*     */   }
/*     */   
/*     */   public void setName(ResourceLocation $$0) {
/* 102 */     this.name = $$0;
/*     */   }
/*     */   
/*     */   public void setTarget(ResourceLocation $$0) {
/* 106 */     this.target = $$0;
/*     */   }
/*     */   
/*     */   public void setPool(ResourceKey<StructureTemplatePool> $$0) {
/* 110 */     this.pool = $$0;
/*     */   }
/*     */   
/*     */   public void setFinalState(String $$0) {
/* 114 */     this.finalState = $$0;
/*     */   }
/*     */   
/*     */   public void setJoint(JointType $$0) {
/* 118 */     this.joint = $$0;
/*     */   }
/*     */   
/*     */   public void setPlacementPriority(int $$0) {
/* 122 */     this.placementPriority = $$0;
/*     */   }
/*     */   
/*     */   public void setSelectionPriority(int $$0) {
/* 126 */     this.selectionPriority = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 131 */     super.saveAdditional($$0);
/* 132 */     $$0.putString("name", this.name.toString());
/* 133 */     $$0.putString("target", this.target.toString());
/* 134 */     $$0.putString("pool", this.pool.location().toString());
/* 135 */     $$0.putString("final_state", this.finalState);
/* 136 */     $$0.putString("joint", this.joint.getSerializedName());
/* 137 */     $$0.putInt("placement_priority", this.placementPriority);
/* 138 */     $$0.putInt("selection_priority", this.selectionPriority);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 143 */     super.load($$0);
/* 144 */     this.name = new ResourceLocation($$0.getString("name"));
/* 145 */     this.target = new ResourceLocation($$0.getString("target"));
/* 146 */     this.pool = ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation($$0.getString("pool")));
/* 147 */     this.finalState = $$0.getString("final_state");
/* 148 */     this
/* 149 */       .joint = JointType.byName($$0.getString("joint")).orElseGet(() -> JigsawBlock.getFrontFacing(getBlockState()).getAxis().isHorizontal() ? JointType.ALIGNED : JointType.ROLLABLE);
/* 150 */     this.placementPriority = $$0.getInt("placement_priority");
/* 151 */     this.selectionPriority = $$0.getInt("selection_priority");
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 156 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 161 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   public void generate(ServerLevel $$0, int $$1, boolean $$2) {
/* 165 */     BlockPos $$3 = getBlockPos().relative(((FrontAndTop)getBlockState().getValue((Property)JigsawBlock.ORIENTATION)).front());
/*     */     
/* 167 */     Registry<StructureTemplatePool> $$4 = $$0.registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
/* 168 */     Holder.Reference reference = $$4.getHolderOrThrow(this.pool);
/*     */     
/* 170 */     JigsawPlacement.generateJigsaw($$0, (Holder)reference, this.target, $$1, $$3, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\JigsawBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */