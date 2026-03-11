/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.world.level.BaseCommandBlock;
/*     */ import net.minecraft.world.level.block.entity.CommandBlockEntity;
/*     */ 
/*     */ public class CommandBlockEditScreen extends AbstractCommandBlockEditScreen {
/*     */   private final CommandBlockEntity autoCommandBlock;
/*     */   private CycleButton<CommandBlockEntity.Mode> modeButton;
/*     */   private CycleButton<Boolean> conditionalButton;
/*     */   private CycleButton<Boolean> autoexecButton;
/*  16 */   private CommandBlockEntity.Mode mode = CommandBlockEntity.Mode.REDSTONE;
/*     */   private boolean conditional;
/*     */   private boolean autoexec;
/*     */   
/*     */   public CommandBlockEditScreen(CommandBlockEntity $$0) {
/*  21 */     this.autoCommandBlock = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   BaseCommandBlock getCommandBlock() {
/*  26 */     return this.autoCommandBlock.getCommandBlock();
/*     */   }
/*     */ 
/*     */   
/*     */   int getPreviousY() {
/*  31 */     return 135;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  36 */     super.init();
/*  37 */     this.modeButton = (CycleButton<CommandBlockEntity.Mode>)addRenderableWidget((GuiEventListener)CycleButton.builder($$0 -> { switch ($$0) { default:
/*     */                 throw new IncompatibleClassChangeError();
/*     */               case SEQUENCE:
/*     */               
/*     */               case AUTO:
/*     */               
/*     */               case REDSTONE:
/*  44 */                 break; }  return (Component)Component.translatable("advMode.mode.redstone"); }).withValues((Object[])CommandBlockEntity.Mode.values())
/*  45 */         .displayOnlyValue()
/*  46 */         .withInitialValue(this.mode)
/*  47 */         .create(this.width / 2 - 50 - 100 - 4, 165, 100, 20, (Component)Component.translatable("advMode.mode"), ($$0, $$1) -> this.mode = $$1));
/*     */ 
/*     */     
/*  50 */     this.conditionalButton = (CycleButton<Boolean>)addRenderableWidget(
/*  51 */         (GuiEventListener)CycleButton.booleanBuilder((Component)Component.translatable("advMode.mode.conditional"), (Component)Component.translatable("advMode.mode.unconditional"))
/*  52 */         .displayOnlyValue()
/*  53 */         .withInitialValue(Boolean.valueOf(this.conditional))
/*  54 */         .create(this.width / 2 - 50, 165, 100, 20, (Component)Component.translatable("advMode.type"), ($$0, $$1) -> this.conditional = $$1.booleanValue()));
/*     */ 
/*     */     
/*  57 */     this.autoexecButton = (CycleButton<Boolean>)addRenderableWidget(
/*  58 */         (GuiEventListener)CycleButton.booleanBuilder((Component)Component.translatable("advMode.mode.autoexec.bat"), (Component)Component.translatable("advMode.mode.redstoneTriggered"))
/*  59 */         .displayOnlyValue()
/*  60 */         .withInitialValue(Boolean.valueOf(this.autoexec))
/*  61 */         .create(this.width / 2 + 50 + 4, 165, 100, 20, (Component)Component.translatable("advMode.triggering"), ($$0, $$1) -> this.autoexec = $$1.booleanValue()));
/*     */ 
/*     */     
/*  64 */     enableControls(false);
/*     */   }
/*     */   
/*     */   private void enableControls(boolean $$0) {
/*  68 */     this.doneButton.active = $$0;
/*  69 */     this.outputButton.active = $$0;
/*  70 */     this.modeButton.active = $$0;
/*  71 */     this.conditionalButton.active = $$0;
/*  72 */     this.autoexecButton.active = $$0;
/*     */   }
/*     */   
/*     */   public void updateGui() {
/*  76 */     BaseCommandBlock $$0 = this.autoCommandBlock.getCommandBlock();
/*  77 */     this.commandEdit.setValue($$0.getCommand());
/*  78 */     boolean $$1 = $$0.isTrackOutput();
/*  79 */     this.mode = this.autoCommandBlock.getMode();
/*  80 */     this.conditional = this.autoCommandBlock.isConditional();
/*  81 */     this.autoexec = this.autoCommandBlock.isAutomatic();
/*     */     
/*  83 */     this.outputButton.setValue(Boolean.valueOf($$1));
/*  84 */     this.modeButton.setValue(this.mode);
/*  85 */     this.conditionalButton.setValue(Boolean.valueOf(this.conditional));
/*  86 */     this.autoexecButton.setValue(Boolean.valueOf(this.autoexec));
/*     */     
/*  88 */     updatePreviousOutput($$1);
/*  89 */     enableControls(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Minecraft $$0, int $$1, int $$2) {
/*  94 */     super.resize($$0, $$1, $$2);
/*  95 */     enableControls(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateAndSendPacket(BaseCommandBlock $$0) {
/* 100 */     this.minecraft.getConnection().send((Packet)new ServerboundSetCommandBlockPacket(BlockPos.containing((Position)$$0.getPosition()), this.commandEdit.getValue(), this.mode, $$0.isTrackOutput(), this.conditional, this.autoexec));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\CommandBlockEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */