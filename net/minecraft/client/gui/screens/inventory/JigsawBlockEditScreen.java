/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSliderButton;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.block.JigsawBlock;
/*     */ import net.minecraft.world.level.block.entity.JigsawBlockEntity;
/*     */ 
/*     */ public class JigsawBlockEditScreen extends Screen {
/*  24 */   private static final Component JOINT_LABEL = (Component)Component.translatable("jigsaw_block.joint_label");
/*  25 */   private static final Component POOL_LABEL = (Component)Component.translatable("jigsaw_block.pool");
/*  26 */   private static final Component NAME_LABEL = (Component)Component.translatable("jigsaw_block.name");
/*  27 */   private static final Component TARGET_LABEL = (Component)Component.translatable("jigsaw_block.target");
/*  28 */   private static final Component FINAL_STATE_LABEL = (Component)Component.translatable("jigsaw_block.final_state");
/*  29 */   private static final Component PLACEMENT_PRIORITY_LABEL = (Component)Component.translatable("jigsaw_block.placement_priority");
/*  30 */   private static final Component PLACEMENT_PRIORITY_TOOLTIP = (Component)Component.translatable("jigsaw_block.placement_priority.tooltip");
/*  31 */   private static final Component SELECTION_PRIORITY_LABEL = (Component)Component.translatable("jigsaw_block.selection_priority");
/*  32 */   private static final Component SELECTION_PRIORITY_TOOLTIP = (Component)Component.translatable("jigsaw_block.selection_priority.tooltip");
/*     */   
/*     */   private final JigsawBlockEntity jigsawEntity;
/*     */   
/*     */   private EditBox nameEdit;
/*     */   
/*     */   private EditBox targetEdit;
/*     */   
/*     */   private EditBox poolEdit;
/*     */   
/*     */   private EditBox finalStateEdit;
/*     */   private EditBox selectionPriorityEdit;
/*     */   private EditBox placementPriorityEdit;
/*     */   int levels;
/*     */   private boolean keepJigsaws = true;
/*     */   private CycleButton<JigsawBlockEntity.JointType> jointButton;
/*     */   private Button doneButton;
/*     */   private Button generateButton;
/*     */   private JigsawBlockEntity.JointType joint;
/*     */   
/*     */   public JigsawBlockEditScreen(JigsawBlockEntity $$0) {
/*  53 */     super(GameNarrator.NO_TITLE);
/*  54 */     this.jigsawEntity = $$0;
/*     */   }
/*     */   
/*     */   private void onDone() {
/*  58 */     sendToServer();
/*  59 */     this.minecraft.setScreen(null);
/*     */   }
/*     */   
/*     */   private void onCancel() {
/*  63 */     this.minecraft.setScreen(null);
/*     */   }
/*     */   
/*     */   private void sendToServer() {
/*  67 */     this.minecraft.getConnection().send((Packet)new ServerboundSetJigsawBlockPacket(this.jigsawEntity
/*  68 */           .getBlockPos(), new ResourceLocation(this.nameEdit
/*  69 */             .getValue()), new ResourceLocation(this.targetEdit
/*  70 */             .getValue()), new ResourceLocation(this.poolEdit
/*  71 */             .getValue()), this.finalStateEdit
/*  72 */           .getValue(), this.joint, 
/*     */           
/*  74 */           parseAsInt(this.selectionPriorityEdit.getValue()), 
/*  75 */           parseAsInt(this.placementPriorityEdit.getValue())));
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseAsInt(String $$0) {
/*     */     try {
/*  81 */       return Integer.parseInt($$0);
/*  82 */     } catch (NumberFormatException $$1) {
/*  83 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendGenerate() {
/*  88 */     this.minecraft.getConnection().send((Packet)new ServerboundJigsawGeneratePacket(this.jigsawEntity
/*  89 */           .getBlockPos(), this.levels, this.keepJigsaws));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  97 */     onCancel();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 102 */     this.poolEdit = new EditBox(this.font, this.width / 2 - 153, 20, 300, 20, POOL_LABEL);
/* 103 */     this.poolEdit.setMaxLength(128);
/* 104 */     this.poolEdit.setValue(this.jigsawEntity.getPool().location().toString());
/* 105 */     this.poolEdit.setResponder($$0 -> updateValidity());
/* 106 */     addWidget((GuiEventListener)this.poolEdit);
/*     */     
/* 108 */     this.nameEdit = new EditBox(this.font, this.width / 2 - 153, 55, 300, 20, NAME_LABEL);
/* 109 */     this.nameEdit.setMaxLength(128);
/* 110 */     this.nameEdit.setValue(this.jigsawEntity.getName().toString());
/* 111 */     this.nameEdit.setResponder($$0 -> updateValidity());
/* 112 */     addWidget((GuiEventListener)this.nameEdit);
/*     */     
/* 114 */     this.targetEdit = new EditBox(this.font, this.width / 2 - 153, 90, 300, 20, TARGET_LABEL);
/* 115 */     this.targetEdit.setMaxLength(128);
/* 116 */     this.targetEdit.setValue(this.jigsawEntity.getTarget().toString());
/* 117 */     this.targetEdit.setResponder($$0 -> updateValidity());
/* 118 */     addWidget((GuiEventListener)this.targetEdit);
/*     */     
/* 120 */     this.finalStateEdit = new EditBox(this.font, this.width / 2 - 153, 125, 300, 20, FINAL_STATE_LABEL);
/* 121 */     this.finalStateEdit.setMaxLength(256);
/* 122 */     this.finalStateEdit.setValue(this.jigsawEntity.getFinalState());
/* 123 */     addWidget((GuiEventListener)this.finalStateEdit);
/*     */     
/* 125 */     this.selectionPriorityEdit = new EditBox(this.font, this.width / 2 - 153, 160, 98, 20, SELECTION_PRIORITY_LABEL);
/* 126 */     this.selectionPriorityEdit.setMaxLength(3);
/* 127 */     this.selectionPriorityEdit.setValue(Integer.toString(this.jigsawEntity.getSelectionPriority()));
/* 128 */     this.selectionPriorityEdit.setTooltip(Tooltip.create(SELECTION_PRIORITY_TOOLTIP));
/* 129 */     addWidget((GuiEventListener)this.selectionPriorityEdit);
/*     */     
/* 131 */     this.placementPriorityEdit = new EditBox(this.font, this.width / 2 - 50, 160, 98, 20, PLACEMENT_PRIORITY_LABEL);
/* 132 */     this.placementPriorityEdit.setMaxLength(3);
/* 133 */     this.placementPriorityEdit.setValue(Integer.toString(this.jigsawEntity.getPlacementPriority()));
/* 134 */     this.placementPriorityEdit.setTooltip(Tooltip.create(PLACEMENT_PRIORITY_TOOLTIP));
/* 135 */     addWidget((GuiEventListener)this.placementPriorityEdit);
/*     */     
/* 137 */     this.joint = this.jigsawEntity.getJoint();
/* 138 */     this.jointButton = (CycleButton<JigsawBlockEntity.JointType>)addRenderableWidget((GuiEventListener)CycleButton.builder(JigsawBlockEntity.JointType::getTranslatedName).withValues((Object[])JigsawBlockEntity.JointType.values())
/* 139 */         .withInitialValue(this.joint)
/* 140 */         .displayOnlyValue()
/* 141 */         .create(this.width / 2 + 54, 160, 100, 20, JOINT_LABEL, ($$0, $$1) -> this.joint = $$1));
/*     */     
/* 143 */     boolean $$0 = JigsawBlock.getFrontFacing(this.jigsawEntity.getBlockState()).getAxis().isVertical();
/* 144 */     this.jointButton.active = $$0;
/* 145 */     this.jointButton.visible = $$0;
/*     */     
/* 147 */     addRenderableWidget((GuiEventListener)new AbstractSliderButton(this.width / 2 - 154, 185, 100, 20, CommonComponents.EMPTY, 0.0D)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           protected void updateMessage()
/*     */           {
/* 154 */             setMessage((Component)Component.translatable("jigsaw_block.levels", new Object[] { Integer.valueOf(this.this$0.levels) }));
/*     */           }
/*     */ 
/*     */           
/*     */           protected void applyValue() {
/* 159 */             JigsawBlockEditScreen.this.levels = Mth.floor(Mth.clampedLerp(0.0D, 20.0D, this.value));
/*     */           }
/*     */         });
/*     */     
/* 163 */     addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(this.keepJigsaws).create(this.width / 2 - 50, 185, 100, 20, (Component)Component.translatable("jigsaw_block.keep_jigsaws"), ($$0, $$1) -> this.keepJigsaws = $$1.booleanValue()));
/*     */     
/* 165 */     this.generateButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("jigsaw_block.generate"), $$0 -> {
/*     */             onDone();
/*     */             
/*     */             sendGenerate();
/* 169 */           }).bounds(this.width / 2 + 54, 185, 100, 20).build());
/*     */     
/* 171 */     this.doneButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> onDone()).bounds(this.width / 2 - 4 - 150, 210, 150, 20).build());
/* 172 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onCancel()).bounds(this.width / 2 + 4, 210, 150, 20).build());
/*     */     
/* 174 */     setInitialFocus((GuiEventListener)this.poolEdit);
/* 175 */     updateValidity();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateValidity() {
/* 181 */     boolean $$0 = (ResourceLocation.isValidResourceLocation(this.nameEdit.getValue()) && ResourceLocation.isValidResourceLocation(this.targetEdit.getValue()) && ResourceLocation.isValidResourceLocation(this.poolEdit.getValue()));
/* 182 */     this.doneButton.active = $$0;
/* 183 */     this.generateButton.active = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Minecraft $$0, int $$1, int $$2) {
/* 188 */     String $$3 = this.nameEdit.getValue();
/* 189 */     String $$4 = this.targetEdit.getValue();
/* 190 */     String $$5 = this.poolEdit.getValue();
/* 191 */     String $$6 = this.finalStateEdit.getValue();
/* 192 */     String $$7 = this.selectionPriorityEdit.getValue();
/* 193 */     String $$8 = this.placementPriorityEdit.getValue();
/* 194 */     int $$9 = this.levels;
/* 195 */     JigsawBlockEntity.JointType $$10 = this.joint;
/*     */     
/* 197 */     init($$0, $$1, $$2);
/*     */     
/* 199 */     this.nameEdit.setValue($$3);
/* 200 */     this.targetEdit.setValue($$4);
/* 201 */     this.poolEdit.setValue($$5);
/* 202 */     this.finalStateEdit.setValue($$6);
/* 203 */     this.levels = $$9;
/* 204 */     this.joint = $$10;
/* 205 */     this.jointButton.setValue($$10);
/* 206 */     this.selectionPriorityEdit.setValue($$7);
/* 207 */     this.placementPriorityEdit.setValue($$8);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 212 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 213 */       return true;
/*     */     }
/*     */     
/* 216 */     if (this.doneButton.active && ($$0 == 257 || $$0 == 335)) {
/* 217 */       onDone();
/* 218 */       return true;
/*     */     } 
/*     */     
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 226 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 228 */     $$0.drawString(this.font, POOL_LABEL, this.width / 2 - 153, 10, 10526880);
/* 229 */     this.poolEdit.render($$0, $$1, $$2, $$3);
/*     */     
/* 231 */     $$0.drawString(this.font, NAME_LABEL, this.width / 2 - 153, 45, 10526880);
/* 232 */     this.nameEdit.render($$0, $$1, $$2, $$3);
/*     */     
/* 234 */     $$0.drawString(this.font, TARGET_LABEL, this.width / 2 - 153, 80, 10526880);
/* 235 */     this.targetEdit.render($$0, $$1, $$2, $$3);
/*     */     
/* 237 */     $$0.drawString(this.font, FINAL_STATE_LABEL, this.width / 2 - 153, 115, 10526880);
/* 238 */     this.finalStateEdit.render($$0, $$1, $$2, $$3);
/*     */     
/* 240 */     $$0.drawString(this.font, SELECTION_PRIORITY_LABEL, this.width / 2 - 153, 150, 10526880);
/* 241 */     this.placementPriorityEdit.render($$0, $$1, $$2, $$3);
/*     */     
/* 243 */     $$0.drawString(this.font, PLACEMENT_PRIORITY_LABEL, this.width / 2 - 50, 150, 10526880);
/* 244 */     this.selectionPriorityEdit.render($$0, $$1, $$2, $$3);
/*     */     
/* 246 */     if (JigsawBlock.getFrontFacing(this.jigsawEntity.getBlockState()).getAxis().isVertical())
/* 247 */       $$0.drawString(this.font, JOINT_LABEL, this.width / 2 + 53, 150, 10526880); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\JigsawBlockEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */