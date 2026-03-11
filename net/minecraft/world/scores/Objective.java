/*    */ package net.minecraft.world.scores;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.network.chat.HoverEvent;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.network.chat.numbers.NumberFormat;
/*    */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*    */ 
/*    */ public class Objective {
/*    */   private final Scoreboard scoreboard;
/*    */   private final String name;
/*    */   private final ObjectiveCriteria criteria;
/*    */   private Component displayName;
/*    */   private Component formattedDisplayName;
/*    */   private ObjectiveCriteria.RenderType renderType;
/*    */   private boolean displayAutoUpdate;
/*    */   @Nullable
/*    */   private NumberFormat numberFormat;
/*    */   
/*    */   public Objective(Scoreboard $$0, String $$1, ObjectiveCriteria $$2, Component $$3, ObjectiveCriteria.RenderType $$4, boolean $$5, @Nullable NumberFormat $$6) {
/* 24 */     this.scoreboard = $$0;
/* 25 */     this.name = $$1;
/* 26 */     this.criteria = $$2;
/* 27 */     this.displayName = $$3;
/* 28 */     this.formattedDisplayName = createFormattedDisplayName();
/* 29 */     this.renderType = $$4;
/* 30 */     this.displayAutoUpdate = $$5;
/* 31 */     this.numberFormat = $$6;
/*    */   }
/*    */   
/*    */   public Scoreboard getScoreboard() {
/* 35 */     return this.scoreboard;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 39 */     return this.name;
/*    */   }
/*    */   
/*    */   public ObjectiveCriteria getCriteria() {
/* 43 */     return this.criteria;
/*    */   }
/*    */   
/*    */   public Component getDisplayName() {
/* 47 */     return this.displayName;
/*    */   }
/*    */   
/*    */   public boolean displayAutoUpdate() {
/* 51 */     return this.displayAutoUpdate;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public NumberFormat numberFormat() {
/* 56 */     return this.numberFormat;
/*    */   }
/*    */   
/*    */   public NumberFormat numberFormatOrDefault(NumberFormat $$0) {
/* 60 */     return Objects.<NumberFormat>requireNonNullElse(this.numberFormat, $$0);
/*    */   }
/*    */   
/*    */   private Component createFormattedDisplayName() {
/* 64 */     return (Component)ComponentUtils.wrapInSquareBrackets((Component)this.displayName
/* 65 */         .copy().withStyle($$0 -> $$0.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(this.name)))));
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getFormattedDisplayName() {
/* 70 */     return this.formattedDisplayName;
/*    */   }
/*    */   
/*    */   public void setDisplayName(Component $$0) {
/* 74 */     this.displayName = $$0;
/* 75 */     this.formattedDisplayName = createFormattedDisplayName();
/* 76 */     this.scoreboard.onObjectiveChanged(this);
/*    */   }
/*    */   
/*    */   public ObjectiveCriteria.RenderType getRenderType() {
/* 80 */     return this.renderType;
/*    */   }
/*    */   
/*    */   public void setRenderType(ObjectiveCriteria.RenderType $$0) {
/* 84 */     this.renderType = $$0;
/* 85 */     this.scoreboard.onObjectiveChanged(this);
/*    */   }
/*    */   
/*    */   public void setDisplayAutoUpdate(boolean $$0) {
/* 89 */     this.displayAutoUpdate = $$0;
/* 90 */     this.scoreboard.onObjectiveChanged(this);
/*    */   }
/*    */   
/*    */   public void setNumberFormat(@Nullable NumberFormat $$0) {
/* 94 */     this.numberFormat = $$0;
/* 95 */     this.scoreboard.onObjectiveChanged(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\Objective.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */