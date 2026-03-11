/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.execution.TraceCallbacks;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Tracer
/*     */   implements CommandSource, TraceCallbacks
/*     */ {
/*     */   public static final int INDENT_OFFSET = 1;
/*     */   private final PrintWriter output;
/*     */   private int lastIndent;
/*     */   private boolean waitingForResult;
/*     */   
/*     */   Tracer(PrintWriter $$0) {
/* 163 */     this.output = $$0;
/*     */   }
/*     */   
/*     */   private void indentAndSave(int $$0) {
/* 167 */     printIndent($$0);
/* 168 */     this.lastIndent = $$0;
/*     */   }
/*     */   
/*     */   private void printIndent(int $$0) {
/* 172 */     for (int $$1 = 0; $$1 < $$0 + 1; $$1++) {
/* 173 */       this.output.write("    ");
/*     */     }
/*     */   }
/*     */   
/*     */   private void newLine() {
/* 178 */     if (this.waitingForResult) {
/* 179 */       this.output.println();
/* 180 */       this.waitingForResult = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCommand(int $$0, String $$1) {
/* 186 */     newLine();
/* 187 */     indentAndSave($$0);
/* 188 */     this.output.print("[C] ");
/* 189 */     this.output.print($$1);
/* 190 */     this.waitingForResult = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReturn(int $$0, String $$1, int $$2) {
/* 195 */     if (this.waitingForResult) {
/* 196 */       this.output.print(" -> ");
/* 197 */       this.output.println($$2);
/* 198 */       this.waitingForResult = false;
/*     */     } else {
/* 200 */       indentAndSave($$0);
/* 201 */       this.output.print("[R = ");
/* 202 */       this.output.print($$2);
/* 203 */       this.output.print("] ");
/* 204 */       this.output.println($$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCall(int $$0, ResourceLocation $$1, int $$2) {
/* 210 */     newLine();
/* 211 */     indentAndSave($$0);
/* 212 */     this.output.print("[F] ");
/* 213 */     this.output.print($$1);
/* 214 */     this.output.print(" size=");
/* 215 */     this.output.println($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onError(String $$0) {
/* 220 */     newLine();
/* 221 */     indentAndSave(this.lastIndent + 1);
/* 222 */     this.output.print("[E] ");
/* 223 */     this.output.print($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendSystemMessage(Component $$0) {
/* 228 */     newLine();
/* 229 */     printIndent(this.lastIndent + 1);
/* 230 */     this.output.print("[M] ");
/* 231 */     this.output.println($$0.getString());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsSuccess() {
/* 236 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsFailure() {
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldInformAdmins() {
/* 246 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean alwaysAccepts() {
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 256 */     IOUtils.closeQuietly(this.output);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DebugCommand$Tracer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */