/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import com.google.common.base.Stopwatch;
/*    */ import java.io.File;
/*    */ import java.time.Instant;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerException;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.dom.DOMSource;
/*    */ import javax.xml.transform.stream.StreamResult;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class JUnitLikeTestReporter
/*    */   implements TestReporter {
/*    */   private final Document document;
/*    */   private final Element testSuite;
/*    */   private final Stopwatch stopwatch;
/*    */   private final File destination;
/*    */   
/*    */   public JUnitLikeTestReporter(File $$0) throws ParserConfigurationException {
/* 26 */     this.destination = $$0;
/* 27 */     this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
/* 28 */     this.testSuite = this.document.createElement("testsuite");
/* 29 */     Element $$1 = this.document.createElement("testsuite");
/* 30 */     $$1.appendChild(this.testSuite);
/* 31 */     this.document.appendChild($$1);
/*    */     
/* 33 */     this.testSuite.setAttribute("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
/*    */     
/* 35 */     this.stopwatch = Stopwatch.createStarted();
/*    */   }
/*    */   
/*    */   private Element createTestCase(GameTestInfo $$0, String $$1) {
/* 39 */     Element $$2 = this.document.createElement("testcase");
/* 40 */     $$2.setAttribute("name", $$1);
/* 41 */     $$2.setAttribute("classname", $$0.getStructureName());
/* 42 */     $$2.setAttribute("time", String.valueOf($$0.getRunTime() / 1000.0D));
/* 43 */     this.testSuite.appendChild($$2);
/* 44 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTestFailed(GameTestInfo $$0) {
/* 49 */     String $$1 = $$0.getTestName();
/* 50 */     String $$2 = $$0.getError().getMessage();
/*    */     
/* 52 */     Element $$3 = this.document.createElement($$0.isRequired() ? "failure" : "skipped");
/* 53 */     $$3.setAttribute("message", "(" + $$0.getStructureBlockPos().toShortString() + ") " + $$2);
/*    */     
/* 55 */     Element $$4 = createTestCase($$0, $$1);
/* 56 */     $$4.appendChild($$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTestSuccess(GameTestInfo $$0) {
/* 61 */     String $$1 = $$0.getTestName();
/* 62 */     createTestCase($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void finish() {
/* 67 */     this.stopwatch.stop();
/* 68 */     this.testSuite.setAttribute("time", String.valueOf(this.stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000.0D));
/*    */     
/*    */     try {
/* 71 */       save(this.destination);
/* 72 */     } catch (TransformerException $$0) {
/* 73 */       throw new Error("Couldn't save test report", $$0);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void save(File $$0) throws TransformerException {
/* 78 */     TransformerFactory $$1 = TransformerFactory.newInstance();
/* 79 */     Transformer $$2 = $$1.newTransformer();
/* 80 */     DOMSource $$3 = new DOMSource(this.document);
/* 81 */     StreamResult $$4 = new StreamResult($$0);
/* 82 */     $$2.transform($$3, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\JUnitLikeTestReporter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */