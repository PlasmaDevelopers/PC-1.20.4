/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*     */ 
/*     */ public class StructureBlockEditScreen extends Screen {
/*  27 */   private static final Component NAME_LABEL = (Component)Component.translatable("structure_block.structure_name");
/*  28 */   private static final Component POSITION_LABEL = (Component)Component.translatable("structure_block.position");
/*  29 */   private static final Component SIZE_LABEL = (Component)Component.translatable("structure_block.size");
/*  30 */   private static final Component INTEGRITY_LABEL = (Component)Component.translatable("structure_block.integrity");
/*  31 */   private static final Component CUSTOM_DATA_LABEL = (Component)Component.translatable("structure_block.custom_data");
/*  32 */   private static final Component INCLUDE_ENTITIES_LABEL = (Component)Component.translatable("structure_block.include_entities");
/*  33 */   private static final Component DETECT_SIZE_LABEL = (Component)Component.translatable("structure_block.detect_size");
/*  34 */   private static final Component SHOW_AIR_LABEL = (Component)Component.translatable("structure_block.show_air");
/*  35 */   private static final Component SHOW_BOUNDING_BOX_LABEL = (Component)Component.translatable("structure_block.show_boundingbox");
/*  36 */   private static final ImmutableList<StructureMode> ALL_MODES = ImmutableList.copyOf((Object[])StructureMode.values()); private static final ImmutableList<StructureMode> DEFAULT_MODES; static {
/*  37 */     DEFAULT_MODES = (ImmutableList<StructureMode>)ALL_MODES.stream().filter($$0 -> ($$0 != StructureMode.DATA)).collect(ImmutableList.toImmutableList());
/*     */   }
/*     */   private final StructureBlockEntity structure;
/*  40 */   private Mirror initialMirror = Mirror.NONE;
/*  41 */   private Rotation initialRotation = Rotation.NONE;
/*  42 */   private StructureMode initialMode = StructureMode.DATA;
/*     */   
/*     */   private boolean initialEntityIgnoring;
/*     */   
/*     */   private boolean initialShowAir;
/*     */   private boolean initialShowBoundingBox;
/*     */   private EditBox nameEdit;
/*     */   private EditBox posXEdit;
/*     */   private EditBox posYEdit;
/*     */   private EditBox posZEdit;
/*     */   private EditBox sizeXEdit;
/*     */   private EditBox sizeYEdit;
/*     */   private EditBox sizeZEdit;
/*     */   private EditBox integrityEdit;
/*     */   private EditBox seedEdit;
/*     */   private EditBox dataEdit;
/*     */   private Button saveButton;
/*     */   private Button loadButton;
/*     */   private Button rot0Button;
/*     */   private Button rot90Button;
/*     */   private Button rot180Button;
/*     */   private Button rot270Button;
/*     */   private Button detectButton;
/*     */   private CycleButton<Boolean> includeEntitiesButton;
/*     */   private CycleButton<Mirror> mirrorButton;
/*     */   private CycleButton<Boolean> toggleAirButton;
/*     */   private CycleButton<Boolean> toggleBoundingBox;
/*  69 */   private final DecimalFormat decimalFormat = new DecimalFormat("0.0###");
/*     */   
/*     */   public StructureBlockEditScreen(StructureBlockEntity $$0) {
/*  72 */     super((Component)Component.translatable(Blocks.STRUCTURE_BLOCK.getDescriptionId()));
/*  73 */     this.structure = $$0;
/*  74 */     this.decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
/*     */   }
/*     */   
/*     */   private void onDone() {
/*  78 */     if (sendToServer(StructureBlockEntity.UpdateType.UPDATE_DATA)) {
/*  79 */       this.minecraft.setScreen(null);
/*     */     }
/*     */   }
/*     */   
/*     */   private void onCancel() {
/*  84 */     this.structure.setMirror(this.initialMirror);
/*  85 */     this.structure.setRotation(this.initialRotation);
/*  86 */     this.structure.setMode(this.initialMode);
/*  87 */     this.structure.setIgnoreEntities(this.initialEntityIgnoring);
/*  88 */     this.structure.setShowAir(this.initialShowAir);
/*  89 */     this.structure.setShowBoundingBox(this.initialShowBoundingBox);
/*  90 */     this.minecraft.setScreen(null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  95 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> onDone()).bounds(this.width / 2 - 4 - 150, 210, 150, 20).build());
/*  96 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onCancel()).bounds(this.width / 2 + 4, 210, 150, 20).build());
/*     */     
/*  98 */     this.initialMirror = this.structure.getMirror();
/*  99 */     this.initialRotation = this.structure.getRotation();
/* 100 */     this.initialMode = this.structure.getMode();
/* 101 */     this.initialEntityIgnoring = this.structure.isIgnoreEntities();
/* 102 */     this.initialShowAir = this.structure.getShowAir();
/* 103 */     this.initialShowBoundingBox = this.structure.getShowBoundingBox();
/*     */     
/* 105 */     this.saveButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("structure_block.button.save"), $$0 -> {
/*     */             if (this.structure.getMode() == StructureMode.SAVE) {
/*     */               sendToServer(StructureBlockEntity.UpdateType.SAVE_AREA);
/*     */               this.minecraft.setScreen(null);
/*     */             } 
/* 110 */           }).bounds(this.width / 2 + 4 + 100, 185, 50, 20).build());
/* 111 */     this.loadButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("structure_block.button.load"), $$0 -> {
/*     */             if (this.structure.getMode() == StructureMode.LOAD) {
/*     */               sendToServer(StructureBlockEntity.UpdateType.LOAD_AREA);
/*     */               this.minecraft.setScreen(null);
/*     */             } 
/* 116 */           }).bounds(this.width / 2 + 4 + 100, 185, 50, 20).build());
/* 117 */     addRenderableWidget((GuiEventListener)CycleButton.builder($$0 -> Component.translatable("structure_block.mode." + $$0.getSerializedName()))
/* 118 */         .withValues((List)DEFAULT_MODES, (List)ALL_MODES)
/* 119 */         .displayOnlyValue()
/* 120 */         .withInitialValue(this.initialMode)
/* 121 */         .create(this.width / 2 - 4 - 150, 185, 50, 20, (Component)Component.literal("MODE"), ($$0, $$1) -> {
/*     */             this.structure.setMode($$1);
/*     */             
/*     */             updateMode($$1);
/*     */           }));
/*     */     
/* 127 */     this.detectButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("structure_block.button.detect_size"), $$0 -> {
/*     */             if (this.structure.getMode() == StructureMode.SAVE) {
/*     */               sendToServer(StructureBlockEntity.UpdateType.SCAN_AREA);
/*     */               this.minecraft.setScreen(null);
/*     */             } 
/* 132 */           }).bounds(this.width / 2 + 4 + 100, 120, 50, 20).build());
/* 133 */     this.includeEntitiesButton = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(!this.structure.isIgnoreEntities()).displayOnlyValue().create(this.width / 2 + 4 + 100, 160, 50, 20, INCLUDE_ENTITIES_LABEL, ($$0, $$1) -> this.structure.setIgnoreEntities(!$$1.booleanValue())));
/*     */ 
/*     */ 
/*     */     
/* 137 */     this.mirrorButton = (CycleButton<Mirror>)addRenderableWidget((GuiEventListener)CycleButton.builder(Mirror::symbol)
/* 138 */         .withValues((Object[])Mirror.values())
/* 139 */         .displayOnlyValue()
/* 140 */         .withInitialValue(this.initialMirror)
/* 141 */         .create(this.width / 2 - 20, 185, 40, 20, (Component)Component.literal("MIRROR"), ($$0, $$1) -> this.structure.setMirror($$1)));
/*     */ 
/*     */     
/* 144 */     this.toggleAirButton = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(this.structure.getShowAir()).displayOnlyValue().create(this.width / 2 + 4 + 100, 80, 50, 20, SHOW_AIR_LABEL, ($$0, $$1) -> this.structure.setShowAir($$1.booleanValue())));
/*     */ 
/*     */ 
/*     */     
/* 148 */     this.toggleBoundingBox = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.onOffBuilder(this.structure.getShowBoundingBox()).displayOnlyValue().create(this.width / 2 + 4 + 100, 80, 50, 20, SHOW_BOUNDING_BOX_LABEL, ($$0, $$1) -> this.structure.setShowBoundingBox($$1.booleanValue())));
/*     */ 
/*     */ 
/*     */     
/* 152 */     this.rot0Button = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.literal("0"), $$0 -> {
/*     */             this.structure.setRotation(Rotation.NONE);
/*     */             updateDirectionButtons();
/* 155 */           }).bounds(this.width / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20).build());
/* 156 */     this.rot90Button = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.literal("90"), $$0 -> {
/*     */             this.structure.setRotation(Rotation.CLOCKWISE_90);
/*     */             updateDirectionButtons();
/* 159 */           }).bounds(this.width / 2 - 1 - 40 - 20, 185, 40, 20).build());
/* 160 */     this.rot180Button = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.literal("180"), $$0 -> {
/*     */             this.structure.setRotation(Rotation.CLOCKWISE_180);
/*     */             updateDirectionButtons();
/* 163 */           }).bounds(this.width / 2 + 1 + 20, 185, 40, 20).build());
/* 164 */     this.rot270Button = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.literal("270"), $$0 -> {
/*     */             this.structure.setRotation(Rotation.COUNTERCLOCKWISE_90);
/*     */             updateDirectionButtons();
/* 167 */           }).bounds(this.width / 2 + 1 + 40 + 1 + 20, 185, 40, 20).build());
/*     */     
/* 169 */     this.nameEdit = new EditBox(this.font, this.width / 2 - 152, 40, 300, 20, (Component)Component.translatable("structure_block.structure_name"))
/*     */       {
/*     */         public boolean charTyped(char $$0, int $$1)
/*     */         {
/* 173 */           if (!StructureBlockEditScreen.this.isValidCharacterForName(getValue(), $$0, getCursorPosition())) {
/* 174 */             return false;
/*     */           }
/* 176 */           return super.charTyped($$0, $$1);
/*     */         }
/*     */       };
/* 179 */     this.nameEdit.setMaxLength(128);
/* 180 */     this.nameEdit.setValue(this.structure.getStructureName());
/* 181 */     addWidget((GuiEventListener)this.nameEdit);
/*     */     
/* 183 */     BlockPos $$0 = this.structure.getStructurePos();
/* 184 */     this.posXEdit = new EditBox(this.font, this.width / 2 - 152, 80, 80, 20, (Component)Component.translatable("structure_block.position.x"));
/* 185 */     this.posXEdit.setMaxLength(15);
/* 186 */     this.posXEdit.setValue(Integer.toString($$0.getX()));
/* 187 */     addWidget((GuiEventListener)this.posXEdit);
/* 188 */     this.posYEdit = new EditBox(this.font, this.width / 2 - 72, 80, 80, 20, (Component)Component.translatable("structure_block.position.y"));
/* 189 */     this.posYEdit.setMaxLength(15);
/* 190 */     this.posYEdit.setValue(Integer.toString($$0.getY()));
/* 191 */     addWidget((GuiEventListener)this.posYEdit);
/* 192 */     this.posZEdit = new EditBox(this.font, this.width / 2 + 8, 80, 80, 20, (Component)Component.translatable("structure_block.position.z"));
/* 193 */     this.posZEdit.setMaxLength(15);
/* 194 */     this.posZEdit.setValue(Integer.toString($$0.getZ()));
/* 195 */     addWidget((GuiEventListener)this.posZEdit);
/*     */     
/* 197 */     Vec3i $$1 = this.structure.getStructureSize();
/* 198 */     this.sizeXEdit = new EditBox(this.font, this.width / 2 - 152, 120, 80, 20, (Component)Component.translatable("structure_block.size.x"));
/* 199 */     this.sizeXEdit.setMaxLength(15);
/* 200 */     this.sizeXEdit.setValue(Integer.toString($$1.getX()));
/* 201 */     addWidget((GuiEventListener)this.sizeXEdit);
/* 202 */     this.sizeYEdit = new EditBox(this.font, this.width / 2 - 72, 120, 80, 20, (Component)Component.translatable("structure_block.size.y"));
/* 203 */     this.sizeYEdit.setMaxLength(15);
/* 204 */     this.sizeYEdit.setValue(Integer.toString($$1.getY()));
/* 205 */     addWidget((GuiEventListener)this.sizeYEdit);
/* 206 */     this.sizeZEdit = new EditBox(this.font, this.width / 2 + 8, 120, 80, 20, (Component)Component.translatable("structure_block.size.z"));
/* 207 */     this.sizeZEdit.setMaxLength(15);
/* 208 */     this.sizeZEdit.setValue(Integer.toString($$1.getZ()));
/* 209 */     addWidget((GuiEventListener)this.sizeZEdit);
/*     */     
/* 211 */     this.integrityEdit = new EditBox(this.font, this.width / 2 - 152, 120, 80, 20, (Component)Component.translatable("structure_block.integrity.integrity"));
/* 212 */     this.integrityEdit.setMaxLength(15);
/* 213 */     this.integrityEdit.setValue(this.decimalFormat.format(this.structure.getIntegrity()));
/* 214 */     addWidget((GuiEventListener)this.integrityEdit);
/* 215 */     this.seedEdit = new EditBox(this.font, this.width / 2 - 72, 120, 80, 20, (Component)Component.translatable("structure_block.integrity.seed"));
/* 216 */     this.seedEdit.setMaxLength(31);
/* 217 */     this.seedEdit.setValue(Long.toString(this.structure.getSeed()));
/* 218 */     addWidget((GuiEventListener)this.seedEdit);
/*     */     
/* 220 */     this.dataEdit = new EditBox(this.font, this.width / 2 - 152, 120, 240, 20, (Component)Component.translatable("structure_block.custom_data"));
/* 221 */     this.dataEdit.setMaxLength(128);
/* 222 */     this.dataEdit.setValue(this.structure.getMetaData());
/* 223 */     addWidget((GuiEventListener)this.dataEdit);
/*     */     
/* 225 */     updateDirectionButtons();
/* 226 */     updateMode(this.initialMode);
/*     */     
/* 228 */     setInitialFocus((GuiEventListener)this.nameEdit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Minecraft $$0, int $$1, int $$2) {
/* 233 */     String $$3 = this.nameEdit.getValue();
/* 234 */     String $$4 = this.posXEdit.getValue();
/* 235 */     String $$5 = this.posYEdit.getValue();
/* 236 */     String $$6 = this.posZEdit.getValue();
/* 237 */     String $$7 = this.sizeXEdit.getValue();
/* 238 */     String $$8 = this.sizeYEdit.getValue();
/* 239 */     String $$9 = this.sizeZEdit.getValue();
/* 240 */     String $$10 = this.integrityEdit.getValue();
/* 241 */     String $$11 = this.seedEdit.getValue();
/* 242 */     String $$12 = this.dataEdit.getValue();
/*     */     
/* 244 */     init($$0, $$1, $$2);
/*     */     
/* 246 */     this.nameEdit.setValue($$3);
/* 247 */     this.posXEdit.setValue($$4);
/* 248 */     this.posYEdit.setValue($$5);
/* 249 */     this.posZEdit.setValue($$6);
/* 250 */     this.sizeXEdit.setValue($$7);
/* 251 */     this.sizeYEdit.setValue($$8);
/* 252 */     this.sizeZEdit.setValue($$9);
/* 253 */     this.integrityEdit.setValue($$10);
/* 254 */     this.seedEdit.setValue($$11);
/* 255 */     this.dataEdit.setValue($$12);
/*     */   }
/*     */   
/*     */   private void updateDirectionButtons() {
/* 259 */     this.rot0Button.active = true;
/* 260 */     this.rot90Button.active = true;
/* 261 */     this.rot180Button.active = true;
/* 262 */     this.rot270Button.active = true;
/*     */     
/* 264 */     switch (this.structure.getRotation()) {
/*     */       case SAVE:
/* 266 */         this.rot0Button.active = false;
/*     */         break;
/*     */       case LOAD:
/* 269 */         this.rot180Button.active = false;
/*     */         break;
/*     */       case CORNER:
/* 272 */         this.rot270Button.active = false;
/*     */         break;
/*     */       case DATA:
/* 275 */         this.rot90Button.active = false;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateMode(StructureMode $$0) {
/* 281 */     this.nameEdit.setVisible(false);
/* 282 */     this.posXEdit.setVisible(false);
/* 283 */     this.posYEdit.setVisible(false);
/* 284 */     this.posZEdit.setVisible(false);
/* 285 */     this.sizeXEdit.setVisible(false);
/* 286 */     this.sizeYEdit.setVisible(false);
/* 287 */     this.sizeZEdit.setVisible(false);
/* 288 */     this.integrityEdit.setVisible(false);
/* 289 */     this.seedEdit.setVisible(false);
/* 290 */     this.dataEdit.setVisible(false);
/*     */     
/* 292 */     this.saveButton.visible = false;
/* 293 */     this.loadButton.visible = false;
/* 294 */     this.detectButton.visible = false;
/* 295 */     this.includeEntitiesButton.visible = false;
/* 296 */     this.mirrorButton.visible = false;
/* 297 */     this.rot0Button.visible = false;
/* 298 */     this.rot90Button.visible = false;
/* 299 */     this.rot180Button.visible = false;
/* 300 */     this.rot270Button.visible = false;
/* 301 */     this.toggleAirButton.visible = false;
/* 302 */     this.toggleBoundingBox.visible = false;
/*     */     
/* 304 */     switch ($$0) {
/*     */       case SAVE:
/* 306 */         this.nameEdit.setVisible(true);
/* 307 */         this.posXEdit.setVisible(true);
/* 308 */         this.posYEdit.setVisible(true);
/* 309 */         this.posZEdit.setVisible(true);
/* 310 */         this.sizeXEdit.setVisible(true);
/* 311 */         this.sizeYEdit.setVisible(true);
/* 312 */         this.sizeZEdit.setVisible(true);
/* 313 */         this.saveButton.visible = true;
/* 314 */         this.detectButton.visible = true;
/* 315 */         this.includeEntitiesButton.visible = true;
/* 316 */         this.toggleAirButton.visible = true;
/*     */         break;
/*     */       case LOAD:
/* 319 */         this.nameEdit.setVisible(true);
/* 320 */         this.posXEdit.setVisible(true);
/* 321 */         this.posYEdit.setVisible(true);
/* 322 */         this.posZEdit.setVisible(true);
/* 323 */         this.integrityEdit.setVisible(true);
/* 324 */         this.seedEdit.setVisible(true);
/* 325 */         this.loadButton.visible = true;
/* 326 */         this.includeEntitiesButton.visible = true;
/* 327 */         this.mirrorButton.visible = true;
/* 328 */         this.rot0Button.visible = true;
/* 329 */         this.rot90Button.visible = true;
/* 330 */         this.rot180Button.visible = true;
/* 331 */         this.rot270Button.visible = true;
/* 332 */         this.toggleBoundingBox.visible = true;
/* 333 */         updateDirectionButtons();
/*     */         break;
/*     */       case CORNER:
/* 336 */         this.nameEdit.setVisible(true);
/*     */         break;
/*     */       case DATA:
/* 339 */         this.dataEdit.setVisible(true);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean sendToServer(StructureBlockEntity.UpdateType $$0) {
/* 345 */     BlockPos $$1 = new BlockPos(parseCoordinate(this.posXEdit.getValue()), parseCoordinate(this.posYEdit.getValue()), parseCoordinate(this.posZEdit.getValue()));
/* 346 */     Vec3i $$2 = new Vec3i(parseCoordinate(this.sizeXEdit.getValue()), parseCoordinate(this.sizeYEdit.getValue()), parseCoordinate(this.sizeZEdit.getValue()));
/* 347 */     float $$3 = parseIntegrity(this.integrityEdit.getValue());
/* 348 */     long $$4 = parseSeed(this.seedEdit.getValue());
/* 349 */     this.minecraft.getConnection().send((Packet)new ServerboundSetStructureBlockPacket(this.structure.getBlockPos(), $$0, this.structure.getMode(), this.nameEdit.getValue(), $$1, $$2, this.structure.getMirror(), this.structure.getRotation(), this.dataEdit.getValue(), this.structure.isIgnoreEntities(), this.structure.getShowAir(), this.structure.getShowBoundingBox(), $$3, $$4));
/* 350 */     return true;
/*     */   }
/*     */   
/*     */   private long parseSeed(String $$0) {
/*     */     try {
/* 355 */       return Long.valueOf($$0).longValue();
/* 356 */     } catch (NumberFormatException $$1) {
/* 357 */       return 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   private float parseIntegrity(String $$0) {
/*     */     try {
/* 363 */       return Float.valueOf($$0).floatValue();
/* 364 */     } catch (NumberFormatException $$1) {
/* 365 */       return 1.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int parseCoordinate(String $$0) {
/*     */     try {
/* 371 */       return Integer.parseInt($$0);
/* 372 */     } catch (NumberFormatException $$1) {
/* 373 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 379 */     onCancel();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 384 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 385 */       return true;
/*     */     }
/*     */     
/* 388 */     if ($$0 == 257 || $$0 == 335) {
/* 389 */       onDone();
/* 390 */       return true;
/*     */     } 
/*     */     
/* 393 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 398 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 400 */     StructureMode $$4 = this.structure.getMode();
/* 401 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 10, 16777215);
/*     */     
/* 403 */     if ($$4 != StructureMode.DATA) {
/* 404 */       $$0.drawString(this.font, NAME_LABEL, this.width / 2 - 153, 30, 10526880);
/* 405 */       this.nameEdit.render($$0, $$1, $$2, $$3);
/*     */     } 
/*     */     
/* 408 */     if ($$4 == StructureMode.LOAD || $$4 == StructureMode.SAVE) {
/* 409 */       $$0.drawString(this.font, POSITION_LABEL, this.width / 2 - 153, 70, 10526880);
/* 410 */       this.posXEdit.render($$0, $$1, $$2, $$3);
/* 411 */       this.posYEdit.render($$0, $$1, $$2, $$3);
/* 412 */       this.posZEdit.render($$0, $$1, $$2, $$3);
/*     */       
/* 414 */       $$0.drawString(this.font, INCLUDE_ENTITIES_LABEL, this.width / 2 + 154 - this.font.width((FormattedText)INCLUDE_ENTITIES_LABEL), 150, 10526880);
/*     */     } 
/*     */     
/* 417 */     if ($$4 == StructureMode.SAVE) {
/* 418 */       $$0.drawString(this.font, SIZE_LABEL, this.width / 2 - 153, 110, 10526880);
/* 419 */       this.sizeXEdit.render($$0, $$1, $$2, $$3);
/* 420 */       this.sizeYEdit.render($$0, $$1, $$2, $$3);
/* 421 */       this.sizeZEdit.render($$0, $$1, $$2, $$3);
/*     */       
/* 423 */       $$0.drawString(this.font, DETECT_SIZE_LABEL, this.width / 2 + 154 - this.font.width((FormattedText)DETECT_SIZE_LABEL), 110, 10526880);
/* 424 */       $$0.drawString(this.font, SHOW_AIR_LABEL, this.width / 2 + 154 - this.font.width((FormattedText)SHOW_AIR_LABEL), 70, 10526880);
/*     */     } 
/*     */     
/* 427 */     if ($$4 == StructureMode.LOAD) {
/* 428 */       $$0.drawString(this.font, INTEGRITY_LABEL, this.width / 2 - 153, 110, 10526880);
/* 429 */       this.integrityEdit.render($$0, $$1, $$2, $$3);
/* 430 */       this.seedEdit.render($$0, $$1, $$2, $$3);
/*     */       
/* 432 */       $$0.drawString(this.font, SHOW_BOUNDING_BOX_LABEL, this.width / 2 + 154 - this.font.width((FormattedText)SHOW_BOUNDING_BOX_LABEL), 70, 10526880);
/*     */     } 
/*     */     
/* 435 */     if ($$4 == StructureMode.DATA) {
/* 436 */       $$0.drawString(this.font, CUSTOM_DATA_LABEL, this.width / 2 - 153, 110, 10526880);
/* 437 */       this.dataEdit.render($$0, $$1, $$2, $$3);
/*     */     } 
/*     */     
/* 440 */     $$0.drawString(this.font, $$4.getDisplayName(), this.width / 2 - 153, 174, 10526880);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 445 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\StructureBlockEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */