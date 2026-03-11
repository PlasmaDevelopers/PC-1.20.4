/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import com.mojang.datafixers.util.Unit;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Optionull;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.OptionInstance;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.Difficulty;
/*    */ import org.apache.commons.compress.utils.Lists;
/*    */ 
/*    */ public class OnlineOptionsScreen extends SimpleOptionsSubScreen {
/*    */   @Nullable
/*    */   private final OptionInstance<Unit> difficultyDisplay;
/*    */   
/*    */   public static OnlineOptionsScreen createOnlineOptionsScreen(Minecraft $$0, Screen $$1, Options $$2) {
/* 22 */     List<OptionInstance<?>> $$3 = Lists.newArrayList();
/*    */     
/* 24 */     $$3.add($$2.realmsNotifications());
/* 25 */     $$3.add($$2.allowServerListing());
/*    */     
/* 27 */     OptionInstance<Unit> $$4 = (OptionInstance<Unit>)Optionull.map($$0.level, $$0 -> {
/*    */           Difficulty $$1 = $$0.getDifficulty();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           return new OptionInstance("options.difficulty.online", OptionInstance.noTooltip(), (), (OptionInstance.ValueSet)new OptionInstance.Enum(List.of(Unit.INSTANCE), Codec.EMPTY.codec()), Unit.INSTANCE, ());
/*    */         });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     if ($$4 != null) {
/* 41 */       $$3.add($$4);
/*    */     }
/*    */     
/* 44 */     return new OnlineOptionsScreen($$1, $$2, (OptionInstance<?>[])$$3.<OptionInstance>toArray(new OptionInstance[0]), $$4);
/*    */   }
/*    */   
/*    */   private OnlineOptionsScreen(Screen $$0, Options $$1, OptionInstance<?>[] $$2, @Nullable OptionInstance<Unit> $$3) {
/* 48 */     super($$0, $$1, (Component)Component.translatable("options.online.title"), $$2);
/* 49 */     this.difficultyDisplay = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 54 */     super.init();
/*    */     
/* 56 */     if (this.difficultyDisplay != null) {
/* 57 */       AbstractWidget $$0 = this.list.findOption(this.difficultyDisplay);
/* 58 */       if ($$0 != null) {
/* 59 */         $$0.active = false;
/*    */       }
/*    */     } 
/*    */     
/* 63 */     AbstractWidget $$1 = this.list.findOption(this.options.telemetryOptInExtra());
/* 64 */     if ($$1 != null)
/* 65 */       $$1.active = this.minecraft.extraTelemetryAvailable(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\OnlineOptionsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */